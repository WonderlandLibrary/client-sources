package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class BlockEndPortal extends BlockContainer
{
    private static final String Õ = "CL_00000236";
    
    protected BlockEndPortal(final Material p_i45404_1_) {
        super(p_i45404_1_);
        this.HorizonCode_Horizon_È(1.0f);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityEndPortal();
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final float var3 = 0.0625f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, var3, 1.0f);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side == EnumFacing.HorizonCode_Horizon_È && super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (entityIn.Æ == null && entityIn.µÕ == null && !worldIn.ŠÄ) {
            entityIn.áŒŠÆ(1);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final double var5 = pos.HorizonCode_Horizon_È() + rand.nextFloat();
        final double var6 = pos.Â() + 0.8f;
        final double var7 = pos.Ý() + rand.nextFloat();
        final double var8 = 0.0;
        final double var9 = 0.0;
        final double var10 = 0.0;
        worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var5, var6, var7, var8, var9, var10, new int[0]);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return null;
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return MapColor.á€;
    }
}
