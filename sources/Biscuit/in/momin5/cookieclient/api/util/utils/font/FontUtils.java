package in.momin5.cookieclient.api.util.utils.font;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.InputStream;

public class FontUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, CustomColor color) {
        if(customFont) {
            return CookieClient.getInstance().customFontRenderer.drawStringWithShadow(text, x, y, color);
        }
        else {
            return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
        }
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return CookieClient.getInstance().customFontRenderer.getStringWidth(string);
        }
        else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return CookieClient.getInstance().customFontRenderer.getHeight();
        }
        else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

    public static Font getFont(String fontName, float size) {
        try {
            InputStream inputStream = FontUtils.class.getResourceAsStream("/assets/font/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();

            return awtClientFont;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("default", Font.PLAIN, (int) size);
        }
    }
}
