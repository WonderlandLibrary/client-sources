/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.Callable;
/*   4:    */ import net.minecraft.crash.CrashReportCategory;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.server.MinecraftServer;
/*   7:    */ import net.minecraft.world.GameRules;
/*   8:    */ import net.minecraft.world.WorldSettings;
/*   9:    */ import net.minecraft.world.WorldSettings.GameType;
/*  10:    */ import net.minecraft.world.WorldType;
/*  11:    */ 
/*  12:    */ public class WorldInfo
/*  13:    */ {
/*  14:    */   private long randomSeed;
/*  15:    */   private WorldType terrainType;
/*  16:    */   private String generatorOptions;
/*  17:    */   private int spawnX;
/*  18:    */   private int spawnY;
/*  19:    */   private int spawnZ;
/*  20:    */   private long totalTime;
/*  21:    */   private long worldTime;
/*  22:    */   private long lastTimePlayed;
/*  23:    */   private long sizeOnDisk;
/*  24:    */   private NBTTagCompound playerTag;
/*  25:    */   private int dimension;
/*  26:    */   private String levelName;
/*  27:    */   private int saveVersion;
/*  28:    */   private boolean raining;
/*  29:    */   private int rainTime;
/*  30:    */   private boolean thundering;
/*  31:    */   private int thunderTime;
/*  32:    */   private WorldSettings.GameType theGameType;
/*  33:    */   private boolean mapFeaturesEnabled;
/*  34:    */   private boolean hardcore;
/*  35:    */   private boolean allowCommands;
/*  36:    */   private boolean initialized;
/*  37:    */   private GameRules theGameRules;
/*  38:    */   private static final String __OBFID = "CL_00000587";
/*  39:    */   
/*  40:    */   protected WorldInfo()
/*  41:    */   {
/*  42: 76 */     this.terrainType = WorldType.DEFAULT;
/*  43: 77 */     this.generatorOptions = "";
/*  44: 78 */     this.theGameRules = new GameRules();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public WorldInfo(NBTTagCompound par1NBTTagCompound)
/*  48:    */   {
/*  49: 83 */     this.terrainType = WorldType.DEFAULT;
/*  50: 84 */     this.generatorOptions = "";
/*  51: 85 */     this.theGameRules = new GameRules();
/*  52: 86 */     this.randomSeed = par1NBTTagCompound.getLong("RandomSeed");
/*  53: 88 */     if (par1NBTTagCompound.func_150297_b("generatorName", 8))
/*  54:    */     {
/*  55: 90 */       String var2 = par1NBTTagCompound.getString("generatorName");
/*  56: 91 */       this.terrainType = WorldType.parseWorldType(var2);
/*  57: 93 */       if (this.terrainType == null)
/*  58:    */       {
/*  59: 95 */         this.terrainType = WorldType.DEFAULT;
/*  60:    */       }
/*  61: 97 */       else if (this.terrainType.isVersioned())
/*  62:    */       {
/*  63: 99 */         int var3 = 0;
/*  64:101 */         if (par1NBTTagCompound.func_150297_b("generatorVersion", 99)) {
/*  65:103 */           var3 = par1NBTTagCompound.getInteger("generatorVersion");
/*  66:    */         }
/*  67:106 */         this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(var3);
/*  68:    */       }
/*  69:109 */       if (par1NBTTagCompound.func_150297_b("generatorOptions", 8)) {
/*  70:111 */         this.generatorOptions = par1NBTTagCompound.getString("generatorOptions");
/*  71:    */       }
/*  72:    */     }
/*  73:115 */     this.theGameType = WorldSettings.GameType.getByID(par1NBTTagCompound.getInteger("GameType"));
/*  74:117 */     if (par1NBTTagCompound.func_150297_b("MapFeatures", 99)) {
/*  75:119 */       this.mapFeaturesEnabled = par1NBTTagCompound.getBoolean("MapFeatures");
/*  76:    */     } else {
/*  77:123 */       this.mapFeaturesEnabled = true;
/*  78:    */     }
/*  79:126 */     this.spawnX = par1NBTTagCompound.getInteger("SpawnX");
/*  80:127 */     this.spawnY = par1NBTTagCompound.getInteger("SpawnY");
/*  81:128 */     this.spawnZ = par1NBTTagCompound.getInteger("SpawnZ");
/*  82:129 */     this.totalTime = par1NBTTagCompound.getLong("Time");
/*  83:131 */     if (par1NBTTagCompound.func_150297_b("DayTime", 99)) {
/*  84:133 */       this.worldTime = par1NBTTagCompound.getLong("DayTime");
/*  85:    */     } else {
/*  86:137 */       this.worldTime = this.totalTime;
/*  87:    */     }
/*  88:140 */     this.lastTimePlayed = par1NBTTagCompound.getLong("LastPlayed");
/*  89:141 */     this.sizeOnDisk = par1NBTTagCompound.getLong("SizeOnDisk");
/*  90:142 */     this.levelName = par1NBTTagCompound.getString("LevelName");
/*  91:143 */     this.saveVersion = par1NBTTagCompound.getInteger("version");
/*  92:144 */     this.rainTime = par1NBTTagCompound.getInteger("rainTime");
/*  93:145 */     this.raining = par1NBTTagCompound.getBoolean("raining");
/*  94:146 */     this.thunderTime = par1NBTTagCompound.getInteger("thunderTime");
/*  95:147 */     this.thundering = par1NBTTagCompound.getBoolean("thundering");
/*  96:148 */     this.hardcore = par1NBTTagCompound.getBoolean("hardcore");
/*  97:150 */     if (par1NBTTagCompound.func_150297_b("initialized", 99)) {
/*  98:152 */       this.initialized = par1NBTTagCompound.getBoolean("initialized");
/*  99:    */     } else {
/* 100:156 */       this.initialized = true;
/* 101:    */     }
/* 102:159 */     if (par1NBTTagCompound.func_150297_b("allowCommands", 99)) {
/* 103:161 */       this.allowCommands = par1NBTTagCompound.getBoolean("allowCommands");
/* 104:    */     } else {
/* 105:165 */       this.allowCommands = (this.theGameType == WorldSettings.GameType.CREATIVE);
/* 106:    */     }
/* 107:168 */     if (par1NBTTagCompound.func_150297_b("Player", 10))
/* 108:    */     {
/* 109:170 */       this.playerTag = par1NBTTagCompound.getCompoundTag("Player");
/* 110:171 */       this.dimension = this.playerTag.getInteger("Dimension");
/* 111:    */     }
/* 112:174 */     if (par1NBTTagCompound.func_150297_b("GameRules", 10)) {
/* 113:176 */       this.theGameRules.readGameRulesFromNBT(par1NBTTagCompound.getCompoundTag("GameRules"));
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public WorldInfo(WorldSettings par1WorldSettings, String par2Str)
/* 118:    */   {
/* 119:182 */     this.terrainType = WorldType.DEFAULT;
/* 120:183 */     this.generatorOptions = "";
/* 121:184 */     this.theGameRules = new GameRules();
/* 122:185 */     this.randomSeed = par1WorldSettings.getSeed();
/* 123:186 */     this.theGameType = par1WorldSettings.getGameType();
/* 124:187 */     this.mapFeaturesEnabled = par1WorldSettings.isMapFeaturesEnabled();
/* 125:188 */     this.levelName = par2Str;
/* 126:189 */     this.hardcore = par1WorldSettings.getHardcoreEnabled();
/* 127:190 */     this.terrainType = par1WorldSettings.getTerrainType();
/* 128:191 */     this.generatorOptions = par1WorldSettings.func_82749_j();
/* 129:192 */     this.allowCommands = par1WorldSettings.areCommandsAllowed();
/* 130:193 */     this.initialized = false;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public WorldInfo(WorldInfo par1WorldInfo)
/* 134:    */   {
/* 135:198 */     this.terrainType = WorldType.DEFAULT;
/* 136:199 */     this.generatorOptions = "";
/* 137:200 */     this.theGameRules = new GameRules();
/* 138:201 */     this.randomSeed = par1WorldInfo.randomSeed;
/* 139:202 */     this.terrainType = par1WorldInfo.terrainType;
/* 140:203 */     this.generatorOptions = par1WorldInfo.generatorOptions;
/* 141:204 */     this.theGameType = par1WorldInfo.theGameType;
/* 142:205 */     this.mapFeaturesEnabled = par1WorldInfo.mapFeaturesEnabled;
/* 143:206 */     this.spawnX = par1WorldInfo.spawnX;
/* 144:207 */     this.spawnY = par1WorldInfo.spawnY;
/* 145:208 */     this.spawnZ = par1WorldInfo.spawnZ;
/* 146:209 */     this.totalTime = par1WorldInfo.totalTime;
/* 147:210 */     this.worldTime = par1WorldInfo.worldTime;
/* 148:211 */     this.lastTimePlayed = par1WorldInfo.lastTimePlayed;
/* 149:212 */     this.sizeOnDisk = par1WorldInfo.sizeOnDisk;
/* 150:213 */     this.playerTag = par1WorldInfo.playerTag;
/* 151:214 */     this.dimension = par1WorldInfo.dimension;
/* 152:215 */     this.levelName = par1WorldInfo.levelName;
/* 153:216 */     this.saveVersion = par1WorldInfo.saveVersion;
/* 154:217 */     this.rainTime = par1WorldInfo.rainTime;
/* 155:218 */     this.raining = par1WorldInfo.raining;
/* 156:219 */     this.thunderTime = par1WorldInfo.thunderTime;
/* 157:220 */     this.thundering = par1WorldInfo.thundering;
/* 158:221 */     this.hardcore = par1WorldInfo.hardcore;
/* 159:222 */     this.allowCommands = par1WorldInfo.allowCommands;
/* 160:223 */     this.initialized = par1WorldInfo.initialized;
/* 161:224 */     this.theGameRules = par1WorldInfo.theGameRules;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public NBTTagCompound getNBTTagCompound()
/* 165:    */   {
/* 166:232 */     NBTTagCompound var1 = new NBTTagCompound();
/* 167:233 */     updateTagCompound(var1, this.playerTag);
/* 168:234 */     return var1;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound)
/* 172:    */   {
/* 173:242 */     NBTTagCompound var2 = new NBTTagCompound();
/* 174:243 */     updateTagCompound(var2, par1NBTTagCompound);
/* 175:244 */     return var2;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void updateTagCompound(NBTTagCompound par1NBTTagCompound, NBTTagCompound par2NBTTagCompound)
/* 179:    */   {
/* 180:249 */     par1NBTTagCompound.setLong("RandomSeed", this.randomSeed);
/* 181:250 */     par1NBTTagCompound.setString("generatorName", this.terrainType.getWorldTypeName());
/* 182:251 */     par1NBTTagCompound.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
/* 183:252 */     par1NBTTagCompound.setString("generatorOptions", this.generatorOptions);
/* 184:253 */     par1NBTTagCompound.setInteger("GameType", this.theGameType.getID());
/* 185:254 */     par1NBTTagCompound.setBoolean("MapFeatures", this.mapFeaturesEnabled);
/* 186:255 */     par1NBTTagCompound.setInteger("SpawnX", this.spawnX);
/* 187:256 */     par1NBTTagCompound.setInteger("SpawnY", this.spawnY);
/* 188:257 */     par1NBTTagCompound.setInteger("SpawnZ", this.spawnZ);
/* 189:258 */     par1NBTTagCompound.setLong("Time", this.totalTime);
/* 190:259 */     par1NBTTagCompound.setLong("DayTime", this.worldTime);
/* 191:260 */     par1NBTTagCompound.setLong("SizeOnDisk", this.sizeOnDisk);
/* 192:261 */     par1NBTTagCompound.setLong("LastPlayed", MinecraftServer.getSystemTimeMillis());
/* 193:262 */     par1NBTTagCompound.setString("LevelName", this.levelName);
/* 194:263 */     par1NBTTagCompound.setInteger("version", this.saveVersion);
/* 195:264 */     par1NBTTagCompound.setInteger("rainTime", this.rainTime);
/* 196:265 */     par1NBTTagCompound.setBoolean("raining", this.raining);
/* 197:266 */     par1NBTTagCompound.setInteger("thunderTime", this.thunderTime);
/* 198:267 */     par1NBTTagCompound.setBoolean("thundering", this.thundering);
/* 199:268 */     par1NBTTagCompound.setBoolean("hardcore", this.hardcore);
/* 200:269 */     par1NBTTagCompound.setBoolean("allowCommands", this.allowCommands);
/* 201:270 */     par1NBTTagCompound.setBoolean("initialized", this.initialized);
/* 202:271 */     par1NBTTagCompound.setTag("GameRules", this.theGameRules.writeGameRulesToNBT());
/* 203:273 */     if (par2NBTTagCompound != null) {
/* 204:275 */       par1NBTTagCompound.setTag("Player", par2NBTTagCompound);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public long getSeed()
/* 209:    */   {
/* 210:284 */     return this.randomSeed;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getSpawnX()
/* 214:    */   {
/* 215:292 */     return this.spawnX;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getSpawnY()
/* 219:    */   {
/* 220:300 */     return this.spawnY;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getSpawnZ()
/* 224:    */   {
/* 225:308 */     return this.spawnZ;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public long getWorldTotalTime()
/* 229:    */   {
/* 230:313 */     return this.totalTime;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public long getWorldTime()
/* 234:    */   {
/* 235:321 */     return this.worldTime;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public long getSizeOnDisk()
/* 239:    */   {
/* 240:326 */     return this.sizeOnDisk;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public NBTTagCompound getPlayerNBTTagCompound()
/* 244:    */   {
/* 245:334 */     return this.playerTag;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public int getVanillaDimension()
/* 249:    */   {
/* 250:343 */     return this.dimension;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setSpawnX(int par1)
/* 254:    */   {
/* 255:351 */     this.spawnX = par1;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setSpawnY(int par1)
/* 259:    */   {
/* 260:359 */     this.spawnY = par1;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setSpawnZ(int par1)
/* 264:    */   {
/* 265:367 */     this.spawnZ = par1;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void incrementTotalWorldTime(long par1)
/* 269:    */   {
/* 270:372 */     this.totalTime = par1;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setWorldTime(long par1)
/* 274:    */   {
/* 275:380 */     this.worldTime = par1;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setSpawnPosition(int par1, int par2, int par3)
/* 279:    */   {
/* 280:388 */     this.spawnX = par1;
/* 281:389 */     this.spawnY = par2;
/* 282:390 */     this.spawnZ = par3;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getWorldName()
/* 286:    */   {
/* 287:398 */     return this.levelName;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setWorldName(String par1Str)
/* 291:    */   {
/* 292:403 */     this.levelName = par1Str;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public int getSaveVersion()
/* 296:    */   {
/* 297:411 */     return this.saveVersion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setSaveVersion(int par1)
/* 301:    */   {
/* 302:419 */     this.saveVersion = par1;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public long getLastTimePlayed()
/* 306:    */   {
/* 307:427 */     return this.lastTimePlayed;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean isThundering()
/* 311:    */   {
/* 312:435 */     return this.thundering;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setThundering(boolean par1)
/* 316:    */   {
/* 317:443 */     this.thundering = par1;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int getThunderTime()
/* 321:    */   {
/* 322:451 */     return this.thunderTime;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setThunderTime(int par1)
/* 326:    */   {
/* 327:459 */     this.thunderTime = par1;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean isRaining()
/* 331:    */   {
/* 332:467 */     return this.raining;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setRaining(boolean par1)
/* 336:    */   {
/* 337:475 */     this.raining = par1;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public int getRainTime()
/* 341:    */   {
/* 342:483 */     return this.rainTime;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setRainTime(int par1)
/* 346:    */   {
/* 347:491 */     this.rainTime = par1;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public WorldSettings.GameType getGameType()
/* 351:    */   {
/* 352:499 */     return this.theGameType;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public boolean isMapFeaturesEnabled()
/* 356:    */   {
/* 357:507 */     return this.mapFeaturesEnabled;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/* 361:    */   {
/* 362:515 */     this.theGameType = par1EnumGameType;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public boolean isHardcoreModeEnabled()
/* 366:    */   {
/* 367:523 */     return this.hardcore;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public WorldType getTerrainType()
/* 371:    */   {
/* 372:528 */     return this.terrainType;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setTerrainType(WorldType par1WorldType)
/* 376:    */   {
/* 377:533 */     this.terrainType = par1WorldType;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public String getGeneratorOptions()
/* 381:    */   {
/* 382:538 */     return this.generatorOptions;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public boolean areCommandsAllowed()
/* 386:    */   {
/* 387:546 */     return this.allowCommands;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public boolean isInitialized()
/* 391:    */   {
/* 392:554 */     return this.initialized;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setServerInitialized(boolean par1)
/* 396:    */   {
/* 397:562 */     this.initialized = par1;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public GameRules getGameRulesInstance()
/* 401:    */   {
/* 402:570 */     return this.theGameRules;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void addToCrashReport(CrashReportCategory par1CrashReportCategory)
/* 406:    */   {
/* 407:578 */     par1CrashReportCategory.addCrashSectionCallable("Level seed", new Callable()
/* 408:    */     {
/* 409:    */       private static final String __OBFID = "CL_00000588";
/* 410:    */       
/* 411:    */       public String call()
/* 412:    */       {
/* 413:583 */         return String.valueOf(WorldInfo.this.getSeed());
/* 414:    */       }
/* 415:585 */     });
/* 416:586 */     par1CrashReportCategory.addCrashSectionCallable("Level generator", new Callable()
/* 417:    */     {
/* 418:    */       private static final String __OBFID = "CL_00000589";
/* 419:    */       
/* 420:    */       public String call()
/* 421:    */       {
/* 422:591 */         return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(WorldInfo.this.terrainType.getWorldTypeID()), WorldInfo.this.terrainType.getWorldTypeName(), Integer.valueOf(WorldInfo.this.terrainType.getGeneratorVersion()), Boolean.valueOf(WorldInfo.this.mapFeaturesEnabled) });
/* 423:    */       }
/* 424:593 */     });
/* 425:594 */     par1CrashReportCategory.addCrashSectionCallable("Level generator options", new Callable()
/* 426:    */     {
/* 427:    */       private static final String __OBFID = "CL_00000590";
/* 428:    */       
/* 429:    */       public String call()
/* 430:    */       {
/* 431:599 */         return WorldInfo.this.generatorOptions;
/* 432:    */       }
/* 433:601 */     });
/* 434:602 */     par1CrashReportCategory.addCrashSectionCallable("Level spawn location", new Callable()
/* 435:    */     {
/* 436:    */       private static final String __OBFID = "CL_00000591";
/* 437:    */       
/* 438:    */       public String call()
/* 439:    */       {
/* 440:607 */         return CrashReportCategory.getLocationInfo(WorldInfo.this.spawnX, WorldInfo.this.spawnY, WorldInfo.this.spawnZ);
/* 441:    */       }
/* 442:609 */     });
/* 443:610 */     par1CrashReportCategory.addCrashSectionCallable("Level time", new Callable()
/* 444:    */     {
/* 445:    */       private static final String __OBFID = "CL_00000592";
/* 446:    */       
/* 447:    */       public String call()
/* 448:    */       {
/* 449:615 */         return String.format("%d game time, %d day time", new Object[] { Long.valueOf(WorldInfo.this.totalTime), Long.valueOf(WorldInfo.this.worldTime) });
/* 450:    */       }
/* 451:617 */     });
/* 452:618 */     par1CrashReportCategory.addCrashSectionCallable("Level dimension", new Callable()
/* 453:    */     {
/* 454:    */       private static final String __OBFID = "CL_00000593";
/* 455:    */       
/* 456:    */       public String call()
/* 457:    */       {
/* 458:623 */         return String.valueOf(WorldInfo.this.dimension);
/* 459:    */       }
/* 460:625 */     });
/* 461:626 */     par1CrashReportCategory.addCrashSectionCallable("Level storage version", new Callable()
/* 462:    */     {
/* 463:    */       private static final String __OBFID = "CL_00000594";
/* 464:    */       
/* 465:    */       public String call()
/* 466:    */       {
/* 467:631 */         String var1 = "Unknown?";
/* 468:    */         try
/* 469:    */         {
/* 470:635 */           switch (WorldInfo.this.saveVersion)
/* 471:    */           {
/* 472:    */           case 19132: 
/* 473:638 */             var1 = "McRegion";
/* 474:639 */             break;
/* 475:    */           case 19133: 
/* 476:642 */             var1 = "Anvil";
/* 477:    */           }
/* 478:    */         }
/* 479:    */         catch (Throwable localThrowable) {}
/* 480:650 */         return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldInfo.this.saveVersion), var1 });
/* 481:    */       }
/* 482:652 */     });
/* 483:653 */     par1CrashReportCategory.addCrashSectionCallable("Level weather", new Callable()
/* 484:    */     {
/* 485:    */       private static final String __OBFID = "CL_00000595";
/* 486:    */       
/* 487:    */       public String call()
/* 488:    */       {
/* 489:658 */         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(WorldInfo.this.rainTime), Boolean.valueOf(WorldInfo.this.raining), Integer.valueOf(WorldInfo.this.thunderTime), Boolean.valueOf(WorldInfo.this.thundering) });
/* 490:    */       }
/* 491:660 */     });
/* 492:661 */     par1CrashReportCategory.addCrashSectionCallable("Level game mode", new Callable()
/* 493:    */     {
/* 494:    */       private static final String __OBFID = "CL_00000597";
/* 495:    */       
/* 496:    */       public String call()
/* 497:    */       {
/* 498:666 */         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldInfo.this.theGameType.getName(), Integer.valueOf(WorldInfo.this.theGameType.getID()), Boolean.valueOf(WorldInfo.this.hardcore), Boolean.valueOf(WorldInfo.this.allowCommands) });
/* 499:    */       }
/* 500:    */     });
/* 501:    */   }
/* 502:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.WorldInfo
 * JD-Core Version:    0.7.0.1
 */