package Graphics;

import java.awt.*;
import java.awt.event.*;

public class BasicButton extends Button {
    private String default_text;
    private String clicked_text;
    private boolean clicked = false;
    private ActionListener listener;

    public BasicButton() {
        this("Button");
    }

    public BasicButton(String text) {
        this(text, 0, 0);
    }

    public BasicButton(String text, int x_pos, int y_pos) {
        this(text, 20, 20, x_pos, y_pos);
    }

    public BasicButton(String text, int x_size, int y_size, int x_pos, int y_pos) {
        super(text);
        this.default_text = text;
        this.setBounds(x_pos, y_pos, x_size, y_size);
        this.setBackground(Color.ORANGE);
		this.listener = ((ActionEvent e) -> {});
		this.addActionListener((ActionEvent E) -> {
            clicked = !clicked;
            listener.actionPerformed(E);
        });
    }


    public void setClickedText(String t) {
        clicked_text = t;
    }

    public String getClickedText() {
        return clicked_text;
    }

    public void setDefaultText(String t) {
        default_text = t;
    }

    public String getDefaultText() {
        return default_text;
    }

    public void setActionListener(ActionListener l) {
        listener = l;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
    }
}
