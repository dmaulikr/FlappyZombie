package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.game.Resources;

public class Level extends GameObject {
	private Resources resources = null;

	@Override
	public void onTextFormatting (Graphics g) {

	}

	@Override
	public void start () {
		this.resources     = this.game.getResources();
		this.setSpriteTexturePivot(PIVOT_TOP_LEFT);
		this.spriteTexture = this.resources.get("level", Image.class);
		this.game.instantiate(Survivor.class, 0.0f, 0.0f, (short) 0, (Image) null);
	}

	@Override
	public void update () {

	}
}
