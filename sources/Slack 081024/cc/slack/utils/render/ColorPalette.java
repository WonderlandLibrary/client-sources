package cc.slack.utils.render;

import lombok.Getter;

import java.awt.*;

@Getter
public enum ColorPalette {

    Background(new Color(20, 20, 20)),
    Primary(new Color(35, 35, 35)),
    Secondary(new Color(16, 16, 16));
    final Color color;

    ColorPalette(Color color) {
        this.color = color;
    }

    public int getRGB() {
        return color.getRGB();
    }
}
