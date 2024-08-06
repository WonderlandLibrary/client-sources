package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.WorldUtil;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Aim Assist",
    uniqueId = "aimassist",
    description = "Helps You Aim",
    category = ModuleCategory.Combat
)
public class AimAssist extends Module {

    @ConfigMode
    @ConfigParentId("smoothrots")
    @ConfigOption(name = "Rotations", description = "Rotation Speed", order = 1)
    public RotationMode rotationMode = RotationMode.Simple;

    @ConfigChild(value = "smoothrots", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Smooth Rotations Speed",
        description = "Time It Takes To Move To A Target (Ticks)",
        order = 1.1,
        min = 1,
        max = 60,
        precision = 2
    )
    public Float smoothRotationsSpeed = 3f;

    @ConfigChild(value = "smoothrots", parentEnumOrdinal = 1)
    @ConfigMinFor("simrots")
    @ConfigOption(
        name = "Rotation Speed MIN",
        description = "Simple Rotation Speed",
        order = 1.1,
        max = 10,
        precision = 3
    )
    public Float simpleRotsMin = 1f;

    @ConfigChild(value = "smoothrots", parentEnumOrdinal = 1)
    @ConfigMaxFor("simrots")
    @ConfigOption(
        name = "Rotation Speed MAX",
        description = "Simple Rotation Speed",
        order = 1.1,
        max = 10,
        precision = 3
    )
    public Float simpleRotsMax = 2f;

    @ConfigOption(
        name = "Rotation Distance",
        description = "Distance Before Rotation",
        order = 2,
        max = 7,
        precision = 2
    )
    public Float rotationDistance = 5.0f;

    @ConfigOption(
        name = "Only Needed Rotations",
        description = "Only Rotates If Not Looking At Entity",
        order = 3
    )
    public Boolean onlyNeededRots = true;

    @ConfigOption(
        name = "Only If Holding Sword",
        description = "Only Rotates If Holding A Sword",
        order = 4
    )
    public Boolean onlyHoldingSword = true;

    @ConfigOption(
        name = "Only If Left Click Down",
        description = "Only Rotates If Left Click Down",
        order = 5
    )
    public Boolean onlyOnClick = true;

    @ConfigOption(
        name = "Only If Not Looking At Block",
        description = "Only Rotates If Not Looking At Block",
        order = 3
    )
    public Boolean DontIfBlock = true;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    Entity target = null;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre event) {
        if (
            target == null ||
            !TargetUtil.shouldTarget(target) ||
            C.p().distanceTo(target) > rotationDistance
        ) target = getTarget();

        EntityHitResult entityHitResult = WorldUtil.rayTraceEntity(
            event.getPitch(),
            RotationUtil.wrapAngleTo180_float(event.getYaw()),
            rotationDistance
        );

        boolean lookingAtEnt =
            entityHitResult != null &&
            entityHitResult.getType() == HitResult.Type.ENTITY;
        if (
            entityHitResult != null &&
            entityHitResult.getEntity() != target &&
            TargetUtil.shouldTarget(entityHitResult.getEntity()) &&
            C.p().distanceTo(entityHitResult.getEntity()) < 3
        ) target = entityHitResult.getEntity();

        BlockHitResult hitResultBlock = WorldUtil.rayTrace(
            C.p().getPitch(),
            C.p().getYaw(),
            5
        );

        if (
            DontIfBlock &&
            hitResultBlock != null &&
            hitResultBlock.getType() == HitResult.Type.BLOCK
        ) return;

        if (
            target != null &&
            (!lookingAtEnt || !onlyNeededRots) &&
            (!onlyHoldingSword ||
                C.p().getStackInHand(Hand.MAIN_HAND).getItem() instanceof
                    SwordItem) &&
            (!onlyOnClick || C.mc.options.attackKey.isPressed())
        ) {
            RotationUtil.Rotation rot = RotationUtil.getLegitRotation(
                event.getRotation(),
                RotationUtil.getRotation(target.getEyePos())
            );
            if (rotationMode == RotationMode.Smooth) rot =
                RotationUtil.getSmoothRotation(
                    event.getRotation(),
                    RotationUtil.getRotation(target.getEyePos()),
                    smoothRotationsSpeed
                );
            if (rotationMode == RotationMode.Simple) rot =
                RotationUtil.getLimitedRotation(
                    event.getRotation(),
                    RotationUtil.getRotation(target.getEyePos()),
                    (simpleRotsMin +
                        (Math.abs(simpleRotsMax - simpleRotsMin) *
                            new Random().nextFloat()))
                );

            C.p().setYaw(rot.yaw);
            C.p().setPitch(rot.pitch);
        }
    }

    private @Nullable Entity getTarget() {
        return getSingleTarget(getPossibleTargets());
    }

    private @Nullable Entity getSingleTarget(Stream<Entity> s) {
        return s
            .min(
                Comparator.comparingDouble(
                    ent -> ent.squaredDistanceTo(C.p().getPos())
                )
            )
            .orElse(null);
    }

    private Stream<Entity> getPossibleTargets() {
        double maxReachSq = Math.pow(rotationDistance, 2);
        Vec3d eP = C.p().getEyePos();
        return StreamSupport.stream(C.w().getEntities().spliterator(), true)
            .filter(TargetUtil::shouldTarget)
            .filter(ent -> ent.squaredDistanceTo(eP) <= maxReachSq);
    }

    // test if github worky
    public enum RotationMode {
        Smooth,
        Simple,
        Legit,
    }
}
