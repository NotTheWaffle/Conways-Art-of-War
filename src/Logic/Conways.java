package Logic;

import java.util.ArrayList;

public class Conways {
	//ts needs to be fixed up
	private final ArrayList<int2d> keystones;
	private final ArrayList<Integer> checksums;
	
	private final int[][] grid;
	public final int rows;
	public final int cols;
	private int currentTick;
	public boolean finished = false;
	public int redAmount;
	public int winner;
	public int bluAmount;

	public Conways(int rows, int cols){
		this.checksums = new ArrayList<>();
		this.keystones = new ArrayList<>();
		this.rows = rows;
		this.cols = cols;
		grid = new int[rows][cols];
		for (int row = 0; row<rows; row++){
			for (int col = 0; col<cols; col++){
				grid[row][col] = 0;
			}
		}
	}
	/**
	 * Toggles item (col, row) between 1 and 0, or 2 and 0.
	 * Checks to ensure it is tick 0.
	 * Within Bounds.
	*/
	public void updateItem(int row, int col){
		if (col > cols/2){
			updateItem(row, col, 2-getItem(row, col));
		} else {
			updateItem(row, col, 1-getItem(row, col));
		}
	}
	public void updateItem(int row, int col, int val){
		if (row < 0 || row >= rows || col < 0 || col >= cols || currentTick != 0 || col == cols / 2 || col+1 == cols / 2){
			return;
		}
		if (col < cols/2){
			if (val == 0){
				setItem(row, col, 0);
			} else {
				setItem(row, col, 1);
			}
		} else {
			if (val == 0){
				setItem(row, col, 0);
			} else {
				setItem(row, col, 2);
			}
		}
	}
	/**
	 * Sets item (col, row) to val
	 * treats col and row modularly
	 */
	public void setItem(int row, int col, int val){
		grid[mod(row,rows)][mod(col,cols)] = val;
	}
	/**
	 * Returns the item at (col, row)
	 * 0 - empty, 1 - p1, 2 - p2
	 */
	public int getItem(int row, int col){
		if (row < 0 || row >= grid.length){
			if (col < 0 || col >= grid[0].length){
				//y oob , x obb
				return 0;
			} else {
				//y oob, x in
				return grid[mod(row, rows)][col];
			}
		} else {
			if (col < 0 || col >= grid[0].length){
				//y in , x oob
				return 0;
			} else {
				//y in , x in
				return grid[row][col];
			}
		}
	}
	/**
	 * Returns a reference to board
	 */
	public int[][] getBoard(){
		return grid;
	}
	public int getCurrentTick(){
		return currentTick;
	}

	public void loadTick(int tick){
		if (tick < 0){
			tick = 0;
		}
		while (currentTick < tick){
			tick(0);
		}
		if (currentTick > tick){
			int[][] stone = keystones.get(tick/10).grid;
			for (int row = 0; row < rows; row++){
				System.arraycopy(stone[row], 0, grid[row], 0, cols);
			}
			currentTick = 10*(tick/10);
			while (currentTick != tick){
				tick(0);
			}
		}
		currentTick = tick;
	}

	
	

	public void tick(int ruleset){
		updateKeystonesChecksums();

		int[][] bufferGrid = new int[rows][cols];
		for (int row = 0; row<rows; row++){
			for (int col = 0; col<cols; col++){
				int myType = getItem(row, col);
				int p1Neighbors = 0;
				int p2Neighbors = 0;
				for (int dRow = -1; dRow<2; dRow++){
					for (int dCol = -1; dCol<2; dCol++){
						if (dRow == 0 && dCol == 0){
							continue;
						}
						int val = getItem(row+dRow, col+dCol);
						if (val == 1){
							p1Neighbors++;
						} else if (val == 2){
							p2Neighbors++;
						}
					}
				}
				if (ruleset == 0){
					bufferGrid[row][col] = applyRules1(p1Neighbors, p2Neighbors, myType);
				} else if (ruleset == 1) {
					bufferGrid[row][col] = applyRules2(p1Neighbors, p2Neighbors, myType);
				}
			}
		}
		for (int y = 0; y < rows; y++) {
			System.arraycopy(bufferGrid[y], 0, grid[y], 0, cols);
		}
		currentTick++;

		updateKeystonesChecksums();
		if (checksums.size()==currentTick+1){
			checkForEnding();
		}
	}

	private void checkForEnding() {
		int checksumMatch = getRepitition();
		if (checksumMatch > -1) {
			int[][] backupGrid = new int[rows][cols];
			for (int row = 0; row < rows; row++){
				System.arraycopy(grid[row], 0, backupGrid[row], 0, cols);
			}
			int backupTick = currentTick;

			loadTick(checksumMatch);

			boolean match = true;
			for (int row = 0; row < rows; row++){
				for (int col = 0; col < cols; col++){
					if (backupGrid[row][col] != grid[row][col]){
						match = false;
					}
				}
			}
			if (match){
				System.out.println("cycle "+(backupTick-currentTick));
				finished = true;
				redAmount=0;
				bluAmount=0;
				for (int row = 0; row < rows; row++){
					for (int col = 0; col < cols; col++){
						if (grid[row][col]==1){
							bluAmount++;
						} else if (grid[row][col]==2){
							redAmount++;
						}
					}
				}
				if (redAmount > bluAmount){
					winner = 2;
				} else if (bluAmount > redAmount){
					winner = 1;
				} else {
					winner = 0;
					
				}
			}
		}
	}

	private void updateKeystonesChecksums() {
		int thisChecksum = getChecksum();
		while (checksums.size()<=currentTick){
			checksums.add(null);
		}
		checksums.set(currentTick, thisChecksum);

		if (currentTick%10 == 0){
			int[][] gridClone = new int[rows][cols];
			for (int row = 0; row < rows; row++){
				System.arraycopy(grid[row], 0, gridClone[row], 0, cols);
			}
			while (keystones.size()<=currentTick/10){
				keystones.add(null);
			}
			keystones.set(currentTick/10, new int2d(gridClone));
		}
	}

	private int getRepitition(){
		int thisChecksum = getChecksum();
		for (int tick = 0; tick<checksums.size(); tick++){
			if (tick >= currentTick){
				break;
			}
			if (checksums.get(tick) == thisChecksum){
				return tick;
			}

		}
		return -1;
	}

	/** Applies the 1st ruleset 
	 * As dictated by John Conway
	*/
	private int applyRules1(int p1Neighbor, int p2Neighbor, int state){
		//overpopulation
		if (p1Neighbor > 3 || p2Neighbor > 3){
			return 0;
		}
		//underpopulation
		if (p1Neighbor < 2 && p2Neighbor < 2){
			return 0;
		}
		//standoff
		if (p1Neighbor == p2Neighbor){
			return state;
		}
		//p1 attack
		if (p1Neighbor == 3){
			if (state == 2){
				return 0;
			} else {
				return 1;
			}
		}
		//p2 attack
		if (p2Neighbor == 3){
			if (state == 1){
				return 0;
			} else {
				return 2;
			}
		}
		//p1 defaulting
		if (p1Neighbor == 2){
			if (state == 1){
				return 1;
			} else {
				return 0;
			}
		}
		//p2 defaulting
		if (p2Neighbor == 2){
			if (state == 2){
				return 2;
			} else {
				return 0;
			}
		}
		System.out.println("BAD BAD BAD");
		return -1;
	}
	/** Applies the 2nd ruleset 
	 * tbd
	*/
	private int applyRules2(int p1Neighbor, int p2Neighbor, int state){
		//over / under pop
		if ((p1Neighbor > 3 || p1Neighbor < 2) && (p2Neighbor > 3 && p2Neighbor < 2)){
			return 0;
		}
		
		if (p1Neighbor == p2Neighbor){
			return state;
		}
		if (p1Neighbor == 3){
			if (state == 2){
				return 0;
			} else {
				return 1;
			}
		}
		if (p2Neighbor == 3){
			if (state == 1){
				return 0;
			} else {
				return 2;
			}
		}
		if (p1Neighbor == 2){
			if (state == 1){
				return 1;
			} else {
				return 0;
			}
		}
		if (p2Neighbor == 2){
			if (state == 2){
				return 2;
			} else {
				return 0;
			}
		}
		return 0;
	}

	public void clear(){
		for (int[] row : grid) {
			for (int col = 0; col < cols; col++) {
				row[col] = 0;
			}
		}
		currentTick = 0;
		keystones.clear();
		checksums.clear();
	}
	public void reset(){
		loadTick(0);
		keystones.clear();
		checksums.clear();
	}

	private int getChecksum(){
		int output = 0;
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < cols; col++){
				output = output*31 +grid[row][col];
			}
		}
		return output;
	}
	private int mod(int a, int b){
		return (a % b + b) % b;
	}
	@Override
	public String toString(){
		StringBuilder output = new StringBuilder();
		output
		.append("Tick: ")
		.append(currentTick)
		.append(" ")
		.append(rows)
		.append("x")
		.append(cols)
		.append("\n");
		for (int i = 0; i<cols; i++){
			output.append('-');
		}
		output.append("+\n");
		for (int[] row : grid){
			output.append('|');
			for (int item : row){
				output.append((char)
					switch (item) {
						case 0 -> ' ';
						case 1 -> '#';
						case 2 -> '*';
						default -> item;
					}
				);
			}
			output.append("|\n");
		}
		output.append('+');
		for (int i = 0; i<cols; i++){
			output.append('-');
		}
		output.append('+');
		return output.toString();
	}
	
}