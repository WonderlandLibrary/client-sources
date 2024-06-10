package ac.sapphire.client.mixin.entity;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.render.RenderWorldPassEvent;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE))
    void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(new RenderWorldPassEvent(partialTicks));
    }
}
