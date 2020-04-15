import java.util.Scanner;

public class ChessBoard {
    static final int chess_board_size = 8;

    private final char[][] chess_board;

    public ChessBoard() {
        this.chess_board =  new char[][] {{'C','H','B','Q','K','B','H','C'},
                                          {'P','P','P','P','P','P','P','P'},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {' ',' ',' ',' ',' ',' ',' ',' '},
                                          {'p','p','p','p','p','p','p','p'},
                                          {'c','h','b','q','k','b','h','c'}};
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

    public void movePiece(int old_row, int old_col, int new_row, int new_col)
    {
        chess_board[new_row-1][new_col-1] = chess_board[old_row-1][old_col-1];
        chess_board[old_row-1][old_col-1] = ' '; 
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
            String position_change = input.nextLine();
            game.movePiece(Character.getNumericValue(position_change.charAt(0)), Character.getNumericValue(position_change.charAt(2)),
              Character.getNumericValue(position_change.charAt(4)), Character.getNumericValue(position_change.charAt(6)));
            if (position_change.length() == 9)
            {
                System.out.println("\nGame Over!");
                break;
            }
        }
    }
}