package net.minecraft.src;

import java.util.concurrent.*;
import java.util.*;

public abstract class EntityLiving extends Entity
{
    private static final float[] enchantmentProbability;
    private static final float[] armorEnchantmentProbability;
    private static final float[] armorProbability;
    public static final float[] pickUpLootProability;
    public int maxHurtResistantTime;
    public float field_70769_ao;
    public float field_70770_ap;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    public float rotationYawHead;
    public float prevRotationYawHead;
    protected float field_70768_au;
    protected float field_70766_av;
    protected float field_70764_aw;
    protected float field_70763_ax;
    protected boolean field_70753_ay;
    protected String texture;
    protected boolean field_70740_aA;
    protected float field_70741_aB;
    protected String entityType;
    protected float field_70743_aD;
    protected int scoreValue;
    protected float field_70745_aF;
    public float landMovementFactor;
    public float jumpMovementFactor;
    public float prevSwingProgress;
    public float swingProgress;
    protected int health;
    public int prevHealth;
    public int carryoverDamage;
    public int livingSoundTime;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public int attackTime;
    public float prevCameraPitch;
    public float cameraPitch;
    protected boolean dead;
    public int experienceValue;
    public int field_70731_aW;
    public float field_70730_aX;
    public float prevLimbYaw;
    public float limbYaw;
    public float limbSwing;
    protected EntityPlayer attackingPlayer;
    protected int recentlyHit;
    private EntityLiving entityLivingToAttack;
    private int revengeTimer;
    private EntityLiving lastAttackingEntity;
    public int arrowHitTimer;
    protected HashMap activePotionsMap;
    private boolean potionsNeedUpdate;
    private int field_70748_f;
    private EntityLookHelper lookHelper;
    private EntityMoveHelper moveHelper;
    private EntityJumpHelper jumpHelper;
    private EntityBodyHelper bodyHelper;
    private PathNavigate navigator;
    public final EntityAITasks tasks;
    public final EntityAITasks targetTasks;
    private EntityLiving attackTarget;
    private EntitySenses senses;
    private float AIMoveSpeed;
    private ChunkCoordinates homePosition;
    private float maximumHomeDistance;
    private ItemStack[] equipment;
    protected float[] equipmentDropChances;
    private ItemStack[] previousEquipment;
    public boolean isSwingInProgress;
    public int swingProgressInt;
    private boolean canPickUpLoot;
    private boolean persistenceRequired;
    protected final CombatTracker field_94063_bt;
    protected int newPosRotationIncrements;
    protected double newPosX;
    protected double newPosY;
    protected double newPosZ;
    protected double newRotationYaw;
    protected double newRotationPitch;
    float field_70706_bo;
    protected int lastDamage;
    protected int entityAge;
    protected float moveStrafing;
    protected float moveForward;
    protected float randomYawVelocity;
    public boolean isJumping;
    protected float defaultPitch;
    protected float moveSpeed;
    private int jumpTicks;
    private Entity currentTarget;
    protected int numTicksToChaseTarget;
    public int persistentId;
    
    static {
        enchantmentProbability = new float[] { 0.0f, 0.0f, 0.1f, 0.2f };
        armorEnchantmentProbability = new float[] { 0.0f, 0.0f, 0.25f, 0.5f };
        armorProbability = new float[] { 0.0f, 0.0f, 0.05f, 0.07f };
        pickUpLootProability = new float[] { 0.0f, 0.1f, 0.15f, 0.45f };
    }
    
    public EntityLiving(final World par1World) {
        super(par1World);
        this.maxHurtResistantTime = 20;
        this.renderYawOffset = 0.0f;
        this.prevRenderYawOffset = 0.0f;
        this.rotationYawHead = 0.0f;
        this.prevRotationYawHead = 0.0f;
        this.field_70753_ay = true;
        this.texture = "/mob/char.png";
        this.field_70740_aA = true;
        this.field_70741_aB = 0.0f;
        this.entityType = null;
        this.field_70743_aD = 1.0f;
        this.scoreValue = 0;
        this.field_70745_aF = 0.0f;
        this.landMovementFactor = 0.1f;
        this.jumpMovementFactor = 0.02f;
        this.health = this.getMaxHealth();
        this.attackedAtYaw = 0.0f;
        this.deathTime = 0;
        this.attackTime = 0;
        this.dead = false;
        this.field_70731_aW = -1;
        this.field_70730_aX = (float)(Math.random() * 0.8999999761581421 + 0.10000000149011612);
        this.attackingPlayer = null;
        this.recentlyHit = 0;
        this.entityLivingToAttack = null;
        this.revengeTimer = 0;
        this.lastAttackingEntity = null;
        this.arrowHitTimer = 0;
        this.activePotionsMap = new HashMap();
        this.potionsNeedUpdate = true;
        this.homePosition = new ChunkCoordinates(0, 0, 0);
        this.maximumHomeDistance = -1.0f;
        this.equipment = new ItemStack[5];
        this.equipmentDropChances = new float[5];
        this.previousEquipment = new ItemStack[5];
        this.isSwingInProgress = false;
        this.swingProgressInt = 0;
        this.canPickUpLoot = false;
        this.persistenceRequired = false;
        this.field_94063_bt = new CombatTracker(this);
        this.field_70706_bo = 0.0f;
        this.lastDamage = 0;
        this.entityAge = 0;
        this.isJumping = false;
        this.defaultPitch = 0.0f;
        this.moveSpeed = 0.7f;
        this.jumpTicks = 0;
        this.numTicksToChaseTarget = 0;
        this.persistentId = this.rand.nextInt(Integer.MAX_VALUE);
        this.preventEntitySpawning = true;
        this.tasks = new EntityAITasks((par1World != null && par1World.theProfiler != null) ? par1World.theProfiler : null);
        this.targetTasks = new EntityAITasks((par1World != null && par1World.theProfiler != null) ? par1World.theProfiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = new PathNavigate(this, par1World, this.func_96121_ay());
        this.senses = new EntitySenses(this);
        this.field_70770_ap = (float)(Math.random() + 1.0) * 0.01f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.rotationYawHead = this.rotationYaw;
        for (int var2 = 0; var2 < this.equipmentDropChances.length; ++var2) {
            this.equipmentDropChances[var2] = 0.085f;
        }
        this.stepHeight = 0.5f;
    }
    
    protected int func_96121_ay() {
        return 16;
    }
    
    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }
    
    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }
    
    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }
    
    public PathNavigate getNavigator() {
        return this.navigator;
    }
    
    public EntitySenses getEntitySenses() {
        return this.senses;
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public EntityLiving getAITarget() {
        return this.entityLivingToAttack;
    }
    
    public EntityLiving getLastAttackingEntity() {
        return this.lastAttackingEntity;
    }
    
    public void setLastAttackingEntity(final Entity par1Entity) {
        if (par1Entity instanceof EntityLiving) {
            this.lastAttackingEntity = (EntityLiving)par1Entity;
        }
    }
    
    public int getAge() {
        return this.entityAge;
    }
    
    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    @Override
    public void setRotationYawHead(final float par1) {
        this.rotationYawHead = par1;
    }
    
    public float getAIMoveSpeed() {
        return this.AIMoveSpeed;
    }
    
    public void setAIMoveSpeed(final float par1) {
        this.setMoveForward(this.AIMoveSpeed = par1);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        this.setLastAttackingEntity(par1Entity);
        return false;
    }
    
    public EntityLiving getAttackTarget() {
        return this.attackTarget;
    }
    
    public void setAttackTarget(final EntityLiving par1EntityLiving) {
        this.attackTarget = par1EntityLiving;
        if (Reflector.ForgeHooks_onLivingSetAttackTarget.exists()) {
            Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, par1EntityLiving);
        }
    }
    
    public boolean canAttackClass(final Class par1Class) {
        return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
    }
    
    public void eatGrassBonus() {
    }
    
    @Override
    protected void updateFallState(final double par1, final boolean par3) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }
        if (par3 && this.fallDistance > 0.0f) {
            final int var4 = MathHelper.floor_double(this.posX);
            final int var5 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var6 = MathHelper.floor_double(this.posZ);
            int var7 = this.worldObj.getBlockId(var4, var5, var6);
            if (var7 == 0) {
                final int var8 = this.worldObj.blockGetRenderType(var4, var5 - 1, var6);
                if (var8 == 11 || var8 == 32 || var8 == 21) {
                    var7 = this.worldObj.getBlockId(var4, var5 - 1, var6);
                }
            }
            if (var7 > 0) {
                Block.blocksList[var7].onFallenUpon(this.worldObj, var4, var5, var6, this, this.fallDistance);
            }
        }
        super.updateFallState(par1, par3);
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistance(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    }
    
    public boolean isWithinHomeDistance(final int par1, final int par2, final int par3) {
        return this.maximumHomeDistance == -1.0f || this.homePosition.getDistanceSquared(par1, par2, par3) < this.maximumHomeDistance * this.maximumHomeDistance;
    }
    
    public void setHomeArea(final int par1, final int par2, final int par3, final int par4) {
        this.homePosition.set(par1, par2, par3);
        this.maximumHomeDistance = par4;
    }
    
    public ChunkCoordinates getHomePosition() {
        return this.homePosition;
    }
    
    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0f;
    }
    
    public void setRevengeTarget(final EntityLiving par1EntityLiving) {
        this.entityLivingToAttack = par1EntityLiving;
        this.revengeTimer = ((this.entityLivingToAttack != null) ? 100 : 0);
        if (Reflector.ForgeHooks_onLivingSetAttackTarget.exists()) {
            Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, par1EntityLiving);
        }
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(8, this.field_70748_f);
        this.dataWatcher.addObject(9, (byte)0);
        this.dataWatcher.addObject(10, (byte)0);
        this.dataWatcher.addObject(6, (byte)0);
        this.dataWatcher.addObject(5, "");
    }
    
    public boolean canEntityBeSeen(final Entity par1Entity) {
        return this.worldObj.rayTraceBlocks(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY + this.getEyeHeight(), this.posZ), this.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
    }
    
    @Override
    public String getTexture() {
        return this.texture;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.85f;
    }
    
    public int getTalkInterval() {
        return 80;
    }
    
    public void playLivingSound() {
        final String var1 = this.getLivingSound();
        if (var1 != null) {
            this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public void onEntityUpdate() {
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        if (this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(DamageSource.inWall, 1);
        }
        if (this.isImmuneToFire() || this.worldObj.isRemote) {
            this.extinguish();
        }
        final boolean var1 = this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.disableDamage;
        if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water) && !this.canBreatheUnderwater() && !this.activePotionsMap.containsKey(Potion.waterBreathing.id) && !var1) {
            this.setAir(this.decreaseAirSupply(this.getAir()));
            if (this.getAir() == -20) {
                this.setAir(0);
                for (int var2 = 0; var2 < 8; ++var2) {
                    final float var3 = this.rand.nextFloat() - this.rand.nextFloat();
                    final float var4 = this.rand.nextFloat() - this.rand.nextFloat();
                    final float var5 = this.rand.nextFloat() - this.rand.nextFloat();
                    this.worldObj.spawnParticle("bubble", this.posX + var3, this.posY + var4, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                }
                this.attackEntityFrom(DamageSource.drown, 2);
            }
            this.extinguish();
        }
        else {
            this.setAir(300);
        }
        this.prevCameraPitch = this.cameraPitch;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        if (this.health <= 0) {
            this.onDeathUpdate();
        }
        if (this.recentlyHit > 0) {
            --this.recentlyHit;
        }
        else {
            this.attackingPlayer = null;
        }
        if (this.lastAttackingEntity != null && !this.lastAttackingEntity.isEntityAlive()) {
            this.lastAttackingEntity = null;
        }
        if (this.entityLivingToAttack != null) {
            if (!this.entityLivingToAttack.isEntityAlive()) {
                this.setRevengeTarget(null);
            }
            else if (this.revengeTimer > 0) {
                --this.revengeTimer;
            }
            else {
                this.setRevengeTarget(null);
            }
        }
        this.updatePotionEffects();
        this.field_70763_ax = this.field_70764_aw;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.worldObj.theProfiler.endSection();
    }
    
    protected void onDeathUpdate() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && !this.isChild() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                int var1 = this.getExperiencePoints(this.attackingPlayer);
                while (var1 > 0) {
                    final int var2 = EntityXPOrb.getXPSplit(var1);
                    var1 -= var2;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
                }
            }
            this.setDead();
            for (int var1 = 0; var1 < 20; ++var1) {
                final double var3 = this.rand.nextGaussian() * 0.02;
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var3, var4, var5);
            }
        }
    }
    
    protected int decreaseAirSupply(final int par1) {
        final int var2 = EnchantmentHelper.getRespiration(this);
        return (var2 > 0 && this.rand.nextInt(var2 + 1) > 0) ? par1 : (par1 - 1);
    }
    
    protected int getExperiencePoints(final EntityPlayer par1EntityPlayer) {
        if (this.experienceValue > 0) {
            int var2 = this.experienceValue;
            final ItemStack[] var3 = this.getLastActiveItems();
            for (int var4 = 0; var4 < var3.length; ++var4) {
                if (var3[var4] != null && this.equipmentDropChances[var4] <= 1.0f) {
                    var2 += 1 + this.rand.nextInt(3);
                }
            }
            return var2;
        }
        return this.experienceValue;
    }
    
    protected boolean isPlayer() {
        return false;
    }
    
    public void spawnExplosionParticle() {
        for (int var1 = 0; var1 < 20; ++var1) {
            final double var2 = this.rand.nextGaussian() * 0.02;
            final double var3 = this.rand.nextGaussian() * 0.02;
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width - var2 * var5, this.posY + this.rand.nextFloat() * this.height - var3 * var5, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width - var4 * var5, var2, var3, var4);
        }
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_70768_au = this.field_70766_av;
        this.field_70766_av = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    @Override
    public void setPositionAndRotation2(final double par1, final double par3, final double par5, final float par7, final float par8, final int par9) {
        this.yOffset = 0.0f;
        this.newPosX = par1;
        this.newPosY = par3;
        this.newPosZ = par5;
        this.newRotationYaw = par7;
        this.newRotationPitch = par8;
        this.newPosRotationIncrements = par9;
    }
    
    @Override
    public void onUpdate() {
        if (!Reflector.ForgeHooks_onLivingUpdate.exists() || !Reflector.callBoolean(Reflector.ForgeHooks_onLivingUpdate, this)) {
            super.onUpdate();
            if (!this.worldObj.isRemote) {
                for (int var1 = 0; var1 < 5; ++var1) {
                    final ItemStack var2 = this.getCurrentItemOrArmor(var1);
                    if (!ItemStack.areItemStacksEqual(var2, this.previousEquipment[var1])) {
                        ((WorldServer)this.worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet5PlayerInventory(this.entityId, var1, var2));
                        this.previousEquipment[var1] = ((var2 == null) ? null : var2.copy());
                    }
                }
                int var1 = this.getArrowCountInEntity();
                if (var1 > 0) {
                    if (this.arrowHitTimer <= 0) {
                        this.arrowHitTimer = 20 * (30 - var1);
                    }
                    --this.arrowHitTimer;
                    if (this.arrowHitTimer <= 0) {
                        this.setArrowCountInEntity(var1 - 1);
                    }
                }
            }
            this.onLivingUpdate();
            final double var3 = this.posX - this.prevPosX;
            final double var4 = this.posZ - this.prevPosZ;
            final float var5 = (float)(var3 * var3 + var4 * var4);
            float var6 = this.renderYawOffset;
            float var7 = 0.0f;
            this.field_70768_au = this.field_70766_av;
            float var8 = 0.0f;
            if (var5 > 0.0025000002f) {
                var8 = 1.0f;
                var7 = (float)Math.sqrt(var5) * 3.0f;
                var6 = (float)Math.atan2(var4, var3) * 180.0f / 3.1415927f - 90.0f;
            }
            if (this.swingProgress > 0.0f) {
                var6 = this.rotationYaw;
            }
            if (!this.onGround) {
                var8 = 0.0f;
            }
            this.field_70766_av += (var8 - this.field_70766_av) * 0.3f;
            this.worldObj.theProfiler.startSection("headTurn");
            if (this.isAIEnabled()) {
                this.bodyHelper.func_75664_a();
            }
            else {
                final float var9 = MathHelper.wrapAngleTo180_float(var6 - this.renderYawOffset);
                this.renderYawOffset += var9 * 0.3f;
                float var10 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
                final boolean var11 = var10 < -90.0f || var10 >= 90.0f;
                if (var10 < -75.0f) {
                    var10 = -75.0f;
                }
                if (var10 >= 75.0f) {
                    var10 = 75.0f;
                }
                this.renderYawOffset = this.rotationYaw - var10;
                if (var10 * var10 > 2500.0f) {
                    this.renderYawOffset += var10 * 0.2f;
                }
                if (var11) {
                    var7 *= -1.0f;
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rangeChecks");
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
                this.prevRenderYawOffset -= 360.0f;
            }
            while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
                this.prevRenderYawOffset += 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
                this.prevRotationYawHead -= 360.0f;
            }
            while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
                this.prevRotationYawHead += 360.0f;
            }
            this.worldObj.theProfiler.endSection();
            this.field_70764_aw += var7;
            if (Config.isSmoothWorld() && !Config.isMinecraftThread()) {
                Thread.yield();
            }
        }
    }
    
    public void heal(final int par1) {
        if (this.health > 0) {
            this.setEntityHealth(this.getHealth() + par1);
            if (this.health > this.getMaxHealth()) {
                this.setEntityHealth(this.getMaxHealth());
            }
            this.hurtResistantTime = this.maxHurtResistantTime / 2;
        }
    }
    
    public abstract int getMaxHealth();
    
    public int getHealth() {
        return this.health;
    }
    
    public void setEntityHealth(int par1) {
        this.health = par1;
        if (par1 > this.getMaxHealth()) {
            par1 = this.getMaxHealth();
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, int par2) {
        if (Reflector.ForgeHooks_onLivingAttack.exists() && Reflector.callBoolean(Reflector.ForgeHooks_onLivingAttack, this, par1DamageSource, par2)) {
            return false;
        }
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.worldObj.isRemote) {
            return false;
        }
        this.entityAge = 0;
        if (this.health <= 0) {
            return false;
        }
        if (par1DamageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
        }
        if ((par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) && this.getCurrentItemOrArmor(4) != null) {
            this.getCurrentItemOrArmor(4).damageItem(par2 * 4 + this.rand.nextInt(par2 * 2), this);
            par2 *= (int)0.75f;
        }
        this.limbYaw = 1.5f;
        boolean var3 = true;
        if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0f) {
            if (par2 <= this.lastDamage) {
                return false;
            }
            this.damageEntity(par1DamageSource, par2 - this.lastDamage);
            this.lastDamage = par2;
            var3 = false;
        }
        else {
            this.lastDamage = par2;
            this.prevHealth = this.health;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(par1DamageSource, par2);
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
        }
        this.attackedAtYaw = 0.0f;
        final Entity var4 = par1DamageSource.getEntity();
        if (var4 != null) {
            if (var4 instanceof EntityLiving) {
                this.setRevengeTarget((EntityLiving)var4);
            }
            if (var4 instanceof EntityPlayer) {
                this.recentlyHit = 100;
                this.attackingPlayer = (EntityPlayer)var4;
            }
            else if (var4 instanceof EntityWolf) {
                final EntityWolf var5 = (EntityWolf)var4;
                if (var5.isTamed()) {
                    this.recentlyHit = 100;
                    this.attackingPlayer = null;
                }
            }
        }
        if (var3) {
            this.worldObj.setEntityState(this, (byte)2);
            if (par1DamageSource != DamageSource.drown) {
                this.setBeenAttacked();
            }
            if (var4 != null) {
                double var6;
                double var7;
                for (var6 = var4.posX - this.posX, var7 = var4.posZ - this.posZ; var6 * var6 + var7 * var7 < 1.0E-4; var6 = (Math.random() - Math.random()) * 0.01, var7 = (Math.random() - Math.random()) * 0.01) {}
                this.attackedAtYaw = (float)(Math.atan2(var7, var6) * 180.0 / 3.141592653589793) - this.rotationYaw;
                this.knockBack(var4, par2, var6, var7);
            }
            else {
                this.attackedAtYaw = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (var3) {
                this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
            }
            this.onDeath(par1DamageSource);
        }
        else if (var3) {
            this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        return true;
    }
    
    protected float getSoundPitch() {
        return this.isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }
    
    @Override
    public void performHurtAnimation() {
        final int n = 10;
        this.maxHurtTime = n;
        this.hurtTime = n;
        this.attackedAtYaw = 0.0f;
    }
    
    public int getTotalArmorValue() {
        int var1 = 0;
        for (final ItemStack var5 : this.getLastActiveItems()) {
            if (var5 != null && var5.getItem() instanceof ItemArmor) {
                final int var6 = ((ItemArmor)var5.getItem()).damageReduceAmount;
                var1 += var6;
            }
        }
        return var1;
    }
    
    protected void damageArmor(final int par1) {
    }
    
    protected int applyArmorCalculations(final DamageSource par1DamageSource, int par2) {
        if (!par1DamageSource.isUnblockable()) {
            final int var3 = 25 - this.getTotalArmorValue();
            final int var4 = par2 * var3 + this.carryoverDamage;
            this.damageArmor(par2);
            par2 = var4 / 25;
            this.carryoverDamage = var4 % 25;
        }
        return par2;
    }
    
    protected int applyPotionDamageCalculations(final DamageSource par1DamageSource, int par2) {
        if (this.isPotionActive(Potion.resistance)) {
            final int var3 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            final int var4 = 25 - var3;
            final int var5 = par2 * var4 + this.carryoverDamage;
            par2 = var5 / 25;
            this.carryoverDamage = var5 % 25;
        }
        if (par2 <= 0) {
            return 0;
        }
        int var3 = EnchantmentHelper.getEnchantmentModifierDamage(this.getLastActiveItems(), par1DamageSource);
        if (var3 > 20) {
            var3 = 20;
        }
        if (var3 > 0 && var3 <= 20) {
            final int var4 = 25 - var3;
            final int var5 = par2 * var4 + this.carryoverDamage;
            par2 = var5 / 25;
            this.carryoverDamage = var5 % 25;
        }
        return par2;
    }
    
    protected void damageEntity(final DamageSource par1DamageSource, int par2) {
        if (!this.isEntityInvulnerable()) {
            if (Reflector.ForgeHooks_onLivingHurt.exists()) {
                par2 = Reflector.callInt(Reflector.ForgeHooks_onLivingHurt, this, par1DamageSource, par2);
                if (par2 <= 0) {
                    return;
                }
            }
            par2 = this.applyArmorCalculations(par1DamageSource, par2);
            par2 = this.applyPotionDamageCalculations(par1DamageSource, par2);
            final int var3 = this.getHealth();
            this.health -= par2;
            this.field_94063_bt.func_94547_a(par1DamageSource, var3, par2);
        }
    }
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected String getLivingSound() {
        return null;
    }
    
    protected String getHurtSound() {
        return "damage.hit";
    }
    
    protected String getDeathSound() {
        return "damage.hit";
    }
    
    public void knockBack(final Entity par1Entity, final int par2, final double par3, final double par5) {
        this.isAirBorne = true;
        final float var7 = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
        final float var8 = 0.4f;
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
        this.motionX -= par3 / var7 * var8;
        this.motionY += var8;
        this.motionZ -= par5 / var7 * var8;
        if (this.motionY > 0.4000000059604645) {
            this.motionY = 0.4000000059604645;
        }
    }
    
    public void onDeath(final DamageSource par1DamageSource) {
        if (!Reflector.ForgeHooks_onLivingDeath.exists() || !Reflector.callBoolean(Reflector.ForgeHooks_onLivingDeath, this, par1DamageSource)) {
            final Entity var2 = par1DamageSource.getEntity();
            final EntityLiving var3 = this.func_94060_bK();
            if (this.scoreValue >= 0 && var3 != null) {
                var3.addToPlayerScore(this, this.scoreValue);
            }
            if (var2 != null) {
                var2.onKillEntity(this);
            }
            this.dead = true;
            if (!this.worldObj.isRemote) {
                int var4 = 0;
                if (var2 instanceof EntityPlayer) {
                    var4 = EnchantmentHelper.getLootingModifier((EntityLiving)var2);
                }
                ArrayList var5 = null;
                if (Reflector.ForgeEntity_captureDrops.exists()) {
                    Reflector.setFieldValue(this, Reflector.ForgeEntity_captureDrops, Boolean.TRUE);
                    var5 = (ArrayList)Reflector.getFieldValue(this, Reflector.ForgeEntity_capturedDrops);
                    var5.clear();
                }
                int var6 = 0;
                if (!this.isChild() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                    this.dropFewItems(this.recentlyHit > 0, var4);
                    this.dropEquipment(this.recentlyHit > 0, var4);
                    if (this.recentlyHit > 0) {
                        var6 = this.rand.nextInt(200) - var4;
                        if (var6 < 5) {
                            this.dropRareDrop((var6 <= 0) ? 1 : 0);
                        }
                    }
                }
                if (Reflector.ForgeEntity_captureDrops.exists()) {
                    Reflector.setFieldValue(this, Reflector.ForgeEntity_captureDrops, Boolean.FALSE);
                    if (!Reflector.callBoolean(Reflector.ForgeHooks_onLivingDrops, this, par1DamageSource, var5, var4, this.recentlyHit > 0, var6)) {
                        for (final EntityItem var8 : var5) {
                            this.worldObj.spawnEntityInWorld(var8);
                        }
                    }
                }
            }
            this.worldObj.setEntityState(this, (byte)3);
        }
    }
    
    protected void dropRareDrop(final int par1) {
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int var3 = this.getDropItemId();
        if (var3 > 0) {
            int var4 = this.rand.nextInt(3);
            if (par2 > 0) {
                var4 += this.rand.nextInt(par2 + 1);
            }
            for (int var5 = 0; var5 < var4; ++var5) {
                this.dropItem(var3, 1);
            }
        }
    }
    
    protected int getDropItemId() {
        return 0;
    }
    
    @Override
    protected void fall(float par1) {
        if (Reflector.ForgeHooks_onLivingFall.exists()) {
            par1 = Reflector.callFloat(Reflector.ForgeHooks_onLivingFall, this, par1);
            if (par1 <= 0.0f) {
                return;
            }
        }
        super.fall(par1);
        final int var2 = MathHelper.ceiling_float_int(par1 - 3.0f);
        if (var2 > 0) {
            if (var2 > 4) {
                this.playSound("damage.fallbig", 1.0f, 1.0f);
            }
            else {
                this.playSound("damage.fallsmall", 1.0f, 1.0f);
            }
            this.attackEntityFrom(DamageSource.fall, var2);
            final int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset), MathHelper.floor_double(this.posZ));
            if (var3 > 0) {
                final StepSound var4 = Block.blocksList[var3].stepSound;
                this.playSound(var4.getStepSound(), var4.getVolume() * 0.5f, var4.getPitch() * 0.75f);
            }
        }
    }
    
    public void moveEntityWithHeading(final float par1, final float par2) {
        if (this.isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            final double var3 = this.posY;
            this.moveFlying(par1, par2, this.isAIEnabled() ? 0.04f : 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + var3, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        }
        else if (this.handleLavaMovement() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            final double var3 = this.posY;
            this.moveFlying(par1, par2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + var3, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        }
        else {
            float var4 = 0.91f;
            if (this.onGround) {
                var4 = 0.54600006f;
                final int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var5 > 0) {
                    var4 = Block.blocksList[var5].slipperiness * 0.91f;
                }
            }
            final float var6 = 0.16277136f / (var4 * var4 * var4);
            float var7;
            if (this.onGround) {
                if (this.isAIEnabled()) {
                    var7 = this.getAIMoveSpeed();
                }
                else {
                    var7 = this.landMovementFactor;
                }
                var7 *= var6;
            }
            else {
                var7 = this.jumpMovementFactor;
            }
            this.moveFlying(par1, par2, var7);
            var4 = 0.91f;
            if (this.onGround) {
                var4 = 0.54600006f;
                final int var8 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var8 > 0) {
                    var4 = Block.blocksList[var8].slipperiness * 0.91f;
                }
            }
            if (this.isOnLadder()) {
                final float var9 = 0.15f;
                if (this.motionX < -var9) {
                    this.motionX = -var9;
                }
                if (this.motionX > var9) {
                    this.motionX = var9;
                }
                if (this.motionZ < -var9) {
                    this.motionZ = -var9;
                }
                if (this.motionZ > var9) {
                    this.motionZ = var9;
                }
                this.fallDistance = 0.0f;
                if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }
                final boolean var10 = this.isSneaking() && this instanceof EntityPlayer;
                if (var10 && this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
            }
            if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)) {
                if (this.posY > 0.0) {
                    this.motionY = -0.1;
                }
                else {
                    this.motionY = 0.0;
                }
            }
            else {
                this.motionY -= 0.08;
            }
            this.motionY *= 0.9800000190734863;
            this.motionX *= var4;
            this.motionZ *= var4;
        }
        this.prevLimbYaw = this.limbYaw;
        final double var3 = this.posX - this.prevPosX;
        final double var11 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var3 * var3 + var11 * var11) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.limbYaw += (var7 - this.limbYaw) * 0.4f;
        this.limbSwing += this.limbYaw;
    }
    
    public boolean isOnLadder() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        final int var4 = this.worldObj.getBlockId(var1, var2, var3);
        return Reflector.ForgeHooks_isLivingOnLadder.exists() ? Reflector.callBoolean(Reflector.ForgeHooks_isLivingOnLadder, Block.blocksList[var4], this.worldObj, var1, var2, var3) : (var4 == Block.ladder.blockID || var4 == Block.vine.blockID);
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        if (this.health < -32768) {
            this.health = -32768;
        }
        par1NBTTagCompound.setShort("Health", (short)this.health);
        par1NBTTagCompound.setShort("HurtTime", (short)this.hurtTime);
        par1NBTTagCompound.setShort("DeathTime", (short)this.deathTime);
        par1NBTTagCompound.setShort("AttackTime", (short)this.attackTime);
        par1NBTTagCompound.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        par1NBTTagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.equipment.length; ++var3) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.equipment[var3] != null) {
                this.equipment[var3].writeToNBT(var4);
            }
            var2.appendTag(var4);
        }
        par1NBTTagCompound.setTag("Equipment", var2);
        if (!this.activePotionsMap.isEmpty()) {
            final NBTTagList var5 = new NBTTagList();
            for (final PotionEffect var7 : this.activePotionsMap.values()) {
                var5.appendTag(var7.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            par1NBTTagCompound.setTag("ActiveEffects", var5);
        }
        final NBTTagList var5 = new NBTTagList();
        for (int var8 = 0; var8 < this.equipmentDropChances.length; ++var8) {
            var5.appendTag(new NBTTagFloat(new StringBuilder(String.valueOf(var8)).toString(), this.equipmentDropChances[var8]));
        }
        par1NBTTagCompound.setTag("DropChances", var5);
        par1NBTTagCompound.setString("CustomName", this.func_94057_bL());
        par1NBTTagCompound.setBoolean("CustomNameVisible", this.func_94062_bN());
        par1NBTTagCompound.setInteger("PersistentId", this.persistentId);
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.health = par1NBTTagCompound.getShort("Health");
        if (!par1NBTTagCompound.hasKey("Health")) {
            this.health = this.getMaxHealth();
        }
        this.hurtTime = par1NBTTagCompound.getShort("HurtTime");
        this.deathTime = par1NBTTagCompound.getShort("DeathTime");
        this.attackTime = par1NBTTagCompound.getShort("AttackTime");
        this.setCanPickUpLoot(par1NBTTagCompound.getBoolean("CanPickUpLoot"));
        this.persistenceRequired = par1NBTTagCompound.getBoolean("PersistenceRequired");
        if (par1NBTTagCompound.hasKey("CustomName") && par1NBTTagCompound.getString("CustomName").length() > 0) {
            this.func_94058_c(par1NBTTagCompound.getString("CustomName"));
        }
        this.func_94061_f(par1NBTTagCompound.getBoolean("CustomNameVisible"));
        if (par1NBTTagCompound.hasKey("Equipment")) {
            final NBTTagList var2 = par1NBTTagCompound.getTagList("Equipment");
            for (int var3 = 0; var3 < this.equipment.length; ++var3) {
                this.equipment[var3] = ItemStack.loadItemStackFromNBT((NBTTagCompound)var2.tagAt(var3));
            }
        }
        if (par1NBTTagCompound.hasKey("ActiveEffects")) {
            final NBTTagList var2 = par1NBTTagCompound.getTagList("ActiveEffects");
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
                final PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
                this.activePotionsMap.put(var5.getPotionID(), var5);
            }
        }
        if (par1NBTTagCompound.hasKey("DropChances")) {
            final NBTTagList var2 = par1NBTTagCompound.getTagList("DropChances");
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.equipmentDropChances[var3] = ((NBTTagFloat)var2.tagAt(var3)).data;
            }
        }
        this.persistentId = par1NBTTagCompound.getInteger("PersistentId");
        if (this.persistentId == 0) {
            this.persistentId = this.rand.nextInt(Integer.MAX_VALUE);
        }
    }
    
    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.health > 0;
    }
    
    public boolean canBreatheUnderwater() {
        return false;
    }
    
    public void setMoveForward(final float par1) {
        this.moveForward = par1;
    }
    
    public void setJumping(final boolean par1) {
        this.isJumping = par1;
    }
    
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }
        if (this.newPosRotationIncrements > 0) {
            final double var1 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            final double var2 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            final double var3 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            final double var4 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
            this.rotationYaw += (float)(var4 / this.newPosRotationIncrements);
            this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(var1, var2, var3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (!this.isClientWorld()) {
            this.motionX *= 0.98;
            this.motionY *= 0.98;
            this.motionZ *= 0.98;
        }
        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }
        this.worldObj.theProfiler.startSection("ai");
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        }
        else if (this.isClientWorld()) {
            if (this.isAIEnabled()) {
                this.worldObj.theProfiler.startSection("newAi");
                this.updateAITasks();
                this.worldObj.theProfiler.endSection();
            }
            else {
                this.worldObj.theProfiler.startSection("oldAi");
                this.updateEntityActionState();
                this.worldObj.theProfiler.endSection();
                this.rotationYawHead = this.rotationYaw;
            }
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");
        if (this.isJumping) {
            if (!this.isInWater() && !this.handleLavaMovement()) {
                if (this.onGround && this.jumpTicks == 0) {
                    this.jump();
                    this.jumpTicks = 10;
                }
            }
            else {
                this.motionY += 0.03999999910593033;
            }
        }
        else {
            this.jumpTicks = 0;
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        final float var5 = this.landMovementFactor;
        this.landMovementFactor *= this.getSpeedModifier();
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.landMovementFactor = var5;
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");
        if (!this.worldObj.isRemote) {
            this.func_85033_bc();
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("looting");
        if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            final List var6 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0, 0.0, 1.0));
            for (final EntityItem var8 : var6) {
                if (!var8.isDead && var8.getEntityItem() != null) {
                    final ItemStack var9 = var8.getEntityItem();
                    final int var10 = getArmorPosition(var9);
                    if (var10 <= -1) {
                        continue;
                    }
                    boolean var11 = true;
                    final ItemStack var12 = this.getCurrentItemOrArmor(var10);
                    if (var12 != null) {
                        if (var10 == 0) {
                            if (var9.getItem() instanceof ItemSword && !(var12.getItem() instanceof ItemSword)) {
                                var11 = true;
                            }
                            else if (var9.getItem() instanceof ItemSword && var12.getItem() instanceof ItemSword) {
                                final ItemSword var13 = (ItemSword)var9.getItem();
                                final ItemSword var14 = (ItemSword)var12.getItem();
                                if (var13.func_82803_g() == var14.func_82803_g()) {
                                    var11 = (var9.getItemDamage() > var12.getItemDamage() || (var9.hasTagCompound() && !var12.hasTagCompound()));
                                }
                                else {
                                    var11 = (var13.func_82803_g() > var14.func_82803_g());
                                }
                            }
                            else {
                                var11 = false;
                            }
                        }
                        else if (var9.getItem() instanceof ItemArmor && !(var12.getItem() instanceof ItemArmor)) {
                            var11 = true;
                        }
                        else if (var9.getItem() instanceof ItemArmor && var12.getItem() instanceof ItemArmor) {
                            final ItemArmor var15 = (ItemArmor)var9.getItem();
                            final ItemArmor var16 = (ItemArmor)var12.getItem();
                            if (var15.damageReduceAmount == var16.damageReduceAmount) {
                                var11 = (var9.getItemDamage() > var12.getItemDamage() || (var9.hasTagCompound() && !var12.hasTagCompound()));
                            }
                            else {
                                var11 = (var15.damageReduceAmount > var16.damageReduceAmount);
                            }
                        }
                        else {
                            var11 = false;
                        }
                    }
                    if (!var11) {
                        continue;
                    }
                    if (var12 != null && this.rand.nextFloat() - 0.1f < this.equipmentDropChances[var10]) {
                        this.entityDropItem(var12, 0.0f);
                    }
                    this.setCurrentItemOrArmor(var10, var9);
                    this.equipmentDropChances[var10] = 2.0f;
                    this.persistenceRequired = true;
                    this.onItemPickup(var8, 1);
                    var8.setDead();
                }
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void func_85033_bc() {
        final List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
        if (var1 != null && !var1.isEmpty()) {
            for (int var2 = 0; var2 < var1.size(); ++var2) {
                final Entity var3 = var1.get(var2);
                if (var3.canBePushed()) {
                    this.collideWithEntity(var3);
                }
            }
        }
    }
    
    protected void collideWithEntity(final Entity par1Entity) {
        par1Entity.applyEntityCollision(this);
    }
    
    protected boolean isAIEnabled() {
        return false;
    }
    
    protected boolean isClientWorld() {
        return !this.worldObj.isRemote;
    }
    
    protected boolean isMovementBlocked() {
        return this.health <= 0;
    }
    
    public boolean isBlocking() {
        return false;
    }
    
    protected void jump() {
        this.motionY = 0.41999998688697815;
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float var1 = this.rotationYaw * 0.017453292f;
            this.motionX -= MathHelper.sin(var1) * 0.2f;
            this.motionZ += MathHelper.cos(var1) * 0.2f;
        }
        this.isAirBorne = true;
        if (Reflector.ForgeHooks_onLivingJump.exists()) {
            Reflector.callVoid(Reflector.ForgeHooks_onLivingJump, this);
        }
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    protected void despawnEntity() {
        if (!this.persistenceRequired) {
            final EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
            if (var1 != null) {
                final double var2 = var1.posX - this.posX;
                final double var3 = var1.posY - this.posY;
                final double var4 = var1.posZ - this.posZ;
                final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
                if (this.canDespawn() && var5 > 16384.0) {
                    this.setDead();
                }
                if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var5 > 1024.0 && this.canDespawn()) {
                    this.setDead();
                }
                else if (var5 < 1024.0) {
                    this.entityAge = 0;
                }
            }
        }
    }
    
    protected void updateAITasks() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITick();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }
    
    protected void updateAITick() {
    }
    
    protected void updateEntityActionState() {
        ++this.entityAge;
        this.despawnEntity();
        this.moveStrafing = 0.0f;
        this.moveForward = 0.0f;
        final float var1 = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            final EntityPlayer var2 = this.worldObj.getClosestPlayerToEntity(this, var1);
            if (var2 != null) {
                this.currentTarget = var2;
                this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
            }
            else {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.currentTarget != null) {
            this.faceEntity(this.currentTarget, 10.0f, this.getVerticalFaceSpeed());
            if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > var1 * var1) {
                this.currentTarget = null;
            }
        }
        else {
            if (this.rand.nextFloat() < 0.05f) {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
            this.rotationYaw += this.randomYawVelocity;
            this.rotationPitch = this.defaultPitch;
        }
        final boolean var3 = this.isInWater();
        final boolean var4 = this.handleLavaMovement();
        if (var3 || var4) {
            this.isJumping = (this.rand.nextFloat() < 0.8f);
        }
    }
    
    protected void updateArmSwingProgress() {
        final int var1 = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
            if (this.swingProgressInt >= var1) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = this.swingProgressInt / var1;
    }
    
    public int getVerticalFaceSpeed() {
        return 40;
    }
    
    public void faceEntity(final Entity par1Entity, final float par2, final float par3) {
        final double var4 = par1Entity.posX - this.posX;
        final double var5 = par1Entity.posZ - this.posZ;
        double var7;
        if (par1Entity instanceof EntityLiving) {
            final EntityLiving var6 = (EntityLiving)par1Entity;
            var7 = var6.posY + var6.getEyeHeight() - (this.posY + this.getEyeHeight());
        }
        else {
            var7 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0 - (this.posY + this.getEyeHeight());
        }
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7, var8) * 180.0 / 3.141592653589793));
        this.rotationPitch = this.updateRotation(this.rotationPitch, var10, par3);
        this.rotationYaw = this.updateRotation(this.rotationYaw, var9, par2);
    }
    
    private float updateRotation(final float par1, final float par2, final float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < -par3) {
            var4 = -par3;
        }
        return par1 + var4;
    }
    
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    @Override
    protected void kill() {
        this.attackEntityFrom(DamageSource.outOfWorld, 4);
    }
    
    public float getSwingProgress(final float par1) {
        float var2 = this.swingProgress - this.prevSwingProgress;
        if (var2 < 0.0f) {
            ++var2;
        }
        return this.prevSwingProgress + var2 * par1;
    }
    
    public Vec3 getPosition(final float par1) {
        if (par1 == 1.0f) {
            return this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        }
        final double var2 = this.prevPosX + (this.posX - this.prevPosX) * par1;
        final double var3 = this.prevPosY + (this.posY - this.prevPosY) * par1;
        final double var4 = this.prevPosZ + (this.posZ - this.prevPosZ) * par1;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var2, var3, var4);
    }
    
    @Override
    public Vec3 getLookVec() {
        return this.getLook(1.0f);
    }
    
    public Vec3 getLook(final float par1) {
        if (par1 == 1.0f) {
            final float var2 = MathHelper.cos(-this.rotationYaw * 0.017453292f - 3.1415927f);
            final float var3 = MathHelper.sin(-this.rotationYaw * 0.017453292f - 3.1415927f);
            final float var4 = -MathHelper.cos(-this.rotationPitch * 0.017453292f);
            final float var5 = MathHelper.sin(-this.rotationPitch * 0.017453292f);
            return this.worldObj.getWorldVec3Pool().getVecFromPool(var3 * var4, var5, var2 * var4);
        }
        final float var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * par1;
        final float var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * par1;
        final float var4 = MathHelper.cos(-var3 * 0.017453292f - 3.1415927f);
        final float var5 = MathHelper.sin(-var3 * 0.017453292f - 3.1415927f);
        final float var6 = -MathHelper.cos(-var2 * 0.017453292f);
        final float var7 = MathHelper.sin(-var2 * 0.017453292f);
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var5 * var6, var7, var4 * var6);
    }
    
    public float getRenderSizeModifier() {
        return 1.0f;
    }
    
    public MovingObjectPosition rayTrace(final double par1, final float par3) {
        final Vec3 var4 = this.getPosition(par3);
        final Vec3 var5 = this.getLook(par3);
        final Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
        return this.worldObj.rayTraceBlocks(var4, var6);
    }
    
    public int getMaxSpawnedInChunk() {
        return 4;
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 2) {
            this.limbYaw = 1.5f;
            this.hurtResistantTime = this.maxHurtResistantTime;
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
            this.attackedAtYaw = 0.0f;
            this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.attackEntityFrom(DamageSource.generic, 0);
        }
        else if (par1 == 3) {
            this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.health = 0;
            this.onDeath(DamageSource.generic);
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public boolean isPlayerSleeping() {
        return false;
    }
    
    public Icon getItemIcon(final ItemStack par1ItemStack, final int par2) {
        return par1ItemStack.getIconIndex();
    }
    
    protected void updatePotionEffects() {
        final Iterator var1 = this.activePotionsMap.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.activePotionsMap.get(var2);
            try {
                if (!var3.onUpdate(this)) {
                    if (this.worldObj.isRemote) {
                        continue;
                    }
                    var1.remove();
                    this.onFinishedPotionEffect(var3);
                }
                else {
                    if (var3.getDuration() % 600 != 0) {
                        continue;
                    }
                    this.onChangedPotionEffect(var3);
                }
            }
            catch (Throwable var5) {
                final CrashReport var4 = CrashReport.makeCrashReport(var5, "Ticking mob effect instance");
                final CrashReportCategory var6 = var4.makeCategory("Mob effect being ticked");
                var6.addCrashSectionCallable("Effect Name", new CallableEffectName(this, var3));
                var6.addCrashSectionCallable("Effect ID", new CallableEffectID(this, var3));
                var6.addCrashSectionCallable("Effect Duration", new CallableEffectDuration(this, var3));
                var6.addCrashSectionCallable("Effect Amplifier", new CallableEffectAmplifier(this, var3));
                var6.addCrashSectionCallable("Effect is Splash", new CallableEffectIsSplash(this, var3));
                var6.addCrashSectionCallable("Effect is Ambient", new CallableEffectIsAmbient(this, var3));
                throw new ReportedException(var4);
            }
        }
        if (this.potionsNeedUpdate) {
            if (!this.worldObj.isRemote) {
                if (this.activePotionsMap.isEmpty()) {
                    this.dataWatcher.updateObject(9, (byte)0);
                    this.dataWatcher.updateObject(8, 0);
                    this.setInvisible(false);
                }
                else {
                    final int var7 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
                    this.dataWatcher.updateObject(9, (byte)(byte)(PotionHelper.func_82817_b(this.activePotionsMap.values()) ? 1 : 0));
                    this.dataWatcher.updateObject(8, var7);
                    this.setInvisible(this.isPotionActive(Potion.invisibility.id));
                }
            }
            this.potionsNeedUpdate = false;
        }
        final int var7 = this.dataWatcher.getWatchableObjectInt(8);
        final boolean var8 = this.dataWatcher.getWatchableObjectByte(9) > 0;
        if (var7 > 0) {
            boolean var9 = false;
            if (!this.isInvisible()) {
                var9 = this.rand.nextBoolean();
            }
            else {
                var9 = (this.rand.nextInt(15) == 0);
            }
            if (var8) {
                var9 &= (this.rand.nextInt(5) == 0);
            }
            if (var9 && var7 > 0) {
                final double var10 = (var7 >> 16 & 0xFF) / 255.0;
                final double var11 = (var7 >> 8 & 0xFF) / 255.0;
                final double var12 = (var7 >> 0 & 0xFF) / 255.0;
                this.worldObj.spawnParticle(var8 ? "mobSpellAmbient" : "mobSpell", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - this.yOffset, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, var10, var11, var12);
            }
        }
    }
    
    public void clearActivePotions() {
        final Iterator var1 = this.activePotionsMap.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.activePotionsMap.get(var2);
            if (!this.worldObj.isRemote) {
                var1.remove();
                this.onFinishedPotionEffect(var3);
            }
        }
    }
    
    public Collection getActivePotionEffects() {
        return this.activePotionsMap.values();
    }
    
    public boolean isPotionActive(final int par1) {
        return this.activePotionsMap.containsKey(par1);
    }
    
    public boolean isPotionActive(final Potion par1Potion) {
        return this.activePotionsMap.containsKey(par1Potion.id);
    }
    
    public PotionEffect getActivePotionEffect(final Potion par1Potion) {
        return this.activePotionsMap.get(par1Potion.id);
    }
    
    public void addPotionEffect(final PotionEffect par1PotionEffect) {
        if (this.isPotionApplicable(par1PotionEffect)) {
            if (this.activePotionsMap.containsKey(par1PotionEffect.getPotionID())) {
                this.activePotionsMap.get(par1PotionEffect.getPotionID()).combine(par1PotionEffect);
                this.onChangedPotionEffect(this.activePotionsMap.get(par1PotionEffect.getPotionID()));
            }
            else {
                this.activePotionsMap.put(par1PotionEffect.getPotionID(), par1PotionEffect);
                this.onNewPotionEffect(par1PotionEffect);
            }
        }
    }
    
    public boolean isPotionApplicable(final PotionEffect par1PotionEffect) {
        if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            final int var2 = par1PotionEffect.getPotionID();
            if (var2 == Potion.regeneration.id || var2 == Potion.poison.id) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
    }
    
    public void removePotionEffectClient(final int par1) {
        this.activePotionsMap.remove(par1);
    }
    
    public void removePotionEffect(final int par1) {
        final PotionEffect var2 = this.activePotionsMap.remove(par1);
        if (var2 != null) {
            this.onFinishedPotionEffect(var2);
        }
    }
    
    protected void onNewPotionEffect(final PotionEffect par1PotionEffect) {
        this.potionsNeedUpdate = true;
    }
    
    protected void onChangedPotionEffect(final PotionEffect par1PotionEffect) {
        this.potionsNeedUpdate = true;
    }
    
    protected void onFinishedPotionEffect(final PotionEffect par1PotionEffect) {
        this.potionsNeedUpdate = true;
    }
    
    public float getSpeedModifier() {
        float var1 = 1.0f;
        if (this.isPotionActive(Potion.moveSpeed)) {
            var1 *= 1.0f + 0.2f * (this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        if (this.isPotionActive(Potion.moveSlowdown)) {
            var1 *= 1.0f - 0.15f * (this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (var1 < 0.0f) {
            var1 = 0.0f;
        }
        return var1;
    }
    
    public void setPositionAndUpdate(final double par1, final double par3, final double par5) {
        this.setLocationAndAngles(par1, par3, par5, this.rotationYaw, this.rotationPitch);
    }
    
    public boolean isChild() {
        return false;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public void renderBrokenItemStack(final ItemStack par1ItemStack) {
        this.playSound("random.break", 0.8f, 0.8f + this.worldObj.rand.nextFloat() * 0.4f);
        for (int var2 = 0; var2 < 5; ++var2) {
            final Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            var3.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
            var3.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
            Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6);
            var4.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
            var4.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
            var4 = var4.addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            this.worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, var4.xCoord, var4.yCoord, var4.zCoord, var3.xCoord, var3.yCoord + 0.05, var3.zCoord);
        }
    }
    
    public void curePotionEffects(final ItemStack var1) {
        final Iterator var2 = this.activePotionsMap.keySet().iterator();
        if (!this.worldObj.isRemote) {
            while (var2.hasNext()) {
                final Integer var3 = var2.next();
                final PotionEffect var4 = this.activePotionsMap.get(var3);
                if (Reflector.callBoolean(var4, Reflector.ForgePotionEffect_isCurativeItem, var1)) {
                    var2.remove();
                    this.onFinishedPotionEffect(var4);
                }
            }
        }
    }
    
    public boolean shouldRiderFaceForward(final EntityPlayer var1) {
        return this instanceof EntityPig;
    }
    
    @Override
    public int func_82143_as() {
        if (this.getAttackTarget() == null) {
            return 3;
        }
        int var1 = (int)(this.health - this.getMaxHealth() * 0.33f);
        var1 -= (3 - this.worldObj.difficultySetting) * 4;
        if (var1 < 0) {
            var1 = 0;
        }
        return var1 + 3;
    }
    
    public ItemStack getHeldItem() {
        return this.equipment[0];
    }
    
    public ItemStack getCurrentItemOrArmor(final int par1) {
        return this.equipment[par1];
    }
    
    public ItemStack getCurrentArmor(final int par1) {
        return this.equipment[par1 + 1];
    }
    
    @Override
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
        this.equipment[par1] = par2ItemStack;
    }
    
    @Override
    public ItemStack[] getLastActiveItems() {
        return this.equipment;
    }
    
    protected void dropEquipment(final boolean par1, final int par2) {
        for (int var3 = 0; var3 < this.getLastActiveItems().length; ++var3) {
            final ItemStack var4 = this.getCurrentItemOrArmor(var3);
            final boolean var5 = this.equipmentDropChances[var3] > 1.0f;
            if (var4 != null && (par1 || var5) && this.rand.nextFloat() - par2 * 0.01f < this.equipmentDropChances[var3]) {
                if (!var5 && var4.isItemStackDamageable()) {
                    final int var6 = Math.max(var4.getMaxDamage() - 25, 1);
                    int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
                    if (var7 > var6) {
                        var7 = var6;
                    }
                    if (var7 < 1) {
                        var7 = 1;
                    }
                    var4.setItemDamage(var7);
                }
                this.entityDropItem(var4, 0.0f);
            }
        }
    }
    
    protected void addRandomArmor() {
        if (this.rand.nextFloat() < EntityLiving.armorProbability[this.worldObj.difficultySetting]) {
            int var1 = this.rand.nextInt(2);
            final float var2 = (this.worldObj.difficultySetting == 3) ? 0.1f : 0.25f;
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            for (int var3 = 3; var3 >= 0; --var3) {
                final ItemStack var4 = this.getCurrentArmor(var3);
                if (var3 < 3 && this.rand.nextFloat() < var2) {
                    break;
                }
                if (var4 == null) {
                    final Item var5 = getArmorItemForSlot(var3 + 1, var1);
                    if (var5 != null) {
                        this.setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
                    }
                }
            }
        }
    }
    
    public void onItemPickup(final Entity par1Entity, final int par2) {
        if (!par1Entity.isDead && !this.worldObj.isRemote) {
            final EntityTracker var3 = ((WorldServer)this.worldObj).getEntityTracker();
            if (par1Entity instanceof EntityItem) {
                var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, this.entityId));
            }
            if (par1Entity instanceof EntityArrow) {
                var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, this.entityId));
            }
            if (par1Entity instanceof EntityXPOrb) {
                var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, this.entityId));
            }
        }
    }
    
    public static int getArmorPosition(final ItemStack par0ItemStack) {
        if (par0ItemStack.itemID != Block.pumpkin.blockID && par0ItemStack.itemID != Item.skull.itemID) {
            if (par0ItemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)par0ItemStack.getItem()).armorType) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public static Item getArmorItemForSlot(final int par0, final int par1) {
        switch (par0) {
            case 4: {
                if (par1 == 0) {
                    return Item.helmetLeather;
                }
                if (par1 == 1) {
                    return Item.helmetGold;
                }
                if (par1 == 2) {
                    return Item.helmetChain;
                }
                if (par1 == 3) {
                    return Item.helmetIron;
                }
                if (par1 == 4) {
                    return Item.helmetDiamond;
                }
            }
            case 3: {
                if (par1 == 0) {
                    return Item.plateLeather;
                }
                if (par1 == 1) {
                    return Item.plateGold;
                }
                if (par1 == 2) {
                    return Item.plateChain;
                }
                if (par1 == 3) {
                    return Item.plateIron;
                }
                if (par1 == 4) {
                    return Item.plateDiamond;
                }
            }
            case 2: {
                if (par1 == 0) {
                    return Item.legsLeather;
                }
                if (par1 == 1) {
                    return Item.legsGold;
                }
                if (par1 == 2) {
                    return Item.legsChain;
                }
                if (par1 == 3) {
                    return Item.legsIron;
                }
                if (par1 == 4) {
                    return Item.legsDiamond;
                }
            }
            case 1: {
                if (par1 == 0) {
                    return Item.bootsLeather;
                }
                if (par1 == 1) {
                    return Item.bootsGold;
                }
                if (par1 == 2) {
                    return Item.bootsChain;
                }
                if (par1 == 3) {
                    return Item.bootsIron;
                }
                if (par1 == 4) {
                    return Item.bootsDiamond;
                }
                break;
            }
        }
        return null;
    }
    
    protected void func_82162_bC() {
        if (this.getHeldItem() != null && this.rand.nextFloat() < EntityLiving.enchantmentProbability[this.worldObj.difficultySetting]) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), 5 + this.worldObj.difficultySetting * this.rand.nextInt(6));
        }
        for (int var1 = 0; var1 < 4; ++var1) {
            final ItemStack var2 = this.getCurrentArmor(var1);
            if (var2 != null && this.rand.nextFloat() < EntityLiving.armorEnchantmentProbability[this.worldObj.difficultySetting]) {
                EnchantmentHelper.addRandomEnchantment(this.rand, var2, 5 + this.worldObj.difficultySetting * this.rand.nextInt(6));
            }
        }
    }
    
    public void initCreature() {
    }
    
    private int getArmSwingAnimationEnd() {
        return this.isPotionActive(Potion.digSpeed) ? (6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (this.isPotionActive(Potion.digSlowdown) ? (6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
    }
    
    public void swingItem() {
        final ItemStack var1 = this.getHeldItem();
        if (var1 != null && var1.getItem() != null) {
            final Item var2 = var1.getItem();
            if (Reflector.callBoolean(var2, Reflector.ForgeItem_onEntitySwing, this, var1)) {
                return;
            }
        }
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet18Animation(this, 1));
            }
        }
    }
    
    public boolean canBeSteered() {
        return false;
    }
    
    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(10);
    }
    
    public final void setArrowCountInEntity(final int par1) {
        this.dataWatcher.updateObject(10, (byte)par1);
    }
    
    public EntityLiving func_94060_bK() {
        return (this.field_94063_bt.func_94550_c() != null) ? this.field_94063_bt.func_94550_c() : ((this.attackingPlayer != null) ? this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
    }
    
    @Override
    public String getEntityName() {
        return this.func_94056_bM() ? this.func_94057_bL() : super.getEntityName();
    }
    
    public void func_94058_c(final String par1Str) {
        this.dataWatcher.updateObject(5, par1Str);
    }
    
    public String func_94057_bL() {
        return this.dataWatcher.getWatchableObjectString(5);
    }
    
    public boolean func_94056_bM() {
        return this.dataWatcher.getWatchableObjectString(5).length() > 0;
    }
    
    public void func_94061_f(final boolean par1) {
        this.dataWatcher.updateObject(6, (byte)(byte)(par1 ? 1 : 0));
    }
    
    public boolean func_94062_bN() {
        return this.dataWatcher.getWatchableObjectByte(6) == 1;
    }
    
    public boolean func_94059_bO() {
        return this.func_94062_bN();
    }
    
    public void func_96120_a(final int par1, final float par2) {
        this.equipmentDropChances[par1] = par2;
    }
    
    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }
    
    public void setCanPickUpLoot(final boolean par1) {
        this.canPickUpLoot = par1;
    }
    
    public boolean func_104002_bU() {
        return this.persistenceRequired;
    }
}
