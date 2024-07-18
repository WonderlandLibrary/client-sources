package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.SlowDownEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import net.minecraft.item.ItemFood;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class LegitNoSlow extends Mode<NoSlow> {

    private boolean enabled;

    public LegitNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (!mc.thePlayer.isUsingItem() || !(getComponent(Slot.class).getItemStack().getItem() instanceof ItemFood)) return;

        PingSpoofComponent.blink();

        if (mc.thePlayer.itemInUseCount < -1) {
            mc.gameSettings.keyBindUseItem.setPressed(false);
        }
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C07PacketPlayerDigging) {
            C07PacketPlayerDigging digging = (C07PacketPlayerDigging) packet;

            ChatUtil.display("digging " + digging.getStatus() + " " + event.isCancelled());

//            if (false) PingSpoofComponent.packets.removeIf(timedPacket -> timedPacket.getPacket().equals(packet));
//            PacketUtil.sendNoEvent(packet);
//            PingSpoofComponent.dispatch();
            enabled = false;
        } else if (!enabled && packet instanceof C08PacketPlayerBlockPlacement && getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().getItem() instanceof ItemFood) {
            enabled = true;
            PingSpoofComponent.blink();
            PingSpoofComponent.enabled = true;
            event.setCancelled();
            PacketUtil.sendNoEvent(packet);

            ChatUtil.display("Started");
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if (getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().getItem() instanceof ItemFood) {
            event.setCancelled();
        }
    };

}
