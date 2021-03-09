package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    // Template
    private String name = "Guest";
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    Socket socket;

    // Constructor
    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
    }

    @Override
    public void run() {

        String messageReceived;
        while (true) {
            try {
                messageReceived = dataInputStream.readUTF();
                System.out.println(messageReceived);

                // Client requested a normal close
                if (messageReceived.equals("CLOSE#")) {
                    break;
                }

                // Break the string into message and client
                StringTokenizer stringTokenizer = new StringTokenizer(messageReceived, "#");
                String command = stringTokenizer.nextToken();
                String client = stringTokenizer.nextToken();

                // This is where the client gets online
                if (command.equals("CONNECT") && ChatServer.userList.findUser(client)) {
                    if (!ChatServer.userList.getStatus(client)) {
                        ChatServer.userList.changeStatus(client, true);
                        this.name = client;
                        for (ClientHandler clientHandler : ChatServer.ar) {
                            if (ChatServer.userList.getStatus(clientHandler.name)) {
                                clientHandler.dataOutputStream.writeUTF("ONLINE#" + ChatServer.userList.showUsers());
                            }

                        }

                    }
                }

            } catch (IOException e) {
               break;
            }
        }
        ChatServer.userList.changeStatus(this.name, false);
        for (ClientHandler clientHandler : ChatServer.ar) {
            try {
                clientHandler.dataOutputStream.writeUTF("ONLINE#" + ChatServer.userList.showUsers());
            } catch (IOException e) {
//                e.printStackTrace();
            }

        }

        System.out.println("# Client disconnected (" + this.name + ") ");
        try {
            // closing resources
            this.socket.close();
            this.dataInputStream.close();
            this.dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
