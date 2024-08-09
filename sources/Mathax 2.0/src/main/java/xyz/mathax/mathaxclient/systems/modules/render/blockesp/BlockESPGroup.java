package xyz.mathax.mathaxclient.systems.modules.render.blockesp;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.utils.misc.UnorderedArrayList;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class BlockESPGroup {
    private static final BlockESP BLOCK_ESP = Modules.get().get(BlockESP.class);

    private final Block block;

    public final UnorderedArrayList<BlockESPBlock> blocks = new UnorderedArrayList<>();

    private double sumX, sumY, sumZ;

    public BlockESPGroup(Block block) {
        this.block = block;
    }

    public void add(BlockESPBlock block, boolean removeFromOld, boolean splitGroup) {
        blocks.add(block);
        sumX += block.x;
        sumY += block.y;
        sumZ += block.z;

        if (block.group != null && removeFromOld) {
            block.group.remove(block, splitGroup);
        }

        block.group = this;
    }

    public void add(BlockESPBlock block) {
        add(block, true, true);
    }

    public void remove(BlockESPBlock block, boolean splitGroup) {
        blocks.remove(block);
        sumX -= block.x;
        sumY -= block.y;
        sumZ -= block.z;

        if (blocks.isEmpty()) {
            BLOCK_ESP.removeGroup(block.group);
        } else if (splitGroup) {
            trySplit(block);
        }
    }

    public void remove(BlockESPBlock block) {
        remove(block, true);
    }

    private void trySplit(BlockESPBlock block) {
        Set<BlockESPBlock> neighbours = new ObjectOpenHashSet<>(6);
        for (int side : BlockESPBlock.SIDES) {
            if ((block.neighbours & side) == side) {
                BlockESPBlock neighbour = block.getSideBlock(side);
                if (neighbour != null) {
                    neighbours.add(neighbour);
                }
            }
        }

        if (neighbours.size() <= 1) {
            return;
        }

        Set<BlockESPBlock> remainingBlocks = new ObjectOpenHashSet<>(blocks);
        Queue<BlockESPBlock> blocksToCheck = new ArrayDeque<>();
        blocksToCheck.offer(blocks.get(0));
        remainingBlocks.remove(blocks.get(0));
        neighbours.remove(blocks.get(0));

        loop: {
            while (!blocksToCheck.isEmpty()) {
                BlockESPBlock blockESPBlock = blocksToCheck.poll();

                for (int side : BlockESPBlock.SIDES) {
                    if ((blockESPBlock.neighbours & side) != side) {
                        continue;
                    }

                    BlockESPBlock neighbour = blockESPBlock.getSideBlock(side);
                    if (neighbour != null && remainingBlocks.contains(neighbour)) {
                        blocksToCheck.offer(neighbour);
                        remainingBlocks.remove(neighbour);

                        neighbours.remove(neighbour);
                        if (neighbours.isEmpty()) {
                            break loop;
                        }
                    }
                }
            }
        }

        if (neighbours.size() > 0) {
            BlockESPGroup group = BLOCK_ESP.newGroup(this.block);
            group.blocks.ensureCapacity(remainingBlocks.size());

            blocks.removeIf(remainingBlocks::contains);

            for (BlockESPBlock blockESPBlock : remainingBlocks) {
                group.add(block, false, false);

                sumX -= blockESPBlock.x;
                sumY -= blockESPBlock.y;
                sumZ -= blockESPBlock.z;
            }

            if (neighbours.size() > 1) {
                block.neighbours = 0;
                for (BlockESPBlock b : neighbours) {
                    int x = b.x - block.x;
                    if (x == 1) {
                        block.neighbours |= BlockESPBlock.RI;
                    } else if (x == -1) {
                        block.neighbours |= BlockESPBlock.LE;
                    }

                    int y = b.y - block.y;
                    if (y == 1) {
                        block.neighbours |= BlockESPBlock.TO;
                    } else if (y == -1) {
                        block.neighbours |= BlockESPBlock.BO;
                    }

                    int z = b.z - block.z;
                    if (z == 1) {
                        block.neighbours |= BlockESPBlock.FO;
                    } else if (z == -1) {
                        block.neighbours |= BlockESPBlock.BA;
                    }
                }

                group.trySplit(block);
            }
        }
    }

    public void merge(BlockESPGroup group) {
        blocks.ensureCapacity(blocks.size() + group.blocks.size());

        for (BlockESPBlock block : group.blocks) {
            add(block, false, false);
        }

        BLOCK_ESP.removeGroup(group);
    }

    public void render(Render3DEvent event) {
        BlockESPBlockData blockData = BLOCK_ESP.getBlockData(block);
        if (blockData.tracer) {
            event.renderer.line(RenderUtils.center.x, RenderUtils.center.y, RenderUtils.center.z, sumX / blocks.size() + 0.5, sumY / blocks.size() + 0.5, sumZ / blocks.size() + 0.5, blockData.tracerColor);
        }
    }
}