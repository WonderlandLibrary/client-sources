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

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.JumpListener;
import de.dietrichpaul.clientbase.event.rotate.RotationGetListener;
import de.dietrichpaul.clientbase.feature.engine.rotation.RotationEngine;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract float getYaw(float tickDelta);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float getJumpYaw(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            return ClientBase.INSTANCE.getEventDispatcher().post(new JumpListener.JumpEvent(instance.getYaw())).yaw;
        }
        return instance.getYaw();
    }

    @Redirect(method = "turnHead", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float getBodyYaw(LivingEntity instance) {
        if (instance instanceof ClientPlayerEntity) {
            RotationEngine engine = ClientBase.INSTANCE.getRotationEngine();
            if (engine.isRotating())
                return engine.getYaw();
        }
        return instance.getYaw();
    }
}
