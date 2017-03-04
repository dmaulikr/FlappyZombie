package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;

public class Pause extends GameObject {
	Font font      = new Font("Arial", Font.BOLD, 24);
	boolean paused = false;
	Image pausebg  = null;

	public boolean isPaused () {
		return paused;
	}

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(Color.WHITE);
	}

	@Override
	public void start () {
		x       = configi("windowWidth")  / 2;
		y       = configi("windowHeight") / 2;
		zIndex  = 501;
		pausebg = resources().get("pausebg", Image.class);
	}

	@Override
	public void update () {
		if (input().getKeyUp(configi("pauseKeyCode1")) || input().getKeyUp(configi("pauseKeyCode2"))) {
			togglePause();
		}
	}

	public void pause () {
		game.pauseScheduledRoutines(true);
		paused        = true;
		text          = "PAUSE";
		spriteTexture = pausebg;
	}

	public void unpause () {
		input().reset();
		game.pauseScheduledRoutines(false);
		paused        = false;
		text          = null;
		spriteTexture = null;
	}

	public void togglePause () {
		if (paused) {
			unpause();
		} else {
			pause();
		}
	}
}
