package dev.stephen.nexus.module.modules.player.antivoid;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.modules.player.AntiVoid;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.util.math.Vec3d;

public class VanillaAntiVoid extends SubMode<AntiVoid> {
    public VanillaAntiVoid(String name, AntiVoid parentModule) {
        super(name, parentModule);
    }

    private Vec3d lastSafePos;

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Fly.class).isEnabled()) {
            return;
        }

        if (PlayerUtil.isOverVoid() && mc.player.fallDistance >= getParentModule().minFallDistance.getValueFloat()) {
            mc.player.setPosition(lastSafePos);
        } else if (mc.player.isOnGround()) {
            lastSafePos = new Vec3d(mc.player.getBlockPos().toCenterPos().x, mc.player.getY(), mc.player.getBlockPos().toCenterPos().z);
        }
    };
}
