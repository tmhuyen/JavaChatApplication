package Client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class Client {
    BufferedReader in;
    PrintWriter out;
    ///
    JFrame LoginSignupFrame = new JFrame("Login and Sign Up");
    JTabbedPane tabbedPane = new JTabbedPane();
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

    //Login panel
    JPanel loginPanel = new JPanel();
    public Client() {
        LoginSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        tabbedPane.addTab("Login", loginPanel);

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

        tabbedPane.addTab("Sign Up", signupPanel);
        LoginSignupFrame.getContentPane().add(tabbedPane);
        LoginSignupFrame.setLocationRelativeTo(null);
        LoginSignupFrame.setVisible(true);
        LoginSignupFrame.pack();
        //End of Login panel

    }

    private void run() throws IOException {
        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            break;
            /*String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }*/
        }
    }
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.LoginSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.LoginSignupFrame.setVisible(true);
        client.run();
    }
}
