package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;

public class Zombie extends GameObject {
	Font font   = new Font("Arial", Font.BOLD, 14);
	Color color = new Color(55, 83, 121, 175);
	Level level = null;
	Pause pause = null;

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(color);
	}

	@Override
	public void start () {
		level         = game.findGameObject(Level.class);
		pause         = game.findGameObject(Pause.class);
		setTextOffsetY(-30);
		boolean h     = (Math.random() < 0.5f);
		x             = (h) ? Math.round(64 + (Math.random() * (configi("windowWidth") - 128))) : ((Math.random() < 0.5f) ? 64 : configi("windowWidth") - 64 ) ;
		y             = (h) ? ((Math.random() < 0.5f) ? 64 : configi("windowHeight") - 64 ) : Math.round(64 + (Math.random() * (configi("windowHeight") - 128))) ;
		spriteTexture = resources().get("zombie", Image.class);
	}

	@Override
	public void update () {
		zIndex = (short) Math.round(y);
	}
}
