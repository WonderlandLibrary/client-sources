package me.Godwhitelight;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class FontUtil {

    public static CustomFontRenderer product;
    public static CustomFontRenderer productWatermark;

    public static void init() {
        product = new CustomFontRenderer(getFontFromTTF("product_sans", 20, Font.PLAIN), true, true);
        productWatermark = new CustomFontRenderer(getFontFromTTF("product_sans_bold", 36, Font.PLAIN), true, true);
    }

    private static Font getFontFromTTF(String name, float fontSize, int fontType) {
        Font output = null;

        ResourceLocation fontLocation = new ResourceLocation("suku/fonts/" + name + ".ttf");

        try {
            output = Font.createFont(fontType, Minecraft.getInstance().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
