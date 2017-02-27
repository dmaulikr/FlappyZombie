package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ve.com.edgaralexanderfr.game.GameObject;

public class Dog extends GameObject {
	private byte xd = 1;
	private byte yd = 1;

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
	}

	@Override
	public void start () {
		try {
			this.game.instantiate(FPSCounter.class, 0.0f, 0.0f, (short) 1, "");
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		this.setTextOffsetY(-80.0f);
		this.text = "Bark.. bark!";
		this.x    = 640 / 2;
		this.y    = 360 / 2;
	}

	@Override
	public void update () {
		this.x += xd * 200.0f * this.game.getRenderer().deltaTime();
		this.y += yd * 200.0f * this.game.getRenderer().deltaTime();

		if (this.x >= 640.0f - 64.0f) {
			this.xd = -1;
		} else 
		if (this.x <= 64.0f) {
			this.xd = 1;
		}

		if (this.y >= 360.0f - 64.0f) {
			this.yd = -1;
		} else 
		if (this.y <= 64.0f) {
			this.yd = 1;
		}
	}
}
