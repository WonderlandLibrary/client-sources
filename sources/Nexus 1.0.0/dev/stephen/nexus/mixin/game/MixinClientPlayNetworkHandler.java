package dev.stephen.nexus.mixin.game;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.utils.mc.ChatUtils;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageInject(String content, CallbackInfo ci) {
        if (content.startsWith(Client.INSTANCE.getCommandManager().getPrefix())) {
            ci.cancel();
            try {
                if (content.length() > 1) {
                    Client.INSTANCE.getCommandManager().executeCommand(content.substring(Client.INSTANCE.getCommandManager().getPrefix().length() - 1));
                } else {
                    ChatUtils.addMessageToChat(Formatting.GRAY + "Please enter a command");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}