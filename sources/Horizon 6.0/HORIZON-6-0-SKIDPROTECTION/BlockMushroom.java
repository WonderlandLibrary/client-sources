package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public class BlockMushroom extends BlockBush implements IGrowable
{
    private static final String Õ = "CL_00000272";
    
    protected BlockMushroom() {
        final float var1 = 0.2f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var1 * 2.0f, 0.5f + var1);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Â(final World worldIn, BlockPos pos, final IBlockState state, final Random rand) {
        if (rand.nextInt(25) == 0) {
            int var5 = 5;
            final boolean var6 = true;
            for (final BlockPos var8 : BlockPos.Ý(pos.Â(-4, -1, -4), pos.Â(4, 1, 4))) {
                if (worldIn.Â(var8).Ý() == this && --var5 <= 0) {
                    return;
                }
            }
            BlockPos var9 = pos.Â(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
            for (int var10 = 0; var10 < 4; ++var10) {
                if (worldIn.Ø­áŒŠá(var9) && this.Ó(worldIn, var9, this.¥à())) {
                    pos = var9;
                }
                var9 = pos.Â(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
            }
            if (worldIn.Ø­áŒŠá(var9) && this.Ó(worldIn, var9, this.¥à())) {
                worldIn.HorizonCode_Horizon_È(var9, this.¥à(), 2);
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && this.Ó(worldIn, pos, this.¥à());
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground.HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        if (p_180671_2_.Â() >= 0 && p_180671_2_.Â() < 256) {
            final IBlockState var4 = worldIn.Â(p_180671_2_.Âµá€());
            return var4.Ý() == Blocks.Œáƒ || (var4.Ý() == Blocks.Âµá€ && var4.HorizonCode_Horizon_È(BlockDirt.Õ) == BlockDirt.HorizonCode_Horizon_È.Ý) || (worldIn.á(p_180671_2_) < 13 && this.Ý(var4.Ý()));
        }
        return false;
    }
    
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos p_176485_2_, final IBlockState p_176485_3_, final Random p_176485_4_) {
        worldIn.Ø(p_176485_2_);
        WorldGenBigMushroom var5 = null;
        if (this == Blocks.È) {
            var5 = new WorldGenBigMushroom(0);
        }
        else if (this == Blocks.áŠ) {
            var5 = new WorldGenBigMushroom(1);
        }
        if (var5 != null && var5.HorizonCode_Horizon_È(worldIn, p_176485_4_, p_176485_2_)) {
            return true;
        }
        worldIn.HorizonCode_Horizon_È(p_176485_2_, p_176485_3_, 3);
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return p_180670_2_.nextFloat() < 0.4;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        this.Ø­áŒŠá(worldIn, p_176474_3_, p_176474_4_, p_176474_2_);
    }
}
