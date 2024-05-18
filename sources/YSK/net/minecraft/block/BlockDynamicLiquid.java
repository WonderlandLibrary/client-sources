package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class BlockDynamicLiquid extends BlockLiquid
{
    int adjacentSourceBlocks;
    
    protected BlockDynamicLiquid(final Material material) {
        super(material);
    }
    
    private void tryFlowInto(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (this.canFlowInto(world, blockPos, blockState)) {
            if (blockState.getBlock() != Blocks.air) {
                if (this.blockMaterial == Material.lava) {
                    this.triggerMixEffects(world, blockPos);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    blockState.getBlock().dropBlockAsItem(world, blockPos, blockState, "".length());
                }
            }
            world.setBlockState(blockPos, this.getDefaultState().withProperty((IProperty<Comparable>)BlockDynamicLiquid.LEVEL, n), "   ".length());
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockToAir, IBlockState withProperty, final Random random) {
        int intValue = withProperty.getValue((IProperty<Integer>)BlockDynamicLiquid.LEVEL);
        int n = " ".length();
        if (this.blockMaterial == Material.lava && !world.provider.doesWaterVaporize()) {
            n = "  ".length();
        }
        int tickRate = this.tickRate(world);
        if (intValue > 0) {
            int checkAdjacentBlock = -(0x20 ^ 0x44);
            this.adjacentSourceBlocks = "".length();
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                checkAdjacentBlock = this.checkAdjacentBlock(world, blockToAir.offset(iterator.next()), checkAdjacentBlock);
            }
            int n2 = checkAdjacentBlock + n;
            if (n2 >= (0x16 ^ 0x1E) || checkAdjacentBlock < 0) {
                n2 = -" ".length();
            }
            if (this.getLevel(world, blockToAir.up()) >= 0) {
                final int level = this.getLevel(world, blockToAir.up());
                if (level >= (0xAB ^ 0xA3)) {
                    n2 = level;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n2 = level + (0x14 ^ 0x1C);
                }
            }
            if (this.adjacentSourceBlocks >= "  ".length() && this.blockMaterial == Material.water) {
                final IBlockState blockState = world.getBlockState(blockToAir.down());
                if (blockState.getBlock().getMaterial().isSolid()) {
                    n2 = "".length();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else if (blockState.getBlock().getMaterial() == this.blockMaterial && blockState.getValue((IProperty<Integer>)BlockDynamicLiquid.LEVEL) == 0) {
                    n2 = "".length();
                }
            }
            if (this.blockMaterial == Material.lava && intValue < (0x53 ^ 0x5B) && n2 < (0x29 ^ 0x21) && n2 > intValue && random.nextInt(0x39 ^ 0x3D) != 0) {
                tickRate *= (0x9B ^ 0x9F);
            }
            if (n2 == intValue) {
                this.placeStaticBlock(world, blockToAir, withProperty);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else if ((intValue = n2) < 0) {
                world.setBlockToAir(blockToAir);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                withProperty = withProperty.withProperty((IProperty<Comparable>)BlockDynamicLiquid.LEVEL, n2);
                world.setBlockState(blockToAir, withProperty, "  ".length());
                world.scheduleUpdate(blockToAir, this, tickRate);
                world.notifyNeighborsOfStateChange(blockToAir, this);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
        }
        else {
            this.placeStaticBlock(world, blockToAir, withProperty);
        }
        final IBlockState blockState2 = world.getBlockState(blockToAir.down());
        if (this.canFlowInto(world, blockToAir.down(), blockState2)) {
            if (this.blockMaterial == Material.lava && world.getBlockState(blockToAir.down()).getBlock().getMaterial() == Material.water) {
                world.setBlockState(blockToAir.down(), Blocks.stone.getDefaultState());
                this.triggerMixEffects(world, blockToAir.down());
                return;
            }
            if (intValue >= (0x16 ^ 0x1E)) {
                this.tryFlowInto(world, blockToAir.down(), blockState2, intValue);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                this.tryFlowInto(world, blockToAir.down(), blockState2, intValue + (0xAF ^ 0xA7));
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        else if (intValue >= 0 && (intValue == 0 || this.isBlocked(world, blockToAir.down(), blockState2))) {
            final Set<EnumFacing> possibleFlowDirections = this.getPossibleFlowDirections(world, blockToAir);
            int length = intValue + n;
            if (intValue >= (0x68 ^ 0x60)) {
                length = " ".length();
            }
            if (length >= (0x3A ^ 0x32)) {
                return;
            }
            final Iterator<EnumFacing> iterator2 = possibleFlowDirections.iterator();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final EnumFacing enumFacing = iterator2.next();
                this.tryFlowInto(world, blockToAir.offset(enumFacing), world.getBlockState(blockToAir.offset(enumFacing)), length);
            }
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    private Set<EnumFacing> getPossibleFlowDirections(final World world, final BlockPos blockPos) {
        int n = 713 + 925 - 681 + 43;
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator.next();
            final BlockPos offset = blockPos.offset(enumFacing);
            final IBlockState blockState = world.getBlockState(offset);
            if (!this.isBlocked(world, offset, blockState) && (blockState.getBlock().getMaterial() != this.blockMaterial || blockState.getValue((IProperty<Integer>)BlockDynamicLiquid.LEVEL) > 0)) {
                int n2;
                if (this.isBlocked(world, offset.down(), world.getBlockState(offset.down()))) {
                    n2 = this.func_176374_a(world, offset, " ".length(), enumFacing.getOpposite());
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                if (n2 < n) {
                    none.clear();
                }
                if (n2 > n) {
                    continue;
                }
                none.add(enumFacing);
                n = n2;
            }
        }
        return none;
    }
    
    private int func_176374_a(final World world, final BlockPos blockPos, final int n, final EnumFacing enumFacing) {
        int n2 = 772 + 128 - 350 + 450;
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            if (enumFacing2 != enumFacing) {
                final BlockPos offset = blockPos.offset(enumFacing2);
                final IBlockState blockState = world.getBlockState(offset);
                if (this.isBlocked(world, offset, blockState) || (blockState.getBlock().getMaterial() == this.blockMaterial && blockState.getValue((IProperty<Integer>)BlockDynamicLiquid.LEVEL) <= 0)) {
                    continue;
                }
                if (!this.isBlocked(world, offset.down(), blockState)) {
                    return n;
                }
                if (n >= (0x60 ^ 0x64)) {
                    continue;
                }
                final int func_176374_a = this.func_176374_a(world, offset, n + " ".length(), enumFacing2.getOpposite());
                if (func_176374_a >= n2) {
                    continue;
                }
                n2 = func_176374_a;
            }
        }
        return n2;
    }
    
    private void placeStaticBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, BlockLiquid.getStaticBlock(this.blockMaterial).getDefaultState().withProperty((IProperty<Comparable>)BlockDynamicLiquid.LEVEL, (Integer)blockState.getValue((IProperty<V>)BlockDynamicLiquid.LEVEL)), "  ".length());
    }
    
    private boolean canFlowInto(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final Material material = blockState.getBlock().getMaterial();
        if (material != this.blockMaterial && material != Material.lava && !this.isBlocked(world, blockPos, blockState)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean isBlocked(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final Block block = world.getBlockState(blockPos).getBlock();
        int n;
        if (!(block instanceof BlockDoor) && block != Blocks.standing_sign && block != Blocks.ladder && block != Blocks.reeds) {
            if (block.blockMaterial == Material.portal) {
                n = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n = (block.blockMaterial.blocksMovement() ? 1 : 0);
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected int checkAdjacentBlock(final World world, final BlockPos blockPos, final int n) {
        int n2 = this.getLevel(world, blockPos);
        if (n2 < 0) {
            return n;
        }
        if (n2 == 0) {
            this.adjacentSourceBlocks += " ".length();
        }
        if (n2 >= (0x27 ^ 0x2F)) {
            n2 = "".length();
        }
        int n3;
        if (n >= 0 && n2 >= n) {
            n3 = n;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
}
