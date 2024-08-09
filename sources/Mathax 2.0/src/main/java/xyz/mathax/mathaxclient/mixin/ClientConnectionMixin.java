package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.ConnectToServerEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.world.HighwayBuilder;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.InetSocketAddress;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if (MatHax.EVENT_BUS.post(PacketEvent.Receive.get(packet)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void disconnect(Text disconnectReason, CallbackInfo info) {
        if (Modules.get().get(HighwayBuilder.class).isEnabled()) {
            MutableText text = Text.literal(String.format("\n\n%s[%sHighway Builder%s] Statistics:", Formatting.GRAY, Formatting.BLUE, Formatting.GRAY)).append("\n");
            text.append(Modules.get().get(HighwayBuilder.class).getStatsText());

            ((MutableText) disconnectReason).append(text);
        }
    }

    @Inject(method = "connect", at = @At("HEAD"))
    private static void onConnect(InetSocketAddress address, boolean useEpoll, CallbackInfoReturnable<ClientConnection> info) {
        MatHax.EVENT_BUS.post(ConnectToServerEvent.get());
    }

    /*@Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exceptionCaught(ChannelHandlerContext context, Throwable throwable, CallbackInfo info) {
        if (throwable instanceof IOException && Modules.get().isEnabled(AntiPacketKick.class)) {
            info.cancel();
        }
    }*/

    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;)V", cancellable = true)
    private void onSendPacketHead(Packet<?> packet, CallbackInfo info) {
        if (MatHax.EVENT_BUS.post(PacketEvent.Send.get(packet)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("TAIL"))
    private void onSendPacketTail(Packet<?> packet, CallbackInfo info) {
        MatHax.EVENT_BUS.post(PacketEvent.Sent.get(packet));
    }
}
