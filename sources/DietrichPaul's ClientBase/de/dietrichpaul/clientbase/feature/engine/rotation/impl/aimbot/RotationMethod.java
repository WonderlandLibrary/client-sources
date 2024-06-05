package de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public interface RotationMethod {

    MinecraftClient mc = MinecraftClient.getInstance();

    void getRotations(Vec3d camera,
                      Box aabb, Entity target, float[] rotations, float[] prevRotations, float partialTicks);

    default void tick(Entity target) {
    }

}
