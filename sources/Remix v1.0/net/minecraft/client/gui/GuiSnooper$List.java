package net.minecraft.client.gui;

class List extends GuiSlot
{
    public List() {
        super(GuiSnooper.mc, GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
    }
    
    @Override
    protected int getSize() {
        return GuiSnooper.access$000(GuiSnooper.this).size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.access$000(GuiSnooper.this).get(p_180791_1_), 10, p_180791_3_, 16777215);
        GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.access$100(GuiSnooper.this).get(p_180791_1_), 230, p_180791_3_, 16777215);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 10;
    }
}
