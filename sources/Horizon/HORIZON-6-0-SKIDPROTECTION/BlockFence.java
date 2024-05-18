package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockFence extends Block
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    private static final String Âµà = "CL_00000242";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("north");
        à¢ = PropertyBool.HorizonCode_Horizon_È("east");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("south");
        ¥à = PropertyBool.HorizonCode_Horizon_È("west");
    }
    
    public BlockFence(final Material p_i45721_1_) {
        super(p_i45721_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFence.Õ, false).HorizonCode_Horizon_È(BlockFence.à¢, false).HorizonCode_Horizon_È(BlockFence.ŠÂµà, false).HorizonCode_Horizon_È(BlockFence.¥à, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        final boolean var7 = this.Âµá€((IBlockAccess)worldIn, pos.Ó());
        final boolean var8 = this.Âµá€((IBlockAccess)worldIn, pos.à());
        final boolean var9 = this.Âµá€((IBlockAccess)worldIn, pos.Ø());
        final boolean var10 = this.Âµá€((IBlockAccess)worldIn, pos.áŒŠÆ());
        float var11 = 0.375f;
        float var12 = 0.625f;
        float var13 = 0.375f;
        float var14 = 0.625f;
        if (var7) {
            var13 = 0.0f;
        }
        if (var8) {
            var14 = 1.0f;
        }
        if (var7 || var8) {
            this.HorizonCode_Horizon_È(var11, 0.0f, var13, var12, 1.5f, var14);
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
        var13 = 0.375f;
        var14 = 0.625f;
        if (var9) {
            var11 = 0.0f;
        }
        if (var10) {
            var12 = 1.0f;
        }
        if (var9 || var10 || (!var7 && !var8)) {
            this.HorizonCode_Horizon_È(var11, 0.0f, var13, var12, 1.5f, var14);
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
        if (var7) {
            var13 = 0.0f;
        }
        if (var8) {
            var14 = 1.0f;
        }
        this.HorizonCode_Horizon_È(var11, 0.0f, var13, var12, 1.0f, var14);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final boolean var3 = this.Âµá€(access, pos.Ó());
        final boolean var4 = this.Âµá€(access, pos.à());
        final boolean var5 = this.Âµá€(access, pos.Ø());
        final boolean var6 = this.Âµá€(access, pos.áŒŠÆ());
        float var7 = 0.375f;
        float var8 = 0.625f;
        float var9 = 0.375f;
        float var10 = 0.625f;
        if (var3) {
            var9 = 0.0f;
        }
        if (var4) {
            var10 = 1.0f;
        }
        if (var5) {
            var7 = 0.0f;
        }
        if (var6) {
            var8 = 1.0f;
        }
        this.HorizonCode_Horizon_È(var7, 0.0f, var9, var8, 1.0f, var10);
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
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return false;
    }
    
    public boolean Âµá€(final IBlockAccess p_176524_1_, final BlockPos p_176524_2_) {
        final Block var3 = p_176524_1_.Â(p_176524_2_).Ý();
        return var3 != Blocks.¥ÇªÅ && ((var3 instanceof BlockFence && var3.É == this.É) || var3 instanceof BlockFenceGate || (var3.É.áˆºÑ¢Õ() && var3.áˆºÑ¢Õ() && var3.É != Material.Çªà¢));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        return worldIn.ŠÄ || ItemLead.HorizonCode_Horizon_È(playerIn, worldIn, pos);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return 0;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockFence.Õ, this.Âµá€(worldIn, pos.Ó())).HorizonCode_Horizon_È(BlockFence.à¢, this.Âµá€(worldIn, pos.áŒŠÆ())).HorizonCode_Horizon_È(BlockFence.ŠÂµà, this.Âµá€(worldIn, pos.à())).HorizonCode_Horizon_È(BlockFence.¥à, this.Âµá€(worldIn, pos.Ø()));
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFence.Õ, BlockFence.à¢, BlockFence.¥à, BlockFence.ŠÂµà });
    }
}
