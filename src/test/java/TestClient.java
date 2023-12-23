import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TestClient {
    private JFrame frame;
    private JButton sendButton;
    private JButton chooseButton;
    private JLabel titleLabel;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private File selectedFile;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client window = new Client();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TestClient() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        titleLabel = new JLabel("Wic Codes File Sender");
        titleLabel.setBounds(10, 10, 200, 30);
        frame.getContentPane().add(titleLabel);

        chooseButton = new JButton("Choose a file to send");
        chooseButton.setBounds(10, 50, 200, 30);
        frame.getContentPane().add(chooseButton);

        sendButton = new JButton("Send");
        sendButton.setBounds(220, 50, 200, 30);
        frame.getContentPane().add(sendButton);

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setBounds(10, 90, 410, 160);
        frame.getContentPane().add(fileList);

        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        Socket socket = new Socket("localhost", 8989);
                        byte[] bytes = new byte[(int) selectedFile.length()];
                        FileInputStream fis = new FileInputStream(selectedFile);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        bis.read(bytes, 0, bytes.length);

                        OutputStream out = socket.getOutputStream();
                        out.write(bytes, 0, bytes.length);
                        out.flush();

                        out.close();
                        bis.close();
                        fis.close();
                        socket.close();

                        listModel.addElement(selectedFile.getName());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
