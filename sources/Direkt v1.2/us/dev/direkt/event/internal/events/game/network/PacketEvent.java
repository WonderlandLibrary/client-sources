package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import us.dev.direkt.event.Event;

/**
 * @author Foundry
 */
public interface PacketEvent<T extends INetHandler> extends Event {
    Packet<T> getPacket();
}
