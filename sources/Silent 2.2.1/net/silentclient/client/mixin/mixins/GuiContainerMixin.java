package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.gui.hud.Watermark;
import net.silentclient.client.mixin.ducks.EntityRendererExt;
import net.silentclient.client.mods.render.InventoryBlurMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin extends GuiScreen {

    //#if MC==10809
    @Shadow private int dragSplittingButton;
    @Shadow private int dragSplittingRemnant;

    @Inject(method = "updateDragSplitting", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void silent$fixRemnants(CallbackInfo ci) {
        if (this.dragSplittingButton == 2) {
            this.dragSplittingRemnant = mc.thePlayer.inventory.getItemStack().getMaxStackSize();
            ci.cancel();
        }
    }
    //#endif

    @Inject(method = "initGui", at = @At("HEAD"))
    public void onOpenBlur(CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getModByClass(InventoryBlurMod.class).isEnabled()) {
            ((EntityRendererExt) Minecraft.getMinecraft().entityRenderer).silent$loadShader(new StaticResourceLocation("shaders/post/menu_blur.json"));
        }
    }

    @Inject(method = "onGuiClosed", at = @At("HEAD"))
    public void onCloseBlur(CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getModByClass(InventoryBlurMod.class).isEnabled()) {
            Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void checkCloseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        if (mouseButton - 100 == mc.gameSettings.keyBindInventory.getKeyCode()) {
            mc.thePlayer.closeScreen();
            ci.cancel();
        }
    }

    @Shadow
    protected abstract boolean checkHotbarKeys(int keyCode);

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void silent$checkHotbarClicks(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        checkHotbarKeys(mouseButton - 100);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawDefaultBackground()V"))
    public void customBackground(GuiContainer instance) {
        if(!Client.getInstance().getModInstances().getModByClass(InventoryBlurMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(InventoryBlurMod.class, "Dark Background").getValBoolean()) {
            this.drawDefaultBackground();
        } else {
            this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 0).getRGB());
        }
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGuiContainerBackgroundLayer(FII)V"))
    public void watermark(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        new Watermark().render();
    }
}
