package dev.stephen.nexus.module.modules.combat.criticals;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventAttack;
import dev.stephen.nexus.module.modules.combat.Criticals;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;

public class WatchdogCriticals extends SubMode<Criticals> {
    public WatchdogCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.isOnGround()) {
            mc.player.setPosition(mc.player.getX(), mc.player.getY() + 0.001D, mc.player.getZ());
        }
    };
}
