public class Move extends Command
{
	private ChessBoard chess_board;
	private ChessTile old_tile;
	private ChessTile new_tile;

	public Move(ChessBoard chessBoard, ChessTile oldTile, ChessTile newTile)
	{
		this.chess_board = chessBoard;
		this.old_tile = oldTile;
		this.new_tile = newTile;
	}

	public void execute()
	{
		char old_piece = chess_board.getTilePiece(old_tile);
		char original_piece = chess_board.getTilePiece(new_tile);

		chess_board.setTilePiece(new_tile, old_piece);
		chess_board.setTilePiece(old_tile, ' ');

		addToUndoLog(original_piece);
	}

	private void addToUndoLog(char original_piece)
	{
		UndoMove undo_move = new UndoMove(chess_board, old_tile, new_tile, original_piece);
		chess_board.undoLog.push(undo_move);
	}
}