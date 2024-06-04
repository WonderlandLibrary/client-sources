package com.polarware.module.impl.movement.noslow;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.module.impl.movement.NoSlowModule;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class PolarNoSlow extends Mode<NoSlowModule> {

    public PolarNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }

    @EventLink
    public Listener<PreMotionEvent> onPreMotion = event -> {
        if (MoveUtil.isMoving() && mc.thePlayer.isUsingItem()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
    };

    @EventLink
    public Listener<SlowDownEvent> onSlowDown = event -> {
        mc.thePlayer.setSprinting(false);
        event.setCancelled(true);
    };

}