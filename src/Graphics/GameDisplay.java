package Graphics;

import Logic.Conways;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameDisplay extends JPanel {
    private final BufferedImage frame;
    private final int[][] cells;
    private final int scale;
    private final int x_offset, y_offset;
    private final Conways conways;
	public int paintType;
	private int prevx;
	private int prevy;


    public GameDisplay(int gameWidth, int gameHeight, int x_pos, int y_pos, int[][] cells, Conways c, Display d) {
        conways = c;
        this.cells = cells;
        this.scale = (gameHeight)/cells.length; //this should also be equal to gameWidth/this.cols (with a bit of rounding errors)
        this.x_offset = x_pos;
        this.y_offset = y_pos;
		this.setSize(gameWidth, gameHeight);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				int cs_x = p.x / scale;
				int cs_y = p.y / scale;
				prevx = cs_x;
				prevy = cs_y;
				if (conways.getItem(cs_y, cs_x) == 0){
					paintType = 1;
				} else {
					paintType = 0;
				}
				conways.updateItem(cs_y, cs_x);
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				int cs_x = p.x / scale;
				int cs_y = p.y / scale;
				int p_x = prevx;
				int p_y = prevy;
				prevx = cs_x;
				prevy = cs_y;
				conways.updateItem(cs_y, cs_x, paintType);
				while (cs_x != p_x || cs_y != p_y){
					if (cs_x != p_x){
						cs_x += Math.signum(p_x-cs_x);
					}
					if (cs_y != p_y){
						cs_y += Math.signum(p_y-cs_y);
					}
					conways.updateItem(cs_y, cs_x, paintType);
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {}
		});

        frame = new BufferedImage(gameWidth, gameHeight, BufferedImage.TYPE_INT_RGB);

        this.setBackground(Color.GREEN);
    }	

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(frame, x_offset, y_offset, null);
    }

    public void drawFrame() {	
        for (int y=0;y<cells.length;y++) {
            for (int x=0;x<cells[y].length;x++) {
                drawCell(x, y);
            }
        }
    }

    public void drawCell(int x, int y) {
        int item = cells[y][x];

        Color blank_color = ((x == cells[0].length / 2) || (x+1 == cells[0].length / 2)) ?
                                (Color.GRAY) :
                            (x % 2 == y%2 ?
                                Color.LIGHT_GRAY 
                            :
                                Color.WHITE);

        Color color = switch (item) {
            case 0 -> blank_color;
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            default -> Color.GREEN;
        };
		
        for (int sx = 0; sx < scale; sx++) {
            for (int sy = 0; sy < scale; sy++) {
                frame.setRGB(x * scale + sx, y * scale + sy, color.getRGB());
            }
        }
    }
}
