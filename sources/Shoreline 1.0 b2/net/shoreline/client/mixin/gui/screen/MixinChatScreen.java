package net.shoreline.client.mixin.gui.screen;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.chat.ChatInputEvent;
import net.shoreline.client.impl.event.gui.chat.ChatKeyInputEvent;
import net.shoreline.client.impl.event.gui.chat.ChatMessageEvent;
import net.shoreline.client.impl.event.gui.chat.ChatRenderEvent;
import net.shoreline.client.mixin.accessor.AccessorTextFieldWidget;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ChatScreen
 */
@Mixin(ChatScreen.class)
public class MixinChatScreen
{
    @Shadow
    protected TextFieldWidget chatField;

    /**
     *
     *
     * @param chatText
     */
    @Inject(method = "onChatFieldUpdate", at = @At(value = "TAIL"))
    private void hookOnChatFieldUpdate(String chatText, CallbackInfo ci)
    {
        ChatInputEvent chatInputEvent = new ChatInputEvent(chatText);
        Shoreline.EVENT_HANDLER.dispatch(chatInputEvent);
    }

    /**
     *
     * @param keyCode
     * @param scanCode
     * @param modifiers
     * @param cir
     */
    @Inject(method = "keyPressed", at = @At(value = "HEAD"), cancellable = true)
    private void hookKeyPressed(int keyCode, int scanCode, int modifiers,
                                CallbackInfoReturnable<Boolean> cir)
    {
        ChatKeyInputEvent keyInputEvent = new ChatKeyInputEvent(keyCode,
                chatField.getText());
        Shoreline.EVENT_HANDLER.dispatch(keyInputEvent);
        if (keyInputEvent.isCanceled())
        {
            cir.cancel();
            chatField.setText(keyInputEvent.getChatText());
        }
    }

    /**
     *
     *
     * @param chatText
     * @param addToHistory
     * @param cir
     */
    @Inject(method = "sendMessage", at = @At(value = "HEAD"), cancellable = true)
    private void hookSendMessage(String chatText, boolean addToHistory,
                                 CallbackInfoReturnable<Boolean> cir)
    {
        ChatMessageEvent.Client chatMessageEvent =
                new ChatMessageEvent.Client(chatText);
        Shoreline.EVENT_HANDLER.dispatch(chatMessageEvent);
        if (chatMessageEvent.isCanceled())
        {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    /**
     *
     *
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     * @param ci
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet " +
            "/minecraft/client/gui/widget/TextFieldWidget;render" +
            "(Lnet/minecraft/client/util/math/MatrixStack;IIF)V",
            shift = At.Shift.BEFORE))
    private void hookRender(MatrixStack matrices, int mouseX, int mouseY,
                            float delta, CallbackInfo ci)
    {
        float x = ((AccessorTextFieldWidget) chatField).isDrawsBackground() ?
                chatField.getX() + 6 : chatField.getX() + 2;
        float y = ((AccessorTextFieldWidget) chatField).isDrawsBackground() ?
                chatField.getY() + (chatField.getHeight() - 8) / 2.0f :
                chatField.getY();
        ChatRenderEvent chatTextRenderEvent = new ChatRenderEvent(matrices,
                x ,y);
        Shoreline.EVENT_HANDLER.dispatch(chatTextRenderEvent);
    }
}
