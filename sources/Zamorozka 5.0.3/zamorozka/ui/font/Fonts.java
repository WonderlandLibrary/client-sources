package zamorozka.ui.font;

import net.minecraft.util.ResourceLocation;
import zamorozka.ui.MCUtil;

import java.awt.*;
import java.io.InputStream;

public class Fonts implements MCUtil {
	public static CFontRenderer default30 = new CFontRenderer(getDefault(30), true, true);
	public static CFontRenderer default40 = new CFontRenderer(getDefault(40), true, true);
    public static CFontRenderer default25 = new CFontRenderer(getDefault(25), true, true);
    public static CFontRenderer default22 = new CFontRenderer(getDefault(22), true, true);
    public static CFontRenderer default20 = new CFontRenderer(getDefault(20), true, true);
    public static CFontRenderer default18 = new CFontRenderer(getDefault(18), true, true);
    public static CFontRenderer default16 = new CFontRenderer(getDefault(16), true, true);
    public static CFontRenderer default15 = new CFontRenderer(getDefault(15), true, true);
    public static CFontRenderer default13 = new CFontRenderer(getDefault(13), true, true);
    public static CFontRenderer default14 = new CFontRenderer(getDefault(14), true, true);
    public static CFontRenderer default12 = new CFontRenderer(getDefault(12), true, true);
    public static CFontRenderer default10 = new CFontRenderer(getDefault(10), true, true);
    public static CFontRenderer Tahoma12 = new CFontRenderer(getFontTTF("Tahoma", 12), true, true);
    public static CFontRenderer Tahoma14 = new CFontRenderer(getFontTTF("Tahoma", 14), true, true);
    public static CFontRenderer Tahoma16 = new CFontRenderer(getFontTTF("Tahoma", 16), true, true);
    public static CFontRenderer Tahoma18 = new CFontRenderer(getFontTTF("Tahoma", 18), true, true);
    public static CFontRenderer Tahoma20 = new CFontRenderer(getFontTTF("Tahoma", 20), true, true);
    public static CFontRenderer Tahoma22 = new CFontRenderer(getFontTTF("Tahoma", 22), true, true);
    public static CFontRenderer Tahoma24 = new CFontRenderer(getFontTTF("Tahoma", 24), true, true);
    public static CFontRenderer comfortaa10 = new CFontRenderer(getFontTTF("Comfortaa", 10), true, true);
    public static CFontRenderer comfortaa12 = new CFontRenderer(getFontTTF("Comfortaa", 12), true, true);
    public static CFontRenderer comfortaa15 = new CFontRenderer(getFontTTF("Comfortaa", 15), true, true);
    public static CFontRenderer comfortaa16 = new CFontRenderer(getFontTTF("Comfortaa", 16), true, true);
    public static CFontRenderer comfortaa17 = new CFontRenderer(getFontTTF("Comfortaa", 17), true, true);
    public static CFontRenderer comfortaa18 = new CFontRenderer(getFontTTF("Comfortaa", 18), true, true);
    public static CFontRenderer comfortaa20 = new CFontRenderer(getFontTTF("Comfortaa", 20), true, true);
    public static CFontRenderer comfortaa25 = new CFontRenderer(getFontTTF("Comfortaa", 25), true, true);
    public static CFontRenderer comfortaa30 = new CFontRenderer(getFontTTF("Comfortaa", 30), true, true);
    public static CFontRenderer elliot12 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 12), true, true);
    public static CFontRenderer elliot15 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 15), true, true);
    public static CFontRenderer elliot17 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 17), true, true);
    public static CFontRenderer elliot18 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 18), true, true);
    public static CFontRenderer elliot40 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 40), true, true);
    public static CFontRenderer elliot37 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 37), true, true);
    public static CFontRenderer elliot20 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 20), true, true);
    public static CFontRenderer elliot30 = new CFontRenderer(getFontTTF("ElliotSans-Regular", 30), true, true);
    public static CFontRenderer consolas13 = new CFontRenderer(getFontTTF("consolas", 13), true, true);
    public static CFontRenderer bebas = new CFontRenderer(getFontTTF("bebas-neue-bold", 15), true, true);

    private static Font getFontTTF(String name, int size) {
        Font font;
        try {
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("minecraft/font/" + name + ".ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getFontOTF(String name, int size) {
        Font font;
        try {
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("minecraft/font/" + name + ".otf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getDefault(int size) {
        Font font;
        font = new Font("default", 0, size);
        return font;
    }
}
