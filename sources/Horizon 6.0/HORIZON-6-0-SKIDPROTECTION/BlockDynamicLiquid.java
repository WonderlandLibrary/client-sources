package HORIZON-6-0-SKIDPROTECTION;

import java.util.EnumSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Random;

public class BlockDynamicLiquid extends BlockLiquid
{
    int Õ;
    private static final String ŠÂµà = "CL_00000234";
    
    protected BlockDynamicLiquid(final Material p_i45403_1_) {
        super(p_i45403_1_);
    }
    
    private void Ó(final World worldIn, final BlockPos p_180690_2_, final IBlockState p_180690_3_) {
        worldIn.HorizonCode_Horizon_È(p_180690_2_, BlockLiquid.Â(this.É).¥à().HorizonCode_Horizon_È(BlockDynamicLiquid.à¢, p_180690_3_.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢)), 2);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, IBlockState state, final Random rand) {
        int var5 = (int)state.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢);
        byte var6 = 1;
        if (this.É == Material.áŒŠÆ && !worldIn.£à.£á()) {
            var6 = 2;
        }
        int var7 = this.HorizonCode_Horizon_È(worldIn);
        if (var5 > 0) {
            int var8 = -100;
            this.Õ = 0;
            for (final EnumFacing var10 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                var8 = this.Â(worldIn, pos.HorizonCode_Horizon_È(var10), var8);
            }
            int var11 = var8 + var6;
            if (var11 >= 8 || var8 < 0) {
                var11 = -1;
            }
            if (this.Âµá€((IBlockAccess)worldIn, pos.Ø­áŒŠá()) >= 0) {
                final int var12 = this.Âµá€((IBlockAccess)worldIn, pos.Ø­áŒŠá());
                if (var12 >= 8) {
                    var11 = var12;
                }
                else {
                    var11 = var12 + 8;
                }
            }
            if (this.Õ >= 2 && this.É == Material.Ø) {
                final IBlockState var13 = worldIn.Â(pos.Âµá€());
                if (var13.Ý().Ó().Â()) {
                    var11 = 0;
                }
                else if (var13.Ý().Ó() == this.É && (int)var13.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢) == 0) {
                    var11 = 0;
                }
            }
            if (this.É == Material.áŒŠÆ && var5 < 8 && var11 < 8 && var11 > var5 && rand.nextInt(4) != 0) {
                var7 *= 4;
            }
            if (var11 == var5) {
                this.Ó(worldIn, pos, state);
            }
            else if ((var5 = var11) < 0) {
                worldIn.Ø(pos);
            }
            else {
                state = state.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢, var11);
                worldIn.HorizonCode_Horizon_È(pos, state, 2);
                worldIn.HorizonCode_Horizon_È(pos, this, var7);
                worldIn.Â(pos, this);
            }
        }
        else {
            this.Ó(worldIn, pos, state);
        }
        final IBlockState var14 = worldIn.Â(pos.Âµá€());
        if (this.Ø(worldIn, pos.Âµá€(), var14)) {
            if (this.É == Material.áŒŠÆ && worldIn.Â(pos.Âµá€()).Ý().Ó() == Material.Ø) {
                worldIn.Â(pos.Âµá€(), Blocks.Ý.¥à());
                this.áŒŠÆ(worldIn, pos.Âµá€());
                return;
            }
            if (var5 >= 8) {
                this.Â(worldIn, pos.Âµá€(), var14, var5);
            }
            else {
                this.Â(worldIn, pos.Âµá€(), var14, var5 + 8);
            }
        }
        else if (var5 >= 0 && (var5 == 0 || this.à(worldIn, pos.Âµá€(), var14))) {
            final Set var15 = this.áˆºÑ¢Õ(worldIn, pos);
            int var12 = var5 + var6;
            if (var5 >= 8) {
                var12 = 1;
            }
            if (var12 >= 8) {
                return;
            }
            for (final EnumFacing var17 : var15) {
                this.Â(worldIn, pos.HorizonCode_Horizon_È(var17), worldIn.Â(pos.HorizonCode_Horizon_È(var17)), var12);
            }
        }
    }
    
    private void Â(final World worldIn, final BlockPos p_176375_2_, final IBlockState p_176375_3_, final int p_176375_4_) {
        if (this.Ø(worldIn, p_176375_2_, p_176375_3_)) {
            if (p_176375_3_.Ý() != Blocks.Â) {
                if (this.É == Material.áŒŠÆ) {
                    this.áŒŠÆ(worldIn, p_176375_2_);
                }
                else {
                    p_176375_3_.Ý().HorizonCode_Horizon_È(worldIn, p_176375_2_, p_176375_3_, 0);
                }
            }
            worldIn.HorizonCode_Horizon_È(p_176375_2_, this.¥à().HorizonCode_Horizon_È(BlockDynamicLiquid.à¢, p_176375_4_), 3);
        }
    }
    
    private int HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176374_2_, final int p_176374_3_, final EnumFacing p_176374_4_) {
        int var5 = 1000;
        for (final EnumFacing var7 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (var7 != p_176374_4_) {
                final BlockPos var8 = p_176374_2_.HorizonCode_Horizon_È(var7);
                final IBlockState var9 = worldIn.Â(var8);
                if (this.à(worldIn, var8, var9) || (var9.Ý().Ó() == this.É && (int)var9.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢) <= 0)) {
                    continue;
                }
                if (!this.à(worldIn, var8.Âµá€(), var9)) {
                    return p_176374_3_;
                }
                if (p_176374_3_ >= 4) {
                    continue;
                }
                final int var10 = this.HorizonCode_Horizon_È(worldIn, var8, p_176374_3_ + 1, var7.Âµá€());
                if (var10 >= var5) {
                    continue;
                }
                var5 = var10;
            }
        }
        return var5;
    }
    
    private Set áˆºÑ¢Õ(final World worldIn, final BlockPos p_176376_2_) {
        int var3 = 1000;
        final EnumSet var4 = EnumSet.noneOf(EnumFacing.class);
        for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final BlockPos var7 = p_176376_2_.HorizonCode_Horizon_È(var6);
            final IBlockState var8 = worldIn.Â(var7);
            if (!this.à(worldIn, var7, var8) && (var8.Ý().Ó() != this.É || (int)var8.HorizonCode_Horizon_È(BlockDynamicLiquid.à¢) > 0)) {
                int var9;
                if (this.à(worldIn, var7.Âµá€(), worldIn.Â(var7.Âµá€()))) {
                    var9 = this.HorizonCode_Horizon_È(worldIn, var7, 1, var6.Âµá€());
                }
                else {
                    var9 = 0;
                }
                if (var9 < var3) {
                    var4.clear();
                }
                if (var9 > var3) {
                    continue;
                }
                var4.add(var6);
                var3 = var9;
            }
        }
        return var4;
    }
    
    private boolean à(final World worldIn, final BlockPos p_176372_2_, final IBlockState p_176372_3_) {
        final Block var4 = worldIn.Â(p_176372_2_).Ý();
        return var4 instanceof BlockDoor || var4 == Blocks.£Õ || var4 == Blocks.áŒŠÏ || var4 == Blocks.Ðƒáƒ || var4.É == Material.ÇŽÉ || var4.É.Ø­áŒŠá();
    }
    
    protected int Â(final World worldIn, final BlockPos p_176371_2_, final int p_176371_3_) {
        int var4 = this.Âµá€((IBlockAccess)worldIn, p_176371_2_);
        if (var4 < 0) {
            return p_176371_3_;
        }
        if (var4 == 0) {
            ++this.Õ;
        }
        if (var4 >= 8) {
            var4 = 0;
        }
        return (p_176371_3_ >= 0 && var4 >= p_176371_3_) ? p_176371_3_ : var4;
    }
    
    private boolean Ø(final World worldIn, final BlockPos p_176373_2_, final IBlockState p_176373_3_) {
        final Material var4 = p_176373_3_.Ý().Ó();
        return var4 != this.É && var4 != Material.áŒŠÆ && !this.à(worldIn, p_176373_2_, p_176373_3_);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!this.Âµá€(worldIn, pos, state)) {
            worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
        }
    }
}
