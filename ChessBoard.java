import java.util.Scanner;
import java.util.Stack;

public class ChessBoard {
    static final int chess_board_size = 8;

    private final char[][] chess_board;
    public boolean descriptive_moves;
    public Stack<Command> undoLog;

    public ChessBoard() {
        this.chess_board =  new char[][] {{'R','N','B','Q','K','B','N','R'},
                                          {'P','P','P','P','P','P','P','P'},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {'p','p','p','p','p','p','p','p'},
                                          {'r','n','b','q','k','b','n','r'}};

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