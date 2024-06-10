/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import java.util.UUID;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  11:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  12:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Blocks;
/*  15:    */ import net.minecraft.init.Items;
/*  16:    */ import net.minecraft.item.Item;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.util.AxisAlignedBB;
/*  20:    */ import net.minecraft.util.DamageSource;
/*  21:    */ import net.minecraft.util.EntityDamageSource;
/*  22:    */ import net.minecraft.util.EntityDamageSourceIndirect;
/*  23:    */ import net.minecraft.util.MathHelper;
/*  24:    */ import net.minecraft.util.Vec3;
/*  25:    */ import net.minecraft.util.Vec3Pool;
/*  26:    */ import net.minecraft.world.GameRules;
/*  27:    */ import net.minecraft.world.World;
/*  28:    */ 
/*  29:    */ public class EntityEnderman
/*  30:    */   extends EntityMob
/*  31:    */ {
/*  32: 25 */   private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  33: 26 */   private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 6.199999809265137D, 0).setSaved(false);
/*  34: 27 */   private static boolean[] carriableBlocks = new boolean[256];
/*  35:    */   private int teleportDelay;
/*  36:    */   private int stareTimer;
/*  37:    */   private Entity lastEntityToAttack;
/*  38:    */   private boolean isAggressive;
/*  39:    */   private static final String __OBFID = "CL_00001685";
/*  40:    */   
/*  41:    */   public EntityEnderman(World par1World)
/*  42:    */   {
/*  43: 44 */     super(par1World);
/*  44: 45 */     setSize(0.6F, 2.9F);
/*  45: 46 */     this.stepHeight = 1.0F;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void applyEntityAttributes()
/*  49:    */   {
/*  50: 51 */     super.applyEntityAttributes();
/*  51: 52 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
/*  52: 53 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.300000011920929D);
/*  53: 54 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected void entityInit()
/*  57:    */   {
/*  58: 59 */     super.entityInit();
/*  59: 60 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*  60: 61 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*  61: 62 */     this.dataWatcher.addObject(18, new Byte((byte)0));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  65:    */   {
/*  66: 70 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  67: 71 */     par1NBTTagCompound.setShort("carried", (short)Block.getIdFromBlock(func_146080_bZ()));
/*  68: 72 */     par1NBTTagCompound.setShort("carriedData", (short)getCarryingData());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  72:    */   {
/*  73: 80 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  74: 81 */     func_146081_a(Block.getBlockById(par1NBTTagCompound.getShort("carried")));
/*  75: 82 */     setCarryingData(par1NBTTagCompound.getShort("carriedData"));
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected Entity findPlayerToAttack()
/*  79:    */   {
/*  80: 91 */     EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);
/*  81: 93 */     if (var1 != null) {
/*  82: 95 */       if (shouldAttackPlayer(var1))
/*  83:    */       {
/*  84: 97 */         this.isAggressive = true;
/*  85: 99 */         if (this.stareTimer == 0) {
/*  86:101 */           this.worldObj.playSoundEffect(var1.posX, var1.posY, var1.posZ, "mob.endermen.stare", 1.0F, 1.0F);
/*  87:    */         }
/*  88:104 */         if (this.stareTimer++ == 5)
/*  89:    */         {
/*  90:106 */           this.stareTimer = 0;
/*  91:107 */           setScreaming(true);
/*  92:108 */           return var1;
/*  93:    */         }
/*  94:    */       }
/*  95:    */       else
/*  96:    */       {
/*  97:113 */         this.stareTimer = 0;
/*  98:    */       }
/*  99:    */     }
/* 100:117 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer)
/* 104:    */   {
/* 105:125 */     ItemStack var2 = par1EntityPlayer.inventory.armorInventory[3];
/* 106:127 */     if ((var2 != null) && (var2.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
/* 107:129 */       return false;
/* 108:    */     }
/* 109:133 */     Vec3 var3 = par1EntityPlayer.getLook(1.0F).normalize();
/* 110:134 */     Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + this.height / 2.0F - (par1EntityPlayer.posY + par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
/* 111:135 */     double var5 = var4.lengthVector();
/* 112:136 */     var4 = var4.normalize();
/* 113:137 */     double var7 = var3.dotProduct(var4);
/* 114:138 */     return var7 > 1.0D - 0.025D / var5 ? par1EntityPlayer.canEntityBeSeen(this) : false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void onLivingUpdate()
/* 118:    */   {
/* 119:148 */     if (isWet()) {
/* 120:150 */       attackEntityFrom(DamageSource.drown, 1.0F);
/* 121:    */     }
/* 122:153 */     if (this.lastEntityToAttack != this.entityToAttack)
/* 123:    */     {
/* 124:155 */       IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 125:156 */       var1.removeModifier(attackingSpeedBoostModifier);
/* 126:158 */       if (this.entityToAttack != null) {
/* 127:160 */         var1.applyModifier(attackingSpeedBoostModifier);
/* 128:    */       }
/* 129:    */     }
/* 130:164 */     this.lastEntityToAttack = this.entityToAttack;
/* 131:167 */     if ((!this.worldObj.isClient) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))) {
/* 132:173 */       if (func_146080_bZ().getMaterial() == Material.air)
/* 133:    */       {
/* 134:175 */         if (this.rand.nextInt(20) == 0)
/* 135:    */         {
/* 136:177 */           int var6 = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
/* 137:178 */           int var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
/* 138:179 */           int var3 = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
/* 139:180 */           Block var4 = this.worldObj.getBlock(var6, var2, var3);
/* 140:182 */           if (carriableBlocks[Block.getIdFromBlock(var4)] != 0)
/* 141:    */           {
/* 142:184 */             func_146081_a(var4);
/* 143:185 */             setCarryingData(this.worldObj.getBlockMetadata(var6, var2, var3));
/* 144:186 */             this.worldObj.setBlock(var6, var2, var3, Blocks.air);
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:190 */       else if (this.rand.nextInt(2000) == 0)
/* 149:    */       {
/* 150:192 */         int var6 = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
/* 151:193 */         int var2 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
/* 152:194 */         int var3 = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
/* 153:195 */         Block var4 = this.worldObj.getBlock(var6, var2, var3);
/* 154:196 */         Block var5 = this.worldObj.getBlock(var6, var2 - 1, var3);
/* 155:198 */         if ((var4.getMaterial() == Material.air) && (var5.getMaterial() != Material.air) && (var5.renderAsNormalBlock()))
/* 156:    */         {
/* 157:200 */           this.worldObj.setBlock(var6, var2, var3, func_146080_bZ(), getCarryingData(), 3);
/* 158:201 */           func_146081_a(Blocks.air);
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:206 */     for (int var6 = 0; var6 < 2; var6++) {
/* 163:208 */       this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
/* 164:    */     }
/* 165:211 */     if ((this.worldObj.isDaytime()) && (!this.worldObj.isClient))
/* 166:    */     {
/* 167:213 */       float var7 = getBrightness(1.0F);
/* 168:215 */       if ((var7 > 0.5F) && (this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) && (this.rand.nextFloat() * 30.0F < (var7 - 0.4F) * 2.0F))
/* 169:    */       {
/* 170:217 */         this.entityToAttack = null;
/* 171:218 */         setScreaming(false);
/* 172:219 */         this.isAggressive = false;
/* 173:220 */         teleportRandomly();
/* 174:    */       }
/* 175:    */     }
/* 176:224 */     if ((isWet()) || (isBurning()))
/* 177:    */     {
/* 178:226 */       this.entityToAttack = null;
/* 179:227 */       setScreaming(false);
/* 180:228 */       this.isAggressive = false;
/* 181:229 */       teleportRandomly();
/* 182:    */     }
/* 183:232 */     if ((isScreaming()) && (!this.isAggressive) && (this.rand.nextInt(100) == 0)) {
/* 184:234 */       setScreaming(false);
/* 185:    */     }
/* 186:237 */     this.isJumping = false;
/* 187:239 */     if (this.entityToAttack != null) {
/* 188:241 */       faceEntity(this.entityToAttack, 100.0F, 100.0F);
/* 189:    */     }
/* 190:244 */     if ((!this.worldObj.isClient) && (isEntityAlive())) {
/* 191:246 */       if (this.entityToAttack != null)
/* 192:    */       {
/* 193:248 */         if (((this.entityToAttack instanceof EntityPlayer)) && (shouldAttackPlayer((EntityPlayer)this.entityToAttack)))
/* 194:    */         {
/* 195:250 */           if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0D) {
/* 196:252 */             teleportRandomly();
/* 197:    */           }
/* 198:255 */           this.teleportDelay = 0;
/* 199:    */         }
/* 200:257 */         else if ((this.entityToAttack.getDistanceSqToEntity(this) > 256.0D) && (this.teleportDelay++ >= 30) && (teleportToEntity(this.entityToAttack)))
/* 201:    */         {
/* 202:259 */           this.teleportDelay = 0;
/* 203:    */         }
/* 204:    */       }
/* 205:    */       else
/* 206:    */       {
/* 207:264 */         setScreaming(false);
/* 208:265 */         this.teleportDelay = 0;
/* 209:    */       }
/* 210:    */     }
/* 211:269 */     super.onLivingUpdate();
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected boolean teleportRandomly()
/* 215:    */   {
/* 216:277 */     double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 217:278 */     double var3 = this.posY + (this.rand.nextInt(64) - 32);
/* 218:279 */     double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 219:280 */     return teleportTo(var1, var3, var5);
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected boolean teleportToEntity(Entity par1Entity)
/* 223:    */   {
/* 224:288 */     Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1Entity.posX, this.boundingBox.minY + this.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(), this.posZ - par1Entity.posZ);
/* 225:289 */     var2 = var2.normalize();
/* 226:290 */     double var3 = 16.0D;
/* 227:291 */     double var5 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
/* 228:292 */     double var7 = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * var3;
/* 229:293 */     double var9 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
/* 230:294 */     return teleportTo(var5, var7, var9);
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected boolean teleportTo(double par1, double par3, double par5)
/* 234:    */   {
/* 235:302 */     double var7 = this.posX;
/* 236:303 */     double var9 = this.posY;
/* 237:304 */     double var11 = this.posZ;
/* 238:305 */     this.posX = par1;
/* 239:306 */     this.posY = par3;
/* 240:307 */     this.posZ = par5;
/* 241:308 */     boolean var13 = false;
/* 242:309 */     int var14 = MathHelper.floor_double(this.posX);
/* 243:310 */     int var15 = MathHelper.floor_double(this.posY);
/* 244:311 */     int var16 = MathHelper.floor_double(this.posZ);
/* 245:313 */     if (this.worldObj.blockExists(var14, var15, var16))
/* 246:    */     {
/* 247:315 */       boolean var17 = false;
/* 248:317 */       while ((!var17) && (var15 > 0))
/* 249:    */       {
/* 250:319 */         Block var18 = this.worldObj.getBlock(var14, var15 - 1, var16);
/* 251:321 */         if (var18.getMaterial().blocksMovement())
/* 252:    */         {
/* 253:323 */           var17 = true;
/* 254:    */         }
/* 255:    */         else
/* 256:    */         {
/* 257:327 */           this.posY -= 1.0D;
/* 258:328 */           var15--;
/* 259:    */         }
/* 260:    */       }
/* 261:332 */       if (var17)
/* 262:    */       {
/* 263:334 */         setPosition(this.posX, this.posY, this.posZ);
/* 264:336 */         if ((this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox))) {
/* 265:338 */           var13 = true;
/* 266:    */         }
/* 267:    */       }
/* 268:    */     }
/* 269:343 */     if (!var13)
/* 270:    */     {
/* 271:345 */       setPosition(var7, var9, var11);
/* 272:346 */       return false;
/* 273:    */     }
/* 274:350 */     short var30 = 128;
/* 275:352 */     for (int var31 = 0; var31 < var30; var31++)
/* 276:    */     {
/* 277:354 */       double var19 = var31 / (var30 - 1.0D);
/* 278:355 */       float var21 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 279:356 */       float var22 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 280:357 */       float var23 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 281:358 */       double var24 = var7 + (this.posX - var7) * var19 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 282:359 */       double var26 = var9 + (this.posY - var9) * var19 + this.rand.nextDouble() * this.height;
/* 283:360 */       double var28 = var11 + (this.posZ - var11) * var19 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 284:361 */       this.worldObj.spawnParticle("portal", var24, var26, var28, var21, var22, var23);
/* 285:    */     }
/* 286:364 */     this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
/* 287:365 */     playSound("mob.endermen.portal", 1.0F, 1.0F);
/* 288:366 */     return true;
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected String getLivingSound()
/* 292:    */   {
/* 293:375 */     return isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
/* 294:    */   }
/* 295:    */   
/* 296:    */   protected String getHurtSound()
/* 297:    */   {
/* 298:383 */     return "mob.endermen.hit";
/* 299:    */   }
/* 300:    */   
/* 301:    */   protected String getDeathSound()
/* 302:    */   {
/* 303:391 */     return "mob.endermen.death";
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected Item func_146068_u()
/* 307:    */   {
/* 308:396 */     return Items.ender_pearl;
/* 309:    */   }
/* 310:    */   
/* 311:    */   protected void dropFewItems(boolean par1, int par2)
/* 312:    */   {
/* 313:404 */     Item var3 = func_146068_u();
/* 314:406 */     if (var3 != null)
/* 315:    */     {
/* 316:408 */       int var4 = this.rand.nextInt(2 + par2);
/* 317:410 */       for (int var5 = 0; var5 < var4; var5++) {
/* 318:412 */         func_145779_a(var3, 1);
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void func_146081_a(Block p_146081_1_)
/* 324:    */   {
/* 325:419 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(Block.getIdFromBlock(p_146081_1_) & 0xFF)));
/* 326:    */   }
/* 327:    */   
/* 328:    */   public Block func_146080_bZ()
/* 329:    */   {
/* 330:424 */     return Block.getBlockById(this.dataWatcher.getWatchableObjectByte(16));
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setCarryingData(int par1)
/* 334:    */   {
/* 335:432 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1 & 0xFF)));
/* 336:    */   }
/* 337:    */   
/* 338:    */   public int getCarryingData()
/* 339:    */   {
/* 340:440 */     return this.dataWatcher.getWatchableObjectByte(17);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 344:    */   {
/* 345:448 */     if (isEntityInvulnerable()) {
/* 346:450 */       return false;
/* 347:    */     }
/* 348:454 */     setScreaming(true);
/* 349:456 */     if (((par1DamageSource instanceof EntityDamageSource)) && ((par1DamageSource.getEntity() instanceof EntityPlayer))) {
/* 350:458 */       this.isAggressive = true;
/* 351:    */     }
/* 352:461 */     if ((par1DamageSource instanceof EntityDamageSourceIndirect))
/* 353:    */     {
/* 354:463 */       this.isAggressive = false;
/* 355:465 */       for (int var3 = 0; var3 < 64; var3++) {
/* 356:467 */         if (teleportRandomly()) {
/* 357:469 */           return true;
/* 358:    */         }
/* 359:    */       }
/* 360:473 */       return false;
/* 361:    */     }
/* 362:477 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public boolean isScreaming()
/* 366:    */   {
/* 367:484 */     return this.dataWatcher.getWatchableObjectByte(18) > 0;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setScreaming(boolean par1)
/* 371:    */   {
/* 372:489 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)(par1 ? 1 : 0)));
/* 373:    */   }
/* 374:    */   
/* 375:    */   static
/* 376:    */   {
/* 377:494 */     carriableBlocks[Block.getIdFromBlock(Blocks.grass)] = true;
/* 378:495 */     carriableBlocks[Block.getIdFromBlock(Blocks.dirt)] = true;
/* 379:496 */     carriableBlocks[Block.getIdFromBlock(Blocks.sand)] = true;
/* 380:497 */     carriableBlocks[Block.getIdFromBlock(Blocks.gravel)] = true;
/* 381:498 */     carriableBlocks[Block.getIdFromBlock(Blocks.yellow_flower)] = true;
/* 382:499 */     carriableBlocks[Block.getIdFromBlock(Blocks.red_flower)] = true;
/* 383:500 */     carriableBlocks[Block.getIdFromBlock(Blocks.brown_mushroom)] = true;
/* 384:501 */     carriableBlocks[Block.getIdFromBlock(Blocks.red_mushroom)] = true;
/* 385:502 */     carriableBlocks[Block.getIdFromBlock(Blocks.tnt)] = true;
/* 386:503 */     carriableBlocks[Block.getIdFromBlock(Blocks.cactus)] = true;
/* 387:504 */     carriableBlocks[Block.getIdFromBlock(Blocks.clay)] = true;
/* 388:505 */     carriableBlocks[Block.getIdFromBlock(Blocks.pumpkin)] = true;
/* 389:506 */     carriableBlocks[Block.getIdFromBlock(Blocks.melon_block)] = true;
/* 390:507 */     carriableBlocks[Block.getIdFromBlock(Blocks.mycelium)] = true;
/* 391:    */   }
/* 392:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityEnderman
 * JD-Core Version:    0.7.0.1
 */