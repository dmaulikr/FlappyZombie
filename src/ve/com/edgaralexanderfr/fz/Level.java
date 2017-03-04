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
		invokeRepeating("spawnZombie", 1, this);
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

	public Zombie spawnZombie (String name) {
		return game.instantiate(Zombie.class, 0, 0, (short) 0, name);
	}

	public boolean isControlled (Survivor survivor) {
		return survivor.getId() == controlledSurvivorId;
	}
}
