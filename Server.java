import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	public static void main(String[] args) {
    	int port = 8001;
    	String player1 = "";
    	String player2 = "";
        ChessBoard chess_board = new ChessBoard();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (player1.equals(""))
                {
                	player1 = socket.getRemoteSocketAddress().toString();
                	System.out.println("Player 1 Connected");
                    writer.println("Ping: You have been registerd by the server!");
                    Thread thread = new ClientHandler(socket, reader, writer, chess_board);
                    thread.start();
                }
                else if (player2.equals(""))
                {
                	player2 = socket.getRemoteSocketAddress().toString();
                	System.out.println("Player 2 Connected");
                    writer.println("Ping: You have been registerd by the server!");
                    Thread thread = new ClientHandler(socket, reader, writer, chess_board);
                    thread.start();
                }
                else
                {
                	System.out.println("Both players are already regiestered. Not registering third player.");
                    writer.println("Error: Two players are already playing. Come back another time!");
                }
            }
        } catch (IOException ex) {
 
            System.out.println("Server Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}