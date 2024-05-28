package arsenic.injection.mixin;

import arsenic.utils.timer.TickMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRenderer.class, priority = 1111)
public abstract class MixinItemRenderer {
    @Shadow
    protected abstract void transformFirstPersonItem(float equipProgress, float swingProgress);

    @Shadow
    @Final
    private Minecraft mc;
    private float partialTicks;

    @Inject(method = "renderItemInFirstPerson", at = @At("HEAD"))
    public void renderItemInFirstPerson(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"))
    public void mixinBlockHit(ItemRenderer instance, float equipProgress, float swingProgress) {
        if (mc.thePlayer.isBlocking()) {
            swingProgress = TickMode.SINE.toSmoothPercent(mc.thePlayer.getSwingProgress(partialTicks));
        }
        transformFirstPersonItem(equipProgress, swingProgress);
    }
}
