package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.util.MathTools;

public class Survivor extends GameObject {
	Font font           = new Font("Arial", Font.BOLD, 14);
	Color color         = new Color(43, 72, 11, 175);
	boolean movingUp    = false;
	boolean movingDown  = false;
	boolean movingLeft  = false;
	boolean movingRight = false;
	Level level;

	public boolean isMovingUp () {
		return movingUp;
	}

	public boolean isMovingDown () {
		return movingDown;
	}

	public boolean isMovingLeft () {
		return movingLeft;
	}

	public boolean isMovingRight () {
		return movingRight;
	}

	public void moveUp (boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void moveDown (boolean movingDown) {
		this.movingDown = movingDown;
	}

	public void moveLeft (boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void moveRight (boolean movingRight) {
		this.movingRight = movingRight;
	}

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(font);
		g.setColor(color);
	}

	@Override
	public void start () {
		level = game.findGameObject(Level.class);
		setTextOffsetY(-30);
		x    = configi("windowWidth")  / 2;
		y    = configi("windowHeight") / 2;
		spriteTexture = resources().get("survivor", Image.class);
	}

	@Override
	public void update () {
		updateMove();
		updateControls();
	}

	public void shoot (float x, float y) {
		float targetX = this.x - 12;
		float targetY = this.y + 7;
		Bullet bullet = game.instantiate(Bullet.class, targetX, targetY, (short) 2, (Image) null);
		bullet.setDirection(MathTools.angleBetween(targetX, targetY, x, y));
	}

	void updateMove () {
		float deltaTime = renderer().deltaTime();

		if (movingUp) {
			y -= 400.0f * deltaTime;
		}

		if (movingDown) {
			y += 400.0f * deltaTime;
		}

		if (movingLeft) {
			x -= 400.0f * deltaTime;
		}

		if (movingRight) {
			x += 400.0f * deltaTime;
		}

		if (x <= 64) {
			x  = 64;
		}

		if (x >= configi("windowWidth") - 64) {
			x  = configi("windowWidth") - 64;
		}

		if (y <= 64) {
			y  = 64;
		}

		if (y >= configi("windowHeight") - 64) {
			y  = configi("windowHeight") - 64;
		}
	}

	void updateControls () {
		if (!level.isControlled(this)) {
			return;
		}

		if (input().getKeyDown(configi("moveUpKeyCode"))) {
			moveUp(true);
		} else 
		if (input().getKeyUp(configi("moveUpKeyCode"))) {
			moveUp(false);
		}

		if (input().getKeyDown(configi("moveDownKeyCode"))) {
			moveDown(true);
		} else 
		if (input().getKeyUp(configi("moveDownKeyCode"))) {
			moveDown(false);
		}

		if (input().getKeyDown(configi("moveLeftKeyCode"))) {
			moveLeft(true);
		} else 
		if (input().getKeyUp(configi("moveLeftKeyCode"))) {
			moveLeft(false);
		}

		if (input().getKeyDown(configi("moveRightKeyCode"))) {
			moveRight(true);
		} else 
		if (input().getKeyUp(configi("moveRightKeyCode"))) {
			moveRight(false);
		}

		if (input().getMouseUp(1)) {
			shoot(input().getMouseX(), input().getMouseY());
		}
	}
}
