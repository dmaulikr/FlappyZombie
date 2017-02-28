package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;

public class Bullet extends GameObject {
	static final float SPEED = 600.0f;

	float direction          = 0.0f;

	public float getDirection () {
		return direction;
	}

	public void setDirection (float direction) {
		if (direction >= 0.0f && direction <= Math.PI * 2) {
			this.direction = direction;
		}
	}

	@Override
	public void onTextFormatting (Graphics g) {
		
	}

	@Override
	public void start () {
		zIndex        = 2;
		spriteTexture = resources().get("bullet", Image.class);
	}

	@Override
	public void update () {
		float deltaTime = renderer().deltaTime();
		x              += Math.cos(direction) * SPEED * deltaTime;
		y              += Math.sin(direction) * SPEED * deltaTime;

		if (x <= 64 || x >= configi("windowWidth") - 64 || y <= 64 || y >= configi("windowHeight") - 64) {
			destroy();
		}
	}
}
