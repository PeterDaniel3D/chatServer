package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable{

    // Template
    private String name = "Guest";
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    Socket socket;
    boolean isloggedin;

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

                if (command.equals("CONNECT") && ChatServer.userList.findUser(client)) {
                    if (!ChatServer.userList.getStatus(client)) {
                        ChatServer.userList.changeStatus(client, true);

                        for (ClientHandler clientHandler : ChatServer.ar) {
                            clientHandler.dataOutputStream.writeUTF("ONLINE#" + client);
                        }


                    }
                }










            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
