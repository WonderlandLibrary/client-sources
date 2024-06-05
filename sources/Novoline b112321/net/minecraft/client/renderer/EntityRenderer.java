package net.minecraft.client.renderer;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.modules.player.ChestStealer;
import cc.novoline.modules.visual.Atmosphere;
import cc.novoline.modules.visual.Brightness;
import cc.novoline.modules.visual.Camera;
import cc.novoline.modules.visual.XRay;
import cc.novoline.modules.visual.motionblur.CustomShaderGroup;
import com.google.common.base.Predicates;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.aHv;
import net.aLc;
import net.ahc;
import net.asJ;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer1;
import net.minecraft.client.renderer.EntityRenderer2;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.Lagometer;
import net.optifine.RandomMobs;
import net.optifine.Reflector;
import net.optifine.TextureUtils;
import net.shadersmod.client.Shaders;
import net.shadersmod.client.ShadersRender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;

public class EntityRenderer implements IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation N = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
   public static boolean anaglyphEnable;
   public static int anaglyphField;
   private Minecraft mc;
   private final IResourceManager resourceManager;
   private Random random = new Random();
   private float farPlaneDistance;
   public ItemRenderer itemRenderer;
   private final MapItemRenderer theMapItemRenderer;
   private int rendererUpdateCount;
   private Entity pointedEntity;
   private MouseFilter n = new MouseFilter();
   private MouseFilter ai = new MouseFilter();
   private float thirdPersonDistance = 4.0F;
   private float thirdPersonDistanceTemp = 4.0F;
   private float smoothCamYaw;
   private float smoothCamPitch;
   private float smoothCamFilterX;
   private float smoothCamFilterY;
   private float smoothCamPartialTicks;
   private float fovModifierHand;
   private float fovModifierHandPrev;
   private float bossColorModifier;
   private float bossColorModifierPrev;
   private boolean cloudFog;
   private boolean renderHand = true;
   private boolean drawBlockOutline = true;
   private long prevFrameTime = Minecraft.getSystemTime();
   private long renderEndNanoTime;
   private final DynamicTexture lightmapTexture;
   private final int[] lightmapColors;
   private final ResourceLocation locationLightMap;
   private boolean lightmapUpdateNeeded;
   private float torchFlickerX;
   private float torchFlickerDX;
   private int rainSoundCounter;
   private float[] rainXCoords = new float[1024];
   private float[] rainYCoords = new float[1024];
   private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
   public float fogColorRed;
   public float fogColorGreen;
   public float fogColorBlue;
   private float fogColor2;
   private float fogColor1;
   private int debugViewDirection = 0;
   private boolean debugView = false;
   private double cameraZoom = 1.0D;
   private double cameraYaw;
   private double cameraPitch;
   public ShaderGroup theShaderGroup;
   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
   public static final int shaderCount = shaderResourceLocations.length;
   private int shaderIndex;
   private boolean useShader;
   public int frameCount;
   private static final String o = "CL_00000947";
   private boolean initialized = false;
   private World updatedWorld = null;
   private boolean showDebugInfo = false;
   public boolean fogStandard = false;
   private float clipDistance = 128.0F;
   private long lastServerTime = 0L;
   private int lastServerTicks = 0;
   private int serverWaitTime = 0;
   private int serverWaitTimeCurrent = 0;
   private float avgServerTimeDiff = 0.0F;
   private float avgServerTickDiff = 0.0F;
   private long lastErrorCheckTimeMs = 0L;
   private ShaderGroup[] fxaaShaders = new ShaderGroup[10];

   public EntityRenderer(Minecraft var1, IResourceManager var2) {
      this.shaderIndex = shaderCount;
      this.useShader = false;
      this.frameCount = 0;
      this.mc = var1;
      this.resourceManager = var2;
      this.itemRenderer = var1.getItemRenderer();
      this.theMapItemRenderer = new MapItemRenderer(var1.getTextureManager());
      this.lightmapTexture = new DynamicTexture(16, 16);
      this.locationLightMap = var1.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
      this.lightmapColors = this.lightmapTexture.getTextureData();
      this.theShaderGroup = null;

      for(int var3 = 0; var3 < 32; ++var3) {
         for(int var4 = 0; var4 < 32; ++var4) {
            float var5 = (float)(var4 - 16);
            float var6 = (float)(var3 - 16);
            float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
            this.rainXCoords[var3 << 5 | var4] = -var6 / var7;
            this.rainYCoords[var3 << 5 | var4] = var5 / var7;
         }
      }

   }

   public boolean isShaderActive() {
      return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
   }

   public void func_181022_b() {
      if(this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      this.shaderIndex = shaderCount;
   }

   public void switchUseShader() {
      this.useShader = !this.useShader;
   }

   public void loadEntityShader(Entity var1) {
      if(OpenGlHelper.shadersSupported) {
         if(this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.theShaderGroup = null;
         if(var1 instanceof EntityCreeper) {
            this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
         } else if(var1 instanceof EntitySpider) {
            this.loadShader(new ResourceLocation("shaders/post/spider.json"));
         } else if(var1 instanceof EntityEnderman) {
            this.loadShader(new ResourceLocation("shaders/post/invert.json"));
         } else if(Reflector.bj.d()) {
            Reflector.f(Reflector.bj, new Object[]{var1, this});
         }
      }

   }

   public void activateNextShader() {
      if(OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         if(this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
         if(this.shaderIndex != shaderCount) {
            this.loadShader(shaderResourceLocations[this.shaderIndex]);
         } else {
            this.theShaderGroup = null;
         }
      }

   }

   public void loadShader(ResourceLocation var1) {
      if(OpenGlHelper.isFramebufferEnabled()) {
         try {
            this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), var1);
            this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.useShader = true;
         } catch (JsonSyntaxException | IOException var3) {
            logger.warn("Failed to load shader: " + var1, var3);
            this.shaderIndex = shaderCount;
            this.useShader = false;
         }
      }

   }

   public void loadCustomShader() throws IOException {
      if(OpenGlHelper.isFramebufferEnabled()) {
         this.theShaderGroup = new CustomShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), new ResourceLocation("zambo", "sucks"));
         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
         this.useShader = true;
      }

   }

   public void onResourceManagerReload(IResourceManager var1) {
      if(this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      if(this.shaderIndex != shaderCount) {
         this.loadShader(shaderResourceLocations[this.shaderIndex]);
      } else {
         this.loadEntityShader(this.mc.getRenderViewEntity());
      }

   }

   public void updateRenderer() {
      if(OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
         ShaderLinkHelper.setNewStaticShaderLinkHelper();
      }

      this.updateFovModifierHand();
      this.updateTorchFlicker();
      this.fogColor2 = this.fogColor1;
      this.thirdPersonDistanceTemp = this.thirdPersonDistance;
      if(this.mc.gameSettings.smoothCamera) {
         float var1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var2 = var1 * var1 * var1 * 8.0F;
         this.smoothCamFilterX = this.n.smooth(this.smoothCamYaw, 0.05F * var2);
         this.smoothCamFilterY = this.ai.smooth(this.smoothCamPitch, 0.05F * var2);
         this.smoothCamPartialTicks = 0.0F;
         this.smoothCamYaw = 0.0F;
         this.smoothCamPitch = 0.0F;
      } else {
         this.smoothCamFilterX = 0.0F;
         this.smoothCamFilterY = 0.0F;
         this.n.reset();
         this.ai.reset();
      }

      if(this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.player);
      }

      Entity var11 = this.mc.getRenderViewEntity();
      double var12 = var11.posX;
      double var4 = var11.posY + (double)var11.getEyeHeight();
      double var6 = var11.posZ;
      float var8 = this.mc.world.getLightBrightness(new BlockPos(var12, var4, var6));
      float var9 = (float)this.mc.gameSettings.renderDistanceChunks / 16.0F;
      var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
      float var10 = var8 * (1.0F - var9) + var9;
      this.fogColor1 += (var10 - this.fogColor1) * 0.1F;
      ++this.rendererUpdateCount;
      this.itemRenderer.updateEquippedItem();
      this.addRainParticles();
      this.bossColorModifierPrev = this.bossColorModifier;
      if(BossStatus.hasColorModifier) {
         this.bossColorModifier += 0.05F;
         if(this.bossColorModifier > 1.0F) {
            this.bossColorModifier = 1.0F;
         }

         BossStatus.hasColorModifier = false;
      } else if(this.bossColorModifier > 0.0F) {
         this.bossColorModifier -= 0.0125F;
      }

   }

   public ShaderGroup getShaderGroup() {
      return this.theShaderGroup;
   }

   public void updateShaderGroupSize(int var1, int var2) {
      if(OpenGlHelper.shadersSupported) {
         if(this.theShaderGroup != null) {
            this.theShaderGroup.createBindFramebuffers(var1, var2);
         }

         this.mc.renderGlobal.createBindEntityOutlineFbs(var1, var2);
      }

   }

   public void getMouseOver(float var1) {
      Entity var2 = this.mc.getRenderViewEntity();
      if(this.mc.world != null) {
         this.mc.mcProfiler.startSection("pick");
         this.mc.pointedEntity = null;
         double var3 = (double)this.mc.at.i();
         this.mc.objectMouseOver = var2.rayTrace(var3, var1);
         double var5 = var3;
         Vec3 var7 = var2.getPositionEyes(var1);
         boolean var8 = false;
         boolean var9 = true;
         if(this.mc.at.n()) {
            var3 = 6.0D;
            var5 = 6.0D;
         } else if(var3 > 3.0D) {
            var8 = true;
         }

         if(this.mc.objectMouseOver != null) {
            var5 = this.mc.objectMouseOver.hitVec.distanceTo(var7);
         }

         Vec3 var10 = var2.getLook(var1);
         Vec3 var11 = var7.addVector(var10.xCoord * var3, var10.yCoord * var3, var10.zCoord * var3);
         this.pointedEntity = null;
         Vec3 var12 = null;
         float var13 = 1.0F;
         List var14 = this.mc.world.getEntitiesInAABBexcluding(var2, var2.getEntityBoundingBox().addCoord(var10.xCoord * var3, var10.yCoord * var3, var10.zCoord * var3).expand((double)var13, (double)var13, (double)var13), Predicates.and(EntitySelectors.NOT_SPECTATING, new EntityRenderer1(this)));
         double var15 = var5;

         for(Object var18 : var14) {
            Entity var19 = (Entity)var18;
            float var20 = var19.getCollisionBorderSize();
            AxisAlignedBB var21 = var19.getEntityBoundingBox().expand((double)var20, (double)var20, (double)var20);
            MovingObjectPosition var22 = var21.calculateIntercept(var7, var11);
            if(var21.isVecInside(var7)) {
               if(var15 >= 0.0D) {
                  this.pointedEntity = var19;
                  var12 = var7;
                  var15 = 0.0D;
               }
            } else {
               double var23 = var7.distanceTo(var22.hitVec);
               if(var23 < var15 || var15 == 0.0D) {
                  boolean var25 = false;
                  if(Reflector.cC.d()) {
                     var25 = Reflector.d(var19, Reflector.cC, new Object[0]);
                  }

                  if(var19 == var2.ridingEntity) {
                     if(var15 == 0.0D) {
                        this.pointedEntity = var19;
                        var12 = var22.hitVec;
                     }
                  } else {
                     this.pointedEntity = var19;
                     var12 = var22.hitVec;
                     var15 = var23;
                  }
               }
            }
         }

         if(this.pointedEntity != null && var7.distanceTo(var12) > 3.0D) {
            this.pointedEntity = null;
            this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition$MovingObjectType.MISS, var12, (EnumFacing)null, new BlockPos(var12));
         }

         if(this.pointedEntity != null && (var15 < var5 || this.mc.objectMouseOver == null)) {
            this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var12);
            if(this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
               this.mc.pointedEntity = this.pointedEntity;
            }
         }

         this.mc.mcProfiler.endSection();
      }

   }

   private void updateFovModifierHand() {
      float var1 = 1.0F;
      if(this.mc.getRenderViewEntity() instanceof asJ) {
         asJ var2 = (asJ)this.mc.getRenderViewEntity();
         var1 = var2.f();
      }

      this.fovModifierHandPrev = this.fovModifierHand;
      this.fovModifierHand += (var1 - this.fovModifierHand) * 0.5F;
      if(this.fovModifierHand > 1.5F) {
         this.fovModifierHand = 1.5F;
      }

      if(this.fovModifierHand < 0.1F) {
         this.fovModifierHand = 0.1F;
      }

   }

   private float getFOVModifier(float var1, boolean var2) {
      if(this.debugView) {
         return 90.0F;
      } else {
         Entity var3 = this.mc.getRenderViewEntity();
         float var4 = 70.0F;
         var4 = this.mc.gameSettings.fovSetting;
         if(Config.isDynamicFov()) {
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * var1;
         }

         boolean var5 = false;
         if(this.mc.currentScreen == null) {
            GameSettings var6 = this.mc.gameSettings;
            var5 = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
         }

         if(!Config.zoomMode) {
            Config.zoomMode = true;
            this.mc.gameSettings.smoothCamera = true;
         }

         if(Config.zoomMode) {
            var4 /= 4.0F;
         }

         if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0F) {
            float var9 = (float)((EntityLivingBase)var3).deathTime + var1;
            var4 /= (1.0F - 500.0F / (var9 + 500.0F)) * 2.0F + 1.0F;
         }

         Block var10 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, var3, var1);
         if(var10.getMaterial() == Material.water) {
            var4 = var4 * 60.0F / 70.0F;
         }

         return var4;
      }
   }

   private void hurtCameraEffect(float var1) {
      if(this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
         if(((Camera)Novoline.getInstance().getModuleManager().getModule(Camera.class)).isEnabled() && ((Boolean)((Camera)Novoline.getInstance().getModuleManager().getModule(Camera.class)).getNoHurtCam().get()).booleanValue()) {
            return;
         }

         EntityLivingBase var2 = (EntityLivingBase)this.mc.getRenderViewEntity();
         float var3 = (float)var2.hurtTime - var1;
         if(var2.getHealth() <= 0.0F) {
            float var4 = (float)var2.deathTime + var1;
            GlStateManager.rotate(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
         }

         if(var3 < 0.0F) {
            return;
         }

         var3 = var3 / (float)var2.maxHurtTime;
         var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927F);
         float var7 = var2.attackedAtYaw;
         GlStateManager.rotate(-var7, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(var7, 0.0F, 1.0F, 0.0F);
      }

   }

   private void setupViewBobbing(float var1) {
      if(this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.mc.getRenderViewEntity();
         float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
         float var4 = -(var2.distanceWalkedModified + var3 * var1);
         float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * var1;
         float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * var1;
         GlStateManager.translate(MathHelper.sin(var4 * 3.1415927F) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * 3.1415927F) * var5), 0.0F);
         GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var6, 1.0F, 0.0F, 0.0F);
      }

   }

   public void orientCamera(float var1) {
      Entity var2 = this.mc.getRenderViewEntity();
      float var3 = var2.getEyeHeight();
      double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)var1;
      double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)var1 + (double)var3;
      double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)var1;
      Camera var10 = (Camera)Novoline.getInstance().getModuleManager().getModule(Camera.class);
      if(var2 instanceof EntityLivingBase && ((EntityLivingBase)var2).isPlayerSleeping()) {
         var3 = (float)((double)var3 + 1.0D);
         GlStateManager.translate(0.0F, 0.3F, 0.0F);
         if(!this.mc.gameSettings.debugCamEnable) {
            BlockPos var31 = new BlockPos(var2);
            IBlockState var12 = this.mc.world.getBlockState(var31);
            Block var37 = var12.getBlock();
            if(Reflector.aA.d()) {
               Reflector.a(Reflector.aA, new Object[]{this.mc.world, var31, var12, var2});
            } else if(var37 == Blocks.bed) {
               int var40 = ((EnumFacing)var12.getValue(BlockBed.FACING)).getHorizontalIndex();
               GlStateManager.rotate((float)(var40 * 90), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1, -1.0F, 0.0F, 0.0F);
         }
      } else if(this.mc.gameSettings.thirdPersonView > 0) {
         double var11 = (double)(this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * var1);
         if(this.mc.gameSettings.debugCamEnable) {
            GlStateManager.translate(0.0F, 0.0F, (float)(-var11));
         } else {
            float var13 = var2.rotationYaw;
            float var14 = var2.rotationPitch;
            if(this.mc.gameSettings.thirdPersonView == 2) {
               var14 += 180.0F;
            }

            double var15 = (double)(-MathHelper.sin(var13 / 180.0F * 3.1415927F) * MathHelper.cos(var14 / 180.0F * 3.1415927F)) * var11;
            double var17 = (double)(MathHelper.cos(var13 / 180.0F * 3.1415927F) * MathHelper.cos(var14 / 180.0F * 3.1415927F)) * var11;
            double var19 = (double)(-MathHelper.sin(var14 / 180.0F * 3.1415927F)) * var11;

            for(int var21 = 0; var21 < 8; ++var21) {
               float var22 = (float)((var21 & 1) * 2 - 1);
               float var23 = (float)((var21 >> 1 & 1) * 2 - 1);
               float var24 = (float)((var21 >> 2 & 1) * 2 - 1);
               var22 = var22 * 0.1F;
               var23 = var23 * 0.1F;
               var24 = var24 * 0.1F;
               MovingObjectPosition var25 = this.mc.world.rayTraceBlocks(new Vec3(var4 + (double)var22, var6 + (double)var23, var8 + (double)var24), new Vec3(var4 - var15 + (double)var22 + (double)var24, var6 - var19 + (double)var23, var8 - var17 + (double)var24));
               double var26 = var25.hitVec.distanceTo(new Vec3(var4, var6, var8));
               if(var26 < var11 && (!var10.isEnabled() || !((Boolean)var10.getViewClip().get()).booleanValue())) {
                  var11 = var26;
               }
            }

            if(this.mc.gameSettings.thirdPersonView == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var2.rotationPitch - var14, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(var2.rotationYaw - var13, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, (float)(-var11));
            GlStateManager.rotate(var13 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(var14 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
         }
      } else {
         GlStateManager.translate(0.0F, 0.0F, -0.1F);
      }

      if(Reflector.bo.b()) {
         if(!this.mc.gameSettings.debugCamEnable) {
            float var32 = var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F;
            float var35 = var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1;
            float var38 = 0.0F;
            if(var2 instanceof EntityAnimal) {
               EntityAnimal var41 = (EntityAnimal)var2;
               var32 = var41.prevRotationYawHead + (var41.rotationYawHead - var41.prevRotationYawHead) * var1 + 180.0F;
            }

            Block var42 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, var2, var1);
            Object var43 = Reflector.b(Reflector.bo, new Object[]{this, var2, var42, Float.valueOf(var1), Float.valueOf(var32), Float.valueOf(var35), Float.valueOf(var38)});
            Reflector.postForgeBusEvent(var43);
            var38 = Reflector.getFieldValueFloat(var43, Reflector.EntityViewRenderEvent_CameraSetup_roll, var38);
            var35 = Reflector.getFieldValueFloat(var43, Reflector.EntityViewRenderEvent_CameraSetup_pitch, var35);
            var32 = Reflector.getFieldValueFloat(var43, Reflector.EntityViewRenderEvent_CameraSetup_yaw, var32);
            GlStateManager.rotate(var38, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(var35, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(var32, 0.0F, 1.0F, 0.0F);
         }
      } else if(!this.mc.gameSettings.debugCamEnable) {
         if(var10.isEnabled() && var10.f()) {
            GlStateManager.rotate(var2.cameraRotationPitch, 1.0F, 0.0F, 0.0F);
         } else {
            GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1, 1.0F, 0.0F, 0.0F);
         }

         if(var2 instanceof EntityAnimal) {
            EntityAnimal var34 = (EntityAnimal)var2;
            GlStateManager.rotate(var34.prevRotationYawHead + (var34.rotationYawHead - var34.prevRotationYawHead) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
         } else if(var10.isEnabled() && var10.f()) {
            GlStateManager.rotate(var2.cameraRotationYaw + 180.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.rotate(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
         }
      }

      GlStateManager.translate(0.0F, -var3, 0.0F);
      var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)var1;
      var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)var1 + (double)var3;
      var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)var1;
      this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var6, var8, var1);
   }

   public void setupCameraTransform(float var1, int var2) {
      this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
      if(Config.isFogFancy()) {
         this.farPlaneDistance *= 0.95F;
      }

      if(Config.ad()) {
         this.farPlaneDistance *= 0.83F;
      }

      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      float var3 = 0.07F;
      if(this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((float)(-(var2 * 2 - 1)) * var3, 0.0F, 0.0F);
      }

      this.clipDistance = this.farPlaneDistance * 2.0F;
      if(this.clipDistance < 173.0F) {
         this.clipDistance = 173.0F;
      }

      if(this.mc.world.provider.getDimensionId() == 1) {
         this.clipDistance = 256.0F;
      }

      if(this.cameraZoom != 1.0D) {
         GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
         GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
      }

      Project.gluPerspective(this.getFOVModifier(var1, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      if(this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      this.hurtCameraEffect(var1);
      if(this.mc.gameSettings.viewBobbing) {
         this.setupViewBobbing(var1);
      }

      float var4 = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * var1;
      if(var4 > 0.0F) {
         byte var5 = 20;
         if(this.mc.player.isPotionActive(Potion.confusion)) {
            var5 = 7;
         }

         float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
         var6 = var6 * var6;
         GlStateManager.rotate(((float)this.rendererUpdateCount + var1) * (float)var5, 0.0F, 1.0F, 1.0F);
         GlStateManager.scale(1.0F / var6, 1.0F, 1.0F);
         GlStateManager.rotate(-((float)this.rendererUpdateCount + var1) * (float)var5, 0.0F, 1.0F, 1.0F);
      }

      this.orientCamera(var1);
      if(this.debugView) {
         switch(this.debugViewDirection) {
         case 0:
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            break;
         case 1:
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            break;
         case 2:
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            break;
         case 3:
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            break;
         case 4:
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
         }
      }

   }

   private void renderHand(float var1, int var2) {
      this.renderHand(var1, var2, true, true, false);
   }

   public void renderHand(float var1, int var2, boolean var3, boolean var4, boolean var5) {
      if(!this.debugView) {
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         float var6 = 0.07F;
         if(this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(-(var2 * 2 - 1)) * var6, 0.0F, 0.0F);
         }

         if(Config.isShaders()) {
            Shaders.applyHandDepth();
         }

         Project.gluPerspective(this.getFOVModifier(var1, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         if(this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
         }

         boolean var7 = false;
         GlStateManager.pushMatrix();
         this.hurtCameraEffect(var1);
         if(this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(var1);
         }

         var7 = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
         boolean var8 = !ahc.a(this.mc.renderGlobal, var1, var2);
         if(this.mc.gameSettings.thirdPersonView == 0 && !this.mc.gameSettings.hideGUI && !this.mc.at.k()) {
            this.enableLightmap();
            if(Config.isShaders()) {
               ShadersRender.renderItemFP(this.itemRenderer, var1, var5);
            } else {
               this.itemRenderer.renderItemInFirstPerson(var1);
            }

            this.disableLightmap();
         }

         GlStateManager.popMatrix();
      }
   }

   public void disableLightmap() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if(Config.isShaders()) {
         Shaders.disableLightmap();
      }

   }

   public void enableLightmap() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      float var1 = 0.00390625F;
      GlStateManager.scale(var1, var1, var1);
      GlStateManager.translate(8.0F, 8.0F, 8.0F);
      GlStateManager.matrixMode(5888);
      this.mc.getTextureManager().bindTexture(this.locationLightMap);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 10496);
      GL11.glTexParameteri(3553, 10243, 10496);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if(Config.isShaders()) {
         Shaders.enableLightmap();
      }

   }

   private void updateTorchFlicker() {
      this.torchFlickerDX = (float)((double)this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.torchFlickerDX = (float)((double)this.torchFlickerDX * 0.9D);
      this.torchFlickerX += this.torchFlickerDX - this.torchFlickerX;
      this.lightmapUpdateNeeded = true;
   }

   private void updateLightmap(float var1) {
      if(this.lightmapUpdateNeeded) {
         this.mc.mcProfiler.startSection("lightTex");
         WorldClient var2 = this.mc.world;
         if(Config.isCustomColors() && CustomColors.a(var2, this.torchFlickerX, this.lightmapColors, this.mc.player.isPotionActive(Potion.nightVision))) {
            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
            this.mc.mcProfiler.endSection();
            return;
         }

         float var3 = var2.getSunBrightness(1.0F);
         float var4 = var3 * 0.95F + 0.05F;

         for(int var5 = 0; var5 < 256; ++var5) {
            float var6 = var2.provider.getLightBrightnessTable()[var5 / 16] * var4;
            float var7 = var2.provider.getLightBrightnessTable()[var5 % 16] * (this.torchFlickerX * 0.1F + 1.5F);
            if(var2.getLastLightningBolt() > 0) {
               var6 = var2.provider.getLightBrightnessTable()[var5 / 16];
            }

            float var8 = var6 * (var3 * 0.65F + 0.35F);
            float var9 = var6 * (var3 * 0.65F + 0.35F);
            float var10 = var7 * ((var7 * 0.6F + 0.4F) * 0.6F + 0.4F);
            float var11 = var7 * (var7 * var7 * 0.6F + 0.4F);
            float var12 = var8 + var7;
            float var13 = var9 + var10;
            float var14 = var6 + var11;
            var12 = var12 * 0.96F + 0.03F;
            var13 = var13 * 0.96F + 0.03F;
            var14 = var14 * 0.96F + 0.03F;
            if(this.bossColorModifier > 0.0F) {
               float var15 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * var1;
               var12 = var12 * (1.0F - var15) + var12 * 0.7F * var15;
               var13 = var13 * (1.0F - var15) + var13 * 0.6F * var15;
               var14 = var14 * (1.0F - var15) + var14 * 0.6F * var15;
            }

            if(var2.provider.getDimensionId() == 1) {
               var12 = 0.22F + var7 * 0.75F;
               var13 = 0.28F + var10 * 0.75F;
               var14 = 0.25F + var11 * 0.75F;
            }

            if(this.mc.player.isPotionActive(Potion.nightVision) || Brightness.c()) {
               float var32 = this.getNightVisionBrightness(this.mc.player, var1);
               float var16 = 1.0F / var12;
               if(var16 > 1.0F / var13) {
                  var16 = 1.0F / var13;
               }

               if(var16 > 1.0F / var14) {
                  var16 = 1.0F / var14;
               }

               var12 = var12 * (1.0F - var32) + var12 * var16 * var32;
               var13 = var13 * (1.0F - var32) + var13 * var16 * var32;
               var14 = var14 * (1.0F - var32) + var14 * var16 * var32;
            }

            if(var12 > 1.0F) {
               var12 = 1.0F;
            }

            if(var13 > 1.0F) {
               var13 = 1.0F;
            }

            if(var14 > 1.0F) {
               var14 = 1.0F;
            }

            float var33 = !XRay.isEnabled && !Brightness.a()?this.mc.gameSettings.gammaSetting:10000.0F;
            float var34 = 1.0F - var12;
            float var17 = 1.0F - var13;
            float var18 = 1.0F - var14;
            var34 = 1.0F - var34 * var34 * var34 * var34;
            var17 = 1.0F - var17 * var17 * var17 * var17;
            var18 = 1.0F - var18 * var18 * var18 * var18;
            var12 = var12 * (1.0F - var33) + var34 * var33;
            var13 = var13 * (1.0F - var33) + var17 * var33;
            var14 = var14 * (1.0F - var33) + var18 * var33;
            var12 = var12 * 0.96F + 0.03F;
            var13 = var13 * 0.96F + 0.03F;
            var14 = var14 * 0.96F + 0.03F;
            if(var12 > 1.0F) {
               var12 = 1.0F;
            }

            if(var13 > 1.0F) {
               var13 = 1.0F;
            }

            if(var14 > 1.0F) {
               var14 = 1.0F;
            }

            if(var12 < 0.0F) {
               var12 = 0.0F;
            }

            if(var13 < 0.0F) {
               var13 = 0.0F;
            }

            if(var14 < 0.0F) {
               var14 = 0.0F;
            }

            short var19 = 255;
            int var20 = (int)(var12 * 255.0F);
            int var21 = (int)(var13 * 255.0F);
            int var22 = (int)(var14 * 255.0F);
            this.lightmapColors[var5] = var19 << 24 | var20 << 16 | var21 << 8 | var22;
         }

         this.lightmapTexture.updateDynamicTexture();
         this.lightmapUpdateNeeded = false;
         this.mc.mcProfiler.endSection();
      }

   }

   public float getNightVisionBrightness(EntityLivingBase var1, float var2) {
      if(var1 instanceof EntityPlayerSP && Brightness.c()) {
         return 1.0F;
      } else {
         int var3 = var1.getActivePotionEffect(Potion.nightVision).getDuration();
         return var3 > 200?1.0F:0.7F + MathHelper.sin(((float)var3 - var2) * 3.1415927F * 0.2F) * 0.3F;
      }
   }

   public void func_181560_a(float var1, long var2) {
      this.frameInit();
      boolean var4 = Display.isActive();
      if(this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
         if(Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
            this.mc.displayInGameMenu();
         }
      } else {
         this.prevFrameTime = Minecraft.getSystemTime();
      }

      this.mc.mcProfiler.startSection("mouse");
      if(Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
         Mouse.setGrabbed(false);
         Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
         Mouse.setGrabbed(true);
      }

      if(this.mc.inGameHasFocus || !Novoline.getInstance().isAnythingNull() && ((ChestStealer)Novoline.getInstance().getModuleManager().getModule(ChestStealer.class)).f()) {
         this.mc.mouseHelper.mouseXYChange();
         float var5 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var6 = var5 * var5 * var5 * 8.0F;
         float var7 = (float)this.mc.mouseHelper.deltaX * var6;
         float var8 = (float)this.mc.mouseHelper.deltaY * var6;
         byte var9 = 1;
         if(this.mc.gameSettings.invertMouse) {
            var9 = -1;
         }

         if(this.mc.gameSettings.smoothCamera) {
            this.smoothCamYaw += var7;
            this.smoothCamPitch += var8;
            float var10 = var1 - this.smoothCamPartialTicks;
            this.smoothCamPartialTicks = var1;
            var7 = this.smoothCamFilterX * var10;
            var8 = this.smoothCamFilterY * var10;
         } else {
            this.smoothCamYaw = 0.0F;
            this.smoothCamPitch = 0.0F;
         }

         this.mc.player.setAngles(var7, var8 * (float)var9);
      }

      this.mc.mcProfiler.endSection();
      if(!this.mc.skipRenderWorld) {
         anaglyphEnable = this.mc.gameSettings.anaglyph;
         ScaledResolution var17 = new ScaledResolution(this.mc);
         int var18 = var17.getScaledWidth();
         int var19 = var17.getScaledHeight();
         int var20 = Mouse.getX() * var18 / this.mc.displayWidth;
         int var21 = var19 - Mouse.getY() * var19 / this.mc.displayHeight - 1;
         int var22 = this.mc.gameSettings.limitFramerate;
         if(this.mc.world != null) {
            this.mc.mcProfiler.startSection("level");
            int var11 = Math.min(this.mc.getDebugFPS(), var22);
            var11 = Math.max(var11, 60);
            long var12 = System.nanoTime() - var2;
            long var14 = Math.max((long)(1000000000 / var11 / 4) - var12, 0L);
            this.renderWorld(var1, System.nanoTime() + var14);
            if(OpenGlHelper.shadersSupported) {
               this.mc.renderGlobal.renderEntityOutlineFramebuffer();
               if(this.theShaderGroup != null && this.useShader) {
                  GlStateManager.matrixMode(5890);
                  GlStateManager.pushMatrix();
                  GlStateManager.loadIdentity();
                  this.theShaderGroup.loadShaderGroup(var1);
                  GlStateManager.popMatrix();
               }

               this.mc.getFramebuffer().bindFramebuffer(true);
            }

            this.renderEndNanoTime = System.nanoTime();
            this.mc.mcProfiler.endStartSection("gui");
            if(!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
               GlStateManager.alphaFunc(516, 0.1F);
               this.mc.ingameGUI.renderGameOverlay(var1);
               if(this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                  Config.drawFps();
               }

               if(this.mc.gameSettings.showDebugInfo) {
                  Lagometer.showLagometer(var17);
               }
            }

            this.mc.mcProfiler.endSection();
         } else {
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            this.setupOverlayRendering();
            this.renderEndNanoTime = System.nanoTime();
            TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
         }

         if(this.mc.currentScreen != null) {
            GlStateManager.clear(256);

            try {
               if(Reflector.cL.d()) {
                  Reflector.a(Reflector.cL, new Object[]{this.mc.currentScreen, Integer.valueOf(var20), Integer.valueOf(var21), Float.valueOf(var1)});
               } else {
                  this.mc.currentScreen.drawScreen(var20, var21, var1);
               }
            } catch (Throwable var16) {
               CrashReport var24 = CrashReport.makeCrashReport(var16, "Rendering screen");
               CrashReportCategory var13 = var24.makeCategory("Screen render details");
               var13.addCrashSectionCallable("Screen name", new EntityRenderer2(this));
               var13.addCrashSectionCallable("Mouse location", EntityRenderer::lambda$func_181560_a$0);
               var13.addCrashSectionCallable("Screen size", this::lambda$func_181560_a$1);
               throw new ReportedException(var24);
            }
         }
      }

      this.frameFinish();
      this.waitForServerThread();
      Lagometer.updateLagometer();
      if(this.mc.gameSettings.ofProfiler) {
         this.mc.gameSettings.showDebugProfilerChart = true;
      }

   }

   public void renderStreamIndicator(float var1) {
      this.setupOverlayRendering();
      this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
   }

   private boolean isDrawBlockOutline() {
      if(!this.drawBlockOutline) {
         return false;
      } else {
         Entity var1 = this.mc.getRenderViewEntity();
         boolean var2 = var1 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
         if(!((EntityPlayer)var1).abilities.isAllowEdit()) {
            ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
            if(this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
               BlockPos var4 = this.mc.objectMouseOver.getBlockPos();
               IBlockState var5 = this.mc.world.getBlockState(var4);
               Block var6 = var5.getBlock();
               if(this.mc.at.l() == WorldSettings$GameType.SPECTATOR) {
                  var2 = ahc.a(var5) && this.mc.world.getTileEntity(var4) instanceof IInventory;
               } else {
                  var2 = var3.canDestroy(var6) || var3.canPlaceOn(var6);
               }
            }
         }

         return var2;
      }
   }

   private void renderWorldDirections(float var1) {
      if(this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.player.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
         Entity var2 = this.mc.getRenderViewEntity();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         this.orientCamera(var1);
         GlStateManager.translate(0.0F, var2.getEyeHeight(), 0.0F);
         RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), 255, 0, 0, 255);
         RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), 0, 0, 255, 255);
         RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), 0, 255, 0, 255);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
      }

   }

   public void renderWorld(float var1, long var2) {
      this.updateLightmap(var1);
      if(this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.player);
      }

      this.getMouseOver(var1);
      if(Config.isShaders()) {
         Shaders.beginRender(this.mc, var1, var2);
      }

      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.mc.mcProfiler.startSection("center");
      if(this.mc.gameSettings.anaglyph) {
         anaglyphField = 0;
         GlStateManager.colorMask(false, true, true, false);
         this.renderWorldPass(0, var1, var2);
         anaglyphField = 1;
         GlStateManager.colorMask(true, false, false, false);
         this.renderWorldPass(1, var1, var2);
         GlStateManager.colorMask(true, true, true, false);
      } else {
         this.renderWorldPass(2, var1, var2);
      }

      this.mc.mcProfiler.endSection();
   }

   private void renderWorldPass(int var1, float var2, long var3) {
      boolean var5 = Config.isShaders();
      Shaders.beginRenderPass(var1, var2, var3);
      RenderGlobal var6 = this.mc.renderGlobal;
      EffectRenderer var7 = this.mc.effectRenderer;
      boolean var8 = this.isDrawBlockOutline();
      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("clear");
      Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      this.updateFogColor(var2);
      GlStateManager.clear(16640);
      Shaders.clearRenderBuffer();
      this.mc.mcProfiler.endStartSection("camera");
      this.setupCameraTransform(var2, var1);
      Shaders.setCamera(var2);
      ActiveRenderInfo.updateRenderInfo(this.mc.player, this.mc.gameSettings.thirdPersonView == 2);
      this.mc.mcProfiler.endStartSection("frustum");
      ClippingHelperImpl.getInstance();
      this.mc.mcProfiler.endStartSection("culling");
      Frustum var9 = new Frustum();
      Entity var10 = this.mc.getRenderViewEntity();
      double var11 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * (double)var2;
      double var13 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * (double)var2;
      double var15 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * (double)var2;
      ShadersRender.setFrustrumPosition(var9, var11, var13, var15);
      if((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
         this.setupFog(-1, var2);
         this.mc.mcProfiler.endStartSection("sky");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
         Shaders.beginSky();
         var6.renderSky(var2, var1);
         Shaders.endSky();
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      } else {
         GlStateManager.disableBlend();
      }

      this.setupFog(0, var2);
      GlStateManager.shadeModel(7425);
      if(var10.posY + (double)var10.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
         this.renderCloudsCheck(var6, var2, var1);
      }

      this.mc.mcProfiler.endStartSection("prepareterrain");
      this.setupFog(0, var2);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      RenderHelper.disableStandardItemLighting();
      this.mc.mcProfiler.endStartSection("terrain_setup");
      ShadersRender.setupTerrain(var6, var10, (double)var2, var9, this.frameCount++, this.mc.player.isSpectator());
      if(var1 == 2) {
         this.mc.mcProfiler.endStartSection("updatechunks");
         Lagometer.timerChunkUpload.start();
         this.mc.renderGlobal.updateChunks(var3);
         Lagometer.timerChunkUpload.end();
      }

      this.mc.mcProfiler.endStartSection("terrain");
      Lagometer.timerTerrain.start();
      if(this.mc.gameSettings.ofSmoothFps) {
         this.mc.mcProfiler.endStartSection("finish");
         GL11.glFinish();
         this.mc.mcProfiler.endStartSection("terrain");
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      ShadersRender.beginTerrainSolid();
      var6.renderBlockLayer(EnumWorldBlockLayer.SOLID, (double)var2, var1, var10);
      GlStateManager.enableAlpha();
      ShadersRender.j();
      var6.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, (double)var2, var1, var10);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
      ShadersRender.m();
      var6.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, (double)var2, var1, var10);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
      ShadersRender.d();
      Lagometer.timerTerrain.end();
      GlStateManager.shadeModel(7424);
      GlStateManager.alphaFunc(516, 0.1F);
      if(!this.debugView) {
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         RenderHelper.enableStandardItemLighting();
         this.mc.mcProfiler.endStartSection("entities");
         if(Reflector.aF.d()) {
            Reflector.a(Reflector.aF, new Object[]{Integer.valueOf(0)});
         }

         var6.renderEntities(var10, var9, var2);
         if(Reflector.aF.d()) {
            Reflector.a(Reflector.aF, new Object[]{Integer.valueOf(-1)});
         }

         RenderHelper.disableStandardItemLighting();
         this.disableLightmap();
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         if(this.mc.objectMouseOver != null && var10.isInsideOfMaterial(Material.water)) {
            EntityPlayer var17 = (EntityPlayer)var10;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection("outline");
            if((!Reflector.c5.d() || !Reflector.b(Reflector.c5, new Object[]{var6, var17, this.mc.objectMouseOver, Integer.valueOf(0), var17.getHeldItem(), Float.valueOf(var2)})) && !this.mc.gameSettings.hideGUI) {
               var6.drawSelectionBox(var17, this.mc.objectMouseOver, 0, var2);
            }

            GlStateManager.enableAlpha();
         }
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      if(this.mc.objectMouseOver != null && !var10.isInsideOfMaterial(Material.water)) {
         EntityPlayer var18 = (EntityPlayer)var10;
         GlStateManager.disableAlpha();
         this.mc.mcProfiler.endStartSection("outline");
         if((!Reflector.c5.d() || !Reflector.b(Reflector.c5, new Object[]{var6, var18, this.mc.objectMouseOver, Integer.valueOf(0), var18.getHeldItem(), Float.valueOf(var2)})) && !this.mc.gameSettings.hideGUI) {
            var6.drawSelectionBox(var18, this.mc.objectMouseOver, 0, var2);
         }

         GlStateManager.enableAlpha();
      }

      if(!var6.damagedBlocks.isEmpty()) {
         this.mc.mcProfiler.endStartSection("destroyProgress");
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
         var6.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var10, var2);
         this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
         GlStateManager.disableBlend();
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableBlend();
      if(!this.debugView) {
         this.enableLightmap();
         this.mc.mcProfiler.endStartSection("litParticles");
         Shaders.g();
         var7.renderLitParticles(var10, var2);
         RenderHelper.disableStandardItemLighting();
         this.setupFog(0, var2);
         this.mc.mcProfiler.endStartSection("particles");
         Shaders.ao();
         var7.renderParticles(var10, var2);
         Shaders.E();
         this.disableLightmap();
      }

      GlStateManager.depthMask(false);
      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("weather");
      Shaders.beginWeather();
      this.renderRainSnow(var2);
      Shaders.aD();
      GlStateManager.depthMask(true);
      var6.renderWorldBorder(var10, var2);
      ShadersRender.renderHand0(this, var2, var1);
      Shaders.preWater();
      GlStateManager.disableBlend();
      GlStateManager.enableCull();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.1F);
      this.setupFog(0, var2);
      GlStateManager.enableBlend();
      GlStateManager.depthMask(false);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      GlStateManager.shadeModel(7425);
      this.mc.mcProfiler.endStartSection("translucent");
      Shaders.beginWater();
      var6.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, (double)var2, var1, var10);
      Shaders.am();
      if(Reflector.aF.d() && !this.debugView) {
         RenderHelper.enableStandardItemLighting();
         this.mc.mcProfiler.endStartSection("entities");
         Reflector.a(Reflector.aF, new Object[]{Integer.valueOf(1)});
         this.mc.renderGlobal.renderEntities(var10, var9, var2);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         Reflector.a(Reflector.aF, new Object[]{Integer.valueOf(-1)});
         RenderHelper.disableStandardItemLighting();
      }

      GlStateManager.shadeModel(7424);
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.disableFog();
      if(var10.posY + (double)var10.getEyeHeight() >= 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
         this.mc.mcProfiler.endStartSection("aboveClouds");
         this.renderCloudsCheck(var6, var2, var1);
      }

      if(Reflector.dr.d()) {
         this.mc.mcProfiler.endStartSection("forge_render_last");
         Reflector.a(Reflector.dr, new Object[]{var6, Float.valueOf(var2)});
      }

      GL11.glPushMatrix();
      EventManager.call(new Render3DEvent(var2));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      this.mc.mcProfiler.endStartSection("hand");
      boolean var19 = ahc.a(this.mc.renderGlobal, var2, var1);
      if(this.renderHand && !Shaders.isShadowPass) {
         ShadersRender.renderHand1(this, var2, var1);
         Shaders.renderCompositeFinal();
         GlStateManager.clear(256);
         ShadersRender.renderFPOverlay(this, var2, var1);
         this.renderWorldDirections(var2);
      }

      Shaders.endRender();
   }

   private void renderCloudsCheck(RenderGlobal var1, float var2, int var3) {
      if(this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
         this.mc.mcProfiler.endStartSection("clouds");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         this.setupFog(0, var2);
         var1.renderClouds(var2, var3);
         GlStateManager.disableFog();
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      }

   }

   private void addRainParticles() {
      float var1 = this.mc.world.getRainStrength(1.0F);
      if(!Config.as()) {
         var1 /= 2.0F;
      }

      if(var1 != 0.0F && Config.isRainSplash()) {
         if(((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).isEnabled() && (((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).getWeather_mode().equalsIgnoreCase("Snowfall") || ((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).getWeather_mode().equalsIgnoreCase("Snowstorm"))) {
            return;
         }

         this.random.setSeed((long)this.rendererUpdateCount * 312987231L);
         Entity var2 = this.mc.getRenderViewEntity();
         WorldClient var3 = this.mc.world;
         BlockPos var4 = new BlockPos(var2);
         byte var5 = 10;
         double var6 = 0.0D;
         double var8 = 0.0D;
         double var10 = 0.0D;
         int var12 = 0;
         int var13 = (int)(100.0F * var1 * var1);
         if(this.mc.gameSettings.particleSetting == 1) {
            var13 >>= 1;
         } else if(this.mc.gameSettings.particleSetting == 2) {
            var13 = 0;
         }

         for(int var14 = 0; var14 < var13; ++var14) {
            BlockPos var15 = var3.getPrecipitationHeight(var4.a(this.random.nextInt(var5) - this.random.nextInt(var5), 0, this.random.nextInt(var5) - this.random.nextInt(var5)));
            BiomeGenBase var16 = var3.getBiomeGenForCoords(var15);
            BlockPos var17 = var15.down();
            Block var18 = var3.getBlockState(var17).getBlock();
            if(var15.getY() <= var4.getY() + var5 && var15.getY() >= var4.getY() - var5 && var16.canSpawnLightningBolt() && var16.getFloatTemperature(var15) >= 0.15F) {
               double var19 = this.random.nextDouble();
               double var21 = this.random.nextDouble();
               if(var18.getMaterial() == Material.lava) {
                  aLc.a(this.mc.world, EnumParticleTypes.SMOKE_NORMAL, (double)var15.getX() + var19, (double)((float)var15.getY() + 0.1F) - var18.getBlockBoundsMinY(), (double)var15.getZ() + var21, 0.0D, 0.0D, 0.0D, new int[0]);
               } else if(var18.getMaterial() != Material.air) {
                  var18.setBlockBoundsBasedOnState(var3, var17);
                  ++var12;
                  if(this.random.nextInt(var12) == 0) {
                     var6 = (double)var17.getX() + var19;
                     var8 = (double)((float)var17.getY() + 0.1F) + var18.getBlockBoundsMaxY() - 1.0D;
                     var10 = (double)var17.getZ() + var21;
                  }

                  aLc.a(this.mc.world, EnumParticleTypes.WATER_DROP, (double)var17.getX() + var19, (double)((float)var17.getY() + 0.1F) + var18.getBlockBoundsMaxY(), (double)var17.getZ() + var21, 0.0D, 0.0D, 0.0D, new int[0]);
               }
            }
         }

         if(this.random.nextInt(3) < this.rainSoundCounter++) {
            this.rainSoundCounter = 0;
            if(var8 > (double)(var4.getY() + 1) && var3.getPrecipitationHeight(var4).getY() > MathHelper.floor_float((float)var4.getY())) {
               this.mc.world.playSound(var6, var8, var10, "ambient.weather.rain", 0.1F, 0.5F, false);
            } else {
               this.mc.world.playSound(var6, var8, var10, "ambient.weather.rain", 0.2F, 1.0F, false);
            }
         }
      }

   }

   protected void renderRainSnow(float var1) {
      if(Reflector.cq.d()) {
         WorldProvider var50 = this.mc.world.provider;
         Object var51 = Reflector.f(var50, Reflector.cq, new Object[0]);
         Reflector.g(var51, Reflector.k, new Object[]{Float.valueOf(var1), this.mc.world, this.mc});
      } else {
         float var2 = this.mc.world.getRainStrength(var1);
         if(var2 > 0.0F) {
            if(Config.aV()) {
               return;
            }

            this.enableLightmap();
            Entity var3 = this.mc.getRenderViewEntity();
            WorldClient var4 = this.mc.world;
            int var5 = MathHelper.floor_double(var3.posX);
            int var6 = MathHelper.floor_double(var3.posY);
            int var7 = MathHelper.floor_double(var3.posZ);
            Tessellator var8 = Tessellator.getInstance();
            WorldRenderer var9 = var8.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1F);
            double var10 = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)var1;
            double var12 = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var1;
            double var14 = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)var1;
            int var16 = MathHelper.floor_double(var12);
            byte var17 = 5;
            if(Config.as()) {
               var17 = 10;
            }

            byte var18 = -1;
            float var19 = (float)this.rendererUpdateCount + var1;
            var9.setTranslation(-var10, -var12, -var14);
            if(Config.as()) {
               var17 = 10;
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            BlockPos$MutableBlockPos var20 = new BlockPos$MutableBlockPos();

            for(int var21 = var7 - var17; var21 <= var7 + var17; ++var21) {
               for(int var22 = var5 - var17; var22 <= var5 + var17; ++var22) {
                  int var23 = (var21 - var7 + 16) * 32 + var22 - var5 + 16;
                  double var24 = (double)this.rainXCoords[var23] * 0.5D;
                  double var26 = (double)this.rainYCoords[var23] * 0.5D;
                  var20.func_181079_c(var22, 0, var21);
                  BiomeGenBase var28 = var4.getBiomeGenForCoords(var20);
                  if(var28.canSpawnLightningBolt() || var28.getEnableSnow()) {
                     int var29 = var4.getPrecipitationHeight(var20).getY();
                     int var30 = var6 - var17;
                     int var31 = var6 + var17;
                     if(var30 < var29) {
                        var30 = var29;
                     }

                     if(var31 < var29) {
                        var31 = var29;
                     }

                     int var32 = var29;
                     if(var29 < var16) {
                        var32 = var16;
                     }

                     if(var30 != var31) {
                        this.random.setSeed((long)var22 * (long)var22 * 3121L + (long)var22 * 45238971L ^ (long)var21 * (long)var21 * 418711L + (long)var21 * 13761L);
                        var20.func_181079_c(var22, var30, var21);
                        float var33 = var28.getFloatTemperature(var20);
                        if(!((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).isEnabled() || !((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).getWeather_mode().equalsIgnoreCase("Snowstorm") && !((Atmosphere)Novoline.getInstance().getModuleManager().getModule(Atmosphere.class)).getWeather_mode().equalsIgnoreCase("Snowfall")) {
                           boolean var52 = false;
                        } else {
                           boolean var10000 = true;
                        }

                        if(var4.getWorldChunkManager().getTemperatureAtHeight(var33, var29) >= 0.15F) {
                           ;
                        }

                        if(var18 != 1) {
                           var8.draw();
                           var18 = 1;
                           this.mc.getTextureManager().bindTexture(locationSnowPng);
                           var9.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        double var35 = (double)(((float)(this.rendererUpdateCount & 511) + var1) / 512.0F);
                        double var37 = this.random.nextDouble() + (double)var19 * 0.01D * (double)((float)this.random.nextGaussian());
                        double var39 = this.random.nextDouble() + (double)(var19 * (float)this.random.nextGaussian()) * 0.001D;
                        double var41 = (double)((float)var22 + 0.5F) - var3.posX;
                        double var43 = (double)((float)var21 + 0.5F) - var3.posZ;
                        float var45 = MathHelper.sqrt_double(var41 * var41 + var43 * var43) / (float)var17;
                        float var46 = ((1.0F - var45 * var45) * 0.3F + 0.5F) * var2;
                        var20.func_181079_c(var22, var32, var21);
                        int var47 = (var4.getCombinedLight(var20, 0) * 3 + 15728880) / 4;
                        int var48 = var47 >> 16 & '\uffff';
                        int var49 = var47 & '\uffff';
                        var9.pos((double)var22 - var24 + 0.5D, (double)var30, (double)var21 - var26 + 0.5D).tex(0.0D + var37, (double)var30 * 0.25D + var35 + var39).color(1.0F, 1.0F, 1.0F, var46).lightmap(var48, var49).endVertex();
                        var9.pos((double)var22 + var24 + 0.5D, (double)var30, (double)var21 + var26 + 0.5D).tex(1.0D + var37, (double)var30 * 0.25D + var35 + var39).color(1.0F, 1.0F, 1.0F, var46).lightmap(var48, var49).endVertex();
                        var9.pos((double)var22 + var24 + 0.5D, (double)var31, (double)var21 + var26 + 0.5D).tex(1.0D + var37, (double)var31 * 0.25D + var35 + var39).color(1.0F, 1.0F, 1.0F, var46).lightmap(var48, var49).endVertex();
                        var9.pos((double)var22 - var24 + 0.5D, (double)var31, (double)var21 - var26 + 0.5D).tex(0.0D + var37, (double)var31 * 0.25D + var35 + var39).color(1.0F, 1.0F, 1.0F, var46).lightmap(var48, var49).endVertex();
                     }
                  }
               }
            }

            var8.draw();
            var9.setTranslation(0.0D, 0.0D, 0.0D);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            this.disableLightmap();
         }

      }
   }

   public void setupOverlayRendering() {
      ScaledResolution var1 = new ScaledResolution(this.mc);
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
   }

   private void updateFogColor(float var1) {
      WorldClient var2 = this.mc.world;
      Entity var3 = this.mc.getRenderViewEntity();
      float var4 = 0.25F + 0.75F * (float)this.mc.gameSettings.renderDistanceChunks / 32.0F;
      var4 = 1.0F - (float)Math.pow((double)var4, 0.25D);
      Vec3 var5 = var2.getSkyColor(this.mc.getRenderViewEntity(), var1);
      var5 = CustomColors.getWorldSkyColor(var5, var2, this.mc.getRenderViewEntity(), var1);
      float var6 = (float)var5.xCoord;
      float var7 = (float)var5.yCoord;
      float var8 = (float)var5.zCoord;
      Vec3 var9 = var2.getFogColor(var1);
      var9 = CustomColors.getWorldFogColor(var9, var2, this.mc.getRenderViewEntity(), var1);
      this.fogColorRed = (float)var9.xCoord;
      this.fogColorGreen = (float)var9.yCoord;
      this.fogColorBlue = (float)var9.zCoord;
      if(this.mc.gameSettings.renderDistanceChunks >= 4) {
         double var10 = -1.0D;
         Vec3 var12 = MathHelper.sin(var2.getCelestialAngleRadians(var1)) > 0.0F?new Vec3(var10, 0.0D, 0.0D):new Vec3(1.0D, 0.0D, 0.0D);
         float var13 = (float)var3.getLook(var1).dotProduct(var12);
         if(var13 < 0.0F) {
            var13 = 0.0F;
         }

         if(var13 > 0.0F) {
            float[] var14 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(var1), var1);
            var13 = var13 * var14[3];
            this.fogColorRed = this.fogColorRed * (1.0F - var13) + var14[0] * var13;
            this.fogColorGreen = this.fogColorGreen * (1.0F - var13) + var14[1] * var13;
            this.fogColorBlue = this.fogColorBlue * (1.0F - var13) + var14[2] * var13;
         }
      }

      this.fogColorRed += (var6 - this.fogColorRed) * var4;
      this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
      this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
      float var24 = var2.getRainStrength(var1);
      if(var24 > 0.0F) {
         float var11 = 1.0F - var24 * 0.5F;
         float var26 = 1.0F - var24 * 0.4F;
         this.fogColorRed *= var11;
         this.fogColorGreen *= var11;
         this.fogColorBlue *= var26;
      }

      float var25 = var2.getThunderStrength(var1);
      if(var25 > 0.0F) {
         float var27 = 1.0F - var25 * 0.5F;
         this.fogColorRed *= var27;
         this.fogColorGreen *= var27;
         this.fogColorBlue *= var27;
      }

      Block var28 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, var3, var1);
      if(this.cloudFog) {
         Vec3 var30 = var2.getCloudColour(var1);
         this.fogColorRed = (float)var30.xCoord;
         this.fogColorGreen = (float)var30.yCoord;
         this.fogColorBlue = (float)var30.zCoord;
      } else if(var28.getMaterial() == Material.water) {
         float var31 = (float)EnchantmentHelper.getRespiration(var3) * 0.2F;
         if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
            var31 = var31 * 0.3F + 0.6F;
         }

         this.fogColorRed = 0.02F + var31;
         this.fogColorGreen = 0.02F + var31;
         this.fogColorBlue = 0.2F + var31;
         Vec3 var33 = CustomColors.getUnderwaterColor(this.mc.world, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0D, this.mc.getRenderViewEntity().posZ);
         this.fogColorRed = (float)var33.xCoord;
         this.fogColorGreen = (float)var33.yCoord;
         this.fogColorBlue = (float)var33.zCoord;
      } else if(var28.getMaterial() == Material.lava) {
         this.fogColorRed = 0.6F;
         this.fogColorGreen = 0.1F;
         this.fogColorBlue = 0.0F;
      }

      float var32 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * var1;
      this.fogColorRed *= var32;
      this.fogColorGreen *= var32;
      this.fogColorBlue *= var32;
      double var34 = var2.provider.getVoidFogYFactor();
      double var16 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var1) * var34;
      if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
         int var18 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
         if(var18 < 20) {
            var16 *= (double)(1.0F - (float)var18 / 20.0F);
         } else {
            var16 = 0.0D;
         }
      }

      if(var16 < 1.0D) {
         if(var16 < 0.0D) {
            var16 = 0.0D;
         }

         var16 = var16 * var16;
         this.fogColorRed = (float)((double)this.fogColorRed * var16);
         this.fogColorGreen = (float)((double)this.fogColorGreen * var16);
         this.fogColorBlue = (float)((double)this.fogColorBlue * var16);
      }

      if(this.bossColorModifier > 0.0F) {
         float var36 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * var1;
         this.fogColorRed = this.fogColorRed * (1.0F - var36) + this.fogColorRed * 0.7F * var36;
         this.fogColorGreen = this.fogColorGreen * (1.0F - var36) + this.fogColorGreen * 0.6F * var36;
         this.fogColorBlue = this.fogColorBlue * (1.0F - var36) + this.fogColorBlue * 0.6F * var36;
      }

      if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.nightVision)) {
         float var37 = this.getNightVisionBrightness((EntityLivingBase)var3, var1);
         float var19 = 1.0F / this.fogColorRed;
         if(var19 > 1.0F / this.fogColorGreen) {
            var19 = 1.0F / this.fogColorGreen;
         }

         if(var19 > 1.0F / this.fogColorBlue) {
            var19 = 1.0F / this.fogColorBlue;
         }

         this.fogColorRed = this.fogColorRed * (1.0F - var37) + this.fogColorRed * var19 * var37;
         this.fogColorGreen = this.fogColorGreen * (1.0F - var37) + this.fogColorGreen * var19 * var37;
         this.fogColorBlue = this.fogColorBlue * (1.0F - var37) + this.fogColorBlue * var19 * var37;
      }

      if(this.mc.gameSettings.anaglyph) {
         float var38 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
         float var40 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
         float var20 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
         this.fogColorRed = var38;
         this.fogColorGreen = var40;
         this.fogColorBlue = var20;
      }

      if(Reflector.aY.b()) {
         Object var39 = Reflector.b(Reflector.aY, new Object[]{this, var3, var28, Float.valueOf(var1), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue)});
         Reflector.postForgeBusEvent(var39);
         this.fogColorRed = Reflector.getFieldValueFloat(var39, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
         this.fogColorGreen = Reflector.getFieldValueFloat(var39, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
         this.fogColorBlue = Reflector.getFieldValueFloat(var39, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
      }

      Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
   }

   private void setupFog(int var1, float var2) {
      Entity var3 = this.mc.getRenderViewEntity();
      boolean var4 = false;
      this.fogStandard = false;
      GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Block var5 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, var3, var2);
      float var6 = -1.0F;
      if(Reflector.cT.d()) {
         var6 = Reflector.g(Reflector.cT, new Object[]{this, var3, var5, Float.valueOf(var2), Float.valueOf(0.1F)});
      }

      if(var6 >= 0.0F) {
         GlStateManager.setFogDensity(var6);
      } else if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
         float var9 = 5.0F;
         int var8 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
         if(var8 < 20) {
            var9 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - (float)var8 / 20.0F);
         }

         if(Config.isShaders()) {
            Shaders.setFog(9729);
         } else {
            GlStateManager.setFog(9729);
         }

         if(var1 == -1) {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(var9 * 0.8F);
         } else {
            GlStateManager.setFogStart(var9 * 0.25F);
            GlStateManager.setFogEnd(var9);
         }

         if(GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
            GL11.glFogi('蕚', '蕛');
         }
      } else if(this.cloudFog) {
         if(Config.isShaders()) {
            Shaders.setFog(2048);
         } else {
            GlStateManager.setFog(2048);
         }

         GlStateManager.setFogDensity(0.1F);
      } else if(var5.getMaterial() == Material.water) {
         if(Config.isShaders()) {
            Shaders.setFog(2048);
         } else {
            GlStateManager.setFog(2048);
         }

         if(var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
            GlStateManager.setFogDensity(0.01F);
         } else {
            GlStateManager.setFogDensity(0.1F - (float)EnchantmentHelper.getRespiration(var3) * 0.03F);
         }

         if(Config.isClearWater()) {
            GlStateManager.setFogDensity(0.02F);
         }
      } else if(var5.getMaterial() == Material.lava) {
         if(Config.isShaders()) {
            Shaders.setFog(2048);
         } else {
            GlStateManager.setFog(2048);
         }

         GlStateManager.setFogDensity(2.0F);
      } else {
         float var7 = this.farPlaneDistance;
         this.fogStandard = true;
         if(Config.isShaders()) {
            Shaders.setFog(9729);
         } else {
            GlStateManager.setFog(9729);
         }

         if(var1 == -1) {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(var7);
         } else {
            GlStateManager.setFogStart(var7 * Config.getFogStart());
            GlStateManager.setFogEnd(var7);
         }

         if(GLContext.getCapabilities().GL_NV_fog_distance) {
            if(Config.isFogFancy()) {
               GL11.glFogi('蕚', '蕛');
            }

            if(Config.ad()) {
               GL11.glFogi('蕚', '蕜');
            }
         }

         if(this.mc.world.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
            GlStateManager.setFogStart(var7 * 0.05F);
            GlStateManager.setFogEnd(var7);
         }

         if(Reflector.bI.d()) {
            Reflector.a(Reflector.bI, new Object[]{this, var3, var5, Float.valueOf(var2), Integer.valueOf(var1), Float.valueOf(var7)});
         }
      }

      GlStateManager.enableColorMaterial();
      GlStateManager.enableFog();
      GlStateManager.colorMaterial(1028, 4608);
   }

   private FloatBuffer setFogColorBuffer(float var1, float var2, float var3, float var4) {
      if(Config.isShaders()) {
         Shaders.setFogColor(var1, var2, var3);
      }

      this.fogColorBuffer.clear();
      this.fogColorBuffer.put(var1).put(var2).put(var3).put(var4);
      this.fogColorBuffer.flip();
      return this.fogColorBuffer;
   }

   public MapItemRenderer getMapItemRenderer() {
      return this.theMapItemRenderer;
   }

   private void waitForServerThread() {
      this.serverWaitTimeCurrent = 0;
      if(Config.isSmoothWorld() && Config.isSingleProcessor()) {
         if(this.mc.isIntegratedServerRunning()) {
            IntegratedServer var1 = this.mc.getIntegratedServer();
            boolean var2 = this.mc.isGamePaused();
            if(!(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
               if(this.serverWaitTime > 0) {
                  Lagometer.timerServer.start();
                  Config.sleep((long)this.serverWaitTime);
                  Lagometer.timerServer.end();
                  this.serverWaitTimeCurrent = this.serverWaitTime;
               }

               long var3 = System.nanoTime() / 1000000L;
               if(this.lastServerTime != 0L && this.lastServerTicks != 0) {
                  long var5 = var3 - this.lastServerTime;
                  if(var5 < 0L) {
                     this.lastServerTime = var3;
                     var5 = 0L;
                  }

                  if(var5 >= 50L) {
                     this.lastServerTime = var3;
                     int var7 = var1.getTickCounter();
                     int var8 = var7 - this.lastServerTicks;
                     this.lastServerTicks = var7;
                     var8 = 0;
                     if(var8 < 1 && this.serverWaitTime < 100) {
                        this.serverWaitTime += 2;
                     }

                     if(var8 > 1 && this.serverWaitTime > 0) {
                        --this.serverWaitTime;
                     }

                     this.lastServerTicks = var7;
                  }
               } else {
                  this.lastServerTime = var3;
                  this.lastServerTicks = var1.getTickCounter();
                  this.avgServerTickDiff = 1.0F;
                  this.avgServerTimeDiff = 50.0F;
               }
            } else {
               if(this.mc.currentScreen instanceof GuiDownloadTerrain) {
                  Config.sleep(20L);
               }

               this.lastServerTime = 0L;
               this.lastServerTicks = 0;
            }
         }
      } else {
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
      }

   }

   private void frameInit() {
      if(!this.initialized) {
         TextureUtils.registerResourceListener();
         if(Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
            Config.setNotify64BitJava(true);
         }

         this.initialized = true;
      }

      Config.checkDisplayMode();
      WorldClient var1 = this.mc.world;
      if(Config.isNotify64BitJava()) {
         Config.setNotify64BitJava(false);
         ChatComponentText var2 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
         this.mc.ingameGUI.n().a((IChatComponent)var2);
      }

      if(this.mc.currentScreen instanceof aHv) {
         this.a((aHv)this.mc.currentScreen);
      }

      if(this.updatedWorld != var1) {
         RandomMobs.worldChanged(this.updatedWorld, var1);
         Config.updateThreadPriorities();
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
         this.updatedWorld = var1;
      }

      if(!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
         Shaders.configAntialiasingLevel = 0;
      }

   }

   private void frameFinish() {
      if(this.mc.world != null) {
         long var1 = System.currentTimeMillis();
         if(var1 > this.lastErrorCheckTimeMs + 10000L) {
            this.lastErrorCheckTimeMs = var1;
            int var3 = GL11.glGetError();
            String var4 = GLU.gluErrorString(var3);
            ChatComponentText var5 = new ChatComponentText(I18n.format("of.message.openglError", new Object[]{Integer.valueOf(var3), var4}));
            this.mc.ingameGUI.n().a((IChatComponent)var5);
         }
      }

   }

   private void a(aHv var1) {
      String var2 = null;

      try {
         Calendar var3 = Calendar.getInstance();
         var3.setTime(new Date());
         int var4 = var3.get(5);
         int var5 = var3.get(2) + 1;
         if(var4 == 8 && var5 == 4) {
            var2 = "Happy birthday, OptiFine!";
         }

         if(var4 == 14 && var5 == 8) {
            var2 = "Happy birthday, sp614x!";
         }

      } catch (Throwable var6) {
         ;
      }
   }

   public boolean setFxaaShader(int var1) {
      if(!OpenGlHelper.isFramebufferEnabled()) {
         return false;
      } else if(this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4]) {
         return true;
      } else if(var1 != 2 && var1 != 4) {
         if(this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
         }

         return true;
      } else if(this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[var1]) {
         return true;
      } else if(this.mc.world == null) {
         return true;
      } else {
         this.loadShader(new ResourceLocation("shaders/post/fxaa_of_" + var1 + "x.json"));
         this.fxaaShaders[var1] = this.theShaderGroup;
         return this.useShader;
      }
   }

   private String lambda$func_181560_a$1(ScaledResolution var1) throws Exception {
      return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[]{Integer.valueOf(var1.getScaledWidth()), Integer.valueOf(var1.getScaledHeight()), Integer.valueOf(this.mc.displayWidth), Integer.valueOf(this.mc.displayHeight), Integer.valueOf(var1.getScaleFactor())});
   }

   private static String lambda$func_181560_a$0(int var0, int var1) throws Exception {
      return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[]{Integer.valueOf(var0), Integer.valueOf(var1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY())});
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
