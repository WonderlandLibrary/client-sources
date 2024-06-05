package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;

public static class Size
{
    private final World field_150867_a;
    private final EnumFacing.Axis field_150865_b;
    private final EnumFacing field_150866_c;
    private final EnumFacing field_150863_d;
    private int field_150864_e;
    private BlockPos field_150861_f;
    private int field_150862_g;
    private int field_150868_h;
    
    public Size(final World worldIn, BlockPos p_i45694_2_, final EnumFacing.Axis p_i45694_3_) {
        this.field_150864_e = 0;
        this.field_150867_a = worldIn;
        this.field_150865_b = p_i45694_3_;
        if (p_i45694_3_ == EnumFacing.Axis.X) {
            this.field_150863_d = EnumFacing.EAST;
            this.field_150866_c = EnumFacing.WEST;
        }
        else {
            this.field_150863_d = EnumFacing.NORTH;
            this.field_150866_c = EnumFacing.SOUTH;
        }
        for (BlockPos var4 = p_i45694_2_; p_i45694_2_.getY() > var4.getY() - 21 && p_i45694_2_.getY() > 0 && this.func_150857_a(worldIn.getBlockState(p_i45694_2_.offsetDown()).getBlock()); p_i45694_2_ = p_i45694_2_.offsetDown()) {}
        final int var5 = this.func_180120_a(p_i45694_2_, this.field_150863_d) - 1;
        if (var5 >= 0) {
            this.field_150861_f = p_i45694_2_.offset(this.field_150863_d, var5);
            this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
                this.field_150861_f = null;
                this.field_150868_h = 0;
            }
        }
        if (this.field_150861_f != null) {
            this.field_150862_g = this.func_150858_a();
        }
    }
    
    protected int func_180120_a(final BlockPos p_180120_1_, final EnumFacing p_180120_2_) {
        int var3;
        for (var3 = 0; var3 < 22; ++var3) {
            final BlockPos var4 = p_180120_1_.offset(p_180120_2_, var3);
            if (!this.func_150857_a(this.field_150867_a.getBlockState(var4).getBlock())) {
                break;
            }
            if (this.field_150867_a.getBlockState(var4.offsetDown()).getBlock() != Blocks.obsidian) {
                break;
            }
        }
        final Block var5 = this.field_150867_a.getBlockState(p_180120_1_.offset(p_180120_2_, var3)).getBlock();
        return (var5 == Blocks.obsidian) ? var3 : 0;
    }
    
    protected int func_150858_a() {
        this.field_150862_g = 0;
    Label_0181:
        while (this.field_150862_g < 21) {
            for (int var1 = 0; var1 < this.field_150868_h; ++var1) {
                final BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1).offsetUp(this.field_150862_g);
                Block var3 = this.field_150867_a.getBlockState(var2).getBlock();
                if (!this.func_150857_a(var3)) {
                    break Label_0181;
                }
                if (var3 == Blocks.portal) {
                    ++this.field_150864_e;
                }
                if (var1 == 0) {
                    var3 = this.field_150867_a.getBlockState(var2.offset(this.field_150863_d)).getBlock();
                    if (var3 != Blocks.obsidian) {
                        break Label_0181;
                    }
                }
                else if (var1 == this.field_150868_h - 1) {
                    var3 = this.field_150867_a.getBlockState(var2.offset(this.field_150866_c)).getBlock();
                    if (var3 != Blocks.obsidian) {
                        break Label_0181;
                    }
                }
            }
            ++this.field_150862_g;
        }
        for (int var1 = 0; var1 < this.field_150868_h; ++var1) {
            if (this.field_150867_a.getBlockState(this.field_150861_f.offset(this.field_150866_c, var1).offsetUp(this.field_150862_g)).getBlock() != Blocks.obsidian) {
                this.field_150862_g = 0;
                break;
            }
        }
        if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
        }
        this.field_150861_f = null;
        this.field_150868_h = 0;
        return this.field_150862_g = 0;
    }
    
    protected boolean func_150857_a(final Block p_150857_1_) {
        return p_150857_1_.blockMaterial == Material.air || p_150857_1_ == Blocks.fire || p_150857_1_ == Blocks.portal;
    }
    
    public boolean func_150860_b() {
        return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
    }
    
    public void func_150859_c() {
        for (int var1 = 0; var1 < this.field_150868_h; ++var1) {
            final BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1);
            for (int var3 = 0; var3 < this.field_150862_g; ++var3) {
                this.field_150867_a.setBlockState(var2.offsetUp(var3), Blocks.portal.getDefaultState().withProperty(BlockPortal.field_176550_a, this.field_150865_b), 2);
            }
        }
    }
}
