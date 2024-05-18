package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneDiode
{
    public static final PropertyBool Õ;
    public static final PropertyInteger à¢;
    private static final String Âµà = "CL_00000301";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("locked");
        à¢ = PropertyInteger.HorizonCode_Horizon_È("delay", 1, 4);
    }
    
    protected BlockRedstoneRepeater(final boolean p_i45424_1_) {
        super(p_i45424_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà, EnumFacing.Ý).HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢, 1).HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ, false));
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ, this.Â(worldIn, pos, state));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.áˆºáˆºáŠ.Âµá€) {
            return false;
        }
        worldIn.HorizonCode_Horizon_È(pos, state.Â(BlockRedstoneRepeater.à¢), 3);
        return true;
    }
    
    @Override
    protected int áŒŠÆ(final IBlockState p_176403_1_) {
        return (int)p_176403_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢) * 2;
    }
    
    @Override
    protected IBlockState áˆºÑ¢Õ(final IBlockState p_180674_1_) {
        final Integer var2 = (Integer)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢);
        final Boolean var3 = (Boolean)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ);
        final EnumFacing var4 = (EnumFacing)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà);
        return Blocks.ˆØ.¥à().HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà, var4).HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢, var2).HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ, var3);
    }
    
    @Override
    protected IBlockState ÂµÈ(final IBlockState p_180675_1_) {
        final Integer var2 = (Integer)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢);
        final Boolean var3 = (Boolean)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ);
        final EnumFacing var4 = (EnumFacing)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà);
        return Blocks.áŒŠá.¥à().HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà, var4).HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢, var2).HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ, var3);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ÂµÂ;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ÂµÂ;
    }
    
    @Override
    public boolean Â(final IBlockAccess p_176405_1_, final BlockPos p_176405_2_, final IBlockState p_176405_3_) {
        return this.Ý(p_176405_1_, p_176405_2_, p_176405_3_) > 0;
    }
    
    @Override
    protected boolean Ý(final Block p_149908_1_) {
        return BlockRedstoneDiode.Ø­áŒŠá(p_149908_1_);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.¥à) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà);
            final double var6 = pos.HorizonCode_Horizon_È() + 0.5f + (rand.nextFloat() - 0.5f) * 0.2;
            final double var7 = pos.Â() + 0.4f + (rand.nextFloat() - 0.5f) * 0.2;
            final double var8 = pos.Ý() + 0.5f + (rand.nextFloat() - 0.5f) * 0.2;
            float var9 = -5.0f;
            if (rand.nextBoolean()) {
                var9 = (int)state.HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢) * 2 - 1;
            }
            var9 /= 16.0f;
            final double var10 = var9 * var5.Ø();
            final double var11 = var9 * var5.áˆºÑ¢Õ();
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÉ, var6 + var10, var7, var8 + var11, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ø­áŒŠá(worldIn, pos, state);
        this.Ø(worldIn, pos, state);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà, EnumFacing.Â(meta)).HorizonCode_Horizon_È(BlockRedstoneRepeater.Õ, false).HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢, 1 + (meta >> 2));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockRedstoneRepeater.ŠÂµà)).Ý();
        var3 |= (int)state.HorizonCode_Horizon_È(BlockRedstoneRepeater.à¢) - 1 << 2;
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRedstoneRepeater.ŠÂµà, BlockRedstoneRepeater.à¢, BlockRedstoneRepeater.Õ });
    }
}
