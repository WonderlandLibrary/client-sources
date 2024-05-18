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
package de.dietrichpaul.clientbase.feature.engine.rotation.impl;

import com.sun.jna.platform.win32.OaIdl;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.RotationMode;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.engine.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.aimbot.Priority;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.*;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import de.dietrichpaul.clientbase.util.minecraft.rtx.RaytraceUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AimbotRotationSpoof extends RotationSpoof {

    private final EntityTypeProperty entityTypeProperty = new EntityTypeProperty("Entity Type", true, EntityType.PLAYER);
    private final BooleanProperty checkThroughWallProperty = new BooleanProperty("Exclude through-wall", true);
    private final IntProperty maxTargets = new IntProperty("Max targets", 1, 1, 20);
    private final FloatProperty aimRangeProperty = new FloatProperty("AimRange", 1.5F, 0, 6);
    private final FloatProperty rangeProperty = new FloatProperty("Range", 3, 0, 6);
    private final IntProperty fovProperty = new IntProperty("FOV", 360, 0, 360);
    private final EnumProperty<Priority> priorityProperty = new EnumProperty<>("Priority", Priority.DISTANCE, Priority.values(), Priority.class);
    private final EnumProperty<RotationMode> rotationModeProperty = new EnumProperty<>("Rotation", RotationMode.CLOSEST, RotationMode.values(), RotationMode.class);
    private final Hack parent;

    private final List<Entity> targets = new LinkedList<>();

    public AimbotRotationSpoof(Hack parent, PropertyGroup propertyGroup) {
        super(propertyGroup);
        this.parent = parent;
        PropertyGroup targetGroup = parent.addPropertyGroup("Targets");
        targetGroup.addProperty(entityTypeProperty);
        targetGroup.addProperty(maxTargets);
        targetGroup.addProperty(aimRangeProperty);
        targetGroup.addProperty(rangeProperty);
        targetGroup.addProperty(fovProperty);
        targetGroup.addProperty(priorityProperty);
        targetGroup.addProperty(checkThroughWallProperty);

        propertyGroup.addProperty(rotationModeProperty);
    }

    @Override
    public void tick() {
        rotationModeProperty.getValue().getMethod().tick(targets.get(0));
    }

    @Override
    public boolean pickTarget() {
        targets.clear();
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        for (Entity entity : mc.world.getEntities()) {
            if (entity == null || entity instanceof ClientPlayerEntity)
                continue;

            if (!entityTypeProperty.filter(entity)) // Entity isn't a target
                continue;

            if (entity instanceof OtherClientPlayerEntity player && ClientBase.INSTANCE.getFriendList().getFriends().contains(player.getGameProfile().getName()))
                continue;

            // Entity out of distance
            Vec3d closest = MathUtil.clamp(camera, entity.getBoundingBox().expand(entity.getTargetingMargin()));
            if (camera.distanceTo(closest) > aimRangeProperty.getValue() + rangeProperty.getValue()) {
                continue;
            }

            // Entity out of sight
            if (fovProperty.getValue() < 360) {
                double angleDiff = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(entity.getZ() - camera.z, entity.getX() - camera.x)) - 90 - mc.player.getYaw());
                if (Math.abs(angleDiff) > fovProperty.getValue() / 2.0)
                    continue;
            }

            if (checkThroughWallProperty.getState()) {
                // can hit entity
                Box aabb = entity.getBoundingBox().expand(entity.getTargetingMargin());
                boolean found = false;
                {
                    Vec3d hitVec = MathUtil.clamp(camera, aabb);
                    float[] rotations = new float[2];
                    MathUtil.getRotations(camera, hitVec, rotations);
                    Raytrace raytrace = RaytraceUtil.raytrace(mc, rotations, rotations, getRange() + aimRangeProperty.getValue(), 1.0F);
                    if (raytrace.hitResult() instanceof EntityHitResult) {
                        found = true;
                    }
                }
                if (!found) {
                    bruteforce:
                    {
                        for (double x = 0; x <= 1; x += 1 / 4.0) {
                            for (double y = 0; y <= 1; y += 1 / 4.0) {
                                for (double z = 0; z <= 1; z += 1 / 4.0) {
                                    Vec3d hitVec = new Vec3d(
                                            MathHelper.lerp(x, aabb.minX, aabb.maxX),
                                            MathHelper.lerp(y, aabb.minY, aabb.maxY),
                                            MathHelper.lerp(z, aabb.minZ, aabb.maxZ)
                                    );
                                    float[] rotations = new float[2];
                                    MathUtil.getRotations(camera, hitVec, rotations);
                                    Raytrace raytrace = RaytraceUtil.raytrace(mc, rotations, rotations, getRange() + aimRangeProperty.getValue(), 1.0F);
                                    if (raytrace.hitResult() instanceof EntityHitResult) {
                                        found = true;
                                        break bruteforce;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!found)
                    continue;
            }

            targets.add(entity);
        }
        targets.sort(priorityProperty.getValue().getComparator());
        while (targets.size() > maxTargets.getValue()) {
            targets.remove(targets.size() - 1);
        }
        return !targets.isEmpty();
    }

    @Override
    public void rotate(float[] rotations, float[] prevRotations, boolean tick, float partialTicks) {
        Vec3d camera = mc.player.getCameraPosVec(partialTicks);
        Entity primaryTarget = targets.get(0);
        Box aabb = primaryTarget.getBoundingBox()
                .offset(-primaryTarget.getX(), -primaryTarget.getY(), -primaryTarget.getZ())
                .offset(primaryTarget.prevX, primaryTarget.prevY, primaryTarget.prevZ)
                .offset(new Vec3d(
                                primaryTarget.getX() - primaryTarget.prevX,
                                primaryTarget.getY() - primaryTarget.prevY,
                                primaryTarget.getZ() - primaryTarget.prevZ
                        ).multiply(partialTicks)
                );
        rotationModeProperty.getValue().getMethod().getRotations(camera, aabb, primaryTarget, rotations, prevRotations, MinecraftClient.getInstance().getTickDelta());

        float[] outRotations = new float[2];
        engine.mouseSensitivity(prevRotations, rotations, outRotations, 1.0F);
        Raytrace raytrace = RaytraceUtil.raytrace(mc, outRotations, outRotations, getRange(), partialTicks);
        if (raytrace.hitResult() instanceof EntityHitResult) {
            return;
        }

        float length = Float.MAX_VALUE;
        for (double x = 0; x <= 1; x += 1 / 12.0) {
            for (double y = 0; y <= 1; y += 1 / 12.0) {
                for (double z = 0; z <= 1; z += 1 / 12.0) {
                    Vec3d hitVec = new Vec3d(
                            MathHelper.lerp(x, aabb.minX, aabb.maxX),
                            MathHelper.lerp(y, aabb.minY, aabb.maxY),
                            MathHelper.lerp(z, aabb.minZ, aabb.maxZ)
                    );
                    float[] bruteForce = new float[2];
                    MathUtil.getRotations(camera, hitVec, bruteForce);

                    engine.mouseSensitivity(prevRotations, bruteForce, outRotations, 1.0F);
                    float yawDiff = MathHelper.angleBetween(prevRotations[0], outRotations[0]);
                    float pitchDiff = MathHelper.angleBetween(prevRotations[1], outRotations[1]);
                    float len = MathHelper.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);
                    if (len > length)
                        continue;

                    raytrace = RaytraceUtil.raytrace(mc, outRotations, outRotations, getRange(), partialTicks);
                    if (!(raytrace.hitResult() instanceof EntityHitResult)) {
                        continue;
                    }

                    length = len;
                    rotations[0] = bruteForce[0];
                    rotations[1] = bruteForce[1];
                }
            }
        }
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public float getRange() {
        return rangeProperty.getValue();
    }

    @Override
    public Raytrace getTarget() {
        Raytrace rtx = new Raytrace(targets.get(0), new EntityHitResult(targets.get(0), targets.get(0).getBoundingBox().getCenter()));
        if (targets.get(0).distanceTo(mc.player) > getRange()) {
            rtx = new Raytrace(null, BlockHitResult.createMissed(mc.player.getCameraPosVec(1.0F), Direction.UP, BlockPos.ORIGIN));
        }
        return rtx;
    }

    public Entity getPrimaryTarget() {
        return targets.get(0);
    }

    public List<Entity> getTargets() {
        return targets;
    }
}
