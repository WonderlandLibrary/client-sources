/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import mpp.venusfr.events.EventStartRiding;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Teleport", type=Category.Movement)
public class Teleport
extends Function {
    private final StopWatch timer = new StopWatch();
    private final Random random = new Random();
    private final Set<BlockPos> visitedPositions = new HashSet<BlockPos>();
    private final int range = 4;

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (this.timer.isReached(10L)) {
            this.processEventUpdate();
        }
    }

    @Subscribe
    public void onRiding(EventStartRiding eventStartRiding) {
        this.processEventStartRiding(eventStartRiding);
    }

    private void processEventUpdate() {
        Block block = this.findRandomNearbyBlock();
        if (block != null) {
            this.sitOnSlab(block);
        }
        this.timer.reset();
    }

    private void processEventStartRiding(EventStartRiding eventStartRiding) {
        Entity entity2 = eventStartRiding.e;
        this.visitedPositions.add(entity2.getPosition());
        System.out.println("1");
        new Thread(Teleport::lambda$processEventStartRiding$0).start();
    }

    private Block findRandomNearbyBlock() {
        List<BlockPos> list = this.getPotentialPositions();
        if (!list.isEmpty()) {
            BlockPos blockPos = list.get(this.random.nextInt(list.size()));
            return Teleport.mc.world.getBlockState(blockPos).getBlock();
        }
        return null;
    }

    private List<BlockPos> getPotentialPositions() {
        Vector3d vector3d = Teleport.mc.player.getPositionVec();
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                for (int k = -4; k <= 4; ++k) {
                    BlockPos blockPos = new BlockPos(vector3d.x + (double)i, vector3d.y + (double)j, vector3d.z + (double)k);
                    if (!this.isPositionEligible(blockPos)) continue;
                    arrayList.add(blockPos);
                }
            }
        }
        return arrayList;
    }

    public static double getDistanceOfEntityToBlock(Entity entity2, BlockPos blockPos) {
        return Teleport.mc.player.getDistance(blockPos);
    }

    private boolean isPositionEligible(BlockPos blockPos) {
        return !this.visitedPositions.contains(blockPos) && Teleport.mc.world.getBlockState(blockPos).getBlock() instanceof SlabBlock || Teleport.mc.world.getBlockState(blockPos).getBlock() instanceof StairsBlock && Teleport.mc.world.isAirBlock(blockPos.up()) && Teleport.mc.world.isAirBlock(blockPos.up(2));
    }

    private void sitOnSlab(Block block) {
        BlockPos blockPos = this.findSlabPosition(block);
        if (blockPos != null && !this.visitedPositions.contains(blockPos)) {
            Vector3d vector3d = new Vector3d((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5);
            BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(vector3d, Direction.UP, blockPos, false);
            Teleport.mc.playerController.processRightClickBlock(Teleport.mc.player, Teleport.mc.world, Hand.MAIN_HAND, blockRayTraceResult);
        }
    }

    private BlockPos findSlabPosition(Block block) {
        Vector3d vector3d = Teleport.mc.player.getPositionVec();
        BlockPos blockPos = null;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {
                for (int k = -4; k <= 4; ++k) {
                    BlockPos blockPos2 = new BlockPos(vector3d.x + (double)i, vector3d.y + (double)j, vector3d.z + (double)k);
                    Block block2 = Teleport.mc.world.getBlockState(blockPos2).getBlock();
                    if (blockPos == null && block2 == block) {
                        blockPos = blockPos2;
                    }
                    if (block2 != block || !(Teleport.mc.player.getDistance(blockPos) < Teleport.mc.player.getDistance(blockPos2))) continue;
                    blockPos = blockPos2;
                }
            }
        }
        return blockPos;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.visitedPositions.clear();
    }

    private static void lambda$processEventStartRiding$0() {
        try {
            Thread.sleep(300L);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        Teleport.mc.player.stopRiding();
    }
}

