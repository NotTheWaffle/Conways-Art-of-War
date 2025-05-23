package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Designs {
	private static ArrayList<Design> designs = new ArrayList<>();

	public static void loadDesigns(File file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			System.out.println("Reading file "+file.getName());
			readLoop:
			while (true){

				String name = reader.readLine();
				if (name == null){
					break;
				}

				String[] metaData = reader.readLine().split(" ");
					String id = metaData[0];
					int rows = Integer.parseInt(metaData[1]);
					int cols = Integer.parseInt(metaData[2]);
				
				char[][] design = new char[rows][cols];
				for (int row = 0; row < rows; row++){
					//i could do some .tochararray and some system.arraycopy to make this faster, but idgaf
					String line = reader.readLine();
					if (line.length() == 0){
						System.out.println("  Malform Design: ("+id+") "+name);
						continue readLoop;
					}
					for (int col = 0; col < cols; col++){
						if (col >= line.length()){
							design[row][col] = ' ';
						} else {
							design[row][col] = line.charAt(col);
						}
					}
				}
				designs.add(new Design(design, name, id));
				System.out.println("  Design Added  : ("+id+") "+name);
				reader.readLine();
			}


			reader.close();
		} catch (IOException e){
			System.out.println("File "+file.getName()+" not found");
		}
	}
	
	public static Design getDesign(String id){
		for (Design design : designs){
			if (design.getId().equals(id)){
				return design;
			}
		}
		return null;
	}

	public static void addShades(Conways game, int x, int y, int width, int height, int type){
		//y 4n+1 - 41 height
		//x 3n+1 - 40 width
		height = 4*((height-1)/4)+1;
		width  = 3*((width -1)/3)+1;
		for (int y1 = 1; y1<height; y1+=2){
			for (int x1 = 1; x1<(width-1); x1+=1){
				game.setItem(y1+y, x1+x, type);
			}
		}
		for (int y1 = 2; y1<height; y1+=4){
			game.setItem(y1+y, x, type);
			game.setItem(y1+y, width-1+x, type);
		}
		for (int x1 = 3; x1<width-1; x1+=3){
			game.setItem(y, x1+x, type);
			game.setItem(height-1+y, x1+x, type);
		}
	}
	public static void addFull  (Conways game, int x, int y, int width, int height, int type){
		for (int sx = x; sx<x+width; sx++){
			for (int sy = y; sy<y+height; sy++){
				game.setItem(sy, sx, type);
			}
		}
	}
	
	public static void addDesign(Conways game, int x, int y, boolean flipx, boolean flipy, boolean flipXY, String[] design, int type){
		for (int row = 0; row < design.length; row++){
			for (int col = 0; col < design[row].length(); col++){
				int rowAdj = row;
				int colAdj = col;
				if (flipy){
					rowAdj = design.length-1-row;
				}
				if (flipx){
					colAdj = design[rowAdj].length()-1-col;
				}
				if (flipXY){
					int temp = rowAdj;
					rowAdj = colAdj;
					colAdj = temp;
				}
				if (design[row].charAt(col) != ' '){
					game.setItem(rowAdj+y, colAdj+x, type);
				}
			}
		}
	}
}

