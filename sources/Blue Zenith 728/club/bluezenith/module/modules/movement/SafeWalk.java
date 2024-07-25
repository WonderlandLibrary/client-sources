package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;

public class SafeWalk extends Module {

    public final BooleanValue onlyGround = new BooleanValue("Only ground", false).setIndex(1);

    public SafeWalk() {
        super("SafeWalk", ModuleCategory.MOVEMENT);
    }

    @Listener
    public void moveEvent(MoveEvent event) {
        event.safeWalkState = onlyGround.get() ? 1 : 2;
    }

}
