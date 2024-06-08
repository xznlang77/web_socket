package xzn;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws Exception {
        int port = 7777;
        WebSocketServer server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter message for clients (or 'quit'): ");
            String message = scanner.nextLine();
            if ("quit".equals(message)) {
                System.out.println("Shutting down server...");
                server.stop();
                System.out.println("Exiting program");
                return;
            }

            for (WebSocket conn : server.getConnections()) {
                conn.send(message);
            }
        }
    }

    private static class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
