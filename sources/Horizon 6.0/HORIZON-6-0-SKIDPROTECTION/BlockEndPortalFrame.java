package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;
import com.google.common.base.Predicate;

public class BlockEndPortalFrame extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    private static final String ŠÂµà = "CL_00000237";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyBool.HorizonCode_Horizon_È("eye");
    }
    
    public BlockEndPortalFrame() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockEndPortalFrame.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, false));
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        if (worldIn.Â(pos).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
            this.HorizonCode_Horizon_È(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
        this.ŠÄ();
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockEndPortalFrame.Õ, placer.ˆà¢().Âµá€()).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, false);
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return worldIn.Â(pos).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢) ? 15 : 0;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, (meta & 0x4) != 0x0).HorizonCode_Horizon_È(BlockEndPortalFrame.Õ, EnumFacing.Â(meta & 0x3));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockEndPortalFrame.Õ)).Ý();
        if (state.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
            var3 |= 0x4;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockEndPortalFrame.Õ, BlockEndPortalFrame.à¢ });
    }
}
