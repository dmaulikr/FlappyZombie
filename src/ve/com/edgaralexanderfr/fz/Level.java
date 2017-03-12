package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.game.ScheduledRoutine;
import ve.com.edgaralexanderfr.game.ScheduledRoutineEvent;

public class Level extends GameObject implements ScheduledRoutineEvent {
	FPSCounter fpsCounter     = null;
	Score score               = null;
	Ping ping                 = null;
	LifeCounter lifeCounter   = null;
	Pause pause               = null;
	long controlledSurvivorId = -1;

	public FPSCounter getFPSCounter () {
		return fpsCounter;
	}

	public Score getScore () {
		return score;
	}

	public Ping getPing () {
		return ping;
	}

	public LifeCounter getLifeCounter () {
		return lifeCounter;
	}

	public Pause getPause () {
		return pause;
	}

	@Override
	public void onTextFormatting (Graphics g) {

	}

	@Override
	public void start () {
		setSpriteTexturePivot(PIVOT_TOP_LEFT);
		spriteTexture        = resources().get("level", Image.class);
		fpsCounter           = game.instantiate(FPSCounter.class, 0, 0, (short) 0, (Image) null);
		score                = game.instantiate(Score.class, 0, 0, (short) 0, (Image) null);
		ping                 = game.instantiate(Ping.class, 0, 0, (short) 0, (Image) null);
		lifeCounter          = game.instantiate(LifeCounter.class, 0, 0, (short) 0, (Image) null);
		pause                = game.instantiate(Pause.class, 0, 0, (short) 0, (Image) null);
		controlledSurvivorId = spawnSurvivor("Edgar Alexander").getId();
		invokeRepeating("spawnZombie", 0.5f, this);
	}

	@Override
	public void update () {

	}

	@Override
	public void onIntervalReached (ScheduledRoutine scheduledRoutine) {
		switch (scheduledRoutine.getName()) {
			case "spawnZombie" : spawnZombie(null) ;
		}
	}

	public Survivor spawnSurvivor (String name) {
		return game.instantiate(Survivor.class, 0, 0, (short) 0, name);
	}

	public Survivor spawnSurvivor (String name, float x, float y) {
		Survivor survivor = spawnSurvivor(name);
		survivor.setX(x);
		survivor.setY(y);

		return survivor;
	}

	public Zombie spawnZombie (String name) {
		return game.instantiate(Zombie.class, 0, 0, (short) 0, name);
	}

	public Zombie spawnZombie (String name, float x, float y) {
		Zombie zombie = spawnZombie(name);
		zombie.setX(x);
		zombie.setY(y);
		zombie.setTargetX(x);
		zombie.setTargetY(y);

		return zombie;
	}

	public boolean isControlled (Survivor survivor) {
		return survivor.getId() == controlledSurvivorId;
	}
}
