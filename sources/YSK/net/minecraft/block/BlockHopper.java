package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;

public class BlockHopper extends BlockContainer
{
    private static final String[] I;
    public static final PropertyBool ENABLED;
    public static final PropertyDirection FACING;
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    private void updateState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n;
        if (world.isBlockPowered(blockPos)) {
            n = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        if (n2 != (((boolean)blockState.getValue((IProperty<Boolean>)BlockHopper.ENABLED)) ? 1 : 0)) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockHopper.ENABLED, (boolean)(n2 != 0)), 0x7 ^ 0x3);
        }
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockHopper.I["".length()], (Predicate<EnumFacing>)new Predicate<EnumFacing>() {
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final EnumFacing enumFacing) {
                if (enumFacing != EnumFacing.UP) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
        });
        ENABLED = PropertyBool.create(BlockHopper.I[" ".length()]);
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getFront(n & (0x77 ^ 0x70));
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final float n = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockHopper.FACING;
        array[" ".length()] = BlockHopper.ENABLED;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        EnumFacing enumFacing2 = enumFacing.getOpposite();
        if (enumFacing2 == EnumFacing.UP) {
            enumFacing2 = EnumFacing.DOWN;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockHopper.FACING, enumFacing2).withProperty((IProperty<Comparable>)BlockHopper.ENABLED, " ".length() != 0);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.updateState(world, blockPos, blockState);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockHopper.FACING).getIndex();
        if (!blockState.getValue((IProperty<Boolean>)BlockHopper.ENABLED)) {
            n |= (0x5F ^ 0x57);
        }
        return n;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockHopper.FACING, getFacing(n)).withProperty((IProperty<Comparable>)BlockHopper.ENABLED, isEnabled(n));
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityHopper();
    }
    
    public static boolean isEnabled(final int n) {
        if ((n & (0x91 ^ 0x99)) != (0x6A ^ 0x62)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityHopper) {
                ((TileEntityHopper)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("/)4\u000e(.", "IHWgF");
        BlockHopper.I[" ".length()] = I("\n>5.\u0018\n4", "oPTLt");
    }
    
    public BlockHopper() {
        super(Material.iron, MapColor.stoneColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockHopper.FACING, EnumFacing.DOWN).withProperty((IProperty<Comparable>)BlockHopper.ENABLED, (boolean)(" ".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181732_P);
        }
        return " ".length() != 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.updateState(world, blockPos, blockState);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
}
