package Server;

import java.util.HashMap;

public class User {
    private static User instance;
    private HashMap<String, UserDetail> userDictionary;

    private User() {
        this.userDictionary = new HashMap<>();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void addUser(String userId, String username, String password) {
        this.userDictionary.put(userId, new UserDetail(username, password));
    }

    private class UserDetail {
        private String username;
        private String password;


        public UserDetail(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
