package ve.com.edgaralexanderfr.game;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel {
	private Game game = null;

	public Game getGame () {
		return this.game;
	}

	public void setGame (Game game) {
		this.game = game;
	}

	public Renderer (Game game) {
		this.setGame(game);
	}

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		if (this.game == null) {
			return;
		}

		this.game.update();
		GameObject[] gameObjects = this.game.getSortedGameObjects();
		
		for (GameObject gameObject : gameObjects) {
			if (gameObject.getSpriteTexture() != null) {
				g.drawImage(gameObject.getSpriteTexture(), (int) gameObject.getX(), (int) gameObject.getY(), null);
			}
		}
	}
}
