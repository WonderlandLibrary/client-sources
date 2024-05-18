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
package de.dietrichpaul.clientbase.util.minecraft.rtx;

import de.dietrichpaul.clientbase.injection.accessor.IGameRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

public class RaytraceUtil {

    public static Raytrace raytrace(MinecraftClient client, float[] rotations, float[] prevRotations,
                                    float range, float tickDelta) {
        if (client.getCameraEntity() == null)
            return null;

        float prevLastYaw = client.getCameraEntity().prevYaw;
        float prevLastPitch = client.getCameraEntity().prevPitch;
        float prevYaw = client.getCameraEntity().yaw;
        float prevPitch = client.getCameraEntity().pitch;
        HitResult prevCrosshairTarget = client.crosshairTarget;
        Entity prevTargetedEntity = client.targetedEntity;

        ((IGameRenderer) client.gameRenderer).setRange(range);
        ((IGameRenderer) client.gameRenderer).setCustomRaytrace(true);

        client.getCameraEntity().yaw = rotations[0];
        client.getCameraEntity().pitch = rotations[1];
        client.getCameraEntity().prevYaw = prevRotations[0];
        client.getCameraEntity().prevPitch = prevRotations[1];
        client.gameRenderer.updateTargetedEntity(tickDelta);
        Raytrace raytrace = new Raytrace(client.targetedEntity, client.crosshairTarget);

        client.getCameraEntity().prevYaw = prevLastYaw;
        client.getCameraEntity().prevPitch = prevLastPitch;
        client.getCameraEntity().yaw = prevYaw;
        client.getCameraEntity().pitch = prevPitch;
        client.crosshairTarget = prevCrosshairTarget;
        client.targetedEntity = prevTargetedEntity;
        ((IGameRenderer) client.gameRenderer).setRange(3.0);
        ((IGameRenderer) client.gameRenderer).setCustomRaytrace(false);

        return raytrace;
    }
}
