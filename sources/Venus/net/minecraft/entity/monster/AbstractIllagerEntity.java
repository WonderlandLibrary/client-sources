/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.world.World;

public abstract class AbstractIllagerEntity
extends AbstractRaiderEntity {
    protected AbstractIllagerEntity(EntityType<? extends AbstractIllagerEntity> entityType, World world) {
        super((EntityType<? extends AbstractRaiderEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ILLAGER;
    }

    public ArmPose getArmPose() {
        return ArmPose.CROSSED;
    }

    public static enum ArmPose {
        CROSSED,
        ATTACKING,
        SPELLCASTING,
        BOW_AND_ARROW,
        CROSSBOW_HOLD,
        CROSSBOW_CHARGE,
        CELEBRATING,
        NEUTRAL;

    }

    public class RaidOpenDoorGoal
    extends OpenDoorGoal {
        final AbstractIllagerEntity this$0;

        public RaidOpenDoorGoal(AbstractIllagerEntity abstractIllagerEntity, AbstractRaiderEntity abstractRaiderEntity) {
            this.this$0 = abstractIllagerEntity;
            super(abstractRaiderEntity, false);
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.this$0.isRaidActive();
        }
    }
}

