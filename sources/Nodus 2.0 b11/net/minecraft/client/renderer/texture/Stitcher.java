/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import net.minecraft.client.renderer.StitcherException;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ 
/*  13:    */ public class Stitcher
/*  14:    */ {
/*  15:    */   private final int mipmapLevelStitcher;
/*  16: 16 */   private final Set setStitchHolders = new HashSet(256);
/*  17: 17 */   private final List stitchSlots = new ArrayList(256);
/*  18:    */   private int currentWidth;
/*  19:    */   private int currentHeight;
/*  20:    */   private final int maxWidth;
/*  21:    */   private final int maxHeight;
/*  22:    */   private final boolean forcePowerOf2;
/*  23:    */   private final int maxTileDimension;
/*  24:    */   private static final String __OBFID = "CL_00001054";
/*  25:    */   
/*  26:    */   public Stitcher(int p_i45095_1_, int p_i45095_2_, boolean p_i45095_3_, int p_i45095_4_, int p_i45095_5_)
/*  27:    */   {
/*  28: 30 */     this.mipmapLevelStitcher = p_i45095_5_;
/*  29: 31 */     this.maxWidth = p_i45095_1_;
/*  30: 32 */     this.maxHeight = p_i45095_2_;
/*  31: 33 */     this.forcePowerOf2 = p_i45095_3_;
/*  32: 34 */     this.maxTileDimension = p_i45095_4_;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getCurrentWidth()
/*  36:    */   {
/*  37: 39 */     return this.currentWidth;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getCurrentHeight()
/*  41:    */   {
/*  42: 44 */     return this.currentHeight;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addSprite(TextureAtlasSprite par1TextureAtlasSprite)
/*  46:    */   {
/*  47: 49 */     Holder var2 = new Holder(par1TextureAtlasSprite, this.mipmapLevelStitcher);
/*  48: 51 */     if (this.maxTileDimension > 0) {
/*  49: 53 */       var2.setNewDimension(this.maxTileDimension);
/*  50:    */     }
/*  51: 56 */     this.setStitchHolders.add(var2);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void doStitch()
/*  55:    */   {
/*  56: 61 */     Holder[] var1 = (Holder[])this.setStitchHolders.toArray(new Holder[this.setStitchHolders.size()]);
/*  57: 62 */     Arrays.sort(var1);
/*  58: 63 */     Holder[] var2 = var1;
/*  59: 64 */     int var3 = var1.length;
/*  60: 66 */     for (int var4 = 0; var4 < var3; var4++)
/*  61:    */     {
/*  62: 68 */       Holder var5 = var2[var4];
/*  63: 70 */       if (!allocateSlot(var5))
/*  64:    */       {
/*  65: 72 */         String var6 = String.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution texturepack?", new Object[] { var5.getAtlasSprite().getIconName(), Integer.valueOf(var5.getAtlasSprite().getIconWidth()), Integer.valueOf(var5.getAtlasSprite().getIconHeight()) });
/*  66: 73 */         throw new StitcherException(var5, var6);
/*  67:    */       }
/*  68:    */     }
/*  69: 77 */     if (this.forcePowerOf2)
/*  70:    */     {
/*  71: 79 */       this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/*  72: 80 */       this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List getStichSlots()
/*  77:    */   {
/*  78: 86 */     ArrayList var1 = Lists.newArrayList();
/*  79: 87 */     Iterator var2 = this.stitchSlots.iterator();
/*  80: 89 */     while (var2.hasNext())
/*  81:    */     {
/*  82: 91 */       Slot var3 = (Slot)var2.next();
/*  83: 92 */       var3.getAllStitchSlots(var1);
/*  84:    */     }
/*  85: 95 */     ArrayList var7 = Lists.newArrayList();
/*  86: 96 */     Iterator var8 = var1.iterator();
/*  87: 98 */     while (var8.hasNext())
/*  88:    */     {
/*  89:100 */       Slot var4 = (Slot)var8.next();
/*  90:101 */       Holder var5 = var4.getStitchHolder();
/*  91:102 */       TextureAtlasSprite var6 = var5.getAtlasSprite();
/*  92:103 */       var6.initSprite(this.currentWidth, this.currentHeight, var4.getOriginX(), var4.getOriginY(), var5.isRotated());
/*  93:104 */       var7.add(var6);
/*  94:    */     }
/*  95:107 */     return var7;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private static int func_147969_b(int p_147969_0_, int p_147969_1_)
/*  99:    */   {
/* 100:112 */     return (p_147969_0_ >> p_147969_1_) + ((p_147969_0_ & (1 << p_147969_1_) - 1) == 0 ? 0 : 1) << p_147969_1_;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private boolean allocateSlot(Holder par1StitchHolder)
/* 104:    */   {
/* 105:120 */     for (int var2 = 0; var2 < this.stitchSlots.size(); var2++)
/* 106:    */     {
/* 107:122 */       if (((Slot)this.stitchSlots.get(var2)).addSlot(par1StitchHolder)) {
/* 108:124 */         return true;
/* 109:    */       }
/* 110:127 */       par1StitchHolder.rotate();
/* 111:129 */       if (((Slot)this.stitchSlots.get(var2)).addSlot(par1StitchHolder)) {
/* 112:131 */         return true;
/* 113:    */       }
/* 114:134 */       par1StitchHolder.rotate();
/* 115:    */     }
/* 116:137 */     return expandAndAllocateSlot(par1StitchHolder);
/* 117:    */   }
/* 118:    */   
/* 119:    */   private boolean expandAndAllocateSlot(Holder par1StitchHolder)
/* 120:    */   {
/* 121:145 */     int var2 = Math.min(par1StitchHolder.getWidth(), par1StitchHolder.getHeight());
/* 122:146 */     boolean var3 = (this.currentWidth == 0) && (this.currentHeight == 0);
/* 123:    */     boolean var4;
/* 124:    */     boolean var4;
/* 125:150 */     if (this.forcePowerOf2)
/* 126:    */     {
/* 127:152 */       int var5 = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/* 128:153 */       int var6 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/* 129:154 */       int var7 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + var2);
/* 130:155 */       int var8 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + var2);
/* 131:156 */       boolean var9 = var7 <= this.maxWidth;
/* 132:157 */       boolean var10 = var8 <= this.maxHeight;
/* 133:159 */       if ((!var9) && (!var10)) {
/* 134:161 */         return false;
/* 135:    */       }
/* 136:164 */       boolean var11 = var5 != var7;
/* 137:165 */       boolean var12 = var6 != var8;
/* 138:    */       boolean var4;
/* 139:167 */       if ((var11 ^ var12)) {
/* 140:169 */         var4 = !var11;
/* 141:    */       } else {
/* 142:173 */         var4 = (var9) && (var5 <= var6);
/* 143:    */       }
/* 144:    */     }
/* 145:    */     else
/* 146:    */     {
/* 147:178 */       boolean var13 = this.currentWidth + var2 <= this.maxWidth;
/* 148:179 */       boolean var15 = this.currentHeight + var2 <= this.maxHeight;
/* 149:181 */       if ((!var13) && (!var15)) {
/* 150:183 */         return false;
/* 151:    */       }
/* 152:186 */       var4 = (var13) && ((var3) || (this.currentWidth <= this.currentHeight));
/* 153:    */     }
/* 154:189 */     int var5 = Math.max(par1StitchHolder.getWidth(), par1StitchHolder.getHeight());
/* 155:191 */     if (MathHelper.roundUpToPowerOfTwo((var4 ? this.currentHeight : this.currentWidth) + var5) > (var4 ? this.maxHeight : this.maxWidth)) {
/* 156:193 */       return false;
/* 157:    */     }
/* 158:    */     Slot var14;
/* 159:199 */     if (var4)
/* 160:    */     {
/* 161:201 */       if (par1StitchHolder.getWidth() > par1StitchHolder.getHeight()) {
/* 162:203 */         par1StitchHolder.rotate();
/* 163:    */       }
/* 164:206 */       if (this.currentHeight == 0) {
/* 165:208 */         this.currentHeight = par1StitchHolder.getHeight();
/* 166:    */       }
/* 167:211 */       Slot var14 = new Slot(this.currentWidth, 0, par1StitchHolder.getWidth(), this.currentHeight);
/* 168:212 */       this.currentWidth += par1StitchHolder.getWidth();
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:216 */       var14 = new Slot(0, this.currentHeight, this.currentWidth, par1StitchHolder.getHeight());
/* 173:217 */       this.currentHeight += par1StitchHolder.getHeight();
/* 174:    */     }
/* 175:220 */     var14.addSlot(par1StitchHolder);
/* 176:221 */     this.stitchSlots.add(var14);
/* 177:222 */     return true;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static class Slot
/* 181:    */   {
/* 182:    */     private final int originX;
/* 183:    */     private final int originY;
/* 184:    */     private final int width;
/* 185:    */     private final int height;
/* 186:    */     private List subSlots;
/* 187:    */     private Stitcher.Holder holder;
/* 188:    */     private static final String __OBFID = "CL_00001056";
/* 189:    */     
/* 190:    */     public Slot(int par1, int par2, int par3, int par4)
/* 191:    */     {
/* 192:238 */       this.originX = par1;
/* 193:239 */       this.originY = par2;
/* 194:240 */       this.width = par3;
/* 195:241 */       this.height = par4;
/* 196:    */     }
/* 197:    */     
/* 198:    */     public Stitcher.Holder getStitchHolder()
/* 199:    */     {
/* 200:246 */       return this.holder;
/* 201:    */     }
/* 202:    */     
/* 203:    */     public int getOriginX()
/* 204:    */     {
/* 205:251 */       return this.originX;
/* 206:    */     }
/* 207:    */     
/* 208:    */     public int getOriginY()
/* 209:    */     {
/* 210:256 */       return this.originY;
/* 211:    */     }
/* 212:    */     
/* 213:    */     public boolean addSlot(Stitcher.Holder par1StitchHolder)
/* 214:    */     {
/* 215:261 */       if (this.holder != null) {
/* 216:263 */         return false;
/* 217:    */       }
/* 218:267 */       int var2 = par1StitchHolder.getWidth();
/* 219:268 */       int var3 = par1StitchHolder.getHeight();
/* 220:270 */       if ((var2 <= this.width) && (var3 <= this.height))
/* 221:    */       {
/* 222:272 */         if ((var2 == this.width) && (var3 == this.height))
/* 223:    */         {
/* 224:274 */           this.holder = par1StitchHolder;
/* 225:275 */           return true;
/* 226:    */         }
/* 227:279 */         if (this.subSlots == null)
/* 228:    */         {
/* 229:281 */           this.subSlots = new ArrayList(1);
/* 230:282 */           this.subSlots.add(new Slot(this.originX, this.originY, var2, var3));
/* 231:283 */           int var4 = this.width - var2;
/* 232:284 */           int var5 = this.height - var3;
/* 233:286 */           if ((var5 > 0) && (var4 > 0))
/* 234:    */           {
/* 235:288 */             int var6 = Math.max(this.height, var4);
/* 236:289 */             int var7 = Math.max(this.width, var5);
/* 237:291 */             if (var6 >= var7)
/* 238:    */             {
/* 239:293 */               this.subSlots.add(new Slot(this.originX, this.originY + var3, var2, var5));
/* 240:294 */               this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, this.height));
/* 241:    */             }
/* 242:    */             else
/* 243:    */             {
/* 244:298 */               this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, var3));
/* 245:299 */               this.subSlots.add(new Slot(this.originX, this.originY + var3, this.width, var5));
/* 246:    */             }
/* 247:    */           }
/* 248:302 */           else if (var4 == 0)
/* 249:    */           {
/* 250:304 */             this.subSlots.add(new Slot(this.originX, this.originY + var3, var2, var5));
/* 251:    */           }
/* 252:306 */           else if (var5 == 0)
/* 253:    */           {
/* 254:308 */             this.subSlots.add(new Slot(this.originX + var2, this.originY, var4, var3));
/* 255:    */           }
/* 256:    */         }
/* 257:312 */         Iterator var8 = this.subSlots.iterator();
/* 258:    */         Slot var9;
/* 259:    */         do
/* 260:    */         {
/* 261:317 */           if (!var8.hasNext()) {
/* 262:319 */             return false;
/* 263:    */           }
/* 264:322 */           var9 = (Slot)var8.next();
/* 265:324 */         } while (!var9.addSlot(par1StitchHolder));
/* 266:326 */         return true;
/* 267:    */       }
/* 268:331 */       return false;
/* 269:    */     }
/* 270:    */     
/* 271:    */     public void getAllStitchSlots(List par1List)
/* 272:    */     {
/* 273:338 */       if (this.holder != null)
/* 274:    */       {
/* 275:340 */         par1List.add(this);
/* 276:    */       }
/* 277:342 */       else if (this.subSlots != null)
/* 278:    */       {
/* 279:344 */         Iterator var2 = this.subSlots.iterator();
/* 280:346 */         while (var2.hasNext())
/* 281:    */         {
/* 282:348 */           Slot var3 = (Slot)var2.next();
/* 283:349 */           var3.getAllStitchSlots(par1List);
/* 284:    */         }
/* 285:    */       }
/* 286:    */     }
/* 287:    */     
/* 288:    */     public String toString()
/* 289:    */     {
/* 290:356 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   public static class Holder
/* 295:    */     implements Comparable
/* 296:    */   {
/* 297:    */     private final TextureAtlasSprite theTexture;
/* 298:    */     private final int width;
/* 299:    */     private final int height;
/* 300:    */     private final int mipmapLevelHolder;
/* 301:    */     private boolean rotated;
/* 302:367 */     private float scaleFactor = 1.0F;
/* 303:    */     private static final String __OBFID = "CL_00001055";
/* 304:    */     
/* 305:    */     public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_)
/* 306:    */     {
/* 307:372 */       this.theTexture = p_i45094_1_;
/* 308:373 */       this.width = p_i45094_1_.getIconWidth();
/* 309:374 */       this.height = p_i45094_1_.getIconHeight();
/* 310:375 */       this.mipmapLevelHolder = p_i45094_2_;
/* 311:376 */       this.rotated = (Stitcher.func_147969_b(this.height, p_i45094_2_) > Stitcher.func_147969_b(this.width, p_i45094_2_));
/* 312:    */     }
/* 313:    */     
/* 314:    */     public TextureAtlasSprite getAtlasSprite()
/* 315:    */     {
/* 316:381 */       return this.theTexture;
/* 317:    */     }
/* 318:    */     
/* 319:    */     public int getWidth()
/* 320:    */     {
/* 321:386 */       return this.rotated ? Stitcher.func_147969_b((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.func_147969_b((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
/* 322:    */     }
/* 323:    */     
/* 324:    */     public int getHeight()
/* 325:    */     {
/* 326:391 */       return this.rotated ? Stitcher.func_147969_b((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.func_147969_b((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
/* 327:    */     }
/* 328:    */     
/* 329:    */     public void rotate()
/* 330:    */     {
/* 331:396 */       this.rotated = (!this.rotated);
/* 332:    */     }
/* 333:    */     
/* 334:    */     public boolean isRotated()
/* 335:    */     {
/* 336:401 */       return this.rotated;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public void setNewDimension(int par1)
/* 340:    */     {
/* 341:406 */       if ((this.width > par1) && (this.height > par1)) {
/* 342:408 */         this.scaleFactor = (par1 / Math.min(this.width, this.height));
/* 343:    */       }
/* 344:    */     }
/* 345:    */     
/* 346:    */     public String toString()
/* 347:    */     {
/* 348:414 */       return "Holder{width=" + this.width + ", height=" + this.height + '}';
/* 349:    */     }
/* 350:    */     
/* 351:    */     public int compareTo(Holder par1StitchHolder)
/* 352:    */     {
/* 353:    */       int var2;
/* 354:    */       int var2;
/* 355:421 */       if (getHeight() == par1StitchHolder.getHeight())
/* 356:    */       {
/* 357:423 */         if (getWidth() == par1StitchHolder.getWidth())
/* 358:    */         {
/* 359:425 */           if (this.theTexture.getIconName() == null) {
/* 360:427 */             return par1StitchHolder.theTexture.getIconName() == null ? 0 : -1;
/* 361:    */           }
/* 362:430 */           return this.theTexture.getIconName().compareTo(par1StitchHolder.theTexture.getIconName());
/* 363:    */         }
/* 364:433 */         var2 = getWidth() < par1StitchHolder.getWidth() ? 1 : -1;
/* 365:    */       }
/* 366:    */       else
/* 367:    */       {
/* 368:437 */         var2 = getHeight() < par1StitchHolder.getHeight() ? 1 : -1;
/* 369:    */       }
/* 370:440 */       return var2;
/* 371:    */     }
/* 372:    */     
/* 373:    */     public int compareTo(Object par1Obj)
/* 374:    */     {
/* 375:445 */       return compareTo((Holder)par1Obj);
/* 376:    */     }
/* 377:    */   }
/* 378:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.Stitcher
 * JD-Core Version:    0.7.0.1
 */