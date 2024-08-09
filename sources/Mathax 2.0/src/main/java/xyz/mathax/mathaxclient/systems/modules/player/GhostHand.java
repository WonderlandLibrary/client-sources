package xyz.mathax.mathaxclient.systems.modules.player;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;

import java.util.Set;

public class GhostHand extends Module {
    private final Set<BlockPos> posList = new ObjectOpenHashSet<>();

    public GhostHand(Category category) {
        super(category, "Ghost Hand", "Opens containers through walls.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!mc.options.useKey.isPressed() || mc.player.isSneaking()) {
            return;
        }

        for (BlockEntity blockEntity : Utils.blockEntities()) {
            if (new BlockPos(mc.player.raycast(mc.interactionManager.getReachDistance(), mc.getTickDelta(), false).getPos()).equals(blockEntity.getPos())) {
                return;
            }
        }

        Vec3d direction = new Vec3d(0, 0, 0.1).rotateX(-(float) Math.toRadians(mc.player.getPitch())).rotateY(-(float) Math.toRadians(mc.player.getYaw()));

        posList.clear();

        for (int i = 1; i < mc.interactionManager.getReachDistance() * 10; i++) {
            BlockPos pos = new BlockPos(mc.player.getCameraPosVec(mc.getTickDelta()).add(direction.multiply(i)));
            if (posList.contains(pos)) {
                continue;
            }

            posList.add(pos);

            for (BlockEntity blockEntity : Utils.blockEntities()) {
                if (blockEntity.getPos().equals(pos)) {
                    mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), Direction.UP, pos, true));
                    return;
                }
            }
        }
    }
}