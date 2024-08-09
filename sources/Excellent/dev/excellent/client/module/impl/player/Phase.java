package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;

@ModuleInfo(name = "Phase", description = "Позволяет выбираться из блоков", category = Category.PLAYER)
public class Phase extends Module {
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (!collisionPredict()) {
            if (mc.gameSettings.keyBindJump.pressed) {
                mc.player.setOnGround(true);
            }
        }
    };

    public boolean collisionPredict() {
        boolean prevCollision = mc.world
                .getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList().isEmpty();

        return mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D))
                .toList().isEmpty() && prevCollision;
    }
}
