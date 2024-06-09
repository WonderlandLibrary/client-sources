package dev.vertic.module.impl.movement;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.other.StrafeEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.util.player.MoveUtil;

public class Strafe extends Module {

    public Strafe() {
        super("Strafe", "Allows you to strafe freely mid-air.", Category.MOVEMENT);
    }

    @EventLink
    public void onStrafe(StrafeEvent event) {
        MoveUtil.strafe();
    }

}
