/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ElderGuardianEntity
extends GuardianEntity {
    public static final float field_213629_b = EntityType.ELDER_GUARDIAN.getWidth() / EntityType.GUARDIAN.getWidth();

    public ElderGuardianEntity(EntityType<? extends ElderGuardianEntity> entityType, World world) {
        super((EntityType<? extends GuardianEntity>)entityType, world);
        this.enablePersistence();
        if (this.wander != null) {
            this.wander.setExecutionChance(400);
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234283_m_() {
        return GuardianEntity.func_234292_eK_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0).createMutableAttribute(Attributes.MAX_HEALTH, 80.0);
    }

    @Override
    public int getAttackDuration() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT : SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT_LAND;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_HURT : SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH : SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        int n = 1200;
        if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
            Effect effect = Effects.MINING_FATIGUE;
            List<ServerPlayerEntity> list = ((ServerWorld)this.world).getPlayers(this::lambda$updateAITasks$0);
            int n2 = 2;
            int n3 = 6000;
            int n4 = 1200;
            for (ServerPlayerEntity serverPlayerEntity : list) {
                if (serverPlayerEntity.isPotionActive(effect) && serverPlayerEntity.getActivePotionEffect(effect).getAmplifier() >= 2 && serverPlayerEntity.getActivePotionEffect(effect).getDuration() >= 1200) continue;
                serverPlayerEntity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241774_k_, this.isSilent() ? 0.0f : 1.0f));
                serverPlayerEntity.addPotionEffect(new EffectInstance(effect, 6000, 2));
            }
        }
        if (!this.detachHome()) {
            this.setHomePosAndDistance(this.getPosition(), 16);
        }
    }

    private boolean lambda$updateAITasks$0(ServerPlayerEntity serverPlayerEntity) {
        return this.getDistanceSq(serverPlayerEntity) < 2500.0 && serverPlayerEntity.interactionManager.survivalOrAdventure();
    }
}

