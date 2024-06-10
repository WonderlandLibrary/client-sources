/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.DataWatcher;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityAgeable;
/*  10:    */ import net.minecraft.entity.IEntityLivingData;
/*  11:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  12:    */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*  13:    */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*  14:    */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*  15:    */ import net.minecraft.entity.ai.EntityAIMate;
/*  16:    */ import net.minecraft.entity.ai.EntityAIOcelotAttack;
/*  17:    */ import net.minecraft.entity.ai.EntityAIOcelotSit;
/*  18:    */ import net.minecraft.entity.ai.EntityAISit;
/*  19:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  20:    */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*  21:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  22:    */ import net.minecraft.entity.ai.EntityAITempt;
/*  23:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  24:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  25:    */ import net.minecraft.entity.ai.EntityMoveHelper;
/*  26:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  27:    */ import net.minecraft.entity.player.EntityPlayer;
/*  28:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  29:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  30:    */ import net.minecraft.init.Blocks;
/*  31:    */ import net.minecraft.init.Items;
/*  32:    */ import net.minecraft.item.Item;
/*  33:    */ import net.minecraft.item.ItemStack;
/*  34:    */ import net.minecraft.nbt.NBTTagCompound;
/*  35:    */ import net.minecraft.pathfinding.PathNavigate;
/*  36:    */ import net.minecraft.util.AxisAlignedBB;
/*  37:    */ import net.minecraft.util.DamageSource;
/*  38:    */ import net.minecraft.util.MathHelper;
/*  39:    */ import net.minecraft.util.StatCollector;
/*  40:    */ import net.minecraft.world.World;
/*  41:    */ 
/*  42:    */ public class EntityOcelot
/*  43:    */   extends EntityTameable
/*  44:    */ {
/*  45:    */   private EntityAITempt aiTempt;
/*  46:    */   private static final String __OBFID = "CL_00001646";
/*  47:    */   
/*  48:    */   public EntityOcelot(World par1World)
/*  49:    */   {
/*  50: 41 */     super(par1World);
/*  51: 42 */     setSize(0.6F, 0.8F);
/*  52: 43 */     getNavigator().setAvoidsWater(true);
/*  53: 44 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  54: 45 */     this.tasks.addTask(2, this.aiSit);
/*  55: 46 */     this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6D, Items.fish, true));
/*  56: 47 */     this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
/*  57: 48 */     this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  58: 49 */     this.tasks.addTask(6, new EntityAIOcelotSit(this, 1.33D));
/*  59: 50 */     this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
/*  60: 51 */     this.tasks.addTask(8, new EntityAIOcelotAttack(this));
/*  61: 52 */     this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
/*  62: 53 */     this.tasks.addTask(10, new EntityAIWander(this, 0.8D));
/*  63: 54 */     this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
/*  64: 55 */     this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void entityInit()
/*  68:    */   {
/*  69: 60 */     super.entityInit();
/*  70: 61 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void updateAITick()
/*  74:    */   {
/*  75: 69 */     if (getMoveHelper().isUpdating())
/*  76:    */     {
/*  77: 71 */       double var1 = getMoveHelper().getSpeed();
/*  78: 73 */       if (var1 == 0.6D)
/*  79:    */       {
/*  80: 75 */         setSneaking(true);
/*  81: 76 */         setSprinting(false);
/*  82:    */       }
/*  83: 78 */       else if (var1 == 1.33D)
/*  84:    */       {
/*  85: 80 */         setSneaking(false);
/*  86: 81 */         setSprinting(true);
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90: 85 */         setSneaking(false);
/*  91: 86 */         setSprinting(false);
/*  92:    */       }
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96: 91 */       setSneaking(false);
/*  97: 92 */       setSprinting(false);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected boolean canDespawn()
/* 102:    */   {
/* 103:101 */     return (!isTamed()) && (this.ticksExisted > 2400);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isAIEnabled()
/* 107:    */   {
/* 108:109 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void applyEntityAttributes()
/* 112:    */   {
/* 113:114 */     super.applyEntityAttributes();
/* 114:115 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/* 115:116 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.300000011920929D);
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void fall(float par1) {}
/* 119:    */   
/* 120:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 121:    */   {
/* 122:129 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 123:130 */     par1NBTTagCompound.setInteger("CatType", getTameSkin());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 127:    */   {
/* 128:138 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 129:139 */     setTameSkin(par1NBTTagCompound.getInteger("CatType"));
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected String getLivingSound()
/* 133:    */   {
/* 134:147 */     return isTamed() ? "mob.cat.meow" : this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : isInLove() ? "mob.cat.purr" : "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected String getHurtSound()
/* 138:    */   {
/* 139:155 */     return "mob.cat.hitt";
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String getDeathSound()
/* 143:    */   {
/* 144:163 */     return "mob.cat.hitt";
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected float getSoundVolume()
/* 148:    */   {
/* 149:171 */     return 0.4F;
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected Item func_146068_u()
/* 153:    */   {
/* 154:176 */     return Items.leather;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 158:    */   {
/* 159:181 */     return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 163:    */   {
/* 164:189 */     if (isEntityInvulnerable()) {
/* 165:191 */       return false;
/* 166:    */     }
/* 167:195 */     this.aiSit.setSitting(false);
/* 168:196 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected void dropFewItems(boolean par1, int par2) {}
/* 172:    */   
/* 173:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 174:    */   {
/* 175:210 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 176:212 */     if (isTamed())
/* 177:    */     {
/* 178:214 */       if ((par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(getOwnerName())) && (!this.worldObj.isClient) && (!isBreedingItem(var2))) {
/* 179:216 */         this.aiSit.setSitting(!isSitting());
/* 180:    */       }
/* 181:    */     }
/* 182:219 */     else if ((this.aiTempt.isRunning()) && (var2 != null) && (var2.getItem() == Items.fish) && (par1EntityPlayer.getDistanceSqToEntity(this) < 9.0D))
/* 183:    */     {
/* 184:221 */       if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 185:223 */         var2.stackSize -= 1;
/* 186:    */       }
/* 187:226 */       if (var2.stackSize <= 0) {
/* 188:228 */         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 189:    */       }
/* 190:231 */       if (!this.worldObj.isClient) {
/* 191:233 */         if (this.rand.nextInt(3) == 0)
/* 192:    */         {
/* 193:235 */           setTamed(true);
/* 194:236 */           setTameSkin(1 + this.worldObj.rand.nextInt(3));
/* 195:237 */           setOwner(par1EntityPlayer.getCommandSenderName());
/* 196:238 */           playTameEffect(true);
/* 197:239 */           this.aiSit.setSitting(true);
/* 198:240 */           this.worldObj.setEntityState(this, (byte)7);
/* 199:    */         }
/* 200:    */         else
/* 201:    */         {
/* 202:244 */           playTameEffect(false);
/* 203:245 */           this.worldObj.setEntityState(this, (byte)6);
/* 204:    */         }
/* 205:    */       }
/* 206:249 */       return true;
/* 207:    */     }
/* 208:252 */     return super.interact(par1EntityPlayer);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public EntityOcelot createChild(EntityAgeable par1EntityAgeable)
/* 212:    */   {
/* 213:257 */     EntityOcelot var2 = new EntityOcelot(this.worldObj);
/* 214:259 */     if (isTamed())
/* 215:    */     {
/* 216:261 */       var2.setOwner(getOwnerName());
/* 217:262 */       var2.setTamed(true);
/* 218:263 */       var2.setTameSkin(getTameSkin());
/* 219:    */     }
/* 220:266 */     return var2;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isBreedingItem(ItemStack par1ItemStack)
/* 224:    */   {
/* 225:275 */     return (par1ItemStack != null) && (par1ItemStack.getItem() == Items.fish);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean canMateWith(EntityAnimal par1EntityAnimal)
/* 229:    */   {
/* 230:283 */     if (par1EntityAnimal == this) {
/* 231:285 */       return false;
/* 232:    */     }
/* 233:287 */     if (!isTamed()) {
/* 234:289 */       return false;
/* 235:    */     }
/* 236:291 */     if (!(par1EntityAnimal instanceof EntityOcelot)) {
/* 237:293 */       return false;
/* 238:    */     }
/* 239:297 */     EntityOcelot var2 = (EntityOcelot)par1EntityAnimal;
/* 240:298 */     return var2.isTamed();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public int getTameSkin()
/* 244:    */   {
/* 245:304 */     return this.dataWatcher.getWatchableObjectByte(18);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setTameSkin(int par1)
/* 249:    */   {
/* 250:309 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
/* 251:    */   }
/* 252:    */   
/* 253:    */   public boolean getCanSpawnHere()
/* 254:    */   {
/* 255:317 */     if (this.worldObj.rand.nextInt(3) == 0) {
/* 256:319 */       return false;
/* 257:    */     }
/* 258:323 */     if ((this.worldObj.checkNoEntityCollision(this.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox)))
/* 259:    */     {
/* 260:325 */       int var1 = MathHelper.floor_double(this.posX);
/* 261:326 */       int var2 = MathHelper.floor_double(this.boundingBox.minY);
/* 262:327 */       int var3 = MathHelper.floor_double(this.posZ);
/* 263:329 */       if (var2 < 63) {
/* 264:331 */         return false;
/* 265:    */       }
/* 266:334 */       Block var4 = this.worldObj.getBlock(var1, var2 - 1, var3);
/* 267:336 */       if ((var4 == Blocks.grass) || (var4.getMaterial() == Material.leaves)) {
/* 268:338 */         return true;
/* 269:    */       }
/* 270:    */     }
/* 271:342 */     return false;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public String getCommandSenderName()
/* 275:    */   {
/* 276:351 */     return isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : hasCustomNameTag() ? getCustomNameTag() : super.getCommandSenderName();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 280:    */   {
/* 281:356 */     par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
/* 282:358 */     if (this.worldObj.rand.nextInt(7) == 0) {
/* 283:360 */       for (int var2 = 0; var2 < 2; var2++)
/* 284:    */       {
/* 285:362 */         EntityOcelot var3 = new EntityOcelot(this.worldObj);
/* 286:363 */         var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 287:364 */         var3.setGrowingAge(-24000);
/* 288:365 */         this.worldObj.spawnEntityInWorld(var3);
/* 289:    */       }
/* 290:    */     }
/* 291:369 */     return par1EntityLivingData;
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityOcelot
 * JD-Core Version:    0.7.0.1
 */