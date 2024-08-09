/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class SpellcastingIllagerEntity
extends AbstractIllagerEntity {
    private static final DataParameter<Byte> SPELL = EntityDataManager.createKey(SpellcastingIllagerEntity.class, DataSerializers.BYTE);
    protected int spellTicks;
    private SpellType activeSpell = SpellType.NONE;

    protected SpellcastingIllagerEntity(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super((EntityType<? extends AbstractIllagerEntity>)entityType, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SPELL, (byte)0);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.spellTicks = compoundNBT.getInt("SpellTicks");
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("SpellTicks", this.spellTicks);
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isSpellcasting()) {
            return AbstractIllagerEntity.ArmPose.SPELLCASTING;
        }
        return this.getCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : AbstractIllagerEntity.ArmPose.CROSSED;
    }

    public boolean isSpellcasting() {
        if (this.world.isRemote) {
            return this.dataManager.get(SPELL) > 0;
        }
        return this.spellTicks > 0;
    }

    public void setSpellType(SpellType spellType) {
        this.activeSpell = spellType;
        this.dataManager.set(SPELL, (byte)spellType.id);
    }

    protected SpellType getSpellType() {
        return !this.world.isRemote ? this.activeSpell : SpellType.getFromId(this.dataManager.get(SPELL).byteValue());
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote && this.isSpellcasting()) {
            SpellType spellType = this.getSpellType();
            double d = spellType.particleSpeed[0];
            double d2 = spellType.particleSpeed[1];
            double d3 = spellType.particleSpeed[2];
            float f = this.renderYawOffset * ((float)Math.PI / 180) + MathHelper.cos((float)this.ticksExisted * 0.6662f) * 0.25f;
            float f2 = MathHelper.cos(f);
            float f3 = MathHelper.sin(f);
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosX() + (double)f2 * 0.6, this.getPosY() + 1.8, this.getPosZ() + (double)f3 * 0.6, d, d2, d3);
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosX() - (double)f2 * 0.6, this.getPosY() + 1.8, this.getPosZ() - (double)f3 * 0.6, d, d2, d3);
        }
    }

    protected int getSpellTicks() {
        return this.spellTicks;
    }

    protected abstract SoundEvent getSpellSound();

    static PathNavigator access$000(SpellcastingIllagerEntity spellcastingIllagerEntity) {
        return spellcastingIllagerEntity.navigator;
    }

    public static enum SpellType {
        NONE(0, 0.0, 0.0, 0.0),
        SUMMON_VEX(1, 0.7, 0.7, 0.8),
        FANGS(2, 0.4, 0.3, 0.35),
        WOLOLO(3, 0.7, 0.5, 0.2),
        DISAPPEAR(4, 0.3, 0.3, 0.8),
        BLINDNESS(5, 0.1, 0.1, 0.2);

        private final int id;
        private final double[] particleSpeed;

        private SpellType(int n2, double d, double d2, double d3) {
            this.id = n2;
            this.particleSpeed = new double[]{d, d2, d3};
        }

        public static SpellType getFromId(int n) {
            for (SpellType spellType : SpellType.values()) {
                if (n != spellType.id) continue;
                return spellType;
            }
            return NONE;
        }
    }

    public abstract class UseSpellGoal
    extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;
        final SpellcastingIllagerEntity this$0;

        protected UseSpellGoal(SpellcastingIllagerEntity spellcastingIllagerEntity) {
            this.this$0 = spellcastingIllagerEntity;
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                if (this.this$0.isSpellcasting()) {
                    return true;
                }
                return this.this$0.ticksExisted >= this.spellCooldown;
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            return livingEntity != null && livingEntity.isAlive() && this.spellWarmup > 0;
        }

        @Override
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            this.this$0.spellTicks = this.getCastingTime();
            this.spellCooldown = this.this$0.ticksExisted + this.getCastingInterval();
            SoundEvent soundEvent = this.getSpellPrepareSound();
            if (soundEvent != null) {
                this.this$0.playSound(soundEvent, 1.0f, 1.0f);
            }
            this.this$0.setSpellType(this.getSpellType());
        }

        @Override
        public void tick() {
            --this.spellWarmup;
            if (this.spellWarmup == 0) {
                this.castSpell();
                this.this$0.playSound(this.this$0.getSpellSound(), 1.0f, 1.0f);
            }
        }

        protected abstract void castSpell();

        protected int getCastWarmupTime() {
            return 1;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();

        protected abstract SpellType getSpellType();
    }

    public class CastingASpellGoal
    extends Goal {
        final SpellcastingIllagerEntity this$0;

        public CastingASpellGoal(SpellcastingIllagerEntity spellcastingIllagerEntity) {
            this.this$0 = spellcastingIllagerEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getSpellTicks() > 0;
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            SpellcastingIllagerEntity.access$000(this.this$0).clearPath();
        }

        @Override
        public void resetTask() {
            super.resetTask();
            this.this$0.setSpellType(SpellType.NONE);
        }

        @Override
        public void tick() {
            if (this.this$0.getAttackTarget() != null) {
                this.this$0.getLookController().setLookPositionWithEntity(this.this$0.getAttackTarget(), this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
            }
        }
    }
}

