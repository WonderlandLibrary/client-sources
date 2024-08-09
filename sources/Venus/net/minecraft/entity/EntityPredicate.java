/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class EntityPredicate {
    public static final EntityPredicate DEFAULT = new EntityPredicate();
    private double distance = -1.0;
    private boolean allowInvulnerable;
    private boolean friendlyFire;
    private boolean requireLineOfSight;
    private boolean skipAttackChecks;
    private boolean useVisibilityModifier = true;
    private Predicate<LivingEntity> customPredicate;

    public EntityPredicate setDistance(double d) {
        this.distance = d;
        return this;
    }

    public EntityPredicate allowInvulnerable() {
        this.allowInvulnerable = true;
        return this;
    }

    public EntityPredicate allowFriendlyFire() {
        this.friendlyFire = true;
        return this;
    }

    public EntityPredicate setLineOfSiteRequired() {
        this.requireLineOfSight = true;
        return this;
    }

    public EntityPredicate setSkipAttackChecks() {
        this.skipAttackChecks = true;
        return this;
    }

    public EntityPredicate setUseInvisibilityCheck() {
        this.useVisibilityModifier = false;
        return this;
    }

    public EntityPredicate setCustomPredicate(@Nullable Predicate<LivingEntity> predicate) {
        this.customPredicate = predicate;
        return this;
    }

    public boolean canTarget(@Nullable LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (livingEntity == livingEntity2) {
            return true;
        }
        if (livingEntity2.isSpectator()) {
            return true;
        }
        if (!livingEntity2.isAlive()) {
            return true;
        }
        if (!this.allowInvulnerable && livingEntity2.isInvulnerable()) {
            return true;
        }
        if (this.customPredicate != null && !this.customPredicate.test(livingEntity2)) {
            return true;
        }
        if (livingEntity != null) {
            if (!this.skipAttackChecks) {
                if (!livingEntity.canAttack(livingEntity2)) {
                    return true;
                }
                if (!livingEntity.canAttack(livingEntity2.getType())) {
                    return true;
                }
            }
            if (!this.friendlyFire && livingEntity.isOnSameTeam(livingEntity2)) {
                return true;
            }
            if (this.distance > 0.0) {
                double d = this.useVisibilityModifier ? livingEntity2.getVisibilityMultiplier(livingEntity) : 1.0;
                double d2 = Math.max(this.distance * d, 2.0);
                double d3 = livingEntity.getDistanceSq(livingEntity2.getPosX(), livingEntity2.getPosY(), livingEntity2.getPosZ());
                if (d3 > d2 * d2) {
                    return true;
                }
            }
            if (!this.requireLineOfSight && livingEntity instanceof MobEntity && !((MobEntity)livingEntity).getEntitySenses().canSee(livingEntity2)) {
                return true;
            }
        }
        return false;
    }
}

