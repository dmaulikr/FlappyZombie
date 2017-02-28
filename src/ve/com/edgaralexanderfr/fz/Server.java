package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ve.com.edgaralexanderfr.game.GameObject;

public class Server extends GameObject {
	Font font = new Font("Arial", Font.BOLD, 24);

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(Color.BLACK);
	}

	@Override
	public void start () {
		setTextOffsetX(-20);
		setTextOffsetY(-40);
		setTextPivot(PIVOT_BOTTOM_RIGHT);
		x      = configi("windowWidth");
		y      = configi("windowHeight");
		zIndex = 3;
	}

	@Override
	public void update () {
		text = "127.0.0.1:55555";
	}
}
