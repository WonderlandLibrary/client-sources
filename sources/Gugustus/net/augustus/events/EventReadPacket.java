package net.augustus.events;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class EventReadPacket extends Event {
   private Packet<?> packet;
   private final INetHandler netHandler;
   private final EnumPacketDirection direction;

   public EventReadPacket(Packet<?> packet, INetHandler netHandler, EnumPacketDirection direction) {
      this.packet = packet;
      this.netHandler = netHandler;
      this.direction = direction;
   }

   public Packet<?> getPacket() {
      return this.packet;
   }

   public void setPacket(Packet<?> packet) {
      this.packet = packet;
   }

   public INetHandler getNetHandler() {
      return this.netHandler;
   }

   public EnumPacketDirection getDirection() {
      return this.direction;
   }
}
