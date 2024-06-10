/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import java.util.UUID;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.IEntityLivingData;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.util.AxisAlignedBB;
/*  16:    */ import net.minecraft.util.DamageSource;
/*  17:    */ import net.minecraft.world.EnumDifficulty;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class EntityPigZombie
/*  21:    */   extends EntityZombie
/*  22:    */ {
/*  23: 20 */   private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  24: 21 */   private static final AttributeModifier field_110190_br = new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.45D, 0).setSaved(false);
/*  25:    */   private int angerLevel;
/*  26:    */   private int randomSoundDelay;
/*  27:    */   private Entity field_110191_bu;
/*  28:    */   private static final String __OBFID = "CL_00001693";
/*  29:    */   
/*  30:    */   public EntityPigZombie(World par1World)
/*  31:    */   {
/*  32: 33 */     super(par1World);
/*  33: 34 */     this.isImmuneToFire = true;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void applyEntityAttributes()
/*  37:    */   {
/*  38: 39 */     super.applyEntityAttributes();
/*  39: 40 */     getEntityAttribute(field_110186_bp).setBaseValue(0.0D);
/*  40: 41 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*  41: 42 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected boolean isAIEnabled()
/*  45:    */   {
/*  46: 50 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void onUpdate()
/*  50:    */   {
/*  51: 58 */     if ((this.field_110191_bu != this.entityToAttack) && (!this.worldObj.isClient))
/*  52:    */     {
/*  53: 60 */       IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*  54: 61 */       var1.removeModifier(field_110190_br);
/*  55: 63 */       if (this.entityToAttack != null) {
/*  56: 65 */         var1.applyModifier(field_110190_br);
/*  57:    */       }
/*  58:    */     }
/*  59: 69 */     this.field_110191_bu = this.entityToAttack;
/*  60: 71 */     if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0)) {
/*  61: 73 */       playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*  62:    */     }
/*  63: 76 */     super.onUpdate();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean getCanSpawnHere()
/*  67:    */   {
/*  68: 84 */     return (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (this.worldObj.checkNoEntityCollision(this.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  72:    */   {
/*  73: 92 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  74: 93 */     par1NBTTagCompound.setShort("Anger", (short)this.angerLevel);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  78:    */   {
/*  79:101 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  80:102 */     this.angerLevel = par1NBTTagCompound.getShort("Anger");
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected Entity findPlayerToAttack()
/*  84:    */   {
/*  85:111 */     return this.angerLevel == 0 ? null : super.findPlayerToAttack();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  89:    */   {
/*  90:119 */     if (isEntityInvulnerable()) {
/*  91:121 */       return false;
/*  92:    */     }
/*  93:125 */     Entity var3 = par1DamageSource.getEntity();
/*  94:127 */     if ((var3 instanceof EntityPlayer))
/*  95:    */     {
/*  96:129 */       List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));
/*  97:131 */       for (int var5 = 0; var5 < var4.size(); var5++)
/*  98:    */       {
/*  99:133 */         Entity var6 = (Entity)var4.get(var5);
/* 100:135 */         if ((var6 instanceof EntityPigZombie))
/* 101:    */         {
/* 102:137 */           EntityPigZombie var7 = (EntityPigZombie)var6;
/* 103:138 */           var7.becomeAngryAt(var3);
/* 104:    */         }
/* 105:    */       }
/* 106:142 */       becomeAngryAt(var3);
/* 107:    */     }
/* 108:145 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void becomeAngryAt(Entity par1Entity)
/* 112:    */   {
/* 113:154 */     this.entityToAttack = par1Entity;
/* 114:155 */     this.angerLevel = (400 + this.rand.nextInt(400));
/* 115:156 */     this.randomSoundDelay = this.rand.nextInt(40);
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected String getLivingSound()
/* 119:    */   {
/* 120:164 */     return "mob.zombiepig.zpig";
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected String getHurtSound()
/* 124:    */   {
/* 125:172 */     return "mob.zombiepig.zpighurt";
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected String getDeathSound()
/* 129:    */   {
/* 130:180 */     return "mob.zombiepig.zpigdeath";
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected void dropFewItems(boolean par1, int par2)
/* 134:    */   {
/* 135:188 */     int var3 = this.rand.nextInt(2 + par2);
/* 136:191 */     for (int var4 = 0; var4 < var3; var4++) {
/* 137:193 */       func_145779_a(Items.rotten_flesh, 1);
/* 138:    */     }
/* 139:196 */     var3 = this.rand.nextInt(2 + par2);
/* 140:198 */     for (var4 = 0; var4 < var3; var4++) {
/* 141:200 */       func_145779_a(Items.gold_nugget, 1);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 146:    */   {
/* 147:209 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void dropRareDrop(int par1)
/* 151:    */   {
/* 152:214 */     func_145779_a(Items.gold_ingot, 1);
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected void addRandomArmor()
/* 156:    */   {
/* 157:222 */     setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 161:    */   {
/* 162:227 */     super.onSpawnWithEgg(par1EntityLivingData);
/* 163:228 */     setVillager(false);
/* 164:229 */     return par1EntityLivingData;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityPigZombie
 * JD-Core Version:    0.7.0.1
 */