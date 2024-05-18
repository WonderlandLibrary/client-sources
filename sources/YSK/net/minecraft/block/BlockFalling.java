package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;

public class BlockFalling extends Block
{
    public static boolean fallInstantly;
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    public BlockFalling(final Material material) {
        super(material);
    }
    
    public static boolean canFallInto(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos).getBlock();
        final Material blockMaterial = block.blockMaterial;
        if (block != Blocks.fire && blockMaterial != Material.air && blockMaterial != Material.water && blockMaterial != Material.lava) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            this.checkFallable(world, blockPos);
        }
    }
    
    private void checkFallable(final World world, final BlockPos blockToAir) {
        if (canFallInto(world, blockToAir.down()) && blockToAir.getY() >= 0) {
            final int n = 0xE1 ^ 0xC1;
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-n, -n, -n), blockToAir.add(n, n, n))) {
                if (!world.isRemote) {
                    final EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, blockToAir.getX() + 0.5, blockToAir.getY(), blockToAir.getZ() + 0.5, world.getBlockState(blockToAir));
                    this.onStartFalling(entityFallingBlock);
                    world.spawnEntityInWorld(entityFallingBlock);
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos blockPos = blockToAir.down();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (canFallInto(world, blockPos) && blockPos.getY() > 0) {
                    blockPos = blockPos.down();
                }
                if (blockPos.getY() > 0) {
                    world.setBlockState(blockPos.up(), this.getDefaultState());
                }
            }
        }
    }
    
    @Override
    public int tickRate(final World world) {
        return "  ".length();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    protected void onStartFalling(final EntityFallingBlock entityFallingBlock) {
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void onEndFalling(final World world, final BlockPos blockPos) {
    }
    
    public BlockFalling() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
