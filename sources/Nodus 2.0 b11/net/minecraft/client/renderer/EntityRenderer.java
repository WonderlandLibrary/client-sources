/*    1:     */ package net.minecraft.client.renderer;
/*    2:     */ 
/*    3:     */ import com.google.gson.JsonSyntaxException;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.lang.reflect.Field;
/*    6:     */ import java.nio.FloatBuffer;
/*    7:     */ import java.util.Calendar;
/*    8:     */ import java.util.Date;
/*    9:     */ import java.util.List;
/*   10:     */ import java.util.Random;
/*   11:     */ import java.util.concurrent.Callable;
/*   12:     */ import me.connorm.Nodus.event.render.EventRenderWorld;
/*   13:     */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*   14:     */ import me.connorm.lib.EventManager;
/*   15:     */ import net.minecraft.block.Block;
/*   16:     */ import net.minecraft.block.BlockLeaves;
/*   17:     */ import net.minecraft.block.material.Material;
/*   18:     */ import net.minecraft.client.Minecraft;
/*   19:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   20:     */ import net.minecraft.client.entity.EntityPlayerSP;
/*   21:     */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*   22:     */ import net.minecraft.client.gui.GuiIngame;
/*   23:     */ import net.minecraft.client.gui.GuiNewChat;
/*   24:     */ import net.minecraft.client.gui.GuiScreen;
/*   25:     */ import net.minecraft.client.gui.MapItemRenderer;
/*   26:     */ import net.minecraft.client.gui.ScaledResolution;
/*   27:     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   28:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   29:     */ import net.minecraft.client.particle.EffectRenderer;
/*   30:     */ import net.minecraft.client.particle.EntityRainFX;
/*   31:     */ import net.minecraft.client.particle.EntitySmokeFX;
/*   32:     */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*   33:     */ import net.minecraft.client.renderer.culling.Frustrum;
/*   34:     */ import net.minecraft.client.renderer.entity.RenderManager;
/*   35:     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*   36:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   37:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   38:     */ import net.minecraft.client.resources.IResourceManager;
/*   39:     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*   40:     */ import net.minecraft.client.settings.GameSettings;
/*   41:     */ import net.minecraft.client.settings.KeyBinding;
/*   42:     */ import net.minecraft.client.shader.Framebuffer;
/*   43:     */ import net.minecraft.client.shader.ShaderGroup;
/*   44:     */ import net.minecraft.client.shader.ShaderLinkHelper;
/*   45:     */ import net.minecraft.crash.CrashReport;
/*   46:     */ import net.minecraft.crash.CrashReportCategory;
/*   47:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*   48:     */ import net.minecraft.entity.Entity;
/*   49:     */ import net.minecraft.entity.EntityLivingBase;
/*   50:     */ import net.minecraft.entity.boss.BossStatus;
/*   51:     */ import net.minecraft.entity.item.EntityItemFrame;
/*   52:     */ import net.minecraft.entity.player.EntityPlayer;
/*   53:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   54:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   55:     */ import net.minecraft.init.Blocks;
/*   56:     */ import net.minecraft.potion.Potion;
/*   57:     */ import net.minecraft.potion.PotionEffect;
/*   58:     */ import net.minecraft.profiler.Profiler;
/*   59:     */ import net.minecraft.server.integrated.IntegratedServer;
/*   60:     */ import net.minecraft.src.Config;
/*   61:     */ import net.minecraft.src.CustomColorizer;
/*   62:     */ import net.minecraft.src.ItemRendererOF;
/*   63:     */ import net.minecraft.src.RandomMobs;
/*   64:     */ import net.minecraft.src.Reflector;
/*   65:     */ import net.minecraft.src.ReflectorClass;
/*   66:     */ import net.minecraft.src.ReflectorMethod;
/*   67:     */ import net.minecraft.src.TextureUtils;
/*   68:     */ import net.minecraft.src.WrUpdates;
/*   69:     */ import net.minecraft.util.AxisAlignedBB;
/*   70:     */ import net.minecraft.util.ChatComponentText;
/*   71:     */ import net.minecraft.util.MathHelper;
/*   72:     */ import net.minecraft.util.MouseFilter;
/*   73:     */ import net.minecraft.util.MouseHelper;
/*   74:     */ import net.minecraft.util.MovingObjectPosition;
/*   75:     */ import net.minecraft.util.ReportedException;
/*   76:     */ import net.minecraft.util.ResourceLocation;
/*   77:     */ import net.minecraft.util.Vec3;
/*   78:     */ import net.minecraft.util.Vec3Pool;
/*   79:     */ import net.minecraft.world.World;
/*   80:     */ import net.minecraft.world.WorldProvider;
/*   81:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   82:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   83:     */ import org.apache.logging.log4j.LogManager;
/*   84:     */ import org.apache.logging.log4j.Logger;
/*   85:     */ import org.lwjgl.input.Keyboard;
/*   86:     */ import org.lwjgl.input.Mouse;
/*   87:     */ import org.lwjgl.opengl.ContextCapabilities;
/*   88:     */ import org.lwjgl.opengl.Display;
/*   89:     */ import org.lwjgl.opengl.GL11;
/*   90:     */ import org.lwjgl.opengl.GLContext;
/*   91:     */ import org.lwjgl.util.glu.Project;
/*   92:     */ 
/*   93:     */ public class EntityRenderer
/*   94:     */   implements IResourceManagerReloadListener
/*   95:     */ {
/*   96:  77 */   private static final Logger logger = ;
/*   97:  78 */   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
/*   98:  79 */   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
/*   99:     */   public static boolean anaglyphEnable;
/*  100:     */   public static int anaglyphField;
/*  101:     */   private Minecraft mc;
/*  102:     */   private float farPlaneDistance;
/*  103:     */   public ItemRenderer itemRenderer;
/*  104:     */   private final MapItemRenderer theMapItemRenderer;
/*  105:     */   private int rendererUpdateCount;
/*  106:     */   private Entity pointedEntity;
/*  107:  96 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  108:  97 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  109: 100 */   private MouseFilter mouseFilterDummy1 = new MouseFilter();
/*  110: 103 */   private MouseFilter mouseFilterDummy2 = new MouseFilter();
/*  111: 106 */   private MouseFilter mouseFilterDummy3 = new MouseFilter();
/*  112: 109 */   private MouseFilter mouseFilterDummy4 = new MouseFilter();
/*  113: 110 */   private float thirdPersonDistance = 4.0F;
/*  114: 113 */   private float thirdPersonDistanceTemp = 4.0F;
/*  115:     */   private float debugCamYaw;
/*  116:     */   private float prevDebugCamYaw;
/*  117:     */   private float debugCamPitch;
/*  118:     */   private float prevDebugCamPitch;
/*  119:     */   private float smoothCamYaw;
/*  120:     */   private float smoothCamPitch;
/*  121:     */   private float smoothCamFilterX;
/*  122:     */   private float smoothCamFilterY;
/*  123:     */   private float smoothCamPartialTicks;
/*  124:     */   private float debugCamFOV;
/*  125:     */   private float prevDebugCamFOV;
/*  126:     */   private float camRoll;
/*  127:     */   private float prevCamRoll;
/*  128:     */   private final DynamicTexture lightmapTexture;
/*  129:     */   private final int[] lightmapColors;
/*  130:     */   private final ResourceLocation locationLightMap;
/*  131:     */   private float fovModifierHand;
/*  132:     */   private float fovModifierHandPrev;
/*  133:     */   private float fovMultiplierTemp;
/*  134:     */   private float bossColorModifier;
/*  135:     */   private float bossColorModifierPrev;
/*  136:     */   private boolean cloudFog;
/*  137:     */   private final IResourceManager resourceManager;
/*  138:     */   public ShaderGroup theShaderGroup;
/*  139: 164 */   private static final ResourceLocation[] shaderResourceLocations = { new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json") };
/*  140: 165 */   public static final int shaderCount = shaderResourceLocations.length;
/*  141:     */   private int shaderIndex;
/*  142:     */   private double cameraZoom;
/*  143:     */   private double cameraYaw;
/*  144:     */   private double cameraPitch;
/*  145:     */   private long prevFrameTime;
/*  146:     */   private long renderEndNanoTime;
/*  147:     */   private boolean lightmapUpdateNeeded;
/*  148:     */   float torchFlickerX;
/*  149:     */   float torchFlickerDX;
/*  150:     */   float torchFlickerY;
/*  151:     */   float torchFlickerDY;
/*  152:     */   private Random random;
/*  153:     */   private int rainSoundCounter;
/*  154:     */   float[] rainXCoords;
/*  155:     */   float[] rainYCoords;
/*  156:     */   FloatBuffer fogColorBuffer;
/*  157:     */   float fogColorRed;
/*  158:     */   float fogColorGreen;
/*  159:     */   float fogColorBlue;
/*  160:     */   private float fogColor2;
/*  161:     */   private float fogColor1;
/*  162:     */   public int debugViewDirection;
/*  163: 226 */   private boolean initialized = false;
/*  164: 227 */   private World updatedWorld = null;
/*  165: 228 */   private boolean showDebugInfo = false;
/*  166: 229 */   public boolean fogStandard = false;
/*  167: 230 */   private long lastServerTime = 0L;
/*  168: 231 */   private int lastServerTicks = 0;
/*  169: 232 */   private int serverWaitTime = 0;
/*  170: 233 */   private int serverWaitTimeCurrent = 0;
/*  171: 234 */   private float avgServerTimeDiff = 0.0F;
/*  172: 235 */   private float avgServerTickDiff = 0.0F;
/*  173: 236 */   public long[] frameTimes = new long[512];
/*  174: 237 */   public long[] tickTimes = new long[512];
/*  175: 238 */   public long[] chunkTimes = new long[512];
/*  176: 239 */   public long[] serverTimes = new long[512];
/*  177: 240 */   public int numRecordedFrameTimes = 0;
/*  178: 241 */   public long prevFrameTimeNano = -1L;
/*  179: 242 */   private boolean lastShowDebugInfo = false;
/*  180: 243 */   private boolean showExtendedDebugInfo = false;
/*  181:     */   private static final String __OBFID = "CL_00000947";
/*  182:     */   
/*  183:     */   public EntityRenderer(Minecraft p_i45076_1_, IResourceManager p_i45076_2_)
/*  184:     */   {
/*  185: 248 */     this.shaderIndex = shaderCount;
/*  186: 249 */     this.cameraZoom = 1.0D;
/*  187: 250 */     this.prevFrameTime = Minecraft.getSystemTime();
/*  188: 251 */     this.random = new Random();
/*  189: 252 */     this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*  190: 253 */     this.mc = p_i45076_1_;
/*  191: 254 */     this.resourceManager = p_i45076_2_;
/*  192: 255 */     this.theMapItemRenderer = new MapItemRenderer(p_i45076_1_.getTextureManager());
/*  193: 256 */     this.itemRenderer = new ItemRenderer(p_i45076_1_);
/*  194: 257 */     this.lightmapTexture = new DynamicTexture(16, 16);
/*  195: 258 */     this.locationLightMap = p_i45076_1_.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
/*  196: 259 */     this.lightmapColors = this.lightmapTexture.getTextureData();
/*  197: 260 */     this.theShaderGroup = null;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public boolean isShaderActive()
/*  201:     */   {
/*  202: 265 */     return (OpenGlHelper.shadersSupported) && (this.theShaderGroup != null);
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void deactivateShader()
/*  206:     */   {
/*  207: 270 */     if (this.theShaderGroup != null) {
/*  208: 272 */       this.theShaderGroup.deleteShaderGroup();
/*  209:     */     }
/*  210: 275 */     this.theShaderGroup = null;
/*  211: 276 */     this.shaderIndex = shaderCount;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public void activateNextShader()
/*  215:     */   {
/*  216: 281 */     if (OpenGlHelper.shadersSupported)
/*  217:     */     {
/*  218: 283 */       if (this.theShaderGroup != null) {
/*  219: 285 */         this.theShaderGroup.deleteShaderGroup();
/*  220:     */       }
/*  221: 288 */       this.shaderIndex = ((this.shaderIndex + 1) % (shaderResourceLocations.length + 1));
/*  222: 290 */       if (this.shaderIndex != shaderCount)
/*  223:     */       {
/*  224:     */         try
/*  225:     */         {
/*  226: 294 */           logger.info("Selecting effect " + shaderResourceLocations[this.shaderIndex]);
/*  227: 295 */           this.theShaderGroup = new ShaderGroup(this.resourceManager, this.mc.getFramebuffer(), shaderResourceLocations[this.shaderIndex]);
/*  228: 296 */           this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  229:     */         }
/*  230:     */         catch (IOException var2)
/*  231:     */         {
/*  232: 300 */           logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], var2);
/*  233: 301 */           this.shaderIndex = shaderCount;
/*  234:     */         }
/*  235:     */         catch (JsonSyntaxException var3)
/*  236:     */         {
/*  237: 305 */           logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], var3);
/*  238: 306 */           this.shaderIndex = shaderCount;
/*  239:     */         }
/*  240:     */       }
/*  241:     */       else
/*  242:     */       {
/*  243: 311 */         this.theShaderGroup = null;
/*  244: 312 */         logger.info("No effect selected");
/*  245:     */       }
/*  246:     */     }
/*  247:     */   }
/*  248:     */   
/*  249:     */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/*  250:     */   {
/*  251: 319 */     if (this.theShaderGroup != null) {
/*  252: 321 */       this.theShaderGroup.deleteShaderGroup();
/*  253:     */     }
/*  254: 324 */     if (this.shaderIndex != shaderCount) {
/*  255:     */       try
/*  256:     */       {
/*  257: 328 */         this.theShaderGroup = new ShaderGroup(par1ResourceManager, this.mc.getFramebuffer(), shaderResourceLocations[this.shaderIndex]);
/*  258: 329 */         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  259:     */       }
/*  260:     */       catch (IOException var3)
/*  261:     */       {
/*  262: 333 */         logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], var3);
/*  263: 334 */         this.shaderIndex = shaderCount;
/*  264:     */       }
/*  265:     */     }
/*  266:     */   }
/*  267:     */   
/*  268:     */   public void updateRenderer()
/*  269:     */   {
/*  270: 344 */     if ((OpenGlHelper.shadersSupported) && (ShaderLinkHelper.getStaticShaderLinkHelper() == null)) {
/*  271: 346 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*  272:     */     }
/*  273: 349 */     updateFovModifierHand();
/*  274: 350 */     updateTorchFlicker();
/*  275: 351 */     this.fogColor2 = this.fogColor1;
/*  276: 352 */     this.thirdPersonDistanceTemp = this.thirdPersonDistance;
/*  277: 353 */     this.prevDebugCamYaw = this.debugCamYaw;
/*  278: 354 */     this.prevDebugCamPitch = this.debugCamPitch;
/*  279: 355 */     this.prevDebugCamFOV = this.debugCamFOV;
/*  280: 356 */     this.prevCamRoll = this.camRoll;
/*  281: 360 */     if (this.mc.gameSettings.smoothCamera)
/*  282:     */     {
/*  283: 362 */       float var1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  284: 363 */       float var2 = var1 * var1 * var1 * 8.0F;
/*  285: 364 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * var2);
/*  286: 365 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * var2);
/*  287: 366 */       this.smoothCamPartialTicks = 0.0F;
/*  288: 367 */       this.smoothCamYaw = 0.0F;
/*  289: 368 */       this.smoothCamPitch = 0.0F;
/*  290:     */     }
/*  291: 371 */     if (this.mc.renderViewEntity == null) {
/*  292: 373 */       this.mc.renderViewEntity = this.mc.thePlayer;
/*  293:     */     }
/*  294: 376 */     float var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
/*  295: 377 */     float var2 = this.mc.gameSettings.renderDistanceChunks / 16;
/*  296: 378 */     float var3 = var1 * (1.0F - var2) + var2;
/*  297: 379 */     this.fogColor1 += (var3 - this.fogColor1) * 0.1F;
/*  298: 380 */     this.rendererUpdateCount += 1;
/*  299: 381 */     this.itemRenderer.updateEquippedItem();
/*  300: 382 */     addRainParticles();
/*  301: 383 */     this.bossColorModifierPrev = this.bossColorModifier;
/*  302: 385 */     if (BossStatus.hasColorModifier)
/*  303:     */     {
/*  304: 387 */       this.bossColorModifier += 0.05F;
/*  305: 389 */       if (this.bossColorModifier > 1.0F) {
/*  306: 391 */         this.bossColorModifier = 1.0F;
/*  307:     */       }
/*  308: 394 */       BossStatus.hasColorModifier = false;
/*  309:     */     }
/*  310: 396 */     else if (this.bossColorModifier > 0.0F)
/*  311:     */     {
/*  312: 398 */       this.bossColorModifier -= 0.0125F;
/*  313:     */     }
/*  314:     */   }
/*  315:     */   
/*  316:     */   public ShaderGroup getShaderGroup()
/*  317:     */   {
/*  318: 404 */     return this.theShaderGroup;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public void updateShaderGroupSize(int p_147704_1_, int p_147704_2_)
/*  322:     */   {
/*  323: 409 */     if ((OpenGlHelper.shadersSupported) && (this.theShaderGroup != null)) {
/*  324: 411 */       this.theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
/*  325:     */     }
/*  326:     */   }
/*  327:     */   
/*  328:     */   public void getMouseOver(float par1)
/*  329:     */   {
/*  330: 420 */     if ((this.mc.renderViewEntity != null) && (this.mc.theWorld != null))
/*  331:     */     {
/*  332: 422 */       this.mc.pointedEntity = null;
/*  333: 423 */       double var2 = this.mc.playerController.getBlockReachDistance();
/*  334: 424 */       this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, par1);
/*  335: 425 */       double var4 = var2;
/*  336: 426 */       Vec3 var6 = this.mc.renderViewEntity.getPosition(par1);
/*  337: 428 */       if (this.mc.playerController.extendedReach())
/*  338:     */       {
/*  339: 430 */         var2 = 6.0D;
/*  340: 431 */         var4 = 6.0D;
/*  341:     */       }
/*  342:     */       else
/*  343:     */       {
/*  344: 435 */         if (var2 > 3.0D) {
/*  345: 437 */           var4 = 3.0D;
/*  346:     */         }
/*  347: 440 */         var2 = var4;
/*  348:     */       }
/*  349: 443 */       if (this.mc.objectMouseOver != null) {
/*  350: 445 */         var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
/*  351:     */       }
/*  352: 448 */       Vec3 var7 = this.mc.renderViewEntity.getLook(par1);
/*  353: 449 */       Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
/*  354: 450 */       this.pointedEntity = null;
/*  355: 451 */       Vec3 var9 = null;
/*  356: 452 */       float var10 = 1.0F;
/*  357: 453 */       List var11 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand(var10, var10, var10));
/*  358: 454 */       double var12 = var4;
/*  359: 456 */       for (int var14 = 0; var14 < var11.size(); var14++)
/*  360:     */       {
/*  361: 458 */         Entity var15 = (Entity)var11.get(var14);
/*  362: 460 */         if (var15.canBeCollidedWith())
/*  363:     */         {
/*  364: 462 */           float var16 = var15.getCollisionBorderSize();
/*  365: 463 */           AxisAlignedBB var17 = var15.boundingBox.expand(var16, var16, var16);
/*  366: 464 */           MovingObjectPosition var18 = var17.calculateIntercept(var6, var8);
/*  367: 466 */           if (var17.isVecInside(var6))
/*  368:     */           {
/*  369: 468 */             if ((0.0D < var12) || (var12 == 0.0D))
/*  370:     */             {
/*  371: 470 */               this.pointedEntity = var15;
/*  372: 471 */               var9 = var18 == null ? var6 : var18.hitVec;
/*  373: 472 */               var12 = 0.0D;
/*  374:     */             }
/*  375:     */           }
/*  376: 475 */           else if (var18 != null)
/*  377:     */           {
/*  378: 477 */             double var19 = var6.distanceTo(var18.hitVec);
/*  379: 479 */             if ((var19 < var12) || (var12 == 0.0D))
/*  380:     */             {
/*  381: 481 */               boolean canRiderInteract = false;
/*  382: 483 */               if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/*  383: 485 */                 canRiderInteract = Reflector.callBoolean(var15, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*  384:     */               }
/*  385: 488 */               if ((var15 == this.mc.renderViewEntity.ridingEntity) && (!canRiderInteract))
/*  386:     */               {
/*  387: 490 */                 if (var12 == 0.0D)
/*  388:     */                 {
/*  389: 492 */                   this.pointedEntity = var15;
/*  390: 493 */                   var9 = var18.hitVec;
/*  391:     */                 }
/*  392:     */               }
/*  393:     */               else
/*  394:     */               {
/*  395: 498 */                 this.pointedEntity = var15;
/*  396: 499 */                 var9 = var18.hitVec;
/*  397: 500 */                 var12 = var19;
/*  398:     */               }
/*  399:     */             }
/*  400:     */           }
/*  401:     */         }
/*  402:     */       }
/*  403: 507 */       if ((this.pointedEntity != null) && ((var12 < var4) || (this.mc.objectMouseOver == null)))
/*  404:     */       {
/*  405: 509 */         this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var9);
/*  406: 511 */         if (((this.pointedEntity instanceof EntityLivingBase)) || ((this.pointedEntity instanceof EntityItemFrame))) {
/*  407: 513 */           this.mc.pointedEntity = this.pointedEntity;
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411:     */   }
/*  412:     */   
/*  413:     */   private void updateFovModifierHand()
/*  414:     */   {
/*  415: 524 */     if ((this.mc.renderViewEntity instanceof EntityPlayerSP))
/*  416:     */     {
/*  417: 526 */       EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
/*  418: 527 */       this.fovMultiplierTemp = var1.getFOVMultiplier();
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422: 531 */       this.fovMultiplierTemp = this.mc.thePlayer.getFOVMultiplier();
/*  423:     */     }
/*  424: 534 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  425: 535 */     this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5F;
/*  426: 537 */     if (this.fovModifierHand > 1.5F) {
/*  427: 539 */       this.fovModifierHand = 1.5F;
/*  428:     */     }
/*  429: 542 */     if (this.fovModifierHand < 0.1F) {
/*  430: 544 */       this.fovModifierHand = 0.1F;
/*  431:     */     }
/*  432:     */   }
/*  433:     */   
/*  434:     */   private float getFOVModifier(float par1, boolean par2)
/*  435:     */   {
/*  436: 553 */     if (this.debugViewDirection > 0) {
/*  437: 555 */       return 90.0F;
/*  438:     */     }
/*  439: 559 */     EntityLivingBase var3 = this.mc.renderViewEntity;
/*  440: 560 */     float var4 = 70.0F;
/*  441: 562 */     if (par2)
/*  442:     */     {
/*  443: 564 */       var4 += this.mc.gameSettings.fovSetting * 40.0F;
/*  444: 565 */       var4 *= (this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1);
/*  445:     */     }
/*  446: 568 */     boolean zoomActive = false;
/*  447: 570 */     if (this.mc.currentScreen == null) {
/*  448: 572 */       if (this.mc.gameSettings.ofKeyBindZoom.getKeyCode() < 0) {
/*  449: 574 */         zoomActive = Mouse.isButtonDown(this.mc.gameSettings.ofKeyBindZoom.getKeyCode() + 100);
/*  450:     */       } else {
/*  451: 578 */         zoomActive = Keyboard.isKeyDown(this.mc.gameSettings.ofKeyBindZoom.getKeyCode());
/*  452:     */       }
/*  453:     */     }
/*  454: 582 */     if (zoomActive)
/*  455:     */     {
/*  456: 584 */       if (!Config.zoomMode)
/*  457:     */       {
/*  458: 586 */         Config.zoomMode = true;
/*  459: 587 */         this.mc.gameSettings.smoothCamera = true;
/*  460:     */       }
/*  461: 590 */       if (Config.zoomMode) {
/*  462: 592 */         var4 /= 4.0F;
/*  463:     */       }
/*  464:     */     }
/*  465: 595 */     else if (Config.zoomMode)
/*  466:     */     {
/*  467: 597 */       Config.zoomMode = false;
/*  468: 598 */       this.mc.gameSettings.smoothCamera = false;
/*  469: 599 */       this.mouseFilterXAxis = new MouseFilter();
/*  470: 600 */       this.mouseFilterYAxis = new MouseFilter();
/*  471:     */     }
/*  472: 603 */     if (var3.getHealth() <= 0.0F)
/*  473:     */     {
/*  474: 605 */       float var6 = var3.deathTime + par1;
/*  475: 606 */       var4 /= ((1.0F - 500.0F / (var6 + 500.0F)) * 2.0F + 1.0F);
/*  476:     */     }
/*  477: 609 */     Block var61 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par1);
/*  478: 611 */     if (var61.getMaterial() == Material.water) {
/*  479: 613 */       var4 = var4 * 60.0F / 70.0F;
/*  480:     */     }
/*  481: 616 */     return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
/*  482:     */   }
/*  483:     */   
/*  484:     */   private void hurtCameraEffect(float par1)
/*  485:     */   {
/*  486: 622 */     EntityLivingBase var2 = this.mc.renderViewEntity;
/*  487: 623 */     float var3 = var2.hurtTime - par1;
/*  488: 626 */     if (var2.getHealth() <= 0.0F)
/*  489:     */     {
/*  490: 628 */       float var4 = var2.deathTime + par1;
/*  491: 629 */       GL11.glRotatef(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
/*  492:     */     }
/*  493: 632 */     if (var3 >= 0.0F)
/*  494:     */     {
/*  495: 634 */       var3 /= var2.maxHurtTime;
/*  496: 635 */       var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.141593F);
/*  497: 636 */       float var4 = var2.attackedAtYaw;
/*  498: 637 */       GL11.glRotatef(-var4, 0.0F, 1.0F, 0.0F);
/*  499: 638 */       GL11.glRotatef(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
/*  500: 639 */       GL11.glRotatef(var4, 0.0F, 1.0F, 0.0F);
/*  501:     */     }
/*  502:     */   }
/*  503:     */   
/*  504:     */   private void setupViewBobbing(float par1)
/*  505:     */   {
/*  506: 648 */     if ((this.mc.renderViewEntity instanceof EntityPlayer))
/*  507:     */     {
/*  508: 650 */       EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
/*  509: 651 */       float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
/*  510: 652 */       float var4 = -(var2.distanceWalkedModified + var3 * par1);
/*  511: 653 */       float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
/*  512: 654 */       float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
/*  513: 655 */       GL11.glTranslatef(MathHelper.sin(var4 * 3.141593F) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * 3.141593F) * var5), 0.0F);
/*  514: 656 */       GL11.glRotatef(MathHelper.sin(var4 * 3.141593F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  515: 657 */       GL11.glRotatef(Math.abs(MathHelper.cos(var4 * 3.141593F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  516: 658 */       GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   private void orientCamera(float par1)
/*  521:     */   {
/*  522: 667 */     EntityLivingBase var2 = this.mc.renderViewEntity;
/*  523: 668 */     float var3 = var2.yOffset - 1.62F;
/*  524: 669 */     double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
/*  525: 670 */     double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
/*  526: 671 */     double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
/*  527: 672 */     GL11.glRotatef(this.prevCamRoll + (this.camRoll - this.prevCamRoll) * par1, 0.0F, 0.0F, 1.0F);
/*  528: 674 */     if (var2.isPlayerSleeping())
/*  529:     */     {
/*  530: 676 */       var3 = (float)(var3 + 1.0D);
/*  531: 677 */       GL11.glTranslatef(0.0F, 0.3F, 0.0F);
/*  532: 679 */       if (!this.mc.gameSettings.debugCamEnable)
/*  533:     */       {
/*  534: 681 */         Block var27 = this.mc.theWorld.getBlock(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
/*  535: 683 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists())
/*  536:     */         {
/*  537: 685 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc, var2 });
/*  538:     */         }
/*  539: 687 */         else if (var27 == Blocks.bed)
/*  540:     */         {
/*  541: 689 */           int var11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
/*  542: 690 */           int var13 = var11 & 0x3;
/*  543: 691 */           GL11.glRotatef(var13 * 90, 0.0F, 1.0F, 0.0F);
/*  544:     */         }
/*  545: 694 */         GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, -1.0F, 0.0F);
/*  546: 695 */         GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, -1.0F, 0.0F, 0.0F);
/*  547:     */       }
/*  548:     */     }
/*  549: 698 */     else if (this.mc.gameSettings.thirdPersonView > 0)
/*  550:     */     {
/*  551: 700 */       double var271 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * par1;
/*  552: 704 */       if (this.mc.gameSettings.debugCamEnable)
/*  553:     */       {
/*  554: 706 */         float var28 = this.prevDebugCamYaw + (this.debugCamYaw - this.prevDebugCamYaw) * par1;
/*  555: 707 */         float var281 = this.prevDebugCamPitch + (this.debugCamPitch - this.prevDebugCamPitch) * par1;
/*  556: 708 */         GL11.glTranslatef(0.0F, 0.0F, (float)-var271);
/*  557: 709 */         GL11.glRotatef(var281, 1.0F, 0.0F, 0.0F);
/*  558: 710 */         GL11.glRotatef(var28, 0.0F, 1.0F, 0.0F);
/*  559:     */       }
/*  560:     */       else
/*  561:     */       {
/*  562: 714 */         float var28 = var2.rotationYaw;
/*  563: 715 */         float var281 = var2.rotationPitch;
/*  564: 717 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  565: 719 */           var281 += 180.0F;
/*  566:     */         }
/*  567: 722 */         double var14 = -MathHelper.sin(var28 / 180.0F * 3.141593F) * MathHelper.cos(var281 / 180.0F * 3.141593F) * var271;
/*  568: 723 */         double var16 = MathHelper.cos(var28 / 180.0F * 3.141593F) * MathHelper.cos(var281 / 180.0F * 3.141593F) * var271;
/*  569: 724 */         double var18 = -MathHelper.sin(var281 / 180.0F * 3.141593F) * var271;
/*  570: 726 */         for (int var20 = 0; var20 < 8; var20++)
/*  571:     */         {
/*  572: 728 */           float var21 = (var20 & 0x1) * 2 - 1;
/*  573: 729 */           float var22 = (var20 >> 1 & 0x1) * 2 - 1;
/*  574: 730 */           float var23 = (var20 >> 2 & 0x1) * 2 - 1;
/*  575: 731 */           var21 *= 0.1F;
/*  576: 732 */           var22 *= 0.1F;
/*  577: 733 */           var23 *= 0.1F;
/*  578: 734 */           MovingObjectPosition var24 = this.mc.theWorld.rayTraceBlocks(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var21, var6 + var22, var8 + var23), this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 - var14 + var21 + var23, var6 - var18 + var22, var8 - var16 + var23));
/*  579: 736 */           if (var24 != null)
/*  580:     */           {
/*  581: 738 */             double var25 = var24.hitVec.distanceTo(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var6, var8));
/*  582: 740 */             if (var25 < var271) {
/*  583: 742 */               var271 = var25;
/*  584:     */             }
/*  585:     */           }
/*  586:     */         }
/*  587: 747 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  588: 749 */           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  589:     */         }
/*  590: 752 */         GL11.glRotatef(var2.rotationPitch - var281, 1.0F, 0.0F, 0.0F);
/*  591: 753 */         GL11.glRotatef(var2.rotationYaw - var28, 0.0F, 1.0F, 0.0F);
/*  592: 754 */         GL11.glTranslatef(0.0F, 0.0F, (float)-var271);
/*  593: 755 */         GL11.glRotatef(var28 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  594: 756 */         GL11.glRotatef(var281 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
/*  595:     */       }
/*  596:     */     }
/*  597:     */     else
/*  598:     */     {
/*  599: 761 */       GL11.glTranslatef(0.0F, 0.0F, -0.1F);
/*  600:     */     }
/*  601: 764 */     if (!this.mc.gameSettings.debugCamEnable)
/*  602:     */     {
/*  603: 766 */       GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1, 1.0F, 0.0F, 0.0F);
/*  604: 767 */       GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0F, 0.0F, 1.0F, 0.0F);
/*  605:     */     }
/*  606: 770 */     GL11.glTranslatef(0.0F, var3, 0.0F);
/*  607: 771 */     var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * par1;
/*  608: 772 */     var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * par1 - var3;
/*  609: 773 */     var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * par1;
/*  610: 774 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var6, var8, par1);
/*  611:     */   }
/*  612:     */   
/*  613:     */   private void setupCameraTransform(float par1, int par2)
/*  614:     */   {
/*  615: 782 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*  616: 784 */     if (Config.isFogFancy()) {
/*  617: 786 */       this.farPlaneDistance *= 0.95F;
/*  618:     */     }
/*  619: 789 */     if (Config.isFogFast()) {
/*  620: 791 */       this.farPlaneDistance *= 0.83F;
/*  621:     */     }
/*  622: 794 */     GL11.glMatrixMode(5889);
/*  623: 795 */     GL11.glLoadIdentity();
/*  624: 796 */     float var3 = 0.07F;
/*  625: 798 */     if (this.mc.gameSettings.anaglyph) {
/*  626: 800 */       GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0F, 0.0F);
/*  627:     */     }
/*  628: 803 */     float clipDistance = this.farPlaneDistance * 2.0F;
/*  629: 805 */     if (clipDistance < 128.0F) {
/*  630: 807 */       clipDistance = 128.0F;
/*  631:     */     }
/*  632: 810 */     if (this.mc.theWorld.provider.dimensionId == 1) {
/*  633: 812 */       clipDistance = 256.0F;
/*  634:     */     }
/*  635: 815 */     if (this.cameraZoom != 1.0D)
/*  636:     */     {
/*  637: 817 */       GL11.glTranslatef((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/*  638: 818 */       GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
/*  639:     */     }
/*  640: 821 */     Project.gluPerspective(getFOVModifier(par1, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, clipDistance);
/*  641: 824 */     if (this.mc.playerController.enableEverythingIsScrewedUpMode())
/*  642:     */     {
/*  643: 826 */       float var4 = 0.6666667F;
/*  644: 827 */       GL11.glScalef(1.0F, var4, 1.0F);
/*  645:     */     }
/*  646: 830 */     GL11.glMatrixMode(5888);
/*  647: 831 */     GL11.glLoadIdentity();
/*  648: 833 */     if (this.mc.gameSettings.anaglyph) {
/*  649: 835 */       GL11.glTranslatef((par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*  650:     */     }
/*  651: 838 */     hurtCameraEffect(par1);
/*  652: 840 */     if (this.mc.gameSettings.viewBobbing) {
/*  653: 842 */       setupViewBobbing(par1);
/*  654:     */     }
/*  655: 845 */     float var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;
/*  656: 847 */     if (var4 > 0.0F)
/*  657:     */     {
/*  658: 849 */       byte var7 = 20;
/*  659: 851 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/*  660: 853 */         var7 = 7;
/*  661:     */       }
/*  662: 856 */       float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
/*  663: 857 */       var6 *= var6;
/*  664: 858 */       GL11.glRotatef((this.rendererUpdateCount + par1) * var7, 0.0F, 1.0F, 1.0F);
/*  665: 859 */       GL11.glScalef(1.0F / var6, 1.0F, 1.0F);
/*  666: 860 */       GL11.glRotatef(-(this.rendererUpdateCount + par1) * var7, 0.0F, 1.0F, 1.0F);
/*  667:     */     }
/*  668: 863 */     orientCamera(par1);
/*  669: 865 */     if (this.debugViewDirection > 0)
/*  670:     */     {
/*  671: 867 */       int var71 = this.debugViewDirection - 1;
/*  672: 869 */       if (var71 == 1) {
/*  673: 871 */         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  674:     */       }
/*  675: 874 */       if (var71 == 2) {
/*  676: 876 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  677:     */       }
/*  678: 879 */       if (var71 == 3) {
/*  679: 881 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  680:     */       }
/*  681: 884 */       if (var71 == 4) {
/*  682: 886 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  683:     */       }
/*  684: 889 */       if (var71 == 5) {
/*  685: 891 */         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*  686:     */       }
/*  687:     */     }
/*  688:     */   }
/*  689:     */   
/*  690:     */   private void renderHand(float par1, int par2)
/*  691:     */   {
/*  692: 901 */     if (this.debugViewDirection <= 0)
/*  693:     */     {
/*  694: 903 */       GL11.glMatrixMode(5889);
/*  695: 904 */       GL11.glLoadIdentity();
/*  696: 905 */       float var3 = 0.07F;
/*  697: 907 */       if (this.mc.gameSettings.anaglyph) {
/*  698: 909 */         GL11.glTranslatef(-(par2 * 2 - 1) * var3, 0.0F, 0.0F);
/*  699:     */       }
/*  700: 912 */       if (this.cameraZoom != 1.0D)
/*  701:     */       {
/*  702: 914 */         GL11.glTranslatef((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/*  703: 915 */         GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
/*  704:     */       }
/*  705: 918 */       Project.gluPerspective(getFOVModifier(par1, false), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/*  706: 920 */       if (this.mc.playerController.enableEverythingIsScrewedUpMode())
/*  707:     */       {
/*  708: 922 */         float var4 = 0.6666667F;
/*  709: 923 */         GL11.glScalef(1.0F, var4, 1.0F);
/*  710:     */       }
/*  711: 926 */       GL11.glMatrixMode(5888);
/*  712: 927 */       GL11.glLoadIdentity();
/*  713: 929 */       if (this.mc.gameSettings.anaglyph) {
/*  714: 931 */         GL11.glTranslatef((par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*  715:     */       }
/*  716: 934 */       GL11.glPushMatrix();
/*  717: 935 */       hurtCameraEffect(par1);
/*  718: 937 */       if (this.mc.gameSettings.viewBobbing) {
/*  719: 939 */         setupViewBobbing(par1);
/*  720:     */       }
/*  721: 942 */       if ((this.mc.gameSettings.thirdPersonView == 0) && (!this.mc.renderViewEntity.isPlayerSleeping()) && (!this.mc.gameSettings.hideGUI) && (!this.mc.playerController.enableEverythingIsScrewedUpMode()))
/*  722:     */       {
/*  723: 944 */         enableLightmap(par1);
/*  724: 945 */         this.itemRenderer.renderItemInFirstPerson(par1);
/*  725: 946 */         disableLightmap(par1);
/*  726:     */       }
/*  727: 949 */       GL11.glPopMatrix();
/*  728: 951 */       if ((this.mc.gameSettings.thirdPersonView == 0) && (!this.mc.renderViewEntity.isPlayerSleeping()))
/*  729:     */       {
/*  730: 953 */         this.itemRenderer.renderOverlays(par1);
/*  731: 954 */         hurtCameraEffect(par1);
/*  732:     */       }
/*  733: 957 */       if (this.mc.gameSettings.viewBobbing) {
/*  734: 959 */         setupViewBobbing(par1);
/*  735:     */       }
/*  736:     */     }
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void disableLightmap(double par1)
/*  740:     */   {
/*  741: 969 */     OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  742: 970 */     GL11.glDisable(3553);
/*  743: 971 */     OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void enableLightmap(double par1)
/*  747:     */   {
/*  748: 979 */     OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  749: 980 */     GL11.glMatrixMode(5890);
/*  750: 981 */     GL11.glLoadIdentity();
/*  751: 982 */     float var3 = 0.0039063F;
/*  752: 983 */     GL11.glScalef(var3, var3, var3);
/*  753: 984 */     GL11.glTranslatef(8.0F, 8.0F, 8.0F);
/*  754: 985 */     GL11.glMatrixMode(5888);
/*  755: 986 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/*  756: 987 */     GL11.glTexParameteri(3553, 10241, 9729);
/*  757: 988 */     GL11.glTexParameteri(3553, 10240, 9729);
/*  758: 989 */     GL11.glTexParameteri(3553, 10242, 10496);
/*  759: 990 */     GL11.glTexParameteri(3553, 10243, 10496);
/*  760: 991 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  761: 992 */     GL11.glEnable(3553);
/*  762: 993 */     OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  763:     */   }
/*  764:     */   
/*  765:     */   private void updateTorchFlicker()
/*  766:     */   {
/*  767:1001 */     this.torchFlickerDX = ((float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random()));
/*  768:1002 */     this.torchFlickerDY = ((float)(this.torchFlickerDY + (Math.random() - Math.random()) * Math.random() * Math.random()));
/*  769:1003 */     this.torchFlickerDX = ((float)(this.torchFlickerDX * 0.9D));
/*  770:1004 */     this.torchFlickerDY = ((float)(this.torchFlickerDY * 0.9D));
/*  771:1005 */     this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
/*  772:1006 */     this.torchFlickerY += (this.torchFlickerDY - this.torchFlickerY) * 1.0F;
/*  773:1007 */     this.lightmapUpdateNeeded = true;
/*  774:     */   }
/*  775:     */   
/*  776:     */   private void updateLightmap(float par1)
/*  777:     */   {
/*  778:1012 */     WorldClient var2 = this.mc.theWorld;
/*  779:1014 */     if (var2 != null)
/*  780:     */     {
/*  781:1016 */       if (CustomColorizer.updateLightmap(var2, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision)))
/*  782:     */       {
/*  783:1018 */         this.lightmapTexture.updateDynamicTexture();
/*  784:1019 */         this.lightmapUpdateNeeded = false;
/*  785:1020 */         return;
/*  786:     */       }
/*  787:1023 */       for (int var3 = 0; var3 < 256; var3++)
/*  788:     */       {
/*  789:1025 */         float var4 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
/*  790:1026 */         float var5 = var2.provider.lightBrightnessTable[(var3 / 16)] * var4;
/*  791:1027 */         float var6 = var2.provider.lightBrightnessTable[(var3 % 16)] * (this.torchFlickerX * 0.1F + 1.5F);
/*  792:1029 */         if (var2.lastLightningBolt > 0) {
/*  793:1031 */           var5 = var2.provider.lightBrightnessTable[(var3 / 16)];
/*  794:     */         }
/*  795:1034 */         float var7 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
/*  796:1035 */         float var8 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
/*  797:1036 */         float var11 = var6 * ((var6 * 0.6F + 0.4F) * 0.6F + 0.4F);
/*  798:1037 */         float var12 = var6 * (var6 * var6 * 0.6F + 0.4F);
/*  799:1038 */         float var13 = var7 + var6;
/*  800:1039 */         float var14 = var8 + var11;
/*  801:1040 */         float var15 = var5 + var12;
/*  802:1041 */         var13 = var13 * 0.96F + 0.03F;
/*  803:1042 */         var14 = var14 * 0.96F + 0.03F;
/*  804:1043 */         var15 = var15 * 0.96F + 0.03F;
/*  805:1046 */         if (this.bossColorModifier > 0.0F)
/*  806:     */         {
/*  807:1048 */           float var16 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * par1;
/*  808:1049 */           var13 = var13 * (1.0F - var16) + var13 * 0.7F * var16;
/*  809:1050 */           var14 = var14 * (1.0F - var16) + var14 * 0.6F * var16;
/*  810:1051 */           var15 = var15 * (1.0F - var16) + var15 * 0.6F * var16;
/*  811:     */         }
/*  812:1054 */         if (var2.provider.dimensionId == 1)
/*  813:     */         {
/*  814:1056 */           var13 = 0.22F + var6 * 0.75F;
/*  815:1057 */           var14 = 0.28F + var11 * 0.75F;
/*  816:1058 */           var15 = 0.25F + var12 * 0.75F;
/*  817:     */         }
/*  818:1063 */         if (this.mc.thePlayer.isPotionActive(Potion.nightVision))
/*  819:     */         {
/*  820:1065 */           float var16 = getNightVisionBrightness(this.mc.thePlayer, par1);
/*  821:1066 */           float var17 = 1.0F / var13;
/*  822:1068 */           if (var17 > 1.0F / var14) {
/*  823:1070 */             var17 = 1.0F / var14;
/*  824:     */           }
/*  825:1073 */           if (var17 > 1.0F / var15) {
/*  826:1075 */             var17 = 1.0F / var15;
/*  827:     */           }
/*  828:1078 */           var13 = var13 * (1.0F - var16) + var13 * var17 * var16;
/*  829:1079 */           var14 = var14 * (1.0F - var16) + var14 * var17 * var16;
/*  830:1080 */           var15 = var15 * (1.0F - var16) + var15 * var17 * var16;
/*  831:     */         }
/*  832:1083 */         if (var13 > 1.0F) {
/*  833:1085 */           var13 = 1.0F;
/*  834:     */         }
/*  835:1088 */         if (var14 > 1.0F) {
/*  836:1090 */           var14 = 1.0F;
/*  837:     */         }
/*  838:1093 */         if (var15 > 1.0F) {
/*  839:1095 */           var15 = 1.0F;
/*  840:     */         }
/*  841:1098 */         float var16 = this.mc.gameSettings.gammaSetting;
/*  842:1099 */         float var17 = 1.0F - var13;
/*  843:1100 */         float var18 = 1.0F - var14;
/*  844:1101 */         float var19 = 1.0F - var15;
/*  845:1102 */         var17 = 1.0F - var17 * var17 * var17 * var17;
/*  846:1103 */         var18 = 1.0F - var18 * var18 * var18 * var18;
/*  847:1104 */         var19 = 1.0F - var19 * var19 * var19 * var19;
/*  848:1105 */         var13 = var13 * (1.0F - var16) + var17 * var16;
/*  849:1106 */         var14 = var14 * (1.0F - var16) + var18 * var16;
/*  850:1107 */         var15 = var15 * (1.0F - var16) + var19 * var16;
/*  851:1108 */         var13 = var13 * 0.96F + 0.03F;
/*  852:1109 */         var14 = var14 * 0.96F + 0.03F;
/*  853:1110 */         var15 = var15 * 0.96F + 0.03F;
/*  854:1112 */         if (var13 > 1.0F) {
/*  855:1114 */           var13 = 1.0F;
/*  856:     */         }
/*  857:1117 */         if (var14 > 1.0F) {
/*  858:1119 */           var14 = 1.0F;
/*  859:     */         }
/*  860:1122 */         if (var15 > 1.0F) {
/*  861:1124 */           var15 = 1.0F;
/*  862:     */         }
/*  863:1127 */         if (var13 < 0.0F) {
/*  864:1129 */           var13 = 0.0F;
/*  865:     */         }
/*  866:1132 */         if (var14 < 0.0F) {
/*  867:1134 */           var14 = 0.0F;
/*  868:     */         }
/*  869:1137 */         if (var15 < 0.0F) {
/*  870:1139 */           var15 = 0.0F;
/*  871:     */         }
/*  872:1142 */         short var20 = 255;
/*  873:1143 */         int var21 = (int)(var13 * 255.0F);
/*  874:1144 */         int var22 = (int)(var14 * 255.0F);
/*  875:1145 */         int var23 = (int)(var15 * 255.0F);
/*  876:1146 */         this.lightmapColors[var3] = (var20 << 24 | var21 << 16 | var22 << 8 | var23);
/*  877:     */       }
/*  878:1149 */       this.lightmapTexture.updateDynamicTexture();
/*  879:1150 */       this.lightmapUpdateNeeded = false;
/*  880:     */     }
/*  881:     */   }
/*  882:     */   
/*  883:     */   private float getNightVisionBrightness(EntityPlayer par1EntityPlayer, float par2)
/*  884:     */   {
/*  885:1159 */     int var3 = par1EntityPlayer.getActivePotionEffect(Potion.nightVision).getDuration();
/*  886:1160 */     return var3 > 200 ? 1.0F : 0.7F + MathHelper.sin((var3 - par2) * 3.141593F * 0.2F) * 0.3F;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void updateCameraAndRender(float par1)
/*  890:     */   {
/*  891:1168 */     this.mc.mcProfiler.startSection("lightTex");
/*  892:1170 */     if (!this.initialized)
/*  893:     */     {
/*  894:1172 */       TextureUtils.registerResourceListener();
/*  895:1173 */       ItemRendererOF world = new ItemRendererOF(this.mc);
/*  896:1174 */       this.itemRenderer = world;
/*  897:1175 */       RenderManager.instance.itemRenderer = world;
/*  898:1176 */       this.initialized = true;
/*  899:     */     }
/*  900:1179 */     Config.checkDisplayMode();
/*  901:1180 */     WorldClient world1 = this.mc.theWorld;
/*  902:1182 */     if ((world1 != null) && (Config.getNewRelease() != null))
/*  903:     */     {
/*  904:1184 */       String var2 = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
/*  905:1185 */       String var13 = var2 + " " + Config.getNewRelease();
/*  906:1186 */       ChatComponentText var14 = new ChatComponentText("A new §eOptiFine§f version is available: §e" + var13 + "§f");
/*  907:1187 */       this.mc.ingameGUI.getChatGUI().func_146227_a(var14);
/*  908:1188 */       Config.setNewRelease(null);
/*  909:     */     }
/*  910:1191 */     if ((this.mc.currentScreen instanceof NodusGuiMainMenu)) {
/*  911:1193 */       updateMainMenu((NodusGuiMainMenu)this.mc.currentScreen);
/*  912:     */     }
/*  913:1196 */     if (this.updatedWorld != world1)
/*  914:     */     {
/*  915:1198 */       RandomMobs.worldChanged(this.updatedWorld, world1);
/*  916:1199 */       Config.updateThreadPriorities();
/*  917:1200 */       this.lastServerTime = 0L;
/*  918:1201 */       this.lastServerTicks = 0;
/*  919:1202 */       this.updatedWorld = world1;
/*  920:     */     }
/*  921:1205 */     RenderBlocks.fancyGrass = (Config.isGrassFancy()) || (Config.isBetterGrassFancy());
/*  922:1206 */     Blocks.leaves.func_150122_b(Config.isTreesFancy());
/*  923:1208 */     if (this.lightmapUpdateNeeded) {
/*  924:1210 */       updateLightmap(par1);
/*  925:     */     }
/*  926:1213 */     this.mc.mcProfiler.endSection();
/*  927:1214 */     boolean var21 = Display.isActive();
/*  928:1216 */     if ((!var21) && (this.mc.gameSettings.pauseOnLostFocus) && ((!this.mc.gameSettings.touchscreen) || (!Mouse.isButtonDown(1))))
/*  929:     */     {
/*  930:1218 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
/*  931:1220 */         this.mc.displayInGameMenu();
/*  932:     */       }
/*  933:     */     }
/*  934:     */     else {
/*  935:1225 */       this.prevFrameTime = Minecraft.getSystemTime();
/*  936:     */     }
/*  937:1228 */     this.mc.mcProfiler.startSection("mouse");
/*  938:1230 */     if ((this.mc.inGameHasFocus) && (var21))
/*  939:     */     {
/*  940:1232 */       this.mc.mouseHelper.mouseXYChange();
/*  941:1233 */       float var132 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  942:1234 */       float var141 = var132 * var132 * var132 * 8.0F;
/*  943:1235 */       float var15 = this.mc.mouseHelper.deltaX * var141;
/*  944:1236 */       float var16 = this.mc.mouseHelper.deltaY * var141;
/*  945:1237 */       byte var18 = 1;
/*  946:1239 */       if (this.mc.gameSettings.invertMouse) {
/*  947:1241 */         var18 = -1;
/*  948:     */       }
/*  949:1244 */       if (this.mc.gameSettings.smoothCamera)
/*  950:     */       {
/*  951:1246 */         this.smoothCamYaw += var15;
/*  952:1247 */         this.smoothCamPitch += var16;
/*  953:1248 */         float var17 = par1 - this.smoothCamPartialTicks;
/*  954:1249 */         this.smoothCamPartialTicks = par1;
/*  955:1250 */         var15 = this.smoothCamFilterX * var17;
/*  956:1251 */         var16 = this.smoothCamFilterY * var17;
/*  957:1252 */         this.mc.thePlayer.setAngles(var15, var16 * var18);
/*  958:     */       }
/*  959:     */       else
/*  960:     */       {
/*  961:1256 */         this.mc.thePlayer.setAngles(var15, var16 * var18);
/*  962:     */       }
/*  963:     */     }
/*  964:1260 */     this.mc.mcProfiler.endSection();
/*  965:1262 */     if (!this.mc.skipRenderWorld)
/*  966:     */     {
/*  967:1264 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/*  968:1265 */       final ScaledResolution var133 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  969:1266 */       int var142 = var133.getScaledWidth();
/*  970:1267 */       int var151 = var133.getScaledHeight();
/*  971:1268 */       final int var161 = Mouse.getX() * var142 / this.mc.displayWidth;
/*  972:1269 */       final int var181 = var151 - Mouse.getY() * var151 / this.mc.displayHeight - 1;
/*  973:1270 */       int var171 = this.mc.gameSettings.limitFramerate;
/*  974:1272 */       if (this.mc.theWorld != null)
/*  975:     */       {
/*  976:1274 */         this.mc.mcProfiler.startSection("level");
/*  977:1276 */         if (this.mc.isFramerateLimitBelowMax()) {
/*  978:1278 */           renderWorld(par1, this.renderEndNanoTime + 1000000000 / var171);
/*  979:     */         } else {
/*  980:1282 */           renderWorld(par1, 0L);
/*  981:     */         }
/*  982:1285 */         if (OpenGlHelper.shadersSupported)
/*  983:     */         {
/*  984:1287 */           if (this.theShaderGroup != null)
/*  985:     */           {
/*  986:1289 */             GL11.glMatrixMode(5890);
/*  987:1290 */             GL11.glPushMatrix();
/*  988:1291 */             GL11.glLoadIdentity();
/*  989:1292 */             this.theShaderGroup.loadShaderGroup(par1);
/*  990:1293 */             GL11.glPopMatrix();
/*  991:     */           }
/*  992:1296 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*  993:     */         }
/*  994:1299 */         this.renderEndNanoTime = System.nanoTime();
/*  995:1300 */         this.mc.mcProfiler.endStartSection("gui");
/*  996:1302 */         if ((!this.mc.gameSettings.hideGUI) || (this.mc.currentScreen != null))
/*  997:     */         {
/*  998:1304 */           GL11.glAlphaFunc(516, 0.1F);
/*  999:1305 */           this.mc.ingameGUI.renderGameOverlay(par1, this.mc.currentScreen != null, var161, var181);
/* 1000:     */         }
/* 1001:1308 */         this.mc.mcProfiler.endSection();
/* 1002:     */       }
/* 1003:     */       else
/* 1004:     */       {
/* 1005:1312 */         GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1006:1313 */         GL11.glMatrixMode(5889);
/* 1007:1314 */         GL11.glLoadIdentity();
/* 1008:1315 */         GL11.glMatrixMode(5888);
/* 1009:1316 */         GL11.glLoadIdentity();
/* 1010:1317 */         setupOverlayRendering();
/* 1011:1318 */         this.renderEndNanoTime = System.nanoTime();
/* 1012:     */       }
/* 1013:1321 */       if (this.mc.currentScreen != null)
/* 1014:     */       {
/* 1015:1323 */         GL11.glClear(256);
/* 1016:     */         try
/* 1017:     */         {
/* 1018:1327 */           this.mc.currentScreen.drawScreen(var161, var181, par1);
/* 1019:     */         }
/* 1020:     */         catch (Throwable var131)
/* 1021:     */         {
/* 1022:1331 */           CrashReport var10 = CrashReport.makeCrashReport(var131, "Rendering screen");
/* 1023:1332 */           CrashReportCategory var11 = var10.makeCategory("Screen render details");
/* 1024:1333 */           var11.addCrashSectionCallable("Screen name", new Callable()
/* 1025:     */           {
/* 1026:     */             private static final String __OBFID = "CL_00000948";
/* 1027:     */             
/* 1028:     */             public String call()
/* 1029:     */             {
/* 1030:1338 */               return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
/* 1031:     */             }
/* 1032:1340 */           });
/* 1033:1341 */           var11.addCrashSectionCallable("Mouse location", new Callable()
/* 1034:     */           {
/* 1035:     */             private static final String __OBFID = "CL_00000950";
/* 1036:     */             
/* 1037:     */             public String call()
/* 1038:     */             {
/* 1039:1346 */               return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(var161), Integer.valueOf(var181), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
/* 1040:     */             }
/* 1041:1348 */           });
/* 1042:1349 */           var11.addCrashSectionCallable("Screen size", new Callable()
/* 1043:     */           {
/* 1044:     */             private static final String __OBFID = "CL_00000951";
/* 1045:     */             
/* 1046:     */             public String call()
/* 1047:     */             {
/* 1048:1354 */               return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(var133.getScaledWidth()), Integer.valueOf(var133.getScaledHeight()), Integer.valueOf(EntityRenderer.this.mc.displayWidth), Integer.valueOf(EntityRenderer.this.mc.displayHeight), Integer.valueOf(var133.getScaleFactor()) });
/* 1049:     */             }
/* 1050:1356 */           });
/* 1051:1357 */           throw new ReportedException(var10);
/* 1052:     */         }
/* 1053:     */       }
/* 1054:     */     }
/* 1055:1362 */     waitForServerThread();
/* 1056:1364 */     if (this.mc.gameSettings.showDebugInfo != this.lastShowDebugInfo)
/* 1057:     */     {
/* 1058:1366 */       this.showExtendedDebugInfo = this.mc.gameSettings.showDebugProfilerChart;
/* 1059:1367 */       this.lastShowDebugInfo = this.mc.gameSettings.showDebugInfo;
/* 1060:     */     }
/* 1061:1370 */     if (this.mc.gameSettings.showDebugInfo) {
/* 1062:1372 */       showLagometer(this.mc.mcProfiler.timeTickNano, this.mc.mcProfiler.timeUpdateChunksNano);
/* 1063:     */     }
/* 1064:1375 */     if (this.mc.gameSettings.ofProfiler) {
/* 1065:1377 */       this.mc.gameSettings.showDebugProfilerChart = true;
/* 1066:     */     }
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   private void waitForServerThread()
/* 1070:     */   {
/* 1071:1383 */     this.serverWaitTimeCurrent = 0;
/* 1072:1385 */     if (!Config.isSmoothWorld())
/* 1073:     */     {
/* 1074:1387 */       this.lastServerTime = 0L;
/* 1075:1388 */       this.lastServerTicks = 0;
/* 1076:     */     }
/* 1077:1390 */     else if (this.mc.getIntegratedServer() != null)
/* 1078:     */     {
/* 1079:1392 */       IntegratedServer srv = this.mc.getIntegratedServer();
/* 1080:1393 */       boolean paused = this.mc.func_147113_T();
/* 1081:1395 */       if ((!paused) && (!(this.mc.currentScreen instanceof GuiDownloadTerrain)))
/* 1082:     */       {
/* 1083:1397 */         if (this.serverWaitTime > 0)
/* 1084:     */         {
/* 1085:1399 */           Config.sleep(this.serverWaitTime);
/* 1086:1400 */           this.serverWaitTimeCurrent = this.serverWaitTime;
/* 1087:     */         }
/* 1088:1403 */         long timeNow = System.nanoTime() / 1000000L;
/* 1089:1405 */         if ((this.lastServerTime != 0L) && (this.lastServerTicks != 0))
/* 1090:     */         {
/* 1091:1407 */           long timeDiff = timeNow - this.lastServerTime;
/* 1092:1409 */           if (timeDiff < 0L)
/* 1093:     */           {
/* 1094:1411 */             this.lastServerTime = timeNow;
/* 1095:1412 */             timeDiff = 0L;
/* 1096:     */           }
/* 1097:1415 */           if (timeDiff >= 50L)
/* 1098:     */           {
/* 1099:1417 */             this.lastServerTime = timeNow;
/* 1100:1418 */             int ticks = srv.getTickCounter();
/* 1101:1419 */             int tickDiff = ticks - this.lastServerTicks;
/* 1102:1421 */             if (tickDiff < 0)
/* 1103:     */             {
/* 1104:1423 */               this.lastServerTicks = ticks;
/* 1105:1424 */               tickDiff = 0;
/* 1106:     */             }
/* 1107:1427 */             if ((tickDiff < 1) && (this.serverWaitTime < 100)) {
/* 1108:1429 */               this.serverWaitTime += 2;
/* 1109:     */             }
/* 1110:1432 */             if ((tickDiff > 1) && (this.serverWaitTime > 0)) {
/* 1111:1434 */               this.serverWaitTime -= 1;
/* 1112:     */             }
/* 1113:1437 */             this.lastServerTicks = ticks;
/* 1114:     */           }
/* 1115:     */         }
/* 1116:     */         else
/* 1117:     */         {
/* 1118:1442 */           this.lastServerTime = timeNow;
/* 1119:1443 */           this.lastServerTicks = srv.getTickCounter();
/* 1120:1444 */           this.avgServerTickDiff = 1.0F;
/* 1121:1445 */           this.avgServerTimeDiff = 50.0F;
/* 1122:     */         }
/* 1123:     */       }
/* 1124:     */       else
/* 1125:     */       {
/* 1126:1450 */         if ((this.mc.currentScreen instanceof GuiDownloadTerrain)) {
/* 1127:1452 */           Config.sleep(20L);
/* 1128:     */         }
/* 1129:1455 */         this.lastServerTime = 0L;
/* 1130:1456 */         this.lastServerTicks = 0;
/* 1131:     */       }
/* 1132:     */     }
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   private void showLagometer(long tickTimeNano, long chunkTimeNano)
/* 1136:     */   {
/* 1137:1463 */     if ((this.mc.gameSettings.ofLagometer) || (this.showExtendedDebugInfo))
/* 1138:     */     {
/* 1139:1465 */       if (this.prevFrameTimeNano == -1L) {
/* 1140:1467 */         this.prevFrameTimeNano = System.nanoTime();
/* 1141:     */       }
/* 1142:1470 */       long timeNowNano = System.nanoTime();
/* 1143:1471 */       int currFrameIndex = this.numRecordedFrameTimes & this.frameTimes.length - 1;
/* 1144:1472 */       this.tickTimes[currFrameIndex] = tickTimeNano;
/* 1145:1473 */       this.chunkTimes[currFrameIndex] = chunkTimeNano;
/* 1146:1474 */       this.serverTimes[currFrameIndex] = this.serverWaitTimeCurrent;
/* 1147:1475 */       this.frameTimes[currFrameIndex] = (timeNowNano - this.prevFrameTimeNano);
/* 1148:1476 */       this.numRecordedFrameTimes += 1;
/* 1149:1477 */       this.prevFrameTimeNano = timeNowNano;
/* 1150:1478 */       GL11.glClear(256);
/* 1151:1479 */       GL11.glMatrixMode(5889);
/* 1152:1480 */       GL11.glEnable(2903);
/* 1153:1481 */       GL11.glLoadIdentity();
/* 1154:1482 */       GL11.glOrtho(0.0D, this.mc.displayWidth, this.mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 1155:1483 */       GL11.glMatrixMode(5888);
/* 1156:1484 */       GL11.glLoadIdentity();
/* 1157:1485 */       GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 1158:1486 */       GL11.glLineWidth(1.0F);
/* 1159:1487 */       GL11.glDisable(3553);
/* 1160:1488 */       Tessellator tessellator = Tessellator.instance;
/* 1161:1489 */       tessellator.startDrawing(1);
/* 1162:1491 */       for (int frameNum = 0; frameNum < this.frameTimes.length; frameNum++)
/* 1163:     */       {
/* 1164:1493 */         int lum = (frameNum - this.numRecordedFrameTimes & this.frameTimes.length - 1) * 255 / this.frameTimes.length;
/* 1165:1494 */         long heightFrame = this.frameTimes[frameNum] / 200000L;
/* 1166:1495 */         float baseHeight = this.mc.displayHeight;
/* 1167:1496 */         tessellator.setColorOpaque_I(-16777216 + lum * 256);
/* 1168:1497 */         tessellator.addVertex(frameNum + 0.5F, baseHeight - (float)heightFrame + 0.5F, 0.0D);
/* 1169:1498 */         tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
/* 1170:1499 */         baseHeight -= (float)heightFrame;
/* 1171:1500 */         long heightTick = this.tickTimes[frameNum] / 200000L;
/* 1172:1501 */         tessellator.setColorOpaque_I(-16777216 + lum * 65536 + lum * 256 + lum * 1);
/* 1173:1502 */         tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
/* 1174:1503 */         tessellator.addVertex(frameNum + 0.5F, baseHeight + (float)heightTick + 0.5F, 0.0D);
/* 1175:1504 */         baseHeight += (float)heightTick;
/* 1176:1505 */         long heightChunk = this.chunkTimes[frameNum] / 200000L;
/* 1177:1506 */         tessellator.setColorOpaque_I(-16777216 + lum * 65536);
/* 1178:1507 */         tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
/* 1179:1508 */         tessellator.addVertex(frameNum + 0.5F, baseHeight + (float)heightChunk + 0.5F, 0.0D);
/* 1180:1509 */         baseHeight += (float)heightChunk;
/* 1181:1510 */         long srvTime = this.serverTimes[frameNum];
/* 1182:1512 */         if (srvTime > 0L)
/* 1183:     */         {
/* 1184:1514 */           long heightSrv = srvTime * 1000000L / 200000L;
/* 1185:1515 */           tessellator.setColorOpaque_I(-16777216 + lum * 1);
/* 1186:1516 */           tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
/* 1187:1517 */           tessellator.addVertex(frameNum + 0.5F, baseHeight + (float)heightSrv + 0.5F, 0.0D);
/* 1188:     */         }
/* 1189:     */       }
/* 1190:1521 */       tessellator.draw();
/* 1191:     */     }
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   private void updateMainMenu(NodusGuiMainMenu mainGui)
/* 1195:     */   {
/* 1196:     */     try
/* 1197:     */     {
/* 1198:1529 */       String e = null;
/* 1199:1530 */       Calendar calendar = Calendar.getInstance();
/* 1200:1531 */       calendar.setTime(new Date());
/* 1201:1532 */       int day = calendar.get(5);
/* 1202:1533 */       int month = calendar.get(2) + 1;
/* 1203:1535 */       if ((day == 8) && (month == 4)) {
/* 1204:1537 */         e = "Happy birthday, OptiFine!";
/* 1205:     */       }
/* 1206:1540 */       if ((day == 14) && (month == 8)) {
/* 1207:1542 */         e = "Happy birthday, sp614x!";
/* 1208:     */       }
/* 1209:1545 */       if (e == null) {
/* 1210:1547 */         return;
/* 1211:     */       }
/* 1212:1550 */       Field[] fs = NodusGuiMainMenu.class.getDeclaredFields();
/* 1213:1552 */       for (int i = 0; i < fs.length; i++) {
/* 1214:1554 */         if (fs[i].getType() == String.class)
/* 1215:     */         {
/* 1216:1556 */           fs[i].setAccessible(true);
/* 1217:1557 */           fs[i].set(mainGui, e);
/* 1218:1558 */           break;
/* 1219:     */         }
/* 1220:     */       }
/* 1221:     */     }
/* 1222:     */     catch (Throwable localThrowable) {}
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public void renderWorld(float par1, long par2)
/* 1226:     */   {
/* 1227:1570 */     this.mc.mcProfiler.startSection("lightTex");
/* 1228:1572 */     if (this.lightmapUpdateNeeded) {
/* 1229:1574 */       updateLightmap(par1);
/* 1230:     */     }
/* 1231:1577 */     GL11.glEnable(2884);
/* 1232:1578 */     GL11.glEnable(2929);
/* 1233:1579 */     GL11.glEnable(3008);
/* 1234:1580 */     GL11.glAlphaFunc(516, 0.5F);
/* 1235:1582 */     if (this.mc.renderViewEntity == null) {
/* 1236:1584 */       this.mc.renderViewEntity = this.mc.thePlayer;
/* 1237:     */     }
/* 1238:1587 */     this.mc.mcProfiler.endStartSection("pick");
/* 1239:1588 */     getMouseOver(par1);
/* 1240:1589 */     EntityLivingBase var4 = this.mc.renderViewEntity;
/* 1241:1590 */     RenderGlobal var5 = this.mc.renderGlobal;
/* 1242:1591 */     EffectRenderer var6 = this.mc.effectRenderer;
/* 1243:1592 */     double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par1;
/* 1244:1593 */     double var9 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par1;
/* 1245:1594 */     double var11 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par1;
/* 1246:1595 */     this.mc.mcProfiler.endStartSection("center");
/* 1247:1597 */     for (int var13 = 0; var13 < 2; var13++)
/* 1248:     */     {
/* 1249:1599 */       if (this.mc.gameSettings.anaglyph)
/* 1250:     */       {
/* 1251:1601 */         anaglyphField = var13;
/* 1252:1603 */         if (anaglyphField == 0) {
/* 1253:1605 */           GL11.glColorMask(false, true, true, false);
/* 1254:     */         } else {
/* 1255:1609 */           GL11.glColorMask(true, false, false, false);
/* 1256:     */         }
/* 1257:     */       }
/* 1258:1613 */       this.mc.mcProfiler.endStartSection("clear");
/* 1259:1614 */       GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1260:1615 */       updateFogColor(par1);
/* 1261:1616 */       GL11.glClear(16640);
/* 1262:1617 */       GL11.glEnable(2884);
/* 1263:1618 */       this.mc.mcProfiler.endStartSection("camera");
/* 1264:1619 */       setupCameraTransform(par1, var13);
/* 1265:1620 */       ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
/* 1266:1621 */       this.mc.mcProfiler.endStartSection("frustrum");
/* 1267:1622 */       ClippingHelperImpl.getInstance();
/* 1268:1624 */       if ((!Config.isSkyEnabled()) && (!Config.isSunMoonEnabled()) && (!Config.isStarsEnabled()))
/* 1269:     */       {
/* 1270:1626 */         GL11.glDisable(3042);
/* 1271:     */       }
/* 1272:     */       else
/* 1273:     */       {
/* 1274:1630 */         setupFog(-1, par1);
/* 1275:1631 */         this.mc.mcProfiler.endStartSection("sky");
/* 1276:1632 */         var5.renderSky(par1);
/* 1277:     */       }
/* 1278:1635 */       GL11.glEnable(2912);
/* 1279:1636 */       setupFog(1, par1);
/* 1280:1638 */       if (this.mc.gameSettings.ambientOcclusion != 0) {
/* 1281:1640 */         GL11.glShadeModel(7425);
/* 1282:     */       }
/* 1283:1643 */       this.mc.mcProfiler.endStartSection("culling");
/* 1284:1644 */       Frustrum var14 = new Frustrum();
/* 1285:1645 */       var14.setPosition(var7, var9, var11);
/* 1286:1646 */       this.mc.renderGlobal.clipRenderersByFrustum(var14, par1);
/* 1287:1648 */       if (var13 == 0)
/* 1288:     */       {
/* 1289:1650 */         this.mc.mcProfiler.endStartSection("updatechunks");
/* 1290:1652 */         while ((!this.mc.renderGlobal.updateRenderers(var4, false)) && (par2 != 0L))
/* 1291:     */         {
/* 1292:1654 */           long var17 = par2 - System.nanoTime();
/* 1293:1656 */           if ((var17 < 0L) || (var17 > 1000000000L)) {
/* 1294:     */             break;
/* 1295:     */           }
/* 1296:     */         }
/* 1297:     */       }
/* 1298:1663 */       if (var4.posY < 128.0D) {
/* 1299:1665 */         renderCloudsCheck(var5, par1);
/* 1300:     */       }
/* 1301:1668 */       this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1302:1669 */       setupFog(0, par1);
/* 1303:1670 */       GL11.glEnable(2912);
/* 1304:1671 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1305:1672 */       RenderHelper.disableStandardItemLighting();
/* 1306:1673 */       this.mc.mcProfiler.endStartSection("terrain");
/* 1307:1674 */       GL11.glMatrixMode(5888);
/* 1308:1675 */       GL11.glPushMatrix();
/* 1309:1676 */       var5.sortAndRender(var4, 0, par1);
/* 1310:1677 */       GL11.glShadeModel(7424);
/* 1311:1678 */       boolean hasForge = Reflector.ForgeHooksClient.exists();
/* 1312:1681 */       if (this.debugViewDirection == 0)
/* 1313:     */       {
/* 1314:1683 */         GL11.glMatrixMode(5888);
/* 1315:1684 */         GL11.glPopMatrix();
/* 1316:1685 */         GL11.glPushMatrix();
/* 1317:1686 */         RenderHelper.enableStandardItemLighting();
/* 1318:1687 */         this.mc.mcProfiler.endStartSection("entities");
/* 1319:1689 */         if (hasForge) {
/* 1320:1691 */           Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/* 1321:     */         }
/* 1322:1694 */         var5.renderEntities(var4, var14, par1);
/* 1323:1696 */         if (hasForge) {
/* 1324:1698 */           Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1325:     */         }
/* 1326:1701 */         RenderHelper.disableStandardItemLighting();
/* 1327:1702 */         GL11.glAlphaFunc(516, 0.1F);
/* 1328:1703 */         GL11.glMatrixMode(5888);
/* 1329:1704 */         GL11.glPopMatrix();
/* 1330:1705 */         GL11.glPushMatrix();
/* 1331:1707 */         if ((this.mc.objectMouseOver != null) && (var4.isInsideOfMaterial(Material.water)) && ((var4 instanceof EntityPlayer)) && (!this.mc.gameSettings.hideGUI))
/* 1332:     */         {
/* 1333:1709 */           EntityPlayer var171 = (EntityPlayer)var4;
/* 1334:1710 */           GL11.glDisable(3008);
/* 1335:1711 */           this.mc.mcProfiler.endStartSection("outline");
/* 1336:1713 */           if (hasForge)
/* 1337:     */           {
/* 1338:1713 */             if (Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { var5, var171, this.mc.objectMouseOver, Integer.valueOf(0), var171.inventory.getCurrentItem(), Float.valueOf(par1) })) {}
/* 1339:     */           }
/* 1340:1713 */           else if (!this.mc.gameSettings.hideGUI) {
/* 1341:1715 */             var5.drawSelectionBox(var171, this.mc.objectMouseOver, 0, par1);
/* 1342:     */           }
/* 1343:1717 */           GL11.glEnable(3008);
/* 1344:     */         }
/* 1345:     */       }
/* 1346:1721 */       GL11.glMatrixMode(5888);
/* 1347:1722 */       GL11.glPopMatrix();
/* 1348:1724 */       if ((this.cameraZoom == 1.0D) && ((var4 instanceof EntityPlayer)) && (!this.mc.gameSettings.hideGUI) && (this.mc.objectMouseOver != null) && (!var4.isInsideOfMaterial(Material.water)))
/* 1349:     */       {
/* 1350:1726 */         EntityPlayer var171 = (EntityPlayer)var4;
/* 1351:1727 */         GL11.glDisable(3008);
/* 1352:1728 */         this.mc.mcProfiler.endStartSection("outline");
/* 1353:1730 */         if (hasForge)
/* 1354:     */         {
/* 1355:1730 */           if (Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { var5, var171, this.mc.objectMouseOver, Integer.valueOf(0), var171.inventory.getCurrentItem(), Float.valueOf(par1) })) {}
/* 1356:     */         }
/* 1357:1730 */         else if (!this.mc.gameSettings.hideGUI) {
/* 1358:1732 */           var5.drawSelectionBox(var171, this.mc.objectMouseOver, 0, par1);
/* 1359:     */         }
/* 1360:1734 */         GL11.glEnable(3008);
/* 1361:     */       }
/* 1362:1737 */       this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1363:1738 */       GL11.glEnable(3042);
/* 1364:1739 */       OpenGlHelper.glBlendFunc(770, 1, 1, 0);
/* 1365:1740 */       var5.drawBlockDamageTexture(Tessellator.instance, var4, par1);
/* 1366:1741 */       GL11.glDisable(3042);
/* 1367:1742 */       GL11.glDepthMask(false);
/* 1368:1743 */       GL11.glEnable(2884);
/* 1369:1744 */       this.mc.mcProfiler.endStartSection("weather");
/* 1370:1745 */       renderRainSnow(par1);
/* 1371:1746 */       GL11.glDepthMask(true);
/* 1372:1747 */       GL11.glDisable(3042);
/* 1373:1748 */       GL11.glEnable(2884);
/* 1374:1749 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1375:1750 */       GL11.glAlphaFunc(516, 0.1F);
/* 1376:1751 */       setupFog(0, par1);
/* 1377:1752 */       GL11.glEnable(3042);
/* 1378:1753 */       GL11.glDepthMask(false);
/* 1379:1754 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1380:1755 */       WrUpdates.resumeBackgroundUpdates();
/* 1381:1757 */       if (Config.isWaterFancy())
/* 1382:     */       {
/* 1383:1759 */         this.mc.mcProfiler.endStartSection("water");
/* 1384:1761 */         if (this.mc.gameSettings.ambientOcclusion != 0) {
/* 1385:1763 */           GL11.glShadeModel(7425);
/* 1386:     */         }
/* 1387:1766 */         GL11.glEnable(3042);
/* 1388:1767 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1389:1769 */         if (this.mc.gameSettings.anaglyph)
/* 1390:     */         {
/* 1391:1771 */           if (anaglyphField == 0) {
/* 1392:1773 */             GL11.glColorMask(false, true, true, true);
/* 1393:     */           } else {
/* 1394:1777 */             GL11.glColorMask(true, false, false, true);
/* 1395:     */           }
/* 1396:1780 */           var5.renderAllSortedRenderers(1, par1);
/* 1397:     */         }
/* 1398:     */         else
/* 1399:     */         {
/* 1400:1784 */           var5.renderAllSortedRenderers(1, par1);
/* 1401:     */         }
/* 1402:1787 */         GL11.glDisable(3042);
/* 1403:1788 */         GL11.glShadeModel(7424);
/* 1404:     */       }
/* 1405:     */       else
/* 1406:     */       {
/* 1407:1792 */         this.mc.mcProfiler.endStartSection("water");
/* 1408:1793 */         var5.renderAllSortedRenderers(1, par1);
/* 1409:     */       }
/* 1410:1796 */       WrUpdates.pauseBackgroundUpdates();
/* 1411:1798 */       if ((hasForge) && (this.debugViewDirection == 0))
/* 1412:     */       {
/* 1413:1800 */         RenderHelper.enableStandardItemLighting();
/* 1414:1801 */         this.mc.mcProfiler.endStartSection("entities");
/* 1415:1802 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 1416:1803 */         this.mc.renderGlobal.renderEntities(var4, var14, par1);
/* 1417:1804 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1418:1805 */         RenderHelper.disableStandardItemLighting();
/* 1419:     */       }
/* 1420:1808 */       GL11.glDepthMask(true);
/* 1421:1809 */       GL11.glEnable(2884);
/* 1422:1810 */       GL11.glDisable(3042);
/* 1423:1811 */       GL11.glDisable(2912);
/* 1424:1813 */       if (var4.posY >= 128.0D + this.mc.gameSettings.ofCloudsHeight * 128.0F)
/* 1425:     */       {
/* 1426:1815 */         this.mc.mcProfiler.endStartSection("aboveClouds");
/* 1427:1816 */         renderCloudsCheck(var5, par1);
/* 1428:     */       }
/* 1429:1819 */       enableLightmap(par1);
/* 1430:1820 */       this.mc.mcProfiler.endStartSection("litParticles");
/* 1431:1821 */       RenderHelper.enableStandardItemLighting();
/* 1432:1822 */       var6.renderLitParticles(var4, par1);
/* 1433:1823 */       RenderHelper.disableStandardItemLighting();
/* 1434:1824 */       setupFog(0, par1);
/* 1435:1825 */       this.mc.mcProfiler.endStartSection("particles");
/* 1436:1826 */       var6.renderParticles(var4, par1);
/* 1437:1827 */       disableLightmap(par1);
/* 1438:1829 */       if (hasForge)
/* 1439:     */       {
/* 1440:1831 */         this.mc.mcProfiler.endStartSection("FRenderLast");
/* 1441:1832 */         Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { var5, Float.valueOf(par1) });
/* 1442:     */       }
/* 1443:1835 */       this.mc.mcProfiler.endStartSection("hand");
/* 1444:     */       
/* 1445:     */ 
/* 1446:1838 */       EventManager.call(new EventRenderWorld());
/* 1447:1840 */       if (this.cameraZoom == 1.0D)
/* 1448:     */       {
/* 1449:1842 */         GL11.glClear(256);
/* 1450:1843 */         renderHand(par1, var13);
/* 1451:     */       }
/* 1452:1846 */       if (!this.mc.gameSettings.anaglyph)
/* 1453:     */       {
/* 1454:1848 */         this.mc.mcProfiler.endSection();
/* 1455:1849 */         return;
/* 1456:     */       }
/* 1457:     */     }
/* 1458:1853 */     GL11.glColorMask(true, true, true, false);
/* 1459:1854 */     this.mc.mcProfiler.endSection();
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   private void renderCloudsCheck(RenderGlobal par1RenderGlobal, float par2)
/* 1463:     */   {
/* 1464:1862 */     if (this.mc.gameSettings.shouldRenderClouds())
/* 1465:     */     {
/* 1466:1864 */       this.mc.mcProfiler.endStartSection("clouds");
/* 1467:1865 */       GL11.glPushMatrix();
/* 1468:1866 */       setupFog(0, par2);
/* 1469:1867 */       GL11.glEnable(2912);
/* 1470:1868 */       par1RenderGlobal.renderClouds(par2);
/* 1471:1869 */       GL11.glDisable(2912);
/* 1472:1870 */       setupFog(1, par2);
/* 1473:1871 */       GL11.glPopMatrix();
/* 1474:     */     }
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   private void addRainParticles()
/* 1478:     */   {
/* 1479:1877 */     float var1 = this.mc.theWorld.getRainStrength(1.0F);
/* 1480:1879 */     if (!Config.isRainFancy()) {
/* 1481:1881 */       var1 /= 2.0F;
/* 1482:     */     }
/* 1483:1884 */     if ((var1 != 0.0F) && (Config.isRainSplash()))
/* 1484:     */     {
/* 1485:1886 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 1486:1887 */       EntityLivingBase var2 = this.mc.renderViewEntity;
/* 1487:1888 */       WorldClient var3 = this.mc.theWorld;
/* 1488:1889 */       int var4 = MathHelper.floor_double(var2.posX);
/* 1489:1890 */       int var5 = MathHelper.floor_double(var2.posY);
/* 1490:1891 */       int var6 = MathHelper.floor_double(var2.posZ);
/* 1491:1892 */       byte var7 = 10;
/* 1492:1893 */       double var8 = 0.0D;
/* 1493:1894 */       double var10 = 0.0D;
/* 1494:1895 */       double var12 = 0.0D;
/* 1495:1896 */       int var14 = 0;
/* 1496:1897 */       int var15 = (int)(100.0F * var1 * var1);
/* 1497:1899 */       if (this.mc.gameSettings.particleSetting == 1) {
/* 1498:1901 */         var15 >>= 1;
/* 1499:1903 */       } else if (this.mc.gameSettings.particleSetting == 2) {
/* 1500:1905 */         var15 = 0;
/* 1501:     */       }
/* 1502:1908 */       for (int var16 = 0; var16 < var15; var16++)
/* 1503:     */       {
/* 1504:1910 */         int var17 = var4 + this.random.nextInt(var7) - this.random.nextInt(var7);
/* 1505:1911 */         int var18 = var6 + this.random.nextInt(var7) - this.random.nextInt(var7);
/* 1506:1912 */         int var19 = var3.getPrecipitationHeight(var17, var18);
/* 1507:1913 */         Block var20 = var3.getBlock(var17, var19 - 1, var18);
/* 1508:1914 */         BiomeGenBase var21 = var3.getBiomeGenForCoords(var17, var18);
/* 1509:1916 */         if ((var19 <= var5 + var7) && (var19 >= var5 - var7) && (var21.canSpawnLightningBolt()) && (var21.getFloatTemperature(var17, var19, var18) >= 0.15F))
/* 1510:     */         {
/* 1511:1918 */           float var22 = this.random.nextFloat();
/* 1512:1919 */           float var23 = this.random.nextFloat();
/* 1513:1921 */           if (var20.getMaterial() == Material.lava)
/* 1514:     */           {
/* 1515:1923 */             this.mc.effectRenderer.addEffect(new EntitySmokeFX(var3, var17 + var22, var19 + 0.1F - var20.getBlockBoundsMinY(), var18 + var23, 0.0D, 0.0D, 0.0D));
/* 1516:     */           }
/* 1517:1925 */           else if (var20.getMaterial() != Material.air)
/* 1518:     */           {
/* 1519:1927 */             var14++;
/* 1520:1929 */             if (this.random.nextInt(var14) == 0)
/* 1521:     */             {
/* 1522:1931 */               var8 = var17 + var22;
/* 1523:1932 */               var10 = var19 + 0.1F - var20.getBlockBoundsMinY();
/* 1524:1933 */               var12 = var18 + var23;
/* 1525:     */             }
/* 1526:1936 */             EntityRainFX fx = new EntityRainFX(var3, var17 + var22, var19 + 0.1F - var20.getBlockBoundsMinY(), var18 + var23);
/* 1527:1937 */             CustomColorizer.updateWaterFX(fx, var3);
/* 1528:1938 */             this.mc.effectRenderer.addEffect(fx);
/* 1529:     */           }
/* 1530:     */         }
/* 1531:     */       }
/* 1532:1943 */       if ((var14 > 0) && (this.random.nextInt(3) < this.rainSoundCounter++))
/* 1533:     */       {
/* 1534:1945 */         this.rainSoundCounter = 0;
/* 1535:1947 */         if ((var10 > var2.posY + 1.0D) && (var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY))) {
/* 1536:1949 */           this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.1F, 0.5F, false);
/* 1537:     */         } else {
/* 1538:1953 */           this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.2F, 1.0F, false);
/* 1539:     */         }
/* 1540:     */       }
/* 1541:     */     }
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   protected void renderRainSnow(float par1)
/* 1545:     */   {
/* 1546:1964 */     float var2 = this.mc.theWorld.getRainStrength(par1);
/* 1547:1966 */     if (var2 > 0.0F)
/* 1548:     */     {
/* 1549:1968 */       enableLightmap(par1);
/* 1550:1970 */       if (this.rainXCoords == null)
/* 1551:     */       {
/* 1552:1972 */         this.rainXCoords = new float[1024];
/* 1553:1973 */         this.rainYCoords = new float[1024];
/* 1554:1975 */         for (int var41 = 0; var41 < 32; var41++) {
/* 1555:1977 */           for (int var42 = 0; var42 < 32; var42++)
/* 1556:     */           {
/* 1557:1979 */             float var43 = var42 - 16;
/* 1558:1980 */             float var44 = var41 - 16;
/* 1559:1981 */             float var45 = MathHelper.sqrt_float(var43 * var43 + var44 * var44);
/* 1560:1982 */             this.rainXCoords[(var41 << 5 | var42)] = (-var44 / var45);
/* 1561:1983 */             this.rainYCoords[(var41 << 5 | var42)] = (var43 / var45);
/* 1562:     */           }
/* 1563:     */         }
/* 1564:     */       }
/* 1565:1988 */       if (Config.isRainOff()) {
/* 1566:1990 */         return;
/* 1567:     */       }
/* 1568:1993 */       EntityLivingBase var411 = this.mc.renderViewEntity;
/* 1569:1994 */       WorldClient var421 = this.mc.theWorld;
/* 1570:1995 */       int var431 = MathHelper.floor_double(var411.posX);
/* 1571:1996 */       int var441 = MathHelper.floor_double(var411.posY);
/* 1572:1997 */       int var451 = MathHelper.floor_double(var411.posZ);
/* 1573:1998 */       Tessellator var8 = Tessellator.instance;
/* 1574:1999 */       GL11.glDisable(2884);
/* 1575:2000 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1576:2001 */       GL11.glEnable(3042);
/* 1577:2002 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 1578:2003 */       GL11.glAlphaFunc(516, 0.1F);
/* 1579:2004 */       double var9 = var411.lastTickPosX + (var411.posX - var411.lastTickPosX) * par1;
/* 1580:2005 */       double var11 = var411.lastTickPosY + (var411.posY - var411.lastTickPosY) * par1;
/* 1581:2006 */       double var13 = var411.lastTickPosZ + (var411.posZ - var411.lastTickPosZ) * par1;
/* 1582:2007 */       int var15 = MathHelper.floor_double(var11);
/* 1583:2008 */       byte var16 = 5;
/* 1584:2010 */       if (Config.isRainFancy()) {
/* 1585:2012 */         var16 = 10;
/* 1586:     */       }
/* 1587:2015 */       boolean var17 = false;
/* 1588:2016 */       byte var18 = -1;
/* 1589:2017 */       float var19 = this.rendererUpdateCount + par1;
/* 1590:2019 */       if (Config.isRainFancy()) {
/* 1591:2021 */         var16 = 10;
/* 1592:     */       }
/* 1593:2024 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1594:2025 */       var17 = false;
/* 1595:2027 */       for (int var20 = var451 - var16; var20 <= var451 + var16; var20++) {
/* 1596:2029 */         for (int var21 = var431 - var16; var21 <= var431 + var16; var21++)
/* 1597:     */         {
/* 1598:2031 */           int var22 = (var20 - var451 + 16) * 32 + var21 - var431 + 16;
/* 1599:2032 */           float var23 = this.rainXCoords[var22] * 0.5F;
/* 1600:2033 */           float var24 = this.rainYCoords[var22] * 0.5F;
/* 1601:2034 */           BiomeGenBase var25 = var421.getBiomeGenForCoords(var21, var20);
/* 1602:2036 */           if ((var25.canSpawnLightningBolt()) || (var25.getEnableSnow()))
/* 1603:     */           {
/* 1604:2038 */             int var26 = var421.getPrecipitationHeight(var21, var20);
/* 1605:2039 */             int var27 = var441 - var16;
/* 1606:2040 */             int var28 = var441 + var16;
/* 1607:2042 */             if (var27 < var26) {
/* 1608:2044 */               var27 = var26;
/* 1609:     */             }
/* 1610:2047 */             if (var28 < var26) {
/* 1611:2049 */               var28 = var26;
/* 1612:     */             }
/* 1613:2052 */             float var29 = 1.0F;
/* 1614:2053 */             int var30 = var26;
/* 1615:2055 */             if (var26 < var15) {
/* 1616:2057 */               var30 = var15;
/* 1617:     */             }
/* 1618:2060 */             if (var27 != var28)
/* 1619:     */             {
/* 1620:2062 */               this.random.setSeed(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
/* 1621:2063 */               float var31 = var25.getFloatTemperature(var21, var27, var20);
/* 1622:2067 */               if (var421.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15F)
/* 1623:     */               {
/* 1624:2069 */                 if (var18 != 0)
/* 1625:     */                 {
/* 1626:2071 */                   if (var18 >= 0) {
/* 1627:2073 */                     var8.draw();
/* 1628:     */                   }
/* 1629:2076 */                   var18 = 0;
/* 1630:2077 */                   this.mc.getTextureManager().bindTexture(locationRainPng);
/* 1631:2078 */                   var8.startDrawingQuads();
/* 1632:     */                 }
/* 1633:2081 */                 float var32 = ((this.rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 0x1F) + par1) / 32.0F * (3.0F + this.random.nextFloat());
/* 1634:2082 */                 double var46 = var21 + 0.5F - var411.posX;
/* 1635:2083 */                 double var35 = var20 + 0.5F - var411.posZ;
/* 1636:2084 */                 float var47 = MathHelper.sqrt_double(var46 * var46 + var35 * var35) / var16;
/* 1637:2085 */                 float var38 = 1.0F;
/* 1638:2086 */                 var8.setBrightness(var421.getLightBrightnessForSkyBlocks(var21, var30, var20, 0));
/* 1639:2087 */                 var8.setColorRGBA_F(var38, var38, var38, ((1.0F - var47 * var47) * 0.5F + 0.5F) * var2);
/* 1640:2088 */                 var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
/* 1641:2089 */                 var8.addVertexWithUV(var21 - var23 + 0.5D, var27, var20 - var24 + 0.5D, 0.0F * var29, var27 * var29 / 4.0F + var32 * var29);
/* 1642:2090 */                 var8.addVertexWithUV(var21 + var23 + 0.5D, var27, var20 + var24 + 0.5D, 1.0F * var29, var27 * var29 / 4.0F + var32 * var29);
/* 1643:2091 */                 var8.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var29, var28 * var29 / 4.0F + var32 * var29);
/* 1644:2092 */                 var8.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var29, var28 * var29 / 4.0F + var32 * var29);
/* 1645:2093 */                 var8.setTranslation(0.0D, 0.0D, 0.0D);
/* 1646:     */               }
/* 1647:     */               else
/* 1648:     */               {
/* 1649:2097 */                 if (var18 != 1)
/* 1650:     */                 {
/* 1651:2099 */                   if (var18 >= 0) {
/* 1652:2101 */                     var8.draw();
/* 1653:     */                   }
/* 1654:2104 */                   var18 = 1;
/* 1655:2105 */                   this.mc.getTextureManager().bindTexture(locationSnowPng);
/* 1656:2106 */                   var8.startDrawingQuads();
/* 1657:     */                 }
/* 1658:2109 */                 float var32 = ((this.rendererUpdateCount & 0x1FF) + par1) / 512.0F;
/* 1659:2110 */                 float var461 = this.random.nextFloat() + var19 * 0.01F * (float)this.random.nextGaussian();
/* 1660:2111 */                 float var34 = this.random.nextFloat() + var19 * (float)this.random.nextGaussian() * 0.001F;
/* 1661:2112 */                 double var35 = var21 + 0.5F - var411.posX;
/* 1662:2113 */                 double var471 = var20 + 0.5F - var411.posZ;
/* 1663:2114 */                 float var39 = MathHelper.sqrt_double(var35 * var35 + var471 * var471) / var16;
/* 1664:2115 */                 float var40 = 1.0F;
/* 1665:2116 */                 var8.setBrightness((var421.getLightBrightnessForSkyBlocks(var21, var30, var20, 0) * 3 + 15728880) / 4);
/* 1666:2117 */                 var8.setColorRGBA_F(var40, var40, var40, ((1.0F - var39 * var39) * 0.3F + 0.5F) * var2);
/* 1667:2118 */                 var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
/* 1668:2119 */                 var8.addVertexWithUV(var21 - var23 + 0.5D, var27, var20 - var24 + 0.5D, 0.0F * var29 + var461, var27 * var29 / 4.0F + var32 * var29 + var34);
/* 1669:2120 */                 var8.addVertexWithUV(var21 + var23 + 0.5D, var27, var20 + var24 + 0.5D, 1.0F * var29 + var461, var27 * var29 / 4.0F + var32 * var29 + var34);
/* 1670:2121 */                 var8.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var29 + var461, var28 * var29 / 4.0F + var32 * var29 + var34);
/* 1671:2122 */                 var8.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var29 + var461, var28 * var29 / 4.0F + var32 * var29 + var34);
/* 1672:2123 */                 var8.setTranslation(0.0D, 0.0D, 0.0D);
/* 1673:     */               }
/* 1674:     */             }
/* 1675:     */           }
/* 1676:     */         }
/* 1677:     */       }
/* 1678:2130 */       if (var18 >= 0) {
/* 1679:2132 */         var8.draw();
/* 1680:     */       }
/* 1681:2135 */       GL11.glEnable(2884);
/* 1682:2136 */       GL11.glDisable(3042);
/* 1683:2137 */       GL11.glAlphaFunc(516, 0.1F);
/* 1684:2138 */       disableLightmap(par1);
/* 1685:     */     }
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public void setupOverlayRendering()
/* 1689:     */   {
/* 1690:2147 */     ScaledResolution var1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/* 1691:2148 */     GL11.glClear(256);
/* 1692:2149 */     GL11.glMatrixMode(5889);
/* 1693:2150 */     GL11.glLoadIdentity();
/* 1694:2151 */     GL11.glOrtho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
/* 1695:2152 */     GL11.glMatrixMode(5888);
/* 1696:2153 */     GL11.glLoadIdentity();
/* 1697:2154 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 1698:     */   }
/* 1699:     */   
/* 1700:     */   private void updateFogColor(float par1)
/* 1701:     */   {
/* 1702:2162 */     WorldClient var2 = this.mc.theWorld;
/* 1703:2163 */     EntityLivingBase var3 = this.mc.renderViewEntity;
/* 1704:2164 */     float var4 = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 16.0F;
/* 1705:2165 */     var4 = 1.0F - (float)Math.pow(var4, 0.25D);
/* 1706:2166 */     Vec3 var5 = var2.getSkyColor(this.mc.renderViewEntity, par1);
/* 1707:2167 */     int worldType = var2.provider.dimensionId;
/* 1708:2169 */     switch (worldType)
/* 1709:     */     {
/* 1710:     */     case 0: 
/* 1711:2172 */       var5 = CustomColorizer.getSkyColor(var5, this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0D, this.mc.renderViewEntity.posZ);
/* 1712:2173 */       break;
/* 1713:     */     case 1: 
/* 1714:2176 */       var5 = CustomColorizer.getSkyColorEnd(var5);
/* 1715:     */     }
/* 1716:2179 */     float var6 = (float)var5.xCoord;
/* 1717:2180 */     float var7 = (float)var5.yCoord;
/* 1718:2181 */     float var8 = (float)var5.zCoord;
/* 1719:2182 */     Vec3 var9 = var2.getFogColor(par1);
/* 1720:2184 */     switch (worldType)
/* 1721:     */     {
/* 1722:     */     case -1: 
/* 1723:2187 */       var9 = CustomColorizer.getFogColorNether(var9);
/* 1724:2188 */       break;
/* 1725:     */     case 0: 
/* 1726:2191 */       var9 = CustomColorizer.getFogColor(var9, this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0D, this.mc.renderViewEntity.posZ);
/* 1727:2192 */       break;
/* 1728:     */     case 1: 
/* 1729:2195 */       var9 = CustomColorizer.getFogColorEnd(var9);
/* 1730:     */     }
/* 1731:2198 */     this.fogColorRed = ((float)var9.xCoord);
/* 1732:2199 */     this.fogColorGreen = ((float)var9.yCoord);
/* 1733:2200 */     this.fogColorBlue = ((float)var9.zCoord);
/* 1734:2203 */     if (this.mc.gameSettings.renderDistanceChunks >= 4)
/* 1735:     */     {
/* 1736:2205 */       Vec3 var19 = MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0F ? var2.getWorldVec3Pool().getVecFromPool(-1.0D, 0.0D, 0.0D) : var2.getWorldVec3Pool().getVecFromPool(1.0D, 0.0D, 0.0D);
/* 1737:2206 */       float var11 = (float)var3.getLook(par1).dotProduct(var19);
/* 1738:2208 */       if (var11 < 0.0F) {
/* 1739:2210 */         var11 = 0.0F;
/* 1740:     */       }
/* 1741:2213 */       if (var11 > 0.0F)
/* 1742:     */       {
/* 1743:2215 */         float[] var20 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1);
/* 1744:2217 */         if (var20 != null)
/* 1745:     */         {
/* 1746:2219 */           var11 *= var20[3];
/* 1747:2220 */           this.fogColorRed = (this.fogColorRed * (1.0F - var11) + var20[0] * var11);
/* 1748:2221 */           this.fogColorGreen = (this.fogColorGreen * (1.0F - var11) + var20[1] * var11);
/* 1749:2222 */           this.fogColorBlue = (this.fogColorBlue * (1.0F - var11) + var20[2] * var11);
/* 1750:     */         }
/* 1751:     */       }
/* 1752:     */     }
/* 1753:2227 */     this.fogColorRed += (var6 - this.fogColorRed) * var4;
/* 1754:2228 */     this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
/* 1755:2229 */     this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
/* 1756:2230 */     float var191 = var2.getRainStrength(par1);
/* 1757:2233 */     if (var191 > 0.0F)
/* 1758:     */     {
/* 1759:2235 */       float var11 = 1.0F - var191 * 0.5F;
/* 1760:2236 */       float var201 = 1.0F - var191 * 0.4F;
/* 1761:2237 */       this.fogColorRed *= var11;
/* 1762:2238 */       this.fogColorGreen *= var11;
/* 1763:2239 */       this.fogColorBlue *= var201;
/* 1764:     */     }
/* 1765:2242 */     float var11 = var2.getWeightedThunderStrength(par1);
/* 1766:2244 */     if (var11 > 0.0F)
/* 1767:     */     {
/* 1768:2246 */       float var201 = 1.0F - var11 * 0.5F;
/* 1769:2247 */       this.fogColorRed *= var201;
/* 1770:2248 */       this.fogColorGreen *= var201;
/* 1771:2249 */       this.fogColorBlue *= var201;
/* 1772:     */     }
/* 1773:2252 */     Block var21 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par1);
/* 1774:2256 */     if (this.cloudFog)
/* 1775:     */     {
/* 1776:2258 */       Vec3 fogYFactor = var2.getCloudColour(par1);
/* 1777:2259 */       this.fogColorRed = ((float)fogYFactor.xCoord);
/* 1778:2260 */       this.fogColorGreen = ((float)fogYFactor.yCoord);
/* 1779:2261 */       this.fogColorBlue = ((float)fogYFactor.zCoord);
/* 1780:     */     }
/* 1781:2263 */     else if (var21.getMaterial() == Material.water)
/* 1782:     */     {
/* 1783:2265 */       float var22 = EnchantmentHelper.getRespiration(var3) * 0.2F;
/* 1784:2266 */       this.fogColorRed = (0.02F + var22);
/* 1785:2267 */       this.fogColorGreen = (0.02F + var22);
/* 1786:2268 */       this.fogColorBlue = (0.2F + var22);
/* 1787:2269 */       Vec3 fogYFactor = CustomColorizer.getUnderwaterColor(this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0D, this.mc.renderViewEntity.posZ);
/* 1788:2271 */       if (fogYFactor != null)
/* 1789:     */       {
/* 1790:2273 */         this.fogColorRed = ((float)fogYFactor.xCoord);
/* 1791:2274 */         this.fogColorGreen = ((float)fogYFactor.yCoord);
/* 1792:2275 */         this.fogColorBlue = ((float)fogYFactor.zCoord);
/* 1793:     */       }
/* 1794:     */     }
/* 1795:2278 */     else if (var21.getMaterial() == Material.lava)
/* 1796:     */     {
/* 1797:2280 */       this.fogColorRed = 0.6F;
/* 1798:2281 */       this.fogColorGreen = 0.1F;
/* 1799:2282 */       this.fogColorBlue = 0.0F;
/* 1800:     */     }
/* 1801:2285 */     float var22 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * par1;
/* 1802:2286 */     this.fogColorRed *= var22;
/* 1803:2287 */     this.fogColorGreen *= var22;
/* 1804:2288 */     this.fogColorBlue *= var22;
/* 1805:2289 */     double fogYFactor1 = var2.provider.getVoidFogYFactor();
/* 1806:2291 */     if (!Config.isDepthFog()) {
/* 1807:2293 */       fogYFactor1 = 1.0D;
/* 1808:     */     }
/* 1809:2296 */     double var14 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par1) * fogYFactor1;
/* 1810:2298 */     if (var3.isPotionActive(Potion.blindness))
/* 1811:     */     {
/* 1812:2300 */       int var23 = var3.getActivePotionEffect(Potion.blindness).getDuration();
/* 1813:2302 */       if (var23 < 20) {
/* 1814:2304 */         var14 *= (1.0F - var23 / 20.0F);
/* 1815:     */       } else {
/* 1816:2308 */         var14 = 0.0D;
/* 1817:     */       }
/* 1818:     */     }
/* 1819:2312 */     if (var14 < 1.0D)
/* 1820:     */     {
/* 1821:2314 */       if (var14 < 0.0D) {
/* 1822:2316 */         var14 = 0.0D;
/* 1823:     */       }
/* 1824:2319 */       var14 *= var14;
/* 1825:2320 */       this.fogColorRed = ((float)(this.fogColorRed * var14));
/* 1826:2321 */       this.fogColorGreen = ((float)(this.fogColorGreen * var14));
/* 1827:2322 */       this.fogColorBlue = ((float)(this.fogColorBlue * var14));
/* 1828:     */     }
/* 1829:2327 */     if (this.bossColorModifier > 0.0F)
/* 1830:     */     {
/* 1831:2329 */       float var231 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * par1;
/* 1832:2330 */       this.fogColorRed = (this.fogColorRed * (1.0F - var231) + this.fogColorRed * 0.7F * var231);
/* 1833:2331 */       this.fogColorGreen = (this.fogColorGreen * (1.0F - var231) + this.fogColorGreen * 0.6F * var231);
/* 1834:2332 */       this.fogColorBlue = (this.fogColorBlue * (1.0F - var231) + this.fogColorBlue * 0.6F * var231);
/* 1835:     */     }
/* 1836:2337 */     if (var3.isPotionActive(Potion.nightVision))
/* 1837:     */     {
/* 1838:2339 */       float var231 = getNightVisionBrightness(this.mc.thePlayer, par1);
/* 1839:2340 */       float var17 = 1.0F / this.fogColorRed;
/* 1840:2342 */       if (var17 > 1.0F / this.fogColorGreen) {
/* 1841:2344 */         var17 = 1.0F / this.fogColorGreen;
/* 1842:     */       }
/* 1843:2347 */       if (var17 > 1.0F / this.fogColorBlue) {
/* 1844:2349 */         var17 = 1.0F / this.fogColorBlue;
/* 1845:     */       }
/* 1846:2352 */       this.fogColorRed = (this.fogColorRed * (1.0F - var231) + this.fogColorRed * var17 * var231);
/* 1847:2353 */       this.fogColorGreen = (this.fogColorGreen * (1.0F - var231) + this.fogColorGreen * var17 * var231);
/* 1848:2354 */       this.fogColorBlue = (this.fogColorBlue * (1.0F - var231) + this.fogColorBlue * var17 * var231);
/* 1849:     */     }
/* 1850:2357 */     if (this.mc.gameSettings.anaglyph)
/* 1851:     */     {
/* 1852:2359 */       float var231 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 1853:2360 */       float var17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 1854:2361 */       float var18 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 1855:2362 */       this.fogColorRed = var231;
/* 1856:2363 */       this.fogColorGreen = var17;
/* 1857:2364 */       this.fogColorBlue = var18;
/* 1858:     */     }
/* 1859:2367 */     GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   private void setupFog(int par1, float par2)
/* 1863:     */   {
/* 1864:2376 */     EntityLivingBase var3 = this.mc.renderViewEntity;
/* 1865:2377 */     boolean var4 = false;
/* 1866:2378 */     this.fogStandard = false;
/* 1867:2380 */     if ((var3 instanceof EntityPlayer)) {
/* 1868:2382 */       var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
/* 1869:     */     }
/* 1870:2385 */     if (par1 == 999)
/* 1871:     */     {
/* 1872:2387 */       GL11.glFog(2918, setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 1873:2388 */       GL11.glFogi(2917, 9729);
/* 1874:2389 */       GL11.glFogf(2915, 0.0F);
/* 1875:2390 */       GL11.glFogf(2916, 8.0F);
/* 1876:2392 */       if (GLContext.getCapabilities().GL_NV_fog_distance) {
/* 1877:2394 */         GL11.glFogi(34138, 34139);
/* 1878:     */       }
/* 1879:2397 */       GL11.glFogf(2915, 0.0F);
/* 1880:     */     }
/* 1881:     */     else
/* 1882:     */     {
/* 1883:2401 */       GL11.glFog(2918, setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/* 1884:2402 */       GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 1885:2403 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1886:2404 */       Block var5 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par2);
/* 1887:2407 */       if (var3.isPotionActive(Potion.blindness))
/* 1888:     */       {
/* 1889:2409 */         float var6 = 5.0F;
/* 1890:2410 */         int var10 = var3.getActivePotionEffect(Potion.blindness).getDuration();
/* 1891:2412 */         if (var10 < 20) {
/* 1892:2414 */           var6 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - var10 / 20.0F);
/* 1893:     */         }
/* 1894:2417 */         GL11.glFogi(2917, 9729);
/* 1895:2419 */         if (par1 < 0)
/* 1896:     */         {
/* 1897:2421 */           GL11.glFogf(2915, 0.0F);
/* 1898:2422 */           GL11.glFogf(2916, var6 * 0.8F);
/* 1899:     */         }
/* 1900:     */         else
/* 1901:     */         {
/* 1902:2426 */           GL11.glFogf(2915, var6 * 0.25F);
/* 1903:2427 */           GL11.glFogf(2916, var6);
/* 1904:     */         }
/* 1905:2430 */         if (Config.isFogFancy()) {
/* 1906:2432 */           GL11.glFogi(34138, 34139);
/* 1907:     */         }
/* 1908:     */       }
/* 1909:2435 */       else if (this.cloudFog)
/* 1910:     */       {
/* 1911:2437 */         GL11.glFogi(2917, 2048);
/* 1912:2438 */         GL11.glFogf(2914, 0.1F);
/* 1913:     */       }
/* 1914:2440 */       else if (var5.getMaterial() == Material.water)
/* 1915:     */       {
/* 1916:2442 */         GL11.glFogi(2917, 2048);
/* 1917:2444 */         if (var3.isPotionActive(Potion.waterBreathing)) {
/* 1918:2446 */           GL11.glFogf(2914, 0.05F);
/* 1919:     */         } else {
/* 1920:2450 */           GL11.glFogf(2914, 0.1F - EnchantmentHelper.getRespiration(var3) * 0.03F);
/* 1921:     */         }
/* 1922:2453 */         if (Config.isClearWater()) {
/* 1923:2455 */           GL11.glFogf(2914, 0.02F);
/* 1924:     */         }
/* 1925:     */       }
/* 1926:2458 */       else if (var5.getMaterial() == Material.lava)
/* 1927:     */       {
/* 1928:2460 */         GL11.glFogi(2917, 2048);
/* 1929:2461 */         GL11.glFogf(2914, 2.0F);
/* 1930:     */       }
/* 1931:     */       else
/* 1932:     */       {
/* 1933:2465 */         float var6 = this.farPlaneDistance;
/* 1934:2466 */         this.fogStandard = true;
/* 1935:2468 */         if ((Config.isDepthFog()) && (this.mc.theWorld.provider.getWorldHasVoidParticles()) && (!var4))
/* 1936:     */         {
/* 1937:2470 */           double var101 = ((var3.getBrightnessForRender(par2) & 0xF00000) >> 20) / 16.0D + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * par2 + 4.0D) / 32.0D;
/* 1938:2472 */           if (var101 < 1.0D)
/* 1939:     */           {
/* 1940:2474 */             if (var101 < 0.0D) {
/* 1941:2476 */               var101 = 0.0D;
/* 1942:     */             }
/* 1943:2479 */             var101 *= var101;
/* 1944:2480 */             float var9 = 100.0F * (float)var101;
/* 1945:2482 */             if (var9 < 5.0F) {
/* 1946:2484 */               var9 = 5.0F;
/* 1947:     */             }
/* 1948:2487 */             if (var6 > var9) {
/* 1949:2489 */               var6 = var9;
/* 1950:     */             }
/* 1951:     */           }
/* 1952:     */         }
/* 1953:2494 */         GL11.glFogi(2917, 9729);
/* 1954:2496 */         if (par1 < 0)
/* 1955:     */         {
/* 1956:2498 */           GL11.glFogf(2915, 0.0F);
/* 1957:2499 */           GL11.glFogf(2916, var6);
/* 1958:     */         }
/* 1959:     */         else
/* 1960:     */         {
/* 1961:2503 */           GL11.glFogf(2915, var6 * Config.getFogStart());
/* 1962:2504 */           GL11.glFogf(2916, var6);
/* 1963:     */         }
/* 1964:2507 */         if (GLContext.getCapabilities().GL_NV_fog_distance)
/* 1965:     */         {
/* 1966:2509 */           if (Config.isFogFancy()) {
/* 1967:2511 */             GL11.glFogi(34138, 34139);
/* 1968:     */           }
/* 1969:2514 */           if (Config.isFogFast()) {
/* 1970:2516 */             GL11.glFogi(34138, 34140);
/* 1971:     */           }
/* 1972:     */         }
/* 1973:2520 */         if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ))
/* 1974:     */         {
/* 1975:2522 */           var6 = this.farPlaneDistance;
/* 1976:2523 */           GL11.glFogf(2915, var6 * 0.05F);
/* 1977:2524 */           GL11.glFogf(2916, var6);
/* 1978:     */         }
/* 1979:     */       }
/* 1980:2528 */       GL11.glEnable(2903);
/* 1981:2529 */       GL11.glColorMaterial(1028, 4608);
/* 1982:     */     }
/* 1983:     */   }
/* 1984:     */   
/* 1985:     */   private FloatBuffer setFogColorBuffer(float par1, float par2, float par3, float par4)
/* 1986:     */   {
/* 1987:2538 */     this.fogColorBuffer.clear();
/* 1988:2539 */     this.fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
/* 1989:2540 */     this.fogColorBuffer.flip();
/* 1990:2541 */     return this.fogColorBuffer;
/* 1991:     */   }
/* 1992:     */   
/* 1993:     */   public MapItemRenderer getMapItemRenderer()
/* 1994:     */   {
/* 1995:2546 */     return this.theMapItemRenderer;
/* 1996:     */   }
/* 1997:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.EntityRenderer
 * JD-Core Version:    0.7.0.1
 */