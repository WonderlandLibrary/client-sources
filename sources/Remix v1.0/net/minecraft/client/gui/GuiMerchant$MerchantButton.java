package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

static class MerchantButton extends GuiButton
{
    private final boolean field_146157_o;
    
    public MerchantButton(final int p_i1095_1_, final int p_i1095_2_, final int p_i1095_3_, final boolean p_i1095_4_) {
        super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 19, "");
        this.field_146157_o = p_i1095_4_;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiMerchant.access$000());
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = 0;
            int var6 = 176;
            if (!this.enabled) {
                var6 += this.width * 2;
            }
            else if (var4) {
                var6 += this.width;
            }
            if (!this.field_146157_o) {
                var5 += this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
}
