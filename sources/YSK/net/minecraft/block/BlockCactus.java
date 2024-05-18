package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockCactus extends Block
{
    private static final String[] I;
    public static final PropertyInteger AGE;
    
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\n=#", "kZFVC");
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        entity.attackEntityFrom(DamageSource.cactus, 1.0f);
    }
    
    protected BlockCactus() {
        super(Material.cactus);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCactus.AGE, "".length()));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCactus.AGE;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next())).getBlock().getMaterial().isSolid()) {
                return "".length() != 0;
            }
        }
        final Block block = world.getBlockState(blockPos.down()).getBlock();
        if (block != Blocks.cactus && block != Blocks.sand) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockCactus.I["".length()], "".length(), 0x56 ^ 0x59);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final BlockPos up = blockPos.up();
        if (world.isAirBlock(up)) {
            int length = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (world.getBlockState(blockPos.down(length)).getBlock() == this) {
                ++length;
            }
            if (length < "   ".length()) {
                final int intValue = blockState.getValue((IProperty<Integer>)BlockCactus.AGE);
                if (intValue == (0x9 ^ 0x6)) {
                    world.setBlockState(up, this.getDefaultState());
                    final IBlockState withProperty = blockState.withProperty((IProperty<Comparable>)BlockCactus.AGE, "".length());
                    world.setBlockState(blockPos, withProperty, 0x82 ^ 0x86);
                    this.onNeighborBlockChange(world, up, withProperty, this);
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                else {
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCactus.AGE, intValue + " ".length()), 0x63 ^ 0x67);
                }
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCactus.AGE, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockCactus.AGE);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (super.canPlaceBlockAt(world, blockPos)) {
            n = (this.canBlockStay(world, blockPos) ? 1 : 0);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + " ".length() - n, blockPos.getY() + " ".length(), blockPos.getZ() + " ".length() - n);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockPos)) {
            world.destroyBlock(blockPos, " ".length() != 0);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final float n = 0.0625f;
        return new AxisAlignedBB(blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n, blockPos.getX() + " ".length() - n, blockPos.getY() + " ".length() - n, blockPos.getZ() + " ".length() - n);
    }
}
