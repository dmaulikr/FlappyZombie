package ve.com.edgaralexanderfr.util;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class MathTools {
	public static float angleBetween (float x1, float y1, float x2, float y2) {
		float angle = (float) Math.atan2(y2 - y1, x2 - x1);

		return (y2 > y1) ? angle : angle + (float) (Math.PI * 2) ;
	}

	public static boolean lineIntersectsRectangle (float lineX1, float lineY1, float lineX2, float lineY2, float rectangleX1, float rectangleY1, float rectangleWidth, float rectangleHeight) {
		return new Line2D.Float(lineX1, lineY1, lineX2, lineY2).intersects(new Rectangle(Math.round(rectangleX1), Math.round(rectangleY1), Math.round(rectangleWidth), Math.round(rectangleHeight)));
	}
}
