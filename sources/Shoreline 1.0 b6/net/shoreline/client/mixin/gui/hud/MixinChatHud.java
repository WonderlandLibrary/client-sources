package net.shoreline.client.mixin.gui.hud;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.hud.ChatMessageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author linus
 * @since 1.0
 */
@Mixin(ChatHud.class)
public class MixinChatHud {
    /**
     * @param message
     * @param signature
     * @param ticks
     * @param indicator
     * @param refresh
     * @param ci
     */
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/" +
            "network/message/MessageSignatureData;ILnet/minecraft/client/" +
            "gui/hud/MessageIndicator;Z)V", at = @At(value = "HEAD"))
    private void hookAddMessage(Text message, MessageSignatureData signature,
                                int ticks, MessageIndicator indicator,
                                boolean refresh, CallbackInfo ci) {
        ChatMessageEvent chatMessageEvent = new ChatMessageEvent(message);
        Shoreline.EVENT_HANDLER.dispatch(chatMessageEvent);
    }
}
