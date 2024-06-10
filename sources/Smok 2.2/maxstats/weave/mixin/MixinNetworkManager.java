package maxstats.weave.mixin;

import maxstats.weave.event.EventSendPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin({NetworkManager.class})
public class MixinNetworkManager {

    @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void onProcessPacketHead(Packet<?> packet, CallbackInfo ci) {
        EventSendPacket event = new EventSendPacket(packet);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}