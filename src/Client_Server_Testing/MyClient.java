package Client_Server_Testing;

import CLIENT.design;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = null;
        int port = 5000;
        try {
            clientSocket = new Socket("localhost", port);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to localhost");
            System.exit(-1);
        }
        Scanner in = new Scanner(System.in);
        PrintStream ps = new PrintStream(clientSocket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while (true) {
            System.out.println("Enter two numbers and a mathematical operator separated by spaces: ");
            String line = in.nextLine();
            if (line.equals("ENDS"))
                break;
            ps.println(line);
            String result = br.readLine();
            System.out.println(result);
        }
        br.close();
        ps.close();
        clientSocket.close();
    }
}