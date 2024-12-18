package net.shoreline.client.mixin.network;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.chat.ChatMessageEvent;
import net.shoreline.client.impl.event.network.GameJoinEvent;
import net.shoreline.client.impl.event.network.InventoryEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler
{
    /**
     *
     *
     * @param content
     * @param ci
     */
    @Inject(method = "sendChatMessage", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookSendChatMessage(String content, CallbackInfo ci)
    {
        ChatMessageEvent.Server chatInputEvent =
                new ChatMessageEvent.Server(content);
        Shoreline.EVENT_HANDLER.dispatch(chatInputEvent);
        // prevent chat packet from sending
        if (chatInputEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     *
     * @param packet
     * @param ci
     */
    @Inject(method = "onGameJoin", at = @At(value = "TAIL"))
    private void hookOnGameJoin(GameJoinS2CPacket packet, CallbackInfo ci)
    {
        GameJoinEvent gameJoinEvent = new GameJoinEvent();
        Shoreline.EVENT_HANDLER.dispatch(gameJoinEvent);
    }

    /**
     *
     * @param packet
     * @param ci
     */
    @Inject(method = "onInventory", at = @At(value = "TAIL"))
    private void hookOnInventory(InventoryS2CPacket packet, CallbackInfo ci)
    {
        InventoryEvent inventoryEvent = new InventoryEvent(packet);
        Shoreline.EVENT_HANDLER.dispatch(inventoryEvent);
    }
}
