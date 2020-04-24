import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) {
        int port = 8001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Connected");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Ping: Hi! I am the server");
            }
        } catch (IOException ex) {
 
            System.out.println("Server Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}