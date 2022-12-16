package SERVER;

import java.net.*;
import java.io.*;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 5000;
        int count = 0;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 6789");
            System.exit(-1);
        }
        while (count < 5) {
            clientSocket = serverSocket.accept();
            MathHandler mathHandler = new MathHandler(clientSocket);
            mathHandler.start();
            count++;
        }
        serverSocket.close();
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
                String[] input = line.split(" ");
                int a = Integer.parseInt(input[0]);
                int b = Integer.parseInt(input[1]);
                char operator = input[2].charAt(0);
                int result = 0;
                switch (operator) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '/':
                        result = a / b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                }
                ps.println("The result is " + result);
            }
            br.close();
            ps.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

