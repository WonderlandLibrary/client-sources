/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityAgeable;
/*   7:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   8:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.stats.AchievementList;
/*  17:    */ import net.minecraft.stats.StatList;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.DamageSource;
/*  20:    */ import net.minecraft.util.MathHelper;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public abstract class EntityAnimal
/*  24:    */   extends EntityAgeable
/*  25:    */   implements IAnimals
/*  26:    */ {
/*  27:    */   private int inLove;
/*  28:    */   private int breeding;
/*  29:    */   private EntityPlayer field_146084_br;
/*  30:    */   private static final String __OBFID = "CL_00001638";
/*  31:    */   
/*  32:    */   public EntityAnimal(World par1World)
/*  33:    */   {
/*  34: 33 */     super(par1World);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void updateAITick()
/*  38:    */   {
/*  39: 41 */     if (getGrowingAge() != 0) {
/*  40: 43 */       this.inLove = 0;
/*  41:    */     }
/*  42: 46 */     super.updateAITick();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void onLivingUpdate()
/*  46:    */   {
/*  47: 55 */     super.onLivingUpdate();
/*  48: 57 */     if (getGrowingAge() != 0) {
/*  49: 59 */       this.inLove = 0;
/*  50:    */     }
/*  51: 62 */     if (this.inLove > 0)
/*  52:    */     {
/*  53: 64 */       this.inLove -= 1;
/*  54: 65 */       String var1 = "heart";
/*  55: 67 */       if (this.inLove % 10 == 0)
/*  56:    */       {
/*  57: 69 */         double var2 = this.rand.nextGaussian() * 0.02D;
/*  58: 70 */         double var4 = this.rand.nextGaussian() * 0.02D;
/*  59: 71 */         double var6 = this.rand.nextGaussian() * 0.02D;
/*  60: 72 */         this.worldObj.spawnParticle(var1, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);
/*  61:    */       }
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 77 */       this.breeding = 0;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void attackEntity(Entity par1Entity, float par2)
/*  70:    */   {
/*  71: 86 */     if ((par1Entity instanceof EntityPlayer))
/*  72:    */     {
/*  73: 88 */       if (par2 < 3.0F)
/*  74:    */       {
/*  75: 90 */         double var3 = par1Entity.posX - this.posX;
/*  76: 91 */         double var5 = par1Entity.posZ - this.posZ;
/*  77: 92 */         this.rotationYaw = ((float)(Math.atan2(var5, var3) * 180.0D / 3.141592653589793D) - 90.0F);
/*  78: 93 */         this.hasAttacked = true;
/*  79:    */       }
/*  80: 96 */       EntityPlayer var7 = (EntityPlayer)par1Entity;
/*  81: 98 */       if ((var7.getCurrentEquippedItem() == null) || (!isBreedingItem(var7.getCurrentEquippedItem()))) {
/*  82:100 */         this.entityToAttack = null;
/*  83:    */       }
/*  84:    */     }
/*  85:103 */     else if ((par1Entity instanceof EntityAnimal))
/*  86:    */     {
/*  87:105 */       EntityAnimal var8 = (EntityAnimal)par1Entity;
/*  88:107 */       if ((getGrowingAge() > 0) && (var8.getGrowingAge() < 0))
/*  89:    */       {
/*  90:109 */         if (par2 < 2.5D) {
/*  91:111 */           this.hasAttacked = true;
/*  92:    */         }
/*  93:    */       }
/*  94:114 */       else if ((this.inLove > 0) && (var8.inLove > 0))
/*  95:    */       {
/*  96:116 */         if (var8.entityToAttack == null) {
/*  97:118 */           var8.entityToAttack = this;
/*  98:    */         }
/*  99:121 */         if ((var8.entityToAttack == this) && (par2 < 3.5D))
/* 100:    */         {
/* 101:123 */           var8.inLove += 1;
/* 102:124 */           this.inLove += 1;
/* 103:125 */           this.breeding += 1;
/* 104:127 */           if (this.breeding % 4 == 0) {
/* 105:129 */             this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D);
/* 106:    */           }
/* 107:132 */           if (this.breeding == 60) {
/* 108:134 */             procreate((EntityAnimal)par1Entity);
/* 109:    */           }
/* 110:    */         }
/* 111:    */         else
/* 112:    */         {
/* 113:139 */           this.breeding = 0;
/* 114:    */         }
/* 115:    */       }
/* 116:    */       else
/* 117:    */       {
/* 118:144 */         this.breeding = 0;
/* 119:145 */         this.entityToAttack = null;
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void procreate(EntityAnimal par1EntityAnimal)
/* 125:    */   {
/* 126:156 */     EntityAgeable var2 = createChild(par1EntityAnimal);
/* 127:158 */     if (var2 != null)
/* 128:    */     {
/* 129:160 */       if ((this.field_146084_br == null) && (par1EntityAnimal.func_146083_cb() != null)) {
/* 130:162 */         this.field_146084_br = par1EntityAnimal.func_146083_cb();
/* 131:    */       }
/* 132:165 */       if (this.field_146084_br != null)
/* 133:    */       {
/* 134:167 */         this.field_146084_br.triggerAchievement(StatList.field_151186_x);
/* 135:169 */         if ((this instanceof EntityCow)) {
/* 136:171 */           this.field_146084_br.triggerAchievement(AchievementList.field_150962_H);
/* 137:    */         }
/* 138:    */       }
/* 139:175 */       setGrowingAge(6000);
/* 140:176 */       par1EntityAnimal.setGrowingAge(6000);
/* 141:177 */       this.inLove = 0;
/* 142:178 */       this.breeding = 0;
/* 143:179 */       this.entityToAttack = null;
/* 144:180 */       par1EntityAnimal.entityToAttack = null;
/* 145:181 */       par1EntityAnimal.breeding = 0;
/* 146:182 */       par1EntityAnimal.inLove = 0;
/* 147:183 */       var2.setGrowingAge(-24000);
/* 148:184 */       var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 149:186 */       for (int var3 = 0; var3 < 7; var3++)
/* 150:    */       {
/* 151:188 */         double var4 = this.rand.nextGaussian() * 0.02D;
/* 152:189 */         double var6 = this.rand.nextGaussian() * 0.02D;
/* 153:190 */         double var8 = this.rand.nextGaussian() * 0.02D;
/* 154:191 */         this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var4, var6, var8);
/* 155:    */       }
/* 156:194 */       this.worldObj.spawnEntityInWorld(var2);
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 161:    */   {
/* 162:203 */     if (isEntityInvulnerable()) {
/* 163:205 */       return false;
/* 164:    */     }
/* 165:209 */     this.fleeingTick = 60;
/* 166:211 */     if (!isAIEnabled())
/* 167:    */     {
/* 168:213 */       IAttributeInstance var3 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 169:215 */       if (var3.getModifier(field_110179_h) == null) {
/* 170:217 */         var3.applyModifier(field_110181_i);
/* 171:    */       }
/* 172:    */     }
/* 173:221 */     this.entityToAttack = null;
/* 174:222 */     this.inLove = 0;
/* 175:223 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public float getBlockPathWeight(int par1, int par2, int par3)
/* 179:    */   {
/* 180:233 */     return this.worldObj.getBlock(par1, par2 - 1, par3) == Blocks.grass ? 10.0F : this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 184:    */   {
/* 185:241 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 186:242 */     par1NBTTagCompound.setInteger("InLove", this.inLove);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 190:    */   {
/* 191:250 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 192:251 */     this.inLove = par1NBTTagCompound.getInteger("InLove");
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected Entity findPlayerToAttack()
/* 196:    */   {
/* 197:260 */     if (this.fleeingTick > 0) {
/* 198:262 */       return null;
/* 199:    */     }
/* 200:266 */     float var1 = 8.0F;
/* 201:271 */     if (this.inLove > 0)
/* 202:    */     {
/* 203:273 */       List var2 = this.worldObj.getEntitiesWithinAABB(getClass(), this.boundingBox.expand(var1, var1, var1));
/* 204:275 */       for (int var3 = 0; var3 < var2.size(); var3++)
/* 205:    */       {
/* 206:277 */         EntityAnimal var4 = (EntityAnimal)var2.get(var3);
/* 207:279 */         if ((var4 != this) && (var4.inLove > 0)) {
/* 208:281 */           return var4;
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:285 */     else if (getGrowingAge() == 0)
/* 213:    */     {
/* 214:287 */       List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(var1, var1, var1));
/* 215:289 */       for (int var3 = 0; var3 < var2.size(); var3++)
/* 216:    */       {
/* 217:291 */         EntityPlayer var5 = (EntityPlayer)var2.get(var3);
/* 218:293 */         if ((var5.getCurrentEquippedItem() != null) && (isBreedingItem(var5.getCurrentEquippedItem()))) {
/* 219:295 */           return var5;
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:299 */     else if (getGrowingAge() > 0)
/* 224:    */     {
/* 225:301 */       List var2 = this.worldObj.getEntitiesWithinAABB(getClass(), this.boundingBox.expand(var1, var1, var1));
/* 226:303 */       for (int var3 = 0; var3 < var2.size(); var3++)
/* 227:    */       {
/* 228:305 */         EntityAnimal var4 = (EntityAnimal)var2.get(var3);
/* 229:307 */         if ((var4 != this) && (var4.getGrowingAge() < 0)) {
/* 230:309 */           return var4;
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:314 */     return null;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean getCanSpawnHere()
/* 238:    */   {
/* 239:323 */     int var1 = MathHelper.floor_double(this.posX);
/* 240:324 */     int var2 = MathHelper.floor_double(this.boundingBox.minY);
/* 241:325 */     int var3 = MathHelper.floor_double(this.posZ);
/* 242:326 */     return (this.worldObj.getBlock(var1, var2 - 1, var3) == Blocks.grass) && (this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8) && (super.getCanSpawnHere());
/* 243:    */   }
/* 244:    */   
/* 245:    */   public int getTalkInterval()
/* 246:    */   {
/* 247:334 */     return 120;
/* 248:    */   }
/* 249:    */   
/* 250:    */   protected boolean canDespawn()
/* 251:    */   {
/* 252:342 */     return false;
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/* 256:    */   {
/* 257:350 */     return 1 + this.worldObj.rand.nextInt(3);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean isBreedingItem(ItemStack par1ItemStack)
/* 261:    */   {
/* 262:359 */     return par1ItemStack.getItem() == Items.wheat;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 266:    */   {
/* 267:367 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 268:369 */     if ((var2 != null) && (isBreedingItem(var2)) && (getGrowingAge() == 0) && (this.inLove <= 0))
/* 269:    */     {
/* 270:371 */       if (!par1EntityPlayer.capabilities.isCreativeMode)
/* 271:    */       {
/* 272:373 */         var2.stackSize -= 1;
/* 273:375 */         if (var2.stackSize <= 0) {
/* 274:377 */           par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 275:    */         }
/* 276:    */       }
/* 277:381 */       func_146082_f(par1EntityPlayer);
/* 278:382 */       return true;
/* 279:    */     }
/* 280:386 */     return super.interact(par1EntityPlayer);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void func_146082_f(EntityPlayer p_146082_1_)
/* 284:    */   {
/* 285:392 */     this.inLove = 600;
/* 286:393 */     this.field_146084_br = p_146082_1_;
/* 287:394 */     this.entityToAttack = null;
/* 288:395 */     this.worldObj.setEntityState(this, (byte)18);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public EntityPlayer func_146083_cb()
/* 292:    */   {
/* 293:400 */     return this.field_146084_br;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean isInLove()
/* 297:    */   {
/* 298:408 */     return this.inLove > 0;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void resetInLove()
/* 302:    */   {
/* 303:413 */     this.inLove = 0;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean canMateWith(EntityAnimal par1EntityAnimal)
/* 307:    */   {
/* 308:421 */     return par1EntityAnimal != this;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void handleHealthUpdate(byte par1)
/* 312:    */   {
/* 313:426 */     if (par1 == 18) {
/* 314:428 */       for (int var2 = 0; var2 < 7; var2++)
/* 315:    */       {
/* 316:430 */         double var3 = this.rand.nextGaussian() * 0.02D;
/* 317:431 */         double var5 = this.rand.nextGaussian() * 0.02D;
/* 318:432 */         double var7 = this.rand.nextGaussian() * 0.02D;
/* 319:433 */         this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var3, var5, var7);
/* 320:    */       }
/* 321:    */     } else {
/* 322:438 */       super.handleHealthUpdate(par1);
/* 323:    */     }
/* 324:    */   }
/* 325:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityAnimal
 * JD-Core Version:    0.7.0.1
 */