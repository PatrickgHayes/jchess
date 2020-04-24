import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.io.IOException;

public class Client {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8001;

        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            line = reader.readLine();
            System.out.println(line);
            reader.close();
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}