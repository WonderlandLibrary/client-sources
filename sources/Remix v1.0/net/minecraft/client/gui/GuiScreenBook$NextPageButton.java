package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

static class NextPageButton extends GuiButton
{
    private final boolean field_146151_o;
    
    public NextPageButton(final int p_i46316_1_, final int p_i46316_2_, final int p_i46316_3_, final boolean p_i46316_4_) {
        super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
        this.field_146151_o = p_i46316_4_;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            final boolean var4 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(GuiScreenBook.access$000());
            int var5 = 0;
            int var6 = 192;
            if (var4) {
                var5 += 23;
            }
            if (!this.field_146151_o) {
                var6 += 13;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var6, 23, 13);
        }
    }
}
