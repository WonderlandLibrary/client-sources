// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.alt.design.fonts;

import intent.AquaDev.aqua.utils.UnicodeFontRenderer;

public class FontManager
{
    public UnicodeFontRenderer unicodeBasicFontRenderer;
    public UnicodeFontRenderer arial;
    
    public FontManager() {
        final UnicodeFontRenderer unicodeBasicFontRenderer = this.unicodeBasicFontRenderer;
        this.unicodeBasicFontRenderer = UnicodeFontRenderer.getFontOnPC("Arial", 22, 0, 0.0f, 1.0f);
        final UnicodeFontRenderer unicodeBasicFontRenderer2 = this.unicodeBasicFontRenderer;
        this.arial = UnicodeFontRenderer.getFontOnPC("Arial", 24, 0, 2.0f, 6.0f);
    }
}
