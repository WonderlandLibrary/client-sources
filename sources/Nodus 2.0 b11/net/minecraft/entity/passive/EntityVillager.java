/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.enchantment.Enchantment;
/*   9:    */ import net.minecraft.enchantment.EnchantmentData;
/*  10:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  11:    */ import net.minecraft.entity.DataWatcher;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.EntityAgeable;
/*  14:    */ import net.minecraft.entity.EntityLiving;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.IEntityLivingData;
/*  17:    */ import net.minecraft.entity.IMerchant;
/*  18:    */ import net.minecraft.entity.INpc;
/*  19:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  20:    */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*  21:    */ import net.minecraft.entity.ai.EntityAIFollowGolem;
/*  22:    */ import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
/*  23:    */ import net.minecraft.entity.ai.EntityAIMoveIndoors;
/*  24:    */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*  25:    */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*  26:    */ import net.minecraft.entity.ai.EntityAIPlay;
/*  27:    */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*  28:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  29:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  30:    */ import net.minecraft.entity.ai.EntityAITradePlayer;
/*  31:    */ import net.minecraft.entity.ai.EntityAIVillagerMate;
/*  32:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  33:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  34:    */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*  35:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  36:    */ import net.minecraft.entity.monster.EntityZombie;
/*  37:    */ import net.minecraft.entity.monster.IMob;
/*  38:    */ import net.minecraft.entity.player.EntityPlayer;
/*  39:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  40:    */ import net.minecraft.init.Blocks;
/*  41:    */ import net.minecraft.init.Items;
/*  42:    */ import net.minecraft.item.Item;
/*  43:    */ import net.minecraft.item.ItemEnchantedBook;
/*  44:    */ import net.minecraft.item.ItemStack;
/*  45:    */ import net.minecraft.nbt.NBTTagCompound;
/*  46:    */ import net.minecraft.pathfinding.PathNavigate;
/*  47:    */ import net.minecraft.potion.Potion;
/*  48:    */ import net.minecraft.potion.PotionEffect;
/*  49:    */ import net.minecraft.util.ChunkCoordinates;
/*  50:    */ import net.minecraft.util.DamageSource;
/*  51:    */ import net.minecraft.util.MathHelper;
/*  52:    */ import net.minecraft.util.Tuple;
/*  53:    */ import net.minecraft.village.MerchantRecipe;
/*  54:    */ import net.minecraft.village.MerchantRecipeList;
/*  55:    */ import net.minecraft.village.Village;
/*  56:    */ import net.minecraft.village.VillageCollection;
/*  57:    */ import net.minecraft.world.World;
/*  58:    */ 
/*  59:    */ public class EntityVillager
/*  60:    */   extends EntityAgeable
/*  61:    */   implements IMerchant, INpc
/*  62:    */ {
/*  63:    */   private int randomTickDivider;
/*  64:    */   private boolean isMating;
/*  65:    */   private boolean isPlaying;
/*  66:    */   Village villageObj;
/*  67:    */   private EntityPlayer buyingPlayer;
/*  68:    */   private MerchantRecipeList buyingList;
/*  69:    */   private int timeUntilReset;
/*  70:    */   private boolean needsInitilization;
/*  71:    */   private int wealth;
/*  72:    */   private String lastBuyingPlayer;
/*  73:    */   private boolean isLookingForHome;
/*  74:    */   private float field_82191_bN;
/*  75: 76 */   private static final Map villagersSellingList = new HashMap();
/*  76: 79 */   private static final Map blacksmithSellingList = new HashMap();
/*  77:    */   private static final String __OBFID = "CL_00001707";
/*  78:    */   
/*  79:    */   public EntityVillager(World par1World)
/*  80:    */   {
/*  81: 84 */     this(par1World, 0);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public EntityVillager(World par1World, int par2)
/*  85:    */   {
/*  86: 89 */     super(par1World);
/*  87: 90 */     setProfession(par2);
/*  88: 91 */     setSize(0.6F, 1.8F);
/*  89: 92 */     getNavigator().setBreakDoors(true);
/*  90: 93 */     getNavigator().setAvoidsWater(true);
/*  91: 94 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  92: 95 */     this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*  93: 96 */     this.tasks.addTask(1, new EntityAITradePlayer(this));
/*  94: 97 */     this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
/*  95: 98 */     this.tasks.addTask(2, new EntityAIMoveIndoors(this));
/*  96: 99 */     this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
/*  97:100 */     this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
/*  98:101 */     this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
/*  99:102 */     this.tasks.addTask(6, new EntityAIVillagerMate(this));
/* 100:103 */     this.tasks.addTask(7, new EntityAIFollowGolem(this));
/* 101:104 */     this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
/* 102:105 */     this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
/* 103:106 */     this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
/* 104:107 */     this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
/* 105:108 */     this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void applyEntityAttributes()
/* 109:    */   {
/* 110:113 */     super.applyEntityAttributes();
/* 111:114 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isAIEnabled()
/* 115:    */   {
/* 116:122 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void updateAITick()
/* 120:    */   {
/* 121:130 */     if (--this.randomTickDivider <= 0)
/* 122:    */     {
/* 123:132 */       this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
/* 124:133 */       this.randomTickDivider = (70 + this.rand.nextInt(50));
/* 125:134 */       this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
/* 126:136 */       if (this.villageObj == null)
/* 127:    */       {
/* 128:138 */         detachHome();
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:142 */         ChunkCoordinates var1 = this.villageObj.getCenter();
/* 133:143 */         setHomeArea(var1.posX, var1.posY, var1.posZ, (int)(this.villageObj.getVillageRadius() * 0.6F));
/* 134:145 */         if (this.isLookingForHome)
/* 135:    */         {
/* 136:147 */           this.isLookingForHome = false;
/* 137:148 */           this.villageObj.setDefaultPlayerReputation(5);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:153 */     if ((!isTrading()) && (this.timeUntilReset > 0))
/* 142:    */     {
/* 143:155 */       this.timeUntilReset -= 1;
/* 144:157 */       if (this.timeUntilReset <= 0)
/* 145:    */       {
/* 146:159 */         if (this.needsInitilization)
/* 147:    */         {
/* 148:161 */           if (this.buyingList.size() > 1)
/* 149:    */           {
/* 150:163 */             Iterator var3 = this.buyingList.iterator();
/* 151:165 */             while (var3.hasNext())
/* 152:    */             {
/* 153:167 */               MerchantRecipe var2 = (MerchantRecipe)var3.next();
/* 154:169 */               if (var2.isRecipeDisabled()) {
/* 155:171 */                 var2.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
/* 156:    */               }
/* 157:    */             }
/* 158:    */           }
/* 159:176 */           addDefaultEquipmentAndRecipies(1);
/* 160:177 */           this.needsInitilization = false;
/* 161:179 */           if ((this.villageObj != null) && (this.lastBuyingPlayer != null))
/* 162:    */           {
/* 163:181 */             this.worldObj.setEntityState(this, (byte)14);
/* 164:182 */             this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
/* 165:    */           }
/* 166:    */         }
/* 167:186 */         addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
/* 168:    */       }
/* 169:    */     }
/* 170:190 */     super.updateAITick();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 174:    */   {
/* 175:198 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 176:199 */     boolean var3 = (var2 != null) && (var2.getItem() == Items.spawn_egg);
/* 177:201 */     if ((!var3) && (isEntityAlive()) && (!isTrading()) && (!isChild()))
/* 178:    */     {
/* 179:203 */       if (!this.worldObj.isClient)
/* 180:    */       {
/* 181:205 */         setCustomer(par1EntityPlayer);
/* 182:206 */         par1EntityPlayer.displayGUIMerchant(this, getCustomNameTag());
/* 183:    */       }
/* 184:209 */       return true;
/* 185:    */     }
/* 186:213 */     return super.interact(par1EntityPlayer);
/* 187:    */   }
/* 188:    */   
/* 189:    */   protected void entityInit()
/* 190:    */   {
/* 191:219 */     super.entityInit();
/* 192:220 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 196:    */   {
/* 197:228 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 198:229 */     par1NBTTagCompound.setInteger("Profession", getProfession());
/* 199:230 */     par1NBTTagCompound.setInteger("Riches", this.wealth);
/* 200:232 */     if (this.buyingList != null) {
/* 201:234 */       par1NBTTagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 206:    */   {
/* 207:243 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 208:244 */     setProfession(par1NBTTagCompound.getInteger("Profession"));
/* 209:245 */     this.wealth = par1NBTTagCompound.getInteger("Riches");
/* 210:247 */     if (par1NBTTagCompound.func_150297_b("Offers", 10))
/* 211:    */     {
/* 212:249 */       NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
/* 213:250 */       this.buyingList = new MerchantRecipeList(var2);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected boolean canDespawn()
/* 218:    */   {
/* 219:259 */     return false;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected String getLivingSound()
/* 223:    */   {
/* 224:267 */     return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
/* 225:    */   }
/* 226:    */   
/* 227:    */   protected String getHurtSound()
/* 228:    */   {
/* 229:275 */     return "mob.villager.hit";
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected String getDeathSound()
/* 233:    */   {
/* 234:283 */     return "mob.villager.death";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setProfession(int par1)
/* 238:    */   {
/* 239:288 */     this.dataWatcher.updateObject(16, Integer.valueOf(par1));
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int getProfession()
/* 243:    */   {
/* 244:293 */     return this.dataWatcher.getWatchableObjectInt(16);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean isMating()
/* 248:    */   {
/* 249:298 */     return this.isMating;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setMating(boolean par1)
/* 253:    */   {
/* 254:303 */     this.isMating = par1;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setPlaying(boolean par1)
/* 258:    */   {
/* 259:308 */     this.isPlaying = par1;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean isPlaying()
/* 263:    */   {
/* 264:313 */     return this.isPlaying;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setRevengeTarget(EntityLivingBase par1EntityLivingBase)
/* 268:    */   {
/* 269:318 */     super.setRevengeTarget(par1EntityLivingBase);
/* 270:320 */     if ((this.villageObj != null) && (par1EntityLivingBase != null))
/* 271:    */     {
/* 272:322 */       this.villageObj.addOrRenewAgressor(par1EntityLivingBase);
/* 273:324 */       if ((par1EntityLivingBase instanceof EntityPlayer))
/* 274:    */       {
/* 275:326 */         byte var2 = -1;
/* 276:328 */         if (isChild()) {
/* 277:330 */           var2 = -3;
/* 278:    */         }
/* 279:333 */         this.villageObj.setReputationForPlayer(par1EntityLivingBase.getCommandSenderName(), var2);
/* 280:335 */         if (isEntityAlive()) {
/* 281:337 */           this.worldObj.setEntityState(this, (byte)13);
/* 282:    */         }
/* 283:    */       }
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void onDeath(DamageSource par1DamageSource)
/* 288:    */   {
/* 289:348 */     if (this.villageObj != null)
/* 290:    */     {
/* 291:350 */       Entity var2 = par1DamageSource.getEntity();
/* 292:352 */       if (var2 != null)
/* 293:    */       {
/* 294:354 */         if ((var2 instanceof EntityPlayer)) {
/* 295:356 */           this.villageObj.setReputationForPlayer(var2.getCommandSenderName(), -2);
/* 296:358 */         } else if ((var2 instanceof IMob)) {
/* 297:360 */           this.villageObj.endMatingSeason();
/* 298:    */         }
/* 299:    */       }
/* 300:363 */       else if (var2 == null)
/* 301:    */       {
/* 302:365 */         EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
/* 303:367 */         if (var3 != null) {
/* 304:369 */           this.villageObj.endMatingSeason();
/* 305:    */         }
/* 306:    */       }
/* 307:    */     }
/* 308:374 */     super.onDeath(par1DamageSource);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setCustomer(EntityPlayer par1EntityPlayer)
/* 312:    */   {
/* 313:379 */     this.buyingPlayer = par1EntityPlayer;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public EntityPlayer getCustomer()
/* 317:    */   {
/* 318:384 */     return this.buyingPlayer;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean isTrading()
/* 322:    */   {
/* 323:389 */     return this.buyingPlayer != null;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void useRecipe(MerchantRecipe par1MerchantRecipe)
/* 327:    */   {
/* 328:394 */     par1MerchantRecipe.incrementToolUses();
/* 329:395 */     this.livingSoundTime = (-getTalkInterval());
/* 330:396 */     playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/* 331:398 */     if (par1MerchantRecipe.hasSameIDsAs((MerchantRecipe)this.buyingList.get(this.buyingList.size() - 1)))
/* 332:    */     {
/* 333:400 */       this.timeUntilReset = 40;
/* 334:401 */       this.needsInitilization = true;
/* 335:403 */       if (this.buyingPlayer != null) {
/* 336:405 */         this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
/* 337:    */       } else {
/* 338:409 */         this.lastBuyingPlayer = null;
/* 339:    */       }
/* 340:    */     }
/* 341:413 */     if (par1MerchantRecipe.getItemToBuy().getItem() == Items.emerald) {
/* 342:415 */       this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void func_110297_a_(ItemStack par1ItemStack)
/* 347:    */   {
/* 348:421 */     if ((!this.worldObj.isClient) && (this.livingSoundTime > -getTalkInterval() + 20))
/* 349:    */     {
/* 350:423 */       this.livingSoundTime = (-getTalkInterval());
/* 351:425 */       if (par1ItemStack != null) {
/* 352:427 */         playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/* 353:    */       } else {
/* 354:431 */         playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
/* 355:    */       }
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
/* 360:    */   {
/* 361:438 */     if (this.buyingList == null) {
/* 362:440 */       addDefaultEquipmentAndRecipies(1);
/* 363:    */     }
/* 364:443 */     return this.buyingList;
/* 365:    */   }
/* 366:    */   
/* 367:    */   private float adjustProbability(float par1)
/* 368:    */   {
/* 369:451 */     float var2 = par1 + this.field_82191_bN;
/* 370:452 */     return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
/* 371:    */   }
/* 372:    */   
/* 373:    */   private void addDefaultEquipmentAndRecipies(int par1)
/* 374:    */   {
/* 375:461 */     if (this.buyingList != null) {
/* 376:463 */       this.field_82191_bN = (MathHelper.sqrt_float(this.buyingList.size()) * 0.2F);
/* 377:    */     } else {
/* 378:467 */       this.field_82191_bN = 0.0F;
/* 379:    */     }
/* 380:471 */     MerchantRecipeList var2 = new MerchantRecipeList();
/* 381:    */     Item[] var4;
/* 382:    */     int var5;
/* 383:    */     int var6;
/* 384:475 */     switch (getProfession())
/* 385:    */     {
/* 386:    */     case 0: 
/* 387:478 */       func_146091_a(var2, Items.wheat, this.rand, adjustProbability(0.9F));
/* 388:479 */       func_146091_a(var2, Item.getItemFromBlock(Blocks.wool), this.rand, adjustProbability(0.5F));
/* 389:480 */       func_146091_a(var2, Items.chicken, this.rand, adjustProbability(0.5F));
/* 390:481 */       func_146091_a(var2, Items.cooked_fished, this.rand, adjustProbability(0.4F));
/* 391:482 */       func_146089_b(var2, Items.bread, this.rand, adjustProbability(0.9F));
/* 392:483 */       func_146089_b(var2, Items.melon, this.rand, adjustProbability(0.3F));
/* 393:484 */       func_146089_b(var2, Items.apple, this.rand, adjustProbability(0.3F));
/* 394:485 */       func_146089_b(var2, Items.cookie, this.rand, adjustProbability(0.3F));
/* 395:486 */       func_146089_b(var2, Items.shears, this.rand, adjustProbability(0.3F));
/* 396:487 */       func_146089_b(var2, Items.flint_and_steel, this.rand, adjustProbability(0.3F));
/* 397:488 */       func_146089_b(var2, Items.cooked_chicken, this.rand, adjustProbability(0.3F));
/* 398:489 */       func_146089_b(var2, Items.arrow, this.rand, adjustProbability(0.5F));
/* 399:491 */       if (this.rand.nextFloat() < adjustProbability(0.5F)) {
/* 400:493 */         var2.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(Items.emerald), new ItemStack(Items.flint, 4 + this.rand.nextInt(2), 0)));
/* 401:    */       }
/* 402:496 */       break;
/* 403:    */     case 1: 
/* 404:499 */       func_146091_a(var2, Items.paper, this.rand, adjustProbability(0.8F));
/* 405:500 */       func_146091_a(var2, Items.book, this.rand, adjustProbability(0.8F));
/* 406:501 */       func_146091_a(var2, Items.written_book, this.rand, adjustProbability(0.3F));
/* 407:502 */       func_146089_b(var2, Item.getItemFromBlock(Blocks.bookshelf), this.rand, adjustProbability(0.8F));
/* 408:503 */       func_146089_b(var2, Item.getItemFromBlock(Blocks.glass), this.rand, adjustProbability(0.2F));
/* 409:504 */       func_146089_b(var2, Items.compass, this.rand, adjustProbability(0.2F));
/* 410:505 */       func_146089_b(var2, Items.clock, this.rand, adjustProbability(0.2F));
/* 411:507 */       if (this.rand.nextFloat() < adjustProbability(0.07F))
/* 412:    */       {
/* 413:509 */         Enchantment var8 = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
/* 414:510 */         int var10 = MathHelper.getRandomIntegerInRange(this.rand, var8.getMinLevel(), var8.getMaxLevel());
/* 415:511 */         ItemStack var11 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var8, var10));
/* 416:512 */         int var6 = 2 + this.rand.nextInt(5 + var10 * 10) + 3 * var10;
/* 417:513 */         var2.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var11));
/* 418:    */       }
/* 419:516 */       break;
/* 420:    */     case 2: 
/* 421:519 */       func_146089_b(var2, Items.ender_eye, this.rand, adjustProbability(0.3F));
/* 422:520 */       func_146089_b(var2, Items.experience_bottle, this.rand, adjustProbability(0.2F));
/* 423:521 */       func_146089_b(var2, Items.redstone, this.rand, adjustProbability(0.4F));
/* 424:522 */       func_146089_b(var2, Item.getItemFromBlock(Blocks.glowstone), this.rand, adjustProbability(0.3F));
/* 425:523 */       Item[] var3 = { Items.iron_sword, Items.diamond_sword, Items.iron_chestplate, Items.diamond_chestplate, Items.iron_axe, Items.diamond_axe, Items.iron_pickaxe, Items.diamond_pickaxe };
/* 426:524 */       var4 = var3;
/* 427:525 */       var5 = var3.length;
/* 428:526 */       var6 = 0;
/* 429:    */     case 3: 
/* 430:    */     case 4: 
/* 431:530 */       while (var6 < var5)
/* 432:    */       {
/* 433:535 */         Item var7 = var4[var6];
/* 434:537 */         if (this.rand.nextFloat() < adjustProbability(0.05F)) {
/* 435:539 */           var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Items.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var7, 1, 0), 5 + this.rand.nextInt(15))));
/* 436:    */         }
/* 437:542 */         var6++;continue;
/* 438:    */         
/* 439:    */ 
/* 440:    */ 
/* 441:546 */         func_146091_a(var2, Items.coal, this.rand, adjustProbability(0.7F));
/* 442:547 */         func_146091_a(var2, Items.iron_ingot, this.rand, adjustProbability(0.5F));
/* 443:548 */         func_146091_a(var2, Items.gold_ingot, this.rand, adjustProbability(0.5F));
/* 444:549 */         func_146091_a(var2, Items.diamond, this.rand, adjustProbability(0.5F));
/* 445:550 */         func_146089_b(var2, Items.iron_sword, this.rand, adjustProbability(0.5F));
/* 446:551 */         func_146089_b(var2, Items.diamond_sword, this.rand, adjustProbability(0.5F));
/* 447:552 */         func_146089_b(var2, Items.iron_axe, this.rand, adjustProbability(0.3F));
/* 448:553 */         func_146089_b(var2, Items.diamond_axe, this.rand, adjustProbability(0.3F));
/* 449:554 */         func_146089_b(var2, Items.iron_pickaxe, this.rand, adjustProbability(0.5F));
/* 450:555 */         func_146089_b(var2, Items.diamond_pickaxe, this.rand, adjustProbability(0.5F));
/* 451:556 */         func_146089_b(var2, Items.iron_shovel, this.rand, adjustProbability(0.2F));
/* 452:557 */         func_146089_b(var2, Items.diamond_shovel, this.rand, adjustProbability(0.2F));
/* 453:558 */         func_146089_b(var2, Items.iron_hoe, this.rand, adjustProbability(0.2F));
/* 454:559 */         func_146089_b(var2, Items.diamond_hoe, this.rand, adjustProbability(0.2F));
/* 455:560 */         func_146089_b(var2, Items.iron_boots, this.rand, adjustProbability(0.2F));
/* 456:561 */         func_146089_b(var2, Items.diamond_boots, this.rand, adjustProbability(0.2F));
/* 457:562 */         func_146089_b(var2, Items.iron_helmet, this.rand, adjustProbability(0.2F));
/* 458:563 */         func_146089_b(var2, Items.diamond_helmet, this.rand, adjustProbability(0.2F));
/* 459:564 */         func_146089_b(var2, Items.iron_chestplate, this.rand, adjustProbability(0.2F));
/* 460:565 */         func_146089_b(var2, Items.diamond_chestplate, this.rand, adjustProbability(0.2F));
/* 461:566 */         func_146089_b(var2, Items.iron_leggings, this.rand, adjustProbability(0.2F));
/* 462:567 */         func_146089_b(var2, Items.diamond_leggings, this.rand, adjustProbability(0.2F));
/* 463:568 */         func_146089_b(var2, Items.chainmail_boots, this.rand, adjustProbability(0.1F));
/* 464:569 */         func_146089_b(var2, Items.chainmail_helmet, this.rand, adjustProbability(0.1F));
/* 465:570 */         func_146089_b(var2, Items.chainmail_chestplate, this.rand, adjustProbability(0.1F));
/* 466:571 */         func_146089_b(var2, Items.chainmail_leggings, this.rand, adjustProbability(0.1F));
/* 467:572 */         break;
/* 468:    */         
/* 469:    */ 
/* 470:575 */         func_146091_a(var2, Items.coal, this.rand, adjustProbability(0.7F));
/* 471:576 */         func_146091_a(var2, Items.porkchop, this.rand, adjustProbability(0.5F));
/* 472:577 */         func_146091_a(var2, Items.beef, this.rand, adjustProbability(0.5F));
/* 473:578 */         func_146089_b(var2, Items.saddle, this.rand, adjustProbability(0.1F));
/* 474:579 */         func_146089_b(var2, Items.leather_chestplate, this.rand, adjustProbability(0.3F));
/* 475:580 */         func_146089_b(var2, Items.leather_boots, this.rand, adjustProbability(0.3F));
/* 476:581 */         func_146089_b(var2, Items.leather_helmet, this.rand, adjustProbability(0.3F));
/* 477:582 */         func_146089_b(var2, Items.leather_leggings, this.rand, adjustProbability(0.3F));
/* 478:583 */         func_146089_b(var2, Items.cooked_porkchop, this.rand, adjustProbability(0.3F));
/* 479:584 */         func_146089_b(var2, Items.cooked_beef, this.rand, adjustProbability(0.3F));
/* 480:    */       }
/* 481:    */     }
/* 482:587 */     if (var2.isEmpty()) {
/* 483:589 */       func_146091_a(var2, Items.gold_ingot, this.rand, 1.0F);
/* 484:    */     }
/* 485:592 */     Collections.shuffle(var2);
/* 486:594 */     if (this.buyingList == null) {
/* 487:596 */       this.buyingList = new MerchantRecipeList();
/* 488:    */     }
/* 489:599 */     for (int var9 = 0; (var9 < par1) && (var9 < var2.size()); var9++) {
/* 490:601 */       this.buyingList.addToListWithCheck((MerchantRecipe)var2.get(var9));
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   private static void func_146091_a(MerchantRecipeList p_146091_0_, Item p_146091_1_, Random p_146091_2_, float p_146091_3_)
/* 495:    */   {
/* 496:609 */     if (p_146091_2_.nextFloat() < p_146091_3_) {
/* 497:611 */       p_146091_0_.add(new MerchantRecipe(func_146088_a(p_146091_1_, p_146091_2_), Items.emerald));
/* 498:    */     }
/* 499:    */   }
/* 500:    */   
/* 501:    */   private static ItemStack func_146088_a(Item p_146088_0_, Random p_146088_1_)
/* 502:    */   {
/* 503:617 */     return new ItemStack(p_146088_0_, func_146092_b(p_146088_0_, p_146088_1_), 0);
/* 504:    */   }
/* 505:    */   
/* 506:    */   private static int func_146092_b(Item p_146092_0_, Random p_146092_1_)
/* 507:    */   {
/* 508:622 */     Tuple var2 = (Tuple)villagersSellingList.get(p_146092_0_);
/* 509:623 */     return ((Integer)var2.getFirst()).intValue() >= ((Integer)var2.getSecond()).intValue() ? ((Integer)var2.getFirst()).intValue() : var2 == null ? 1 : ((Integer)var2.getFirst()).intValue() + p_146092_1_.nextInt(((Integer)var2.getSecond()).intValue() - ((Integer)var2.getFirst()).intValue());
/* 510:    */   }
/* 511:    */   
/* 512:    */   private static void func_146089_b(MerchantRecipeList p_146089_0_, Item p_146089_1_, Random p_146089_2_, float p_146089_3_)
/* 513:    */   {
/* 514:628 */     if (p_146089_2_.nextFloat() < p_146089_3_)
/* 515:    */     {
/* 516:630 */       int var4 = func_146090_c(p_146089_1_, p_146089_2_);
/* 517:    */       ItemStack var6;
/* 518:    */       ItemStack var5;
/* 519:    */       ItemStack var6;
/* 520:634 */       if (var4 < 0)
/* 521:    */       {
/* 522:636 */         ItemStack var5 = new ItemStack(Items.emerald, 1, 0);
/* 523:637 */         var6 = new ItemStack(p_146089_1_, -var4, 0);
/* 524:    */       }
/* 525:    */       else
/* 526:    */       {
/* 527:641 */         var5 = new ItemStack(Items.emerald, var4, 0);
/* 528:642 */         var6 = new ItemStack(p_146089_1_, 1, 0);
/* 529:    */       }
/* 530:645 */       p_146089_0_.add(new MerchantRecipe(var5, var6));
/* 531:    */     }
/* 532:    */   }
/* 533:    */   
/* 534:    */   private static int func_146090_c(Item p_146090_0_, Random p_146090_1_)
/* 535:    */   {
/* 536:651 */     Tuple var2 = (Tuple)blacksmithSellingList.get(p_146090_0_);
/* 537:652 */     return ((Integer)var2.getFirst()).intValue() >= ((Integer)var2.getSecond()).intValue() ? ((Integer)var2.getFirst()).intValue() : var2 == null ? 1 : ((Integer)var2.getFirst()).intValue() + p_146090_1_.nextInt(((Integer)var2.getSecond()).intValue() - ((Integer)var2.getFirst()).intValue());
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void handleHealthUpdate(byte par1)
/* 541:    */   {
/* 542:657 */     if (par1 == 12) {
/* 543:659 */       generateRandomParticles("heart");
/* 544:661 */     } else if (par1 == 13) {
/* 545:663 */       generateRandomParticles("angryVillager");
/* 546:665 */     } else if (par1 == 14) {
/* 547:667 */       generateRandomParticles("happyVillager");
/* 548:    */     } else {
/* 549:671 */       super.handleHealthUpdate(par1);
/* 550:    */     }
/* 551:    */   }
/* 552:    */   
/* 553:    */   private void generateRandomParticles(String par1Str)
/* 554:    */   {
/* 555:680 */     for (int var2 = 0; var2 < 5; var2++)
/* 556:    */     {
/* 557:682 */       double var3 = this.rand.nextGaussian() * 0.02D;
/* 558:683 */       double var5 = this.rand.nextGaussian() * 0.02D;
/* 559:684 */       double var7 = this.rand.nextGaussian() * 0.02D;
/* 560:685 */       this.worldObj.spawnParticle(par1Str, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 1.0D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var3, var5, var7);
/* 561:    */     }
/* 562:    */   }
/* 563:    */   
/* 564:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 565:    */   {
/* 566:691 */     par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
/* 567:692 */     setProfession(this.worldObj.rand.nextInt(5));
/* 568:693 */     return par1EntityLivingData;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void setLookingForHome()
/* 572:    */   {
/* 573:698 */     this.isLookingForHome = true;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public EntityVillager createChild(EntityAgeable par1EntityAgeable)
/* 577:    */   {
/* 578:703 */     EntityVillager var2 = new EntityVillager(this.worldObj);
/* 579:704 */     var2.onSpawnWithEgg(null);
/* 580:705 */     return var2;
/* 581:    */   }
/* 582:    */   
/* 583:    */   public boolean allowLeashing()
/* 584:    */   {
/* 585:710 */     return false;
/* 586:    */   }
/* 587:    */   
/* 588:    */   static
/* 589:    */   {
/* 590:715 */     villagersSellingList.put(Items.coal, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
/* 591:716 */     villagersSellingList.put(Items.iron_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 592:717 */     villagersSellingList.put(Items.gold_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 593:718 */     villagersSellingList.put(Items.diamond, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 594:719 */     villagersSellingList.put(Items.paper, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
/* 595:720 */     villagersSellingList.put(Items.book, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
/* 596:721 */     villagersSellingList.put(Items.written_book, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
/* 597:722 */     villagersSellingList.put(Items.ender_pearl, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 598:723 */     villagersSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
/* 599:724 */     villagersSellingList.put(Items.porkchop, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 600:725 */     villagersSellingList.put(Items.beef, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 601:726 */     villagersSellingList.put(Items.chicken, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 602:727 */     villagersSellingList.put(Items.cooked_fished, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
/* 603:728 */     villagersSellingList.put(Items.wheat_seeds, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
/* 604:729 */     villagersSellingList.put(Items.melon_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
/* 605:730 */     villagersSellingList.put(Items.pumpkin_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
/* 606:731 */     villagersSellingList.put(Items.wheat, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
/* 607:732 */     villagersSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
/* 608:733 */     villagersSellingList.put(Items.rotten_flesh, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
/* 609:734 */     blacksmithSellingList.put(Items.flint_and_steel, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 610:735 */     blacksmithSellingList.put(Items.shears, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 611:736 */     blacksmithSellingList.put(Items.iron_sword, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
/* 612:737 */     blacksmithSellingList.put(Items.diamond_sword, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
/* 613:738 */     blacksmithSellingList.put(Items.iron_axe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
/* 614:739 */     blacksmithSellingList.put(Items.diamond_axe, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
/* 615:740 */     blacksmithSellingList.put(Items.iron_pickaxe, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
/* 616:741 */     blacksmithSellingList.put(Items.diamond_pickaxe, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 617:742 */     blacksmithSellingList.put(Items.iron_shovel, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 618:743 */     blacksmithSellingList.put(Items.diamond_shovel, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 619:744 */     blacksmithSellingList.put(Items.iron_hoe, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 620:745 */     blacksmithSellingList.put(Items.diamond_hoe, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 621:746 */     blacksmithSellingList.put(Items.iron_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 622:747 */     blacksmithSellingList.put(Items.diamond_boots, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 623:748 */     blacksmithSellingList.put(Items.iron_helmet, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 624:749 */     blacksmithSellingList.put(Items.diamond_helmet, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 625:750 */     blacksmithSellingList.put(Items.iron_chestplate, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
/* 626:751 */     blacksmithSellingList.put(Items.diamond_chestplate, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
/* 627:752 */     blacksmithSellingList.put(Items.iron_leggings, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 628:753 */     blacksmithSellingList.put(Items.diamond_leggings, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
/* 629:754 */     blacksmithSellingList.put(Items.chainmail_boots, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
/* 630:755 */     blacksmithSellingList.put(Items.chainmail_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
/* 631:756 */     blacksmithSellingList.put(Items.chainmail_chestplate, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
/* 632:757 */     blacksmithSellingList.put(Items.chainmail_leggings, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
/* 633:758 */     blacksmithSellingList.put(Items.bread, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
/* 634:759 */     blacksmithSellingList.put(Items.melon, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
/* 635:760 */     blacksmithSellingList.put(Items.apple, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
/* 636:761 */     blacksmithSellingList.put(Items.cookie, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
/* 637:762 */     blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
/* 638:763 */     blacksmithSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 639:764 */     blacksmithSellingList.put(Items.leather_chestplate, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
/* 640:765 */     blacksmithSellingList.put(Items.leather_boots, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 641:766 */     blacksmithSellingList.put(Items.leather_helmet, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 642:767 */     blacksmithSellingList.put(Items.leather_leggings, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 643:768 */     blacksmithSellingList.put(Items.saddle, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
/* 644:769 */     blacksmithSellingList.put(Items.experience_bottle, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
/* 645:770 */     blacksmithSellingList.put(Items.redstone, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
/* 646:771 */     blacksmithSellingList.put(Items.compass, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 647:772 */     blacksmithSellingList.put(Items.clock, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 648:773 */     blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
/* 649:774 */     blacksmithSellingList.put(Items.cooked_porkchop, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
/* 650:775 */     blacksmithSellingList.put(Items.cooked_beef, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
/* 651:776 */     blacksmithSellingList.put(Items.cooked_chicken, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
/* 652:777 */     blacksmithSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
/* 653:778 */     blacksmithSellingList.put(Items.arrow, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void setRecipes(MerchantRecipeList par1MerchantRecipeList) {}
/* 657:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityVillager
 * JD-Core Version:    0.7.0.1
 */