package Final.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client02_design_OPTION extends JFrame implements ActionListener {
    Container c;
    JButton btn1, btn2, btn3;
    String username, ip, port;

    public Client02_design_OPTION(String username, String ip, String port) {
        this.username = username;
        this.ip = ip;
        this.port = port;

        initComponents();   // Calling initComponents method
    }

    public void initComponents() {
        /*====================This section is for Frame====================*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250, 350);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Client Option");

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(240, 240, 240));
        /*====================This section is for Frame====================*/

        btn1 = new JButton("Calculator");
        btn2 = new JButton("Chat with AI");
        btn3 = new JButton("Chat with Friend");

        btn1.setBounds(50, 50, 150, 50);
        btn2.setBounds(50, 150, 150, 50);
        btn3.setBounds(50, 250, 150, 50);

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);

        add(btn1);
        add(btn2);
        add(btn3);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            Client03_Calculator new_frame = new Client03_Calculator(this.username, this.ip, this.port);
            new_frame.setVisible(true);
            dispose();
            // do something
        } else if (e.getSource() == btn2) {
            Client04_AI_CHAT new_frame = new Client04_AI_CHAT(this.username, this.ip, this.port);
            new_frame.setVisible(true);
            dispose();

            // do something
        } else {
            // do something
        }
    }

    public static void main(String args[], String username, String ip, String port) {
        new Client02_design_OPTION(username, ip, port);
    }
}