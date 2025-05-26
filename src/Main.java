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

		int size = 256;
		Conways game = new Conways(size, size);
		int scale = 1024/size;
		Window window = new Window(game, scale, 4, 70);
		window.render();
		boolean playing = false;

		while (true){
			if (window.play){
				playing = !playing;
				window.play = false;
			}
			if (window.next){
				System.out.println("my dih");
				playing = false;
				window.next = false;
				game.tick();
				window.render();
			}
			if (window.prev){
				playing = false;
				window.prev = false;
			}
			if (window.clear){
				playing = false;
				window.clear = false;
			}
			if (window.reset){
				playing = false;
				window.reset = false;
			}
			sleep(100);
		}

	}
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e){}
	}
}