package com.alan.clients.module.impl.combat.regen;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.Regen;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class VerusRegen extends Mode<Regen> {
    public VerusRegen(String name, Regen parent) {
        super(name, parent);
    }
    private final NumberValue health = new NumberValue("Minimum Health", this, 15, 1, 20, 1);
    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.getHealth() < this.health.getValue().floatValue()) {
            for (int i = 0; i < 30; i++) {
                if( mc.thePlayer.onGround) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

                }
                else{

                }
            }
        }
    };
}

