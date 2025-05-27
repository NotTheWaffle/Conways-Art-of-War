package Graphics;

import Logic.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private final BufferedImage image;
	private final Conways game;
	private final Overlay overlay;
	private final int scale;
	private final int width;
	private final int height;

	private int mouseX;
	private int mouseY;

	private int prevX;
	private int prevY;
	private byte brush;

	public GamePanel(Conways game, Overlay overlay, int scale, Window window){
		this.game  = game;
		this.overlay = overlay;
		this.scale = scale;
		this.width  = game.getCols() * scale;
		this.height = game.getRows() * scale;
		this.image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		this.setFocusable(true);

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
				mouseX = x;
				mouseY = y;
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
			public void mouseMoved(MouseEvent e){
				int x = e.getX()/scale;
				int y = e.getY()/scale;
				mouseX = x;
				mouseY = y;
			}
		});
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e){
				System.out.print(e.getKeyCode()+" ");
				// 81 87 69
				// 65 83 68
				//  q  w  e
				// a  s  d
				// w & s flip v
				// a & d flip h
				// q & e rotate (c)cw
				//37 left
				//39 right
				//38 up
				//40 down
			}
			@Override
			public void keyReleased(KeyEvent e){
				//System.out.print(e.getKeyChar());
			}
			@Override
			public void keyTyped(KeyEvent e){
				//System.out.print(e.getKeyChar());
			}
		});
		
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		updateImage();
		g2d.drawImage(image, 0, 0, null);
	}

	public int getMouseX(){
		return mouseX;
	}
	public int getMouseY(){
		return mouseY;
	}

	public void updateImage(){
		for (int row = 0; row < game.getRows(); row++){
			for (int col = 0; col < game.getCols(); col++){
				int color;
				int val = overlay.getItem(row, col);
				if (val == 0){
					val = game.getItem(row, col);
				}
				color = switch (val){
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