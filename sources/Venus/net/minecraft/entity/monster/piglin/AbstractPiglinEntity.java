/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractPiglinEntity
extends MonsterEntity {
    protected static final DataParameter<Boolean> field_242333_b = EntityDataManager.createKey(AbstractPiglinEntity.class, DataSerializers.BOOLEAN);
    protected int field_242334_c = 0;

    public AbstractPiglinEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.setCanPickUpLoot(false);
        this.func_242339_eS();
        this.setPathPriority(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    private void func_242339_eS() {
        if (GroundPathHelper.isGroundNavigator(this)) {
            ((GroundPathNavigator)this.getNavigator()).setBreakDoors(false);
        }
    }

    protected abstract boolean func_234422_eK_();

    public void func_242340_t(boolean bl) {
        this.getDataManager().set(field_242333_b, bl);
    }

    protected boolean func_242335_eK() {
        return this.getDataManager().get(field_242333_b);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_242333_b, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.func_242335_eK()) {
            compoundNBT.putBoolean("IsImmuneToZombification", false);
        }
        compoundNBT.putInt("TimeInOverworld", this.field_242334_c);
    }

    @Override
    public double getYOffset() {
        return this.isChild() ? -0.05 : -0.45;
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.func_242340_t(compoundNBT.getBoolean("IsImmuneToZombification"));
        this.field_242334_c = compoundNBT.getInt("TimeInOverworld");
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.field_242334_c = this.func_242336_eL() ? ++this.field_242334_c : 0;
        if (this.field_242334_c > 300) {
            this.func_241848_eP();
            this.func_234416_a_((ServerWorld)this.world);
        }
    }

    public boolean func_242336_eL() {
        return !this.world.getDimensionType().isPiglinSafe() && !this.func_242335_eK() && !this.isAIDisabled();
    }

    protected void func_234416_a_(ServerWorld serverWorld) {
        ZombifiedPiglinEntity zombifiedPiglinEntity = this.func_233656_b_(EntityType.ZOMBIFIED_PIGLIN, false);
        if (zombifiedPiglinEntity != null) {
            zombifiedPiglinEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
        }
    }

    public boolean func_242337_eM() {
        return !this.isChild();
    }

    public abstract PiglinAction func_234424_eM_();

    @Override
    @Nullable
    public LivingEntity getAttackTarget() {
        return this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    protected boolean func_242338_eO() {
        return this.getHeldItemMainhand().getItem() instanceof TieredItem;
    }

    @Override
    public void playAmbientSound() {
        if (PiglinTasks.func_234520_i_(this)) {
            super.playAmbientSound();
        }
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendLivingEntity(this);
    }

    protected abstract void func_241848_eP();
}

