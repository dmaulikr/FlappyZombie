package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import java.util.ArrayList;
import java.util.List;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.util.MathTools;

public class Bullet extends GameObject {
	static final float SPEED = 600.0f;

	float direction          = 0.0f;
	Level level              = null;

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
		level         = game.findGameObject(Level.class);
		zIndex        = 499;
		spriteTexture = resources().get("bullet", Image.class);
	}

	@Override
	public void update () {
		if (level.getPause().isPaused()) {
			return;
		}

		updateMove();
	}

	void updateMove () {
		float deltaTime      = renderer().deltaTime();
		float nextX          = (float) (x + Math.cos(direction) * SPEED * deltaTime);
		float nextY          = (float) (y + Math.sin(direction) * SPEED * deltaTime);
		List<Zombie> zombies = game.findGameObjects(Zombie.class);
		boolean collided     = false;

		for (Zombie zombie : zombies) {
			if (MathTools.lineIntersectsRectangle(x, y, nextX, nextY, zombie.getX() - 16, zombie.getY() - 16, 32, 32)) {
				zombie.kill();
				collided = true;

				break;
			}
		}

		x = nextX;
		y = nextY;

		if (collided || x <= 64 || x >= configi("windowWidth") - 64 || y <= 64 || y >= configi("windowHeight") - 64) {
			destroy();
		}
	}
}
