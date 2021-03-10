package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {

    // List for storing 'known' users
    static UserList userList = new UserList();

    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();

    // Call server with arguments like this: 0.0.0.0 8088 logfile.log
    public static void main(String[] args) throws IOException {

        // Add users to the list of users
        userList.addUser(new User("Peter"));
        userList.addUser(new User("René"));
        userList.addUser(new User("Simon"));
        userList.addUser(new User("Wehba"));

        String ip;
        int port;
        boolean isAlive = false;

        try {
            if (args.length == 2) {
                ip = args[0];
                port = Integer.parseInt(args[1]);
                isAlive = true;
            }
            else {
                throw new IllegalArgumentException("# Server not provided with the right arguments");
            }
        } catch (NumberFormatException ne) {
            System.out.println("# Illegal inputs provided when starting the server!");
            return;
        }

        // Create server
        if (isAlive) {

            System.out.println("# Server created with ip: " + ip + " and port: " + port);

            // Server is listening on port XXXX
            ServerSocket serverSocket = new ServerSocket(port);

            // Socket (Waiting for client to connect)
            Socket socket;

            // Running infinite loop for getting client request
            while (true) {

                // Accept the incoming request (Blocking call)
                socket = serverSocket.accept();

                System.out.println("# New client request received : " + socket);

                // Obtain input and output streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("# Creating a new handler for this client...");

                // Create a new handler object for handling this request.
                ClientHandler clientHandler = new ClientHandler(socket, dataInputStream, dataOutputStream);

                // Create a new Thread with this object.
                Thread clientThread = new Thread(clientHandler);

                System.out.println("# Adding this client to active client list");

                // Add this client to active clients list
                ar.add(clientHandler);

                // Start the thread.
                clientThread.start();

                System.out.println("# Waiting for new client to CONNECT");
            }
        }
    }
}
