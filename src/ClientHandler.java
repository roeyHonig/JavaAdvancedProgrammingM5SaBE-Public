import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                ObjectOutputStream out =
                        new ObjectOutputStream(socket.getOutputStream());

                ObjectInputStream in =
                        new ObjectInputStream(socket.getInputStream());
        ) {

            String request = (String) in.readObject();

            if ("GET_MENU".equals(request)) {

                ArrayList<MenuItem> menu =
                        Util.loadMenuFromFile("menu.txt");

                out.writeObject(menu);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException e) {}
        }
    }
}
