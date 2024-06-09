package HORIZON-6-0-SKIDPROTECTION;

public class BlockBeacon extends BlockContainer
{
    private static final String Õ = "CL_00000197";
    
    public BlockBeacon() {
        super(Material.¥Æ);
        this.Ý(3.0f);
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityBeacon();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityBeacon) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
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
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, placer, stack);
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityBeacon) {
                ((TileEntityBeacon)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final TileEntity var5 = worldIn.HorizonCode_Horizon_È(pos);
        if (var5 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var5).Ø­à();
            worldIn.Ý(pos, this, 1, 0);
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    public static void áŒŠÆ(final World worldIn, final BlockPos p_176450_1_) {
        HttpUtil.HorizonCode_Horizon_È.submit((Runnable)new Runnable() {
            private static final String HorizonCode_Horizon_È = "CL_00002136";
            
            @Override
            public void run() {
                final Chunk var1 = worldIn.à(p_176450_1_);
                for (int var2 = p_176450_1_.Â() - 1; var2 >= 0; --var2) {
                    final BlockPos var3 = new BlockPos(p_176450_1_.HorizonCode_Horizon_È(), var2, p_176450_1_.Ý());
                    if (!var1.Ó(var3)) {
                        break;
                    }
                    final IBlockState var4 = worldIn.Â(var3);
                    if (var4.Ý() == Blocks.áˆºá) {
                        ((WorldServer)worldIn).HorizonCode_Horizon_È(new Runnable() {
                            private static final String Â = "CL_00002135";
                            
                            @Override
                            public void run() {
                                final TileEntity var1 = worldIn.HorizonCode_Horizon_È(var3);
                                if (var1 instanceof TileEntityBeacon) {
                                    ((TileEntityBeacon)var1).Ø­à();
                                    worldIn.Ý(var3, Blocks.áˆºá, 1, 0);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
