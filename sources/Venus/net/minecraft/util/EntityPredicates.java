/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.Difficulty;

public final class EntityPredicates {
    public static final Predicate<Entity> IS_ALIVE = Entity::isAlive;
    public static final Predicate<LivingEntity> IS_LIVING_ALIVE = LivingEntity::isAlive;
    public static final Predicate<Entity> IS_STANDALONE = EntityPredicates::lambda$static$0;
    public static final Predicate<Entity> HAS_INVENTORY = EntityPredicates::lambda$static$1;
    public static final Predicate<Entity> CAN_AI_TARGET = EntityPredicates::lambda$static$2;
    public static final Predicate<Entity> CAN_HOSTILE_AI_TARGET = EntityPredicates::lambda$static$3;
    public static final Predicate<Entity> NOT_SPECTATING = EntityPredicates::lambda$static$4;

    public static Predicate<Entity> withinRange(double d, double d2, double d3, double d4) {
        double d5 = d4 * d4;
        return arg_0 -> EntityPredicates.lambda$withinRange$5(d, d2, d3, d5, arg_0);
    }

    public static Predicate<Entity> pushableBy(Entity entity2) {
        Team team = entity2.getTeam();
        Team.CollisionRule collisionRule = team == null ? Team.CollisionRule.ALWAYS : team.getCollisionRule();
        return collisionRule == Team.CollisionRule.NEVER ? Predicates.alwaysFalse() : NOT_SPECTATING.and(arg_0 -> EntityPredicates.lambda$pushableBy$6(entity2, team, collisionRule, arg_0));
    }

    public static Predicate<Entity> notRiding(Entity entity2) {
        return arg_0 -> EntityPredicates.lambda$notRiding$7(entity2, arg_0);
    }

    private static boolean lambda$notRiding$7(Entity entity2, Entity entity3) {
        while (entity3.isPassenger()) {
            if ((entity3 = entity3.getRidingEntity()) != entity2) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$pushableBy$6(Entity entity2, Team team, Team.CollisionRule collisionRule, Entity entity3) {
        if (!entity3.canBePushed()) {
            return true;
        }
        if (!entity2.world.isRemote || entity3 instanceof PlayerEntity && ((PlayerEntity)entity3).isUser()) {
            boolean bl;
            Team.CollisionRule collisionRule2;
            Team team2 = entity3.getTeam();
            Team.CollisionRule collisionRule3 = collisionRule2 = team2 == null ? Team.CollisionRule.ALWAYS : team2.getCollisionRule();
            if (collisionRule2 == Team.CollisionRule.NEVER) {
                return true;
            }
            boolean bl2 = bl = team != null && team.isSameTeam(team2);
            if ((collisionRule == Team.CollisionRule.PUSH_OWN_TEAM || collisionRule2 == Team.CollisionRule.PUSH_OWN_TEAM) && bl) {
                return true;
            }
            return collisionRule != Team.CollisionRule.PUSH_OTHER_TEAMS && collisionRule2 != Team.CollisionRule.PUSH_OTHER_TEAMS || bl;
        }
        return true;
    }

    private static boolean lambda$withinRange$5(double d, double d2, double d3, double d4, Entity entity2) {
        return entity2 != null && entity2.getDistanceSq(d, d2, d3) <= d4;
    }

    private static boolean lambda$static$4(Entity entity2) {
        return !entity2.isSpectator();
    }

    private static boolean lambda$static$3(Entity entity2) {
        return !(entity2 instanceof PlayerEntity) || !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative() && entity2.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    private static boolean lambda$static$2(Entity entity2) {
        return !(entity2 instanceof PlayerEntity) || !entity2.isSpectator() && !((PlayerEntity)entity2).isCreative();
    }

    private static boolean lambda$static$1(Entity entity2) {
        return entity2 instanceof IInventory && entity2.isAlive();
    }

    private static boolean lambda$static$0(Entity entity2) {
        return entity2.isAlive() && !entity2.isBeingRidden() && !entity2.isPassenger();
    }

    public static class ArmoredMob
    implements Predicate<Entity> {
        private final ItemStack armor;

        public ArmoredMob(ItemStack itemStack) {
            this.armor = itemStack;
        }

        @Override
        public boolean test(@Nullable Entity entity2) {
            if (!entity2.isAlive()) {
                return true;
            }
            if (!(entity2 instanceof LivingEntity)) {
                return true;
            }
            LivingEntity livingEntity = (LivingEntity)entity2;
            return livingEntity.canPickUpItem(this.armor);
        }

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((Entity)object);
        }
    }
}

