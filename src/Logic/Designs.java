package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Designs {
	private static ArrayList<Design> designs;

	public static void loadDesigns(File file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			
			String name = reader.readLine();
			String[] metaData = reader.readLine().split(" ");
			String id = metaData[0];
			int rows = Integer.parseInt(metaData[1]);
			int cols = Integer.parseInt(metaData[2]);
			Design design = new Design(rows);
			for (int row = 0; row < rows; row++){
				//i could do some .tochararray and some system.arraycopy to make this faster, but idgaf
				String line = reader.readLine();
				char[] curRow = new char[cols];
				for (int col = 0; col < cols; col++){
					if (col >= line.length()){
						curRow[col] = ' ';
					} else {
						curRow[col] = line.charAt(col);
					}
				}
				design.updateRow(row, curRow);
			}
			designs.add(design);



			reader.close();
		} catch (IOException e){}
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

