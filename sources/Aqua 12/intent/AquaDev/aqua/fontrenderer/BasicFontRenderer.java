// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.fontrenderer;

import java.util.List;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public interface BasicFontRenderer extends IResourceManagerReloadListener
{
    default String getFormatFromString(final String text) {
        String s = "";
        int i = -1;
        final int j = text.length();
        while ((i = text.indexOf(167, i + 1)) != -1) {
            if (i < j - 1) {
                final char c0 = text.charAt(i + 1);
                if (isFormatColor(c0)) {
                    s = "§" + c0;
                }
                else {
                    if (!isFormatSpecial(c0)) {
                        continue;
                    }
                    s = s + "§" + c0;
                }
            }
        }
        return s;
    }
    
    default boolean isFormatColor(final char colorChar) {
        return (colorChar >= '0' && colorChar <= '9') || (colorChar >= 'a' && colorChar <= 'f') || (colorChar >= 'A' && colorChar <= 'F');
    }
    
    default boolean isFormatSpecial(final char formatChar) {
        return (formatChar >= 'k' && formatChar <= 'o') || (formatChar >= 'K' && formatChar <= 'O') || formatChar == 'r' || formatChar == 'R';
    }
    
    int getFontHeight();
    
    int drawStringWithShadow(final String p0, final float p1, final float p2, final int p3);
    
    int drawString(final String p0, final float p1, final float p2, final int p3);
    
    int drawString(final String p0, final float p1, final float p2, final int p3, final boolean p4);
    
    int drawCenteredString(final String p0, final float p1, final float p2, final int p3, final boolean p4);
    
    int getStringWidth(final String p0);
    
    int getCharWidth(final char p0);
    
    boolean getBidiFlag();
    
    void setBidiFlag(final boolean p0);
    
    String wrapFormattedStringToWidth(final String p0, final int p1);
    
    List listFormattedStringToWidth(final String p0, final int p1);
    
    String trimStringToWidth(final String p0, final int p1, final boolean p2);
    
    String trimStringToWidth(final String p0, final int p1);
    
    int getColorCode(final char p0);
    
    boolean isEnabled();
    
    boolean setEnabled(final boolean p0);
    
    void setFontRandomSeed(final long p0);
    
    void drawSplitString(final String p0, final int p1, final int p2, final int p3, final int p4);
    
    int splitStringWidth(final String p0, final int p1);
    
    boolean getUnicodeFlag();
    
    void setUnicodeFlag(final boolean p0);
}
