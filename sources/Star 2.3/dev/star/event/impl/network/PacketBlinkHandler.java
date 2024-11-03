package dev.star.event.impl.network;

import dev.star.Client;
import dev.star.event.ListenerAdapter;
import lombok.Getter;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static dev.star.utils.Utils.mc;

public class PacketBlinkHandler extends ListenerAdapter {

    @Getter
    private boolean blinking;

    private boolean clearedPackets;

    private final CopyOnWriteArrayList<Packet> packetsQueue = new CopyOnWriteArrayList<>();

    public PacketBlinkHandler() {
        Client.INSTANCE.getEventProtocol().register(this);
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {
        if(mc.isSingleplayer()) return;
        if(mc.thePlayer == null || mc.thePlayer.ticksExisted < 5) {
            if(!clearedPackets) {
                packetsQueue.clear();
                stopBlinking();
                clearedPackets = true;
            }
        } else {
            clearedPackets = false;
        }

        if(!event.isCancelled()) {
            if(blinking) {
                event.cancel();
                packetsQueue.add(event.getPacket());
            }
        }
    }

    public void startBlinking() {
        this.blinking = true;
    }

    public void stopBlinking() {
        this.blinking = false;

        releasePackets();
    }

    public void releasePackets() {
        if(!packetsQueue.isEmpty()) {
            for(Packet p : packetsQueue) {
                mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
            }

            packetsQueue.clear();
        }
    }

    public void releaseWithPingPacketsFirst() {
        if(!packetsQueue.isEmpty()) {
            for(Packet p : packetsQueue) {
                if(p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive) {
                    mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
                }
            }

            for(Packet p : packetsQueue) {
                if(!(p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive)) {
                    mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
                }
            }

            packetsQueue.clear();
        }
    }

    public void releaseWithPingPacketsLast() {
        if(!packetsQueue.isEmpty()) {
            for(Packet p : packetsQueue) {
                if(!(p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive)) {
                    mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
                }
            }

            for(Packet p : packetsQueue) {
                if(p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive) {
                    mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
                }
            }

            packetsQueue.clear();
        }
    }

    public void releasePingPackets() {
        if(!packetsQueue.isEmpty()) {
            ArrayList<Packet> toRemove = new ArrayList<>();

            for(Packet p : packetsQueue) {
                if(p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive) {
                    mc.getNetHandler().getNetworkManager().sendPacketFinal(p);
                    toRemove.add(p);
                }
            }

            for(Packet p : toRemove) {
                packetsQueue.remove(p);
            }

            toRemove.clear();
        }
    }

    public void clearPackets() {
        packetsQueue.clear();
    }
}
