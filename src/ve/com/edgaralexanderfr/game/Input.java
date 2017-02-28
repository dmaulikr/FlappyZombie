package ve.com.edgaralexanderfr.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	private HashSet<Integer> downKeys     = new HashSet<Integer>();
	private HashSet<Integer> pressedKeys  = new HashSet<Integer>();
	private HashSet<Integer> upKeys       = new HashSet<Integer>();
	private HashSet<Integer> downMouse    = new HashSet<Integer>();
	private HashSet<Integer> pressedMouse = new HashSet<Integer>();
	private HashSet<Integer> upMouse      = new HashSet<Integer>();
	private Set<Integer> pendingDownKeys  = new HashSet<Integer>();
	private Set<Integer> pendingUpKeys    = new HashSet<Integer>();
	private Set<Integer> pendingDownMouse = new HashSet<Integer>();
	private Set<Integer> pendingUpMouse   = new HashSet<Integer>();
	private int mouseX                    = 0;
	private int mouseY                    = 0;

	public int getMouseX () {
		return this.mouseX;
	}

	public int getMouseY () {
		return this.mouseY;
	}

	@Override
	public synchronized void keyPressed (KeyEvent e) {
		this.pressedKeys.add(e.getKeyCode());
	}

	@Override
	public synchronized void keyReleased (KeyEvent e) {
		this.downKeys.remove(e.getKeyCode());
		this.pressedKeys.remove(e.getKeyCode());
		this.upKeys.add(e.getKeyCode());
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
		this.downMouse.add(e.getButton());
		this.pressedMouse.add(e.getButton());
	}

	@Override
	public synchronized void mouseReleased (MouseEvent e) {
		this.pressedMouse.remove(e.getButton());
		this.upMouse.add(e.getButton());
	}

	@Override
	public void mouseDragged (MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	@Override
	public void mouseMoved (MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	public synchronized boolean getKeyDown (int keyCode) {
		if (this.downKeys.contains(keyCode) || !this.pressedKeys.contains(keyCode)) {
			return false;
		}

		this.pendingDownKeys.add(keyCode);

		return true;
	}

	public synchronized boolean getKey (int keyCode) {
		return this.pressedKeys.contains(keyCode);
	}

	public synchronized boolean getKeyUp (int keyCode) {
		if (this.upKeys.contains(keyCode)) {
			this.pendingUpKeys.add(keyCode);

			return true;
		}

		return false;
	}

	public synchronized boolean getMouseDown (int button) {
		if (this.downMouse.contains(button)) {
			this.pendingDownMouse.add(button);

			return true;
		}

		return false;
	}

	public synchronized boolean getMouse (int button) {
		return this.pressedMouse.contains(button);
	}

	public synchronized boolean getMouseUp (int button) {
		if (this.upMouse.contains(button)) {
			this.pendingUpMouse.add(button);

			return true;
		}

		return false;
	}

	public synchronized void updatePending () {
		for (int keyCode : this.pendingDownKeys) {
			this.downKeys.add(keyCode);
		}

		for (int keyCode : this.pendingUpKeys) {
			this.upKeys.remove(keyCode);
		}

		for (int button : this.pendingDownMouse) {
			this.downMouse.remove(button);
		}

		for (int button : this.pendingUpMouse) {
			this.upMouse.remove(button);
		}

		this.pendingDownKeys.clear();
		this.pendingUpKeys.clear();
		this.pendingDownMouse.clear();
		this.pendingUpMouse.clear();
	}

	public synchronized void reset () {
		this.downKeys.clear();
		this.upKeys.clear();
		this.downMouse.clear();
		this.upMouse.clear();
		this.pendingDownKeys.clear();
		this.pendingUpKeys.clear();
		this.pendingDownMouse.clear();
		this.pendingUpMouse.clear();
	}
}
