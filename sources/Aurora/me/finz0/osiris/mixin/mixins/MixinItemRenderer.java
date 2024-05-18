//package me.finz0.osiris.mixin.mixins;
//
//import me.finz0.osiris.module.modules.render.LowHands;
//import net.minecraft.client.entity.AbstractClientPlayer;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumHand;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.Redirect;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
///**
// * 3arth
// */
//@Mixin(ItemRenderer.class)
//public abstract class MixinItemRenderer {
//
//    @Shadow
//    public abstract void renderItemInFirstPerson(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_);
//
//    private boolean injectetion = true;
//
//    @Inject(method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V", at = @At("HEAD"), cancellable = true)
//    public void renderItemInFirstPersonHook(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo info) {
//        if (injectetion && hand == EnumHand.MAIN_HAND) {
//            {
//                info.cancel();
//                injectetion = false;
//                this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_, stack, LowHands.INSTANCE.isEnabled() ? p_187457_7_ - (float) LowHands.INSTANCE.offheight.getValDouble()
//                        : p_187457_7_);
//                injectetion = true;
//            }
//
//        }
//    }
//    @Redirect(method = "renderArmFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0))
//    public void translateHook(float x, float y, float z) {
//        GlStateManager.translate(x, LowHands.INSTANCE.isEnabled() ? y +(float) LowHands.INSTANCE.offheight.getValDouble() : y, z);
//    }
//}