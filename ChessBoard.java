import java.util.Scanner;
import java.util.Stack;

public class ChessBoard {
    static final int chess_board_size = 8;

    private final char[][] chess_board;
    public boolean descriptive_moves;
    public Stack<Command> undoLog;

    public ChessBoard() {
        this.chess_board =  new char[][] {{'C','H','B','Q','K','B','H','C'},
                                          {'P','P','P','P','P','P','P','P'},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {'p','p','p','p','p','p','p','p'},
                                          {'c','h','b','q','k','b','h','c'}};

        this.descriptive_moves = false;
        this.undoLog = new Stack<>();
    }

    public void print() {
        if (this.descriptive_moves == false)
        {
            System.out.println("     C  C  C  C  C  C  C  C  ");
            System.out.println("     1  2  3  4  5  6  7  8  ");
            System.out.println("    ------------------------ ");
        }
        else
        {
            System.out.println(" ------------------------ ");
        }
        for(int i=0; i<chess_board_size; ++i) {
            if (this.descriptive_moves == false)
            {
                System.out.print("R" + Integer.toString(i+1) + " |");
            }
            else
            {
                System.out.print("|");
            }
            for (int j=0; j<chess_board_size; ++j) {
                if ((i % 2) == (j % 2))
                {
                    System.out.print(" " + this.chess_board[i][j] + " ");
                }
                else
                {
                    System.out.print("[" + this.chess_board[i][j] + "]");
                }
            }
            System.out.println("|");
        }
        if (this.descriptive_moves == false)
        {
            System.out.println("     ------------------------ ");
        }
        else
        {
            System.out.println(" ------------------------ ");
        }
    }

    public char getTilePiece(ChessTile chessTile)
    {
        return chess_board[chessTile.getRow()][chessTile.getCol()];
    }

    public void setTilePiece(ChessTile chessTile, char piece)
    {
        chess_board[chessTile.getRow()][chessTile.getCol()] = piece;
    }

    public static void main(String[] args) {
        ChessBoard game = new ChessBoard();
        Scanner input = new Scanner(System.in);
        Parser parser = new Parser();

        System.out.println();
        System.out.println("Welcome to Chess!\n");

        System.out.println("Would you like to play with coordinates or descriptive moves? Say \"coordinates\" or \"descriptive\".");
        String descriptive = input.nextLine();

        game.descriptive_moves = (descriptive.equals("descriptive")) ? true : false;

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