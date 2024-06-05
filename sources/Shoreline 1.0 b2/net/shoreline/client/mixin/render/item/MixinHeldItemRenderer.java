package net.shoreline.client.mixin.render.item;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.render.item.RenderFirstPersonEvent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer
{
    /**
     *
     * @param player
     * @param tickDelta
     * @param pitch
     * @param hand
     * @param swingProgress
     * @param item
     * @param equipProgress
     * @param matrices
     * @param vertexConsumers
     * @param light
     * @param ci
     */
    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/HeldItemRenderer;" +
                    "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/" +
                    "item/ItemStack;Lnet/minecraft/client/render/model/json/" +
                    "ModelTransformationMode;ZLnet/minecraft/client/util/math/" +
                    "MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void hookRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta,
                                           float pitch, Hand hand, float swingProgress,
                                           ItemStack item, float equipProgress, MatrixStack matrices,
                                           VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci)
    {
        RenderFirstPersonEvent renderFirstPersonEvent = new RenderFirstPersonEvent(
                hand, item, equipProgress, matrices);
        Shoreline.EVENT_HANDLER.dispatch(renderFirstPersonEvent);
    }
}
