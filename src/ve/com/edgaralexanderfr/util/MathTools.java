package ve.com.edgaralexanderfr.util;

public class MathTools {
	public static float angleBetween (float x1, float y1, float x2, float y2) {
		float angle = (float) Math.atan2(y2 - y1, x2 - x1);

		return (y2 > y1) ? angle : angle + (float) (Math.PI * 2) ;
	}
}
