package net.minecraft.client.gui;

import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

class ListSlot extends GuiSlot
{
    public int field_148175_k;
    
    public ListSlot() {
        super(GuiFlatPresets.mc, GuiFlatPresets.width, GuiFlatPresets.height, 80, GuiFlatPresets.height - 37, 24);
        this.field_148175_k = -1;
    }
    
    private void func_178054_a(final int p_178054_1_, final int p_178054_2_, final Item p_178054_3_, final int p_178054_4_) {
        this.func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GuiFlatPresets.this.itemRender.func_175042_a(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2, p_178054_2_ + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    
    private void func_148173_e(final int p_148173_1_, final int p_148173_2_) {
        this.func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
    }
    
    private void func_148171_c(final int p_148171_1_, final int p_148171_2_, final int p_148171_3_, final int p_148171_4_) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Gui.statIcons);
        final float var5 = 0.0078125f;
        final float var6 = 0.0078125f;
        final boolean var7 = true;
        final boolean var8 = true;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        final WorldRenderer worldRenderer = var10;
        final double p_178985_1_ = p_148171_1_ + 0;
        final double p_178985_3_ = p_148171_2_ + 18;
        final GuiFlatPresets this$0 = GuiFlatPresets.this;
        worldRenderer.addVertexWithUV(p_178985_1_, p_178985_3_, GuiFlatPresets.zLevel, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
        final WorldRenderer worldRenderer2 = var10;
        final double p_178985_1_2 = p_148171_1_ + 18;
        final double p_178985_3_2 = p_148171_2_ + 18;
        final GuiFlatPresets this$2 = GuiFlatPresets.this;
        worldRenderer2.addVertexWithUV(p_178985_1_2, p_178985_3_2, GuiFlatPresets.zLevel, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
        final WorldRenderer worldRenderer3 = var10;
        final double p_178985_1_3 = p_148171_1_ + 18;
        final double p_178985_3_3 = p_148171_2_ + 0;
        final GuiFlatPresets this$3 = GuiFlatPresets.this;
        worldRenderer3.addVertexWithUV(p_178985_1_3, p_178985_3_3, GuiFlatPresets.zLevel, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
        final WorldRenderer worldRenderer4 = var10;
        final double p_178985_1_4 = p_148171_1_ + 0;
        final double p_178985_3_4 = p_148171_2_ + 0;
        final GuiFlatPresets this$4 = GuiFlatPresets.this;
        worldRenderer4.addVertexWithUV(p_178985_1_4, p_178985_3_4, GuiFlatPresets.zLevel, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
        var9.draw();
    }
    
    @Override
    protected int getSize() {
        return GuiFlatPresets.access$000().size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.field_148175_k = slotIndex;
        GuiFlatPresets.this.func_146426_g();
        GuiFlatPresets.access$200(GuiFlatPresets.this).setText(GuiFlatPresets.access$000().get(GuiFlatPresets.access$100(GuiFlatPresets.this).field_148175_k).field_148233_c);
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.field_148175_k;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final LayerItem var7 = GuiFlatPresets.access$000().get(p_180791_1_);
        this.func_178054_a(p_180791_2_, p_180791_3_, var7.field_148234_a, var7.field_179037_b);
        GuiFlatPresets.this.fontRendererObj.drawString(var7.field_148232_b, p_180791_2_ + 18 + 5, p_180791_3_ + 6, 16777215);
    }
}
