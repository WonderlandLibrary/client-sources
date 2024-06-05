// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.font;

import net.minecraft.client.Minecraft;
import java.awt.Font;
import net.minecraft.client.gui.FontRenderer;

public class CFontRenderer extends CFont
{
    private FontRenderer mcFontRendererObj;
    private boolean mcFont;
    
    public CFontRenderer(final Font font) {
        super(font);
        this.mcFontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public CFontRenderer(final Font font, final int characterCount) {
        super(font, characterCount);
        this.mcFontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public CFontRenderer(final Font font, final boolean fractionalMetrics) {
        super(font, fractionalMetrics);
        this.mcFontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public CFontRenderer(final Font font, final int characterCount, final boolean fractionalMetrics) {
        super(font, characterCount, fractionalMetrics);
        this.mcFontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
    
    public void setMcFont(final boolean balls) {
        this.mcFont = balls;
    }
    
    public boolean getMcFont() {
        return this.mcFont;
    }
    
    @Override
    public void drawString(final String text, final float x, final float y, final int color) {
        if (this.mcFont) {
            this.mcFontRendererObj.drawString(text, (float)(int)x, (float)(int)y, color);
        }
        else {
            super.drawString(text, x, y, color);
        }
    }
    
    @Override
    public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        if (this.mcFont) {
            this.mcFontRendererObj.drawStringWithShadow(text, x, y, color);
        }
        else {
            super.drawStringWithShadow(text, x, y, color);
        }
    }
    
    @Override
    public float getWidth(final String text) {
        if (this.mcFont) {
            return (float)this.mcFontRendererObj.getStringWidth(text);
        }
        return super.getWidth(text);
    }
    
    @Override
    public float getHeight(final String text) {
        if (this.mcFont) {
            return 8.0f;
        }
        return super.getHeight(text);
    }
}
