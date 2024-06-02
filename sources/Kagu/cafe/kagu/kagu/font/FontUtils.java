/**
 * 
 */
package cafe.kagu.kagu.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lavaflowglow
 *
 */
public class FontUtils {
	
	/**
	 * Called when the client starts, does nothing but load the class and by extension calls the <clinit\> method which in return loads all the fonts
	 */
	public static void start() {
		
	}
	
	// Roboto fonts
	public static final FontRenderer ROBOTO_LIGHT_10 = new FontRenderer(getFontFromInputStream(getFontInputStream("Roboto-Light.ttf"), 20, Font.PLAIN), 0.5);
	public static final FontRenderer ROBOTO_REGULAR_10 = new FontRenderer(getFontFromInputStream(getFontInputStream("Roboto-Regular.ttf"), 20, Font.PLAIN), 0.5);
	
	// The CS:GO font
	public static final FontRenderer STRATUM2_REGULAR_8_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2_regular.ttf"), 15, Font.PLAIN), 0.5, true);
	public static final FontRenderer STRATUM2_REGULAR_10_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2_regular.ttf"), 20, Font.PLAIN), 0.5, true);
	public static final FontRenderer STRATUM2_REGULAR_10 = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2_regular.ttf"), 20, Font.PLAIN), 0.5);
	public static final FontRenderer STRATUM2_REGULAR_13_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2_regular.ttf"), 26, Font.PLAIN), 0.5);
	public static final FontRenderer STRATUM2_MEDIUM_13_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2-medium.ttf"), 26, Font.PLAIN), 0.5, true);
	public static final FontRenderer STRATUM2_MEDIUM_15_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2-medium.ttf"), 30, Font.PLAIN), 0.5, true);
	public static final FontRenderer STRATUM2_MEDIUM_18_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("stratum2-medium.ttf"), 36, Font.PLAIN), 0.5, true);
	
	// The CS:GO font in normal font object form for the obs proof hud
	public static final Font AWT_STRATUM2_MEDIUM_26 = getFontFromInputStream(getFontInputStream("stratum2-medium.ttf"), 26, Font.PLAIN);
	
	// San francisco
	public static final FontRenderer SAN_FRANCISCO_REGULAR_16_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("SFUIDisplay-Regular.ttf"), 32, Font.PLAIN), 0.5, true);
	public static final FontRenderer SAN_FRANCISCO_REGULAR_10_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("SFUIDisplay-Regular.ttf"), 20, Font.PLAIN), 0.5, true);
	public static final FontRenderer SAN_FRANCISCO_THIN_10_AA = new FontRenderer(getFontFromInputStream(getFontInputStream("SFUIDisplay-Light.ttf"), 20, Font.PLAIN), 0.5, true);
	
	// San francisco in normal font object form for the obs proof hud
	public static final Font AWT_SAN_FRANCISCO_REGULAR_20 = getFontFromInputStream(getFontInputStream("SFUIDisplay-Regular.ttf"), 20, Font.PLAIN);
	public static final Font AWT_SAN_FRANCISCO_THIN_20 = getFontFromInputStream(getFontInputStream("SFUIDisplay-Light.ttf"), 20, Font.PLAIN);
	
	// Small pixel
	public static final FontRenderer SMALL_PIXEL_4 = new FontRenderer(getFontFromInputStream(getFontInputStream("small_pixel.ttf"), 16, Font.PLAIN), 0.25);
	public static final FontRenderer SMALL_PIXEL_10 = new FontRenderer(getFontFromInputStream(getFontInputStream("small_pixel.ttf"), 40, Font.PLAIN), 0.25);
	public static final FontRenderer SMALL_PIXEL_16 = new FontRenderer(getFontFromInputStream(getFontInputStream("small_pixel.ttf"), 64, Font.PLAIN), 0.25);
	
	// Mojang logo font
	public static final FontRenderer MOJANG_LOGO_20 = new FontRenderer(getFontFromInputStream(getFontInputStream("gyroscope-mojang.ttf"), 40, Font.PLAIN), 0.5);
	
	// Microsoft logo font
	public static final FontRenderer MICROSOFT_LOGO_16 = new FontRenderer(getFontFromInputStream(getFontInputStream("Segoe UI.ttf"), 32, Font.PLAIN), 0.5);
	
	// Verdana, used in the skeet hud
	public static final FontRenderer VERDANA_BOLD_10 = new FontRenderer(getFontFromInputStream(getFontInputStream("verdana-bold.ttf"), 20, Font.PLAIN), 0.5);
	
	/**
	 * Gets a font from an inputstream
	 * @param in The inputstream
	 * @param size The font size
	 * @param style The font style
	 * @return The loaded font
	 */
	public static Font getFontFromInputStream(InputStream in, float size, int style) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(style, size); // Load font
			in.close(); // Close stream
			return font; // Return
		} catch (FontFormatException | IOException e) {
			return null; // Could not load font
		}
	}
	
	private static InputStream getFontInputStream(String fontName) {
		return FontUtils.class.getClassLoader().getResourceAsStream("assets/minecraft/Kagu/fonts/" + fontName);
	}
	
}
