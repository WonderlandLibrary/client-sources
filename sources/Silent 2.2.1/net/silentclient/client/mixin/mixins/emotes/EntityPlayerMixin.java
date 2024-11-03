package net.silentclient.client.mixin.mixins.emotes;

import net.silentclient.client.Client;
import net.silentclient.client.emotes.AnimatorController;
import net.silentclient.client.emotes.EmoteControllerManager;
import net.silentclient.client.emotes.EmotesMod;
import net.silentclient.client.emotes.PlayerModelManager;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author refactoring
 */
@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Shadow public abstract String getName();

    @Unique
    public AnimatorController controller;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void ae$init(CallbackInfo ci) {
        controller = new AnimatorController(PlayerModelManager.get().steve, PlayerModelManager.get().steveConfig);
        EmoteControllerManager.controllers.put(this.getName(), controller);
    }

    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void ae$onUpdate(CallbackInfo ci) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emotes").getValBoolean()) {
            return;
        }
        controller.update((EntityPlayer) (Object) this);
    }
}