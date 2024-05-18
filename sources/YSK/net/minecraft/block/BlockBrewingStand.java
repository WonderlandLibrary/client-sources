package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockBrewingStand extends BlockContainer
{
    private static final String[] I;
    public static final PropertyBool[] HAS_BOTTLE;
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBrewingStand) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181729_M);
        }
        return " ".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.brewing_stand;
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    static {
        I();
        final PropertyBool[] has_BOTTLE = new PropertyBool["   ".length()];
        has_BOTTLE["".length()] = PropertyBool.create(BlockBrewingStand.I["".length()]);
        has_BOTTLE[" ".length()] = PropertyBool.create(BlockBrewingStand.I[" ".length()]);
        has_BOTTLE["  ".length()] = PropertyBool.create(BlockBrewingStand.I["  ".length()]);
        HAS_BOTTLE = has_BOTTLE;
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x47 ^ 0x43])["".length()] = I("\u001a\b=+\u0007\u001d\u001d:\u0018\u0000-Y", "riNte");
        BlockBrewingStand.I[" ".length()] = I("\u001d\u0011\">;\u001a\u0004%\r<*A", "upQaY");
        BlockBrewingStand.I["  ".length()] = I("..\u00052\u0011);\u0002\u0001\u0016\u0019}", "FOvms");
        BlockBrewingStand.I["   ".length()] = I("\u0006\u001f\u0006.G\r\u0019\u00064\u0000\u0001\f07\b\u0001\u000fM-\b\u0002\u000e", "okcCi");
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockBrewingStand.HAS_BOTTLE["".length()];
        array[" ".length()] = BlockBrewingStand.HAS_BOTTLE[" ".length()];
        array["  ".length()] = BlockBrewingStand.HAS_BOTTLE["  ".length()];
        return new BlockState(this, array);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.brewing_stand;
    }
    
    public BlockBrewingStand() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockBrewingStand.HAS_BOTTLE["".length()], "".length() != 0).withProperty((IProperty<Comparable>)BlockBrewingStand.HAS_BOTTLE[" ".length()], "".length() != 0).withProperty((IProperty<Comparable>)BlockBrewingStand.HAS_BOTTLE["  ".length()], (boolean)("".length() != 0)));
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + 0.4f + random.nextFloat() * 0.2f, blockPos.getY() + 0.7f + random.nextFloat() * 0.3f, blockPos.getZ() + 0.4f + random.nextFloat() * 0.2f, 0.0, 0.0, 0.0, new int["".length()]);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState = this.getDefaultState();
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < "   ".length()) {
            final IBlockState blockState2 = blockState;
            final PropertyBool propertyBool = BlockBrewingStand.HAS_BOTTLE[i];
            int n2;
            if ((n & " ".length() << i) > 0) {
                n2 = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            blockState = blockState2.withProperty((IProperty<Comparable>)propertyBool, n2 != 0);
            ++i;
        }
        return blockState;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBrewingStand) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBrewingStand();
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < "   ".length()) {
            if (blockState.getValue((IProperty<Boolean>)BlockBrewingStand.HAS_BOTTLE[i])) {
                length |= " ".length() << i;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand)tileEntity).setName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockBrewingStand.I["   ".length()]);
    }
}
