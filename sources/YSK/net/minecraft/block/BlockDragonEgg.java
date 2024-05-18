package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockDragonEgg extends Block
{
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.checkFall(world, blockPos);
    }
    
    @Override
    public int tickRate(final World world) {
        return 0xB1 ^ 0xB4;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    public BlockDragonEgg() {
        super(Material.dragonEgg, MapColor.blackColor);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    private void checkFall(final World world, final BlockPos blockToAir) {
        if (BlockFalling.canFallInto(world, blockToAir.down()) && blockToAir.getY() >= 0) {
            final int n = 0x8B ^ 0xAB;
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-n, -n, -n), blockToAir.add(n, n, n))) {
                world.spawnEntityInWorld(new EntityFallingBlock(world, blockToAir.getX() + 0.5f, blockToAir.getY(), blockToAir.getZ() + 0.5f, this.getDefaultState()));
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos down = blockToAir;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                while (BlockFalling.canFallInto(world, down) && down.getY() > 0) {
                    down = down.down();
                }
                if (down.getY() > 0) {
                    world.setBlockState(down, this.getDefaultState(), "  ".length());
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.teleport(world, blockPos);
        return " ".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.teleport(world, blockPos);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private void teleport(final World world, final BlockPos blockToAir) {
        final IBlockState blockState = world.getBlockState(blockToAir);
        if (blockState.getBlock() == this) {
            int i = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (i < 102 + 659 + 95 + 144) {
                final BlockPos add = blockToAir.add(world.rand.nextInt(0x9E ^ 0x8E) - world.rand.nextInt(0x14 ^ 0x4), world.rand.nextInt(0xB9 ^ 0xB1) - world.rand.nextInt(0x52 ^ 0x5A), world.rand.nextInt(0x91 ^ 0x81) - world.rand.nextInt(0x50 ^ 0x40));
                if (world.getBlockState(add).getBlock().blockMaterial == Material.air) {
                    if (world.isRemote) {
                        int j = "".length();
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                        while (j < 23 + 7 - 5 + 103) {
                            final double nextDouble = world.rand.nextDouble();
                            world.spawnParticle(EnumParticleTypes.PORTAL, add.getX() + (blockToAir.getX() - add.getX()) * nextDouble + (world.rand.nextDouble() - 0.5) * 1.0 + 0.5, add.getY() + (blockToAir.getY() - add.getY()) * nextDouble + world.rand.nextDouble() * 1.0 - 0.5, add.getZ() + (blockToAir.getZ() - add.getZ()) * nextDouble + (world.rand.nextDouble() - 0.5) * 1.0 + 0.5, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, new int["".length()]);
                            ++j;
                        }
                        "".length();
                        if (4 == 0) {
                            throw null;
                        }
                    }
                    else {
                        world.setBlockState(add, blockState, "  ".length());
                        world.setBlockToAir(blockToAir);
                    }
                    return;
                }
                ++i;
            }
        }
    }
}
