package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.util.math.AxisAlignedBB;

@ModuleInfo(name = "Parkour", description = "Прыгает, если вы стоите на краю блока", category = Category.PLAYER)
public class Parkour extends Module {
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (isBlockUnder(0.001f) && mc.player.isOnGround()) {
            mc.player.jump();
        }
    };

    private boolean isBlockUnder(float under) {
        if (mc.player.getPosY() < 0.0) {
            return false;
        } else {
            AxisAlignedBB aab = mc.player.getBoundingBox().offset(0.0, -under, 0.0);
            return mc.world.getCollisionShapes(mc.player, aab).toList().isEmpty();
        }
    }
}
