package net.minecraft.src;

import org.lwjgl.opengl.*;

class GuiCreateFlatWorldListSlot extends GuiSlot
{
    public int field_82454_a;
    final GuiCreateFlatWorld createFlatWorldGui;
    
    public GuiCreateFlatWorldListSlot(final GuiCreateFlatWorld par1GuiCreateFlatWorld) {
        super(par1GuiCreateFlatWorld.mc, par1GuiCreateFlatWorld.width, par1GuiCreateFlatWorld.height, 43, par1GuiCreateFlatWorld.height - 60, 24);
        this.createFlatWorldGui = par1GuiCreateFlatWorld;
        this.field_82454_a = -1;
    }
    
    private void func_82452_a(final int par1, final int par2, final ItemStack par3ItemStack) {
        this.func_82451_d(par1 + 1, par2 + 1);
        GL11.glEnable(32826);
        if (par3ItemStack != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GuiCreateFlatWorld.getRenderItem();
            RenderItem.renderItemIntoGUI(this.createFlatWorldGui.fontRenderer, this.createFlatWorldGui.mc.renderEngine, par3ItemStack, par1 + 2, par2 + 2);
            RenderHelper.disableStandardItemLighting();
        }
        GL11.glDisable(32826);
    }
    
    private void func_82451_d(final int par1, final int par2) {
        this.func_82450_b(par1, par2, 0, 0);
    }
    
    private void func_82450_b(final int par1, final int par2, final int par3, final int par4) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.createFlatWorldGui.mc.renderEngine.bindTexture("/gui/slot.png");
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + 18, this.createFlatWorldGui.zLevel, (par3 + 0) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var9.addVertexWithUV(par1 + 18, par2 + 18, this.createFlatWorldGui.zLevel, (par3 + 18) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var9.addVertexWithUV(par1 + 18, par2 + 0, this.createFlatWorldGui.zLevel, (par3 + 18) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var9.addVertexWithUV(par1 + 0, par2 + 0, this.createFlatWorldGui.zLevel, (par3 + 0) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var9.draw();
    }
    
    @Override
    protected int getSize() {
        return GuiCreateFlatWorld.func_82271_a(this.createFlatWorldGui).getFlatLayers().size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        this.field_82454_a = par1;
        this.createFlatWorldGui.func_82270_g();
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return par1 == this.field_82454_a;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final FlatLayerInfo var6 = GuiCreateFlatWorld.func_82271_a(this.createFlatWorldGui).getFlatLayers().get(GuiCreateFlatWorld.func_82271_a(this.createFlatWorldGui).getFlatLayers().size() - par1 - 1);
        final ItemStack var7 = (var6.getFillBlock() == 0) ? null : new ItemStack(var6.getFillBlock(), 1, var6.getFillBlockMeta());
        final String var8 = (var7 == null) ? "Air" : Item.itemsList[var6.getFillBlock()].func_77653_i(var7);
        this.func_82452_a(par2, par3, var7);
        this.createFlatWorldGui.fontRenderer.drawString(var8, par2 + 18 + 5, par3 + 3, 16777215);
        String var9;
        if (par1 == 0) {
            var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.top", var6.getLayerCount());
        }
        else if (par1 == GuiCreateFlatWorld.func_82271_a(this.createFlatWorldGui).getFlatLayers().size() - 1) {
            var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.bottom", var6.getLayerCount());
        }
        else {
            var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer", var6.getLayerCount());
        }
        this.createFlatWorldGui.fontRenderer.drawString(var9, par2 + 2 + 213 - this.createFlatWorldGui.fontRenderer.getStringWidth(var9), par3 + 3, 16777215);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.createFlatWorldGui.width - 70;
    }
}
