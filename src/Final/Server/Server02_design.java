package Final.Server;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server02_design extends JFrame implements ActionListener {
    Container c;
    private Font titleFont, font;

    private JLabel titleLabel, dspLabel;
    private JTextArea dspArea, memberArea;
    private JScrollPane scroll;
    private JButton onBtn, offBtn;
    private JPanel dspPanel, buttonPanel, memberPanel;

    Server02_design() {
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

        scroll = new JScrollPane(dspArea);
        scroll.setBounds(0, 0, 389, 270);
        dspPanel.add(scroll);
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

    /*====================This section is for ActionListener Implementation====================*/
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
    /*====================This section is for ActionListener Implementation====================*/

    /*====================This section is for Socket Programming====================*/ ServerSocket serverSocket;
    Socket clientSocket;
    int port = 5000;
    int count = 0;
    String result;


    private static ArrayList<ClientHandler> clients;
    private static ExecutorService pool;
    private Socket socket;


    public void serverConnection() throws IOException {

        try {
            // TODO add your handling code here:
            serverSocket = new ServerSocket(4789);
            clients = new ArrayList<ClientHandler>();
            pool = Executors.newFixedThreadPool(2);
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            InetAddress localhost = InetAddress.getLocalHost();
                            dspArea.append("Server is opened at IP.: " + localhost.getHostAddress() + "\n");
                            dspArea.append("Server is opened at port no.: " + serverSocket.getLocalPort() + "\n");
                            dspArea.append("Waiting for Client..." + "\n");
                            socket = serverSocket.accept();
                            dspArea.append("Client found." + "\n");
                            ClientHandler clientThread = new ClientHandler(socket);
                            clients.add(clientThread);
                            pool.execute(clientThread);
                        } catch (IOException ex) {
                            Logger.getLogger(Server02_design.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });
            myThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Server02_design.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class ClientHandler extends Thread {
        Socket clientSocket;

        public ClientHandler(Socket cSocket) {
            this.clientSocket = cSocket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream ps = new PrintStream(clientSocket.getOutputStream());

                while (true) {
                    String line = br.readLine();
                    if (line.equals("ENDS") || line.equals("BYE")) {
                        ps.println("Connection Terminated");
                        break;
                    }


                    String[] input = line.split(" ", 2);
                    String firstWord = input[0];
                    String theRest = input[1].toLowerCase();

                    // if user press chat button
                    if (firstWord.equals("chatBtn")) {
                        ps.println(Module02_AI.rtrn(theRest));
                    }

                    //if user press Calculator button
                    else if (firstWord.equals("calculatorBtn")) {
                        ps.println(Module01_calculator.calculate(theRest));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*====================This section is for Socket Programming====================*/

    public static void main(String[] args) {
        Server02_design frame = new Server02_design();
        frame.setVisible(true);
    }
}
