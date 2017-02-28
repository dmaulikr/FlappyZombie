package ve.com.edgaralexanderfr.game;

import java.awt.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ve.com.edgaralexanderfr.net.UDPClient;
import ve.com.edgaralexanderfr.net.UDPConnection;
import ve.com.edgaralexanderfr.net.UDPServer;

public class Game {
	private Resources resources          = null;
	private Input input                  = null;
	private UDPConnection udpConnection  = null;
	private Renderer renderer            = null;
	private long lastId                  = -1;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();

	public Resources getResources () {
		return this.resources;
	}

	public Input getInput () {
		return this.input;
	}

	public UDPConnection getUDPConnection () {
		return this.udpConnection;
	}

	public Renderer getRenderer () {
		return this.renderer;
	}

	public long getLastId () {
		return this.lastId;
	}

	public boolean isClient () {
		return (this.udpConnection != null && this.udpConnection instanceof UDPClient);
	}

	public boolean isServer () {
		return (this.udpConnection != null && this.udpConnection instanceof UDPServer);
	}

	public void setResources (Resources resources) {
		this.resources = resources;
	}

	public void setInput (Input input) {
		this.input = input;
	}

	public void setUDPConnection (UDPConnection udpConnection) {
		this.udpConnection = udpConnection;
	}

	public void setRenderer (Renderer renderer) {
		this.renderer = renderer;
	}

	public Game (Resources resources, Input input) {
		this.setResources(resources);
		this.setInput(input);
	}

	public synchronized <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, Image spriteTexture, String text) {
		T gameObject = null;
		this.lastId++;

		try {
			gameObject = type.newInstance();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(1);
		}
		
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

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, Image spriteTexture) {
		return this.instantiate(type, x, y, zIndex, spriteTexture, null);
	}

	public <T extends GameObject> T instantiate (Class<T> type, float x, float y, short zIndex, String text) {
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

	public synchronized <T extends GameObject> List<T> findGameObjects (Class<T> type) {
		int size            = this.gameObjects.size();
		List<T> gameObjects = new ArrayList<T>();
		int i;
		GameObject gameObject;

		for (i = 0; i < size; i++) {
			gameObject = this.gameObjects.get(i);

			if (type.isInstance(gameObject)) {
				gameObjects.add(type.cast(gameObject));
			}
		}

		return gameObjects;
	}

	public synchronized <T extends GameObject> T findGameObject (Class<T> type) {
		int size = this.gameObjects.size();
		int i;
		GameObject gameObject;

		for (i = 0; i < size; i++) {
			gameObject = this.gameObjects.get(i);

			if (type.isInstance(gameObject)) {
				return type.cast(gameObject);
			}
		}

		return null;
	}

	public synchronized GameObject findGameObjectById (long id) {
		int size = this.gameObjects.size();
		int i;
		GameObject gameObject;

		for (i = 0; i < size; i++) {
			gameObject = this.gameObjects.get(i);

			if (gameObject.getId() == id) {
				return gameObject;
			}
		}

		return null;
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
