package ve.com.edgaralexanderfr.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class GraphicsTools {
	/*
	 * Method extracted from http://stackoverflow.com/questions/368295/how-to-get-real-string-height-in-java
	 */
	public static Rectangle getStringBounds (Graphics2D g2, String string, float x, float y) {
		FontRenderContext fontRenderContext = g2.getFontRenderContext();
        GlyphVector glyphVector             = g2.getFont().createGlyphVector(fontRenderContext, string);

        return glyphVector.getPixelBounds(null, x, y);
	}
}
