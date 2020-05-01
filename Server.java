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
    int port = 8001;
    String player1 = "";
    String player2 = "";
    ChessBoard chess_board = new ChessBoard();
    PrintWriter player1sWriter;
    PrintWriter player2sWriter;

    public void startServer() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (player1.equals(""))
                {
                    player1 = socket.getRemoteSocketAddress().toString();
                    player1sWriter = writer;
                	System.out.println("Player 1 Connected");
                    writer.println("Ping: You have been registerd by the server!");
                    int playerNum = 1;
                    Thread thread = new ClientHandler(socket, reader, writer, chess_board, this, playerNum);
                    thread.start();
                }
                else if (player2.equals(""))
                {
                    player2 = socket.getRemoteSocketAddress().toString();
                    player2sWriter = writer;
                	System.out.println("Player 2 Connected");
                    writer.println("Ping: You have been registerd by the server!");
                    int playerNum = 2;
                    Thread thread = new ClientHandler(socket, reader, writer, chess_board, this, playerNum);
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

	public static void main(String[] args) {
        Server server = new Server();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCommandToPlayer1(String command) {
        System.out.println("Sending command to player 1");
        player1sWriter.println(command);
    }

    public void sendCommandToPlayer2(String command) {
        System.out.println("Sending command to player 2");
        player2sWriter.println(command);
    }
}