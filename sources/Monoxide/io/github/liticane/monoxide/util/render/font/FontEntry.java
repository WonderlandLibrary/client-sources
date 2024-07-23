package io.github.liticane.monoxide.util.render.font;

import net.minecraft.client.gui.FontRenderer;

public class FontEntry {

    private final String name;
    private final float size;
    private final FontRenderer modernFontRenderer;

    public FontEntry(String name, float size, FontRenderer modernFontRenderer) {
        this.name = name;
        this.size = size;
        this.modernFontRenderer = modernFontRenderer;
    }

    public String getName() {
        return name;
    }

    public float getSize() {
        return size;
    }

    public FontRenderer getModernFontRenderer() {
        return modernFontRenderer;
    }
}
