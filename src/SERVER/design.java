package SERVER;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class design extends JFrame implements ActionListener {
    Container c;
    private Font titleFont, font;

    private JLabel titleLabel, dspLabel;
    private JTextArea dspArea, memberArea;
    private JButton onBtn, offBtn;
    private JPanel dspPanel, buttonPanel, memberPanel;

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
        /*====================This section is for Font creation====================*/

        /*====================This section is for HEADING====================*/
        titleLabel = new JLabel("SERVER PANEL");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 255, 255));
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(0, 0, 780, 100);
        c.add(titleLabel);
        /*====================This section is for HEADING====================*/

        /*====================This section is for MemberPanel====================*/
        memberPanel = new JPanel(new GridLayout(1, 1));
        memberPanel.setBounds(457, 120, 270, 150);
        memberPanel.setBorder(BorderFactory.createTitledBorder(null, "Owner_Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        c.add(memberPanel);

        memberArea = new JTextArea();
        memberArea.setFont(new Font("Roboto", Font.PLAIN, 14));
        memberArea.setBorder(BorderFactory.createTitledBorder(null, "Md. Jahid Hassan", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        memberArea.setText(" ID: 201002463\n Section: 201DB\n Batch: 201\n Dept.: CSE\n Green University of Bangladesh");
        memberArea.setEditable(true);
        memberPanel.add(memberArea);
        /*====================This section is for MemberPanel====================*/

        /*====================This section is for dspPanel====================*/
        dspPanel = new JPanel(new GridLayout(1, 1));
        dspPanel.setBounds(30, 120, 389, 270);
        dspPanel.setBorder(BorderFactory.createTitledBorder(null, "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Adobe Arabic", 1, 18)));
        c.add(dspPanel);

        dspArea = new JTextArea();
        dspArea.setFont(font);
        dspPanel.add(dspArea);
        /*====================This section is for dspPanel====================*/

        /*====================This section is for buttonPanel====================*/
        buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBounds(457, 300, 270, 100);
        c.add(buttonPanel);

        onBtn = new JButton("ON");
        onBtn.setFont(font);
        buttonPanel.add(onBtn);

        offBtn = new JButton("OFF");
        offBtn.setFont(font);
        buttonPanel.add(offBtn);
        /*====================This section is for buttonPanel====================*/

        /*====================This section is for ActionListener====================*/
        onBtn.addActionListener(this);
        offBtn.addActionListener(this);
        /*====================This section is for ActionListener====================*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if user press ON button
        if (e.getSource().equals(onBtn)) {
            try {
                serverConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        // if user press OFF button
        if (e.getSource().equals(offBtn)) {
            try {
                serverSocket.close();
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    ServerSocket serverSocket;
    Socket clientSocket;
    int port = 5000;
    int count = 0;
    String result;


    private static ArrayList<MathHandler> clients;
    private static ExecutorService pool;
    private Socket socket;


    //    public void serverConnection() throws IOException {
//        try {
//            serverSocket = new ServerSocket(port);
//        } catch (IOException e) {
//            System.out.println("Could not listen on port: 5000");
//            System.exit(-1);
//        }
//        dspArea.setText(dspArea.getText() + "Server is opened at port no.: " + serverSocket.getLocalPort() + "\n");
//
//        while (count < 2) {
//            clientSocket = serverSocket.accept();     // Waiting connection request from client side
//            dspArea.setText(dspArea.getText() + "Server is connected to port no.: " + clientSocket.getPort() + "\n");
//            MathHandler mathHandler = new MathHandler(clientSocket);
//            mathHandler.start();
//            count++;
//        }
//    }
    public void serverConnection() throws IOException {

        try {
            // TODO add your handling code here:
            serverSocket = new ServerSocket(4789);
            clients = new ArrayList<MathHandler>();
            pool = Executors.newFixedThreadPool(2);
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            dspArea.append("Server is opened at port no.: " + serverSocket.getLocalPort() + "\n");
                            dspArea.append("Waiting for Client..." + "\n");
                            socket = serverSocket.accept();
                            dspArea.append("Client found." + "\n");
                            MathHandler clientThread = new MathHandler(socket);
                            clients.add(clientThread);
                            pool.execute(clientThread);
                        } catch (IOException ex) {
                            Logger.getLogger(design.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
            myThread.start();
        } catch (IOException ex) {
            Logger.getLogger(design.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class MathHandler extends Thread {
        Socket clientSocket;

        public MathHandler(Socket cSocket) {
            this.clientSocket = cSocket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream ps = new PrintStream(clientSocket.getOutputStream());

                while (true) {
                    String line = br.readLine();
                    if (line.equals("ENDS")) {
                        ps.println("Connection Terminated");
                        break;
                    }

                    String[] input = line.split(" ", 2);
                    String firstWord = input[0];
                    String theRest = input[1].toLowerCase();

                    // if user press chat button
                    if (firstWord.equals("chatBtn")) {
                        if (theRest.contains("hi"))
                            result = "Hi there!";
                        else if (theRest.contains("hello"))
                            result = "Hello!";
                        else if (theRest.contains("how are you") || theRest.contains("hru") || theRest.contains("how r you") || theRest.contains("how r u") || theRest.contains("h r y") || theRest.contains("how r y") || theRest.contains("how are u"))
                            result = "I'm doing well, thanks for asking!";
                        else if (theRest.contains("what is your name") || theRest.contains("what's your name"))
                            result = "I'm SANTO";
                        else if (theRest.contains("what do you do"))
                            result = "NOTHING TO DO";
                        else if (theRest.contains("What is the weather like today?"))
                            result = "";
                        else if (theRest.contains("How can I book a flight?"))
                            result = "";
                        else if (theRest.contains("What is the best restaurant in town?"))
                            result = "";
                        else if (theRest.contains("What is the fastest way to get to the airport?"))
                            result = "";
                        else if (theRest.contains("What is the best way to learn a new language?"))
                            result = "";
                        else {
                            int rand = (int) (Math.random() * 4 + 1);
                            if (rand == 1)
                                result = "I have to go. TATA BYE... BYE...";
                            else if (rand == 2)
                                result = "I don't understand you";
                            else if (rand == 3)
                                result = "I don't understand you, Bro";
                            else if (rand == 4)
                                result = "Please come again";
                        }
                        ps.println(result);
                    }

                    //if user press Calculator button
                    else if (firstWord.equals("calculatorBtn")) {
                        ps.println(calculate(theRest));
                    }
                }

            } catch (
                    IOException e) {
                e.printStackTrace();
            }

        }

    }


    // This Section for CALCULATOR=======================================================================================
    public static int calculate(String expression) {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {

            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ') continue;

            // Current token is a number,
            // push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                //  token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(') ops.push(tokens[i]);

                // Closing brace encountered,
                // solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(') values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty()) values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains
        // result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher
// or same precedence as 'op1',
// otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        else return true;
    }

    // A utility method to apply an
// operator 'op' on operands 'a'
// and 'b'. Return the result.
    public static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
// This Section for CALCULATOR=======================================================================================


    public static void main(String[] args) {
        design frame = new design();
        frame.setVisible(true);
    }
}
