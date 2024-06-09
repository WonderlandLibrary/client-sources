package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.module.modules.render.ShulkerPreview;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class MixinGuiContainer {
    @Shadow public int guiLeft;
    @Shadow public int guiTop;


    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        ShulkerPreview.mouseX = mouseX;
        ShulkerPreview.mouseY = mouseY;
        ShulkerPreview.guiLeft = this.guiLeft;
        ShulkerPreview.guiTop = this.guiTop;
    }

}
