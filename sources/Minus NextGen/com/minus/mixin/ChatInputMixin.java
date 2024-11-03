package com.minus.mixin;

import com.minus.Minus;
import com.minus.event.events.client.EventOnChatSend;
import com.minus.utils.MinecraftInterface;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatInputMixin implements MinecraftInterface {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageHook(String content, CallbackInfo ci) {
        EventOnChatSend chatInputEvent = new EventOnChatSend(content);
        Minus.instance.getEventBus().post(chatInputEvent);

        if (chatInputEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
