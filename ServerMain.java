import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
	public static void main(String[] args) {
    	int port = 8001;
    	String player1 = "";
    	String player2 = "";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                if (player1.equals(""))
                {
                	player1 = socket.getRemoteSocketAddress().toString();
                	System.out.println("Player 1 Connected");
                    out.println("Ping: You have been registerd by the server!");
                }
                else if (player2.equals(""))
                {
                	player2 = socket.getRemoteSocketAddress().toString();
                	System.out.println("Player 2 Connected");
                    out.println("Ping: You have been registerd by the server!");
                }
                else
                {
                	System.out.println("Both players are already regiestered. Not registering third player.");
                    out.println("Error: Two players are already playing. Come back another time!");
                }
            }
        } catch (IOException ex) {
 
            System.out.println("Server Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}