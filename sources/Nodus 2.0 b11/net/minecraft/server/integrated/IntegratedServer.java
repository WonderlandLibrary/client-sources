/*   1:    */ package net.minecraft.server.integrated;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.concurrent.Callable;
/*   6:    */ import net.minecraft.client.ClientBrandRetriever;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*   9:    */ import net.minecraft.client.settings.GameSettings;
/*  10:    */ import net.minecraft.crash.CrashReport;
/*  11:    */ import net.minecraft.crash.CrashReportCategory;
/*  12:    */ import net.minecraft.network.NetworkSystem;
/*  13:    */ import net.minecraft.profiler.PlayerUsageSnooper;
/*  14:    */ import net.minecraft.server.MinecraftServer;
/*  15:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  16:    */ import net.minecraft.src.Reflector;
/*  17:    */ import net.minecraft.src.ReflectorClass;
/*  18:    */ import net.minecraft.src.ReflectorMethod;
/*  19:    */ import net.minecraft.src.WorldServerOF;
/*  20:    */ import net.minecraft.util.CryptManager;
/*  21:    */ import net.minecraft.util.HttpUtil;
/*  22:    */ import net.minecraft.util.Session;
/*  23:    */ import net.minecraft.world.EnumDifficulty;
/*  24:    */ import net.minecraft.world.WorldManager;
/*  25:    */ import net.minecraft.world.WorldServer;
/*  26:    */ import net.minecraft.world.WorldServerMulti;
/*  27:    */ import net.minecraft.world.WorldSettings;
/*  28:    */ import net.minecraft.world.WorldSettings.GameType;
/*  29:    */ import net.minecraft.world.WorldType;
/*  30:    */ import net.minecraft.world.demo.DemoWorldServer;
/*  31:    */ import net.minecraft.world.storage.ISaveFormat;
/*  32:    */ import net.minecraft.world.storage.ISaveHandler;
/*  33:    */ import net.minecraft.world.storage.WorldInfo;
/*  34:    */ import org.apache.logging.log4j.LogManager;
/*  35:    */ import org.apache.logging.log4j.Logger;
/*  36:    */ 
/*  37:    */ public class IntegratedServer
/*  38:    */   extends MinecraftServer
/*  39:    */ {
/*  40: 30 */   private static final Logger logger = ;
/*  41:    */   private final Minecraft mc;
/*  42:    */   private final WorldSettings theWorldSettings;
/*  43:    */   private boolean isGamePaused;
/*  44:    */   private boolean isPublic;
/*  45:    */   private ThreadLanServerPing lanServerPing;
/*  46:    */   private static final String __OBFID = "CL_00001129";
/*  47:    */   
/*  48:    */   public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings)
/*  49:    */   {
/*  50: 42 */     super(new File(par1Minecraft.mcDataDir, "saves"), par1Minecraft.getProxy());
/*  51: 43 */     setServerOwner(par1Minecraft.getSession().getUsername());
/*  52: 44 */     setFolderName(par2Str);
/*  53: 45 */     setWorldName(par3Str);
/*  54: 46 */     setDemo(par1Minecraft.isDemo());
/*  55: 47 */     canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
/*  56: 48 */     setBuildLimit(256);
/*  57: 49 */     setConfigurationManager(new IntegratedPlayerList(this));
/*  58: 50 */     this.mc = par1Minecraft;
/*  59: 51 */     this.theWorldSettings = par4WorldSettings;
/*  60: 52 */     Reflector.callVoid(Reflector.ModLoader_registerServer, new Object[] { this });
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str)
/*  64:    */   {
/*  65: 57 */     convertMapIfNeeded(par1Str);
/*  66: 58 */     ISaveHandler var7 = getActiveAnvilConverter().getSaveLoader(par1Str, true);
/*  67: 60 */     if (Reflector.DimensionManager.exists())
/*  68:    */     {
/*  69: 62 */       Object var8 = isDemo() ? new DemoWorldServer(this, var7, par2Str, 0, this.theProfiler) : new WorldServerOF(this, var7, par2Str, 0, this.theWorldSettings, this.theProfiler);
/*  70: 63 */       Integer[] var9 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/*  71: 64 */       Integer[] arr$ = var9;
/*  72: 65 */       int len$ = var9.length;
/*  73: 67 */       for (int i$ = 0; i$ < len$; i$++)
/*  74:    */       {
/*  75: 69 */         int dim = arr$[i$].intValue();
/*  76: 70 */         Object world = dim == 0 ? var8 : new WorldServerMulti(this, var7, par2Str, dim, this.theWorldSettings, (WorldServer)var8, this.theProfiler);
/*  77: 71 */         ((WorldServer)world).addWorldAccess(new WorldManager(this, (WorldServer)world));
/*  78: 73 */         if (!isSinglePlayer()) {
/*  79: 75 */           ((WorldServer)world).getWorldInfo().setGameType(getGameType());
/*  80:    */         }
/*  81: 78 */         if (Reflector.EventBus.exists()) {
/*  82: 80 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { world });
/*  83:    */         }
/*  84:    */       }
/*  85: 84 */       getConfigurationManager().setPlayerManager(new WorldServer[] { (WorldServer)var8 });
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 88 */       this.worldServers = new WorldServer[3];
/*  90: 89 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  91: 91 */       for (int var15 = 0; var15 < this.worldServers.length; var15++)
/*  92:    */       {
/*  93: 93 */         byte var16 = 0;
/*  94: 95 */         if (var15 == 1) {
/*  95: 97 */           var16 = -1;
/*  96:    */         }
/*  97:100 */         if (var15 == 2) {
/*  98:102 */           var16 = 1;
/*  99:    */         }
/* 100:105 */         if (var15 == 0)
/* 101:    */         {
/* 102:107 */           if (isDemo()) {
/* 103:109 */             this.worldServers[var15] = new DemoWorldServer(this, var7, par2Str, var16, this.theProfiler);
/* 104:    */           } else {
/* 105:113 */             this.worldServers[var15] = new WorldServerOF(this, var7, par2Str, var16, this.theWorldSettings, this.theProfiler);
/* 106:    */           }
/* 107:    */         }
/* 108:    */         else {
/* 109:118 */           this.worldServers[var15] = new WorldServerMulti(this, var7, par2Str, var16, this.theWorldSettings, this.worldServers[0], this.theProfiler);
/* 110:    */         }
/* 111:121 */         this.worldServers[var15].addWorldAccess(new WorldManager(this, this.worldServers[var15]));
/* 112:122 */         getConfigurationManager().setPlayerManager(this.worldServers);
/* 113:    */       }
/* 114:    */     }
/* 115:126 */     func_147139_a(func_147135_j());
/* 116:127 */     initialWorldChunkLoad();
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected boolean startServer()
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:135 */     logger.info("Starting integrated minecraft server version 1.7.2");
/* 123:136 */     setOnlineMode(false);
/* 124:137 */     setCanSpawnAnimals(true);
/* 125:138 */     setCanSpawnNPCs(true);
/* 126:139 */     setAllowPvp(true);
/* 127:140 */     setAllowFlight(true);
/* 128:141 */     logger.info("Generating keypair");
/* 129:142 */     setKeyPair(CryptManager.createNewKeyPair());
/* 130:145 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists())
/* 131:    */     {
/* 132:147 */       Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 133:149 */       if (!Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this })) {
/* 134:151 */         return false;
/* 135:    */       }
/* 136:    */     }
/* 137:155 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.func_82749_j());
/* 138:156 */     setMOTD(getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/* 139:158 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists())
/* 140:    */     {
/* 141:160 */       Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 142:162 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
/* 143:164 */         return Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/* 144:    */       }
/* 145:167 */       Reflector.callVoid(inst, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/* 146:    */     }
/* 147:170 */     return true;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void tick()
/* 151:    */   {
/* 152:178 */     boolean var1 = this.isGamePaused;
/* 153:179 */     this.isGamePaused = ((Minecraft.getMinecraft().getNetHandler() != null) && (Minecraft.getMinecraft().func_147113_T()));
/* 154:181 */     if ((!var1) && (this.isGamePaused))
/* 155:    */     {
/* 156:183 */       logger.info("Saving and pausing game...");
/* 157:184 */       getConfigurationManager().saveAllPlayerData();
/* 158:185 */       saveAllWorlds(false);
/* 159:    */     }
/* 160:188 */     if (!this.isGamePaused) {
/* 161:190 */       super.tick();
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean canStructuresSpawn()
/* 166:    */   {
/* 167:196 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public WorldSettings.GameType getGameType()
/* 171:    */   {
/* 172:201 */     return this.theWorldSettings.getGameType();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public EnumDifficulty func_147135_j()
/* 176:    */   {
/* 177:206 */     return this.mc.gameSettings.difficulty;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isHardcore()
/* 181:    */   {
/* 182:214 */     return this.theWorldSettings.getHardcoreEnabled();
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected File getDataDirectory()
/* 186:    */   {
/* 187:219 */     return this.mc.mcDataDir;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isDedicatedServer()
/* 191:    */   {
/* 192:224 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected void finalTick(CrashReport par1CrashReport)
/* 196:    */   {
/* 197:232 */     this.mc.crashed(par1CrashReport);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
/* 201:    */   {
/* 202:240 */     par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
/* 203:241 */     par1CrashReport.getCategory().addCrashSectionCallable("Type", new Callable()
/* 204:    */     {
/* 205:    */       private static final String __OBFID = "CL_00001130";
/* 206:    */       
/* 207:    */       public String call()
/* 208:    */       {
/* 209:246 */         return "Integrated Server (map_client.txt)";
/* 210:    */       }
/* 211:248 */     });
/* 212:249 */     par1CrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable()
/* 213:    */     {
/* 214:    */       private static final String __OBFID = "CL_00001131";
/* 215:    */       
/* 216:    */       public String call()
/* 217:    */       {
/* 218:254 */         String var1 = ClientBrandRetriever.getClientModName();
/* 219:256 */         if (!var1.equals("vanilla")) {
/* 220:258 */           return "Definitely; Client brand changed to '" + var1 + "'";
/* 221:    */         }
/* 222:262 */         var1 = IntegratedServer.this.getServerModName();
/* 223:263 */         return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : !var1.equals("vanilla") ? "Definitely; Server brand changed to '" + var1 + "'" : "Probably not. Jar signature remains and both client + server brands are untouched.";
/* 224:    */       }
/* 225:266 */     });
/* 226:267 */     return par1CrashReport;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
/* 230:    */   {
/* 231:272 */     super.addServerStatsToSnooper(par1PlayerUsageSnooper);
/* 232:273 */     par1PlayerUsageSnooper.addData("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/* 233:    */   }
/* 234:    */   
/* 235:    */   public boolean isSnooperEnabled()
/* 236:    */   {
/* 237:281 */     return Minecraft.getMinecraft().isSnooperEnabled();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String shareToLAN(WorldSettings.GameType par1EnumGameType, boolean par2)
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:291 */       int var6 = -1;
/* 245:    */       try
/* 246:    */       {
/* 247:295 */         var6 = HttpUtil.func_76181_a();
/* 248:    */       }
/* 249:    */       catch (IOException localIOException1) {}
/* 250:302 */       if (var6 <= 0) {
/* 251:304 */         var6 = 25564;
/* 252:    */       }
/* 253:307 */       func_147137_ag().addLanEndpoint(null, var6);
/* 254:308 */       logger.info("Started on " + var6);
/* 255:309 */       this.isPublic = true;
/* 256:310 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), var6);
/* 257:311 */       this.lanServerPing.start();
/* 258:312 */       getConfigurationManager().setGameType(par1EnumGameType);
/* 259:313 */       getConfigurationManager().setCommandsAllowedForAll(par2);
/* 260:314 */       return var6;
/* 261:    */     }
/* 262:    */     catch (IOException var61) {}
/* 263:318 */     return null;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void stopServer()
/* 267:    */   {
/* 268:327 */     super.stopServer();
/* 269:329 */     if (this.lanServerPing != null)
/* 270:    */     {
/* 271:331 */       this.lanServerPing.interrupt();
/* 272:332 */       this.lanServerPing = null;
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void initiateShutdown()
/* 277:    */   {
/* 278:341 */     super.initiateShutdown();
/* 279:343 */     if (this.lanServerPing != null)
/* 280:    */     {
/* 281:345 */       this.lanServerPing.interrupt();
/* 282:346 */       this.lanServerPing = null;
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public boolean getPublic()
/* 287:    */   {
/* 288:355 */     return this.isPublic;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/* 292:    */   {
/* 293:363 */     getConfigurationManager().setGameType(par1EnumGameType);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean isCommandBlockEnabled()
/* 297:    */   {
/* 298:371 */     return true;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int func_110455_j()
/* 302:    */   {
/* 303:376 */     return 4;
/* 304:    */   }
/* 305:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.integrated.IntegratedServer
 * JD-Core Version:    0.7.0.1
 */