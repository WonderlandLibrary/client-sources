/*    1:     */ package net.minecraft.client.renderer;
/*    2:     */ 
/*    3:     */ import com.google.common.collect.Maps;
/*    4:     */ import java.nio.IntBuffer;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Arrays;
/*    7:     */ import java.util.Collection;
/*    8:     */ import java.util.HashMap;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.List;
/*   11:     */ import java.util.Map;
/*   12:     */ import java.util.Random;
/*   13:     */ import java.util.concurrent.Callable;
/*   14:     */ import net.minecraft.block.Block;
/*   15:     */ import net.minecraft.block.Block.SoundType;
/*   16:     */ import net.minecraft.block.BlockChest;
/*   17:     */ import net.minecraft.block.BlockLeaves;
/*   18:     */ import net.minecraft.block.material.Material;
/*   19:     */ import net.minecraft.client.Minecraft;
/*   20:     */ import net.minecraft.client.audio.ISound;
/*   21:     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*   22:     */ import net.minecraft.client.audio.SoundHandler;
/*   23:     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*   24:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   25:     */ import net.minecraft.client.gui.FontRenderer;
/*   26:     */ import net.minecraft.client.gui.GuiIngame;
/*   27:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   28:     */ import net.minecraft.client.particle.EffectRenderer;
/*   29:     */ import net.minecraft.client.particle.EntityAuraFX;
/*   30:     */ import net.minecraft.client.particle.EntityBlockDustFX;
/*   31:     */ import net.minecraft.client.particle.EntityBreakingFX;
/*   32:     */ import net.minecraft.client.particle.EntityBubbleFX;
/*   33:     */ import net.minecraft.client.particle.EntityCloudFX;
/*   34:     */ import net.minecraft.client.particle.EntityCritFX;
/*   35:     */ import net.minecraft.client.particle.EntityDiggingFX;
/*   36:     */ import net.minecraft.client.particle.EntityDropParticleFX;
/*   37:     */ import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
/*   38:     */ import net.minecraft.client.particle.EntityExplodeFX;
/*   39:     */ import net.minecraft.client.particle.EntityFX;
/*   40:     */ import net.minecraft.client.particle.EntityFireworkSparkFX;
/*   41:     */ import net.minecraft.client.particle.EntityFishWakeFX;
/*   42:     */ import net.minecraft.client.particle.EntityFlameFX;
/*   43:     */ import net.minecraft.client.particle.EntityFootStepFX;
/*   44:     */ import net.minecraft.client.particle.EntityHeartFX;
/*   45:     */ import net.minecraft.client.particle.EntityHugeExplodeFX;
/*   46:     */ import net.minecraft.client.particle.EntityLargeExplodeFX;
/*   47:     */ import net.minecraft.client.particle.EntityLavaFX;
/*   48:     */ import net.minecraft.client.particle.EntityNoteFX;
/*   49:     */ import net.minecraft.client.particle.EntityPortalFX;
/*   50:     */ import net.minecraft.client.particle.EntityReddustFX;
/*   51:     */ import net.minecraft.client.particle.EntitySmokeFX;
/*   52:     */ import net.minecraft.client.particle.EntitySnowShovelFX;
/*   53:     */ import net.minecraft.client.particle.EntitySpellParticleFX;
/*   54:     */ import net.minecraft.client.particle.EntitySplashFX;
/*   55:     */ import net.minecraft.client.particle.EntitySuspendFX;
/*   56:     */ import net.minecraft.client.renderer.culling.ICamera;
/*   57:     */ import net.minecraft.client.renderer.entity.Render;
/*   58:     */ import net.minecraft.client.renderer.entity.RenderManager;
/*   59:     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   60:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   61:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   62:     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*   63:     */ import net.minecraft.client.settings.GameSettings;
/*   64:     */ import net.minecraft.client.util.RenderDistanceSorter;
/*   65:     */ import net.minecraft.crash.CrashReport;
/*   66:     */ import net.minecraft.crash.CrashReportCategory;
/*   67:     */ import net.minecraft.entity.Entity;
/*   68:     */ import net.minecraft.entity.EntityLiving;
/*   69:     */ import net.minecraft.entity.EntityLivingBase;
/*   70:     */ import net.minecraft.entity.item.EntityItemFrame;
/*   71:     */ import net.minecraft.entity.player.EntityPlayer;
/*   72:     */ import net.minecraft.init.Blocks;
/*   73:     */ import net.minecraft.init.Items;
/*   74:     */ import net.minecraft.item.Item;
/*   75:     */ import net.minecraft.item.ItemDye;
/*   76:     */ import net.minecraft.item.ItemPotion;
/*   77:     */ import net.minecraft.item.ItemRecord;
/*   78:     */ import net.minecraft.profiler.Profiler;
/*   79:     */ import net.minecraft.src.CompactArrayList;
/*   80:     */ import net.minecraft.src.Config;
/*   81:     */ import net.minecraft.src.CustomColorizer;
/*   82:     */ import net.minecraft.src.CustomSky;
/*   83:     */ import net.minecraft.src.EntitySorterFast;
/*   84:     */ import net.minecraft.src.RandomMobs;
/*   85:     */ import net.minecraft.src.Reflector;
/*   86:     */ import net.minecraft.src.ReflectorMethod;
/*   87:     */ import net.minecraft.src.WrUpdates;
/*   88:     */ import net.minecraft.tileentity.TileEntity;
/*   89:     */ import net.minecraft.tileentity.TileEntityChest;
/*   90:     */ import net.minecraft.tileentity.TileEntitySign;
/*   91:     */ import net.minecraft.util.AABBPool;
/*   92:     */ import net.minecraft.util.AxisAlignedBB;
/*   93:     */ import net.minecraft.util.ChunkCoordinates;
/*   94:     */ import net.minecraft.util.IIcon;
/*   95:     */ import net.minecraft.util.MathHelper;
/*   96:     */ import net.minecraft.util.MovingObjectPosition;
/*   97:     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*   98:     */ import net.minecraft.util.ReportedException;
/*   99:     */ import net.minecraft.util.ResourceLocation;
/*  100:     */ import net.minecraft.util.Vec3;
/*  101:     */ import net.minecraft.world.IWorldAccess;
/*  102:     */ import net.minecraft.world.WorldProvider;
/*  103:     */ import org.apache.logging.log4j.LogManager;
/*  104:     */ import org.apache.logging.log4j.Logger;
/*  105:     */ import org.lwjgl.BufferUtils;
/*  106:     */ import org.lwjgl.input.Mouse;
/*  107:     */ import org.lwjgl.opengl.ARBOcclusionQuery;
/*  108:     */ import org.lwjgl.opengl.GL11;
/*  109:     */ 
/*  110:     */ public class RenderGlobal
/*  111:     */   implements IWorldAccess
/*  112:     */ {
/*  113: 100 */   private static final Logger logger = ;
/*  114: 101 */   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
/*  115: 102 */   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
/*  116: 103 */   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
/*  117: 104 */   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
/*  118: 105 */   public List tileEntities = new ArrayList();
/*  119:     */   public WorldClient theWorld;
/*  120:     */   public final TextureManager renderEngine;
/*  121: 110 */   public CompactArrayList worldRenderersToUpdate = new CompactArrayList(100, 0.8F);
/*  122:     */   private WorldRenderer[] sortedWorldRenderers;
/*  123:     */   private WorldRenderer[] worldRenderers;
/*  124:     */   private int renderChunksWide;
/*  125:     */   private int renderChunksTall;
/*  126:     */   private int renderChunksDeep;
/*  127:     */   public int glRenderListBase;
/*  128:     */   public Minecraft mc;
/*  129:     */   public RenderBlocks renderBlocksRg;
/*  130:     */   private IntBuffer glOcclusionQueryBase;
/*  131:     */   private boolean occlusionEnabled;
/*  132:     */   private int cloudTickCounter;
/*  133:     */   private int starGLCallList;
/*  134:     */   private int glSkyList;
/*  135:     */   private int glSkyList2;
/*  136:     */   private int minBlockX;
/*  137:     */   private int minBlockY;
/*  138:     */   private int minBlockZ;
/*  139:     */   private int maxBlockX;
/*  140:     */   private int maxBlockY;
/*  141:     */   private int maxBlockZ;
/*  142: 166 */   public final Map damagedBlocks = new HashMap();
/*  143: 167 */   private final Map mapSoundPositions = Maps.newHashMap();
/*  144:     */   private IIcon[] destroyBlockIcons;
/*  145:     */   private boolean displayListEntitiesDirty;
/*  146:     */   private int displayListEntities;
/*  147: 171 */   private int renderDistanceChunks = -1;
/*  148: 174 */   private int renderEntitiesStartupCounter = 2;
/*  149:     */   private int countEntitiesTotal;
/*  150:     */   private int countEntitiesRendered;
/*  151:     */   private int countEntitiesHidden;
/*  152: 186 */   IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
/*  153:     */   private int renderersLoaded;
/*  154:     */   private int renderersBeingClipped;
/*  155:     */   private int renderersBeingOccluded;
/*  156:     */   private int renderersBeingRendered;
/*  157:     */   private int renderersSkippingRenderPass;
/*  158:     */   private int dummyRenderInt;
/*  159:     */   private int worldRenderersCheckIndex;
/*  160: 212 */   private List glRenderLists = new ArrayList();
/*  161: 215 */   private RenderList[] allRenderLists = { new RenderList(), new RenderList(), new RenderList(), new RenderList() };
/*  162: 221 */   double prevSortX = -9999.0D;
/*  163: 227 */   double prevSortY = -9999.0D;
/*  164: 233 */   double prevSortZ = -9999.0D;
/*  165: 234 */   double prevRenderSortX = -9999.0D;
/*  166: 235 */   double prevRenderSortY = -9999.0D;
/*  167: 236 */   double prevRenderSortZ = -9999.0D;
/*  168: 237 */   int prevChunkSortX = -999;
/*  169: 238 */   int prevChunkSortY = -999;
/*  170: 239 */   int prevChunkSortZ = -999;
/*  171:     */   int frustumCheckOffset;
/*  172: 245 */   private IntBuffer glListBuffer = BufferUtils.createIntBuffer(65536);
/*  173:     */   double prevReposX;
/*  174:     */   double prevReposY;
/*  175:     */   double prevReposZ;
/*  176:     */   public Entity renderedEntity;
/*  177: 250 */   private int glListClouds = -1;
/*  178: 251 */   private boolean isFancyGlListClouds = false;
/*  179: 252 */   private int cloudTickCounterGlList = -99999;
/*  180: 253 */   private double cloudPlayerX = 0.0D;
/*  181: 254 */   private double cloudPlayerY = 0.0D;
/*  182: 255 */   private double cloudPlayerZ = 0.0D;
/*  183: 256 */   private int countSortedWorldRenderers = 0;
/*  184: 257 */   private int effectivePreloadedChunks = 0;
/*  185: 258 */   private int vertexResortCounter = 30;
/*  186: 259 */   public int glExtendedListBase = 0;
/*  187:     */   public EntityLivingBase renderViewEntity;
/*  188: 261 */   private long lastMovedTime = System.currentTimeMillis();
/*  189: 262 */   private long lastActionTime = System.currentTimeMillis();
/*  190: 263 */   private static AxisAlignedBB AABB_INFINITE = AxisAlignedBB.getBoundingBox((-1.0D / 0.0D), (-1.0D / 0.0D), (-1.0D / 0.0D), (1.0D / 0.0D), (1.0D / 0.0D), (1.0D / 0.0D));
/*  191:     */   private static final String __OBFID = "CL_00000954";
/*  192:     */   
/*  193:     */   public RenderGlobal(Minecraft par1Minecraft)
/*  194:     */   {
/*  195: 268 */     this.glListClouds = GLAllocation.generateDisplayLists(1);
/*  196: 269 */     this.mc = par1Minecraft;
/*  197: 270 */     this.renderEngine = par1Minecraft.getTextureManager();
/*  198: 271 */     byte maxChunkDim = 65;
/*  199: 272 */     byte maxChunkHeight = 16;
/*  200: 273 */     int countWorldRenderers = maxChunkDim * maxChunkDim * maxChunkHeight;
/*  201: 274 */     int countStandardRenderLists = countWorldRenderers * 3;
/*  202: 275 */     int countListSmooth = countWorldRenderers * 2 * 2 * 16;
/*  203: 276 */     int countListThreaded = countWorldRenderers * 2;
/*  204: 277 */     int countExtendedRenderLists = Math.max(countListSmooth, countListThreaded);
/*  205: 278 */     int countAllRenderLists = countStandardRenderLists + countExtendedRenderLists;
/*  206: 279 */     this.glRenderListBase = GLAllocation.generateDisplayLists(countAllRenderLists);
/*  207: 280 */     this.glExtendedListBase = (this.glRenderListBase + countStandardRenderLists);
/*  208: 281 */     this.displayListEntitiesDirty = false;
/*  209: 282 */     this.displayListEntities = GLAllocation.generateDisplayLists(1);
/*  210: 283 */     this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
/*  211: 285 */     if (this.occlusionEnabled)
/*  212:     */     {
/*  213: 287 */       this.occlusionResult.clear();
/*  214: 288 */       this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(maxChunkDim * maxChunkDim * maxChunkHeight);
/*  215: 289 */       this.glOcclusionQueryBase.clear();
/*  216: 290 */       this.glOcclusionQueryBase.position(0);
/*  217: 291 */       this.glOcclusionQueryBase.limit(maxChunkDim * maxChunkDim * maxChunkHeight);
/*  218: 292 */       ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
/*  219:     */     }
/*  220: 295 */     this.starGLCallList = GLAllocation.generateDisplayLists(3);
/*  221: 296 */     GL11.glPushMatrix();
/*  222: 297 */     GL11.glNewList(this.starGLCallList, 4864);
/*  223: 298 */     renderStars();
/*  224: 299 */     GL11.glEndList();
/*  225: 300 */     GL11.glPopMatrix();
/*  226: 301 */     Tessellator var4 = Tessellator.instance;
/*  227: 302 */     this.glSkyList = (this.starGLCallList + 1);
/*  228: 303 */     GL11.glNewList(this.glSkyList, 4864);
/*  229: 304 */     byte var6 = 64;
/*  230: 305 */     int var7 = 256 / var6 + 2;
/*  231: 306 */     float var5 = 16.0F;
/*  232: 310 */     for (int var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6) {
/*  233: 312 */       for (int var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6)
/*  234:     */       {
/*  235: 314 */         var4.startDrawingQuads();
/*  236: 315 */         var4.addVertex(var8 + 0, var5, var9 + 0);
/*  237: 316 */         var4.addVertex(var8 + var6, var5, var9 + 0);
/*  238: 317 */         var4.addVertex(var8 + var6, var5, var9 + var6);
/*  239: 318 */         var4.addVertex(var8 + 0, var5, var9 + var6);
/*  240: 319 */         var4.draw();
/*  241:     */       }
/*  242:     */     }
/*  243: 323 */     GL11.glEndList();
/*  244: 324 */     this.glSkyList2 = (this.starGLCallList + 2);
/*  245: 325 */     GL11.glNewList(this.glSkyList2, 4864);
/*  246: 326 */     var5 = -16.0F;
/*  247: 327 */     var4.startDrawingQuads();
/*  248: 329 */     for (var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6) {
/*  249: 331 */       for (int var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6)
/*  250:     */       {
/*  251: 333 */         var4.addVertex(var8 + var6, var5, var9 + 0);
/*  252: 334 */         var4.addVertex(var8 + 0, var5, var9 + 0);
/*  253: 335 */         var4.addVertex(var8 + 0, var5, var9 + var6);
/*  254: 336 */         var4.addVertex(var8 + var6, var5, var9 + var6);
/*  255:     */       }
/*  256:     */     }
/*  257: 340 */     var4.draw();
/*  258: 341 */     GL11.glEndList();
/*  259:     */   }
/*  260:     */   
/*  261:     */   private void renderStars()
/*  262:     */   {
/*  263: 346 */     Random var1 = new Random(10842L);
/*  264: 347 */     Tessellator var2 = Tessellator.instance;
/*  265: 348 */     var2.startDrawingQuads();
/*  266: 350 */     for (int var3 = 0; var3 < 1500; var3++)
/*  267:     */     {
/*  268: 352 */       double var4 = var1.nextFloat() * 2.0F - 1.0F;
/*  269: 353 */       double var6 = var1.nextFloat() * 2.0F - 1.0F;
/*  270: 354 */       double var8 = var1.nextFloat() * 2.0F - 1.0F;
/*  271: 355 */       double var10 = 0.15F + var1.nextFloat() * 0.1F;
/*  272: 356 */       double var12 = var4 * var4 + var6 * var6 + var8 * var8;
/*  273: 358 */       if ((var12 < 1.0D) && (var12 > 0.01D))
/*  274:     */       {
/*  275: 360 */         var12 = 1.0D / Math.sqrt(var12);
/*  276: 361 */         var4 *= var12;
/*  277: 362 */         var6 *= var12;
/*  278: 363 */         var8 *= var12;
/*  279: 364 */         double var14 = var4 * 100.0D;
/*  280: 365 */         double var16 = var6 * 100.0D;
/*  281: 366 */         double var18 = var8 * 100.0D;
/*  282: 367 */         double var20 = Math.atan2(var4, var8);
/*  283: 368 */         double var22 = Math.sin(var20);
/*  284: 369 */         double var24 = Math.cos(var20);
/*  285: 370 */         double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
/*  286: 371 */         double var28 = Math.sin(var26);
/*  287: 372 */         double var30 = Math.cos(var26);
/*  288: 373 */         double var32 = var1.nextDouble() * 3.141592653589793D * 2.0D;
/*  289: 374 */         double var34 = Math.sin(var32);
/*  290: 375 */         double var36 = Math.cos(var32);
/*  291: 377 */         for (int var38 = 0; var38 < 4; var38++)
/*  292:     */         {
/*  293: 379 */           double var39 = 0.0D;
/*  294: 380 */           double var41 = ((var38 & 0x2) - 1) * var10;
/*  295: 381 */           double var43 = ((var38 + 1 & 0x2) - 1) * var10;
/*  296: 382 */           double var47 = var41 * var36 - var43 * var34;
/*  297: 383 */           double var49 = var43 * var36 + var41 * var34;
/*  298: 384 */           double var53 = var47 * var28 + var39 * var30;
/*  299: 385 */           double var55 = var39 * var28 - var47 * var30;
/*  300: 386 */           double var57 = var55 * var22 - var49 * var24;
/*  301: 387 */           double var61 = var49 * var22 + var55 * var24;
/*  302: 388 */           var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
/*  303:     */         }
/*  304:     */       }
/*  305:     */     }
/*  306: 393 */     var2.draw();
/*  307:     */   }
/*  308:     */   
/*  309:     */   public void setWorldAndLoadRenderers(WorldClient par1WorldClient)
/*  310:     */   {
/*  311: 401 */     if (this.theWorld != null) {
/*  312: 403 */       this.theWorld.removeWorldAccess(this);
/*  313:     */     }
/*  314: 406 */     this.prevSortX = -9999.0D;
/*  315: 407 */     this.prevSortY = -9999.0D;
/*  316: 408 */     this.prevSortZ = -9999.0D;
/*  317: 409 */     this.prevRenderSortX = -9999.0D;
/*  318: 410 */     this.prevRenderSortY = -9999.0D;
/*  319: 411 */     this.prevRenderSortZ = -9999.0D;
/*  320: 412 */     this.prevChunkSortX = -9999;
/*  321: 413 */     this.prevChunkSortY = -9999;
/*  322: 414 */     this.prevChunkSortZ = -9999;
/*  323: 415 */     RenderManager.instance.set(par1WorldClient);
/*  324: 416 */     this.theWorld = par1WorldClient;
/*  325: 417 */     this.renderBlocksRg = new RenderBlocks(par1WorldClient);
/*  326: 419 */     if (par1WorldClient != null)
/*  327:     */     {
/*  328: 421 */       par1WorldClient.addWorldAccess(this);
/*  329: 422 */       loadRenderers();
/*  330:     */     }
/*  331:     */   }
/*  332:     */   
/*  333:     */   public void loadRenderers()
/*  334:     */   {
/*  335: 431 */     if (this.theWorld != null)
/*  336:     */     {
/*  337: 433 */       Blocks.leaves.func_150122_b(Config.isTreesFancy());
/*  338: 434 */       Blocks.leaves2.func_150122_b(Config.isTreesFancy());
/*  339: 435 */       this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
/*  340: 436 */       WrUpdates.clearAllUpdates();
/*  341: 439 */       if (this.worldRenderers != null) {
/*  342: 441 */         for (int numChunks = 0; numChunks < this.worldRenderers.length; numChunks++) {
/*  343: 443 */           this.worldRenderers[numChunks].stopRendering();
/*  344:     */         }
/*  345:     */       }
/*  346: 447 */       int numChunks = this.mc.gameSettings.renderDistanceChunks;
/*  347: 449 */       if ((numChunks > 10) && (numChunks <= 16)) {
/*  348: 451 */         numChunks = 10;
/*  349:     */       }
/*  350: 454 */       byte numChunksFar = 16;
/*  351: 456 */       if ((Config.isLoadChunksFar()) && (numChunks < numChunksFar)) {
/*  352: 458 */         numChunks = numChunksFar;
/*  353:     */       }
/*  354: 461 */       int maxPreloadedChunks = Config.limit(numChunksFar - numChunks, 0, 8);
/*  355: 462 */       this.effectivePreloadedChunks = Config.limit(Config.getPreloadedChunks(), 0, maxPreloadedChunks);
/*  356: 463 */       numChunks += this.effectivePreloadedChunks;
/*  357: 464 */       byte limit = 32;
/*  358: 466 */       if (numChunks > limit) {
/*  359: 468 */         numChunks = limit;
/*  360:     */       }
/*  361: 471 */       this.prevReposX = -9999.0D;
/*  362: 472 */       this.prevReposY = -9999.0D;
/*  363: 473 */       this.prevReposZ = -9999.0D;
/*  364: 474 */       int var1 = numChunks * 2 + 1;
/*  365: 475 */       this.renderChunksWide = var1;
/*  366: 476 */       this.renderChunksTall = 16;
/*  367: 477 */       this.renderChunksDeep = var1;
/*  368: 478 */       this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
/*  369: 479 */       this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
/*  370: 480 */       this.countSortedWorldRenderers = 0;
/*  371: 481 */       int var2 = 0;
/*  372: 482 */       int var3 = 0;
/*  373: 483 */       this.minBlockX = 0;
/*  374: 484 */       this.minBlockY = 0;
/*  375: 485 */       this.minBlockZ = 0;
/*  376: 486 */       this.maxBlockX = this.renderChunksWide;
/*  377: 487 */       this.maxBlockY = this.renderChunksTall;
/*  378: 488 */       this.maxBlockZ = this.renderChunksDeep;
/*  379: 491 */       for (int var10 = 0; var10 < this.worldRenderersToUpdate.size(); var10++)
/*  380:     */       {
/*  381: 493 */         WorldRenderer esf = (WorldRenderer)this.worldRenderersToUpdate.get(var10);
/*  382: 495 */         if (esf != null) {
/*  383: 497 */           esf.needsUpdate = false;
/*  384:     */         }
/*  385:     */       }
/*  386: 501 */       this.worldRenderersToUpdate.clear();
/*  387: 502 */       this.tileEntities.clear();
/*  388: 504 */       for (var10 = 0; var10 < this.renderChunksWide; var10++) {
/*  389: 506 */         for (int var15 = 0; var15 < this.renderChunksTall; var15++) {
/*  390: 508 */           for (int cz = 0; cz < this.renderChunksDeep; cz++)
/*  391:     */           {
/*  392: 510 */             int wri = (cz * this.renderChunksTall + var15) * this.renderChunksWide + var10;
/*  393: 511 */             this.worldRenderers[wri] = WrUpdates.makeWorldRenderer(this.theWorld, this.tileEntities, var10 * 16, var15 * 16, cz * 16, this.glRenderListBase + var2, var3);
/*  394: 513 */             if (this.occlusionEnabled) {
/*  395: 515 */               this.worldRenderers[wri].glOcclusionQuery = this.glOcclusionQueryBase.get(var3);
/*  396:     */             }
/*  397: 518 */             this.worldRenderers[wri].isWaitingOnOcclusionQuery = false;
/*  398: 519 */             this.worldRenderers[wri].isVisible = true;
/*  399: 520 */             this.worldRenderers[wri].isInFrustum = false;
/*  400: 521 */             this.worldRenderers[wri].chunkIndex = (var3++);
/*  401: 523 */             if (this.theWorld.blockExists(var10 << 4, 0, cz << 4))
/*  402:     */             {
/*  403: 525 */               this.worldRenderers[wri].markDirty();
/*  404: 526 */               this.worldRenderersToUpdate.add(this.worldRenderers[wri]);
/*  405:     */             }
/*  406: 529 */             var2 += 3;
/*  407:     */           }
/*  408:     */         }
/*  409:     */       }
/*  410: 534 */       if (this.theWorld != null)
/*  411:     */       {
/*  412: 536 */         Object var14 = this.mc.renderViewEntity;
/*  413: 538 */         if (var14 == null) {
/*  414: 540 */           var14 = this.mc.thePlayer;
/*  415:     */         }
/*  416: 543 */         if (var14 != null)
/*  417:     */         {
/*  418: 545 */           markRenderersForNewPosition(MathHelper.floor_double(((EntityLivingBase)var14).posX), MathHelper.floor_double(((EntityLivingBase)var14).posY), MathHelper.floor_double(((EntityLivingBase)var14).posZ));
/*  419: 546 */           EntitySorterFast var13 = new EntitySorterFast((Entity)var14);
/*  420: 547 */           var13.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
/*  421: 548 */           Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, var13);
/*  422:     */         }
/*  423:     */       }
/*  424: 552 */       this.renderEntitiesStartupCounter = 2;
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void renderEntities(EntityLivingBase p_147589_1_, ICamera p_147589_2_, float p_147589_3_)
/*  429:     */   {
/*  430: 558 */     int pass = 0;
/*  431: 560 */     if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
/*  432: 562 */       pass = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object[0]);
/*  433:     */     }
/*  434: 565 */     boolean hasEntityShouldRenderInPass = Reflector.ForgeEntity_shouldRenderInPass.exists();
/*  435: 566 */     boolean hasTileEntityShouldRenderInPass = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
/*  436: 568 */     if (this.renderEntitiesStartupCounter > 0)
/*  437:     */     {
/*  438: 570 */       if (pass > 0) {
/*  439: 572 */         return;
/*  440:     */       }
/*  441: 575 */       this.renderEntitiesStartupCounter -= 1;
/*  442:     */     }
/*  443:     */     else
/*  444:     */     {
/*  445: 579 */       double var4 = p_147589_1_.prevPosX + (p_147589_1_.posX - p_147589_1_.prevPosX) * p_147589_3_;
/*  446: 580 */       double var6 = p_147589_1_.prevPosY + (p_147589_1_.posY - p_147589_1_.prevPosY) * p_147589_3_;
/*  447: 581 */       double var8 = p_147589_1_.prevPosZ + (p_147589_1_.posZ - p_147589_1_.prevPosZ) * p_147589_3_;
/*  448: 582 */       this.theWorld.theProfiler.startSection("prepare");
/*  449: 583 */       TileEntityRendererDispatcher.instance.func_147542_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, p_147589_3_);
/*  450: 584 */       RenderManager.instance.func_147938_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntity, this.mc.gameSettings, p_147589_3_);
/*  451: 586 */       if (pass == 0)
/*  452:     */       {
/*  453: 588 */         this.countEntitiesTotal = 0;
/*  454: 589 */         this.countEntitiesRendered = 0;
/*  455: 590 */         this.countEntitiesHidden = 0;
/*  456: 591 */         EntityLivingBase var17 = this.mc.renderViewEntity;
/*  457: 592 */         double var19 = var17.lastTickPosX + (var17.posX - var17.lastTickPosX) * p_147589_3_;
/*  458: 593 */         double oldFancyGraphics = var17.lastTickPosY + (var17.posY - var17.lastTickPosY) * p_147589_3_;
/*  459: 594 */         double aabb = var17.lastTickPosZ + (var17.posZ - var17.lastTickPosZ) * p_147589_3_;
/*  460: 595 */         TileEntityRendererDispatcher.staticPlayerX = var19;
/*  461: 596 */         TileEntityRendererDispatcher.staticPlayerY = oldFancyGraphics;
/*  462: 597 */         TileEntityRendererDispatcher.staticPlayerZ = aabb;
/*  463: 598 */         this.theWorld.theProfiler.endStartSection("staticentities");
/*  464: 600 */         if (this.displayListEntitiesDirty)
/*  465:     */         {
/*  466: 602 */           RenderManager.renderPosX = 0.0D;
/*  467: 603 */           RenderManager.renderPosY = 0.0D;
/*  468: 604 */           RenderManager.renderPosZ = 0.0D;
/*  469: 605 */           rebuildDisplayListEntities();
/*  470:     */         }
/*  471: 608 */         GL11.glMatrixMode(5888);
/*  472: 609 */         GL11.glPushMatrix();
/*  473: 610 */         GL11.glTranslated(-var19, -oldFancyGraphics, -aabb);
/*  474: 611 */         GL11.glCallList(this.displayListEntities);
/*  475: 612 */         GL11.glPopMatrix();
/*  476: 613 */         RenderManager.renderPosX = var19;
/*  477: 614 */         RenderManager.renderPosY = oldFancyGraphics;
/*  478: 615 */         RenderManager.renderPosZ = aabb;
/*  479:     */       }
/*  480: 618 */       this.mc.entityRenderer.enableLightmap(p_147589_3_);
/*  481: 619 */       this.theWorld.theProfiler.endStartSection("global");
/*  482: 620 */       List var24 = this.theWorld.getLoadedEntityList();
/*  483: 622 */       if (pass == 0) {
/*  484: 624 */         this.countEntitiesTotal = var24.size();
/*  485:     */       }
/*  486: 627 */       if ((Config.isFogOff()) && (this.mc.entityRenderer.fogStandard)) {
/*  487: 629 */         GL11.glDisable(2912);
/*  488:     */       }
/*  489: 635 */       for (int var18 = 0; var18 < this.theWorld.weatherEffects.size(); var18++)
/*  490:     */       {
/*  491: 637 */         Entity var25 = (Entity)this.theWorld.weatherEffects.get(var18);
/*  492: 639 */         if (hasEntityShouldRenderInPass)
/*  493:     */         {
/*  494: 639 */           if (!Reflector.callBoolean(var25, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(pass) })) {}
/*  495:     */         }
/*  496:     */         else
/*  497:     */         {
/*  498: 641 */           this.countEntitiesRendered += 1;
/*  499: 643 */           if (var25.isInRangeToRender3d(var4, var6, var8)) {
/*  500: 645 */             RenderManager.instance.func_147937_a(var25, p_147589_3_);
/*  501:     */           }
/*  502:     */         }
/*  503:     */       }
/*  504: 650 */       this.theWorld.theProfiler.endStartSection("entities");
/*  505: 651 */       boolean var26 = this.mc.gameSettings.fancyGraphics;
/*  506: 652 */       this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
/*  507: 654 */       for (var18 = 0; var18 < var24.size(); var18++)
/*  508:     */       {
/*  509: 656 */         Entity var25 = (Entity)var24.get(var18);
/*  510: 658 */         if (hasEntityShouldRenderInPass)
/*  511:     */         {
/*  512: 658 */           if (!Reflector.callBoolean(var25, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(pass) })) {}
/*  513:     */         }
/*  514:     */         else
/*  515:     */         {
/*  516: 660 */           boolean te = (var25.isInRangeToRender3d(var4, var6, var8)) && ((var25.ignoreFrustumCheck) || (p_147589_2_.isBoundingBoxInFrustum(var25.boundingBox)) || (var25.riddenByEntity == this.mc.thePlayer));
/*  517: 662 */           if ((!te) && ((var25 instanceof EntityLiving)))
/*  518:     */           {
/*  519: 664 */             EntityLiving var30 = (EntityLiving)var25;
/*  520: 666 */             if ((var30.getLeashed()) && (var30.getLeashedToEntity() != null))
/*  521:     */             {
/*  522: 668 */               Entity teClass = var30.getLeashedToEntity();
/*  523: 669 */               te = p_147589_2_.isBoundingBoxInFrustum(teClass.boundingBox);
/*  524:     */             }
/*  525:     */           }
/*  526: 673 */           if ((te) && ((var25 != this.mc.renderViewEntity) || (this.mc.gameSettings.thirdPersonView != 0) || (this.mc.renderViewEntity.isPlayerSleeping())) && (this.theWorld.blockExists(MathHelper.floor_double(var25.posX), 0, MathHelper.floor_double(var25.posZ))))
/*  527:     */           {
/*  528: 675 */             this.countEntitiesRendered += 1;
/*  529: 677 */             if (var25.getClass() == EntityItemFrame.class) {
/*  530: 679 */               var25.renderDistanceWeight = 0.06D;
/*  531:     */             }
/*  532: 682 */             this.renderedEntity = var25;
/*  533: 683 */             RenderManager.instance.func_147937_a(var25, p_147589_3_);
/*  534: 684 */             this.renderedEntity = null;
/*  535:     */           }
/*  536:     */         }
/*  537:     */       }
/*  538: 689 */       this.mc.gameSettings.fancyGraphics = var26;
/*  539: 690 */       this.theWorld.theProfiler.endStartSection("blockentities");
/*  540: 691 */       RenderHelper.enableStandardItemLighting();
/*  541: 693 */       for (var18 = 0; var18 < this.tileEntities.size(); var18++)
/*  542:     */       {
/*  543: 695 */         TileEntity var27 = (TileEntity)this.tileEntities.get(var18);
/*  544: 697 */         if (hasTileEntityShouldRenderInPass)
/*  545:     */         {
/*  546: 697 */           if (!Reflector.callBoolean(var27, Reflector.ForgeTileEntity_shouldRenderInPass, new Object[] { Integer.valueOf(pass) })) {}
/*  547:     */         }
/*  548:     */         else
/*  549:     */         {
/*  550: 699 */           AxisAlignedBB var29 = getTileEntityBoundingBox(var27);
/*  551: 701 */           if (p_147589_2_.isBoundingBoxInFrustum(var29))
/*  552:     */           {
/*  553: 703 */             Class var28 = var27.getClass();
/*  554: 705 */             if ((var28 == TileEntitySign.class) && (!Config.zoomMode))
/*  555:     */             {
/*  556: 707 */               EntityClientPlayerMP block = this.mc.thePlayer;
/*  557: 708 */               double distSq = var27.getDistanceFrom(block.posX, block.posY, block.posZ);
/*  558: 710 */               if (distSq > 256.0D)
/*  559:     */               {
/*  560: 712 */                 FontRenderer fr = TileEntityRendererDispatcher.instance.func_147548_a();
/*  561: 713 */                 fr.enabled = false;
/*  562: 714 */                 TileEntityRendererDispatcher.instance.func_147544_a(var27, p_147589_3_);
/*  563: 715 */                 fr.enabled = true;
/*  564: 716 */                 continue;
/*  565:     */               }
/*  566:     */             }
/*  567: 720 */             if (var28 == TileEntityChest.class)
/*  568:     */             {
/*  569: 722 */               Block var31 = this.theWorld.getBlock(var27.field_145851_c, var27.field_145848_d, var27.field_145849_e);
/*  570: 724 */               if (!(var31 instanceof BlockChest)) {}
/*  571:     */             }
/*  572:     */             else
/*  573:     */             {
/*  574: 730 */               TileEntityRendererDispatcher.instance.func_147544_a(var27, p_147589_3_);
/*  575:     */             }
/*  576:     */           }
/*  577:     */         }
/*  578:     */       }
/*  579: 735 */       this.mc.entityRenderer.disableLightmap(p_147589_3_);
/*  580: 736 */       this.theWorld.theProfiler.endSection();
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   public String getDebugInfoRenders()
/*  585:     */   {
/*  586: 745 */     return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public String getDebugInfoEntities()
/*  590:     */   {
/*  591: 753 */     return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersion();
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void onStaticEntitiesChanged()
/*  595:     */   {
/*  596: 758 */     this.displayListEntitiesDirty = true;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void rebuildDisplayListEntities()
/*  600:     */   {
/*  601: 763 */     this.theWorld.theProfiler.startSection("staticentityrebuild");
/*  602: 764 */     GL11.glPushMatrix();
/*  603: 765 */     GL11.glNewList(this.displayListEntities, 4864);
/*  604: 766 */     List var1 = this.theWorld.getLoadedEntityList();
/*  605: 767 */     this.displayListEntitiesDirty = false;
/*  606: 769 */     for (int var2 = 0; var2 < var1.size(); var2++)
/*  607:     */     {
/*  608: 771 */       Entity var3 = (Entity)var1.get(var2);
/*  609: 773 */       if (RenderManager.instance.getEntityRenderObject(var3).func_147905_a()) {
/*  610: 775 */         this.displayListEntitiesDirty = ((this.displayListEntitiesDirty) || (!RenderManager.instance.func_147936_a(var3, 0.0F, true)));
/*  611:     */       }
/*  612:     */     }
/*  613: 779 */     GL11.glEndList();
/*  614: 780 */     GL11.glPopMatrix();
/*  615: 781 */     this.theWorld.theProfiler.endSection();
/*  616:     */   }
/*  617:     */   
/*  618:     */   private void markRenderersForNewPosition(int x, int y, int z)
/*  619:     */   {
/*  620: 790 */     x -= 8;
/*  621: 791 */     y -= 8;
/*  622: 792 */     z -= 8;
/*  623: 793 */     this.minBlockX = 2147483647;
/*  624: 794 */     this.minBlockY = 2147483647;
/*  625: 795 */     this.minBlockZ = 2147483647;
/*  626: 796 */     this.maxBlockX = -2147483648;
/*  627: 797 */     this.maxBlockY = -2147483648;
/*  628: 798 */     this.maxBlockZ = -2147483648;
/*  629: 799 */     int blocksWide = this.renderChunksWide * 16;
/*  630: 800 */     int blocksWide2 = blocksWide / 2;
/*  631: 802 */     for (int ix = 0; ix < this.renderChunksWide; ix++)
/*  632:     */     {
/*  633: 804 */       int blockX = ix * 16;
/*  634: 805 */       int blockXAbs = blockX + blocksWide2 - x;
/*  635: 807 */       if (blockXAbs < 0) {
/*  636: 809 */         blockXAbs -= blocksWide - 1;
/*  637:     */       }
/*  638: 812 */       blockXAbs /= blocksWide;
/*  639: 813 */       blockX -= blockXAbs * blocksWide;
/*  640: 815 */       if (blockX < this.minBlockX) {
/*  641: 817 */         this.minBlockX = blockX;
/*  642:     */       }
/*  643: 820 */       if (blockX > this.maxBlockX) {
/*  644: 822 */         this.maxBlockX = blockX;
/*  645:     */       }
/*  646: 825 */       for (int iz = 0; iz < this.renderChunksDeep; iz++)
/*  647:     */       {
/*  648: 827 */         int blockZ = iz * 16;
/*  649: 828 */         int blockZAbs = blockZ + blocksWide2 - z;
/*  650: 830 */         if (blockZAbs < 0) {
/*  651: 832 */           blockZAbs -= blocksWide - 1;
/*  652:     */         }
/*  653: 835 */         blockZAbs /= blocksWide;
/*  654: 836 */         blockZ -= blockZAbs * blocksWide;
/*  655: 838 */         if (blockZ < this.minBlockZ) {
/*  656: 840 */           this.minBlockZ = blockZ;
/*  657:     */         }
/*  658: 843 */         if (blockZ > this.maxBlockZ) {
/*  659: 845 */           this.maxBlockZ = blockZ;
/*  660:     */         }
/*  661: 848 */         for (int iy = 0; iy < this.renderChunksTall; iy++)
/*  662:     */         {
/*  663: 850 */           int blockY = iy * 16;
/*  664: 852 */           if (blockY < this.minBlockY) {
/*  665: 854 */             this.minBlockY = blockY;
/*  666:     */           }
/*  667: 857 */           if (blockY > this.maxBlockY) {
/*  668: 859 */             this.maxBlockY = blockY;
/*  669:     */           }
/*  670: 862 */           WorldRenderer worldrenderer = this.worldRenderers[((iz * this.renderChunksTall + iy) * this.renderChunksWide + ix)];
/*  671: 863 */           boolean wasNeedingUpdate = worldrenderer.needsUpdate;
/*  672: 864 */           worldrenderer.setPosition(blockX, blockY, blockZ);
/*  673: 866 */           if ((!wasNeedingUpdate) && (worldrenderer.needsUpdate)) {
/*  674: 868 */             this.worldRenderersToUpdate.add(worldrenderer);
/*  675:     */           }
/*  676:     */         }
/*  677:     */       }
/*  678:     */     }
/*  679:     */   }
/*  680:     */   
/*  681:     */   public int sortAndRender(EntityLivingBase player, int renderPass, double partialTicks)
/*  682:     */   {
/*  683: 880 */     this.renderViewEntity = player;
/*  684: 881 */     Profiler profiler = this.theWorld.theProfiler;
/*  685: 882 */     profiler.startSection("sortchunks");
/*  686: 885 */     if (this.worldRenderersToUpdate.size() < 10)
/*  687:     */     {
/*  688: 887 */       byte shouldSort = 10;
/*  689: 889 */       for (int num = 0; num < shouldSort; num++)
/*  690:     */       {
/*  691: 891 */         this.worldRenderersCheckIndex = ((this.worldRenderersCheckIndex + 1) % this.worldRenderers.length);
/*  692: 892 */         WorldRenderer ocReq = this.worldRenderers[this.worldRenderersCheckIndex];
/*  693: 894 */         if ((ocReq.needsUpdate) && (!this.worldRenderersToUpdate.contains(ocReq))) {
/*  694: 896 */           this.worldRenderersToUpdate.add(ocReq);
/*  695:     */         }
/*  696:     */       }
/*  697:     */     }
/*  698: 901 */     if ((this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) && (!Config.isLoadChunksFar())) {
/*  699: 903 */       loadRenderers();
/*  700:     */     }
/*  701: 906 */     if (renderPass == 0)
/*  702:     */     {
/*  703: 908 */       this.renderersLoaded = 0;
/*  704: 909 */       this.dummyRenderInt = 0;
/*  705: 910 */       this.renderersBeingClipped = 0;
/*  706: 911 */       this.renderersBeingOccluded = 0;
/*  707: 912 */       this.renderersBeingRendered = 0;
/*  708: 913 */       this.renderersSkippingRenderPass = 0;
/*  709:     */     }
/*  710: 916 */     boolean var33 = (this.prevChunkSortX != player.chunkCoordX) || (this.prevChunkSortY != player.chunkCoordY) || (this.prevChunkSortZ != player.chunkCoordZ);
/*  711: 922 */     if (!var33)
/*  712:     */     {
/*  713: 924 */       double var34 = player.posX - this.prevSortX;
/*  714: 925 */       double partialX = player.posY - this.prevSortY;
/*  715: 926 */       double partialY = player.posZ - this.prevSortZ;
/*  716: 927 */       double partialZ = var34 * var34 + partialX * partialX + partialY * partialY;
/*  717: 928 */       var33 = partialZ > 16.0D;
/*  718:     */     }
/*  719: 934 */     if (var33)
/*  720:     */     {
/*  721: 936 */       this.prevChunkSortX = player.chunkCoordX;
/*  722: 937 */       this.prevChunkSortY = player.chunkCoordY;
/*  723: 938 */       this.prevChunkSortZ = player.chunkCoordZ;
/*  724: 939 */       this.prevSortX = player.posX;
/*  725: 940 */       this.prevSortY = player.posY;
/*  726: 941 */       this.prevSortZ = player.posZ;
/*  727: 942 */       int num = this.effectivePreloadedChunks * 16;
/*  728: 943 */       double var37 = player.posX - this.prevReposX;
/*  729: 944 */       double dReposY = player.posY - this.prevReposY;
/*  730: 945 */       double dReposZ = player.posZ - this.prevReposZ;
/*  731: 946 */       double countResort = var37 * var37 + dReposY * dReposY + dReposZ * dReposZ;
/*  732: 948 */       if (countResort > num * num + 16.0D)
/*  733:     */       {
/*  734: 950 */         this.prevReposX = player.posX;
/*  735: 951 */         this.prevReposY = player.posY;
/*  736: 952 */         this.prevReposZ = player.posZ;
/*  737: 953 */         markRenderersForNewPosition(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
/*  738:     */       }
/*  739: 956 */       EntitySorterFast lastIndex = new EntitySorterFast(player);
/*  740: 957 */       lastIndex.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
/*  741: 958 */       Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, lastIndex);
/*  742: 960 */       if (Config.isFastRender())
/*  743:     */       {
/*  744: 962 */         int endIndex = (int)player.posX;
/*  745: 963 */         int stepNum = (int)player.posZ;
/*  746: 964 */         short step = 2000;
/*  747: 966 */         if ((Math.abs(endIndex - WorldRenderer.globalChunkOffsetX) > step) || (Math.abs(stepNum - WorldRenderer.globalChunkOffsetZ) > step))
/*  748:     */         {
/*  749: 968 */           WorldRenderer.globalChunkOffsetX = endIndex;
/*  750: 969 */           WorldRenderer.globalChunkOffsetZ = stepNum;
/*  751: 970 */           loadRenderers();
/*  752:     */         }
/*  753:     */       }
/*  754:     */     }
/*  755: 975 */     if (Config.isTranslucentBlocksFancy())
/*  756:     */     {
/*  757: 977 */       double var34 = player.posX - this.prevRenderSortX;
/*  758: 978 */       double partialX = player.posY - this.prevRenderSortY;
/*  759: 979 */       double partialY = player.posZ - this.prevRenderSortZ;
/*  760: 980 */       int var39 = Math.min(27, this.countSortedWorldRenderers);
/*  761: 983 */       if (var34 * var34 + partialX * partialX + partialY * partialY > 1.0D)
/*  762:     */       {
/*  763: 985 */         this.prevRenderSortX = player.posX;
/*  764: 986 */         this.prevRenderSortY = player.posY;
/*  765: 987 */         this.prevRenderSortZ = player.posZ;
/*  766: 989 */         for (int var38 = 0; var38 < var39; var38++)
/*  767:     */         {
/*  768: 991 */           WorldRenderer firstIndex = this.sortedWorldRenderers[var38];
/*  769: 993 */           if ((firstIndex.vertexState != null) && (firstIndex.sortDistanceToEntitySquared < 1152.0F))
/*  770:     */           {
/*  771: 995 */             firstIndex.needVertexStateResort = true;
/*  772: 997 */             if (this.vertexResortCounter > var38) {
/*  773: 999 */               this.vertexResortCounter = var38;
/*  774:     */             }
/*  775:     */           }
/*  776:     */         }
/*  777:     */       }
/*  778:1005 */       if (this.vertexResortCounter < var39) {
/*  779:1007 */         while (this.vertexResortCounter < var39)
/*  780:     */         {
/*  781:1009 */           WorldRenderer firstIndex = this.sortedWorldRenderers[this.vertexResortCounter];
/*  782:1010 */           this.vertexResortCounter += 1;
/*  783:1012 */           if (firstIndex.needVertexStateResort)
/*  784:     */           {
/*  785:1014 */             firstIndex.updateRendererSort(player);
/*  786:1015 */             break;
/*  787:     */           }
/*  788:     */         }
/*  789:     */       }
/*  790:     */     }
/*  791:1021 */     RenderHelper.disableStandardItemLighting();
/*  792:1022 */     WrUpdates.preRender(this, player);
/*  793:1024 */     if ((this.mc.gameSettings.ofSmoothFps) && (renderPass == 0)) {
/*  794:1026 */       GL11.glFinish();
/*  795:     */     }
/*  796:1029 */     byte var36 = 0;
/*  797:1030 */     int var35 = 0;
/*  798:     */     int num;
/*  799:1032 */     if ((this.occlusionEnabled) && (this.mc.gameSettings.advancedOpengl) && (!this.mc.gameSettings.anaglyph) && (renderPass == 0))
/*  800:     */     {
/*  801:1034 */       double partialX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/*  802:1035 */       double partialY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/*  803:1036 */       double partialZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/*  804:1037 */       byte var41 = 0;
/*  805:1038 */       int var40 = Math.min(20, this.countSortedWorldRenderers);
/*  806:1039 */       checkOcclusionQueryResult(var41, var40, player.posX, player.posY, player.posZ);
/*  807:1041 */       for (int endIndex = var41; endIndex < var40; endIndex++) {
/*  808:1043 */         this.sortedWorldRenderers[endIndex].isVisible = true;
/*  809:     */       }
/*  810:1046 */       profiler.endStartSection("render");
/*  811:1047 */       int num = var36 + renderSortedRenderers(var41, var40, renderPass, partialTicks);
/*  812:1048 */       endIndex = var40;
/*  813:1049 */       int stepNum = 0;
/*  814:1050 */       byte var42 = 40;
/*  815:     */       int startIndex;
/*  816:1053 */       for (int switchStep = this.renderChunksWide; endIndex < this.countSortedWorldRenderers; num += renderSortedRenderers(startIndex, endIndex, renderPass, partialTicks))
/*  817:     */       {
/*  818:1055 */         profiler.endStartSection("occ");
/*  819:1056 */         startIndex = endIndex;
/*  820:1058 */         if (stepNum < switchStep) {
/*  821:1060 */           stepNum++;
/*  822:     */         } else {
/*  823:1064 */           stepNum--;
/*  824:     */         }
/*  825:1067 */         endIndex += stepNum * var42;
/*  826:1069 */         if (endIndex <= startIndex) {
/*  827:1071 */           endIndex = startIndex + 10;
/*  828:     */         }
/*  829:1074 */         if (endIndex > this.countSortedWorldRenderers) {
/*  830:1076 */           endIndex = this.countSortedWorldRenderers;
/*  831:     */         }
/*  832:1079 */         GL11.glDisable(3553);
/*  833:1080 */         GL11.glDisable(2896);
/*  834:1081 */         GL11.glDisable(3008);
/*  835:1082 */         GL11.glDisable(2912);
/*  836:1083 */         GL11.glColorMask(false, false, false, false);
/*  837:1084 */         GL11.glDepthMask(false);
/*  838:1085 */         profiler.startSection("check");
/*  839:1086 */         checkOcclusionQueryResult(startIndex, endIndex, player.posX, player.posY, player.posZ);
/*  840:1087 */         profiler.endSection();
/*  841:1088 */         GL11.glPushMatrix();
/*  842:1089 */         float sumTX = 0.0F;
/*  843:1090 */         float sumTY = 0.0F;
/*  844:1091 */         float sumTZ = 0.0F;
/*  845:1093 */         for (int k = startIndex; k < endIndex; k++)
/*  846:     */         {
/*  847:1095 */           WorldRenderer wr = this.sortedWorldRenderers[k];
/*  848:1097 */           if (wr.skipAllRenderPasses()) {
/*  849:1099 */             wr.isInFrustum = false;
/*  850:1101 */           } else if ((!wr.isUpdating) && (!wr.needsBoxUpdate))
/*  851:     */           {
/*  852:1103 */             if (wr.isInFrustum) {
/*  853:1105 */               if ((Config.isOcclusionFancy()) && (!wr.isInFrustrumFully)) {
/*  854:1107 */                 wr.isVisible = true;
/*  855:1109 */               } else if ((wr.isInFrustum) && (!wr.isWaitingOnOcclusionQuery)) {
/*  856:1116 */                 if (wr.isVisibleFromPosition)
/*  857:     */                 {
/*  858:1118 */                   float bbX = Math.abs((float)(wr.visibleFromX - player.posX));
/*  859:1119 */                   float bbY = Math.abs((float)(wr.visibleFromY - player.posY));
/*  860:1120 */                   float bbZ = Math.abs((float)(wr.visibleFromZ - player.posZ));
/*  861:1121 */                   float tX = bbX + bbY + bbZ;
/*  862:1123 */                   if (tX < 10.0D + k / 1000.0D) {
/*  863:1125 */                     wr.isVisible = true;
/*  864:     */                   } else {
/*  865:1129 */                     wr.isVisibleFromPosition = false;
/*  866:     */                   }
/*  867:     */                 }
/*  868:     */                 else
/*  869:     */                 {
/*  870:1132 */                   float bbX = (float)(wr.posXMinus - partialX);
/*  871:1133 */                   float bbY = (float)(wr.posYMinus - partialY);
/*  872:1134 */                   float bbZ = (float)(wr.posZMinus - partialZ);
/*  873:1135 */                   float tX = bbX - sumTX;
/*  874:1136 */                   float tY = bbY - sumTY;
/*  875:1137 */                   float tZ = bbZ - sumTZ;
/*  876:1139 */                   if ((tX != 0.0F) || (tY != 0.0F) || (tZ != 0.0F))
/*  877:     */                   {
/*  878:1141 */                     GL11.glTranslatef(tX, tY, tZ);
/*  879:1142 */                     sumTX += tX;
/*  880:1143 */                     sumTY += tY;
/*  881:1144 */                     sumTZ += tZ;
/*  882:     */                   }
/*  883:1147 */                   profiler.startSection("bb");
/*  884:1148 */                   ARBOcclusionQuery.glBeginQueryARB(35092, wr.glOcclusionQuery);
/*  885:1149 */                   wr.callOcclusionQueryList();
/*  886:1150 */                   ARBOcclusionQuery.glEndQueryARB(35092);
/*  887:1151 */                   profiler.endSection();
/*  888:1152 */                   wr.isWaitingOnOcclusionQuery = true;
/*  889:1153 */                   var35++;
/*  890:     */                 }
/*  891:     */               }
/*  892:     */             }
/*  893:     */           }
/*  894:     */           else {
/*  895:1159 */             wr.isVisible = true;
/*  896:     */           }
/*  897:     */         }
/*  898:1163 */         GL11.glPopMatrix();
/*  899:1165 */         if (this.mc.gameSettings.anaglyph)
/*  900:     */         {
/*  901:1167 */           if (EntityRenderer.anaglyphField == 0) {
/*  902:1169 */             GL11.glColorMask(false, true, true, true);
/*  903:     */           } else {
/*  904:1173 */             GL11.glColorMask(true, false, false, true);
/*  905:     */           }
/*  906:     */         }
/*  907:     */         else {
/*  908:1178 */           GL11.glColorMask(true, true, true, true);
/*  909:     */         }
/*  910:1181 */         GL11.glDepthMask(true);
/*  911:1182 */         GL11.glEnable(3553);
/*  912:1183 */         GL11.glEnable(3008);
/*  913:1184 */         GL11.glEnable(2912);
/*  914:1185 */         profiler.endStartSection("render");
/*  915:     */       }
/*  916:     */     }
/*  917:     */     else
/*  918:     */     {
/*  919:1190 */       profiler.endStartSection("render");
/*  920:1191 */       num = var36 + renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
/*  921:     */     }
/*  922:1194 */     profiler.endSection();
/*  923:1195 */     WrUpdates.postRender();
/*  924:1196 */     return num;
/*  925:     */   }
/*  926:     */   
/*  927:     */   private void checkOcclusionQueryResult(int startIndex, int endIndex, double px, double py, double pz)
/*  928:     */   {
/*  929:1201 */     for (int k = startIndex; k < endIndex; k++)
/*  930:     */     {
/*  931:1203 */       WorldRenderer wr = this.sortedWorldRenderers[k];
/*  932:1205 */       if (wr.isWaitingOnOcclusionQuery)
/*  933:     */       {
/*  934:1207 */         this.occlusionResult.clear();
/*  935:1208 */         ARBOcclusionQuery.glGetQueryObjectuARB(wr.glOcclusionQuery, 34919, this.occlusionResult);
/*  936:1210 */         if (this.occlusionResult.get(0) != 0)
/*  937:     */         {
/*  938:1212 */           wr.isWaitingOnOcclusionQuery = false;
/*  939:1213 */           this.occlusionResult.clear();
/*  940:1214 */           ARBOcclusionQuery.glGetQueryObjectuARB(wr.glOcclusionQuery, 34918, this.occlusionResult);
/*  941:1216 */           if ((!wr.isUpdating) && (!wr.needsBoxUpdate))
/*  942:     */           {
/*  943:1218 */             boolean wasVisible = wr.isVisible;
/*  944:1219 */             wr.isVisible = (this.occlusionResult.get(0) > 0);
/*  945:1221 */             if ((wasVisible) && (wr.isVisible))
/*  946:     */             {
/*  947:1223 */               wr.isVisibleFromPosition = true;
/*  948:1224 */               wr.visibleFromX = px;
/*  949:1225 */               wr.visibleFromY = py;
/*  950:1226 */               wr.visibleFromZ = pz;
/*  951:     */             }
/*  952:     */           }
/*  953:     */           else
/*  954:     */           {
/*  955:1231 */             wr.isVisible = true;
/*  956:     */           }
/*  957:     */         }
/*  958:     */       }
/*  959:     */     }
/*  960:     */   }
/*  961:     */   
/*  962:     */   private int renderSortedRenderers(int par1, int par2, int par3, double par4)
/*  963:     */   {
/*  964:1244 */     if (Config.isFastRender()) {
/*  965:1246 */       return renderSortedRenderersFast(par1, par2, par3, par4);
/*  966:     */     }
/*  967:1250 */     this.glRenderLists.clear();
/*  968:1251 */     int var6 = 0;
/*  969:1252 */     int var7 = par1;
/*  970:1253 */     int var8 = par2;
/*  971:1254 */     byte var9 = 1;
/*  972:1256 */     if (par3 == 1)
/*  973:     */     {
/*  974:1258 */       var7 = this.countSortedWorldRenderers - 1 - par1;
/*  975:1259 */       var8 = this.countSortedWorldRenderers - 1 - par2;
/*  976:1260 */       var9 = -1;
/*  977:     */     }
/*  978:1263 */     for (int var23 = var7; var23 != var8; var23 += var9)
/*  979:     */     {
/*  980:1265 */       if (par3 == 0)
/*  981:     */       {
/*  982:1267 */         this.renderersLoaded += 1;
/*  983:1269 */         if (this.sortedWorldRenderers[var23].skipRenderPass[par3] != 0) {
/*  984:1271 */           this.renderersSkippingRenderPass += 1;
/*  985:1273 */         } else if (!this.sortedWorldRenderers[var23].isInFrustum) {
/*  986:1275 */           this.renderersBeingClipped += 1;
/*  987:1277 */         } else if ((this.occlusionEnabled) && (!this.sortedWorldRenderers[var23].isVisible)) {
/*  988:1279 */           this.renderersBeingOccluded += 1;
/*  989:     */         } else {
/*  990:1283 */           this.renderersBeingRendered += 1;
/*  991:     */         }
/*  992:     */       }
/*  993:1287 */       if ((this.sortedWorldRenderers[var23].skipRenderPass[par3] == 0) && (this.sortedWorldRenderers[var23].isInFrustum) && ((!this.occlusionEnabled) || (this.sortedWorldRenderers[var23].isVisible)))
/*  994:     */       {
/*  995:1289 */         int var22 = this.sortedWorldRenderers[var23].getGLCallListForPass(par3);
/*  996:1291 */         if (var22 >= 0)
/*  997:     */         {
/*  998:1293 */           this.glRenderLists.add(this.sortedWorldRenderers[var23]);
/*  999:1294 */           var6++;
/* 1000:     */         }
/* 1001:     */       }
/* 1002:     */     }
/* 1003:1299 */     EntityLivingBase var231 = this.mc.renderViewEntity;
/* 1004:1300 */     double var221 = var231.lastTickPosX + (var231.posX - var231.lastTickPosX) * par4;
/* 1005:1301 */     double var13 = var231.lastTickPosY + (var231.posY - var231.lastTickPosY) * par4;
/* 1006:1302 */     double var15 = var231.lastTickPosZ + (var231.posZ - var231.lastTickPosZ) * par4;
/* 1007:1303 */     int var17 = 0;
/* 1008:1306 */     for (int var18 = 0; var18 < this.allRenderLists.length; var18++) {
/* 1009:1308 */       this.allRenderLists[var18].resetList();
/* 1010:     */     }
/* 1011:1311 */     for (var18 = 0; var18 < this.glRenderLists.size(); var18++)
/* 1012:     */     {
/* 1013:1313 */       WorldRenderer var19 = (WorldRenderer)this.glRenderLists.get(var18);
/* 1014:1314 */       int var20 = -1;
/* 1015:1316 */       for (int var21 = 0; var21 < var17; var21++) {
/* 1016:1318 */         if (this.allRenderLists[var21].rendersChunk(var19.posXMinus, var19.posYMinus, var19.posZMinus)) {
/* 1017:1320 */           var20 = var21;
/* 1018:     */         }
/* 1019:     */       }
/* 1020:1324 */       if (var20 < 0)
/* 1021:     */       {
/* 1022:1326 */         var20 = var17++;
/* 1023:1327 */         this.allRenderLists[var20].setupRenderList(var19.posXMinus, var19.posYMinus, var19.posZMinus, var221, var13, var15);
/* 1024:     */       }
/* 1025:1330 */       this.allRenderLists[var20].addGLRenderList(var19.getGLCallListForPass(par3));
/* 1026:     */     }
/* 1027:1333 */     if ((Config.isFogOff()) && (this.mc.entityRenderer.fogStandard)) {
/* 1028:1335 */       GL11.glDisable(2912);
/* 1029:     */     }
/* 1030:1338 */     Arrays.sort(this.allRenderLists, new RenderDistanceSorter());
/* 1031:1339 */     renderAllRenderLists(par3, par4);
/* 1032:1340 */     return var6;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   private int renderSortedRenderersFast(int startIndex, int endIndex, int renderPass, double partialTicks)
/* 1036:     */   {
/* 1037:1346 */     this.glListBuffer.clear();
/* 1038:1347 */     int l = 0;
/* 1039:1348 */     boolean debug = this.mc.gameSettings.showDebugInfo;
/* 1040:1349 */     int loopStart = startIndex;
/* 1041:1350 */     int loopEnd = endIndex;
/* 1042:1351 */     byte loopInc = 1;
/* 1043:1353 */     if (renderPass == 1)
/* 1044:     */     {
/* 1045:1355 */       loopStart = this.countSortedWorldRenderers - 1 - startIndex;
/* 1046:1356 */       loopEnd = this.countSortedWorldRenderers - 1 - endIndex;
/* 1047:1357 */       loopInc = -1;
/* 1048:     */     }
/* 1049:1360 */     for (int entityliving = loopStart; entityliving != loopEnd; entityliving += loopInc)
/* 1050:     */     {
/* 1051:1362 */       WorldRenderer partialX = this.sortedWorldRenderers[entityliving];
/* 1052:1364 */       if ((debug) && (renderPass == 0))
/* 1053:     */       {
/* 1054:1366 */         this.renderersLoaded += 1;
/* 1055:1368 */         if (partialX.skipRenderPass[renderPass] != 0) {
/* 1056:1370 */           this.renderersSkippingRenderPass += 1;
/* 1057:1372 */         } else if (!partialX.isInFrustum) {
/* 1058:1374 */           this.renderersBeingClipped += 1;
/* 1059:1376 */         } else if ((this.occlusionEnabled) && (!partialX.isVisible)) {
/* 1060:1378 */           this.renderersBeingOccluded += 1;
/* 1061:     */         } else {
/* 1062:1382 */           this.renderersBeingRendered += 1;
/* 1063:     */         }
/* 1064:     */       }
/* 1065:1386 */       if ((partialX.isInFrustum) && (partialX.skipRenderPass[renderPass] == 0) && ((!this.occlusionEnabled) || (partialX.isVisible)))
/* 1066:     */       {
/* 1067:1388 */         int glCallList = partialX.getGLCallListForPass(renderPass);
/* 1068:1390 */         if (glCallList >= 0)
/* 1069:     */         {
/* 1070:1392 */           this.glListBuffer.put(glCallList);
/* 1071:1393 */           l++;
/* 1072:     */         }
/* 1073:     */       }
/* 1074:     */     }
/* 1075:1398 */     if (l == 0) {
/* 1076:1400 */       return 0;
/* 1077:     */     }
/* 1078:1404 */     if ((Config.isFogOff()) && (this.mc.entityRenderer.fogStandard)) {
/* 1079:1406 */       GL11.glDisable(2912);
/* 1080:     */     }
/* 1081:1409 */     this.glListBuffer.flip();
/* 1082:1410 */     EntityLivingBase var19 = this.mc.renderViewEntity;
/* 1083:1411 */     double var18 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * partialTicks - WorldRenderer.globalChunkOffsetX;
/* 1084:1412 */     double partialY = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * partialTicks;
/* 1085:1413 */     double partialZ = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * partialTicks - WorldRenderer.globalChunkOffsetZ;
/* 1086:1414 */     this.mc.entityRenderer.enableLightmap(partialTicks);
/* 1087:1415 */     GL11.glTranslatef((float)-var18, (float)-partialY, (float)-partialZ);
/* 1088:1416 */     GL11.glCallLists(this.glListBuffer);
/* 1089:1417 */     GL11.glTranslatef((float)var18, (float)partialY, (float)partialZ);
/* 1090:1418 */     this.mc.entityRenderer.disableLightmap(partialTicks);
/* 1091:1419 */     return l;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void renderAllRenderLists(int par1, double par2)
/* 1095:     */   {
/* 1096:1428 */     this.mc.entityRenderer.enableLightmap(par2);
/* 1097:1430 */     for (int var4 = 0; var4 < this.allRenderLists.length; var4++) {
/* 1098:1432 */       this.allRenderLists[var4].callLists();
/* 1099:     */     }
/* 1100:1435 */     this.mc.entityRenderer.disableLightmap(par2);
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public void updateClouds()
/* 1104:     */   {
/* 1105:1440 */     this.cloudTickCounter += 1;
/* 1106:1442 */     if (this.cloudTickCounter % 20 == 0)
/* 1107:     */     {
/* 1108:1444 */       Iterator var1 = this.damagedBlocks.values().iterator();
/* 1109:1446 */       while (var1.hasNext())
/* 1110:     */       {
/* 1111:1448 */         DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
/* 1112:1449 */         int var3 = var2.getCreationCloudUpdateTick();
/* 1113:1451 */         if (this.cloudTickCounter - var3 > 400) {
/* 1114:1453 */           var1.remove();
/* 1115:     */         }
/* 1116:     */       }
/* 1117:     */     }
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void renderSky(float par1)
/* 1121:     */   {
/* 1122:1464 */     if (Reflector.ForgeWorldProvider_getSkyRenderer.exists())
/* 1123:     */     {
/* 1124:1466 */       WorldProvider var2 = this.mc.theWorld.provider;
/* 1125:1467 */       Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
/* 1126:1469 */       if (var3 != null)
/* 1127:     */       {
/* 1128:1471 */         Reflector.callVoid(var3, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(par1), this.theWorld, this.mc });
/* 1129:1472 */         return;
/* 1130:     */       }
/* 1131:     */     }
/* 1132:1476 */     if (this.mc.theWorld.provider.dimensionId == 1)
/* 1133:     */     {
/* 1134:1478 */       if (!Config.isSkyEnabled()) {
/* 1135:1480 */         return;
/* 1136:     */       }
/* 1137:1483 */       GL11.glDisable(2912);
/* 1138:1484 */       GL11.glDisable(3008);
/* 1139:1485 */       GL11.glEnable(3042);
/* 1140:1486 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1141:1487 */       RenderHelper.disableStandardItemLighting();
/* 1142:1488 */       GL11.glDepthMask(false);
/* 1143:1489 */       this.renderEngine.bindTexture(locationEndSkyPng);
/* 1144:1490 */       Tessellator var201 = Tessellator.instance;
/* 1145:1492 */       for (int var22 = 0; var22 < 6; var22++)
/* 1146:     */       {
/* 1147:1494 */         GL11.glPushMatrix();
/* 1148:1496 */         if (var22 == 1) {
/* 1149:1498 */           GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 1150:     */         }
/* 1151:1501 */         if (var22 == 2) {
/* 1152:1503 */           GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 1153:     */         }
/* 1154:1506 */         if (var22 == 3) {
/* 1155:1508 */           GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 1156:     */         }
/* 1157:1511 */         if (var22 == 4) {
/* 1158:1513 */           GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 1159:     */         }
/* 1160:1516 */         if (var22 == 5) {
/* 1161:1518 */           GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 1162:     */         }
/* 1163:1521 */         var201.startDrawingQuads();
/* 1164:1522 */         var201.setColorOpaque_I(2631720);
/* 1165:1523 */         var201.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
/* 1166:1524 */         var201.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
/* 1167:1525 */         var201.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
/* 1168:1526 */         var201.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
/* 1169:1527 */         var201.draw();
/* 1170:1528 */         GL11.glPopMatrix();
/* 1171:     */       }
/* 1172:1531 */       GL11.glDepthMask(true);
/* 1173:1532 */       GL11.glEnable(3553);
/* 1174:1533 */       GL11.glEnable(3008);
/* 1175:     */     }
/* 1176:1535 */     else if (this.mc.theWorld.provider.isSurfaceWorld())
/* 1177:     */     {
/* 1178:1537 */       GL11.glDisable(3553);
/* 1179:1538 */       Vec3 var21 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
/* 1180:1539 */       var21 = CustomColorizer.getSkyColor(var21, this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0D, this.mc.renderViewEntity.posZ);
/* 1181:1540 */       float var231 = (float)var21.xCoord;
/* 1182:1541 */       float var4 = (float)var21.yCoord;
/* 1183:1542 */       float var5 = (float)var21.zCoord;
/* 1184:1545 */       if (this.mc.gameSettings.anaglyph)
/* 1185:     */       {
/* 1186:1547 */         float var23 = (var231 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
/* 1187:1548 */         float var24 = (var231 * 30.0F + var4 * 70.0F) / 100.0F;
/* 1188:1549 */         float var8 = (var231 * 30.0F + var5 * 70.0F) / 100.0F;
/* 1189:1550 */         var231 = var23;
/* 1190:1551 */         var4 = var24;
/* 1191:1552 */         var5 = var8;
/* 1192:     */       }
/* 1193:1555 */       GL11.glColor3f(var231, var4, var5);
/* 1194:1556 */       Tessellator var241 = Tessellator.instance;
/* 1195:1557 */       GL11.glDepthMask(false);
/* 1196:1558 */       GL11.glEnable(2912);
/* 1197:1559 */       GL11.glColor3f(var231, var4, var5);
/* 1198:1561 */       if (Config.isSkyEnabled()) {
/* 1199:1563 */         GL11.glCallList(this.glSkyList);
/* 1200:     */       }
/* 1201:1566 */       GL11.glDisable(2912);
/* 1202:1567 */       GL11.glDisable(3008);
/* 1203:1568 */       GL11.glEnable(3042);
/* 1204:1569 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1205:1570 */       RenderHelper.disableStandardItemLighting();
/* 1206:1571 */       float[] var251 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
/* 1207:1581 */       if ((var251 != null) && (Config.isSunMoonEnabled()))
/* 1208:     */       {
/* 1209:1583 */         GL11.glDisable(3553);
/* 1210:1584 */         GL11.glShadeModel(7425);
/* 1211:1585 */         GL11.glPushMatrix();
/* 1212:1586 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 1213:1587 */         GL11.glRotatef(MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 1214:1588 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 1215:1589 */         float var8 = var251[0];
/* 1216:1590 */         float var9 = var251[1];
/* 1217:1591 */         float var10 = var251[2];
/* 1218:1593 */         if (this.mc.gameSettings.anaglyph)
/* 1219:     */         {
/* 1220:1595 */           float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/* 1221:1596 */           float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/* 1222:1597 */           float var20 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/* 1223:1598 */           var8 = var11;
/* 1224:1599 */           var9 = var12;
/* 1225:1600 */           var10 = var20;
/* 1226:     */         }
/* 1227:1603 */         var241.startDrawing(6);
/* 1228:1604 */         var241.setColorRGBA_F(var8, var9, var10, var251[3]);
/* 1229:1605 */         var241.addVertex(0.0D, 100.0D, 0.0D);
/* 1230:1606 */         byte var25 = 16;
/* 1231:1607 */         var241.setColorRGBA_F(var251[0], var251[1], var251[2], 0.0F);
/* 1232:1609 */         for (int var29 = 0; var29 <= var25; var29++)
/* 1233:     */         {
/* 1234:1611 */           float var20 = var29 * 3.141593F * 2.0F / var25;
/* 1235:1612 */           float var16 = MathHelper.sin(var20);
/* 1236:1613 */           float var17 = MathHelper.cos(var20);
/* 1237:1614 */           var241.addVertex(var16 * 120.0F, var17 * 120.0F, -var17 * 40.0F * var251[3]);
/* 1238:     */         }
/* 1239:1617 */         var241.draw();
/* 1240:1618 */         GL11.glPopMatrix();
/* 1241:1619 */         GL11.glShadeModel(7424);
/* 1242:     */       }
/* 1243:1622 */       GL11.glEnable(3553);
/* 1244:1623 */       OpenGlHelper.glBlendFunc(770, 1, 1, 0);
/* 1245:1624 */       GL11.glPushMatrix();
/* 1246:1625 */       float var8 = 1.0F - this.theWorld.getRainStrength(par1);
/* 1247:1626 */       float var9 = 0.0F;
/* 1248:1627 */       float var10 = 0.0F;
/* 1249:1628 */       float var11 = 0.0F;
/* 1250:1629 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
/* 1251:1630 */       GL11.glTranslatef(var9, var10, var11);
/* 1252:1631 */       GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1253:1632 */       CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(par1), var8);
/* 1254:1633 */       GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0F, 1.0F, 0.0F, 0.0F);
/* 1255:1635 */       if (Config.isSunMoonEnabled())
/* 1256:     */       {
/* 1257:1637 */         float var12 = 30.0F;
/* 1258:1638 */         this.renderEngine.bindTexture(locationSunPng);
/* 1259:1639 */         var241.startDrawingQuads();
/* 1260:1640 */         var241.addVertexWithUV(-var12, 100.0D, -var12, 0.0D, 0.0D);
/* 1261:1641 */         var241.addVertexWithUV(var12, 100.0D, -var12, 1.0D, 0.0D);
/* 1262:1642 */         var241.addVertexWithUV(var12, 100.0D, var12, 1.0D, 1.0D);
/* 1263:1643 */         var241.addVertexWithUV(-var12, 100.0D, var12, 0.0D, 1.0D);
/* 1264:1644 */         var241.draw();
/* 1265:1645 */         var12 = 20.0F;
/* 1266:1646 */         this.renderEngine.bindTexture(locationMoonPhasesPng);
/* 1267:1647 */         int var26 = this.theWorld.getMoonPhase();
/* 1268:1648 */         int var27 = var26 % 4;
/* 1269:1649 */         int var29 = var26 / 4 % 2;
/* 1270:1650 */         float var16 = (var27 + 0) / 4.0F;
/* 1271:1651 */         float var17 = (var29 + 0) / 2.0F;
/* 1272:1652 */         float var18 = (var27 + 1) / 4.0F;
/* 1273:1653 */         float var19 = (var29 + 1) / 2.0F;
/* 1274:1654 */         var241.startDrawingQuads();
/* 1275:1655 */         var241.addVertexWithUV(-var12, -100.0D, var12, var18, var19);
/* 1276:1656 */         var241.addVertexWithUV(var12, -100.0D, var12, var16, var19);
/* 1277:1657 */         var241.addVertexWithUV(var12, -100.0D, -var12, var16, var17);
/* 1278:1658 */         var241.addVertexWithUV(-var12, -100.0D, -var12, var18, var17);
/* 1279:1659 */         var241.draw();
/* 1280:     */       }
/* 1281:1662 */       GL11.glDisable(3553);
/* 1282:1663 */       float var20 = this.theWorld.getStarBrightness(par1) * var8;
/* 1283:1665 */       if ((var20 > 0.0F) && (Config.isStarsEnabled()) && (!CustomSky.hasSkyLayers(this.theWorld)))
/* 1284:     */       {
/* 1285:1667 */         GL11.glColor4f(var20, var20, var20, var20);
/* 1286:1668 */         GL11.glCallList(this.starGLCallList);
/* 1287:     */       }
/* 1288:1671 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1289:1672 */       GL11.glDisable(3042);
/* 1290:1673 */       GL11.glEnable(3008);
/* 1291:1674 */       GL11.glEnable(2912);
/* 1292:1675 */       GL11.glPopMatrix();
/* 1293:1676 */       GL11.glDisable(3553);
/* 1294:1677 */       GL11.glColor3f(0.0F, 0.0F, 0.0F);
/* 1295:1678 */       double var28 = this.mc.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();
/* 1296:1680 */       if (var28 < 0.0D)
/* 1297:     */       {
/* 1298:1682 */         GL11.glPushMatrix();
/* 1299:1683 */         GL11.glTranslatef(0.0F, 12.0F, 0.0F);
/* 1300:1684 */         GL11.glCallList(this.glSkyList2);
/* 1301:1685 */         GL11.glPopMatrix();
/* 1302:1686 */         var10 = 1.0F;
/* 1303:1687 */         var11 = -(float)(var28 + 65.0D);
/* 1304:1688 */         float var12 = -var10;
/* 1305:1689 */         var241.startDrawingQuads();
/* 1306:1690 */         var241.setColorRGBA_I(0, 255);
/* 1307:1691 */         var241.addVertex(-var10, var11, var10);
/* 1308:1692 */         var241.addVertex(var10, var11, var10);
/* 1309:1693 */         var241.addVertex(var10, var12, var10);
/* 1310:1694 */         var241.addVertex(-var10, var12, var10);
/* 1311:1695 */         var241.addVertex(-var10, var12, -var10);
/* 1312:1696 */         var241.addVertex(var10, var12, -var10);
/* 1313:1697 */         var241.addVertex(var10, var11, -var10);
/* 1314:1698 */         var241.addVertex(-var10, var11, -var10);
/* 1315:1699 */         var241.addVertex(var10, var12, -var10);
/* 1316:1700 */         var241.addVertex(var10, var12, var10);
/* 1317:1701 */         var241.addVertex(var10, var11, var10);
/* 1318:1702 */         var241.addVertex(var10, var11, -var10);
/* 1319:1703 */         var241.addVertex(-var10, var11, -var10);
/* 1320:1704 */         var241.addVertex(-var10, var11, var10);
/* 1321:1705 */         var241.addVertex(-var10, var12, var10);
/* 1322:1706 */         var241.addVertex(-var10, var12, -var10);
/* 1323:1707 */         var241.addVertex(-var10, var12, -var10);
/* 1324:1708 */         var241.addVertex(-var10, var12, var10);
/* 1325:1709 */         var241.addVertex(var10, var12, var10);
/* 1326:1710 */         var241.addVertex(var10, var12, -var10);
/* 1327:1711 */         var241.draw();
/* 1328:     */       }
/* 1329:1714 */       if (this.theWorld.provider.isSkyColored()) {
/* 1330:1716 */         GL11.glColor3f(var231 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
/* 1331:     */       } else {
/* 1332:1720 */         GL11.glColor3f(var231, var4, var5);
/* 1333:     */       }
/* 1334:1723 */       if (this.mc.gameSettings.renderDistanceChunks <= 4) {
/* 1335:1725 */         GL11.glColor3f(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
/* 1336:     */       }
/* 1337:1728 */       GL11.glPushMatrix();
/* 1338:1729 */       GL11.glTranslatef(0.0F, -(float)(var28 - 16.0D), 0.0F);
/* 1339:1731 */       if (Config.isSkyEnabled()) {
/* 1340:1733 */         GL11.glCallList(this.glSkyList2);
/* 1341:     */       }
/* 1342:1736 */       GL11.glPopMatrix();
/* 1343:1737 */       GL11.glEnable(3553);
/* 1344:1738 */       GL11.glDepthMask(true);
/* 1345:     */     }
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   public void renderClouds(float par1)
/* 1349:     */   {
/* 1350:1744 */     if (!Config.isCloudsOff())
/* 1351:     */     {
/* 1352:1746 */       if (Reflector.ForgeWorldProvider_getCloudRenderer.exists())
/* 1353:     */       {
/* 1354:1748 */         WorldProvider partialTicks = this.mc.theWorld.provider;
/* 1355:1749 */         Object var2 = Reflector.call(partialTicks, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
/* 1356:1751 */         if (var2 != null)
/* 1357:     */         {
/* 1358:1753 */           Reflector.callVoid(var2, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(par1), this.theWorld, this.mc });
/* 1359:1754 */           return;
/* 1360:     */         }
/* 1361:     */       }
/* 1362:1758 */       if (this.mc.theWorld.provider.isSurfaceWorld()) {
/* 1363:1760 */         if (Config.isCloudsFancy())
/* 1364:     */         {
/* 1365:1762 */           renderCloudsFancy(par1);
/* 1366:     */         }
/* 1367:     */         else
/* 1368:     */         {
/* 1369:1766 */           float partialTicks1 = par1;
/* 1370:1767 */           par1 = 0.0F;
/* 1371:1768 */           GL11.glDisable(2884);
/* 1372:1769 */           float var21 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
/* 1373:1770 */           byte var3 = 32;
/* 1374:1771 */           int var4 = 256 / var3;
/* 1375:1772 */           Tessellator var5 = Tessellator.instance;
/* 1376:1773 */           this.renderEngine.bindTexture(locationCloudsPng);
/* 1377:1774 */           GL11.glEnable(3042);
/* 1378:1775 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1379:1779 */           if ((this.isFancyGlListClouds) || (this.cloudTickCounter >= this.cloudTickCounterGlList + 20))
/* 1380:     */           {
/* 1381:1781 */             GL11.glNewList(this.glListClouds, 4864);
/* 1382:1782 */             Vec3 entityliving = this.theWorld.getCloudColour(par1);
/* 1383:1783 */             float exactPlayerX = (float)entityliving.xCoord;
/* 1384:1784 */             float var8 = (float)entityliving.yCoord;
/* 1385:1785 */             float exactPlayerY = (float)entityliving.zCoord;
/* 1386:1788 */             if (this.mc.gameSettings.anaglyph)
/* 1387:     */             {
/* 1388:1790 */               float var10 = (exactPlayerX * 30.0F + var8 * 59.0F + exactPlayerY * 11.0F) / 100.0F;
/* 1389:1791 */               float exactPlayerZ = (exactPlayerX * 30.0F + var8 * 70.0F) / 100.0F;
/* 1390:1792 */               float var12 = (exactPlayerX * 30.0F + exactPlayerY * 70.0F) / 100.0F;
/* 1391:1793 */               exactPlayerX = var10;
/* 1392:1794 */               var8 = exactPlayerZ;
/* 1393:1795 */               exactPlayerY = var12;
/* 1394:     */             }
/* 1395:1798 */             float var10 = 0.0004882813F;
/* 1396:1799 */             double exactPlayerZ1 = this.cloudTickCounter + par1;
/* 1397:1800 */             double dc = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + exactPlayerZ1 * 0.02999999932944775D;
/* 1398:1801 */             double cdx = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1;
/* 1399:1802 */             int cdz = MathHelper.floor_double(dc / 2048.0D);
/* 1400:1803 */             int var18 = MathHelper.floor_double(cdx / 2048.0D);
/* 1401:1804 */             dc -= cdz * 2048;
/* 1402:1805 */             cdx -= var18 * 2048;
/* 1403:1806 */             float var19 = this.theWorld.provider.getCloudHeight() - var21 + 0.33F;
/* 1404:1807 */             var19 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 1405:1808 */             float var20 = (float)(dc * var10);
/* 1406:1809 */             var5.startDrawingQuads();
/* 1407:1810 */             var5.setColorRGBA_F(exactPlayerX, var8, exactPlayerY, 0.8F);
/* 1408:1812 */             for (int var22 = -var3 * var4; var22 < var3 * var4; var22 += var3) {
/* 1409:1814 */               for (int var23 = -var3 * var4; var23 < var3 * var4; var23 += var3)
/* 1410:     */               {
/* 1411:1816 */                 var5.addVertexWithUV(var22 + 0, var19, var23 + var3, (var22 + 0) * var10 + var20, (var23 + var3) * var10 + var21);
/* 1412:1817 */                 var5.addVertexWithUV(var22 + var3, var19, var23 + var3, (var22 + var3) * var10 + var20, (var23 + var3) * var10 + var21);
/* 1413:1818 */                 var5.addVertexWithUV(var22 + var3, var19, var23 + 0, (var22 + var3) * var10 + var20, (var23 + 0) * var10 + var21);
/* 1414:1819 */                 var5.addVertexWithUV(var22 + 0, var19, var23 + 0, (var22 + 0) * var10 + var20, (var23 + 0) * var10 + var21);
/* 1415:     */               }
/* 1416:     */             }
/* 1417:1823 */             var5.draw();
/* 1418:1824 */             GL11.glEndList();
/* 1419:1825 */             this.isFancyGlListClouds = false;
/* 1420:1826 */             this.cloudTickCounterGlList = this.cloudTickCounter;
/* 1421:1827 */             this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
/* 1422:1828 */             this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
/* 1423:1829 */             this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
/* 1424:     */           }
/* 1425:1832 */           EntityLivingBase entityliving1 = this.mc.renderViewEntity;
/* 1426:1833 */           double exactPlayerX1 = entityliving1.prevPosX + (entityliving1.posX - entityliving1.prevPosX) * partialTicks1;
/* 1427:1834 */           double exactPlayerY1 = entityliving1.prevPosY + (entityliving1.posY - entityliving1.prevPosY) * partialTicks1;
/* 1428:1835 */           double exactPlayerZ1 = entityliving1.prevPosZ + (entityliving1.posZ - entityliving1.prevPosZ) * partialTicks1;
/* 1429:1836 */           double dc = this.cloudTickCounter - this.cloudTickCounterGlList + partialTicks1;
/* 1430:1837 */           float cdx1 = (float)(exactPlayerX1 - this.cloudPlayerX + dc * 0.03D);
/* 1431:1838 */           float cdy = (float)(exactPlayerY1 - this.cloudPlayerY);
/* 1432:1839 */           float cdz1 = (float)(exactPlayerZ1 - this.cloudPlayerZ);
/* 1433:1840 */           GL11.glTranslatef(-cdx1, -cdy, -cdz1);
/* 1434:1841 */           GL11.glCallList(this.glListClouds);
/* 1435:1842 */           GL11.glTranslatef(cdx1, cdy, cdz1);
/* 1436:1843 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1437:1844 */           GL11.glDisable(3042);
/* 1438:1845 */           GL11.glEnable(2884);
/* 1439:     */         }
/* 1440:     */       }
/* 1441:     */     }
/* 1442:     */   }
/* 1443:     */   
/* 1444:     */   public boolean hasCloudFog(double par1, double par3, double par5, float par7)
/* 1445:     */   {
/* 1446:1856 */     return false;
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   public void renderCloudsFancy(float par1)
/* 1450:     */   {
/* 1451:1864 */     float partialTicks = par1;
/* 1452:1865 */     par1 = 0.0F;
/* 1453:1866 */     GL11.glDisable(2884);
/* 1454:1867 */     float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
/* 1455:1868 */     Tessellator var3 = Tessellator.instance;
/* 1456:1869 */     float var4 = 12.0F;
/* 1457:1870 */     float var5 = 4.0F;
/* 1458:1871 */     double var6 = this.cloudTickCounter + par1;
/* 1459:1872 */     double var8 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + var6 * 0.02999999932944775D) / var4;
/* 1460:1873 */     double var10 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1) / var4 + 0.3300000131130219D;
/* 1461:1874 */     float var12 = this.theWorld.provider.getCloudHeight() - var2 + 0.33F;
/* 1462:1875 */     var12 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 1463:1876 */     int var13 = MathHelper.floor_double(var8 / 2048.0D);
/* 1464:1877 */     int var14 = MathHelper.floor_double(var10 / 2048.0D);
/* 1465:1878 */     var8 -= var13 * 2048;
/* 1466:1879 */     var10 -= var14 * 2048;
/* 1467:1880 */     this.renderEngine.bindTexture(locationCloudsPng);
/* 1468:1881 */     GL11.glEnable(3042);
/* 1469:1882 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1470:1885 */     if ((!this.isFancyGlListClouds) || (this.cloudTickCounter >= this.cloudTickCounterGlList + 20))
/* 1471:     */     {
/* 1472:1887 */       GL11.glNewList(this.glListClouds, 4864);
/* 1473:1888 */       Vec3 entityliving = this.theWorld.getCloudColour(par1);
/* 1474:1889 */       float exactPlayerX = (float)entityliving.xCoord;
/* 1475:1890 */       float var17 = (float)entityliving.yCoord;
/* 1476:1891 */       float exactPlayerY = (float)entityliving.zCoord;
/* 1477:1896 */       if (this.mc.gameSettings.anaglyph)
/* 1478:     */       {
/* 1479:1898 */         float var19 = (exactPlayerX * 30.0F + var17 * 59.0F + exactPlayerY * 11.0F) / 100.0F;
/* 1480:1899 */         float var20 = (exactPlayerX * 30.0F + var17 * 70.0F) / 100.0F;
/* 1481:1900 */         float exactPlayerZ = (exactPlayerX * 30.0F + exactPlayerY * 70.0F) / 100.0F;
/* 1482:1901 */         exactPlayerX = var19;
/* 1483:1902 */         var17 = var20;
/* 1484:1903 */         exactPlayerY = exactPlayerZ;
/* 1485:     */       }
/* 1486:1906 */       float var19 = (float)(var8 * 0.0D);
/* 1487:1907 */       float var20 = (float)(var10 * 0.0D);
/* 1488:1908 */       float exactPlayerZ = 0.0039063F;
/* 1489:1909 */       var19 = MathHelper.floor_double(var8) * exactPlayerZ;
/* 1490:1910 */       var20 = MathHelper.floor_double(var10) * exactPlayerZ;
/* 1491:1911 */       float dc = (float)(var8 - MathHelper.floor_double(var8));
/* 1492:1912 */       float var23 = (float)(var10 - MathHelper.floor_double(var10));
/* 1493:1913 */       byte cdx = 8;
/* 1494:1914 */       byte cdy = 4;
/* 1495:1915 */       float cdz = 0.000976563F;
/* 1496:1916 */       GL11.glScalef(var4, 1.0F, var4);
/* 1497:1918 */       for (int var27 = 0; var27 < 2; var27++)
/* 1498:     */       {
/* 1499:1920 */         if (var27 == 0) {
/* 1500:1922 */           GL11.glColorMask(false, false, false, false);
/* 1501:1924 */         } else if (this.mc.gameSettings.anaglyph)
/* 1502:     */         {
/* 1503:1926 */           if (EntityRenderer.anaglyphField == 0) {
/* 1504:1928 */             GL11.glColorMask(false, true, true, true);
/* 1505:     */           } else {
/* 1506:1932 */             GL11.glColorMask(true, false, false, true);
/* 1507:     */           }
/* 1508:     */         }
/* 1509:     */         else {
/* 1510:1937 */           GL11.glColorMask(true, true, true, true);
/* 1511:     */         }
/* 1512:1940 */         for (int var28 = -cdy + 1; var28 <= cdy; var28++) {
/* 1513:1942 */           for (int var29 = -cdy + 1; var29 <= cdy; var29++)
/* 1514:     */           {
/* 1515:1944 */             var3.startDrawingQuads();
/* 1516:1945 */             float var30 = var28 * cdx;
/* 1517:1946 */             float var31 = var29 * cdx;
/* 1518:1947 */             float var32 = var30 - dc;
/* 1519:1948 */             float var33 = var31 - var23;
/* 1520:1950 */             if (var12 > -var5 - 1.0F)
/* 1521:     */             {
/* 1522:1952 */               var3.setColorRGBA_F(exactPlayerX * 0.7F, var17 * 0.7F, exactPlayerY * 0.7F, 0.8F);
/* 1523:1953 */               var3.setNormal(0.0F, -1.0F, 0.0F);
/* 1524:1954 */               var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + cdx, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1525:1955 */               var3.addVertexWithUV(var32 + cdx, var12 + 0.0F, var33 + cdx, (var30 + cdx) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1526:1956 */               var3.addVertexWithUV(var32 + cdx, var12 + 0.0F, var33 + 0.0F, (var30 + cdx) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1527:1957 */               var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + 0.0F, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1528:     */             }
/* 1529:1960 */             if (var12 <= var5 + 1.0F)
/* 1530:     */             {
/* 1531:1962 */               var3.setColorRGBA_F(exactPlayerX, var17, exactPlayerY, 0.8F);
/* 1532:1963 */               var3.setNormal(0.0F, 1.0F, 0.0F);
/* 1533:1964 */               var3.addVertexWithUV(var32 + 0.0F, var12 + var5 - cdz, var33 + cdx, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1534:1965 */               var3.addVertexWithUV(var32 + cdx, var12 + var5 - cdz, var33 + cdx, (var30 + cdx) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1535:1966 */               var3.addVertexWithUV(var32 + cdx, var12 + var5 - cdz, var33 + 0.0F, (var30 + cdx) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1536:1967 */               var3.addVertexWithUV(var32 + 0.0F, var12 + var5 - cdz, var33 + 0.0F, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1537:     */             }
/* 1538:1970 */             var3.setColorRGBA_F(exactPlayerX * 0.9F, var17 * 0.9F, exactPlayerY * 0.9F, 0.8F);
/* 1539:1973 */             if (var28 > -1)
/* 1540:     */             {
/* 1541:1975 */               var3.setNormal(-1.0F, 0.0F, 0.0F);
/* 1542:1977 */               for (int var34 = 0; var34 < cdx; var34++)
/* 1543:     */               {
/* 1544:1979 */                 var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + 0.0F, var33 + cdx, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1545:1980 */                 var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + var5, var33 + cdx, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1546:1981 */                 var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + var5, var33 + 0.0F, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1547:1982 */                 var3.addVertexWithUV(var32 + var34 + 0.0F, var12 + 0.0F, var33 + 0.0F, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1548:     */               }
/* 1549:     */             }
/* 1550:1986 */             if (var28 <= 1)
/* 1551:     */             {
/* 1552:1988 */               var3.setNormal(1.0F, 0.0F, 0.0F);
/* 1553:1990 */               for (int var34 = 0; var34 < cdx; var34++)
/* 1554:     */               {
/* 1555:1992 */                 var3.addVertexWithUV(var32 + var34 + 1.0F - cdz, var12 + 0.0F, var33 + cdx, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1556:1993 */                 var3.addVertexWithUV(var32 + var34 + 1.0F - cdz, var12 + var5, var33 + cdx, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + cdx) * exactPlayerZ + var20);
/* 1557:1994 */                 var3.addVertexWithUV(var32 + var34 + 1.0F - cdz, var12 + var5, var33 + 0.0F, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1558:1995 */                 var3.addVertexWithUV(var32 + var34 + 1.0F - cdz, var12 + 0.0F, var33 + 0.0F, (var30 + var34 + 0.5F) * exactPlayerZ + var19, (var31 + 0.0F) * exactPlayerZ + var20);
/* 1559:     */               }
/* 1560:     */             }
/* 1561:1999 */             var3.setColorRGBA_F(exactPlayerX * 0.8F, var17 * 0.8F, exactPlayerY * 0.8F, 0.8F);
/* 1562:2001 */             if (var29 > -1)
/* 1563:     */             {
/* 1564:2003 */               var3.setNormal(0.0F, 0.0F, -1.0F);
/* 1565:2005 */               for (int var34 = 0; var34 < cdx; var34++)
/* 1566:     */               {
/* 1567:2007 */                 var3.addVertexWithUV(var32 + 0.0F, var12 + var5, var33 + var34 + 0.0F, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1568:2008 */                 var3.addVertexWithUV(var32 + cdx, var12 + var5, var33 + var34 + 0.0F, (var30 + cdx) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1569:2009 */                 var3.addVertexWithUV(var32 + cdx, var12 + 0.0F, var33 + var34 + 0.0F, (var30 + cdx) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1570:2010 */                 var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + var34 + 0.0F, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1571:     */               }
/* 1572:     */             }
/* 1573:2014 */             if (var29 <= 1)
/* 1574:     */             {
/* 1575:2016 */               var3.setNormal(0.0F, 0.0F, 1.0F);
/* 1576:2018 */               for (int var34 = 0; var34 < cdx; var34++)
/* 1577:     */               {
/* 1578:2020 */                 var3.addVertexWithUV(var32 + 0.0F, var12 + var5, var33 + var34 + 1.0F - cdz, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1579:2021 */                 var3.addVertexWithUV(var32 + cdx, var12 + var5, var33 + var34 + 1.0F - cdz, (var30 + cdx) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1580:2022 */                 var3.addVertexWithUV(var32 + cdx, var12 + 0.0F, var33 + var34 + 1.0F - cdz, (var30 + cdx) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1581:2023 */                 var3.addVertexWithUV(var32 + 0.0F, var12 + 0.0F, var33 + var34 + 1.0F - cdz, (var30 + 0.0F) * exactPlayerZ + var19, (var31 + var34 + 0.5F) * exactPlayerZ + var20);
/* 1582:     */               }
/* 1583:     */             }
/* 1584:2027 */             var3.draw();
/* 1585:     */           }
/* 1586:     */         }
/* 1587:     */       }
/* 1588:2032 */       GL11.glEndList();
/* 1589:2033 */       this.isFancyGlListClouds = true;
/* 1590:2034 */       this.cloudTickCounterGlList = this.cloudTickCounter;
/* 1591:2035 */       this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
/* 1592:2036 */       this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
/* 1593:2037 */       this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
/* 1594:     */     }
/* 1595:2040 */     EntityLivingBase var36 = this.mc.renderViewEntity;
/* 1596:2041 */     double var37 = var36.prevPosX + (var36.posX - var36.prevPosX) * partialTicks;
/* 1597:2042 */     double var38 = var36.prevPosY + (var36.posY - var36.prevPosY) * partialTicks;
/* 1598:2043 */     double var40 = var36.prevPosZ + (var36.posZ - var36.prevPosZ) * partialTicks;
/* 1599:2044 */     double var39 = this.cloudTickCounter - this.cloudTickCounterGlList + partialTicks;
/* 1600:2045 */     float var41 = (float)(var37 - this.cloudPlayerX + var39 * 0.03D);
/* 1601:2046 */     float var42 = (float)(var38 - this.cloudPlayerY);
/* 1602:2047 */     float cdz = (float)(var40 - this.cloudPlayerZ);
/* 1603:2048 */     GL11.glTranslatef(-var41, -var42, -cdz);
/* 1604:2049 */     GL11.glCallList(this.glListClouds);
/* 1605:2050 */     GL11.glTranslatef(var41, var42, cdz);
/* 1606:2051 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1607:2052 */     GL11.glDisable(3042);
/* 1608:2053 */     GL11.glEnable(2884);
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   public boolean updateRenderers(EntityLivingBase entityliving, boolean flag)
/* 1612:     */   {
/* 1613:2061 */     this.renderViewEntity = entityliving;
/* 1614:2063 */     if (WrUpdates.hasWrUpdater()) {
/* 1615:2065 */       return WrUpdates.updateRenderers(this, entityliving, flag);
/* 1616:     */     }
/* 1617:2067 */     if (this.worldRenderersToUpdate.size() <= 0) {
/* 1618:2069 */       return false;
/* 1619:     */     }
/* 1620:2073 */     int num = 0;
/* 1621:2074 */     int maxNum = Config.getUpdatesPerFrame();
/* 1622:2076 */     if ((Config.isDynamicUpdates()) && (!isMoving(entityliving))) {
/* 1623:2078 */       maxNum *= 3;
/* 1624:     */     }
/* 1625:2081 */     byte NOT_IN_FRUSTRUM_MUL = 4;
/* 1626:2082 */     int numValid = 0;
/* 1627:2083 */     WorldRenderer wrBest = null;
/* 1628:2084 */     float distSqBest = 3.4028235E+38F;
/* 1629:2085 */     int indexBest = -1;
/* 1630:2087 */     for (int maxDiffDistSq = 0; maxDiffDistSq < this.worldRenderersToUpdate.size(); maxDiffDistSq++)
/* 1631:     */     {
/* 1632:2089 */       WorldRenderer i = (WorldRenderer)this.worldRenderersToUpdate.get(maxDiffDistSq);
/* 1633:2091 */       if (i != null)
/* 1634:     */       {
/* 1635:2093 */         numValid++;
/* 1636:2095 */         if (!i.needsUpdate)
/* 1637:     */         {
/* 1638:2097 */           this.worldRenderersToUpdate.set(maxDiffDistSq, null);
/* 1639:     */         }
/* 1640:     */         else
/* 1641:     */         {
/* 1642:2101 */           float wr = i.distanceToEntitySquared(entityliving);
/* 1643:2103 */           if ((wr <= 256.0F) && (isActingNow()))
/* 1644:     */           {
/* 1645:2105 */             i.updateRenderer(entityliving);
/* 1646:2106 */             i.needsUpdate = false;
/* 1647:2107 */             this.worldRenderersToUpdate.set(maxDiffDistSq, null);
/* 1648:2108 */             num++;
/* 1649:     */           }
/* 1650:     */           else
/* 1651:     */           {
/* 1652:2112 */             if ((wr > 256.0F) && (num >= maxNum)) {
/* 1653:     */               break;
/* 1654:     */             }
/* 1655:2117 */             if (!i.isInFrustum) {
/* 1656:2119 */               wr *= NOT_IN_FRUSTRUM_MUL;
/* 1657:     */             }
/* 1658:2122 */             if (wrBest == null)
/* 1659:     */             {
/* 1660:2124 */               wrBest = i;
/* 1661:2125 */               distSqBest = wr;
/* 1662:2126 */               indexBest = maxDiffDistSq;
/* 1663:     */             }
/* 1664:2128 */             else if (wr < distSqBest)
/* 1665:     */             {
/* 1666:2130 */               wrBest = i;
/* 1667:2131 */               distSqBest = wr;
/* 1668:2132 */               indexBest = maxDiffDistSq;
/* 1669:     */             }
/* 1670:     */           }
/* 1671:     */         }
/* 1672:     */       }
/* 1673:     */     }
/* 1674:2139 */     if (wrBest != null)
/* 1675:     */     {
/* 1676:2141 */       wrBest.updateRenderer(entityliving);
/* 1677:2142 */       wrBest.needsUpdate = false;
/* 1678:2143 */       this.worldRenderersToUpdate.set(indexBest, null);
/* 1679:2144 */       num++;
/* 1680:2145 */       float var16 = distSqBest / 5.0F;
/* 1681:2147 */       for (int var15 = 0; (var15 < this.worldRenderersToUpdate.size()) && (num < maxNum); var15++)
/* 1682:     */       {
/* 1683:2149 */         WorldRenderer var17 = (WorldRenderer)this.worldRenderersToUpdate.get(var15);
/* 1684:2151 */         if (var17 != null)
/* 1685:     */         {
/* 1686:2153 */           float distSq = var17.distanceToEntitySquared(entityliving);
/* 1687:2155 */           if (!var17.isInFrustum) {
/* 1688:2157 */             distSq *= NOT_IN_FRUSTRUM_MUL;
/* 1689:     */           }
/* 1690:2160 */           float diffDistSq = Math.abs(distSq - distSqBest);
/* 1691:2162 */           if (diffDistSq < var16)
/* 1692:     */           {
/* 1693:2164 */             var17.updateRenderer(entityliving);
/* 1694:2165 */             var17.needsUpdate = false;
/* 1695:2166 */             this.worldRenderersToUpdate.set(var15, null);
/* 1696:2167 */             num++;
/* 1697:     */           }
/* 1698:     */         }
/* 1699:     */       }
/* 1700:     */     }
/* 1701:2173 */     if (numValid == 0) {
/* 1702:2175 */       this.worldRenderersToUpdate.clear();
/* 1703:     */     }
/* 1704:2178 */     this.worldRenderersToUpdate.compact();
/* 1705:2179 */     return true;
/* 1706:     */   }
/* 1707:     */   
/* 1708:     */   public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityPlayer par2EntityPlayer, float par3)
/* 1709:     */   {
/* 1710:2185 */     drawBlockDamageTexture(par1Tessellator, par2EntityPlayer, par3);
/* 1711:     */   }
/* 1712:     */   
/* 1713:     */   public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityLivingBase par2EntityPlayer, float par3)
/* 1714:     */   {
/* 1715:2190 */     double var4 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * par3;
/* 1716:2191 */     double var6 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * par3;
/* 1717:2192 */     double var8 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * par3;
/* 1718:2194 */     if (!this.damagedBlocks.isEmpty())
/* 1719:     */     {
/* 1720:2196 */       OpenGlHelper.glBlendFunc(774, 768, 1, 0);
/* 1721:2197 */       this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 1722:2198 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
/* 1723:2199 */       GL11.glPushMatrix();
/* 1724:2200 */       GL11.glPolygonOffset(-3.0F, -3.0F);
/* 1725:2201 */       GL11.glEnable(32823);
/* 1726:2202 */       GL11.glEnable(3008);
/* 1727:2203 */       par1Tessellator.startDrawingQuads();
/* 1728:2204 */       par1Tessellator.setTranslation(-var4, -var6, -var8);
/* 1729:2205 */       par1Tessellator.disableColor();
/* 1730:2206 */       Iterator var10 = this.damagedBlocks.values().iterator();
/* 1731:2208 */       while (var10.hasNext())
/* 1732:     */       {
/* 1733:2210 */         DestroyBlockProgress var11 = (DestroyBlockProgress)var10.next();
/* 1734:2211 */         double var12 = var11.getPartialBlockX() - var4;
/* 1735:2212 */         double var14 = var11.getPartialBlockY() - var6;
/* 1736:2213 */         double var16 = var11.getPartialBlockZ() - var8;
/* 1737:2215 */         if (var12 * var12 + var14 * var14 + var16 * var16 > 1024.0D)
/* 1738:     */         {
/* 1739:2217 */           var10.remove();
/* 1740:     */         }
/* 1741:     */         else
/* 1742:     */         {
/* 1743:2221 */           Block var18 = this.theWorld.getBlock(var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ());
/* 1744:2223 */           if (var18.getMaterial() != Material.air) {
/* 1745:2225 */             this.renderBlocksRg.renderBlockUsingTexture(var18, var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ(), this.destroyBlockIcons[var11.getPartialBlockDamage()]);
/* 1746:     */           }
/* 1747:     */         }
/* 1748:     */       }
/* 1749:2230 */       par1Tessellator.draw();
/* 1750:2231 */       par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
/* 1751:2232 */       GL11.glDisable(3008);
/* 1752:2233 */       GL11.glPolygonOffset(0.0F, 0.0F);
/* 1753:2234 */       GL11.glDisable(32823);
/* 1754:2235 */       GL11.glEnable(3008);
/* 1755:2236 */       GL11.glDepthMask(true);
/* 1756:2237 */       GL11.glPopMatrix();
/* 1757:     */     }
/* 1758:     */   }
/* 1759:     */   
/* 1760:     */   public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4)
/* 1761:     */   {
/* 1762:2246 */     if ((par3 == 0) && (par2MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK))
/* 1763:     */     {
/* 1764:2248 */       GL11.glEnable(3042);
/* 1765:2249 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1766:2250 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
/* 1767:2251 */       GL11.glLineWidth(2.0F);
/* 1768:2252 */       GL11.glDisable(3553);
/* 1769:2253 */       GL11.glDepthMask(false);
/* 1770:2254 */       float var5 = 0.002F;
/* 1771:2255 */       Block var6 = this.theWorld.getBlock(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
/* 1772:2257 */       if (var6.getMaterial() != Material.air)
/* 1773:     */       {
/* 1774:2259 */         var6.setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
/* 1775:2260 */         double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * par4;
/* 1776:2261 */         double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * par4;
/* 1777:2262 */         double var11 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * par4;
/* 1778:2263 */         drawOutlinedBoundingBox(var6.getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(var5, var5, var5).getOffsetBoundingBox(-var7, -var9, -var11), -1);
/* 1779:     */       }
/* 1780:2266 */       GL11.glDepthMask(true);
/* 1781:2267 */       GL11.glEnable(3553);
/* 1782:2268 */       GL11.glDisable(3042);
/* 1783:     */     }
/* 1784:     */   }
/* 1785:     */   
/* 1786:     */   public static void drawOutlinedBoundingBox(AxisAlignedBB p_147590_0_, int p_147590_1_)
/* 1787:     */   {
/* 1788:2277 */     Tessellator var2 = Tessellator.instance;
/* 1789:2278 */     var2.startDrawing(3);
/* 1790:2280 */     if (p_147590_1_ != -1) {
/* 1791:2282 */       var2.setColorOpaque_I(p_147590_1_);
/* 1792:     */     }
/* 1793:2285 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
/* 1794:2286 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
/* 1795:2287 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
/* 1796:2288 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
/* 1797:2289 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
/* 1798:2290 */     var2.draw();
/* 1799:2291 */     var2.startDrawing(3);
/* 1800:2293 */     if (p_147590_1_ != -1) {
/* 1801:2295 */       var2.setColorOpaque_I(p_147590_1_);
/* 1802:     */     }
/* 1803:2298 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
/* 1804:2299 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
/* 1805:2300 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
/* 1806:2301 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
/* 1807:2302 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
/* 1808:2303 */     var2.draw();
/* 1809:2304 */     var2.startDrawing(1);
/* 1810:2306 */     if (p_147590_1_ != -1) {
/* 1811:2308 */       var2.setColorOpaque_I(p_147590_1_);
/* 1812:     */     }
/* 1813:2311 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
/* 1814:2312 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
/* 1815:2313 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
/* 1816:2314 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
/* 1817:2315 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
/* 1818:2316 */     var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
/* 1819:2317 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
/* 1820:2318 */     var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
/* 1821:2319 */     var2.draw();
/* 1822:     */   }
/* 1823:     */   
/* 1824:     */   public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
/* 1825:     */   {
/* 1826:2327 */     int var7 = MathHelper.bucketInt(par1, 16);
/* 1827:2328 */     int var8 = MathHelper.bucketInt(par2, 16);
/* 1828:2329 */     int var9 = MathHelper.bucketInt(par3, 16);
/* 1829:2330 */     int var10 = MathHelper.bucketInt(par4, 16);
/* 1830:2331 */     int var11 = MathHelper.bucketInt(par5, 16);
/* 1831:2332 */     int var12 = MathHelper.bucketInt(par6, 16);
/* 1832:2334 */     for (int var13 = var7; var13 <= var10; var13++)
/* 1833:     */     {
/* 1834:2336 */       int var14 = var13 % this.renderChunksWide;
/* 1835:2338 */       if (var14 < 0) {
/* 1836:2340 */         var14 += this.renderChunksWide;
/* 1837:     */       }
/* 1838:2343 */       for (int var15 = var8; var15 <= var11; var15++)
/* 1839:     */       {
/* 1840:2345 */         int var16 = var15 % this.renderChunksTall;
/* 1841:2347 */         if (var16 < 0) {
/* 1842:2349 */           var16 += this.renderChunksTall;
/* 1843:     */         }
/* 1844:2352 */         for (int var17 = var9; var17 <= var12; var17++)
/* 1845:     */         {
/* 1846:2354 */           int var18 = var17 % this.renderChunksDeep;
/* 1847:2356 */           if (var18 < 0) {
/* 1848:2358 */             var18 += this.renderChunksDeep;
/* 1849:     */           }
/* 1850:2361 */           int var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14;
/* 1851:2362 */           WorldRenderer var20 = this.worldRenderers[var19];
/* 1852:2364 */           if ((var20 != null) && (!var20.needsUpdate))
/* 1853:     */           {
/* 1854:2366 */             this.worldRenderersToUpdate.add(var20);
/* 1855:2367 */             var20.markDirty();
/* 1856:     */           }
/* 1857:     */         }
/* 1858:     */       }
/* 1859:     */     }
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_)
/* 1863:     */   {
/* 1864:2380 */     markBlocksForUpdate(p_147586_1_ - 1, p_147586_2_ - 1, p_147586_3_ - 1, p_147586_1_ + 1, p_147586_2_ + 1, p_147586_3_ + 1);
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_)
/* 1868:     */   {
/* 1869:2388 */     markBlocksForUpdate(p_147588_1_ - 1, p_147588_2_ - 1, p_147588_3_ - 1, p_147588_1_ + 1, p_147588_2_ + 1, p_147588_3_ + 1);
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   public void markBlockRangeForRenderUpdate(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_)
/* 1873:     */   {
/* 1874:2397 */     markBlocksForUpdate(p_147585_1_ - 1, p_147585_2_ - 1, p_147585_3_ - 1, p_147585_4_ + 1, p_147585_5_ + 1, p_147585_6_ + 1);
/* 1875:     */   }
/* 1876:     */   
/* 1877:     */   public void clipRenderersByFrustum(ICamera par1ICamera, float par2)
/* 1878:     */   {
/* 1879:2406 */     for (int var3 = 0; var3 < this.countSortedWorldRenderers; var3++)
/* 1880:     */     {
/* 1881:2408 */       WorldRenderer wr = this.sortedWorldRenderers[var3];
/* 1882:2410 */       if (!wr.skipAllRenderPasses()) {
/* 1883:2412 */         wr.updateInFrustum(par1ICamera);
/* 1884:     */       }
/* 1885:     */     }
/* 1886:     */   }
/* 1887:     */   
/* 1888:     */   public void playRecord(String par1Str, int par2, int par3, int par4)
/* 1889:     */   {
/* 1890:2422 */     ChunkCoordinates var5 = new ChunkCoordinates(par2, par3, par4);
/* 1891:2423 */     ISound var6 = (ISound)this.mapSoundPositions.get(var5);
/* 1892:2425 */     if (var6 != null)
/* 1893:     */     {
/* 1894:2427 */       this.mc.getSoundHandler().func_147683_b(var6);
/* 1895:2428 */       this.mapSoundPositions.remove(var5);
/* 1896:     */     }
/* 1897:2431 */     if (par1Str != null)
/* 1898:     */     {
/* 1899:2433 */       ItemRecord var7 = ItemRecord.func_150926_b(par1Str);
/* 1900:2435 */       if (var7 != null) {
/* 1901:2437 */         this.mc.ingameGUI.setRecordPlayingMessage(var7.func_150927_i());
/* 1902:     */       }
/* 1903:2440 */       PositionedSoundRecord var8 = PositionedSoundRecord.func_147675_a(new ResourceLocation(par1Str), par2, par3, par4);
/* 1904:2441 */       this.mapSoundPositions.put(var5, var8);
/* 1905:2442 */       this.mc.getSoundHandler().playSound(var8);
/* 1906:     */     }
/* 1907:     */   }
/* 1908:     */   
/* 1909:     */   public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {}
/* 1910:     */   
/* 1911:     */   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10) {}
/* 1912:     */   
/* 1913:     */   public void spawnParticle(String par1Str, final double par2, double par4, final double par6, double par8, double par10, double par12)
/* 1914:     */   {
/* 1915:     */     try
/* 1916:     */     {
/* 1917:2463 */       doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
/* 1918:     */     }
/* 1919:     */     catch (Throwable var17)
/* 1920:     */     {
/* 1921:2467 */       CrashReport var15 = CrashReport.makeCrashReport(var17, "Exception while adding particle");
/* 1922:2468 */       CrashReportCategory var16 = var15.makeCategory("Particle being added");
/* 1923:2469 */       var16.addCrashSection("Name", par1Str);
/* 1924:2470 */       var16.addCrashSectionCallable("Position", new Callable()
/* 1925:     */       {
/* 1926:     */         private static final String __OBFID = "CL_00000955";
/* 1927:     */         
/* 1928:     */         public String call()
/* 1929:     */         {
/* 1930:2475 */           return CrashReportCategory.func_85074_a(par2, par6, this.val$par6);
/* 1931:     */         }
/* 1932:2477 */       });
/* 1933:2478 */       throw new ReportedException(var15);
/* 1934:     */     }
/* 1935:     */   }
/* 1936:     */   
/* 1937:     */   public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
/* 1938:     */   {
/* 1939:2487 */     if ((this.mc != null) && (this.mc.renderViewEntity != null) && (this.mc.effectRenderer != null))
/* 1940:     */     {
/* 1941:2489 */       int var14 = this.mc.gameSettings.particleSetting;
/* 1942:2491 */       if ((var14 == 1) && (this.theWorld.rand.nextInt(3) == 0)) {
/* 1943:2493 */         var14 = 2;
/* 1944:     */       }
/* 1945:2496 */       double var15 = this.mc.renderViewEntity.posX - par2;
/* 1946:2497 */       double var17 = this.mc.renderViewEntity.posY - par4;
/* 1947:2498 */       double var19 = this.mc.renderViewEntity.posZ - par6;
/* 1948:2499 */       EntityFX var21 = null;
/* 1949:2501 */       if (par1Str.equals("hugeexplosion"))
/* 1950:     */       {
/* 1951:2503 */         if (Config.isAnimatedExplosion()) {
/* 1952:2505 */           this.mc.effectRenderer.addEffect(var21 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
/* 1953:     */         }
/* 1954:     */       }
/* 1955:2508 */       else if (par1Str.equals("largeexplode"))
/* 1956:     */       {
/* 1957:2510 */         if (Config.isAnimatedExplosion()) {
/* 1958:2512 */           this.mc.effectRenderer.addEffect(var21 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
/* 1959:     */         }
/* 1960:     */       }
/* 1961:2515 */       else if (par1Str.equals("fireworksSpark")) {
/* 1962:2517 */         this.mc.effectRenderer.addEffect(var21 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
/* 1963:     */       }
/* 1964:2520 */       if (var21 != null) {
/* 1965:2522 */         return var21;
/* 1966:     */       }
/* 1967:2526 */       double var22 = 16.0D;
/* 1968:2527 */       double d3 = 16.0D;
/* 1969:2529 */       if (par1Str.equals("crit")) {
/* 1970:2531 */         var22 = 196.0D;
/* 1971:     */       }
/* 1972:2534 */       if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) {
/* 1973:2536 */         return null;
/* 1974:     */       }
/* 1975:2538 */       if (var14 > 1) {
/* 1976:2540 */         return null;
/* 1977:     */       }
/* 1978:2544 */       if (par1Str.equals("bubble"))
/* 1979:     */       {
/* 1980:2546 */         var21 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 1981:2547 */         CustomColorizer.updateWaterFX(var21, this.theWorld);
/* 1982:     */       }
/* 1983:2549 */       else if (par1Str.equals("suspended"))
/* 1984:     */       {
/* 1985:2551 */         if (Config.isWaterParticles()) {
/* 1986:2553 */           var21 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 1987:     */         }
/* 1988:     */       }
/* 1989:2556 */       else if (par1Str.equals("depthsuspend"))
/* 1990:     */       {
/* 1991:2558 */         if (Config.isVoidParticles()) {
/* 1992:2560 */           var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 1993:     */         }
/* 1994:     */       }
/* 1995:2563 */       else if (par1Str.equals("townaura"))
/* 1996:     */       {
/* 1997:2565 */         var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 1998:2566 */         CustomColorizer.updateMyceliumFX(var21);
/* 1999:     */       }
/* 2000:2568 */       else if (par1Str.equals("crit"))
/* 2001:     */       {
/* 2002:2570 */         var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2003:     */       }
/* 2004:2572 */       else if (par1Str.equals("magicCrit"))
/* 2005:     */       {
/* 2006:2574 */         var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2007:2575 */         var21.setRBGColorF(var21.getRedColorF() * 0.3F, var21.getGreenColorF() * 0.8F, var21.getBlueColorF());
/* 2008:2576 */         var21.nextTextureIndexX();
/* 2009:     */       }
/* 2010:2578 */       else if (par1Str.equals("smoke"))
/* 2011:     */       {
/* 2012:2580 */         if (Config.isAnimatedSmoke()) {
/* 2013:2582 */           var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2014:     */         }
/* 2015:     */       }
/* 2016:2585 */       else if (par1Str.equals("mobSpell"))
/* 2017:     */       {
/* 2018:2587 */         if (Config.isPotionParticles())
/* 2019:     */         {
/* 2020:2589 */           var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 2021:2590 */           var21.setRBGColorF((float)par8, (float)par10, (float)par12);
/* 2022:     */         }
/* 2023:     */       }
/* 2024:2593 */       else if (par1Str.equals("mobSpellAmbient"))
/* 2025:     */       {
/* 2026:2595 */         if (Config.isPotionParticles())
/* 2027:     */         {
/* 2028:2597 */           var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 2029:2598 */           var21.setAlphaF(0.15F);
/* 2030:2599 */           var21.setRBGColorF((float)par8, (float)par10, (float)par12);
/* 2031:     */         }
/* 2032:     */       }
/* 2033:2602 */       else if (par1Str.equals("spell"))
/* 2034:     */       {
/* 2035:2604 */         if (Config.isPotionParticles()) {
/* 2036:2606 */           var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2037:     */         }
/* 2038:     */       }
/* 2039:2609 */       else if (par1Str.equals("instantSpell"))
/* 2040:     */       {
/* 2041:2611 */         if (Config.isPotionParticles())
/* 2042:     */         {
/* 2043:2613 */           var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2044:2614 */           ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
/* 2045:     */         }
/* 2046:     */       }
/* 2047:2617 */       else if (par1Str.equals("witchMagic"))
/* 2048:     */       {
/* 2049:2619 */         if (Config.isPotionParticles())
/* 2050:     */         {
/* 2051:2621 */           var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2052:2622 */           ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
/* 2053:2623 */           float var26 = this.theWorld.rand.nextFloat() * 0.5F + 0.35F;
/* 2054:2624 */           var21.setRBGColorF(1.0F * var26, 0.0F * var26, 1.0F * var26);
/* 2055:     */         }
/* 2056:     */       }
/* 2057:2627 */       else if (par1Str.equals("note"))
/* 2058:     */       {
/* 2059:2629 */         var21 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2060:     */       }
/* 2061:2631 */       else if (par1Str.equals("portal"))
/* 2062:     */       {
/* 2063:2633 */         if (Config.isPortalParticles())
/* 2064:     */         {
/* 2065:2635 */           var21 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2066:2636 */           CustomColorizer.updatePortalFX(var21);
/* 2067:     */         }
/* 2068:     */       }
/* 2069:2639 */       else if (par1Str.equals("enchantmenttable"))
/* 2070:     */       {
/* 2071:2641 */         var21 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2072:     */       }
/* 2073:2643 */       else if (par1Str.equals("explode"))
/* 2074:     */       {
/* 2075:2645 */         if (Config.isAnimatedExplosion()) {
/* 2076:2647 */           var21 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2077:     */         }
/* 2078:     */       }
/* 2079:2650 */       else if (par1Str.equals("flame"))
/* 2080:     */       {
/* 2081:2652 */         if (Config.isAnimatedFlame()) {
/* 2082:2654 */           var21 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2083:     */         }
/* 2084:     */       }
/* 2085:2657 */       else if (par1Str.equals("lava"))
/* 2086:     */       {
/* 2087:2659 */         var21 = new EntityLavaFX(this.theWorld, par2, par4, par6);
/* 2088:     */       }
/* 2089:2661 */       else if (par1Str.equals("footstep"))
/* 2090:     */       {
/* 2091:2663 */         var21 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
/* 2092:     */       }
/* 2093:2665 */       else if (par1Str.equals("splash"))
/* 2094:     */       {
/* 2095:2667 */         var21 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2096:2668 */         CustomColorizer.updateWaterFX(var21, this.theWorld);
/* 2097:     */       }
/* 2098:2670 */       else if (par1Str.equals("wake"))
/* 2099:     */       {
/* 2100:2672 */         var21 = new EntityFishWakeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2101:     */       }
/* 2102:2674 */       else if (par1Str.equals("largesmoke"))
/* 2103:     */       {
/* 2104:2676 */         if (Config.isAnimatedSmoke()) {
/* 2105:2678 */           var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
/* 2106:     */         }
/* 2107:     */       }
/* 2108:2681 */       else if (par1Str.equals("cloud"))
/* 2109:     */       {
/* 2110:2683 */         var21 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2111:     */       }
/* 2112:2685 */       else if (par1Str.equals("reddust"))
/* 2113:     */       {
/* 2114:2687 */         if (Config.isAnimatedRedstone())
/* 2115:     */         {
/* 2116:2689 */           var21 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
/* 2117:2690 */           CustomColorizer.updateReddustFX(var21, this.theWorld, var15, var17, var19);
/* 2118:     */         }
/* 2119:     */       }
/* 2120:2693 */       else if (par1Str.equals("snowballpoof"))
/* 2121:     */       {
/* 2122:2695 */         var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.snowball);
/* 2123:     */       }
/* 2124:2697 */       else if (par1Str.equals("dripWater"))
/* 2125:     */       {
/* 2126:2699 */         if (Config.isDrippingWaterLava()) {
/* 2127:2701 */           var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
/* 2128:     */         }
/* 2129:     */       }
/* 2130:2704 */       else if (par1Str.equals("dripLava"))
/* 2131:     */       {
/* 2132:2706 */         if (Config.isDrippingWaterLava()) {
/* 2133:2708 */           var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
/* 2134:     */         }
/* 2135:     */       }
/* 2136:2711 */       else if (par1Str.equals("snowshovel"))
/* 2137:     */       {
/* 2138:2713 */         var21 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2139:     */       }
/* 2140:2715 */       else if (par1Str.equals("slime"))
/* 2141:     */       {
/* 2142:2717 */         var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.slime_ball);
/* 2143:     */       }
/* 2144:2719 */       else if (par1Str.equals("heart"))
/* 2145:     */       {
/* 2146:2721 */         var21 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2147:     */       }
/* 2148:2723 */       else if (par1Str.equals("angryVillager"))
/* 2149:     */       {
/* 2150:2725 */         var21 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5D, par6, par8, par10, par12);
/* 2151:2726 */         var21.setParticleTextureIndex(81);
/* 2152:2727 */         var21.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 2153:     */       }
/* 2154:2729 */       else if (par1Str.equals("happyVillager"))
/* 2155:     */       {
/* 2156:2731 */         var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
/* 2157:2732 */         var21.setParticleTextureIndex(82);
/* 2158:2733 */         var21.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 2159:     */       }
/* 2160:2740 */       else if (par1Str.startsWith("iconcrack_"))
/* 2161:     */       {
/* 2162:2742 */         String[] var28 = par1Str.split("_", 3);
/* 2163:2743 */         int var27 = Integer.parseInt(var28[1]);
/* 2164:2745 */         if (var28.length > 2)
/* 2165:     */         {
/* 2166:2747 */           int var261 = Integer.parseInt(var28[2]);
/* 2167:2748 */           var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var27), var261);
/* 2168:     */         }
/* 2169:     */         else
/* 2170:     */         {
/* 2171:2752 */           var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var27), 0);
/* 2172:     */         }
/* 2173:     */       }
/* 2174:2759 */       else if (par1Str.startsWith("blockcrack_"))
/* 2175:     */       {
/* 2176:2761 */         String[] var28 = par1Str.split("_", 3);
/* 2177:2762 */         Block var271 = Block.getBlockById(Integer.parseInt(var28[1]));
/* 2178:2763 */         int var261 = Integer.parseInt(var28[2]);
/* 2179:2764 */         var21 = new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, var271, var261).applyRenderColor(var261);
/* 2180:     */       }
/* 2181:2766 */       else if (par1Str.startsWith("blockdust_"))
/* 2182:     */       {
/* 2183:2768 */         String[] var28 = par1Str.split("_", 3);
/* 2184:2769 */         Block var271 = Block.getBlockById(Integer.parseInt(var28[1]));
/* 2185:2770 */         int var261 = Integer.parseInt(var28[2]);
/* 2186:2771 */         var21 = new EntityBlockDustFX(this.theWorld, par2, par4, par6, par8, par10, par12, var271, var261).applyRenderColor(var261);
/* 2187:     */       }
/* 2188:2776 */       if (var21 != null) {
/* 2189:2778 */         this.mc.effectRenderer.addEffect(var21);
/* 2190:     */       }
/* 2191:2781 */       return var21;
/* 2192:     */     }
/* 2193:2787 */     return null;
/* 2194:     */   }
/* 2195:     */   
/* 2196:     */   public void onEntityCreate(Entity par1Entity)
/* 2197:     */   {
/* 2198:2797 */     RandomMobs.entityLoaded(par1Entity);
/* 2199:     */   }
/* 2200:     */   
/* 2201:     */   public void onEntityDestroy(Entity par1Entity) {}
/* 2202:     */   
/* 2203:     */   public void deleteAllDisplayLists()
/* 2204:     */   {
/* 2205:2811 */     GLAllocation.deleteDisplayLists(this.glRenderListBase);
/* 2206:     */   }
/* 2207:     */   
/* 2208:     */   public void broadcastSound(int par1, int par2, int par3, int par4, int par5)
/* 2209:     */   {
/* 2210:2816 */     Random var6 = this.theWorld.rand;
/* 2211:2818 */     switch (par1)
/* 2212:     */     {
/* 2213:     */     case 1013: 
/* 2214:     */     case 1018: 
/* 2215:2822 */       if (this.mc.renderViewEntity != null)
/* 2216:     */       {
/* 2217:2824 */         double var7 = par2 - this.mc.renderViewEntity.posX;
/* 2218:2825 */         double var9 = par3 - this.mc.renderViewEntity.posY;
/* 2219:2826 */         double var11 = par4 - this.mc.renderViewEntity.posZ;
/* 2220:2827 */         double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
/* 2221:2828 */         double var15 = this.mc.renderViewEntity.posX;
/* 2222:2829 */         double var17 = this.mc.renderViewEntity.posY;
/* 2223:2830 */         double var19 = this.mc.renderViewEntity.posZ;
/* 2224:2832 */         if (var13 > 0.0D)
/* 2225:     */         {
/* 2226:2834 */           var15 += var7 / var13 * 2.0D;
/* 2227:2835 */           var17 += var9 / var13 * 2.0D;
/* 2228:2836 */           var19 += var11 / var13 * 2.0D;
/* 2229:     */         }
/* 2230:2839 */         if (par1 == 1013) {
/* 2231:2841 */           this.theWorld.playSound(var15, var17, var19, "mob.wither.spawn", 1.0F, 1.0F, false);
/* 2232:2843 */         } else if (par1 == 1018) {
/* 2233:2845 */           this.theWorld.playSound(var15, var17, var19, "mob.enderdragon.end", 5.0F, 1.0F, false);
/* 2234:     */         }
/* 2235:     */       }
/* 2236:     */       break;
/* 2237:     */     }
/* 2238:     */   }
/* 2239:     */   
/* 2240:     */   public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
/* 2241:     */   {
/* 2242:2858 */     Random var7 = this.theWorld.rand;
/* 2243:2859 */     Block var8 = null;
/* 2244:2873 */     switch (par2)
/* 2245:     */     {
/* 2246:     */     case 1000: 
/* 2247:2876 */       this.theWorld.playSound(par3, par4, par5, "random.click", 1.0F, 1.0F, false);
/* 2248:2877 */       break;
/* 2249:     */     case 1001: 
/* 2250:2880 */       this.theWorld.playSound(par3, par4, par5, "random.click", 1.0F, 1.2F, false);
/* 2251:2881 */       break;
/* 2252:     */     case 1002: 
/* 2253:2884 */       this.theWorld.playSound(par3, par4, par5, "random.bow", 1.0F, 1.2F, false);
/* 2254:2885 */       break;
/* 2255:     */     case 1003: 
/* 2256:2888 */       if (Math.random() < 0.5D) {
/* 2257:2890 */         this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2258:     */       } else {
/* 2259:2894 */         this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2260:     */       }
/* 2261:2897 */       break;
/* 2262:     */     case 1004: 
/* 2263:2900 */       this.theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
/* 2264:2901 */       break;
/* 2265:     */     case 1005: 
/* 2266:2904 */       if ((Item.getItemById(par6) instanceof ItemRecord)) {
/* 2267:2906 */         this.theWorld.playRecord("records." + ((ItemRecord)Item.getItemById(par6)).field_150929_a, par3, par4, par5);
/* 2268:     */       } else {
/* 2269:2910 */         this.theWorld.playRecord(null, par3, par4, par5);
/* 2270:     */       }
/* 2271:2913 */       break;
/* 2272:     */     case 1007: 
/* 2273:2916 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2274:2917 */       break;
/* 2275:     */     case 1008: 
/* 2276:2920 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2277:2921 */       break;
/* 2278:     */     case 1009: 
/* 2279:2924 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2280:2925 */       break;
/* 2281:     */     case 1010: 
/* 2282:2928 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2283:2929 */       break;
/* 2284:     */     case 1011: 
/* 2285:2932 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2286:2933 */       break;
/* 2287:     */     case 1012: 
/* 2288:2936 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2289:2937 */       break;
/* 2290:     */     case 1014: 
/* 2291:2940 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2292:2941 */       break;
/* 2293:     */     case 1015: 
/* 2294:2944 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2295:2945 */       break;
/* 2296:     */     case 1016: 
/* 2297:2948 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2298:2949 */       break;
/* 2299:     */     case 1017: 
/* 2300:2952 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
/* 2301:2953 */       break;
/* 2302:     */     case 1020: 
/* 2303:2956 */       this.theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2304:2957 */       break;
/* 2305:     */     case 1021: 
/* 2306:2960 */       this.theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2307:2961 */       break;
/* 2308:     */     case 1022: 
/* 2309:2964 */       this.theWorld.playSound(par3 + 0.5F, par4 + 0.5F, par5 + 0.5F, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2310:2965 */       break;
/* 2311:     */     case 2000: 
/* 2312:2968 */       int var34 = par6 % 3 - 1;
/* 2313:2969 */       int var10 = par6 / 3 % 3 - 1;
/* 2314:2970 */       double var11 = par3 + var34 * 0.6D + 0.5D;
/* 2315:2971 */       double var13 = par4 + 0.5D;
/* 2316:2972 */       double var36 = par5 + var10 * 0.6D + 0.5D;
/* 2317:2974 */       for (int var44 = 0; var44 < 10; var44++)
/* 2318:     */       {
/* 2319:2976 */         double var43 = var7.nextDouble() * 0.2D + 0.01D;
/* 2320:2977 */         double var45 = var11 + var34 * 0.01D + (var7.nextDouble() - 0.5D) * var10 * 0.5D;
/* 2321:2978 */         double var22 = var13 + (var7.nextDouble() - 0.5D) * 0.5D;
/* 2322:2979 */         double var41 = var36 + var10 * 0.01D + (var7.nextDouble() - 0.5D) * var34 * 0.5D;
/* 2323:2980 */         double var26 = var34 * var43 + var7.nextGaussian() * 0.01D;
/* 2324:2981 */         double var28 = -0.03D + var7.nextGaussian() * 0.01D;
/* 2325:2982 */         double var30 = var10 * var43 + var7.nextGaussian() * 0.01D;
/* 2326:2983 */         spawnParticle("smoke", var45, var22, var41, var26, var28, var30);
/* 2327:     */       }
/* 2328:2986 */       return;
/* 2329:     */     case 2001: 
/* 2330:2989 */       var8 = Block.getBlockById(par6 & 0xFFF);
/* 2331:2991 */       if (var8.getMaterial() != Material.air) {
/* 2332:2993 */         this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var8.stepSound.func_150495_a()), (var8.stepSound.func_150497_c() + 1.0F) / 2.0F, var8.stepSound.func_150494_d() * 0.8F, par3 + 0.5F, par4 + 0.5F, par5 + 0.5F));
/* 2333:     */       }
/* 2334:2996 */       this.mc.effectRenderer.func_147215_a(par3, par4, par5, var8, par6 >> 12 & 0xFF);
/* 2335:2997 */       break;
/* 2336:     */     case 2002: 
/* 2337:3000 */       double var9 = par3;
/* 2338:3001 */       double var11 = par4;
/* 2339:3002 */       double var13 = par5;
/* 2340:3003 */       String var15 = "iconcrack_" + Item.getIdFromItem(Items.potionitem) + "_" + par6;
/* 2341:3005 */       for (int var16 = 0; var16 < 8; var16++) {
/* 2342:3007 */         spawnParticle(var15, var9, var11, var13, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
/* 2343:     */       }
/* 2344:3010 */       var16 = Items.potionitem.getColorFromDamage(par6);
/* 2345:3011 */       float var17 = (var16 >> 16 & 0xFF) / 255.0F;
/* 2346:3012 */       float var18 = (var16 >> 8 & 0xFF) / 255.0F;
/* 2347:3013 */       float var19 = (var16 >> 0 & 0xFF) / 255.0F;
/* 2348:3014 */       String var20 = "spell";
/* 2349:3016 */       if (Items.potionitem.isEffectInstant(par6)) {
/* 2350:3018 */         var20 = "instantSpell";
/* 2351:     */       }
/* 2352:3021 */       for (int var40 = 0; var40 < 100; var40++)
/* 2353:     */       {
/* 2354:3023 */         double var22 = var7.nextDouble() * 4.0D;
/* 2355:3024 */         double var41 = var7.nextDouble() * 3.141592653589793D * 2.0D;
/* 2356:3025 */         double var26 = Math.cos(var41) * var22;
/* 2357:3026 */         double var28 = 0.01D + var7.nextDouble() * 0.5D;
/* 2358:3027 */         double var30 = Math.sin(var41) * var22;
/* 2359:3028 */         EntityFX var46 = doSpawnParticle(var20, var9 + var26 * 0.1D, var11 + 0.3D, var13 + var30 * 0.1D, var26, var28, var30);
/* 2360:3030 */         if (var46 != null)
/* 2361:     */         {
/* 2362:3032 */           float var33 = 0.75F + var7.nextFloat() * 0.25F;
/* 2363:3033 */           var46.setRBGColorF(var17 * var33, var18 * var33, var19 * var33);
/* 2364:3034 */           var46.multiplyVelocity((float)var22);
/* 2365:     */         }
/* 2366:     */       }
/* 2367:3038 */       this.theWorld.playSound(par3 + 0.5D, par4 + 0.5D, par5 + 0.5D, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/* 2368:3039 */       break;
/* 2369:     */     case 2003: 
/* 2370:3042 */       double var9 = par3 + 0.5D;
/* 2371:3043 */       double var11 = par4;
/* 2372:3044 */       double var13 = par5 + 0.5D;
/* 2373:3045 */       String var15 = "iconcrack_" + Item.getIdFromItem(Items.ender_eye);
/* 2374:3047 */       for (int var16 = 0; var16 < 8; var16++) {
/* 2375:3049 */         spawnParticle(var15, var9, var11, var13, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
/* 2376:     */       }
/* 2377:3052 */       for (double var21 = 0.0D; var21 < 6.283185307179586D; var21 += 0.1570796326794897D)
/* 2378:     */       {
/* 2379:3054 */         spawnParticle("portal", var9 + Math.cos(var21) * 5.0D, var11 - 0.4D, var13 + Math.sin(var21) * 5.0D, Math.cos(var21) * -5.0D, 0.0D, Math.sin(var21) * -5.0D);
/* 2380:3055 */         spawnParticle("portal", var9 + Math.cos(var21) * 5.0D, var11 - 0.4D, var13 + Math.sin(var21) * 5.0D, Math.cos(var21) * -7.0D, 0.0D, Math.sin(var21) * -7.0D);
/* 2381:     */       }
/* 2382:3058 */       return;
/* 2383:     */     case 2004: 
/* 2384:3061 */       for (int var40 = 0; var40 < 20; var40++)
/* 2385:     */       {
/* 2386:3063 */         double var22 = par3 + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2387:3064 */         double var41 = par4 + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2388:3065 */         double var26 = par5 + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2389:3066 */         this.theWorld.spawnParticle("smoke", var22, var41, var26, 0.0D, 0.0D, 0.0D);
/* 2390:3067 */         this.theWorld.spawnParticle("flame", var22, var41, var26, 0.0D, 0.0D, 0.0D);
/* 2391:     */       }
/* 2392:3070 */       return;
/* 2393:     */     case 2005: 
/* 2394:3073 */       ItemDye.func_150918_a(this.theWorld, par3, par4, par5, par6);
/* 2395:3074 */       break;
/* 2396:     */     case 2006: 
/* 2397:3077 */       var8 = this.theWorld.getBlock(par3, par4, par5);
/* 2398:3079 */       if (var8.getMaterial() != Material.air)
/* 2399:     */       {
/* 2400:3081 */         double var21 = Math.min(0.2F + par6 / 15.0F, 10.0F);
/* 2401:3083 */         if (var21 > 2.5D) {
/* 2402:3085 */           var21 = 2.5D;
/* 2403:     */         }
/* 2404:3088 */         int var23 = (int)(150.0D * var21);
/* 2405:3090 */         for (int var24 = 0; var24 < var23; var24++)
/* 2406:     */         {
/* 2407:3092 */           float var25 = MathHelper.randomFloatClamp(var7, 0.0F, 6.283186F);
/* 2408:3093 */           double var26 = MathHelper.randomFloatClamp(var7, 0.75F, 1.0F);
/* 2409:3094 */           double var28 = 0.2000000029802322D + var21 / 100.0D;
/* 2410:3095 */           double var30 = MathHelper.cos(var25) * 0.2F * var26 * var26 * (var21 + 0.2D);
/* 2411:3096 */           double var32 = MathHelper.sin(var25) * 0.2F * var26 * var26 * (var21 + 0.2D);
/* 2412:3097 */           this.theWorld.spawnParticle("blockdust_" + Block.getIdFromBlock(var8) + "_" + this.theWorld.getBlockMetadata(par3, par4, par5), par3 + 0.5F, par4 + 1.0F, par5 + 0.5F, var30, var28, var32);
/* 2413:     */         }
/* 2414:     */       }
/* 2415:     */       break;
/* 2416:     */     }
/* 2417:     */   }
/* 2418:     */   
/* 2419:     */   public void destroyBlockPartially(int p_147587_1_, int p_147587_2_, int p_147587_3_, int p_147587_4_, int p_147587_5_)
/* 2420:     */   {
/* 2421:3109 */     if ((p_147587_5_ >= 0) && (p_147587_5_ < 10))
/* 2422:     */     {
/* 2423:3111 */       DestroyBlockProgress var6 = (DestroyBlockProgress)this.damagedBlocks.get(Integer.valueOf(p_147587_1_));
/* 2424:3113 */       if ((var6 == null) || (var6.getPartialBlockX() != p_147587_2_) || (var6.getPartialBlockY() != p_147587_3_) || (var6.getPartialBlockZ() != p_147587_4_))
/* 2425:     */       {
/* 2426:3115 */         var6 = new DestroyBlockProgress(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_);
/* 2427:3116 */         this.damagedBlocks.put(Integer.valueOf(p_147587_1_), var6);
/* 2428:     */       }
/* 2429:3119 */       var6.setPartialBlockDamage(p_147587_5_);
/* 2430:3120 */       var6.setCloudUpdateTick(this.cloudTickCounter);
/* 2431:     */     }
/* 2432:     */     else
/* 2433:     */     {
/* 2434:3124 */       this.damagedBlocks.remove(Integer.valueOf(p_147587_1_));
/* 2435:     */     }
/* 2436:     */   }
/* 2437:     */   
/* 2438:     */   public void registerDestroyBlockIcons(IIconRegister par1IconRegister)
/* 2439:     */   {
/* 2440:3130 */     this.destroyBlockIcons = new IIcon[10];
/* 2441:3132 */     for (int var2 = 0; var2 < this.destroyBlockIcons.length; var2++) {
/* 2442:3134 */       this.destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_stage_" + var2);
/* 2443:     */     }
/* 2444:     */   }
/* 2445:     */   
/* 2446:     */   public void setAllRenderersVisible()
/* 2447:     */   {
/* 2448:3140 */     if (this.worldRenderers != null) {
/* 2449:3142 */       for (int i = 0; i < this.worldRenderers.length; i++) {
/* 2450:3144 */         this.worldRenderers[i].isVisible = true;
/* 2451:     */       }
/* 2452:     */     }
/* 2453:     */   }
/* 2454:     */   
/* 2455:     */   public boolean isMoving(EntityLivingBase entityliving)
/* 2456:     */   {
/* 2457:3151 */     boolean moving = isMovingNow(entityliving);
/* 2458:3153 */     if (moving)
/* 2459:     */     {
/* 2460:3155 */       this.lastMovedTime = System.currentTimeMillis();
/* 2461:3156 */       return true;
/* 2462:     */     }
/* 2463:3160 */     return System.currentTimeMillis() - this.lastMovedTime < 2000L;
/* 2464:     */   }
/* 2465:     */   
/* 2466:     */   private boolean isMovingNow(EntityLivingBase entityliving)
/* 2467:     */   {
/* 2468:3166 */     double maxDiff = 0.001D;
/* 2469:3167 */     return entityliving.isSneaking();
/* 2470:     */   }
/* 2471:     */   
/* 2472:     */   public boolean isActing()
/* 2473:     */   {
/* 2474:3172 */     boolean acting = isActingNow();
/* 2475:3174 */     if (acting)
/* 2476:     */     {
/* 2477:3176 */       this.lastActionTime = System.currentTimeMillis();
/* 2478:3177 */       return true;
/* 2479:     */     }
/* 2480:3181 */     return System.currentTimeMillis() - this.lastActionTime < 500L;
/* 2481:     */   }
/* 2482:     */   
/* 2483:     */   public boolean isActingNow()
/* 2484:     */   {
/* 2485:3187 */     return Mouse.isButtonDown(0) ? true : Mouse.isButtonDown(1);
/* 2486:     */   }
/* 2487:     */   
/* 2488:     */   public int renderAllSortedRenderers(int renderPass, double partialTicks)
/* 2489:     */   {
/* 2490:3192 */     return renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
/* 2491:     */   }
/* 2492:     */   
/* 2493:     */   public void updateCapes()
/* 2494:     */   {
/* 2495:3197 */     if (this.theWorld != null)
/* 2496:     */     {
/* 2497:3199 */       boolean showCapes = Config.isShowCapes();
/* 2498:3200 */       List playerList = this.theWorld.playerEntities;
/* 2499:3202 */       for (int i = 0; i < playerList.size(); i++)
/* 2500:     */       {
/* 2501:3204 */         Entity entity = (Entity)playerList.get(i);
/* 2502:3206 */         if ((entity instanceof AbstractClientPlayer))
/* 2503:     */         {
/* 2504:3208 */           AbstractClientPlayer player = (AbstractClientPlayer)entity;
/* 2505:3209 */           player.getTextureCape().enabled = showCapes;
/* 2506:     */         }
/* 2507:     */       }
/* 2508:     */     }
/* 2509:     */   }
/* 2510:     */   
/* 2511:     */   public AxisAlignedBB getTileEntityBoundingBox(TileEntity te)
/* 2512:     */   {
/* 2513:3217 */     Block blockType = te.getBlockType();
/* 2514:3219 */     if (blockType == Blocks.enchanting_table) {
/* 2515:3221 */       return AxisAlignedBB.getAABBPool().getAABB(te.field_145851_c, te.field_145848_d, te.field_145849_e, te.field_145851_c + 1, te.field_145848_d + 1, te.field_145849_e + 1);
/* 2516:     */     }
/* 2517:3223 */     if ((blockType != Blocks.chest) && (blockType != Blocks.trapped_chest))
/* 2518:     */     {
/* 2519:3225 */       if ((blockType != null) && (blockType != Blocks.beacon))
/* 2520:     */       {
/* 2521:3227 */         AxisAlignedBB blockAabb = te.getBlockType().getCollisionBoundingBoxFromPool(te.getWorldObj(), te.field_145851_c, te.field_145848_d, te.field_145849_e);
/* 2522:3229 */         if (blockAabb != null) {
/* 2523:3231 */           return blockAabb;
/* 2524:     */         }
/* 2525:     */       }
/* 2526:3235 */       return AABB_INFINITE;
/* 2527:     */     }
/* 2528:3239 */     return AxisAlignedBB.getAABBPool().getAABB(te.field_145851_c - 1, te.field_145848_d, te.field_145849_e - 1, te.field_145851_c + 2, te.field_145848_d + 2, te.field_145849_e + 2);
/* 2529:     */   }
/* 2530:     */   
/* 2531:     */   public void addToSortedWorldRenderers(WorldRenderer wr)
/* 2532:     */   {
/* 2533:3245 */     if (!wr.inSortedWorldRenderers)
/* 2534:     */     {
/* 2535:3247 */       int pos = this.countSortedWorldRenderers;
/* 2536:3248 */       wr.sortDistanceToEntitySquared = wr.distanceToEntitySquared(this.renderViewEntity);
/* 2537:3249 */       float distSq = wr.sortDistanceToEntitySquared;
/* 2538:3252 */       if (this.countSortedWorldRenderers > 0)
/* 2539:     */       {
/* 2540:3254 */         int countGreater = 0;
/* 2541:3255 */         int high = this.countSortedWorldRenderers - 1;
/* 2542:3256 */         int mid = (countGreater + high) / 2;
/* 2543:3258 */         while (countGreater <= high)
/* 2544:     */         {
/* 2545:3260 */           mid = (countGreater + high) / 2;
/* 2546:3261 */           WorldRenderer wrMid = this.sortedWorldRenderers[mid];
/* 2547:3263 */           if (distSq < wrMid.sortDistanceToEntitySquared) {
/* 2548:3265 */             high = mid - 1;
/* 2549:     */           } else {
/* 2550:3269 */             countGreater = mid + 1;
/* 2551:     */           }
/* 2552:     */         }
/* 2553:3273 */         if (countGreater > mid) {
/* 2554:3275 */           pos = mid + 1;
/* 2555:     */         } else {
/* 2556:3279 */           pos = mid;
/* 2557:     */         }
/* 2558:     */       }
/* 2559:3283 */       int countGreater = this.countSortedWorldRenderers - pos;
/* 2560:3285 */       if (countGreater > 0) {
/* 2561:3287 */         System.arraycopy(this.sortedWorldRenderers, pos, this.sortedWorldRenderers, pos + 1, countGreater);
/* 2562:     */       }
/* 2563:3290 */       this.sortedWorldRenderers[pos] = wr;
/* 2564:3291 */       wr.inSortedWorldRenderers = true;
/* 2565:3292 */       this.countSortedWorldRenderers += 1;
/* 2566:     */     }
/* 2567:     */   }
/* 2568:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.RenderGlobal
 * JD-Core Version:    0.7.0.1
 */