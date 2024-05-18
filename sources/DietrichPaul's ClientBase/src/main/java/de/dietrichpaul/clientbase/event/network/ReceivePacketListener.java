package de.dietrichpaul.clientbase.event.network;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

public interface ReceivePacketListener extends Listener {

    void onReceivePacket(ReceivePacketEvent event);

    class ReceivePacketEvent extends CancellableEvent<ReceivePacketListener> {

        private Packet<?> packet;
        private PacketListener listener;
        private final EventExecutor<ReceivePacketListener> executor = listener -> listener.onReceivePacket(this);

        public ReceivePacketEvent(Packet<?> packet, PacketListener listener) {
            this.packet = packet;
            this.listener = listener;
        }

        public PacketListener getListener() {
            return listener;
        }

        public void setListener(PacketListener listener) {
            this.listener = listener;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(Packet<?> packet) {
            this.packet = packet;
        }

        @Override
        public EventExecutor<ReceivePacketListener> getEventExecutor() {
            return executor;
        }

        @Override
        public Class<ReceivePacketListener> getListenerType() {
            return ReceivePacketListener.class;
        }
    }

}
