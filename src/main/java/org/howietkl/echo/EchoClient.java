package org.howietkl.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {
        String hostName;
        int portNumber;

        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <hostname> <port>");
            hostName = "localhost";
            portNumber = 8080;
        } else {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        try (Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
                if (userInput.equals("quit") || userInput.equals("exit")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}