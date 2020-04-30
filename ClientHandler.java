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

    public ClientHandler(Socket socket, BufferedReader reader, PrintWriter writer, ChessBoard chess_board) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
        this.chess_board = chess_board;
    }

    @Override
    public void run() {
        while (true) {
            try {
                command_text = reader.readLine();
                System.out.println("Executing command: " + command_text);
                Command command = parser.parse(command_text, chess_board);
                command.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch(Exception e) {
                    System.out.println(e);
            }
        }
    }

}