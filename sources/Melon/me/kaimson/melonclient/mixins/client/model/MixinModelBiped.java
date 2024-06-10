package me.kaimson.melonclient.mixins.client.model;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bbj.class })
public class MixinModelBiped extends bbo
{
    @Shadow
    public bct h;
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelRenderer;rotateAngleY:F", ordinal = 6, shift = At.Shift.AFTER) })
    private void setRotationAngleY(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final pk entityIn, final CallbackInfo ci) {
        this.h.g = (OldAnimationsModule.INSTANCE.block.getBoolean() ? 0.0f : -0.5235988f);
    }
}
