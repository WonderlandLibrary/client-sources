package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.item.BlockItem;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "Jesus", type = Categories.Movement, server = "")
public class Jesus extends Module {

    @Subscribe
    private void onUpdate(EventUpdate update) {
        if (mc.player == null) return;

        if (mc.player.isInWater() || mc.player.isInLava()) {
            if (hasBlocksInInventory()) {
                BlockPos blockPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY() - 1, mc.player.getPosZ());
                sendBlockPackets(blockPos);
            }

            if (isMoving()) {
                mc.player.setMotion(new Vector3d(mc.player.getMotion().x, 0.1, mc.player.getMotion().z));
            } else {
                mc.player.setMotion(new Vector3d(mc.player.getMotion().x, 0.0, mc.player.getMotion().z));
            }

            if (mc.player.collidedHorizontally) {
                mc.player.setMotion(new Vector3d(mc.player.getMotion().x, 0.1, mc.player.getMotion().z));
            }
        }
    }

    private boolean hasBlocksInInventory() {
        for (int i = 0; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof BlockItem) {
                return true;
            }
        }
        return false;
    }

    private void sendBlockPackets(BlockPos blockPos) {
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(
                mc.player.getPosX(),
                mc.player.getPosY() - 0.1,
                mc.player.getPosZ(),
                mc.player.rotationYaw,
                mc.player.rotationPitch,
                mc.player.isOnGround()
        ));
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(
                mc.player.getPosX(),
                mc.player.getPosY(),
                mc.player.getPosZ(),
                mc.player.rotationYaw,
                mc.player.rotationPitch,
                mc.player.isOnGround()
        ));
    }

    private boolean isMoving() {
        return mc.player.moveForward != 0 || mc.player.moveStrafing != 0;
    }
}
