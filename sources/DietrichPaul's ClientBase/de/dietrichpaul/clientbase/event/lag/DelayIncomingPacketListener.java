package de.dietrichpaul.clientbase.event.lag;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import net.minecraft.network.packet.Packet;

public interface DelayIncomingPacketListener extends Listener {

    void onDelayIncomingPacket(DelayIncomingPacketEvent event);

    class DelayIncomingPacketEvent extends AbstractEvent<DelayIncomingPacketListener> {

        private final EventExecutor<DelayIncomingPacketListener> executor = listener -> listener.onDelayIncomingPacket(this);
        private final Packet<?> packet;
        private boolean stopTraffic;

        public DelayIncomingPacketEvent(Packet<?> packet) {
            this.packet = packet;
        }

        public void stopTraffic() {
            stopTraffic = true;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public boolean isStopTraffic() {
            return stopTraffic;
        }

        @Override
        public EventExecutor<DelayIncomingPacketListener> getEventExecutor() {
            return executor;
        }

        @Override
        public Class<DelayIncomingPacketListener> getListenerType() {
            return DelayIncomingPacketListener.class;
        }
    }

}
