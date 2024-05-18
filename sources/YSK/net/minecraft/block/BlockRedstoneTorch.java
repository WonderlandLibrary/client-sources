package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class BlockRedstoneTorch extends BlockTorch
{
    private static Map<World, List<Toggle>> toggles;
    private static final String[] I;
    private final boolean isOn;
    
    @Override
    public int tickRate(final World world) {
        return "  ".length();
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.DOWN) {
            n = this.getWeakPower(blockAccess, blockPos, blockState, enumFacing);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int length;
        if (this.isOn && blockState.getValue((IProperty<Comparable>)BlockRedstoneTorch.FACING) != enumFacing) {
            length = (0x4 ^ 0xB);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013\u0014/5(\f['8=\u001b", "auAQG");
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.onNeighborChangeInternal(world, blockPos, blockState) && this.isOn == this.shouldBeOff(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    protected BlockRedstoneTorch(final boolean isOn) {
        this.isOn = isOn;
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(null);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isOn) {
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[i]), this);
                ++i;
            }
        }
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    static {
        I();
        BlockRedstoneTorch.toggles = (Map<World, List<Toggle>>)Maps.newHashMap();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isOn) {
            double n = blockPos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            double n2 = blockPos.getY() + 0.7 + (random.nextDouble() - 0.5) * 0.2;
            double n3 = blockPos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneTorch.FACING);
            if (enumFacing.getAxis().isHorizontal()) {
                final EnumFacing opposite = enumFacing.getOpposite();
                n += 0.27 * opposite.getFrontOffsetX();
                n2 += 0.22;
                n3 += 0.27 * opposite.getFrontOffsetZ();
            }
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    private boolean shouldBeOff(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing opposite = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneTorch.FACING).getOpposite();
        return world.isSidePowered(blockPos.offset(opposite), opposite);
    }
    
    private boolean isBurnedOut(final World world, final BlockPos blockPos, final boolean b) {
        if (!BlockRedstoneTorch.toggles.containsKey(world)) {
            BlockRedstoneTorch.toggles.put(world, Lists.newArrayList());
        }
        final List<Toggle> list = BlockRedstoneTorch.toggles.get(world);
        if (b) {
            list.add(new Toggle(blockPos, world.getTotalWorldTime()));
        }
        int length = "".length();
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < list.size()) {
            if (list.get(i).pos.equals(blockPos) && ++length >= (0x10 ^ 0x18)) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final boolean shouldBeOff = this.shouldBeOff(world, blockPos, blockState);
        final List<Toggle> list = BlockRedstoneTorch.toggles.get(world);
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (list != null && !list.isEmpty() && world.getTotalWorldTime() - list.get("".length()).time > 60L) {
            list.remove("".length());
        }
        if (this.isOn) {
            if (shouldBeOff) {
                world.setBlockState(blockPos, Blocks.unlit_redstone_torch.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneTorch.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockRedstoneTorch.FACING)), "   ".length());
                if (this.isBurnedOut(world, blockPos, " ".length() != 0)) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, BlockRedstoneTorch.I["".length()], 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                    int i = "".length();
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                    while (i < (0x13 ^ 0x16)) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + random.nextDouble() * 0.6 + 0.2, blockPos.getY() + random.nextDouble() * 0.6 + 0.2, blockPos.getZ() + random.nextDouble() * 0.6 + 0.2, 0.0, 0.0, 0.0, new int["".length()]);
                        ++i;
                    }
                    world.scheduleUpdate(blockPos, world.getBlockState(blockPos).getBlock(), 133 + 107 - 143 + 63);
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
            }
        }
        else if (!shouldBeOff && !this.isBurnedOut(world, blockPos, "".length() != 0)) {
            world.setBlockState(blockPos, Blocks.redstone_torch.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneTorch.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockRedstoneTorch.FACING)), "   ".length());
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        if (block != Blocks.unlit_redstone_torch && block != Blocks.redstone_torch) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isOn) {
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[i]), this);
                ++i;
            }
        }
    }
    
    static class Toggle
    {
        BlockPos pos;
        long time;
        
        public Toggle(final BlockPos pos, final long time) {
            this.pos = pos;
            this.time = time;
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
                if (-1 >= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
