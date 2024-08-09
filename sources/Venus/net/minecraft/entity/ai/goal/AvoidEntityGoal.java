/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

public class AvoidEntityGoal<T extends LivingEntity>
extends Goal {
    protected final CreatureEntity entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T avoidTarget;
    protected final float avoidDistance;
    protected Path path;
    protected final PathNavigator navigation;
    protected final Class<T> classToAvoid;
    protected final Predicate<LivingEntity> avoidTargetSelector;
    protected final Predicate<LivingEntity> field_203784_k;
    private final EntityPredicate builtTargetSelector;

    public AvoidEntityGoal(CreatureEntity creatureEntity, Class<T> clazz, float f, double d, double d2) {
        this(creatureEntity, clazz, AvoidEntityGoal::lambda$new$0, f, d, d2, EntityPredicates.CAN_AI_TARGET::test);
    }

    public AvoidEntityGoal(CreatureEntity creatureEntity, Class<T> clazz, Predicate<LivingEntity> predicate, float f, double d, double d2, Predicate<LivingEntity> predicate2) {
        this.entity = creatureEntity;
        this.classToAvoid = clazz;
        this.avoidTargetSelector = predicate;
        this.avoidDistance = f;
        this.farSpeed = d;
        this.nearSpeed = d2;
        this.field_203784_k = predicate2;
        this.navigation = creatureEntity.getNavigator();
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        this.builtTargetSelector = new EntityPredicate().setDistance(f).setCustomPredicate(predicate2.and(predicate));
    }

    public AvoidEntityGoal(CreatureEntity creatureEntity, Class<T> clazz, float f, double d, double d2, Predicate<LivingEntity> predicate) {
        this(creatureEntity, clazz, AvoidEntityGoal::lambda$new$1, f, d, d2, predicate);
    }

    @Override
    public boolean shouldExecute() {
        this.avoidTarget = this.entity.world.func_225318_b(this.classToAvoid, this.builtTargetSelector, this.entity, this.entity.getPosX(), this.entity.getPosY(), this.entity.getPosZ(), this.entity.getBoundingBox().grow(this.avoidDistance, 3.0, this.avoidDistance));
        if (this.avoidTarget == null) {
            return true;
        }
        Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, ((Entity)this.avoidTarget).getPositionVec());
        if (vector3d == null) {
            return true;
        }
        if (((Entity)this.avoidTarget).getDistanceSq(vector3d.x, vector3d.y, vector3d.z) < ((Entity)this.avoidTarget).getDistanceSq(this.entity)) {
            return true;
        }
        this.path = this.navigation.getPathToPos(vector3d.x, vector3d.y, vector3d.z, 0);
        return this.path != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.navigation.noPath();
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.avoidTarget = null;
    }

    @Override
    public void tick() {
        if (this.entity.getDistanceSq((Entity)this.avoidTarget) < 49.0) {
            this.entity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.farSpeed);
        }
    }

    private static boolean lambda$new$1(LivingEntity livingEntity) {
        return false;
    }

    private static boolean lambda$new$0(LivingEntity livingEntity) {
        return false;
    }
}

