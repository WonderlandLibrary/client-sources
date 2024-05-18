package wwe;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class dDAG extends GuiButton
{
    int AzaU;
    
    public dDAG(final int n, final int n2, final int n3, final int n4, final int n5, final String s, final int azaU) {
        super(n, n2, n3, n4, n5, s);
        this.AzaU = azaU;
    }
    
    public void func_191745_a(final Minecraft minecraft, final int n, final int n2, final float n3) {
        if (this.visible) {
            final FontRenderer fontRenderer = minecraft.fontRenderer;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            boolean hovered;
            if (n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height) {
                hovered = true;
                if (dDAG.\u2004\u200f) {
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
            gnNa.wQYt((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), this.AzaU);
            if (this.hovered) {
                gnNa.dYaT((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 2.0f, this.AzaU, this.AzaU);
            }
            this.mouseDragged(minecraft, n, n2);
            int n4 = 14737632;
            if (!this.enabled) {
                n4 = 10526880;
            }
            XybC.eTim(this.displayString, (float)(this.x + 7), (float)(this.y + 4), n4);
        }
    }
}
