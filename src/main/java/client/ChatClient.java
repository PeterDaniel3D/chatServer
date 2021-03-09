package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String args[]) throws IOException {

        InetAddress ip;
        int serverPort;
        boolean isAlive = false;

        try {
            if (args.length == 2) {
                ip = InetAddress.getByName(args[0]);
                serverPort = Integer.parseInt(args[1]);
            }
            else {
                throw new IllegalArgumentException("# Server not provided with the right arguments");
            }
        } catch (NumberFormatException ne) {
            System.out.println("# Illegal inputs provided when starting the server!");
            return;
        }

        System.out.println("# Connecting to server: " + ip.getHostName() + " " + serverPort);

        // Establish the connection
        Socket socket = null;
        try {
            System.out.println("# Connection established...");
            socket = new Socket(ip, serverPort);
            isAlive = true;
        } catch (IOException e) {
            System.out.println("# Couldn't connect to server");
        }

        // Connected to server
        if (isAlive) {
            // obtaining input and out streams
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            Scanner keyboard = new Scanner(System.in);

            // Send messages
            Thread sendMessage = new Thread(() -> {
                StringBuilder message = new StringBuilder();
                while (true) {
                    // Read the message to deliver.
                     message.append(keyboard.nextLine());

                    try {
                        // Write on the output stream
                        dataOutputStream.writeUTF(message.toString());
                    } catch (IOException e) {
                        System.out.println("# Couldn't deliver message to server");
                    }
                    message.setLength(0);
                }
            });

            // Read incoming messages
            Thread readMessage = new Thread(() -> {
                StringBuilder message = new StringBuilder();
                while (true) {
                    try {
                        // Read the message sent to this client
                         message.append(dataInputStream.readUTF());
                        System.out.println(message.toString());
                    } catch (IOException e) {
                        System.out.println("# Connection to server lost");
                        System.exit(0);
                    }
                }
            });

            // Start threads
            sendMessage.start();
            readMessage.start();
        }
    }
}
