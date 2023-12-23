package Server;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatConversation {
    private static ChatConversation instance;
    HashMap<String, ChatDetail> chatDictionary;

    public static boolean checkDiffUserList(ArrayList<String> list1, ArrayList<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        if (set1.size() != set2.size())
            return true;
        for (String s : set1) {
            if (!set2.contains(s))
                return true;
        }
        return false;
    }
    public class ChatDetail {
        private String groupChatName;
        private boolean type; // true = group chat, false = private chat
        private ArrayList<String> userId;

        public ChatDetail(String groupChatName, boolean type, ArrayList<String> userId) {
            this.groupChatName = groupChatName;
            this.type = type;
            this.userId = userId;
        }
        public boolean getType() {
            return this.type;
        }

        public String getGroupChatName() {
            return this.groupChatName;
        }

        public ArrayList<String> getUserId() {
            return this.userId;
        }
    }
    public boolean isGroupChat(String chatId) {
        return this.chatDictionary.get(chatId).getType();
    }
    public String findChatId(ArrayList<String> userId) {
        for (String chatId : chatDictionary.keySet()) {
            ChatDetail detail = chatDictionary.get(chatId);
            if (checkDiffUserList(detail.getUserId(), userId) == false) {
                return chatId;
            }
        }
        return null;
    }
    public ChatConversation() {
        this.chatDictionary = new HashMap<>();
    }

    public static ChatConversation getInstance() {
        if (instance == null) {
            instance = new ChatConversation();
        }
        return instance;
    }

    public ArrayList<String> getGroupChatNameList(String username){
        ArrayList<String> groupChatName = new ArrayList<>();
        String userId = User.getInstance().findUserId(username);
        for (String chatId : chatDictionary.keySet()) {
            ChatDetail detail = chatDictionary.get(chatId);
            if (detail.getType() == true && detail.getUserId().contains(userId)) {
                groupChatName.add(detail.getGroupChatName());
            }
        }
        return groupChatName;
    }
    public void addChatConversation(String chatId, String groupChatName, boolean type, ArrayList<String> userId) {
        this.chatDictionary.put(chatId, new ChatDetail(groupChatName,type, userId));
    }
    public String getNextChatId() {
        int maxChatId = 0;
        for (String chatId : chatDictionary.keySet()) {
            int currentChatId = Integer.parseInt(chatId.substring(4)); // Remove "chat" from chatId
            if (currentChatId > maxChatId) {
                maxChatId = currentChatId;
            }
        }
        return "chat" + Integer.toString(maxChatId + 1);
    }


    public ArrayList<String> getChatUser(String id) {
        return this.chatDictionary.get(id).userId;
    }
    public String getGroupChatId(String groupChatName) {
        for (String chatId : chatDictionary.keySet()) {
            ChatDetail detail = chatDictionary.get(chatId);
            if (detail.getGroupChatName().equals(groupChatName)) {
                return chatId;
            }
        }
        return null;
    }
    public void saveDataToJson() {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, ChatDetail> entry : chatDictionary.entrySet()) {
            JSONObject chatDetailJson = new JSONObject();
            chatDetailJson.put("groupChatName", entry.getValue().getGroupChatName());
            chatDetailJson.put("type", entry.getValue().getType());
            chatDetailJson.put("userId", entry.getValue().getUserId());
            jsonObject.put(entry.getKey(), chatDetailJson);
        }

        try (FileWriter file = new FileWriter("chat_data.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read from JSON file
    public void loadDataFromJson() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("chat_data.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                JSONObject chatDetailJson = (JSONObject) jsonObject.get(key);
                String groupChatName = (String) chatDetailJson.get("groupChatName");
                boolean type = (boolean) chatDetailJson.get("type");
                ArrayList<String> userId = (ArrayList<String>) chatDetailJson.get("userId");
                chatDictionary.put((String) key, new ChatDetail(groupChatName,type, userId));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


}
