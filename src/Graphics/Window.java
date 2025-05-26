package Graphics;

import Logic.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Window {
	private final JFrame frame;

	private final GamePanel gamePanel;

	public boolean play = false;
	public boolean next = false;
	public boolean prev = false;
	public boolean reset = false;
	public boolean clear = false;

	private final JButton[] buttons;

	public Window(Conways game, Overlay overlay, int scale, int border, int barWidth){
		int gameWidth  = game.getCols() * scale;
		int gameHeight = game.getRows() * scale;

		int width  = 8  + border + gameWidth  + border + barWidth + border + 8;
		int height = 31 + border + gameHeight + border + 8;

		this.frame = new JFrame("Conway's Game of Life");
		this.frame.setBounds(0, 0, width, height);
		this.frame.getContentPane().setBackground(Color.BLACK);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		this.frame.setLayout(null);
		this.frame.setResizable(false);
		

		this.gamePanel = new GamePanel(game, overlay, scale, this);
		this.gamePanel.setBounds(border, border, gameWidth, gameHeight);
		this.gamePanel.setBackground(Color.CYAN);
		this.frame.add(gamePanel);

		
		buttons = new JButton[5];
		buttons[0] = new JButton("Play");
		buttons[1] = new JButton("Next");
		buttons[2] = new JButton("Prev");
		buttons[3] = new JButton("Reset");
		buttons[4] = new JButton("Clear");

		int buttonHeight = gameHeight/buttons.length;

		for (int i = 0; i < buttons.length; i++){
			buttons[i].setBounds(border*2 + gameWidth, border + buttonHeight * i, barWidth, buttonHeight);
			buttons[i].setBackground(Color.white);
			this.frame.add(buttons[i]);
		}
		
		buttons[0].addActionListener(
			(ActionEvent e) -> {
				this.play = true;
			}
		);
		buttons[1].addActionListener(
			(ActionEvent e) -> {
				this.next = true;
			}
		);
		buttons[2].addActionListener(
			(ActionEvent e) -> {
				this.prev = true;
			}
		);
		buttons[3].addActionListener(
			(ActionEvent e) -> {
				this.reset = true;
			}
		);
		buttons[4].addActionListener(
			(ActionEvent e) -> {
				this.clear = true;
			}
		);
		gamePanel.requestFocusInWindow();
	}

	public void render(){
		frame.repaint();
		
	}
}