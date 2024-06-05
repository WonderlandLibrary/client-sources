package de.dietrichpaul.clientbase.event.network;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;

public interface SendPacketListener extends Listener {

    void onSendPacket(SendPacketEvent event);

    class SendPacketEvent extends CancellableEvent<SendPacketListener> {

        private final EventExecutor<SendPacketListener> executor = listener -> listener.onSendPacket(this);
        private Packet<?> packet;
        private PacketCallbacks packetCallbacks;

        public SendPacketEvent(Packet<?> packet, PacketCallbacks packetCallbacks) {
            this.packet = packet;
            this.packetCallbacks = packetCallbacks;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(Packet<?> packet) {
            this.packet = packet;
        }

        public PacketCallbacks getPacketCallbacks() {
            return packetCallbacks;
        }

        public void setPacketCallbacks(PacketCallbacks packetCallbacks) {
            this.packetCallbacks = packetCallbacks;
        }

        @Override
        public EventExecutor<SendPacketListener> getEventExecutor() {
            return executor;
        }

        @Override
        public Class<SendPacketListener> getListenerType() {
            return SendPacketListener.class;
        }
    }

}
