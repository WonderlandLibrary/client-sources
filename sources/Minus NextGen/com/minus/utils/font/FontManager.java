package com.minus.utils.font;

import com.minus.module.impl.Render.Interface;
import com.minus.utils.font.fonts.FontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager {
    public static FontRenderer product_sans_9;

    public void initialize() {
        if (product_sans_9 == null) {
            Font[] fonts;
            try {
                fonts = Font.createFonts(Objects.requireNonNull(Interface.class.getClassLoader().getResourceAsStream("assets.minus-client/fonts/product_sans.ttf")));
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }
            product_sans_9 = new FontRenderer(fonts, 9, 32, 2);
        }
    }
}
