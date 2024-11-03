package dev.stephen.nexus.module.modules.player.antivoid;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.modules.player.AntiVoid;
import dev.stephen.nexus.module.modules.player.Blink;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class BlinkAntiVoid extends SubMode<AntiVoid> {
    public BlinkAntiVoid(String name, AntiVoid parentModule) {
        super(name, parentModule);
    }

    private Vec3d position, motion;
    private boolean wasVoid, setBack;
    private int overVoidTicks;
    private boolean blinking;

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Fly.class).isEnabled()) {
            return;
        }

        if (mc.player.getAbilities().allowFlying) return;
        if (PlayerUtil.ticksExisted() <= 50) return;

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() || (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(KillAura.class).target != null)) {
            Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
            return;
        }

        boolean overVoid = !mc.player.isOnGround() && PlayerUtil.isOverVoid();

        if (overVoid) {
            overVoidTicks++;
        } else if (mc.player.isOnGround()) {
            overVoidTicks = 0;
        }

        if (overVoid && position != null && motion != null && overVoidTicks < 30 + getParentModule().minFallDistance.getValue() * 20) {
            if (!setBack) {
                wasVoid = true;
                blinking = true;
                Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(true);
                if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue() || setBack) {
                    PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(position.x, position.y - 0.1 - Math.random(), position.z, false));
                    Client.INSTANCE.getModuleManager().getModule(Blink.class).packets.clear();
                    mc.player.fallDistance = 0;
                    setBack = true;
                }
            } else {
                blinking = false;
                Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
            }
        } else {
            setBack = false;
            if (wasVoid) {
                blinking = false;
                Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
                wasVoid = false;
            }
            motion = new Vec3d(mc.player.getVelocity().x, mc.player.getVelocity().y, mc.player.getVelocity().z);
            position = new Vec3d(mc.player.getPos().x, mc.player.getPos().y, mc.player.getPos().z);
        }
    };

    @Override
    public void onDisable() {
        if (blinking) {
            Client.INSTANCE.getModuleManager().getModule(Blink.class).setEnabled(false);
            blinking = false;
        }
        super.onDisable();
    }
}
