package net.minecraft.network;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Packet;

class NetworkManager$InboundHandlerTuplePacketListener {
   private final Packet packet;
   private final GenericFutureListener[] futureListeners;

   @SafeVarargs
   public NetworkManager$InboundHandlerTuplePacketListener(Packet var1, GenericFutureListener... var2) {
      this.packet = var1;
      this.futureListeners = var2;
   }

   static Packet access$000(NetworkManager$InboundHandlerTuplePacketListener var0) {
      return var0.packet;
   }

   static GenericFutureListener[] access$100(NetworkManager$InboundHandlerTuplePacketListener var0) {
      return var0.futureListeners;
   }
}
