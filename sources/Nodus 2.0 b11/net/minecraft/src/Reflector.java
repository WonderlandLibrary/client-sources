/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.potion.PotionEffect;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ import net.minecraft.world.WorldProvider;
/*  17:    */ 
/*  18:    */ public class Reflector
/*  19:    */ {
/*  20: 20 */   public static ReflectorClass ModLoader = new ReflectorClass("ModLoader");
/*  21: 21 */   public static ReflectorMethod ModLoader_renderWorldBlock = new ReflectorMethod(ModLoader, "renderWorldBlock");
/*  22: 22 */   public static ReflectorMethod ModLoader_renderInvBlock = new ReflectorMethod(ModLoader, "renderInvBlock");
/*  23: 23 */   public static ReflectorMethod ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(ModLoader, "renderBlockIsItemFull3D");
/*  24: 24 */   public static ReflectorMethod ModLoader_registerServer = new ReflectorMethod(ModLoader, "registerServer");
/*  25: 25 */   public static ReflectorMethod ModLoader_getCustomAnimationLogic = new ReflectorMethod(ModLoader, "getCustomAnimationLogic");
/*  26: 26 */   public static ReflectorClass FMLRenderAccessLibrary = new ReflectorClass("net.minecraft.src.FMLRenderAccessLibrary");
/*  27: 27 */   public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderWorldBlock");
/*  28: 28 */   public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderInventoryBlock");
/*  29: 29 */   public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
/*  30: 30 */   public static ReflectorClass LightCache = new ReflectorClass("LightCache");
/*  31: 31 */   public static ReflectorField LightCache_cache = new ReflectorField(LightCache, "cache");
/*  32: 32 */   public static ReflectorMethod LightCache_clear = new ReflectorMethod(LightCache, "clear");
/*  33: 33 */   public static ReflectorClass BlockCoord = new ReflectorClass("BlockCoord");
/*  34: 34 */   public static ReflectorMethod BlockCoord_resetPool = new ReflectorMethod(BlockCoord, "resetPool");
/*  35: 35 */   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/*  36: 36 */   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/*  37: 37 */   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/*  38: 38 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/*  39: 39 */   public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
/*  40: 40 */   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
/*  41: 41 */   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
/*  42: 42 */   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
/*  43: 43 */   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
/*  44: 44 */   public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
/*  45: 45 */   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
/*  46: 46 */   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/*  47: 47 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/*  48: 48 */   public static ReflectorMethod MinecraftForgeClient_getItemRenderer = new ReflectorMethod(MinecraftForgeClient, "getItemRenderer");
/*  49: 49 */   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/*  50: 50 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/*  51: 51 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/*  52: 52 */   public static ReflectorMethod ForgeHooksClient_renderEquippedItem = new ReflectorMethod(ForgeHooksClient, "renderEquippedItem");
/*  53: 53 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/*  54: 54 */   public static ReflectorMethod ForgeHooksClient_onTextureLoadPre = new ReflectorMethod(ForgeHooksClient, "onTextureLoadPre");
/*  55: 55 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/*  56: 56 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/*  57: 57 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/*  58: 58 */   public static ReflectorClass FMLCommonHandler = new ReflectorClass("cpw.mods.fml.common.FMLCommonHandler");
/*  59: 59 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/*  60: 60 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/*  61: 61 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/*  62: 62 */   public static ReflectorClass FMLClientHandler = new ReflectorClass("cpw.mods.fml.client.FMLClientHandler");
/*  63: 63 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/*  64: 64 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/*  65: 65 */   public static ReflectorClass ItemRenderType = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
/*  66: 66 */   public static ReflectorField ItemRenderType_EQUIPPED = new ReflectorField(ItemRenderType, "EQUIPPED");
/*  67: 67 */   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
/*  68: 68 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/*  69: 69 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/*  70: 70 */   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
/*  71: 71 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/*  72: 72 */   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/*  73: 73 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/*  74: 74 */   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/*  75: 75 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { World.class });
/*  76: 76 */   public static ReflectorClass EventBus = new ReflectorClass("cpw.mods.fml.common.eventhandler.EventBus");
/*  77: 77 */   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/*  78: 78 */   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
/*  79: 79 */   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
/*  80: 80 */   public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/*  81: 81 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/*  82: 82 */   public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
/*  83: 83 */   public static ReflectorMethod ForgeBlock_canRenderInPass = new ReflectorMethod(ForgeBlock, "canRenderInPass");
/*  84: 84 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { Integer.TYPE });
/*  85: 85 */   public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
/*  86: 86 */   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
/*  87: 87 */   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
/*  88: 88 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/*  89: 89 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/*  90: 90 */   public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
/*  91: 91 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/*  92: 92 */   public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
/*  93: 93 */   public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
/*  94: 94 */   public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
/*  95: 95 */   public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
/*  96: 96 */   public static ReflectorClass ForgeItemStack = new ReflectorClass(ItemStack.class);
/*  97: 97 */   public static ReflectorMethod ForgeItemStack_hasEffect = new ReflectorMethod(ForgeItemStack, "hasEffect", new Class[] { Integer.TYPE });
/*  98:    */   
/*  99:    */   public static void callVoid(ReflectorMethod refMethod, Object... params)
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:103 */       Method e = refMethod.getTargetMethod();
/* 104:105 */       if (e == null) {
/* 105:107 */         return;
/* 106:    */       }
/* 107:110 */       e.invoke(null, params);
/* 108:    */     }
/* 109:    */     catch (Throwable var3)
/* 110:    */     {
/* 111:114 */       handleException(var3, null, refMethod, params);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static boolean callBoolean(ReflectorMethod refMethod, Object... params)
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:122 */       Method e = refMethod.getTargetMethod();
/* 120:124 */       if (e == null) {
/* 121:126 */         return false;
/* 122:    */       }
/* 123:130 */       Boolean retVal = (Boolean)e.invoke(null, params);
/* 124:131 */       return retVal.booleanValue();
/* 125:    */     }
/* 126:    */     catch (Throwable var4)
/* 127:    */     {
/* 128:136 */       handleException(var4, null, refMethod, params);
/* 129:    */     }
/* 130:137 */     return false;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static int callInt(ReflectorMethod refMethod, Object... params)
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:145 */       Method e = refMethod.getTargetMethod();
/* 138:147 */       if (e == null) {
/* 139:149 */         return 0;
/* 140:    */       }
/* 141:153 */       Integer retVal = (Integer)e.invoke(null, params);
/* 142:154 */       return retVal.intValue();
/* 143:    */     }
/* 144:    */     catch (Throwable var4)
/* 145:    */     {
/* 146:159 */       handleException(var4, null, refMethod, params);
/* 147:    */     }
/* 148:160 */     return 0;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static float callFloat(ReflectorMethod refMethod, Object... params)
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:168 */       Method e = refMethod.getTargetMethod();
/* 156:170 */       if (e == null) {
/* 157:172 */         return 0.0F;
/* 158:    */       }
/* 159:176 */       Float retVal = (Float)e.invoke(null, params);
/* 160:177 */       return retVal.floatValue();
/* 161:    */     }
/* 162:    */     catch (Throwable var4)
/* 163:    */     {
/* 164:182 */       handleException(var4, null, refMethod, params);
/* 165:    */     }
/* 166:183 */     return 0.0F;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static String callString(ReflectorMethod refMethod, Object... params)
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:191 */       Method e = refMethod.getTargetMethod();
/* 174:193 */       if (e == null) {
/* 175:195 */         return null;
/* 176:    */       }
/* 177:199 */       return (String)e.invoke(null, params);
/* 178:    */     }
/* 179:    */     catch (Throwable var4)
/* 180:    */     {
/* 181:205 */       handleException(var4, null, refMethod, params);
/* 182:    */     }
/* 183:206 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static Object call(ReflectorMethod refMethod, Object... params)
/* 187:    */   {
/* 188:    */     try
/* 189:    */     {
/* 190:214 */       Method e = refMethod.getTargetMethod();
/* 191:216 */       if (e == null) {
/* 192:218 */         return null;
/* 193:    */       }
/* 194:222 */       return e.invoke(null, params);
/* 195:    */     }
/* 196:    */     catch (Throwable var4)
/* 197:    */     {
/* 198:228 */       handleException(var4, null, refMethod, params);
/* 199:    */     }
/* 200:229 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params)
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:237 */       if (obj == null) {
/* 208:239 */         return;
/* 209:    */       }
/* 210:242 */       Method e = refMethod.getTargetMethod();
/* 211:244 */       if (e == null) {
/* 212:246 */         return;
/* 213:    */       }
/* 214:249 */       e.invoke(obj, params);
/* 215:    */     }
/* 216:    */     catch (Throwable var4)
/* 217:    */     {
/* 218:253 */       handleException(var4, obj, refMethod, params);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params)
/* 223:    */   {
/* 224:    */     try
/* 225:    */     {
/* 226:261 */       Method e = refMethod.getTargetMethod();
/* 227:263 */       if (e == null) {
/* 228:265 */         return false;
/* 229:    */       }
/* 230:269 */       Boolean retVal = (Boolean)e.invoke(obj, params);
/* 231:270 */       return retVal.booleanValue();
/* 232:    */     }
/* 233:    */     catch (Throwable var5)
/* 234:    */     {
/* 235:275 */       handleException(var5, obj, refMethod, params);
/* 236:    */     }
/* 237:276 */     return false;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params)
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:284 */       Method e = refMethod.getTargetMethod();
/* 245:286 */       if (e == null) {
/* 246:288 */         return 0;
/* 247:    */       }
/* 248:292 */       Integer retVal = (Integer)e.invoke(obj, params);
/* 249:293 */       return retVal.intValue();
/* 250:    */     }
/* 251:    */     catch (Throwable var5)
/* 252:    */     {
/* 253:298 */       handleException(var5, obj, refMethod, params);
/* 254:    */     }
/* 255:299 */     return 0;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params)
/* 259:    */   {
/* 260:    */     try
/* 261:    */     {
/* 262:307 */       Method e = refMethod.getTargetMethod();
/* 263:309 */       if (e == null) {
/* 264:311 */         return 0.0F;
/* 265:    */       }
/* 266:315 */       Float retVal = (Float)e.invoke(obj, params);
/* 267:316 */       return retVal.floatValue();
/* 268:    */     }
/* 269:    */     catch (Throwable var5)
/* 270:    */     {
/* 271:321 */       handleException(var5, obj, refMethod, params);
/* 272:    */     }
/* 273:322 */     return 0.0F;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public static String callString(Object obj, ReflectorMethod refMethod, Object... params)
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:330 */       Method e = refMethod.getTargetMethod();
/* 281:332 */       if (e == null) {
/* 282:334 */         return null;
/* 283:    */       }
/* 284:338 */       return (String)e.invoke(obj, params);
/* 285:    */     }
/* 286:    */     catch (Throwable var5)
/* 287:    */     {
/* 288:344 */       handleException(var5, obj, refMethod, params);
/* 289:    */     }
/* 290:345 */     return null;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static Object call(Object obj, ReflectorMethod refMethod, Object... params)
/* 294:    */   {
/* 295:    */     try
/* 296:    */     {
/* 297:353 */       Method e = refMethod.getTargetMethod();
/* 298:355 */       if (e == null) {
/* 299:357 */         return null;
/* 300:    */       }
/* 301:361 */       return e.invoke(obj, params);
/* 302:    */     }
/* 303:    */     catch (Throwable var5)
/* 304:    */     {
/* 305:367 */       handleException(var5, obj, refMethod, params);
/* 306:    */     }
/* 307:368 */     return null;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public static Object getFieldValue(ReflectorField refField)
/* 311:    */   {
/* 312:374 */     return getFieldValue(null, refField);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public static Object getFieldValue(Object obj, ReflectorField refField)
/* 316:    */   {
/* 317:    */     try
/* 318:    */     {
/* 319:381 */       Field e = refField.getTargetField();
/* 320:383 */       if (e == null) {
/* 321:385 */         return null;
/* 322:    */       }
/* 323:389 */       return e.get(obj);
/* 324:    */     }
/* 325:    */     catch (Throwable var4)
/* 326:    */     {
/* 327:395 */       var4.printStackTrace();
/* 328:    */     }
/* 329:396 */     return null;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public static void setFieldValue(ReflectorField refField, Object value)
/* 333:    */   {
/* 334:402 */     setFieldValue(null, refField, value);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static void setFieldValue(Object obj, ReflectorField refField, Object value)
/* 338:    */   {
/* 339:    */     try
/* 340:    */     {
/* 341:409 */       Field e = refField.getTargetField();
/* 342:411 */       if (e == null) {
/* 343:413 */         return;
/* 344:    */       }
/* 345:416 */       e.set(obj, value);
/* 346:    */     }
/* 347:    */     catch (Throwable var4)
/* 348:    */     {
/* 349:420 */       var4.printStackTrace();
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static void postForgeBusEvent(ReflectorConstructor constr, Object... params)
/* 354:    */   {
/* 355:    */     try
/* 356:    */     {
/* 357:428 */       Object e = getFieldValue(MinecraftForge_EVENT_BUS);
/* 358:430 */       if (e == null) {
/* 359:432 */         return;
/* 360:    */       }
/* 361:435 */       Constructor c = constr.getTargetConstructor();
/* 362:437 */       if (c == null) {
/* 363:439 */         return;
/* 364:    */       }
/* 365:442 */       Object event = c.newInstance(params);
/* 366:443 */       callVoid(e, EventBus_post, new Object[] { event });
/* 367:    */     }
/* 368:    */     catch (Throwable var5)
/* 369:    */     {
/* 370:447 */       var5.printStackTrace();
/* 371:    */     }
/* 372:    */   }
/* 373:    */   
/* 374:    */   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes)
/* 375:    */   {
/* 376:453 */     if (pTypes.length != cTypes.length) {
/* 377:455 */       return false;
/* 378:    */     }
/* 379:459 */     for (int i = 0; i < cTypes.length; i++)
/* 380:    */     {
/* 381:461 */       Class pType = pTypes[i];
/* 382:462 */       Class cType = cTypes[i];
/* 383:464 */       if (pType != cType) {
/* 384:466 */         return false;
/* 385:    */       }
/* 386:    */     }
/* 387:470 */     return true;
/* 388:    */   }
/* 389:    */   
/* 390:    */   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal)
/* 391:    */   {
/* 392:476 */     String className = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 393:477 */     String methodName = refMethod.getTargetMethod().getName();
/* 394:478 */     String staticStr = "";
/* 395:480 */     if (isStatic) {
/* 396:482 */       staticStr = " static";
/* 397:    */     }
/* 398:485 */     Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ") => " + retVal);
/* 399:    */   }
/* 400:    */   
/* 401:    */   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params)
/* 402:    */   {
/* 403:490 */     String className = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 404:491 */     String methodName = refMethod.getTargetMethod().getName();
/* 405:492 */     String staticStr = "";
/* 406:494 */     if (isStatic) {
/* 407:496 */       staticStr = " static";
/* 408:    */     }
/* 409:499 */     Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ")");
/* 410:    */   }
/* 411:    */   
/* 412:    */   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val)
/* 413:    */   {
/* 414:504 */     String className = refField.getTargetField().getDeclaringClass().getName();
/* 415:505 */     String fieldName = refField.getTargetField().getName();
/* 416:506 */     String staticStr = "";
/* 417:508 */     if (isStatic) {
/* 418:510 */       staticStr = " static";
/* 419:    */     }
/* 420:513 */     Config.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + val);
/* 421:    */   }
/* 422:    */   
/* 423:    */   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params)
/* 424:    */   {
/* 425:518 */     if ((e instanceof InvocationTargetException))
/* 426:    */     {
/* 427:520 */       e.printStackTrace();
/* 428:    */     }
/* 429:    */     else
/* 430:    */     {
/* 431:524 */       if ((e instanceof IllegalArgumentException))
/* 432:    */       {
/* 433:526 */         Config.warn("*** IllegalArgumentException ***");
/* 434:527 */         Config.warn("Method: " + refMethod.getTargetMethod());
/* 435:528 */         Config.warn("Object: " + obj);
/* 436:529 */         Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
/* 437:530 */         Config.warn("Parameters: " + Config.arrayToString(params));
/* 438:    */       }
/* 439:533 */       Config.warn("*** Exception outside of method ***");
/* 440:534 */       Config.warn("Method deactivated: " + refMethod.getTargetMethod());
/* 441:535 */       refMethod.deactivate();
/* 442:536 */       e.printStackTrace();
/* 443:    */     }
/* 444:    */   }
/* 445:    */   
/* 446:    */   private static Object[] getClasses(Object[] objs)
/* 447:    */   {
/* 448:542 */     if (objs == null) {
/* 449:544 */       return new Class[0];
/* 450:    */     }
/* 451:548 */     Class[] classes = new Class[objs.length];
/* 452:550 */     for (int i = 0; i < classes.length; i++)
/* 453:    */     {
/* 454:552 */       Object obj = objs[i];
/* 455:554 */       if (obj != null) {
/* 456:556 */         classes[i] = obj.getClass();
/* 457:    */       }
/* 458:    */     }
/* 459:560 */     return classes;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public static Field getFieldByType(Class cls, Class fieldType)
/* 463:    */   {
/* 464:    */     try
/* 465:    */     {
/* 466:568 */       Field[] e = cls.getDeclaredFields();
/* 467:570 */       for (int i = 0; i < e.length; i++)
/* 468:    */       {
/* 469:572 */         Field field = e[i];
/* 470:574 */         if (field.getType() == fieldType)
/* 471:    */         {
/* 472:576 */           field.setAccessible(true);
/* 473:577 */           return field;
/* 474:    */         }
/* 475:    */       }
/* 476:581 */       return null;
/* 477:    */     }
/* 478:    */     catch (Exception var5) {}
/* 479:585 */     return null;
/* 480:    */   }
/* 481:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.Reflector
 * JD-Core Version:    0.7.0.1
 */