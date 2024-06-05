package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

public class Rail
{
    private final World field_150660_b;
    private final BlockPos field_180367_c;
    private final BlockRailBase field_180365_d;
    private final boolean field_150656_f;
    private final List field_150657_g;
    private IBlockState field_180366_e;
    
    public Rail(final World worldIn, final BlockPos p_i45739_3_, final IBlockState p_i45739_4_) {
        this.field_150657_g = Lists.newArrayList();
        this.field_150660_b = worldIn;
        this.field_180367_c = p_i45739_3_;
        this.field_180366_e = p_i45739_4_;
        this.field_180365_d = (BlockRailBase)p_i45739_4_.getBlock();
        final EnumRailDirection var5 = (EnumRailDirection)p_i45739_4_.getValue(BlockRailBase.this.func_176560_l());
        this.field_150656_f = this.field_180365_d.isPowered;
        this.func_180360_a(var5);
    }
    
    private void func_180360_a(final EnumRailDirection p_180360_1_) {
        this.field_150657_g.clear();
        switch (SwitchEnumRailDirection.field_180371_a[p_180360_1_.ordinal()]) {
            case 1: {
                this.field_150657_g.add(this.field_180367_c.offsetNorth());
                this.field_150657_g.add(this.field_180367_c.offsetSouth());
                break;
            }
            case 2: {
                this.field_150657_g.add(this.field_180367_c.offsetWest());
                this.field_150657_g.add(this.field_180367_c.offsetEast());
                break;
            }
            case 3: {
                this.field_150657_g.add(this.field_180367_c.offsetWest());
                this.field_150657_g.add(this.field_180367_c.offsetEast().offsetUp());
                break;
            }
            case 4: {
                this.field_150657_g.add(this.field_180367_c.offsetWest().offsetUp());
                this.field_150657_g.add(this.field_180367_c.offsetEast());
                break;
            }
            case 5: {
                this.field_150657_g.add(this.field_180367_c.offsetNorth().offsetUp());
                this.field_150657_g.add(this.field_180367_c.offsetSouth());
                break;
            }
            case 6: {
                this.field_150657_g.add(this.field_180367_c.offsetNorth());
                this.field_150657_g.add(this.field_180367_c.offsetSouth().offsetUp());
                break;
            }
            case 7: {
                this.field_150657_g.add(this.field_180367_c.offsetEast());
                this.field_150657_g.add(this.field_180367_c.offsetSouth());
                break;
            }
            case 8: {
                this.field_150657_g.add(this.field_180367_c.offsetWest());
                this.field_150657_g.add(this.field_180367_c.offsetSouth());
                break;
            }
            case 9: {
                this.field_150657_g.add(this.field_180367_c.offsetWest());
                this.field_150657_g.add(this.field_180367_c.offsetNorth());
                break;
            }
            case 10: {
                this.field_150657_g.add(this.field_180367_c.offsetEast());
                this.field_150657_g.add(this.field_180367_c.offsetNorth());
                break;
            }
        }
    }
    
    private void func_150651_b() {
        for (int var1 = 0; var1 < this.field_150657_g.size(); ++var1) {
            final Rail var2 = this.func_180697_b(this.field_150657_g.get(var1));
            if (var2 != null && var2.func_150653_a(this)) {
                this.field_150657_g.set(var1, var2.field_180367_c);
            }
            else {
                this.field_150657_g.remove(var1--);
            }
        }
    }
    
    private boolean func_180359_a(final BlockPos p_180359_1_) {
        return BlockRailBase.func_176562_d(this.field_150660_b, p_180359_1_) || BlockRailBase.func_176562_d(this.field_150660_b, p_180359_1_.offsetUp()) || BlockRailBase.func_176562_d(this.field_150660_b, p_180359_1_.offsetDown());
    }
    
    private Rail func_180697_b(final BlockPos p_180697_1_) {
        IBlockState var3 = this.field_150660_b.getBlockState(p_180697_1_);
        if (BlockRailBase.func_176563_d(var3)) {
            return new Rail(this.field_150660_b, p_180697_1_, var3);
        }
        BlockPos var4 = p_180697_1_.offsetUp();
        var3 = this.field_150660_b.getBlockState(var4);
        if (BlockRailBase.func_176563_d(var3)) {
            return new Rail(this.field_150660_b, var4, var3);
        }
        var4 = p_180697_1_.offsetDown();
        var3 = this.field_150660_b.getBlockState(var4);
        return BlockRailBase.func_176563_d(var3) ? new Rail(this.field_150660_b, var4, var3) : null;
    }
    
    private boolean func_150653_a(final Rail p_150653_1_) {
        return this.func_180363_c(p_150653_1_.field_180367_c);
    }
    
    private boolean func_180363_c(final BlockPos p_180363_1_) {
        for (int var2 = 0; var2 < this.field_150657_g.size(); ++var2) {
            final BlockPos var3 = this.field_150657_g.get(var2);
            if (var3.getX() == p_180363_1_.getX() && var3.getZ() == p_180363_1_.getZ()) {
                return true;
            }
        }
        return false;
    }
    
    protected int countAdjacentRails() {
        int var1 = 0;
        for (final EnumFacing var3 : EnumFacing.Plane.HORIZONTAL) {
            if (this.func_180359_a(this.field_180367_c.offset(var3))) {
                ++var1;
            }
        }
        return var1;
    }
    
    private boolean func_150649_b(final Rail p_150649_1_) {
        return this.func_150653_a(p_150649_1_) || this.field_150657_g.size() != 2;
    }
    
    private void func_150645_c(final Rail p_150645_1_) {
        this.field_150657_g.add(p_150645_1_.field_180367_c);
        final BlockPos var2 = this.field_180367_c.offsetNorth();
        final BlockPos var3 = this.field_180367_c.offsetSouth();
        final BlockPos var4 = this.field_180367_c.offsetWest();
        final BlockPos var5 = this.field_180367_c.offsetEast();
        final boolean var6 = this.func_180363_c(var2);
        final boolean var7 = this.func_180363_c(var3);
        final boolean var8 = this.func_180363_c(var4);
        final boolean var9 = this.func_180363_c(var5);
        EnumRailDirection var10 = null;
        if (var6 || var7) {
            var10 = EnumRailDirection.NORTH_SOUTH;
        }
        if (var8 || var9) {
            var10 = EnumRailDirection.EAST_WEST;
        }
        if (!this.field_150656_f) {
            if (var7 && var9 && !var6 && !var8) {
                var10 = EnumRailDirection.SOUTH_EAST;
            }
            if (var7 && var8 && !var6 && !var9) {
                var10 = EnumRailDirection.SOUTH_WEST;
            }
            if (var6 && var8 && !var7 && !var9) {
                var10 = EnumRailDirection.NORTH_WEST;
            }
            if (var6 && var9 && !var7 && !var8) {
                var10 = EnumRailDirection.NORTH_EAST;
            }
        }
        if (var10 == EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var2.offsetUp())) {
                var10 = EnumRailDirection.ASCENDING_NORTH;
            }
            if (BlockRailBase.func_176562_d(this.field_150660_b, var3.offsetUp())) {
                var10 = EnumRailDirection.ASCENDING_SOUTH;
            }
        }
        if (var10 == EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var5.offsetUp())) {
                var10 = EnumRailDirection.ASCENDING_EAST;
            }
            if (BlockRailBase.func_176562_d(this.field_150660_b, var4.offsetUp())) {
                var10 = EnumRailDirection.ASCENDING_WEST;
            }
        }
        if (var10 == null) {
            var10 = EnumRailDirection.NORTH_SOUTH;
        }
        this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), var10);
        this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);
    }
    
    private boolean func_180361_d(final BlockPos p_180361_1_) {
        final Rail var2 = this.func_180697_b(p_180361_1_);
        if (var2 == null) {
            return false;
        }
        var2.func_150651_b();
        return var2.func_150649_b(this);
    }
    
    public Rail func_180364_a(final boolean p_180364_1_, final boolean p_180364_2_) {
        final BlockPos var3 = this.field_180367_c.offsetNorth();
        final BlockPos var4 = this.field_180367_c.offsetSouth();
        final BlockPos var5 = this.field_180367_c.offsetWest();
        final BlockPos var6 = this.field_180367_c.offsetEast();
        final boolean var7 = this.func_180361_d(var3);
        final boolean var8 = this.func_180361_d(var4);
        final boolean var9 = this.func_180361_d(var5);
        final boolean var10 = this.func_180361_d(var6);
        EnumRailDirection var11 = null;
        if ((var7 || var8) && !var9 && !var10) {
            var11 = EnumRailDirection.NORTH_SOUTH;
        }
        if ((var9 || var10) && !var7 && !var8) {
            var11 = EnumRailDirection.EAST_WEST;
        }
        if (!this.field_150656_f) {
            if (var8 && var10 && !var7 && !var9) {
                var11 = EnumRailDirection.SOUTH_EAST;
            }
            if (var8 && var9 && !var7 && !var10) {
                var11 = EnumRailDirection.SOUTH_WEST;
            }
            if (var7 && var9 && !var8 && !var10) {
                var11 = EnumRailDirection.NORTH_WEST;
            }
            if (var7 && var10 && !var8 && !var9) {
                var11 = EnumRailDirection.NORTH_EAST;
            }
        }
        if (var11 == null) {
            if (var7 || var8) {
                var11 = EnumRailDirection.NORTH_SOUTH;
            }
            if (var9 || var10) {
                var11 = EnumRailDirection.EAST_WEST;
            }
            if (!this.field_150656_f) {
                if (p_180364_1_) {
                    if (var8 && var10) {
                        var11 = EnumRailDirection.SOUTH_EAST;
                    }
                    if (var9 && var8) {
                        var11 = EnumRailDirection.SOUTH_WEST;
                    }
                    if (var10 && var7) {
                        var11 = EnumRailDirection.NORTH_EAST;
                    }
                    if (var7 && var9) {
                        var11 = EnumRailDirection.NORTH_WEST;
                    }
                }
                else {
                    if (var7 && var9) {
                        var11 = EnumRailDirection.NORTH_WEST;
                    }
                    if (var10 && var7) {
                        var11 = EnumRailDirection.NORTH_EAST;
                    }
                    if (var9 && var8) {
                        var11 = EnumRailDirection.SOUTH_WEST;
                    }
                    if (var8 && var10) {
                        var11 = EnumRailDirection.SOUTH_EAST;
                    }
                }
            }
        }
        if (var11 == EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var3.offsetUp())) {
                var11 = EnumRailDirection.ASCENDING_NORTH;
            }
            if (BlockRailBase.func_176562_d(this.field_150660_b, var4.offsetUp())) {
                var11 = EnumRailDirection.ASCENDING_SOUTH;
            }
        }
        if (var11 == EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var6.offsetUp())) {
                var11 = EnumRailDirection.ASCENDING_EAST;
            }
            if (BlockRailBase.func_176562_d(this.field_150660_b, var5.offsetUp())) {
                var11 = EnumRailDirection.ASCENDING_WEST;
            }
        }
        if (var11 == null) {
            var11 = EnumRailDirection.NORTH_SOUTH;
        }
        this.func_180360_a(var11);
        this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), var11);
        if (p_180364_2_ || this.field_150660_b.getBlockState(this.field_180367_c) != this.field_180366_e) {
            this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);
            for (int var12 = 0; var12 < this.field_150657_g.size(); ++var12) {
                final Rail var13 = this.func_180697_b(this.field_150657_g.get(var12));
                if (var13 != null) {
                    var13.func_150651_b();
                    if (var13.func_150649_b(this)) {
                        var13.func_150645_c(this);
                    }
                }
            }
        }
        return this;
    }
    
    public IBlockState func_180362_b() {
        return this.field_180366_e;
    }
}
