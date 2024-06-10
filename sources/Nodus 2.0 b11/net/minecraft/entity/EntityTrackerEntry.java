/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*  10:    */ import net.minecraft.entity.boss.EntityDragon;
/*  11:    */ import net.minecraft.entity.item.EntityBoat;
/*  12:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  13:    */ import net.minecraft.entity.item.EntityEnderEye;
/*  14:    */ import net.minecraft.entity.item.EntityEnderPearl;
/*  15:    */ import net.minecraft.entity.item.EntityExpBottle;
/*  16:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*  17:    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  18:    */ import net.minecraft.entity.item.EntityItem;
/*  19:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  20:    */ import net.minecraft.entity.item.EntityMinecart;
/*  21:    */ import net.minecraft.entity.item.EntityPainting;
/*  22:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  23:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  24:    */ import net.minecraft.entity.passive.IAnimals;
/*  25:    */ import net.minecraft.entity.player.EntityPlayer;
/*  26:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  27:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  28:    */ import net.minecraft.entity.projectile.EntityEgg;
/*  29:    */ import net.minecraft.entity.projectile.EntityFireball;
/*  30:    */ import net.minecraft.entity.projectile.EntityFishHook;
/*  31:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  32:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*  33:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  34:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  35:    */ import net.minecraft.init.Items;
/*  36:    */ import net.minecraft.item.ItemMap;
/*  37:    */ import net.minecraft.item.ItemStack;
/*  38:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  39:    */ import net.minecraft.network.Packet;
/*  40:    */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*  41:    */ import net.minecraft.network.play.server.S0APacketUseBed;
/*  42:    */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*  43:    */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*  44:    */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*  45:    */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*  46:    */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*  47:    */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*  48:    */ import net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove;
/*  49:    */ import net.minecraft.network.play.server.S14PacketEntity.S16PacketEntityLook;
/*  50:    */ import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;
/*  51:    */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*  52:    */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*  53:    */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*  54:    */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*  55:    */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*  56:    */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*  57:    */ import net.minecraft.potion.PotionEffect;
/*  58:    */ import net.minecraft.server.management.PlayerManager;
/*  59:    */ import net.minecraft.util.MathHelper;
/*  60:    */ import net.minecraft.world.WorldServer;
/*  61:    */ import net.minecraft.world.storage.MapData;
/*  62:    */ import org.apache.logging.log4j.LogManager;
/*  63:    */ import org.apache.logging.log4j.Logger;
/*  64:    */ 
/*  65:    */ public class EntityTrackerEntry
/*  66:    */ {
/*  67: 62 */   private static final Logger logger = ;
/*  68:    */   public Entity myEntity;
/*  69:    */   public int blocksDistanceThreshold;
/*  70:    */   public int updateFrequency;
/*  71:    */   public int lastScaledXPosition;
/*  72:    */   public int lastScaledYPosition;
/*  73:    */   public int lastScaledZPosition;
/*  74:    */   public int lastYaw;
/*  75:    */   public int lastPitch;
/*  76:    */   public int lastHeadMotion;
/*  77:    */   public double motionX;
/*  78:    */   public double motionY;
/*  79:    */   public double motionZ;
/*  80:    */   public int ticks;
/*  81:    */   private double posX;
/*  82:    */   private double posY;
/*  83:    */   private double posZ;
/*  84:    */   private boolean isDataInitialized;
/*  85:    */   private boolean sendVelocityUpdates;
/*  86:    */   private int ticksSinceLastForcedTeleport;
/*  87:    */   private Entity field_85178_v;
/*  88:    */   private boolean ridingEntity;
/*  89:    */   public boolean playerEntitiesUpdated;
/*  90: 98 */   public Set trackingPlayers = new HashSet();
/*  91:    */   private static final String __OBFID = "CL_00001443";
/*  92:    */   
/*  93:    */   public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
/*  94:    */   {
/*  95:103 */     this.myEntity = par1Entity;
/*  96:104 */     this.blocksDistanceThreshold = par2;
/*  97:105 */     this.updateFrequency = par3;
/*  98:106 */     this.sendVelocityUpdates = par4;
/*  99:107 */     this.lastScaledXPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
/* 100:108 */     this.lastScaledYPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
/* 101:109 */     this.lastScaledZPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
/* 102:110 */     this.lastYaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0F / 360.0F);
/* 103:111 */     this.lastPitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0F / 360.0F);
/* 104:112 */     this.lastHeadMotion = MathHelper.floor_float(par1Entity.getRotationYawHead() * 256.0F / 360.0F);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean equals(Object par1Obj)
/* 108:    */   {
/* 109:117 */     return ((EntityTrackerEntry)par1Obj).myEntity.getEntityId() == this.myEntity.getEntityId();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int hashCode()
/* 113:    */   {
/* 114:122 */     return this.myEntity.getEntityId();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void sendLocationToAllClients(List par1List)
/* 118:    */   {
/* 119:130 */     this.playerEntitiesUpdated = false;
/* 120:132 */     if ((!this.isDataInitialized) || (this.myEntity.getDistanceSq(this.posX, this.posY, this.posZ) > 16.0D))
/* 121:    */     {
/* 122:134 */       this.posX = this.myEntity.posX;
/* 123:135 */       this.posY = this.myEntity.posY;
/* 124:136 */       this.posZ = this.myEntity.posZ;
/* 125:137 */       this.isDataInitialized = true;
/* 126:138 */       this.playerEntitiesUpdated = true;
/* 127:139 */       sendEventsToPlayers(par1List);
/* 128:    */     }
/* 129:142 */     if ((this.field_85178_v != this.myEntity.ridingEntity) || ((this.myEntity.ridingEntity != null) && (this.ticks % 60 == 0)))
/* 130:    */     {
/* 131:144 */       this.field_85178_v = this.myEntity.ridingEntity;
/* 132:145 */       func_151259_a(new S1BPacketEntityAttach(0, this.myEntity, this.myEntity.ridingEntity));
/* 133:    */     }
/* 134:148 */     if (((this.myEntity instanceof EntityItemFrame)) && (this.ticks % 10 == 0))
/* 135:    */     {
/* 136:150 */       EntityItemFrame var23 = (EntityItemFrame)this.myEntity;
/* 137:151 */       ItemStack var24 = var23.getDisplayedItem();
/* 138:153 */       if ((var24 != null) && ((var24.getItem() instanceof ItemMap)))
/* 139:    */       {
/* 140:155 */         MapData var26 = Items.filled_map.getMapData(var24, this.myEntity.worldObj);
/* 141:156 */         Iterator var27 = par1List.iterator();
/* 142:158 */         while (var27.hasNext())
/* 143:    */         {
/* 144:160 */           EntityPlayer var28 = (EntityPlayer)var27.next();
/* 145:161 */           EntityPlayerMP var29 = (EntityPlayerMP)var28;
/* 146:162 */           var26.updateVisiblePlayers(var29, var24);
/* 147:163 */           Packet var30 = Items.filled_map.func_150911_c(var24, this.myEntity.worldObj, var29);
/* 148:165 */           if (var30 != null) {
/* 149:167 */             var29.playerNetServerHandler.sendPacket(var30);
/* 150:    */           }
/* 151:    */         }
/* 152:    */       }
/* 153:172 */       func_111190_b();
/* 154:    */     }
/* 155:174 */     else if ((this.ticks % this.updateFrequency == 0) || (this.myEntity.isAirBorne) || (this.myEntity.getDataWatcher().hasChanges()))
/* 156:    */     {
/* 157:179 */       if (this.myEntity.ridingEntity == null)
/* 158:    */       {
/* 159:181 */         this.ticksSinceLastForcedTeleport += 1;
/* 160:182 */         int var2 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
/* 161:183 */         int var3 = MathHelper.floor_double(this.myEntity.posY * 32.0D);
/* 162:184 */         int var4 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
/* 163:185 */         int var5 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0F / 360.0F);
/* 164:186 */         int var6 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0F / 360.0F);
/* 165:187 */         int var7 = var2 - this.lastScaledXPosition;
/* 166:188 */         int var8 = var3 - this.lastScaledYPosition;
/* 167:189 */         int var9 = var4 - this.lastScaledZPosition;
/* 168:190 */         Object var10 = null;
/* 169:191 */         boolean var11 = (Math.abs(var7) >= 4) || (Math.abs(var8) >= 4) || (Math.abs(var9) >= 4) || (this.ticks % 60 == 0);
/* 170:192 */         boolean var12 = (Math.abs(var5 - this.lastYaw) >= 4) || (Math.abs(var6 - this.lastPitch) >= 4);
/* 171:194 */         if ((this.ticks > 0) || ((this.myEntity instanceof EntityArrow))) {
/* 172:196 */           if ((var7 >= -128) && (var7 < 128) && (var8 >= -128) && (var8 < 128) && (var9 >= -128) && (var9 < 128) && (this.ticksSinceLastForcedTeleport <= 400) && (!this.ridingEntity))
/* 173:    */           {
/* 174:198 */             if ((var11) && (var12)) {
/* 175:200 */               var10 = new S14PacketEntity.S17PacketEntityLookMove(this.myEntity.getEntityId(), (byte)var7, (byte)var8, (byte)var9, (byte)var5, (byte)var6);
/* 176:202 */             } else if (var11) {
/* 177:204 */               var10 = new S14PacketEntity.S15PacketEntityRelMove(this.myEntity.getEntityId(), (byte)var7, (byte)var8, (byte)var9);
/* 178:206 */             } else if (var12) {
/* 179:208 */               var10 = new S14PacketEntity.S16PacketEntityLook(this.myEntity.getEntityId(), (byte)var5, (byte)var6);
/* 180:    */             }
/* 181:    */           }
/* 182:    */           else
/* 183:    */           {
/* 184:213 */             this.ticksSinceLastForcedTeleport = 0;
/* 185:214 */             var10 = new S18PacketEntityTeleport(this.myEntity.getEntityId(), var2, var3, var4, (byte)var5, (byte)var6);
/* 186:    */           }
/* 187:    */         }
/* 188:218 */         if (this.sendVelocityUpdates)
/* 189:    */         {
/* 190:220 */           double var13 = this.myEntity.motionX - this.motionX;
/* 191:221 */           double var15 = this.myEntity.motionY - this.motionY;
/* 192:222 */           double var17 = this.myEntity.motionZ - this.motionZ;
/* 193:223 */           double var19 = 0.02D;
/* 194:224 */           double var21 = var13 * var13 + var15 * var15 + var17 * var17;
/* 195:226 */           if ((var21 > var19 * var19) || ((var21 > 0.0D) && (this.myEntity.motionX == 0.0D) && (this.myEntity.motionY == 0.0D) && (this.myEntity.motionZ == 0.0D)))
/* 196:    */           {
/* 197:228 */             this.motionX = this.myEntity.motionX;
/* 198:229 */             this.motionY = this.myEntity.motionY;
/* 199:230 */             this.motionZ = this.myEntity.motionZ;
/* 200:231 */             func_151259_a(new S12PacketEntityVelocity(this.myEntity.getEntityId(), this.motionX, this.motionY, this.motionZ));
/* 201:    */           }
/* 202:    */         }
/* 203:235 */         if (var10 != null) {
/* 204:237 */           func_151259_a((Packet)var10);
/* 205:    */         }
/* 206:240 */         func_111190_b();
/* 207:242 */         if (var11)
/* 208:    */         {
/* 209:244 */           this.lastScaledXPosition = var2;
/* 210:245 */           this.lastScaledYPosition = var3;
/* 211:246 */           this.lastScaledZPosition = var4;
/* 212:    */         }
/* 213:249 */         if (var12)
/* 214:    */         {
/* 215:251 */           this.lastYaw = var5;
/* 216:252 */           this.lastPitch = var6;
/* 217:    */         }
/* 218:255 */         this.ridingEntity = false;
/* 219:    */       }
/* 220:    */       else
/* 221:    */       {
/* 222:259 */         var2 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0F / 360.0F);
/* 223:260 */         int var3 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0F / 360.0F);
/* 224:261 */         boolean var25 = (Math.abs(var2 - this.lastYaw) >= 4) || (Math.abs(var3 - this.lastPitch) >= 4);
/* 225:263 */         if (var25)
/* 226:    */         {
/* 227:265 */           func_151259_a(new S14PacketEntity.S16PacketEntityLook(this.myEntity.getEntityId(), (byte)var2, (byte)var3));
/* 228:266 */           this.lastYaw = var2;
/* 229:267 */           this.lastPitch = var3;
/* 230:    */         }
/* 231:270 */         this.lastScaledXPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
/* 232:271 */         this.lastScaledYPosition = MathHelper.floor_double(this.myEntity.posY * 32.0D);
/* 233:272 */         this.lastScaledZPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
/* 234:273 */         func_111190_b();
/* 235:274 */         this.ridingEntity = true;
/* 236:    */       }
/* 237:277 */       int var2 = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 238:279 */       if (Math.abs(var2 - this.lastHeadMotion) >= 4)
/* 239:    */       {
/* 240:281 */         func_151259_a(new S19PacketEntityHeadLook(this.myEntity, (byte)var2));
/* 241:282 */         this.lastHeadMotion = var2;
/* 242:    */       }
/* 243:285 */       this.myEntity.isAirBorne = false;
/* 244:    */     }
/* 245:288 */     this.ticks += 1;
/* 246:290 */     if (this.myEntity.velocityChanged)
/* 247:    */     {
/* 248:292 */       func_151261_b(new S12PacketEntityVelocity(this.myEntity));
/* 249:293 */       this.myEntity.velocityChanged = false;
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   private void func_111190_b()
/* 254:    */   {
/* 255:299 */     DataWatcher var1 = this.myEntity.getDataWatcher();
/* 256:301 */     if (var1.hasChanges()) {
/* 257:303 */       func_151261_b(new S1CPacketEntityMetadata(this.myEntity.getEntityId(), var1, false));
/* 258:    */     }
/* 259:306 */     if ((this.myEntity instanceof EntityLivingBase))
/* 260:    */     {
/* 261:308 */       ServersideAttributeMap var2 = (ServersideAttributeMap)((EntityLivingBase)this.myEntity).getAttributeMap();
/* 262:309 */       Set var3 = var2.getAttributeInstanceSet();
/* 263:311 */       if (!var3.isEmpty()) {
/* 264:313 */         func_151261_b(new S20PacketEntityProperties(this.myEntity.getEntityId(), var3));
/* 265:    */       }
/* 266:316 */       var3.clear();
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void func_151259_a(Packet p_151259_1_)
/* 271:    */   {
/* 272:322 */     Iterator var2 = this.trackingPlayers.iterator();
/* 273:324 */     while (var2.hasNext())
/* 274:    */     {
/* 275:326 */       EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
/* 276:327 */       var3.playerNetServerHandler.sendPacket(p_151259_1_);
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void func_151261_b(Packet p_151261_1_)
/* 281:    */   {
/* 282:333 */     func_151259_a(p_151261_1_);
/* 283:335 */     if ((this.myEntity instanceof EntityPlayerMP)) {
/* 284:337 */       ((EntityPlayerMP)this.myEntity).playerNetServerHandler.sendPacket(p_151261_1_);
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void informAllAssociatedPlayersOfItemDestruction()
/* 289:    */   {
/* 290:343 */     Iterator var1 = this.trackingPlayers.iterator();
/* 291:345 */     while (var1.hasNext())
/* 292:    */     {
/* 293:347 */       EntityPlayerMP var2 = (EntityPlayerMP)var1.next();
/* 294:348 */       var2.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.getEntityId()));
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void removeFromWatchingList(EntityPlayerMP par1EntityPlayerMP)
/* 299:    */   {
/* 300:354 */     if (this.trackingPlayers.contains(par1EntityPlayerMP))
/* 301:    */     {
/* 302:356 */       par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.getEntityId()));
/* 303:357 */       this.trackingPlayers.remove(par1EntityPlayerMP);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void tryStartWachingThis(EntityPlayerMP par1EntityPlayerMP)
/* 308:    */   {
/* 309:366 */     if (par1EntityPlayerMP != this.myEntity)
/* 310:    */     {
/* 311:368 */       double var2 = par1EntityPlayerMP.posX - this.lastScaledXPosition / 32;
/* 312:369 */       double var4 = par1EntityPlayerMP.posZ - this.lastScaledZPosition / 32;
/* 313:371 */       if ((var2 >= -this.blocksDistanceThreshold) && (var2 <= this.blocksDistanceThreshold) && (var4 >= -this.blocksDistanceThreshold) && (var4 <= this.blocksDistanceThreshold))
/* 314:    */       {
/* 315:373 */         if ((!this.trackingPlayers.contains(par1EntityPlayerMP)) && ((isPlayerWatchingThisChunk(par1EntityPlayerMP)) || (this.myEntity.forceSpawn)))
/* 316:    */         {
/* 317:375 */           this.trackingPlayers.add(par1EntityPlayerMP);
/* 318:376 */           Packet var6 = func_151260_c();
/* 319:377 */           par1EntityPlayerMP.playerNetServerHandler.sendPacket(var6);
/* 320:379 */           if (!this.myEntity.getDataWatcher().getIsBlank()) {
/* 321:381 */             par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1CPacketEntityMetadata(this.myEntity.getEntityId(), this.myEntity.getDataWatcher(), true));
/* 322:    */           }
/* 323:384 */           if ((this.myEntity instanceof EntityLivingBase))
/* 324:    */           {
/* 325:386 */             ServersideAttributeMap var7 = (ServersideAttributeMap)((EntityLivingBase)this.myEntity).getAttributeMap();
/* 326:387 */             Collection var8 = var7.getWatchedAttributes();
/* 327:389 */             if (!var8.isEmpty()) {
/* 328:391 */               par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S20PacketEntityProperties(this.myEntity.getEntityId(), var8));
/* 329:    */             }
/* 330:    */           }
/* 331:395 */           this.motionX = this.myEntity.motionX;
/* 332:396 */           this.motionY = this.myEntity.motionY;
/* 333:397 */           this.motionZ = this.myEntity.motionZ;
/* 334:399 */           if ((this.sendVelocityUpdates) && (!(var6 instanceof S0FPacketSpawnMob))) {
/* 335:401 */             par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(this.myEntity.getEntityId(), this.myEntity.motionX, this.myEntity.motionY, this.myEntity.motionZ));
/* 336:    */           }
/* 337:404 */           if (this.myEntity.ridingEntity != null) {
/* 338:406 */             par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this.myEntity, this.myEntity.ridingEntity));
/* 339:    */           }
/* 340:409 */           if (((this.myEntity instanceof EntityLiving)) && (((EntityLiving)this.myEntity).getLeashedToEntity() != null)) {
/* 341:411 */             par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(1, this.myEntity, ((EntityLiving)this.myEntity).getLeashedToEntity()));
/* 342:    */           }
/* 343:414 */           if ((this.myEntity instanceof EntityLivingBase)) {
/* 344:416 */             for (int var10 = 0; var10 < 5; var10++)
/* 345:    */             {
/* 346:418 */               ItemStack var13 = ((EntityLivingBase)this.myEntity).getEquipmentInSlot(var10);
/* 347:420 */               if (var13 != null) {
/* 348:422 */                 par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(this.myEntity.getEntityId(), var10, var13));
/* 349:    */               }
/* 350:    */             }
/* 351:    */           }
/* 352:427 */           if ((this.myEntity instanceof EntityPlayer))
/* 353:    */           {
/* 354:429 */             EntityPlayer var11 = (EntityPlayer)this.myEntity;
/* 355:431 */             if (var11.isPlayerSleeping()) {
/* 356:433 */               par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S0APacketUseBed(var11, MathHelper.floor_double(this.myEntity.posX), MathHelper.floor_double(this.myEntity.posY), MathHelper.floor_double(this.myEntity.posZ)));
/* 357:    */             }
/* 358:    */           }
/* 359:437 */           if ((this.myEntity instanceof EntityLivingBase))
/* 360:    */           {
/* 361:439 */             EntityLivingBase var14 = (EntityLivingBase)this.myEntity;
/* 362:440 */             Iterator var12 = var14.getActivePotionEffects().iterator();
/* 363:442 */             while (var12.hasNext())
/* 364:    */             {
/* 365:444 */               PotionEffect var9 = (PotionEffect)var12.next();
/* 366:445 */               par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.myEntity.getEntityId(), var9));
/* 367:    */             }
/* 368:    */           }
/* 369:    */         }
/* 370:    */       }
/* 371:450 */       else if (this.trackingPlayers.contains(par1EntityPlayerMP))
/* 372:    */       {
/* 373:452 */         this.trackingPlayers.remove(par1EntityPlayerMP);
/* 374:453 */         par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.getEntityId()));
/* 375:    */       }
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   private boolean isPlayerWatchingThisChunk(EntityPlayerMP par1EntityPlayerMP)
/* 380:    */   {
/* 381:460 */     return par1EntityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(par1EntityPlayerMP, this.myEntity.chunkCoordX, this.myEntity.chunkCoordZ);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void sendEventsToPlayers(List par1List)
/* 385:    */   {
/* 386:465 */     for (int var2 = 0; var2 < par1List.size(); var2++) {
/* 387:467 */       tryStartWachingThis((EntityPlayerMP)par1List.get(var2));
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   private Packet func_151260_c()
/* 392:    */   {
/* 393:473 */     if (this.myEntity.isDead) {
/* 394:475 */       logger.warn("Fetching addPacket for removed entity");
/* 395:    */     }
/* 396:478 */     if ((this.myEntity instanceof EntityItem)) {
/* 397:480 */       return new S0EPacketSpawnObject(this.myEntity, 2, 1);
/* 398:    */     }
/* 399:482 */     if ((this.myEntity instanceof EntityPlayerMP)) {
/* 400:484 */       return new S0CPacketSpawnPlayer((EntityPlayer)this.myEntity);
/* 401:    */     }
/* 402:486 */     if ((this.myEntity instanceof EntityMinecart))
/* 403:    */     {
/* 404:488 */       EntityMinecart var9 = (EntityMinecart)this.myEntity;
/* 405:489 */       return new S0EPacketSpawnObject(this.myEntity, 10, var9.getMinecartType());
/* 406:    */     }
/* 407:491 */     if ((this.myEntity instanceof EntityBoat)) {
/* 408:493 */       return new S0EPacketSpawnObject(this.myEntity, 1);
/* 409:    */     }
/* 410:495 */     if ((!(this.myEntity instanceof IAnimals)) && (!(this.myEntity instanceof EntityDragon)))
/* 411:    */     {
/* 412:497 */       if ((this.myEntity instanceof EntityFishHook))
/* 413:    */       {
/* 414:499 */         EntityPlayer var8 = ((EntityFishHook)this.myEntity).field_146042_b;
/* 415:500 */         return new S0EPacketSpawnObject(this.myEntity, 90, var8 != null ? var8.getEntityId() : this.myEntity.getEntityId());
/* 416:    */       }
/* 417:502 */       if ((this.myEntity instanceof EntityArrow))
/* 418:    */       {
/* 419:504 */         Entity var7 = ((EntityArrow)this.myEntity).shootingEntity;
/* 420:505 */         return new S0EPacketSpawnObject(this.myEntity, 60, var7 != null ? var7.getEntityId() : this.myEntity.getEntityId());
/* 421:    */       }
/* 422:507 */       if ((this.myEntity instanceof EntitySnowball)) {
/* 423:509 */         return new S0EPacketSpawnObject(this.myEntity, 61);
/* 424:    */       }
/* 425:511 */       if ((this.myEntity instanceof EntityPotion)) {
/* 426:513 */         return new S0EPacketSpawnObject(this.myEntity, 73, ((EntityPotion)this.myEntity).getPotionDamage());
/* 427:    */       }
/* 428:515 */       if ((this.myEntity instanceof EntityExpBottle)) {
/* 429:517 */         return new S0EPacketSpawnObject(this.myEntity, 75);
/* 430:    */       }
/* 431:519 */       if ((this.myEntity instanceof EntityEnderPearl)) {
/* 432:521 */         return new S0EPacketSpawnObject(this.myEntity, 65);
/* 433:    */       }
/* 434:523 */       if ((this.myEntity instanceof EntityEnderEye)) {
/* 435:525 */         return new S0EPacketSpawnObject(this.myEntity, 72);
/* 436:    */       }
/* 437:527 */       if ((this.myEntity instanceof EntityFireworkRocket)) {
/* 438:529 */         return new S0EPacketSpawnObject(this.myEntity, 76);
/* 439:    */       }
/* 440:535 */       if ((this.myEntity instanceof EntityFireball))
/* 441:    */       {
/* 442:537 */         EntityFireball var6 = (EntityFireball)this.myEntity;
/* 443:538 */         S0EPacketSpawnObject var2 = null;
/* 444:539 */         byte var3 = 63;
/* 445:541 */         if ((this.myEntity instanceof EntitySmallFireball)) {
/* 446:543 */           var3 = 64;
/* 447:545 */         } else if ((this.myEntity instanceof EntityWitherSkull)) {
/* 448:547 */           var3 = 66;
/* 449:    */         }
/* 450:550 */         if (var6.shootingEntity != null) {
/* 451:552 */           var2 = new S0EPacketSpawnObject(this.myEntity, var3, ((EntityFireball)this.myEntity).shootingEntity.getEntityId());
/* 452:    */         } else {
/* 453:556 */           var2 = new S0EPacketSpawnObject(this.myEntity, var3, 0);
/* 454:    */         }
/* 455:559 */         var2.func_149003_d((int)(var6.accelerationX * 8000.0D));
/* 456:560 */         var2.func_149000_e((int)(var6.accelerationY * 8000.0D));
/* 457:561 */         var2.func_149007_f((int)(var6.accelerationZ * 8000.0D));
/* 458:562 */         return var2;
/* 459:    */       }
/* 460:564 */       if ((this.myEntity instanceof EntityEgg)) {
/* 461:566 */         return new S0EPacketSpawnObject(this.myEntity, 62);
/* 462:    */       }
/* 463:568 */       if ((this.myEntity instanceof EntityTNTPrimed)) {
/* 464:570 */         return new S0EPacketSpawnObject(this.myEntity, 50);
/* 465:    */       }
/* 466:572 */       if ((this.myEntity instanceof EntityEnderCrystal)) {
/* 467:574 */         return new S0EPacketSpawnObject(this.myEntity, 51);
/* 468:    */       }
/* 469:576 */       if ((this.myEntity instanceof EntityFallingBlock))
/* 470:    */       {
/* 471:578 */         EntityFallingBlock var5 = (EntityFallingBlock)this.myEntity;
/* 472:579 */         return new S0EPacketSpawnObject(this.myEntity, 70, Block.getIdFromBlock(var5.func_145805_f()) | var5.field_145814_a << 16);
/* 473:    */       }
/* 474:581 */       if ((this.myEntity instanceof EntityPainting)) {
/* 475:583 */         return new S10PacketSpawnPainting((EntityPainting)this.myEntity);
/* 476:    */       }
/* 477:585 */       if ((this.myEntity instanceof EntityItemFrame))
/* 478:    */       {
/* 479:587 */         EntityItemFrame var4 = (EntityItemFrame)this.myEntity;
/* 480:588 */         S0EPacketSpawnObject var2 = new S0EPacketSpawnObject(this.myEntity, 71, var4.hangingDirection);
/* 481:589 */         var2.func_148996_a(MathHelper.floor_float(var4.field_146063_b * 32));
/* 482:590 */         var2.func_148995_b(MathHelper.floor_float(var4.field_146064_c * 32));
/* 483:591 */         var2.func_149005_c(MathHelper.floor_float(var4.field_146062_d * 32));
/* 484:592 */         return var2;
/* 485:    */       }
/* 486:594 */       if ((this.myEntity instanceof EntityLeashKnot))
/* 487:    */       {
/* 488:596 */         EntityLeashKnot var1 = (EntityLeashKnot)this.myEntity;
/* 489:597 */         S0EPacketSpawnObject var2 = new S0EPacketSpawnObject(this.myEntity, 77);
/* 490:598 */         var2.func_148996_a(MathHelper.floor_float(var1.field_146063_b * 32));
/* 491:599 */         var2.func_148995_b(MathHelper.floor_float(var1.field_146064_c * 32));
/* 492:600 */         var2.func_149005_c(MathHelper.floor_float(var1.field_146062_d * 32));
/* 493:601 */         return var2;
/* 494:    */       }
/* 495:603 */       if ((this.myEntity instanceof EntityXPOrb)) {
/* 496:605 */         return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.myEntity);
/* 497:    */       }
/* 498:609 */       throw new IllegalArgumentException("Don't know how to add " + this.myEntity.getClass() + "!");
/* 499:    */     }
/* 500:615 */     this.lastHeadMotion = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 501:616 */     return new S0FPacketSpawnMob((EntityLivingBase)this.myEntity);
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void removePlayerFromTracker(EntityPlayerMP par1EntityPlayerMP)
/* 505:    */   {
/* 506:622 */     if (this.trackingPlayers.contains(par1EntityPlayerMP))
/* 507:    */     {
/* 508:624 */       this.trackingPlayers.remove(par1EntityPlayerMP);
/* 509:625 */       par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(this.myEntity.getEntityId()));
/* 510:    */     }
/* 511:    */   }
/* 512:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityTrackerEntry
 * JD-Core Version:    0.7.0.1
 */