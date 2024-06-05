package white.floor.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import white.floor.Main;
import white.floor.features.impl.display.CustomFont;

import java.awt.*;
import java.io.InputStream;


public class Fonts {
	
    Font byb;
	
	public static float blob(String fontName, int fontSize, final String text, float x, float y, int color, final boolean shadow) {

		CFontRenderer cf = new CFontRenderer(getFontTTF(fontName, fontSize), true, true);
		
		return cf.drawString(text, x, y, color, shadow);
	}
    public static CFontRenderer sfui11 = new CFontRenderer(getFontTTF("sf-ui", 11), true, true);
    public static CFontRenderer sfui12 = new CFontRenderer(getFontTTF("sf-ui", 12), true, true);
    public static CFontRenderer sfui13 = new CFontRenderer(getFontTTF("sf-ui", 13), true, true);
    public static CFontRenderer sfui14 = new CFontRenderer(getFontTTF("sf-ui", 14), true, true);
    public static CFontRenderer sfui15 = new CFontRenderer(getFontTTF("sf-ui", 15), true, true);
    public static CFontRenderer sfui16 = new CFontRenderer(getFontTTF("sf-ui", 16), true, true);
    public static CFontRenderer sfui18 = new CFontRenderer(getFontTTF("sf-ui", 18), true, true);

    public static CFontRenderer urw11 = new CFontRenderer(getFontTTF("urw", 11), true, true);
    public static CFontRenderer urw12 = new CFontRenderer(getFontTTF("urw", 12), true, true);
    public static CFontRenderer urw13 = new CFontRenderer(getFontTTF("urw", 13), true, true);
    public static CFontRenderer urw14 = new CFontRenderer(getFontTTF("urw", 14), true, true);
    public static CFontRenderer urw15 = new CFontRenderer(getFontTTF("urw", 15), true, true);
    public static CFontRenderer urw16 = new CFontRenderer(getFontTTF("urw", 16), true, true);
    public static CFontRenderer urw17 = new CFontRenderer(getFontTTF("urw", 17), true, true);
    public static CFontRenderer urw18 = new CFontRenderer(getFontTTF("urw", 18), true, true);
    public static CFontRenderer urw19 = new CFontRenderer(getFontTTF("urw", 19), true, true);
    public static CFontRenderer urw20 = new CFontRenderer(getFontTTF("urw", 20), true, true);
    public static CFontRenderer urw21 = new CFontRenderer(getFontTTF("urw", 21), true, true);
    public static CFontRenderer urw22 = new CFontRenderer(getFontTTF("urw", 22), true, true);
    public static CFontRenderer urw23 = new CFontRenderer(getFontTTF("urw", 23), true, true);


    public static CFontRenderer roboto_16 = new CFontRenderer(getFontTTF("roboto", 16), true, true);
    public static CFontRenderer roboto_13 = new CFontRenderer(getFontTTF("roboto", 13), true, true);
    
    public static CFontRenderer neverlose500_13 = new CFontRenderer(getFontTTF("neverlose500", 13), true, true);
    public static CFontRenderer neverlose500_14 = new CFontRenderer(getFontTTF("neverlose500", 14), true, true);
    public static CFontRenderer neverlose500_15 = new CFontRenderer(getFontTTF("neverlose500", 15), true, true);
    public static CFontRenderer neverlose500_16 = new CFontRenderer(getFontTTF("neverlose500", 16), true, true);
    public static CFontRenderer neverlose500_17 = new CFontRenderer(getFontTTF("neverlose500", 17), true, true);
    public static CFontRenderer neverlose500_18 = new CFontRenderer(getFontTTF("neverlose500", 18), true, true);

    public static CFontRenderer icons_14 = new CFontRenderer(getFontTTF("icons", 14), true, true);
    public static CFontRenderer icons_15 = new CFontRenderer(getFontTTF("icons", 15), true, true);
    public static CFontRenderer icons_16 = new CFontRenderer(getFontTTF("icons", 16), true, true);
    public static CFontRenderer icons_17 = new CFontRenderer(getFontTTF("icons", 17), true, true);
    public static CFontRenderer icons_18 = new CFontRenderer(getFontTTF("icons", 18), true, true);
    public static CFontRenderer icons_19 = new CFontRenderer(getFontTTF("icons", 19), true, true);
    public static CFontRenderer icons_20 = new CFontRenderer(getFontTTF("icons", 20), true, true);
    public static CFontRenderer icons_25 = new CFontRenderer(getFontTTF("icons", 25), true, true);
    public static CFontRenderer icons_30 = new CFontRenderer(getFontTTF("icons", 30), true, true);
    
    public static CFontRenderer elegant_14 = new CFontRenderer(getFontTTF("elegent", 14), true, true);
    public static CFontRenderer elegant_15 = new CFontRenderer(getFontTTF("elegent", 15), true, true);
    public static CFontRenderer elegant_16 = new CFontRenderer(getFontTTF("elegent", 16), true, true);
    public static CFontRenderer elegant_17 = new CFontRenderer(getFontTTF("elegent", 17), true, true);
    public static CFontRenderer elegant_18 = new CFontRenderer(getFontTTF("elegent", 18), true, true);
    public static CFontRenderer elegant_19 = new CFontRenderer(getFontTTF("elegent", 19), true, true);
    public static CFontRenderer elegant_20 = new CFontRenderer(getFontTTF("elegent", 20), true, true);
    public static CFontRenderer elegant_25 = new CFontRenderer(getFontTTF("elegent", 25), true, true);
    public static CFontRenderer elegant_30 = new CFontRenderer(getFontTTF("elegent", 30), true, true);

    public static CFontRenderer stylesicons_14 = new CFontRenderer(getFontTTF("stylesicons", 14), true, true);
    public static CFontRenderer stylesicons_15 = new CFontRenderer(getFontTTF("stylesicons", 15), true, true);
    public static CFontRenderer stylesicons_16 = new CFontRenderer(getFontTTF("stylesicons", 16), true, true);
    public static CFontRenderer stylesicons_17 = new CFontRenderer(getFontTTF("stylesicons", 17), true, true);
    public static CFontRenderer stylesicons_18 = new CFontRenderer(getFontTTF("stylesicons", 18), true, true);
    public static CFontRenderer stylesicons_19 = new CFontRenderer(getFontTTF("stylesicons", 19), true, true);
    public static CFontRenderer stylesicons_20 = new CFontRenderer(getFontTTF("stylesicons", 20), true, true);

    public static CFontRenderer geliat_15 = new CFontRenderer(getFontTTF("geliat", 15), true, true);
    public static CFontRenderer geliat_16 = new CFontRenderer(getFontTTF("geliat", 16), true, true);
    public static CFontRenderer geliat_17 = new CFontRenderer(getFontTTF("geliat", 17), true, true);
    public static CFontRenderer geliat_18 = new CFontRenderer(getFontTTF("geliat", 18), true, true);
    public static CFontRenderer geliat_19 = new CFontRenderer(getFontTTF("geliat", 19), true, true);


    public static Font getFontTTF(String name, int size) {
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

    public static CFontRenderer getFontRender() {
        CFontRenderer font = Fonts.sfui18;
        String mode = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(CustomFont.class),"FontList").getValString();
        switch (mode.toLowerCase()) {
            case "myseo":
                font = Fonts.neverlose500_18;
                break;
            case "sfui":
                font = Fonts.sfui18;
                break;
            case "urw":
                font = Fonts.urw19;
                break;
        }
        return font;
    }
}
