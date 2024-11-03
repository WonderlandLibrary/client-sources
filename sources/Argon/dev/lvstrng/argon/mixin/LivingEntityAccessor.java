// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LivingEntity.class})
public interface LivingEntityAccessor {
    @Accessor
    boolean getJumping();

    @Accessor("lastDamageSource")
    DamageSource getLastDamageSource();

    @Accessor("lastDamageSource")
    void setLastDamageSource(final DamageSource p0);

    @Accessor("lastDamageTime")
    long getLastDamageTime();

    @Accessor("lastDamageTime")
    void setLastDamageTime(final long p0);

    @Accessor("lastBlockPos")
    BlockPos getLastBlockPos();

    @Accessor("lastBlockPos")
    void setLastBlockPos(final BlockPos p0);

    @Accessor("attacking")
    void setAttacking(final LivingEntity p0);
}
