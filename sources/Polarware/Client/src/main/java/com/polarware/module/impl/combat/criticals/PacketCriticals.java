package com.polarware.module.impl.combat.criticals;

import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import util.time.StopWatch;

public final class PacketCriticals extends Mode<CriticalsModule> {
    private final NumberValue delay = new NumberValue("Delay", this, 500, 0, 1000, 1);

    private final double[] offsets = new double[]{0.0625, 0};
    private final StopWatch stopwatch = new StopWatch();

    public PacketCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        if(stopwatch.finished(delay.getValue().longValue()) && mc.thePlayer.onGroundTicks > 2) {
            for (final double offset : offsets) {
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
            }

            mc.thePlayer.onCriticalHit(event.getTarget());
            stopwatch.reset();
        }
    };
}
