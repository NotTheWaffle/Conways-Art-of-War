import Graphics.Display;
import Logic.*;
import java.awt.*;

public class Main{
	public static void main(String[] args) {
		Conways logic = new Conways(80,160);

		//Prebuilt.addDesign(logic, 0, 1, true, true, false, Prebuilt.gliderGun, 1);

		//for (int i = 0; i < 90; i+=3){
		//	logic.setItem(i, 77, 1);
		//	logic.setItem(i, 78, 1);
		//	logic.setItem(i+1,77, 1);
		//	logic.setItem(i+1, 78, 1);
		//}
		//Prebuilt.addShades(logic, 2, 0, 75, 90, 1);
		//Prebuilt.addDesign(logic, 0, 0 , true, false, false, Prebuilt.gliderGun, 1);
		//Prebuilt.addDesign(logic, 151, 54 , false, true, false, Prebuilt.gliderGun, 2);
		Designs.addDesign(logic, 100, 5, false, false, false, Designs.hwss, 1);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() * .75;

		int gameWidth = (int) width;
		int gameHeight = gameWidth*logic.rows/logic.cols;
		int sideBarWidth = 50;
		int topBarWidth = 20;
		int border = 5;
		int fps;

		Display display = new Display(gameWidth, gameHeight, border, sideBarWidth, topBarWidth, logic.getBoard(), logic);

		display.repaint();
		while (display.isVisible()) {
			fps = display.fpsSlider.getValue();
			if (logic.finished){
				display.start_button.setLabel("Play");
				display.started = false;
				logic.finished = false;
				switch (logic.winner) {
					case 1 -> display.display_field.setText("Blue Wins! "+logic.bluAmount+" to "+logic.redAmount);
					case 2 -> display.display_field.setText("Red Wins! "+logic.redAmount+" to "+logic.bluAmount);
					default -> display.display_field.setText("Tie Blue:"+logic.bluAmount+" to Red;"+logic.redAmount);
				}
			}
			if (display.started){
				logic.tick(0);
				display.prev_button.setEnabled(true);
			} else if (display.tick){
				logic.tick(0);
				display.tick = false;
				display.prev_button.setEnabled(true);
			} else if (display.prev){
				logic.loadTick(logic.getCurrentTick()-1);
				if (logic.getCurrentTick()==0){
					display.prev_button.setEnabled(false);
				}
				display.prev = false;
				display.start_button.setEnabled(true);
				display.tick_button.setEnabled(true);
			} else if (display.reset) {
				logic.reset();
				display.display_field.setText("Running ...");
				display.reset = false;
				display.start_button.setEnabled(true);
				display.tick_button.setEnabled(true);
				display.prev_button.setEnabled(false);
			} else if (display.clear) {
				logic.clear();
				display.display_field.setText("Running ...");
				display.clear = false;
				display.start_button.setEnabled(true);
				display.tick_button.setEnabled(true);
				display.prev_button.setEnabled(false);
			}
			display.repaint();
			sleep(1000 / fps);
		}
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e);
		}
	}
}