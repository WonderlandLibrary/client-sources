/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonEntity
extends MobEntity
implements IMob {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(EnderDragonEntity.class, DataSerializers.VARINT);
    private static final EntityPredicate PLAYER_INVADER_CONDITION = new EntityPredicate().setDistance(64.0);
    public final double[][] ringBuffer = new double[64][3];
    public int ringBufferIndex = -1;
    private final EnderDragonPartEntity[] dragonParts;
    public final EnderDragonPartEntity dragonPartHead;
    private final EnderDragonPartEntity dragonPartNeck;
    private final EnderDragonPartEntity dragonPartBody;
    private final EnderDragonPartEntity dragonPartTail1;
    private final EnderDragonPartEntity dragonPartTail2;
    private final EnderDragonPartEntity dragonPartTail3;
    private final EnderDragonPartEntity dragonPartRightWing;
    private final EnderDragonPartEntity dragonPartLeftWing;
    public float prevAnimTime;
    public float animTime;
    public boolean slowed;
    public int deathTicks;
    public float field_226525_bB_;
    @Nullable
    public EnderCrystalEntity closestEnderCrystal;
    @Nullable
    private final DragonFightManager fightManager;
    private final PhaseManager phaseManager;
    private int growlTime = 100;
    private int sittingDamageReceived;
    private final PathPoint[] pathPoints = new PathPoint[24];
    private final int[] neighbors = new int[24];
    private final PathHeap pathFindQueue = new PathHeap();

    public EnderDragonEntity(EntityType<? extends EnderDragonEntity> entityType, World world) {
        super((EntityType<? extends MobEntity>)EntityType.ENDER_DRAGON, world);
        this.dragonPartHead = new EnderDragonPartEntity(this, "head", 1.0f, 1.0f);
        this.dragonPartNeck = new EnderDragonPartEntity(this, "neck", 3.0f, 3.0f);
        this.dragonPartBody = new EnderDragonPartEntity(this, "body", 5.0f, 3.0f);
        this.dragonPartTail1 = new EnderDragonPartEntity(this, "tail", 2.0f, 2.0f);
        this.dragonPartTail2 = new EnderDragonPartEntity(this, "tail", 2.0f, 2.0f);
        this.dragonPartTail3 = new EnderDragonPartEntity(this, "tail", 2.0f, 2.0f);
        this.dragonPartRightWing = new EnderDragonPartEntity(this, "wing", 4.0f, 2.0f);
        this.dragonPartLeftWing = new EnderDragonPartEntity(this, "wing", 4.0f, 2.0f);
        this.dragonParts = new EnderDragonPartEntity[]{this.dragonPartHead, this.dragonPartNeck, this.dragonPartBody, this.dragonPartTail1, this.dragonPartTail2, this.dragonPartTail3, this.dragonPartRightWing, this.dragonPartLeftWing};
        this.setHealth(this.getMaxHealth());
        this.noClip = true;
        this.ignoreFrustumCheck = true;
        this.fightManager = world instanceof ServerWorld ? ((ServerWorld)world).func_241110_C_() : null;
        this.phaseManager = new PhaseManager(this);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 200.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(PHASE, PhaseType.HOVER.getId());
    }

    public double[] getMovementOffsets(int n, float f) {
        if (this.getShouldBeDead()) {
            f = 0.0f;
        }
        f = 1.0f - f;
        int n2 = this.ringBufferIndex - n & 0x3F;
        int n3 = this.ringBufferIndex - n - 1 & 0x3F;
        double[] dArray = new double[3];
        double d = this.ringBuffer[n2][0];
        double d2 = MathHelper.wrapDegrees(this.ringBuffer[n3][0] - d);
        dArray[0] = d + d2 * (double)f;
        d = this.ringBuffer[n2][1];
        d2 = this.ringBuffer[n3][1] - d;
        dArray[1] = d + d2 * (double)f;
        dArray[2] = MathHelper.lerp((double)f, this.ringBuffer[n2][2], this.ringBuffer[n3][2]);
        return dArray;
    }

    @Override
    public void livingTick() {
        float f;
        float f2;
        if (this.world.isRemote) {
            this.setHealth(this.getHealth());
            if (!this.isSilent()) {
                f2 = MathHelper.cos(this.animTime * ((float)Math.PI * 2));
                f = MathHelper.cos(this.prevAnimTime * ((float)Math.PI * 2));
                if (f <= -0.3f && f2 >= -0.3f) {
                    this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 5.0f, 0.8f + this.rand.nextFloat() * 0.3f, true);
                }
                if (!this.phaseManager.getCurrentPhase().getIsStationary() && --this.growlTime < 0) {
                    this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.getSoundCategory(), 2.5f, 0.8f + this.rand.nextFloat() * 0.3f, true);
                    this.growlTime = 200 + this.rand.nextInt(200);
                }
            }
        }
        this.prevAnimTime = this.animTime;
        if (this.getShouldBeDead()) {
            f2 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            f = (this.rand.nextFloat() - 0.5f) * 4.0f;
            float f3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX() + (double)f2, this.getPosY() + 2.0 + (double)f, this.getPosZ() + (double)f3, 0.0, 0.0, 0.0);
        } else {
            this.updateDragonEnderCrystal();
            Vector3d vector3d = this.getMotion();
            f = 0.2f / (MathHelper.sqrt(EnderDragonEntity.horizontalMag(vector3d)) * 10.0f + 1.0f);
            this.animTime = this.phaseManager.getCurrentPhase().getIsStationary() ? (this.animTime += 0.1f) : (this.slowed ? (this.animTime += f * 0.5f) : (this.animTime += (f *= (float)Math.pow(2.0, vector3d.y))));
            this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
            if (this.isAIDisabled()) {
                this.animTime = 0.5f;
            } else {
                int n;
                float f4;
                float f5;
                if (this.ringBufferIndex < 0) {
                    for (int i = 0; i < this.ringBuffer.length; ++i) {
                        this.ringBuffer[i][0] = this.rotationYaw;
                        this.ringBuffer[i][1] = this.getPosY();
                    }
                }
                if (++this.ringBufferIndex == this.ringBuffer.length) {
                    this.ringBufferIndex = 0;
                }
                this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
                this.ringBuffer[this.ringBufferIndex][1] = this.getPosY();
                if (this.world.isRemote) {
                    if (this.newPosRotationIncrements > 0) {
                        double d = this.getPosX() + (this.interpTargetX - this.getPosX()) / (double)this.newPosRotationIncrements;
                        var5_9 = this.getPosY() + (this.interpTargetY - this.getPosY()) / (double)this.newPosRotationIncrements;
                        var7_11 = this.getPosZ() + (this.interpTargetZ - this.getPosZ()) / (double)this.newPosRotationIncrements;
                        var9_13 = MathHelper.wrapDegrees(this.interpTargetYaw - (double)this.rotationYaw);
                        this.rotationYaw = (float)((double)this.rotationYaw + var9_13 / (double)this.newPosRotationIncrements);
                        this.rotationPitch = (float)((double)this.rotationPitch + (this.interpTargetPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
                        --this.newPosRotationIncrements;
                        this.setPosition(d, var5_9, var7_11);
                        this.setRotation(this.rotationYaw, this.rotationPitch);
                    }
                    this.phaseManager.getCurrentPhase().clientTick();
                } else {
                    Vector3d vector3d2;
                    IPhase iPhase = this.phaseManager.getCurrentPhase();
                    iPhase.serverTick();
                    if (this.phaseManager.getCurrentPhase() != iPhase) {
                        iPhase = this.phaseManager.getCurrentPhase();
                        iPhase.serverTick();
                    }
                    if ((vector3d2 = iPhase.getTargetLocation()) != null) {
                        var5_9 = vector3d2.x - this.getPosX();
                        var7_11 = vector3d2.y - this.getPosY();
                        var9_13 = vector3d2.z - this.getPosZ();
                        double d = var5_9 * var5_9 + var7_11 * var7_11 + var9_13 * var9_13;
                        float f6 = iPhase.getMaxRiseOrFall();
                        double d2 = MathHelper.sqrt(var5_9 * var5_9 + var9_13 * var9_13);
                        if (d2 > 0.0) {
                            var7_11 = MathHelper.clamp(var7_11 / d2, (double)(-f6), (double)f6);
                        }
                        this.setMotion(this.getMotion().add(0.0, var7_11 * 0.01, 0.0));
                        this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
                        double d3 = MathHelper.clamp(MathHelper.wrapDegrees(180.0 - MathHelper.atan2(var5_9, var9_13) * 57.2957763671875 - (double)this.rotationYaw), -50.0, 50.0);
                        Vector3d vector3d3 = vector3d2.subtract(this.getPosX(), this.getPosY(), this.getPosZ()).normalize();
                        Vector3d vector3d4 = new Vector3d(MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)), this.getMotion().y, -MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180))).normalize();
                        f5 = Math.max(((float)vector3d4.dotProduct(vector3d3) + 0.5f) / 1.5f, 0.0f);
                        this.field_226525_bB_ *= 0.8f;
                        this.field_226525_bB_ = (float)((double)this.field_226525_bB_ + d3 * (double)iPhase.getYawFactor());
                        this.rotationYaw += this.field_226525_bB_ * 0.1f;
                        f4 = (float)(2.0 / (d + 1.0));
                        float f7 = 0.06f;
                        this.moveRelative(0.06f * (f5 * f4 + (1.0f - f4)), new Vector3d(0.0, 0.0, -1.0));
                        if (this.slowed) {
                            this.move(MoverType.SELF, this.getMotion().scale(0.8f));
                        } else {
                            this.move(MoverType.SELF, this.getMotion());
                        }
                        Vector3d vector3d5 = this.getMotion().normalize();
                        double d4 = 0.8 + 0.15 * (vector3d5.dotProduct(vector3d4) + 1.0) / 2.0;
                        this.setMotion(this.getMotion().mul(d4, 0.91f, d4));
                    }
                }
                this.renderYawOffset = this.rotationYaw;
                Vector3d[] vector3dArray = new Vector3d[this.dragonParts.length];
                for (int i = 0; i < this.dragonParts.length; ++i) {
                    vector3dArray[i] = new Vector3d(this.dragonParts[i].getPosX(), this.dragonParts[i].getPosY(), this.dragonParts[i].getPosZ());
                }
                float f8 = (float)(this.getMovementOffsets(5, 1.0f)[1] - this.getMovementOffsets(10, 1.0f)[1]) * 10.0f * ((float)Math.PI / 180);
                float f9 = MathHelper.cos(f8);
                float f10 = MathHelper.sin(f8);
                float f11 = this.rotationYaw * ((float)Math.PI / 180);
                float f12 = MathHelper.sin(f11);
                float f13 = MathHelper.cos(f11);
                this.setPartPosition(this.dragonPartBody, f12 * 0.5f, 0.0, -f13 * 0.5f);
                this.setPartPosition(this.dragonPartRightWing, f13 * 4.5f, 2.0, f12 * 4.5f);
                this.setPartPosition(this.dragonPartLeftWing, f13 * -4.5f, 2.0, f12 * -4.5f);
                if (!this.world.isRemote && this.hurtTime == 0) {
                    this.collideWithEntities(this.world.getEntitiesInAABBexcluding(this, this.dragonPartRightWing.getBoundingBox().grow(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.CAN_AI_TARGET));
                    this.collideWithEntities(this.world.getEntitiesInAABBexcluding(this, this.dragonPartLeftWing.getBoundingBox().grow(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.CAN_AI_TARGET));
                    this.attackEntitiesInList(this.world.getEntitiesInAABBexcluding(this, this.dragonPartHead.getBoundingBox().grow(1.0), EntityPredicates.CAN_AI_TARGET));
                    this.attackEntitiesInList(this.world.getEntitiesInAABBexcluding(this, this.dragonPartNeck.getBoundingBox().grow(1.0), EntityPredicates.CAN_AI_TARGET));
                }
                float f14 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180) - this.field_226525_bB_ * 0.01f);
                float f15 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180) - this.field_226525_bB_ * 0.01f);
                float f16 = this.getHeadAndNeckYOffset();
                this.setPartPosition(this.dragonPartHead, f14 * 6.5f * f9, f16 + f10 * 6.5f, -f15 * 6.5f * f9);
                this.setPartPosition(this.dragonPartNeck, f14 * 5.5f * f9, f16 + f10 * 5.5f, -f15 * 5.5f * f9);
                double[] dArray = this.getMovementOffsets(5, 1.0f);
                for (n = 0; n < 3; ++n) {
                    EnderDragonPartEntity enderDragonPartEntity = null;
                    if (n == 0) {
                        enderDragonPartEntity = this.dragonPartTail1;
                    }
                    if (n == 1) {
                        enderDragonPartEntity = this.dragonPartTail2;
                    }
                    if (n == 2) {
                        enderDragonPartEntity = this.dragonPartTail3;
                    }
                    double[] dArray2 = this.getMovementOffsets(12 + n * 2, 1.0f);
                    float f17 = this.rotationYaw * ((float)Math.PI / 180) + this.simplifyAngle(dArray2[0] - dArray[0]) * ((float)Math.PI / 180);
                    float f18 = MathHelper.sin(f17);
                    float f19 = MathHelper.cos(f17);
                    f5 = 1.5f;
                    f4 = (float)(n + 1) * 2.0f;
                    this.setPartPosition(enderDragonPartEntity, -(f12 * 1.5f + f18 * f4) * f9, dArray2[1] - dArray[1] - (double)((f4 + 1.5f) * f10) + 1.5, (f13 * 1.5f + f19 * f4) * f9);
                }
                if (!this.world.isRemote) {
                    this.slowed = this.destroyBlocksInAABB(this.dragonPartHead.getBoundingBox()) | this.destroyBlocksInAABB(this.dragonPartNeck.getBoundingBox()) | this.destroyBlocksInAABB(this.dragonPartBody.getBoundingBox());
                    if (this.fightManager != null) {
                        this.fightManager.dragonUpdate(this);
                    }
                }
                for (n = 0; n < this.dragonParts.length; ++n) {
                    this.dragonParts[n].prevPosX = vector3dArray[n].x;
                    this.dragonParts[n].prevPosY = vector3dArray[n].y;
                    this.dragonParts[n].prevPosZ = vector3dArray[n].z;
                    this.dragonParts[n].lastTickPosX = vector3dArray[n].x;
                    this.dragonParts[n].lastTickPosY = vector3dArray[n].y;
                    this.dragonParts[n].lastTickPosZ = vector3dArray[n].z;
                }
            }
        }
    }

    private void setPartPosition(EnderDragonPartEntity enderDragonPartEntity, double d, double d2, double d3) {
        enderDragonPartEntity.setPosition(this.getPosX() + d, this.getPosY() + d2, this.getPosZ() + d3);
    }

    private float getHeadAndNeckYOffset() {
        if (this.phaseManager.getCurrentPhase().getIsStationary()) {
            return -1.0f;
        }
        double[] dArray = this.getMovementOffsets(5, 1.0f);
        double[] dArray2 = this.getMovementOffsets(0, 1.0f);
        return (float)(dArray[1] - dArray2[1]);
    }

    private void updateDragonEnderCrystal() {
        if (this.closestEnderCrystal != null) {
            if (this.closestEnderCrystal.removed) {
                this.closestEnderCrystal = null;
            } else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.rand.nextInt(10) == 0) {
            List<EnderCrystalEntity> list = this.world.getEntitiesWithinAABB(EnderCrystalEntity.class, this.getBoundingBox().grow(32.0));
            EnderCrystalEntity enderCrystalEntity = null;
            double d = Double.MAX_VALUE;
            for (EnderCrystalEntity enderCrystalEntity2 : list) {
                double d2 = enderCrystalEntity2.getDistanceSq(this);
                if (!(d2 < d)) continue;
                d = d2;
                enderCrystalEntity = enderCrystalEntity2;
            }
            this.closestEnderCrystal = enderCrystalEntity;
        }
    }

    private void collideWithEntities(List<Entity> list) {
        double d = (this.dragonPartBody.getBoundingBox().minX + this.dragonPartBody.getBoundingBox().maxX) / 2.0;
        double d2 = (this.dragonPartBody.getBoundingBox().minZ + this.dragonPartBody.getBoundingBox().maxZ) / 2.0;
        for (Entity entity2 : list) {
            if (!(entity2 instanceof LivingEntity)) continue;
            double d3 = entity2.getPosX() - d;
            double d4 = entity2.getPosZ() - d2;
            double d5 = Math.max(d3 * d3 + d4 * d4, 0.1);
            entity2.addVelocity(d3 / d5 * 4.0, 0.2f, d4 / d5 * 4.0);
            if (this.phaseManager.getCurrentPhase().getIsStationary() || ((LivingEntity)entity2).getRevengeTimer() >= entity2.ticksExisted - 2) continue;
            entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0f);
            this.applyEnchantments(this, entity2);
        }
    }

    private void attackEntitiesInList(List<Entity> list) {
        for (Entity entity2 : list) {
            if (!(entity2 instanceof LivingEntity)) continue;
            entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0f);
            this.applyEnchantments(this, entity2);
        }
    }

    private float simplifyAngle(double d) {
        return (float)MathHelper.wrapDegrees(d);
    }

    private boolean destroyBlocksInAABB(AxisAlignedBB axisAlignedBB) {
        int n = MathHelper.floor(axisAlignedBB.minX);
        int n2 = MathHelper.floor(axisAlignedBB.minY);
        int n3 = MathHelper.floor(axisAlignedBB.minZ);
        int n4 = MathHelper.floor(axisAlignedBB.maxX);
        int n5 = MathHelper.floor(axisAlignedBB.maxY);
        int n6 = MathHelper.floor(axisAlignedBB.maxZ);
        boolean bl = false;
        boolean bl2 = false;
        for (int i = n; i <= n4; ++i) {
            for (int j = n2; j <= n5; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    BlockPos blockPos = new BlockPos(i, j, k);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    if (blockState.isAir() || blockState.getMaterial() == Material.FIRE) continue;
                    if (this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && !BlockTags.DRAGON_IMMUNE.contains(block)) {
                        bl2 = this.world.removeBlock(blockPos, true) || bl2;
                        continue;
                    }
                    bl = true;
                }
            }
        }
        if (bl2) {
            BlockPos blockPos = new BlockPos(n + this.rand.nextInt(n4 - n + 1), n2 + this.rand.nextInt(n5 - n2 + 1), n3 + this.rand.nextInt(n6 - n3 + 1));
            this.world.playEvent(2008, blockPos, 0);
        }
        return bl;
    }

    public boolean attackEntityPartFrom(EnderDragonPartEntity enderDragonPartEntity, DamageSource damageSource, float f) {
        if (this.phaseManager.getCurrentPhase().getType() == PhaseType.DYING) {
            return true;
        }
        f = this.phaseManager.getCurrentPhase().func_221113_a(damageSource, f);
        if (enderDragonPartEntity != this.dragonPartHead) {
            f = f / 4.0f + Math.min(f, 1.0f);
        }
        if (f < 0.01f) {
            return true;
        }
        if (damageSource.getTrueSource() instanceof PlayerEntity || damageSource.isExplosion()) {
            float f2 = this.getHealth();
            this.attackDragonFrom(damageSource, f);
            if (this.getShouldBeDead() && !this.phaseManager.getCurrentPhase().getIsStationary()) {
                this.setHealth(1.0f);
                this.phaseManager.setPhase(PhaseType.DYING);
            }
            if (this.phaseManager.getCurrentPhase().getIsStationary()) {
                this.sittingDamageReceived = (int)((float)this.sittingDamageReceived + (f2 - this.getHealth()));
                if ((float)this.sittingDamageReceived > 0.25f * this.getMaxHealth()) {
                    this.sittingDamageReceived = 0;
                    this.phaseManager.setPhase(PhaseType.TAKEOFF);
                }
            }
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (damageSource instanceof EntityDamageSource && ((EntityDamageSource)damageSource).getIsThornsDamage()) {
            this.attackEntityPartFrom(this.dragonPartBody, damageSource, f);
        }
        return true;
    }

    protected boolean attackDragonFrom(DamageSource damageSource, float f) {
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public void onKillCommand() {
        this.remove();
        if (this.fightManager != null) {
            this.fightManager.dragonUpdate(this);
            this.fightManager.processDragonDeath(this);
        }
    }

    @Override
    protected void onDeathUpdate() {
        if (this.fightManager != null) {
            this.fightManager.dragonUpdate(this);
        }
        ++this.deathTicks;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            float f = (this.rand.nextFloat() - 0.5f) * 8.0f;
            float f2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            float f3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX() + (double)f, this.getPosY() + 2.0 + (double)f2, this.getPosZ() + (double)f3, 0.0, 0.0, 0.0);
        }
        boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT);
        int n = 500;
        if (this.fightManager != null && !this.fightManager.hasPreviouslyKilledDragon()) {
            n = 12000;
        }
        if (!this.world.isRemote) {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && bl) {
                this.dropExperience(MathHelper.floor((float)n * 0.08f));
            }
            if (this.deathTicks == 1 && !this.isSilent()) {
                this.world.playBroadcastSound(1028, this.getPosition(), 0);
            }
        }
        this.move(MoverType.SELF, new Vector3d(0.0, 0.1f, 0.0));
        this.rotationYaw += 20.0f;
        this.renderYawOffset = this.rotationYaw;
        if (this.deathTicks == 200 && !this.world.isRemote) {
            if (bl) {
                this.dropExperience(MathHelper.floor((float)n * 0.2f));
            }
            if (this.fightManager != null) {
                this.fightManager.processDragonDeath(this);
            }
            this.remove();
        }
    }

    private void dropExperience(int n) {
        while (n > 0) {
            int n2 = ExperienceOrbEntity.getXPSplit(n);
            n -= n2;
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), n2));
        }
    }

    public int initPathPoints() {
        if (this.pathPoints[0] == null) {
            for (int i = 0; i < 24; ++i) {
                int n;
                int n2;
                int n3;
                int n4 = 5;
                if (i < 12) {
                    n3 = MathHelper.floor(60.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)i)));
                    n2 = MathHelper.floor(60.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)i)));
                } else if (i < 20) {
                    n = i - 12;
                    n3 = MathHelper.floor(40.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)n)));
                    n2 = MathHelper.floor(40.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)n)));
                    n4 += 10;
                } else {
                    n = i - 20;
                    n3 = MathHelper.floor(20.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)n)));
                    n2 = MathHelper.floor(20.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)n)));
                }
                n = Math.max(this.world.getSeaLevel() + 10, this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(n3, 0, n2)).getY() + n4);
                this.pathPoints[i] = new PathPoint(n3, n, n2);
            }
            this.neighbors[0] = 6146;
            this.neighbors[1] = 8197;
            this.neighbors[2] = 8202;
            this.neighbors[3] = 16404;
            this.neighbors[4] = 32808;
            this.neighbors[5] = 32848;
            this.neighbors[6] = 65696;
            this.neighbors[7] = 131392;
            this.neighbors[8] = 131712;
            this.neighbors[9] = 263424;
            this.neighbors[10] = 526848;
            this.neighbors[11] = 525313;
            this.neighbors[12] = 1581057;
            this.neighbors[13] = 3166214;
            this.neighbors[14] = 2138120;
            this.neighbors[15] = 6373424;
            this.neighbors[16] = 4358208;
            this.neighbors[17] = 12910976;
            this.neighbors[18] = 9044480;
            this.neighbors[19] = 9706496;
            this.neighbors[20] = 15216640;
            this.neighbors[21] = 0xD0E000;
            this.neighbors[22] = 11763712;
            this.neighbors[23] = 0x7E0000;
        }
        return this.getNearestPpIdx(this.getPosX(), this.getPosY(), this.getPosZ());
    }

    public int getNearestPpIdx(double d, double d2, double d3) {
        float f = 10000.0f;
        int n = 0;
        PathPoint pathPoint = new PathPoint(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
        int n2 = 0;
        if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0) {
            n2 = 12;
        }
        for (int i = n2; i < 24; ++i) {
            float f2;
            if (this.pathPoints[i] == null || !((f2 = this.pathPoints[i].distanceToSquared(pathPoint)) < f)) continue;
            f = f2;
            n = i;
        }
        return n;
    }

    @Nullable
    public Path findPath(int n, int n2, @Nullable PathPoint pathPoint) {
        PathPoint pathPoint2;
        for (int i = 0; i < 24; ++i) {
            pathPoint2 = this.pathPoints[i];
            pathPoint2.visited = false;
            pathPoint2.distanceToTarget = 0.0f;
            pathPoint2.totalPathDistance = 0.0f;
            pathPoint2.distanceToNext = 0.0f;
            pathPoint2.previous = null;
            pathPoint2.index = -1;
        }
        PathPoint pathPoint3 = this.pathPoints[n];
        pathPoint2 = this.pathPoints[n2];
        pathPoint3.totalPathDistance = 0.0f;
        pathPoint3.distanceToTarget = pathPoint3.distanceToNext = pathPoint3.distanceTo(pathPoint2);
        this.pathFindQueue.clearPath();
        this.pathFindQueue.addPoint(pathPoint3);
        PathPoint pathPoint4 = pathPoint3;
        int n3 = 0;
        if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0) {
            n3 = 12;
        }
        while (!this.pathFindQueue.isPathEmpty()) {
            int n4;
            PathPoint pathPoint5 = this.pathFindQueue.dequeue();
            if (pathPoint5.equals(pathPoint2)) {
                if (pathPoint != null) {
                    pathPoint.previous = pathPoint2;
                    pathPoint2 = pathPoint;
                }
                return this.makePath(pathPoint3, pathPoint2);
            }
            if (pathPoint5.distanceTo(pathPoint2) < pathPoint4.distanceTo(pathPoint2)) {
                pathPoint4 = pathPoint5;
            }
            pathPoint5.visited = true;
            int n5 = 0;
            for (n4 = 0; n4 < 24; ++n4) {
                if (this.pathPoints[n4] != pathPoint5) continue;
                n5 = n4;
                break;
            }
            for (n4 = n3; n4 < 24; ++n4) {
                if ((this.neighbors[n5] & 1 << n4) <= 0) continue;
                PathPoint pathPoint6 = this.pathPoints[n4];
                if (pathPoint6.visited) continue;
                float f = pathPoint5.totalPathDistance + pathPoint5.distanceTo(pathPoint6);
                if (pathPoint6.isAssigned() && !(f < pathPoint6.totalPathDistance)) continue;
                pathPoint6.previous = pathPoint5;
                pathPoint6.totalPathDistance = f;
                pathPoint6.distanceToNext = pathPoint6.distanceTo(pathPoint2);
                if (pathPoint6.isAssigned()) {
                    this.pathFindQueue.changeDistance(pathPoint6, pathPoint6.totalPathDistance + pathPoint6.distanceToNext);
                    continue;
                }
                pathPoint6.distanceToTarget = pathPoint6.totalPathDistance + pathPoint6.distanceToNext;
                this.pathFindQueue.addPoint(pathPoint6);
            }
        }
        if (pathPoint4 == pathPoint3) {
            return null;
        }
        LOGGER.debug("Failed to find path from {} to {}", (Object)n, (Object)n2);
        if (pathPoint != null) {
            pathPoint.previous = pathPoint4;
            pathPoint4 = pathPoint;
        }
        return this.makePath(pathPoint3, pathPoint4);
    }

    private Path makePath(PathPoint pathPoint, PathPoint pathPoint2) {
        ArrayList<PathPoint> arrayList = Lists.newArrayList();
        PathPoint pathPoint3 = pathPoint2;
        arrayList.add(0, pathPoint2);
        while (pathPoint3.previous != null) {
            pathPoint3 = pathPoint3.previous;
            arrayList.add(0, pathPoint3);
        }
        return new Path(arrayList, new BlockPos(pathPoint2.x, pathPoint2.y, pathPoint2.z), true);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("DragonPhase", this.phaseManager.getCurrentPhase().getType().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("DragonPhase")) {
            this.phaseManager.setPhase(PhaseType.getById(compoundNBT.getInt("DragonPhase")));
        }
    }

    @Override
    public void checkDespawn() {
    }

    public EnderDragonPartEntity[] getDragonParts() {
        return this.dragonParts;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }

    public float getHeadPartYOffset(int n, double[] dArray, double[] dArray2) {
        double d;
        IPhase iPhase = this.phaseManager.getCurrentPhase();
        PhaseType<? extends IPhase> phaseType = iPhase.getType();
        if (phaseType != PhaseType.LANDING && phaseType != PhaseType.TAKEOFF) {
            d = iPhase.getIsStationary() ? (double)n : (n == 6 ? 0.0 : dArray2[1] - dArray[1]);
        } else {
            BlockPos blockPos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
            float f = Math.max(MathHelper.sqrt(blockPos.distanceSq(this.getPositionVec(), false)) / 4.0f, 1.0f);
            d = (float)n / f;
        }
        return (float)d;
    }

    public Vector3d getHeadLookVec(float f) {
        Vector3d vector3d;
        IPhase iPhase = this.phaseManager.getCurrentPhase();
        PhaseType<? extends IPhase> phaseType = iPhase.getType();
        if (phaseType != PhaseType.LANDING && phaseType != PhaseType.TAKEOFF) {
            if (iPhase.getIsStationary()) {
                float f2 = this.rotationPitch;
                float f3 = 1.5f;
                this.rotationPitch = -45.0f;
                vector3d = this.getLook(f);
                this.rotationPitch = f2;
            } else {
                vector3d = this.getLook(f);
            }
        } else {
            BlockPos blockPos = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
            float f4 = Math.max(MathHelper.sqrt(blockPos.distanceSq(this.getPositionVec(), false)) / 4.0f, 1.0f);
            float f5 = 6.0f / f4;
            float f6 = this.rotationPitch;
            float f7 = 1.5f;
            this.rotationPitch = -f5 * 1.5f * 5.0f;
            vector3d = this.getLook(f);
            this.rotationPitch = f6;
        }
        return vector3d;
    }

    public void onCrystalDestroyed(EnderCrystalEntity enderCrystalEntity, BlockPos blockPos, DamageSource damageSource) {
        PlayerEntity playerEntity = damageSource.getTrueSource() instanceof PlayerEntity ? (PlayerEntity)damageSource.getTrueSource() : this.world.getClosestPlayer(PLAYER_INVADER_CONDITION, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (enderCrystalEntity == this.closestEnderCrystal) {
            this.attackEntityPartFrom(this.dragonPartHead, DamageSource.causeExplosionDamage(playerEntity), 10.0f);
        }
        this.phaseManager.getCurrentPhase().onCrystalDestroyed(enderCrystalEntity, blockPos, damageSource, playerEntity);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (PHASE.equals(dataParameter) && this.world.isRemote) {
            this.phaseManager.setPhase(PhaseType.getById(this.getDataManager().get(PHASE)));
        }
        super.notifyDataManagerChange(dataParameter);
    }

    public PhaseManager getPhaseManager() {
        return this.phaseManager;
    }

    @Nullable
    public DragonFightManager getFightManager() {
        return this.fightManager;
    }

    @Override
    public boolean addPotionEffect(EffectInstance effectInstance) {
        return true;
    }

    @Override
    protected boolean canBeRidden(Entity entity2) {
        return true;
    }

    @Override
    public boolean isNonBoss() {
        return true;
    }
}

