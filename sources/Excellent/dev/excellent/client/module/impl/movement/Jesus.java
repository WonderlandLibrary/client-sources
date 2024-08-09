package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(name = "Jesus", description = "Позволяет бегать по воде", category = Category.MOVEMENT)
public class Jesus extends Module {
    private int ticks;
    private final Listener<MotionEvent> onMotion = e -> {
        BlockPos playerPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY() + 0.008D, mc.player.getPosZ());
        Block playerBlock = mc.world.getBlockState(playerPos).getBlock();
        if (playerBlock == Blocks.WATER && !mc.player.isOnGround()) {
            boolean isUp = mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getPosY() + 0.03D, mc.player.getPosZ())).getBlock() == Blocks.WATER;
            mc.player.jumpMovementFactor = 0.0F;
            float yPort = MoveUtil.getMotion() > 0.1D ? 0.02F : 0.032F;
            mc.player.setVelocity(mc.player.motion.x, (double) mc.player.fallDistance < 3.5D ? (double) (isUp ? yPort : -yPort) : -0.1D, mc.player.motion.z);
        }

        double posY = mc.player.getPosY();
        if (posY > (double) ((int) posY) + 0.89D && posY <= (double) ((int) posY + 1) || (double) mc.player.fallDistance > 3.5D) {
            mc.player.getPositionVec().y = ((double) ((int) posY + 1) + 1.0E-45D);
            if (!mc.player.isInWater()) {
                BlockPos waterBlockPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY() - 0.1D, mc.player.getPosZ());
                Block waterBlock = mc.world.getBlockState(waterBlockPos).getBlock();
                if (waterBlock == Blocks.WATER) {
                    e.setOnGround(false);
                    if (ticks == 1) {
                        MoveUtil.setSpeed(1.1f);
                        ticks = 0;
                    } else {
                        ticks = 1;
                    }
                }
            }
        }
    };
}
