// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public final class CrystalUtil {
    public static boolean method392(final BlockPos block) {
        final int n = 0;
        final BlockState getBlockState = Argon.mc.world.getBlockState(block);
        final int n2 = n;
        final boolean getBlockEntity = getBlockState.getBlock().equals(Blocks.OBSIDIAN);
        if (n2 == 0) {
            if (!getBlockEntity) {
                final boolean method_8322 = getBlockState.getBlock().equals((Blocks.BEDROCK));
                if (n2 != 0) {
                    return getBlockEntity;
                }
                if (!method_8322) {
                    return false;
                }
            }
            hasNoEntityOnIt(block);
        }
        return getBlockEntity;
    }

    public static boolean hasNoEntityOnIt(final BlockPos block) {
        final int n = 0;
        final BlockPos up = block.up();
        final int n2 = n;
        int n4;
        final int n3 = n4 = (Argon.mc.world.isAir(up) ? 1 : 0);
        if (n2 == 0) {
            if (n3 == 0) {
                return false;
            }
            n4 = up.getX();
        }
        final double n5 = n4;
        final double n6 = up.getY();
        final double n7 = up.getZ();
        return Argon.mc.world.getOtherEntities(null, new Box(n5, n6, n7, n5 + 1.0, n6 + 2.0, n7 + 1.0)).isEmpty();
    }

    public static boolean method394(final BlockPos pos) {
        final int n = 0;
        final BlockState getBlockState = Argon.mc.world.getBlockState(pos);
        final int n2 = n;
        final boolean getBlockEntity = getBlockState.isOf(Blocks.OBSIDIAN);
        if (n2 == 0) {
            if (getBlockEntity) {
                final boolean method_8322 = getBlockState.isOf(Blocks.BEDROCK);
                if (n2 == 0) {
                    if (method_8322) {
                        final BlockPos up = pos.up();
                        int n4;
                        final int n3 = n4 = (Argon.mc.world.isAir(up) ? 1 : 0);
                        if (n2 == 0) {
                            if (n3 == 0) {
                                return false;
                            }
                            n4 = up.getX();
                        }
                        final double n5 = n4;
                        final double n6 = up.getY();
                        final double n7 = up.getZ();
                        return Argon.mc.world.getOtherEntities(null, new Box(n5, n6, n7, n5 + 1.0, n6 + 2.0, n7 + 1.0)).isEmpty();
                    }
                }
            }
        }
        return getBlockEntity;
    }
}
