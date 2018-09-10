import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GeekChatServer implements GeekChat {
    DataOutputStream outputStream = null;
    GeekChatServer(){

    }
    @Override
    public void run() {
        while (true) {
            DataInputStream inputStream = null;
            Socket client = null;
            try (ServerSocket server = new ServerSocket(GeekChat.SERVER_PORT_DEFAULT)) {
                System.out.print("Waiting for client...");
                client = server.accept();
                System.out.print("Client conntected...");
                outputStream = new DataOutputStream(client.getOutputStream());
                System.out.println("Ready to send message...");
                inputStream = new DataInputStream(client.getInputStream());
                System.out.println("Ready to receive message...");
                while (client.isConnected()) {
                    String message = inputStream.readUTF();
                    System.out.println("Client: " + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                System.out.println("Client disconnected");
                System.out.println("Closing connections & channels.");

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Closing connections & channels - DONE.");
            }
        }

    }


    @Override
    public void postMessage(String message) {
        if (outputStream==null) {
            System.out.println("Client not conntected yet");
            return;
        }
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Can't send message to client: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
