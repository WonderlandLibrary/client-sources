package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.util.*;

static class Position
{
    double field_111101_a;
    double field_111100_b;
    
    Position() {
    }
    
    Position(final double p_i1358_1_, final double p_i1358_3_) {
        this.field_111101_a = p_i1358_1_;
        this.field_111100_b = p_i1358_3_;
    }
    
    double func_111099_a(final Position p_111099_1_) {
        final double var2 = this.field_111101_a - p_111099_1_.field_111101_a;
        final double var3 = this.field_111100_b - p_111099_1_.field_111100_b;
        return Math.sqrt(var2 * var2 + var3 * var3);
    }
    
    void func_111095_a() {
        final double var1 = this.func_111096_b();
        this.field_111101_a /= var1;
        this.field_111100_b /= var1;
    }
    
    float func_111096_b() {
        return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
    }
    
    public void func_111094_b(final Position p_111094_1_) {
        this.field_111101_a -= p_111094_1_.field_111101_a;
        this.field_111100_b -= p_111094_1_.field_111100_b;
    }
    
    public boolean func_111093_a(final double p_111093_1_, final double p_111093_3_, final double p_111093_5_, final double p_111093_7_) {
        boolean var9 = false;
        if (this.field_111101_a < p_111093_1_) {
            this.field_111101_a = p_111093_1_;
            var9 = true;
        }
        else if (this.field_111101_a > p_111093_5_) {
            this.field_111101_a = p_111093_5_;
            var9 = true;
        }
        if (this.field_111100_b < p_111093_3_) {
            this.field_111100_b = p_111093_3_;
            var9 = true;
        }
        else if (this.field_111100_b > p_111093_7_) {
            this.field_111100_b = p_111093_7_;
            var9 = true;
        }
        return var9;
    }
    
    public int func_111092_a(final World worldIn) {
        BlockPos var2 = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
        while (var2.getY() > 0) {
            var2 = var2.offsetDown();
            if (worldIn.getBlockState(var2).getBlock().getMaterial() != Material.air) {
                return var2.getY() + 1;
            }
        }
        return 257;
    }
    
    public boolean func_111098_b(final World worldIn) {
        BlockPos var2 = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
        while (var2.getY() > 0) {
            var2 = var2.offsetDown();
            final Material var3 = worldIn.getBlockState(var2).getBlock().getMaterial();
            if (var3 != Material.air) {
                return !var3.isLiquid() && var3 != Material.fire;
            }
        }
        return false;
    }
    
    public void func_111097_a(final Random p_111097_1_, final double p_111097_2_, final double p_111097_4_, final double p_111097_6_, final double p_111097_8_) {
        this.field_111101_a = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_2_, p_111097_6_);
        this.field_111100_b = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_4_, p_111097_8_);
    }
}
