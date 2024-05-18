package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockStaticLiquid extends BlockLiquid
{
    private static final String Õ = "CL_00000315";
    
    protected BlockStaticLiquid(final Material p_i45429_1_) {
        super(p_i45429_1_);
        this.HorizonCode_Horizon_È(false);
        if (p_i45429_1_ == Material.áŒŠÆ) {
            this.HorizonCode_Horizon_È(true);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.Âµá€(worldIn, pos, state)) {
            this.Ó(worldIn, pos, state);
        }
    }
    
    private void Ó(final World worldIn, final BlockPos p_176370_2_, final IBlockState p_176370_3_) {
        final BlockDynamicLiquid var4 = BlockLiquid.HorizonCode_Horizon_È(this.É);
        worldIn.HorizonCode_Horizon_È(p_176370_2_, var4.¥à().HorizonCode_Horizon_È(BlockStaticLiquid.à¢, p_176370_3_.HorizonCode_Horizon_È(BlockStaticLiquid.à¢)), 2);
        worldIn.HorizonCode_Horizon_È(p_176370_2_, var4, this.HorizonCode_Horizon_È(worldIn));
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.É == Material.áŒŠÆ && worldIn.Çªà¢().Â("doFireTick")) {
            final int var5 = rand.nextInt(3);
            if (var5 > 0) {
                BlockPos var6 = pos;
                for (int var7 = 0; var7 < var5; ++var7) {
                    var6 = var6.Â(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
                    final Block var8 = worldIn.Â(var6).Ý();
                    if (var8.É == Material.HorizonCode_Horizon_È) {
                        if (this.áˆºÑ¢Õ(worldIn, var6)) {
                            worldIn.Â(var6, Blocks.Ô.¥à());
                            return;
                        }
                    }
                    else if (var8.É.Ø­áŒŠá()) {
                        return;
                    }
                }
            }
            else {
                for (int var9 = 0; var9 < 3; ++var9) {
                    final BlockPos var10 = pos.Â(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
                    if (worldIn.Ø­áŒŠá(var10.Ø­áŒŠá()) && this.ÂµÈ(worldIn, var10)) {
                        worldIn.Â(var10.Ø­áŒŠá(), Blocks.Ô.¥à());
                    }
                }
            }
        }
    }
    
    protected boolean áˆºÑ¢Õ(final World worldIn, final BlockPos p_176369_2_) {
        for (final EnumFacing var6 : EnumFacing.values()) {
            if (this.ÂµÈ(worldIn, p_176369_2_.HorizonCode_Horizon_È(var6))) {
                return true;
            }
        }
        return false;
    }
    
    private boolean ÂµÈ(final World worldIn, final BlockPos p_176368_2_) {
        return worldIn.Â(p_176368_2_).Ý().Ó().à();
    }
}
