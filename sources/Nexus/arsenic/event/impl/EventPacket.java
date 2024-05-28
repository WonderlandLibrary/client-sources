package arsenic.event.impl;

import arsenic.event.types.CancellableEvent;
import net.minecraft.network.Packet;

public class EventPacket extends CancellableEvent {

    private Packet<?> packet;
    private boolean cancelled;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public static class OutGoing extends EventPacket {
        public OutGoing(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Incoming extends EventPacket {

        public Incoming(Packet<?> packet) {
            super(packet);
        }

        public static class Pre extends Incoming {

            public Pre(Packet<?> packet) {
                super(packet);
            }
        }

        public static class Post extends Incoming {

            public Post(Packet<?> packet) {
                super(packet);
            }
        }
    }

    public static class All extends EventPacket {

        public All(Packet<?> packet) {
            super(packet);
        }

        public static class OutGoing extends All {
            public OutGoing(Packet<?> packet) {
                super(packet);
            }
        }

        public static class Pre extends All {
            public Pre(Packet<?> packet) {
                super(packet);
            }
        }

        public static class Post extends All {

            public Post(Packet<?> packet) {
                super(packet);
            }
        }
    }
}
