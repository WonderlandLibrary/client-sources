package com.example.editme.events;

import net.minecraft.network.Packet;

public class PacketEvent extends EditmeEvent {
   private final Packet packet;

   public PacketEvent(Packet var1) {
      this.packet = var1;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public static class Send extends PacketEvent {
      public Send(Packet var1) {
         super(var1);
      }
   }

   public static class Receive extends PacketEvent {
      public Receive(Packet var1) {
         super(var1);
      }
   }
}
