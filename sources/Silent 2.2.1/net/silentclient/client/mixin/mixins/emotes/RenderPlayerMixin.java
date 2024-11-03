package net.silentclient.client.mixin.mixins.emotes;

import net.silentclient.client.Client;
import net.silentclient.client.emotes.EmoteControllerManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.silentclient.client.emotes.EmotesMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author refactoring
 */
@Mixin(RenderPlayer.class)
public abstract class RenderPlayerMixin extends RendererLivingEntity<AbstractClientPlayer> {
    public RenderPlayerMixin(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Shadow
    protected abstract void setModelVisibilities(AbstractClientPlayer p_177137_0_);

    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void ae$doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(EmotesMod.class, "Emotes").getValBoolean()) {
            return;
        }
        if (EmoteControllerManager.controllers.get(entity.getName()) != null) {
            if (EmoteControllerManager.controllers.get(entity.getName()).renderHook((RenderPlayer) (Object) (this), entity, x, y, z, partialTicks, this.canRenderName(entity))) {
                ci.cancel();
            }
        }
    }
}