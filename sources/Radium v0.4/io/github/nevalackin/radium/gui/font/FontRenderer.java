package io.github.nevalackin.radium.gui.font;

public interface FontRenderer {

    int drawString(String text, float x, float y, int color);

    int drawStringWithShadow(String text, float x, float y, int color);

    float getWidth(String text);

    default float getHeight(String text) {
        return 11.0F;
    };

}
