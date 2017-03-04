package ve.com.edgaralexanderfr.fz;

import java.awt.Image;

import javax.swing.JFrame;

import ve.com.edgaralexanderfr.game.Game;
import ve.com.edgaralexanderfr.game.Input;
import ve.com.edgaralexanderfr.game.Resources;
import ve.com.edgaralexanderfr.game.Renderer;
import ve.com.edgaralexanderfr.util.PropertiesReader;

public class Main {
	private static final String RESOURCES_FILE_PATH = "/res/text/Resources.txt";

	public Main () {
		Resources resources     = new Resources(RESOURCES_FILE_PATH);
		Input input             = new Input();
		Game game               = new Game(resources, input);
		Renderer renderer       = new Renderer(game);
		PropertiesReader config = resources.get("config", PropertiesReader.class);
		renderer.addMouseListener(input);
		renderer.addMouseMotionListener(input);
		game.instantiate(Level.class, 0.0f, 0.0f, (short) 0, (Image) null);
		renderer.start((byte) config.i("fps"));
		JFrame jFrame           = new JFrame(config.s("windowTitle"));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(config.i("windowWidth"), config.i("windowHeight"));
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		jFrame.add(renderer);
		jFrame.addKeyListener(input);
		jFrame.setIconImage(resources.get("zombie", Image.class));
		jFrame.setVisible(true);
	}

	public static void main (String[] args) {
		new Main();
	}
}
