package ve.com.edgaralexanderfr.game;

import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.net.UDPConnection;
import ve.com.edgaralexanderfr.util.PropertiesReader;

public abstract class GameObject {
	public static final byte PIVOT_TOP_LEFT     = 0;
	public static final byte PIVOT_TOP          = 1;
	public static final byte PIVOT_TOP_RIGHT    = 2;
	public static final byte PIVOT_LEFT         = 3;
	public static final byte PIVOT_CENTER       = 4;
	public static final byte PIVOT_RIGHT        = 5;
	public static final byte PIVOT_BOTTOM_LEFT  = 6;
	public static final byte PIVOT_BOTTOM       = 7;
	public static final byte PIVOT_BOTTOM_RIGHT = 8;

	protected Game game                         = null;
	protected long id                           = 0;
	protected float x                           = 0.0f;
	protected float y                           = 0.0f;
	protected short zIndex                      = 0;
	protected float spriteTextureOffsetX        = 0.0f;
	protected float spriteTextureOffsetY        = 0.0f;
	protected byte spriteTexturePivot           = PIVOT_CENTER;
	protected float textOffsetX                 = 0.0f;
	protected float textOffsetY                 = 0.0f;
	protected byte textPivot                    = PIVOT_CENTER;
	protected Image spriteTexture               = null;
	protected String text                       = null;

	public Game getGame () {
		return this.game;
	}

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

	public float getSpriteTextureOffsetX () {
		return this.spriteTextureOffsetX;
	}

	public float getSpriteTextureOffsetY () {
		return this.spriteTextureOffsetY;
	}

	public byte getSpriteTexturePivot () {
		return this.spriteTexturePivot;
	}

	public float getTextOffsetX () {
		return this.textOffsetX;
	}

	public float getTextOffsetY () {
		return this.textOffsetY;
	}

	public byte getTextPivot () {
		return this.textPivot;
	}

	public Image getSpriteTexture () {
		return this.spriteTexture;
	}

	public String getText () {
		return this.text;
	}

	public Coords2D getSpriteTextureFinalCoordinates () {
		float width, height;

		if (this.spriteTexture == null) {
			width = height = 0.0f;
		} else {
			width  = (float) this.spriteTexture.getWidth(null);
			height = (float) this.spriteTexture.getHeight(null);
		}

		Coords2D coordinates = calculateCoordinatesDifference(this.spriteTextureOffsetX, this.spriteTextureOffsetY, this.spriteTexturePivot, width, height);
		coordinates.x       += this.x;
		coordinates.y       += this.y;

		return coordinates;
	}

	public Coords2D getTextFinalCoordinates (float width, float height) {
		Coords2D coordinates = calculateCoordinatesDifference(this.textOffsetX, this.textOffsetY, this.textPivot, width, height);
		coordinates.x       += this.x;
		coordinates.y       += this.y;

		return coordinates;
	}

	public void setGame (Game game) {
		this.game = game;
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

	public void setSpriteTextureOffsetX (float spriteTextureOffsetX) {
		this.spriteTextureOffsetX = spriteTextureOffsetX;
	}

	public void setSpriteTextureOffsetY (float spriteTextureOffsetY) {
		this.spriteTextureOffsetY = spriteTextureOffsetY;
	}

	public void setSpriteTexturePivot (byte spriteTexturePivot) {
		if (spriteTexturePivot >= PIVOT_TOP_LEFT && spriteTexturePivot <= PIVOT_BOTTOM_RIGHT) {
			this.spriteTexturePivot = spriteTexturePivot;
		}
	}

	public void setTextOffsetX (float textOffsetX) {
		this.textOffsetX = textOffsetX;
	}

	public void setTextOffsetY (float textOffsetY) {
		this.textOffsetY = textOffsetY;
	}

	public void setTextPivot (byte textPivot) {
		if (textPivot >= PIVOT_TOP_LEFT && textPivot <= PIVOT_BOTTOM_RIGHT) {
			this.textPivot = textPivot;
		}
	}

	public void setSpriteTexture (Image spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public void setText (String text) {
		this.text = text;
	}

	public static Coords2D calculateCoordinatesDifference (float offsetX, float offsetY, byte pivot, float width, float height) {
		float halfWidth  = width  / 2;
		float halfHeight = height / 2;
		float x, y;

		switch (pivot) {
			case PIVOT_TOP_LEFT     : {
				x = 0.0f;
				y = 0.0f;
			} break;
			case PIVOT_TOP          : {
				x = -halfWidth;
				y = 0.0f;
			} break;
			case PIVOT_TOP_RIGHT    : {
				x = -width;
				y = 0.0f;
			} break;
			case PIVOT_LEFT         : {
				x = 0.0f;
				y = -halfHeight;
			} break;
			case PIVOT_RIGHT        : {
				x = -width;
				y = -halfHeight;
			} break;
			case PIVOT_BOTTOM_LEFT  : {
				x = 0.0f;
				y = -height;
			} break;
			case PIVOT_BOTTOM       : {
				x = -halfWidth;
				y = -height;
			} break;
			case PIVOT_BOTTOM_RIGHT : {
				x = -width;
				y = -height;
			} break;
			default                 : {
				x = -halfWidth;
				y = -halfHeight;
			}
		}

		return new Coords2D(x + offsetX, y + offsetY);
	}

	public void destroy () {
		if (this.game != null) {
			this.game.destroy(this);
		}
	}

	public abstract void onTextFormatting (Graphics g);
	public abstract void start ();
	public abstract void update ();

	protected void log (String output) {
		System.out.println(output);
	}

	protected Resources resources () {
		return this.game.getResources();
	}

	protected Input input () {
		return this.game.getInput();
	}

	protected UDPConnection udpConnection () {
		return this.game.getUDPConnection();
	}

	protected Renderer renderer () {
		return this.game.getRenderer();
	}

	protected String configs (String property) {
		return this.resources().get("config", PropertiesReader.class).s(property);
	}

	protected int configi (String property) {
		return this.resources().get("config", PropertiesReader.class).i(property);
	}

	protected float configf (String property) {
		return this.resources().get("config", PropertiesReader.class).f(property);
	}

	protected boolean isClient () {
		return this.game.isClient();
	}

	protected boolean isServer () {
		return this.game.isServer();
	}

	protected float deltaTime () {
		return this.renderer().deltaTime();
	}

	protected void invoke (String name, float time, float interval, boolean repeat, ScheduledRoutineEvent scheduledRoutineEvent) {
		this.game.invoke(new ScheduledRoutine(scheduledRoutineEvent, this.game.getRenderer(), name, interval, time, repeat));
	}

	protected void invoke (String name, float time, float interval, ScheduledRoutineEvent scheduledRoutineEvent) {
		this.invoke(name, time, interval, false, scheduledRoutineEvent);
	}

	protected void invoke (String name, float interval, ScheduledRoutineEvent scheduledRoutineEvent) {
		this.invoke(name, 0.0f, interval, scheduledRoutineEvent);
	}

	protected void invokeRepeating (String name, float time, float interval, ScheduledRoutineEvent scheduledRoutineEvent) {
		this.invoke(name, time, interval, true, scheduledRoutineEvent);
	}

	protected void invokeRepeating (String name, float interval, ScheduledRoutineEvent scheduledRoutineEvent) {
		this.invokeRepeating(name, 0.0f, interval, scheduledRoutineEvent);
	}

	protected void cancelInvoke (String name) {
		this.game.cancelInvoke(name);
	}
}
