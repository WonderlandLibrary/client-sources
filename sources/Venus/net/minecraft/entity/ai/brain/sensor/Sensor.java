/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import java.util.Random;
import java.util.Set;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public abstract class Sensor<E extends LivingEntity> {
    private static final Random RANDOM = new Random();
    private static final EntityPredicate FRIENDLY_FIRE_WITH_VISIBILITY_CHECK = new EntityPredicate().setDistance(16.0).allowFriendlyFire().setSkipAttackChecks();
    private static final EntityPredicate FRIENDLY_FIRE_WITHOUT_VISIBILITY_CHECK = new EntityPredicate().setDistance(16.0).allowFriendlyFire().setSkipAttackChecks().setUseInvisibilityCheck();
    private final int interval;
    private long counter;

    public Sensor(int n) {
        this.interval = n;
        this.counter = RANDOM.nextInt(n);
    }

    public Sensor() {
        this(20);
    }

    public final void tick(ServerWorld serverWorld, E e) {
        if (--this.counter <= 0L) {
            this.counter = this.interval;
            this.update(serverWorld, e);
        }
    }

    protected abstract void update(ServerWorld var1, E var2);

    public abstract Set<MemoryModuleType<?>> getUsedMemories();

    protected static boolean canAttackTarget(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity.getBrain().hasMemory(MemoryModuleType.ATTACK_TARGET, livingEntity2) ? FRIENDLY_FIRE_WITHOUT_VISIBILITY_CHECK.canTarget(livingEntity, livingEntity2) : FRIENDLY_FIRE_WITH_VISIBILITY_CHECK.canTarget(livingEntity, livingEntity2);
    }
}

