/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.inventory.IInventory;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.network.Packet;
/*  13:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  14:    */ import net.minecraft.potion.Potion;
/*  15:    */ import net.minecraft.potion.PotionEffect;
/*  16:    */ import net.minecraft.stats.AchievementList;
/*  17:    */ import net.minecraft.util.AABBPool;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class TileEntityBeacon
/*  22:    */   extends TileEntity
/*  23:    */   implements IInventory
/*  24:    */ {
/*  25: 21 */   public static final Potion[][] field_146009_a = { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
/*  26:    */   private long field_146016_i;
/*  27:    */   private float field_146014_j;
/*  28:    */   private boolean field_146015_k;
/*  29: 25 */   private int field_146012_l = -1;
/*  30:    */   private int field_146013_m;
/*  31:    */   private int field_146010_n;
/*  32:    */   private ItemStack field_146011_o;
/*  33:    */   private String field_146008_p;
/*  34:    */   private static final String __OBFID = "CL_00000339";
/*  35:    */   
/*  36:    */   public void updateEntity()
/*  37:    */   {
/*  38: 34 */     if (this.worldObj.getTotalWorldTime() % 80L == 0L)
/*  39:    */     {
/*  40: 36 */       func_146003_y();
/*  41: 37 */       func_146000_x();
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void func_146000_x()
/*  46:    */   {
/*  47: 43 */     if ((this.field_146015_k) && (this.field_146012_l > 0) && (!this.worldObj.isClient) && (this.field_146013_m > 0))
/*  48:    */     {
/*  49: 45 */       double var1 = this.field_146012_l * 10 + 10;
/*  50: 46 */       byte var3 = 0;
/*  51: 48 */       if ((this.field_146012_l >= 4) && (this.field_146013_m == this.field_146010_n)) {
/*  52: 50 */         var3 = 1;
/*  53:    */       }
/*  54: 53 */       AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e + 1).expand(var1, var1, var1);
/*  55: 54 */       var4.maxY = this.worldObj.getHeight();
/*  56: 55 */       List var5 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4);
/*  57: 56 */       Iterator var6 = var5.iterator();
/*  58: 59 */       while (var6.hasNext())
/*  59:    */       {
/*  60: 61 */         EntityPlayer var7 = (EntityPlayer)var6.next();
/*  61: 62 */         var7.addPotionEffect(new PotionEffect(this.field_146013_m, 180, var3, true));
/*  62:    */       }
/*  63: 65 */       if ((this.field_146012_l >= 4) && (this.field_146013_m != this.field_146010_n) && (this.field_146010_n > 0))
/*  64:    */       {
/*  65: 67 */         var6 = var5.iterator();
/*  66: 69 */         while (var6.hasNext())
/*  67:    */         {
/*  68: 71 */           EntityPlayer var7 = (EntityPlayer)var6.next();
/*  69: 72 */           var7.addPotionEffect(new PotionEffect(this.field_146010_n, 180, 0, true));
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void func_146003_y()
/*  76:    */   {
/*  77: 80 */     int var1 = this.field_146012_l;
/*  78: 82 */     if (!this.worldObj.canBlockSeeTheSky(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e))
/*  79:    */     {
/*  80: 84 */       this.field_146015_k = false;
/*  81: 85 */       this.field_146012_l = 0;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85: 89 */       this.field_146015_k = true;
/*  86: 90 */       this.field_146012_l = 0;
/*  87: 92 */       for (int var2 = 1; var2 <= 4; this.field_146012_l = (var2++))
/*  88:    */       {
/*  89: 94 */         int var3 = this.field_145848_d - var2;
/*  90: 96 */         if (var3 < 0) {
/*  91:    */           break;
/*  92:    */         }
/*  93:101 */         boolean var4 = true;
/*  94:103 */         for (int var5 = this.field_145851_c - var2; (var5 <= this.field_145851_c + var2) && (var4); var5++) {
/*  95:105 */           for (int var6 = this.field_145849_e - var2; var6 <= this.field_145849_e + var2; var6++)
/*  96:    */           {
/*  97:107 */             Block var7 = this.worldObj.getBlock(var5, var3, var6);
/*  98:109 */             if ((var7 != Blocks.emerald_block) && (var7 != Blocks.gold_block) && (var7 != Blocks.diamond_block) && (var7 != Blocks.iron_block))
/*  99:    */             {
/* 100:111 */               var4 = false;
/* 101:112 */               break;
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105:117 */         if (!var4) {
/* 106:    */           break;
/* 107:    */         }
/* 108:    */       }
/* 109:123 */       if (this.field_146012_l == 0) {
/* 110:125 */         this.field_146015_k = false;
/* 111:    */       }
/* 112:    */     }
/* 113:129 */     if ((!this.worldObj.isClient) && (this.field_146012_l == 4) && (var1 < this.field_146012_l))
/* 114:    */     {
/* 115:131 */       Iterator var8 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d - 4, this.field_145849_e).expand(10.0D, 5.0D, 10.0D)).iterator();
/* 116:133 */       while (var8.hasNext())
/* 117:    */       {
/* 118:135 */         EntityPlayer var9 = (EntityPlayer)var8.next();
/* 119:136 */         var9.triggerAchievement(AchievementList.field_150965_K);
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public float func_146002_i()
/* 125:    */   {
/* 126:143 */     if (!this.field_146015_k) {
/* 127:145 */       return 0.0F;
/* 128:    */     }
/* 129:149 */     int var1 = (int)(this.worldObj.getTotalWorldTime() - this.field_146016_i);
/* 130:150 */     this.field_146016_i = this.worldObj.getTotalWorldTime();
/* 131:152 */     if (var1 > 1)
/* 132:    */     {
/* 133:154 */       this.field_146014_j -= var1 / 40.0F;
/* 134:156 */       if (this.field_146014_j < 0.0F) {
/* 135:158 */         this.field_146014_j = 0.0F;
/* 136:    */       }
/* 137:    */     }
/* 138:162 */     this.field_146014_j += 0.025F;
/* 139:164 */     if (this.field_146014_j > 1.0F) {
/* 140:166 */       this.field_146014_j = 1.0F;
/* 141:    */     }
/* 142:169 */     return this.field_146014_j;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int func_146007_j()
/* 146:    */   {
/* 147:175 */     return this.field_146013_m;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int func_146006_k()
/* 151:    */   {
/* 152:180 */     return this.field_146010_n;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int func_145998_l()
/* 156:    */   {
/* 157:185 */     return this.field_146012_l;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void func_146005_c(int p_146005_1_)
/* 161:    */   {
/* 162:190 */     this.field_146012_l = p_146005_1_;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void func_146001_d(int p_146001_1_)
/* 166:    */   {
/* 167:195 */     this.field_146013_m = 0;
/* 168:197 */     for (int var2 = 0; (var2 < this.field_146012_l) && (var2 < 3); var2++)
/* 169:    */     {
/* 170:199 */       Potion[] var3 = field_146009_a[var2];
/* 171:200 */       int var4 = var3.length;
/* 172:202 */       for (int var5 = 0; var5 < var4; var5++)
/* 173:    */       {
/* 174:204 */         Potion var6 = var3[var5];
/* 175:206 */         if (var6.id == p_146001_1_)
/* 176:    */         {
/* 177:208 */           this.field_146013_m = p_146001_1_;
/* 178:209 */           return;
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void func_146004_e(int p_146004_1_)
/* 185:    */   {
/* 186:217 */     this.field_146010_n = 0;
/* 187:219 */     if (this.field_146012_l >= 4) {
/* 188:221 */       for (int var2 = 0; var2 < 4; var2++)
/* 189:    */       {
/* 190:223 */         Potion[] var3 = field_146009_a[var2];
/* 191:224 */         int var4 = var3.length;
/* 192:226 */         for (int var5 = 0; var5 < var4; var5++)
/* 193:    */         {
/* 194:228 */           Potion var6 = var3[var5];
/* 195:230 */           if (var6.id == p_146004_1_)
/* 196:    */           {
/* 197:232 */             this.field_146010_n = p_146004_1_;
/* 198:233 */             return;
/* 199:    */           }
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Packet getDescriptionPacket()
/* 206:    */   {
/* 207:245 */     NBTTagCompound var1 = new NBTTagCompound();
/* 208:246 */     writeToNBT(var1);
/* 209:247 */     return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 3, var1);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public double getMaxRenderDistanceSquared()
/* 213:    */   {
/* 214:252 */     return 65536.0D;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 218:    */   {
/* 219:257 */     super.readFromNBT(p_145839_1_);
/* 220:258 */     this.field_146013_m = p_145839_1_.getInteger("Primary");
/* 221:259 */     this.field_146010_n = p_145839_1_.getInteger("Secondary");
/* 222:260 */     this.field_146012_l = p_145839_1_.getInteger("Levels");
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 226:    */   {
/* 227:265 */     super.writeToNBT(p_145841_1_);
/* 228:266 */     p_145841_1_.setInteger("Primary", this.field_146013_m);
/* 229:267 */     p_145841_1_.setInteger("Secondary", this.field_146010_n);
/* 230:268 */     p_145841_1_.setInteger("Levels", this.field_146012_l);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int getSizeInventory()
/* 234:    */   {
/* 235:276 */     return 1;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public ItemStack getStackInSlot(int par1)
/* 239:    */   {
/* 240:284 */     return par1 == 0 ? this.field_146011_o : null;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public ItemStack decrStackSize(int par1, int par2)
/* 244:    */   {
/* 245:293 */     if ((par1 == 0) && (this.field_146011_o != null))
/* 246:    */     {
/* 247:295 */       if (par2 >= this.field_146011_o.stackSize)
/* 248:    */       {
/* 249:297 */         ItemStack var3 = this.field_146011_o;
/* 250:298 */         this.field_146011_o = null;
/* 251:299 */         return var3;
/* 252:    */       }
/* 253:303 */       this.field_146011_o.stackSize -= par2;
/* 254:304 */       return new ItemStack(this.field_146011_o.getItem(), par2, this.field_146011_o.getItemDamage());
/* 255:    */     }
/* 256:309 */     return null;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public ItemStack getStackInSlotOnClosing(int par1)
/* 260:    */   {
/* 261:319 */     if ((par1 == 0) && (this.field_146011_o != null))
/* 262:    */     {
/* 263:321 */       ItemStack var2 = this.field_146011_o;
/* 264:322 */       this.field_146011_o = null;
/* 265:323 */       return var2;
/* 266:    */     }
/* 267:327 */     return null;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/* 271:    */   {
/* 272:336 */     if (par1 == 0) {
/* 273:338 */       this.field_146011_o = par2ItemStack;
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getInventoryName()
/* 278:    */   {
/* 279:347 */     return isInventoryNameLocalized() ? this.field_146008_p : "container.beacon";
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean isInventoryNameLocalized()
/* 283:    */   {
/* 284:355 */     return (this.field_146008_p != null) && (this.field_146008_p.length() > 0);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void func_145999_a(String p_145999_1_)
/* 288:    */   {
/* 289:360 */     this.field_146008_p = p_145999_1_;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int getInventoryStackLimit()
/* 293:    */   {
/* 294:368 */     return 1;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 298:    */   {
/* 299:376 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void openInventory() {}
/* 303:    */   
/* 304:    */   public void closeInventory() {}
/* 305:    */   
/* 306:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 307:    */   {
/* 308:388 */     return (par2ItemStack.getItem() == Items.emerald) || (par2ItemStack.getItem() == Items.diamond) || (par2ItemStack.getItem() == Items.gold_ingot) || (par2ItemStack.getItem() == Items.iron_ingot);
/* 309:    */   }
/* 310:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityBeacon
 * JD-Core Version:    0.7.0.1
 */