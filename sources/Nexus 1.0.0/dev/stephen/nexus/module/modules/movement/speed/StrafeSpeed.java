package dev.stephen.nexus.module.modules.movement.speed;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Speed;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;

public class StrafeSpeed extends SubMode<Speed> {
    public StrafeSpeed(String name, Speed parentModule) {
        super(name, parentModule);
    }
    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (!MoveUtils.isMoving2()) {
            return;
        }
        MoveUtils.strafe();
    };
}
