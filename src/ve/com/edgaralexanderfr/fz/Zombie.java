package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.util.ArrayList;
import java.util.List;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.game.ScheduledRoutine;
import ve.com.edgaralexanderfr.game.ScheduledRoutineEvent;

public class Zombie extends GameObject implements ScheduledRoutineEvent {
	Font font     = new Font("Arial", Font.BOLD, 14);
	Color color   = new Color(55, 83, 121, 175);
	Level level   = null;
	Pause pause   = null;
	float targetX = 0;
	float targetY = 0;

	public float getTargetX () {
		return targetX;
	}

	public float getTargetY () {
		return targetY;
	}

	public void setTargetX (float targetX) {
		this.targetX = targetX;
	}

	public void setTargetY (float targetY) {
		this.targetY = targetY;
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
		boolean h     = (Math.random() < 0.5f);
		x             = (h) ? Math.round(64 + (Math.random() * (configi("windowWidth") - 128))) : ((Math.random() < 0.5f) ? 64 : configi("windowWidth") - 64 ) ;
		y             = (h) ? ((Math.random() < 0.5f) ? 64 : configi("windowHeight") - 64 ) : Math.round(64 + (Math.random() * (configi("windowHeight") - 128))) ;
		spriteTexture = resources().get("zombie", Image.class);
		targetX       = x;
		targetY       = y;
		invokeRepeating("updateTargetPos" + this.id, 3, this);
		updateTargetPos();
	}

	@Override
	public void update () {
		if (pause.isPaused()) {
			return;
		}

		updateMove();
	}

	@Override
	public void onIntervalReached (ScheduledRoutine scheduledRoutine) {
		if (scheduledRoutine.getName().equals("updateTargetPos" + this.id)) {
			updateTargetPos();
		}
	}

	void updateMove () {
		float deltaTime = deltaTime();

		if (x < targetX) {
			x = Math.min(x + (200 * deltaTime), targetX);
		} else 
		if (x > targetX) {
			x = Math.max(x - (200 * deltaTime), targetX);
		}

		if (y < targetY) {
			y = Math.min(y + (200 * deltaTime), targetY);
		} else 
		if (y > targetY) {
			y = Math.max(y - (200 * deltaTime), targetY);
		}

		zIndex = (short) Math.round(y);
	}

	void updateTargetPos () {
		List<Survivor> survivors = game.findGameObjects(Survivor.class);

		if (survivors.size() == 0) {
			return;
		}

		Survivor closestSurvivor = survivors.get(0);
		float closestDistance    = (float) Math.sqrt(Math.pow(closestSurvivor.getX(), 2) + Math.pow(closestSurvivor.getY(), 2));
		float distance;

		for (Survivor survivor : survivors) {
			distance = (float) Math.sqrt(Math.pow(survivor.getX(), 2) + Math.pow(survivor.getY(), 2));

			if (distance < closestDistance) {
				closestSurvivor = survivor;
				closestDistance = distance;
			}
		}

		targetX = closestSurvivor.getX();
		targetY = closestSurvivor.getY();
	}
}
