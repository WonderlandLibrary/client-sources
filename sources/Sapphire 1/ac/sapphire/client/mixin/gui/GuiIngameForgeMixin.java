package ac.sapphire.client.mixin.gui;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.render.RenderOverlayEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class GuiIngameForgeMixin {
    @Inject(method = "renderTooltip", at = @At(value = "RETURN"))
    void renderGameOverlay(ScaledResolution l, float j, CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(new RenderOverlayEvent(j));
    }
}
