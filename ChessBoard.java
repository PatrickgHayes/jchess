import java.util.Scanner;
import java.util.Stack;

public class ChessBoard {
    static final int chess_board_size = 8;

    private final char[][] chess_board;
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

        this.history = new Stack<>();
    }

    public void print() {
      System.out.println("     C  C  C  C  C  C  C  C  ");
      System.out.println("     1  2  3  4  5  6  7  8  ");
      System.out.println("    ------------------------ ");
        for(int i=0; i<chess_board_size; ++i) {
            System.out.print("R" + Integer.toString(i+1) + " |");
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
        System.out.println("     ------------------------ ");
    }

    public char getTilePiece(ChessTile chessTile)
    {
        return chess_board[chessTile.getRow()][chessTile.getCol()];
    }

    public void setTilePiece(ChessTile chessTile, char piece)
    {
        chess_board[chessTile.getRow()][chessTile.getCol()] = piece;
    }

    public char movePiece(int old_row, int old_col, int new_row, int new_col)
    {
        if (old_row < 1 || old_row > chess_board_size || old_col < 1 || old_col > chess_board_size) {
            System.out.println("Not a valid location" );
            return ' ';
        }
        placePiece(new_row, new_col, chess_board[old_row-1][old_col-1]);
        placePiece(old_row, old_col, ' ');
        return chess_board[old_row-1][old_col-1]; 
    }

    public char placePiece(int row, int col, char piece) {
        if (row < 1 || row > chess_board_size || col < 1 || col > chess_board_size) {
            System.out.println("Not a valid location" );
            return ' ';
        }
        char tmp = chess_board[row-1][col-1];
        chess_board[row-1][col-1] = piece;
        return tmp;
    }

    public void undoMove(String move) {
        String[] command_tokens = move.split(" ");
        switch(command_tokens[0]) {
            case "move":
                int old_row = Integer.parseInt(command_tokens[1]);
                int old_col = Integer.parseInt(command_tokens[2]);
                int new_row = Integer.parseInt(command_tokens[3]);
                int new_col = Integer.parseInt(command_tokens[4]);
                char removed_piece = command_tokens[5].charAt(0);
                removed_piece = (removed_piece == '_') ? ' ' : removed_piece;
                char curr_piece = chess_board[new_row-1][new_col-1];
                placePiece(new_row,new_col,removed_piece);
                placePiece(old_row,old_col,curr_piece);
                break;
            case "place":
                char unneeded_piece = command_tokens[1].charAt(0);
                int row = Integer.parseInt(command_tokens[2]);
                int col = Integer.parseInt(command_tokens[3]);
                removed_piece = command_tokens[4].charAt(0);
                removed_piece = (removed_piece == '_') ? ' ' : removed_piece;
                placePiece(row, col, removed_piece);
                break;
        }
    }

    public static void main(String[] args) {
        ChessBoard game = new ChessBoard();
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Welcome to Chess!\n");
        System.out.println("Let's see our initial board:\n");
        while (true)
        {
            game.print();
            System.out.println();
            System.out.println("next player, make your move!");
            String command = input.nextLine();

            String[] command_tokens = command.split(" ");

            switch(command_tokens[0]) {
                case "move":
                    int old_row = Integer.parseInt(command_tokens[1]);
                    int old_col = Integer.parseInt(command_tokens[2]);
                    int new_row = Integer.parseInt(command_tokens[3]);
                    int new_col = Integer.parseInt(command_tokens[4]);
                    char old_piece = game.movePiece(old_row, old_col, new_row, new_col);
                    old_piece = (old_piece == ' ') ? '_' : old_piece; 
                    game.history.push(command + " " + old_piece);
                    break;
                case "place":
                    char piece = command_tokens[1].charAt(0);
                    int row = Integer.parseInt(command_tokens[2]);
                    int col = Integer.parseInt(command_tokens[3]);
                    old_piece = game.placePiece(row, col, piece);
                    old_piece = (old_piece == ' ') ? '_' : old_piece;
                    game.history.push(command + " " + old_piece);
                    break;
                case "undo":
                    String last_move = game.history.pop();
                    game.undoMove(last_move);
                    break;
                case "checkmate":
                    input.close();
                    return;
                default:
                    System.out.println("Not a valid move");
                    break;
            }
        }
    }
}