package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.silentclient.client.event.impl.RunCommandEvent;
import net.silentclient.client.utils.ResolutionHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class GuiScreenMixin {
    @Shadow protected Minecraft mc;

    //#if MC==10809
    @Redirect(method = "handleKeyboardInput", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private boolean silent$checkCharacter() {
        return Keyboard.getEventKey() == 0 && Keyboard.getEventCharacter() >= ' ' || Keyboard.getEventKeyState();
    }
    //#endif

    @SuppressWarnings({"ConstantConditions", "RedundantCast"})
    @Inject(method = "handleInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleKeyboardInput()V"), cancellable = true)
    private void silent$checkScreen(CallbackInfo ci) {
        if ((GuiScreen) (Object) this != this.mc.currentScreen) {
            ResolutionHelper.setScaleOverride(-1);
            ci.cancel();
        }
    }

    @Redirect(method = "handleComponentClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;sendChatMessage(Ljava/lang/String;Z)V"))
    public void event(GuiScreen instance, String msg, boolean addToChat) {
        RunCommandEvent event = new RunCommandEvent(msg);
        event.call();

        if(!event.isCancelable()) {
            instance.sendChatMessage(msg, addToChat);
        }
    }
}
