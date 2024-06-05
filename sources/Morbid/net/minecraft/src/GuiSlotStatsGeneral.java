package net.minecraft.src;

class GuiSlotStatsGeneral extends GuiSlot
{
    final GuiStats statsGui;
    
    public GuiSlotStatsGeneral(final GuiStats par1GuiStats) {
        super(GuiStats.getMinecraft(par1GuiStats), par1GuiStats.width, par1GuiStats.height, 32, par1GuiStats.height - 64, 10);
        this.statsGui = par1GuiStats;
        this.setShowSelectionBox(false);
    }
    
    @Override
    protected int getSize() {
        return StatList.generalStats.size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return false;
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 10;
    }
    
    @Override
    protected void drawBackground() {
        this.statsGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final StatBase var6 = StatList.generalStats.get(par1);
        this.statsGui.drawString(GuiStats.getFontRenderer1(this.statsGui), StatCollector.translateToLocal(var6.getName()), par2 + 2, par3 + 1, (par1 % 2 == 0) ? 16777215 : 9474192);
        final String var7 = var6.func_75968_a(GuiStats.getStatsFileWriter(this.statsGui).writeStat(var6));
        this.statsGui.drawString(GuiStats.getFontRenderer2(this.statsGui), var7, par2 + 2 + 213 - GuiStats.getFontRenderer3(this.statsGui).getStringWidth(var7), par3 + 1, (par1 % 2 == 0) ? 16777215 : 9474192);
    }
}
