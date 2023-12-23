import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.Component.LEFT_ALIGNMENT;

public class TestGUI {
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Chat Application");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Private Chat tab
            JPanel privateChatPanel = new JPanel();
            privateChatPanel.add(new JLabel("Private Chat"));
            tabbedPane.addTab("Private Chat", privateChatPanel);

            // Group Chat tab
            JPanel groupChatPanel = new JPanel();
            groupChatPanel.add(new JLabel("Group Chat"));
            tabbedPane.addTab("Group Chat", groupChatPanel);

            // List of online users
            DefaultListModel<String> listModel = new DefaultListModel<>();
            listModel.addElement("User 1");
            listModel.addElement("User 2");
            listModel.addElement("User 3");
            JList<String> userList = new JList<>(listModel);
            userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userList.setLayoutOrientation(JList.VERTICAL);
            JScrollPane listScroller = new JScrollPane(userList);
            listScroller.setPreferredSize(new Dimension(250, 100));
            listScroller.setAlignmentX(LEFT_ALIGNMENT);
            privateChatPanel.add(listScroller);
            groupChatPanel.add(listScroller);
            mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
            mainFrame.pack();
            mainFrame.setVisible(true);
        });
    }*/
        /* Demo choose item double click from JList
        // Create a new JFrame
        JFrame frame = new JFrame("List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Create a JList
        String[] data = {"Item 1", "Item 2", "Item 3", "Item 4"};
        JList<String> list = new JList<>(data);

        // Create a JLabel
        JLabel label = new JLabel();

        // Add a MouseListener to the JList
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    label.setText((String) list.getModel().getElementAt(index));
                }
            }
        });

        // Add the JList and JLabel to the JFrame
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(list), BorderLayout.CENTER);
        frame.add(label, BorderLayout.SOUTH);

        // Display the JFrame
        frame.setVisible(true);*/
        /*//Demo switch screen
        JFrame frame = new JFrame("List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        JPanel screen1 = new JPanel();
        JPanel screen2 = new JPanel();
        JButton button1 = new JButton("Switch screen");
        screen1.add(new JLabel("Screen 1"));
        screen2.add(new JLabel("Screen 2"));
        panel.add(screen1);
        frame.add(panel);
        frame.add(button1, BorderLayout.SOUTH);
        frame.setVisible(true);
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (panel.getComponent(0) == screen1) {
                    panel.removeAll();
                    panel.add(screen2);
                    panel.revalidate();
                    panel.repaint();
                } else {
                    panel.removeAll();
                    panel.add(screen1);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });*/
        JTextArea textArea;
        JPanel textPane = new JPanel();
        JTextField textField;
        JButton removeButton = new JButton("Remove");
        JFrame frame = new JFrame("Chat Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        textField = new JTextField();
        /*textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                textArea.append(text + "\n");
                textField.setText("");
            }
        });*/
        textField.setPreferredSize(new Dimension(300,30));
        textPane.add(textField);
        textPane.add(removeButton);
        frame.add(textPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
