package wwe;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class bdJV extends GuiButton
{
    public FZwI.wwe/bdJV STEn;
    
    public bdJV(final int n, final int n2, final int n3, final int n4, final int n5, final FZwI.wwe/bdJV stEn) {
        super(n, n2, n3, n4, n5, stEn.NKEQ);
        this.STEn = stEn;
    }
    
    public void func_191745_a(final Minecraft minecraft, final int n, final int n2, final float n3) {
        if (this.visible) {
            final FontRenderer fontRenderer = minecraft.fontRenderer;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            boolean hovered;
            if (n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height) {
                hovered = true;
                if (bdJV.\u2007\u200f\u2002\u2001\u200e\u200b\u2002) {
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
            gnNa.wQYt((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), -1873784752);
            if (this.hovered) {
                gnNa.wQYt((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), new Color(-1873784752).darker().getRGB());
            }
            this.mouseDragged(minecraft, n, n2);
            int n4 = 14737632;
            if (!this.enabled) {
                n4 = 10526880;
            }
            XybC.eTim(this.displayString, (float)(this.x + 2), (float)(this.y + 2), n4);
        }
    }
}
