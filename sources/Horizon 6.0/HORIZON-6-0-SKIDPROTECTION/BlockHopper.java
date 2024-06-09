package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;

public class BlockHopper extends BlockContainer
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    private static final String ŠÂµà = "CL_00000257";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002106";
            
            public boolean HorizonCode_Horizon_È(final EnumFacing p_180180_1_) {
                return p_180180_1_ != EnumFacing.Â;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EnumFacing)p_apply_1_);
            }
        });
        à¢ = PropertyBool.HorizonCode_Horizon_È("enabled");
    }
    
    public BlockHopper() {
        super(Material.Ó);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockHopper.Õ, EnumFacing.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockHopper.à¢, true));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        final float var7 = 0.125f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var7, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var7);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(1.0f - var7, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var7, 1.0f, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        EnumFacing var9 = facing.Âµá€();
        if (var9 == EnumFacing.Â) {
            var9 = EnumFacing.HorizonCode_Horizon_È;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockHopper.Õ, var9).HorizonCode_Horizon_È(BlockHopper.à¢, true);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityHopper();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, placer, stack);
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityHopper) {
                ((TileEntityHopper)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityHopper) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    private void Âµá€(final World worldIn, final BlockPos p_176427_2_, final IBlockState p_176427_3_) {
        final boolean var4 = !worldIn.áŒŠà(p_176427_2_);
        if (var4 != (boolean)p_176427_3_.HorizonCode_Horizon_È(BlockHopper.à¢)) {
            worldIn.HorizonCode_Horizon_È(p_176427_2_, p_176427_3_.HorizonCode_Horizon_È(BlockHopper.à¢, var4), 4);
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof TileEntityHopper) {
            InventoryHelper.HorizonCode_Horizon_È(worldIn, pos, (IInventory)var4);
            worldIn.Âµá€(pos, this);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public static EnumFacing Âµá€(final int p_176428_0_) {
        return EnumFacing.HorizonCode_Horizon_È(p_176428_0_ & 0x7);
    }
    
    public static boolean Ó(final int p_149917_0_) {
        return (p_149917_0_ & 0x8) != 0x8;
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
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Â;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockHopper.Õ, Âµá€(meta)).HorizonCode_Horizon_È(BlockHopper.à¢, Ó(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockHopper.Õ)).Â();
        if (!(boolean)state.HorizonCode_Horizon_È(BlockHopper.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockHopper.Õ, BlockHopper.à¢ });
    }
}
