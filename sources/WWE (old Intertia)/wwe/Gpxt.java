package wwe;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class Gpxt extends GuiButton
{
    public int xQun;
    public int fobH;
    
    public Gpxt(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        super(n, n2, n3, n4, n5, s);
        this.fobH = 255;
    }
    
    public void func_191745_a(final Minecraft minecraft, final int n, final int n2, final float n3) {
        if (this.visible) {
            final FontRenderer fontRenderer = minecraft.fontRenderer;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            boolean hovered;
            if (n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height) {
                hovered = true;
                if (Gpxt.\u2005\u200c\u2005\u200e\u200f\u2009\u2003) {
                    throw null;
                }
            }
            else {
                hovered = false;
            }
            this.getHoverState(this.hovered = hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (this.hovered) {
                ++this.xQun;
                if (this.xQun > 10) {
                    this.xQun = 10;
                }
                gnNa.Bdzq((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 2.0f, 16777215, new Color(255, 0, 0, this.fobH).getRGB(), this.xQun);
                if (Gpxt.\u2005\u200c\u2005\u200e\u200f\u2009\u2003) {
                    throw null;
                }
            }
            else {
                if (this.xQun > 0) {
                    --this.xQun;
                }
                gnNa.Bdzq((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 2.0f, 16777215, new Color(255, 255, 255, this.fobH).getRGB(), this.xQun);
            }
            this.mouseDragged(minecraft, n, n2);
            int n4 = new Color(224, 224, 224, this.fobH).getRGB();
            if (!this.enabled) {
                n4 = new Color(160, 160, 160, this.fobH).getRGB();
            }
            bozE.SPev.drawString(this.displayString, this.x + 7, this.y + 4, n4);
        }
    }
}
