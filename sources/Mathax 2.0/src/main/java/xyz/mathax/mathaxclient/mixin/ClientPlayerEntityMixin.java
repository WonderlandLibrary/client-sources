package xyz.mathax.mathaxclient.mixin;

import com.mojang.authlib.GameProfile;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.DamageEvent;
import xyz.mathax.mathaxclient.events.entity.DropItemsEvent;
import xyz.mathax.mathaxclient.events.entity.player.SendMovementPacketsEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.PortalChat;
import xyz.mathax.mathaxclient.systems.modules.movement.EntityControl;
import xyz.mathax.mathaxclient.systems.modules.movement.Velocity;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void onDropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (MatHax.EVENT_BUS.post(DropItemsEvent.get(getMainHandStack())).isCancelled()) {
            infoReturnable.setReturnValue(false);
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;"))
    private Screen updateNauseaGetCurrentScreenProxy(MinecraftClient client) {
        if (Modules.get().isEnabled(PortalChat.class)) {
            return null;
        }

        return client.currentScreen;
    }

    /*@Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean redirectUsingItem(ClientPlayerEntity player) {
        if (Modules.get().get(NoSlow.class).items()) {
            return false;
        }

        return player.isUsingItem();
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    private void onIsSneaking(CallbackInfoReturnable<Boolean> infoReturnable) {
        if (Modules.get().isEnabled(Scaffold.class)) {
            infoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    private void onShouldSlowDown(CallbackInfoReturnable<Boolean> infoReturnable) {
        if (Modules.get().get(NoSlow.class).sneaking()) {
            infoReturnable.setReturnValue(isCrawling());
        }
    }*/

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void onPushOutOfBlocks(double x, double d, CallbackInfo info) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if (velocity.isEnabled() && velocity.blocksSetting.get()) {
            info.cancel();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (Utils.canUpdate() && world.isClient && canTakeDamage()) {
            MatHax.EVENT_BUS.post(DamageEvent.get(this, source));
        }
    }

    // Rotations

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onSendMovementPacketsHead(CallbackInfo info) {
        MatHax.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 0))
    private void onTickHasVehicleBeforeSendPackets(CallbackInfo info) {
        MatHax.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
    }

    @Inject(method = "sendMovementPackets", at = @At("TAIL"))
    private void onSendMovementPacketsTail(CallbackInfo info) {
        MatHax.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onTickHasVehicleAfterSendPackets(CallbackInfo info) {
        MatHax.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
    }

    /*@Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSneaking()Z"))
    private boolean isSneaking(ClientPlayerEntity clientPlayerEntity) {
        return Modules.get().get(Sneak.class).doPacket() || Modules.get().get(NoSlow.class).airStrict() || clientPlayerEntity.isSneaking();
    }*/

    @Inject(method = "getMountJumpStrength", at = @At("HEAD"), cancellable = true)
    private void getMountJumpStrength(CallbackInfoReturnable<Float> infoReturnable) {
        if (Modules.get().get(EntityControl.class).isEnabled() && Modules.get().get(EntityControl.class).maxJumpSetting.get()) {
            infoReturnable.setReturnValue(1f);
        }
    }
}
