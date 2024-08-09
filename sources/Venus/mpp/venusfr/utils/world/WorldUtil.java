/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WorldUtil
implements IMinecraft {

    public static class Blocks {
        public static ArrayList<BlockPos> getAllInBox(BlockPos blockPos, BlockPos blockPos2) {
            ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
            BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
            BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
            for (int i = blockPos3.getX(); i <= blockPos4.getX(); ++i) {
                for (int j = blockPos3.getY(); j <= blockPos4.getY(); ++j) {
                    for (int k = blockPos3.getZ(); k <= blockPos4.getZ(); ++k) {
                        arrayList.add(new BlockPos(i, j, k));
                    }
                }
            }
            return arrayList;
        }

        public static CopyOnWriteArrayList<BlockPos> getAllInBoxA(BlockPos blockPos, BlockPos blockPos2) {
            CopyOnWriteArrayList<BlockPos> copyOnWriteArrayList = new CopyOnWriteArrayList<BlockPos>();
            BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
            BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
            for (int i = blockPos3.getX(); i <= blockPos4.getX(); ++i) {
                for (int j = blockPos3.getY(); j <= blockPos4.getY(); ++j) {
                    for (int k = blockPos3.getZ(); k <= blockPos4.getZ(); ++k) {
                        if (IMinecraft.mc.world.getBlockState(new BlockPos(i, j, k)).getBlock() == net.minecraft.block.Blocks.AIR) continue;
                        copyOnWriteArrayList.add(new BlockPos(i, j, k));
                    }
                }
            }
            return copyOnWriteArrayList;
        }
    }

    public static class TotemUtil {
        public static BlockPos getBlock(float f, Block block) {
            return TotemUtil.getSphere(TotemUtil.getPlayerPosLocal(), f, 6, false, true, 0).stream().filter(arg_0 -> TotemUtil.lambda$getBlock$0(block, arg_0)).min(Comparator.comparing(TotemUtil::lambda$getBlock$1)).orElse(null);
        }

        public static BlockPos getBlock(float f) {
            return TotemUtil.getSphere(TotemUtil.getPlayerPosLocal(), f, 6, false, true, 0).stream().filter(TotemUtil::lambda$getBlock$2).min(Comparator.comparing(TotemUtil::lambda$getBlock$3)).orElse(null);
        }

        public static BlockPos getBlockFlat(int n) {
            BlockPos blockPos = TotemUtil.getPlayerPosLocal().add(0, -1, 0);
            for (int i = blockPos.getX() - n; i <= blockPos.getX() + n; ++i) {
                for (int j = blockPos.getX() - n; j <= blockPos.getZ() + n; ++j) {
                    if (IMinecraft.mc.world.getBlockState(new BlockPos(i, blockPos.getY(), j)).getBlock() == net.minecraft.block.Blocks.AIR) continue;
                    return new BlockPos(i, blockPos.getY(), j);
                }
            }
            return blockPos;
        }

        public static List<BlockPos> getSphere(BlockPos blockPos, float f, int n, boolean bl, boolean bl2, int n2) {
            ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
            int n3 = blockPos.getX();
            int n4 = blockPos.getY();
            int n5 = blockPos.getZ();
            int n6 = n3 - (int)f;
            while ((float)n6 <= (float)n3 + f) {
                int n7 = n5 - (int)f;
                while ((float)n7 <= (float)n5 + f) {
                    int n8 = bl2 ? n4 - (int)f : n4;
                    while (true) {
                        float f2 = n8;
                        float f3 = bl2 ? (float)n4 + f : (float)(n4 + n);
                        if (!(f2 < f3)) break;
                        double d = (n3 - n6) * (n3 - n6) + (n5 - n7) * (n5 - n7) + (bl2 ? (n4 - n8) * (n4 - n8) : 0);
                        if (d < (double)(f * f) && (!bl || d >= (double)((f - 1.0f) * (f - 1.0f)))) {
                            arrayList.add(new BlockPos(n6, n8 + n2, n7));
                        }
                        ++n8;
                    }
                    ++n7;
                }
                ++n6;
            }
            return arrayList;
        }

        public static BlockPos getPlayerPosLocal() {
            if (IMinecraft.mc.player == null) {
                return BlockPos.ZERO;
            }
            return new BlockPos(Math.floor(IMinecraft.mc.player.getPosX()), Math.floor(IMinecraft.mc.player.getPosY()), Math.floor(IMinecraft.mc.player.getPosZ()));
        }

        public static double getDistanceOfEntityToBlock(Entity entity2, BlockPos blockPos) {
            return TotemUtil.getDistance(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public static double getDistance(double d, double d2, double d3, double d4, double d5, double d6) {
            double d7 = d - d4;
            double d8 = d2 - d5;
            double d9 = d3 - d6;
            return MathHelper.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
        }

        private static Double lambda$getBlock$3(BlockPos blockPos) {
            return TotemUtil.getDistanceOfEntityToBlock(IMinecraft.mc.player, blockPos);
        }

        private static boolean lambda$getBlock$2(BlockPos blockPos) {
            return IMinecraft.mc.world.getBlockState(blockPos).getBlock() != net.minecraft.block.Blocks.AIR;
        }

        private static Double lambda$getBlock$1(BlockPos blockPos) {
            return TotemUtil.getDistanceOfEntityToBlock(IMinecraft.mc.player, blockPos);
        }

        private static boolean lambda$getBlock$0(Block block, BlockPos blockPos) {
            return IMinecraft.mc.world.getBlockState(blockPos).getBlock() == block;
        }
    }
}

