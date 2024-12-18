/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import optifine.BlockPosM;
import optifine.Config;

public class ClearWater {
    public static void updateWaterOpacity(GameSettings settings, World world) {
        IChunkProvider var25;
        Entity rve;
        if (settings != null) {
            int cp = 3;
            if (settings.ofClearWater) {
                cp = 1;
            }
            BlockLeavesBase.setLightOpacity(Blocks.water, cp);
            BlockLeavesBase.setLightOpacity(Blocks.flowing_water, cp);
        }
        if (world != null && (var25 = world.getChunkProvider()) != null && (rve = Config.getMinecraft().func_175606_aa()) != null) {
            int cViewX = (int)rve.posX / 16;
            int cViewZ = (int)rve.posZ / 16;
            int cXMin = cViewX - 512;
            int cXMax = cViewX + 512;
            int cZMin = cViewZ - 512;
            int cZMax = cViewZ + 512;
            int countUpdated = 0;
            int threadName = cXMin;
            while (threadName < cXMax) {
                int cz = cZMin;
                while (cz < cZMax) {
                    Chunk c;
                    if (var25.chunkExists(threadName, cz) && (c = var25.provideChunk(threadName, cz)) != null && !(c instanceof EmptyChunk)) {
                        int x0 = threadName << 4;
                        int z0 = cz << 4;
                        int x1 = x0 + 16;
                        int z1 = z0 + 16;
                        BlockPosM posXZ = new BlockPosM(0, 0, 0);
                        BlockPosM posXYZ = new BlockPosM(0, 0, 0);
                        int x = x0;
                        while (x < x1) {
                            int z = z0;
                            while (z < z1) {
                                posXZ.setXyz(x, 0, z);
                                BlockPos posH = world.func_175725_q(posXZ);
                                for (int y = 0; y < posH.getY(); ++y) {
                                    posXYZ.setXyz(x, y, z);
                                    IBlockState bs = world.getBlockState(posXYZ);
                                    if (bs.getBlock().getMaterial() != Material.water) {
                                        continue;
                                    }
                                    world.markBlocksDirtyVertical(x, z, posXYZ.getY(), posH.getY());
                                    ++countUpdated;
                                    break;
                                }
                                ++z;
                            }
                            ++x;
                        }
                    }
                    ++cz;
                }
                ++threadName;
            }
            if (countUpdated > 0) {
                String var26 = "server";
                if (Config.isMinecraftThread()) {
                    var26 = "client";
                }
                Config.dbg("ClearWater (" + var26 + ") relighted " + countUpdated + " chunks");
            }
        }
    }
}

