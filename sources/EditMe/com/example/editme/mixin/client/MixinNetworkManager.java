package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.PacketEvent;
import com.example.editme.modules.misc.NoPacketKick;
import io.netty.channel.ChannelHandlerContext;
import java.io.IOException;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {NetworkManager.class},
   priority = Integer.MAX_VALUE
)
public class MixinNetworkManager {
   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendPacket(Packet var1, CallbackInfo var2) {
      PacketEvent.Send var3 = new PacketEvent.Send(var1);
      EditmeMod.EVENT_BUS.post(var3);
      if (var3.isCancelled()) {
         var2.cancel();
      }

   }

   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onChannelRead(ChannelHandlerContext var1, Packet var2, CallbackInfo var3) {
      PacketEvent.Receive var4 = new PacketEvent.Receive(var2);
      EditmeMod.EVENT_BUS.post(var4);
      if (var4.isCancelled()) {
         var3.cancel();
      }

   }

   @Inject(
      method = {"exceptionCaught"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void exceptionCaught(ChannelHandlerContext var1, Throwable var2, CallbackInfo var3) {
      if (var2 instanceof IOException && NoPacketKick.isEnabled()) {
         var3.cancel();
      }

   }
}
