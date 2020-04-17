public class UndoMove extends Command
{
	private ChessBoard chess_board;
	private ChessTile old_tile;
	private ChessTile new_tile;
	private char original_piece;

	public UndoMove(ChessBoard chessBoard, ChessTile oldTile, ChessTile newTile, char originalPiece)
	{
		this.chess_board = chessBoard;
		this.old_tile = oldTile;
		this.new_tile = newTile;
		this.original_piece = originalPiece;
	}

	public void execute()
	{
		char old_piece = chess_board.getTilePiece(new_tile)

		chess_board.setTilePiece(old_tile, old_piece);
		chess_board.setTilePiece(new_tile, original_piece);
	}
}