package Logic;

public class Design {
	private final int[][] design;
	private final String name;
	private final String id;

	public Design(int[][] design, String name, String id){
		this.design = design;
		this.name = name;
		this.id = id;
	}
	
	public int[][] get(){
		return design;
	}
	
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}

	public int getItem(int row, int col){
		return design[row][col];
	}
	public void setItem(int row, int col, int val){
		design[row][col] = val;
	}

	@Override
	public String toString(){
		String designString = "";
		for (int[] row : design){
			for (int item : row){
				designString += item;
			}
			designString += "\n";
		}
		return (name+" ("+id+") "+design.length+"x"+design[0].length+"\n"+designString);
	}

	@Override
	public int hashCode(){
		int hash = 0;
		for (int[] row : design) {
			for (int col = 0; col < row.length; col++) {
				hash = hash * 31 + row[col];
			}
		}
		return hash;
	}

	@Override
	public boolean equals(Object o){
		if (! (o instanceof Design)){
			return false;
		}
		Design d = (Design) o;
		if (!d.getName().equals(this.name) || !d.getId().equals(this.id)){
			return false;
		}
		//same name, id
		int[][] altDesign = d.get();
		if (altDesign.length != design.length || altDesign[0].length != design[0].length){
			return false;
		}
		//same dimensions
		for (int i = 0; i < design.length; i++){
			for (int j = 0; j < design[0].length; j++){
				if (design[i][j] != altDesign[i][j]){
					return false;
				}
			}
		}
		//identical
		return true;
	}
}