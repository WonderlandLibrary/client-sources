package net.minecraft.client.gui.achievement;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.resources.*;

class StatsMobsList extends GuiSlot
{
    private final List field_148222_l;
    
    public StatsMobsList(final Minecraft mcIn) {
        super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, GuiStats.access$1400(GuiStats.this).FONT_HEIGHT * 4);
        this.field_148222_l = Lists.newArrayList();
        this.setShowSelectionBox(false);
        for (final EntityList.EntityEggInfo var4 : EntityList.entityEggs.values()) {
            if (GuiStats.access$100(GuiStats.this).writeStat(var4.field_151512_d) > 0 || GuiStats.access$100(GuiStats.this).writeStat(var4.field_151513_e) > 0) {
                this.field_148222_l.add(var4);
            }
        }
    }
    
    @Override
    protected int getSize() {
        return this.field_148222_l.size();
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
        return this.getSize() * GuiStats.access$1500(GuiStats.this).FONT_HEIGHT * 4;
    }
    
    @Override
    protected void drawBackground() {
        GuiStats.this.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final EntityList.EntityEggInfo var7 = this.field_148222_l.get(p_180791_1_);
        final String var8 = I18n.format("entity." + EntityList.getStringFromID(var7.spawnedID) + ".name", new Object[0]);
        final int var9 = GuiStats.access$100(GuiStats.this).writeStat(var7.field_151512_d);
        final int var10 = GuiStats.access$100(GuiStats.this).writeStat(var7.field_151513_e);
        String var11 = I18n.format("stat.entityKills", var9, var8);
        String var12 = I18n.format("stat.entityKilledBy", var8, var10);
        if (var9 == 0) {
            var11 = I18n.format("stat.entityKills.none", var8);
        }
        if (var10 == 0) {
            var12 = I18n.format("stat.entityKilledBy.none", var8);
        }
        GuiStats.this.drawString(GuiStats.access$1600(GuiStats.this), var8, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
        GuiStats.this.drawString(GuiStats.access$1700(GuiStats.this), var11, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.access$1800(GuiStats.this).FONT_HEIGHT, (var9 == 0) ? 6316128 : 9474192);
        GuiStats.this.drawString(GuiStats.access$1900(GuiStats.this), var12, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.access$2000(GuiStats.this).FONT_HEIGHT * 2, (var10 == 0) ? 6316128 : 9474192);
    }
}
