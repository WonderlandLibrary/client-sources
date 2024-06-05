package com.boomer.client.module.modules.movement;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.value.impl.BooleanValue;

import java.awt.*;

/**
 * made by oHare for Client
 *
 * @since 5/29/2019
 **/
public class Sprint extends Module {
    private BooleanValue multiDir = new BooleanValue("MultiDirectional", true);

    public Sprint() {
        super("Sprint", Category.MOVEMENT, new Color(0, 255, 0, 255).getRGB());
        setDescription("the lazy way");
        addValues(multiDir);
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        setSuffix(multiDir.isEnabled() ? "Multi":null);
        mc.thePlayer.setSprinting(canSprint());
    }

    private boolean canSprint() {
        return !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking() && mc.thePlayer.getFoodStats().getFoodLevel() > 6 && (multiDir.isEnabled() ? mc.thePlayer.isMoving() : mc.thePlayer.moveForward > 0);
    }
}
