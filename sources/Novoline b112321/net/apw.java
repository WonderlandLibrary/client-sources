package net;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;

public class apw {
   public static C0BPacketEntityAction$Action b(C0BPacketEntityAction var0) {
      return var0.getAction();
   }

   public static int a(C0BPacketEntityAction var0) {
      return var0.getAuxData();
   }
}
