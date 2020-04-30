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
    
    public ClientHandler(Socket socket, BufferedReader reader, PrintWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String command_text = reader.readLine();
                // Parse action and execute
                // Command command = parser.parse(command_text);
                // command.execute();
                writer.println("Your command has been received");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}