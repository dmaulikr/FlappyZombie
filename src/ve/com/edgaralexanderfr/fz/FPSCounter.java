package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ve.com.edgaralexanderfr.game.GameObject;

public class FPSCounter extends GameObject {
	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.setColor(Color.RED);
	}

	@Override
	public void start () {
		this.setTextOffsetX(5.0f);
		this.setTextOffsetY(5.0f);
		this.setTextPivot(PIVOT_TOP_LEFT);
	}

	@Override
	public void update () {
		this.text = this.game.getRenderer().getFPS() + " FPS";
	}
}
