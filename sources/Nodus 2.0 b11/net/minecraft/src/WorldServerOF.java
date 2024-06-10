/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.TreeSet;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.monster.EntityMob;
/*  14:    */ import net.minecraft.init.Blocks;
/*  15:    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*  16:    */ import net.minecraft.profiler.Profiler;
/*  17:    */ import net.minecraft.server.MinecraftServer;
/*  18:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  19:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  20:    */ import net.minecraft.world.NextTickListEntry;
/*  21:    */ import net.minecraft.world.WorldServer;
/*  22:    */ import net.minecraft.world.WorldSettings;
/*  23:    */ import net.minecraft.world.WorldSettings.GameType;
/*  24:    */ import net.minecraft.world.chunk.Chunk;
/*  25:    */ import net.minecraft.world.chunk.EmptyChunk;
/*  26:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  27:    */ import net.minecraft.world.chunk.NibbleArray;
/*  28:    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*  29:    */ import net.minecraft.world.storage.ISaveHandler;
/*  30:    */ import net.minecraft.world.storage.WorldInfo;
/*  31:    */ import org.apache.logging.log4j.LogManager;
/*  32:    */ import org.apache.logging.log4j.Logger;
/*  33:    */ 
/*  34:    */ public class WorldServerOF
/*  35:    */   extends WorldServer
/*  36:    */ {
/*  37:    */   private NextTickHashSet pendingTickListEntriesHashSet;
/*  38:    */   private TreeSet pendingTickListEntriesTreeSet;
/*  39: 35 */   private List pendingTickListEntriesThisTick = new ArrayList();
/*  40: 36 */   private static final Logger logger = ;
/*  41:    */   
/*  42:    */   public WorldServerOF(MinecraftServer par1MinecraftServer, ISaveHandler par2iSaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, Profiler par6Profiler)
/*  43:    */   {
/*  44: 40 */     super(par1MinecraftServer, par2iSaveHandler, par3Str, par4, par5WorldSettings, par6Profiler);
/*  45: 41 */     fixSetNextTicks();
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void initialize(WorldSettings par1WorldSettings)
/*  49:    */   {
/*  50: 46 */     super.initialize(par1WorldSettings);
/*  51: 47 */     fixSetNextTicks();
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void fixSetNextTicks()
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 54 */       Field[] e = WorldServer.class.getDeclaredFields();
/*  59: 55 */       int posSet = findField(e, Set.class, 0);
/*  60: 56 */       int posTreeSet = findField(e, TreeSet.class, posSet);
/*  61: 57 */       int posList = findField(e, List.class, posTreeSet);
/*  62: 59 */       if ((posSet >= 0) && (posTreeSet >= 0) && (posList >= 0))
/*  63:    */       {
/*  64: 61 */         Field fieldSet = e[posSet];
/*  65: 62 */         Field fieldTreeSet = e[posTreeSet];
/*  66: 63 */         Field fieldList = e[posList];
/*  67: 64 */         fieldSet.setAccessible(true);
/*  68: 65 */         fieldTreeSet.setAccessible(true);
/*  69: 66 */         fieldList.setAccessible(true);
/*  70: 67 */         this.pendingTickListEntriesTreeSet = ((TreeSet)fieldTreeSet.get(this));
/*  71: 68 */         this.pendingTickListEntriesThisTick = ((List)fieldList.get(this));
/*  72: 69 */         Set oldSet = (Set)fieldSet.get(this);
/*  73: 71 */         if ((oldSet instanceof NextTickHashSet)) {
/*  74: 73 */           return;
/*  75:    */         }
/*  76: 76 */         this.pendingTickListEntriesHashSet = new NextTickHashSet(oldSet);
/*  77: 77 */         fieldSet.set(this, this.pendingTickListEntriesHashSet);
/*  78: 78 */         Config.dbg("WorldServer.nextTickSet updated");
/*  79: 79 */         return;
/*  80:    */       }
/*  81: 82 */       Config.warn("Error updating WorldServer.nextTickSet");
/*  82:    */     }
/*  83:    */     catch (Exception var9)
/*  84:    */     {
/*  85: 86 */       Config.warn("Error setting WorldServer.nextTickSet: " + var9.getMessage());
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private int findField(Field[] fields, Class cls, int startPos)
/*  90:    */   {
/*  91: 92 */     if (startPos < 0) {
/*  92: 94 */       return -1;
/*  93:    */     }
/*  94: 98 */     for (int i = startPos; i < fields.length; i++)
/*  95:    */     {
/*  96:100 */       Field field = fields[i];
/*  97:102 */       if (field.getType() == cls) {
/*  98:104 */         return i;
/*  99:    */       }
/* 100:    */     }
/* 101:108 */     return -1;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
/* 105:    */   {
/* 106:114 */     if ((this.pendingTickListEntriesHashSet != null) && (this.pendingTickListEntriesTreeSet != null) && (this.pendingTickListEntriesThisTick != null))
/* 107:    */     {
/* 108:116 */       ArrayList var3 = null;
/* 109:117 */       ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
/* 110:118 */       int var5 = (var4.chunkXPos << 4) - 2;
/* 111:119 */       int var6 = var5 + 16 + 2;
/* 112:120 */       int var7 = (var4.chunkZPos << 4) - 2;
/* 113:121 */       int var8 = var7 + 16 + 2;
/* 114:123 */       for (int var9 = 0; var9 < 2; var9++)
/* 115:    */       {
/* 116:    */         Iterator var10;
/* 117:    */         Iterator var10;
/* 118:127 */         if (var9 == 0)
/* 119:    */         {
/* 120:129 */           TreeSet var11 = new TreeSet();
/* 121:131 */           for (int dx = -1; dx <= 1; dx++) {
/* 122:133 */             for (int dz = -1; dz <= 1; dz++)
/* 123:    */             {
/* 124:135 */               HashSet set = this.pendingTickListEntriesHashSet.getNextTickEntriesSet(var4.chunkXPos + dx, var4.chunkZPos + dz);
/* 125:136 */               var11.addAll(set);
/* 126:    */             }
/* 127:    */           }
/* 128:140 */           var10 = var11.iterator();
/* 129:    */         }
/* 130:    */         else
/* 131:    */         {
/* 132:144 */           var10 = this.pendingTickListEntriesThisTick.iterator();
/* 133:146 */           if (!this.pendingTickListEntriesThisTick.isEmpty()) {
/* 134:148 */             logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
/* 135:    */           }
/* 136:    */         }
/* 137:152 */         while (var10.hasNext())
/* 138:    */         {
/* 139:154 */           NextTickListEntry var15 = (NextTickListEntry)var10.next();
/* 140:156 */           if ((var15.xCoord >= var5) && (var15.xCoord < var6) && (var15.zCoord >= var7) && (var15.zCoord < var8))
/* 141:    */           {
/* 142:158 */             if (par2)
/* 143:    */             {
/* 144:160 */               this.pendingTickListEntriesHashSet.remove(var15);
/* 145:161 */               this.pendingTickListEntriesTreeSet.remove(var15);
/* 146:162 */               var10.remove();
/* 147:    */             }
/* 148:165 */             if (var3 == null) {
/* 149:167 */               var3 = new ArrayList();
/* 150:    */             }
/* 151:170 */             var3.add(var15);
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:175 */       return var3;
/* 156:    */     }
/* 157:179 */     return super.getPendingBlockUpdates(par1Chunk, par2);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void tick()
/* 161:    */   {
/* 162:188 */     super.tick();
/* 163:190 */     if (!Config.isTimeDefault()) {
/* 164:192 */       fixWorldTime();
/* 165:    */     }
/* 166:195 */     if (Config.waterOpacityChanged)
/* 167:    */     {
/* 168:197 */       Config.waterOpacityChanged = false;
/* 169:198 */       updateWaterOpacity();
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected void updateWeather()
/* 174:    */   {
/* 175:207 */     if (!Config.isWeatherEnabled()) {
/* 176:209 */       fixWorldWeather();
/* 177:    */     }
/* 178:212 */     super.updateWeather();
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void fixWorldWeather()
/* 182:    */   {
/* 183:217 */     if ((this.worldInfo.isRaining()) || (this.worldInfo.isThundering()))
/* 184:    */     {
/* 185:219 */       this.worldInfo.setRainTime(0);
/* 186:220 */       this.worldInfo.setRaining(false);
/* 187:221 */       setRainStrength(0.0F);
/* 188:222 */       this.worldInfo.setThunderTime(0);
/* 189:223 */       this.worldInfo.setThundering(false);
/* 190:224 */       setThunderStrength(0.0F);
/* 191:225 */       func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0F));
/* 192:226 */       func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, 0.0F));
/* 193:227 */       func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, 0.0F));
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   private void fixWorldTime()
/* 198:    */   {
/* 199:233 */     if (this.worldInfo.getGameType().getID() == 1)
/* 200:    */     {
/* 201:235 */       long time = getWorldTime();
/* 202:236 */       long timeOfDay = time % 24000L;
/* 203:238 */       if (Config.isTimeDayOnly())
/* 204:    */       {
/* 205:240 */         if (timeOfDay <= 1000L) {
/* 206:242 */           setWorldTime(time - timeOfDay + 1001L);
/* 207:    */         }
/* 208:245 */         if (timeOfDay >= 11000L) {
/* 209:247 */           setWorldTime(time - timeOfDay + 24001L);
/* 210:    */         }
/* 211:    */       }
/* 212:251 */       if (Config.isTimeNightOnly())
/* 213:    */       {
/* 214:253 */         if (timeOfDay <= 14000L) {
/* 215:255 */           setWorldTime(time - timeOfDay + 14001L);
/* 216:    */         }
/* 217:258 */         if (timeOfDay >= 22000L) {
/* 218:260 */           setWorldTime(time - timeOfDay + 24000L + 14001L);
/* 219:    */         }
/* 220:    */       }
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void updateWaterOpacity()
/* 225:    */   {
/* 226:268 */     byte opacity = 3;
/* 227:270 */     if (Config.isClearWater()) {
/* 228:272 */       opacity = 1;
/* 229:    */     }
/* 230:275 */     BlockUtils.setLightOpacity(Blocks.water, opacity);
/* 231:276 */     BlockUtils.setLightOpacity(Blocks.flowing_water, opacity);
/* 232:277 */     IChunkProvider cp = this.chunkProvider;
/* 233:279 */     if (cp != null) {
/* 234:281 */       for (int x = -512; x < 512; x++) {
/* 235:283 */         for (int z = -512; z < 512; z++) {
/* 236:285 */           if (cp.chunkExists(x, z))
/* 237:    */           {
/* 238:287 */             Chunk c = cp.provideChunk(x, z);
/* 239:289 */             if ((c != null) && (!(c instanceof EmptyChunk)))
/* 240:    */             {
/* 241:291 */               ExtendedBlockStorage[] ebss = c.getBlockStorageArray();
/* 242:293 */               for (int i = 0; i < ebss.length; i++)
/* 243:    */               {
/* 244:295 */                 ExtendedBlockStorage ebs = ebss[i];
/* 245:297 */                 if (ebs != null)
/* 246:    */                 {
/* 247:299 */                   NibbleArray na = ebs.getSkylightArray();
/* 248:301 */                   if (na != null)
/* 249:    */                   {
/* 250:303 */                     byte[] data = na.data;
/* 251:305 */                     for (int d = 0; d < data.length; d++) {
/* 252:307 */                       data[d] = 0;
/* 253:    */                     }
/* 254:    */                   }
/* 255:    */                 }
/* 256:    */               }
/* 257:313 */               c.generateSkylightMap();
/* 258:    */             }
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void updateEntity(Entity par1Entity)
/* 266:    */   {
/* 267:326 */     if ((canSkipEntityUpdate(par1Entity)) && ((par1Entity instanceof EntityLivingBase)))
/* 268:    */     {
/* 269:328 */       EntityLivingBase elb = (EntityLivingBase)par1Entity;
/* 270:329 */       int entityAge = EntityUtils.getEntityAge(elb);
/* 271:330 */       entityAge++;
/* 272:332 */       if ((elb instanceof EntityMob))
/* 273:    */       {
/* 274:334 */         float el = elb.getBrightness(1.0F);
/* 275:336 */         if (el > 0.5F) {
/* 276:338 */           entityAge += 2;
/* 277:    */         }
/* 278:    */       }
/* 279:342 */       EntityUtils.setEntityAge(elb, entityAge);
/* 280:344 */       if ((elb instanceof EntityLiving))
/* 281:    */       {
/* 282:346 */         EntityLiving var5 = (EntityLiving)elb;
/* 283:347 */         EntityUtils.despawnEntity(var5);
/* 284:    */       }
/* 285:    */     }
/* 286:    */     else
/* 287:    */     {
/* 288:352 */       super.updateEntity(par1Entity);
/* 289:354 */       if (Config.isSmoothWorld())
/* 290:    */       {
/* 291:356 */         Thread.currentThread();
/* 292:357 */         Thread.yield();
/* 293:    */       }
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private boolean canSkipEntityUpdate(Entity entity)
/* 298:    */   {
/* 299:364 */     if (!Config.isSingleProcessor()) {
/* 300:366 */       return false;
/* 301:    */     }
/* 302:368 */     if (!(entity instanceof EntityLivingBase)) {
/* 303:370 */       return false;
/* 304:    */     }
/* 305:374 */     EntityLivingBase entityLiving = (EntityLivingBase)entity;
/* 306:376 */     if (entityLiving.isChild()) {
/* 307:378 */       return false;
/* 308:    */     }
/* 309:380 */     if (entity.ticksExisted < 20) {
/* 310:382 */       return false;
/* 311:    */     }
/* 312:384 */     if (this.playerEntities.size() != 1) {
/* 313:386 */       return false;
/* 314:    */     }
/* 315:390 */     Entity player = (Entity)this.playerEntities.get(0);
/* 316:391 */     double dx = Math.abs(entity.posX - player.posX) - 16.0D;
/* 317:392 */     double dz = Math.abs(entity.posZ - player.posZ) - 16.0D;
/* 318:393 */     double distSq = dx * dx + dz * dz;
/* 319:394 */     return !entity.isInRangeToRenderDist(distSq);
/* 320:    */   }
/* 321:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WorldServerOF
 * JD-Core Version:    0.7.0.1
 */