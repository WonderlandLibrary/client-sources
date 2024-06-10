package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(targets = { "net/minecraft/client/gui/GuiScreenOptionsSounds$Button" })
public class MixinGuiScreenOptionsSoundsButton extends avs
{
    @Shadow
    public float o;
    
    public MixinGuiScreenOptionsSoundsButton(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
    }
    
    @Inject(method = { "mouseDragged(Lnet/minecraft/client/Minecraft;II)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V") }, cancellable = true)
    protected void mouseDragged(final ave mc, final int mouseX, final int mouseY, final CallbackInfo ci) {
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        GLRectUtils.drawRoundedRect((float)this.h, (float)this.i, (float)(this.h + (int)(this.o * (this.f - 8)) + 8), (float)(this.i + 20), 2.0f, Client.getMainColor(40));
        GLRectUtils.drawRoundedOutline(this.h, this.i, this.h + (int)(this.o * (this.f - 8)) + 8, this.i + 20, 2.0f, 2.0f, Client.getMainColor(255));
        GLRectUtils.drawRoundedOutline(this.h + (int)(this.o * (this.f - 8)), this.i, this.h + (int)(this.o * (this.f - 8)) + 8, this.i + 20, 2.0f, 2.0f, Client.getMainColor(255));
        ci.cancel();
    }
}
