/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class MoveToSkylightTask
extends Task<LivingEntity> {
    private final float speed;

    public MoveToSkylightTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.speed = f;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Optional<Vector3d> optional = Optional.ofNullable(this.findSkylightPosition(serverWorld, livingEntity));
        if (optional.isPresent()) {
            livingEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, optional.map(this::lambda$startExecuting$0));
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return !serverWorld.canSeeSky(livingEntity.getPosition());
    }

    @Nullable
    private Vector3d findSkylightPosition(ServerWorld serverWorld, LivingEntity livingEntity) {
        Random random2 = livingEntity.getRNG();
        BlockPos blockPos = livingEntity.getPosition();
        for (int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = blockPos.add(random2.nextInt(20) - 10, random2.nextInt(6) - 3, random2.nextInt(20) - 10);
            if (!MoveToSkylightTask.func_226306_a_(serverWorld, livingEntity, blockPos2)) continue;
            return Vector3d.copyCenteredHorizontally(blockPos2);
        }
        return null;
    }

    public static boolean func_226306_a_(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        return serverWorld.canSeeSky(blockPos) && (double)serverWorld.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() <= livingEntity.getPosY();
    }

    private WalkTarget lambda$startExecuting$0(Vector3d vector3d) {
        return new WalkTarget(vector3d, this.speed, 0);
    }
}

