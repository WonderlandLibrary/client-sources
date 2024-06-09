/*
 * Decompiled with CFR 0.145.
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
            int cp2 = 3;
            if (settings.ofClearWater) {
                cp2 = 1;
            }
            BlockLeavesBase.setLightOpacity(Blocks.water, cp2);
            BlockLeavesBase.setLightOpacity(Blocks.flowing_water, cp2);
        }
        if (world != null && (var25 = world.getChunkProvider()) != null && (rve = Config.getMinecraft().func_175606_aa()) != null) {
            int cViewX = (int)rve.posX / 16;
            int cViewZ = (int)rve.posZ / 16;
            int cXMin = cViewX - 512;
            int cXMax = cViewX + 512;
            int cZMin = cViewZ - 512;
            int cZMax = cViewZ + 512;
            int countUpdated = 0;
            for (int threadName = cXMin; threadName < cXMax; ++threadName) {
                for (int cz2 = cZMin; cz2 < cZMax; ++cz2) {
                    Chunk c2;
                    if (!var25.chunkExists(threadName, cz2) || (c2 = var25.provideChunk(threadName, cz2)) == null || c2 instanceof EmptyChunk) continue;
                    int x0 = threadName << 4;
                    int z0 = cz2 << 4;
                    int x1 = x0 + 16;
                    int z1 = z0 + 16;
                    BlockPosM posXZ = new BlockPosM(0, 0, 0);
                    BlockPosM posXYZ = new BlockPosM(0, 0, 0);
                    for (int x2 = x0; x2 < x1; ++x2) {
                        block3 : for (int z2 = z0; z2 < z1; ++z2) {
                            posXZ.setXyz(x2, 0, z2);
                            BlockPos posH = world.func_175725_q(posXZ);
                            for (int y2 = 0; y2 < posH.getY(); ++y2) {
                                posXYZ.setXyz(x2, y2, z2);
                                IBlockState bs2 = world.getBlockState(posXYZ);
                                if (bs2.getBlock().getMaterial() != Material.water) {
                                    continue;
                                }
                                world.markBlocksDirtyVertical(x2, z2, posXYZ.getY(), posH.getY());
                                ++countUpdated;
                                continue block3;
                            }
                        }
                    }
                }
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

