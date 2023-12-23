package Server;
import java.util.ArrayList;
import java.util.Arrays;
public class TestDB {
    public static void main(String[] args) {
        /*User test
        User user = new User();
        User.UserDetail detail1 = user.new UserDetail("user1", "password1");
        user.userDictionary.put("1", detail1);

        User.UserDetail detail2 = user.new UserDetail("user2", "password2");
        user.userDictionary.put("2", detail2);

        user.saveDataToJson();

        for (String key : user.userDictionary.keySet()) {
            User.UserDetail detail = user.userDictionary.get(key);
        }
         */



        Message Message = new Message();

        // Add some data to the messageDictionary
        Message.messageDictionary.put("message1", Message.new MessageDetail("user1", "chat1", "Hello"));
        Message.messageDictionary.put("message2", Message.new MessageDetail("user1", "chat1", "Hi"));

        // Write the data to a JSON file
        Message.saveDataToJson();

        // Clear the messageDictionary to simulate reading from an empty state
        Message.messageDictionary.clear();

        // Read the data back from the JSON file
        Message.loadDataFromJson();

        // Print the data to verify that it was written and read correctly
        for (String key : Message.messageDictionary.keySet()) {
            Message.MessageDetail messageDetail = Message.messageDictionary.get(key);
            System.out.println("Message ID: " + key);
            System.out.println("Sent ID: " + messageDetail.getSentId());
            User.getInstance().loadDataFromJson();
            User.UserDetail userDetail = User.getInstance().getUserinfo(messageDetail.getSentId());
            System.out.println("Username: " + userDetail.getUsername());
            System.out.println("Chat ID: " + messageDetail.getChatId());
            System.out.println("Content: " + messageDetail.getContent());
            System.out.println();
        }
        System.out.println("Next message ID: " + Message.getNextMessageId());
    }
}
