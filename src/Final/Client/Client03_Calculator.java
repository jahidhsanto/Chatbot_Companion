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
import java.util.Scanner;

public class Client03_Calculator extends JFrame implements ActionListener {
    private JTextField txtField1, txtField2;
    private JButton btnSubmit, btnBack;
    Container c;
    Socket clientSocket = null;
    Scanner in;
    PrintStream ps;
    BufferedReader br;
    String line;
    String result;
    String username, ip, port;


    public Client03_Calculator(String username, String ip, String port) {
        this.username = username;
        this.ip = ip;
        this.port = port;

        initComponents();   // Calling initComponents method
    }

    public void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("My JFrame Design");
//        this.setLayout(new FlowLayout());

        c = this.getContentPane();
        c.setLayout(new FlowLayout());

        txtField1 = new JTextField(20);
        txtField2 = new JTextField(20);
        btnSubmit = new JButton("Submit");
        btnBack = new JButton("Back");

        add(txtField1);
        add(txtField2);
        add(btnSubmit);
        add(btnBack);

        try {
            clientSocket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
            ps = new PrintStream(clientSocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        btnSubmit.addActionListener(this);
        btnBack.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            try {

                line = "calculatorBtn " + txtField1.getText();
                ps.println(line);           // Send to server
                result = br.readLine();     // Receive from server
                txtField2.setText(result);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == btnBack) {
            Client02_design_OPTION new_frame = new Client02_design_OPTION(username, ip, port);
            new_frame.setVisible(true);
            dispose();
        }
    }


    public static void main(String[] args, String username, String ip, String port) {
        new Client03_Calculator(username, ip, port);
    }
}