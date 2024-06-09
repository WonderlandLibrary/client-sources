package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockCommandBlock extends BlockContainer
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000219";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("triggered");
    }
    
    public BlockCommandBlock() {
        super(Material.Ó);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCommandBlock.Õ, false));
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            final boolean var5 = worldIn.áŒŠà(pos);
            final boolean var6 = (boolean)state.HorizonCode_Horizon_È(BlockCommandBlock.Õ);
            if (var5 && !var6) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCommandBlock.Õ, true), 4);
                worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
            }
            else if (!var5 && var6) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCommandBlock.Õ, false), 4);
            }
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final TileEntity var5 = worldIn.HorizonCode_Horizon_È(pos);
        if (var5 instanceof TileEntityCommandBlock) {
            ((TileEntityCommandBlock)var5).HorizonCode_Horizon_È().HorizonCode_Horizon_È(worldIn);
            worldIn.Âµá€(pos, this);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 1;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        return var9 instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)var9).HorizonCode_Horizon_È().HorizonCode_Horizon_È(playerIn);
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        final TileEntity var3 = worldIn.HorizonCode_Horizon_È(pos);
        return (var3 instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)var3).HorizonCode_Horizon_È().à() : 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        if (var6 instanceof TileEntityCommandBlock) {
            final CommandBlockLogic var7 = ((TileEntityCommandBlock)var6).HorizonCode_Horizon_È();
            if (stack.¥Æ()) {
                var7.Â(stack.µà());
            }
            if (!worldIn.ŠÄ) {
                var7.HorizonCode_Horizon_È(worldIn.Çªà¢().Â("sendCommandFeedback"));
            }
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCommandBlock.Õ, (meta & 0x1) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        if (state.HorizonCode_Horizon_È(BlockCommandBlock.Õ)) {
            var2 |= 0x1;
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCommandBlock.Õ });
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockCommandBlock.Õ, false);
    }
}
