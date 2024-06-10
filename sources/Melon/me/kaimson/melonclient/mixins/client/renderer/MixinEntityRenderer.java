package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.mixins.client.multiplayer.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bfk.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    private ave h;
    
    @ModifyVariable(method = { "isDrawBlockOutline" }, at = @At(value = "STORE", ordinal = 2))
    private boolean isDrawBlockOutline(final boolean flag) {
        return flag || (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) && BlockOverlayModule.INSTANCE.persistent.getBoolean());
    }
    
    @Inject(method = { "renderHand" }, at = { @At("TAIL") })
    private void renderHand(final float partialTicks, final int xOffset, final CallbackInfo ci) {
        if (OldAnimationsModule.INSTANCE.build.getBoolean()) {
            if (this.h.h.bR() != 0 && this.h.t.ai.d() && this.h.t.ag.d() && this.h.s != null && this.h.s.a.equals((Object)auh.a.b)) {
                this.swingItem((pr)this.h.h);
            }
            if (this.h.t.ai.d() && this.h.t.ag.d() && this.h.s != null && this.h.s.a.equals((Object)auh.a.b)) {
                ((MixinPlayerControllerMP)this.h.c).setIsHittingBlock(false);
            }
        }
    }
    
    private void swingItem(final pr entity) {
        final zx stack = entity.bA();
        if (stack != null && stack.b() != null && (!entity.ar || entity.as >= this.getArmSwingAnimationEnd(entity) / 2 || entity.as < 0)) {
            entity.as = -1;
            entity.ar = true;
        }
    }
    
    private int getArmSwingAnimationEnd(final pr e) {
        return e.a(pe.e) ? (6 - (1 + e.b(pe.e).c())) : (e.a(pe.f) ? (6 + (1 + e.b(pe.f).c()) * 2) : 6);
    }
}
