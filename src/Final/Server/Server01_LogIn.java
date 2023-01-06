package Final.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server01_LogIn extends JFrame {
    Container c;
    Font font;
    private static String user = "Admin", pass = "2468";
    private static JLabel Heading, l1, l2;
    private static JTextField userName;
    private static JPasswordField password;
    private static JButton btn1, btn2;

    Server01_LogIn() {
        initComponent();
    }

    public void initComponent() {
        c = this.getContentPane();
        c.setLayout(new GridLayout(3, 2, 8, 8));
        c.setBackground(new Color(240, 240, 240));
        font = new Font("Arial", Font.PLAIN, 20);

        /*====================This section is for UserNmae panel====================*/
        l1 = new JLabel("User Name: ");
        l1.setFont(font);
        l1.setHorizontalAlignment(JLabel.CENTER);
        c.add(l1);

        userName = new JTextField();
        userName.setFont(font);
        c.add(userName);
        /*====================This section is for UserNmae panel====================*/

        /*====================This section is for Password panel====================*/
        l2 = new JLabel("Password: ");
        l2.setFont(font);
        l2.setHorizontalAlignment(JLabel.CENTER);
        c.add(l2);

        password = new JPasswordField();
        password.setEchoChar('*');
        password.setFont(font);
        c.add(password);
        /*====================This section is for Password panel====================*/

        /*====================This section is for buttonPanel====================*/
        btn1 = new JButton("CLEAR");
        btn1.setFont(font);
        c.add(btn1);
        btn2 = new JButton("SUBMIT");
        btn2.setFont(font);
        c.add(btn2);
        /*====================This section is for buttonPanel====================*/

        /*====================This section is for ActionListener====================*/
        Handler handler = new Handler();
        userName.addActionListener(handler);
        password.addActionListener(handler);
        btn1.addActionListener(handler);
        btn2.addActionListener(handler);
        /*====================This section is for ActionListener====================*/
    }

    /*====================This section is for ActionListener Implementation====================*/
    class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == userName || e.getSource() == password || e.getSource() == btn2) {
                if (userName.getText().equals(user) && password.getText().equals(pass)) {
                    JOptionPane.showMessageDialog(null, "Successfully Login");
                    Server02_design new_frame = new Server02_design();
                    new_frame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                    userName.setText("");
                    password.setText("");
                }
            } else {
                userName.setText("");
                password.setText("");
            }
        }
    }
    /*====================This section is for ActionListener Implementation====================*/

    /*====================This section is for Driver Code====================*/
    public static void main(String[] args) {
        Server01_LogIn logIn_frame = new Server01_LogIn();
        logIn_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logIn_frame.setSize(300, 150);
        logIn_frame.setLocationRelativeTo(null);
        logIn_frame.setVisible(true);
        logIn_frame.setResizable(false);
        logIn_frame.setTitle("Server logIn");
    }
    /*====================This section is for Driver Code====================*/
}
