package net.minecraft.client.gui.achievement;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.stats.*;

class StatsGeneral extends GuiSlot
{
    public StatsGeneral(final Minecraft mcIn) {
        super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 10);
        this.setShowSelectionBox(false);
    }
    
    @Override
    protected int getSize() {
        return StatList.generalStats.size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return false;
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 10;
    }
    
    @Override
    protected void drawBackground() {
        GuiStats.this.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final StatBase var7 = StatList.generalStats.get(p_180791_1_);
        GuiStats.this.drawString(GuiStats.access$1100(GuiStats.this), var7.getStatName().getUnformattedText(), p_180791_2_ + 2, p_180791_3_ + 1, (p_180791_1_ % 2 == 0) ? 16777215 : 9474192);
        final String var8 = var7.func_75968_a(GuiStats.access$100(GuiStats.this).writeStat(var7));
        GuiStats.this.drawString(GuiStats.access$1200(GuiStats.this), var8, p_180791_2_ + 2 + 213 - GuiStats.access$1300(GuiStats.this).getStringWidth(var8), p_180791_3_ + 1, (p_180791_1_ % 2 == 0) ? 16777215 : 9474192);
    }
}
