package Graphics;

import Logic.Conways;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Display extends JFrame {
    private final GameDisplay gameDisplay;

    public final BasicButton start_button;
	public final BasicButton tick_button;
	public final BasicButton prev_button;
    public final BasicButton reset_button;
    public final BasicButton clear_button;
	public final JSlider fpsSlider;

    public final JTextField display_field;

    public boolean started = false;
	public boolean tick = false;
	public boolean prev = false;
    public boolean reset = false;
    public boolean clear = false;

    public Display(int gameWidth, int gameHeight, int border, int sideBarWidth, int topBarHeight, int[][] cells, Conways conways) {
		int width = 	gameWidth+	sideBarWidth+	border*3 +7*2; 		// |game|b| <-- three "|" borders, and game and bar
		int height = 	gameHeight+	topBarHeight+	border*3 +7*2+24; 	// add some borders 
		int sideButtonHeight = gameHeight/5;
		int topButtonWidth = gameWidth/2;

        this.setTitle("Conway's The Art of War");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        this.setSize(width,height);
        this.setResizable(false);
		this.setVisible(true);
		
		this.display_field = new JTextField("Running ...");
		display_field.setBounds(border+topButtonWidth, border, topButtonWidth, topBarHeight);

		this.fpsSlider = new JSlider(1,120, 60);
		this.fpsSlider.setBounds(border, border, topButtonWidth, topBarHeight);

		this.start_button = new BasicButton("Start", sideBarWidth, sideButtonHeight, gameWidth+2*border, topBarHeight+border*2+sideButtonHeight*0);
		this.tick_button =  new BasicButton("Next" , sideBarWidth, sideButtonHeight, gameWidth+2*border, topBarHeight+border*2+sideButtonHeight*1);
		this.prev_button =  new BasicButton("Prev" , sideBarWidth, sideButtonHeight, gameWidth+2*border, topBarHeight+border*2+sideButtonHeight*2);
		this.reset_button = new BasicButton("Reset", sideBarWidth, sideButtonHeight, gameWidth+2*border, topBarHeight+border*2+sideButtonHeight*3);
		this.clear_button = new BasicButton("Clear", sideBarWidth, sideButtonHeight, gameWidth+2*border, topBarHeight+border*2+sideButtonHeight*4);
		
		this.gameDisplay = new GameDisplay(gameWidth, gameHeight, 0, 0, cells, conways, this);
		this.gameDisplay.setLocation(border, border*2+topBarHeight);
		this.display_field.setEditable(false);

		this.add(this.start_button);
		this.add(this.tick_button);
		this.add(this.prev_button);
		this.add(this.reset_button);
		this.add(this.clear_button);
		this.add(this.fpsSlider);

		this.add(this.display_field);
		
		this.add(this.gameDisplay);

		this.start_button.setActionListener((ActionEvent e) -> {
			if (started){
				started = false;
				this.start_button.setLabel("Play");
			} else {
				started = true;
				this.start_button.setLabel("Pause");
			}
		   
        });
	    this.tick_button.setActionListener((ActionEvent e) -> {
			started = false;
			tick = true;
            this.start_button.setLabel("Play");
	    });
	    this.prev_button.setActionListener((ActionEvent e) -> {
			started = false;
			prev = true;
            this.start_button.setLabel("Play");
	    });
        this.reset_button.setActionListener((ActionEvent e) -> {
            started = false;
            reset = true;
            this.start_button.setLabel("Play");
        });
        this.clear_button.setActionListener((ActionEvent e) -> {
            started = false;
            clear = true;
            this.start_button.setLabel("Play");
        });

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        gameDisplay.drawFrame();
    }


}
