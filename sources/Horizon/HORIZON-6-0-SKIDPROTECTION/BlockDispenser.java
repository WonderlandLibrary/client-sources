package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    public static final RegistryDefaulted ŠÂµà;
    protected Random ¥à;
    private static final String Âµà = "CL_00000229";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing");
        à¢ = PropertyBool.HorizonCode_Horizon_È("triggered");
        ŠÂµà = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }
    
    protected BlockDispenser() {
        super(Material.Âµá€);
        this.¥à = new Random();
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockDispenser.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockDispenser.à¢, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 4;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ý(worldIn, pos, state);
        this.Âµá€(worldIn, pos, state);
    }
    
    private void Âµá€(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            EnumFacing var4 = (EnumFacing)state.HorizonCode_Horizon_È(BlockDispenser.Õ);
            final boolean var5 = worldIn.Â(pos.Ó()).Ý().HorizonCode_Horizon_È();
            final boolean var6 = worldIn.Â(pos.à()).Ý().HorizonCode_Horizon_È();
            if (var4 == EnumFacing.Ý && var5 && !var6) {
                var4 = EnumFacing.Ø­áŒŠá;
            }
            else if (var4 == EnumFacing.Ø­áŒŠá && var6 && !var5) {
                var4 = EnumFacing.Ý;
            }
            else {
                final boolean var7 = worldIn.Â(pos.Ø()).Ý().HorizonCode_Horizon_È();
                final boolean var8 = worldIn.Â(pos.áŒŠÆ()).Ý().HorizonCode_Horizon_È();
                if (var4 == EnumFacing.Âµá€ && var7 && !var8) {
                    var4 = EnumFacing.Ó;
                }
                else if (var4 == EnumFacing.Ó && var8 && !var7) {
                    var4 = EnumFacing.Âµá€;
                }
            }
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockDispenser.Õ, var4).HorizonCode_Horizon_È(BlockDispenser.à¢, false), 2);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityDispenser) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
    }
    
    protected void áŒŠÆ(final World worldIn, final BlockPos p_176439_2_) {
        final BlockSourceImpl var3 = new BlockSourceImpl(worldIn, p_176439_2_);
        final TileEntityDispenser var4 = (TileEntityDispenser)var3.Ø();
        if (var4 != null) {
            final int var5 = var4.Ø­à();
            if (var5 < 0) {
                worldIn.Â(1001, p_176439_2_, 0);
            }
            else {
                final ItemStack var6 = var4.á(var5);
                final IBehaviorDispenseItem var7 = this.HorizonCode_Horizon_È(var6);
                if (var7 != IBehaviorDispenseItem.HorizonCode_Horizon_È) {
                    final ItemStack var8 = var7.HorizonCode_Horizon_È(var3, var6);
                    var4.Ý(var5, (var8.Â == 0) ? null : var8);
                }
            }
        }
    }
    
    protected IBehaviorDispenseItem HorizonCode_Horizon_È(final ItemStack p_149940_1_) {
        return (IBehaviorDispenseItem)BlockDispenser.ŠÂµà.HorizonCode_Horizon_È((p_149940_1_ == null) ? null : p_149940_1_.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final boolean var5 = worldIn.áŒŠà(pos) || worldIn.áŒŠà(pos.Ø­áŒŠá());
        final boolean var6 = (boolean)state.HorizonCode_Horizon_È(BlockDispenser.à¢);
        if (var5 && !var6) {
            worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockDispenser.à¢, true), 4);
        }
        else if (!var5 && var6) {
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockDispenser.à¢, false), 4);
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            this.áŒŠÆ(worldIn, pos);
        }
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityDispenser();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockDispenser.Õ, BlockPistonBase.HorizonCode_Horizon_È(worldIn, pos, placer)).HorizonCode_Horizon_È(BlockDispenser.à¢, false);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockDispenser.Õ, BlockPistonBase.HorizonCode_Horizon_È(worldIn, pos, placer)), 2);
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityDispenser) {
                ((TileEntityDispenser)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof TileEntityDispenser) {
            InventoryHelper.HorizonCode_Horizon_È(worldIn, pos, (IInventory)var4);
            worldIn.Âµá€(pos, this);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    public static IPosition HorizonCode_Horizon_È(final IBlockSource coords) {
        final EnumFacing var1 = Âµá€(coords.à());
        final double var2 = coords.Â() + 0.7 * var1.Ø();
        final double var3 = coords.Ý() + 0.7 * var1.áŒŠÆ();
        final double var4 = coords.Ø­áŒŠá() + 0.7 * var1.áˆºÑ¢Õ();
        return new PositionImpl(var2, var3, var4);
    }
    
    public static EnumFacing Âµá€(final int meta) {
        return EnumFacing.HorizonCode_Horizon_È(meta & 0x7);
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return Container.HorizonCode_Horizon_È(worldIn.HorizonCode_Horizon_È(pos));
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public IBlockState à(final IBlockState state) {
        return this.¥à().HorizonCode_Horizon_È(BlockDispenser.Õ, EnumFacing.Ø­áŒŠá);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockDispenser.Õ, Âµá€(meta)).HorizonCode_Horizon_È(BlockDispenser.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockDispenser.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockDispenser.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockDispenser.Õ, BlockDispenser.à¢ });
    }
}
