/*    1:     */ package net.minecraft.entity;
/*    2:     */ 
/*    3:     */ import java.util.Iterator;
/*    4:     */ import java.util.List;
/*    5:     */ import java.util.Random;
/*    6:     */ import java.util.UUID;
/*    7:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*    8:     */ import net.minecraft.entity.ai.EntityAITasks;
/*    9:     */ import net.minecraft.entity.ai.EntityJumpHelper;
/*   10:     */ import net.minecraft.entity.ai.EntityLookHelper;
/*   11:     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*   12:     */ import net.minecraft.entity.ai.EntitySenses;
/*   13:     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*   14:     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   15:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   16:     */ import net.minecraft.entity.item.EntityItem;
/*   17:     */ import net.minecraft.entity.monster.EntityCreeper;
/*   18:     */ import net.minecraft.entity.monster.EntityGhast;
/*   19:     */ import net.minecraft.entity.monster.IMob;
/*   20:     */ import net.minecraft.entity.passive.EntityTameable;
/*   21:     */ import net.minecraft.entity.player.EntityPlayer;
/*   22:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   23:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   24:     */ import net.minecraft.init.Blocks;
/*   25:     */ import net.minecraft.init.Items;
/*   26:     */ import net.minecraft.item.Item;
/*   27:     */ import net.minecraft.item.ItemArmor;
/*   28:     */ import net.minecraft.item.ItemStack;
/*   29:     */ import net.minecraft.item.ItemSword;
/*   30:     */ import net.minecraft.nbt.NBTTagCompound;
/*   31:     */ import net.minecraft.nbt.NBTTagFloat;
/*   32:     */ import net.minecraft.nbt.NBTTagList;
/*   33:     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*   34:     */ import net.minecraft.pathfinding.PathNavigate;
/*   35:     */ import net.minecraft.profiler.Profiler;
/*   36:     */ import net.minecraft.stats.AchievementList;
/*   37:     */ import net.minecraft.util.AxisAlignedBB;
/*   38:     */ import net.minecraft.util.MathHelper;
/*   39:     */ import net.minecraft.world.EnumDifficulty;
/*   40:     */ import net.minecraft.world.GameRules;
/*   41:     */ import net.minecraft.world.World;
/*   42:     */ import net.minecraft.world.WorldServer;
/*   43:     */ 
/*   44:     */ public abstract class EntityLiving
/*   45:     */   extends EntityLivingBase
/*   46:     */ {
/*   47:     */   public int livingSoundTime;
/*   48:     */   protected int experienceValue;
/*   49:     */   private EntityLookHelper lookHelper;
/*   50:     */   private EntityMoveHelper moveHelper;
/*   51:     */   private EntityJumpHelper jumpHelper;
/*   52:     */   private EntityBodyHelper bodyHelper;
/*   53:     */   private PathNavigate navigator;
/*   54:     */   protected final EntityAITasks tasks;
/*   55:     */   protected final EntityAITasks targetTasks;
/*   56:     */   private EntityLivingBase attackTarget;
/*   57:     */   private EntitySenses senses;
/*   58:  58 */   private ItemStack[] equipment = new ItemStack[5];
/*   59:  61 */   protected float[] equipmentDropChances = new float[5];
/*   60:     */   private boolean canPickUpLoot;
/*   61:     */   private boolean persistenceRequired;
/*   62:     */   protected float defaultPitch;
/*   63:     */   private Entity currentTarget;
/*   64:     */   protected int numTicksToChaseTarget;
/*   65:     */   private boolean isLeashed;
/*   66:     */   private Entity leashedToEntity;
/*   67:     */   private NBTTagCompound field_110170_bx;
/*   68:     */   private static final String __OBFID = "CL_00001550";
/*   69:     */   
/*   70:     */   public EntityLiving(World par1World)
/*   71:     */   {
/*   72:  82 */     super(par1World);
/*   73:  83 */     this.tasks = new EntityAITasks((par1World != null) && (par1World.theProfiler != null) ? par1World.theProfiler : null);
/*   74:  84 */     this.targetTasks = new EntityAITasks((par1World != null) && (par1World.theProfiler != null) ? par1World.theProfiler : null);
/*   75:  85 */     this.lookHelper = new EntityLookHelper(this);
/*   76:  86 */     this.moveHelper = new EntityMoveHelper(this);
/*   77:  87 */     this.jumpHelper = new EntityJumpHelper(this);
/*   78:  88 */     this.bodyHelper = new EntityBodyHelper(this);
/*   79:  89 */     this.navigator = new PathNavigate(this, par1World);
/*   80:  90 */     this.senses = new EntitySenses(this);
/*   81:  92 */     for (int var2 = 0; var2 < this.equipmentDropChances.length; var2++) {
/*   82:  94 */       this.equipmentDropChances[var2] = 0.085F;
/*   83:     */     }
/*   84:     */   }
/*   85:     */   
/*   86:     */   protected void applyEntityAttributes()
/*   87:     */   {
/*   88: 100 */     super.applyEntityAttributes();
/*   89: 101 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*   90:     */   }
/*   91:     */   
/*   92:     */   public EntityLookHelper getLookHelper()
/*   93:     */   {
/*   94: 106 */     return this.lookHelper;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public EntityMoveHelper getMoveHelper()
/*   98:     */   {
/*   99: 111 */     return this.moveHelper;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public EntityJumpHelper getJumpHelper()
/*  103:     */   {
/*  104: 116 */     return this.jumpHelper;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public PathNavigate getNavigator()
/*  108:     */   {
/*  109: 121 */     return this.navigator;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public EntitySenses getEntitySenses()
/*  113:     */   {
/*  114: 129 */     return this.senses;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public EntityLivingBase getAttackTarget()
/*  118:     */   {
/*  119: 137 */     return this.attackTarget;
/*  120:     */   }
/*  121:     */   
/*  122:     */   public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
/*  123:     */   {
/*  124: 145 */     this.attackTarget = par1EntityLivingBase;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public boolean canAttackClass(Class par1Class)
/*  128:     */   {
/*  129: 153 */     return (EntityCreeper.class != par1Class) && (EntityGhast.class != par1Class);
/*  130:     */   }
/*  131:     */   
/*  132:     */   public void eatGrassBonus() {}
/*  133:     */   
/*  134:     */   protected void entityInit()
/*  135:     */   {
/*  136: 164 */     super.entityInit();
/*  137: 165 */     this.dataWatcher.addObject(11, Byte.valueOf((byte)0));
/*  138: 166 */     this.dataWatcher.addObject(10, "");
/*  139:     */   }
/*  140:     */   
/*  141:     */   public int getTalkInterval()
/*  142:     */   {
/*  143: 174 */     return 80;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public void playLivingSound()
/*  147:     */   {
/*  148: 182 */     String var1 = getLivingSound();
/*  149: 184 */     if (var1 != null) {
/*  150: 186 */       playSound(var1, getSoundVolume(), getSoundPitch());
/*  151:     */     }
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void onEntityUpdate()
/*  155:     */   {
/*  156: 195 */     super.onEntityUpdate();
/*  157: 196 */     this.worldObj.theProfiler.startSection("mobBaseTick");
/*  158: 198 */     if ((isEntityAlive()) && (this.rand.nextInt(1000) < this.livingSoundTime++))
/*  159:     */     {
/*  160: 200 */       this.livingSoundTime = (-getTalkInterval());
/*  161: 201 */       playLivingSound();
/*  162:     */     }
/*  163: 204 */     this.worldObj.theProfiler.endSection();
/*  164:     */   }
/*  165:     */   
/*  166:     */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/*  167:     */   {
/*  168: 212 */     if (this.experienceValue > 0)
/*  169:     */     {
/*  170: 214 */       int var2 = this.experienceValue;
/*  171: 215 */       ItemStack[] var3 = getLastActiveItems();
/*  172: 217 */       for (int var4 = 0; var4 < var3.length; var4++) {
/*  173: 219 */         if ((var3[var4] != null) && (this.equipmentDropChances[var4] <= 1.0F)) {
/*  174: 221 */           var2 += 1 + this.rand.nextInt(3);
/*  175:     */         }
/*  176:     */       }
/*  177: 225 */       return var2;
/*  178:     */     }
/*  179: 229 */     return this.experienceValue;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void spawnExplosionParticle()
/*  183:     */   {
/*  184: 238 */     for (int var1 = 0; var1 < 20; var1++)
/*  185:     */     {
/*  186: 240 */       double var2 = this.rand.nextGaussian() * 0.02D;
/*  187: 241 */       double var4 = this.rand.nextGaussian() * 0.02D;
/*  188: 242 */       double var6 = this.rand.nextGaussian() * 0.02D;
/*  189: 243 */       double var8 = 10.0D;
/*  190: 244 */       this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width - var2 * var8, this.posY + this.rand.nextFloat() * this.height - var4 * var8, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width - var6 * var8, var2, var4, var6);
/*  191:     */     }
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void onUpdate()
/*  195:     */   {
/*  196: 253 */     super.onUpdate();
/*  197: 255 */     if (!this.worldObj.isClient) {
/*  198: 257 */       updateLeashedState();
/*  199:     */     }
/*  200:     */   }
/*  201:     */   
/*  202:     */   protected float func_110146_f(float par1, float par2)
/*  203:     */   {
/*  204: 263 */     if (isAIEnabled())
/*  205:     */     {
/*  206: 265 */       this.bodyHelper.func_75664_a();
/*  207: 266 */       return par2;
/*  208:     */     }
/*  209: 270 */     return super.func_110146_f(par1, par2);
/*  210:     */   }
/*  211:     */   
/*  212:     */   protected String getLivingSound()
/*  213:     */   {
/*  214: 279 */     return null;
/*  215:     */   }
/*  216:     */   
/*  217:     */   protected Item func_146068_u()
/*  218:     */   {
/*  219: 284 */     return Item.getItemById(0);
/*  220:     */   }
/*  221:     */   
/*  222:     */   protected void dropFewItems(boolean par1, int par2)
/*  223:     */   {
/*  224: 292 */     Item var3 = func_146068_u();
/*  225: 294 */     if (var3 != null)
/*  226:     */     {
/*  227: 296 */       int var4 = this.rand.nextInt(3);
/*  228: 298 */       if (par2 > 0) {
/*  229: 300 */         var4 += this.rand.nextInt(par2 + 1);
/*  230:     */       }
/*  231: 303 */       for (int var5 = 0; var5 < var4; var5++) {
/*  232: 305 */         func_145779_a(var3, 1);
/*  233:     */       }
/*  234:     */     }
/*  235:     */   }
/*  236:     */   
/*  237:     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  238:     */   {
/*  239: 315 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  240: 316 */     par1NBTTagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
/*  241: 317 */     par1NBTTagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
/*  242: 318 */     NBTTagList var2 = new NBTTagList();
/*  243: 321 */     for (int var3 = 0; var3 < this.equipment.length; var3++)
/*  244:     */     {
/*  245: 323 */       NBTTagCompound var4 = new NBTTagCompound();
/*  246: 325 */       if (this.equipment[var3] != null) {
/*  247: 327 */         this.equipment[var3].writeToNBT(var4);
/*  248:     */       }
/*  249: 330 */       var2.appendTag(var4);
/*  250:     */     }
/*  251: 333 */     par1NBTTagCompound.setTag("Equipment", var2);
/*  252: 334 */     NBTTagList var6 = new NBTTagList();
/*  253: 336 */     for (int var7 = 0; var7 < this.equipmentDropChances.length; var7++) {
/*  254: 338 */       var6.appendTag(new NBTTagFloat(this.equipmentDropChances[var7]));
/*  255:     */     }
/*  256: 341 */     par1NBTTagCompound.setTag("DropChances", var6);
/*  257: 342 */     par1NBTTagCompound.setString("CustomName", getCustomNameTag());
/*  258: 343 */     par1NBTTagCompound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*  259: 344 */     par1NBTTagCompound.setBoolean("Leashed", this.isLeashed);
/*  260: 346 */     if (this.leashedToEntity != null)
/*  261:     */     {
/*  262: 348 */       NBTTagCompound var4 = new NBTTagCompound();
/*  263: 350 */       if ((this.leashedToEntity instanceof EntityLivingBase))
/*  264:     */       {
/*  265: 352 */         var4.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
/*  266: 353 */         var4.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
/*  267:     */       }
/*  268: 355 */       else if ((this.leashedToEntity instanceof EntityHanging))
/*  269:     */       {
/*  270: 357 */         EntityHanging var5 = (EntityHanging)this.leashedToEntity;
/*  271: 358 */         var4.setInteger("X", var5.field_146063_b);
/*  272: 359 */         var4.setInteger("Y", var5.field_146064_c);
/*  273: 360 */         var4.setInteger("Z", var5.field_146062_d);
/*  274:     */       }
/*  275: 363 */       par1NBTTagCompound.setTag("Leash", var4);
/*  276:     */     }
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  280:     */   {
/*  281: 372 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  282: 373 */     setCanPickUpLoot(par1NBTTagCompound.getBoolean("CanPickUpLoot"));
/*  283: 374 */     this.persistenceRequired = par1NBTTagCompound.getBoolean("PersistenceRequired");
/*  284: 376 */     if ((par1NBTTagCompound.func_150297_b("CustomName", 8)) && (par1NBTTagCompound.getString("CustomName").length() > 0)) {
/*  285: 378 */       setCustomNameTag(par1NBTTagCompound.getString("CustomName"));
/*  286:     */     }
/*  287: 381 */     setAlwaysRenderNameTag(par1NBTTagCompound.getBoolean("CustomNameVisible"));
/*  288: 385 */     if (par1NBTTagCompound.func_150297_b("Equipment", 9))
/*  289:     */     {
/*  290: 387 */       NBTTagList var2 = par1NBTTagCompound.getTagList("Equipment", 10);
/*  291: 389 */       for (int var3 = 0; var3 < this.equipment.length; var3++) {
/*  292: 391 */         this.equipment[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
/*  293:     */       }
/*  294:     */     }
/*  295: 395 */     if (par1NBTTagCompound.func_150297_b("DropChances", 9))
/*  296:     */     {
/*  297: 397 */       NBTTagList var2 = par1NBTTagCompound.getTagList("DropChances", 5);
/*  298: 399 */       for (int var3 = 0; var3 < var2.tagCount(); var3++) {
/*  299: 401 */         this.equipmentDropChances[var3] = var2.func_150308_e(var3);
/*  300:     */       }
/*  301:     */     }
/*  302: 405 */     this.isLeashed = par1NBTTagCompound.getBoolean("Leashed");
/*  303: 407 */     if ((this.isLeashed) && (par1NBTTagCompound.func_150297_b("Leash", 10))) {
/*  304: 409 */       this.field_110170_bx = par1NBTTagCompound.getCompoundTag("Leash");
/*  305:     */     }
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void setMoveForward(float par1)
/*  309:     */   {
/*  310: 415 */     this.moveForward = par1;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void setAIMoveSpeed(float par1)
/*  314:     */   {
/*  315: 423 */     super.setAIMoveSpeed(par1);
/*  316: 424 */     setMoveForward(par1);
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void onLivingUpdate()
/*  320:     */   {
/*  321: 433 */     super.onLivingUpdate();
/*  322: 434 */     this.worldObj.theProfiler.startSection("looting");
/*  323: 436 */     if ((!this.worldObj.isClient) && (canPickUpLoot()) && (!this.dead) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
/*  324:     */     {
/*  325: 438 */       List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0D, 0.0D, 1.0D));
/*  326: 439 */       Iterator var2 = var1.iterator();
/*  327: 441 */       while (var2.hasNext())
/*  328:     */       {
/*  329: 443 */         EntityItem var3 = (EntityItem)var2.next();
/*  330: 445 */         if ((!var3.isDead) && (var3.getEntityItem() != null))
/*  331:     */         {
/*  332: 447 */           ItemStack var4 = var3.getEntityItem();
/*  333: 448 */           int var5 = getArmorPosition(var4);
/*  334: 450 */           if (var5 > -1)
/*  335:     */           {
/*  336: 452 */             boolean var6 = true;
/*  337: 453 */             ItemStack var7 = getEquipmentInSlot(var5);
/*  338: 455 */             if (var7 != null) {
/*  339: 457 */               if (var5 == 0)
/*  340:     */               {
/*  341: 459 */                 if (((var4.getItem() instanceof ItemSword)) && (!(var7.getItem() instanceof ItemSword)))
/*  342:     */                 {
/*  343: 461 */                   var6 = true;
/*  344:     */                 }
/*  345: 463 */                 else if (((var4.getItem() instanceof ItemSword)) && ((var7.getItem() instanceof ItemSword)))
/*  346:     */                 {
/*  347: 465 */                   ItemSword var8 = (ItemSword)var4.getItem();
/*  348: 466 */                   ItemSword var9 = (ItemSword)var7.getItem();
/*  349: 468 */                   if (var8.func_150931_i() == var9.func_150931_i()) {
/*  350: 470 */                     var6 = (var4.getItemDamage() > var7.getItemDamage()) || ((var4.hasTagCompound()) && (!var7.hasTagCompound()));
/*  351:     */                   } else {
/*  352: 474 */                     var6 = var8.func_150931_i() > var9.func_150931_i();
/*  353:     */                   }
/*  354:     */                 }
/*  355:     */                 else
/*  356:     */                 {
/*  357: 479 */                   var6 = false;
/*  358:     */                 }
/*  359:     */               }
/*  360: 482 */               else if (((var4.getItem() instanceof ItemArmor)) && (!(var7.getItem() instanceof ItemArmor)))
/*  361:     */               {
/*  362: 484 */                 var6 = true;
/*  363:     */               }
/*  364: 486 */               else if (((var4.getItem() instanceof ItemArmor)) && ((var7.getItem() instanceof ItemArmor)))
/*  365:     */               {
/*  366: 488 */                 ItemArmor var11 = (ItemArmor)var4.getItem();
/*  367: 489 */                 ItemArmor var12 = (ItemArmor)var7.getItem();
/*  368: 491 */                 if (var11.damageReduceAmount == var12.damageReduceAmount) {
/*  369: 493 */                   var6 = (var4.getItemDamage() > var7.getItemDamage()) || ((var4.hasTagCompound()) && (!var7.hasTagCompound()));
/*  370:     */                 } else {
/*  371: 497 */                   var6 = var11.damageReduceAmount > var12.damageReduceAmount;
/*  372:     */                 }
/*  373:     */               }
/*  374:     */               else
/*  375:     */               {
/*  376: 502 */                 var6 = false;
/*  377:     */               }
/*  378:     */             }
/*  379: 506 */             if (var6)
/*  380:     */             {
/*  381: 508 */               if ((var7 != null) && (this.rand.nextFloat() - 0.1F < this.equipmentDropChances[var5])) {
/*  382: 510 */                 entityDropItem(var7, 0.0F);
/*  383:     */               }
/*  384: 513 */               if ((var4.getItem() == Items.diamond) && (var3.func_145800_j() != null))
/*  385:     */               {
/*  386: 515 */                 EntityPlayer var10 = this.worldObj.getPlayerEntityByName(var3.func_145800_j());
/*  387: 517 */                 if (var10 != null) {
/*  388: 519 */                   var10.triggerAchievement(AchievementList.field_150966_x);
/*  389:     */                 }
/*  390:     */               }
/*  391: 523 */               setCurrentItemOrArmor(var5, var4);
/*  392: 524 */               this.equipmentDropChances[var5] = 2.0F;
/*  393: 525 */               this.persistenceRequired = true;
/*  394: 526 */               onItemPickup(var3, 1);
/*  395: 527 */               var3.setDead();
/*  396:     */             }
/*  397:     */           }
/*  398:     */         }
/*  399:     */       }
/*  400:     */     }
/*  401: 534 */     this.worldObj.theProfiler.endSection();
/*  402:     */   }
/*  403:     */   
/*  404:     */   protected boolean isAIEnabled()
/*  405:     */   {
/*  406: 542 */     return false;
/*  407:     */   }
/*  408:     */   
/*  409:     */   protected boolean canDespawn()
/*  410:     */   {
/*  411: 550 */     return true;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void despawnEntity()
/*  415:     */   {
/*  416: 558 */     if (this.persistenceRequired)
/*  417:     */     {
/*  418: 560 */       this.entityAge = 0;
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422: 564 */       EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
/*  423: 566 */       if (var1 != null)
/*  424:     */       {
/*  425: 568 */         double var2 = var1.posX - this.posX;
/*  426: 569 */         double var4 = var1.posY - this.posY;
/*  427: 570 */         double var6 = var1.posZ - this.posZ;
/*  428: 571 */         double var8 = var2 * var2 + var4 * var4 + var6 * var6;
/*  429: 573 */         if ((canDespawn()) && (var8 > 16384.0D)) {
/*  430: 575 */           setDead();
/*  431:     */         }
/*  432: 578 */         if ((this.entityAge > 600) && (this.rand.nextInt(800) == 0) && (var8 > 1024.0D) && (canDespawn())) {
/*  433: 580 */           setDead();
/*  434: 582 */         } else if (var8 < 1024.0D) {
/*  435: 584 */           this.entityAge = 0;
/*  436:     */         }
/*  437:     */       }
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   protected void updateAITasks()
/*  442:     */   {
/*  443: 592 */     this.entityAge += 1;
/*  444: 593 */     this.worldObj.theProfiler.startSection("checkDespawn");
/*  445: 594 */     despawnEntity();
/*  446: 595 */     this.worldObj.theProfiler.endSection();
/*  447: 596 */     this.worldObj.theProfiler.startSection("sensing");
/*  448: 597 */     this.senses.clearSensingCache();
/*  449: 598 */     this.worldObj.theProfiler.endSection();
/*  450: 599 */     this.worldObj.theProfiler.startSection("targetSelector");
/*  451: 600 */     this.targetTasks.onUpdateTasks();
/*  452: 601 */     this.worldObj.theProfiler.endSection();
/*  453: 602 */     this.worldObj.theProfiler.startSection("goalSelector");
/*  454: 603 */     this.tasks.onUpdateTasks();
/*  455: 604 */     this.worldObj.theProfiler.endSection();
/*  456: 605 */     this.worldObj.theProfiler.startSection("navigation");
/*  457: 606 */     this.navigator.onUpdateNavigation();
/*  458: 607 */     this.worldObj.theProfiler.endSection();
/*  459: 608 */     this.worldObj.theProfiler.startSection("mob tick");
/*  460: 609 */     updateAITick();
/*  461: 610 */     this.worldObj.theProfiler.endSection();
/*  462: 611 */     this.worldObj.theProfiler.startSection("controls");
/*  463: 612 */     this.worldObj.theProfiler.startSection("move");
/*  464: 613 */     this.moveHelper.onUpdateMoveHelper();
/*  465: 614 */     this.worldObj.theProfiler.endStartSection("look");
/*  466: 615 */     this.lookHelper.onUpdateLook();
/*  467: 616 */     this.worldObj.theProfiler.endStartSection("jump");
/*  468: 617 */     this.jumpHelper.doJump();
/*  469: 618 */     this.worldObj.theProfiler.endSection();
/*  470: 619 */     this.worldObj.theProfiler.endSection();
/*  471:     */   }
/*  472:     */   
/*  473:     */   protected void updateEntityActionState()
/*  474:     */   {
/*  475: 624 */     super.updateEntityActionState();
/*  476: 625 */     this.moveStrafing = 0.0F;
/*  477: 626 */     this.moveForward = 0.0F;
/*  478: 627 */     despawnEntity();
/*  479: 628 */     float var1 = 8.0F;
/*  480: 630 */     if (this.rand.nextFloat() < 0.02F)
/*  481:     */     {
/*  482: 632 */       EntityPlayer var2 = this.worldObj.getClosestPlayerToEntity(this, var1);
/*  483: 634 */       if (var2 != null)
/*  484:     */       {
/*  485: 636 */         this.currentTarget = var2;
/*  486: 637 */         this.numTicksToChaseTarget = (10 + this.rand.nextInt(20));
/*  487:     */       }
/*  488:     */       else
/*  489:     */       {
/*  490: 641 */         this.randomYawVelocity = ((this.rand.nextFloat() - 0.5F) * 20.0F);
/*  491:     */       }
/*  492:     */     }
/*  493: 645 */     if (this.currentTarget != null)
/*  494:     */     {
/*  495: 647 */       faceEntity(this.currentTarget, 10.0F, getVerticalFaceSpeed());
/*  496: 649 */       if ((this.numTicksToChaseTarget-- <= 0) || (this.currentTarget.isDead) || (this.currentTarget.getDistanceSqToEntity(this) > var1 * var1)) {
/*  497: 651 */         this.currentTarget = null;
/*  498:     */       }
/*  499:     */     }
/*  500:     */     else
/*  501:     */     {
/*  502: 656 */       if (this.rand.nextFloat() < 0.05F) {
/*  503: 658 */         this.randomYawVelocity = ((this.rand.nextFloat() - 0.5F) * 20.0F);
/*  504:     */       }
/*  505: 661 */       this.rotationYaw += this.randomYawVelocity;
/*  506: 662 */       this.rotationPitch = this.defaultPitch;
/*  507:     */     }
/*  508: 665 */     boolean var4 = isInWater();
/*  509: 666 */     boolean var3 = handleLavaMovement();
/*  510: 668 */     if ((var4) || (var3)) {
/*  511: 670 */       this.isJumping = (this.rand.nextFloat() < 0.8F);
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public int getVerticalFaceSpeed()
/*  516:     */   {
/*  517: 680 */     return 40;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void faceEntity(Entity par1Entity, float par2, float par3)
/*  521:     */   {
/*  522: 688 */     double var4 = par1Entity.posX - this.posX;
/*  523: 689 */     double var8 = par1Entity.posZ - this.posZ;
/*  524:     */     double var6;
/*  525:     */     double var6;
/*  526: 692 */     if ((par1Entity instanceof EntityLivingBase))
/*  527:     */     {
/*  528: 694 */       EntityLivingBase var10 = (EntityLivingBase)par1Entity;
/*  529: 695 */       var6 = var10.posY + var10.getEyeHeight() - (this.posY + getEyeHeight());
/*  530:     */     }
/*  531:     */     else
/*  532:     */     {
/*  533: 699 */       var6 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D - (this.posY + getEyeHeight());
/*  534:     */     }
/*  535: 702 */     double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
/*  536: 703 */     float var12 = (float)(Math.atan2(var8, var4) * 180.0D / 3.141592653589793D) - 90.0F;
/*  537: 704 */     float var13 = (float)-(Math.atan2(var6, var14) * 180.0D / 3.141592653589793D);
/*  538: 705 */     this.rotationPitch = updateRotation(this.rotationPitch, var13, par3);
/*  539: 706 */     this.rotationYaw = updateRotation(this.rotationYaw, var12, par2);
/*  540:     */   }
/*  541:     */   
/*  542:     */   private float updateRotation(float par1, float par2, float par3)
/*  543:     */   {
/*  544: 714 */     float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
/*  545: 716 */     if (var4 > par3) {
/*  546: 718 */       var4 = par3;
/*  547:     */     }
/*  548: 721 */     if (var4 < -par3) {
/*  549: 723 */       var4 = -par3;
/*  550:     */     }
/*  551: 726 */     return par1 + var4;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public boolean getCanSpawnHere()
/*  555:     */   {
/*  556: 734 */     return (this.worldObj.checkNoEntityCollision(this.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox));
/*  557:     */   }
/*  558:     */   
/*  559:     */   public float getRenderSizeModifier()
/*  560:     */   {
/*  561: 742 */     return 1.0F;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public int getMaxSpawnedInChunk()
/*  565:     */   {
/*  566: 750 */     return 4;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public int getMaxSafePointTries()
/*  570:     */   {
/*  571: 758 */     if (getAttackTarget() == null) {
/*  572: 760 */       return 3;
/*  573:     */     }
/*  574: 764 */     int var1 = (int)(getHealth() - getMaxHealth() * 0.33F);
/*  575: 765 */     var1 -= (3 - this.worldObj.difficultySetting.getDifficultyId()) * 4;
/*  576: 767 */     if (var1 < 0) {
/*  577: 769 */       var1 = 0;
/*  578:     */     }
/*  579: 772 */     return var1 + 3;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public ItemStack getHeldItem()
/*  583:     */   {
/*  584: 781 */     return this.equipment[0];
/*  585:     */   }
/*  586:     */   
/*  587:     */   public ItemStack getEquipmentInSlot(int par1)
/*  588:     */   {
/*  589: 789 */     return this.equipment[par1];
/*  590:     */   }
/*  591:     */   
/*  592:     */   public ItemStack func_130225_q(int par1)
/*  593:     */   {
/*  594: 794 */     return this.equipment[(par1 + 1)];
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
/*  598:     */   {
/*  599: 802 */     this.equipment[par1] = par2ItemStack;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public ItemStack[] getLastActiveItems()
/*  603:     */   {
/*  604: 807 */     return this.equipment;
/*  605:     */   }
/*  606:     */   
/*  607:     */   protected void dropEquipment(boolean par1, int par2)
/*  608:     */   {
/*  609: 815 */     for (int var3 = 0; var3 < getLastActiveItems().length; var3++)
/*  610:     */     {
/*  611: 817 */       ItemStack var4 = getEquipmentInSlot(var3);
/*  612: 818 */       boolean var5 = this.equipmentDropChances[var3] > 1.0F;
/*  613: 820 */       if ((var4 != null) && ((par1) || (var5)) && (this.rand.nextFloat() - par2 * 0.01F < this.equipmentDropChances[var3]))
/*  614:     */       {
/*  615: 822 */         if ((!var5) && (var4.isItemStackDamageable()))
/*  616:     */         {
/*  617: 824 */           int var6 = Math.max(var4.getMaxDamage() - 25, 1);
/*  618: 825 */           int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
/*  619: 827 */           if (var7 > var6) {
/*  620: 829 */             var7 = var6;
/*  621:     */           }
/*  622: 832 */           if (var7 < 1) {
/*  623: 834 */             var7 = 1;
/*  624:     */           }
/*  625: 837 */           var4.setItemDamage(var7);
/*  626:     */         }
/*  627: 840 */         entityDropItem(var4, 0.0F);
/*  628:     */       }
/*  629:     */     }
/*  630:     */   }
/*  631:     */   
/*  632:     */   protected void addRandomArmor()
/*  633:     */   {
/*  634: 850 */     if (this.rand.nextFloat() < 0.15F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ))
/*  635:     */     {
/*  636: 852 */       int var1 = this.rand.nextInt(2);
/*  637: 853 */       float var2 = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.1F : 0.25F;
/*  638: 855 */       if (this.rand.nextFloat() < 0.095F) {
/*  639: 857 */         var1++;
/*  640:     */       }
/*  641: 860 */       if (this.rand.nextFloat() < 0.095F) {
/*  642: 862 */         var1++;
/*  643:     */       }
/*  644: 865 */       if (this.rand.nextFloat() < 0.095F) {
/*  645: 867 */         var1++;
/*  646:     */       }
/*  647: 870 */       for (int var3 = 3; var3 >= 0; var3--)
/*  648:     */       {
/*  649: 872 */         ItemStack var4 = func_130225_q(var3);
/*  650: 874 */         if ((var3 < 3) && (this.rand.nextFloat() < var2)) {
/*  651:     */           break;
/*  652:     */         }
/*  653: 879 */         if (var4 == null)
/*  654:     */         {
/*  655: 881 */           Item var5 = getArmorItemForSlot(var3 + 1, var1);
/*  656: 883 */           if (var5 != null) {
/*  657: 885 */             setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
/*  658:     */           }
/*  659:     */         }
/*  660:     */       }
/*  661:     */     }
/*  662:     */   }
/*  663:     */   
/*  664:     */   public static int getArmorPosition(ItemStack par0ItemStack)
/*  665:     */   {
/*  666: 894 */     if ((par0ItemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin)) && (par0ItemStack.getItem() != Items.skull))
/*  667:     */     {
/*  668: 896 */       if ((par0ItemStack.getItem() instanceof ItemArmor)) {
/*  669: 898 */         switch (((ItemArmor)par0ItemStack.getItem()).armorType)
/*  670:     */         {
/*  671:     */         case 0: 
/*  672: 901 */           return 4;
/*  673:     */         case 1: 
/*  674: 904 */           return 3;
/*  675:     */         case 2: 
/*  676: 907 */           return 2;
/*  677:     */         case 3: 
/*  678: 910 */           return 1;
/*  679:     */         }
/*  680:     */       }
/*  681: 914 */       return 0;
/*  682:     */     }
/*  683: 918 */     return 4;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public static Item getArmorItemForSlot(int par0, int par1)
/*  687:     */   {
/*  688: 927 */     switch (par0)
/*  689:     */     {
/*  690:     */     case 4: 
/*  691: 930 */       if (par1 == 0) {
/*  692: 932 */         return Items.leather_helmet;
/*  693:     */       }
/*  694: 934 */       if (par1 == 1) {
/*  695: 936 */         return Items.golden_helmet;
/*  696:     */       }
/*  697: 938 */       if (par1 == 2) {
/*  698: 940 */         return Items.chainmail_helmet;
/*  699:     */       }
/*  700: 942 */       if (par1 == 3) {
/*  701: 944 */         return Items.iron_helmet;
/*  702:     */       }
/*  703: 946 */       if (par1 == 4) {
/*  704: 948 */         return Items.diamond_helmet;
/*  705:     */       }
/*  706:     */     case 3: 
/*  707: 952 */       if (par1 == 0) {
/*  708: 954 */         return Items.leather_chestplate;
/*  709:     */       }
/*  710: 956 */       if (par1 == 1) {
/*  711: 958 */         return Items.golden_chestplate;
/*  712:     */       }
/*  713: 960 */       if (par1 == 2) {
/*  714: 962 */         return Items.chainmail_chestplate;
/*  715:     */       }
/*  716: 964 */       if (par1 == 3) {
/*  717: 966 */         return Items.iron_chestplate;
/*  718:     */       }
/*  719: 968 */       if (par1 == 4) {
/*  720: 970 */         return Items.diamond_chestplate;
/*  721:     */       }
/*  722:     */     case 2: 
/*  723: 974 */       if (par1 == 0) {
/*  724: 976 */         return Items.leather_leggings;
/*  725:     */       }
/*  726: 978 */       if (par1 == 1) {
/*  727: 980 */         return Items.golden_leggings;
/*  728:     */       }
/*  729: 982 */       if (par1 == 2) {
/*  730: 984 */         return Items.chainmail_leggings;
/*  731:     */       }
/*  732: 986 */       if (par1 == 3) {
/*  733: 988 */         return Items.iron_leggings;
/*  734:     */       }
/*  735: 990 */       if (par1 == 4) {
/*  736: 992 */         return Items.diamond_leggings;
/*  737:     */       }
/*  738:     */     case 1: 
/*  739: 996 */       if (par1 == 0) {
/*  740: 998 */         return Items.leather_boots;
/*  741:     */       }
/*  742:1000 */       if (par1 == 1) {
/*  743:1002 */         return Items.golden_boots;
/*  744:     */       }
/*  745:1004 */       if (par1 == 2) {
/*  746:1006 */         return Items.chainmail_boots;
/*  747:     */       }
/*  748:1008 */       if (par1 == 3) {
/*  749:1010 */         return Items.iron_boots;
/*  750:     */       }
/*  751:1012 */       if (par1 == 4) {
/*  752:1014 */         return Items.diamond_boots;
/*  753:     */       }
/*  754:     */       break;
/*  755:     */     }
/*  756:1018 */     return null;
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected void enchantEquipment()
/*  760:     */   {
/*  761:1027 */     float var1 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
/*  762:1029 */     if ((getHeldItem() != null) && (this.rand.nextFloat() < 0.25F * var1)) {
/*  763:1031 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(5.0F + var1 * this.rand.nextInt(18)));
/*  764:     */     }
/*  765:1034 */     for (int var2 = 0; var2 < 4; var2++)
/*  766:     */     {
/*  767:1036 */       ItemStack var3 = func_130225_q(var2);
/*  768:1038 */       if ((var3 != null) && (this.rand.nextFloat() < 0.5F * var1)) {
/*  769:1040 */         EnchantmentHelper.addRandomEnchantment(this.rand, var3, (int)(5.0F + var1 * this.rand.nextInt(18)));
/*  770:     */       }
/*  771:     */     }
/*  772:     */   }
/*  773:     */   
/*  774:     */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/*  775:     */   {
/*  776:1047 */     getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
/*  777:1048 */     return par1EntityLivingData;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public boolean canBeSteered()
/*  781:     */   {
/*  782:1057 */     return false;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public String getCommandSenderName()
/*  786:     */   {
/*  787:1065 */     return hasCustomNameTag() ? getCustomNameTag() : super.getCommandSenderName();
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void func_110163_bv()
/*  791:     */   {
/*  792:1070 */     this.persistenceRequired = true;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public void setCustomNameTag(String par1Str)
/*  796:     */   {
/*  797:1075 */     this.dataWatcher.updateObject(10, par1Str);
/*  798:     */   }
/*  799:     */   
/*  800:     */   public String getCustomNameTag()
/*  801:     */   {
/*  802:1080 */     return this.dataWatcher.getWatchableObjectString(10);
/*  803:     */   }
/*  804:     */   
/*  805:     */   public boolean hasCustomNameTag()
/*  806:     */   {
/*  807:1085 */     return this.dataWatcher.getWatchableObjectString(10).length() > 0;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void setAlwaysRenderNameTag(boolean par1)
/*  811:     */   {
/*  812:1090 */     this.dataWatcher.updateObject(11, Byte.valueOf((byte)(par1 ? 1 : 0)));
/*  813:     */   }
/*  814:     */   
/*  815:     */   public boolean getAlwaysRenderNameTag()
/*  816:     */   {
/*  817:1095 */     return this.dataWatcher.getWatchableObjectByte(11) == 1;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public boolean getAlwaysRenderNameTagForRender()
/*  821:     */   {
/*  822:1100 */     return getAlwaysRenderNameTag();
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void setEquipmentDropChance(int par1, float par2)
/*  826:     */   {
/*  827:1105 */     this.equipmentDropChances[par1] = par2;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public boolean canPickUpLoot()
/*  831:     */   {
/*  832:1110 */     return this.canPickUpLoot;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void setCanPickUpLoot(boolean par1)
/*  836:     */   {
/*  837:1115 */     this.canPickUpLoot = par1;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public boolean isNoDespawnRequired()
/*  841:     */   {
/*  842:1120 */     return this.persistenceRequired;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public final boolean interactFirst(EntityPlayer par1EntityPlayer)
/*  846:     */   {
/*  847:1128 */     if ((getLeashed()) && (getLeashedToEntity() == par1EntityPlayer))
/*  848:     */     {
/*  849:1130 */       clearLeashed(true, !par1EntityPlayer.capabilities.isCreativeMode);
/*  850:1131 */       return true;
/*  851:     */     }
/*  852:1135 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/*  853:1137 */     if ((var2 != null) && (var2.getItem() == Items.lead) && (allowLeashing()))
/*  854:     */     {
/*  855:1139 */       if ((!(this instanceof EntityTameable)) || (!((EntityTameable)this).isTamed()))
/*  856:     */       {
/*  857:1141 */         setLeashedToEntity(par1EntityPlayer, true);
/*  858:1142 */         var2.stackSize -= 1;
/*  859:1143 */         return true;
/*  860:     */       }
/*  861:1146 */       if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(((EntityTameable)this).getOwnerName()))
/*  862:     */       {
/*  863:1148 */         setLeashedToEntity(par1EntityPlayer, true);
/*  864:1149 */         var2.stackSize -= 1;
/*  865:1150 */         return true;
/*  866:     */       }
/*  867:     */     }
/*  868:1154 */     return interact(par1EntityPlayer) ? true : super.interactFirst(par1EntityPlayer);
/*  869:     */   }
/*  870:     */   
/*  871:     */   protected boolean interact(EntityPlayer par1EntityPlayer)
/*  872:     */   {
/*  873:1163 */     return false;
/*  874:     */   }
/*  875:     */   
/*  876:     */   protected void updateLeashedState()
/*  877:     */   {
/*  878:1171 */     if (this.field_110170_bx != null) {
/*  879:1173 */       recreateLeash();
/*  880:     */     }
/*  881:1176 */     if (this.isLeashed) {
/*  882:1178 */       if ((this.leashedToEntity == null) || (this.leashedToEntity.isDead)) {
/*  883:1180 */         clearLeashed(true, true);
/*  884:     */       }
/*  885:     */     }
/*  886:     */   }
/*  887:     */   
/*  888:     */   public void clearLeashed(boolean par1, boolean par2)
/*  889:     */   {
/*  890:1190 */     if (this.isLeashed)
/*  891:     */     {
/*  892:1192 */       this.isLeashed = false;
/*  893:1193 */       this.leashedToEntity = null;
/*  894:1195 */       if ((!this.worldObj.isClient) && (par2)) {
/*  895:1197 */         func_145779_a(Items.lead, 1);
/*  896:     */       }
/*  897:1200 */       if ((!this.worldObj.isClient) && (par1) && ((this.worldObj instanceof WorldServer))) {
/*  898:1202 */         ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, null));
/*  899:     */       }
/*  900:     */     }
/*  901:     */   }
/*  902:     */   
/*  903:     */   public boolean allowLeashing()
/*  904:     */   {
/*  905:1209 */     return (!getLeashed()) && (!(this instanceof IMob));
/*  906:     */   }
/*  907:     */   
/*  908:     */   public boolean getLeashed()
/*  909:     */   {
/*  910:1214 */     return this.isLeashed;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public Entity getLeashedToEntity()
/*  914:     */   {
/*  915:1219 */     return this.leashedToEntity;
/*  916:     */   }
/*  917:     */   
/*  918:     */   public void setLeashedToEntity(Entity par1Entity, boolean par2)
/*  919:     */   {
/*  920:1228 */     this.isLeashed = true;
/*  921:1229 */     this.leashedToEntity = par1Entity;
/*  922:1231 */     if ((!this.worldObj.isClient) && (par2) && ((this.worldObj instanceof WorldServer))) {
/*  923:1233 */       ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
/*  924:     */     }
/*  925:     */   }
/*  926:     */   
/*  927:     */   private void recreateLeash()
/*  928:     */   {
/*  929:1239 */     if ((this.isLeashed) && (this.field_110170_bx != null)) {
/*  930:1241 */       if ((this.field_110170_bx.func_150297_b("UUIDMost", 4)) && (this.field_110170_bx.func_150297_b("UUIDLeast", 4)))
/*  931:     */       {
/*  932:1243 */         UUID var5 = new UUID(this.field_110170_bx.getLong("UUIDMost"), this.field_110170_bx.getLong("UUIDLeast"));
/*  933:1244 */         List var6 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0D, 10.0D, 10.0D));
/*  934:1245 */         Iterator var7 = var6.iterator();
/*  935:1247 */         while (var7.hasNext())
/*  936:     */         {
/*  937:1249 */           EntityLivingBase var8 = (EntityLivingBase)var7.next();
/*  938:1251 */           if (var8.getUniqueID().equals(var5))
/*  939:     */           {
/*  940:1253 */             this.leashedToEntity = var8;
/*  941:1254 */             break;
/*  942:     */           }
/*  943:     */         }
/*  944:     */       }
/*  945:1258 */       else if ((this.field_110170_bx.func_150297_b("X", 99)) && (this.field_110170_bx.func_150297_b("Y", 99)) && (this.field_110170_bx.func_150297_b("Z", 99)))
/*  946:     */       {
/*  947:1260 */         int var1 = this.field_110170_bx.getInteger("X");
/*  948:1261 */         int var2 = this.field_110170_bx.getInteger("Y");
/*  949:1262 */         int var3 = this.field_110170_bx.getInteger("Z");
/*  950:1263 */         EntityLeashKnot var4 = EntityLeashKnot.getKnotForBlock(this.worldObj, var1, var2, var3);
/*  951:1265 */         if (var4 == null) {
/*  952:1267 */           var4 = EntityLeashKnot.func_110129_a(this.worldObj, var1, var2, var3);
/*  953:     */         }
/*  954:1270 */         this.leashedToEntity = var4;
/*  955:     */       }
/*  956:     */       else
/*  957:     */       {
/*  958:1274 */         clearLeashed(false, true);
/*  959:     */       }
/*  960:     */     }
/*  961:1278 */     this.field_110170_bx = null;
/*  962:     */   }
/*  963:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityLiving
 * JD-Core Version:    0.7.0.1
 */