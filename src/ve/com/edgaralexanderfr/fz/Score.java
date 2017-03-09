package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ve.com.edgaralexanderfr.game.GameObject;

public class Score extends GameObject {
	Font font   = new Font("Arial", Font.BOLD, 24);
	Color color = new Color(94, 87, 0);
	short score = 0;

	public int getScore () {
		return score;
	}

	public void setScore (byte score) {
		if (score >= 0) {
			this.score = score;
		}
	}

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(color);
	}

	@Override
	public void start () {
		setTextOffsetX(-20);
		setTextOffsetY(10);
		setTextPivot(PIVOT_TOP_RIGHT);
		x      = configi("windowWidth");
		zIndex = 500;
	}

	@Override
	public void update () {
		text = "SCORE: " + score;
	}

	public void increase () {
		score++;
	}
}
