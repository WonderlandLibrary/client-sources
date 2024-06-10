package me.kaimson.melonclient.mixins.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ bky.class })
public class MixinLayerHeldItem
{
    @Shadow
    @Final
    private bjl<?> a;
    
    @Inject(method = { "doRenderLayer" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBiped;postRenderArm(F)V", ordinal = 0) }, cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void doRenderLayer(final pr entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale, final CallbackInfo ci, zx itemstack) {
        if (entitylivingbaseIn instanceof wn) {
            if (OldAnimationsModule.INSTANCE.block.getBoolean()) {
                if (((wn)entitylivingbaseIn).bW()) {
                    if (entitylivingbaseIn.av()) {
                        ((bbj)this.a.b()).a(0.0325f);
                        bfl.a(1.05f, 1.05f, 1.05f);
                        bfl.b(-0.58f, 0.32f, -0.07f);
                        bfl.b(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                    }
                    else {
                        ((bbj)this.a.b()).a(0.0325f);
                        bfl.a(1.05f, 1.05f, 1.05f);
                        bfl.b(-0.45f, 0.25f, -0.07f);
                        bfl.b(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                    }
                }
                else {
                    ((bbj)this.a.b()).a(0.0625f);
                }
            }
            else {
                ((bbj)this.a.b()).a(0.0625f);
            }
            bfl.b(-0.0625f, 0.4375f, 0.0625f);
            if (((wn)entitylivingbaseIn).bG != null) {
                itemstack = new zx((zw)zy.aR, 0);
            }
        }
        else {
            ((bbj)this.a.b()).a(0.0625f);
            bfl.b(-0.0625f, 0.4375f, 0.0625f);
        }
        final zw item = itemstack.b();
        final ave minecraft = ave.A();
        if (item instanceof yo && afh.a(item).b() == 2) {
            bfl.b(0.0f, 0.1875f, -0.3125f);
            bfl.b(20.0f, 1.0f, 0.0f, 0.0f);
            bfl.b(45.0f, 0.0f, 1.0f, 0.0f);
            final float f1 = 0.375f;
            bfl.a(-f1, -f1, f1);
        }
        if (entitylivingbaseIn.av()) {
            bfl.b(0.0f, 0.203125f, 0.0f);
        }
        minecraft.ah().a(entitylivingbaseIn, itemstack, bgr.b.b);
        bfl.F();
        ci.cancel();
    }
}
