package xyz.mathax.mathaxclient.systems.modules.render.blockesp;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.utils.Utils;

import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class BlockESPChunk {
    private static final BlockPos.Mutable blockPos = new BlockPos.Mutable();

    private final int x, z;

    public Long2ObjectMap<BlockESPBlock> blocks;

    public BlockESPChunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public BlockESPBlock get(int x, int y, int z) {
        return blocks == null ? null : blocks.get(BlockESPBlock.getKey(x, y, z));
    }

    public void add(BlockPos blockPos, boolean update) {
        BlockESPBlock block = new BlockESPBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (blocks == null) {
            blocks = new Long2ObjectOpenHashMap<>(64);
        }

        blocks.put(BlockESPBlock.getKey(blockPos), block);

        if (update) {
            block.update();
        }
    }

    public void add(BlockPos blockPos) {
        add(blockPos, true);
    }

    public void remove(BlockPos blockPos) {
        if (blocks != null) {
            BlockESPBlock block = blocks.remove(BlockESPBlock.getKey(blockPos));
            if (block != null) {
                block.group.remove(block);
            }
        }
    }

    public void update() {
        if (blocks != null) {
            for (BlockESPBlock block : blocks.values()) {
                block.update();
            }
        }
    }

    public void update(int x, int y, int z) {
        if (blocks != null) {
            BlockESPBlock block = blocks.get(BlockESPBlock.getKey(x, y, z));
            if (block != null) {
                block.update();
            }
        }
    }

    public int size() {
        return blocks == null ? 0 : blocks.size();
    }

    public boolean shouldBeDeleted() {
        int viewDist = Utils.getRenderDistance() + 1;
        int chunkX = ChunkSectionPos.getSectionCoord(mc.player.getBlockPos().getX());
        int chunkZ = ChunkSectionPos.getSectionCoord(mc.player.getBlockPos().getZ());

        return x > chunkX + viewDist || x < chunkX - viewDist || z > chunkZ + viewDist || z < chunkZ - viewDist;
    }

    public void render(Render3DEvent event) {
        if (blocks != null) {
            for (BlockESPBlock block : blocks.values()) block.render(event);
        }
    }


    public static BlockESPChunk searchChunk(Chunk chunk, List<Block> blocks) {
        BlockESPChunk schunk = new BlockESPChunk(chunk.getPos().x, chunk.getPos().z);
        if (schunk.shouldBeDeleted()) {
            return schunk;
        }

        for (int x = chunk.getPos().getStartX(); x <= chunk.getPos().getEndX(); x++) {
            for (int z = chunk.getPos().getStartZ(); z <= chunk.getPos().getEndZ(); z++) {
                int height = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE).get(x - chunk.getPos().getStartX(), z - chunk.getPos().getStartZ());
                for (int y = mc.world.getBottomY(); y < height; y++) {
                    blockPos.set(x, y, z);
                    BlockState blockState = chunk.getBlockState(blockPos);
                    if (blocks.contains(blockState.getBlock())) {
                        schunk.add(blockPos, false);
                    }
                }
            }
        }

        return schunk;
    }
}
