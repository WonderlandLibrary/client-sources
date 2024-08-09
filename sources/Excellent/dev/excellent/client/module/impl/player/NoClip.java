package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.MoveEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.util.math.vector.Vector3d;

@ModuleInfo(name = "No Clip", description = "Позволяет ходить сквозь блоки", category = Category.PLAYER)
public class NoClip extends Module {
    private final Listener<MoveEvent> onMove = move -> {
        if (!collisionPredict(move.getTo())) {
            if (move.isCollidedHorizontal())
                move.setIgnoreHorizontal(true);
            if (move.getMotion().y > 0 || mc.player.isSneaking()) {
                move.setIgnoreVertical(true);
            }
            move.getMotion().y = Math.min(move.getMotion().y, 99999);
        }
    };

    public boolean collisionPredict(Vector3d to) {
        boolean prevCollision = mc.world
                .getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList().isEmpty();
        Vector3d backUp = new Vector3d(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
        mc.player.setPosition(to.x, to.y, to.z);
        boolean collision = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D))
                .toList().isEmpty() && prevCollision;
        mc.player.setPosition(backUp.x, backUp.y, backUp.z);
        return collision;
    }
}
