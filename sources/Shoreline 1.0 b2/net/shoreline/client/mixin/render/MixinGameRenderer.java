package net.shoreline.client.mixin.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.hit.HitResult;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.render.RenderLayersClient;
import net.shoreline.client.impl.event.network.ReachEvent;
import net.shoreline.client.impl.event.render.*;
import net.shoreline.client.init.Programs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author linus
 * @see GameRenderer
 * @since 1.0
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
    //
    @Shadow
    @Final
    MinecraftClient client;

    /**
     * @param tickDelta
     * @param limitTime
     * @param matrices
     * @param ci
     */
    @Inject(method = "renderWorld", at = @At(value = "FIELD", target = "Lnet" +
            "/minecraft/client/render/GameRenderer;renderHand:Z",
            opcode = Opcodes.GETFIELD, ordinal = 0))
    private void hookRenderWorld(float tickDelta, long limitTime,
                                 MatrixStack matrices, CallbackInfo ci)
    {
        // final RenderWorldEvent renderWorldEvent =
        //         new RenderWorldEvent(matrices, tickDelta);
        // MSAAFramebuffer.use(() ->
        //         Caspian.EVENT_HANDLER.dispatch(renderWorldEvent));
    }

    /**
     * @param matrices
     * @param tickDelta
     * @param ci
     */
    @Inject(method = "tiltViewWhenHurt", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookTiltViewWhenHurt(MatrixStack matrices, float tickDelta,
                                      CallbackInfo ci)
    {
        HurtCamEvent hurtCamEvent = new HurtCamEvent();
        Shoreline.EVENT_HANDLER.dispatch(hurtCamEvent);
        if (hurtCamEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     * @param floatingItem
     * @param ci
     */
    @Inject(method = "showFloatingItem", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookShowFloatingItem(ItemStack floatingItem, CallbackInfo ci)
    {
        RenderFloatingItemEvent renderFloatingItemEvent =
                new RenderFloatingItemEvent(floatingItem);
        Shoreline.EVENT_HANDLER.dispatch(renderFloatingItemEvent);
        if (renderFloatingItemEvent.isCanceled()) {
            ci.cancel();
        }
    }

    /**
     * @param distortionStrength
     * @param ci
     */
    @Inject(method = "renderNausea", at = @At(value = "HEAD"), cancellable = true)
    private void hookRenderNausea(float distortionStrength, CallbackInfo ci)
    {
        RenderNauseaEvent renderNauseaEvent = new RenderNauseaEvent();
        Shoreline.EVENT_HANDLER.dispatch(renderNauseaEvent);
        if (renderNauseaEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     * @param cir
     */
    @Inject(method = "shouldRenderBlockOutline", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookShouldRenderBlockOutline(CallbackInfoReturnable<Boolean> cir)
    {
        RenderBlockOutlineEvent renderBlockOutlineEvent =
                new RenderBlockOutlineEvent();
        Shoreline.EVENT_HANDLER.dispatch(renderBlockOutlineEvent);
        if (renderBlockOutlineEvent.isCanceled())
        {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    /**
     * @param tickDelta
     * @param info
     */
    @Inject(method = "updateTargetedEntity", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast" +
                    "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/" +
                    "Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/" +
                    "math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/" +
                    "util/hit/EntityHitResult;"), cancellable = true)
    private void hookUpdateTargetedEntity(float tickDelta, CallbackInfo info)
    {
        TargetEntityEvent targetEntityEvent = new TargetEntityEvent();
        Shoreline.EVENT_HANDLER.dispatch(targetEntityEvent);
        if (targetEntityEvent.isCanceled() && client.crosshairTarget.getType() == HitResult.Type.BLOCK)
        {
            client.getProfiler().pop();
            info.cancel();
        }
    }

    /**
     * @param d
     * @return
     */
    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 3))
    private double updateTargetedEntityModifySurvivalReach(double d)
    {
        ReachEvent reachEvent = new ReachEvent();
        Shoreline.EVENT_HANDLER.dispatch(reachEvent);
        return reachEvent.isCanceled() ? reachEvent.getReach() + 3.0 : 3.0;
    }

    /**
     * @param d
     * @return
     */
    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 9))
    private double updateTargetedEntityModifySquaredMaxReach(double d)
    {
        ReachEvent reachEvent = new ReachEvent();
        Shoreline.EVENT_HANDLER.dispatch(reachEvent);
        double reach = reachEvent.getReach() + 3.0;
        return reachEvent.isCanceled() ? reach * reach : 9.0;
    }


    /**
     * @param matrices
     * @param tickDelta
     * @param ci
     */
    @Inject(method = "bobView", at = @At(value = "HEAD"), cancellable = true)
    private void hookBobView(MatrixStack matrices, float tickDelta,
                             CallbackInfo ci)
    {
        BobViewEvent bobViewEvent = new BobViewEvent();
        Shoreline.EVENT_HANDLER.dispatch(bobViewEvent);
        if (bobViewEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     *
     * @param factory
     * @param ci
     */
    @Inject(method = "loadPrograms", at = @At(value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
            ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void initPrograms(ResourceFactory factory, CallbackInfo ci)
    {
        Programs.initPrograms();
        // Initialize client layers
        RenderLayersClient.initLayers();
    }
}
