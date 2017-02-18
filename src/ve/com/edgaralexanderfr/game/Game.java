package ve.com.edgaralexanderfr.game;

import java.awt.Image;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private long lastId                  = -1;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();

	public long getLastId () {
		return this.lastId;
	}

	public synchronized <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, Image spriteTexture, String text) throws IllegalAccessException, InstantiationException {
		this.lastId++;
		T gameObject = type.newInstance();
		gameObject.setId(this.lastId);
		gameObject.setX(x);
		gameObject.setY(y);
		gameObject.setZIndex(zIndex);
		gameObject.setSpriteTexture(spriteTexture);
		gameObject.setText(text);
		this.gameObjects.add(gameObject);
		gameObject.start(this);

		return gameObject;
	}

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, Image spriteTexture) throws IllegalAccessException, InstantiationException {
		return this.instantiate(type, x, y, zIndex, spriteTexture, null);
	}

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, String text) throws IllegalAccessException, InstantiationException {
		return this.instantiate(type, x, y, zIndex, null, text);
	}
}
