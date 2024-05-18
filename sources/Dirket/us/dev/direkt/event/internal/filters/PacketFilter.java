package us.dev.direkt.event.internal.filters;

import net.minecraft.network.Packet;
import us.dev.direkt.event.internal.events.game.network.PacketEvent;
import us.dev.dvent.Link;
import us.dev.dvent.filter.Filter;

/**
 * @author Foundry
 */
public class PacketFilter<T extends PacketEvent> implements Filter<T> {
    private final Class<? extends Packet<?>>[] packets;

    @SafeVarargs
    public PacketFilter(Class<? extends Packet<?>>... packets) {
        this.packets = packets;
    }

    @Override
    public boolean test(Link<T> link, T event) {
        for (Class<? extends Packet<?>> packetClass : packets) {
            if (packetClass.isAssignableFrom(event.getPacket().getClass())) {
                return true;
            }
        }
        return false;
    }
}
