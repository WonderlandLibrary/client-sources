package xyz.mathax.mathaxclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.DropItemsEvent;
import xyz.mathax.mathaxclient.events.entity.player.ClipAtLedgeEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.movement.Anchor;
import xyz.mathax.mathaxclient.systems.modules.player.SpeedMine;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    protected void clipAtLedge(CallbackInfoReturnable<Boolean> infoReturnable) {
        ClipAtLedgeEvent event = MatHax.EVENT_BUS.post(ClipAtLedgeEvent.get());
        if (event.isSet()) {
            infoReturnable.setReturnValue(event.isClip());
        }
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void onDropItem(ItemStack stack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> info) {
        if (world.isClient && !stack.isEmpty()) {
            if (MatHax.EVENT_BUS.post(DropItemsEvent.get(stack)).isCancelled()) {
                info.cancel();
            }
        }
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "RETURN"), cancellable = true)
    public void onGetBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> infoReturnable) {
        SpeedMine speedMine = Modules.get().get(SpeedMine.class);
        if (!speedMine.isEnabled() || speedMine.modeSetting.get() != SpeedMine.Mode.Normal) {
            return;
        }

        float breakSpeed = infoReturnable.getReturnValue();
        float breakSpeedMod = (float) (breakSpeed * speedMine.modifierSetting.get());
        HitResult result = mc.crosshairTarget;
        if (result != null && result.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) result).getBlockPos();
            if (speedMine.modifierSetting.get() < 1 || (BlockUtils.canInstaBreak(pos, breakSpeed) == BlockUtils.canInstaBreak(pos, breakSpeedMod)))
                infoReturnable.setReturnValue(breakSpeedMod);
            else
                infoReturnable.setReturnValue(0.9f / BlockUtils.calcBlockBreakingDelta2(pos, 1));
        }
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void dontJump(CallbackInfo info) {
        Anchor anchor = Modules.get().get(Anchor.class);
        if (anchor.isEnabled() && anchor.cancelJump) {
            info.cancel();
        }
    }
}
