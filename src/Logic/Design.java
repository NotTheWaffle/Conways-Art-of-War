package Logic;

public class Design {
	private char[][] design;
	private String name;
	private String id;
	
	public Design(int rows){
		design = new char[rows][];
	}
	public Design(char[][] design){
		this.design = design;
	}
	public char getItem(int row, int col){
		return design[row][col];
	}
	public void updateRow(int row, char[] design){
		this.design[row] = design;
	}
}