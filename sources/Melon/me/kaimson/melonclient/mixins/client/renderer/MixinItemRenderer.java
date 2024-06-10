package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bfn.class })
public class MixinItemRenderer
{
    @Shadow
    @Final
    private ave c;
    private float swingProgress;
    
    @Inject(method = { "renderItemInFirstPerson" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemUseAction()Lnet/minecraft/item/EnumAction;", shift = At.Shift.AFTER) }, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void modifySwingProgress(final float partialTicks, final CallbackInfo ci, final float f, final bet player, final float f1, final float f2, final float f3) {
        this.swingProgress = f1;
    }
    
    @Inject(method = { "renderItemInFirstPerson" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doBlockTransformations()V", shift = At.Shift.AFTER) })
    private void modifySwing(final float partialTicks, final CallbackInfo ci) {
        if (OldAnimationsModule.INSTANCE.blockHit.getBoolean()) {
            bfl.a(0.83f, 0.88f, 0.85f);
            bfl.b(-0.3f, 0.1f, 0.0f);
        }
    }
    
    @Inject(method = { "renderItemInFirstPerson" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void modifyEat(final float partialTicks, final CallbackInfo ci) {
        if (OldAnimationsModule.INSTANCE.eating.getBoolean()) {
            bfl.a(0.8f, 1.0f, 1.0f);
            bfl.b(-0.2f, -0.1f, 0.0f);
        }
    }
    
    @ModifyArg(method = { "renderItemInFirstPerson" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1), index = 1)
    private float drinkSwingProgress(final float swingProgress) {
        return OldAnimationsModule.INSTANCE.eating.getBoolean() ? this.swingProgress : 0.0f;
    }
    
    @ModifyArg(method = { "renderItemInFirstPerson" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 1)
    private float blockSwingProgress(final float swingProgress) {
        return OldAnimationsModule.INSTANCE.blockHit.getBoolean() ? this.swingProgress : 0.0f;
    }
    
    @ModifyArg(method = { "renderItemInFirstPerson" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 3), index = 1)
    private float bowSwingProgress(final float swingProgress) {
        return OldAnimationsModule.INSTANCE.bow.getBoolean() ? this.swingProgress : 0.0f;
    }
    
    @Inject(method = { "transformFirstPersonItem" }, at = { @At("HEAD") })
    private void transformFirstPersonItem(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        if (OldAnimationsModule.INSTANCE.bow.getBoolean() && this.c != null && this.c.h != null && this.c.h.bQ() != null && this.c.h.bQ().b() != null && zw.b(this.c.h.bQ().b()) == 261) {
            bfl.b(-0.01f, 0.05f, -0.06f);
        }
        if (OldAnimationsModule.INSTANCE.rod.getBoolean() && this.c != null && this.c.h != null && this.c.h.bZ() != null && this.c.h.bZ().b() != null && (zw.b(this.c.h.bZ().b()) == 346 || zw.b(this.c.h.bZ().b()) == 398)) {
            bfl.b(0.08f, -0.027f, -0.33f);
            bfl.a(0.93f, 1.0f, 1.0f);
        }
        if (OldAnimationsModule.INSTANCE.swing.getBoolean() && this.c != null && this.c.h != null && this.c.h.ar && this.c.h.bZ() != null && !this.c.h.ay() && !this.c.h.bW()) {
            bfl.a(0.85f, 0.85f, 0.85f);
            bfl.b(-0.078f, 0.003f, 0.05f);
        }
    }
}
