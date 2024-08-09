package xyz.mathax.mathaxclient.utils.text;

import xyz.mathax.mathaxclient.utils.render.color.Color;

import java.util.Objects;

public class ColoredText {
    private final String text;

    private final Color color;

    public ColoredText(String text, Color color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ColoredText that = (ColoredText) object;
        return text.equals(that.text) && color.equals(that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, color);
    }
}
