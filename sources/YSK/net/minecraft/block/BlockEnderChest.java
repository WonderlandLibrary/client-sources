package net.minecraft.block;

import com.google.common.base.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockEnderChest extends BlockContainer
{
    private static final String[] I;
    public static final PropertyDirection FACING;
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000e\u0015\u0017\u0010 \u000f", "httyN");
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockEnderChest.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockEnderChest.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0xB1 ^ 0xB9;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockEnderChest.FACING, enumFacing);
    }
    
    @Override
    public int getRenderType() {
        return "  ".length();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        int i = "".length();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (i < "   ".length()) {
            final int n = random.nextInt("  ".length()) * "  ".length() - " ".length();
            final int n2 = random.nextInt("  ".length()) * "  ".length() - " ".length();
            world.spawnParticle(EnumParticleTypes.PORTAL, blockPos.getX() + 0.5 + 0.25 * n, blockPos.getY() + random.nextFloat(), blockPos.getZ() + 0.5 + 0.25 * n2, random.nextFloat() * n, (random.nextFloat() - 0.5) * 0.125, random.nextFloat() * n2, new int["".length()]);
            ++i;
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final InventoryEnderChest inventoryEnderChest = entityPlayer.getInventoryEnderChest();
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (inventoryEnderChest == null || !(tileEntity instanceof TileEntityEnderChest)) {
            return " ".length() != 0;
        }
        if (world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
            return " ".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        inventoryEnderChest.setChestTileEntity((TileEntityEnderChest)tileEntity);
        entityPlayer.displayGUIChest(inventoryEnderChest);
        entityPlayer.triggerAchievement(StatList.field_181738_V);
        return " ".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockEnderChest.FACING).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockEnderChest.FACING;
        return new BlockState(this, array);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockEnderChest.FACING, entityLivingBase.getHorizontalFacing().getOpposite()), "  ".length());
    }
    
    protected BlockEnderChest() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockEnderChest.FACING, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
}
