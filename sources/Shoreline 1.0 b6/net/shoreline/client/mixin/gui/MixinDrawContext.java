package net.shoreline.client.mixin.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.RenderTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class MixinDrawContext {
    /**
     * @param stack
     * @param x
     * @param y
     * @param ci
     */
    @Inject(method = "drawItemTooltip", at = @At(value = "HEAD"), cancellable = true)
    private void hookRenderTooltip(TextRenderer textRenderer, ItemStack stack, int x, int y, CallbackInfo ci) {
        RenderTooltipEvent renderTooltipEvent =
                new RenderTooltipEvent((DrawContext) (Object) this, stack, x, y);
        Shoreline.EVENT_HANDLER.dispatch(renderTooltipEvent);
        if (renderTooltipEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
