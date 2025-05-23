package Logic;

import java.util.ArrayList;

public class Conways2{
	private final ArrayList<byte2d> keystones;
	private final int keystoneSpacing = 32;
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
		grid.setItem(row, col, (byte) val);
	}
	public void setItem(int row, int col, byte val){
		grid.setItem(row, col, val);
	}
	public void updateItem(int row, int col){

	}
	public void updateItem(int row, int col, int val){
		if (row < 0 || row >= rows || col < 0 || col >= cols){
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

	public void tick(int amount){
		for (int i = 0; i < amount; i++){
			tick();
		}
	}
	public void tick(){
		if (currentTick == 0){
			updateHashcodes();
			updateKeystones();
		}
		


		updateHashcodes();
		updateKeystones();
		currentTick++;
	}
	public void loadTick(int tick){

		currentTick = tick;
	}

	private void updateKeystones(){
		if (currentTick % keystoneSpacing == 0){
			while (keystones.size() <= currentTick/keystoneSpacing){
				keystones.add(null);
			}
			keystones.set(currentTick/keystoneSpacing, grid);
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