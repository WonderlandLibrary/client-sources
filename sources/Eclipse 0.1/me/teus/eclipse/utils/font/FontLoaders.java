package me.teus.eclipse.utils.font;

import java.awt.Font;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {
    public static CFontRenderer quicksand21 = new CFontRenderer(FontLoaders.getRoboto(21), true, true);
    public static CFontRenderer tenacityBold21 = new CFontRenderer(FontLoaders.getTenacityBold(21), true, true);
    public static CFontRenderer tenacity21 = new CFontRenderer(FontLoaders.getTenacity(21), true, true);
    public static CFontRenderer tenacity18 = new CFontRenderer(FontLoaders.getTenacity(18), true, true);
    private static Font getRoboto(int size) {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("eclipse/font/quicksand.ttf")).getInputStream();
            Font font = Font.createFont(0, is);
            return font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            return new Font("default", 0, size);
        }
    }

    private static Font getTenacityBold(int size) {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("eclipse/font/tenacitybold.ttf")).getInputStream();
            Font font = Font.createFont(0, is);
            return font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            return new Font("default", 0, size);
        }
    }

    private static Font getTenacity(int size) {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("eclipse/font/tenacity.ttf")).getInputStream();
            Font font = Font.createFont(0, is);
            return font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            return new Font("default", 0, size);
        }
    }
}

