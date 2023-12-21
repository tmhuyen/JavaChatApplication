import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatInterface extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> messageList;
    private JTextField inputField;
    private JButton sendButton;
    private JButton removeButton;

    public ChatInterface() {
        listModel = new DefaultListModel<>();
        messageList = new JList<>(listModel);
        inputField = new JTextField(20);
        sendButton = new JButton("Send");
        removeButton = new JButton("Remove");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    listModel.addElement(message);
                    inputField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = messageList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(inputField);
        southPanel.add(sendButton);
        southPanel.add(removeButton);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatInterface();
            }
        });
    }
}
