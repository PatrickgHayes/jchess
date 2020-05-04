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
                try {
                    if (chessBoard.descriptive_moves == false) {
                        return parseCoordinatePlaceCommand(tokens, chessBoard);
                    } else {
                        return parseDescriptivePlaceCommand(tokens, chessBoard);
                    }
                } catch (Exception e) {
                    throw e;
                }
            case "undo":
                return new Undo(chessBoard);
            case "checkmate":
                return new Checkmate();
            case "endturn":
                return new EndTurn();
            default:
                throw new Exception("Not a vaild command");
        }
    }

    public Command parseCoordinateMoveCommand(String[] tokens, ChessBoard chessBoard) throws Exception
    {
        if (tokens.length != 3) {
            throw new Exception("Move requires the following format move" +
                                 " r(old_row)c(old_col) r(new_row)c(new_col)");
        }
        int old_row = Integer.parseInt(tokens[1].substring(1,2));
        int old_col = Integer.parseInt(tokens[1].substring(3,4));
        int new_row = Integer.parseInt(tokens[2].substring(1,2));
        int new_col = Integer.parseInt(tokens[2].substring(3,4));
        try {
            ChessTile old_tile = new ChessTile(old_row-1, old_col-1);
            ChessTile new_tile = new ChessTile(new_row-1, new_col-1);
            return new Move(chessBoard, old_tile, new_tile);
        } catch (Exception e) {
            throw e;
        }
    }

    public Command parseDescriptiveMoveCommand(String[] tokens, ChessBoard chessBoard) throws Exception
    {
        if (tokens.length != 4) {
            throw new Exception("Move requires the following format move" +
                                 " player descriptive_location descriptive_move");
        }
        int player = Integer.parseInt(tokens[1]);
        String descriptive_location = tokens[2];
        String descriptive_move = tokens[3];
        
        // Get the old row and column
        ChessTile old_tile = getTileFromDescriptiveLocation(descriptive_location, player);
        // Get the new row and new column
        ChessTile new_tile = getTileFromDescriptiveMove(descriptive_move, old_tile);
        return new Move(chessBoard, old_tile, new_tile);
    }

    private ChessTile getTileFromDescriptiveMove(String descriptive_move, ChessTile oldTile) throws Exception {
        int old_row = oldTile.getRow();
        int old_col = oldTile.getCol();
        int new_row = -1;
        int new_col = -1;

        if (descriptive_move.length() == 2)
        {
            switch(descriptive_move.charAt(0))
            {
                case 'u':
                    new_row = old_row - Integer.parseInt(String.valueOf(descriptive_move.charAt(1)));
                    new_col = old_col;
                    break;
                case 'd':
                    new_row = old_row + Integer.parseInt(String.valueOf(descriptive_move.charAt(1)));
                    new_col = old_col;
                    break;
                case 'r':
                    new_col = old_col + Integer.parseInt(String.valueOf(descriptive_move.charAt(1)));
                    new_row = old_row;
                    break;
                case 'l':
                    new_col = old_col - Integer.parseInt(String.valueOf(descriptive_move.charAt(1)));
                    new_row = old_row;
                    break;
            }
        }
        else if (descriptive_move.length() == 3)
        {
            new_row = determineMoveRow(descriptive_move.charAt(0), Integer.parseInt(String.valueOf(descriptive_move.charAt(2))), old_row);
            new_col = determineMoveColumn(descriptive_move.charAt(1), Integer.parseInt(String.valueOf(descriptive_move.charAt(2))), old_col);
        }
        else
        {
            new_row = determineMoveRow(descriptive_move.charAt(0), Integer.parseInt(String.valueOf(descriptive_move.charAt(1))), old_row);
            new_col = determineMoveColumn(descriptive_move.charAt(2), Integer.parseInt(String.valueOf(descriptive_move.charAt(3))), old_col);
        }
        try {
            return new ChessTile(new_row, new_col);
        } catch (Exception e) {
            throw new Exception("Destination " + e);
        }
    }

    int determineDescriptiveColumn(char side, char piece)
    {
        if (side == 'Q')
        {
            switch(piece)
            {
                case 'R':
                    return 0;
                case 'N':
                    return 1;
                case 'B':
                    return 2;
            }
        }
        else
        {
            switch(piece)
            {
                case 'R':
                    return 7;
                case 'N':
                    return 6;
                case 'B':
                    return 5;
            }
        }
        return 0;
    }

    int determineMoveColumn(char direction, int amount, int old_col)
    {
        switch(direction)
        {
            case 'r':
                return old_col + amount;
            case 'l':
                return old_col - amount;
            default:
                return 0;
        }
    }

    int determineMoveRow(char direction, int amount, int old_row)
    {
        switch(direction)
        {
            case 'u':
                return old_row - amount;
            case 'd':
                return old_row + amount;
            default:
                return 0;
        }
    }

    private Place parseCoordinatePlaceCommand(String[] tokens, ChessBoard chessBoard) throws Exception {
        if (tokens.length != 3) {
            throw new Exception("Place requires the following format move" +
                                "place piece r(row)c(col)");
        }
        char piece = tokens[1].charAt(0);
        piece = (piece == '_') ? ' ' : piece;
        int row = Integer.parseInt(tokens[2].substring(1,2)) - 1;
        int col = Integer.parseInt(tokens[2].substring(3,4)) - 1;
        try {
            ChessTile tile = new ChessTile(row, col);
            return new Place(chessBoard, piece, tile);
        } catch (Exception e) {
            throw e;
        }
    }

    private Place parseDescriptivePlaceCommand(String[] tokens, ChessBoard chessBoard) throws Exception {
        if (tokens.length != 4) {
            throw new Exception("Place requires the following format: place " +
                                "player piece descriptive_location");
        }

        int player = Integer.parseInt(tokens[1]);
        char piece = tokens[2].charAt(0);
        piece = (piece == '_') ? ' ' : piece;
        String descriptiveLocation = tokens[3];
        ChessTile tile = getTileFromDescriptiveLocation(descriptiveLocation, player);
        return new Place(chessBoard, piece, tile);
    }

    private ChessTile getTileFromDescriptiveLocation(String descriptive_location, int player) throws Exception {
        // Get the row and column
        int col;
        int row;
        if (descriptive_location.length() == 2)
        {
            col = (descriptive_location.charAt(0) == 'Q') ? 3 : 4;
            int temp_row = Integer.parseInt(String.valueOf(descriptive_location.charAt(1)));
            row = (player == 1) ? temp_row - 1 : ChessBoard.chess_board_size - temp_row;
        }
        else
        {
            col = determineDescriptiveColumn(descriptive_location.charAt(0), descriptive_location.charAt(1));
            int temp_row = Integer.parseInt(String.valueOf(descriptive_location.charAt(2)));
            row = (player == 1) ? temp_row - 1 : ChessBoard.chess_board_size - temp_row;
        }
        try {
            return new ChessTile(row, col);
        } catch (Exception e) {
            throw new Exception("Starting Location " + e);
        }
    }
}