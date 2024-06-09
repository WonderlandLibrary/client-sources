package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockLilyPad extends BlockBush
{
    private static final String Õ = "CL_00000332";
    
    protected BlockLilyPad() {
        final float var1 = 0.5f;
        final float var2 = 0.015625f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var2, 0.5f + var1);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        if (collidingEntity == null || !(collidingEntity instanceof EntityBoat)) {
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + this.ŠÄ, pos.Â() + this.Ñ¢á, pos.Ý() + this.ŒÏ, pos.HorizonCode_Horizon_È() + this.Çªà¢, pos.Â() + this.Ê, pos.Ý() + this.ÇŽÉ);
    }
    
    @Override
    public int Ï­Ðƒà() {
        return 7455580;
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        return 7455580;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return 2129968;
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground == Blocks.ÂµÈ;
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        if (p_180671_2_.Â() >= 0 && p_180671_2_.Â() < 256) {
            final IBlockState var4 = worldIn.Â(p_180671_2_.Âµá€());
            return var4.Ý().Ó() == Material.Ø && (int)var4.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0;
        }
        return false;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return 0;
    }
}
