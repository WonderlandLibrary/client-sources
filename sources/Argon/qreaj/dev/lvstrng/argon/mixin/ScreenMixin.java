// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.clickgui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Screen.class})
public class ScreenMixin {
    @Shadow
    @Nullable
    protected MinecraftClient mc;

    @Inject(method = {"renderBackground"}, at = {@At("HEAD")}, cancellable = true)
    private void dontRenderBackground(final DrawContext context, final int mouseX, final int mouseY, final float delta, final CallbackInfo ci) {
        if (this.mc.currentScreen instanceof ClickGUI) {
            ci.cancel();
        }
    }
}
