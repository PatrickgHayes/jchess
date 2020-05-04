import java.util.Scanner;
import java.util.Stack;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.io.IOException;

public class Client
{
	public static void main(String[] args) {

        int playerNum = Integer.parseInt(args[0]);

        //String hostname = "54.177.179.89";
        String hostname = "localhost";
        int port = 8001;

        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            line = reader.readLine();
            //reader.close();
            System.out.println(line);
            if (line.substring(0,5).equals("Error"))
            {
                return;
            }

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ChessBoard game = new ChessBoard();
            Scanner input = new Scanner(System.in);
            Parser parser = new Parser();

            System.out.println();
            System.out.println("Welcome to Chess!\n");
            System.out.println("Please enter your name:");
            String name = input.nextLine();
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
            game.print();
            System.out.println();

            while (true)
            {
                try {
                    if (playerNum == 1) {
                        Command command;
                        do
                        {
                            System.out.println("Player 1, make your move!");
                            String command_text = input.nextLine();
                            System.out.println();
                            writer.println(command_text);
                            command = parser.parse(command_text, game);
                            command.execute();
                            game.print();
                            System.out.println();
                        }
                        while ((command instanceof EndTurn) == false);
                        System.out.println();
                        do
                        {
                            System.out.println("Waiting for player 2 to move");
                            String command_text = reader.readLine();
                            System.out.println("Executing command: " + command_text);
                            System.out.println();
                            command = parser.parse(command_text, game);
                            command.execute();
                            game.print();
                            System.out.println();
                        }
                        while ((command instanceof EndTurn) == false);
                    } else {
                        Command command;
                        do
                        {
                            System.out.println("Waiting for player 1 to move");
                            String command_text = reader.readLine();
                            System.out.println("Executing command: " + command_text);
                            System.out.println();
                            command = parser.parse(command_text, game);
                            command.execute();
                            game.print();
                            System.out.println();
                        }
                        while ((command instanceof EndTurn) == false);
                        do
                        {
                            System.out.println("Player 2, make your move!");
                            String command_text = input.nextLine();
                            System.out.println();
                            writer.println(command_text);
                            command = parser.parse(command_text, game);
                            command.execute();
                            game.print();
                            System.out.println();
                        }
                        while ((command instanceof EndTurn) == false);
                    }
                } catch(Exception e) {
                    System.out.println(e);
                    System.exit(2);
                }
            }

        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}