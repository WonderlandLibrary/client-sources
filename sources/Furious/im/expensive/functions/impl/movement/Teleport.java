package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventStartRiding;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.math.StopWatch;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.system.CallbackI;

import java.util.*;

@FunctionRegister(name = "Teleport", type = Category.Movement)
public class Teleport extends Function {
    private final StopWatch timer = new StopWatch();
    private final Random random = new Random();
    private final Set<BlockPos> visitedPositions = new HashSet<>();
    private final int range = 4;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (timer.isReached(10))
            processEventUpdate();

    }

    @Subscribe
    public void onRiding(EventStartRiding e) {
        processEventStartRiding(e);
    }


    private void processEventUpdate() {
        Block randomBlock = findRandomNearbyBlock();
        if (randomBlock != null) {
            sitOnSlab(randomBlock);
        }
        timer.reset();
    }

    private void processEventStartRiding(EventStartRiding event) {
        Entity entity = event.e;
        visitedPositions.add(entity.getPosition());
        System.out.println("1");
        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mc.player.stopRiding();
        }).start();
    }

    private Block findRandomNearbyBlock() {
        List<BlockPos> potentialPositions = getPotentialPositions();
        if (!potentialPositions.isEmpty()) {
            BlockPos selectedPos = potentialPositions.get(random.nextInt(potentialPositions.size()));
            return mc.world.getBlockState(selectedPos).getBlock();
        }
        return null;
    }

    private List<BlockPos> getPotentialPositions() {
        Vector3d playerPos = mc.player.getPositionVec();
        List<BlockPos> potentialPositions = new ArrayList<>();

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = new BlockPos(playerPos.x + x, playerPos.y + y, playerPos.z + z);
                    if (isPositionEligible(pos)) {
                        potentialPositions.add(pos);
                    }
                }
            }
        }
        return potentialPositions;
    }

    public static double getDistanceOfEntityToBlock(final Entity entity, final BlockPos blockPos) {
        return mc.player.getDistance(blockPos);
    }

    private boolean isPositionEligible(BlockPos pos) {
        return !visitedPositions.contains(pos)
                && mc.world.getBlockState(pos).getBlock() instanceof SlabBlock || mc.world.getBlockState(pos).getBlock() instanceof StairsBlock
                && mc.world.isAirBlock(pos.up()) && mc.world.isAirBlock(pos.up(2));
    }

    private void sitOnSlab(Block block) {
        BlockPos pos = findSlabPosition(block);
        if (pos != null && !visitedPositions.contains(pos)) {
            Vector3d hitVec = new Vector3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(hitVec, Direction.UP, pos, false);
            mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, blockRayTraceResult);
        }
    }

    private BlockPos findSlabPosition(Block slab) {
        Vector3d playerPos = mc.player.getPositionVec();
        BlockPos posr = null;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = new BlockPos(playerPos.x + x, playerPos.y + y, playerPos.z + z);
                    Block block = mc.world.getBlockState(pos).getBlock();
                    if (posr == null && block == slab) {
                        posr = pos;
                    }
                    if (block == slab && mc.player.getDistance(posr) < mc.player.getDistance(pos)) {
                        posr = pos;
                    }
                }
            }
        }

        return posr;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        visitedPositions.clear();
    }
}