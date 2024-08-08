package com.example.editme.mixin.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.ChunkEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
   value = {NetHandlerPlayClient.class},
   priority = Integer.MAX_VALUE
)
public class MixinNetHandlerPlayClient {
   @Inject(
      method = {"handleChunkData"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/chunk/Chunk;read(Lnet/minecraft/network/PacketBuffer;IZ)V"
)},
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   private void read(SPacketChunkData var1, CallbackInfo var2, Chunk var3) {
      EditmeMod.EVENT_BUS.post(new ChunkEvent(var3, var1));
   }
}
