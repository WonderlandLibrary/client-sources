package dev.star.module.impl.player;

import dev.star.event.impl.player.SafeWalkEvent;
import dev.star.module.Category;
import dev.star.module.Module;

public final class SafeWalk extends Module {
    @Override
    public void onSafeWalkEvent(SafeWalkEvent e) {
        if (mc.thePlayer == null) return;
        e.setSafe(true);
    }

    public SafeWalk() {
        super("SafeWalk", Category.PLAYER, "prevents walking off blocks");
    }

}
