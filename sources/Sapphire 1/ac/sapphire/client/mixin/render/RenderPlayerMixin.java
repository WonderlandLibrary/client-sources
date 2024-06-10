package ac.sapphire.client.mixin.render;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.render.PostRenderPlayerEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class RenderPlayerMixin extends RendererLivingEntity<AbstractClientPlayer> {
    public RenderPlayerMixin(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("RETURN"))
    void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(new PostRenderPlayerEvent(entity, partialTicks, x, y, z));
    }
}
