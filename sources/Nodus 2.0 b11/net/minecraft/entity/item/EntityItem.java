/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.stats.AchievementList;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.DamageSource;
/*  20:    */ import net.minecraft.util.MathHelper;
/*  21:    */ import net.minecraft.util.StatCollector;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ import org.apache.logging.log4j.LogManager;
/*  24:    */ import org.apache.logging.log4j.Logger;
/*  25:    */ 
/*  26:    */ public class EntityItem
/*  27:    */   extends Entity
/*  28:    */ {
/*  29: 22 */   private static final Logger logger = ;
/*  30:    */   public int age;
/*  31:    */   public int delayBeforeCanPickup;
/*  32:    */   private int health;
/*  33:    */   private String field_145801_f;
/*  34:    */   private String field_145802_g;
/*  35:    */   public float hoverStart;
/*  36:    */   private static final String __OBFID = "CL_00001669";
/*  37:    */   
/*  38:    */   public EntityItem(World par1World, double par2, double par4, double par6)
/*  39:    */   {
/*  40: 41 */     super(par1World);
/*  41: 42 */     this.health = 5;
/*  42: 43 */     this.hoverStart = ((float)(Math.random() * 3.141592653589793D * 2.0D));
/*  43: 44 */     setSize(0.25F, 0.25F);
/*  44: 45 */     this.yOffset = (this.height / 2.0F);
/*  45: 46 */     setPosition(par2, par4, par6);
/*  46: 47 */     this.rotationYaw = ((float)(Math.random() * 360.0D));
/*  47: 48 */     this.motionX = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/*  48: 49 */     this.motionY = 0.2000000029802322D;
/*  49: 50 */     this.motionZ = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*  53:    */   {
/*  54: 55 */     this(par1World, par2, par4, par6);
/*  55: 56 */     setEntityItemStack(par8ItemStack);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected boolean canTriggerWalking()
/*  59:    */   {
/*  60: 65 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public EntityItem(World par1World)
/*  64:    */   {
/*  65: 70 */     super(par1World);
/*  66: 71 */     this.health = 5;
/*  67: 72 */     this.hoverStart = ((float)(Math.random() * 3.141592653589793D * 2.0D));
/*  68: 73 */     setSize(0.25F, 0.25F);
/*  69: 74 */     this.yOffset = (this.height / 2.0F);
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void entityInit()
/*  73:    */   {
/*  74: 79 */     getDataWatcher().addObjectByDataType(10, 5);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void onUpdate()
/*  78:    */   {
/*  79: 87 */     if (getEntityItem() == null)
/*  80:    */     {
/*  81: 89 */       setDead();
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85: 93 */       super.onUpdate();
/*  86: 95 */       if (this.delayBeforeCanPickup > 0) {
/*  87: 97 */         this.delayBeforeCanPickup -= 1;
/*  88:    */       }
/*  89:100 */       this.prevPosX = this.posX;
/*  90:101 */       this.prevPosY = this.posY;
/*  91:102 */       this.prevPosZ = this.posZ;
/*  92:103 */       this.motionY -= 0.03999999910593033D;
/*  93:104 */       this.noClip = func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
/*  94:105 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  95:106 */       boolean var1 = ((int)this.prevPosX != (int)this.posX) || ((int)this.prevPosY != (int)this.posY) || ((int)this.prevPosZ != (int)this.posZ);
/*  96:108 */       if ((var1) || (this.ticksExisted % 25 == 0))
/*  97:    */       {
/*  98:110 */         if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava)
/*  99:    */         {
/* 100:112 */           this.motionY = 0.2000000029802322D;
/* 101:113 */           this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 102:114 */           this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 103:115 */           playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/* 104:    */         }
/* 105:118 */         if (!this.worldObj.isClient) {
/* 106:120 */           searchForOtherItemsNearby();
/* 107:    */         }
/* 108:    */       }
/* 109:124 */       float var2 = 0.98F;
/* 110:126 */       if (this.onGround) {
/* 111:128 */         var2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
/* 112:    */       }
/* 113:131 */       this.motionX *= var2;
/* 114:132 */       this.motionY *= 0.9800000190734863D;
/* 115:133 */       this.motionZ *= var2;
/* 116:135 */       if (this.onGround) {
/* 117:137 */         this.motionY *= -0.5D;
/* 118:    */       }
/* 119:140 */       this.age += 1;
/* 120:142 */       if ((!this.worldObj.isClient) && (this.age >= 6000)) {
/* 121:144 */         setDead();
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void searchForOtherItemsNearby()
/* 127:    */   {
/* 128:154 */     Iterator var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();
/* 129:156 */     while (var1.hasNext())
/* 130:    */     {
/* 131:158 */       EntityItem var2 = (EntityItem)var1.next();
/* 132:159 */       combineItems(var2);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean combineItems(EntityItem par1EntityItem)
/* 137:    */   {
/* 138:169 */     if (par1EntityItem == this) {
/* 139:171 */       return false;
/* 140:    */     }
/* 141:173 */     if ((par1EntityItem.isEntityAlive()) && (isEntityAlive()))
/* 142:    */     {
/* 143:175 */       ItemStack var2 = getEntityItem();
/* 144:176 */       ItemStack var3 = par1EntityItem.getEntityItem();
/* 145:178 */       if (var3.getItem() != var2.getItem()) {
/* 146:180 */         return false;
/* 147:    */       }
/* 148:182 */       if ((var3.hasTagCompound() ^ var2.hasTagCompound())) {
/* 149:184 */         return false;
/* 150:    */       }
/* 151:186 */       if ((var3.hasTagCompound()) && (!var3.getTagCompound().equals(var2.getTagCompound()))) {
/* 152:188 */         return false;
/* 153:    */       }
/* 154:190 */       if (var3.getItem() == null) {
/* 155:192 */         return false;
/* 156:    */       }
/* 157:194 */       if ((var3.getItem().getHasSubtypes()) && (var3.getItemDamage() != var2.getItemDamage())) {
/* 158:196 */         return false;
/* 159:    */       }
/* 160:198 */       if (var3.stackSize < var2.stackSize) {
/* 161:200 */         return par1EntityItem.combineItems(this);
/* 162:    */       }
/* 163:202 */       if (var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
/* 164:204 */         return false;
/* 165:    */       }
/* 166:208 */       var3.stackSize += var2.stackSize;
/* 167:209 */       par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
/* 168:210 */       par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
/* 169:211 */       par1EntityItem.setEntityItemStack(var3);
/* 170:212 */       setDead();
/* 171:213 */       return true;
/* 172:    */     }
/* 173:218 */     return false;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setAgeToCreativeDespawnTime()
/* 177:    */   {
/* 178:228 */     this.age = 4800;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean handleWaterMovement()
/* 182:    */   {
/* 183:236 */     return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected void dealFireDamage(int par1)
/* 187:    */   {
/* 188:245 */     attackEntityFrom(DamageSource.inFire, par1);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 192:    */   {
/* 193:253 */     if (isEntityInvulnerable()) {
/* 194:255 */       return false;
/* 195:    */     }
/* 196:257 */     if ((getEntityItem() != null) && (getEntityItem().getItem() == Items.nether_star) && (par1DamageSource.isExplosion())) {
/* 197:259 */       return false;
/* 198:    */     }
/* 199:263 */     setBeenAttacked();
/* 200:264 */     this.health = ((int)(this.health - par2));
/* 201:266 */     if (this.health <= 0) {
/* 202:268 */       setDead();
/* 203:    */     }
/* 204:271 */     return false;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 208:    */   {
/* 209:280 */     par1NBTTagCompound.setShort("Health", (short)(byte)this.health);
/* 210:281 */     par1NBTTagCompound.setShort("Age", (short)this.age);
/* 211:283 */     if (func_145800_j() != null) {
/* 212:285 */       par1NBTTagCompound.setString("Thrower", this.field_145801_f);
/* 213:    */     }
/* 214:288 */     if (func_145798_i() != null) {
/* 215:290 */       par1NBTTagCompound.setString("Owner", this.field_145802_g);
/* 216:    */     }
/* 217:293 */     if (getEntityItem() != null) {
/* 218:295 */       par1NBTTagCompound.setTag("Item", getEntityItem().writeToNBT(new NBTTagCompound()));
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 223:    */   {
/* 224:304 */     this.health = (par1NBTTagCompound.getShort("Health") & 0xFF);
/* 225:305 */     this.age = par1NBTTagCompound.getShort("Age");
/* 226:307 */     if (par1NBTTagCompound.hasKey("Owner")) {
/* 227:309 */       this.field_145802_g = par1NBTTagCompound.getString("Owner");
/* 228:    */     }
/* 229:312 */     if (par1NBTTagCompound.hasKey("Thrower")) {
/* 230:314 */       this.field_145801_f = par1NBTTagCompound.getString("Thrower");
/* 231:    */     }
/* 232:317 */     NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
/* 233:318 */     setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
/* 234:320 */     if (getEntityItem() == null) {
/* 235:322 */       setDead();
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
/* 240:    */   {
/* 241:331 */     if (!this.worldObj.isClient)
/* 242:    */     {
/* 243:333 */       ItemStack var2 = getEntityItem();
/* 244:334 */       int var3 = var2.stackSize;
/* 245:336 */       if ((this.delayBeforeCanPickup == 0) && ((this.field_145802_g == null) || (6000 - this.age <= 200) || (this.field_145802_g.equals(par1EntityPlayer.getCommandSenderName()))) && (par1EntityPlayer.inventory.addItemStackToInventory(var2)))
/* 246:    */       {
/* 247:338 */         if (var2.getItem() == Item.getItemFromBlock(Blocks.log)) {
/* 248:340 */           par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
/* 249:    */         }
/* 250:343 */         if (var2.getItem() == Item.getItemFromBlock(Blocks.log2)) {
/* 251:345 */           par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
/* 252:    */         }
/* 253:348 */         if (var2.getItem() == Items.leather) {
/* 254:350 */           par1EntityPlayer.triggerAchievement(AchievementList.killCow);
/* 255:    */         }
/* 256:353 */         if (var2.getItem() == Items.diamond) {
/* 257:355 */           par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
/* 258:    */         }
/* 259:358 */         if (var2.getItem() == Items.blaze_rod) {
/* 260:360 */           par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
/* 261:    */         }
/* 262:363 */         if ((var2.getItem() == Items.diamond) && (func_145800_j() != null))
/* 263:    */         {
/* 264:365 */           EntityPlayer var4 = this.worldObj.getPlayerEntityByName(func_145800_j());
/* 265:367 */           if ((var4 != null) && (var4 != par1EntityPlayer)) {
/* 266:369 */             var4.triggerAchievement(AchievementList.field_150966_x);
/* 267:    */           }
/* 268:    */         }
/* 269:373 */         this.worldObj.playSoundAtEntity(par1EntityPlayer, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 270:374 */         par1EntityPlayer.onItemPickup(this, var3);
/* 271:376 */         if (var2.stackSize <= 0) {
/* 272:378 */           setDead();
/* 273:    */         }
/* 274:    */       }
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String getCommandSenderName()
/* 279:    */   {
/* 280:389 */     return StatCollector.translateToLocal("item." + getEntityItem().getUnlocalizedName());
/* 281:    */   }
/* 282:    */   
/* 283:    */   public boolean canAttackWithItem()
/* 284:    */   {
/* 285:397 */     return false;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void travelToDimension(int par1)
/* 289:    */   {
/* 290:405 */     super.travelToDimension(par1);
/* 291:407 */     if (!this.worldObj.isClient) {
/* 292:409 */       searchForOtherItemsNearby();
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ItemStack getEntityItem()
/* 297:    */   {
/* 298:419 */     ItemStack var1 = getDataWatcher().getWatchableObjectItemStack(10);
/* 299:421 */     if (var1 == null)
/* 300:    */     {
/* 301:423 */       if (this.worldObj != null) {
/* 302:425 */         logger.error("Item entity " + getEntityId() + " has no item?!");
/* 303:    */       }
/* 304:428 */       return new ItemStack(Blocks.stone);
/* 305:    */     }
/* 306:432 */     return var1;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setEntityItemStack(ItemStack par1ItemStack)
/* 310:    */   {
/* 311:441 */     getDataWatcher().updateObject(10, par1ItemStack);
/* 312:442 */     getDataWatcher().setObjectWatched(10);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String func_145798_i()
/* 316:    */   {
/* 317:447 */     return this.field_145802_g;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void func_145797_a(String p_145797_1_)
/* 321:    */   {
/* 322:452 */     this.field_145802_g = p_145797_1_;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public String func_145800_j()
/* 326:    */   {
/* 327:457 */     return this.field_145801_f;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void func_145799_b(String p_145799_1_)
/* 331:    */   {
/* 332:462 */     this.field_145801_f = p_145799_1_;
/* 333:    */   }
/* 334:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityItem
 * JD-Core Version:    0.7.0.1
 */