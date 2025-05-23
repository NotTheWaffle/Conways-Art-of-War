package Logic;

public class byte2d {
	public byte[][] grid;
	public byte2d(byte[][] grid){
		this.grid = grid;
	}
	public void setItem(int row, int col, byte val){
		grid[row][col] = val;
	}
	public byte getItem(int row, int col){
		return grid[row][col];
	}
}