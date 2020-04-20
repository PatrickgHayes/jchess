public class Place extends Command
{
	private ChessBoard chess_board;
	private char piece;
	private ChessTile tile;

	public Place(ChessBoard chessBoard, char piece, ChessTile tile)
	{
		this.chess_board = chessBoard;
		this.piece = piece;
		this.tile = tile;
	}

	public void execute()
	{
		char original_piece = chess_board.getTilePiece(tile);

		chess_board.setTilePiece(tile, piece);

		addToUndoLog(original_piece);
	}

	private void addToUndoLog(char original_piece)
	{
		UndoPlace undo_place = new UndoPlace(chess_board, original_piece, tile);
		chess_board.undoLog.push(undo_place);
	}
}