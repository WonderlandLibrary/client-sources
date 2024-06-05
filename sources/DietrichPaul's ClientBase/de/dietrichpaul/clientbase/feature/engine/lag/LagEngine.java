package de.dietrichpaul.clientbase.feature.engine.lag;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.lag.DelayIncomingPacketListener;
import de.dietrichpaul.clientbase.event.network.ReceivePacketListener;
import de.florianmichael.dietrichevents.DietrichEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.util.math.MathHelper;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LagEngine implements ReceivePacketListener {

    private final Queue<DelayedPacket> incoming = new ConcurrentLinkedQueue<>();

    private boolean stopTraffic;
    private boolean lastWasAck;

    public LagEngine() {
        DietrichEvents eventDispatcher = ClientBase.INSTANCE.getEventDispatcher();
        eventDispatcher.subscribe(ReceivePacketListener.class, this);
    }

    public boolean isAcknowledgment(Packet<?> packet) {
        if (packet instanceof GameStateChangeS2CPacket gameState) {
            int screen = MathHelper.floor(gameState.getValue() + 0.5f);
            if (gameState.getReason() == GameStateChangeS2CPacket.GAME_WON && (screen == 0 || screen == 1))
                return true;
        }

        /*return packet instanceof PlayPingS2CPacket || packet instanceof KeepAliveS2CPacket
                || packet instanceof ChatMessageS2CPacket;*/
        return false;
    }

    @Override
    public void onReceivePacket(ReceivePacketEvent event) {
        if (!(event.getListener() instanceof ClientPlayNetworkHandler) || MinecraftClient.getInstance().player == null)
            return;

        boolean hasSubscribers = ClientBase.INSTANCE.getEventDispatcher().hasSubscribers(DelayIncomingPacketListener.class);

        Packet<?> packet = event.getPacket();
        if (this.stopTraffic || hasSubscribers && isAcknowledgment(packet)) {
            incoming.add(new DelayedPacket(packet, event.getListener()));
            event.cancel();
            if (!isAcknowledgment(packet)) {
                DelayIncomingPacketListener.DelayIncomingPacketEvent delayEvent =
                        new DelayIncomingPacketListener.DelayIncomingPacketEvent(packet);
                ClientBase.INSTANCE.getEventDispatcher().post(delayEvent);
            }
            return;
        }
        DelayIncomingPacketListener.DelayIncomingPacketEvent delayEvent =
                new DelayIncomingPacketListener.DelayIncomingPacketEvent(packet);
        if (hasSubscribers) ClientBase.INSTANCE.getEventDispatcher().post(delayEvent);
        if (delayEvent.isStopTraffic()) {
            stopTraffic = true;
            incoming.add(new DelayedPacket(packet, event.getListener()));
            event.cancel();
        } else {
            handlePackets();
        }
    }

    public void handlePackets() {
        while (!incoming.isEmpty()) {
            try {
                incoming.poll().handle();
            } catch (OffThreadException e) {
            }
        }
    }

    public void release() {
        handlePackets();
        stopTraffic = false;
    }
}
