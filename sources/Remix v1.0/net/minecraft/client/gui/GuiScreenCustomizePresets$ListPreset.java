package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

class ListPreset extends GuiSlot
{
    public int field_178053_u;
    
    public ListPreset() {
        super(GuiScreenCustomizePresets.mc, GuiScreenCustomizePresets.width, GuiScreenCustomizePresets.height, 80, GuiScreenCustomizePresets.height - 32, 38);
        this.field_178053_u = -1;
    }
    
    @Override
    protected int getSize() {
        return GuiScreenCustomizePresets.access$000().size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.field_178053_u = slotIndex;
        GuiScreenCustomizePresets.this.func_175304_a();
        GuiScreenCustomizePresets.access$200(GuiScreenCustomizePresets.this).setText(GuiScreenCustomizePresets.access$000().get(GuiScreenCustomizePresets.access$100(GuiScreenCustomizePresets.this).field_178053_u).field_178954_c.toString());
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.field_178053_u;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    private void func_178051_a(final int p_178051_1_, final int p_178051_2_, final ResourceLocation p_178051_3_) {
        final int var4 = p_178051_1_ + 5;
        GuiScreenCustomizePresets.this.drawHorizontalLine(var4 - 1, var4 + 32, p_178051_2_ - 1, -2039584);
        GuiScreenCustomizePresets.this.drawHorizontalLine(var4 - 1, var4 + 32, p_178051_2_ + 32, -6250336);
        GuiScreenCustomizePresets.this.drawVerticalLine(var4 - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
        GuiScreenCustomizePresets.this.drawVerticalLine(var4 + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(p_178051_3_);
        final boolean var5 = true;
        final boolean var6 = true;
        final Tessellator var7 = Tessellator.getInstance();
        final WorldRenderer var8 = var7.getWorldRenderer();
        var8.startDrawingQuads();
        var8.addVertexWithUV(var4 + 0, p_178051_2_ + 32, 0.0, 0.0, 1.0);
        var8.addVertexWithUV(var4 + 32, p_178051_2_ + 32, 0.0, 1.0, 1.0);
        var8.addVertexWithUV(var4 + 32, p_178051_2_ + 0, 0.0, 1.0, 0.0);
        var8.addVertexWithUV(var4 + 0, p_178051_2_ + 0, 0.0, 0.0, 0.0);
        var7.draw();
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final Info var7 = GuiScreenCustomizePresets.access$000().get(p_180791_1_);
        this.func_178051_a(p_180791_2_, p_180791_3_, var7.field_178953_b);
        GuiScreenCustomizePresets.this.fontRendererObj.drawString(var7.field_178955_a, p_180791_2_ + 32 + 10, p_180791_3_ + 14, 16777215);
    }
}
