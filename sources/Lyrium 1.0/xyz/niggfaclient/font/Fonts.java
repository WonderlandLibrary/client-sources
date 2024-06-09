// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.font;

import net.minecraft.client.Minecraft;
import java.awt.Font;
import net.minecraft.util.ResourceLocation;

public class Fonts
{
    public static MCFontRenderer sf15;
    public static MCFontRenderer sf18;
    public static MCFontRenderer sf19;
    public static MCFontRenderer sf20;
    public static MCFontRenderer sf21;
    public static MCFontRenderer sf23;
    public static MCFontRenderer icon26;
    public static MCFontRenderer icons40;
    
    public static Font fontFromTTF(final ResourceLocation fontLocation, final float fontSize) {
        Font output = null;
        try {
            output = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
    
    static {
        Fonts.sf15 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 15.0f), true, false);
        Fonts.sf18 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 18.0f), true, false);
        Fonts.sf19 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 19.0f), true, false);
        Fonts.sf20 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 20.0f), true, false);
        Fonts.sf21 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 21.0f), true, false);
        Fonts.sf23 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/sfpro.ttf"), 23.0f), true, false);
        Fonts.icon26 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/icon-font.ttf"), 26.0f), true, false);
        Fonts.icons40 = new MCFontRenderer(fontFromTTF(new ResourceLocation("client/fonts/icons.ttf"), 40.0f), true, false);
    }
}
