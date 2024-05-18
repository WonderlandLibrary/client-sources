package rina.turok.bope.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventPacket;

@Mixin({NetworkManager.class})
public class BopeMixinNetworkManager {
   @Inject(
      method = "channelRead0",
      at = {@At("HEAD")},
      cancellable = true
   )
   private void receive(ChannelHandlerContext context, Packet packet, CallbackInfo callback) {
      BopeEventPacket event_packet = new BopeEventPacket.ReceivePacket(packet);
      Bope.ZERO_ALPINE_EVENT_BUS.post(event_packet);
      if (event_packet.isCancelled()) {
         callback.cancel();
      }

   }

   @Inject(
      method = "sendPacket(Lnet/minecraft/network/Packet;)V",
      at = {@At("HEAD")},
      cancellable = true
   )
   private void send(Packet packet, CallbackInfo callback) {
      BopeEventPacket event_packet = new BopeEventPacket.SendPacket(packet);
      Bope.ZERO_ALPINE_EVENT_BUS.post(event_packet);
      if (event_packet.isCancelled()) {
         callback.cancel();
      }

   }
}
