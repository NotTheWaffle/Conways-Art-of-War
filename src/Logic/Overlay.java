package Logic;

public class Overlay {
	private final byte2d grid;
	private final int rows;
	private final int cols;

	public Overlay(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.grid = new byte2d(new byte[rows][cols]);
	}
	
	public byte getItem(int row, int col){
		return grid.getItem(row, col);
	}
	public void setItem(int row, int col, byte val){
		grid.setItem(row, col, val);
	}
	public void setItem(int row, int col, int val){
		grid.setItem(row, col, (byte)val);
	}
	public void updateItem(int row, int col, int val){
		if (row < 0 || row >= rows || col < 0 || col >= cols){
			return;
		}
		if (val == 0){
			grid.setItem(row, col, (byte) 0);
		} else {
			grid.setItem(row, col, (byte) (1+(2*col)/cols));
		}
	}

	public int getRows(){
		return rows;
	}
	public int getCols(){
		return cols;
	}
}
