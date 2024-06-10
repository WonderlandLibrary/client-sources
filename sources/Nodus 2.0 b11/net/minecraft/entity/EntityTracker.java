/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.concurrent.Callable;
/*   8:    */ import net.minecraft.crash.CrashReport;
/*   9:    */ import net.minecraft.crash.CrashReportCategory;
/*  10:    */ import net.minecraft.entity.boss.EntityDragon;
/*  11:    */ import net.minecraft.entity.boss.EntityWither;
/*  12:    */ import net.minecraft.entity.item.EntityBoat;
/*  13:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  14:    */ import net.minecraft.entity.item.EntityEnderEye;
/*  15:    */ import net.minecraft.entity.item.EntityEnderPearl;
/*  16:    */ import net.minecraft.entity.item.EntityExpBottle;
/*  17:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*  18:    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  19:    */ import net.minecraft.entity.item.EntityItem;
/*  20:    */ import net.minecraft.entity.item.EntityMinecart;
/*  21:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  22:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  23:    */ import net.minecraft.entity.passive.EntityBat;
/*  24:    */ import net.minecraft.entity.passive.EntitySquid;
/*  25:    */ import net.minecraft.entity.passive.IAnimals;
/*  26:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  27:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  28:    */ import net.minecraft.entity.projectile.EntityEgg;
/*  29:    */ import net.minecraft.entity.projectile.EntityFireball;
/*  30:    */ import net.minecraft.entity.projectile.EntityFishHook;
/*  31:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  32:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*  33:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  34:    */ import net.minecraft.network.Packet;
/*  35:    */ import net.minecraft.server.MinecraftServer;
/*  36:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  37:    */ import net.minecraft.util.IntHashMap;
/*  38:    */ import net.minecraft.util.ReportedException;
/*  39:    */ import net.minecraft.world.WorldServer;
/*  40:    */ import net.minecraft.world.chunk.Chunk;
/*  41:    */ import org.apache.logging.log4j.LogManager;
/*  42:    */ import org.apache.logging.log4j.Logger;
/*  43:    */ 
/*  44:    */ public class EntityTracker
/*  45:    */ {
/*  46: 44 */   private static final Logger logger = ;
/*  47:    */   private final WorldServer theWorld;
/*  48: 50 */   private Set trackedEntities = new HashSet();
/*  49: 51 */   private IntHashMap trackedEntityIDs = new IntHashMap();
/*  50:    */   private int entityViewDistance;
/*  51:    */   private static final String __OBFID = "CL_00001431";
/*  52:    */   
/*  53:    */   public EntityTracker(WorldServer par1WorldServer)
/*  54:    */   {
/*  55: 57 */     this.theWorld = par1WorldServer;
/*  56: 58 */     this.entityViewDistance = par1WorldServer.func_73046_m().getConfigurationManager().getEntityViewDistance();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addEntityToTracker(Entity par1Entity)
/*  60:    */   {
/*  61: 67 */     if ((par1Entity instanceof EntityPlayerMP))
/*  62:    */     {
/*  63: 69 */       addEntityToTracker(par1Entity, 512, 2);
/*  64: 70 */       EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
/*  65: 71 */       Iterator var3 = this.trackedEntities.iterator();
/*  66: 73 */       while (var3.hasNext())
/*  67:    */       {
/*  68: 75 */         EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
/*  69: 77 */         if (var4.myEntity != var2) {
/*  70: 79 */           var4.tryStartWachingThis(var2);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74: 83 */     else if ((par1Entity instanceof EntityFishHook))
/*  75:    */     {
/*  76: 85 */       addEntityToTracker(par1Entity, 64, 5, true);
/*  77:    */     }
/*  78: 87 */     else if ((par1Entity instanceof EntityArrow))
/*  79:    */     {
/*  80: 89 */       addEntityToTracker(par1Entity, 64, 20, false);
/*  81:    */     }
/*  82: 91 */     else if ((par1Entity instanceof EntitySmallFireball))
/*  83:    */     {
/*  84: 93 */       addEntityToTracker(par1Entity, 64, 10, false);
/*  85:    */     }
/*  86: 95 */     else if ((par1Entity instanceof EntityFireball))
/*  87:    */     {
/*  88: 97 */       addEntityToTracker(par1Entity, 64, 10, false);
/*  89:    */     }
/*  90: 99 */     else if ((par1Entity instanceof EntitySnowball))
/*  91:    */     {
/*  92:101 */       addEntityToTracker(par1Entity, 64, 10, true);
/*  93:    */     }
/*  94:103 */     else if ((par1Entity instanceof EntityEnderPearl))
/*  95:    */     {
/*  96:105 */       addEntityToTracker(par1Entity, 64, 10, true);
/*  97:    */     }
/*  98:107 */     else if ((par1Entity instanceof EntityEnderEye))
/*  99:    */     {
/* 100:109 */       addEntityToTracker(par1Entity, 64, 4, true);
/* 101:    */     }
/* 102:111 */     else if ((par1Entity instanceof EntityEgg))
/* 103:    */     {
/* 104:113 */       addEntityToTracker(par1Entity, 64, 10, true);
/* 105:    */     }
/* 106:115 */     else if ((par1Entity instanceof EntityPotion))
/* 107:    */     {
/* 108:117 */       addEntityToTracker(par1Entity, 64, 10, true);
/* 109:    */     }
/* 110:119 */     else if ((par1Entity instanceof EntityExpBottle))
/* 111:    */     {
/* 112:121 */       addEntityToTracker(par1Entity, 64, 10, true);
/* 113:    */     }
/* 114:123 */     else if ((par1Entity instanceof EntityFireworkRocket))
/* 115:    */     {
/* 116:125 */       addEntityToTracker(par1Entity, 64, 10, true);
/* 117:    */     }
/* 118:127 */     else if ((par1Entity instanceof EntityItem))
/* 119:    */     {
/* 120:129 */       addEntityToTracker(par1Entity, 64, 20, true);
/* 121:    */     }
/* 122:131 */     else if ((par1Entity instanceof EntityMinecart))
/* 123:    */     {
/* 124:133 */       addEntityToTracker(par1Entity, 80, 3, true);
/* 125:    */     }
/* 126:135 */     else if ((par1Entity instanceof EntityBoat))
/* 127:    */     {
/* 128:137 */       addEntityToTracker(par1Entity, 80, 3, true);
/* 129:    */     }
/* 130:139 */     else if ((par1Entity instanceof EntitySquid))
/* 131:    */     {
/* 132:141 */       addEntityToTracker(par1Entity, 64, 3, true);
/* 133:    */     }
/* 134:143 */     else if ((par1Entity instanceof EntityWither))
/* 135:    */     {
/* 136:145 */       addEntityToTracker(par1Entity, 80, 3, false);
/* 137:    */     }
/* 138:147 */     else if ((par1Entity instanceof EntityBat))
/* 139:    */     {
/* 140:149 */       addEntityToTracker(par1Entity, 80, 3, false);
/* 141:    */     }
/* 142:151 */     else if ((par1Entity instanceof IAnimals))
/* 143:    */     {
/* 144:153 */       addEntityToTracker(par1Entity, 80, 3, true);
/* 145:    */     }
/* 146:155 */     else if ((par1Entity instanceof EntityDragon))
/* 147:    */     {
/* 148:157 */       addEntityToTracker(par1Entity, 160, 3, true);
/* 149:    */     }
/* 150:159 */     else if ((par1Entity instanceof EntityTNTPrimed))
/* 151:    */     {
/* 152:161 */       addEntityToTracker(par1Entity, 160, 10, true);
/* 153:    */     }
/* 154:163 */     else if ((par1Entity instanceof EntityFallingBlock))
/* 155:    */     {
/* 156:165 */       addEntityToTracker(par1Entity, 160, 20, true);
/* 157:    */     }
/* 158:167 */     else if ((par1Entity instanceof EntityHanging))
/* 159:    */     {
/* 160:169 */       addEntityToTracker(par1Entity, 160, 2147483647, false);
/* 161:    */     }
/* 162:171 */     else if ((par1Entity instanceof EntityXPOrb))
/* 163:    */     {
/* 164:173 */       addEntityToTracker(par1Entity, 160, 20, true);
/* 165:    */     }
/* 166:175 */     else if ((par1Entity instanceof EntityEnderCrystal))
/* 167:    */     {
/* 168:177 */       addEntityToTracker(par1Entity, 256, 2147483647, false);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void addEntityToTracker(Entity par1Entity, int par2, int par3)
/* 173:    */   {
/* 174:183 */     addEntityToTracker(par1Entity, par2, par3, false);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void addEntityToTracker(Entity par1Entity, int par2, final int par3, boolean par4)
/* 178:    */   {
/* 179:188 */     if (par2 > this.entityViewDistance) {
/* 180:190 */       par2 = this.entityViewDistance;
/* 181:    */     }
/* 182:    */     try
/* 183:    */     {
/* 184:195 */       if (this.trackedEntityIDs.containsItem(par1Entity.getEntityId())) {
/* 185:197 */         throw new IllegalStateException("Entity is already tracked!");
/* 186:    */       }
/* 187:200 */       EntityTrackerEntry var5 = new EntityTrackerEntry(par1Entity, par2, par3, par4);
/* 188:201 */       this.trackedEntities.add(var5);
/* 189:202 */       this.trackedEntityIDs.addKey(par1Entity.getEntityId(), var5);
/* 190:203 */       var5.sendEventsToPlayers(this.theWorld.playerEntities);
/* 191:    */     }
/* 192:    */     catch (Throwable var11)
/* 193:    */     {
/* 194:207 */       CrashReport var6 = CrashReport.makeCrashReport(var11, "Adding entity to track");
/* 195:208 */       CrashReportCategory var7 = var6.makeCategory("Entity To Track");
/* 196:209 */       var7.addCrashSection("Tracking range", par2 + " blocks");
/* 197:210 */       var7.addCrashSectionCallable("Update interval", new Callable()
/* 198:    */       {
/* 199:    */         private static final String __OBFID = "CL_00001432";
/* 200:    */         
/* 201:    */         public String call()
/* 202:    */         {
/* 203:215 */           String var1 = "Once per " + par3 + " ticks";
/* 204:217 */           if (par3 == 2147483647) {
/* 205:219 */             var1 = "Maximum (" + var1 + ")";
/* 206:    */           }
/* 207:222 */           return var1;
/* 208:    */         }
/* 209:224 */       });
/* 210:225 */       par1Entity.addEntityCrashInfo(var7);
/* 211:226 */       CrashReportCategory var8 = var6.makeCategory("Entity That Is Already Tracked");
/* 212:227 */       ((EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.getEntityId())).myEntity.addEntityCrashInfo(var8);
/* 213:    */       try
/* 214:    */       {
/* 215:231 */         throw new ReportedException(var6);
/* 216:    */       }
/* 217:    */       catch (ReportedException var10)
/* 218:    */       {
/* 219:235 */         logger.error("\"Silently\" catching entity tracking error.", var10);
/* 220:    */       }
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void removeEntityFromAllTrackingPlayers(Entity par1Entity)
/* 225:    */   {
/* 226:242 */     if ((par1Entity instanceof EntityPlayerMP))
/* 227:    */     {
/* 228:244 */       EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
/* 229:245 */       Iterator var3 = this.trackedEntities.iterator();
/* 230:247 */       while (var3.hasNext())
/* 231:    */       {
/* 232:249 */         EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
/* 233:250 */         var4.removeFromWatchingList(var2);
/* 234:    */       }
/* 235:    */     }
/* 236:254 */     EntityTrackerEntry var5 = (EntityTrackerEntry)this.trackedEntityIDs.removeObject(par1Entity.getEntityId());
/* 237:256 */     if (var5 != null)
/* 238:    */     {
/* 239:258 */       this.trackedEntities.remove(var5);
/* 240:259 */       var5.informAllAssociatedPlayersOfItemDestruction();
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void updateTrackedEntities()
/* 245:    */   {
/* 246:265 */     ArrayList var1 = new ArrayList();
/* 247:266 */     Iterator var2 = this.trackedEntities.iterator();
/* 248:268 */     while (var2.hasNext())
/* 249:    */     {
/* 250:270 */       EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
/* 251:271 */       var3.sendLocationToAllClients(this.theWorld.playerEntities);
/* 252:273 */       if ((var3.playerEntitiesUpdated) && ((var3.myEntity instanceof EntityPlayerMP))) {
/* 253:275 */         var1.add((EntityPlayerMP)var3.myEntity);
/* 254:    */       }
/* 255:    */     }
/* 256:279 */     for (int var6 = 0; var6 < var1.size(); var6++)
/* 257:    */     {
/* 258:281 */       EntityPlayerMP var7 = (EntityPlayerMP)var1.get(var6);
/* 259:282 */       Iterator var4 = this.trackedEntities.iterator();
/* 260:284 */       while (var4.hasNext())
/* 261:    */       {
/* 262:286 */         EntityTrackerEntry var5 = (EntityTrackerEntry)var4.next();
/* 263:288 */         if (var5.myEntity != var7) {
/* 264:290 */           var5.tryStartWachingThis(var7);
/* 265:    */         }
/* 266:    */       }
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void func_151247_a(Entity p_151247_1_, Packet p_151247_2_)
/* 271:    */   {
/* 272:298 */     EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(p_151247_1_.getEntityId());
/* 273:300 */     if (var3 != null) {
/* 274:302 */       var3.func_151259_a(p_151247_2_);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void func_151248_b(Entity p_151248_1_, Packet p_151248_2_)
/* 279:    */   {
/* 280:308 */     EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(p_151248_1_.getEntityId());
/* 281:310 */     if (var3 != null) {
/* 282:312 */       var3.func_151261_b(p_151248_2_);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void removePlayerFromTrackers(EntityPlayerMP par1EntityPlayerMP)
/* 287:    */   {
/* 288:318 */     Iterator var2 = this.trackedEntities.iterator();
/* 289:320 */     while (var2.hasNext())
/* 290:    */     {
/* 291:322 */       EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
/* 292:323 */       var3.removePlayerFromTracker(par1EntityPlayerMP);
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void func_85172_a(EntityPlayerMP par1EntityPlayerMP, Chunk par2Chunk)
/* 297:    */   {
/* 298:329 */     Iterator var3 = this.trackedEntities.iterator();
/* 299:331 */     while (var3.hasNext())
/* 300:    */     {
/* 301:333 */       EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
/* 302:335 */       if ((var4.myEntity != par1EntityPlayerMP) && (var4.myEntity.chunkCoordX == par2Chunk.xPosition) && (var4.myEntity.chunkCoordZ == par2Chunk.zPosition)) {
/* 303:337 */         var4.tryStartWachingThis(par1EntityPlayerMP);
/* 304:    */       }
/* 305:    */     }
/* 306:    */   }
/* 307:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityTracker
 * JD-Core Version:    0.7.0.1
 */