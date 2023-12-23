package Client;

import Server.ChatConversation;
import Server.Message;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.awt.Component.LEFT_ALIGNMENT;

public class ChatClient {
    BufferedReader in;
    PrintWriter out;
    DefaultListModel<String> onlineUserModel =  new DefaultListModel<String>();
    DefaultListModel<String> groupChatModel = new DefaultListModel<>();
    DefaultListModel<String> ChatModel = new DefaultListModel<>();
    String currentUser;
    String tempUser;
    String currentChatId;
    HashMap<String, String> currentChat = new HashMap<>();
    //Login frame
    JFrame frame = new JFrame("HCMUS _ 21127202 _ Chat application");
    JPanel loginPanel = new JPanel();
    JTabbedPane LoginSignupPane = new JTabbedPane();
    JLabel loginUsername = new JLabel("Username");
    JLabel signupUsername = new JLabel("Username");
    JLabel loginPassword = new JLabel("Password");
    JLabel signupPassword = new JLabel("Password");
    JLabel confirmPassword = new JLabel("Confirm Password");
    JLabel loginStatus = new JLabel("Login Status");
    JLabel signupStatus = new JLabel("Sign Up Status");
    JTextField loginUsernameField = new JTextField(20);
    JTextField signupUsernameField = new JTextField(20);
    JPasswordField loginPasswordField = new JPasswordField(20);
    JPasswordField signupPasswordField = new JPasswordField(20);
    JPasswordField confirmPasswordField = new JPasswordField(20);
    JButton loginButton = new JButton("Login");
    JButton signupButton = new JButton("Sign Up");
    //Main frame
    JFrame mainFrame = new JFrame("Chat Application");
    JTabbedPane ChatPane = new JTabbedPane();
    JPanel privateChatPanel = new JPanel();
    JPanel groupChatPanel = new JPanel();
    JList<String> privateChatList = new JList<String>(onlineUserModel);
    JList<String> groupChatList = new JList<String> (groupChatModel);
    JList<String> chatMessageList = new JList<String>(ChatModel);
    JScrollPane privateChatScroller = new JScrollPane();
    JScrollPane groupChatScroller = new JScrollPane();
    JButton createGroupButton = new JButton("New Group");
    JButton refreshOnlineUserButton = new JButton("Refresh");
    JButton refreshGroupChatButton = new JButton("Refresh");
    JButton refreshPrivateMessage = new JButton("Refresh");
    JButton refreshGroupMessage = new JButton("Refresh");
    //Chat conversation
    JPanel textbuttonPanel = new JPanel();
    JButton removeButton = new JButton("Remove");
    JButton privateBackButton = new JButton("Back");
    JButton groupBackButton = new JButton("Back");
    JTextField textField = new JTextField();
    //Create new group
    JFrame createGroupFrame;
    JTextField groupNameField;
    JList<String> onlineUsersList;
    JPanel createGroupPanel1;
    JPanel createGroupPanel2;
    JButton addToGroupButton;
    JLabel groupNameLabel = new JLabel("Enter name of the group: ");
    JLabel onlineUsersLabel = new JLabel("Select user(s) to add to the group: ");


    public ChatClient() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        loginPanel.add(new JLabel("<html><font color='blue'>Login to the account </font></html>"), constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        loginPanel.add(loginUsername, constraints);

        constraints.gridx = 1;
        loginPanel.add(loginUsernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        loginPanel.add(loginPassword, constraints);

        constraints.gridx = 1;
        loginPanel.add(loginPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginStatus, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, constraints);

        LoginSignupPane.addTab("Login", loginPanel);

        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        signupPanel.add(new JLabel("<html><font color='blue'>Sign up for an account</font></html>"), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        signupPanel.add(signupUsername, constraints);
        constraints.gridx = 1;
        signupPanel.add(signupUsernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        signupPanel.add(signupPassword, constraints);

        constraints.gridx = 1;
        signupPanel.add(signupPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        signupPanel.add(confirmPassword, constraints);

        constraints.gridx = 1;
        signupPanel.add(confirmPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        signupPanel.add(signupStatus, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        signupPanel.add(signupButton, constraints);

        LoginSignupPane.addTab("Sign Up", signupPanel);
        frame.getContentPane().add(LoginSignupPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("LOGIN");
                out.println(loginUsernameField.getText());
                tempUser = loginUsernameField.getText();
                out.println(loginPasswordField.getText());
            }
        });
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("SIGNUP");
                out.println(signupUsernameField.getText());
                out.println(signupPasswordField.getText());
                out.println(confirmPasswordField.getText());
            }
        });
        //End of LoginSignup panel
        ////////////////////////////
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(750,520);
        privateChatScroller.setPreferredSize(new Dimension(600, 400));
        privateChatScroller.setAlignmentX(LEFT_ALIGNMENT);
        groupChatScroller.setPreferredSize(new Dimension(600, 400));

        textField.setPreferredSize(new Dimension(400,30));

        onlineUserModel.addElement("test");
        onlineUserModel.addElement("test2");
        onlineUserModel.addElement("test3");
        privateChatList.setModel(onlineUserModel);
        privateChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        privateChatList.setLayoutOrientation(JList.VERTICAL);
        privateChatScroller.setViewportView(privateChatList);
        privateChatPanel.add(privateChatScroller);
        privateChatPanel.add(refreshOnlineUserButton);

        groupChatModel.addElement("test");
        groupChatModel.addElement("test2");
        groupChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupChatList.setLayoutOrientation(JList.VERTICAL);
        groupChatScroller.setViewportView(groupChatList);
        groupChatPanel.add(groupChatScroller);
        groupChatPanel.add(refreshGroupChatButton);
        groupChatPanel.add(createGroupButton);

        ChatPane.addTab("Private Chat", privateChatPanel);
        ChatPane.addTab("Group Chat", groupChatPanel);
        mainFrame.getContentPane().add(ChatPane, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        //End of List panel
        /////////////

        refreshOnlineUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET ONLINE USER"+currentUser);
            }
        });
        privateBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET ONLINE USER"+currentUser);
            }
        });
        refreshGroupChatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET GROUP CHAT LIST"+currentUser);
            }
        });

        groupBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET GROUP CHAT LIST"+currentUser);
            }
        });
        refreshPrivateMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET PRIVATE MESSAGE LIST"+currentUser);
            }
        });
        refreshGroupMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("GET GROUP MESSAGE LIST"+currentUser);
            }
        });
        privateChatList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    String selectedUser = onlineUserModel.getElementAt(index);
                    if (selectedUser.equals(currentUser)) {
                        JOptionPane.showMessageDialog(null, "You cannot chat with yourself");
                    } else {
                        out.println("GET PRIVATE CHAT"+currentUser+ "|" + selectedUser);
                    }
                }
            }
        });

        groupChatList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    String selectedUser = groupChatModel.getElementAt(index);
                    out.println("GET GROUP CHAT"+ selectedUser);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer selectedMessage = chatMessageList.getSelectedIndex();
                String removeMessageId = null;
                if (selectedMessage == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a message to remove");
                } else {
                    String selectedMessageId = null;
                    int count = currentChat.size() - 1;
                    for (String messageId : currentChat.keySet()) {
                        if (count == selectedMessage) {
                            selectedMessageId = messageId;
                            break;
                        }
                        count--;
                    }
                    System.out.println(selectedMessageId);
                    out.println("REMOVE MESSAGE"+selectedMessageId);
                    out.println(currentUser);
                }
            }
        });
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                System.out.println(message);
                if (message.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a message");
                } else {
                    out.println("SEND MESSAGES");
                    out.println(currentUser);
                    out.println(currentChatId);
                    out.println(message);
                    textField.setText("");
                }
            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (onlineUserModel.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There is no user online");
                } else {
                    createGroupPanel2.removeAll();
                    onlineUsersList = new JList<String>(onlineUserModel);
                    onlineUsersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    createGroupPanel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
                    createGroupPanel2.setLayout(new BoxLayout(createGroupPanel2, BoxLayout.Y_AXIS));
                    createGroupPanel2.add(onlineUsersLabel);
                    createGroupPanel2.add(new JScrollPane(onlineUsersList));
                    createGroupPanel2.repaint();
                    createGroupFrame.setVisible(true);
                }
            }
        });
        //End of Chat panel
        ////////////////////////////
        createGroupFrame = new JFrame("Group Chat");
        createGroupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGroupFrame.setSize(300, 600);

        groupNameLabel = new JLabel("Enter name of the group: ");
        groupNameField = new JTextField();
        groupNameField.setPreferredSize(new Dimension(250, 30));
        groupNameField.setColumns(10);

        onlineUsersLabel = new JLabel("Select user(s) to add to the group: ");

        addToGroupButton = new JButton("Add to Group");
        createGroupPanel1 = new JPanel();
        createGroupPanel2 = new JPanel();
        createGroupPanel1.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        addToGroupButton.setMargin(new Insets(0, 10, 10, 10));
        createGroupPanel1.setLayout(new BoxLayout(createGroupPanel1, BoxLayout.Y_AXIS));
        createGroupPanel1.add(groupNameLabel);
        createGroupPanel1.add(groupNameField);

        createGroupFrame.getContentPane().add(createGroupPanel1, BorderLayout.NORTH);
        createGroupFrame.getContentPane().add(createGroupPanel2, BorderLayout.CENTER);
        createGroupFrame.getContentPane().add(addToGroupButton, BorderLayout.SOUTH);
        createGroupFrame.setLocationRelativeTo(null);
        addToGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupNameField.getText();
                ArrayList<String> selectedUsers = new ArrayList<>(onlineUsersList.getSelectedValuesList());
                if (!selectedUsers.contains(currentUser)) {
                    JOptionPane.showMessageDialog(null, "You must be in the group!");
                }
                if (selectedUsers.size() < 2) {
                    JOptionPane.showMessageDialog(null, "You must select at least 2 users!");
                }
                String users = "";
                for (String user : selectedUsers) {
                    users += "|"+user;
                }
                out.println("CREATE GROUP CHAT"+"|"+groupName+users);
                System.out.println("CREATE GROUP CHAT"+"|"+groupName+users);
                createGroupFrame.setVisible(false);
                // Logic to add selected users to the group
                //System.out.println("Adding " + selectedUsers + " to " + groupName);
            }
        });
    }

    private void run() throws IOException {
        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String line = in.readLine();
            if (line.startsWith("LOGIN")) {
                if (line.substring(6).equals("SUCCESS")) {
                    currentUser = tempUser;
                    loginUsernameField.setText("");
                    loginPasswordField.setText("");
                    loginStatus.setText("Login Successful");
                    loginStatus.setForeground(Color.GREEN);
                    frame.setVisible(false);
                    mainFrame.setTitle("Chat Application - " + currentUser);
                    mainFrame.setVisible(true);
                } else {
                    loginStatus.setText("Login Failed");
                    loginStatus.setForeground(Color.RED);
                }
            } else if (line.startsWith("SIGNUP")) {
                System.out.println(line.substring(7).equals("SUCCESS"));
                if (line.substring(7).equals("SUCCESS")) {
                    signupPasswordField.setText("");
                    signupUsernameField.setText("");
                    confirmPasswordField.setText("");
                    signupStatus.setText("Sign Up Successful! Please login");
                    signupStatus.setForeground(Color.GREEN);
                } else if (line.substring(7).equals("USERNAME EXIST")) {
                    signupStatus.setText("Username already exist! Please try again");
                    signupStatus.setForeground(Color.RED);
                } else {
                    signupStatus.setText("Sign Up Failed! Please try again");
                    signupStatus.setForeground(Color.RED);
                }
            } else if (line.startsWith("ONLINE USER")) {
                int numberOfOnlineUser = Integer.parseInt(line.substring(11));
                onlineUserModel.clear();
                for (int i = 0; i < numberOfOnlineUser; i++) {
                    onlineUserModel.addElement(in.readLine());
                }
                privateChatList.setModel(onlineUserModel);
                chatMessageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                privateChatScroller.setViewportView(privateChatList);
                privateChatPanel.removeAll();
                privateChatPanel.add(privateChatScroller);
                privateChatPanel.add(refreshOnlineUserButton);
                privateChatPanel.revalidate();
                privateChatPanel.repaint();
                System.out.println(onlineUserModel);
            } else if (line.startsWith("GROUP CHAT LIST")) {
                int numberOfGroupChat = Integer.parseInt(line.substring(15));
                groupChatModel.clear();
                for (int i = 0; i < numberOfGroupChat; i++) {
                    groupChatModel.addElement(in.readLine());
                }
                groupChatList.setModel(groupChatModel);
                chatMessageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                groupChatScroller.setViewportView(groupChatList);
                groupChatPanel.removeAll();
                groupChatPanel.add(groupChatScroller);
                groupChatPanel.add(refreshGroupChatButton);
                groupChatPanel.add(createGroupButton);
                groupChatPanel.revalidate();
                groupChatPanel.repaint();
                System.out.println(groupChatModel);
            } else if (line.startsWith("PRIVATE MESSAGE LIST")){
                int NumberOfMessage = Integer.parseInt(line.substring(20));
                currentChatId = in.readLine();
                System.out.println(currentChatId);
                System.out.println(NumberOfMessage);
                currentChat.clear();
                ChatModel.clear();
                for (int i = 0; i < NumberOfMessage; i++) {
                    String messageId = in.readLine();
                    String content = in.readLine();
                    currentChat.put(messageId, content);
                    System.out.println(messageId + ": " + content);
                    ChatModel.addElement(content);
                }
                textbuttonPanel.removeAll();
                textbuttonPanel.add(privateBackButton);
                textbuttonPanel.add(textField);
                textbuttonPanel.add(removeButton);
                textbuttonPanel.revalidate();
                textbuttonPanel.repaint();
                chatMessageList.setModel(ChatModel);
                privateChatScroller.setViewportView(chatMessageList);
                privateChatPanel.removeAll();
                privateChatPanel.add(refreshPrivateMessage,BorderLayout.NORTH);
                privateChatPanel.add(privateChatScroller,BorderLayout.CENTER);
                privateChatPanel.add(textbuttonPanel,BorderLayout.SOUTH);
                privateChatPanel.revalidate();
                privateChatPanel.repaint();
            } else if (line.startsWith("GROUP MESSAGE LIST")){
                int NumberOfMessage = Integer.parseInt(line.substring(18));
                currentChatId = in.readLine();
                currentChat.clear();
                ChatModel.clear();
                for (int i = 0; i < NumberOfMessage; i++) {
                    String messageId = in.readLine();
                    String content = in.readLine();
                    currentChat.put(messageId, content);
                    System.out.println(messageId + ": " + content);
                    ChatModel.addElement(content);
                }
                textbuttonPanel.removeAll();
                textbuttonPanel.add(groupBackButton);
                textbuttonPanel.add(textField);
                textbuttonPanel.add(removeButton);
                textbuttonPanel.revalidate();
                textbuttonPanel.repaint();
                chatMessageList.setModel(ChatModel);
                groupChatScroller.setViewportView(chatMessageList);
                groupChatPanel.removeAll();
                groupChatPanel.add(refreshGroupMessage,BorderLayout.NORTH);
                groupChatPanel.add(groupChatScroller,BorderLayout.CENTER);
                groupChatPanel.add(textbuttonPanel,BorderLayout.SOUTH);
                groupChatPanel.revalidate();
                groupChatPanel.repaint();
            } else if (line.startsWith("REMOVE MESSAGE FAILED")){
                JOptionPane.showMessageDialog(null, "This is not your message!");
            } else if (line.startsWith("ADD GROUP CHAT SUCCESS")){
                JOptionPane.showMessageDialog(null, "Group chat created successfully!");
                out.println("GET GROUP CHAT LIST"+currentUser);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        ChatClient chatClient = new ChatClient();
        chatClient.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatClient.frame.setVisible(true);
        chatClient.run();
    }
}
