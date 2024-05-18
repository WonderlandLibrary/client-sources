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

import de.dietrichpaul.clientbase.event.StrafeListener;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.rotate.RotationGetListener;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract float getYaw();

    @Shadow public abstract float getPitch();

    @Redirect(method = "lerpPosAndRotation", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
    public float getLerpYaw(Entity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getYaw();
        }
        return ClientBase.INSTANCE.getEventDispatcher().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).yaw;
    }

    @Redirect(method = "lerpPosAndRotation", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPitch()F"))
    public float getLerpPitch(Entity instance) {
        if (!(instance instanceof ClientPlayerEntity)) {
            return instance.getPitch();
        }
        return ClientBase.INSTANCE.getEventDispatcher().post(new RotationGetListener.RotationGetEvent(getYaw(), getPitch())).yaw;
    }

    @Redirect(method = "updateVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
    public float getStrafeYaw(Entity instance) {
        if (instance instanceof ClientPlayerEntity) return ClientBase.INSTANCE.getEventDispatcher().post(new StrafeListener.StrafeEvent(instance.getYaw())).yaw;

        return instance.getYaw();
    }
}
