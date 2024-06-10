package me.dinozoid.strife.font;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CustomFont {

    private static final int[] FONT_SIZES = new int[] {12, 14, 15, 16, 18, 19, 20, 22, 24, 30};

    private final String name;
    private final String path;
    private Map<Integer, CustomFontRenderer> fonts;


    public CustomFont(String name, String path) {
        this.name = name;
        this.path = path;
        fonts = new HashMap<>();
    }

    public boolean setup() {
        boolean failed = false;
        for(int size : FONT_SIZES) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
                fonts.put(size, new CustomFontRenderer(font));
            } catch (Exception e) {
                e.printStackTrace();
                failed = true;
                break;
            }
        }
        return !failed;
    }

    public CustomFontRenderer size(int size) {
        if(fonts.get(size) == null) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
                fonts.put(size, new CustomFontRenderer(font));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("WARNING! " + name + " Font does not have " + size + " font size. Temporarily added.");
        }
        return fonts.get(size);
    }

    public String name() {
        return name;
    }

    public String path() {
        return path;
    }
}
