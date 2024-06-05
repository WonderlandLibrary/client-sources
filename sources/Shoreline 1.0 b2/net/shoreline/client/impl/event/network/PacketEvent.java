package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.shoreline.client.init.Managers;
import net.minecraft.network.packet.Packet;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class PacketEvent extends Event
{
    //
    private final Packet<?> packet;

    /**
     *
     *
     * @param packet
     */
    public PacketEvent(Packet<?> packet)
    {
        this.packet = packet;
    }

    /**
     *
     *
     * @return
     */
    public Packet<?> getPacket()
    {
        return packet;
    }

    /**
     *
     */
    @Cancelable
    public static class Inbound extends PacketEvent
    {
        /**
         *
         *
         * @param packet
         */
        public Inbound(Packet<?> packet)
        {
            super(packet);
        }
    }

    /**
     *
     */
    @Cancelable
    public static class Outbound extends PacketEvent
    {
        //
        private final boolean cached;

        /**
         *
         *
         * @param packet
         */
        public Outbound(Packet<?> packet)
        {
            super(packet);
            this.cached = Managers.NETWORK.isCached(packet);
        }

        /**
         *
         * @return
         */
        public boolean isClientPacket()
        {
            return cached;
        }
    }
}
