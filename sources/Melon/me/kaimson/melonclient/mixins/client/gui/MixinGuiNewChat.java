package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.gui.utils.*;

@Mixin({ avt.class })
public class MixinGuiNewChat
{
    @Inject(method = { "printChatMessage" }, at = { @At("HEAD") })
    private void printChatMessage(final eu chatComponent, final CallbackInfo ci) {
        AutoGLHFModule.INSTANCE.onChat(chatComponent);
        AutoGGModule.INSTANCE.onChat(chatComponent);
        AutoFriendModule.INSTANCE.onChat(chatComponent);
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void drawRect(final int left, final int top, final int right, final int bottom, final int color) {
        final int alpha = ChatModule.INSTANCE.color.getColor() >> 24 & 0xFF;
        GuiUtils.a(left, top, right, bottom, ModuleConfig.INSTANCE.isEnabled(ChatModule.INSTANCE) ? GuiUtils.getRGB(ChatModule.INSTANCE.color.getColor(), color >> 24) : color);
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawString(final avn fontRenderer, final String text, final float x, final float y, final int color) {
        return FontUtils.drawString(text, x, y, color, ChatModule.INSTANCE.shadow.getBoolean());
    }
}
