package net.minecraft.client.gui.achievement;

import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.client.renderer.*;

class StatsBlock extends Stats
{
    public StatsBlock(final Minecraft mcIn) {
        super(mcIn);
        this.statsHolder = Lists.newArrayList();
        for (final StatCrafting var4 : StatList.objectMineStats) {
            boolean var5 = false;
            final int var6 = Item.getIdFromItem(var4.func_150959_a());
            if (GuiStats.access$100(GuiStats.this).writeStat(var4) > 0) {
                var5 = true;
            }
            else if (StatList.objectUseStats[var6] != null && GuiStats.access$100(GuiStats.this).writeStat(StatList.objectUseStats[var6]) > 0) {
                var5 = true;
            }
            else if (StatList.objectCraftStats[var6] != null && GuiStats.access$100(GuiStats.this).writeStat(StatList.objectCraftStats[var6]) > 0) {
                var5 = true;
            }
            if (var5) {
                this.statsHolder.add(var4);
            }
        }
        this.statSorter = new Comparator() {
            public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
                final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
                final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
                StatBase var5 = null;
                StatBase var6 = null;
                if (StatsBlock.this.field_148217_o == 2) {
                    var5 = StatList.mineBlockStatArray[var3];
                    var6 = StatList.mineBlockStatArray[var4];
                }
                else if (StatsBlock.this.field_148217_o == 0) {
                    var5 = StatList.objectCraftStats[var3];
                    var6 = StatList.objectCraftStats[var4];
                }
                else if (StatsBlock.this.field_148217_o == 1) {
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
                    final int var7 = GuiStats.access$100(GuiStats.this).writeStat(var5);
                    final int var8 = GuiStats.access$100(GuiStats.this).writeStat(var6);
                    if (var7 != var8) {
                        return (var7 - var8) * StatsBlock.this.field_148215_p;
                    }
                }
                return var3 - var4;
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
            }
        };
    }
    
    @Override
    protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
        super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
        if (this.field_148218_l == 0) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
        }
        if (this.field_148218_l == 1) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
        }
        if (this.field_148218_l == 2) {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
        }
        else {
            GuiStats.access$000(GuiStats.this, p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
        }
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        final StatCrafting var7 = this.func_148211_c(p_180791_1_);
        final Item var8 = var7.func_150959_a();
        GuiStats.access$1000(GuiStats.this, p_180791_2_ + 40, p_180791_3_, var8);
        final int var9 = Item.getIdFromItem(var8);
        this.func_148209_a(StatList.objectCraftStats[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
        this.func_148209_a(StatList.objectUseStats[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
        this.func_148209_a(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
    }
    
    @Override
    protected String func_148210_b(final int p_148210_1_) {
        return (p_148210_1_ == 0) ? "stat.crafted" : ((p_148210_1_ == 1) ? "stat.used" : "stat.mined");
    }
}
