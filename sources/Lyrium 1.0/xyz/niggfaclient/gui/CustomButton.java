// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui;

import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class CustomButton extends GuiButton
{
    private final int x;
    private final int y;
    private final int x1;
    private final int y1;
    private final String text;
    
    public CustomButton(final int bId, final int x, final int y, final int x1, final int y1, final String text) {
        super(bId, x, y, x1, y1, text);
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.text = text;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        final boolean isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.x1 && mouseY < this.y + this.y1;
        RenderUtils.drawRoundedRect2(this.x, this.y, this.x + this.x1, this.y + this.y1, 9.0, new Color(0, 0, 0, 100).getRGB());
        mc.bit.drawCenteredString(this.text, (float)(this.x + this.x1 / 2), (float)(this.y + (this.y1 - 8) / 2), isHovered ? Color.WHITE.getRGB() : Color.LIGHT_GRAY.getRGB());
    }
}
