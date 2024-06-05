package me.rich.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.io.InputStream;


public class Fonts {
	
	public static float blob(String fontName, int fontSize, final String text, float x, float y, int color, final boolean shadow) {

		CFontRenderer cf = new CFontRenderer(getFontTTF(fontName, fontSize), true, true);
		
		return cf.drawString(text, x, y, color, shadow);
	}
	
    public static CFontRenderer sfui14 = new CFontRenderer(getFontTTF("sf-ui", 14), true, true);
    public static CFontRenderer sfui15 = new CFontRenderer(getFontTTF("sf-ui", 15), true, true);
    public static CFontRenderer sfui16 = new CFontRenderer(getFontTTF("sf-ui", 16), true, true);
    public static CFontRenderer sfui18 = new CFontRenderer(getFontTTF("sf-ui", 18), true, true);
    
    public static CFontRenderer roboto_20 = new CFontRenderer(getFontTTF("roboto", 20), true, true);
    public static CFontRenderer roboto_19 = new CFontRenderer(getFontTTF("roboto", 19), true, true);
    public static CFontRenderer roboto_18 = new CFontRenderer(getFontTTF("roboto", 18), true, true);
    public static CFontRenderer roboto_16 = new CFontRenderer(getFontTTF("roboto", 16), true, true);
    public static CFontRenderer roboto_15 = new CFontRenderer(getFontTTF("roboto", 15), true, true);
    public static CFontRenderer roboto_14 = new CFontRenderer(getFontTTF("roboto", 14), true, true);
    public static CFontRenderer roboto_13 = new CFontRenderer(getFontTTF("roboto", 13), true, true);
    
    public static CFontRenderer neverlose500_13 = new CFontRenderer(getFontTTF("neverlose500", 13), true, true);
    public static CFontRenderer neverlose500_14 = new CFontRenderer(getFontTTF("neverlose500", 14), true, true);
    public static CFontRenderer neverlose500_15 = new CFontRenderer(getFontTTF("neverlose500", 15), true, true);
    public static CFontRenderer neverlose500_16 = new CFontRenderer(getFontTTF("neverlose500", 16), true, true);
    public static CFontRenderer neverlose500_17 = new CFontRenderer(getFontTTF("neverlose500", 17), true, true);
    public static CFontRenderer neverlose500_18 = new CFontRenderer(getFontTTF("neverlose500", 18), true, true);
    
    public static CFontRenderer smallestpixel_14 = new CFontRenderer(getFontTTF("smallpixel", 14), true, true);
    public static CFontRenderer smallestpixel_15 = new CFontRenderer(getFontTTF("smallpixel", 15), true, true);
    public static CFontRenderer smallestpixel_16 = new CFontRenderer(getFontTTF("smallpixel", 16), true, true);
    public static CFontRenderer smallestpixel_17 = new CFontRenderer(getFontTTF("smallpixel", 17), true, true);
    
    public static CFontRenderer icons_14 = new CFontRenderer(getFontTTF("icons", 14), true, true);
    public static CFontRenderer icons_15 = new CFontRenderer(getFontTTF("icons", 15), true, true);
    public static CFontRenderer icons_16 = new CFontRenderer(getFontTTF("icons", 16), true, true);
    public static CFontRenderer icons_17 = new CFontRenderer(getFontTTF("icons", 17), true, true);
    public static CFontRenderer icons_18 = new CFontRenderer(getFontTTF("icons", 18), true, true);
    public static CFontRenderer icons_19 = new CFontRenderer(getFontTTF("icons", 19), true, true);
    public static CFontRenderer icons_20 = new CFontRenderer(getFontTTF("icons", 20), true, true);
    
    public static CFontRenderer elegant_14 = new CFontRenderer(getFontTTF("ElegantIcons", 14), true, true);
    public static CFontRenderer elegant_15 = new CFontRenderer(getFontTTF("ElegantIcons", 15), true, true);
    public static CFontRenderer elegant_16 = new CFontRenderer(getFontTTF("ElegantIcons", 16), true, true);
    public static CFontRenderer elegant_17 = new CFontRenderer(getFontTTF("ElegantIcons", 17), true, true);
    public static CFontRenderer elegant_18 = new CFontRenderer(getFontTTF("ElegantIcons", 18), true, true);
    public static CFontRenderer elegant_19 = new CFontRenderer(getFontTTF("ElegantIcons", 19), true, true);
    public static CFontRenderer elegant_20 = new CFontRenderer(getFontTTF("ElegantIcons", 20), true, true);
    
    public static CFontRenderer stylesicons_14 = new CFontRenderer(getFontTTF("stylesicons", 14), true, true);
    public static CFontRenderer stylesicons_15 = new CFontRenderer(getFontTTF("stylesicons", 15), true, true);
    public static CFontRenderer stylesicons_16 = new CFontRenderer(getFontTTF("stylesicons", 16), true, true);
    public static CFontRenderer stylesicons_17 = new CFontRenderer(getFontTTF("stylesicons", 17), true, true);
    public static CFontRenderer stylesicons_18 = new CFontRenderer(getFontTTF("stylesicons", 18), true, true);
    public static CFontRenderer stylesicons_19 = new CFontRenderer(getFontTTF("stylesicons", 19), true, true);
    public static CFontRenderer stylesicons_20 = new CFontRenderer(getFontTTF("stylesicons", 20), true, true);
    
    private static Font getFontTTF(String name, int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/" + name + ".ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
           // ex.printStackTrace();
           // System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}
