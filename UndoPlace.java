public class UndoPlace extends Command
{
	private ChessBoard chess_board;
	private char piece;
	private ChessTile tile;

	public UndoPlace(ChessBoard chessBoard, char piece, ChessTile tile)
	{
		this.chess_board = chessBoard;
		this.piece = piece;
		this.tile = tile;
	}

	public void execute()
	{
		chess_board.setTilePiece(tile, piece);
	}
}