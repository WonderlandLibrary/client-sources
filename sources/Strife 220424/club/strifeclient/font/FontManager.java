package club.strifeclient.font;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FontManager {

    @Getter
    private final List<CustomFont> fonts;
    public static final int[] FONT_SIZES = new int[]{17, 19, 20, 22, 24};

    @Getter
    private CustomFont defaultFont, currentFont;

    public FontManager() {
        fonts = new ArrayList<>();
    }

    public void init() {
        String basePath = "assets/minecraft/strife/fonts/";
        add(new CustomFont("SF", basePath + "SF.ttf"));
        add(new CustomFont("Circular-B", basePath + "CircularStd-Book.ttf"));
//        add(new CustomFont("Circular-M", basePath + "CircularStd-Medium.ttf"));
        defaultFont = fonts.get(0);
        currentFont = defaultFont;
    }

    public void deInit() {
        fonts.clear();
    }

    public void add(CustomFont... fonts) {
        for (CustomFont font : fonts) {
            if (!font.setup(FONT_SIZES))
                System.out.println("An error has occurred while attempting to load \"" + font.getName() + "\"");
            else System.out.println("Successfully loaded \"" + font.getName() + "\"");
            this.fonts.add(font);
        }
    }

    public CustomFont getFontByName(String name) {
        return fonts.stream().filter(font -> font.getName().equalsIgnoreCase(name)).findFirst().orElse(defaultFont);
    }
}
