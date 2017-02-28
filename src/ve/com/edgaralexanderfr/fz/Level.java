package ve.com.edgaralexanderfr.fz;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;

public class Level extends GameObject {
	@Override
	public void onTextFormatting (Graphics g) {

	}

	@Override
	public void start () {
		this.setSpriteTexturePivot(PIVOT_TOP_LEFT);
		this.spriteTexture = resources().get("level", Image.class);
	}

	@Override
	public void update () {

	}
}
