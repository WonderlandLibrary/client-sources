package dev.stephen.nexus.module.modules.movement.spider;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Spider;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PlayerUtil;

public class VulcanSpider extends SubMode<Spider> {
    public VulcanSpider(String name, Spider parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.horizontalCollision && mc.player.fallDistance < 1) {
            if (PlayerUtil.ticksExisted() % 2 == 0) {
                mc.player.jump();
            }
        }
    };
}
