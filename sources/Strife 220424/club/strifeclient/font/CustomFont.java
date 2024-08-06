package club.strifeclient.font;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomFont {

    @Getter
    private final String name, path;
    private final Map<Integer, CustomFontRenderer> fonts;

    public CustomFont(String name, String path) {
        fonts = new HashMap<>();
        this.name = name;
        this.path = path;
    }

    public boolean setup(final int[] fontSizes) {
        for (int size : fontSizes) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
                fonts.put(size, new CustomFontRenderer(font));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public CustomFontRenderer size(int size) {
        if (fonts.get(size) == null) {
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

}
