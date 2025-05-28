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
		int scale = 1024/size;
		Cursor cursor = new Cursor(overlay, 0, 0, Designs.getDesign("LWSS"));
		Window window = new Window(game, overlay, scale, 4, 70, cursor);
		window.render();
		boolean playing = false;
		boolean stampMode = false;
		
		while (true){
			if (window.toggleStamp){
				stampMode = !stampMode;
				window.stamp = stampMode;
				if (!stampMode){
					cursor.erase();
				}
				window.toggleStamp = false;
			}
			if (stampMode){
				if (window.flipX){
					cursor.flipHDesign();
					window.flipX = false;
				}
				if (window.flipY){
					cursor.flipVDesign();
					window.flipY = false;
				}
				if (window.ccwRot){
					cursor.ccwDesign();
					window.ccwRot = false;
				}
				if (window.cwRot){
					cursor.cwDesign();
					window.cwRot = false;
				}
				if (window.prevDesi){
					Designs.prevDesign();
					cursor.newDesign(Designs.getDesignNum());
					window.prevDesi = false;
				}
				if (window.nextDesi){
					Designs.nextDesign();
					cursor.newDesign(Designs.getDesignNum());
					window.nextDesi = false;
				}
				if (window.prevFile){
					Designs.prevFile();
					cursor.newDesign(Designs.getDesignNum());
					window.prevFile = false;
				}
				if (window.nextFile){
					Designs.nextFile();
					cursor.newDesign(Designs.getDesignNum());
					window.nextFile = false;
				}
				cursor.erase();
				cursor.draw(window.getMouseX(), window.getMouseY());
			}
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