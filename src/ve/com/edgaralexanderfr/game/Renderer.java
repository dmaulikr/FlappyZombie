package ve.com.edgaralexanderfr.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import ve.com.edgaralexanderfr.util.GraphicsTools;

public class Renderer extends JPanel implements ActionListener {
	public static final long serialVersionUID = 42l;

	private Game game                         = null;
	private long lastFrameTime                = -1;
	private long currentFrameTime             = 0;
	private float fpsElapsedTime              = 0.0f;
	private int frameCount                    = 0;
	private int fps                           = 0;
	private Timer timer                       = null;
	private short time                        = (short) Math.round(1000 / 24);

	public Game getGame () {
		return this.game;
	}

	public long getLastFrameTime () {
		return this.lastFrameTime;
	}

	public long getCurrentFrameTime () {
		return this.currentFrameTime;
	}

	public float getFPSElapsedTime () {
		return this.fpsElapsedTime;
	}

	public int getFrameCount () {
		return this.frameCount;
	}

	public int getFPS () {
		return this.fps;
	}

	public short getTime () {
		return this.time;
	}

	public void setGame (Game game) {
		this.game = game;
	}

	public void setTime (short time) {
		if (time > 0) {
			this.time = time;
		}
	}

	public void setFPS (byte fps) {
		if (fps > 0) {
			this.setTime((short) (1000 / fps));
		}
	}

	public Renderer (Game game) {
		this.setGame(game);
		game.setRenderer(this);
	}

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		if (this.game == null) {
			return;
		}

		this.currentFrameTime    = ve.com.edgaralexanderfr.util.Timer.now();
		this.fpsElapsedTime     += this.deltaTime();
		this.game.updateScheduledRoutines();
		this.game.update();
		this.lastFrameTime       = this.currentFrameTime;
		GameObject[] gameObjects = this.game.getSortedGameObjects();
		Coords2D coordinates;
		Rectangle rectangle;

		for (GameObject gameObject : gameObjects) {
			if (gameObject.getSpriteTexture() != null) {
				coordinates = gameObject.getSpriteTextureFinalCoordinates();
				g.drawImage(gameObject.getSpriteTexture(), coordinates.getRoundedX(), coordinates.getRoundedY(), null);
			}

			if (gameObject.getText() != null) {
				gameObject.onTextFormatting(g);
				rectangle      = GraphicsTools.getStringBounds((Graphics2D) g, gameObject.getText(), 0.0f, 0.0f);
				coordinates    = gameObject.getTextFinalCoordinates((float) rectangle.getWidth(), (float) rectangle.getHeight());
				coordinates.y += (float) rectangle.getHeight();
				g.drawString(gameObject.getText(), coordinates.getRoundedX(), coordinates.getRoundedY());
			}
		}

		if (this.game.getInput() != null) {
			this.game.getInput().updatePending();
		}

		this.frameCount++;

		if (this.fpsElapsedTime >= 1.0f) {
			this.fpsElapsedTime = 0.0f;
			this.fps            = this.frameCount;
			this.frameCount     = 0;
		}
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		this.repaint();
	}

	public float deltaTime () {
		return (this.lastFrameTime == -1) ? 0.0f : (this.currentFrameTime - this.lastFrameTime) / 1000.0f ;
	}

	public synchronized void start () {
		if (this.timer != null) {
			return;
		}

		this.lastFrameTime = -1;

		if (this.game != null && this.game.getInput() != null) {
			this.game.getInput().reset();
		}

		this.timer = new Timer(this.time, this);
		this.timer.start();
	}

	public void start (byte fps) {
		this.setFPS(fps);
		this.start();
	}

	public synchronized void stop () {
		if (this.timer != null) {
			this.timer.stop();
			this.timer = null;
		}
	}
}
