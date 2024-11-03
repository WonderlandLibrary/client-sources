package dev.stephen.nexus.module.modules.player.scaffold.tower;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;

public class VulcanTower extends SubMode<Scaffold> {
    public VulcanTower(String name, Scaffold parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (getParentModule().canTower()) {
            if (mc.player.isOnGround()) {
                MoveUtils.setMotionY(0.42F);
            }
            switch (PlayerUtil.inAirTicks() % 3) {
                case 0:
                    MoveUtils.setMotionY(0.41985 + (Math.random() * 0.000095));
                    break;
                case 2:
                    MoveUtils.setMotionY(Math.ceil(mc.player.getY()) - mc.player.getY());
                    break;
            }
        }
    };
}
