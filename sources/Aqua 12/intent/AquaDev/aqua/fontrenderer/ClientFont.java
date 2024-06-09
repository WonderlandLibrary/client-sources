// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.fontrenderer;

import java.io.InputStream;
import java.awt.Font;

public class ClientFont
{
    public static GlyphPageFontRenderer font(final int size, final String fontName, final boolean ttf) {
        try {
            final InputStream istream = ClientFont.class.getResourceAsStream("/resources/" + fontName + ".ttf");
            final Font myFont = Font.createFont(0, istream).deriveFont((float)size);
            final GlyphPage fontPage = new GlyphPage(ttf ? myFont : new Font(fontName, 0, size), true, true);
            final char[] chars = new char[256];
            for (int i = 0; i < chars.length; ++i) {
                chars[i] = (char)i;
            }
            fontPage.generateGlyphPage(chars);
            fontPage.setupTexture();
            final GlyphPageFontRenderer fontrenderer = new GlyphPageFontRenderer(fontPage, fontPage, fontPage, fontPage);
            return fontrenderer;
        }
        catch (Exception e) {
            final GlyphPage fontPage2 = new GlyphPage(new Font("Arial", 0, size), true, true);
            final char[] chars2 = new char[256];
            for (int j = 0; j < chars2.length; ++j) {
                chars2[j] = (char)j;
            }
            fontPage2.generateGlyphPage(chars2);
            try {
                fontPage2.setupTexture();
            }
            catch (Exception ex) {}
            final GlyphPageFontRenderer fontrenderer2 = new GlyphPageFontRenderer(fontPage2, fontPage2, fontPage2, fontPage2);
            return fontrenderer2;
        }
    }
}
