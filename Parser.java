public class Parser {
    static final int chess_board_size = 8;

    public Parser() {
        return;
    }

    public Command parse(String user_input, ChessBoard chessBoard) throws Exception {
        String[] tokens = user_input.split(" ");

        switch(tokens[0]) {
            case "move":
                if (tokens.length != 5) {
                    throw new Exception("Move requires the following format move" +
                                         " old_row old_col new_row new_col");
                }
                int old_row = Integer.parseInt(tokens[1]) - 1;
                int old_col = Integer.parseInt(tokens[2]) - 1;
                int new_row = Integer.parseInt(tokens[3]) - 1;
                int new_col = Integer.parseInt(tokens[4]) - 1;
                try {
                    ChessTile old_tile = new ChessTile(old_row, old_col);
                    ChessTile new_tile = new ChessTile(new_row, new_col);
                    return new Move(chessBoard, old_tile, new_tile);
                } catch (Exception e) {
                    throw e;
                }               
            case "place":
                if (tokens.length != 4) {
                    throw new Exception("Place requires the following format move" +
                                        "place piece row col");
                }
                char piece = tokens[1].charAt(0);
                piece = (piece == '_') ? ' ' : piece;
                int row = Integer.parseInt(tokens[2]) - 1;
                int col = Integer.parseInt(tokens[3]) - 1;
                try {
                    ChessTile tile = new ChessTile(row, col);
                    return new Place(chessBoard, piece, tile);
                } catch (Exception e) {
                    throw e;
                }
            case "undo":
                return new Undo(chessBoard);
            case "checkmate":
                return new Checkmate();
            default:
                throw new Exception("Not a vaild command");
        }
    }
}