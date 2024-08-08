package lol.point.returnclient.module.managers.impl;

import lol.point.Return;
import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.module.managers.Manager;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.Packet;

import java.util.ArrayDeque;

public class BlinkManager extends Manager {

    /*
        USAGE:
          ENABLING:
             Return.INSTANCE.moduleManager.blinkManager.enable();

          DISABLING:
            Return.INSTANCE.moduleManager.blinkManager.disable();
     */

    public Object parent;

    public BlinkManager(Object parent) {
        Return.BUS.subscribe(this);
        this.parent = parent;
    }

    public final ArrayDeque<Packet<?>> outPacketDeque = new ArrayDeque<>();
    public boolean active = false;

    @Subscribe
    private final Listener<EventPacket> eventPacketListener = new Listener<>(eventPacket -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            active = false;
            return;
        }

        if (active && eventPacket.isOutbound()) {
            outPacketDeque.add(eventPacket.packet);
            eventPacket.setCancelled(true);
        }
    });

    public void releasePackets() {
        while (!outPacketDeque.isEmpty()) {
            send(outPacketDeque.poll());
        }
    }

    public void enable() {
        outPacketDeque.clear();
        active = true;
    }

    public void disable() {
        active = false;
        releasePackets();
    }

    public void send(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public void sendNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueueUnregistered(packet);
    }

}
