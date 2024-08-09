/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

public class FollowParentGoal
extends Goal {
    private final AnimalEntity childAnimal;
    private AnimalEntity parentAnimal;
    private final double moveSpeed;
    private int delayCounter;

    public FollowParentGoal(AnimalEntity animalEntity, double d) {
        this.childAnimal = animalEntity;
        this.moveSpeed = d;
    }

    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return true;
        }
        List<?> list = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().grow(8.0, 4.0, 8.0));
        AnimalEntity animalEntity = null;
        double d = Double.MAX_VALUE;
        for (AnimalEntity animalEntity2 : list) {
            double d2;
            if (animalEntity2.getGrowingAge() < 0 || (d2 = this.childAnimal.getDistanceSq(animalEntity2)) > d) continue;
            d = d2;
            animalEntity = animalEntity2;
        }
        if (animalEntity == null) {
            return true;
        }
        if (d < 9.0) {
            return true;
        }
        this.parentAnimal = animalEntity;
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return true;
        }
        if (!this.parentAnimal.isAlive()) {
            return true;
        }
        double d = this.childAnimal.getDistanceSq(this.parentAnimal);
        return !(d < 9.0) && !(d > 256.0);
    }

    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }

    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }

    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}

