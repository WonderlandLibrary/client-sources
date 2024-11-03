package net.silentclient.client.mixin.mixins;

import net.minecraft.client.model.ModelBiped;
import net.silentclient.client.mods.render.AnimationsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ModelBiped.class)
public class ModelBipedMixin {
    @ModifyConstant(method = "setRotationAngles", constant = @Constant(floatValue = -0.5235988F))
    private float cancelRotation(float original) {
        return AnimationsMod.getSettingBoolean("1.7 3rd Person Block Animation") ? 0 : original;
    }
}
