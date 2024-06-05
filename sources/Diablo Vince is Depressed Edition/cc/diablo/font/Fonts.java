/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.font;

import cc.diablo.font.MCFontRenderer;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
    public static final MCFontRenderer tabGuiIconFont = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Client/diablo/font/Icon-Font.ttf"), 22.0f, 0), true, true);
    public static final MCFontRenderer clickGuiIconFont = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Client/diablo/font/skeetfont.ttf"), 36.0f, 0), true, true);
    public static final MCFontRenderer diablotest = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Client/diablo/font/Montserrat-Medium.ttf"), 14.0f, 0), true, true);
    public static final MCFontRenderer diablotest2 = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Client/diablo/font/verdana.ttf"), 20.0f, 0), true, true);

    private static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}

