/*   1:    */ package net.minecraft.server.management;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   7:    */ import net.minecraft.network.NetHandlerPlayServer;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.play.server.S21PacketChunkData;
/*  10:    */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*  11:    */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*  12:    */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*  13:    */ import net.minecraft.src.CompactArrayList;
/*  14:    */ import net.minecraft.src.Config;
/*  15:    */ import net.minecraft.src.Reflector;
/*  16:    */ import net.minecraft.src.ReflectorClass;
/*  17:    */ import net.minecraft.tileentity.TileEntity;
/*  18:    */ import net.minecraft.util.LongHashMap;
/*  19:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  20:    */ import net.minecraft.world.WorldProvider;
/*  21:    */ import net.minecraft.world.WorldServer;
/*  22:    */ import net.minecraft.world.chunk.Chunk;
/*  23:    */ import net.minecraft.world.gen.ChunkProviderServer;
/*  24:    */ 
/*  25:    */ public class PlayerManager
/*  26:    */ {
/*  27:    */   private final WorldServer theWorldServer;
/*  28: 26 */   private final List players = new ArrayList();
/*  29: 31 */   private final LongHashMap playerInstances = new LongHashMap();
/*  30: 37 */   private final List chunkWatcherWithPlayers = new ArrayList();
/*  31: 40 */   private final List playerInstanceList = new ArrayList();
/*  32: 41 */   public CompactArrayList chunkCoordsNotLoaded = new CompactArrayList(100, 0.8F);
/*  33:    */   private int playerViewRadius;
/*  34:    */   private long previousTotalWorldTime;
/*  35: 52 */   private final int[][] xzDirectionsConst = { { 1 }, { 0, 1 }, { -1 }, { 0, -1 } };
/*  36:    */   private static final String __OBFID = "CL_00001434";
/*  37:    */   
/*  38:    */   public PlayerManager(WorldServer par1WorldServer, int par2)
/*  39:    */   {
/*  40: 57 */     if (par2 > 15) {
/*  41: 59 */       throw new IllegalArgumentException("Too big view radius!");
/*  42:    */     }
/*  43: 61 */     if (par2 < 3) {
/*  44: 63 */       throw new IllegalArgumentException("Too small view radius!");
/*  45:    */     }
/*  46: 67 */     this.playerViewRadius = Config.getChunkViewDistance();
/*  47: 68 */     Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (constructor)");
/*  48: 69 */     this.theWorldServer = par1WorldServer;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public WorldServer getWorldServer()
/*  52:    */   {
/*  53: 75 */     return this.theWorldServer;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void updatePlayerInstances()
/*  57:    */   {
/*  58: 83 */     long var1 = this.theWorldServer.getTotalWorldTime();
/*  59: 87 */     if (var1 - this.previousTotalWorldTime > 8000L)
/*  60:    */     {
/*  61: 89 */       this.previousTotalWorldTime = var1;
/*  62: 91 */       for (int var3 = 0; var3 < this.playerInstanceList.size(); var3++)
/*  63:    */       {
/*  64: 93 */         PlayerInstance var4 = (PlayerInstance)this.playerInstanceList.get(var3);
/*  65: 94 */         var4.sendChunkUpdate();
/*  66: 95 */         var4.processChunk();
/*  67:    */       }
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71:100 */       for (int var3 = 0; var3 < this.chunkWatcherWithPlayers.size(); var3++)
/*  72:    */       {
/*  73:102 */         PlayerInstance var4 = (PlayerInstance)this.chunkWatcherWithPlayers.get(var3);
/*  74:103 */         var4.sendChunkUpdate();
/*  75:    */       }
/*  76:    */     }
/*  77:107 */     this.chunkWatcherWithPlayers.clear();
/*  78:109 */     if (this.players.isEmpty())
/*  79:    */     {
/*  80:111 */       WorldProvider ip = this.theWorldServer.provider;
/*  81:113 */       if (!ip.canRespawnHere()) {
/*  82:115 */         this.theWorldServer.theChunkProviderServer.unloadAllChunks();
/*  83:    */       }
/*  84:    */     }
/*  85:119 */     if (this.playerViewRadius != Config.getChunkViewDistance()) {
/*  86:121 */       setPlayerViewRadius(Config.getChunkViewDistance());
/*  87:    */     }
/*  88:124 */     if (this.chunkCoordsNotLoaded.size() > 0)
/*  89:    */     {
/*  90:126 */       for (int var22 = 0; var22 < this.players.size(); var22++)
/*  91:    */       {
/*  92:128 */         EntityPlayerMP player = (EntityPlayerMP)this.players.get(var22);
/*  93:129 */         int px = player.chunkCoordX;
/*  94:130 */         int pz = player.chunkCoordZ;
/*  95:131 */         int maxRadius = this.playerViewRadius + 1;
/*  96:132 */         int maxRadius2 = maxRadius / 2;
/*  97:133 */         int maxDistSq = maxRadius * maxRadius + maxRadius2 * maxRadius2;
/*  98:134 */         int bestDistSq = maxDistSq;
/*  99:135 */         int bestIndex = -1;
/* 100:136 */         PlayerInstance bestCw = null;
/* 101:137 */         ChunkCoordIntPair bestCoords = null;
/* 102:139 */         for (int chunk = 0; chunk < this.chunkCoordsNotLoaded.size(); chunk++)
/* 103:    */         {
/* 104:141 */           ChunkCoordIntPair coords = (ChunkCoordIntPair)this.chunkCoordsNotLoaded.get(chunk);
/* 105:143 */           if (coords != null)
/* 106:    */           {
/* 107:145 */             PlayerInstance cw = getOrCreateChunkWatcher(coords.chunkXPos, coords.chunkZPos, false);
/* 108:147 */             if ((cw != null) && (!cw.chunkLoaded))
/* 109:    */             {
/* 110:149 */               int dx = px - coords.chunkXPos;
/* 111:150 */               int dz = pz - coords.chunkZPos;
/* 112:151 */               int distSq = dx * dx + dz * dz;
/* 113:153 */               if (distSq < bestDistSq)
/* 114:    */               {
/* 115:155 */                 bestDistSq = distSq;
/* 116:156 */                 bestIndex = chunk;
/* 117:157 */                 bestCw = cw;
/* 118:158 */                 bestCoords = coords;
/* 119:    */               }
/* 120:    */             }
/* 121:    */             else
/* 122:    */             {
/* 123:163 */               this.chunkCoordsNotLoaded.set(chunk, null);
/* 124:    */             }
/* 125:    */           }
/* 126:    */         }
/* 127:168 */         if (bestIndex >= 0) {
/* 128:170 */           this.chunkCoordsNotLoaded.set(bestIndex, null);
/* 129:    */         }
/* 130:173 */         if (bestCw != null)
/* 131:    */         {
/* 132:175 */           bestCw.chunkLoaded = true;
/* 133:176 */           getWorldServer().theChunkProviderServer.loadChunk(bestCoords.chunkXPos, bestCoords.chunkZPos);
/* 134:177 */           bestCw.sendThisChunkToAllPlayers();
/* 135:178 */           break;
/* 136:    */         }
/* 137:    */       }
/* 138:182 */       this.chunkCoordsNotLoaded.compact();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public PlayerInstance getOrCreateChunkWatcher(int par1, int par2, boolean par3)
/* 143:    */   {
/* 144:188 */     return getOrCreateChunkWatcher(par1, par2, par3, false);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public PlayerInstance getOrCreateChunkWatcher(int par1, int par2, boolean par3, boolean lazy)
/* 148:    */   {
/* 149:193 */     long var4 = par1 + 2147483647L | par2 + 2147483647L << 32;
/* 150:194 */     PlayerInstance var6 = (PlayerInstance)this.playerInstances.getValueByKey(var4);
/* 151:196 */     if ((var6 == null) && (par3))
/* 152:    */     {
/* 153:198 */       var6 = new PlayerInstance(par1, par2, lazy);
/* 154:199 */       this.playerInstances.add(var4, var6);
/* 155:200 */       this.playerInstanceList.add(var6);
/* 156:    */     }
/* 157:203 */     return var6;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void func_151250_a(int p_151250_1_, int p_151250_2_, int p_151250_3_)
/* 161:    */   {
/* 162:208 */     int var4 = p_151250_1_ >> 4;
/* 163:209 */     int var5 = p_151250_3_ >> 4;
/* 164:210 */     PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
/* 165:212 */     if (var6 != null) {
/* 166:214 */       var6.func_151253_a(p_151250_1_ & 0xF, p_151250_2_, p_151250_3_ & 0xF);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
/* 171:    */   {
/* 172:223 */     int var2 = (int)par1EntityPlayerMP.posX >> 4;
/* 173:224 */     int var3 = (int)par1EntityPlayerMP.posZ >> 4;
/* 174:225 */     par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
/* 175:226 */     par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
/* 176:227 */     ArrayList spawnList = new ArrayList(1);
/* 177:229 */     for (int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; var4++) {
/* 178:231 */       for (int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; var5++)
/* 179:    */       {
/* 180:233 */         getOrCreateChunkWatcher(var4, var5, true).addPlayer(par1EntityPlayerMP);
/* 181:235 */         if ((var4 >= var2 - 1) && (var4 <= var2 + 1) && (var5 >= var3 - 1) && (var5 <= var3 + 1))
/* 182:    */         {
/* 183:237 */           Chunk spawnChunk = getWorldServer().theChunkProviderServer.loadChunk(var4, var5);
/* 184:238 */           spawnList.add(spawnChunk);
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:243 */     par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(spawnList));
/* 189:244 */     this.players.add(par1EntityPlayerMP);
/* 190:245 */     filterChunkLoadQueue(par1EntityPlayerMP);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void filterChunkLoadQueue(EntityPlayerMP par1EntityPlayerMP)
/* 194:    */   {
/* 195:253 */     ArrayList var2 = new ArrayList(par1EntityPlayerMP.loadedChunks);
/* 196:254 */     int var3 = 0;
/* 197:255 */     int var4 = this.playerViewRadius;
/* 198:256 */     int var5 = (int)par1EntityPlayerMP.posX >> 4;
/* 199:257 */     int var6 = (int)par1EntityPlayerMP.posZ >> 4;
/* 200:258 */     int var7 = 0;
/* 201:259 */     int var8 = 0;
/* 202:260 */     ChunkCoordIntPair var9 = getOrCreateChunkWatcher(var5, var6, true).chunkLocation;
/* 203:261 */     par1EntityPlayerMP.loadedChunks.clear();
/* 204:263 */     if (var2.contains(var9)) {
/* 205:265 */       par1EntityPlayerMP.loadedChunks.add(var9);
/* 206:    */     }
/* 207:270 */     for (int var10 = 1; var10 <= var4 * 2; var10++) {
/* 208:272 */       for (int var11 = 0; var11 < 2; var11++)
/* 209:    */       {
/* 210:274 */         int[] var12 = this.xzDirectionsConst[(var3++ % 4)];
/* 211:276 */         for (int var13 = 0; var13 < var10; var13++)
/* 212:    */         {
/* 213:278 */           var7 += var12[0];
/* 214:279 */           var8 += var12[1];
/* 215:280 */           var9 = getOrCreateChunkWatcher(var5 + var7, var6 + var8, true).chunkLocation;
/* 216:282 */           if (var2.contains(var9)) {
/* 217:284 */             par1EntityPlayerMP.loadedChunks.add(var9);
/* 218:    */           }
/* 219:    */         }
/* 220:    */       }
/* 221:    */     }
/* 222:290 */     var3 %= 4;
/* 223:292 */     for (var10 = 0; var10 < var4 * 2; var10++)
/* 224:    */     {
/* 225:294 */       var7 += this.xzDirectionsConst[var3][0];
/* 226:295 */       var8 += this.xzDirectionsConst[var3][1];
/* 227:296 */       var9 = getOrCreateChunkWatcher(var5 + var7, var6 + var8, true).chunkLocation;
/* 228:298 */       if (var2.contains(var9)) {
/* 229:300 */         par1EntityPlayerMP.loadedChunks.add(var9);
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
/* 235:    */   {
/* 236:310 */     int var2 = (int)par1EntityPlayerMP.managedPosX >> 4;
/* 237:311 */     int var3 = (int)par1EntityPlayerMP.managedPosZ >> 4;
/* 238:313 */     for (int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; var4++) {
/* 239:315 */       for (int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; var5++)
/* 240:    */       {
/* 241:317 */         PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
/* 242:319 */         if (var6 != null) {
/* 243:321 */           var6.removePlayer(par1EntityPlayerMP, false);
/* 244:    */         }
/* 245:    */       }
/* 246:    */     }
/* 247:326 */     this.players.remove(par1EntityPlayerMP);
/* 248:    */   }
/* 249:    */   
/* 250:    */   private boolean overlaps(int par1, int par2, int par3, int par4, int par5)
/* 251:    */   {
/* 252:335 */     int var6 = par1 - par3;
/* 253:336 */     int var7 = par2 - par4;
/* 254:337 */     return (var7 >= -par5) && (var7 <= par5);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void updateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
/* 258:    */   {
/* 259:345 */     int var2 = (int)par1EntityPlayerMP.posX >> 4;
/* 260:346 */     int var3 = (int)par1EntityPlayerMP.posZ >> 4;
/* 261:347 */     double var4 = par1EntityPlayerMP.managedPosX - par1EntityPlayerMP.posX;
/* 262:348 */     double var6 = par1EntityPlayerMP.managedPosZ - par1EntityPlayerMP.posZ;
/* 263:349 */     double var8 = var4 * var4 + var6 * var6;
/* 264:351 */     if (var8 >= 64.0D)
/* 265:    */     {
/* 266:353 */       int var10 = (int)par1EntityPlayerMP.managedPosX >> 4;
/* 267:354 */       int var11 = (int)par1EntityPlayerMP.managedPosZ >> 4;
/* 268:355 */       int var12 = this.playerViewRadius;
/* 269:356 */       int var13 = var2 - var10;
/* 270:357 */       int var14 = var3 - var11;
/* 271:359 */       if ((var13 != 0) || (var14 != 0))
/* 272:    */       {
/* 273:361 */         for (int var15 = var2 - var12; var15 <= var2 + var12; var15++) {
/* 274:363 */           for (int var16 = var3 - var12; var16 <= var3 + var12; var16++)
/* 275:    */           {
/* 276:365 */             if (!overlaps(var15, var16, var10, var11, var12)) {
/* 277:367 */               getOrCreateChunkWatcher(var15, var16, true).addPlayer(par1EntityPlayerMP);
/* 278:    */             }
/* 279:370 */             if (!overlaps(var15 - var13, var16 - var14, var2, var3, var12))
/* 280:    */             {
/* 281:372 */               PlayerInstance var17 = getOrCreateChunkWatcher(var15 - var13, var16 - var14, false);
/* 282:374 */               if (var17 != null) {
/* 283:376 */                 var17.removePlayer(par1EntityPlayerMP);
/* 284:    */               }
/* 285:    */             }
/* 286:    */           }
/* 287:    */         }
/* 288:382 */         filterChunkLoadQueue(par1EntityPlayerMP);
/* 289:383 */         par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
/* 290:384 */         par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isPlayerWatchingChunk(EntityPlayerMP par1EntityPlayerMP, int par2, int par3)
/* 296:    */   {
/* 297:391 */     PlayerInstance var4 = getOrCreateChunkWatcher(par2, par3, false);
/* 298:392 */     return var4 != null;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static int getFurthestViewableBlock(int par0)
/* 302:    */   {
/* 303:400 */     return par0 * 16 - 16;
/* 304:    */   }
/* 305:    */   
/* 306:    */   private void setPlayerViewRadius(int newRadius)
/* 307:    */   {
/* 308:405 */     if (this.playerViewRadius != newRadius)
/* 309:    */     {
/* 310:407 */       EntityPlayerMP[] eps = (EntityPlayerMP[])this.players.toArray(new EntityPlayerMP[this.players.size()]);
/* 311:411 */       for (int i = 0; i < eps.length; i++)
/* 312:    */       {
/* 313:413 */         EntityPlayerMP ep = eps[i];
/* 314:414 */         removePlayer(ep);
/* 315:    */       }
/* 316:417 */       this.playerViewRadius = newRadius;
/* 317:419 */       for (i = 0; i < eps.length; i++)
/* 318:    */       {
/* 319:421 */         EntityPlayerMP ep = eps[i];
/* 320:422 */         addPlayer(ep);
/* 321:    */       }
/* 322:425 */       Config.dbg("ViewRadius: " + this.playerViewRadius + ", for: " + this + " (detect)");
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public class PlayerInstance
/* 327:    */   {
/* 328:    */     private final List playersWatchingChunk;
/* 329:    */     private final ChunkCoordIntPair chunkLocation;
/* 330:    */     private short[] field_151254_d;
/* 331:    */     private int numberOfTilesToUpdate;
/* 332:    */     private int flagsYAreasToUpdate;
/* 333:    */     private long previousWorldTime;
/* 334:    */     public boolean chunkLoaded;
/* 335:    */     private static final String __OBFID = "CL_00001435";
/* 336:    */     
/* 337:    */     public PlayerInstance(int par2, int par3)
/* 338:    */     {
/* 339:442 */       this(par2, par3, false);
/* 340:    */     }
/* 341:    */     
/* 342:    */     public PlayerInstance(int par2, int par3, boolean lazy)
/* 343:    */     {
/* 344:447 */       this.playersWatchingChunk = new ArrayList();
/* 345:448 */       this.field_151254_d = new short[64];
/* 346:449 */       this.chunkLoaded = false;
/* 347:450 */       this.chunkLocation = new ChunkCoordIntPair(par2, par3);
/* 348:451 */       boolean useLazy = (lazy) && (Config.isLazyChunkLoading());
/* 349:453 */       if ((useLazy) && (!PlayerManager.this.getWorldServer().theChunkProviderServer.chunkExists(par2, par3)))
/* 350:    */       {
/* 351:455 */         PlayerManager.this.chunkCoordsNotLoaded.add(this.chunkLocation);
/* 352:456 */         this.chunkLoaded = false;
/* 353:    */       }
/* 354:    */       else
/* 355:    */       {
/* 356:460 */         PlayerManager.this.getWorldServer().theChunkProviderServer.loadChunk(par2, par3);
/* 357:461 */         this.chunkLoaded = true;
/* 358:    */       }
/* 359:    */     }
/* 360:    */     
/* 361:    */     public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
/* 362:    */     {
/* 363:467 */       if (this.playersWatchingChunk.contains(par1EntityPlayerMP)) {
/* 364:469 */         throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + this.chunkLocation.chunkXPos + ", " + this.chunkLocation.chunkZPos);
/* 365:    */       }
/* 366:473 */       if (this.playersWatchingChunk.isEmpty()) {
/* 367:475 */         this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/* 368:    */       }
/* 369:478 */       this.playersWatchingChunk.add(par1EntityPlayerMP);
/* 370:479 */       par1EntityPlayerMP.loadedChunks.add(this.chunkLocation);
/* 371:    */     }
/* 372:    */     
/* 373:    */     public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
/* 374:    */     {
/* 375:485 */       removePlayer(par1EntityPlayerMP, true);
/* 376:    */     }
/* 377:    */     
/* 378:    */     public void removePlayer(EntityPlayerMP par1EntityPlayerMP, boolean sendData)
/* 379:    */     {
/* 380:490 */       if (this.playersWatchingChunk.contains(par1EntityPlayerMP))
/* 381:    */       {
/* 382:492 */         Chunk var2 = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
/* 383:494 */         if ((sendData) && (var2.func_150802_k())) {
/* 384:496 */           par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
/* 385:    */         }
/* 386:499 */         this.playersWatchingChunk.remove(par1EntityPlayerMP);
/* 387:500 */         par1EntityPlayerMP.loadedChunks.remove(this.chunkLocation);
/* 388:502 */         if (Reflector.EventBus.exists()) {
/* 389:504 */           Reflector.postForgeBusEvent(Reflector.ChunkWatchEvent_UnWatch_Constructor, new Object[] { this.chunkLocation, par1EntityPlayerMP });
/* 390:    */         }
/* 391:507 */         if (this.playersWatchingChunk.isEmpty())
/* 392:    */         {
/* 393:509 */           long var3 = this.chunkLocation.chunkXPos + 2147483647L | this.chunkLocation.chunkZPos + 2147483647L << 32;
/* 394:510 */           increaseInhabitedTime(var2);
/* 395:511 */           PlayerManager.this.playerInstances.remove(var3);
/* 396:512 */           PlayerManager.this.playerInstanceList.remove(this);
/* 397:514 */           if (this.numberOfTilesToUpdate > 0) {
/* 398:516 */             PlayerManager.this.chunkWatcherWithPlayers.remove(this);
/* 399:    */           }
/* 400:519 */           if (this.chunkLoaded) {
/* 401:521 */             PlayerManager.this.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
/* 402:    */           }
/* 403:    */         }
/* 404:    */       }
/* 405:    */     }
/* 406:    */     
/* 407:    */     public void processChunk()
/* 408:    */     {
/* 409:529 */       increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos));
/* 410:    */     }
/* 411:    */     
/* 412:    */     private void increaseInhabitedTime(Chunk par1Chunk)
/* 413:    */     {
/* 414:534 */       par1Chunk.inhabitedTime += PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime;
/* 415:535 */       this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/* 416:    */     }
/* 417:    */     
/* 418:    */     public void func_151253_a(int p_151253_1_, int p_151253_2_, int p_151253_3_)
/* 419:    */     {
/* 420:540 */       if (this.numberOfTilesToUpdate == 0) {
/* 421:542 */         PlayerManager.this.chunkWatcherWithPlayers.add(this);
/* 422:    */       }
/* 423:545 */       this.flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);
/* 424:547 */       if (this.numberOfTilesToUpdate < 64)
/* 425:    */       {
/* 426:549 */         short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
/* 427:551 */         for (int var5 = 0; var5 < this.numberOfTilesToUpdate; var5++) {
/* 428:553 */           if (this.field_151254_d[var5] == var4) {
/* 429:555 */             return;
/* 430:    */           }
/* 431:    */         }
/* 432:559 */         this.field_151254_d[(this.numberOfTilesToUpdate++)] = var4;
/* 433:    */       }
/* 434:    */     }
/* 435:    */     
/* 436:    */     public void func_151251_a(Packet p_151251_1_)
/* 437:    */     {
/* 438:565 */       for (int var2 = 0; var2 < this.playersWatchingChunk.size(); var2++)
/* 439:    */       {
/* 440:567 */         EntityPlayerMP var3 = (EntityPlayerMP)this.playersWatchingChunk.get(var2);
/* 441:569 */         if (!var3.loadedChunks.contains(this.chunkLocation)) {
/* 442:571 */           var3.playerNetServerHandler.sendPacket(p_151251_1_);
/* 443:    */         }
/* 444:    */       }
/* 445:    */     }
/* 446:    */     
/* 447:    */     public void sendChunkUpdate()
/* 448:    */     {
/* 449:578 */       if (this.numberOfTilesToUpdate != 0)
/* 450:    */       {
/* 451:584 */         if (this.numberOfTilesToUpdate == 1)
/* 452:    */         {
/* 453:586 */           int var1 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[0] >> 12 & 0xF);
/* 454:587 */           int var2 = this.field_151254_d[0] & 0xFF;
/* 455:588 */           int var3 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[0] >> 8 & 0xF);
/* 456:589 */           func_151251_a(new S23PacketBlockChange(var1, var2, var3, PlayerManager.this.theWorldServer));
/* 457:591 */           if (PlayerManager.this.theWorldServer.getBlock(var1, var2, var3).hasTileEntity()) {
/* 458:593 */             func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var1, var2, var3));
/* 459:    */           }
/* 460:    */         }
/* 461:600 */         else if (this.numberOfTilesToUpdate == 64)
/* 462:    */         {
/* 463:602 */           int var1 = this.chunkLocation.chunkXPos * 16;
/* 464:603 */           int var2 = this.chunkLocation.chunkZPos * 16;
/* 465:604 */           func_151251_a(new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), false, this.flagsYAreasToUpdate));
/* 466:606 */           for (int var3 = 0; var3 < 16; var3++) {
/* 467:608 */             if ((this.flagsYAreasToUpdate & 1 << var3) != 0)
/* 468:    */             {
/* 469:610 */               int var4 = var3 << 4;
/* 470:611 */               List var5 = PlayerManager.this.theWorldServer.func_147486_a(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);
/* 471:613 */               for (int var6 = 0; var6 < var5.size(); var6++) {
/* 472:615 */                 func_151252_a((TileEntity)var5.get(var6));
/* 473:    */               }
/* 474:    */             }
/* 475:    */           }
/* 476:    */         }
/* 477:    */         else
/* 478:    */         {
/* 479:622 */           func_151251_a(new S22PacketMultiBlockChange(this.numberOfTilesToUpdate, this.field_151254_d, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos)));
/* 480:624 */           for (int var1 = 0; var1 < this.numberOfTilesToUpdate; var1++)
/* 481:    */           {
/* 482:626 */             int var2 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[var1] >> 12 & 0xF);
/* 483:627 */             int var3 = this.field_151254_d[var1] & 0xFF;
/* 484:628 */             int var4 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[var1] >> 8 & 0xF);
/* 485:630 */             if (PlayerManager.this.theWorldServer.getBlock(var2, var3, var4).hasTileEntity()) {
/* 486:632 */               func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var2, var3, var4));
/* 487:    */             }
/* 488:    */           }
/* 489:    */         }
/* 490:638 */         this.numberOfTilesToUpdate = 0;
/* 491:639 */         this.flagsYAreasToUpdate = 0;
/* 492:    */       }
/* 493:    */     }
/* 494:    */     
/* 495:    */     private void func_151252_a(TileEntity p_151252_1_)
/* 496:    */     {
/* 497:645 */       if (p_151252_1_ != null)
/* 498:    */       {
/* 499:647 */         Packet var2 = p_151252_1_.getDescriptionPacket();
/* 500:649 */         if (var2 != null) {
/* 501:651 */           func_151251_a(var2);
/* 502:    */         }
/* 503:    */       }
/* 504:    */     }
/* 505:    */     
/* 506:    */     public void sendThisChunkToAllPlayers()
/* 507:    */     {
/* 508:658 */       for (int i = 0; i < this.playersWatchingChunk.size(); i++)
/* 509:    */       {
/* 510:660 */         EntityPlayerMP player = (EntityPlayerMP)this.playersWatchingChunk.get(i);
/* 511:661 */         Chunk chunk = PlayerManager.this.getWorldServer().getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
/* 512:662 */         ArrayList list = new ArrayList(1);
/* 513:663 */         list.add(chunk);
/* 514:664 */         player.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(list));
/* 515:    */       }
/* 516:    */     }
/* 517:    */   }
/* 518:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.PlayerManager
 * JD-Core Version:    0.7.0.1
 */