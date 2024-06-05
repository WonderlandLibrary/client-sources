package de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot;

import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.math.ProjectedBox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.SimplexNoise;

import static de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.RotationMethod.mc;

public enum RotationMode {

    HEAD("Head", (camera, box, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, target.getCameraPosVec(partialTicks), rotations);
    }),
    CENTER("Center", ((camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, aabb.getCenter(), rotations);
    })),
    CLOSEST("Closest", (camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        MathUtil.getRotations(camera, MathUtil.clamp(camera, aabb), rotations);
    }),
    NEAREST("Nearest", (camera, aabb, target, rotations, prevRotations, partialTicks) -> {
        rotations[0] = prevRotations[0];
        rotations[1] = prevRotations[1];
    }),
    SIMPLEX("Simplex", new RotationMethod() {

        private float x = 0.5F;
        private float prevX;

        private float state;

        @Override
        public void tick(Entity target) {

            Vec3d targetVelocity = new Vec3d(target.getX() - target.prevX, target.getY() - target.prevY,
                    target.getZ() - target.prevZ);
            Vec3d velocity = new Vec3d(mc.player.getX() - mc.player.prevX, mc.player.getY() - mc.player.prevY,
                    mc.player.getZ() - mc.player.prevZ);
            double velocityLength = targetVelocity.length() + velocity.length();
            prevX = x;
            x = 0.5F + SimplexNoise.noise(state, 0) / 2;
            if (velocityLength > 0.07) {
                state += 0.05F;
            }
        }

        @Override
        public void getRotations(Vec3d camera, Box aabb, Entity target, float[] rotations, float[] prevRotations, float partialTicks) {
            if (aabb.contains(camera)) {
                float[] nearest = new float[2];
                Box box = target.getBoundingBox();
                Vec3d fixCamera = mc.player.getCameraPosVec(1.0F);
                Vec3d polar = Vec3d.fromPolar(prevRotations[1], prevRotations[0]);
                double rayLength = camera.distanceTo(box.getCenter()) * 0.5 + camera.distanceTo(MathUtil.clamp(camera, box)) * 0.5;
                MathUtil.getRotations(fixCamera, MathUtil.clamp(fixCamera.add(polar.multiply(rayLength)), box), nearest);
                rotations[0] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[0], nearest[0]);
                rotations[1] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[1], nearest[1]);
                return;
            }

            ProjectedBox projBox = new ProjectedBox(camera, aabb);
            Vec3d bestVec = MathUtil.clamp(camera, aabb);

            float[] bestRotations = new float[2];
            MathUtil.getRotations(camera, bestVec, bestRotations);

            rotations[0] = projBox.getYaw(MathHelper.lerp(partialTicks, prevX, x));
            rotations[1] = projBox.getPitch(0.5F);


            float[] nearest = new float[2];
            Box box = target.getBoundingBox();
            Vec3d fixCamera = mc.player.getCameraPosVec(1.0F);
            Vec3d polar = Vec3d.fromPolar(prevRotations[1], prevRotations[0]);
            double rayLength = camera.distanceTo(box.getCenter()) * 0.5 + camera.distanceTo(MathUtil.clamp(camera, box)) * 0.5;
            MathUtil.getRotations(fixCamera, MathUtil.clamp(fixCamera.add(polar.multiply(rayLength)), box), nearest);
            rotations[1] = MathHelper.lerpAngleDegrees(partialTicks, prevRotations[1], nearest[1]);
        }
    }),
    RANDOM_CENTER("RandomCenter", new RotationMethod() {

        private Vec3d hitVec = new Vec3d(0.5, 0.5, 0.5);
        private Vec3d prevHitVec;
        private int step;

        @Override
        public void tick(Entity target) {
            prevHitVec = hitVec;

            Box box = target.getBoundingBox();

            double lenX = box.getLengthX();
            double lenY = box.getLengthY();
            double lenZ = box.getLengthZ();
            double diagonale = Math.sqrt(lenX * lenX + lenY * lenY + lenZ * lenZ);

            double invX = 1.0 / lenX / diagonale;
            double invY = 1.0 / lenY / diagonale;
            double invZ = 1.0 / lenZ / diagonale;
            float speed = 0.1F;

            float x = (SimplexNoise.noise(((float) invX) * speed * step, 0, 0) + 1) / 2;
            float y = (SimplexNoise.noise(0, ((float) invY) * speed * step, 0) + 1) / 2;
            float z = (SimplexNoise.noise(0, 0, ((float) invZ) * speed * step) + 1) / 2;
            hitVec = new Vec3d(x, y, z);
            step++;
        }

        @Override
        public void getRotations(Vec3d camera, Box aabb, Entity target, float[] rotations, float[] prevRotations, float partialTicks) {
            Vec3d relativeHitVec = prevHitVec.lerp(this.hitVec, partialTicks);
            Vec3d hitVec = new Vec3d(
                    MathHelper.lerp(relativeHitVec.x, aabb.minX, aabb.maxX),
                    MathHelper.lerp(relativeHitVec.y, aabb.minY, aabb.maxY),
                    MathHelper.lerp(relativeHitVec.z, aabb.minZ, aabb.maxZ)
            );
            MathUtil.getRotations(camera, hitVec, rotations);
        }
    });

    private String name;
    private RotationMethod method;

    RotationMode(String name, RotationMethod method) {
        this.name = name;
        this.method = method;
    }

    @Override
    public String toString() {
        return name;
    }

    public RotationMethod getMethod() {
        return method;
    }
}
