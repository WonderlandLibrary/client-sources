/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.raid.RaidManager;
import net.minecraft.world.server.ServerWorld;

public class MoveTowardsRaidGoal<T extends AbstractRaiderEntity>
extends Goal {
    private final T raider;

    public MoveTowardsRaidGoal(T t) {
        this.raider = t;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return ((MobEntity)this.raider).getAttackTarget() == null && !((Entity)this.raider).isBeingRidden() && ((AbstractRaiderEntity)this.raider).isRaidActive() && !((AbstractRaiderEntity)this.raider).getRaid().isOver() && !((ServerWorld)((AbstractRaiderEntity)this.raider).world).isVillage(((Entity)this.raider).getPosition());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return ((AbstractRaiderEntity)this.raider).isRaidActive() && !((AbstractRaiderEntity)this.raider).getRaid().isOver() && ((AbstractRaiderEntity)this.raider).world instanceof ServerWorld && !((ServerWorld)((AbstractRaiderEntity)this.raider).world).isVillage(((Entity)this.raider).getPosition());
    }

    @Override
    public void tick() {
        if (((AbstractRaiderEntity)this.raider).isRaidActive()) {
            Vector3d vector3d;
            Raid raid = ((AbstractRaiderEntity)this.raider).getRaid();
            if (((AbstractRaiderEntity)this.raider).ticksExisted % 20 == 0) {
                this.func_220743_a(raid);
            }
            if (!((CreatureEntity)this.raider).hasPath() && (vector3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.raider, 15, 4, Vector3d.copyCenteredHorizontally(raid.getCenter()))) != null) {
                ((MobEntity)this.raider).getNavigator().tryMoveToXYZ(vector3d.x, vector3d.y, vector3d.z, 1.0);
            }
        }
    }

    private void func_220743_a(Raid raid) {
        if (raid.isActive()) {
            HashSet<AbstractRaiderEntity> hashSet = Sets.newHashSet();
            List<AbstractRaiderEntity> list = ((AbstractRaiderEntity)this.raider).world.getEntitiesWithinAABB(AbstractRaiderEntity.class, ((Entity)this.raider).getBoundingBox().grow(16.0), arg_0 -> MoveTowardsRaidGoal.lambda$func_220743_a$0(raid, arg_0));
            hashSet.addAll(list);
            for (AbstractRaiderEntity abstractRaiderEntity : hashSet) {
                raid.joinRaid(raid.getGroupsSpawned(), abstractRaiderEntity, null, false);
            }
        }
    }

    private static boolean lambda$func_220743_a$0(Raid raid, AbstractRaiderEntity abstractRaiderEntity) {
        return !abstractRaiderEntity.isRaidActive() && RaidManager.canJoinRaid(abstractRaiderEntity, raid);
    }
}

