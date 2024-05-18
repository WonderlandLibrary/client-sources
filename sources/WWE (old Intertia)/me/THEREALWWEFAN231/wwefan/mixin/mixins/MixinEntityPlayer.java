package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.world.*;
import net.minecraft.entity.*;
import wwe.modules.combat.*;
import wwe.modules.exploits.*;
import wwe.modules.movement.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.modules.player.*;
import wwe.*;

@Mixin(value = { EntityPlayer.class }, priority = 9999)
public abstract class MixinEntityPlayer
{
    public MixinEntityPlayer() {
        super();
    }
    
    @Shadow
    public abstract GameProfile func_146103_bH();
    
    @Inject(method = { "getCooldownPeriod" }, at = { @At("HEAD") }, cancellable = true)
    private void getPlayerCooldownPeriod(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<TpsSync>)cNBO.class).IuiN()) {
            callbackInfoReturnable.setReturnValue((float)(1.0 / EntityPlayer.class.cast(this).getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0 * (20.0f / PJxY.Npqe)));
            callbackInfoReturnable.cancel();
        }
        else if (vlwv.hNxW.qHPF((Class<Timer>)lXGb.class).IuiN()) {
            callbackInfoReturnable.setReturnValue((float)(1.0 / EntityPlayer.class.cast(this).getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0 * vlwv.hNxW.qHPF((Class<Timer>)lXGb.class).speed.OnGi().floatValue() + 2.0));
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "isPushedByWater" }, at = { @At("HEAD") }, cancellable = true)
    private void isPushedByWater(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<AntiKnockback>)RWsP.class).IuiN() || vlwv.hNxW.qHPF((Class<NoPush>)jsGp.class).IuiN()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @ModifyConstant(method = { "attackTargetEntityWithCurrentItem" }, constant = { @Constant(doubleValue = 0.6) })
    private double multiplyMotion(final double n) {
        try {
            if (vlwv.hNxW.qHPF((Class<Sprint>)dbwp.class).IuiN()) {
                return 1.0;
            }
        }
        catch (Exception ex) {}
        return n;
    }
    
    @Inject(method = { "isEntityInsideOpaqueBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void isEntityInsideOpaqueBlock(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<Freecam>)ZTVf.class).IuiN()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
