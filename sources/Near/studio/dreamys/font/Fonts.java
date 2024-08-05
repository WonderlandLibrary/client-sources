/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package studio.dreamys.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.io.InputStream;

public class Fonts {
    public static GameFontRenderer font35RobotoMedium;
    public static GameFontRenderer font40RobotoMedium;
    public static GameFontRenderer font180RobotoBold;
    public static GameFontRenderer font35MontserratMedium;
    public static FontRenderer mcFont;

    public static void loadFonts() {
        long l = System.currentTimeMillis();

        System.out.println("Loading Fonts.");

        font35RobotoMedium = new GameFontRenderer(getFont("Roboto-Medium.ttf", 35));
        font40RobotoMedium = new GameFontRenderer(getFont("Roboto-Medium.ttf", 40));
        font180RobotoBold = new GameFontRenderer(getFont("Roboto-Bold.ttf", 180));
        font35MontserratMedium = new GameFontRenderer(getFont("Montserrat-SemiBold.ttf", 35));
        mcFont = Minecraft.getMinecraft().fontRendererObj;

        System.out.println("Loaded Fonts. (" + (System.currentTimeMillis() - l) + "ms)");
    }


    private static Font getFont(String fontName, int size) {
        try {
            InputStream inputStream = Fonts.class.getResourceAsStream("/assets/near/" + fontName);
            Font awtClientFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();
            return awtClientFont;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("default", Font.PLAIN, size);
        }
    }
}