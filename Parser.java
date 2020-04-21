public class Parser {
    static final int chess_board_size = 8;

    public Parser() {
        return;
    }

    public Command parse(String user_input, ChessBoard chessBoard) throws Exception {
        String[] tokens = user_input.split(" ");

        switch(tokens[0]) {
            case "move":
                try {
                    if (chessBoard.descriptive_moves == false)
                    {
                        return parseCoordinateMoveCommand(tokens, chessBoard);
                    }
                    else
                    {
                        return parseDescriptiveMoveCommand(tokens, chessBoard);
                    }
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

    public Command parseCoordinateMoveCommand(String[] tokens, ChessBoard chessBoard) throws Exception
    {
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
    }

    public Command parseDescriptiveMoveCommand(String[] tokens, ChessBoard chessBoard) throws Exception
    {
        int old_row = 0;
        int old_col = 0;
        int new_row = 0;
        int new_col = 0;

        if (tokens.length != 4) {
            throw new Exception("Move requires the following format move" +
                                 " player descriptive_location descriptive_move");
        }
        int player = Integer.parseInt(tokens[1]);
        String descriptive_location = tokens[2];
        String descriptive_move = tokens[3];
        try {
            if (descriptive_location.length() == 2)
            {
                old_col = (descriptive_location.charAt(0) == 'Q') ? 4 : 5;
                int temp_row = (int)descriptive_location.charAt(1);
                old_row = (player == 1) ? temp_row : chessBoard.chess_board_size - temp_row;
            }
            else
            {
                if (descriptive_location.charAt(0) == 'Q')
                {
                    switch(descriptive_location.charAt(1))
                    {
                        case 'R':
                            old_col = 1;
                            break;
                        case 'N':
                            old_col = 2;
                            break;
                        case 'B':
                            old_col = 3;
                            break;
                    }
                }
                else
                {
                    switch(descriptive_location.charAt(1))
                    {
                        case 'R':
                            old_col = 8;
                            break;
                        case 'N':
                            old_col = 7;
                            break;
                        case 'B':
                            old_col = 6;
                            break;
                    }
                }
                int temp_row = (int)descriptive_location.charAt(2);
                old_row = (player == 1) ? temp_row : chessBoard.chess_board_size - temp_row;
            }
            
            ChessTile old_tile = new ChessTile(old_row, old_col);
            ChessTile new_tile = new ChessTile(new_row, new_col);
            return new Move(chessBoard, old_tile, new_tile);
        } catch (Exception e) {
            throw e;
        }
    }
}