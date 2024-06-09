package com.leafclient.leaf.mixin.render.item;

import com.leafclient.leaf.event.game.render.ItemRenderEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Inject(
            method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject$preRenderItem(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_,
                                      EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo info) {
        if(EventManager.INSTANCE.publish(new ItemRenderEvent.Render(stack, hand)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(
            method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"
            )
    )
    private void inject$transformItem(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_,
                                      EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo info) {
        EventManager.INSTANCE
                .publish(new ItemRenderEvent.Transform(stack, hand, p_187457_5_));
    }

}
