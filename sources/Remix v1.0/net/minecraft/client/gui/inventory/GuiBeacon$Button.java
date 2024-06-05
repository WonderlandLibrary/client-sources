package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

static class Button extends GuiButton
{
    private final ResourceLocation field_146145_o;
    private final int field_146144_p;
    private final int field_146143_q;
    private boolean field_146142_r;
    
    protected Button(final int p_i1077_1_, final int p_i1077_2_, final int p_i1077_3_, final ResourceLocation p_i1077_4_, final int p_i1077_5_, final int p_i1077_6_) {
        super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
        this.field_146145_o = p_i1077_4_;
        this.field_146144_p = p_i1077_5_;
        this.field_146143_q = p_i1077_6_;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiBeacon.access$000());
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            final short var4 = 219;
            int var5 = 0;
            if (!this.enabled) {
                var5 += this.width * 2;
            }
            else if (this.field_146142_r) {
                var5 += this.width * 1;
            }
            else if (this.hovered) {
                var5 += this.width * 3;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var4, this.width, this.height);
            if (!GuiBeacon.access$000().equals(this.field_146145_o)) {
                mc.getTextureManager().bindTexture(this.field_146145_o);
            }
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
        }
    }
    
    public boolean func_146141_c() {
        return this.field_146142_r;
    }
    
    public void func_146140_b(final boolean p_146140_1_) {
        this.field_146142_r = p_146140_1_;
    }
}
