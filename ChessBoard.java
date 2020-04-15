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
        for(int i=0; i<chess_board_size; ++i) {
            System.out.print("| ");
            for (int j=0; j<chess_board_size; ++j) {
                System.out.print("" + this.chess_board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    private void print_odd_line() {
        for (int i=0; i<chess_board_size; ++i) {
            if (i % 2 == 0) {
                System.out.print
            }
        }

    }

    private void print_even_line() {

    }

    public static void main(String[] args) {
        ChessBoard game = new ChessBoard();
        game.print();
    }
}