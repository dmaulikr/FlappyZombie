package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;

public class Level extends GameObject {
	FPSCounter fpsCounter     = null;
	LifeCounter lifeCounter   = null;
	Ping ping                 = null;
	Server server             = null;
	long controlledSurvivorId = -1;

	@Override
	public void onTextFormatting (Graphics g) {

	}

	@Override
	public void start () {
		setSpriteTexturePivot(PIVOT_TOP_LEFT);
		spriteTexture        = resources().get("level", Image.class);
		fpsCounter           = game.instantiate(FPSCounter.class, 0, 0, (short) 0, (Image) null);
		lifeCounter          = game.instantiate(LifeCounter.class, 0, 0, (short) 0, (Image) null);
		ping                 = game.instantiate(Ping.class, 0, 0, (short) 0, (Image) null);
		server               = game.instantiate(Server.class, 0, 0, (short) 0, (Image) null);
		controlledSurvivorId = spawnSurvivor("Edgar Alexander").getId();
	}

	@Override
	public void update () {

	}

	public Survivor spawnSurvivor (String name) {
		return game.instantiate(Survivor.class, configi("windowWidth") / 2, configi("windowHeight") / 2, (short) 1, name);
	}

	public boolean isControlled (Survivor survivor) {
		return survivor.getId() == controlledSurvivorId;
	}
}
