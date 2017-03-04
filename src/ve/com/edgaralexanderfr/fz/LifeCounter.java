package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ve.com.edgaralexanderfr.game.GameObject;

public class LifeCounter extends GameObject {
	Font font   = new Font("Arial", Font.BOLD, 24);
	Color color = new Color(120, 33, 20);

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(color);
	}

	@Override
	public void start () {
		setTextOffsetX(-20);
		setTextOffsetY(-40);
		setTextPivot(PIVOT_BOTTOM_RIGHT);
		x      = configi("windowWidth");
		y      = configi("windowHeight");
		zIndex = 500;
	}

	@Override
	public void update () {
		text = "LIFE: 100";
	}
}
