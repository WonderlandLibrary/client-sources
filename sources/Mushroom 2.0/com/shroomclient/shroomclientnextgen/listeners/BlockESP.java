package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.AlwaysPost;
import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.BlockChangeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ChunkLoadEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.events.impl.WorldLoadEvent;
import com.shroomclient.shroomclientnextgen.util.C;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

@RegisterListeners
public class BlockESP {

    public static ArrayList<BlockPos> beds = new ArrayList<>();
    public static ArrayList<BlockPos> chests = new ArrayList<>();
    public static ArrayList<ChunkPos> toScan = new ArrayList<>();
    public static ArrayList<BlockPos> unloadedChunks = new ArrayList<>();
    public static boolean done = true;
    static ArrayList<BlockPos> lastPoses = new ArrayList<>();

    public static Block blockAtPos(BlockPos pos) {
        return C.w().getBlockState(pos).getBlock();
    }

    static long erroredTime = 0;

    // new function for async >:)
    public static void doScan() {
        try {
            if (erroredTime == 0) {
                done = false;

                // cloning to stop async shit crashing (may tax fps idk)
                ArrayList<ChunkPos> toScanClone = new ArrayList<>();
                ArrayList<BlockPos> unloadedChunksClone = new ArrayList<>();
                ArrayList<BlockPos> lastPosesClone = new ArrayList<>();
                if (toScan.size() > 0) toScanClone = (ArrayList<
                        ChunkPos
                    >) toScan.clone();
                if (unloadedChunks.size() > 0) unloadedChunksClone = (ArrayList<
                        BlockPos
                    >) unloadedChunks.clone();
                if (lastPoses.size() > 0) lastPosesClone = (ArrayList<
                        BlockPos
                    >) lastPoses.clone();

                if (!toScanClone.isEmpty()) {
                    // scanning in progress!!
                    for (ChunkPos chunkPos : toScanClone) {
                        for (int x = 0; x < 16; x++) {
                            for (int y = 0; y < 256; y++) {
                                for (int z = 0; z < 16; z++) {
                                    BlockPos blockPos = new BlockPos(
                                        chunkPos.x * 16 + x,
                                        y,
                                        chunkPos.z * 16 + z
                                    );

                                    Block blockAtPos = blockAtPos(blockPos);
                                    // $$$$
                                    if (
                                        blockAtPos instanceof BedBlock &&
                                        BedBlock.getBedPart(
                                            C.w().getBlockState(blockPos)
                                        ).equals(
                                            DoubleBlockProperties.Type.FIRST
                                        ) &&
                                        !beds.contains(blockPos)
                                    ) beds.add(blockPos);
                                    // optimized chest esp $$$$$$$
                                    else if (
                                        blockAtPos instanceof ChestBlock &&
                                        !chests.contains(blockPos)
                                    ) {
                                        chests.add(blockPos);
                                    }
                                }
                            }
                        }
                    }

                    toScan.removeAll(toScanClone);
                }

                for (BlockPos pos : unloadedChunksClone) {
                    if (pos != null) {
                        if (beds.contains(pos)) {
                            beds.remove(pos);

                            lastPoses.removeIf(posi -> posi == pos);
                            continue;
                        }

                        if (chests.contains(pos)) {
                            chests.remove(pos);

                            lastPoses.removeIf(posi -> posi == pos);
                            continue;
                        }
                    }
                }

                for (BlockPos lastPos : lastPosesClone) {
                    if (blockAtPos(lastPos) instanceof BedBlock) {
                        // no math needed :sunglasses:
                        if (
                            BedBlock.getBedPart(
                                C.w().getBlockState(lastPos)
                            ).equals(DoubleBlockProperties.Type.FIRST) &&
                            !beds.contains(lastPos)
                        ) beds.add(lastPos);
                        else {
                            // optimized math $$$$ (theres def a function to get the beds head pos i just didnt look for it)
                            int bedHeadX = lastPos.getX();
                            int bedHeadZ = lastPos.getZ();
                            switch (
                                BedBlock.getOppositePartDirection(
                                    C.w().getBlockState(lastPos)
                                )
                            ) {
                                case NORTH:
                                    bedHeadZ -= 1;
                                    break;
                                case WEST:
                                    bedHeadX -= 1;
                                    break;
                            }

                            if (
                                !beds.contains(
                                    new BlockPos(
                                        bedHeadX,
                                        lastPos.getY(),
                                        bedHeadZ
                                    )
                                )
                            ) beds.add(
                                new BlockPos(bedHeadX, lastPos.getY(), bedHeadZ)
                            );
                        }

                        // only stops doing this if the bed is actually placed yayayay!!
                        // badder for fps but like its slight so who care
                        lastPoses.remove(lastPos);

                        continue;
                    }

                    if (blockAtPos(lastPos) instanceof ChestBlock) {
                        // this check STINKS (its slow)
                        if (!chests.contains(lastPos)) {
                            chests.add(lastPos);
                        }

                        // only stops doing this if the block is actually placed yayayay!!
                        // badder for fps but like its slight so who care
                        lastPoses.remove(lastPos);
                    }
                }

                done = true;
                unloadedChunks.removeAll(unloadedChunksClone);
            } else if (System.currentTimeMillis() - erroredTime > 5000) {
                erroredTime = 0;
                toScan.clear();
                unloadedChunks.clear();
                lastPoses.clear();
            }
        } catch (Exception e) {
            erroredTime = System.currentTimeMillis();

            System.out.println(
                "chunk loading has failed!!! however it is alright! error: " + e
            );
        }
    }

    @SubscribeEvent
    public void onBlockLoad(ChunkLoadEvent e) {
        toScan.add(e.chunk);
    }

    @AlwaysPost
    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        if (C.w() != null && C.isInGame() && done) {
            ForkJoinPool.commonPool().execute(BlockESP::doScan);
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent e) {
        // only works for beds, fix TODO
        lastPoses.add(e.pos);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldLoadEvent e) {
        beds.clear();
        chests.clear();
    }
}
