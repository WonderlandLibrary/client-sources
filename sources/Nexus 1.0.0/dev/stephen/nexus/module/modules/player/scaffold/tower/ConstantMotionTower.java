package dev.stephen.nexus.module.modules.player.scaffold.tower;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;

// nightx trust
public class ConstantMotionTower extends SubMode<Scaffold> {
    public ConstantMotionTower(String name, Scaffold parentModule) {
        super(name, parentModule);
    }

    private double jumpGround = 0;

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (getParentModule().canTower()) {
            if (mc.player.isOnGround()) {
                jumpGround = mc.player.getY();
                mc.player.getVelocity().y = getParentModule().constantMotionValue.getValue();
            }
            if (mc.player.getY() > jumpGround + getParentModule().constantMotionJumpGroundValue.getValue()) {
                mc.player.setPosition(mc.player.getX(), (int) mc.player.getY(), mc.player.getZ());
                mc.player.getVelocity().y = getParentModule().constantMotionValue.getValue();
                jumpGround = mc.player.getY();
            }
        }
    };

    @Override
    public void onEnable() {
        jumpGround = mc.player.getY();
        super.onEnable();
    }
}
