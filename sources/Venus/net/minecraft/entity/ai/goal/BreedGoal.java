/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BreedGoal
extends Goal {
    private static final EntityPredicate field_220689_d = new EntityPredicate().setDistance(8.0).allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired();
    protected final AnimalEntity animal;
    private final Class<? extends AnimalEntity> mateClass;
    protected final World world;
    protected AnimalEntity targetMate;
    private int spawnBabyDelay;
    private final double moveSpeed;

    public BreedGoal(AnimalEntity animalEntity, double d) {
        this(animalEntity, d, animalEntity.getClass());
    }

    public BreedGoal(AnimalEntity animalEntity, double d, Class<? extends AnimalEntity> clazz) {
        this.animal = animalEntity;
        this.world = animalEntity.world;
        this.mateClass = clazz;
        this.moveSpeed = d;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.animal.isInLove()) {
            return true;
        }
        this.targetMate = this.getNearbyMate();
        return this.targetMate != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    @Override
    public void tick() {
        this.animal.getLookController().setLookPositionWithEntity(this.targetMate, 10.0f, this.animal.getVerticalFaceSpeed());
        this.animal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.animal.getDistanceSq(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
    }

    @Nullable
    private AnimalEntity getNearbyMate() {
        List<? extends AnimalEntity> list = this.world.getTargettableEntitiesWithinAABB(this.mateClass, field_220689_d, this.animal, this.animal.getBoundingBox().grow(8.0));
        double d = Double.MAX_VALUE;
        AnimalEntity animalEntity = null;
        for (AnimalEntity animalEntity2 : list) {
            if (!this.animal.canMateWith(animalEntity2) || !(this.animal.getDistanceSq(animalEntity2) < d)) continue;
            animalEntity = animalEntity2;
            d = this.animal.getDistanceSq(animalEntity2);
        }
        return animalEntity;
    }

    protected void spawnBaby() {
        this.animal.func_234177_a_((ServerWorld)this.world, this.targetMate);
    }
}

