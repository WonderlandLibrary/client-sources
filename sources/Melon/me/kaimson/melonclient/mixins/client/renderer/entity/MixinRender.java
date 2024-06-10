package me.kaimson.melonclient.mixins.client.renderer.entity;

import org.lwjgl.opengl.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.modules.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ biv.class })
public abstract class MixinRender<T extends pk>
{
    @Shadow
    @Final
    protected biu b;
    
    @Shadow
    public abstract avn c();
    
    @Overwrite
    protected void a(final T entityIn, final String str, final double x, final double y, final double z, final int maxDistance) {
        final double d0 = entityIn.h(this.b.c);
        if (d0 <= maxDistance * maxDistance) {
            final avn fontrenderer = this.c();
            final float f = 1.6f;
            final float f2 = 0.016666668f * f;
            bfl.E();
            bfl.b((float)x + 0.0f, (float)y + entityIn.K + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            bfl.b(-this.b.e, 0.0f, 1.0f, 0.0f);
            bfl.b(this.b.f, 1.0f, 0.0f, 0.0f);
            bfl.a(-f2, -f2, f2);
            bfl.f();
            bfl.a(false);
            bfl.i();
            bfl.l();
            bfl.a(770, 771, 1, 0);
            final bfx tessellator = bfx.a();
            final bfd worldrenderer = tessellator.c();
            int i = 0;
            if (str.equals("deadmau5")) {
                i = -10;
            }
            if (!SettingsManager.INSTANCE.transparentNametags.getBoolean()) {
                final int j = fontrenderer.a(str) / 2;
                bfl.x();
                worldrenderer.a(7, bms.f);
                worldrenderer.b((double)(-j - 1), (double)(-1 + i), 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(-j - 1), (double)(8 + i), 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(j + 1), (double)(8 + i), 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(j + 1), (double)(-1 + i), 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                tessellator.b();
                bfl.w();
            }
            fontrenderer.a(str, -fontrenderer.a(str) / 2, i, 553648127);
            bfl.j();
            bfl.a(true);
            fontrenderer.a(str, -fontrenderer.a(str) / 2, i, -1);
            bfl.e();
            bfl.k();
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            bfl.F();
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("TAIL") })
    private void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (SettingsManager.INSTANCE.showName.getBoolean() && entity instanceof bew) {
            NametagRenderer.render(0.0, 0.0, 0.0);
        }
    }
    
    @ModifyArg(method = { "renderLivingLabel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float getViewX(final float viewXIn) {
        final float viewX = PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.getCameraPitch() : viewXIn;
        return (SettingsManager.INSTANCE.fixNametagRot.getBoolean() && ave.A().t.aB == 2) ? (-viewX) : viewX;
    }
    
    @ModifyArg(method = { "renderLivingLabel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float getViewY(final float viewY) {
        return PerspectiveModule.INSTANCE.isHeld() ? (-PerspectiveModule.getCameraYaw()) : viewY;
    }
}
