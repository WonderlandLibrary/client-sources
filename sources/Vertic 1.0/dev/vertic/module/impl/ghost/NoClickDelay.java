package dev.vertic.module.impl.ghost;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.motion.PreMotionEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.util.player.PlayerUtil;

public class NoClickDelay extends Module {

    public NoClickDelay() {
        super("NoClickDelay", "Removes the retarded click delay added in 1.8", Category.GHOST);
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        if (PlayerUtil.isInGame()) {
            mc.leftClickCounter = 0;
        }
    }

}
