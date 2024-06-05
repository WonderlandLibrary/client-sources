package net.minecraft.entity;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.JumpEvent;
import cc.novoline.events.events.LivingUpdateEvent;
import cc.novoline.modules.visual.Animations;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.EJ;
import net.minecraft.block.Block;
import net.minecraft.block.Block$SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.jetbrains.annotations.NotNull;

public abstract class EntityLivingBase extends Entity {
   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier sprintingSpeedBoostModifier = (new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
   private BaseAttributeMap attributeMap;
   private final CombatTracker _combatTracker = new CombatTracker(this);
   private final Map activePotionsMap = Maps.newHashMap();
   private final ItemStack[] previousEquipment = new ItemStack[5];
   public boolean isSwingInProgress;
   public int swingProgressInt;
   public int arrowHitTimer;
   public int hurtTime;
   public int maxHurtTime;
   public float attackedAtYaw;
   public int deathTime;
   public float prevSwingProgress;
   public float swingProgress;
   public float prevLimbSwingAmount;
   public float limbSwingAmount;
   public float limbSwing;
   public int maxHurtResistantTime = 20;
   public float prevCameraPitch;
   public float cameraPitch;
   public float renderYawOffset;
   public float prevRenderYawOffset;
   public float rotationYawHead;
   public float rotationPitchHead;
   public float prevRotationYawHead;
   public float prevRotationPitchHead;
   public float jumpMovementFactor = 0.02F;
   protected EntityPlayer attackingPlayer;
   protected int recentlyHit;
   protected boolean dead;
   protected boolean pushed;
   protected int entityAge;
   protected float prevOnGroundSpeedFactor;
   protected float onGroundSpeedFactor;
   protected float movedDistance;
   protected float prevMovedDistance;
   protected int scoreValue;
   public float lastDamage;
   protected boolean isJumping;
   public float moveStrafing;
   public float moveForward;
   protected float randomYawVelocity;
   protected int newPosRotationIncrements;
   protected double newPosX;
   protected double newPosY;
   protected double newPosZ;
   protected double newRotationYaw;
   protected double newRotationPitch;
   private boolean potionsNeedUpdate = true;
   private EntityLivingBase entityLivingToAttack;
   private int revengeTimer;
   private EntityLivingBase lastAttacker;
   private int lastAttackerTime;
   private float landMovementFactor;
   private int jumpTicks;
   private float absorptionAmount;
   private double aM = 0.41999998688698D;

   public void onKillCommand() {
      this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
   }

   public EntityLivingBase(World var1) {
      super(var1);
      this.applyEntityAttributes();
      this.setHealth(this.getMaxHealth());
      this.preventEntitySpawning = true;
      this.setPosition(this.posX, this.posY, this.posZ);
      this.rotationYaw = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.rotationYawHead = this.rotationYaw;
      this.rotationPitchHead = this.rotationPitch;
      this.stepHeight = 0.6F;
   }

   protected void entityInit() {
      this.I.b(7, Integer.valueOf(0));
      this.I.b(8, Byte.valueOf((byte)0));
      this.I.b(9, Byte.valueOf((byte)0));
      this.I.b(6, Float.valueOf(1.0F));
   }

   protected void applyEntityAttributes() {
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
   }

   protected void updateFallState(double var1, boolean var3, Block var4, BlockPos var5) {
      if(!this.isInWater()) {
         this.handleWaterMovement();
      }

      if(!this.worldObj.isRemote && this.fallDistance > 3.0F) {
         IBlockState var6 = this.worldObj.getBlockState(var5);
         Block var7 = var6.getBlock();
         float var8 = (float)MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
         if(var7.getMaterial() != Material.air) {
            double var9 = (double)Math.min(0.2F + var8 / 15.0F, 10.0F);
            if(var9 > 2.5D) {
               var9 = 2.5D;
            }

            int var11 = (int)(150.0D * var9);
            EJ.a((WorldServer)this.worldObj, EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, var11, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[]{Block.getStateId(var6)});
         }
      }

      super.updateFallState(var1, var3, var4, var5);
   }

   public Map getActivePotionsMap() {
      return this.activePotionsMap;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public void onEntityUpdate() {
      this.prevSwingProgress = this.swingProgress;
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("livingEntityBaseTick");
      boolean var1 = this instanceof EntityPlayer;
      if(this.isEntityAlive()) {
         if(this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(DamageSource.inWall, 1.0F);
         } else if(!this.worldObj.getWorldBorder().contains(this.getEntityBoundingBox())) {
            double var2 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
            if(var2 < 0.0D) {
               this.attackEntityFrom(DamageSource.inWall, (float)Math.max(1, MathHelper.floor_double(-var2 * this.worldObj.getWorldBorder().getDamageAmount())));
            }
         }
      }

      if(this.isImmuneToFire() || this.worldObj.isRemote) {
         this.extinguish();
      }

      boolean var7 = ((EntityPlayer)this).abilities.isDisabledDamage();
      if(this.isEntityAlive()) {
         if(this.isInsideOfMaterial(Material.water)) {
            if(!this.canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.getId())) {
               this.setAir(this.decreaseAirSupply(this.getAir()));
               if(this.getAir() == -20) {
                  this.setAir(0);

                  for(int var3 = 0; var3 < 8; ++var3) {
                     float var4 = this.rand.nextFloat() - this.rand.nextFloat();
                     float var5 = this.rand.nextFloat() - this.rand.nextFloat();
                     float var6 = this.rand.nextFloat() - this.rand.nextFloat();
                     this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (double)var4, this.posY + (double)var5, this.posZ + (double)var6, this.motionX, this.motionY, this.motionZ, new int[0]);
                  }

                  this.attackEntityFrom(DamageSource.drown, 2.0F);
               }
            }

            if(!this.worldObj.isRemote && this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
               this.mountEntity((Entity)null);
            }
         } else {
            this.setAir(300);
         }
      }

      if(this.isEntityAlive() && this.isWet()) {
         this.extinguish();
      }

      this.prevCameraPitch = this.cameraPitch;
      if(this.hurtTime > 0) {
         --this.hurtTime;
      }

      if(this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
         --this.hurtResistantTime;
      }

      if(this.getHealth() <= 0.0F) {
         this.onDeathUpdate();
      }

      if(this.recentlyHit > 0) {
         --this.recentlyHit;
      } else {
         this.attackingPlayer = null;
      }

      if(this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
         this.lastAttacker = null;
      }

      if(this.entityLivingToAttack != null) {
         if(!this.entityLivingToAttack.isEntityAlive()) {
            this.setRevengeTarget((EntityLivingBase)null);
         } else if(this.ticksExisted - this.revengeTimer > 100) {
            this.setRevengeTarget((EntityLivingBase)null);
         }
      }

      this.updatePotionEffects();
      this.prevMovedDistance = this.movedDistance;
      this.prevRenderYawOffset = this.renderYawOffset;
      this.prevRotationYawHead = this.rotationYawHead;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationPitchHead = this.rotationPitchHead;
      this.worldObj.theProfiler.endSection();
      EventManager.call(new LivingUpdateEvent(this));
   }

   public boolean isChild() {
      return false;
   }

   protected void onDeathUpdate() {
      ++this.deathTime;
      if(this.deathTime == 20) {
         if(!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
            int var8 = this.getExperiencePoints(this.attackingPlayer);

            while(true) {
               int var9 = EntityXPOrb.getXPSplit(var8);
               var8 -= var9;
               this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var9));
            }
         }

         this.setDead();

         for(int var1 = 0; var1 < 20; ++var1) {
            double var2 = this.rand.nextGaussian() * 0.02D;
            double var4 = this.rand.nextGaussian() * 0.02D;
            double var6 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var2, var4, var6, new int[0]);
         }
      }

   }

   protected boolean canDropLoot() {
      return !this.isChild();
   }

   protected int decreaseAirSupply(int var1) {
      int var2 = EnchantmentHelper.getRespiration(this);
      return this.rand.nextInt(var2 + 1) > 0?var1:var1 - 1;
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      return 0;
   }

   protected boolean isPlayer() {
      return false;
   }

   public Random getRNG() {
      return this.rand;
   }

   public EntityLivingBase getAITarget() {
      return this.entityLivingToAttack;
   }

   public int getRevengeTimer() {
      return this.revengeTimer;
   }

   public void setRevengeTarget(EntityLivingBase var1) {
      this.entityLivingToAttack = var1;
      this.revengeTimer = this.ticksExisted;
   }

   public EntityLivingBase getLastAttacker() {
      return this.lastAttacker;
   }

   public void setLastAttacker(Entity var1) {
      if(var1 instanceof EntityLivingBase) {
         this.lastAttacker = (EntityLivingBase)var1;
      } else {
         this.lastAttacker = null;
      }

      this.lastAttackerTime = this.ticksExisted;
   }

   public int getLastAttackerTime() {
      return this.lastAttackerTime;
   }

   public int getAge() {
      return this.entityAge;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setFloat("HealF", this.getHealth());
      var1.setShort("Health", (short)((int)Math.ceil((double)this.getHealth())));
      var1.setShort("HurtTime", (short)this.hurtTime);
      var1.setInteger("HurtByTimestamp", this.revengeTimer);
      var1.setShort("DeathTime", (short)this.deathTime);
      var1.setFloat("AbsorptionAmount", this.getAbsorptionAmount());

      for(ItemStack var5 : this.getInventory()) {
         this.attributeMap.removeAttributeModifiers(var5.getAttributeModifiers());
      }

      var1.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));

      for(ItemStack var12 : this.getInventory()) {
         this.attributeMap.applyAttributeModifiers(var12.getAttributeModifiers());
      }

      if(!this.activePotionsMap.isEmpty()) {
         NBTTagList var7 = new NBTTagList();

         for(PotionEffect var11 : this.activePotionsMap.values()) {
            var7.appendTag(var11.writeCustomPotionEffectToNBT(new NBTTagCompound()));
         }

         var1.setTag("ActiveEffects", var7);
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      this.setAbsorptionAmount(var1.getFloat("AbsorptionAmount"));
      if(var1.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
         SharedMonsterAttributes.a(this.getAttributeMap(), var1.getTagList("Attributes", 10));
      }

      if(var1.hasKey("ActiveEffects", 9)) {
         NBTTagList var2 = var1.getTagList("ActiveEffects", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
            this.activePotionsMap.put(Integer.valueOf(var5.getPotionID()), var5);
         }
      }

      if(var1.hasKey("HealF", 99)) {
         this.setHealth(var1.getFloat("HealF"));
      } else {
         NBTBase var6 = var1.getTag("Health");
         this.setHealth(this.getMaxHealth());
      }

      this.hurtTime = var1.getShort("HurtTime");
      this.deathTime = var1.getShort("DeathTime");
      this.revengeTimer = var1.getInteger("HurtByTimestamp");
   }

   protected void updatePotionEffects() {
      Iterator var1 = this.activePotionsMap.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
         if(!var3.onUpdate(this)) {
            if(!this.worldObj.isRemote) {
               var1.remove();
               this.onFinishedPotionEffect(var3);
            }
         } else if(var3.getDuration() % 600 == 0) {
            this.onChangedPotionEffect(var3, false);
         }
      }

      if(this.potionsNeedUpdate) {
         if(!this.worldObj.isRemote) {
            this.updatePotionMetadata();
         }

         this.potionsNeedUpdate = false;
      }

      int var11 = this.I.c(7);
      boolean var12 = this.I.g(8) > 0;
      boolean var4 = false;
      if(!this.isInvisible()) {
         var4 = this.rand.nextBoolean();
      } else {
         var4 = this.rand.nextInt(15) == 0;
      }

      var4 = var4 & this.rand.nextInt(5) == 0;
      double var5 = (double)(var11 >> 16 & 255) / 255.0D;
      double var7 = (double)(var11 >> 8 & 255) / 255.0D;
      double var9 = (double)(var11 & 255) / 255.0D;
      this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, var5, var7, var9, new int[0]);
   }

   protected void updatePotionMetadata() {
      if(this.activePotionsMap.isEmpty()) {
         this.resetPotionEffectMetadata();
         this.setInvisible(false);
      } else {
         int var1 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
         this.I.a(8, Byte.valueOf((byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values())?1:0)));
         this.I.a(7, Integer.valueOf(var1));
         this.setInvisible(this.isPotionActive(Potion.invisibility.getId()));
      }

   }

   protected void resetPotionEffectMetadata() {
      this.I.a(8, Byte.valueOf((byte)0));
      this.I.a(7, Integer.valueOf(0));
   }

   public void clearActivePotions() {
      Iterator var1 = this.activePotionsMap.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
         if(!this.worldObj.isRemote) {
            var1.remove();
            this.onFinishedPotionEffect(var3);
         }
      }

   }

   public Collection getActivePotionEffects() {
      return this.activePotionsMap.values();
   }

   public boolean isPotionActive(int var1) {
      return this.activePotionsMap.containsKey(Integer.valueOf(var1));
   }

   public boolean isPotionActive(Potion var1) {
      return this.activePotionsMap.containsKey(Integer.valueOf(var1.getId()));
   }

   public PotionEffect getActivePotionEffect(Potion var1) {
      return (PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getId()));
   }

   public int c(Potion var1) {
      return this.isPotionActive(var1)?((PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getId()))).getAmplifier() + 1:0;
   }

   public void addPotionEffect(PotionEffect var1) {
      if(this.isPotionApplicable(var1)) {
         if(this.activePotionsMap.containsKey(Integer.valueOf(var1.getPotionID()))) {
            ((PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getPotionID()))).combine(var1);
            this.onChangedPotionEffect((PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getPotionID())), true);
         } else {
            this.activePotionsMap.put(Integer.valueOf(var1.getPotionID()), var1);
            this.onNewPotionEffect(var1);
         }
      }

   }

   public boolean isPotionApplicable(PotionEffect var1) {
      if(this.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
         return true;
      } else {
         int var2 = var1.getPotionID();
         return var2 != Potion.regeneration.getId() && var2 != Potion.poison.getId();
      }
   }

   public boolean isEntityUndead() {
      return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
   }

   public void removePotionEffectClient(int var1) {
      this.activePotionsMap.remove(Integer.valueOf(var1));
   }

   public void removePotionEffect(int var1) {
      PotionEffect var2 = (PotionEffect)this.activePotionsMap.remove(Integer.valueOf(var1));
      this.onFinishedPotionEffect(var2);
   }

   protected void onNewPotionEffect(PotionEffect var1) {
      this.potionsNeedUpdate = true;
      if(!this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   protected void onChangedPotionEffect(PotionEffect var1, boolean var2) {
      this.potionsNeedUpdate = true;
      if(!this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), var1.getAmplifier());
         Potion.potionTypes[var1.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   protected void onFinishedPotionEffect(PotionEffect var1) {
      this.potionsNeedUpdate = true;
      if(!this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   public void heal(float var1) {
      float var2 = this.getHealth();
      if(var2 > 0.0F) {
         this.setHealth(var2 + var1);
      }

   }

   public final float getHealth() {
      return this.I.b(6);
   }

   public void setHealth(float var1) {
      this.I.a(6, Float.valueOf(MathHelper.clamp_float(var1, 0.0F, this.getMaxHealth())));
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if(this.worldObj.isRemote) {
         return false;
      } else {
         this.entityAge = 0;
         if(this.getHealth() <= 0.0F) {
            return false;
         } else if(var1.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
         } else {
            if((var1 == DamageSource.anvil || var1 == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
               this.getEquipmentInSlot(4).damageItem((int)(var2 * 4.0F + this.rand.nextFloat() * var2 * 2.0F), this);
               var2 *= 0.75F;
            }

            this.limbSwingAmount = 1.5F;
            boolean var3 = true;
            if((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F) {
               if(var2 <= this.lastDamage) {
                  return false;
               }

               this.damageEntity(var1, var2 - this.lastDamage);
               this.lastDamage = var2;
               var3 = false;
            } else {
               this.lastDamage = var2;
               this.hurtResistantTime = this.maxHurtResistantTime;
               this.damageEntity(var1, var2);
               this.hurtTime = this.maxHurtTime = 10;
            }

            this.attackedAtYaw = 0.0F;
            Entity var4 = var1.getEntity();
            if(var4 instanceof EntityLivingBase) {
               this.setRevengeTarget((EntityLivingBase)var4);
            }

            if(var4 instanceof EntityPlayer) {
               this.recentlyHit = 100;
               this.attackingPlayer = (EntityPlayer)var4;
            } else if(var4 instanceof EntityWolf) {
               EntityWolf var5 = (EntityWolf)var4;
               if(var5.isTamed()) {
                  this.recentlyHit = 100;
                  this.attackingPlayer = null;
               }
            }

            this.worldObj.setEntityState(this, (byte)2);
            if(var1 != DamageSource.drown) {
               this.setBeenAttacked();
            }

            double var10 = var4.posX - this.posX;

            double var7;
            for(var7 = var4.posZ - this.posZ; var10 * var10 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
               var10 = (Math.random() - Math.random()) * 0.01D;
            }

            this.attackedAtYaw = (float)(MathHelper.func_181159_b(var7, var10) * 180.0D / 3.141592653589793D - (double)this.rotationYaw);
            this.knockBack(var4, var2, var10, var7);
            if(this.getHealth() <= 0.0F) {
               String var11 = this.getDeathSound();
               this.playSound(var11, this.getSoundVolume(), this.getSoundPitch());
               this.onDeath(var1);
            } else {
               String var12 = this.getHurtSound();
               this.playSound(var12, this.getSoundVolume(), this.getSoundPitch());
            }

            return true;
         }
      }
   }

   public void renderBrokenItemStack(ItemStack var1) {
      this.playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);

      for(int var2 = 0; var2 < 5; ++var2) {
         Vec3 var3 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         var3 = var3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
         var3 = var3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
         double var4 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
         Vec3 var6 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.3D, var4, 0.6D);
         var6 = var6.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
         var6 = var6.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
         var6 = var6.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
         this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var6.xCoord, var6.yCoord, var6.zCoord, var3.xCoord, var3.yCoord + 0.05D, var3.zCoord, new int[]{Item.b(var1.getItem())});
      }

   }

   public void onDeath(DamageSource var1) {
      Entity var2 = var1.getEntity();
      EntityLivingBase var3 = this.func_94060_bK();
      if(this.scoreValue >= 0) {
         var3.addToPlayerScore(this, this.scoreValue);
      }

      var2.onKillEntity(this);
      this.dead = true;
      this.getCombatTracker().reset();
      if(!this.worldObj.isRemote) {
         int var4 = 0;
         if(var2 instanceof EntityPlayer) {
            var4 = EnchantmentHelper.getLootingModifier((EntityLivingBase)var2);
         }

         if(this.canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
            this.dropFewItems(this.recentlyHit > 0, var4);
            this.dropEquipment(this.recentlyHit > 0, var4);
            if(this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + (float)var4 * 0.01F) {
               this.addRandomDrop();
            }
         }
      }

      this.worldObj.setEntityState(this, (byte)3);
   }

   protected void dropEquipment(boolean var1, int var2) {
   }

   public void knockBack(Entity var1, float var2, double var3, double var5) {
      if(this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
         this.isAirBorne = true;
         float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
         float var8 = 0.4F;
         this.motionX /= 2.0D;
         this.motionY /= 2.0D;
         this.motionZ /= 2.0D;
         this.motionX -= var3 / (double)var7 * 0.4000000059604645D;
         this.motionY += 0.4000000059604645D;
         this.motionZ -= var5 / (double)var7 * 0.4000000059604645D;
         if(this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
         }
      }

   }

   protected String getHurtSound() {
      return "game.neutral.hurt";
   }

   protected String getDeathSound() {
      return "game.neutral.die";
   }

   protected void addRandomDrop() {
   }

   protected void dropFewItems(boolean var1, int var2) {
   }

   public boolean isOnLadder() {
      int var1 = MathHelper.floor_double(this.posX);
      int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
      int var3 = MathHelper.floor_double(this.posZ);
      Block var4 = this.worldObj.getBlockState(new BlockPos(var1, var2, var3)).getBlock();
      return (var4 == Blocks.ladder || var4 == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator());
   }

   public boolean isEntityAlive() {
      return !this.isDead && this.getHealth() > 0.0F;
   }

   public void fall(float var1, float var2) {
      super.fall(var1, var2);
      PotionEffect var3 = this.getActivePotionEffect(Potion.jump);
      float var4 = (float)(var3.getAmplifier() + 1);
      int var5 = MathHelper.ceiling_float_int((var1 - 3.0F - var4) * var2);
      this.playSound(this.getFallSoundString(var5), 1.0F, 1.0F);
      this.attackEntityFrom(DamageSource.fall, (float)var5);
      int var6 = MathHelper.floor_double(this.posX);
      int var7 = MathHelper.floor_double(this.posY - 0.20000000298023224D);
      int var8 = MathHelper.floor_double(this.posZ);
      Block var9 = this.worldObj.getBlockState(new BlockPos(var6, var7, var8)).getBlock();
      if(var9.getMaterial() != Material.air) {
         Block$SoundType var10 = var9.stepSound;
         this.playSound(var10.getStepSound(), var10.getVolume() * 0.5F, var10.getFrequency() * 0.75F);
      }

   }

   protected String getFallSoundString(int var1) {
      return var1 > 4?"game.neutral.hurt.fall.big":"game.neutral.hurt.fall.small";
   }

   public void performHurtAnimation() {
      this.hurtTime = this.maxHurtTime = 10;
      this.attackedAtYaw = 0.0F;
   }

   public int getTotalArmorValue() {
      int var1 = 0;

      for(ItemStack var5 : this.getInventory()) {
         if(var5.getItem() instanceof ItemArmor) {
            int var6 = ((ItemArmor)var5.getItem()).damageReduceAmount;
            var1 += var6;
         }
      }

      return var1;
   }

   protected void damageArmor(float var1) {
   }

   protected float applyArmorCalculations(DamageSource var1, float var2) {
      if(!var1.isUnblockable()) {
         int var3 = 25 - this.getTotalArmorValue();
         float var4 = var2 * (float)var3;
         this.damageArmor(var2);
         var2 = var4 / 25.0F;
      }

      return var2;
   }

   protected float applyPotionDamageCalculations(DamageSource var1, float var2) {
      if(var1.isDamageAbsolute()) {
         return var2;
      } else {
         if(this.isPotionActive(Potion.resistance) && var1 != DamageSource.outOfWorld) {
            int var3 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            int var4 = 25 - var3;
            float var5 = var2 * (float)var4;
            var2 = var5 / 25.0F;
         }

         if(var2 <= 0.0F) {
            return 0.0F;
         } else {
            int var6 = EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), var1);
            if(var6 > 20) {
               var6 = 20;
            }

            if(var6 <= 20) {
               int var7 = 25 - var6;
               float var8 = var2 * (float)var7;
               var2 = var8 / 25.0F;
            }

            return var2;
         }
      }
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if(!this.isEntityInvulnerable(var1)) {
         var2 = this.applyArmorCalculations(var1, var2);
         var2 = this.applyPotionDamageCalculations(var1, var2);
         float var3 = var2;
         var2 = Math.max(var2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - var2));
         if(var2 != 0.0F) {
            float var4 = this.getHealth();
            this.setHealth(var4 - var2);
            this.getCombatTracker().trackDamage(var1, var4, var2);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - var2);
         }
      }

   }

   public CombatTracker getCombatTracker() {
      return this._combatTracker;
   }

   public EntityLivingBase func_94060_bK() {
      return (EntityLivingBase)(this._combatTracker.func_94550_c() != null?this._combatTracker.func_94550_c():(this.attackingPlayer != null?this.attackingPlayer:this.entityLivingToAttack));
   }

   public final float getMaxHealth() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
   }

   public final int getArrowCountInEntity() {
      return this.I.g(9);
   }

   public final void setArrowCountInEntity(int var1) {
      this.I.a(9, Byte.valueOf((byte)var1));
   }

   private int getArmSwingAnimationEnd() {
      int var1 = 0;
      if(((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).isEnabled() && this == Minecraft.getInstance().player) {
         var1 = 6 + ((Integer)((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).getSlowdown().get()).intValue();
      } else {
         var1 = this.isPotionActive(Potion.digSpeed)?6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()):(this.isPotionActive(Potion.digSlowdown)?6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2:6);
      }

      return var1;
   }

   public void swingItem() {
      if(!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
         this.swingProgressInt = -1;
         this.isSwingInProgress = true;
         if(this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
         }
      }

   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 2) {
         this.limbSwingAmount = 1.5F;
         this.hurtResistantTime = this.maxHurtResistantTime;
         this.hurtTime = this.maxHurtTime = 10;
         this.attackedAtYaw = 0.0F;
         String var2 = this.getHurtSound();
         this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         this.attackEntityFrom(DamageSource.generic, 0.0F);
      } else if(var1 == 3) {
         String var3 = this.getDeathSound();
         this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         this.setHealth(0.0F);
         this.onDeath(DamageSource.generic);
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   protected void kill() {
      this.attackEntityFrom(DamageSource.outOfWorld, 4.0F);
   }

   protected void updateArmSwingProgress() {
      int var1 = this.getArmSwingAnimationEnd();
      if(this.isSwingInProgress) {
         ++this.swingProgressInt;
         if(this.swingProgressInt >= var1) {
            this.swingProgressInt = 0;
            this.isSwingInProgress = false;
         }
      } else {
         this.swingProgressInt = 0;
      }

      this.swingProgress = (float)this.swingProgressInt / (float)var1;
   }

   public IAttributeInstance getEntityAttribute(IAttribute var1) {
      return this.getAttributeMap().getAttributeInstance(var1);
   }

   public BaseAttributeMap getAttributeMap() {
      if(this.attributeMap == null) {
         this.attributeMap = new ServersideAttributeMap();
      }

      return this.attributeMap;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public abstract ItemStack getHeldItem();

   public abstract ItemStack getEquipmentInSlot(int var1);

   public abstract ItemStack getCurrentArmor(int var1);

   public abstract void setCurrentItemOrArmor(int var1, ItemStack var2);

   public void setSprinting(boolean var1) {
      super.setSprinting(var1);
      IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if(var2.getModifier(sprintingSpeedBoostModifierUUID) != null) {
         var2.removeModifier(sprintingSpeedBoostModifier);
      }

      var2.applyModifier(sprintingSpeedBoostModifier);
   }

   public abstract ItemStack[] getInventory();

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return this.isChild()?(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F:(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F;
   }

   public void dismountEntity(Entity var1) {
      double var2 = var1.posX;
      double var4 = var1.getEntityBoundingBox().minY + (double)var1.height;
      double var6 = var1.posZ;
      boolean var8 = true;

      for(int var9 = -1; var9 <= 1; ++var9) {
         for(int var10 = -1; var10 < 1; ++var10) {
            int var11 = (int)(this.posX + (double)var9);
            int var12 = (int)(this.posZ + (double)var10);
            AxisAlignedBB var13 = this.getEntityBoundingBox().offset((double)var9, 1.0D, (double)var10);
            if(this.worldObj.func_147461_a(var13).isEmpty()) {
               if(World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var11, (int)this.posY, var12))) {
                  this.setPositionAndUpdate(this.posX + (double)var9, this.posY + 1.0D, this.posZ + (double)var10);
                  return;
               }

               if(World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var11, (int)this.posY - 1, var12)) || this.worldObj.getBlockState(new BlockPos(var11, (int)this.posY - 1, var12)).getBlock().getMaterial() == Material.water) {
                  var2 = this.posX + (double)var9;
                  var4 = this.posY + 1.0D;
                  var6 = this.posZ + (double)var10;
               }
            }
         }
      }

      this.setPositionAndUpdate(var2, var4, var6);
   }

   public boolean getAlwaysRenderNameTagForRender() {
      return false;
   }

   protected void jump() {
      this.motionY = this.aM;
      if(this instanceof EntityPlayerSP) {
         JumpEvent var1 = new JumpEvent(this.motionY);
         EventManager.call(var1);
         this.motionY = var1.getHeight();
      }

      if(this.isPotionActive(Potion.jump)) {
         this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
      }

      if(this.isSprinting()) {
         float var2 = (float)Math.toRadians((double)this.rotationYaw);
         this.motionX -= (double)(MathHelper.sin(var2) * 0.2F);
         this.motionZ += (double)(MathHelper.cos(var2) * 0.2F);
      }

      this.isAirBorne = true;
   }

   protected void updateAITick() {
      this.motionY += 0.03999999910593033D;
   }

   protected void handleJumpLava() {
      this.motionY += 0.03999999910593033D;
   }

   public void moveEntityWithHeading(float var1, float var2) {
      if(this.isServerWorld()) {
         if(!this.isInWater() || this instanceof EntityPlayer && ((EntityPlayer)this).abilities.isFlying()) {
            if(!this.isInLava() || this instanceof EntityPlayer && ((EntityPlayer)this).abilities.isFlying()) {
               float var9 = 0.91F;
               if(this.onGround) {
                  var9 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
               }

               float var4 = 0.16277136F / (var9 * var9 * var9);
               float var12;
               if(this.onGround) {
                  var12 = this.getAIMoveSpeed() * var4;
               } else {
                  var12 = this.jumpMovementFactor;
               }

               this.moveFlying(var1, var2, var12);
               var9 = 0.91F;
               if(this.onGround) {
                  var9 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
               }

               if(this.isOnLadder()) {
                  float var14 = 0.15F;
                  this.motionX = MathHelper.clamp_double(this.motionX, -0.15000000596046448D, 0.15000000596046448D);
                  this.motionZ = MathHelper.clamp_double(this.motionZ, -0.15000000596046448D, 0.15000000596046448D);
                  this.fallDistance = 0.0F;
                  if(this.motionY < -0.15D) {
                     this.motionY = -0.15D;
                  }

                  boolean var15 = this.isSneaking() && this instanceof EntityPlayer;
                  if(this.motionY < 0.0D) {
                     this.motionY = 0.0D;
                  }
               }

               this.moveEntity(this.motionX, this.motionY, this.motionZ);
               if(this.isCollidedHorizontally && this.isOnLadder()) {
                  this.motionY = 0.2D;
               }

               if(this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
                  if(this.posY > 0.0D) {
                     this.motionY = -0.1D;
                  } else {
                     this.motionY = 0.0D;
                  }
               } else {
                  this.motionY -= 0.08D;
               }

               this.motionY *= 0.9800000190734863D;
               this.motionX *= (double)var9;
               this.motionZ *= (double)var9;
            } else {
               double var8 = this.posY;
               this.moveFlying(var1, var2, 0.02F);
               this.moveEntity(this.motionX, this.motionY, this.motionZ);
               this.motionX *= 0.5D;
               this.motionY *= 0.5D;
               this.motionZ *= 0.5D;
               this.motionY -= 0.02D;
               if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var8, this.motionZ)) {
                  this.motionY = 0.30000001192092896D;
               }
            }
         } else {
            double var3 = this.posY;
            float var5 = 0.8F;
            float var6 = 0.02F;
            float var7 = (float)EnchantmentHelper.getDepthStriderModifier(this);
            if(var7 > 3.0F) {
               var7 = 3.0F;
            }

            if(!this.onGround) {
               var7 *= 0.5F;
            }

            if(var7 > 0.0F) {
               var5 += (0.54600006F - var5) * var7 / 3.0F;
               var6 += (this.getAIMoveSpeed() - var6) * var7 / 3.0F;
            }

            this.moveFlying(var1, var2, var6);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)var5;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= (double)var5;
            this.motionY -= 0.02D;
            if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var3, this.motionZ)) {
               this.motionY = 0.30000001192092896D;
            }
         }
      }

      this.prevLimbSwingAmount = this.limbSwingAmount;
      double var11 = this.posX - this.prevPosX;
      double var13 = this.posZ - this.prevPosZ;
      float var16 = MathHelper.sqrt_double(var11 * var11 + var13 * var13) * 4.0F;
      if(var16 > 1.0F) {
         var16 = 1.0F;
      }

      this.limbSwingAmount += (var16 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
   }

   public float getAIMoveSpeed() {
      return this.landMovementFactor;
   }

   public void setAIMoveSpeed(float var1) {
      this.landMovementFactor = var1;
   }

   public boolean attackEntityAsMob(Entity var1) {
      this.setLastAttacker(var1);
      return false;
   }

   public boolean isPlayerSleeping() {
      return false;
   }

   public void onUpdate() {
      super.onUpdate();
      if(!this.worldObj.isRemote) {
         int var1 = this.getArrowCountInEntity();
         if(this.arrowHitTimer <= 0) {
            this.arrowHitTimer = 20 * (30 - var1);
         }

         --this.arrowHitTimer;
         if(this.arrowHitTimer <= 0) {
            this.setArrowCountInEntity(var1 - 1);
         }

         for(int var2 = 0; var2 < 5; ++var2) {
            ItemStack var3 = this.previousEquipment[var2];
            ItemStack var4 = this.getEquipmentInSlot(var2);
            if(!ItemStack.areItemStacksEqual(var4, var3)) {
               ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityID(), var2, var4));
               this.attributeMap.removeAttributeModifiers(var3.getAttributeModifiers());
               this.attributeMap.applyAttributeModifiers(var4.getAttributeModifiers());
               this.previousEquipment[var2] = null;
            }
         }

         if(this.ticksExisted % 20 == 0) {
            this.getCombatTracker().reset();
         }
      }

      this.onLivingUpdate();
      double var9 = this.posX - this.prevPosX;
      double var10 = this.posZ - this.prevPosZ;
      float var5 = (float)(var9 * var9 + var10 * var10);
      float var6 = this.renderYawOffset;
      float var7 = 0.0F;
      this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
      float var8 = 0.0F;
      if(var5 > 0.0025000002F) {
         var8 = 1.0F;
         var7 = (float)Math.sqrt((double)var5) * 3.0F;
         var6 = (float)MathHelper.func_181159_b(var10, var9) * 180.0F / 3.1415927F - 90.0F;
      }

      if(this.swingProgress > 0.0F) {
         var6 = this.rotationYaw;
      }

      if(!this.onGround) {
         var8 = 0.0F;
      }

      this.onGroundSpeedFactor += (var8 - this.onGroundSpeedFactor) * 0.3F;
      this.worldObj.theProfiler.startSection("headTurn");
      var7 = this.func_110146_f(var6, var7);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("rangeChecks");

      while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      while(this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
         this.prevRenderYawOffset -= 360.0F;
      }

      while(this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
         this.prevRenderYawOffset += 360.0F;
      }

      while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while(this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
         this.prevRotationYawHead -= 360.0F;
      }

      while(this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
         this.prevRotationYawHead += 360.0F;
      }

      this.worldObj.theProfiler.endSection();
      this.movedDistance += var7;
   }

   protected float func_110146_f(float var1, float var2) {
      float var3 = MathHelper.wrapAngleTo180_float(var1 - this.renderYawOffset);
      this.renderYawOffset += var3 * 0.3F;
      float var4 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
      boolean var5 = var4 < -90.0F || var4 >= 90.0F;
      if(var4 < -75.0F) {
         var4 = -75.0F;
      }

      if(var4 >= 75.0F) {
         var4 = 75.0F;
      }

      this.renderYawOffset = this.rotationYaw - var4;
      if(var4 * var4 > 2500.0F) {
         this.renderYawOffset += var4 * 0.2F;
      }

      var2 = var2 * -1.0F;
      return var2;
   }

   public void onLivingUpdate() {
      if(this.jumpTicks > 0) {
         --this.jumpTicks;
      }

      if(this.newPosRotationIncrements > 0) {
         double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
         double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
         double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
         double var7 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
         this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
         this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
         --this.newPosRotationIncrements;
         this.setPosition(var1, var3, var5);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else if(!this.isServerWorld()) {
         this.motionX *= 0.98D;
         this.motionY *= 0.98D;
         this.motionZ *= 0.98D;
      }

      if(Math.abs(this.motionX) < 0.005D) {
         this.motionX = 0.0D;
      }

      if(Math.abs(this.motionY) < 0.005D) {
         this.motionY = 0.0D;
      }

      if(Math.abs(this.motionZ) < 0.005D) {
         this.motionZ = 0.0D;
      }

      this.worldObj.theProfiler.startSection("ai");
      if(this.isMovementBlocked()) {
         this.isJumping = false;
         this.moveStrafing = 0.0F;
         this.moveForward = 0.0F;
         this.randomYawVelocity = 0.0F;
      } else if(this.isServerWorld()) {
         this.worldObj.theProfiler.startSection("newAi");
         this.updateEntityActionState();
         this.worldObj.theProfiler.endSection();
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("jump");
      if(this.isJumping) {
         if(this.isInWater()) {
            this.updateAITick();
         } else if(this.isInLava()) {
            this.handleJumpLava();
         } else if(this.onGround && this.jumpTicks == 0) {
            this.jump();
            this.jumpTicks = 10;
         }
      } else {
         this.jumpTicks = 0;
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("travel");
      this.moveStrafing *= 0.98F;
      this.moveForward *= 0.98F;
      this.randomYawVelocity *= 0.9F;
      this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("push");
      if(!this.worldObj.isRemote) {
         this.collideWithNearbyEntities();
      }

      this.worldObj.theProfiler.endSection();
   }

   protected void updateEntityActionState() {
   }

   protected void collideWithNearbyEntities() {
      List var1 = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBePushed));
      if(!var1.isEmpty()) {
         for(Entity var3 : var1) {
            this.collideWithEntity(var3);
         }
      }

   }

   protected void collideWithEntity(Entity var1) {
      var1.applyEntityCollision(this);
   }

   public void mountEntity(Entity var1) {
      if(this.ridingEntity != null) {
         if(!this.worldObj.isRemote) {
            this.dismountEntity(this.ridingEntity);
         }

         if(this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
         }

         this.ridingEntity = null;
      } else {
         super.mountEntity(var1);
      }

   }

   public void updateRidden() {
      super.updateRidden();
      this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
      this.onGroundSpeedFactor = 0.0F;
      this.fallDistance = 0.0F;
   }

   public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.newPosX = var1;
      this.newPosY = var3;
      this.newPosZ = var5;
      this.newRotationYaw = (double)var7;
      this.newRotationPitch = (double)var8;
      this.newPosRotationIncrements = var9;
   }

   public void setJumping(boolean var1) {
      this.isJumping = var1;
   }

   public void onItemPickup(Entity var1, int var2) {
      if(!var1.isDead && !this.worldObj.isRemote) {
         EntityTracker var3 = ((WorldServer)this.worldObj).getEntityTracker();
         if(var1 instanceof EntityItem) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityID(), this.getEntityID()));
         }

         if(var1 instanceof EntityArrow) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityID(), this.getEntityID()));
         }

         if(var1 instanceof EntityXPOrb) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityID(), this.getEntityID()));
         }
      }

   }

   public boolean canEntityBeSeen(@NotNull Entity var1) {
      return this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
   }

   public Vec3 getLookVec() {
      return this.getLook(1.0F);
   }

   public Vec3 getLook(float var1) {
      return this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
   }

   public float getSwingProgress(float var1) {
      float var2 = this.swingProgress - this.prevSwingProgress;
      if(var2 < 0.0F) {
         ++var2;
      }

      return this.prevSwingProgress + var2 * var1;
   }

   public boolean isServerWorld() {
      return !this.worldObj.isRemote;
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public boolean canBePushed() {
      return !this.isDead;
   }

   protected void setBeenAttacked() {
      this.velocityChanged = this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();
   }

   public float getRotationYawHead() {
      return this.rotationYawHead;
   }

   public void setRotationYawHead(float var1) {
      this.rotationYawHead = var1;
   }

   public void setRenderYawOffset(float var1) {
      this.renderYawOffset = var1;
   }

   public void setRotationPitchHead(float var1) {
      this.rotationPitchHead = var1;
   }

   public void func_181013_g(float var1) {
      this.renderYawOffset = var1;
   }

   public float getAbsorptionAmount() {
      return this.absorptionAmount;
   }

   public void setAbsorptionAmount(float var1) {
      if(var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.absorptionAmount = var1;
   }

   public Team getTeam() {
      return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
   }

   public boolean isOnSameTeam(EntityLivingBase var1) {
      return this.isOnTeam(var1.getTeam());
   }

   public boolean isOnTeam(Team var1) {
      return this.getTeam() != null && this.getTeam().isSameTeam(var1);
   }

   public void sendEnterCombat() {
   }

   public void sendEndCombat() {
   }

   protected void markPotionsDirty() {
      this.potionsNeedUpdate = true;
   }

   public static AttributeModifier C() {
      return sprintingSpeedBoostModifier;
   }

   public static UUID I() {
      return sprintingSpeedBoostModifierUUID;
   }
}
