import java.util.Scanner;
import java.util.Stack;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.io.IOException;

public class ClientMain
{
	public static void main(String[] args) {

        String hostname = "localhost";
        int port = 8001;

        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            line = reader.readLine();
            System.out.println(line);
            reader.close();
            if (line.substring(0,5).equals("Error"))
            {
                return;
            }
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }

        ChessBoard game = new ChessBoard();
        Scanner input = new Scanner(System.in);
        Parser parser = new Parser();

        System.out.println();
        System.out.println("Welcome to Chess!\n");

        System.out.println("Would you like to play with coordinates or descriptive moves? Say \"coordinates\" or \"descriptive\".");
        String descriptive = input.nextLine();

        game.descriptive_moves = (descriptive.equals("descriptive")) ? true : false;

        if (game.descriptive_moves == false)
        {
            System.out.println("To move specify the row an column of the piece you want to move and then the row and column of the destination");
            System.out.println("Example: move 7 1 8 1");
        }
        else
        {
            System.out.println("To move first specify th piece you want to move by you using decriptive chess notation for example the pawn in front of your queen is q2. The pawn in front of the bishop on your queens side (queens bishop 2) would be entered qb2.");
            System.out.println("Then specify how squares youd like to move up of down followed by how many squares you'd like to move left and right");
            System.out.println("An example command all together would  move QB1 u5r5");
        }

        System.out.println("Let's see our initial board:\n");

        while (true)
        {
            game.print();
            System.out.println();

            System.out.println("Next player, make your move!");
            String command_text = input.nextLine();
            try {
                Command command = parser.parse(command_text, game);
                command.execute();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}