package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.util.MathTools;

public class Survivor extends GameObject {
	Font font           = new Font("Arial", Font.BOLD, 14);
	Color playerColor   = new Color(43, 72, 11, 175);
	Color teamMateColor = new Color(55, 83, 121, 175);
	Color color         = teamMateColor;
	boolean movingUp    = false;
	boolean movingDown  = false;
	boolean movingLeft  = false;
	boolean movingRight = false;
	Level level         = null;
	Pause pause         = null;

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
		level         = game.findGameObject(Level.class);
		pause         = game.findGameObject(Pause.class);
		setTextOffsetY(-30);
		x             = configi("windowWidth")  / 2;
		y             = configi("windowHeight") / 2;
		spriteTexture = resources().get("survivor", Image.class);
	}

	@Override
	public void update () {
		if (pause.isPaused()) {
			return;
		}

		updateMove();
		updateControls();
	}

	public void shoot (float targetX, float targetY) {
		float originX = this.x - 12;
		float originY = this.y + 7;
		Bullet bullet = game.instantiate(Bullet.class, originX, originY, (short) 2, (Image) null);
		bullet.setDirection(MathTools.angleBetween(originX, originY, targetX, targetY));
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

		zIndex = (short) Math.round(y);
	}

	void updateControls () {
		if (!level.isControlled(this)) {
			color = teamMateColor;

			return;
		}

		color = playerColor;

		if (input().getKeyDown(configi("moveUpKeyCode1")) || input().getKeyDown(configi("moveUpKeyCode2"))) {
			moveUp(true);
		} else 
		if (input().getKeyUp(configi("moveUpKeyCode1")) || input().getKeyUp(configi("moveUpKeyCode2"))) {
			moveUp(false);
		}

		if (input().getKeyDown(configi("moveDownKeyCode1")) || input().getKeyDown(configi("moveDownKeyCode2"))) {
			moveDown(true);
		} else 
		if (input().getKeyUp(configi("moveDownKeyCode1")) || input().getKeyUp(configi("moveDownKeyCode2"))) {
			moveDown(false);
		}

		if (input().getKeyDown(configi("moveLeftKeyCode1")) || input().getKeyDown(configi("moveLeftKeyCode2"))) {
			moveLeft(true);
		} else 
		if (input().getKeyUp(configi("moveLeftKeyCode1")) || input().getKeyUp(configi("moveLeftKeyCode2"))) {
			moveLeft(false);
		}

		if (input().getKeyDown(configi("moveRightKeyCode1")) || input().getKeyDown(configi("moveRightKeyCode2"))) {
			moveRight(true);
		} else 
		if (input().getKeyUp(configi("moveRightKeyCode1")) || input().getKeyUp(configi("moveRightKeyCode2"))) {
			moveRight(false);
		}

		if (input().getMouseDown(configi("shootMouseButton")) || input().getKeyDown(configi("shootKeyCode1")) || input().getKeyDown(configi("shootKeyCode2"))) {
			shoot(input().getMouseX(), input().getMouseY());
		}
	}
}
