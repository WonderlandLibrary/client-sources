package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.combat.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { Entity.class }, priority = 9999)
public class MixinEntity
{
    @Shadow
    public double field_70165_t;
    @Shadow
    public double field_70163_u;
    @Shadow
    public double field_70161_v;
    @Shadow
    public float field_70177_z;
    @Shadow
    public float field_70125_A;
    @Shadow
    public boolean field_70122_E;
    
    public MixinEntity() {
        super();
    }
    
    @Shadow
    public void func_70091_d(final MoverType moverType, final double n, final double n2, final double n3) {
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("RETURN") }, cancellable = true)
    public void onGetCollisionBorderSize(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<HitBoxes>)QiaY.class).IuiN()) {
            callbackInfoReturnable.setReturnValue(vlwv.hNxW.qHPF((Class<HitBoxes>)QiaY.class).hitBoxSize.OnGi().floatValue());
        }
    }
}
