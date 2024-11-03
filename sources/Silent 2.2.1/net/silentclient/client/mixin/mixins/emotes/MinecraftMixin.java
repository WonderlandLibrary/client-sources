package net.silentclient.client.mixin.mixins.emotes;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.emotes.EmotesMod;
import net.silentclient.client.emotes.PlayerModelManager;
import net.silentclient.client.emotes.ui.EmoteMenuGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author refactoring
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("RETURN"))
    public void mchorse$runTick(CallbackInfo ci) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emotes").getValBoolean()) {
            return;
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emote Wheel Keybind").isKeyDown()) {
            Minecraft.getMinecraft().displayGuiScreen(new EmoteMenuGui());
        }
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    public void mchorse$startGame(CallbackInfo ci) {
        PlayerModelManager.get();
    }
}
