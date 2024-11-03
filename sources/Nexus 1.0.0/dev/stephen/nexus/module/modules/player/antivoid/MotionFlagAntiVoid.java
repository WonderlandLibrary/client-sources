package dev.stephen.nexus.module.modules.player.antivoid;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.modules.player.AntiVoid;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;

public class MotionFlagAntiVoid extends SubMode<AntiVoid> {
    public MotionFlagAntiVoid(String name, AntiVoid parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Fly.class).isEnabled()) {
            return;
        }

        if (PlayerUtil.isOverVoid() && mc.player.fallDistance >= getParentModule().minFallDistance.getValueFloat() && mc.player.getBlockY() + mc.player.getVelocity().y < Math.floor(mc.player.getBlockY())) {
            MoveUtils.setMotionY(3);
            mc.player.fallDistance = 0;
        }
    };
}
