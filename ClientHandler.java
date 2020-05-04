import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    final Socket socket;
    final BufferedReader reader;
    final PrintWriter writer;
    String name;
    ChessBoard chess_board;
    Parser parser = new Parser();
    String command_text;
    Server server;
    int playerNum;

    public ClientHandler(Socket socket, BufferedReader reader, PrintWriter writer, ChessBoard chess_board, Server server, int playerNum) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
        this.chess_board = chess_board;
        this.server = server;
        this.playerNum = playerNum;
    }

    @Override
    public void run() {
        while (true) {
            try {
                command_text = reader.readLine();
                System.out.println("Executing command: " + command_text);
                System.out.println();
                Command command = parser.parse(command_text, chess_board);
                command.execute();
                chess_board.print();
                System.out.println();
                if (playerNum == 1) {
                    server.sendCommandToPlayer2(command_text);
                } else {
                    server.sendCommandToPlayer1(command_text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(Exception e) {
                    System.out.println(e);
            }
        }
    }

}