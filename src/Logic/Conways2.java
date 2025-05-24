package Logic;

import java.util.ArrayList;

public class Conways2{
	private final ArrayList<byte2d> keystones;
	private final int keystoneFreq = 32;
	private final ArrayList<Long> hashcodes;

	private byte2d grid;
	private final int rows;
	private final int cols;

	private int currentTick;


	public Conways2(int rows, int cols){
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
		if (row < 0 || row >= rows || col < 0 || col >= cols || currentTick > 0){
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
		if (row < 0 || row >= rows || col < 0 || col >= cols || currentTick > 0){
			return;
		}
		grid.setItem(row, col, (byte) val);
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
			updateHashcodes();
			updateKeystones();
		}

		byte2d bufferGrid = new byte2d(new byte[rows][cols]);
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < cols; col++){
				byte p1Neighbors = 0;
				byte p2Neighbors = 0;
				for (int dRow = row-1; dRow <= row+1; dRow++){
					for (int dCol = row-1; dCol <= col+1; dCol++){
						if (dRow == row && dCol == col){
							continue;
						}
						byte val = grid.getItem(dRow, dCol);
						if (val == 1){
							p1Neighbors ++;
						}
						if (val == 2){
							p2Neighbors ++;
						}
					}
				}
				bufferGrid.setItem(row, col, applyRules(p1Neighbors, p2Neighbors, bufferGrid.getItem(row, col)));
			}
		}
		grid = bufferGrid;

		updateHashcodes();
		updateKeystones();
		currentTick++;
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

		grid = keystones.get(tick/keystoneFreq);
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
		long hash = 0;
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < cols; col++){
				hash = hash * 31 + grid.getItem(row, col);
			}
		}
		hashcodes.set(currentTick, hash);
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
	}
	public void clear(){
		this.keystones.clear();
		this.hashcodes.clear();
		this.grid = new byte2d(new byte[rows][cols]);
		this.currentTick = 0;
	}
}