package medicinetracker;

import java.util.HashSet;
import java.util.Set;

public class UserService {

    private static final Set<String> USERS = new HashSet<>();

    // Check if user exists
    public static boolean userExists(String username) {
        return USERS.contains(username.toLowerCase());
    }

    // Add new user
    public static void addUser(String username) {
        USERS.add(username.toLowerCase());
    }
}