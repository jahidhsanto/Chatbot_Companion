package Final.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client01_info_input extends JFrame {
    JLabel lblUsername, lblIP, lblPort;
    JTextField txtUsername, txtIP, txtPort;
    JButton btnSubmit;

    Socket clientSocket = null;
    Scanner in;
    PrintStream ps;
    BufferedReader br;
    String line;
    String result;

    public Client01_info_input() {
        setTitle("Input Form");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        lblUsername = new JLabel("Username: ");
        lblIP = new JLabel("IP Address: ");
        lblPort = new JLabel("Port: ");

        txtUsername = new JTextField();
        txtIP = new JTextField();
        txtPort = new JTextField();

        btnSubmit = new JButton("Submit");

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String ip = txtIP.getText();
                String port = txtPort.getText();

                Client02_design_OPTION new_frame = new Client02_design_OPTION(username, ip, port);
                new_frame.setVisible(true);
                dispose();
            }
        });

        add(lblUsername);
        add(txtUsername);
        add(lblIP);
        add(txtIP);
        add(lblPort);
        add(txtPort);
        add(btnSubmit);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Client01_info_input();
    }
}