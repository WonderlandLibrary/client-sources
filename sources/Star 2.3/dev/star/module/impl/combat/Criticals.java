package dev.star.module.impl.combat;

import dev.star.event.impl.network.PacketSendEvent;
import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.server.PacketUtils;
import dev.star.utils.time.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C18PacketSpectate;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public final class Criticals extends Module {

    private boolean stage;
    private double offset;
    private int groundTicks;
    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Packet", "Dev", "Verus");
    private final NumberSetting delay = new NumberSetting("Delay", 1, 20, 0, 1);
    private final TimerUtil timer = new TimerUtil();

    public Criticals() {
        super("Criticals", Category.COMBAT, "Crit attacks");
        delay.addParent(mode, m -> !(m.is("Verus")));
        this.addSettings(mode,  delay);
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent e) {
    }

    @Override
    public void onMotionEvent(MotionEvent e) {
        this.setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "Watchdog":
                if (e.isPre())
                {
                    if (mc.thePlayer.ticksSinceVelocity <= 80 && mc.thePlayer.fallDistance < 1.8) {
                        e.setOnGround(false);
                    }
                }
                break;
            case "Packet":
                if (mc.objectMouseOver.entityHit != null && mc.thePlayer.onGround) {
                    if (mc.objectMouseOver.entityHit.hurtResistantTime > delay.getValue().intValue()) {
                        for (double offset : new double[]{0.006253453, 0.002253453, 0.001253453}) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                        }
                    }
                }
                break;
            case "Dev":
                if (mc.objectMouseOver.entityHit != null && mc.thePlayer.onGround) {
                    if (mc.objectMouseOver.entityHit.hurtResistantTime > delay.getValue().intValue()) {
                        for (double offset : new double[]{0.06253453, 0.02253453, 0.001253453, 0.0001135346}) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                            PacketUtils.sendPacketNoEvent(new C18PacketSpectate(UUID.randomUUID()));
                        }
                    }
                }
                break;
            case "Verus":
                if (KillAura.attacking && KillAura.target != null && e.isOnGround()) {
                    switch (KillAura.target.hurtResistantTime) {
                        case 17:
                        case 19:
                            e.setOnGround(false);
                            e.setY(e.getY() + ThreadLocalRandom.current().nextDouble(0.001, 0.0011));
                            break;
                        case 18:
                        case 20:
                            e.setOnGround(false);
                            e.setY(e.getY() + 0.03 + ThreadLocalRandom.current().nextDouble(0.001, 0.0011));
                            break;
                    }
                }
        }
    }

}
