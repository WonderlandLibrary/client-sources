package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.impl.BooleanValue;

/**
 * @author Auth
 * @since 20/10/2021
 */
@ModuleInfo(name = "module.movement.sprint.name", description = "module.movement.sprint.description", category = Category.MOVEMENT)
public class SprintModule extends Module {
    private final BooleanValue legit = new BooleanValue("Legit", this, true);

    @EventLink(value = Priority.LOW)
    public final Listener<StrafeEvent> onStrafe = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);

        if (mc.thePlayer.omniSprint && MoveUtil.isMoving() && !legit.getValue()) {
            mc.thePlayer.setSprinting(true);
        }

        mc.thePlayer.omniSprint = !legit.getValue() && MoveUtil.isMoving() && !mc.thePlayer.isCollidedHorizontally &&
                !mc.thePlayer.isSneaking() && !mc.thePlayer.isUsingItem();
    };

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
        mc.thePlayer.omniSprint = false;
    }
}