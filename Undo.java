public class Undo extends Command
{
	ChessBoard chess_board;

	public Undo(ChessBoard chessBoard)
	{
		this.chess_board = chessBoard;
	}

	public void execute()
	{
		Command undo_command = chess_board.undoLog.pop();
		undo_command.execute();
	}
}