package net.minecraft.src;

import org.lwjgl.opengl.*;

class GuiFlatPresetsListSlot extends GuiSlot
{
    public int field_82459_a;
    final GuiFlatPresets flatPresetsGui;
    
    public GuiFlatPresetsListSlot(final GuiFlatPresets par1) {
        super(par1.mc, par1.width, par1.height, 80, par1.height - 37, 24);
        this.flatPresetsGui = par1;
        this.field_82459_a = -1;
    }
    
    private void func_82457_a(final int par1, final int par2, final int par3) {
        this.func_82456_d(par1 + 1, par2 + 1);
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        GuiFlatPresets.getPresetIconRenderer();
        RenderItem.renderItemIntoGUI(this.flatPresetsGui.fontRenderer, this.flatPresetsGui.mc.renderEngine, new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
    }
    
    private void func_82456_d(final int par1, final int par2) {
        this.func_82455_b(par1, par2, 0, 0);
    }
    
    private void func_82455_b(final int par1, final int par2, final int par3, final int par4) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.flatPresetsGui.mc.renderEngine.bindTexture("/gui/slot.png");
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + 18, this.flatPresetsGui.zLevel, (par3 + 0) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var9.addVertexWithUV(par1 + 18, par2 + 18, this.flatPresetsGui.zLevel, (par3 + 18) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var9.addVertexWithUV(par1 + 18, par2 + 0, this.flatPresetsGui.zLevel, (par3 + 18) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var9.addVertexWithUV(par1 + 0, par2 + 0, this.flatPresetsGui.zLevel, (par3 + 0) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var9.draw();
    }
    
    @Override
    protected int getSize() {
        return GuiFlatPresets.getPresets().size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        this.field_82459_a = par1;
        this.flatPresetsGui.func_82296_g();
        GuiFlatPresets.func_82298_b(this.flatPresetsGui).setText(GuiFlatPresets.getPresets().get(GuiFlatPresets.func_82292_a(this.flatPresetsGui).field_82459_a).presetData);
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return par1 == this.field_82459_a;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final GuiFlatPresetsItem var6 = GuiFlatPresets.getPresets().get(par1);
        this.func_82457_a(par2, par3, var6.iconId);
        this.flatPresetsGui.fontRenderer.drawString(var6.presetName, par2 + 18 + 5, par3 + 6, 16777215);
    }
}
