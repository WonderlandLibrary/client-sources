// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.fontrenderer.ClientFont;
import intent.AquaDev.aqua.fontrenderer.GlyphPageFontRenderer;

public class GuiButton2 extends GuiButton
{
    GlyphPageFontRenderer fluxButton;
    
    public GuiButton2(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.fluxButton = ClientFont.font(15, "Vision", true);
    }
    
    public GuiButton2(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
        this.fluxButton = ClientFont.font(15, "Vision", true);
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        final int unhovered = Color.decode("#0FA292").getRGB();
        final int hovered = Color.decode("#0C8275").getRGB();
        final int blockedunhovered = Color.decode("#7D7D7D").getRGB();
        final int blockedhovered = Color.decode("#646464").getRGB();
        this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
        RenderUtil.glColor(Color.white.getRGB());
        mc.fontRendererObj.drawString(this.displayString, this.xPosition + this.width / 2, this.yPosition + this.height / 2 - this.fluxButton.getFontHeight() / 2, -1);
    }
}
