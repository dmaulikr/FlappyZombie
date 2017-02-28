package ve.com.edgaralexanderfr.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.HashSet;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	private HashSet<Integer> pressedKeys  = new HashSet<Integer>();
	private HashSet<Integer> pressedMouse = new HashSet<Integer>();
	private int mouseX                    = 0;
	private int mouseY                    = 0;

	public int getMouseX () {
		return this.mouseX;
	}

	public int getMouseY () {
		return this.mouseY;
	}

	public synchronized boolean getKey (int keyCode) {
		return this.pressedKeys.contains(keyCode);
	}

	public synchronized boolean getMouse (int button) {
		return this.pressedMouse.contains(button);
	}

	@Override
	public synchronized void keyPressed (KeyEvent e) {
		this.pressedKeys.add(e.getKeyCode());
	}

	@Override
	public synchronized void keyReleased (KeyEvent e) {
		this.pressedKeys.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped (KeyEvent e) {
		
	}

	@Override
	public void mouseClicked (MouseEvent e) {

	}

	@Override
	public void mouseEntered (MouseEvent e) {
		
	}

	@Override
	public void mouseExited (MouseEvent e) {

	}

	@Override
	public synchronized void mousePressed (MouseEvent e) {
		this.pressedMouse.add(e.getButton());
	}

	@Override
	public synchronized void mouseReleased (MouseEvent e) {
		this.pressedMouse.remove(e.getButton());
	}

	@Override
	public void mouseDragged (MouseEvent e) {

	}

	@Override
	public void mouseMoved (MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}
}
