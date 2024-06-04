package com.polarware.module.impl.movement.sneak;

import com.polarware.module.impl.movement.SneakModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PostMotionEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C0BPacketEntityAction;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class NCPSneak extends Mode<SneakModule> {

    public NCPSneak(String name, SneakModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;

        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    };

    @Override
    public void onDisable() {
        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
}