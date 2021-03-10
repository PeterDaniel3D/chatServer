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

                // Break the string into command, message and client
                String command, client, message = "";
                StringTokenizer stringTokenizer = new StringTokenizer(messageReceived, "#");
                command = stringTokenizer.nextToken();
                if ((command.equals("CONNECT") || command.equals("SEND")) && (stringTokenizer.countTokens() < 3)) {
                    client = stringTokenizer.nextToken();
                    if (stringTokenizer.hasMoreTokens()) {
                        message = stringTokenizer.nextToken();
                    }
                } else {
                    // Only one token found
                    dataOutputStream.writeUTF("CLOSE#1");
                    this.socket.close();
                    break;
                }

                // This is where the client gets online
                if (command.equals("CONNECT") && this.name == "Guest") {
                    if (ChatServer.userList.findUser(client)) {
                        if (!ChatServer.userList.getStatus(client)) {
                            ChatServer.userList.changeStatus(client, true);
                            this.name = client;
                            for (ClientHandler clientHandler : ChatServer.ar) {
                                if (ChatServer.userList.getStatus(clientHandler.name)) {
                                    clientHandler.dataOutputStream.writeUTF("ONLINE#" + ChatServer.userList.showUsers());
                                }
                            }
                        }
                    } else {
                        // User not found
                        dataOutputStream.writeUTF("CLOSE#2");
                        this.socket.close();
                        break;
                    }
                } else if (command.equals("CONNECT") && this.name != "Guest") {
                    // Cannot connect twice
                    dataOutputStream.writeUTF("CLOSE#1");
                    this.socket.close();
                    break;
                }
// SEND
                if (command.equals("SEND") && ChatServer.userList.getStatus(this.name) && !message.isEmpty()) {
                    for (ClientHandler clientHandler : ChatServer.ar) {
                        if (client.equals("*")) {
                            // Send message to ALL clients
                            clientHandler.dataOutputStream.writeUTF("MESSAGE#" + this.name + "#" + message);
                        } else if (ChatServer.userList.getStatus(client) && clientHandler.name.equals(client)) {
                            // Send message to specific client
                            clientHandler.dataOutputStream.writeUTF("MESSAGE#" + this.name + "#" + message);
                        } else if (client.contains(",")) {
                            // Send message to multiple clients
                            String[] strings = client.split(",");
                            for (String string : strings) {
                                if (clientHandler.name.equals(string) && ChatServer.userList.getStatus(string)){
                                    clientHandler.dataOutputStream.writeUTF("MESSAGE#" + this.name + "#" + message);
                                }
                            }

                        }
                    }
                }
            } catch (IOException e) {
                break;
            }
        }

        // Inform other clients about us not being online any longer
        ChatServer.userList.changeStatus(this.name, false);
        for (ClientHandler clientHandler : ChatServer.ar) {
            try {
                clientHandler.dataOutputStream.writeUTF("ONLINE#" + ChatServer.userList.showUsers());
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("# Client disconnected (" + this.name + ") ");

        // Close and release resources
        try {
            this.socket.close();
            this.dataInputStream.close();
            this.dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// 1.
// Todo: SEND, send to multiple users    SEND#Peter,Simon#Hello
// TODO Vi skal lave et build af vores ChatServer og ChatClient. Serveren skal kunne køre fra vores Droplet,
// og klienten skal kunne forbinde til den.

// 2.
// Todo: CONNECT with same name from foreign client: What if user is already online ?
// Todo: Illegal input was received, close connection

// Spørgsmål til Daniel: MANGLER SVAR
// Hvad menes der her? SEND -> "After a SEND command, the server can send an ONLINE, MESSAGE or a CLOSE command"