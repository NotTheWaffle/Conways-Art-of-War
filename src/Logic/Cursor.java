package Logic;

public class Cursor {
	private final Overlay overlay;
	private int x;
	private int y;
	private Design design;

	public Cursor(Overlay overlay, int x, int y, Design design){
		this.overlay = overlay;
		this.design = design;
		this.x = x;
		this.y = y;
	}

	public void flipVDesign(){
		Design newDesign = new Design(new int[this.design.get().length][this.design.get()[0].length], this.design.getName(), this.design.getId());
		int invRow = newDesign.get().length;
		for (int row = 0; row < newDesign.get().length; row++){
			invRow--;
			for (int col = 0; col < newDesign.get()[0].length; col++){
				newDesign.setItem(row, col, this.design.getItem(invRow, col));
			}
		}
		this.design = newDesign;
	}
	public void flipHDesign(){
		Design newDesign = new Design(new int[this.design.get().length][this.design.get()[0].length], this.design.getName(), this.design.getId());
		for (int row = 0; row < newDesign.get().length; row++){
			int invCol = newDesign.get()[0].length;
			for (int col = 0; col < newDesign.get()[0].length; col++){
				invCol--;
				newDesign.setItem(row, col, this.design.getItem(row, invCol));
			}
		}
		this.design = newDesign;

	}
	public void cwDesign(){
		erase();
		Design newDesign = new Design(new int[this.design.get()[0].length][this.design.get().length], this.design.getName(), this.design.getId());
		int invRow = newDesign.get()[0].length;
		for (int row = 0; row < newDesign.get()[0].length; row++){
			invRow--;
			for (int col = 0; col < newDesign.get().length; col++){
				newDesign.setItem(col, row, this.design.getItem(invRow, col));
			}
		}
		this.design = newDesign;
	}
	public void ccwDesign(){
		erase();
		Design newDesign = new Design(new int[this.design.get()[0].length][this.design.get().length], this.design.getName(), this.design.getId());
		for (int row = 0; row < newDesign.get()[0].length; row++){
			int invCol = newDesign.get().length;
			for (int col = 0; col < newDesign.get().length; col++){
				invCol--;
				newDesign.setItem(col, row, this.design.getItem(row, invCol));
			}
		}
		this.design = newDesign;
	}
	
	public void newDesign(Design newDesign){
		erase();
		this.design = newDesign;
	}

	public void erase(){
		int rows = overlay.getRows();
		int cols = overlay.getCols();
		int dRows = design.get().length;
		int dCols = design.get()[0].length;
		for (int row = 0; row < dRows; row++){
			for (int col = 0; col < dCols; col++){
				overlay.setItem(Math.floorMod(row + y, rows), Math.floorMod(col + x, cols), 0);
			}
		}
	}
	public void draw(int newx, int newy){
		int rows = overlay.getRows();
		int cols = overlay.getCols();
		int dRows = design.get().length;
		int dCols = design.get()[0].length;
		for (int row = 0; row < dRows; row++){
			for (int col = 0; col < dCols; col++){
				if (design.getItem(row, col) == 1){
					overlay.updateItem(Math.floorMod(row + newy, rows),Math.floorMod(col + newx, cols), 1);
				}
			}
		}
		this.x = newx;
		this.y = newy;
	}
	public void drawTo(Conways game, int newx, int newy){
		int rows = overlay.getRows();
		int cols = overlay.getCols();
		int dRows = design.get().length;
		int dCols = design.get()[0].length;
		for (int row = 0; row < dRows; row++){
			for (int col = 0; col < dCols; col++){
				if (design.getItem(row, col) == 1){
					game.updateItem(Math.floorMod(row + newy, rows),Math.floorMod(col + newx, cols), 1);
				}
			}
		}
		this.x = newx;
		this.y = newy;
	}
	
}
