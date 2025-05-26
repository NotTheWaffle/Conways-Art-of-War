import Graphics.Window;
import Logic.*;
import java.io.File;

public class Main{
	public static void main(String[] args) {
		String root = "C:/Users/andre/Documents/VS Code Programs/Random/Conways Art of War/src/Designs/";
		String[] designs = {"gliders", "generators", "oscillators", "still"};
		for (String design : designs){
			Designs.loadDesigns(new File(root+design+".dat"));
		}
		Conways game = new Conways(512, 512);
		Window window = new Window(game, 2, 5, 50);
		window.render();
	}
}