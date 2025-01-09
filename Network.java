

public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /**
     * Creates a network with a given maximum number of users.
     */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users for testing purposes.
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /**
     * Returns the number of users in this network.
     */
    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Finds in this network, and returns, the user that has the given name. If
     * there is no such user, returns null.
     */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) {
                return users[i];
            }
        }
        return null;
    }

    /**
     * Adds a new user with the given name to this network. If the network is
     * full, or the name is already a user, does nothing and returns false.
     * Otherwise, creates a new user with the given name, adds to the network,
     * and returns true.
     */
    public boolean addUser(String name) {
        if (userCount >= users.length || getUser(name) != null) {
            return false;
        }
        users[userCount++] = new User(name);
        return true;
    }

    /**
     * Makes the user with name1 follow the user with name2. If successful,
     * returns true. If any of the two names is not a user in this network, or
     * if the "follows" addition failed for some reason, returns false.
     */
    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false;
        }
        if (name1.equalsIgnoreCase(name2)) {
            return false;
        }
        return user1.addFollowee(name2);
    }

    /**
     * For the user with the given name, recommends another user to follow. The
     * recommended user is the user that has the maximal mutual number of
     * followees as the user with the given name.
     */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) {
            return null;
        }

        String recommendation = null;
        int maxMutual = -1;

        for (int i = 0; i < userCount; i++) {
            User potential = users[i];
            if (!user.follows(potential.getName()) && !potential.getName().equals(name)) {
                int mutual = user.countMutual(potential);
                if (mutual > maxMutual) {
                    maxMutual = mutual;
                    recommendation = potential.getName();
                }
            }
        }
        return recommendation;
    }

    /**
     * Computes and returns the name of the most popular user in this network:
     * The user who appears the most in the follow lists of all the users.
     */
    public String mostPopularUser() {
        String mostPopular = null;
        int maxCount = 0;

        for (int i = 0; i < userCount; i++) {
            String name = users[i].getName();
            int count = followeeCount(name);
            if (count > maxCount) {
                maxCount = count;
                mostPopular = name;
            }
        }
        return mostPopular;
    }

    /**
     * Returns the number of times that the given name appears in the follows
     * lists of all the users in this network.
     */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a textual description of all the users in this network, and who
     * they follow.
     */
    public String toString() {
        StringBuilder result = new StringBuilder("Network:");
        for (int i = 0; i < userCount; i++) {
            result.append("\n").append(users[i].toString());
        }
        return result.toString();
    }

}
