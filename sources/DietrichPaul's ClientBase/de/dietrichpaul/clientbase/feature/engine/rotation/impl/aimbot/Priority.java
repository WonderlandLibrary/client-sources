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
package de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot;

import de.dietrichpaul.clientbase.util.math.MathUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public enum Priority {

    DISTANCE("Distance", Comparator.comparingDouble(e -> {
        Vec3d camera = MinecraftClient.getInstance().player.getCameraPosVec(1.0F);
        return camera.distanceTo(MathUtil.clamp(camera, e.getBoundingBox().expand(e.getTargetingMargin())));
    })),
    FOV("FOV", Comparator.comparingDouble(e -> {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        Vec3d camera = player.getCameraPosVec(1.0F);
        double angle = Math.toDegrees(Math.atan2(camera.x - e.getX(), e.getZ() - camera.z));
        return Math.abs(MathHelper.wrapDegrees(angle - player.getYaw()));
    })),
    HEALTH("Health", Comparator.comparingDouble(e -> {
        if (e instanceof LivingEntity living)
            return living.getHealth();
        return 0;
    })),
    HURT_TIME("HurtTime", Comparator.comparingDouble(e -> {
        if (e instanceof LivingEntity living)
            return living.hurtTime;
        return 0;
    }));

    private String name;
    private Comparator<Entity> comparator;
    Priority(String name, Comparator<Entity> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    @Override
    public String toString() {
        return name;
    }

    public Comparator<Entity> getComparator() {
        return comparator;
    }
}
