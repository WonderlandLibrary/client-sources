package dev.stephen.nexus.module.modules.movement.fly;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;

public class VulcanGlideFly extends SubMode<Fly> {
    public VulcanGlideFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.fallDistance > 0.1) {
            if (mc.player.age % 2 == 0) {
                mc.player.getVelocity().y = -0.155;
            } else {
                mc.player.getVelocity().y = -0.1;
            }
        }
    };
}
