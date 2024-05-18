package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockRedstoneRepeater extends BlockRedstoneDiode
{
    public static final PropertyBool LOCKED;
    public static final PropertyInteger DELAY;
    private static final String[] I;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.repeater;
    }
    
    protected BlockRedstoneRepeater(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockRedstoneRepeater.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.DELAY, " ".length()).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.LOCKED, (boolean)("".length() != 0)));
    }
    
    static {
        I();
        LOCKED = PropertyBool.create(BlockRedstoneRepeater.I["".length()]);
        DELAY = PropertyInteger.create(BlockRedstoneRepeater.I[" ".length()], " ".length(), 0x8C ^ 0x88);
    }
    
    @Override
    protected IBlockState getPoweredState(final IBlockState blockState) {
        return Blocks.powered_repeater.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneRepeater.FACING, blockState.getValue((IProperty<EnumFacing>)BlockRedstoneRepeater.FACING)).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.DELAY, blockState.getValue((IProperty<Integer>)BlockRedstoneRepeater.DELAY)).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.LOCKED, blockState.getValue((IProperty<Boolean>)BlockRedstoneRepeater.LOCKED));
    }
    
    @Override
    public boolean isLocked(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        if (this.getPowerOnSides(blockAccess, blockPos, blockState) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected boolean canPowerSide(final Block block) {
        return BlockRedstoneDiode.isRedstoneRepeaterBlockID(block);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.repeater;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockRedstoneRepeater.FACING;
        array[" ".length()] = BlockRedstoneRepeater.DELAY;
        array["  ".length()] = BlockRedstoneRepeater.LOCKED;
        return new BlockState(this, array);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockRedstoneRepeater.I["  ".length()]);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return "".length() != 0;
        }
        world.setBlockState(blockPos, blockState.cycleProperty((IProperty<Comparable>)BlockRedstoneRepeater.DELAY), "   ".length());
        return " ".length() != 0;
    }
    
    @Override
    protected IBlockState getUnpoweredState(final IBlockState blockState) {
        return Blocks.unpowered_repeater.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneRepeater.FACING, blockState.getValue((IProperty<EnumFacing>)BlockRedstoneRepeater.FACING)).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.DELAY, blockState.getValue((IProperty<Integer>)BlockRedstoneRepeater.DELAY)).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.LOCKED, blockState.getValue((IProperty<Boolean>)BlockRedstoneRepeater.LOCKED));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length() | blockState.getValue((IProperty<EnumFacing>)BlockRedstoneRepeater.FACING).getHorizontalIndex() | blockState.getValue((IProperty<Integer>)BlockRedstoneRepeater.DELAY) - " ".length() << "  ".length();
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty((IProperty<Comparable>)BlockRedstoneRepeater.LOCKED, this.isLocked(blockAccess, blockPos, blockState));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneRepeater.FACING, EnumFacing.getHorizontal(n)).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.LOCKED, "".length() != 0).withProperty((IProperty<Comparable>)BlockRedstoneRepeater.DELAY, " ".length() + (n >> "  ".length()));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(".\n4.)&", "BeWEL");
        BlockRedstoneRepeater.I[" ".length()] = I("\u0011\u00158\f#", "upTmZ");
        BlockRedstoneRepeater.I["  ".length()] = I("*\u0000\r/E'\u001d\u0007&\u000em\u001a\t/\u000e", "CthBk");
    }
    
    @Override
    protected int getDelay(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockRedstoneRepeater.DELAY) * "  ".length();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneRepeater.FACING);
            final double n = blockPos.getX() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            final double n2 = blockPos.getY() + 0.4f + (random.nextFloat() - 0.5f) * 0.2;
            final double n3 = blockPos.getZ() + 0.5f + (random.nextFloat() - 0.5f) * 0.2;
            float n4 = -5.0f;
            if (random.nextBoolean()) {
                n4 = blockState.getValue((IProperty<Integer>)BlockRedstoneRepeater.DELAY) * "  ".length() - " ".length();
            }
            final float n5 = n4 / 16.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n + n5 * enumFacing.getFrontOffsetX(), n2, n3 + n5 * enumFacing.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int["".length()]);
        }
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
