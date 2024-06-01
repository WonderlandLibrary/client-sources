package com.polarware.module.impl.player.antivoid;

import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketAntiVoid extends Mode<AntiVoidModule> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    public PacketAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue() && !PlayerUtil.isBlockUnder()) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition());
        }
    };
}