package xyz.mathax.mathaxclient.mixin;

import net.minecraft.entity.EquipmentSlot;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.DamageEvent;
import xyz.mathax.mathaxclient.events.entity.player.CanWalkOnFluidEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.OffhandCrash;
import xyz.mathax.mathaxclient.systems.modules.render.HandView;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamageHead(DamageSource source, float amount, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (Utils.canUpdate() && world.isClient) {
            MatHax.EVENT_BUS.post(DamageEvent.get((LivingEntity) (Object) this, source));
        }
    }

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void onCanWalkOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> infoReturnable) {
        if ((Object) this != mc.player) {
            return;
        }

        CanWalkOnFluidEvent event = MatHax.EVENT_BUS.post(CanWalkOnFluidEvent.get(fluidState));
        infoReturnable.setReturnValue(event.walkOnFluid);
    }

    /*@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean travelHasStatusEffectProxy(LivingEntity self, StatusEffect statusEffect) {
        if (statusEffect == StatusEffects.LEVITATION && Modules.get().isEnabled(AntiLevitation.class)) {
            return false;
        }

        return self.hasStatusEffect(statusEffect);
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z"))
    private boolean travelHasNoGravityProxy(LivingEntity self) {
        if (self.hasStatusEffect(StatusEffects.LEVITATION) && Modules.get().isEnabled(AntiLevitation.class)) {
            return !Modules.get().get(AntiLevitation.class).isApplyGravity();
        }

        return self.hasNoGravity();
    }*/

    @Inject(method = "spawnItemParticles", at = @At("HEAD"), cancellable = true)
    private void spawnItemParticles(ItemStack stack, int count, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noEatParticles() && stack.isFood()) {
            info.cancel();
        }
    }

    @Inject(method = "onEquipStack", at = @At("HEAD"), cancellable = true)
    private void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo info) {
        if ((Object) this == mc.player && Modules.get().get(OffhandCrash.class).isAntiCrash()) {
            info.cancel();
        }
    }

    @ModifyArg(method = "swingHand(Lnet/minecraft/util/Hand;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;swingHand(Lnet/minecraft/util/Hand;Z)V"))
    private Hand setHand(Hand hand) {
        HandView handView = Modules.get().get(HandView.class);
        if ((Object) this == mc.player && handView.isEnabled()) {
            if (handView.swingModeSetting.get() == HandView.SwingMode.None) {
                return hand;
            }

            return handView.swingModeSetting.get() == HandView.SwingMode.Offhand ? Hand.OFF_HAND : Hand.MAIN_HAND;
        }

        return hand;
    }

    @ModifyConstant(method = "getHandSwingDuration", constant = @Constant(intValue = 6))
    private int getHandSwingDuration(int constant) {
        if ((Object) this != mc.player) {
            return constant;
        }

        return Modules.get().get(HandView.class).isEnabled() && mc.options.getPerspective().isFirstPerson() ? Modules.get().get(HandView.class).swingSpeedSetting.get() : constant;
    }

    /*@Inject(method = "isFallFlying", at = @At("HEAD"), cancellable = true)
    private void isFallFlyingHook(CallbackInfoReturnable<Boolean> infoReturnable) {
        if ((Object) this == mc.player && Modules.get().get(ElytraFly.class).canPacketEfly()) {
            infoReturnable.setReturnValue(true);
        }
    }*/
}
