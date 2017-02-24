package ve.com.edgaralexanderfr.game;

public class Coords2D {
	public float x = 0.0f;
	public float y = 0.0f;

	public int getRoundedX () {
		return Math.round(this.x);
	}

	public int getRoundedY () {
		return Math.round(this.y);
	}

	public Coords2D (float x, float y) {
		this.x = x;
		this.y = y;
	}
}
