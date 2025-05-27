import Graphics.Window;
import Logic.*;
import java.io.File;

public class Main{
	public static void main(String[] args) {
		String root = "C:/Users/andre/Documents/VS Code Programs/Random/Conways Art of War/src/Designs/";
		String[] designs = {"gliders", "generators", "oscillators", "still"};
		for (String design : designs){
			Designs.loadFile(new File(root+design+".dat"));
		}

		int size = 32;
		Conways game = new Conways(size, size);
		Overlay overlay = new Overlay(size, size);
		overlay.setItem(0, 0, 2);
		int scale = 1024/size;
		Cursor cursor = new Cursor(0, 0, new Design(new int[1][1], "nothing", "aint"));
		Window window = new Window(game, overlay, scale, 4, 70);
		window.render();
		boolean playing = false;
		
		while (true){
			cursor.updateOverlay(overlay, window.getMouseX(), window.getMouseY(), Designs.getDesign("GLDR"));
			if (window.play){
				playing = !playing;
				window.play = false;
			}
			if (window.next){
				playing = false;
				window.next = false;
				game.tick();
			}
			if (window.prev){
				playing = false;
				window.prev = false;
				game.loadTick(game.getCurrentTick()-1);
			}
			if (window.reset){
				playing = false;
				window.reset = false;
				game.reset();
			}
			if (window.clear){
				playing = false;
				window.clear = false;
				game.clear();
			}
			if (playing){
				game.tick();
			}
			window.render();
			sleep(16);
		}

	}
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e){}
	}
}