/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.cache;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baritone.api.utils.BlockUtils;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.pathing.PathingBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/**
 * @author Brady
 * @since 8/3/2018
 */
public final class ChunkPacker {

    private ChunkPacker() {}

    public static CachedChunk pack(Chunk chunk) {
        //long start = System.nanoTime() / 1000000L;

        Map<String, List<BlockPos>> specialBlocks = new HashMap<>();
        BitSet bitSet = new BitSet(CachedChunk.SIZE);
        try {
            ExtendedBlockStorage[] chunkInternalStorageArray = chunk.getBlockStorageArray();
            for (int y0 = 0; y0 < 16; y0++) {
                ExtendedBlockStorage extendedblockstorage = chunkInternalStorageArray[y0];
                if (extendedblockstorage == null) {
                    // any 16x16x16 area that's all air will have null storage
                    // for example, in an ocean biome, with air from y=64 to y=256
                    // the first 4 extended blocks storages will be full
                    // and the remaining 12 will be null

                    // since the index into the bitset is calculated from the x y and z
                    // and doesn't function as an append, we can entirely skip the scanning
                    // since a bitset is initialized to all zero, and air is saved as zeros
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //long end = System.nanoTime() / 1000000L;
        //System.out.println("Chunk packing took " + (end - start) + "ms for " + chunk.x + "," + chunk.z);
        IBlockState[] blocks = new IBlockState[256];

        // @formatter:off
        for (int z = 0; z < 16; z++) {
            https://www.ibm.com/developerworks/library/j-perry-writing-good-java-code/index.html
            for (int x = 0; x < 16; x++) {
                for (int y = 255; y >= 0; y--) {
                    int index = CachedChunk.getPositionIndex(x, y, z);
                    if (bitSet.get(index) || bitSet.get(index + 1)) {
                        blocks[z << 4 | x] = chunk.getBlockState(x, y, z);
                        continue https;
                    }
                }
                blocks[z << 4 | x] = Blocks.air.getDefaultState();
            }
        }
        // @formatter:on
        return new CachedChunk(chunk.xPosition, chunk.zPosition, bitSet, blocks, specialBlocks, System.currentTimeMillis());
    }


    private static PathingBlockType getPathingBlockType(IBlockState state, Chunk chunk, int x, int y, int z) {
        Block block = state.getBlock();
        if (block == Blocks.water || block == Blocks.flowing_water) {
            // only water source blocks are plausibly usable, flowing water should be avoid
            // FLOWING_WATER is a waterfall, it doesn't really matter and caching it as AVOID just makes it look wrong
            if (MovementHelper.possiblyFlowing(state)) {
                return PathingBlockType.AVOID;
            }
            if (
                    (x != 15 && MovementHelper.possiblyFlowing(chunk.getBlockState(x + 1, y, z)))
                            || (x != 0 && MovementHelper.possiblyFlowing(chunk.getBlockState(x - 1, y, z)))
                            || (z != 15 && MovementHelper.possiblyFlowing(chunk.getBlockState(x, y, z + 1)))
                            || (z != 0 && MovementHelper.possiblyFlowing(chunk.getBlockState(x, y, z - 1)))
            ) {
                return PathingBlockType.AVOID;
            }
            if (x == 0 || x == 15 || z == 0 || z == 15) {
                return PathingBlockType.AVOID;
            }
            return PathingBlockType.WATER;
        }

        if (MovementHelper.avoidWalkingInto(block) || MovementHelper.isBottomSlab(state)) {
            return PathingBlockType.AVOID;
        }
        // We used to do an AABB check here
        // however, this failed in the nether when you were near a nether fortress
        // because fences check their adjacent blocks in the world for their fence connection status to determine AABB shape
        // this caused a nullpointerexception when we saved chunks on unload, because they were unable to check their neighbors
        if (block == Blocks.air || block instanceof BlockTallGrass || block instanceof BlockDoublePlant || block instanceof BlockFlower) {
            return PathingBlockType.AIR;
        }

        return PathingBlockType.SOLID;
    }

    public static IBlockState pathingTypeToBlock(PathingBlockType type, int dimension) {
        switch (type) {
            case AIR:
                return Blocks.air.getDefaultState();
            case WATER:
                return Blocks.water.getDefaultState();
            case AVOID:
                return Blocks.lava.getDefaultState();
            case SOLID:
                // Dimension solid types
                switch (dimension) {
                    case -1:
                        return Blocks.netherrack.getDefaultState();
                    case 0:
                    default: // The fallback solid type
                        return Blocks.stone.getDefaultState();
                    case 1:
                        return Blocks.end_stone.getDefaultState();
                }
            default:
                return null;
        }
    }
}
