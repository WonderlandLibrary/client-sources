package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class BlockPistonStructureHelper
{
    private final World HorizonCode_Horizon_È;
    private final BlockPos Â;
    private final BlockPos Ý;
    private final EnumFacing Ø­áŒŠá;
    private final List Âµá€;
    private final List Ó;
    private static final String à = "CL_00002033";
    
    public BlockPistonStructureHelper(final World worldIn, final BlockPos p_i45664_2_, final EnumFacing p_i45664_3_, final boolean p_i45664_4_) {
        this.Âµá€ = Lists.newArrayList();
        this.Ó = Lists.newArrayList();
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = p_i45664_2_;
        if (p_i45664_4_) {
            this.Ø­áŒŠá = p_i45664_3_;
            this.Ý = p_i45664_2_.HorizonCode_Horizon_È(p_i45664_3_);
        }
        else {
            this.Ø­áŒŠá = p_i45664_3_.Âµá€();
            this.Ý = p_i45664_2_.HorizonCode_Horizon_È(p_i45664_3_, 2);
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        this.Âµá€.clear();
        this.Ó.clear();
        final Block var1 = this.HorizonCode_Horizon_È.Â(this.Ý).Ý();
        if (!BlockPistonBase.HorizonCode_Horizon_È(var1, this.HorizonCode_Horizon_È, this.Ý, this.Ø­áŒŠá, false)) {
            if (var1.ˆá() != 1) {
                return false;
            }
            this.Ó.add(this.Ý);
            return true;
        }
        else {
            if (!this.HorizonCode_Horizon_È(this.Ý)) {
                return false;
            }
            for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
                final BlockPos var3 = this.Âµá€.get(var2);
                if (this.HorizonCode_Horizon_È.Â(var3).Ý() == Blocks.ÇŽØ­à && !this.Â(var3)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_177251_1_) {
        Block var2 = this.HorizonCode_Horizon_È.Â(p_177251_1_).Ý();
        if (var2.Ó() == Material.HorizonCode_Horizon_È) {
            return true;
        }
        if (!BlockPistonBase.HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È, p_177251_1_, this.Ø­áŒŠá, false)) {
            return true;
        }
        if (p_177251_1_.equals(this.Â)) {
            return true;
        }
        if (this.Âµá€.contains(p_177251_1_)) {
            return true;
        }
        int var3 = 1;
        if (var3 + this.Âµá€.size() > 12) {
            return false;
        }
        while (var2 == Blocks.ÇŽØ­à) {
            final BlockPos var4 = p_177251_1_.HorizonCode_Horizon_È(this.Ø­áŒŠá.Âµá€(), var3);
            var2 = this.HorizonCode_Horizon_È.Â(var4).Ý();
            if (var2.Ó() == Material.HorizonCode_Horizon_È || !BlockPistonBase.HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È, var4, this.Ø­áŒŠá, false)) {
                break;
            }
            if (var4.equals(this.Â)) {
                break;
            }
            if (++var3 + this.Âµá€.size() > 12) {
                return false;
            }
        }
        int var5 = 0;
        for (int var6 = var3 - 1; var6 >= 0; --var6) {
            this.Âµá€.add(p_177251_1_.HorizonCode_Horizon_È(this.Ø­áŒŠá.Âµá€(), var6));
            ++var5;
        }
        int var6 = 1;
        while (true) {
            final BlockPos var7 = p_177251_1_.HorizonCode_Horizon_È(this.Ø­áŒŠá, var6);
            final int var8 = this.Âµá€.indexOf(var7);
            if (var8 > -1) {
                this.HorizonCode_Horizon_È(var5, var8);
                for (int var9 = 0; var9 <= var8 + var5; ++var9) {
                    final BlockPos var10 = this.Âµá€.get(var9);
                    if (this.HorizonCode_Horizon_È.Â(var10).Ý() == Blocks.ÇŽØ­à && !this.Â(var10)) {
                        return false;
                    }
                }
                return true;
            }
            var2 = this.HorizonCode_Horizon_È.Â(var7).Ý();
            if (var2.Ó() == Material.HorizonCode_Horizon_È) {
                return true;
            }
            if (!BlockPistonBase.HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È, var7, this.Ø­áŒŠá, true) || var7.equals(this.Â)) {
                return false;
            }
            if (var2.ˆá() == 1) {
                this.Ó.add(var7);
                return true;
            }
            if (this.Âµá€.size() >= 12) {
                return false;
            }
            this.Âµá€.add(var7);
            ++var5;
            ++var6;
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_177255_1_, final int p_177255_2_) {
        final ArrayList var3 = Lists.newArrayList();
        final ArrayList var4 = Lists.newArrayList();
        final ArrayList var5 = Lists.newArrayList();
        var3.addAll(this.Âµá€.subList(0, p_177255_2_));
        var4.addAll(this.Âµá€.subList(this.Âµá€.size() - p_177255_1_, this.Âµá€.size()));
        var5.addAll(this.Âµá€.subList(p_177255_2_, this.Âµá€.size() - p_177255_1_));
        this.Âµá€.clear();
        this.Âµá€.addAll(var3);
        this.Âµá€.addAll(var4);
        this.Âµá€.addAll(var5);
    }
    
    private boolean Â(final BlockPos p_177250_1_) {
        for (final EnumFacing var5 : EnumFacing.values()) {
            if (var5.á() != this.Ø­áŒŠá.á() && !this.HorizonCode_Horizon_È(p_177250_1_.HorizonCode_Horizon_È(var5))) {
                return false;
            }
        }
        return true;
    }
    
    public List Â() {
        return this.Âµá€;
    }
    
    public List Ý() {
        return this.Ó;
    }
}
