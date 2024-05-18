/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIControlledByPlayer;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal {
/*     */   public EntityPig(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     setSize(0.9F, 0.9F);
/*  36 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  37 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  38 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  39 */     this.tasks.addTask(2, (EntityAIBase)(this.aiControlledByPlayer = new EntityAIControlledByPlayer((EntityLiving)this, 0.3F)));
/*  40 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  41 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot_on_a_stick, false));
/*  42 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot, false));
/*  43 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  44 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  45 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  46 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  51 */     super.applyEntityAttributes();
/*  52 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  53 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final EntityAIControlledByPlayer aiControlledByPlayer;
/*     */ 
/*     */   
/*     */   public boolean canBeSteered() {
/*  62 */     ItemStack itemstack = ((EntityPlayer)this.riddenByEntity).getHeldItem();
/*  63 */     return (itemstack != null && itemstack.getItem() == Items.carrot_on_a_stick);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  68 */     super.entityInit();
/*  69 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  77 */     super.writeEntityToNBT(tagCompound);
/*  78 */     tagCompound.setBoolean("Saddle", getSaddled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  86 */     super.readEntityFromNBT(tagCompund);
/*  87 */     setSaddled(tagCompund.getBoolean("Saddle"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  95 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 103 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 111 */     return "mob.pig.death";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 116 */     playSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 124 */     if (super.interact(player))
/*     */     {
/* 126 */       return true;
/*     */     }
/* 128 */     if (!getSaddled() || this.worldObj.isRemote || (this.riddenByEntity != null && this.riddenByEntity != player))
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 134 */     player.mountEntity((Entity)this);
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 141 */     return isBurning() ? Items.cooked_porkchop : Items.porkchop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
/* 149 */     int i = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_);
/*     */     
/* 151 */     for (int j = 0; j < i; j++) {
/*     */       
/* 153 */       if (isBurning()) {
/*     */         
/* 155 */         dropItem(Items.cooked_porkchop, 1);
/*     */       }
/*     */       else {
/*     */         
/* 159 */         dropItem(Items.porkchop, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     if (getSaddled())
/*     */     {
/* 165 */       dropItem(Items.saddle, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSaddled() {
/* 174 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaddled(boolean saddled) {
/* 182 */     if (saddled) {
/*     */       
/* 184 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
/*     */     }
/*     */     else {
/*     */       
/* 188 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 197 */     if (!this.worldObj.isRemote && !this.isDead) {
/*     */       
/* 199 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.worldObj);
/* 200 */       entitypigzombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/* 201 */       entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 202 */       entitypigzombie.setNoAI(isAIDisabled());
/*     */       
/* 204 */       if (hasCustomName()) {
/*     */         
/* 206 */         entitypigzombie.setCustomNameTag(getCustomNameTag());
/* 207 */         entitypigzombie.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 210 */       this.worldObj.spawnEntityInWorld((Entity)entitypigzombie);
/* 211 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 217 */     super.fall(distance, damageMultiplier);
/*     */     
/* 219 */     if (distance > 5.0F && this.riddenByEntity instanceof EntityPlayer)
/*     */     {
/* 221 */       ((EntityPlayer)this.riddenByEntity).triggerAchievement((StatBase)AchievementList.flyPig);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPig createChild(EntityAgeable ageable) {
/* 227 */     return new EntityPig(this.worldObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 236 */     return (stack != null && stack.getItem() == Items.carrot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAIControlledByPlayer getAIControlledByPlayer() {
/* 244 */     return this.aiControlledByPlayer;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\passive\EntityPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */