package ac.sapphire.client.event.events.network

import ac.sapphire.client.model.Cancellable
import net.minecraft.network.Packet

data class PacketSendEvent(val packet: Packet<*>) : Cancellable()