package Server;

import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User {
    private static User instance;
    HashMap<String, UserDetail> userDictionary;

    public User() {
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

    public UserDetail getUserinfo(String userId) {
        return this.userDictionary.get(userId);
    }

    public class UserDetail {
        private String username;
        private String password;

        public UserDetail(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return this.username;
        }
        public String getPassword() {
            return this.password;
        }
    }

    public String getNextUserId() {
        int maxUserId = 0;
        for (String userId : userDictionary.keySet()) {
            int currentUserId = Integer.parseInt(userId.substring(4)); // Remove "user" from chatId
            if (currentUserId > maxUserId) {
                maxUserId = currentUserId;
            }
        }
        return "user" + Integer.toString(maxUserId + 1);
    }

    public String findUserId(String username) {
        for (String userId : userDictionary.keySet()) {
            UserDetail detail = userDictionary.get(userId);
            if (detail.getUsername().equals(username)) {
                return userId;
            }
        }
        return null;
    }
    public void saveDataToJson() {
        String filename = "user_data.json";
        JSONObject jsonObject = new JSONObject();
        for (String key : userDictionary.keySet()) {
            UserDetail detail = userDictionary.get(key);
            JSONObject userDetailJson = new JSONObject();
            userDetailJson.put("username", detail.username);
            userDetailJson.put("password", detail.password);
            jsonObject.put(key, userDetailJson);
        }
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromJson() {
        this.userDictionary.clear();
        String filename = "user_data.json";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filename)) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object keyObj : jsonObject.keySet()) {
                String key = (String) keyObj;
                JSONObject userDetailJson = (JSONObject) jsonObject.get(key);
                UserDetail detail = new UserDetail((String) userDetailJson.get("username"), (String) userDetailJson.get("password"));
                userDictionary.put(key, detail);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
