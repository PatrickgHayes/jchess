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
        int old_row = Integer.parseInt(tokens[1]);
        int old_col = Integer.parseInt(tokens[2]);
        int new_row = Integer.parseInt(tokens[3]);
        int new_col = Integer.parseInt(tokens[4]);
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

            // Get the old row and old column
            if (descriptive_location.length() == 2)
            {
                old_col = (descriptive_location.charAt(0) == 'Q') ? 4 : 5;
                int temp_row = Integer.parseInt(String.valueOf(descriptive_location.charAt(1)));
                old_row = (player == 1) ? temp_row : chessBoard.chess_board_size - temp_row + 1;
            }
            else
            {
                old_col = determineDescriptiveColumn(descriptive_location.charAt(0), descriptive_location.charAt(1));
                int temp_row = Integer.parseInt(String.valueOf(descriptive_location.charAt(2)));
                old_row = (player == 1) ? temp_row : chessBoard.chess_board_size - temp_row + 1;
            }

            // Get the new row and new column
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

            System.out.println(new_col);
            System.out.println(new_row);
            
            ChessTile old_tile = new ChessTile(old_row-1, old_col-1);
            ChessTile new_tile = new ChessTile(new_row-1, new_col-1);
            return new Move(chessBoard, old_tile, new_tile);
        } catch (Exception e) {
            throw e;
        }
    }

    int determineDescriptiveColumn(char side, char piece)
    {
        if (side == 'Q')
        {
            switch(piece)
            {
                case 'R':
                    return 1;
                case 'N':
                    return 2;
                case 'B':
                    return 3;
            }
        }
        else
        {
            switch(piece)
            {
                case 'R':
                    return 8;
                case 'N':
                    return 7;
                case 'B':
                    return 6;
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
}