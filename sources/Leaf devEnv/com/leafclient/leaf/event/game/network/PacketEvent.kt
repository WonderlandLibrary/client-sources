package com.leafclient.leaf.event.game.network

import fr.shyrogan.publisher4k.Cancellable
import net.minecraft.network.Packet

abstract class PacketEvent(var packet: Packet<*>): Cancellable() {

    class Send(packet: Packet<*>): PacketEvent(packet)

    class Receive(packet: Packet<*>): PacketEvent(packet)

}