package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.RenderMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiAchievement.class)
public class GuiAchievementMixin {
    @Inject(method = "updateAchievementWindow", at = @At("HEAD"), cancellable = true)
    public void removeAchievements(CallbackInfo ci) {
        if(Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Disable Achievements").getValBoolean()) {
            ci.cancel();
        }
    }

    @Inject(method = "updateAchievementWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievement/GuiAchievement;updateAchievementWindowScale()V"))
    public void fixAchievement1(CallbackInfo ci) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
    }

    @Inject(method = "updateAchievementWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableDepth()V"))
    public void fixAchievement2(CallbackInfo ci) {
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }
}
