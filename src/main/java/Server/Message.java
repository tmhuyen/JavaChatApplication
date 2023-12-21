package Server;

import java.util.HashMap;

public class Message {
    private static Message instance;
    private HashMap<String, MessageDetail> messageDictionary;

    private Message() {
        this.messageDictionary = new HashMap<>();
    }

    public static Message getInstance() {
        if (instance == null) {
            instance = new Message();
        }
        return instance;
    }

    public void addMessage(String messageId, String sentId, String chatId, String content) {
        this.messageDictionary.put(messageId, new MessageDetail(sentId,chatId, content));
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
    private class MessageDetail {
        private String sentId;
        private String chatId;
        private String content;

        public MessageDetail(String sentId, String chatId, String content) {
            this.sentId = sentId;
            this.chatId = chatId;
            this.content = content;
        }
    }
}
