package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenBook.class)
public class GuiScreenBookMixin extends GuiScreen {
    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreenBook;handleComponentHover(Lnet/minecraft/util/IChatComponent;II)V"))
    private void silent$callSuper(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Inject(method = "drawScreen", at = @At(value = "HEAD"))
    private void silent$fix1(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
    }

    @Inject(method = "drawScreen", at = @At(value = "TAIL"))
    private void silent$fix2(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreenBook;handleComponentHover(Lnet/minecraft/util/IChatComponent;II)V", shift = At.Shift.AFTER), cancellable = true)
    private void silent$cancelFurtherRendering(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        ci.cancel();
    }
}
