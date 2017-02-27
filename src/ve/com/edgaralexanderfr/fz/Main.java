package ve.com.edgaralexanderfr.fz;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ve.com.edgaralexanderfr.game.Game;
import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.game.Renderer;

public class Main {
	public Main () {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/res/sprites/dog.png"));
		Image image         = imageIcon.getImage();
		Game game           = new Game();
		Renderer renderer   = new Renderer(game);
		JFrame jFrame       = new JFrame("Flappy Zombie");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(640, 360);
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		jFrame.add(renderer);
		jFrame.setVisible(true);

		try {
			Dog dog = game.instantiate(Dog.class, 0.0f, 0.0f, (short) 0, image);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		renderer.start();
	}

	public static void main (String[] args) {
		new Main();
	}
}
