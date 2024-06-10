/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.enchantment.Enchantment;
/*   7:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityCreature;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*  13:    */ import net.minecraft.entity.IEntityLivingData;
/*  14:    */ import net.minecraft.entity.IRangedAttackMob;
/*  15:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  16:    */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*  17:    */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*  18:    */ import net.minecraft.entity.ai.EntityAIFleeSun;
/*  19:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  20:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  21:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  22:    */ import net.minecraft.entity.ai.EntityAIRestrictSun;
/*  23:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  24:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  25:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  26:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  27:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  28:    */ import net.minecraft.entity.player.EntityPlayer;
/*  29:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  30:    */ import net.minecraft.init.Blocks;
/*  31:    */ import net.minecraft.init.Items;
/*  32:    */ import net.minecraft.item.Item;
/*  33:    */ import net.minecraft.item.ItemStack;
/*  34:    */ import net.minecraft.nbt.NBTTagCompound;
/*  35:    */ import net.minecraft.potion.Potion;
/*  36:    */ import net.minecraft.potion.PotionEffect;
/*  37:    */ import net.minecraft.stats.AchievementList;
/*  38:    */ import net.minecraft.util.DamageSource;
/*  39:    */ import net.minecraft.util.MathHelper;
/*  40:    */ import net.minecraft.world.EnumDifficulty;
/*  41:    */ import net.minecraft.world.World;
/*  42:    */ import net.minecraft.world.WorldProviderHell;
/*  43:    */ 
/*  44:    */ public class EntitySkeleton
/*  45:    */   extends EntityMob
/*  46:    */   implements IRangedAttackMob
/*  47:    */ {
/*  48: 41 */   private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
/*  49: 42 */   private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
/*  50:    */   private static final String __OBFID = "CL_00001697";
/*  51:    */   
/*  52:    */   public EntitySkeleton(World par1World)
/*  53:    */   {
/*  54: 47 */     super(par1World);
/*  55: 48 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  56: 49 */     this.tasks.addTask(2, new EntityAIRestrictSun(this));
/*  57: 50 */     this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
/*  58: 51 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  59: 52 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  60: 53 */     this.tasks.addTask(6, new EntityAILookIdle(this));
/*  61: 54 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
/*  62: 55 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
/*  63: 57 */     if ((par1World != null) && (!par1World.isClient)) {
/*  64: 59 */       setCombatTask();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void applyEntityAttributes()
/*  69:    */   {
/*  70: 65 */     super.applyEntityAttributes();
/*  71: 66 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void entityInit()
/*  75:    */   {
/*  76: 71 */     super.entityInit();
/*  77: 72 */     this.dataWatcher.addObject(13, new Byte((byte)0));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isAIEnabled()
/*  81:    */   {
/*  82: 80 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String getLivingSound()
/*  86:    */   {
/*  87: 88 */     return "mob.skeleton.say";
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String getHurtSound()
/*  91:    */   {
/*  92: 96 */     return "mob.skeleton.hurt";
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String getDeathSound()
/*  96:    */   {
/*  97:104 */     return "mob.skeleton.death";
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 101:    */   {
/* 102:109 */     playSound("mob.skeleton.step", 0.15F, 1.0F);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 106:    */   {
/* 107:114 */     if (super.attackEntityAsMob(par1Entity))
/* 108:    */     {
/* 109:116 */       if ((getSkeletonType() == 1) && ((par1Entity instanceof EntityLivingBase))) {
/* 110:118 */         ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
/* 111:    */       }
/* 112:121 */       return true;
/* 113:    */     }
/* 114:125 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public EnumCreatureAttribute getCreatureAttribute()
/* 118:    */   {
/* 119:134 */     return EnumCreatureAttribute.UNDEAD;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onLivingUpdate()
/* 123:    */   {
/* 124:143 */     if ((this.worldObj.isDaytime()) && (!this.worldObj.isClient))
/* 125:    */     {
/* 126:145 */       float var1 = getBrightness(1.0F);
/* 127:147 */       if ((var1 > 0.5F) && (this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) && (this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))))
/* 128:    */       {
/* 129:149 */         boolean var2 = true;
/* 130:150 */         ItemStack var3 = getEquipmentInSlot(4);
/* 131:152 */         if (var3 != null)
/* 132:    */         {
/* 133:154 */           if (var3.isItemStackDamageable())
/* 134:    */           {
/* 135:156 */             var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
/* 136:158 */             if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
/* 137:    */             {
/* 138:160 */               renderBrokenItemStack(var3);
/* 139:161 */               setCurrentItemOrArmor(4, null);
/* 140:    */             }
/* 141:    */           }
/* 142:165 */           var2 = false;
/* 143:    */         }
/* 144:168 */         if (var2) {
/* 145:170 */           setFire(8);
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:175 */     if ((this.worldObj.isClient) && (getSkeletonType() == 1)) {
/* 150:177 */       setSize(0.72F, 2.34F);
/* 151:    */     }
/* 152:180 */     super.onLivingUpdate();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void updateRidden()
/* 156:    */   {
/* 157:188 */     super.updateRidden();
/* 158:190 */     if ((this.ridingEntity instanceof EntityCreature))
/* 159:    */     {
/* 160:192 */       EntityCreature var1 = (EntityCreature)this.ridingEntity;
/* 161:193 */       this.renderYawOffset = var1.renderYawOffset;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void onDeath(DamageSource par1DamageSource)
/* 166:    */   {
/* 167:202 */     super.onDeath(par1DamageSource);
/* 168:204 */     if (((par1DamageSource.getSourceOfDamage() instanceof EntityArrow)) && ((par1DamageSource.getEntity() instanceof EntityPlayer)))
/* 169:    */     {
/* 170:206 */       EntityPlayer var2 = (EntityPlayer)par1DamageSource.getEntity();
/* 171:207 */       double var3 = var2.posX - this.posX;
/* 172:208 */       double var5 = var2.posZ - this.posZ;
/* 173:210 */       if (var3 * var3 + var5 * var5 >= 2500.0D) {
/* 174:212 */         var2.triggerAchievement(AchievementList.snipeSkeleton);
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected Item func_146068_u()
/* 180:    */   {
/* 181:219 */     return Items.arrow;
/* 182:    */   }
/* 183:    */   
/* 184:    */   protected void dropFewItems(boolean par1, int par2)
/* 185:    */   {
/* 186:230 */     if (getSkeletonType() == 1)
/* 187:    */     {
/* 188:232 */       int var3 = this.rand.nextInt(3 + par2) - 1;
/* 189:234 */       for (int var4 = 0; var4 < var3; var4++) {
/* 190:236 */         func_145779_a(Items.coal, 1);
/* 191:    */       }
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:241 */       var3 = this.rand.nextInt(3 + par2);
/* 196:243 */       for (var4 = 0; var4 < var3; var4++) {
/* 197:245 */         func_145779_a(Items.arrow, 1);
/* 198:    */       }
/* 199:    */     }
/* 200:249 */     int var3 = this.rand.nextInt(3 + par2);
/* 201:251 */     for (int var4 = 0; var4 < var3; var4++) {
/* 202:253 */       func_145779_a(Items.bone, 1);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected void dropRareDrop(int par1)
/* 207:    */   {
/* 208:259 */     if (getSkeletonType() == 1) {
/* 209:261 */       entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected void addRandomArmor()
/* 214:    */   {
/* 215:270 */     super.addRandomArmor();
/* 216:271 */     setCurrentItemOrArmor(0, new ItemStack(Items.bow));
/* 217:    */   }
/* 218:    */   
/* 219:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 220:    */   {
/* 221:276 */     par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
/* 222:278 */     if (((this.worldObj.provider instanceof WorldProviderHell)) && (getRNG().nextInt(5) > 0))
/* 223:    */     {
/* 224:280 */       this.tasks.addTask(4, this.aiAttackOnCollide);
/* 225:281 */       setSkeletonType(1);
/* 226:282 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
/* 227:283 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/* 228:    */     }
/* 229:    */     else
/* 230:    */     {
/* 231:287 */       this.tasks.addTask(4, this.aiArrowAttack);
/* 232:288 */       addRandomArmor();
/* 233:289 */       enchantEquipment();
/* 234:    */     }
/* 235:292 */     setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
/* 236:294 */     if (getEquipmentInSlot(4) == null)
/* 237:    */     {
/* 238:296 */       Calendar var2 = this.worldObj.getCurrentDate();
/* 239:298 */       if ((var2.get(2) + 1 == 10) && (var2.get(5) == 31) && (this.rand.nextFloat() < 0.25F))
/* 240:    */       {
/* 241:300 */         setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 242:301 */         this.equipmentDropChances[4] = 0.0F;
/* 243:    */       }
/* 244:    */     }
/* 245:305 */     return par1EntityLivingData;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setCombatTask()
/* 249:    */   {
/* 250:313 */     this.tasks.removeTask(this.aiAttackOnCollide);
/* 251:314 */     this.tasks.removeTask(this.aiArrowAttack);
/* 252:315 */     ItemStack var1 = getHeldItem();
/* 253:317 */     if ((var1 != null) && (var1.getItem() == Items.bow)) {
/* 254:319 */       this.tasks.addTask(4, this.aiArrowAttack);
/* 255:    */     } else {
/* 256:323 */       this.tasks.addTask(4, this.aiAttackOnCollide);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
/* 261:    */   {
/* 262:332 */     EntityArrow var3 = new EntityArrow(this.worldObj, this, par1EntityLivingBase, 1.6F, 14 - this.worldObj.difficultySetting.getDifficultyId() * 4);
/* 263:333 */     int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
/* 264:334 */     int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
/* 265:335 */     var3.setDamage(par2 * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
/* 266:337 */     if (var4 > 0) {
/* 267:339 */       var3.setDamage(var3.getDamage() + var4 * 0.5D + 0.5D);
/* 268:    */     }
/* 269:342 */     if (var5 > 0) {
/* 270:344 */       var3.setKnockbackStrength(var5);
/* 271:    */     }
/* 272:347 */     if ((EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0) || (getSkeletonType() == 1)) {
/* 273:349 */       var3.setFire(100);
/* 274:    */     }
/* 275:352 */     playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 276:353 */     this.worldObj.spawnEntityInWorld(var3);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public int getSkeletonType()
/* 280:    */   {
/* 281:361 */     return this.dataWatcher.getWatchableObjectByte(13);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setSkeletonType(int par1)
/* 285:    */   {
/* 286:369 */     this.dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
/* 287:370 */     this.isImmuneToFire = (par1 == 1);
/* 288:372 */     if (par1 == 1) {
/* 289:374 */       setSize(0.72F, 2.34F);
/* 290:    */     } else {
/* 291:378 */       setSize(0.6F, 1.8F);
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 296:    */   {
/* 297:387 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 298:389 */     if (par1NBTTagCompound.func_150297_b("SkeletonType", 99))
/* 299:    */     {
/* 300:391 */       byte var2 = par1NBTTagCompound.getByte("SkeletonType");
/* 301:392 */       setSkeletonType(var2);
/* 302:    */     }
/* 303:395 */     setCombatTask();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 307:    */   {
/* 308:403 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 309:404 */     par1NBTTagCompound.setByte("SkeletonType", (byte)getSkeletonType());
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
/* 313:    */   {
/* 314:412 */     super.setCurrentItemOrArmor(par1, par2ItemStack);
/* 315:414 */     if ((!this.worldObj.isClient) && (par1 == 0)) {
/* 316:416 */       setCombatTask();
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public double getYOffset()
/* 321:    */   {
/* 322:425 */     return super.getYOffset() - 0.5D;
/* 323:    */   }
/* 324:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntitySkeleton
 * JD-Core Version:    0.7.0.1
 */