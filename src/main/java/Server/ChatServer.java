package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Server server;
    private static Thread serverThread;
    //Load database

    public static void main(String[] args) {
        //Load database
        User.getInstance().loadDataFromJson();
        Message.getInstance().loadDataFromJson();
        ChatConversation.getInstance().loadDataFromJson();
        //Load Login/Signup GUI
        JFrame frame = new JFrame("Server Control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Server's status");
        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.add(new JLabel());
        panel.add(label);
        panel.add(new JLabel());
        panel.add(new JLabel());
        JButton button = new JButton("Start Server");
        panel.add(button);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("Start Server")) {
                    server = new Server();
                    serverThread = new Thread(server);
                    serverThread.start();
                    label.setText("Server is running");
                    button.setEnabled( false );
                } else {
                    server.stop();
                    try {
                        serverThread.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    label.setText("Server is stopped");
                    button.setText("Start Server");
                }
            }
        });
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class Server implements Runnable {
    private static final int PORT = 1234;
    private static HashSet<PrintWriter> writers = new HashSet<>();

    public void run() {
        System.out.println("The chat server is running.");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                new Handler(listener.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        Thread.currentThread().interrupt();
    }

    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public synchronized void createAccount(String username, String password) {
            User user = User.getInstance();
            user.loadDataFromJson();
            user.addUser(user.getNextUserId(), username, password);
            user.saveDataToJson();
        }
        public synchronized void createChatConversation(String groupChatName,boolean type,ArrayList<String> memberList) {
            // type = true -> group chat
            ChatConversation chat = ChatConversation.getInstance();
            chat.loadDataFromJson();
            String chatId = chat.getNextChatId();
            chat.addChatConversation(chatId,groupChatName,type,memberList);
            chat.saveDataToJson();
        }
        public synchronized void createMessage(String chatId,String senderId,String content) {
            Message message = Message.getInstance();
            message.loadDataFromJson();
            message.addMessage(message.getNextMessageId(),chatId,senderId,content);
            message.saveDataToJson();
        }
        public boolean checkUserExist(String username) {
            User user = User.getInstance();
            for (String userId : user.userDictionary.keySet()) {
                User.UserDetail detail = user.getUserinfo(userId);
                if (detail.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        }
        public boolean authentication(String username, String password) {
            User user = User.getInstance();
            user.loadDataFromJson();
            for (String userId : user.userDictionary.keySet()) {
                User.UserDetail detail = user.getUserinfo(userId);
                System.out.println(detail.getUsername()+" "+detail.getPassword());
                if (detail.getUsername().equals(username) && detail.getPassword().equals(password)) {
                    return true;
                }
            }
            return false;
        }
        public String checkExistPrivateChatAndCreate(String line){
            String substring = line.substring(16);
            String chatUser[] = substring.split("\\|",2);
            ArrayList <String> userId = new ArrayList<>();
            userId.add(User.getInstance().findUserId(chatUser[0]));
            userId.add(User.getInstance().findUserId(chatUser[1]));
            System.out.println(userId);
            if (ChatConversation.getInstance().findChatId(userId) == null) {
                createChatConversation(chatUser[0]+"|"+chatUser[1],false,userId);
            }
            return ChatConversation.getInstance().findChatId(userId);
        }
        public String createGroupChat(String line){
            //line = "CREATE GROUP CHAT|groupChatName|member1|member2|member3|..."
            String substring = line.substring(18);
            String[] groupChatInfo = substring.split("\\|");
            String groupChatName = groupChatInfo[0];
            ArrayList <String> userId = new ArrayList<>();
            for (int i = 1; i < groupChatInfo.length; i++) {
                userId.add(User.getInstance().findUserId(groupChatInfo[i]));
            }
            ChatConversation.getInstance().addNewGroupChat(groupChatName,userId);
            ChatConversation.getInstance().saveDataToJson();
            return ChatConversation.getInstance().findChatId(userId);
        }
        public void sendOnlineUserList(String currentUsername) {
            ArrayList<String> onlineUser = OnlineUser.getInstance().getOnlineUsers();
            out.println("ONLINE USER" + onlineUser.size());
            for (String username : onlineUser) {
                    out.println(username);
            }
        }

        public void printAllMessage(){
            for (String messageId : Message.getInstance().messageDictionary.keySet()) {
                System.out.println(messageId);
                System.out.println(Message.getInstance().messageDictionary.get(messageId).getSentId());
                System.out.println(Message.getInstance().messageDictionary.get(messageId).getChatId());
                System.out.println(Message.getInstance().messageDictionary.get(messageId).getContent());
            }
        }
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    //Load data
                    User.getInstance().loadDataFromJson();
                    Message.getInstance().loadDataFromJson();
                    ChatConversation.getInstance().loadDataFromJson();
                    String line = in.readLine();
                    if (line.startsWith("LOGIN")) {
                        String username = in.readLine();
                        String password = in.readLine();
                        System.out.println(username + " " + password);
                        System.out.println(authentication(username, password));
                        if (authentication(username, password)) {
                            out.println("LOGIN SUCCESS");
                            OnlineUser.getInstance().addOnlineUser(username);
                            System.out.println(OnlineUser.getInstance().getOnlineUsers());
                        } else {
                            out.println("LOGIN FAILED");
                        }
                    } else if (line.startsWith("SIGNUP")) {
                        String username = in.readLine();
                        System.out.println(username);
                        String password = in.readLine();
                        System.out.println(password);
                        String confirmPassword = in.readLine();
                        System.out.println(confirmPassword);
                        System.out.println(password.equals(confirmPassword));
                        if (password.equals(confirmPassword)) {
                            out.println("SIGNUP SUCCESS");
                            createAccount(username, password);
                        } else if (checkUserExist(username)) {
                            out.println("SIGNUP USERNAME EXIST");
                        } else {
                            out.println("SIGNUP FAILED");
                        }
                    } else if (line.startsWith("GET ONLINE USER")) {
                        String currentUsername = line.substring(15);
                        sendOnlineUserList(currentUsername);
                    } else if (line.startsWith("GET GROUP CHAT LIST")) {
                        String username = line.substring(19);
                        ArrayList<String> groupChatName = ChatConversation.getInstance().getGroupChatNameList(username);
                        out.println("GROUP CHAT LIST" + groupChatName.size());
                        for (String chatName : groupChatName) {
                            out.println(chatName);
                        }
                    } else if (line.startsWith("GET PRIVATE CHAT")) {
                        String chatId = checkExistPrivateChatAndCreate(line);
                        printAllMessage();
                        ArrayList<String> messageList = new ArrayList<>();
                        messageList.clear();
                        messageList = Message.getInstance().getMessageList(chatId);
                        for (String messageId : messageList) {
                            System.out.println(messageId);
                            System.out.println(Message.getInstance().getSendUser(messageId) + ": " + Message.getInstance().getContent(messageId));
                        }
                        Collections.reverse(messageList);
                        out.println("PRIVATE MESSAGE LIST" + messageList.size());
                        out.println(chatId);
                        for (String messageId : messageList) {
                            out.println(messageId);
                            out.println(Message.getInstance().getSendUser(messageId) + ": " + Message.getInstance().getContent(messageId));
                        }
                    } else if (line.startsWith("GET GROUP CHAT")) {
                        String groupChatName = line.substring(15);
                        String chatId = ChatConversation.getInstance().getGroupChatId(groupChatName);
                        ArrayList<String> messageList = Message.getInstance().getMessageList(chatId);
                        Collections.reverse(messageList);
                        out.println("GROUP MESSAGE LIST" + messageList.size());
                        out.println(chatId);
                        for (String messageId : messageList) {
                            out.println(messageId);
                            out.println(Message.getInstance().getSendUser(messageId) + ": " + Message.getInstance().getContent(messageId));
                        }
                    } else if (line.startsWith("REMOVE MESSAGE")) {
                        String removedMessageID = line.substring(14);
                        String currentUserId = User.getInstance().findUserId(in.readLine());
                        String chatId = Message.getInstance().getChatId(removedMessageID);
                        if (Message.getInstance().checkSentUser(removedMessageID, currentUserId)) {
                            Message.getInstance().removeMessage(removedMessageID);
                            Message.getInstance().saveDataToJson();
                            boolean isGroupChat = ChatConversation.getInstance().isGroupChat(chatId);
                            ArrayList<String> messageList = Message.getInstance().getMessageList(chatId);
                            Collections.reverse(messageList);
                            if (isGroupChat) {
                                out.println("GROUP MESSAGE LIST" + messageList.size());
                            } else
                                out.println("PRIVATE MESSAGE LIST" + messageList.size());
                            out.println(chatId);
                            for (String messageId : messageList) {
                                out.println(messageId);
                                out.println(Message.getInstance().getSendUser(messageId) + ": " + Message.getInstance().getContent(messageId));
                            }
                        } else {
                            out.println("REMOVE MESSAGE FAILED");
                        }
                    } else if (line.startsWith("SEND MESSAGES")) {
                        String userId = User.getInstance().findUserId(in.readLine());
                        String chatId = in.readLine();
                        String content = in.readLine();
                        System.out.println(Message.getInstance().getNextMessageId());
                        System.out.println(userId + " " + chatId + " " + content);
                        Message.getInstance().addMessage(Message.getInstance().getNextMessageId(), userId, chatId, content);
                        Message.getInstance().saveDataToJson();
                        Message.getInstance().loadDataFromJson();
                        boolean isGroupChat = ChatConversation.getInstance().isGroupChat(chatId);
                        System.out.println(isGroupChat);
                        ArrayList<String> messageList = Message.getInstance().getMessageList(chatId);
                        System.out.println(messageList);
                        Collections.reverse(messageList);
                        if (isGroupChat) {
                            out.println("GROUP MESSAGE LIST" + messageList.size());
                        } else
                            out.println("PRIVATE MESSAGE LIST" + messageList.size());
                        out.println(chatId);
                        for (String messageId : messageList) {
                            out.println(messageId);
                            out.println(Message.getInstance().getSendUser(messageId) + ": " + Message.getInstance().getContent(messageId));
                        }
                    } else if (line.startsWith("CREATE GROUP CHAT")){
                        String chatId = createGroupChat(line);
                        out.println("ADD GROUP CHAT SUCCESS" + chatId);
                    }
                }
        } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
