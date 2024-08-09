/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AreaEffectCloudEntity
extends Entity {
    private static final Logger PRIVATE_LOGGER = LogManager.getLogger();
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(AreaEffectCloudEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(AreaEffectCloudEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.createKey(AreaEffectCloudEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<IParticleData> PARTICLE = EntityDataManager.createKey(AreaEffectCloudEntity.class, DataSerializers.PARTICLE_DATA);
    private Potion potion = Potions.EMPTY;
    private final List<EffectInstance> effects = Lists.newArrayList();
    private final Map<Entity, Integer> reapplicationDelayMap = Maps.newHashMap();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private boolean colorSet;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    private LivingEntity owner;
    private UUID ownerUniqueId;

    public AreaEffectCloudEntity(EntityType<? extends AreaEffectCloudEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        this.setRadius(3.0f);
    }

    public AreaEffectCloudEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends AreaEffectCloudEntity>)EntityType.AREA_EFFECT_CLOUD, world);
        this.setPosition(d, d2, d3);
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(COLOR, 0);
        this.getDataManager().register(RADIUS, Float.valueOf(0.5f));
        this.getDataManager().register(IGNORE_RADIUS, false);
        this.getDataManager().register(PARTICLE, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float f) {
        if (!this.world.isRemote) {
            this.getDataManager().set(RADIUS, Float.valueOf(f));
        }
    }

    @Override
    public void recalculateSize() {
        double d = this.getPosX();
        double d2 = this.getPosY();
        double d3 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d, d2, d3);
    }

    public float getRadius() {
        return this.getDataManager().get(RADIUS).floatValue();
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
        if (!this.colorSet) {
            this.updateFixedColor();
        }
    }

    private void updateFixedColor() {
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.getDataManager().set(COLOR, 0);
        } else {
            this.getDataManager().set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.effects)));
        }
    }

    public void addEffect(EffectInstance effectInstance) {
        this.effects.add(effectInstance);
        if (!this.colorSet) {
            this.updateFixedColor();
        }
    }

    public int getColor() {
        return this.getDataManager().get(COLOR);
    }

    public void setColor(int n) {
        this.colorSet = true;
        this.getDataManager().set(COLOR, n);
    }

    public IParticleData getParticleData() {
        return this.getDataManager().get(PARTICLE);
    }

    public void setParticleData(IParticleData iParticleData) {
        this.getDataManager().set(PARTICLE, iParticleData);
    }

    protected void setIgnoreRadius(boolean bl) {
        this.getDataManager().set(IGNORE_RADIUS, bl);
    }

    public boolean shouldIgnoreRadius() {
        return this.getDataManager().get(IGNORE_RADIUS);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int n) {
        this.duration = n;
    }

    @Override
    public void tick() {
        block23: {
            boolean bl;
            float f;
            boolean bl2;
            block21: {
                IParticleData iParticleData;
                block22: {
                    super.tick();
                    bl2 = this.shouldIgnoreRadius();
                    f = this.getRadius();
                    if (!this.world.isRemote) break block21;
                    iParticleData = this.getParticleData();
                    if (!bl2) break block22;
                    if (!this.rand.nextBoolean()) break block23;
                    for (int i = 0; i < 2; ++i) {
                        float f2 = this.rand.nextFloat() * ((float)Math.PI * 2);
                        float f3 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2f;
                        float f4 = MathHelper.cos(f2) * f3;
                        float f9 = MathHelper.sin(f2) * f3;
                        if (iParticleData.getType() == ParticleTypes.ENTITY_EFFECT) {
                            int f10 = this.rand.nextBoolean() ? 0xFFFFFF : this.getColor();
                            int n5 = f10 >> 16 & 0xFF;
                            int n6 = f10 >> 8 & 0xFF;
                            int n7 = f10 & 0xFF;
                            this.world.addOptionalParticle(iParticleData, this.getPosX() + (double)f4, this.getPosY(), this.getPosZ() + (double)f9, (float)n5 / 255.0f, (float)n6 / 255.0f, (float)n7 / 255.0f);
                            continue;
                        }
                        this.world.addOptionalParticle(iParticleData, this.getPosX() + (double)f4, this.getPosY(), this.getPosZ() + (double)f9, 0.0, 0.0, 0.0);
                    }
                    break block23;
                }
                float f6 = (float)Math.PI * f * f;
                int n = 0;
                while ((float)n < f6) {
                    float f7 = this.rand.nextFloat() * ((float)Math.PI * 2);
                    float f8 = MathHelper.sqrt(this.rand.nextFloat()) * f;
                    float livingEntity = MathHelper.cos(f7) * f8;
                    float d2 = MathHelper.sin(f7) * f8;
                    if (iParticleData.getType() == ParticleTypes.ENTITY_EFFECT) {
                        int n2 = this.getColor();
                        int d = n2 >> 16 & 0xFF;
                        int n3 = n2 >> 8 & 0xFF;
                        int d3 = n2 & 0xFF;
                        this.world.addOptionalParticle(iParticleData, this.getPosX() + (double)livingEntity, this.getPosY(), this.getPosZ() + (double)d2, (float)d / 255.0f, (float)n3 / 255.0f, (float)d3 / 255.0f);
                    } else {
                        this.world.addOptionalParticle(iParticleData, this.getPosX() + (double)livingEntity, this.getPosY(), this.getPosZ() + (double)d2, (0.5 - this.rand.nextDouble()) * 0.15, 0.01f, (0.5 - this.rand.nextDouble()) * 0.15);
                    }
                    ++n;
                }
                break block23;
            }
            if (this.ticksExisted >= this.waitTime + this.duration) {
                this.remove();
                return;
            }
            boolean bl3 = bl = this.ticksExisted < this.waitTime;
            if (bl2 != bl) {
                this.setIgnoreRadius(bl);
            }
            if (bl) {
                return;
            }
            if (this.radiusPerTick != 0.0f) {
                if ((f += this.radiusPerTick) < 0.5f) {
                    this.remove();
                    return;
                }
                this.setRadius(f);
            }
            if (this.ticksExisted % 5 == 0) {
                Map.Entry<Entity, Integer> entry;
                Iterator<Map.Entry<Entity, Integer>> iterator2 = this.reapplicationDelayMap.entrySet().iterator();
                while (iterator2.hasNext()) {
                    entry = iterator2.next();
                    if (this.ticksExisted < (Integer)entry.getValue()) continue;
                    iterator2.remove();
                }
                entry = Lists.newArrayList();
                for (EffectInstance object : this.potion.getEffects()) {
                    entry.add(new EffectInstance(object.getPotion(), object.getDuration() / 4, object.getAmplifier(), object.isAmbient(), object.doesShowParticles()));
                }
                entry.addAll(this.effects);
                if (entry.isEmpty()) {
                    this.reapplicationDelayMap.clear();
                } else {
                    List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox());
                    if (!list.isEmpty()) {
                        for (LivingEntity livingEntity : list) {
                            double d;
                            double d2;
                            double d3;
                            if (this.reapplicationDelayMap.containsKey(livingEntity) || !livingEntity.canBeHitWithPotion() || !((d3 = (d2 = livingEntity.getPosX() - this.getPosX()) * d2 + (d = livingEntity.getPosZ() - this.getPosZ()) * d) <= (double)(f * f))) continue;
                            this.reapplicationDelayMap.put(livingEntity, this.ticksExisted + this.reapplicationDelay);
                            Iterator iterator3 = entry.iterator();
                            while (iterator3.hasNext()) {
                                EffectInstance effectInstance = (EffectInstance)iterator3.next();
                                if (effectInstance.getPotion().isInstant()) {
                                    effectInstance.getPotion().affectEntity(this, this.getOwner(), livingEntity, effectInstance.getAmplifier(), 0.5);
                                    continue;
                                }
                                livingEntity.addPotionEffect(new EffectInstance(effectInstance));
                            }
                            if (this.radiusOnUse != 0.0f) {
                                if ((f += this.radiusOnUse) < 0.5f) {
                                    this.remove();
                                    return;
                                }
                                this.setRadius(f);
                            }
                            if (this.durationOnUse == 0) continue;
                            this.duration += this.durationOnUse;
                            if (this.duration > 0) continue;
                            this.remove();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void setRadiusOnUse(float f) {
        this.radiusOnUse = f;
    }

    public void setRadiusPerTick(float f) {
        this.radiusPerTick = f;
    }

    public void setWaitTime(int n) {
        this.waitTime = n;
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUniqueId = livingEntity == null ? null : livingEntity.getUniqueID();
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity2;
        if (this.owner == null && this.ownerUniqueId != null && this.world instanceof ServerWorld && (entity2 = ((ServerWorld)this.world).getEntityByUuid(this.ownerUniqueId)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity2;
        }
        return this.owner;
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        this.ticksExisted = compoundNBT.getInt("Age");
        this.duration = compoundNBT.getInt("Duration");
        this.waitTime = compoundNBT.getInt("WaitTime");
        this.reapplicationDelay = compoundNBT.getInt("ReapplicationDelay");
        this.durationOnUse = compoundNBT.getInt("DurationOnUse");
        this.radiusOnUse = compoundNBT.getFloat("RadiusOnUse");
        this.radiusPerTick = compoundNBT.getFloat("RadiusPerTick");
        this.setRadius(compoundNBT.getFloat("Radius"));
        if (compoundNBT.hasUniqueId("Owner")) {
            this.ownerUniqueId = compoundNBT.getUniqueId("Owner");
        }
        if (compoundNBT.contains("Particle", 1)) {
            try {
                this.setParticleData(ParticleArgument.parseParticle(new StringReader(compoundNBT.getString("Particle"))));
            } catch (CommandSyntaxException commandSyntaxException) {
                PRIVATE_LOGGER.warn("Couldn't load custom particle {}", (Object)compoundNBT.getString("Particle"), (Object)commandSyntaxException);
            }
        }
        if (compoundNBT.contains("Color", 0)) {
            this.setColor(compoundNBT.getInt("Color"));
        }
        if (compoundNBT.contains("Potion", 1)) {
            this.setPotion(PotionUtils.getPotionTypeFromNBT(compoundNBT));
        }
        if (compoundNBT.contains("Effects", 0)) {
            ListNBT listNBT = compoundNBT.getList("Effects", 10);
            this.effects.clear();
            for (int i = 0; i < listNBT.size(); ++i) {
                EffectInstance effectInstance = EffectInstance.read(listNBT.getCompound(i));
                if (effectInstance == null) continue;
                this.addEffect(effectInstance);
            }
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Age", this.ticksExisted);
        compoundNBT.putInt("Duration", this.duration);
        compoundNBT.putInt("WaitTime", this.waitTime);
        compoundNBT.putInt("ReapplicationDelay", this.reapplicationDelay);
        compoundNBT.putInt("DurationOnUse", this.durationOnUse);
        compoundNBT.putFloat("RadiusOnUse", this.radiusOnUse);
        compoundNBT.putFloat("RadiusPerTick", this.radiusPerTick);
        compoundNBT.putFloat("Radius", this.getRadius());
        compoundNBT.putString("Particle", this.getParticleData().getParameters());
        if (this.ownerUniqueId != null) {
            compoundNBT.putUniqueId("Owner", this.ownerUniqueId);
        }
        if (this.colorSet) {
            compoundNBT.putInt("Color", this.getColor());
        }
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compoundNBT.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }
        if (!this.effects.isEmpty()) {
            ListNBT listNBT = new ListNBT();
            for (EffectInstance effectInstance : this.effects) {
                listNBT.add(effectInstance.write(new CompoundNBT()));
            }
            compoundNBT.put("Effects", listNBT);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (RADIUS.equals(dataParameter)) {
            this.recalculateSize();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public PushReaction getPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return EntitySize.flexible(this.getRadius() * 2.0f, 0.5f);
    }
}

