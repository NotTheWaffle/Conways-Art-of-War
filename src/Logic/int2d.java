package Logic;
public class int2d {
	public int[][] grid;
	public int2d(int[][] grid){
		this.grid = grid;
	}

	@Override
	public String toString(){
		String output = grid.length+"x"+grid[0].length+"\n";
		for (int[] row : grid){
			for (int item : row){
				output += item;
			}
			output += '\n';
		}
		return output;
	}
}	
