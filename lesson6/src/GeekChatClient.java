import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class GeekChatClient implements Runnable, GeekChat {
    DataOutputStream outputStream;
    @Override
    public void run() {
        try(Socket socket = new Socket(GeekChat.SERVER_HOST_DEFAULT,GeekChat.SERVER_PORT_DEFAULT)) {
            DataInputStream inputStream =  new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Ready for chat... ");
            while (true){
                String response = inputStream.readUTF();
                if (response!=null && !response.isEmpty())
                    System.out.println("Server: "+response);
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (UnknownHostException e) {
            System.out.println("No such host");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void postMessage(String string) {
        try {
            outputStream.writeUTF(string);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
