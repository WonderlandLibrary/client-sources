package net.minecraft.src;

class GuiSnooperList extends GuiSlot
{
    final GuiSnooper snooperGui;
    
    public GuiSnooperList(final GuiSnooper par1GuiSnooper) {
        super(par1GuiSnooper.mc, par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.fontRenderer.FONT_HEIGHT + 1);
        this.snooperGui = par1GuiSnooper;
    }
    
    @Override
    protected int getSize() {
        return GuiSnooper.func_74095_a(this.snooperGui).size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        this.snooperGui.fontRenderer.drawString(GuiSnooper.func_74095_a(this.snooperGui).get(par1), 10, par3, 16777215);
        this.snooperGui.fontRenderer.drawString(GuiSnooper.func_74094_b(this.snooperGui).get(par1), 230, par3, 16777215);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.snooperGui.width - 10;
    }
}
