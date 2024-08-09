package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Inject(method = "onStoppedUsing", at = @At("HEAD"))
    private void onStoppedUsingHead(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (user == mc.player) {
            Utils.isReleasingTrident = true;
        }
    }

    @Inject(method = "onStoppedUsing", at = @At("TAIL"))
    private void onStoppedUsingTail(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (user == mc.player) {
            Utils.isReleasingTrident = false;
        }
    }

    /*@ModifyArgs(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addVelocity(DDD)V"))
    private void modifyVelocity(Args args) {
        TridentBoost tridentBoost = Modules.get().get(TridentBoost.class);
        args.set(0, (double) args.get(0) * tridentBoost.getMultiplier());
        args.set(1, (double) args.get(1) * tridentBoost.getMultiplier());
        args.set(2, (double) args.get(2) * tridentBoost.getMultiplier());
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    private boolean isInWaterUse(PlayerEntity playerEntity) {
        TridentBoost tridentBoost = Modules.get().get(TridentBoost.class);
        return tridentBoost.allowOutOfWater() || mc.player.isTouchingWaterOrRain();
    }

    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    private boolean isInWaterPostUse(PlayerEntity playerEntity) {
        TridentBoost tridentBoost = Modules.get().get(TridentBoost.class);
        return tridentBoost.allowOutOfWater() || mc.player.isTouchingWaterOrRain();
    }*/
}
