package CLIENT;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class design extends JFrame implements ActionListener {
    Container c;
    private Font titleFont, font, calculatorFont;
    private JLabel titleLabel, dspLabel;
    private JTextArea dspArea, memberArea;
    private JTextField calculatorDisplayField, calculatorInputField, chatInputField;
    private JButton connectBtn, disconnectBtn, chatBtn, calculatorBtn;
    private JPanel dspPanel, buttonPanel, memberPanel, calculatorPanel, optionPanel;

    design() {
        initComponents();   // Calling initComponents method
    }

    public void initComponents() {
        /*====================This section is for Frame====================*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("SERVER");

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(240, 240, 240));
        /*====================This section is for Frame====================*/

        /*====================This section is for Font creation====================*/
        titleFont = new Font("Acme", Font.BOLD, 50);
        font = new Font("Roboto", Font.PLAIN, 16);
        calculatorFont = new Font("Digital-7", Font.PLAIN, 36);
        /*====================This section is for Font creation====================*/

        /*====================This section is for HEADING====================*/
        titleLabel = new JLabel("CLIENT PANEL");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 255, 255));
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(0, 0, 780, 100);
        c.add(titleLabel);
        /*====================This section is for HEADING====================*/

        /*====================This section is for buttonPanel====================*/
        buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.setBounds(30, 120, 700, 60);
        c.add(buttonPanel);

        connectBtn = new JButton("CONNECT");
        connectBtn.setFont(font);
        buttonPanel.add(connectBtn);

        disconnectBtn = new JButton("DISCONNECT");
        disconnectBtn.setFont(font);
        buttonPanel.add(disconnectBtn);
        /*====================This section is for buttonPanel====================*/

        /*====================This section is for MemberPanel====================*/
        memberPanel = new JPanel(new GridLayout(1, 1));
        memberPanel.setBounds(457, 190, 270, 160);
        memberPanel.setBorder(BorderFactory.createTitledBorder(null, "Owner_Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        c.add(memberPanel);

        memberArea = new JTextArea();
        memberArea.setFont(new Font("Roboto", Font.PLAIN, 14));
        memberArea.setBorder(BorderFactory.createTitledBorder(null, "Md. Jahid Hassan", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 26)));
        memberArea.setText(" ID: 201002463\n Section: 201DB\n Batch: 201\n Dept.: CSE\n Green University of Bangladesh");
        memberArea.setEditable(true);
        memberPanel.add(memberArea);
        /*====================This section is for MemberPanel====================*/

        /*====================This section is for dspPanel====================*/
        dspPanel = new JPanel(new GridLayout(1, 1));
        dspPanel.setBounds(30, 190, 350, 240);
        dspPanel.setBorder(BorderFactory.createTitledBorder(null, "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        c.add(dspPanel);

        dspArea = new JTextArea();
        dspArea.setFont(font);
        dspArea.setEditable(false);
        dspPanel.add(dspArea);

        chatInputField = new JTextField();
        chatInputField.setBounds(30, 435, 350, 60);
        chatInputField.setBorder(BorderFactory.createTitledBorder(null, "Chat Input", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        chatInputField.setFont(font);
        c.add(chatInputField);
        /*====================This section is for dspPanel====================*/
        calculatorPanel = new JPanel((new GridLayout(2, 1, 0, 10)));
        calculatorPanel.setBounds(457, 350, 270, 150);
        calculatorPanel.setBorder(BorderFactory.createTitledBorder(null, "Calculator", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        c.add(calculatorPanel);

        // Create an output text field.=======================================
        calculatorDisplayField = new JTextField();
        calculatorDisplayField.setBackground(Color.WHITE);
        calculatorDisplayField.setFont(calculatorFont);
        calculatorDisplayField.setHorizontalAlignment(JTextField.RIGHT);
        calculatorDisplayField.setEditable(false);
        calculatorDisplayField.getCaret().setVisible(true);
        calculatorPanel.add(calculatorDisplayField);

        // Create an input text field.=======================================
        calculatorInputField = new JTextField();
        calculatorInputField.setBackground(Color.WHITE);
        calculatorInputField.setFont(calculatorFont);
        calculatorInputField.setHorizontalAlignment(JTextField.LEFT);
        calculatorInputField.setEditable(true);
        calculatorPanel.add(calculatorInputField);
        /*====================This section is for dspPanel====================*/

        /*====================This section is for optionPanel====================*/
        optionPanel = new JPanel((new GridLayout(2, 1, 0, 10)));
        optionPanel.setBounds(380, 190, 77, 310);
        c.add(optionPanel);

        chatBtn = new JButton("CHAT");
        chatBtn.setFont(font);
        optionPanel.add(chatBtn);

        calculatorBtn = new JButton("CALCULATOR");
        calculatorBtn.setFont(font);
        optionPanel.add(calculatorBtn);
        /*====================This section is for optionPanel====================*/

        /*====================This section is for ActionListener====================*/
        connectBtn.addActionListener(this);
        disconnectBtn.addActionListener(this);
        chatBtn.addActionListener(this);
        calculatorBtn.addActionListener(this);
        chatInputField.addActionListener(this);
        calculatorInputField.addActionListener(this);
        /*====================This section is for ActionListener====================*/
    }

    Socket clientSocket = null;
    Scanner in;
    PrintStream ps;
    BufferedReader br;
    String line;
    String result;

    @Override
    public void actionPerformed(ActionEvent e) {
        // if user press Connection button
        if (e.getSource().equals(connectBtn)) {
            try {
                clientSocket = new Socket("localhost", 4789);
                ps = new PrintStream(clientSocket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        // if user press Disonnection button
        if (e.getSource().equals(disconnectBtn)) {
            line = "ENDS";
            ps.println(line);
            try {
                br.close();
                ps.close();
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        // if user press chat button
        if (e.getSource().equals(chatBtn) || e.getSource().equals(chatInputField)) {
            try {
                line = "chatBtn " + chatInputField.getText();
                ps.println(line);           // Send to server
                result = br.readLine();     // Receive from server
                dspArea.setText(dspArea.getText() + result + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //if user press Calculator button
        if (e.getSource().equals(calculatorBtn) || e.getSource().equals(calculatorInputField)) {
            try {
                line = "calculatorBtn " + calculatorInputField.getText();
                ps.println(line);           // Send to server
                result = br.readLine();     // Receive from server
                calculatorDisplayField.setText(result);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        design frame = new design();
        frame.setVisible(true);
    }
}
