package Logic;

public class Cursor {
	private int x;
	private int y;
	private Design design;

	public Cursor(int x, int y, Design design){
		this.design = design;
		this.x = x;
		this.y = y;
	}

	public void updateOverlay(Overlay overlay, int newx, int newy, Design newDesign){
		int rows = overlay.getRows();
		int cols = overlay.getCols();
		int dRows = design.get().length;
		int dCols = design.get()[0].length;
		int ndRows = newDesign.get().length;
		int ndCols = newDesign.get()[0].length;
		for (int row = 0; row < dRows; row++){
			for (int col = 0; col < dCols; col++){
				overlay.setItem(Math.floorMod(row + y, rows), Math.floorMod(col + x, cols), 0);
			}
		}
		for (int row = 0; row < ndRows; row++){
			for (int col = 0; col < ndCols; col++){
				if (newDesign.getItem(row, col) == 1){
					overlay.setItem(Math.floorMod(row + newy, rows),Math.floorMod(col + newx, cols), 1);
				}
			}
		}
		this.x = newx;
		this.y = newy;
		this.design = newDesign;
	}
}
