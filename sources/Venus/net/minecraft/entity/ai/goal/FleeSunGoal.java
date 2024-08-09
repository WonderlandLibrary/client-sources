/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FleeSunGoal
extends Goal {
    protected final CreatureEntity creature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final World world;

    public FleeSunGoal(CreatureEntity creatureEntity, double d) {
        this.creature = creatureEntity;
        this.movementSpeed = d;
        this.world = creatureEntity.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.creature.getAttackTarget() != null) {
            return true;
        }
        if (!this.world.isDaytime()) {
            return true;
        }
        if (!this.creature.isBurning()) {
            return true;
        }
        if (!this.world.canSeeSky(this.creature.getPosition())) {
            return true;
        }
        return !this.creature.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() ? false : this.isPossibleShelter();
    }

    protected boolean isPossibleShelter() {
        Vector3d vector3d = this.findPossibleShelter();
        if (vector3d == null) {
            return true;
        }
        this.shelterX = vector3d.x;
        this.shelterY = vector3d.y;
        this.shelterZ = vector3d.z;
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    @Nullable
    protected Vector3d findPossibleShelter() {
        Random random2 = this.creature.getRNG();
        BlockPos blockPos = this.creature.getPosition();
        for (int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = blockPos.add(random2.nextInt(20) - 10, random2.nextInt(6) - 3, random2.nextInt(20) - 10);
            if (this.world.canSeeSky(blockPos2) || !(this.creature.getBlockPathWeight(blockPos2) < 0.0f)) continue;
            return Vector3d.copyCenteredHorizontally(blockPos2);
        }
        return null;
    }
}

