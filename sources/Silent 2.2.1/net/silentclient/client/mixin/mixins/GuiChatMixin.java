package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.mods.render.ChatMod;
import net.silentclient.client.utils.calculator.ChatCalculator;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiChat.class)
public class GuiChatMixin {
    @Shadow protected GuiTextField inputField;
    @Unique
    private SimpleAnimation animation = new SimpleAnimation(0.0F);

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void drawScreenPre(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        if(Client.getInstance().getModInstances().getModByClass(ChatMod.class).isToggled() && Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Bar Animation").getValBoolean()) {
            animation.setAnimation(30, 20);
            GlUtils.startTranslate(0, 29 - (int) animation.getValue());
        }
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreenPost(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getModByClass(ChatMod.class).isToggled() && Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Bar Animation").getValBoolean()) {
            GlUtils.stopTranslate();
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
    public void chatCalculator(char typedChar, int keyCode, CallbackInfo ci) {
        if(Keyboard.getEventKey() == 15 && Keyboard.getEventKeyState() && ChatCalculator.runExpression(inputField)) {
            ci.cancel();
        }
    }
}
