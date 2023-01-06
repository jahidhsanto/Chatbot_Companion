package Final.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client04_AI_CHAT extends JFrame implements ActionListener {
    Container c;

    // Create a JPanel to hold the conversation
    private JPanel conversationPanel;

    // Create two JTextArea to display conversations
    private JTextArea userTextArea;
    private JTextArea botTextArea;

    // Create a JScrollPane to scroll through the conversations
    private JScrollPane conversationScrollPane;

    // Create a JPanel to hold the user input
    private JPanel inputPanel;

    // Create a JTextField to take user input
    private JTextField userInputField;

    // Create a JButton to send the user input
    private JButton sendButton, backButton;

    // Create a String to store user input
    private String userInput;

    Socket clientSocket = null;
    PrintStream ps;
    BufferedReader br;
    String line;
    String result;
    String username, ip, port;

    public Client04_AI_CHAT(String username, String ip, String port) {
        this.username = username;
        this.ip = ip;
        this.port = port;

        initComponents();   // Calling initComponents method
    }

    public void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setTitle("AI Chatbot");

        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(new Color(240, 240, 240));


        // Initialize the conversation panel
        conversationPanel = new JPanel();
        conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.PAGE_AXIS));

        // Initialize the user text area
        userTextArea = new JTextArea();
        userTextArea.setEditable(false);
        userTextArea.setLineWrap(true);
        userTextArea.setWrapStyleWord(true);

        // Initialize the bot text area
        botTextArea = new JTextArea();
        botTextArea.setEditable(false);
        botTextArea.setLineWrap(true);
        botTextArea.setWrapStyleWord(true);

        // Add the text areas to the conversation panel
        conversationPanel.add(userTextArea);
        conversationPanel.add(botTextArea);

        // Initialize the conversation scroll pane
        conversationScrollPane = new JScrollPane(conversationPanel);

        // Add the scroll pane to the frame
        this.add(conversationScrollPane, BorderLayout.CENTER);

        // Initialize the input panel
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Initialize the user input field
        userInputField = new JTextField(24);

        // Add the user input field to the input panel
        inputPanel.add(userInputField);

        // Initialize the send button
        sendButton = new JButton("Send");
        backButton = new JButton("Back");

        // Add the send button to the input panel
        inputPanel.add(sendButton);
        inputPanel.add(backButton);

        // Add the input panel to the frame
        this.add(inputPanel, BorderLayout.SOUTH);


        try {
            clientSocket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
            ps = new PrintStream(clientSocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        sendButton.addActionListener(this);
        backButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton || e.getSource() == userInputField) {
            try {
                userTextArea.append(userInputField.getText() + "\n");
                line = "chatBtn " + userInputField.getText();
                ps.println(line);           // Send to server
                result = br.readLine();     // Receive from server
                botTextArea.append(result + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == backButton) {
            Client02_design_OPTION new_frame = new Client02_design_OPTION(username, ip, port);
            new_frame.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args, String username, String ip, String port) {
        Client04_AI_CHAT frame = new Client04_AI_CHAT(username, ip, port);
        frame.setVisible(true);
    }
}