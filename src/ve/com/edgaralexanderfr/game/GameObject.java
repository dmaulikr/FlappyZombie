package ve.com.edgaralexanderfr.game;

import java.awt.Image;

public abstract class GameObject {
	protected long id             = 0;
	protected float x             = 0.0f;
	protected float y             = 0.0f;
	protected short zIndex        = 0;
	protected Image spriteTexture = null;
	protected String text         = null;

	public long getId () {
		return this.id;
	}

	public float getX () {
		return this.x;
	}

	public float getY () {
		return this.y;
	}

	public short getZIndex () {
		return this.zIndex;
	}

	public Image getSpriteTexture () {
		return this.spriteTexture;
	}

	public String getText () {
		return this.text;
	}

	public void setId (long id) {
		this.id = id;
	}

	public void setX (float x) {
		this.x = x;
	}

	public void setY (float y) {
		this.y = y;
	}

	public void setZIndex (short zIndex) {
		this.zIndex = zIndex;
	}

	public void setSpriteTexture (Image spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public void setText (String text) {
		this.text = text;
	}

	public abstract void start(Game game);

	public abstract void update(Game game);
}
