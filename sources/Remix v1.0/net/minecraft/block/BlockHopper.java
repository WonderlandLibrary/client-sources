package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;

public class BlockHopper extends BlockContainer
{
    public static final PropertyDirection field_176430_a;
    public static final PropertyBool field_176429_b;
    
    public BlockHopper() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHopper.field_176430_a, EnumFacing.DOWN).withProperty(BlockHopper.field_176429_b, true));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static EnumFacing func_176428_b(final int p_176428_0_) {
        return EnumFacing.getFront(p_176428_0_ & 0x7);
    }
    
    public static boolean getActiveStateFromMetadata(final int p_149917_0_) {
        return (p_149917_0_ & 0x8) != 0x8;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        final float var7 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var7, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var7);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(1.0f - var7, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var7, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        EnumFacing var9 = facing.getOpposite();
        if (var9 == EnumFacing.UP) {
            var9 = EnumFacing.DOWN;
        }
        return this.getDefaultState().withProperty(BlockHopper.field_176430_a, var9).withProperty(BlockHopper.field_176429_b, true);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityHopper();
    }
    
    @Override
    public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasDisplayName()) {
            final TileEntity var6 = worldIn.getTileEntity(pos);
            if (var6 instanceof TileEntityHopper) {
                ((TileEntityHopper)var6).setCustomName(stack.getDisplayName());
            }
        }
    }
    
    @Override
    public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.func_176427_e(worldIn, pos, state);
    }
    
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        final TileEntity var9 = worldIn.getTileEntity(pos);
        if (var9 instanceof TileEntityHopper) {
            playerIn.displayGUIChest((IInventory)var9);
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.func_176427_e(worldIn, pos, state);
    }
    
    private void func_176427_e(final World worldIn, final BlockPos p_176427_2_, final IBlockState p_176427_3_) {
        final boolean var4 = !worldIn.isBlockPowered(p_176427_2_);
        if (var4 != (boolean)p_176427_3_.getValue(BlockHopper.field_176429_b)) {
            worldIn.setBlockState(p_176427_2_, p_176427_3_.withProperty(BlockHopper.field_176429_b, var4), 4);
        }
    }
    
    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.getTileEntity(pos);
        if (var4 instanceof TileEntityHopper) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)var4);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
        return Container.calcRedstoneFromInventory(worldIn.getTileEntity(pos));
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockHopper.field_176430_a, func_176428_b(meta)).withProperty(BlockHopper.field_176429_b, getActiveStateFromMetadata(meta));
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.getValue(BlockHopper.field_176430_a)).getIndex();
        if (!(boolean)state.getValue(BlockHopper.field_176429_b)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHopper.field_176430_a, BlockHopper.field_176429_b });
    }
    
    static {
        field_176430_a = PropertyDirection.create("facing", (Predicate)new Predicate() {
            public boolean func_180180_a(final EnumFacing p_180180_1_) {
                return p_180180_1_ != EnumFacing.UP;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180180_a((EnumFacing)p_apply_1_);
            }
        });
        field_176429_b = PropertyBool.create("enabled");
    }
}
