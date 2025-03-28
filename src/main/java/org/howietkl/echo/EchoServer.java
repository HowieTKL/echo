package org.howietkl.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @see <a href="https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-6444CF1A-FCAD-4F8A-877F-4A72AA0143B7">Java 21 Multithreaded Server Example</a>
 */
public class EchoServer {
  private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);

  public static void main(String[] args) {
    int portNumber;

    if (args.length != 1) {
      System.err.println("Usage: java EchoServer <port> [8080]");
      portNumber = 8080;
    } else {
      portNumber = Integer.parseInt(args[0]);
    }

    try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        Thread.ofVirtual().start(() -> {
          try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
               InputStreamReader insr = new InputStreamReader(clientSocket.getInputStream());
               BufferedReader in = new BufferedReader(insr);) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
              System.out.println(inputLine);
              out.println(inputLine);
            }
          } catch (IOException e) {
            LOG.error(e.getMessage(), e);
          }
        });
      }
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }
  }

}
