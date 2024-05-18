/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.injection.mixin.event;

import com.mojang.authlib.GameProfile;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.PostUpdateListener;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.event.rotate.RotationSetListener;
import de.dietrichpaul.clientbase.event.rotate.SendRotationListener;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    public float onGetYaw(ClientPlayerEntity instance) {
        SendRotationListener.SendRotationEvent sendRotationEvent = ClientBase.INSTANCE.getEventDispatcher().post(new SendRotationListener.SendRotationEvent(instance.getYaw(), SendRotationListener.Type.YAW));
        return sendRotationEvent.value;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    public float onGetPitch(ClientPlayerEntity instance) {
        SendRotationListener.SendRotationEvent sendRotationEvent = ClientBase.INSTANCE.getEventDispatcher().post(new SendRotationListener.SendRotationEvent(instance.getPitch(), SendRotationListener.Type.PITCH));
        return sendRotationEvent.value;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void onPostTick(CallbackInfo ci) {
        ClientBase.INSTANCE.getEventDispatcher().post(PostUpdateListener.PostUpdateEvent.INSTANCE);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.BEFORE))
    public void onUpdate(CallbackInfo ci) {
        ClientBase.INSTANCE.getEventDispatcher().post(new UpdateListener.UpdateEvent());
    }

    @Override
    protected void setRotation(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
        ClientBase.INSTANCE.getEventDispatcher().post(new RotationSetListener.RotationSetEvent(getYaw(), getPitch(), true, true));
    }
}
