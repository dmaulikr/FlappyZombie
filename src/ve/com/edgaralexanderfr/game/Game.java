package ve.com.edgaralexanderfr.game;

import java.awt.Image;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.util.ArrayList;
import java.util.Arrays;
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
		gameObject.setGame(this);
		gameObject.setId(this.lastId);
		gameObject.setX(x);
		gameObject.setY(y);
		gameObject.setZIndex(zIndex);
		gameObject.setSpriteTexture(spriteTexture);
		gameObject.setText(text);
		this.gameObjects.add(gameObject);
		gameObject.start();

		return gameObject;
	}

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, Image spriteTexture) throws IllegalAccessException, InstantiationException {
		return this.instantiate(type, x, y, zIndex, spriteTexture, null);
	}

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, String text) throws IllegalAccessException, InstantiationException {
		return this.instantiate(type, x, y, zIndex, null, text);
	}

	public synchronized void update () {
		GameObject[] gameObjects = this.getGameObjects();
		int length               = gameObjects.length;
		int i;

		for (i = 0; i < length; i++) {
			gameObjects[ i ].update();
		}
	}

	public synchronized GameObject[] getGameObjects () {
		GameObject[] gameObjects = new GameObject[ this.gameObjects.size() ];

		return this.gameObjects.toArray(gameObjects);
	}

	public GameObject[] getSortedGameObjects () {
		GameObject[] gameObjects = this.getGameObjects();
		Arrays.sort(gameObjects, new GameObjectZComparator());

		return gameObjects;
	}

	public synchronized void destroy (GameObject gameObject) {
		int size = this.gameObjects.size();
		int i;

		for (i = 0; i < size; i++) {
			if (gameObject.getId() == this.gameObjects.get(i).getId()) {
				this.gameObjects.remove(i);

				break;
			}
		}
	}
}
