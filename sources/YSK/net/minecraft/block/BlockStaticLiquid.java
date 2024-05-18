package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class BlockStaticLiquid extends BlockLiquid
{
    private static final String[] I;
    
    private boolean getCanBlockBurn(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().getMaterial().getCanBurn();
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.blockMaterial == Material.lava && world.getGameRules().getBoolean(BlockStaticLiquid.I["".length()])) {
            final int nextInt = random.nextInt("   ".length());
            if (nextInt > 0) {
                BlockPos add = blockPos;
                int i = "".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
                while (i < nextInt) {
                    add = add.add(random.nextInt("   ".length()) - " ".length(), " ".length(), random.nextInt("   ".length()) - " ".length());
                    final Block block = world.getBlockState(add).getBlock();
                    if (block.blockMaterial == Material.air) {
                        if (this.isSurroundingBlockFlammable(world, add)) {
                            world.setBlockState(add, Blocks.fire.getDefaultState());
                            return;
                        }
                    }
                    else if (block.blockMaterial.blocksMovement()) {
                        return;
                    }
                    ++i;
                }
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                int j = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (j < "   ".length()) {
                    final BlockPos add2 = blockPos.add(random.nextInt("   ".length()) - " ".length(), "".length(), random.nextInt("   ".length()) - " ".length());
                    if (world.isAirBlock(add2.up()) && this.getCanBlockBurn(world, add2)) {
                        world.setBlockState(add2.up(), Blocks.fire.getDefaultState());
                    }
                    ++j;
                }
            }
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected boolean isSurroundingBlockFlammable(final World world, final BlockPos blockPos) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < length) {
            if (this.getCanBlockBurn(world, blockPos.offset(values[i]))) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    protected BlockStaticLiquid(final Material material) {
        super(material);
        this.setTickRandomly("".length() != 0);
        if (material == Material.lava) {
            this.setTickRandomly(" ".length() != 0);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("3\u0002\u000e,:29!&#", "WmHEH");
    }
    
    private void updateLiquid(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockDynamicLiquid flowingBlock = BlockLiquid.getFlowingBlock(this.blockMaterial);
        world.setBlockState(blockPos, flowingBlock.getDefaultState().withProperty((IProperty<Comparable>)BlockStaticLiquid.LEVEL, (Integer)blockState.getValue((IProperty<V>)BlockStaticLiquid.LEVEL)), "  ".length());
        world.scheduleUpdate(blockPos, flowingBlock, this.tickRate(world));
    }
    
    static {
        I();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            this.updateLiquid(world, blockPos, blockState);
        }
    }
}
