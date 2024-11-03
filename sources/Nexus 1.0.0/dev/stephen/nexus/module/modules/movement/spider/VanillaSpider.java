package dev.stephen.nexus.module.modules.movement.spider;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Spider;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;

public class VanillaSpider extends SubMode<Spider> {
    public VanillaSpider(String name, Spider parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.horizontalCollision && mc.player.fallDistance < 1) {
            MoveUtils.setMotionY(getParentModule().verticalMotion.getValueFloat());
        }
    };
}
