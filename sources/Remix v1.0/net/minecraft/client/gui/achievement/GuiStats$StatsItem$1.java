package net.minecraft.client.gui.achievement;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;

class GuiStats$StatsItem$1 implements Comparator {
    public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
        final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
        final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
        StatBase var5 = null;
        StatBase var6 = null;
        if (StatsItem.this.field_148217_o == 0) {
            var5 = StatList.objectBreakStats[var3];
            var6 = StatList.objectBreakStats[var4];
        }
        else if (StatsItem.this.field_148217_o == 1) {
            var5 = StatList.objectCraftStats[var3];
            var6 = StatList.objectCraftStats[var4];
        }
        else if (StatsItem.this.field_148217_o == 2) {
            var5 = StatList.objectUseStats[var3];
            var6 = StatList.objectUseStats[var4];
        }
        if (var5 != null || var6 != null) {
            if (var5 == null) {
                return 1;
            }
            if (var6 == null) {
                return -1;
            }
            final int var7 = GuiStats.access$100(StatsItem.this.this$0).writeStat(var5);
            final int var8 = GuiStats.access$100(StatsItem.this.this$0).writeStat(var6);
            if (var7 != var8) {
                return (var7 - var8) * StatsItem.this.field_148215_p;
            }
        }
        return var3 - var4;
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
    }
}