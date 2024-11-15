// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public final class BlockUtil {
    public static boolean isBlockType(final BlockPos pos, final Block block) {
        return Argon.mc.world.getBlockState(pos).getBlock() == block;
    }

    public static boolean canExplodeAnchor(final BlockPos pos) {
        return isBlockType(pos, Blocks.RESPAWN_ANCHOR) && Argon.mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES) != 0;
    }

    public static boolean shouldCharge(final BlockPos pos) {
        return isBlockType(pos, Blocks.RESPAWN_ANCHOR) && Argon.mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES) == 0;
    }

    public static Stream<BlockPos> method260(final BlockPos from, final BlockPos to) {
        final BlockPos seed = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos blockPos = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return Stream.iterate(seed, lol -> method261(from, to, blockPos)).limit((long) (blockPos.getX() - seed.getX() + 1) * (blockPos.getY() - seed.getY() + 1) * (blockPos.getZ() - seed.getZ() + 1));
    }

    private static BlockPos method261(final BlockPos blockPos, final BlockPos blockPos2, final BlockPos blockPos3) {
        final int n = 0;
        int n2 = blockPos3.getX();
        int n3 = blockPos3.getY();
        int getZ = blockPos3.getZ();
        final int n4 = n;
        int n6;
        final int n5 = n6 = ++n2;
        int n8;
        final int n7 = n8 = blockPos.getX();
        if (n4 == 0) {
            if (n5 > n7) {
                n2 = blockPos2.getX();
                ++n3;
            }
            final int n9;
            n6 = (n9 = n3);
            final int method_10261;
            n8 = blockPos.getY();
        }
        if (n5 > n7) {
            n3 = blockPos2.getY();
            ++getZ;
        }
        n6 = getZ;
        n8 = blockPos.getZ();
        return new BlockPos(n2, n3, getZ);
    }
}