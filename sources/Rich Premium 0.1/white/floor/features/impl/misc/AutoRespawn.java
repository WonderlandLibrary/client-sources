package white.floor.features.impl.misc;

import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

public class AutoRespawn extends Feature {
    public AutoRespawn() {
        super("AutoRespawn", "AutoRespawn if you death", 0, Category.PLAYER);
    }

    @EventTarget
    private void respawn(EventUpdate upd) {
        if (mc.player.isDead) {
            mc.player.respawnPlayer();
        }
    }
}