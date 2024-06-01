package best.actinium.util.render.font;

import best.actinium.util.IAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class FontUtil implements IAccess {
    /*nah man bold more like bald*/
    public static CustomFontRenderer productBald, productWatermark, product;

    public static void init() {
        productWatermark = new CustomFontRenderer(getFontFromTTF("product-sans-bold", 30, Font.PLAIN), true, true);
        productBald = new CustomFontRenderer(getFontFromTTF("product-sans-bold", 20, Font.PLAIN), true, true);
        product = new CustomFontRenderer(getFontFromTTF("product-sans-regular", 20, Font.PLAIN), true, false);
    }

    private static Font getFontFromTTF(String name, float fontSize, int fontType) {
        Font output = null;

        ResourceLocation fontLocation = new ResourceLocation("actinium/fonts/" + name + ".ttf");

        try {
            output = Font.createFont(fontType, mc.getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
