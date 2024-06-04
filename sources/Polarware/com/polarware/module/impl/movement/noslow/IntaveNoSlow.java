package com.polarware.module.impl.movement.noslow;

import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.impl.movement.NoSlowModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PostMotionEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class IntaveNoSlow extends Mode<NoSlowModule> {

    public IntaveNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (InstanceAccess.mc.thePlayer.isBlocking()) {
            PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (InstanceAccess.mc.thePlayer.isBlocking()) {
            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };
}