package net.silentclient.client.mixin.mixins;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.network.PacketThreadUtil$1")
public class PacketThreadUtilMixin {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V"))
    private void silent$ignorePacketsFromClosedConnections(Packet packet, INetHandler handler) {
        if (handler instanceof NetHandlerPlayClient) {
            if (((NetHandlerPlayClient) handler).getNetworkManager().isChannelOpen()) {
                packet.processPacket(handler);
            }
        } else {
            packet.processPacket(handler);
        }
    }
}
