package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class BlockSnow extends Block
{
    private static final String[] I;
    public static final PropertyInteger LAYERS;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockSnow.LAYERS;
        return new BlockState(this, array);
    }
    
    protected BlockSnow() {
        super(Material.snow);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockSnow.LAYERS, " ".length()));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + (blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) - " ".length()) * 0.125f, blockPos.getZ() + this.maxZ);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.UP) {
            n = " ".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockSnow.LAYERS) < (0xAA ^ 0xAF)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos.down());
        final Block block = blockState.getBlock();
        int n;
        if (block != Blocks.ice && block != Blocks.packed_ice) {
            if (block.getMaterial() == Material.leaves) {
                n = " ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (block == this && blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) >= (0x81 ^ 0x86)) {
                n = " ".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (block.isOpaqueCube() && block.blockMaterial.blocksMovement()) {
                n = " ".length();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockToAir, final IBlockState blockState, final TileEntity tileEntity) {
        Block.spawnAsEntity(world, blockToAir, new ItemStack(Items.snowball, blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) + " ".length(), "".length()));
        world.setBlockToAir(blockToAir);
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.snowball;
    }
    
    private boolean checkAndDropBlock(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockToAir)) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getValue((IProperty<Integer>)BlockSnow.LAYERS) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.getBoundsForLayers(blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockSnow.LAYERS));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) - " ".length();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockSnow.LAYERS, (n & (0x32 ^ 0x35)) + " ".length());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.getBoundsForLayers("".length());
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00054\u0013.\u001e\u001a", "iUjKl");
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockToAir) > (0x62 ^ 0x69)) {
            this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    protected void getBoundsForLayers(final int n) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, n / 8.0f, 1.0f);
    }
    
    static {
        I();
        LAYERS = PropertyInteger.create(BlockSnow.I["".length()], " ".length(), 0xBC ^ 0xB4);
    }
}
