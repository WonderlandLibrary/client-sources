package dev.stephen.nexus.module.modules.player.scaffold.tower;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;

public class NormalTower extends SubMode<Scaffold> {
    public NormalTower(String name, Scaffold parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (getParentModule().canTower()) {
            MoveUtils.setMotionY(0.42F);
        }
    };
}
