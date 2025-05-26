package Logic;

public class Design {
	private final char[][] design;
	private final String name;
	private final String id;

	public Design(char[][] design, String name, String id){
		this.design = design;
		this.name = name;
		this.id = id;
	}
	
	public char[][] get(){
		return design;
	}
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}

	@Override
	public String toString(){
		String designString = "";
		for (char[] row : design){
			for (char item : row){
				designString += item;
			}
			designString += "\n";
		}
		return (name+" ("+id+") "+design.length+"x"+design[0].length+"\n"+designString);
	}

	@Override
	public int hashCode(){
		int hash = 0;
		for (char[] design1 : design) {
			for (int col = 0; col < design[0].length; col++) {
				hash = hash * 31 + design1[col];
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
		char[][] altDesign = d.get();
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