package xyz.mathax.mathaxclient.mixin;

import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.DropItemsEvent;
import xyz.mathax.mathaxclient.mixininterface.IClientPlayerInteractionManager;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.InventoryTweaks;
import xyz.mathax.mathaxclient.systems.modules.player.Reach;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import xyz.mathax.mathaxclient.events.entity.player.*;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {
    @Shadow
    private int blockBreakingCooldown;

    @Shadow
    protected abstract void syncSelectedSlot();

    @Shadow
    public abstract void clickSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player);

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void onClickSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo info) {
        if (actionType == SlotActionType.THROW && slotId >= 0 && slotId < player.currentScreenHandler.slots.size()) {
            if (MatHax.EVENT_BUS.post(DropItemsEvent.get(player.currentScreenHandler.slots.get(slotId).getStack())).isCancelled()) {
                info.cancel();
            }
        } else if (slotId == -999) {
            if (MatHax.EVENT_BUS.post(DropItemsEvent.get(player.currentScreenHandler.getCursorStack())).isCancelled()) {
                info.cancel();
            }
        }
    }

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    public void onClickArmorSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo info) {
        if (!Modules.get().get(InventoryTweaks.class).armorStorage()) {
            return;
        }

        ScreenHandler screenHandler = player.currentScreenHandler;
        if (screenHandler instanceof PlayerScreenHandler) {
            if (slotId >= 5 && slotId <= 8) {
                int armorSlot = (8 - slotId) + 36;
                if (actionType == SlotActionType.PICKUP && !screenHandler.getCursorStack().isEmpty()) {
                    clickSlot(syncId, 17, armorSlot, SlotActionType.SWAP, player);
                    clickSlot(syncId, 17, button, SlotActionType.PICKUP, player);
                    clickSlot(syncId, 17, armorSlot, SlotActionType.SWAP, player);
                    info.cancel();
                } else if (actionType == SlotActionType.SWAP) {
                    clickSlot(syncId, 36 + button, armorSlot, SlotActionType.SWAP, player);
                    info.cancel();
                }
            }
        }
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (MatHax.EVENT_BUS.post(StartBreakingBlockEvent.get(blockPos, direction)).isCancelled()) {
            infoReturnable.cancel();
        }
    }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> infoReturnable) {
        if (MatHax.EVENT_BUS.post(InteractBlockEvent.get(player.getMainHandStack().isEmpty() ? Hand.OFF_HAND : hand, hitResult)).isCancelled()) {
            infoReturnable.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
        if (MatHax.EVENT_BUS.post(AttackEntityEvent.get(target)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
    private void onInteractEntity(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if (MatHax.EVENT_BUS.post(InteractEntityEvent.get(entity, hand)).isCancelled()) {
            info.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(method = "dropCreativeStack", at = @At("HEAD"), cancellable = true)
    private void onDropCreativeStack(ItemStack stack, CallbackInfo info) {
        if (MatHax.EVENT_BUS.post(DropItemsEvent.get(stack)).isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> infoReturnable) {
        infoReturnable.setReturnValue(Modules.get().get(Reach.class).getReach());
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", ordinal = 3))
    private void updateBlockBreakingProgress3(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = MatHax.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
        blockBreakingCooldown = event.cooldown;
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", ordinal = 4))
    private void updateBlockBreakingProgress4(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = MatHax.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
        blockBreakingCooldown = event.cooldown;
    }

    @Redirect(method = "attackBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.PUTFIELD))
    private void attackBlock(ClientPlayerInteractionManager interactionManager, int value) {
        BlockBreakingCooldownEvent event = MatHax.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
        blockBreakingCooldown = event.cooldown;
    }

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    private void onBreakBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> infoReturnable) {
        BreakBlockEvent event = BreakBlockEvent.get(blockPos);
        MatHax.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            infoReturnable.setReturnValue(false);
            infoReturnable.cancel();
        }
    }

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> infoReturnable) {
        InteractItemEvent event = MatHax.EVENT_BUS.post(InteractItemEvent.get(hand));
        if (event.toReturn != null) {
            infoReturnable.setReturnValue(event.toReturn);
        }
    }

    @Inject(method = "cancelBlockBreaking", at = @At("HEAD"), cancellable = true)
    private void onCancelBlockBreaking(CallbackInfo info) {
        if (BlockUtils.breaking) {
            info.cancel();
        }
    }

    @ModifyArgs(method = "interactItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket$Full;<init>(DDDFFZ)V"))
    private void onInteractItem(Args args) {
        if (Rotations.rotating) {
            args.set(3, Rotations.serverYaw);
            args.set(4, Rotations.serverPitch);
        }
    }

    @Override
    public void syncSelected() {
        syncSelectedSlot();
    }
}
