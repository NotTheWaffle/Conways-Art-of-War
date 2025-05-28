package Logic;

import java.util.ArrayList;

public class Conways{
	private final ArrayList<byte2d> keystones;
	private final int keystoneFreq = 32;
	private final ArrayList<Integer> hashcodes;

	private byte2d grid;
	private final int rows;
	private final int cols;

	private int currentTick;
	private boolean drawing;


	public Conways(int rows, int cols){
		this.drawing = true;
		this.keystones = new ArrayList<>();
		this.hashcodes = new ArrayList<>();
		this.rows = rows;
		this.cols = cols;
		this.grid = new byte2d(new byte[rows][cols]);
		this.currentTick = 0;
	}

	public byte getItem(int row, int col){
		return grid.getItem(row, col);
	}
	public void setItem(int row, int col, int val){
		grid.setItem(Math.floorMod(row, rows), Math.floorMod(col, cols), (byte) val);
	}
	public void setItem(int row, int col, byte val){
		grid.setItem(Math.floorMod(row, rows), Math.floorMod(col, cols), val);
	}

	public void updateItem(int row, int col){
		if (row < 0 || row >= rows || col < 0 || col >= cols || !drawing){
			return;
		}
		if (grid.getItem(row, col) == 0){
			if (col < cols / 2){
				grid.setItem(row, col, (byte)1);
			} else {
				grid.setItem(row, col, (byte)2);
			}
		} else {
			grid.setItem(row, col, (byte)0);
		}
	}
	public void updateItem(int row, int col, int val){
		if (row < 0 || row >= rows || col < 0 || col >= cols || !drawing){
			return;
		}
		if (val == 0){
			grid.setItem(row, col, (byte) 0);
		} else {
			grid.setItem(row, col, (byte) (1+(2*col)/cols));
		}
	}

	public int getCurrentTick(){
		return this.currentTick;
	}
	public int getRows(){
		return this.rows;
	}
	public int getCols(){
		return this.cols;
	}

	public void tick(){
		if (currentTick == 0){
			drawing = false;
			updateHashcodes();
			updateKeystones();
		}

		byte2d bufferGrid = new byte2d(new byte[rows][cols]);
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < cols; col++){
				byte p1Neighbors = 0;
				byte p2Neighbors = 0;
				for (int dRow = row-1; dRow <= row+1; dRow++){
					for (int dCol = col-1; dCol <= col+1; dCol++){
						if (dRow == row && dCol == col){
							continue;
						}
						byte val = grid.getItem(Math.floorMod(dRow, rows), Math.floorMod(dCol, cols));
						if (val == 1){
							p1Neighbors ++;
						}
						if (val == 2){
							p2Neighbors ++;
						}
					}
				}
				bufferGrid.setItem(row, col, applyRules(p1Neighbors, p2Neighbors, grid.getItem(row, col)));
			}
		}
		grid = bufferGrid;
		currentTick++;

		updateHashcodes();
		updateKeystones();

		//testHashcodes();
	}
	public void tick(int amount){
		for (int i = 0; i < amount; i++){
			tick();
		}
	}
	public void loadTick(int tick){
		if (tick < 0){
			tick = 0;
		}

		if (tick > currentTick){
			while (tick > currentTick){
				tick();
			}
			return;
		}

		if (!keystones.isEmpty()){	//if we click reset on 0
			grid = keystones.get(tick/keystoneFreq);
		}

		currentTick = tick/keystoneFreq;
		while (tick > currentTick){
			tick();
		}
	}

	private void updateKeystones(){
		if (currentTick % keystoneFreq == 0){
			while (keystones.size() <= currentTick/keystoneFreq){
				keystones.add(null);
			}
			keystones.set(currentTick/keystoneFreq, grid);
		}
	}
	private void updateHashcodes(){
		while (hashcodes.size() <= currentTick){
			hashcodes.add(null);
		}
		hashcodes.set(currentTick, grid.hashCode());
	}
	private void testHashcodes(){
		int hash = grid.hashCode();
		for (int tick = 0; tick < currentTick; tick++){
			if (hash == hashcodes.get(tick)){
				byte2d backup = grid;
				int backupTick = currentTick;
				loadTick(tick);
				if (grid.equals(backup)){
					System.out.println("Repeating " + backupTick +" -> " + currentTick);
					return;
				} else {
					loadTick(backupTick);
				}
			}
		}
	}

	private byte applyRules(int p1Neighbors, int p2Neighbors, byte type){
		if (p1Neighbors > 3 || p2Neighbors > 3 || (p1Neighbors < 2 && p2Neighbors < 2)){
			return 0;
		}
		if (p1Neighbors == p2Neighbors){
			return type;
		}
		if (type == 0){
			if (p1Neighbors == 3){
				return 1;
			}
			if (p2Neighbors == 3){
				return 2;
			}
			return 0;
		}
		if (type == 1){
			if (p1Neighbors > p2Neighbors){
				return 1;
			}
			return 0;
		}
		if (type == 2){
			if (p2Neighbors > p1Neighbors){
				return 2;
			}
			return 0;
		}
		
		return -1; //
	}
	
	public void reset(){
		loadTick(0);
		drawing = true;
	}
	public void clear(){
		this.keystones.clear();
		this.hashcodes.clear();
		this.grid = new byte2d(new byte[rows][cols]);
		this.currentTick = 0;
		drawing = true;
	}

	@Override
	public String toString(){
		return grid.toString();
	}
}