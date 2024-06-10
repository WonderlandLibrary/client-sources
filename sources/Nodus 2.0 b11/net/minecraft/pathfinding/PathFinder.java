/*   1:    */ package net.minecraft.pathfinding;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.IntHashMap;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class PathFinder
/*  14:    */ {
/*  15:    */   private IBlockAccess worldMap;
/*  16: 17 */   private Path path = new Path();
/*  17: 20 */   private IntHashMap pointMap = new IntHashMap();
/*  18: 23 */   private PathPoint[] pathOptions = new PathPoint[32];
/*  19:    */   private boolean isWoddenDoorAllowed;
/*  20:    */   private boolean isMovementBlockAllowed;
/*  21:    */   private boolean isPathingInWater;
/*  22:    */   private boolean canEntityDrown;
/*  23:    */   private static final String __OBFID = "CL_00000576";
/*  24:    */   
/*  25:    */   public PathFinder(IBlockAccess par1IBlockAccess, boolean par2, boolean par3, boolean par4, boolean par5)
/*  26:    */   {
/*  27: 40 */     this.worldMap = par1IBlockAccess;
/*  28: 41 */     this.isWoddenDoorAllowed = par2;
/*  29: 42 */     this.isMovementBlockAllowed = par3;
/*  30: 43 */     this.isPathingInWater = par4;
/*  31: 44 */     this.canEntityDrown = par5;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PathEntity createEntityPathTo(Entity par1Entity, Entity par2Entity, float par3)
/*  35:    */   {
/*  36: 52 */     return createEntityPathTo(par1Entity, par2Entity.posX, par2Entity.boundingBox.minY, par2Entity.posZ, par3);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PathEntity createEntityPathTo(Entity par1Entity, int par2, int par3, int par4, float par5)
/*  40:    */   {
/*  41: 60 */     return createEntityPathTo(par1Entity, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, par5);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private PathEntity createEntityPathTo(Entity par1Entity, double par2, double par4, double par6, float par8)
/*  45:    */   {
/*  46: 68 */     this.path.clearPath();
/*  47: 69 */     this.pointMap.clearMap();
/*  48: 70 */     boolean var9 = this.isPathingInWater;
/*  49: 71 */     int var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5D);
/*  50: 73 */     if ((this.canEntityDrown) && (par1Entity.isInWater()))
/*  51:    */     {
/*  52: 75 */       var10 = (int)par1Entity.boundingBox.minY;
/*  53: 77 */       for (Block var11 = this.worldMap.getBlock(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ)); (var11 == Blocks.flowing_water) || (var11 == Blocks.water); var11 = this.worldMap.getBlock(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ))) {
/*  54: 79 */         var10++;
/*  55:    */       }
/*  56: 82 */       var9 = this.isPathingInWater;
/*  57: 83 */       this.isPathingInWater = false;
/*  58:    */     }
/*  59:    */     else
/*  60:    */     {
/*  61: 87 */       var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5D);
/*  62:    */     }
/*  63: 90 */     PathPoint var15 = openPoint(MathHelper.floor_double(par1Entity.boundingBox.minX), var10, MathHelper.floor_double(par1Entity.boundingBox.minZ));
/*  64: 91 */     PathPoint var12 = openPoint(MathHelper.floor_double(par2 - par1Entity.width / 2.0F), MathHelper.floor_double(par4), MathHelper.floor_double(par6 - par1Entity.width / 2.0F));
/*  65: 92 */     PathPoint var13 = new PathPoint(MathHelper.floor_float(par1Entity.width + 1.0F), MathHelper.floor_float(par1Entity.height + 1.0F), MathHelper.floor_float(par1Entity.width + 1.0F));
/*  66: 93 */     PathEntity var14 = addToPath(par1Entity, var15, var12, var13, par8);
/*  67: 94 */     this.isPathingInWater = var9;
/*  68: 95 */     return var14;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private PathEntity addToPath(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
/*  72:    */   {
/*  73:103 */     par2PathPoint.totalPathDistance = 0.0F;
/*  74:104 */     par2PathPoint.distanceToNext = par2PathPoint.distanceToSquared(par3PathPoint);
/*  75:105 */     par2PathPoint.distanceToTarget = par2PathPoint.distanceToNext;
/*  76:106 */     this.path.clearPath();
/*  77:107 */     this.path.addPoint(par2PathPoint);
/*  78:108 */     PathPoint var6 = par2PathPoint;
/*  79:    */     int var8;
/*  80:    */     int var9;
/*  81:110 */     for (; !this.path.isPathEmpty(); var9 < var8)
/*  82:    */     {
/*  83:112 */       PathPoint var7 = this.path.dequeue();
/*  84:114 */       if (var7.equals(par3PathPoint)) {
/*  85:116 */         return createEntityPath(par2PathPoint, par3PathPoint);
/*  86:    */       }
/*  87:119 */       if (var7.distanceToSquared(par3PathPoint) < var6.distanceToSquared(par3PathPoint)) {
/*  88:121 */         var6 = var7;
/*  89:    */       }
/*  90:124 */       var7.isFirst = true;
/*  91:125 */       var8 = findPathOptions(par1Entity, var7, par4PathPoint, par3PathPoint, par5);
/*  92:    */       
/*  93:127 */       var9 = 0; continue;
/*  94:    */       
/*  95:129 */       PathPoint var10 = this.pathOptions[var9];
/*  96:130 */       float var11 = var7.totalPathDistance + var7.distanceToSquared(var10);
/*  97:132 */       if ((!var10.isAssigned()) || (var11 < var10.totalPathDistance))
/*  98:    */       {
/*  99:134 */         var10.previous = var7;
/* 100:135 */         var10.totalPathDistance = var11;
/* 101:136 */         var10.distanceToNext = var10.distanceToSquared(par3PathPoint);
/* 102:138 */         if (var10.isAssigned())
/* 103:    */         {
/* 104:140 */           this.path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
/* 105:    */         }
/* 106:    */         else
/* 107:    */         {
/* 108:144 */           var10.distanceToTarget = (var10.totalPathDistance + var10.distanceToNext);
/* 109:145 */           this.path.addPoint(var10);
/* 110:    */         }
/* 111:    */       }
/* 112:127 */       var9++;
/* 113:    */     }
/* 114:151 */     if (var6 == par2PathPoint) {
/* 115:153 */       return null;
/* 116:    */     }
/* 117:157 */     return createEntityPath(par2PathPoint, var6);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private int findPathOptions(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
/* 121:    */   {
/* 122:167 */     int var6 = 0;
/* 123:168 */     byte var7 = 0;
/* 124:170 */     if (getVerticalOffset(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord + 1, par2PathPoint.zCoord, par3PathPoint) == 1) {
/* 125:172 */       var7 = 1;
/* 126:    */     }
/* 127:175 */     PathPoint var8 = getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord + 1, par3PathPoint, var7);
/* 128:176 */     PathPoint var9 = getSafePoint(par1Entity, par2PathPoint.xCoord - 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
/* 129:177 */     PathPoint var10 = getSafePoint(par1Entity, par2PathPoint.xCoord + 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
/* 130:178 */     PathPoint var11 = getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord - 1, par3PathPoint, var7);
/* 131:180 */     if ((var8 != null) && (!var8.isFirst) && (var8.distanceTo(par4PathPoint) < par5)) {
/* 132:182 */       this.pathOptions[(var6++)] = var8;
/* 133:    */     }
/* 134:185 */     if ((var9 != null) && (!var9.isFirst) && (var9.distanceTo(par4PathPoint) < par5)) {
/* 135:187 */       this.pathOptions[(var6++)] = var9;
/* 136:    */     }
/* 137:190 */     if ((var10 != null) && (!var10.isFirst) && (var10.distanceTo(par4PathPoint) < par5)) {
/* 138:192 */       this.pathOptions[(var6++)] = var10;
/* 139:    */     }
/* 140:195 */     if ((var11 != null) && (!var11.isFirst) && (var11.distanceTo(par4PathPoint) < par5)) {
/* 141:197 */       this.pathOptions[(var6++)] = var11;
/* 142:    */     }
/* 143:200 */     return var6;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private PathPoint getSafePoint(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint, int par6)
/* 147:    */   {
/* 148:208 */     PathPoint var7 = null;
/* 149:209 */     int var8 = getVerticalOffset(par1Entity, par2, par3, par4, par5PathPoint);
/* 150:211 */     if (var8 == 2) {
/* 151:213 */       return openPoint(par2, par3, par4);
/* 152:    */     }
/* 153:217 */     if (var8 == 1) {
/* 154:219 */       var7 = openPoint(par2, par3, par4);
/* 155:    */     }
/* 156:222 */     if ((var7 == null) && (par6 > 0) && (var8 != -3) && (var8 != -4) && (getVerticalOffset(par1Entity, par2, par3 + par6, par4, par5PathPoint) == 1))
/* 157:    */     {
/* 158:224 */       var7 = openPoint(par2, par3 + par6, par4);
/* 159:225 */       par3 += par6;
/* 160:    */     }
/* 161:228 */     if (var7 != null)
/* 162:    */     {
/* 163:230 */       int var9 = 0;
/* 164:231 */       int var10 = 0;
/* 165:233 */       while (par3 > 0)
/* 166:    */       {
/* 167:235 */         var10 = getVerticalOffset(par1Entity, par2, par3 - 1, par4, par5PathPoint);
/* 168:237 */         if ((this.isPathingInWater) && (var10 == -1)) {
/* 169:239 */           return null;
/* 170:    */         }
/* 171:242 */         if (var10 != 1) {
/* 172:    */           break;
/* 173:    */         }
/* 174:247 */         if (var9++ >= par1Entity.getMaxSafePointTries()) {
/* 175:249 */           return null;
/* 176:    */         }
/* 177:252 */         par3--;
/* 178:254 */         if (par3 > 0) {
/* 179:256 */           var7 = openPoint(par2, par3, par4);
/* 180:    */         }
/* 181:    */       }
/* 182:260 */       if (var10 == -2) {
/* 183:262 */         return null;
/* 184:    */       }
/* 185:    */     }
/* 186:266 */     return var7;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private final PathPoint openPoint(int par1, int par2, int par3)
/* 190:    */   {
/* 191:275 */     int var4 = PathPoint.makeHash(par1, par2, par3);
/* 192:276 */     PathPoint var5 = (PathPoint)this.pointMap.lookup(var4);
/* 193:278 */     if (var5 == null)
/* 194:    */     {
/* 195:280 */       var5 = new PathPoint(par1, par2, par3);
/* 196:281 */       this.pointMap.addKey(var4, var5);
/* 197:    */     }
/* 198:284 */     return var5;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getVerticalOffset(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint)
/* 202:    */   {
/* 203:294 */     return func_82565_a(par1Entity, par2, par3, par4, par5PathPoint, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static int func_82565_a(Entity par0Entity, int par1, int par2, int par3, PathPoint par4PathPoint, boolean par5, boolean par6, boolean par7)
/* 207:    */   {
/* 208:299 */     boolean var8 = false;
/* 209:301 */     for (int var9 = par1; var9 < par1 + par4PathPoint.xCoord; var9++) {
/* 210:303 */       for (int var10 = par2; var10 < par2 + par4PathPoint.yCoord; var10++) {
/* 211:305 */         for (int var11 = par3; var11 < par3 + par4PathPoint.zCoord; var11++)
/* 212:    */         {
/* 213:307 */           Block var12 = par0Entity.worldObj.getBlock(var9, var10, var11);
/* 214:309 */           if (var12.getMaterial() != Material.air)
/* 215:    */           {
/* 216:311 */             if (var12 == Blocks.trapdoor)
/* 217:    */             {
/* 218:313 */               var8 = true;
/* 219:    */             }
/* 220:315 */             else if ((var12 != Blocks.flowing_water) && (var12 != Blocks.water))
/* 221:    */             {
/* 222:317 */               if ((!par7) && (var12 == Blocks.wooden_door)) {
/* 223:319 */                 return 0;
/* 224:    */               }
/* 225:    */             }
/* 226:    */             else
/* 227:    */             {
/* 228:324 */               if (par5) {
/* 229:326 */                 return -1;
/* 230:    */               }
/* 231:329 */               var8 = true;
/* 232:    */             }
/* 233:332 */             int var13 = var12.getRenderType();
/* 234:334 */             if (par0Entity.worldObj.getBlock(var9, var10, var11).getRenderType() == 9)
/* 235:    */             {
/* 236:336 */               int var17 = MathHelper.floor_double(par0Entity.posX);
/* 237:337 */               int var15 = MathHelper.floor_double(par0Entity.posY);
/* 238:338 */               int var16 = MathHelper.floor_double(par0Entity.posZ);
/* 239:340 */               if ((par0Entity.worldObj.getBlock(var17, var15, var16).getRenderType() != 9) && (par0Entity.worldObj.getBlock(var17, var15 - 1, var16).getRenderType() != 9)) {
/* 240:342 */                 return -3;
/* 241:    */               }
/* 242:    */             }
/* 243:345 */             else if ((!var12.getBlocksMovement(par0Entity.worldObj, var9, var10, var11)) && ((!par6) || (var12 != Blocks.wooden_door)))
/* 244:    */             {
/* 245:347 */               if ((var13 == 11) || (var12 == Blocks.fence_gate) || (var13 == 32)) {
/* 246:349 */                 return -3;
/* 247:    */               }
/* 248:352 */               if (var12 == Blocks.trapdoor) {
/* 249:354 */                 return -4;
/* 250:    */               }
/* 251:357 */               Material var14 = var12.getMaterial();
/* 252:359 */               if (var14 != Material.lava) {
/* 253:361 */                 return 0;
/* 254:    */               }
/* 255:364 */               if (!par0Entity.handleLavaMovement()) {
/* 256:366 */                 return -2;
/* 257:    */               }
/* 258:    */             }
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:    */     }
/* 263:374 */     return var8 ? 2 : 1;
/* 264:    */   }
/* 265:    */   
/* 266:    */   private PathEntity createEntityPath(PathPoint par1PathPoint, PathPoint par2PathPoint)
/* 267:    */   {
/* 268:382 */     int var3 = 1;
/* 269:385 */     for (PathPoint var4 = par2PathPoint; var4.previous != null; var4 = var4.previous) {
/* 270:387 */       var3++;
/* 271:    */     }
/* 272:390 */     PathPoint[] var5 = new PathPoint[var3];
/* 273:391 */     var4 = par2PathPoint;
/* 274:392 */     var3--;
/* 275:394 */     for (var5[var3] = par2PathPoint; var4.previous != null; var5[var3] = var4)
/* 276:    */     {
/* 277:396 */       var4 = var4.previous;
/* 278:397 */       var3--;
/* 279:    */     }
/* 280:400 */     return new PathEntity(var5);
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.pathfinding.PathFinder
 * JD-Core Version:    0.7.0.1
 */