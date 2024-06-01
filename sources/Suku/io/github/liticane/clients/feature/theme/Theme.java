package io.github.liticane.clients.feature.theme;


import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.lwjglx.util.vector.Vector2f;

import java.awt.*;
import static net.minecraft.util.Formatting.*;

@Getter
public enum Theme {
    COSMIC("Cosmic", new Color(1, 214, 253), new Color(17, 14, 253), DARK_AQUA),
    FIERY("Fiery", new Color(253, 214, 61), new Color(253, 14, 89), YELLOW),
    FLAWLESS("Flawless", new Color(253, 228, 90), new Color(253, 15, 242), LIGHT_PURPLE),
    LIGHTWEIGHT("Lightweight", new Color(0, 238, 101), new Color(0, 35, 243), AQUA),
    MOONADA("Moonada", new Color(159, 239, 205), new Color(89, 64, 250), BLUE),
    PLEASANT("Pleasant", new Color(16, 190, 250), new Color(237, 13, 206), BLUE),
    RASPBERRY("Raspberry", new Color(253, 235, 189), new Color(218, 28, 128), LIGHT_PURPLE),
    PINK("Pink", new Color(212, 50, 148), Color.white, LIGHT_PURPLE),
    VIOLET("Violet", new Color(197, 193, 247), new Color(108, 61, 158), DARK_PURPLE),
    AMETHYS("Amethys", new Color(152, 96, 193), new Color(89, 22, 248), DARK_PURPLE),
    BLOOD("Blood", new Color(203, 57, 114), new Color(190, 19, 22), DARK_RED),
    BLURBERRY("Blue Berry", new Color(50, 57, 255), new Color(50, 10, 255), DARK_RED),
    BLOODY("Bloody", new Color(203, 57, 114), new Color(82, 20, 8), DARK_RED),
    FLOW("Flow", new Color(203, 57, 114), Color.white, RED),
    WHITE("White", Color.white, Color.white, BLUE),
    AQUAMARINE("Aquamarine", new Color(127, 255, 212), Color.white, RED);

    private final String themeName;
    private final Color firstColor, secondColor, thirdColor;
    private final Formatting chatAccentColor;
    private final boolean triColor;

    Theme(String themeName, Color firstColor, Color secondColor, Formatting chatAccentColor) {
        this.themeName = themeName;
        this.firstColor = this.thirdColor = firstColor;
        this.secondColor = secondColor;
        this.chatAccentColor = chatAccentColor;
        this.triColor = false;
    }

    Theme(String themeName, Color firstColor, Color secondColor, Color thirdColor, Formatting chatAccentColor) {
        this.themeName = themeName;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.thirdColor = thirdColor;
        this.chatAccentColor = chatAccentColor;
        this.triColor = true;
    }

    public Color getAccentColor(Vector2f screenCoordinates) {
        if (this.triColor) {
            double blendFactor = this.getBlendFactor(screenCoordinates);

            if (blendFactor <= 0.5)
                return mixColors(getSecondColor(), getFirstColor(), blendFactor * 2D);
            else
                return mixColors(getThirdColor(), getSecondColor(), (blendFactor - 0.5) * 2D);
        }

        return mixColors(getFirstColor(), getSecondColor(), getBlendFactor(screenCoordinates));
    }

    static Color mixColors(final Color color1, final Color color2, final double percent) {
        final double inverse_percent = 1.0 - percent;
        final int redPart = (int) (color1.getRed() * percent + color2.getRed() * inverse_percent);
        final int greenPart = (int) (color1.getGreen() * percent + color2.getGreen() * inverse_percent);
        final int bluePart = (int) (color1.getBlue() * percent + color2.getBlue() * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    public double getBlendFactor(Vector2f screenCoordinates) {
        return Math.sin(System.currentTimeMillis() / 175.0D
                + screenCoordinates.getX() * 0.0007D
                + screenCoordinates.getY() * 0.0007D
        ) * 0.5D + 0.5D;
    }
}
