import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatTabInterface {

    public static void main(String[] args) {

        JTabbedPane mainPane = new JTabbedPane();

        // Create an inbox tab
        JPanel inboxTab = new JPanel(new BorderLayout());
        JList<String> inboxList = new JList<>(new String[]{"Person 1", "Person 2", "Person 3"});
        JPanel groupChatTab = new JPanel(new BorderLayout());
        JList<String> groupChatList = new JList<>(new String[]{"Group 1", "Group 2", "Group 3"});

        inboxList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inboxTab.add(new JScrollPane(inboxList), BorderLayout.CENTER);

        // Create a group chat tab

        groupChatTab.add(new JScrollPane(groupChatList), BorderLayout.CENTER);

        // Create a conversation panel
        JPanel conversationPanel = new JPanel(new BorderLayout());
        JTextArea conversationTextArea = new JTextArea();
        conversationTextArea.setEditable(false);
        conversationPanel.add(new JScrollPane(conversationTextArea), BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        JTextField messageTextField = new JTextField();
        messagePanel.add(messageTextField, BorderLayout.CENTER);

        // Create a remove button
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversationTextArea.setText("");
            }
        });
        messagePanel.add(removeButton, BorderLayout.EAST);

        // Add the conversation and message panels to the chat tab
        groupChatTab.add(conversationPanel, BorderLayout.CENTER);
        groupChatTab.add(messagePanel, BorderLayout.SOUTH);

        // Add the inbox and group chat tabs to the tabbed pane
        mainPane.addTab("Inbox", inboxTab);
        mainPane.addTab("Group Chat", groupChatTab);

        // Create the main frame
        JFrame frame = new JFrame("Chat Tab Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}