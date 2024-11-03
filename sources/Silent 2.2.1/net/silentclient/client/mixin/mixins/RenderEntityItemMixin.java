package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.silentclient.client.hooks.RenderEntityItemHook;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.culling.EntityCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin {
    @Shadow protected abstract int func_177078_a(ItemStack stack);

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void checkRenderState(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (EntityCulling.renderItem(entity)) {
            ci.cancel();
        }
    }

    /**
     * @author kirillsaint
     * @reason Animations Mod / Item Physics Mod
     */
    @Overwrite
    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
    {
        return RenderEntityItemHook.func_177077_a(itemIn, p_177077_2_, p_177077_4_, p_177077_6_, p_177077_8_, p_177077_9_, func_177078_a(itemIn.getEntityItem()));
    }

    @Inject(method = "func_177078_a", at = @At("HEAD"), cancellable = true)
    private void advancedBoost(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(FPSBoostMod.advancedEnabled()) {
            cir.setReturnValue(1);
            cir.cancel();
        }
    }
}
