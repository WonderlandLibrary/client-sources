/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityList;
/*  10:    */ import net.minecraft.entity.EntityLiving;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.nbt.NBTBase;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.nbt.NBTTagList;
/*  15:    */ import net.minecraft.util.AABBPool;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.util.WeightedRandom;
/*  18:    */ import net.minecraft.util.WeightedRandom.Item;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public abstract class MobSpawnerBaseLogic
/*  22:    */ {
/*  23: 21 */   public int spawnDelay = 20;
/*  24: 22 */   private String mobID = "Pig";
/*  25:    */   private List minecartToSpawn;
/*  26:    */   private WeightedRandomMinecart randomMinecart;
/*  27:    */   public double field_98287_c;
/*  28:    */   public double field_98284_d;
/*  29: 29 */   private int minSpawnDelay = 200;
/*  30: 30 */   private int maxSpawnDelay = 800;
/*  31: 33 */   private int spawnCount = 4;
/*  32:    */   private Entity field_98291_j;
/*  33: 35 */   private int maxNearbyEntities = 6;
/*  34: 38 */   private int activatingRangeFromPlayer = 16;
/*  35: 41 */   private int spawnRange = 4;
/*  36:    */   private static final String __OBFID = "CL_00000129";
/*  37:    */   
/*  38:    */   public String getEntityNameToSpawn()
/*  39:    */   {
/*  40: 49 */     if (getRandomMinecart() == null)
/*  41:    */     {
/*  42: 51 */       if (this.mobID.equals("Minecart")) {
/*  43: 53 */         this.mobID = "MinecartRideable";
/*  44:    */       }
/*  45: 56 */       return this.mobID;
/*  46:    */     }
/*  47: 60 */     return getRandomMinecart().minecartName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setMobID(String par1Str)
/*  51:    */   {
/*  52: 66 */     this.mobID = par1Str;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean canRun()
/*  56:    */   {
/*  57: 74 */     return getSpawnerWorld().getClosestPlayer(getSpawnerX() + 0.5D, getSpawnerY() + 0.5D, getSpawnerZ() + 0.5D, this.activatingRangeFromPlayer) != null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void updateSpawner()
/*  61:    */   {
/*  62: 79 */     if (canRun()) {
/*  63: 83 */       if (getSpawnerWorld().isClient)
/*  64:    */       {
/*  65: 85 */         double var1 = getSpawnerX() + getSpawnerWorld().rand.nextFloat();
/*  66: 86 */         double var3 = getSpawnerY() + getSpawnerWorld().rand.nextFloat();
/*  67: 87 */         double var5 = getSpawnerZ() + getSpawnerWorld().rand.nextFloat();
/*  68: 88 */         getSpawnerWorld().spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
/*  69: 89 */         getSpawnerWorld().spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);
/*  70: 91 */         if (this.spawnDelay > 0) {
/*  71: 93 */           this.spawnDelay -= 1;
/*  72:    */         }
/*  73: 96 */         this.field_98284_d = this.field_98287_c;
/*  74: 97 */         this.field_98287_c = ((this.field_98287_c + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D);
/*  75:    */       }
/*  76:    */       else
/*  77:    */       {
/*  78:101 */         if (this.spawnDelay == -1) {
/*  79:103 */           resetTimer();
/*  80:    */         }
/*  81:106 */         if (this.spawnDelay > 0)
/*  82:    */         {
/*  83:108 */           this.spawnDelay -= 1;
/*  84:109 */           return;
/*  85:    */         }
/*  86:112 */         boolean var12 = false;
/*  87:114 */         for (int var2 = 0; var2 < this.spawnCount; var2++)
/*  88:    */         {
/*  89:116 */           Entity var13 = EntityList.createEntityByName(getEntityNameToSpawn(), getSpawnerWorld());
/*  90:118 */           if (var13 == null) {
/*  91:120 */             return;
/*  92:    */           }
/*  93:123 */           int var4 = getSpawnerWorld().getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().getAABB(getSpawnerX(), getSpawnerY(), getSpawnerZ(), getSpawnerX() + 1, getSpawnerY() + 1, getSpawnerZ() + 1).expand(this.spawnRange * 2, 4.0D, this.spawnRange * 2)).size();
/*  94:125 */           if (var4 >= this.maxNearbyEntities)
/*  95:    */           {
/*  96:127 */             resetTimer();
/*  97:128 */             return;
/*  98:    */           }
/*  99:131 */           double var5 = getSpawnerX() + (getSpawnerWorld().rand.nextDouble() - getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
/* 100:132 */           double var7 = getSpawnerY() + getSpawnerWorld().rand.nextInt(3) - 1;
/* 101:133 */           double var9 = getSpawnerZ() + (getSpawnerWorld().rand.nextDouble() - getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
/* 102:134 */           EntityLiving var11 = (var13 instanceof EntityLiving) ? (EntityLiving)var13 : null;
/* 103:135 */           var13.setLocationAndAngles(var5, var7, var9, getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
/* 104:137 */           if ((var11 == null) || (var11.getCanSpawnHere()))
/* 105:    */           {
/* 106:139 */             func_98265_a(var13);
/* 107:140 */             getSpawnerWorld().playAuxSFX(2004, getSpawnerX(), getSpawnerY(), getSpawnerZ(), 0);
/* 108:142 */             if (var11 != null) {
/* 109:144 */               var11.spawnExplosionParticle();
/* 110:    */             }
/* 111:147 */             var12 = true;
/* 112:    */           }
/* 113:    */         }
/* 114:151 */         if (var12) {
/* 115:153 */           resetTimer();
/* 116:    */         }
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Entity func_98265_a(Entity par1Entity)
/* 122:    */   {
/* 123:161 */     if (getRandomMinecart() != null)
/* 124:    */     {
/* 125:163 */       NBTTagCompound var2 = new NBTTagCompound();
/* 126:164 */       par1Entity.writeToNBTOptional(var2);
/* 127:165 */       Iterator var3 = getRandomMinecart().field_98222_b.func_150296_c().iterator();
/* 128:167 */       while (var3.hasNext())
/* 129:    */       {
/* 130:169 */         String var4 = (String)var3.next();
/* 131:170 */         NBTBase var5 = getRandomMinecart().field_98222_b.getTag(var4);
/* 132:171 */         var2.setTag(var4, var5.copy());
/* 133:    */       }
/* 134:174 */       par1Entity.readFromNBT(var2);
/* 135:176 */       if (par1Entity.worldObj != null) {
/* 136:178 */         par1Entity.worldObj.spawnEntityInWorld(par1Entity);
/* 137:    */       }
/* 138:    */       NBTTagCompound var11;
/* 139:183 */       for (Entity var10 = par1Entity; var2.func_150297_b("Riding", 10); var2 = var11)
/* 140:    */       {
/* 141:185 */         var11 = var2.getCompoundTag("Riding");
/* 142:186 */         Entity var12 = EntityList.createEntityByName(var11.getString("id"), par1Entity.worldObj);
/* 143:188 */         if (var12 != null)
/* 144:    */         {
/* 145:190 */           NBTTagCompound var6 = new NBTTagCompound();
/* 146:191 */           var12.writeToNBTOptional(var6);
/* 147:192 */           Iterator var7 = var11.func_150296_c().iterator();
/* 148:194 */           while (var7.hasNext())
/* 149:    */           {
/* 150:196 */             String var8 = (String)var7.next();
/* 151:197 */             NBTBase var9 = var11.getTag(var8);
/* 152:198 */             var6.setTag(var8, var9.copy());
/* 153:    */           }
/* 154:201 */           var12.readFromNBT(var6);
/* 155:202 */           var12.setLocationAndAngles(var10.posX, var10.posY, var10.posZ, var10.rotationYaw, var10.rotationPitch);
/* 156:204 */           if (par1Entity.worldObj != null) {
/* 157:206 */             par1Entity.worldObj.spawnEntityInWorld(var12);
/* 158:    */           }
/* 159:209 */           var10.mountEntity(var12);
/* 160:    */         }
/* 161:212 */         var10 = var12;
/* 162:    */       }
/* 163:    */     }
/* 164:215 */     else if (((par1Entity instanceof EntityLivingBase)) && (par1Entity.worldObj != null))
/* 165:    */     {
/* 166:217 */       ((EntityLiving)par1Entity).onSpawnWithEgg(null);
/* 167:218 */       getSpawnerWorld().spawnEntityInWorld(par1Entity);
/* 168:    */     }
/* 169:221 */     return par1Entity;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private void resetTimer()
/* 173:    */   {
/* 174:226 */     if (this.maxSpawnDelay <= this.minSpawnDelay)
/* 175:    */     {
/* 176:228 */       this.spawnDelay = this.minSpawnDelay;
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:232 */       int var10003 = this.maxSpawnDelay - this.minSpawnDelay;
/* 181:233 */       this.spawnDelay = (this.minSpawnDelay + getSpawnerWorld().rand.nextInt(var10003));
/* 182:    */     }
/* 183:236 */     if ((this.minecartToSpawn != null) && (this.minecartToSpawn.size() > 0)) {
/* 184:238 */       setRandomMinecart((WeightedRandomMinecart)WeightedRandom.getRandomItem(getSpawnerWorld().rand, this.minecartToSpawn));
/* 185:    */     }
/* 186:241 */     func_98267_a(1);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 190:    */   {
/* 191:246 */     this.mobID = par1NBTTagCompound.getString("EntityId");
/* 192:247 */     this.spawnDelay = par1NBTTagCompound.getShort("Delay");
/* 193:249 */     if (par1NBTTagCompound.func_150297_b("SpawnPotentials", 9))
/* 194:    */     {
/* 195:251 */       this.minecartToSpawn = new ArrayList();
/* 196:252 */       NBTTagList var2 = par1NBTTagCompound.getTagList("SpawnPotentials", 10);
/* 197:254 */       for (int var3 = 0; var3 < var2.tagCount(); var3++) {
/* 198:256 */         this.minecartToSpawn.add(new WeightedRandomMinecart(var2.getCompoundTagAt(var3)));
/* 199:    */       }
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:261 */       this.minecartToSpawn = null;
/* 204:    */     }
/* 205:264 */     if (par1NBTTagCompound.func_150297_b("SpawnData", 10)) {
/* 206:266 */       setRandomMinecart(new WeightedRandomMinecart(par1NBTTagCompound.getCompoundTag("SpawnData"), this.mobID));
/* 207:    */     } else {
/* 208:270 */       setRandomMinecart(null);
/* 209:    */     }
/* 210:273 */     if (par1NBTTagCompound.func_150297_b("MinSpawnDelay", 99))
/* 211:    */     {
/* 212:275 */       this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
/* 213:276 */       this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
/* 214:277 */       this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
/* 215:    */     }
/* 216:280 */     if (par1NBTTagCompound.func_150297_b("MaxNearbyEntities", 99))
/* 217:    */     {
/* 218:282 */       this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
/* 219:283 */       this.activatingRangeFromPlayer = par1NBTTagCompound.getShort("RequiredPlayerRange");
/* 220:    */     }
/* 221:286 */     if (par1NBTTagCompound.func_150297_b("SpawnRange", 99)) {
/* 222:288 */       this.spawnRange = par1NBTTagCompound.getShort("SpawnRange");
/* 223:    */     }
/* 224:291 */     if ((getSpawnerWorld() != null) && (getSpawnerWorld().isClient)) {
/* 225:293 */       this.field_98291_j = null;
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 230:    */   {
/* 231:299 */     par1NBTTagCompound.setString("EntityId", getEntityNameToSpawn());
/* 232:300 */     par1NBTTagCompound.setShort("Delay", (short)this.spawnDelay);
/* 233:301 */     par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 234:302 */     par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 235:303 */     par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
/* 236:304 */     par1NBTTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 237:305 */     par1NBTTagCompound.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
/* 238:306 */     par1NBTTagCompound.setShort("SpawnRange", (short)this.spawnRange);
/* 239:308 */     if (getRandomMinecart() != null) {
/* 240:310 */       par1NBTTagCompound.setTag("SpawnData", getRandomMinecart().field_98222_b.copy());
/* 241:    */     }
/* 242:313 */     if ((getRandomMinecart() != null) || ((this.minecartToSpawn != null) && (this.minecartToSpawn.size() > 0)))
/* 243:    */     {
/* 244:315 */       NBTTagList var2 = new NBTTagList();
/* 245:317 */       if ((this.minecartToSpawn != null) && (this.minecartToSpawn.size() > 0))
/* 246:    */       {
/* 247:319 */         Iterator var3 = this.minecartToSpawn.iterator();
/* 248:321 */         while (var3.hasNext())
/* 249:    */         {
/* 250:323 */           WeightedRandomMinecart var4 = (WeightedRandomMinecart)var3.next();
/* 251:324 */           var2.appendTag(var4.func_98220_a());
/* 252:    */         }
/* 253:    */       }
/* 254:    */       else
/* 255:    */       {
/* 256:329 */         var2.appendTag(getRandomMinecart().func_98220_a());
/* 257:    */       }
/* 258:332 */       par1NBTTagCompound.setTag("SpawnPotentials", var2);
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Entity func_98281_h()
/* 263:    */   {
/* 264:338 */     if (this.field_98291_j == null)
/* 265:    */     {
/* 266:340 */       Entity var1 = EntityList.createEntityByName(getEntityNameToSpawn(), null);
/* 267:341 */       var1 = func_98265_a(var1);
/* 268:342 */       this.field_98291_j = var1;
/* 269:    */     }
/* 270:345 */     return this.field_98291_j;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public boolean setDelayToMin(int par1)
/* 274:    */   {
/* 275:353 */     if ((par1 == 1) && (getSpawnerWorld().isClient))
/* 276:    */     {
/* 277:355 */       this.spawnDelay = this.minSpawnDelay;
/* 278:356 */       return true;
/* 279:    */     }
/* 280:360 */     return false;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public WeightedRandomMinecart getRandomMinecart()
/* 284:    */   {
/* 285:366 */     return this.randomMinecart;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setRandomMinecart(WeightedRandomMinecart par1WeightedRandomMinecart)
/* 289:    */   {
/* 290:371 */     this.randomMinecart = par1WeightedRandomMinecart;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public abstract void func_98267_a(int paramInt);
/* 294:    */   
/* 295:    */   public abstract World getSpawnerWorld();
/* 296:    */   
/* 297:    */   public abstract int getSpawnerX();
/* 298:    */   
/* 299:    */   public abstract int getSpawnerY();
/* 300:    */   
/* 301:    */   public abstract int getSpawnerZ();
/* 302:    */   
/* 303:    */   public class WeightedRandomMinecart
/* 304:    */     extends WeightedRandom.Item
/* 305:    */   {
/* 306:    */     public final NBTTagCompound field_98222_b;
/* 307:    */     public final String minecartName;
/* 308:    */     private static final String __OBFID = "CL_00000130";
/* 309:    */     
/* 310:    */     public WeightedRandomMinecart(NBTTagCompound par2NBTTagCompound)
/* 311:    */     {
/* 312:392 */       super();
/* 313:393 */       NBTTagCompound var3 = par2NBTTagCompound.getCompoundTag("Properties");
/* 314:394 */       String var4 = par2NBTTagCompound.getString("Type");
/* 315:396 */       if (var4.equals("Minecart")) {
/* 316:398 */         if (var3 != null) {
/* 317:400 */           switch (var3.getInteger("Type"))
/* 318:    */           {
/* 319:    */           case 0: 
/* 320:403 */             var4 = "MinecartRideable";
/* 321:404 */             break;
/* 322:    */           case 1: 
/* 323:407 */             var4 = "MinecartChest";
/* 324:408 */             break;
/* 325:    */           case 2: 
/* 326:411 */             var4 = "MinecartFurnace";
/* 327:    */           }
/* 328:    */         } else {
/* 329:416 */           var4 = "MinecartRideable";
/* 330:    */         }
/* 331:    */       }
/* 332:420 */       this.field_98222_b = var3;
/* 333:421 */       this.minecartName = var4;
/* 334:    */     }
/* 335:    */     
/* 336:    */     public WeightedRandomMinecart(NBTTagCompound par2NBTTagCompound, String par3Str)
/* 337:    */     {
/* 338:426 */       super();
/* 339:428 */       if (par3Str.equals("Minecart")) {
/* 340:430 */         if (par2NBTTagCompound != null) {
/* 341:432 */           switch (par2NBTTagCompound.getInteger("Type"))
/* 342:    */           {
/* 343:    */           case 0: 
/* 344:435 */             par3Str = "MinecartRideable";
/* 345:436 */             break;
/* 346:    */           case 1: 
/* 347:439 */             par3Str = "MinecartChest";
/* 348:440 */             break;
/* 349:    */           case 2: 
/* 350:443 */             par3Str = "MinecartFurnace";
/* 351:    */           }
/* 352:    */         } else {
/* 353:448 */           par3Str = "MinecartRideable";
/* 354:    */         }
/* 355:    */       }
/* 356:452 */       this.field_98222_b = par2NBTTagCompound;
/* 357:453 */       this.minecartName = par3Str;
/* 358:    */     }
/* 359:    */     
/* 360:    */     public NBTTagCompound func_98220_a()
/* 361:    */     {
/* 362:458 */       NBTTagCompound var1 = new NBTTagCompound();
/* 363:459 */       var1.setTag("Properties", this.field_98222_b);
/* 364:460 */       var1.setString("Type", this.minecartName);
/* 365:461 */       var1.setInteger("Weight", this.itemWeight);
/* 366:462 */       return var1;
/* 367:    */     }
/* 368:    */   }
/* 369:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.MobSpawnerBaseLogic
 * JD-Core Version:    0.7.0.1
 */