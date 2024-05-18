package us.dev.direkt.gui.font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FontManager {
    private FontManager() {}

    public static final String DEFAULT_FONT_NAME = "Default Minecraft";

	public static boolean usingCustomFont = false;

	public static final CustomFont GLOBAL_FONT = new CustomFont();
	
	private static final List<String> SYSTEM_FONTS;

    static List<String> getSystemFonts() {
        return Collections.unmodifiableList(SYSTEM_FONTS);
    }

    public static void setFont(String fontName) {
        GLOBAL_FONT.setFont(fontName);
        usingCustomFont = !fontName.equals(DEFAULT_FONT_NAME);
    }

    public static String getFontName() {
        return GLOBAL_FONT.getFontName();
    }

    static {
        Font[] currentFonts;
        (SYSTEM_FONTS = new ArrayList<>((currentFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()).length + 1)).add(DEFAULT_FONT_NAME);
        for (Font f : currentFonts) {
            SYSTEM_FONTS.add(f.getFontName());
        }
    }

}
