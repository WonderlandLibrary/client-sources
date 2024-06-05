package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;

/**
 * @author linus
 * @since 1.0
 */
public class ParkourModule extends ToggleModule {

    /**
     *
     */
    public ParkourModule() {
        super("Parkour", "Automatically jumps at the edge of blocks", ModuleCategory.MOVEMENT);
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (event.getStage() != EventStage.PRE) {
            return;
        }
        if (mc.player.isOnGround() && !mc.player.isSneaking()
                && mc.world.isSpaceEmpty(mc.player.getBoundingBox().offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001))) {
            mc.player.jump();
        }
    }
}
