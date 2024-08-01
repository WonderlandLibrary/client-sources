package wtf.diablo.client.module.impl.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

@ModuleMetaData(
        name = "Strafe",
        description = "Allows you to move faster",
        category = ModuleCategoryEnum.MOVEMENT
)
public final class StrafeModule extends AbstractModule {

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        MovementUtil.strafe();
    };

}