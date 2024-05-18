package HORIZON-6-0-SKIDPROTECTION;

public class BlockFenceGate extends BlockDirectional
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ¥à;
    private static final String Âµà = "CL_00000243";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("open");
        à¢ = PropertyBool.HorizonCode_Horizon_È("powered");
        ¥à = PropertyBool.HorizonCode_Horizon_È("in_wall");
    }
    
    public BlockFenceGate() {
        super(Material.Ø­áŒŠá);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFenceGate.Õ, false).HorizonCode_Horizon_È(BlockFenceGate.à¢, false).HorizonCode_Horizon_È(BlockFenceGate.¥à, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final EnumFacing.HorizonCode_Horizon_È var4 = ((EnumFacing)state.HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà)).á();
        if ((var4 == EnumFacing.HorizonCode_Horizon_È.Ý && (worldIn.Â(pos.Ø()).Ý() == Blocks.Ï­Ó || worldIn.Â(pos.áŒŠÆ()).Ý() == Blocks.Ï­Ó)) || (var4 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È && (worldIn.Â(pos.Ó()).Ý() == Blocks.Ï­Ó || worldIn.Â(pos.à()).Ý() == Blocks.Ï­Ó))) {
            state = state.HorizonCode_Horizon_È(BlockFenceGate.¥à, true);
        }
        return state;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return worldIn.Â(pos.Âµá€()).Ý().Ó().Â() && super.Ø­áŒŠá(worldIn, pos);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (state.HorizonCode_Horizon_È(BlockFenceGate.Õ)) {
            return null;
        }
        final EnumFacing.HorizonCode_Horizon_È var4 = ((EnumFacing)state.HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà)).á();
        return (var4 == EnumFacing.HorizonCode_Horizon_È.Ý) ? new AxisAlignedBB(pos.HorizonCode_Horizon_È(), pos.Â(), pos.Ý() + 0.375f, pos.HorizonCode_Horizon_È() + 1, pos.Â() + 1.5f, pos.Ý() + 0.625f) : new AxisAlignedBB(pos.HorizonCode_Horizon_È() + 0.375f, pos.Â(), pos.Ý(), pos.HorizonCode_Horizon_È() + 0.625f, pos.Â() + 1.5f, pos.Ý() + 1);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing.HorizonCode_Horizon_È var3 = ((EnumFacing)access.Â(pos).HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà)).á();
        if (var3 == EnumFacing.HorizonCode_Horizon_È.Ý) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
        else {
            this.HorizonCode_Horizon_È(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
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
        return (boolean)blockAccess.Â(pos).HorizonCode_Horizon_È(BlockFenceGate.Õ);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà, placer.ˆà¢()).HorizonCode_Horizon_È(BlockFenceGate.Õ, false).HorizonCode_Horizon_È(BlockFenceGate.à¢, false).HorizonCode_Horizon_È(BlockFenceGate.¥à, false);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (state.HorizonCode_Horizon_È(BlockFenceGate.Õ)) {
            state = state.HorizonCode_Horizon_È(BlockFenceGate.Õ, false);
            worldIn.HorizonCode_Horizon_È(pos, state, 2);
        }
        else {
            final EnumFacing var9 = EnumFacing.HorizonCode_Horizon_È(playerIn.É);
            if (state.HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà) == var9.Âµá€()) {
                state = state.HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà, var9);
            }
            state = state.HorizonCode_Horizon_È(BlockFenceGate.Õ, true);
            worldIn.HorizonCode_Horizon_È(pos, state, 2);
        }
        worldIn.HorizonCode_Horizon_È(playerIn, ((boolean)state.HorizonCode_Horizon_È(BlockFenceGate.Õ)) ? 1003 : 1006, pos, 0);
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            final boolean var5 = worldIn.áŒŠà(pos);
            if (var5 || neighborBlock.áŒŠà()) {
                if (var5 && !(boolean)state.HorizonCode_Horizon_È(BlockFenceGate.Õ) && !(boolean)state.HorizonCode_Horizon_È(BlockFenceGate.à¢)) {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFenceGate.Õ, true).HorizonCode_Horizon_È(BlockFenceGate.à¢, true), 2);
                    worldIn.HorizonCode_Horizon_È(null, 1003, pos, 0);
                }
                else if (!var5 && (boolean)state.HorizonCode_Horizon_È(BlockFenceGate.Õ) && (boolean)state.HorizonCode_Horizon_È(BlockFenceGate.à¢)) {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFenceGate.Õ, false).HorizonCode_Horizon_È(BlockFenceGate.à¢, false), 2);
                    worldIn.HorizonCode_Horizon_È(null, 1006, pos, 0);
                }
                else if (var5 != (boolean)state.HorizonCode_Horizon_È(BlockFenceGate.à¢)) {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFenceGate.à¢, var5), 2);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà, EnumFacing.Â(meta)).HorizonCode_Horizon_È(BlockFenceGate.Õ, (meta & 0x4) != 0x0).HorizonCode_Horizon_È(BlockFenceGate.à¢, (meta & 0x8) != 0x0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockFenceGate.ŠÂµà)).Ý();
        if (state.HorizonCode_Horizon_È(BlockFenceGate.à¢)) {
            var3 |= 0x8;
        }
        if (state.HorizonCode_Horizon_È(BlockFenceGate.Õ)) {
            var3 |= 0x4;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFenceGate.ŠÂµà, BlockFenceGate.Õ, BlockFenceGate.à¢, BlockFenceGate.¥à });
    }
}
