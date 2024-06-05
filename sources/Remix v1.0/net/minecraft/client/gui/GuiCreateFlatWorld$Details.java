package net.minecraft.client.gui;

import net.minecraft.client.renderer.*;
import net.minecraft.world.gen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

class Details extends GuiSlot
{
    public int field_148228_k;
    
    public Details() {
        super(GuiCreateFlatWorld.mc, GuiCreateFlatWorld.width, GuiCreateFlatWorld.height, 43, GuiCreateFlatWorld.height - 60, 24);
        this.field_148228_k = -1;
    }
    
    private void func_148225_a(final int p_148225_1_, final int p_148225_2_, final ItemStack p_148225_3_) {
        this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
        GlStateManager.enableRescaleNormal();
        if (p_148225_3_ != null && p_148225_3_.getItem() != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GuiCreateFlatWorld.this.itemRender.func_175042_a(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.disableRescaleNormal();
    }
    
    private void func_148226_e(final int p_148226_1_, final int p_148226_2_) {
        this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
    }
    
    private void func_148224_c(final int p_148224_1_, final int p_148224_2_, final int p_148224_3_, final int p_148224_4_) {
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
        final double p_178985_1_ = p_148224_1_ + 0;
        final double p_178985_3_ = p_148224_2_ + 18;
        final GuiCreateFlatWorld this$0 = GuiCreateFlatWorld.this;
        worldRenderer.addVertexWithUV(p_178985_1_, p_178985_3_, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
        final WorldRenderer worldRenderer2 = var10;
        final double p_178985_1_2 = p_148224_1_ + 18;
        final double p_178985_3_2 = p_148224_2_ + 18;
        final GuiCreateFlatWorld this$2 = GuiCreateFlatWorld.this;
        worldRenderer2.addVertexWithUV(p_178985_1_2, p_178985_3_2, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
        final WorldRenderer worldRenderer3 = var10;
        final double p_178985_1_3 = p_148224_1_ + 18;
        final double p_178985_3_3 = p_148224_2_ + 0;
        final GuiCreateFlatWorld this$3 = GuiCreateFlatWorld.this;
        worldRenderer3.addVertexWithUV(p_178985_1_3, p_178985_3_3, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
        final WorldRenderer worldRenderer4 = var10;
        final double p_178985_1_4 = p_148224_1_ + 0;
        final double p_178985_3_4 = p_148224_2_ + 0;
        final GuiCreateFlatWorld this$4 = GuiCreateFlatWorld.this;
        worldRenderer4.addVertexWithUV(p_178985_1_4, p_178985_3_4, GuiCreateFlatWorld.zLevel, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
        var9.draw();
    }
    
    @Override
    protected int getSize() {
        return GuiCreateFlatWorld.access$000(GuiCreateFlatWorld.this).getFlatLayers().size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.field_148228_k = slotIndex;
        GuiCreateFlatWorld.this.func_146375_g();
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.field_148228_k;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final FlatLayerInfo var7 = GuiCreateFlatWorld.access$000(GuiCreateFlatWorld.this).getFlatLayers().get(GuiCreateFlatWorld.access$000(GuiCreateFlatWorld.this).getFlatLayers().size() - p_180791_1_ - 1);
        final IBlockState var8 = var7.func_175900_c();
        final Block var9 = var8.getBlock();
        Item var10 = Item.getItemFromBlock(var9);
        ItemStack var11 = (var9 != Blocks.air && var10 != null) ? new ItemStack(var10, 1, var9.getMetaFromState(var8)) : null;
        String var12 = (var11 == null) ? "Air" : var10.getItemStackDisplayName(var11);
        if (var10 == null) {
            if (var9 != Blocks.water && var9 != Blocks.flowing_water) {
                if (var9 == Blocks.lava || var9 == Blocks.flowing_lava) {
                    var10 = Items.lava_bucket;
                }
            }
            else {
                var10 = Items.water_bucket;
            }
            if (var10 != null) {
                var11 = new ItemStack(var10, 1, var9.getMetaFromState(var8));
                var12 = var9.getLocalizedName();
            }
        }
        this.func_148225_a(p_180791_2_, p_180791_3_, var11);
        GuiCreateFlatWorld.this.fontRendererObj.drawString(var12, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 16777215);
        String var13;
        if (p_180791_1_ == 0) {
            var13 = I18n.format("createWorld.customize.flat.layer.top", var7.getLayerCount());
        }
        else if (p_180791_1_ == GuiCreateFlatWorld.access$000(GuiCreateFlatWorld.this).getFlatLayers().size() - 1) {
            var13 = I18n.format("createWorld.customize.flat.layer.bottom", var7.getLayerCount());
        }
        else {
            var13 = I18n.format("createWorld.customize.flat.layer", var7.getLayerCount());
        }
        GuiCreateFlatWorld.this.fontRendererObj.drawString(var13, p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var13), p_180791_3_ + 3, 16777215);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 70;
    }
}
