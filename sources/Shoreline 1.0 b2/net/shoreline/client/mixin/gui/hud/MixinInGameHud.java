package net.shoreline.client.mixin.gui.hud;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.hud.RenderOverlayEvent;
import net.shoreline.client.util.Globals;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see InGameHud
 */
@Mixin(InGameHud.class)
public class MixinInGameHud implements Globals
{
    @Shadow
    @Final
    private static Identifier PUMPKIN_BLUR;
    //
    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE;

    /**
     *
     *
     * @param matrices
     * @param tickDelta
     * @param ci
     */
    @Inject(method = "render", at = @At(value = "TAIL"))
    private void hookRender(MatrixStack matrices, float tickDelta,
                            CallbackInfo ci)
    {
        RenderOverlayEvent.Post renderOverlayEvent =
            new RenderOverlayEvent.Post(matrices, tickDelta);
        Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
    }

    /**
     *
     *
     * @param matrices
     * @param ci
     */
    @Inject(method = "renderStatusEffectOverlay", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookRenderStatusEffectOverlay(MatrixStack matrices,
                            CallbackInfo ci)
    {
        RenderOverlayEvent.StatusEffect renderOverlayEvent =
                new RenderOverlayEvent.StatusEffect(matrices);
        Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
        if (renderOverlayEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     *
     * @param matrices
     * @param scale
     * @param ci
     */
    @Inject(method = "renderSpyglassOverlay", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookRenderSpyglassOverlay(MatrixStack matrices, float scale,
                                           CallbackInfo ci)
    {
        RenderOverlayEvent.Spyglass renderOverlayEvent =
                new RenderOverlayEvent.Spyglass(matrices);
        Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
        if (renderOverlayEvent.isCanceled())
        {
            ci.cancel();
        }
    }

    /**
     *
     * @param matrices
     * @param texture
     * @param opacity
     * @param ci
     */
    @Inject(method = "renderOverlay", at = @At(value = "HEAD"), cancellable = true)
    private void hookRenderOverlay(MatrixStack matrices, Identifier texture,
                                   float opacity, CallbackInfo ci)
    {
        if (texture.getPath().equals(PUMPKIN_BLUR.getPath()))
        {
            RenderOverlayEvent.Pumpkin renderOverlayEvent =
                    new RenderOverlayEvent.Pumpkin(matrices);
            Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
            if (renderOverlayEvent.isCanceled())
            {
                ci.cancel();
            }
        }
        else if (texture.getPath().equals(POWDER_SNOW_OUTLINE.getPath()))
        {
            RenderOverlayEvent.Frostbite renderOverlayEvent =
                    new RenderOverlayEvent.Frostbite(matrices);
            Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
            if (renderOverlayEvent.isCanceled())
            {
                ci.cancel();
            }
        }
    }

    /**
     *
     *
     * @param instance
     * @param matrices
     * @param text
     * @param x
     * @param y
     * @param color
     * @return
     */
    @Redirect(method = "renderHeldItemTooltip", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow" +
                    "(Lnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/text/Text;FFI)I"))
    private int hookRenderHeldItemTooltip(TextRenderer instance,
                                          MatrixStack matrices, Text text,
                                          float x, float y, int color)
    {
        RenderOverlayEvent.ItemName renderOverlayEvent =
                new RenderOverlayEvent.ItemName(matrices);
        Shoreline.EVENT_HANDLER.dispatch(renderOverlayEvent);
        if (renderOverlayEvent.isCanceled())
        {
            if (renderOverlayEvent.isUpdateXY())
            {
                return instance.drawWithShadow(matrices, text,
                        renderOverlayEvent.getX(), renderOverlayEvent.getY(), color);
            }
            return 0;
        }
        return instance.drawWithShadow(matrices, text, x, y, color);
    }
}
