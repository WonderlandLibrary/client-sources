package me.r.touchgrass.injection.mixins.entity;

import me.r.touchgrass.touchgrass;
import me.r.touchgrass.module.Module;
import me.r.touchgrass.module.modules.render.AntiBlind;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by r on 07/02/2021
 */

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    @Shadow
    protected abstract void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos);

    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute attribute);

    @Shadow
    private EntityLivingBase entityLivingToAttack;

    @Shadow
    private int revengeTimer;

    @Inject(method = "isPotionActive(Lnet/minecraft/potion/Potion;)Z", at = @At("HEAD"), cancellable = true)
    private void isPotionActive(Potion p_isPotionActive_1_, CallbackInfoReturnable<Boolean> ciReturnbale) {
        Module antiBlind = touchgrass.getClient().moduleManager.getModule(AntiBlind.class);
        if ((p_isPotionActive_1_ == Potion.confusion || p_isPotionActive_1_ == Potion.blindness) && antiBlind.isEnabled() && touchgrass.getClient().settingsManager.getSettingByName(antiBlind, "Potion").isEnabled()) {
            ciReturnbale.setReturnValue(false);
        }
    }
}
