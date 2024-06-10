/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.BlockChest;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.inventory.ContainerChest;
/*  10:    */ import net.minecraft.inventory.IInventory;
/*  11:    */ import net.minecraft.inventory.InventoryLargeChest;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.nbt.NBTTagList;
/*  15:    */ import net.minecraft.util.AABBPool;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class TileEntityChest
/*  20:    */   extends TileEntity
/*  21:    */   implements IInventory
/*  22:    */ {
/*  23: 18 */   private ItemStack[] field_145985_p = new ItemStack[36];
/*  24:    */   public boolean field_145984_a;
/*  25:    */   public TileEntityChest field_145992_i;
/*  26:    */   public TileEntityChest field_145990_j;
/*  27:    */   public TileEntityChest field_145991_k;
/*  28:    */   public TileEntityChest field_145988_l;
/*  29:    */   public float field_145989_m;
/*  30:    */   public float field_145986_n;
/*  31:    */   public int field_145987_o;
/*  32:    */   public int field_145983_q;
/*  33:    */   public int field_145982_r;
/*  34:    */   private String field_145981_s;
/*  35:    */   private static final String __OBFID = "CL_00000346";
/*  36:    */   
/*  37:    */   public TileEntityChest()
/*  38:    */   {
/*  39: 34 */     this.field_145982_r = -1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public TileEntityChest(int par1)
/*  43:    */   {
/*  44: 39 */     this.field_145982_r = par1;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getSizeInventory()
/*  48:    */   {
/*  49: 47 */     return 27;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ItemStack getStackInSlot(int par1)
/*  53:    */   {
/*  54: 55 */     return this.field_145985_p[par1];
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ItemStack decrStackSize(int par1, int par2)
/*  58:    */   {
/*  59: 64 */     if (this.field_145985_p[par1] != null)
/*  60:    */     {
/*  61: 68 */       if (this.field_145985_p[par1].stackSize <= par2)
/*  62:    */       {
/*  63: 70 */         ItemStack var3 = this.field_145985_p[par1];
/*  64: 71 */         this.field_145985_p[par1] = null;
/*  65: 72 */         onInventoryChanged();
/*  66: 73 */         return var3;
/*  67:    */       }
/*  68: 77 */       ItemStack var3 = this.field_145985_p[par1].splitStack(par2);
/*  69: 79 */       if (this.field_145985_p[par1].stackSize == 0) {
/*  70: 81 */         this.field_145985_p[par1] = null;
/*  71:    */       }
/*  72: 84 */       onInventoryChanged();
/*  73: 85 */       return var3;
/*  74:    */     }
/*  75: 90 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  79:    */   {
/*  80:100 */     if (this.field_145985_p[par1] != null)
/*  81:    */     {
/*  82:102 */       ItemStack var2 = this.field_145985_p[par1];
/*  83:103 */       this.field_145985_p[par1] = null;
/*  84:104 */       return var2;
/*  85:    */     }
/*  86:108 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  90:    */   {
/*  91:117 */     this.field_145985_p[par1] = par2ItemStack;
/*  92:119 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  93:121 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*  94:    */     }
/*  95:124 */     onInventoryChanged();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getInventoryName()
/*  99:    */   {
/* 100:132 */     return isInventoryNameLocalized() ? this.field_145981_s : "container.chest";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isInventoryNameLocalized()
/* 104:    */   {
/* 105:140 */     return (this.field_145981_s != null) && (this.field_145981_s.length() > 0);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void func_145976_a(String p_145976_1_)
/* 109:    */   {
/* 110:145 */     this.field_145981_s = p_145976_1_;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 114:    */   {
/* 115:150 */     super.readFromNBT(p_145839_1_);
/* 116:151 */     NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
/* 117:152 */     this.field_145985_p = new ItemStack[getSizeInventory()];
/* 118:154 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/* 119:156 */       this.field_145981_s = p_145839_1_.getString("CustomName");
/* 120:    */     }
/* 121:159 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 122:    */     {
/* 123:161 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 124:162 */       int var5 = var4.getByte("Slot") & 0xFF;
/* 125:164 */       if ((var5 >= 0) && (var5 < this.field_145985_p.length)) {
/* 126:166 */         this.field_145985_p[var5] = ItemStack.loadItemStackFromNBT(var4);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 132:    */   {
/* 133:173 */     super.writeToNBT(p_145841_1_);
/* 134:174 */     NBTTagList var2 = new NBTTagList();
/* 135:176 */     for (int var3 = 0; var3 < this.field_145985_p.length; var3++) {
/* 136:178 */       if (this.field_145985_p[var3] != null)
/* 137:    */       {
/* 138:180 */         NBTTagCompound var4 = new NBTTagCompound();
/* 139:181 */         var4.setByte("Slot", (byte)var3);
/* 140:182 */         this.field_145985_p[var3].writeToNBT(var4);
/* 141:183 */         var2.appendTag(var4);
/* 142:    */       }
/* 143:    */     }
/* 144:187 */     p_145841_1_.setTag("Items", var2);
/* 145:189 */     if (isInventoryNameLocalized()) {
/* 146:191 */       p_145841_1_.setString("CustomName", this.field_145981_s);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getInventoryStackLimit()
/* 151:    */   {
/* 152:200 */     return 64;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 156:    */   {
/* 157:208 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void updateContainingBlockInfo()
/* 161:    */   {
/* 162:213 */     super.updateContainingBlockInfo();
/* 163:214 */     this.field_145984_a = false;
/* 164:    */   }
/* 165:    */   
/* 166:    */   private void func_145978_a(TileEntityChest p_145978_1_, int p_145978_2_)
/* 167:    */   {
/* 168:219 */     if (p_145978_1_.isInvalid()) {
/* 169:221 */       this.field_145984_a = false;
/* 170:223 */     } else if (this.field_145984_a) {
/* 171:225 */       switch (p_145978_2_)
/* 172:    */       {
/* 173:    */       case 0: 
/* 174:228 */         if (this.field_145988_l != p_145978_1_) {
/* 175:230 */           this.field_145984_a = false;
/* 176:    */         }
/* 177:233 */         break;
/* 178:    */       case 1: 
/* 179:236 */         if (this.field_145991_k != p_145978_1_) {
/* 180:238 */           this.field_145984_a = false;
/* 181:    */         }
/* 182:241 */         break;
/* 183:    */       case 2: 
/* 184:244 */         if (this.field_145992_i != p_145978_1_) {
/* 185:246 */           this.field_145984_a = false;
/* 186:    */         }
/* 187:249 */         break;
/* 188:    */       case 3: 
/* 189:252 */         if (this.field_145990_j != p_145978_1_) {
/* 190:254 */           this.field_145984_a = false;
/* 191:    */         }
/* 192:    */         break;
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void func_145979_i()
/* 198:    */   {
/* 199:262 */     if (!this.field_145984_a)
/* 200:    */     {
/* 201:264 */       this.field_145984_a = true;
/* 202:265 */       this.field_145992_i = null;
/* 203:266 */       this.field_145990_j = null;
/* 204:267 */       this.field_145991_k = null;
/* 205:268 */       this.field_145988_l = null;
/* 206:270 */       if (func_145977_a(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)) {
/* 207:272 */         this.field_145991_k = ((TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e));
/* 208:    */       }
/* 209:275 */       if (func_145977_a(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)) {
/* 210:277 */         this.field_145990_j = ((TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e));
/* 211:    */       }
/* 212:280 */       if (func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)) {
/* 213:282 */         this.field_145992_i = ((TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1));
/* 214:    */       }
/* 215:285 */       if (func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)) {
/* 216:287 */         this.field_145988_l = ((TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1));
/* 217:    */       }
/* 218:290 */       if (this.field_145992_i != null) {
/* 219:292 */         this.field_145992_i.func_145978_a(this, 0);
/* 220:    */       }
/* 221:295 */       if (this.field_145988_l != null) {
/* 222:297 */         this.field_145988_l.func_145978_a(this, 2);
/* 223:    */       }
/* 224:300 */       if (this.field_145990_j != null) {
/* 225:302 */         this.field_145990_j.func_145978_a(this, 1);
/* 226:    */       }
/* 227:305 */       if (this.field_145991_k != null) {
/* 228:307 */         this.field_145991_k.func_145978_a(this, 3);
/* 229:    */       }
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_)
/* 234:    */   {
/* 235:314 */     Block var4 = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
/* 236:315 */     return ((var4 instanceof BlockChest)) && (((BlockChest)var4).field_149956_a == func_145980_j());
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void updateEntity()
/* 240:    */   {
/* 241:320 */     super.updateEntity();
/* 242:321 */     func_145979_i();
/* 243:322 */     this.field_145983_q += 1;
/* 244:325 */     if ((!this.worldObj.isClient) && (this.field_145987_o != 0) && ((this.field_145983_q + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0))
/* 245:    */     {
/* 246:327 */       this.field_145987_o = 0;
/* 247:328 */       float var1 = 5.0F;
/* 248:329 */       List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c - var1, this.field_145848_d - var1, this.field_145849_e - var1, this.field_145851_c + 1 + var1, this.field_145848_d + 1 + var1, this.field_145849_e + 1 + var1));
/* 249:330 */       Iterator var3 = var2.iterator();
/* 250:332 */       while (var3.hasNext())
/* 251:    */       {
/* 252:334 */         EntityPlayer var4 = (EntityPlayer)var3.next();
/* 253:336 */         if ((var4.openContainer instanceof ContainerChest))
/* 254:    */         {
/* 255:338 */           IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();
/* 256:340 */           if ((var5 == this) || (((var5 instanceof InventoryLargeChest)) && (((InventoryLargeChest)var5).isPartOfLargeChest(this)))) {
/* 257:342 */             this.field_145987_o += 1;
/* 258:    */           }
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:348 */     this.field_145986_n = this.field_145989_m;
/* 263:349 */     float var1 = 0.1F;
/* 264:352 */     if ((this.field_145987_o > 0) && (this.field_145989_m == 0.0F) && (this.field_145992_i == null) && (this.field_145991_k == null))
/* 265:    */     {
/* 266:354 */       double var8 = this.field_145851_c + 0.5D;
/* 267:355 */       double var11 = this.field_145849_e + 0.5D;
/* 268:357 */       if (this.field_145988_l != null) {
/* 269:359 */         var11 += 0.5D;
/* 270:    */       }
/* 271:362 */       if (this.field_145990_j != null) {
/* 272:364 */         var8 += 0.5D;
/* 273:    */       }
/* 274:367 */       this.worldObj.playSoundEffect(var8, this.field_145848_d + 0.5D, var11, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/* 275:    */     }
/* 276:370 */     if (((this.field_145987_o == 0) && (this.field_145989_m > 0.0F)) || ((this.field_145987_o > 0) && (this.field_145989_m < 1.0F)))
/* 277:    */     {
/* 278:372 */       float var9 = this.field_145989_m;
/* 279:374 */       if (this.field_145987_o > 0) {
/* 280:376 */         this.field_145989_m += var1;
/* 281:    */       } else {
/* 282:380 */         this.field_145989_m -= var1;
/* 283:    */       }
/* 284:383 */       if (this.field_145989_m > 1.0F) {
/* 285:385 */         this.field_145989_m = 1.0F;
/* 286:    */       }
/* 287:388 */       float var10 = 0.5F;
/* 288:390 */       if ((this.field_145989_m < var10) && (var9 >= var10) && (this.field_145992_i == null) && (this.field_145991_k == null))
/* 289:    */       {
/* 290:392 */         double var11 = this.field_145851_c + 0.5D;
/* 291:393 */         double var6 = this.field_145849_e + 0.5D;
/* 292:395 */         if (this.field_145988_l != null) {
/* 293:397 */           var6 += 0.5D;
/* 294:    */         }
/* 295:400 */         if (this.field_145990_j != null) {
/* 296:402 */           var11 += 0.5D;
/* 297:    */         }
/* 298:405 */         this.worldObj.playSoundEffect(var11, this.field_145848_d + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/* 299:    */       }
/* 300:408 */       if (this.field_145989_m < 0.0F) {
/* 301:410 */         this.field_145989_m = 0.0F;
/* 302:    */       }
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
/* 307:    */   {
/* 308:417 */     if (p_145842_1_ == 1)
/* 309:    */     {
/* 310:419 */       this.field_145987_o = p_145842_2_;
/* 311:420 */       return true;
/* 312:    */     }
/* 313:424 */     return super.receiveClientEvent(p_145842_1_, p_145842_2_);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void openInventory()
/* 317:    */   {
/* 318:430 */     if (this.field_145987_o < 0) {
/* 319:432 */       this.field_145987_o = 0;
/* 320:    */     }
/* 321:435 */     this.field_145987_o += 1;
/* 322:436 */     this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType(), 1, this.field_145987_o);
/* 323:437 */     this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType());
/* 324:438 */     this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, getBlockType());
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void closeInventory()
/* 328:    */   {
/* 329:443 */     if ((getBlockType() instanceof BlockChest))
/* 330:    */     {
/* 331:445 */       this.field_145987_o -= 1;
/* 332:446 */       this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType(), 1, this.field_145987_o);
/* 333:447 */       this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType());
/* 334:448 */       this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, getBlockType());
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 339:    */   {
/* 340:457 */     return true;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void invalidate()
/* 344:    */   {
/* 345:465 */     super.invalidate();
/* 346:466 */     updateContainingBlockInfo();
/* 347:467 */     func_145979_i();
/* 348:    */   }
/* 349:    */   
/* 350:    */   public int func_145980_j()
/* 351:    */   {
/* 352:472 */     if (this.field_145982_r == -1)
/* 353:    */     {
/* 354:474 */       if ((this.worldObj == null) || (!(getBlockType() instanceof BlockChest))) {
/* 355:476 */         return 0;
/* 356:    */       }
/* 357:479 */       this.field_145982_r = ((BlockChest)getBlockType()).field_149956_a;
/* 358:    */     }
/* 359:482 */     return this.field_145982_r;
/* 360:    */   }
/* 361:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityChest
 * JD-Core Version:    0.7.0.1
 */