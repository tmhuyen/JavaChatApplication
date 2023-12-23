package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private static Message instance;
    HashMap<String, MessageDetail> messageDictionary;
    public class MessageDetail {
        private String sentId;
        private String chatId;
        private String content;

        public MessageDetail(String sentId, String chatId, String content) {
            this.sentId = sentId;
            this.chatId = chatId;
            this.content = content;
        }
        public String getSentId() {
            return this.sentId;
        }
        public String getChatId() {
            return this.chatId;
        }
        public String getContent() {
            return this.content;
        }
    }
    public Message() {
        this.messageDictionary = new HashMap<>();
    }
    public Message(String messageId, String sentId, String chatId, String content) {
        this.messageDictionary.put(messageId, new MessageDetail(sentId, chatId, content));
    }
    public static Message getInstance() {
        if (instance == null) {
            instance = new Message();
        }
        return instance;
    }
    public void addMessage(String messageId, String sentId, String chatId, String content) {
        this.messageDictionary.put(messageId, new MessageDetail(sentId, chatId, content));
    }
    public String getNextMessageId() {
        int maxMessageId = 0;
        for (String messageId : messageDictionary.keySet()) {
            int currentMessageId = Integer.parseInt(messageId.substring(7)); // Remove "message" from messageId
            if (currentMessageId > maxMessageId) {
                maxMessageId = currentMessageId;
            }
        }
        return "message" + Integer.toString(maxMessageId + 1);
    }
    public String getChatId(String messageId) {
        return this.messageDictionary.get(messageId).chatId;
    }
    public String getContent(String messageId) {
        return this.messageDictionary.get(messageId).content;
    }

    public boolean checkSentUser(String messageId,String userId) {
        if (this.messageDictionary.get(messageId).sentId.equals(userId)) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getMessageList(String chatId) {
        ArrayList<String> messageList = new ArrayList<>();
        for (String messageId : messageDictionary.keySet()) {
            MessageDetail detail = messageDictionary.get(messageId);
            if (detail.getChatId().equals(chatId)) {
                messageList.add(messageId);
            }
        }
        return messageList;
    }
    public String getSendUser(String messageId) {
        return User.getInstance().getUserinfo(this.messageDictionary.get(messageId).sentId).getUsername();
    }
    public void saveDataToJson() {
        JSONObject jsonObject = new JSONObject();
        for (String key : messageDictionary.keySet()) {
            MessageDetail detail = messageDictionary.get(key);
            JSONObject messageDetailJson = new JSONObject();
            messageDetailJson.put("sentId", detail.getSentId());
            messageDetailJson.put("chatId", detail.getChatId());
            messageDetailJson.put("content", detail.getContent());
            jsonObject.put(key, messageDetailJson);
        }

        try (FileWriter file = new FileWriter("message_data.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read from JSON file
    public void loadDataFromJson() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("message_data.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                JSONObject messageDetailJson = (JSONObject) jsonObject.get(key);
                String sentId = (String) messageDetailJson.get("sentId");
                String chatId = (String) messageDetailJson.get("chatId");
                String content = (String) messageDetailJson.get("content");
                messageDictionary.put((String) key, new MessageDetail(sentId, chatId, content));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
