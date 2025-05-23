
package Graphics;

import java.awt.Color;
import javax.swing.JLabel;

public class BasicLabel extends JLabel{
	public BasicLabel(){
		this("Label");
	}
	public BasicLabel(String text){
		this(text, 0, 0);
	}
	public BasicLabel(String text, int x_pos, int y_pos){
		this(text, x_pos, y_pos, 20, 20);
	}
	public BasicLabel(String text, int width, int height, int x_pos, int y_pos){
		super(text);
        this.setBounds(x_pos, y_pos, width, height);
		this.setBackground(Color.BLACK);
	}
	//setlabel
}
