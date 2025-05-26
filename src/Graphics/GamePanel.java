package Graphics;

import Logic.Conways;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private final BufferedImage image;
	private final Conways game;
	private final int scale;
	private final int width;
	private final int height;

	private int prevX;
	private int prevY;
	private byte brush;

	public GamePanel(Conways game, int scale, Window window){
		this.game  = game;
		this.scale = scale;
		this.width  = game.getCols() * scale;
		this.height = game.getRows() * scale;
		this.image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				int x = e.getX()/scale;
				int y = e.getY()/scale;
				if (game.getItem(y, x) == 0){
					brush = (byte) 1;
				} else {
					brush = (byte) 0;
				}
				//brush = (byte) (1 - game.getItem(y, x));
				game.updateItem(y, x, brush);
				window.render();

				prevX = x;
				prevY = y;
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e){
				int x = e.getX()/scale;
				int y = e.getY()/scale;
				int bx = x;
				int by = y;
				int dx = Math.abs(prevX - x);
				int dy = Math.abs(prevY - y);
				int sx = x < prevX ? 1 : -1;
				int sy = y < prevY ? 1 : -1;
				int err = dx - dy;

				while (true) {
					game.updateItem(y, x, brush);
					if (x == prevX && y == prevY) 
						break;
					int e2 = 2 * err;
					if (e2 > -dy) {
						err -= dy;
						x += sx;
					}
					if (e2 < dx) {
						err += dx;
						y += sy;
					}
				}
				prevX = bx;
				prevY = by;
				window.render();
			}
			@Override
			public void mouseMoved(MouseEvent e){}
		});
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		updateImage();
		g2d.drawImage(image, 0, 0, null);
	}

	public void updateImage(){
		for (int row = 0; row < game.getRows(); row++){
			for (int col = 0; col < game.getCols(); col++){
				int color = switch (game.getItem(row, col)){
					case 0 -> {
						if (col == 0 || col == game.getCols()/2 || col == game.getCols()/2-1 || col == game.getCols()-1){
							if ((row + col) % 2 == 1){
								yield 0xff_80_80_80;
							} else {
								yield 0xff_60_60_60;
							}
						}
						if ((row + col) % 2 == 1){
							yield 0xff_ff_ff_ff;
						} else {
							yield 0xff_c0_c0_c0;
						}
					}
					case 1 -> 	{yield 0xff_00_00_ff;}	//blue
					case 2 -> 	{yield 0xff_ff_00_00;}	//red
					default -> 	{yield 0xff_00_ff_00;}	//green
				};

				for (int sx = col*scale; sx < (col+1)*scale; sx++){
					for (int sy = row*scale; sy < (row+1)*scale; sy++){
						image.setRGB(sx, sy, color);
					}
				}
			}
		}
	}
}