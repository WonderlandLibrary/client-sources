package net.minecraft.block;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;

public class BlockCake extends Block
{
    private static final String[] I;
    public static final PropertyInteger BITES;
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.cake;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.eatCake(world, blockPos, world.getBlockState(blockPos), entityPlayer);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockToAir)) {
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockCake.BITES);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.eatCake(world, blockPos, blockState, entityPlayer);
        return " ".length() != 0;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    protected BlockCake() {
        super(Material.cake);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCake.BITES, "".length()));
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.0625f;
        this.setBlockBounds(n, 0.0f, n, 1.0f - n, 0.5f, 1.0f - n);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return this.getCollisionBoundingBox(world, blockPos, world.getBlockState(blockPos));
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCake.BITES;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isOpaqueCube() {
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean canBlockStay(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0006*\u0017\u0010\u0018", "dCcuk");
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.0625f;
        this.setBlockBounds((" ".length() + blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockCake.BITES) * "  ".length()) / 16.0f, 0.0f, n, 1.0f - n, 0.5f, 1.0f - n);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCake.BITES, n);
    }
    
    static {
        I();
        BITES = PropertyInteger.create(BlockCake.I["".length()], "".length(), 0xAE ^ 0xA8);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + (" ".length() + blockState.getValue((IProperty<Integer>)BlockCake.BITES) * "  ".length()) / 16.0f, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + " ".length() - n, blockPos.getY() + 0.5f, blockPos.getZ() + " ".length() - n);
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return ((0xAE ^ 0xA9) - world.getBlockState(blockPos).getValue((IProperty<Integer>)BlockCake.BITES)) * "  ".length();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (super.canPlaceBlockAt(world, blockPos)) {
            n = (this.canBlockStay(world, blockPos) ? 1 : 0);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private void eatCake(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.canEat("".length() != 0)) {
            entityPlayer.triggerAchievement(StatList.field_181724_H);
            entityPlayer.getFoodStats().addStats("  ".length(), 0.1f);
            final int intValue = blockState.getValue((IProperty<Integer>)BlockCake.BITES);
            if (intValue < (0x22 ^ 0x24)) {
                world.setBlockState(blockToAir, blockState.withProperty((IProperty<Comparable>)BlockCake.BITES, intValue + " ".length()), "   ".length());
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                world.setBlockToAir(blockToAir);
            }
        }
    }
}
