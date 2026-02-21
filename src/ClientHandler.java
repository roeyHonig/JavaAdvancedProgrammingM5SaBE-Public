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

            RequestWrapper requestWrapper = (RequestWrapper) in.readObject();
            String requestType = requestWrapper.getRequestType();
            Object payload = requestWrapper.getPayload();

            if ("GET_MENU".equals(requestType)) {

                ArrayList<MenuItem> menu =
                        Util.loadMenuFromFile("menu.txt");

                out.writeObject(menu);
                out.flush();
            } else if ("ORDER".equals(requestType)) {
                // Receive the Order object from the client
                Order order = (Order) payload;

                // Print the order details on the server side
                ArrayList<MenuItem> menu = Util.loadMenuFromFile("menu.txt");
                String orderSummary = order.toOrderString(menu);
                System.out.println(orderSummary);

                // Prepare the response to send back to the client
                Response response = new Response(orderSummary);

                // Send confirmation response back to the client
                out.writeObject(response);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException e) {}
        }
    }
}
