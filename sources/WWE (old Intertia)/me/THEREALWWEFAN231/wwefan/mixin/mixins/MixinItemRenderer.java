package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { ItemRenderer.class }, priority = 9999)
public class MixinItemRenderer
{
    @Shadow
    private float field_187471_h;
    
    public MixinItemRenderer() {
        super();
    }
    
    @Redirect(method = { "updateEquippedItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F", ordinal = 3))
    public float updateEquippedItem(final float n, final float n2, final float n3) {
        return MathHelper.clamp(vlwv.hNxW.qHPF((Class<LowShield>)PLVS.class).IuiN() ? (vlwv.hNxW.qHPF((Class<LowShield>)PLVS.class).height.OnGi().floatValue() - this.equippedProgressOffHand) : n, n2, n3);
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFireInFirstPerson(final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).fire.OnGi()) {
            callbackInfo.cancel();
        }
    }
}
