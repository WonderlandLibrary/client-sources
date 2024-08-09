/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.server.ServerWorld;

public class AnimalBreedTask
extends Task<AnimalEntity> {
    private final EntityType<? extends AnimalEntity> breedTarget;
    private final float speed;
    private long breedTime;

    public AnimalBreedTask(EntityType<? extends AnimalEntity> entityType, float f) {
        super(ImmutableMap.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.BREED_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), 325);
        this.breedTarget = entityType;
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, AnimalEntity animalEntity) {
        return animalEntity.isInLove() && this.getNearestMate(animalEntity).isPresent();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, AnimalEntity animalEntity, long l) {
        AnimalEntity animalEntity2 = this.getNearestMate(animalEntity).get();
        animalEntity.getBrain().setMemory(MemoryModuleType.BREED_TARGET, animalEntity2);
        animalEntity2.getBrain().setMemory(MemoryModuleType.BREED_TARGET, animalEntity);
        BrainUtil.lookApproachEachOther(animalEntity, animalEntity2, this.speed);
        int n = 275 + animalEntity.getRNG().nextInt(50);
        this.breedTime = l + (long)n;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, AnimalEntity animalEntity, long l) {
        if (!this.canBreed(animalEntity)) {
            return true;
        }
        AnimalEntity animalEntity2 = this.getBreedTarget(animalEntity);
        return animalEntity2.isAlive() && animalEntity.canMateWith(animalEntity2) && BrainUtil.canSee(animalEntity.getBrain(), animalEntity2) && l <= this.breedTime;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, AnimalEntity animalEntity, long l) {
        AnimalEntity animalEntity2 = this.getBreedTarget(animalEntity);
        BrainUtil.lookApproachEachOther(animalEntity, animalEntity2, this.speed);
        if (animalEntity.isEntityInRange(animalEntity2, 3.0) && l >= this.breedTime) {
            animalEntity.func_234177_a_(serverWorld, animalEntity2);
            animalEntity.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
            animalEntity2.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
        }
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, AnimalEntity animalEntity, long l) {
        animalEntity.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
        animalEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        animalEntity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        this.breedTime = 0L;
    }

    private AnimalEntity getBreedTarget(AnimalEntity animalEntity) {
        return (AnimalEntity)animalEntity.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
    }

    private boolean canBreed(AnimalEntity animalEntity) {
        Brain<AgeableEntity> brain = animalEntity.getBrain();
        return brain.hasMemory(MemoryModuleType.BREED_TARGET) && brain.getMemory(MemoryModuleType.BREED_TARGET).get().getType() == this.breedTarget;
    }

    private Optional<? extends AnimalEntity> getNearestMate(AnimalEntity animalEntity) {
        return animalEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().filter(this::lambda$getNearestMate$0).map(AnimalBreedTask::lambda$getNearestMate$1).filter(animalEntity::canMateWith).findFirst();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (AnimalEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (AnimalEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (AnimalEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (AnimalEntity)livingEntity, l);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (AnimalEntity)livingEntity, l);
    }

    private static AnimalEntity lambda$getNearestMate$1(LivingEntity livingEntity) {
        return (AnimalEntity)livingEntity;
    }

    private boolean lambda$getNearestMate$0(LivingEntity livingEntity) {
        return livingEntity.getType() == this.breedTarget;
    }
}

