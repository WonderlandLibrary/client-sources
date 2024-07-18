package com.alan.clients.module.impl.movement.step;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.Step;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class WatchdogStep extends Mode<Step> {
    private boolean step;

    public WatchdogStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
    };

    @EventLink
    public final Listener<PreMotionEvent> preMotionEventListener = event -> {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
            step = true;
        }

        if (PlayerUtil.shouldStep()) {
            this.mc.thePlayer.stepHeight = 1.0F;
        }

    };

    @EventLink
    private final Listener<PacketSendEvent> packetSendEventListener = event -> {
        final double[] values = new double[] {0.42, 0.75, 1.0};

        if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition && PlayerUtil.shouldStep()) {
            for (double value : values) {
                event.setPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - value, mc.thePlayer.posY + value, mc.thePlayer.posZ + value, false));
            }
        }

    };

    @EventLink
    public final Listener<PostStrafeEvent> postStrafeEventListener = postStrafeEvent -> {
        if (step) {
            this.mc.thePlayer.jump();
            step = false;
        }
    };
}