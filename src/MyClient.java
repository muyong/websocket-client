import org.glassfish.tyrus.client.ClientManager;

import javax.json.Json;
import javax.websocket.Session;
import java.net.URI;
import java.util.Scanner;

public class MyClient {

    public static final String SERVER = "ws://localhost:8025/ws/chat";

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        String message;

        // connect to server
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tiny Chat!");
        System.out.println("What's your name?");
        String user = scanner.nextLine();
        Session session = client.connectToServer(MyClientEndpoint.class, new URI(SERVER));
        System.out.println("You are logged in as: " + user);

        // repeatedly read a message and send it to the server (until quit)
        do {
            message = scanner.nextLine();
            session.getBasicRemote().sendText(formatMessage(message, user));
        } while (!message.equalsIgnoreCase("quit"));
    }

    private static String formatMessage(String message, String user) {
        return Json.createObjectBuilder()
                .add("content", message)
                .add("sender", user)
                .add("received", "")
                .build().toString();
    }

}