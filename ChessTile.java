public class ChessTile
{
	static final int chess_board_size = 8;

	private int row;
	private int col;

	public ChessTile(int row, int col) throws Exception
	{
		if (row < 0 || row >= chess_board_size || col < 0 || col >= chess_board_size) {
            throw new Exception("Not a valid location " + "row is: " + row + " column is: " + col);
        }
		this.row = row;
		this.col = col;
	}

	public int getRow()
	{
		return this.row;
	}

	public int getCol()
	{
		return this.col;
	}
}