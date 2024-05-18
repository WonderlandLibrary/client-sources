package net.minecraft.src;

import java.util.*;

class GuiSlotStatsBlock extends GuiSlotStats
{
    final GuiStats theStats;
    
    public GuiSlotStatsBlock(final GuiStats par1GuiStats) {
        super(par1GuiStats);
        this.theStats = par1GuiStats;
        this.field_77266_h = new ArrayList();
        for (final StatCrafting var3 : StatList.objectMineStats) {
            boolean var4 = false;
            final int var5 = var3.getItemID();
            if (GuiStats.getStatsFileWriter(par1GuiStats).writeStat(var3) > 0) {
                var4 = true;
            }
            else if (StatList.objectUseStats[var5] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectUseStats[var5]) > 0) {
                var4 = true;
            }
            else if (StatList.objectCraftStats[var5] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectCraftStats[var5]) > 0) {
                var4 = true;
            }
            if (var4) {
                this.field_77266_h.add(var3);
            }
        }
        this.field_77267_i = new SorterStatsBlock(this, par1GuiStats);
    }
    
    @Override
    protected void func_77222_a(final int par1, final int par2, final Tessellator par3Tessellator) {
        super.func_77222_a(par1, par2, par3Tessellator);
        if (this.field_77262_g == 0) {
            GuiStats.drawSprite(this.theStats, par1 + 115 - 18 + 1, par2 + 1 + 1, 18, 18);
        }
        else {
            GuiStats.drawSprite(this.theStats, par1 + 115 - 18, par2 + 1, 18, 18);
        }
        if (this.field_77262_g == 1) {
            GuiStats.drawSprite(this.theStats, par1 + 165 - 18 + 1, par2 + 1 + 1, 36, 18);
        }
        else {
            GuiStats.drawSprite(this.theStats, par1 + 165 - 18, par2 + 1, 36, 18);
        }
        if (this.field_77262_g == 2) {
            GuiStats.drawSprite(this.theStats, par1 + 215 - 18 + 1, par2 + 1 + 1, 54, 18);
        }
        else {
            GuiStats.drawSprite(this.theStats, par1 + 215 - 18, par2 + 1, 54, 18);
        }
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final StatCrafting var6 = this.func_77257_d(par1);
        final int var7 = var6.getItemID();
        GuiStats.drawItemSprite(this.theStats, par2 + 40, par3, var7);
        this.func_77260_a((StatCrafting)StatList.objectCraftStats[var7], par2 + 115, par3, par1 % 2 == 0);
        this.func_77260_a((StatCrafting)StatList.objectUseStats[var7], par2 + 165, par3, par1 % 2 == 0);
        this.func_77260_a(var6, par2 + 215, par3, par1 % 2 == 0);
    }
    
    @Override
    protected String func_77258_c(final int par1) {
        return (par1 == 0) ? "stat.crafted" : ((par1 == 1) ? "stat.used" : "stat.mined");
    }
}
