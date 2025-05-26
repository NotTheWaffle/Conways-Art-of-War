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
	@Override
	public int hashCode(){
		int hash = 0;
		for (byte[] row : grid) {
			for (int col = 0; col < grid[0].length; col++) {
				hash = hash * 31 + row[col];
			}
		}
		return hash;
	}
	@Override
	public boolean equals(Object o){
		if (!(o instanceof byte2d)){
			return false;
		}
		byte2d b = (byte2d)o;
		if (b.grid.length != this.grid.length || b.grid[0].length != this.grid[0].length){
			return false;
		}
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				if (grid[row][col] != b.grid[row][col]){
					return false;
				}
			}
		}
		return true;
	}
}