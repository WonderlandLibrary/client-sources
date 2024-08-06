package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.WorldLoadEvent;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Setter;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

@RegisterListeners
public class BlinkUtil {

    public static final ConcurrentLinkedQueue<
        Packet<ClientPlayNetworkHandler>
    > incomingQueue = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<Packet<?>> outgoingQueue =
        new ConcurrentLinkedQueue<>();

    @Setter
    private static boolean incomingBlink;

    @Setter
    private static boolean outgoingBlink;

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {
        if (
            outgoingBlink &&
            !C.mc.isInSingleplayer() &&
            C.p() != null &&
            C.w() != null &&
            C.isInGame()
        ) {
            try {
                outgoingQueue.add(e.getPacket());
                e.setCancelled(true);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive.Pre e) {
        if (
            incomingBlink &&
            !C.mc.isInSingleplayer() &&
            C.p() != null &&
            C.w() != null &&
            C.isInGame()
        ) {
            try {
                incomingQueue.add(
                    (Packet<ClientPlayNetworkHandler>) e.getPacket()
                );
                e.setCancelled(true);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (
            !incomingBlink &&
            !C.mc.isInSingleplayer() &&
            C.p() != null &&
            C.w() != null &&
            C.isInGame()
        ) {
            while (!incomingQueue.isEmpty()) {
                Packet<ClientPlayNetworkHandler> packet = incomingQueue.poll();
                try {
                    packet.apply(C.mc.getNetworkHandler());
                } catch (Exception ignored) {}
            }
        }

        if (
            !outgoingBlink &&
            !C.mc.isInSingleplayer() &&
            C.p() != null &&
            C.w() != null &&
            C.isInGame()
        ) {
            while (!outgoingQueue.isEmpty()) {
                Packet<?> packet = outgoingQueue.poll();
                PacketUtil.sendPacket(packet, false);
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldLoadEvent e) {
        outgoingQueue.clear();
        incomingQueue.clear();

        setIncomingBlink(false);
        setOutgoingBlink(false);
    }
}
