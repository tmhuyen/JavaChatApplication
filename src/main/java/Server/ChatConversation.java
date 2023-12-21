package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatConversation {
    private static ChatConversation instance;
    private HashMap<String, ChatDetail> chatDictionary;

    private ChatConversation() {
        this.chatDictionary = new HashMap<>();
    }

    public static ChatConversation getInstance() {
        if (instance == null) {
            instance = new ChatConversation();
        }
        return instance;
    }

    public void addChat(String chatId, boolean type, ArrayList<String> userId) {
        this.chatDictionary.put(chatId, new ChatDetail(type, userId));
    }
    public boolean checkGroupchat(String id) {
        if (this.chatDictionary.get(id).type) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getChatUser(String id) {
        return this.chatDictionary.get(id).userId;
    }

    private class ChatDetail {
        private boolean type; // true = group chat, false = private chat
        private ArrayList<String> userId;

        public ChatDetail(boolean type, ArrayList<String> userId) {
            this.type = type;
            this.userId = userId;
        }
    }
}
