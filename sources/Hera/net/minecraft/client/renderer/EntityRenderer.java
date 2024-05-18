/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import me.eagler.Client;
/*      */ import me.eagler.module.Module;
/*      */ import me.eagler.module.modules.render.Zoom;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import optfine.Config;
/*      */ import optfine.CustomColorizer;
/*      */ import optfine.Lagometer;
/*      */ import optfine.RandomMobs;
/*      */ import optfine.Reflector;
/*      */ import optfine.RenderPlayerOF;
/*      */ import optfine.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import org.lwjgl.util.glu.Project;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*   99 */   private static final Logger logger = LogManager.getLogger();
/*  100 */   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
/*  101 */   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
/*      */   
/*      */   public static boolean anaglyphEnable;
/*      */   
/*      */   public static int anaglyphField;
/*      */   
/*      */   private Minecraft mc;
/*      */   
/*      */   private final IResourceManager resourceManager;
/*  110 */   private Random random = new Random();
/*      */   
/*      */   private float farPlaneDistance;
/*      */   
/*      */   public ItemRenderer itemRenderer;
/*      */   
/*      */   private final MapItemRenderer theMapItemRenderer;
/*      */   
/*      */   private int rendererUpdateCount;
/*      */   private Entity pointedEntity;
/*  120 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  121 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  122 */   private float thirdPersonDistance = 4.0F;
/*      */ 
/*      */   
/*  125 */   private float thirdPersonDistanceTemp = 4.0F;
/*      */ 
/*      */   
/*      */   private float smoothCamYaw;
/*      */ 
/*      */   
/*      */   private float smoothCamPitch;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterX;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterY;
/*      */ 
/*      */   
/*      */   private float smoothCamPartialTicks;
/*      */   
/*      */   private float fovModifierHand;
/*      */   
/*      */   private float fovModifierHandPrev;
/*      */   
/*      */   private float bossColorModifier;
/*      */   
/*      */   private float bossColorModifierPrev;
/*      */   
/*      */   private boolean cloudFog;
/*      */   
/*      */   private boolean renderHand = true;
/*      */   
/*      */   private boolean drawBlockOutline = true;
/*      */   
/*  156 */   private long prevFrameTime = Minecraft.getSystemTime();
/*      */ 
/*      */   
/*      */   private long renderEndNanoTime;
/*      */ 
/*      */   
/*      */   private final DynamicTexture lightmapTexture;
/*      */ 
/*      */   
/*      */   private final int[] lightmapColors;
/*      */ 
/*      */   
/*      */   private final ResourceLocation locationLightMap;
/*      */ 
/*      */   
/*      */   private boolean lightmapUpdateNeeded;
/*      */ 
/*      */   
/*      */   private float torchFlickerX;
/*      */ 
/*      */   
/*      */   private float torchFlickerDX;
/*      */ 
/*      */   
/*      */   private int rainSoundCounter;
/*      */ 
/*      */   
/*  183 */   private float[] rainXCoords = new float[1024];
/*  184 */   private float[] rainYCoords = new float[1024];
/*      */ 
/*      */   
/*  187 */   private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*      */   
/*      */   public float fogColorRed;
/*      */   
/*      */   public float fogColorGreen;
/*      */   
/*      */   public float fogColorBlue;
/*      */   
/*      */   private float fogColor2;
/*      */   private float fogColor1;
/*  197 */   private int debugViewDirection = 0;
/*      */   private boolean debugView = false;
/*  199 */   private double cameraZoom = 1.0D;
/*      */   private double cameraYaw;
/*      */   private double cameraPitch;
/*      */   private ShaderGroup theShaderGroup;
/*  203 */   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
/*  204 */   public static final int shaderCount = shaderResourceLocations.length;
/*      */   private int shaderIndex;
/*      */   private boolean useShader;
/*      */   private int frameCount;
/*      */   private static final String __OBFID = "CL_00000947";
/*      */   private boolean initialized = false;
/*  210 */   private World updatedWorld = null;
/*      */   private boolean showDebugInfo = false;
/*      */   public boolean fogStandard = false;
/*  213 */   private float clipDistance = 128.0F;
/*  214 */   private long lastServerTime = 0L;
/*  215 */   private int lastServerTicks = 0;
/*  216 */   private int serverWaitTime = 0;
/*  217 */   private int serverWaitTimeCurrent = 0;
/*  218 */   private float avgServerTimeDiff = 0.0F;
/*  219 */   private float avgServerTickDiff = 0.0F;
/*  220 */   private long lastErrorCheckTimeMs = 0L;
/*      */ 
/*      */   
/*      */   public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
/*  224 */     this.shaderIndex = shaderCount;
/*  225 */     this.useShader = false;
/*  226 */     this.frameCount = 0;
/*  227 */     this.mc = mcIn;
/*  228 */     this.resourceManager = resourceManagerIn;
/*  229 */     this.itemRenderer = mcIn.getItemRenderer();
/*  230 */     this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
/*  231 */     this.lightmapTexture = new DynamicTexture(16, 16);
/*  232 */     this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
/*  233 */     this.lightmapColors = this.lightmapTexture.getTextureData();
/*  234 */     this.theShaderGroup = null;
/*      */     
/*  236 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  238 */       for (int j = 0; j < 32; j++) {
/*      */         
/*  240 */         float f = (j - 16);
/*  241 */         float f1 = (i - 16);
/*  242 */         float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
/*  243 */         this.rainXCoords[i << 5 | j] = -f1 / f2;
/*  244 */         this.rainYCoords[i << 5 | j] = f / f2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShaderActive() {
/*  251 */     return (OpenGlHelper.shadersSupported && this.theShaderGroup != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181022_b() {
/*  256 */     if (this.theShaderGroup != null)
/*      */     {
/*  258 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  261 */     this.theShaderGroup = null;
/*  262 */     this.shaderIndex = shaderCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchUseShader() {
/*  267 */     this.useShader = !this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadEntityShader(Entity entityIn) {
/*  275 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  277 */       if (this.theShaderGroup != null)
/*      */       {
/*  279 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  282 */       this.theShaderGroup = null;
/*      */       
/*  284 */       if (entityIn instanceof net.minecraft.entity.monster.EntityCreeper) {
/*      */         
/*  286 */         loadShader(new ResourceLocation("shaders/post/creeper.json"));
/*      */       }
/*  288 */       else if (entityIn instanceof net.minecraft.entity.monster.EntitySpider) {
/*      */         
/*  290 */         loadShader(new ResourceLocation("shaders/post/spider.json"));
/*      */       }
/*  292 */       else if (entityIn instanceof net.minecraft.entity.monster.EntityEnderman) {
/*      */         
/*  294 */         loadShader(new ResourceLocation("shaders/post/invert.json"));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void activateNextShader() {
/*  301 */     if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  303 */       if (this.theShaderGroup != null)
/*      */       {
/*  305 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  308 */       this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
/*      */       
/*  310 */       if (this.shaderIndex != shaderCount) {
/*      */         
/*  312 */         loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */       }
/*      */       else {
/*      */         
/*  316 */         this.theShaderGroup = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadShader(ResourceLocation resourceLocationIn) {
/*      */     try {
/*  325 */       this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
/*  326 */       this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  327 */       this.useShader = true;
/*      */     }
/*  329 */     catch (IOException ioexception) {
/*      */       
/*  331 */       logger.warn("Failed to load shader: " + resourceLocationIn, ioexception);
/*  332 */       this.shaderIndex = shaderCount;
/*  333 */       this.useShader = false;
/*      */     }
/*  335 */     catch (JsonSyntaxException jsonsyntaxexception) {
/*      */       
/*  337 */       logger.warn("Failed to load shader: " + resourceLocationIn, (Throwable)jsonsyntaxexception);
/*  338 */       this.shaderIndex = shaderCount;
/*  339 */       this.useShader = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  345 */     if (this.theShaderGroup != null)
/*      */     {
/*  347 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  350 */     this.theShaderGroup = null;
/*      */     
/*  352 */     if (this.shaderIndex != shaderCount) {
/*      */       
/*  354 */       loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */     }
/*      */     else {
/*      */       
/*  358 */       loadEntityShader(this.mc.getRenderViewEntity());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRenderer() {
/*  367 */     if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */     {
/*  369 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */     }
/*      */     
/*  372 */     updateFovModifierHand();
/*  373 */     updateTorchFlicker();
/*  374 */     this.fogColor2 = this.fogColor1;
/*  375 */     this.thirdPersonDistanceTemp = this.thirdPersonDistance;
/*      */     
/*  377 */     if (this.mc.gameSettings.smoothCamera) {
/*      */       
/*  379 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  380 */       float f1 = f * f * f * 8.0F;
/*  381 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
/*  382 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
/*  383 */       this.smoothCamPartialTicks = 0.0F;
/*  384 */       this.smoothCamYaw = 0.0F;
/*  385 */       this.smoothCamPitch = 0.0F;
/*      */     }
/*      */     else {
/*      */       
/*  389 */       this.smoothCamFilterX = 0.0F;
/*  390 */       this.smoothCamFilterY = 0.0F;
/*  391 */       this.mouseFilterXAxis.reset();
/*  392 */       this.mouseFilterYAxis.reset();
/*      */     } 
/*      */     
/*  395 */     if (this.mc.getRenderViewEntity() == null)
/*      */     {
/*  397 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/*  400 */     float f3 = this.mc.theWorld.getLightBrightness(new BlockPos(this.mc.getRenderViewEntity()));
/*  401 */     float f4 = this.mc.gameSettings.renderDistanceChunks / 32.0F;
/*  402 */     float f2 = f3 * (1.0F - f4) + f4;
/*  403 */     this.fogColor1 += (f2 - this.fogColor1) * 0.1F;
/*  404 */     this.rendererUpdateCount++;
/*  405 */     this.itemRenderer.updateEquippedItem();
/*  406 */     addRainParticles();
/*  407 */     this.bossColorModifierPrev = this.bossColorModifier;
/*      */     
/*  409 */     if (BossStatus.hasColorModifier) {
/*      */       
/*  411 */       this.bossColorModifier += 0.05F;
/*      */       
/*  413 */       if (this.bossColorModifier > 1.0F)
/*      */       {
/*  415 */         this.bossColorModifier = 1.0F;
/*      */       }
/*      */       
/*  418 */       BossStatus.hasColorModifier = false;
/*      */     }
/*  420 */     else if (this.bossColorModifier > 0.0F) {
/*      */       
/*  422 */       this.bossColorModifier -= 0.0125F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ShaderGroup getShaderGroup() {
/*  428 */     return this.theShaderGroup;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateShaderGroupSize(int width, int height) {
/*  433 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  435 */       if (this.theShaderGroup != null)
/*      */       {
/*  437 */         this.theShaderGroup.createBindFramebuffers(width, height);
/*      */       }
/*      */       
/*  440 */       this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMouseOver(float partialTicks) {
/*  449 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/*  451 */     if (entity != null && this.mc.theWorld != null) {
/*      */       
/*  453 */       this.mc.mcProfiler.startSection("pick");
/*  454 */       this.mc.pointedEntity = null;
/*  455 */       double d0 = this.mc.playerController.getBlockReachDistance();
/*  456 */       this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
/*  457 */       double d1 = d0;
/*  458 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/*  459 */       boolean flag = false;
/*  460 */       boolean flag1 = true;
/*      */       
/*  462 */       if (this.mc.playerController.extendedReach()) {
/*      */         
/*  464 */         d0 = 6.0D;
/*  465 */         d1 = 6.0D;
/*      */       }
/*      */       else {
/*      */         
/*  469 */         if (d0 > 3.0D)
/*      */         {
/*  471 */           flag = true;
/*      */         }
/*      */         
/*  474 */         d0 = d0;
/*      */       } 
/*      */       
/*  477 */       if (this.mc.objectMouseOver != null)
/*      */       {
/*  479 */         d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
/*      */       }
/*      */       
/*  482 */       Vec3 vec31 = entity.getLook(partialTicks);
/*  483 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
/*  484 */       this.pointedEntity = null;
/*  485 */       Vec3 vec33 = null;
/*  486 */       float f = 1.0F;
/*  487 */       List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, new EntityRenderer1(this)));
/*  488 */       double d2 = d1;
/*      */       
/*  490 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  492 */         Entity entity1 = list.get(i);
/*  493 */         float f1 = entity1.getCollisionBorderSize();
/*  494 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/*  495 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*      */         
/*  497 */         if (axisalignedbb.isVecInside(vec3)) {
/*      */           
/*  499 */           if (d2 >= 0.0D)
/*      */           {
/*  501 */             this.pointedEntity = entity1;
/*  502 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/*  503 */             d2 = 0.0D;
/*      */           }
/*      */         
/*  506 */         } else if (movingobjectposition != null) {
/*      */           
/*  508 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*      */           
/*  510 */           if (d3 < d2 || d2 == 0.0D) {
/*      */             
/*  512 */             boolean flag2 = false;
/*      */             
/*  514 */             if (Reflector.ForgeEntity_canRiderInteract.exists())
/*      */             {
/*  516 */               flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*      */             }
/*      */             
/*  519 */             if (entity1 == entity.ridingEntity && !flag2) {
/*      */               
/*  521 */               if (d2 == 0.0D)
/*      */               {
/*  523 */                 this.pointedEntity = entity1;
/*  524 */                 vec33 = movingobjectposition.hitVec;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/*  529 */               this.pointedEntity = entity1;
/*  530 */               vec33 = movingobjectposition.hitVec;
/*  531 */               d2 = d3;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  537 */       if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0D) {
/*      */         
/*  539 */         this.pointedEntity = null;
/*  540 */         this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
/*      */       } 
/*      */       
/*  543 */       if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
/*      */         
/*  545 */         this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
/*      */         
/*  547 */         if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame)
/*      */         {
/*  549 */           this.mc.pointedEntity = this.pointedEntity;
/*      */         }
/*      */       } 
/*      */       
/*  553 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFovModifierHand() {
/*  562 */     float f = 1.0F;
/*      */     
/*  564 */     if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
/*      */       
/*  566 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
/*  567 */       f = abstractclientplayer.getFovModifier();
/*      */     } 
/*      */     
/*  570 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  571 */     this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
/*      */     
/*  573 */     if (this.fovModifierHand > 1.5F)
/*      */     {
/*  575 */       this.fovModifierHand = 1.5F;
/*      */     }
/*      */     
/*  578 */     if (this.fovModifierHand < 0.1F)
/*      */     {
/*  580 */       this.fovModifierHand = 0.1F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFOVModifier(float partialTicks, boolean p_78481_2_) {
/*  589 */     if (this.debugView)
/*      */     {
/*  591 */       return 90.0F;
/*      */     }
/*      */ 
/*      */     
/*  595 */     Entity entity = this.mc.getRenderViewEntity();
/*  596 */     float f = 70.0F;
/*      */     
/*  598 */     if (p_78481_2_) {
/*      */       
/*  600 */       f = this.mc.gameSettings.fovSetting;
/*  601 */       f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
/*      */     } 
/*      */     
/*  604 */     boolean flag = false;
/*      */     
/*  606 */     if (this.mc.currentScreen == null) {
/*      */       
/*  608 */       GameSettings gamesettings = this.mc.gameSettings;
/*  609 */       flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
/*      */     } 
/*      */     
/*  612 */     if (Client.instance.getModuleManager().getModuleByName("Zoom").isEnabled())
/*      */     {
/*  614 */       if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.Gui)) {
/*      */         
/*  616 */         float multiplier = (float)Client.instance.getSettingManager().getSettingByName("Multiplier").getValue();
/*  617 */         boolean smooth = Client.instance.getSettingManager().getSettingByName("Smooth").getBoolean();
/*  618 */         boolean smoothCam = Client.instance.getSettingManager().getSettingByName("SmoothCam").getBoolean();
/*      */         
/*  620 */         if (flag) {
/*      */           
/*  622 */           if (!Config.zoomMode) {
/*      */             
/*  624 */             Config.zoomMode = true;
/*      */             
/*  626 */             if (smoothCam)
/*      */             {
/*  628 */               this.mc.gameSettings.smoothCamera = true;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  634 */           if (Config.zoomMode)
/*      */           {
/*      */             
/*  637 */             if (smooth) {
/*      */               
/*  639 */               f /= Zoom.smooth;
/*      */             }
/*      */             else {
/*      */               
/*  643 */               f /= multiplier;
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  659 */         if (Config.zoomMode && Zoom.smooth == 1.0F) {
/*      */           
/*  661 */           Config.zoomMode = false;
/*  662 */           this.mc.gameSettings.smoothCamera = false;
/*  663 */           this.mouseFilterXAxis = new MouseFilter();
/*  664 */           this.mouseFilterYAxis = new MouseFilter();
/*  665 */           this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */         } 
/*      */ 
/*      */         
/*  669 */         if (!flag) {
/*      */           
/*  671 */           this.mc.gameSettings.smoothCamera = false;
/*      */           
/*  673 */           if (smooth) {
/*      */             
/*  675 */             f /= Zoom.smooth;
/*      */           }
/*      */           else {
/*      */             
/*  679 */             f /= 1.0F;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  689 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
/*      */       
/*  691 */       float f1 = ((EntityLivingBase)entity).deathTime + partialTicks;
/*  692 */       f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
/*      */     } 
/*      */     
/*  695 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/*  697 */     if (block.getMaterial() == Material.water)
/*      */     {
/*  699 */       f = f * 60.0F / 70.0F;
/*      */     }
/*      */     
/*  702 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void hurtCameraEffect(float partialTicks) {
/*  708 */     if (Client.instance.getModuleManager().getModuleByName("Hurtcam").isEnabled()) {
/*      */       
/*  710 */       if (!Client.instance.getSettingManager().getSettingByName("NoHurtcam").getBoolean())
/*      */       {
/*  712 */         if (this.mc.getRenderViewEntity() instanceof EntityLivingBase)
/*      */         {
/*  714 */           EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  715 */           float f = entitylivingbase.hurtTime - partialTicks;
/*      */           
/*  717 */           if (entitylivingbase.getHealth() <= 0.0F) {
/*      */             
/*  719 */             float f1 = entitylivingbase.deathTime + partialTicks;
/*  720 */             GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */           } 
/*      */           
/*  723 */           if (f < 0.0F) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  728 */           f /= entitylivingbase.maxHurtTime;
/*  729 */           f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  730 */           float f2 = entitylivingbase.attackedAtYaw;
/*  731 */           GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  732 */           GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  733 */           GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */     }
/*  740 */     else if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
/*      */       
/*  742 */       EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  743 */       float f = entitylivingbase.hurtTime - partialTicks;
/*      */       
/*  745 */       if (entitylivingbase.getHealth() <= 0.0F) {
/*      */         
/*  747 */         float f1 = entitylivingbase.deathTime + partialTicks;
/*  748 */         GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */       
/*  751 */       if (f < 0.0F) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  756 */       f /= entitylivingbase.maxHurtTime;
/*  757 */       f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  758 */       float f2 = entitylivingbase.attackedAtYaw;
/*  759 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  760 */       GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  761 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewBobbing(float partialTicks) {
/*  772 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
/*      */     {
/*  774 */       if (Client.instance.getModuleManager().getModuleByName("Bobbing").isEnabled()) {
/*      */         
/*  776 */         float hardness = (float)Client.instance.getSettingManager().getSettingByName("Hardness").getValue();
/*  777 */         boolean nohand = Client.instance.getSettingManager().getSettingByName("NoHand").getBoolean();
/*  778 */         boolean nobob = Client.instance.getSettingManager().getSettingByName("NoBob").getBoolean();
/*      */         
/*  780 */         if (!nobob)
/*      */         {
/*  782 */           EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  783 */           float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  784 */           float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  785 */           float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  786 */           float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*      */           
/*  788 */           if (!nohand) {
/*      */             
/*  790 */             GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*      */           }
/*      */           else {
/*      */             
/*  794 */             GlStateManager.translate(0.0F, -Math.abs(MathHelper.cos(6.2831855F) * f2), 0.0F);
/*      */           } 
/*      */ 
/*      */           
/*  798 */           GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F * hardness, 0.0F, 0.0F, 1.0F);
/*  799 */           GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  800 */           GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         else
/*      */         {
/*  804 */           EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  805 */           float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  806 */           GlStateManager.translate(0.0F, -Math.abs(MathHelper.cos(6.2831855F) * f2), 0.0F);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  812 */         EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  813 */         float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  814 */         float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  815 */         float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  816 */         float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*  817 */         GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*  818 */         GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  819 */         GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  820 */         GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void orientCamera(float partialTicks) {
/*  831 */     Entity entity = this.mc.getRenderViewEntity();
/*  832 */     float f = entity.getEyeHeight();
/*  833 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  834 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  835 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  837 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*      */       
/*  839 */       f = (float)(f + 1.0D);
/*  840 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  842 */       if (!this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  844 */         BlockPos blockpos = new BlockPos(entity);
/*  845 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*  846 */         Block block = iblockstate.getBlock();
/*      */         
/*  848 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*      */           
/*  850 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.theWorld, blockpos, iblockstate, entity });
/*      */         }
/*  852 */         else if (block == Blocks.bed) {
/*      */           
/*  854 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  855 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  858 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/*  859 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*  862 */     } else if (this.mc.gameSettings.thirdPersonView > 0) {
/*      */       
/*  864 */       double d3 = (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);
/*      */       
/*  866 */       if (this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  868 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       }
/*      */       else
/*      */       {
/*  872 */         float f1 = entity.rotationYaw;
/*  873 */         float f2 = entity.rotationPitch;
/*      */         
/*  875 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/*  877 */           f2 += 180.0F;
/*      */         }
/*      */         
/*  880 */         double d4 = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  881 */         double d5 = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  882 */         double d6 = -MathHelper.sin(f2 / 180.0F * 3.1415927F) * d3;
/*      */         
/*  884 */         for (int i = 0; i < 8; i++) {
/*      */           
/*  886 */           float f3 = ((i & 0x1) * 2 - 1);
/*  887 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/*  888 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/*  889 */           f3 *= 0.1F;
/*  890 */           f4 *= 0.1F;
/*  891 */           f5 *= 0.1F;
/*  892 */           MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + f3, d1 + f4, d2 + f5), new Vec3(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */           
/*  894 */           if (movingobjectposition != null) {
/*      */             
/*  896 */             double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
/*      */             
/*  898 */             if (d7 < d3)
/*      */             {
/*  900 */               d3 = d7;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  905 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/*  907 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/*  910 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/*  911 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/*  912 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*  913 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  914 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  919 */       GlStateManager.translate(0.0F, 0.0F, -0.1F);
/*      */     } 
/*      */     
/*  922 */     if (!this.mc.gameSettings.debugCamEnable) {
/*      */       
/*  924 */       GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
/*      */       
/*  926 */       if (entity instanceof EntityAnimal) {
/*      */         
/*  928 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/*  929 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  934 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  938 */     GlStateManager.translate(0.0F, -f, 0.0F);
/*  939 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  940 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  941 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  942 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   private void orientCameraBack(float partialTicks) {
/*  947 */     Entity entity = this.mc.getRenderViewEntity();
/*  948 */     float f = entity.getEyeHeight();
/*  949 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  950 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  951 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  953 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*      */       
/*  955 */       f = (float)(f + 1.0D);
/*  956 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  958 */       if (!this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  960 */         BlockPos blockpos = new BlockPos(entity);
/*  961 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*  962 */         Block block = iblockstate.getBlock();
/*      */         
/*  964 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*      */           
/*  966 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.theWorld, blockpos, iblockstate, entity });
/*      */         }
/*  968 */         else if (block == Blocks.bed) {
/*      */           
/*  970 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  971 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  974 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/*  975 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*  978 */     } else if (this.mc.gameSettings.thirdPersonView > 0) {
/*      */       
/*  980 */       double d3 = (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);
/*      */       
/*  982 */       if (this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  984 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       }
/*      */       else
/*      */       {
/*  988 */         float f1 = entity.rotationYaw;
/*  989 */         float f2 = entity.rotationPitch;
/*      */         
/*  991 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/*  993 */           f2 += 180.0F;
/*      */         }
/*      */         
/*  996 */         double d4 = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  997 */         double d5 = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  998 */         double d6 = -MathHelper.sin(f2 / 180.0F * 3.1415927F) * d3;
/*      */         
/* 1000 */         for (int i = 0; i < 8; i++) {
/*      */           
/* 1002 */           float f3 = ((i & 0x1) * 2 - 1);
/* 1003 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/* 1004 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/* 1005 */           f3 *= 0.1F;
/* 1006 */           f4 *= 0.1F;
/* 1007 */           f5 *= 0.1F;
/* 1008 */           MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + f3, d1 + f4, d2 + f5), new Vec3(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */           
/* 1010 */           if (movingobjectposition != null) {
/*      */             
/* 1012 */             double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
/*      */             
/* 1014 */             if (d7 < d3)
/*      */             {
/* 1016 */               d3 = d7;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1021 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/* 1023 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/* 1026 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/* 1027 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/* 1028 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/* 1029 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 1030 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1035 */       GlStateManager.translate(0.0F, 0.0F, -0.1F);
/*      */     } 
/*      */     
/* 1038 */     if (!this.mc.gameSettings.debugCamEnable) {
/*      */       
/* 1040 */       GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
/*      */       
/* 1042 */       if (entity instanceof EntityAnimal) {
/*      */         
/* 1044 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/* 1045 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1050 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/* 1054 */     GlStateManager.translate(0.0F, -f, 0.0F);
/* 1055 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/* 1056 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/* 1057 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/* 1058 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupCameraTransform(float partialTicks, int pass) {
/* 1066 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 1068 */     if (Config.isFogFancy())
/*      */     {
/* 1070 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/* 1073 */     if (Config.isFogFast())
/*      */     {
/* 1075 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/* 1078 */     GlStateManager.matrixMode(5889);
/* 1079 */     GlStateManager.loadIdentity();
/* 1080 */     float f = 0.07F;
/*      */     
/* 1082 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/* 1084 */       GlStateManager.translate(-(pass * 2 - 1) * f, 0.0F, 0.0F);
/*      */     }
/*      */     
/* 1087 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/* 1089 */     if (this.clipDistance < 173.0F)
/*      */     {
/* 1091 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/* 1094 */     if (this.mc.theWorld.provider.getDimensionId() == 1)
/*      */     {
/* 1096 */       this.clipDistance = 256.0F;
/*      */     }
/*      */     
/* 1099 */     if (this.cameraZoom != 1.0D) {
/*      */       
/* 1101 */       GlStateManager.translate((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/* 1102 */       GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
/*      */     } 
/*      */     
/* 1105 */     Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1106 */     GlStateManager.matrixMode(5888);
/* 1107 */     GlStateManager.loadIdentity();
/*      */     
/* 1109 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/* 1111 */       GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/* 1114 */     hurtCameraEffect(partialTicks);
/*      */     
/* 1116 */     if (this.mc.gameSettings.viewBobbing)
/*      */     {
/* 1118 */       setupViewBobbing(partialTicks);
/*      */     }
/*      */     
/* 1121 */     float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */     
/* 1123 */     if (f1 > 0.0F) {
/*      */       
/* 1125 */       byte b0 = 20;
/*      */       
/* 1127 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion))
/*      */       {
/* 1129 */         b0 = 7;
/*      */       }
/*      */       
/* 1132 */       float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/* 1133 */       f2 *= f2;
/* 1134 */       GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * b0, 0.0F, 1.0F, 1.0F);
/* 1135 */       GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/* 1136 */       GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * b0, 0.0F, 1.0F, 1.0F);
/*      */     } 
/*      */     
/* 1139 */     orientCamera(partialTicks);
/*      */     
/* 1141 */     if (this.debugView)
/*      */     {
/* 1143 */       switch (this.debugViewDirection) {
/*      */         
/*      */         case 0:
/* 1146 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1150 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 2:
/* 1154 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 3:
/* 1158 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 4:
/* 1162 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void setupCameraTransformBack(float partialTicks, int pass) {
/* 1169 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 1171 */     if (Config.isFogFancy())
/*      */     {
/* 1173 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/* 1176 */     if (Config.isFogFast())
/*      */     {
/* 1178 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/* 1181 */     GlStateManager.matrixMode(5889);
/* 1182 */     GlStateManager.loadIdentity();
/* 1183 */     float f = 0.07F;
/*      */     
/* 1185 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/* 1187 */       GlStateManager.translate(-(pass * 2 - 1) * f, 0.0F, 0.0F);
/*      */     }
/*      */     
/* 1190 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/* 1192 */     if (this.clipDistance < 173.0F)
/*      */     {
/* 1194 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/* 1197 */     if (this.mc.theWorld.provider.getDimensionId() == 1)
/*      */     {
/* 1199 */       this.clipDistance = 256.0F;
/*      */     }
/*      */     
/* 1202 */     if (this.cameraZoom != 1.0D) {
/*      */       
/* 1204 */       GlStateManager.translate((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/* 1205 */       GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
/*      */     } 
/*      */     
/* 1208 */     Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1209 */     GlStateManager.matrixMode(5888);
/* 1210 */     GlStateManager.loadIdentity();
/*      */     
/* 1212 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/* 1214 */       GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/* 1217 */     hurtCameraEffect(partialTicks);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1224 */     float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */     
/* 1226 */     if (f1 > 0.0F) {
/*      */       
/* 1228 */       byte b0 = 20;
/*      */       
/* 1230 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion))
/*      */       {
/* 1232 */         b0 = 7;
/*      */       }
/*      */       
/* 1235 */       float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/* 1236 */       f2 *= f2;
/* 1237 */       GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * b0, 0.0F, 1.0F, 1.0F);
/* 1238 */       GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/* 1239 */       GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * b0, 0.0F, 1.0F, 1.0F);
/*      */     } 
/*      */     
/* 1242 */     orientCameraBack(partialTicks);
/*      */     
/* 1244 */     if (this.debugView)
/*      */     {
/* 1246 */       switch (this.debugViewDirection) {
/*      */         
/*      */         case 0:
/* 1249 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1253 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 2:
/* 1257 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 3:
/* 1261 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 4:
/* 1265 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHand(float partialTicks, int xOffset) {
/* 1275 */     if (!this.debugView) {
/*      */       
/* 1277 */       GlStateManager.matrixMode(5889);
/* 1278 */       GlStateManager.loadIdentity();
/* 1279 */       float f = 0.07F;
/*      */       
/* 1281 */       if (this.mc.gameSettings.anaglyph)
/*      */       {
/* 1283 */         GlStateManager.translate(-(xOffset * 2 - 1) * f, 0.0F, 0.0F);
/*      */       }
/*      */       
/* 1286 */       Project.gluPerspective(getFOVModifier(partialTicks, false), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/* 1287 */       GlStateManager.matrixMode(5888);
/* 1288 */       GlStateManager.loadIdentity();
/*      */       
/* 1290 */       if (this.mc.gameSettings.anaglyph)
/*      */       {
/* 1292 */         GlStateManager.translate((xOffset * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */       }
/*      */       
/* 1295 */       GlStateManager.pushMatrix();
/* 1296 */       hurtCameraEffect(partialTicks);
/*      */       
/* 1298 */       if (this.mc.gameSettings.viewBobbing)
/*      */       {
/* 1300 */         setupViewBobbing(partialTicks);
/*      */       }
/*      */       
/* 1303 */       boolean flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping());
/*      */       
/* 1305 */       if (this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
/*      */         
/* 1307 */         enableLightmap();
/* 1308 */         this.itemRenderer.renderItemInFirstPerson(partialTicks);
/* 1309 */         disableLightmap();
/*      */       } 
/*      */       
/* 1312 */       GlStateManager.popMatrix();
/*      */       
/* 1314 */       if (this.mc.gameSettings.thirdPersonView == 0 && !flag) {
/*      */         
/* 1316 */         this.itemRenderer.renderOverlays(partialTicks);
/* 1317 */         hurtCameraEffect(partialTicks);
/*      */       } 
/*      */       
/* 1320 */       if (this.mc.gameSettings.viewBobbing)
/*      */       {
/* 1322 */         setupViewBobbing(partialTicks);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void disableLightmap() {
/* 1329 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1330 */     GlStateManager.disableTexture2D();
/* 1331 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableLightmap() {
/* 1336 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1337 */     GlStateManager.matrixMode(5890);
/* 1338 */     GlStateManager.loadIdentity();
/* 1339 */     float f = 0.00390625F;
/* 1340 */     GlStateManager.scale(f, f, f);
/* 1341 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 1342 */     GlStateManager.matrixMode(5888);
/* 1343 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/* 1344 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 1345 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 1346 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 1347 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 1348 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1349 */     GlStateManager.enableTexture2D();
/* 1350 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTorchFlicker() {
/* 1358 */     this.torchFlickerDX = (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
/* 1359 */     this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9D);
/* 1360 */     this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
/* 1361 */     this.lightmapUpdateNeeded = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateLightmap(float partialTicks) {
/* 1366 */     if (this.lightmapUpdateNeeded) {
/*      */       
/* 1368 */       this.mc.mcProfiler.startSection("lightTex");
/* 1369 */       WorldClient worldclient = this.mc.theWorld;
/*      */       
/* 1371 */       if (worldclient != null) {
/*      */         
/* 1373 */         if (CustomColorizer.updateLightmap((World)worldclient, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
/*      */           
/* 1375 */           this.lightmapTexture.updateDynamicTexture();
/* 1376 */           this.lightmapUpdateNeeded = false;
/* 1377 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           return;
/*      */         } 
/* 1381 */         float f = worldclient.getSunBrightness(1.0F);
/* 1382 */         float f1 = f * 0.95F + 0.05F;
/*      */         
/* 1384 */         for (int i = 0; i < 256; i++) {
/*      */           
/* 1386 */           float f2 = worldclient.provider.getLightBrightnessTable()[i / 16] * f1;
/* 1387 */           float f3 = worldclient.provider.getLightBrightnessTable()[i % 16] * (this.torchFlickerX * 0.1F + 1.5F);
/*      */           
/* 1389 */           if (worldclient.getLastLightningBolt() > 0)
/*      */           {
/* 1391 */             f2 = worldclient.provider.getLightBrightnessTable()[i / 16];
/*      */           }
/*      */           
/* 1394 */           float f4 = f2 * (f * 0.65F + 0.35F);
/* 1395 */           float f5 = f2 * (f * 0.65F + 0.35F);
/* 1396 */           float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
/* 1397 */           float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
/* 1398 */           float f8 = f4 + f3;
/* 1399 */           float f9 = f5 + f6;
/* 1400 */           float f10 = f2 + f7;
/* 1401 */           f8 = f8 * 0.96F + 0.03F;
/* 1402 */           f9 = f9 * 0.96F + 0.03F;
/* 1403 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1405 */           if (this.bossColorModifier > 0.0F) {
/*      */             
/* 1407 */             float f11 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 1408 */             f8 = f8 * (1.0F - f11) + f8 * 0.7F * f11;
/* 1409 */             f9 = f9 * (1.0F - f11) + f9 * 0.6F * f11;
/* 1410 */             f10 = f10 * (1.0F - f11) + f10 * 0.6F * f11;
/*      */           } 
/*      */           
/* 1413 */           if (worldclient.provider.getDimensionId() == 1) {
/*      */             
/* 1415 */             f8 = 0.22F + f3 * 0.75F;
/* 1416 */             f9 = 0.28F + f6 * 0.75F;
/* 1417 */             f10 = 0.25F + f7 * 0.75F;
/*      */           } 
/*      */           
/* 1420 */           if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
/*      */             
/* 1422 */             float f15 = getNightVisionBrightness((EntityLivingBase)this.mc.thePlayer, partialTicks);
/* 1423 */             float f12 = 1.0F / f8;
/*      */             
/* 1425 */             if (f12 > 1.0F / f9)
/*      */             {
/* 1427 */               f12 = 1.0F / f9;
/*      */             }
/*      */             
/* 1430 */             if (f12 > 1.0F / f10)
/*      */             {
/* 1432 */               f12 = 1.0F / f10;
/*      */             }
/*      */             
/* 1435 */             f8 = f8 * (1.0F - f15) + f8 * f12 * f15;
/* 1436 */             f9 = f9 * (1.0F - f15) + f9 * f12 * f15;
/* 1437 */             f10 = f10 * (1.0F - f15) + f10 * f12 * f15;
/*      */           } 
/*      */           
/* 1440 */           if (f8 > 1.0F)
/*      */           {
/* 1442 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1445 */           if (f9 > 1.0F)
/*      */           {
/* 1447 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1450 */           if (f10 > 1.0F)
/*      */           {
/* 1452 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1455 */           float f16 = this.mc.gameSettings.gammaSetting;
/* 1456 */           float f17 = 1.0F - f8;
/* 1457 */           float f13 = 1.0F - f9;
/* 1458 */           float f14 = 1.0F - f10;
/* 1459 */           f17 = 1.0F - f17 * f17 * f17 * f17;
/* 1460 */           f13 = 1.0F - f13 * f13 * f13 * f13;
/* 1461 */           f14 = 1.0F - f14 * f14 * f14 * f14;
/* 1462 */           f8 = f8 * (1.0F - f16) + f17 * f16;
/* 1463 */           f9 = f9 * (1.0F - f16) + f13 * f16;
/* 1464 */           f10 = f10 * (1.0F - f16) + f14 * f16;
/* 1465 */           f8 = f8 * 0.96F + 0.03F;
/* 1466 */           f9 = f9 * 0.96F + 0.03F;
/* 1467 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1469 */           if (f8 > 1.0F)
/*      */           {
/* 1471 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1474 */           if (f9 > 1.0F)
/*      */           {
/* 1476 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1479 */           if (f10 > 1.0F)
/*      */           {
/* 1481 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1484 */           if (f8 < 0.0F)
/*      */           {
/* 1486 */             f8 = 0.0F;
/*      */           }
/*      */           
/* 1489 */           if (f9 < 0.0F)
/*      */           {
/* 1491 */             f9 = 0.0F;
/*      */           }
/*      */           
/* 1494 */           if (f10 < 0.0F)
/*      */           {
/* 1496 */             f10 = 0.0F;
/*      */           }
/*      */           
/* 1499 */           short short1 = 255;
/* 1500 */           int j = (int)(f8 * 255.0F);
/* 1501 */           int k = (int)(f9 * 255.0F);
/* 1502 */           int l = (int)(f10 * 255.0F);
/* 1503 */           this.lightmapColors[i] = short1 << 24 | j << 16 | k << 8 | l;
/*      */         } 
/*      */         
/* 1506 */         this.lightmapTexture.updateDynamicTexture();
/* 1507 */         this.lightmapUpdateNeeded = false;
/* 1508 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 1515 */     int i = entitylivingbaseIn.getActivePotionEffect(Potion.nightVision).getDuration();
/* 1516 */     return (i > 200) ? 1.0F : (0.7F + MathHelper.sin((i - partialTicks) * 3.1415927F * 0.2F) * 0.3F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181560_a(float p_181560_1_, long p_181560_2_) {
/* 1521 */     frameInit();
/* 1522 */     boolean flag = Display.isActive();
/*      */     
/* 1524 */     if (!flag && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
/*      */       
/* 1526 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L)
/*      */       {
/* 1528 */         this.mc.displayInGameMenu();
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1533 */       this.prevFrameTime = Minecraft.getSystemTime();
/*      */     } 
/*      */     
/* 1536 */     this.mc.mcProfiler.startSection("mouse");
/*      */     
/* 1538 */     if (flag && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
/*      */       
/* 1540 */       Mouse.setGrabbed(false);
/* 1541 */       Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 1542 */       Mouse.setGrabbed(true);
/*      */     } 
/*      */     
/* 1545 */     if (this.mc.inGameHasFocus && flag) {
/*      */       
/* 1547 */       this.mc.mouseHelper.mouseXYChange();
/* 1548 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 1549 */       float f1 = f * f * f * 8.0F;
/* 1550 */       float f2 = this.mc.mouseHelper.deltaX * f1;
/* 1551 */       float f3 = this.mc.mouseHelper.deltaY * f1;
/* 1552 */       byte b0 = 1;
/*      */       
/* 1554 */       if (this.mc.gameSettings.invertMouse)
/*      */       {
/* 1556 */         b0 = -1;
/*      */       }
/*      */       
/* 1559 */       if (this.mc.gameSettings.smoothCamera) {
/*      */         
/* 1561 */         this.smoothCamYaw += f2;
/* 1562 */         this.smoothCamPitch += f3;
/* 1563 */         float f4 = p_181560_1_ - this.smoothCamPartialTicks;
/* 1564 */         this.smoothCamPartialTicks = p_181560_1_;
/* 1565 */         f2 = this.smoothCamFilterX * f4;
/* 1566 */         f3 = this.smoothCamFilterY * f4;
/* 1567 */         this.mc.thePlayer.setAngles(f2, f3 * b0);
/*      */       }
/*      */       else {
/*      */         
/* 1571 */         this.smoothCamYaw = 0.0F;
/* 1572 */         this.smoothCamPitch = 0.0F;
/* 1573 */         this.mc.thePlayer.setAngles(f2, f3 * b0);
/*      */       } 
/*      */     } 
/*      */     
/* 1577 */     this.mc.mcProfiler.endSection();
/*      */     
/* 1579 */     if (!this.mc.skipRenderWorld) {
/*      */       
/* 1581 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/* 1582 */       final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1583 */       int l = scaledresolution.getScaledWidth();
/* 1584 */       int i1 = scaledresolution.getScaledHeight();
/* 1585 */       final int j1 = Mouse.getX() * l / this.mc.displayWidth;
/* 1586 */       final int k1 = i1 - Mouse.getY() * i1 / this.mc.displayHeight - 1;
/* 1587 */       int l1 = this.mc.gameSettings.limitFramerate;
/*      */       
/* 1589 */       if (this.mc.theWorld != null) {
/*      */         
/* 1591 */         this.mc.mcProfiler.startSection("level");
/* 1592 */         int i = Math.min(Minecraft.getDebugFPS(), l1);
/* 1593 */         i = Math.max(i, 60);
/* 1594 */         long j = System.nanoTime() - p_181560_2_;
/* 1595 */         long k = Math.max((1000000000 / i / 4) - j, 0L);
/* 1596 */         renderWorld(p_181560_1_, System.nanoTime() + k);
/*      */         
/* 1598 */         if (OpenGlHelper.shadersSupported) {
/*      */           
/* 1600 */           this.mc.renderGlobal.renderEntityOutlineFramebuffer();
/*      */           
/* 1602 */           if (this.theShaderGroup != null && this.useShader) {
/*      */             
/* 1604 */             GlStateManager.matrixMode(5890);
/* 1605 */             GlStateManager.pushMatrix();
/* 1606 */             GlStateManager.loadIdentity();
/* 1607 */             this.theShaderGroup.loadShaderGroup(p_181560_1_);
/* 1608 */             GlStateManager.popMatrix();
/*      */           } 
/*      */           
/* 1611 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*      */         } 
/*      */         
/* 1614 */         this.renderEndNanoTime = System.nanoTime();
/* 1615 */         this.mc.mcProfiler.endStartSection("gui");
/*      */         
/* 1617 */         if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
/*      */           
/* 1619 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1620 */           this.mc.ingameGUI.renderGameOverlay(p_181560_1_);
/*      */           
/* 1622 */           if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo)
/*      */           {
/* 1624 */             Config.drawFps();
/*      */           }
/*      */           
/* 1627 */           if (this.mc.gameSettings.showDebugInfo)
/*      */           {
/* 1629 */             Lagometer.showLagometer(scaledresolution);
/*      */           }
/*      */         } 
/*      */         
/* 1633 */         this.mc.mcProfiler.endSection();
/*      */       }
/*      */       else {
/*      */         
/* 1637 */         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1638 */         GlStateManager.matrixMode(5889);
/* 1639 */         GlStateManager.loadIdentity();
/* 1640 */         GlStateManager.matrixMode(5888);
/* 1641 */         GlStateManager.loadIdentity();
/* 1642 */         setupOverlayRendering();
/* 1643 */         this.renderEndNanoTime = System.nanoTime();
/*      */       } 
/*      */       
/* 1646 */       if (this.mc.currentScreen != null) {
/*      */         
/* 1648 */         GlStateManager.clear(256);
/*      */ 
/*      */         
/*      */         try {
/* 1652 */           if (Reflector.ForgeHooksClient_drawScreen.exists())
/*      */           {
/* 1654 */             Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(j1), Integer.valueOf(k1), Float.valueOf(p_181560_1_) });
/*      */           }
/*      */           else
/*      */           {
/* 1658 */             this.mc.currentScreen.drawScreen(j1, k1, p_181560_1_);
/*      */           }
/*      */         
/* 1661 */         } catch (Throwable throwable) {
/*      */           
/* 1663 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
/* 1664 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
/* 1665 */           crashreportcategory.addCrashSectionCallable("Screen name", new EntityRenderer2(this));
/* 1666 */           crashreportcategory.addCrashSectionCallable("Mouse location", new Callable()
/*      */               {
/*      */                 private static final String __OBFID = "CL_00000950";
/*      */                 
/*      */                 public String call() throws Exception {
/* 1671 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(this.val$j1), Integer.valueOf(this.val$k1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
/*      */                 }
/*      */               });
/* 1674 */           crashreportcategory.addCrashSectionCallable("Screen size", new Callable()
/*      */               {
/*      */                 private static final String __OBFID = "CL_00000951";
/*      */                 
/*      */                 public String call() throws Exception {
/* 1679 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(this.val$scaledresolution.getScaledWidth()), Integer.valueOf(this.val$scaledresolution.getScaledHeight()), Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayWidth), Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayHeight), Integer.valueOf(this.val$scaledresolution.getScaleFactor()) });
/*      */                 }
/*      */               });
/* 1682 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1687 */     frameFinish();
/* 1688 */     waitForServerThread();
/* 1689 */     Lagometer.updateLagometer();
/*      */     
/* 1691 */     if (this.mc.gameSettings.ofProfiler)
/*      */     {
/* 1693 */       this.mc.gameSettings.showDebugProfilerChart = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderStreamIndicator(float partialTicks) {
/* 1699 */     setupOverlayRendering();
/* 1700 */     this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isDrawBlockOutline() {
/* 1705 */     if (!this.drawBlockOutline)
/*      */     {
/* 1707 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1711 */     Entity entity = this.mc.getRenderViewEntity();
/* 1712 */     boolean flag = (entity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI);
/*      */     
/* 1714 */     if (flag && !((EntityPlayer)entity).capabilities.allowEdit) {
/*      */       
/* 1716 */       ItemStack itemstack = ((EntityPlayer)entity).getCurrentEquippedItem();
/*      */       
/* 1718 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 1720 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1721 */         Block block = this.mc.theWorld.getBlockState(blockpos).getBlock();
/*      */         
/* 1723 */         if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
/*      */           boolean flag1;
/*      */ 
/*      */           
/* 1727 */           if (Reflector.ForgeBlock_hasTileEntity.exists()) {
/*      */             
/* 1729 */             IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/* 1730 */             flag1 = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { iblockstate });
/*      */           }
/*      */           else {
/*      */             
/* 1734 */             flag1 = block.hasTileEntity();
/*      */           } 
/*      */           
/* 1737 */           flag = (flag1 && this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory);
/*      */         }
/*      */         else {
/*      */           
/* 1741 */           flag = (itemstack != null && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block)));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1746 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderWorldDirections(float partialTicks) {
/* 1752 */     if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
/*      */       
/* 1754 */       Entity entity = this.mc.getRenderViewEntity();
/* 1755 */       GlStateManager.enableBlend();
/* 1756 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1757 */       GL11.glLineWidth(1.0F);
/* 1758 */       GlStateManager.disableTexture2D();
/* 1759 */       GlStateManager.depthMask(false);
/* 1760 */       GlStateManager.pushMatrix();
/* 1761 */       GlStateManager.matrixMode(5888);
/* 1762 */       GlStateManager.loadIdentity();
/* 1763 */       orientCamera(partialTicks);
/* 1764 */       GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
/* 1765 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), 255, 0, 0, 255);
/* 1766 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), 0, 0, 255, 255);
/* 1767 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), 0, 255, 0, 255);
/* 1768 */       GlStateManager.popMatrix();
/* 1769 */       GlStateManager.depthMask(true);
/* 1770 */       GlStateManager.enableTexture2D();
/* 1771 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderWorld(float partialTicks, long finishTimeNano) {
/* 1777 */     updateLightmap(partialTicks);
/*      */     
/* 1779 */     if (this.mc.getRenderViewEntity() == null)
/*      */     {
/* 1781 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/* 1784 */     getMouseOver(partialTicks);
/* 1785 */     GlStateManager.enableDepth();
/* 1786 */     GlStateManager.enableAlpha();
/* 1787 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1788 */     this.mc.mcProfiler.startSection("center");
/*      */ 
/*      */ 
/*      */     
/* 1792 */     if (this.mc.gameSettings.anaglyph) {
/*      */       
/* 1794 */       anaglyphField = 0;
/* 1795 */       GlStateManager.colorMask(false, true, true, false);
/* 1796 */       renderWorldPass(0, partialTicks, finishTimeNano);
/*      */       
/* 1798 */       anaglyphField = 1;
/* 1799 */       GlStateManager.colorMask(true, false, false, false);
/* 1800 */       renderWorldPass(1, partialTicks, finishTimeNano);
/*      */       
/* 1802 */       GlStateManager.colorMask(true, true, true, false);
/*      */     }
/*      */     else {
/*      */       
/* 1806 */       renderWorldPass(2, partialTicks, finishTimeNano);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1813 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
/* 1818 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 1819 */     EffectRenderer effectrenderer = this.mc.effectRenderer;
/* 1820 */     boolean flag = isDrawBlockOutline();
/* 1821 */     GlStateManager.enableCull();
/* 1822 */     this.mc.mcProfiler.endStartSection("clear");
/*      */     
/* 1824 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1825 */     updateFogColor(partialTicks);
/* 1826 */     GlStateManager.clear(16640);
/* 1827 */     this.mc.mcProfiler.endStartSection("camera");
/* 1828 */     setupCameraTransform(partialTicks, pass);
/* 1829 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.thePlayer, (this.mc.gameSettings.thirdPersonView == 2));
/* 1830 */     this.mc.mcProfiler.endStartSection("frustum");
/* 1831 */     ClippingHelperImpl.getInstance();
/* 1832 */     this.mc.mcProfiler.endStartSection("culling");
/* 1833 */     Frustum frustum = new Frustum();
/* 1834 */     Entity entity = this.mc.getRenderViewEntity();
/* 1835 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1836 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1837 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 1838 */     frustum.setPosition(d0, d1, d2);
/*      */     
/* 1840 */     if (!Config.isSkyEnabled() && !Config.isSunMoonEnabled() && !Config.isStarsEnabled()) {
/*      */       
/* 1842 */       GlStateManager.disableBlend();
/*      */     }
/*      */     else {
/*      */       
/* 1846 */       setupFog(-1, partialTicks);
/* 1847 */       this.mc.mcProfiler.endStartSection("sky");
/* 1848 */       GlStateManager.matrixMode(5889);
/* 1849 */       GlStateManager.loadIdentity();
/* 1850 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1851 */       GlStateManager.matrixMode(5888);
/* 1852 */       renderglobal.renderSky(partialTicks, pass);
/* 1853 */       GlStateManager.matrixMode(5889);
/* 1854 */       GlStateManager.loadIdentity();
/* 1855 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1856 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */     
/* 1859 */     setupFog(0, partialTicks);
/* 1860 */     GlStateManager.shadeModel(7425);
/*      */     
/* 1862 */     if (entity.posY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F))
/*      */     {
/* 1864 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     }
/*      */     
/* 1867 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1868 */     setupFog(0, partialTicks);
/* 1869 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1870 */     RenderHelper.disableStandardItemLighting();
/* 1871 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 1872 */     renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     
/* 1874 */     if (pass == 0 || pass == 2) {
/*      */       
/* 1876 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 1877 */       Lagometer.timerChunkUpload.start();
/* 1878 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 1879 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 1882 */     this.mc.mcProfiler.endStartSection("terrain");
/* 1883 */     Lagometer.timerTerrain.start();
/*      */     
/* 1885 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0)
/*      */     {
/* 1887 */       GL11.glFinish();
/*      */     }
/*      */     
/* 1890 */     GlStateManager.matrixMode(5888);
/* 1891 */     GlStateManager.pushMatrix();
/* 1892 */     GlStateManager.disableAlpha();
/* 1893 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, pass, entity);
/* 1894 */     GlStateManager.enableAlpha();
/* 1895 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 1896 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 1897 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, entity);
/* 1898 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1899 */     Lagometer.timerTerrain.end();
/* 1900 */     GlStateManager.shadeModel(7424);
/* 1901 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 1903 */     if (!this.debugView) {
/*      */       
/* 1905 */       GlStateManager.matrixMode(5888);
/* 1906 */       GlStateManager.popMatrix();
/* 1907 */       GlStateManager.pushMatrix();
/* 1908 */       RenderHelper.enableStandardItemLighting();
/* 1909 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 1911 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 1913 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 1916 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 1918 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 1920 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 1923 */       RenderHelper.disableStandardItemLighting();
/* 1924 */       disableLightmap();
/* 1925 */       GlStateManager.matrixMode(5888);
/* 1926 */       GlStateManager.popMatrix();
/* 1927 */       GlStateManager.pushMatrix();
/*      */       
/* 1929 */       if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag) {
/*      */         
/* 1931 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/* 1932 */         GlStateManager.disableAlpha();
/* 1933 */         this.mc.mcProfiler.endStartSection("outline");
/* 1934 */         boolean flag1 = Reflector.ForgeHooksClient_onDrawBlockHighlight.exists();
/*      */         
/* 1936 */         if ((!flag1 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI)
/*      */         {
/* 1938 */           renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/*      */         }
/* 1940 */         GlStateManager.enableAlpha();
/*      */       } 
/*      */     } 
/*      */     
/* 1944 */     GlStateManager.matrixMode(5888);
/* 1945 */     GlStateManager.popMatrix();
/*      */     
/* 1947 */     if (flag && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
/*      */       
/* 1949 */       EntityPlayer entityplayer1 = (EntityPlayer)entity;
/* 1950 */       GlStateManager.disableAlpha();
/* 1951 */       this.mc.mcProfiler.endStartSection("outline");
/* 1952 */       boolean flag2 = Reflector.ForgeHooksClient_onDrawBlockHighlight.exists();
/*      */       
/* 1954 */       if ((!flag2 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer1, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer1.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI)
/*      */       {
/* 1956 */         renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/* 1958 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 1961 */     this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1962 */     GlStateManager.enableBlend();
/* 1963 */     GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1964 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 1965 */     renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
/* 1966 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1967 */     GlStateManager.disableBlend();
/*      */     
/* 1969 */     if (!this.debugView) {
/*      */       
/* 1971 */       enableLightmap();
/* 1972 */       this.mc.mcProfiler.endStartSection("litParticles");
/* 1973 */       effectrenderer.renderLitParticles(entity, partialTicks);
/* 1974 */       RenderHelper.disableStandardItemLighting();
/* 1975 */       setupFog(0, partialTicks);
/* 1976 */       this.mc.mcProfiler.endStartSection("particles");
/* 1977 */       effectrenderer.renderParticles(entity, partialTicks);
/* 1978 */       disableLightmap();
/*      */     } 
/*      */     
/* 1981 */     GlStateManager.depthMask(false);
/* 1982 */     GlStateManager.enableCull();
/* 1983 */     this.mc.mcProfiler.endStartSection("weather");
/* 1984 */     renderRainSnow(partialTicks);
/* 1985 */     GlStateManager.depthMask(true);
/* 1986 */     renderglobal.renderWorldBorder(entity, partialTicks);
/* 1987 */     GlStateManager.disableBlend();
/* 1988 */     GlStateManager.enableCull();
/* 1989 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1990 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1991 */     setupFog(0, partialTicks);
/* 1992 */     GlStateManager.enableBlend();
/* 1993 */     GlStateManager.depthMask(false);
/* 1994 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1995 */     GlStateManager.shadeModel(7425);
/* 1996 */     this.mc.mcProfiler.endStartSection("translucent");
/* 1997 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 1999 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/*      */       
/* 2001 */       RenderHelper.enableStandardItemLighting();
/* 2002 */       this.mc.mcProfiler.endStartSection("entities");
/* 2003 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 2004 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 2005 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 2006 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 2009 */     GlStateManager.shadeModel(7424);
/* 2010 */     GlStateManager.depthMask(true);
/* 2011 */     GlStateManager.enableCull();
/* 2012 */     GlStateManager.disableBlend();
/* 2013 */     GlStateManager.disableFog();
/*      */     
/* 2015 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/*      */       
/* 2017 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 2018 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     } 
/*      */     
/* 2021 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/*      */       
/* 2023 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 2024 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 2027 */     this.mc.mcProfiler.endStartSection("hand");
/* 2028 */     boolean flag3 = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { this.mc.renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*      */ 
/*      */ 
/*      */     
/* 2032 */     for (Module module : Client.instance.getModuleManager().getModules()) {
/*      */       
/* 2034 */       if (module.isEnabled())
/*      */       {
/* 2036 */         module.onRender();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2042 */     if (!flag3 && this.renderHand) {
/*      */       
/* 2044 */       GlStateManager.clear(256);
/* 2045 */       renderHand(partialTicks, pass);
/* 2046 */       renderWorldDirections(partialTicks);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderWorldPassBack(int pass, float partialTicks, long finishTimeNano) {
/* 2052 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 2053 */     EffectRenderer effectrenderer = this.mc.effectRenderer;
/* 2054 */     boolean flag = isDrawBlockOutline();
/* 2055 */     GlStateManager.enableCull();
/* 2056 */     this.mc.mcProfiler.endStartSection("clear");
/* 2057 */     GlStateManager.viewport(0, 0, this.mc.displayWidth / 4, this.mc.displayHeight / 4);
/*      */     
/* 2059 */     updateFogColor(partialTicks);
/*      */     
/* 2061 */     this.mc.mcProfiler.endStartSection("camera");
/*      */ 
/*      */ 
/*      */     
/* 2065 */     setupCameraTransformBack(partialTicks, pass);
/* 2066 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.thePlayer, (this.mc.gameSettings.thirdPersonView == 2));
/* 2067 */     this.mc.mcProfiler.endStartSection("frustum");
/* 2068 */     ClippingHelperImpl.getInstance();
/* 2069 */     this.mc.mcProfiler.endStartSection("culling");
/* 2070 */     Frustum frustum = new Frustum();
/* 2071 */     Entity entity = this.mc.getRenderViewEntity();
/* 2072 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 2073 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 2074 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 2075 */     frustum.setPosition(d0, d1, d2);
/*      */     
/* 2077 */     if (!Config.isSkyEnabled() && !Config.isSunMoonEnabled() && !Config.isStarsEnabled()) {
/*      */       
/* 2079 */       GlStateManager.disableBlend();
/*      */     }
/*      */     else {
/*      */       
/* 2083 */       setupFog(-1, partialTicks);
/* 2084 */       this.mc.mcProfiler.endStartSection("sky");
/* 2085 */       GlStateManager.matrixMode(5889);
/* 2086 */       GlStateManager.loadIdentity();
/* 2087 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / 4.0F / this.mc.displayHeight / 4.0F, 0.05F, this.clipDistance);
/* 2088 */       GlStateManager.matrixMode(5888);
/* 2089 */       renderglobal.renderSky(partialTicks, pass);
/* 2090 */       GlStateManager.matrixMode(5889);
/* 2091 */       GlStateManager.loadIdentity();
/* 2092 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / 4.0F / this.mc.displayHeight / 4.0F, 0.05F, this.clipDistance);
/* 2093 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */     
/* 2096 */     setupFog(0, partialTicks);
/* 2097 */     GlStateManager.shadeModel(7425);
/*      */     
/* 2099 */     entity.getEyeHeight();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2104 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 2105 */     setupFog(0, partialTicks);
/* 2106 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 2107 */     RenderHelper.disableStandardItemLighting();
/* 2108 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 2109 */     renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     
/* 2111 */     if (pass == 0 || pass == 2) {
/*      */       
/* 2113 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 2114 */       Lagometer.timerChunkUpload.start();
/* 2115 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 2116 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 2119 */     this.mc.mcProfiler.endStartSection("terrain");
/* 2120 */     Lagometer.timerTerrain.start();
/*      */     
/* 2122 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0)
/*      */     {
/* 2124 */       GL11.glFinish();
/*      */     }
/*      */     
/* 2127 */     GlStateManager.matrixMode(5888);
/* 2128 */     GlStateManager.pushMatrix();
/* 2129 */     GlStateManager.disableAlpha();
/* 2130 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, pass, entity);
/* 2131 */     GlStateManager.enableAlpha();
/* 2132 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 2133 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 2134 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, entity);
/* 2135 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 2136 */     Lagometer.timerTerrain.end();
/* 2137 */     GlStateManager.shadeModel(7424);
/* 2138 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 2140 */     if (!this.debugView) {
/*      */       
/* 2142 */       GlStateManager.matrixMode(5888);
/* 2143 */       GlStateManager.popMatrix();
/* 2144 */       GlStateManager.pushMatrix();
/* 2145 */       RenderHelper.enableStandardItemLighting();
/* 2146 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 2148 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 2150 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 2153 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 2155 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 2157 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 2160 */       RenderHelper.disableStandardItemLighting();
/* 2161 */       disableLightmap();
/* 2162 */       GlStateManager.matrixMode(5888);
/* 2163 */       GlStateManager.popMatrix();
/* 2164 */       GlStateManager.pushMatrix();
/*      */       
/* 2166 */       if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag) {
/*      */         
/* 2168 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/* 2169 */         GlStateManager.disableAlpha();
/* 2170 */         this.mc.mcProfiler.endStartSection("outline");
/* 2171 */         boolean flag1 = Reflector.ForgeHooksClient_onDrawBlockHighlight.exists();
/*      */         
/* 2173 */         if ((!flag1 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI)
/*      */         {
/* 2175 */           renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/*      */         }
/* 2177 */         GlStateManager.enableAlpha();
/*      */       } 
/*      */     } 
/*      */     
/* 2181 */     GlStateManager.matrixMode(5888);
/* 2182 */     GlStateManager.popMatrix();
/*      */     
/* 2184 */     if (flag && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
/*      */       
/* 2186 */       EntityPlayer entityplayer1 = (EntityPlayer)entity;
/* 2187 */       GlStateManager.disableAlpha();
/* 2188 */       this.mc.mcProfiler.endStartSection("outline");
/* 2189 */       boolean flag2 = Reflector.ForgeHooksClient_onDrawBlockHighlight.exists();
/*      */       
/* 2191 */       if ((!flag2 || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer1, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer1.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI)
/*      */       {
/* 2193 */         renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/* 2195 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 2198 */     this.mc.mcProfiler.endStartSection("destroyProgress");
/* 2199 */     GlStateManager.enableBlend();
/* 2200 */     GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 2201 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 2202 */     renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
/* 2203 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 2204 */     GlStateManager.disableBlend();
/*      */     
/* 2206 */     if (!this.debugView) {
/*      */       
/* 2208 */       enableLightmap();
/* 2209 */       this.mc.mcProfiler.endStartSection("litParticles");
/* 2210 */       effectrenderer.renderLitParticles(entity, partialTicks);
/* 2211 */       RenderHelper.disableStandardItemLighting();
/* 2212 */       setupFog(0, partialTicks);
/* 2213 */       this.mc.mcProfiler.endStartSection("particles");
/* 2214 */       effectrenderer.renderParticles(entity, partialTicks);
/* 2215 */       disableLightmap();
/*      */     } 
/*      */     
/* 2218 */     GlStateManager.depthMask(false);
/* 2219 */     GlStateManager.enableCull();
/* 2220 */     this.mc.mcProfiler.endStartSection("weather");
/* 2221 */     renderRainSnow(partialTicks);
/* 2222 */     GlStateManager.depthMask(true);
/* 2223 */     renderglobal.renderWorldBorder(entity, partialTicks);
/* 2224 */     GlStateManager.disableBlend();
/* 2225 */     GlStateManager.enableCull();
/* 2226 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2227 */     GlStateManager.alphaFunc(516, 0.1F);
/* 2228 */     setupFog(0, partialTicks);
/* 2229 */     GlStateManager.enableBlend();
/* 2230 */     GlStateManager.depthMask(false);
/* 2231 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 2232 */     GlStateManager.shadeModel(7425);
/* 2233 */     this.mc.mcProfiler.endStartSection("translucent");
/* 2234 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 2236 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/*      */       
/* 2238 */       RenderHelper.enableStandardItemLighting();
/* 2239 */       this.mc.mcProfiler.endStartSection("entities");
/* 2240 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 2241 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 2242 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 2243 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 2246 */     GlStateManager.shadeModel(7424);
/* 2247 */     GlStateManager.depthMask(true);
/* 2248 */     GlStateManager.enableCull();
/* 2249 */     GlStateManager.disableBlend();
/* 2250 */     GlStateManager.disableFog();
/*      */     
/* 2252 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/*      */       
/* 2254 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 2255 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     } 
/*      */     
/* 2258 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/*      */       
/* 2260 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 2261 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 2264 */     this.mc.mcProfiler.endStartSection("hand");
/* 2265 */     boolean flag3 = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { this.mc.renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*      */ 
/*      */ 
/*      */     
/* 2269 */     for (Module module : Client.instance.getModuleManager().getModules()) {
/*      */       
/* 2271 */       if (module.isEnabled())
/*      */       {
/* 2273 */         module.onRender();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2281 */     if (!flag3 && this.renderHand) {
/*      */       
/* 2283 */       GlStateManager.clear(256);
/*      */       
/* 2285 */       renderWorldDirections(partialTicks);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass) {
/* 2291 */     if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff()) {
/*      */       
/* 2293 */       this.mc.mcProfiler.endStartSection("clouds");
/* 2294 */       GlStateManager.matrixMode(5889);
/* 2295 */       GlStateManager.loadIdentity();
/* 2296 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
/* 2297 */       GlStateManager.matrixMode(5888);
/* 2298 */       GlStateManager.pushMatrix();
/* 2299 */       setupFog(0, partialTicks);
/* 2300 */       renderGlobalIn.renderClouds(partialTicks, pass);
/* 2301 */       GlStateManager.disableFog();
/* 2302 */       GlStateManager.popMatrix();
/* 2303 */       GlStateManager.matrixMode(5889);
/* 2304 */       GlStateManager.loadIdentity();
/* 2305 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 2306 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addRainParticles() {
/* 2312 */     float f = this.mc.theWorld.getRainStrength(1.0F);
/*      */     
/* 2314 */     if (!Config.isRainFancy())
/*      */     {
/* 2316 */       f /= 2.0F;
/*      */     }
/*      */     
/* 2319 */     if (f != 0.0F && Config.isRainSplash()) {
/*      */       
/* 2321 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 2322 */       Entity entity = this.mc.getRenderViewEntity();
/* 2323 */       WorldClient worldclient = this.mc.theWorld;
/* 2324 */       BlockPos blockpos = new BlockPos(entity);
/* 2325 */       byte b0 = 10;
/* 2326 */       double d0 = 0.0D;
/* 2327 */       double d1 = 0.0D;
/* 2328 */       double d2 = 0.0D;
/* 2329 */       int i = 0;
/* 2330 */       int j = (int)(100.0F * f * f);
/*      */       
/* 2332 */       if (this.mc.gameSettings.particleSetting == 1) {
/*      */         
/* 2334 */         j >>= 1;
/*      */       }
/* 2336 */       else if (this.mc.gameSettings.particleSetting == 2) {
/*      */         
/* 2338 */         j = 0;
/*      */       } 
/*      */       
/* 2341 */       for (int k = 0; k < j; k++) {
/*      */         
/* 2343 */         BlockPos blockpos1 = worldclient.getPrecipitationHeight(blockpos.add(this.random.nextInt(b0) - this.random.nextInt(b0), 0, this.random.nextInt(b0) - this.random.nextInt(b0)));
/* 2344 */         BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(blockpos1);
/* 2345 */         BlockPos blockpos2 = blockpos1.down();
/* 2346 */         Block block = worldclient.getBlockState(blockpos2).getBlock();
/*      */         
/* 2348 */         if (blockpos1.getY() <= blockpos.getY() + b0 && blockpos1.getY() >= blockpos.getY() - b0 && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature(blockpos1) >= 0.15F) {
/*      */           
/* 2350 */           double d3 = this.random.nextDouble();
/* 2351 */           double d4 = this.random.nextDouble();
/*      */           
/* 2353 */           if (block.getMaterial() == Material.lava) {
/*      */             
/* 2355 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, (blockpos1.getY() + 0.1F) - block.getBlockBoundsMinY(), blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           }
/* 2357 */           else if (block.getMaterial() != Material.air) {
/*      */             
/* 2359 */             block.setBlockBoundsBasedOnState((IBlockAccess)worldclient, blockpos2);
/* 2360 */             i++;
/*      */             
/* 2362 */             if (this.random.nextInt(i) == 0) {
/*      */               
/* 2364 */               d0 = blockpos2.getX() + d3;
/* 2365 */               d1 = (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY() - 1.0D;
/* 2366 */               d2 = blockpos2.getZ() + d4;
/*      */             } 
/*      */             
/* 2369 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY(), blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2374 */       if (i > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
/*      */         
/* 2376 */         this.rainSoundCounter = 0;
/*      */         
/* 2378 */         if (d1 > (blockpos.getY() + 1) && worldclient.getPrecipitationHeight(blockpos).getY() > MathHelper.floor_float(blockpos.getY())) {
/*      */           
/* 2380 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
/*      */         }
/*      */         else {
/*      */           
/* 2384 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderRainSnow(float partialTicks) {
/* 2395 */     if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
/*      */       
/* 2397 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 2398 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
/*      */       
/* 2400 */       if (object != null) {
/*      */         
/* 2402 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 2407 */     float f5 = this.mc.theWorld.getRainStrength(partialTicks);
/*      */     
/* 2409 */     if (f5 > 0.0F) {
/*      */       
/* 2411 */       if (Config.isRainOff()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2416 */       enableLightmap();
/* 2417 */       Entity entity = this.mc.getRenderViewEntity();
/* 2418 */       WorldClient worldclient = this.mc.theWorld;
/* 2419 */       int i = MathHelper.floor_double(entity.posX);
/* 2420 */       int j = MathHelper.floor_double(entity.posY);
/* 2421 */       int k = MathHelper.floor_double(entity.posZ);
/* 2422 */       Tessellator tessellator = Tessellator.getInstance();
/* 2423 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2424 */       GlStateManager.disableCull();
/* 2425 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 2426 */       GlStateManager.enableBlend();
/* 2427 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2428 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2429 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 2430 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 2431 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 2432 */       int l = MathHelper.floor_double(d1);
/* 2433 */       byte b0 = 5;
/*      */       
/* 2435 */       if (Config.isRainFancy())
/*      */       {
/* 2437 */         b0 = 10;
/*      */       }
/*      */       
/* 2440 */       byte b1 = -1;
/* 2441 */       float f = this.rendererUpdateCount + partialTicks;
/* 2442 */       worldrenderer.setTranslation(-d0, -d1, -d2);
/*      */       
/* 2444 */       if (Config.isRainFancy())
/*      */       {
/* 2446 */         b0 = 10;
/*      */       }
/*      */       
/* 2449 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2450 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 2452 */       for (int i1 = k - b0; i1 <= k + b0; i1++) {
/*      */         
/* 2454 */         for (int j1 = i - b0; j1 <= i + b0; j1++) {
/*      */           
/* 2456 */           int k1 = (i1 - k + 16) * 32 + j1 - i + 16;
/* 2457 */           double d3 = this.rainXCoords[k1] * 0.5D;
/* 2458 */           double d4 = this.rainYCoords[k1] * 0.5D;
/* 2459 */           blockpos$mutableblockpos.func_181079_c(j1, 0, i1);
/* 2460 */           BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos);
/*      */           
/* 2462 */           if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow()) {
/*      */             
/* 2464 */             int l1 = worldclient.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
/* 2465 */             int i2 = j - b0;
/* 2466 */             int j2 = j + b0;
/*      */             
/* 2468 */             if (i2 < l1)
/*      */             {
/* 2470 */               i2 = l1;
/*      */             }
/*      */             
/* 2473 */             if (j2 < l1)
/*      */             {
/* 2475 */               j2 = l1;
/*      */             }
/*      */             
/* 2478 */             int k2 = l1;
/*      */             
/* 2480 */             if (l1 < l)
/*      */             {
/* 2482 */               k2 = l;
/*      */             }
/*      */             
/* 2485 */             if (i2 != j2) {
/*      */               
/* 2487 */               this.random.setSeed((j1 * j1 * 3121 + j1 * 45238971 ^ i1 * i1 * 418711 + i1 * 13761));
/* 2488 */               blockpos$mutableblockpos.func_181079_c(j1, i2, i1);
/* 2489 */               float f1 = biomegenbase.getFloatTemperature((BlockPos)blockpos$mutableblockpos);
/*      */               
/* 2491 */               if (worldclient.getWorldChunkManager().getTemperatureAtHeight(f1, l1) >= 0.15F) {
/*      */                 
/* 2493 */                 if (b1 != 0) {
/*      */                   
/* 2495 */                   if (b1 >= 0)
/*      */                   {
/* 2497 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 2500 */                   b1 = 0;
/* 2501 */                   this.mc.getTextureManager().bindTexture(locationRainPng);
/* 2502 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 2505 */                 double d5 = ((this.rendererUpdateCount + j1 * j1 * 3121 + j1 * 45238971 + i1 * i1 * 418711 + i1 * 13761 & 0x1F) + partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
/* 2506 */                 double d6 = (j1 + 0.5F) - entity.posX;
/* 2507 */                 double d7 = (i1 + 0.5F) - entity.posZ;
/* 2508 */                 float f2 = MathHelper.sqrt_double(d6 * d6 + d7 * d7) / b0;
/* 2509 */                 float f3 = ((1.0F - f2 * f2) * 0.5F + 0.5F) * f5;
/* 2510 */                 blockpos$mutableblockpos.func_181079_c(j1, k2, i1);
/* 2511 */                 int l2 = worldclient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/* 2512 */                 int i3 = l2 >> 16 & 0xFFFF;
/* 2513 */                 int j3 = l2 & 0xFFFF;
/* 2514 */                 worldrenderer.pos(j1 - d3 + 0.5D, i2, i1 - d4 + 0.5D).tex(0.0D, i2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(i3, j3).endVertex();
/* 2515 */                 worldrenderer.pos(j1 + d3 + 0.5D, i2, i1 + d4 + 0.5D).tex(1.0D, i2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(i3, j3).endVertex();
/* 2516 */                 worldrenderer.pos(j1 + d3 + 0.5D, j2, i1 + d4 + 0.5D).tex(1.0D, j2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(i3, j3).endVertex();
/* 2517 */                 worldrenderer.pos(j1 - d3 + 0.5D, j2, i1 - d4 + 0.5D).tex(0.0D, j2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(i3, j3).endVertex();
/*      */               }
/*      */               else {
/*      */                 
/* 2521 */                 if (b1 != 1) {
/*      */                   
/* 2523 */                   if (b1 >= 0)
/*      */                   {
/* 2525 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 2528 */                   b1 = 1;
/* 2529 */                   this.mc.getTextureManager().bindTexture(locationSnowPng);
/* 2530 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 2533 */                 double d8 = (((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0F);
/* 2534 */                 double d9 = this.random.nextDouble() + f * 0.01D * (float)this.random.nextGaussian();
/* 2535 */                 double d10 = this.random.nextDouble() + (f * (float)this.random.nextGaussian()) * 0.001D;
/* 2536 */                 double d11 = (j1 + 0.5F) - entity.posX;
/* 2537 */                 double d12 = (i1 + 0.5F) - entity.posZ;
/* 2538 */                 float f6 = MathHelper.sqrt_double(d11 * d11 + d12 * d12) / b0;
/* 2539 */                 float f4 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f5;
/* 2540 */                 blockpos$mutableblockpos.func_181079_c(j1, k2, i1);
/* 2541 */                 int k3 = (worldclient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 2542 */                 int l3 = k3 >> 16 & 0xFFFF;
/* 2543 */                 int i4 = k3 & 0xFFFF;
/* 2544 */                 worldrenderer.pos(j1 - d3 + 0.5D, i2, i1 - d4 + 0.5D).tex(0.0D + d9, i2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(l3, i4).endVertex();
/* 2545 */                 worldrenderer.pos(j1 + d3 + 0.5D, i2, i1 + d4 + 0.5D).tex(1.0D + d9, i2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(l3, i4).endVertex();
/* 2546 */                 worldrenderer.pos(j1 + d3 + 0.5D, j2, i1 + d4 + 0.5D).tex(1.0D + d9, j2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(l3, i4).endVertex();
/* 2547 */                 worldrenderer.pos(j1 - d3 + 0.5D, j2, i1 - d4 + 0.5D).tex(0.0D + d9, j2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(l3, i4).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2554 */       if (b1 >= 0)
/*      */       {
/* 2556 */         tessellator.draw();
/*      */       }
/*      */       
/* 2559 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 2560 */       GlStateManager.enableCull();
/* 2561 */       GlStateManager.disableBlend();
/* 2562 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2563 */       disableLightmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupOverlayRendering() {
/* 2572 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 2573 */     GlStateManager.clear(256);
/* 2574 */     GlStateManager.matrixMode(5889);
/* 2575 */     GlStateManager.loadIdentity();
/* 2576 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
/* 2577 */     GlStateManager.matrixMode(5888);
/* 2578 */     GlStateManager.loadIdentity();
/* 2579 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFogColor(float partialTicks) {
/* 2587 */     WorldClient worldclient = this.mc.theWorld;
/* 2588 */     Entity entity = this.mc.getRenderViewEntity();
/* 2589 */     float f = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
/* 2590 */     f = 1.0F - (float)Math.pow(f, 0.25D);
/* 2591 */     Vec3 vec3 = worldclient.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 2592 */     vec3 = CustomColorizer.getWorldSkyColor(vec3, worldclient, this.mc.getRenderViewEntity(), partialTicks);
/* 2593 */     float f1 = (float)vec3.xCoord;
/* 2594 */     float f2 = (float)vec3.yCoord;
/* 2595 */     float f3 = (float)vec3.zCoord;
/* 2596 */     Vec3 vec31 = worldclient.getFogColor(partialTicks);
/* 2597 */     vec31 = CustomColorizer.getWorldFogColor(vec31, worldclient, this.mc.getRenderViewEntity(), partialTicks);
/* 2598 */     this.fogColorRed = (float)vec31.xCoord;
/* 2599 */     this.fogColorGreen = (float)vec31.yCoord;
/* 2600 */     this.fogColorBlue = (float)vec31.zCoord;
/*      */     
/* 2602 */     if (this.mc.gameSettings.renderDistanceChunks >= 4) {
/*      */       
/* 2604 */       double d0 = -1.0D;
/* 2605 */       Vec3 vec32 = (MathHelper.sin(worldclient.getCelestialAngleRadians(partialTicks)) > 0.0F) ? new Vec3(d0, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
/* 2606 */       float f4 = (float)entity.getLook(partialTicks).dotProduct(vec32);
/*      */       
/* 2608 */       if (f4 < 0.0F)
/*      */       {
/* 2610 */         f4 = 0.0F;
/*      */       }
/*      */       
/* 2613 */       if (f4 > 0.0F) {
/*      */         
/* 2615 */         float[] afloat = worldclient.provider.calcSunriseSunsetColors(worldclient.getCelestialAngle(partialTicks), partialTicks);
/*      */         
/* 2617 */         if (afloat != null) {
/*      */           
/* 2619 */           f4 *= afloat[3];
/* 2620 */           this.fogColorRed = this.fogColorRed * (1.0F - f4) + afloat[0] * f4;
/* 2621 */           this.fogColorGreen = this.fogColorGreen * (1.0F - f4) + afloat[1] * f4;
/* 2622 */           this.fogColorBlue = this.fogColorBlue * (1.0F - f4) + afloat[2] * f4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2627 */     this.fogColorRed += (f1 - this.fogColorRed) * f;
/* 2628 */     this.fogColorGreen += (f2 - this.fogColorGreen) * f;
/* 2629 */     this.fogColorBlue += (f3 - this.fogColorBlue) * f;
/* 2630 */     float f10 = worldclient.getRainStrength(partialTicks);
/*      */     
/* 2632 */     if (f10 > 0.0F) {
/*      */       
/* 2634 */       float f5 = 1.0F - f10 * 0.5F;
/* 2635 */       float f12 = 1.0F - f10 * 0.4F;
/* 2636 */       this.fogColorRed *= f5;
/* 2637 */       this.fogColorGreen *= f5;
/* 2638 */       this.fogColorBlue *= f12;
/*      */     } 
/*      */     
/* 2641 */     float f11 = worldclient.getThunderStrength(partialTicks);
/*      */     
/* 2643 */     if (f11 > 0.0F) {
/*      */       
/* 2645 */       float f13 = 1.0F - f11 * 0.5F;
/* 2646 */       this.fogColorRed *= f13;
/* 2647 */       this.fogColorGreen *= f13;
/* 2648 */       this.fogColorBlue *= f13;
/*      */     } 
/*      */     
/* 2651 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/* 2653 */     if (this.cloudFog) {
/*      */       
/* 2655 */       Vec3 vec33 = worldclient.getCloudColour(partialTicks);
/* 2656 */       this.fogColorRed = (float)vec33.xCoord;
/* 2657 */       this.fogColorGreen = (float)vec33.yCoord;
/* 2658 */       this.fogColorBlue = (float)vec33.zCoord;
/*      */     }
/* 2660 */     else if (block.getMaterial() == Material.water) {
/*      */       
/* 2662 */       float f8 = EnchantmentHelper.getRespiration(entity) * 0.2F;
/*      */       
/* 2664 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
/*      */       {
/* 2666 */         f8 = f8 * 0.3F + 0.6F;
/*      */       }
/*      */       
/* 2669 */       this.fogColorRed = 0.02F + f8;
/* 2670 */       this.fogColorGreen = 0.02F + f8;
/* 2671 */       this.fogColorBlue = 0.2F + f8;
/* 2672 */       Vec3 vec34 = CustomColorizer.getUnderwaterColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2674 */       if (vec34 != null)
/*      */       {
/* 2676 */         this.fogColorRed = (float)vec34.xCoord;
/* 2677 */         this.fogColorGreen = (float)vec34.yCoord;
/* 2678 */         this.fogColorBlue = (float)vec34.zCoord;
/*      */       }
/*      */     
/* 2681 */     } else if (block.getMaterial() == Material.lava) {
/*      */       
/* 2683 */       this.fogColorRed = 0.6F;
/* 2684 */       this.fogColorGreen = 0.1F;
/* 2685 */       this.fogColorBlue = 0.0F;
/*      */     } 
/*      */     
/* 2688 */     float f9 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
/* 2689 */     this.fogColorRed *= f9;
/* 2690 */     this.fogColorGreen *= f9;
/* 2691 */     this.fogColorBlue *= f9;
/* 2692 */     double d2 = worldclient.provider.getVoidFogYFactor();
/* 2693 */     double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) * d2;
/*      */     
/* 2695 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/*      */       
/* 2697 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2699 */       if (i < 20) {
/*      */         
/* 2701 */         d1 *= (1.0F - i / 20.0F);
/*      */       }
/*      */       else {
/*      */         
/* 2705 */         d1 = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/* 2709 */     if (d1 < 1.0D) {
/*      */       
/* 2711 */       if (d1 < 0.0D)
/*      */       {
/* 2713 */         d1 = 0.0D;
/*      */       }
/*      */       
/* 2716 */       d1 *= d1;
/* 2717 */       this.fogColorRed = (float)(this.fogColorRed * d1);
/* 2718 */       this.fogColorGreen = (float)(this.fogColorGreen * d1);
/* 2719 */       this.fogColorBlue = (float)(this.fogColorBlue * d1);
/*      */     } 
/*      */     
/* 2722 */     if (this.bossColorModifier > 0.0F) {
/*      */       
/* 2724 */       float f14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 2725 */       this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
/* 2726 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
/* 2727 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
/*      */     } 
/*      */     
/* 2730 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.nightVision)) {
/*      */       
/* 2732 */       float f15 = getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
/* 2733 */       float f6 = 1.0F / this.fogColorRed;
/*      */       
/* 2735 */       if (f6 > 1.0F / this.fogColorGreen)
/*      */       {
/* 2737 */         f6 = 1.0F / this.fogColorGreen;
/*      */       }
/*      */       
/* 2740 */       if (f6 > 1.0F / this.fogColorBlue)
/*      */       {
/* 2742 */         f6 = 1.0F / this.fogColorBlue;
/*      */       }
/*      */       
/* 2745 */       this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
/* 2746 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
/* 2747 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
/*      */     } 
/*      */     
/* 2750 */     if (this.mc.gameSettings.anaglyph) {
/*      */       
/* 2752 */       float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 2753 */       float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 2754 */       float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 2755 */       this.fogColorRed = f16;
/* 2756 */       this.fogColorGreen = f17;
/* 2757 */       this.fogColorBlue = f7;
/*      */     } 
/*      */     
/* 2760 */     if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
/*      */       
/* 2762 */       Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
/* 2763 */       Reflector.postForgeBusEvent(object);
/* 2764 */       this.fogColorRed = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
/* 2765 */       this.fogColorGreen = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
/* 2766 */       this.fogColorBlue = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
/*      */     } 
/*      */     
/* 2769 */     GlStateManager.clearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFog(int p_78468_1_, float partialTicks) {
/* 2778 */     Entity entity = this.mc.getRenderViewEntity();
/* 2779 */     boolean flag = false;
/* 2780 */     this.fogStandard = false;
/*      */     
/* 2782 */     if (entity instanceof EntityPlayer)
/*      */     {
/* 2784 */       flag = ((EntityPlayer)entity).capabilities.isCreativeMode;
/*      */     }
/*      */     
/* 2787 */     GL11.glFog(2918, setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/* 2788 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 2789 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2790 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/* 2791 */     Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogDensity_Constructor, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
/*      */     
/* 2793 */     if (Reflector.postForgeBusEvent(object)) {
/*      */       
/* 2795 */       float f1 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogDensity_density, 0.0F);
/* 2796 */       GL11.glFogf(2914, f1);
/*      */     }
/* 2798 */     else if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/*      */       
/* 2800 */       float f2 = 5.0F;
/* 2801 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2803 */       if (i < 20)
/*      */       {
/* 2805 */         f2 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - i / 20.0F);
/*      */       }
/*      */       
/* 2808 */       GlStateManager.setFog(9729);
/*      */       
/* 2810 */       if (p_78468_1_ == -1) {
/*      */         
/* 2812 */         GlStateManager.setFogStart(0.0F);
/* 2813 */         GlStateManager.setFogEnd(f2 * 0.8F);
/*      */       }
/*      */       else {
/*      */         
/* 2817 */         GlStateManager.setFogStart(f2 * 0.25F);
/* 2818 */         GlStateManager.setFogEnd(f2);
/*      */       } 
/*      */       
/* 2821 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance && Config.isFogFancy())
/*      */       {
/* 2823 */         GL11.glFogi(34138, 34139);
/*      */       }
/*      */     }
/* 2826 */     else if (this.cloudFog) {
/*      */       
/* 2828 */       GlStateManager.setFog(2048);
/* 2829 */       GlStateManager.setFogDensity(0.1F);
/*      */     }
/* 2831 */     else if (block.getMaterial() == Material.water) {
/*      */       
/* 2833 */       GlStateManager.setFog(2048);
/*      */       
/* 2835 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing)) {
/*      */         
/* 2837 */         GlStateManager.setFogDensity(0.01F);
/*      */       }
/*      */       else {
/*      */         
/* 2841 */         GlStateManager.setFogDensity(0.1F - EnchantmentHelper.getRespiration(entity) * 0.03F);
/*      */       } 
/*      */       
/* 2844 */       if (Config.isClearWater())
/*      */       {
/* 2846 */         GL11.glFogf(2914, 0.02F);
/*      */       }
/*      */     }
/* 2849 */     else if (block.getMaterial() == Material.lava) {
/*      */       
/* 2851 */       GlStateManager.setFog(2048);
/* 2852 */       GlStateManager.setFogDensity(2.0F);
/*      */     }
/*      */     else {
/*      */       
/* 2856 */       float f = this.farPlaneDistance;
/* 2857 */       this.fogStandard = true;
/* 2858 */       GlStateManager.setFog(9729);
/*      */       
/* 2860 */       if (p_78468_1_ == -1) {
/*      */         
/* 2862 */         GlStateManager.setFogStart(0.0F);
/* 2863 */         GlStateManager.setFogEnd(f);
/*      */       }
/*      */       else {
/*      */         
/* 2867 */         GlStateManager.setFogStart(f * Config.getFogStart());
/* 2868 */         GlStateManager.setFogEnd(f);
/*      */       } 
/*      */       
/* 2871 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance) {
/*      */         
/* 2873 */         if (Config.isFogFancy())
/*      */         {
/* 2875 */           GL11.glFogi(34138, 34139);
/*      */         }
/*      */         
/* 2878 */         if (Config.isFogFast())
/*      */         {
/* 2880 */           GL11.glFogi(34138, 34140);
/*      */         }
/*      */       } 
/*      */       
/* 2884 */       if (this.mc.theWorld.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ)) {
/*      */         
/* 2886 */         GlStateManager.setFogStart(f * 0.05F);
/* 2887 */         GlStateManager.setFogEnd(f);
/*      */       } 
/*      */       
/* 2890 */       if (Reflector.ForgeHooksClient_onFogRender.exists())
/*      */       {
/* 2892 */         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, block, Float.valueOf(partialTicks), Integer.valueOf(p_78468_1_), Float.valueOf(f) });
/*      */       }
/*      */     } 
/*      */     
/* 2896 */     GlStateManager.enableColorMaterial();
/* 2897 */     GlStateManager.enableFog();
/* 2898 */     GlStateManager.colorMaterial(1028, 4608);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
/* 2906 */     this.fogColorBuffer.clear();
/* 2907 */     this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
/* 2908 */     this.fogColorBuffer.flip();
/* 2909 */     return this.fogColorBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public MapItemRenderer getMapItemRenderer() {
/* 2914 */     return this.theMapItemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void waitForServerThread() {
/* 2919 */     this.serverWaitTimeCurrent = 0;
/*      */     
/* 2921 */     if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
/*      */       
/* 2923 */       if (this.mc.isIntegratedServerRunning()) {
/*      */         
/* 2925 */         IntegratedServer integratedserver = this.mc.getIntegratedServer();
/*      */         
/* 2927 */         if (integratedserver != null) {
/*      */           
/* 2929 */           boolean flag = this.mc.isGamePaused();
/*      */           
/* 2931 */           if (!flag && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/*      */             
/* 2933 */             if (this.serverWaitTime > 0) {
/*      */               
/* 2935 */               Lagometer.timerServer.start();
/* 2936 */               Config.sleep(this.serverWaitTime);
/* 2937 */               Lagometer.timerServer.end();
/* 2938 */               this.serverWaitTimeCurrent = this.serverWaitTime;
/*      */             } 
/*      */             
/* 2941 */             long i = System.nanoTime() / 1000000L;
/*      */             
/* 2943 */             if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
/*      */               
/* 2945 */               long j = i - this.lastServerTime;
/*      */               
/* 2947 */               if (j < 0L) {
/*      */                 
/* 2949 */                 this.lastServerTime = i;
/* 2950 */                 j = 0L;
/*      */               } 
/*      */               
/* 2953 */               if (j >= 50L)
/*      */               {
/* 2955 */                 this.lastServerTime = i;
/* 2956 */                 int k = integratedserver.getTickCounter();
/* 2957 */                 int l = k - this.lastServerTicks;
/*      */                 
/* 2959 */                 if (l < 0) {
/*      */                   
/* 2961 */                   this.lastServerTicks = k;
/* 2962 */                   l = 0;
/*      */                 } 
/*      */                 
/* 2965 */                 if (l < 1 && this.serverWaitTime < 100)
/*      */                 {
/* 2967 */                   this.serverWaitTime += 2;
/*      */                 }
/*      */                 
/* 2970 */                 if (l > 1 && this.serverWaitTime > 0)
/*      */                 {
/* 2972 */                   this.serverWaitTime--;
/*      */                 }
/*      */                 
/* 2975 */                 this.lastServerTicks = k;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 2980 */               this.lastServerTime = i;
/* 2981 */               this.lastServerTicks = integratedserver.getTickCounter();
/* 2982 */               this.avgServerTickDiff = 1.0F;
/* 2983 */               this.avgServerTimeDiff = 50.0F;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2988 */             if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)
/*      */             {
/* 2990 */               Config.sleep(20L);
/*      */             }
/*      */             
/* 2993 */             this.lastServerTime = 0L;
/* 2994 */             this.lastServerTicks = 0;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 3001 */       this.lastServerTime = 0L;
/* 3002 */       this.lastServerTicks = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void frameInit() {
/* 3008 */     if (!this.initialized) {
/*      */       
/* 3010 */       TextureUtils.registerResourceListener();
/* 3011 */       RenderPlayerOF.register();
/*      */       
/* 3013 */       if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32)
/*      */       {
/* 3015 */         Config.setNotify64BitJava(true);
/*      */       }
/*      */       
/* 3018 */       this.initialized = true;
/*      */     } 
/*      */     
/* 3021 */     Config.isActing();
/* 3022 */     Config.checkDisplayMode();
/* 3023 */     WorldClient worldClient = this.mc.theWorld;
/*      */     
/* 3025 */     if (worldClient != null) {
/*      */       
/* 3027 */       if (Config.getNewRelease() != null) {
/*      */         
/* 3029 */         String s = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
/* 3030 */         String s1 = String.valueOf(s) + " " + Config.getNewRelease();
/* 3031 */         ChatComponentText chatcomponenttext = new ChatComponentText("A new §eOptiFine§f version is available: §e" + s1 + "§f");
/* 3032 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/* 3033 */         Config.setNewRelease(null);
/*      */       } 
/*      */       
/* 3036 */       if (Config.isNotify64BitJava()) {
/*      */         
/* 3038 */         Config.setNotify64BitJava(false);
/* 3039 */         ChatComponentText chatcomponenttext1 = new ChatComponentText("You can install §e64-bit Java§f to increase performance");
/* 3040 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext1);
/*      */       } 
/*      */     } 
/*      */     
/* 3044 */     if (this.mc.currentScreen instanceof GuiMainMenu)
/*      */     {
/* 3046 */       updateMainMenu((GuiMainMenu)this.mc.currentScreen);
/*      */     }
/*      */     
/* 3049 */     if (this.updatedWorld != worldClient) {
/*      */       
/* 3051 */       RandomMobs.worldChanged(this.updatedWorld, (World)worldClient);
/* 3052 */       Config.updateThreadPriorities();
/* 3053 */       this.lastServerTime = 0L;
/* 3054 */       this.lastServerTicks = 0;
/* 3055 */       this.updatedWorld = (World)worldClient;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void frameFinish() {
/* 3061 */     if (this.mc.theWorld != null) {
/*      */       
/* 3063 */       long i = System.currentTimeMillis();
/*      */       
/* 3065 */       if (i > this.lastErrorCheckTimeMs + 10000L) {
/*      */         
/* 3067 */         this.lastErrorCheckTimeMs = i;
/* 3068 */         int j = GL11.glGetError();
/*      */         
/* 3070 */         if (j != 0) {
/*      */           
/* 3072 */           String s = GLU.gluErrorString(j);
/* 3073 */           ChatComponentText chatcomponenttext = new ChatComponentText("§eOpenGL Error§f: " + j + " (" + s + ")");
/* 3074 */           this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMainMenu(GuiMainMenu p_updateMainMenu_1_) {
/*      */     try {
/* 3084 */       String s = null;
/* 3085 */       Calendar calendar = Calendar.getInstance();
/* 3086 */       calendar.setTime(new Date());
/* 3087 */       int i = calendar.get(5);
/* 3088 */       int j = calendar.get(2) + 1;
/*      */       
/* 3090 */       if (i == 8 && j == 4)
/*      */       {
/* 3092 */         s = "Happy birthday, OptiFine!";
/*      */       }
/*      */       
/* 3095 */       if (i == 14 && j == 8)
/*      */       {
/* 3097 */         s = "Happy birthday, sp614x!";
/*      */       }
/*      */       
/* 3100 */       if (s == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 3105 */       Field[] afield = GuiMainMenu.class.getDeclaredFields();
/*      */       
/* 3107 */       for (int k = 0; k < afield.length; k++) {
/*      */         
/* 3109 */         if (afield[k].getType() == String.class) {
/*      */           
/* 3111 */           afield[k].setAccessible(true);
/* 3112 */           afield[k].set(p_updateMainMenu_1_, s);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 3117 */     } catch (Throwable throwable) {}
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\EntityRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */