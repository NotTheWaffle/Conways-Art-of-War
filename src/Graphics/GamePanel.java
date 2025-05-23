package Graphics;

import Logic.Conways;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private final BufferedImage frame;
	private Conways game;
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
		this.frame = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				int x = e.getX()/scale;
				int y = e.getY()/scale;
				brush = (byte) (1 - game.getItem(y, x));
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

		updateFrame();
		g2d.drawImage(frame, 0, 0, null);
	}

	public void updateGame(Conways game){
		this.game = game;
	}

	public void updateFrame(){
		for (int row = 0; row < game.getRows(); row++){
			for (int col = 0; col < game.getCols(); col++){
				Color color = switch (game.getItem(row, col)){
					case 0 -> Color.WHITE;
					case 1 -> Color.BLACK;
					default -> Color.GREEN;
				};
				for (int sx = 0; sx < scale; sx++){
					for (int sy = 0; sy < scale; sy++){
						frame.setRGB(col*scale+sx, row*scale+sy, color.getRGB());
					}
				}
			}
		}
	}
}