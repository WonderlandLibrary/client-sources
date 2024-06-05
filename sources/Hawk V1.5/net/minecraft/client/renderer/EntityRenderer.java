package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import hawk.Client;
import hawk.events.listeners.EventRender3D;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.CustomColors;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class EntityRenderer implements IResourceManagerReloadListener {
   private float thirdPersonDistanceTemp = 4.0F;
   private Random random = new Random();
   private final MapItemRenderer theMapItemRenderer;
   private float thirdPersonDistance = 4.0F;
   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
   private float avgServerTimeDiff = 0.0F;
   private double cameraZoom = 1.0D;
   private boolean field_175078_W = false;
   private float smoothCamFilterY;
   private MouseFilter mouseFilterYAxis = new MouseFilter();
   private float smoothCamFilterX;
   private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
   private boolean field_175074_C = true;
   private boolean initialized = false;
   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
   private boolean field_175083_ad;
   private int serverWaitTime = 0;
   private float fovModifierHand;
   private float fogColor2;
   private final ResourceLocation locationLightMap;
   private final IResourceManager resourceManager;
   private float torchFlickerX;
   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
   private double cameraPitch;
   private float fogColor1;
   private static final String __OBFID = "CL_00000947";
   private float field_175075_L;
   private boolean cloudFog;
   private long renderEndNanoTime;
   private MouseFilter mouseFilterXAxis = new MouseFilter();
   private float clipDistance = 128.0F;
   private long prevFrameTime = Minecraft.getSystemTime();
   public float field_175080_Q;
   public static boolean anaglyphEnable;
   private boolean field_175073_D = true;
   private float[] field_175076_N = new float[1024];
   public float field_175082_R;
   private float avgServerTickDiff = 0.0F;
   private float smoothCamYaw;
   public float field_175081_S;
   private float fovModifierHandPrev;
   private float[] field_175077_O = new float[1024];
   private static final Logger logger = LogManager.getLogger();
   private float bossColorModifier;
   private float smoothCamPartialTicks;
   public ItemRenderer itemRenderer;
   private float bossColorModifierPrev;
   public static final int shaderCount;
   private final DynamicTexture lightmapTexture;
   private int rendererUpdateCount;
   private float smoothCamPitch;
   private double cameraYaw;
   private World updatedWorld = null;
   private boolean lightmapUpdateNeeded;
   public int field_175084_ae;
   private long lastServerTime = 0L;
   private final int[] lightmapColors;
   private Entity pointedEntity;
   private int field_175079_V = 0;
   public boolean fogStandard = false;
   private int lastServerTicks = 0;
   public static int anaglyphField;
   private ShaderGroup[] fxaaShaders = new ShaderGroup[10];
   private int rainSoundCounter;
   private int shaderIndex;
   private int serverWaitTimeCurrent = 0;
   private boolean showDebugInfo = false;
   private Minecraft mc;
   private float farPlaneDistance;
   private ShaderGroup theShaderGroup;
   private long lastErrorCheckTimeMs = 0L;

   private void func_175069_a(ResourceLocation var1) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         try {
            this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), var1);
            this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.field_175083_ad = true;
         } catch (IOException var3) {
            logger.warn(String.valueOf((new StringBuilder("Failed to load shader: ")).append(var1)), var3);
            this.shaderIndex = shaderCount;
            this.field_175083_ad = false;
         } catch (JsonSyntaxException var4) {
            logger.warn(String.valueOf((new StringBuilder("Failed to load shader: ")).append(var1)), var4);
            this.shaderIndex = shaderCount;
            this.field_175083_ad = false;
         }
      }

   }

   public void getMouseOver(float var1) {
      Entity var2 = this.mc.func_175606_aa();
      if (var2 != null && this.mc.theWorld != null) {
         this.mc.mcProfiler.startSection("pick");
         this.mc.pointedEntity = null;
         double var3 = (double)this.mc.playerController.getBlockReachDistance();
         this.mc.objectMouseOver = var2.func_174822_a(var3, var1);
         double var5 = var3;
         Vec3 var7 = var2.func_174824_e(var1);
         if (this.mc.playerController.extendedReach()) {
            var3 = 6.0D;
            var5 = 6.0D;
         } else {
            if (var3 > 3.0D) {
               var5 = 3.0D;
            }

            var3 = var5;
         }

         if (this.mc.objectMouseOver != null) {
            var5 = this.mc.objectMouseOver.hitVec.distanceTo(var7);
         }

         Vec3 var8 = var2.getLook(var1);
         Vec3 var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
         this.pointedEntity = null;
         Vec3 var10 = null;
         float var11 = 1.0F;
         List var12 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand((double)var11, (double)var11, (double)var11));
         double var13 = var5;

         for(int var15 = 0; var15 < var12.size(); ++var15) {
            Entity var16 = (Entity)var12.get(var15);
            if (var16.canBeCollidedWith()) {
               float var17 = var16.getCollisionBorderSize();
               AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double)var17, (double)var17, (double)var17);
               MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
               if (var18.isVecInside(var7)) {
                  if (0.0D < var13 || var13 == 0.0D) {
                     this.pointedEntity = var16;
                     var10 = var19 == null ? var7 : var19.hitVec;
                     var13 = 0.0D;
                  }
               } else if (var19 != null) {
                  double var20 = var7.distanceTo(var19.hitVec);
                  if (var20 < var13 || var13 == 0.0D) {
                     boolean var22 = false;
                     if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        var22 = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract);
                     }

                     if (var16 == var2.ridingEntity && !var22) {
                        if (var13 == 0.0D) {
                           this.pointedEntity = var16;
                           var10 = var19.hitVec;
                        }
                     } else {
                        this.pointedEntity = var16;
                        var10 = var19.hitVec;
                        var13 = var20;
                     }
                  }
               }
            }
         }

         if (this.pointedEntity != null && (var13 < var5 || this.mc.objectMouseOver == null)) {
            this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var10);
            if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
               this.mc.pointedEntity = this.pointedEntity;
            }
         }

         this.mc.mcProfiler.endSection();
      }

   }

   public void updateCameraAndRender(float var1) {
      this.frameInit();
      boolean var2 = Display.isActive();
      if (var2 || !this.mc.gameSettings.pauseOnLostFocus || this.mc.gameSettings.touchscreen && Mouse.isButtonDown(1)) {
         this.prevFrameTime = Minecraft.getSystemTime();
      } else if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
         this.mc.displayInGameMenu();
      }

      this.mc.mcProfiler.startSection("mouse");
      if (var2 && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
         Mouse.setGrabbed(false);
         Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
         Mouse.setGrabbed(true);
      }

      if (this.mc.inGameHasFocus && var2) {
         this.mc.mouseHelper.mouseXYChange();
         float var3 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var4 = var3 * var3 * var3 * 8.0F;
         float var5 = (float)this.mc.mouseHelper.deltaX * var4;
         float var6 = (float)this.mc.mouseHelper.deltaY * var4;
         byte var7 = 1;
         if (this.mc.gameSettings.invertMouse) {
            var7 = -1;
         }

         if (this.mc.gameSettings.smoothCamera) {
            this.smoothCamYaw += var5;
            this.smoothCamPitch += var6;
            float var8 = var1 - this.smoothCamPartialTicks;
            this.smoothCamPartialTicks = var1;
            var5 = this.smoothCamFilterX * var8;
            var6 = this.smoothCamFilterY * var8;
            this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
         } else {
            this.smoothCamYaw = 0.0F;
            this.smoothCamPitch = 0.0F;
            this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
         }
      }

      this.mc.mcProfiler.endSection();
      if (!this.mc.skipRenderWorld) {
         anaglyphEnable = this.mc.gameSettings.anaglyph;
         ScaledResolution var13 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
         int var14 = var13.getScaledWidth();
         int var15 = var13.getScaledHeight();
         int var16 = Mouse.getX() * var14 / this.mc.displayWidth;
         int var17 = var15 - Mouse.getY() * var15 / this.mc.displayHeight - 1;
         int var18 = this.mc.gameSettings.limitFramerate;
         if (this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("level");
            int var9 = Math.max(Minecraft.func_175610_ah(), 30);
            this.renderWorld(var1, this.renderEndNanoTime + (long)(1000000000 / var9));
            if (OpenGlHelper.shadersSupported) {
               this.mc.renderGlobal.func_174975_c();
               if (this.theShaderGroup != null && this.field_175083_ad) {
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
            if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
               GlStateManager.alphaFunc(516, 0.1F);
               this.mc.ingameGUI.func_175180_a(var1);
               if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                  Config.drawFps();
               }

               if (this.mc.gameSettings.showDebugInfo) {
                  Lagometer.showLagometer(var13);
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
         }

         if (this.mc.currentScreen != null) {
            GlStateManager.clear(256);

            try {
               if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                  Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, this.mc.currentScreen, var16, var17, var1);
               } else {
                  this.mc.currentScreen.drawScreen(var16, var17, var1);
               }
            } catch (Throwable var12) {
               CrashReport var10 = CrashReport.makeCrashReport(var12, "Rendering screen");
               CrashReportCategory var11 = var10.makeCategory("Screen render details");
               var11.addCrashSectionCallable("Screen name", new Callable(this) {
                  final EntityRenderer this$0;
                  private static final String __OBFID = "CL_00000948";

                  {
                     this.this$0 = var1;
                  }

                  public Object call() throws Exception {
                     return this.call();
                  }

                  public String call() {
                     return EntityRenderer.access$0(this.this$0).currentScreen.getClass().getCanonicalName();
                  }
               });
               var11.addCrashSectionCallable("Mouse location", new Callable(this, var16, var17) {
                  final EntityRenderer this$0;
                  private static final String __OBFID = "CL_00000950";
                  private final int val$var161;
                  private final int val$var171;

                  public Object call() throws Exception {
                     return this.call();
                  }

                  public String call() {
                     return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", this.val$var161, this.val$var171, Mouse.getX(), Mouse.getY());
                  }

                  {
                     this.this$0 = var1;
                     this.val$var161 = var2;
                     this.val$var171 = var3;
                  }
               });
               var11.addCrashSectionCallable("Screen size", new Callable(this, var13) {
                  final EntityRenderer this$0;
                  private static final String __OBFID = "CL_00000951";
                  private final ScaledResolution val$var131;

                  public Object call() throws Exception {
                     return this.call();
                  }

                  {
                     this.this$0 = var1;
                     this.val$var131 = var2;
                  }

                  public String call() {
                     return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", this.val$var131.getScaledWidth(), this.val$var131.getScaledHeight(), EntityRenderer.access$0(this.this$0).displayWidth, EntityRenderer.access$0(this.this$0).displayHeight, this.val$var131.getScaleFactor());
                  }
               });
               throw new ReportedException(var10);
            }
         }
      }

      this.frameFinish();
      this.waitForServerThread();
      Lagometer.updateLagometer();
      if (this.mc.gameSettings.ofProfiler) {
         this.mc.gameSettings.showDebugProfilerChart = true;
      }

   }

   private void hurtCameraEffect(float var1) {
      if (this.mc.func_175606_aa() instanceof EntityLivingBase) {
         EntityLivingBase var2 = (EntityLivingBase)this.mc.func_175606_aa();
         float var3 = (float)var2.hurtTime - var1;
         float var4;
         if (var2.getHealth() <= 0.0F) {
            var4 = (float)var2.deathTime + var1;
            GlStateManager.rotate(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
         }

         if (var3 < 0.0F) {
            return;
         }

         var3 /= (float)var2.maxHurtTime;
         var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927F);
         var4 = var2.attackedAtYaw;
         GlStateManager.rotate(-var4, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(var4, 0.0F, 1.0F, 0.0F);
      }

   }

   private void updateFovModifierHand() {
      float var1 = 1.0F;
      if (this.mc.func_175606_aa() instanceof AbstractClientPlayer) {
         AbstractClientPlayer var2 = (AbstractClientPlayer)this.mc.func_175606_aa();
         var1 = var2.func_175156_o();
      }

      this.fovModifierHandPrev = this.fovModifierHand;
      this.fovModifierHand += (var1 - this.fovModifierHand) * 0.5F;
      if (this.fovModifierHand > 1.5F) {
         this.fovModifierHand = 1.5F;
      }

      if (this.fovModifierHand < 0.1F) {
         this.fovModifierHand = 0.1F;
      }

   }

   public void renderHand(float var1, int var2) {
      if (!this.field_175078_W) {
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         float var3 = 0.07F;
         if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(-(var2 * 2 - 1)) * var3, 0.0F, 0.0F);
         }

         if (Config.isShaders()) {
            Shaders.applyHandDepth();
         }

         Project.gluPerspective(this.getFOVModifier(var1, false), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
         }

         boolean var4 = false;
         if (!Config.isShaders() || !Shaders.isHandRendered) {
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(var1);
            if (this.mc.gameSettings.viewBobbing) {
               this.setupViewBobbing(var1);
            }

            var4 = this.mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.func_175606_aa()).isPlayerSleeping();
            if (this.mc.gameSettings.thirdPersonView == 0 && !var4 && !this.mc.gameSettings.hideGUI && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
               this.func_180436_i();
               if (Config.isShaders()) {
                  ShadersRender.renderItemFP(this.itemRenderer, var1);
               } else {
                  this.itemRenderer.renderItemInFirstPerson(var1);
               }

               this.func_175072_h();
            }

            GlStateManager.popMatrix();
         }

         if (Config.isShaders() && !Shaders.isCompositeRendered) {
            return;
         }

         this.func_175072_h();
         if (this.mc.gameSettings.thirdPersonView == 0 && !var4) {
            this.itemRenderer.renderOverlays(var1);
            this.hurtCameraEffect(var1);
         }

         if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(var1);
         }
      }

   }

   public void stopUseShader() {
      if (this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      this.shaderIndex = shaderCount;
   }

   private void func_175067_i(float var1) {
      if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.func_175140_cp() && !this.mc.gameSettings.field_178879_v) {
         Entity var2 = this.mc.func_175606_aa();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glLineWidth(1.0F);
         GlStateManager.func_179090_x();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         this.orientCamera(var1);
         GlStateManager.translate(0.0F, var2.getEyeHeight(), 0.0F);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), -65536);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), -16776961);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), -16711936);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.func_179098_w();
         GlStateManager.disableBlend();
      }

   }

   public void renderWorld(float var1, long var2) {
      this.updateLightmap(var1);
      if (this.mc.func_175606_aa() == null) {
         this.mc.func_175607_a(this.mc.thePlayer);
      }

      this.getMouseOver(var1);
      if (Config.isShaders()) {
         Shaders.beginRender(this.mc, var1, var2);
      }

      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.mc.mcProfiler.startSection("center");
      if (this.mc.gameSettings.anaglyph) {
         anaglyphField = 0;
         GlStateManager.colorMask(false, true, true, false);
         this.func_175068_a(0, var1, var2);
         anaglyphField = 1;
         GlStateManager.colorMask(true, false, false, false);
         this.func_175068_a(1, var1, var2);
         GlStateManager.colorMask(true, true, true, false);
      } else {
         this.func_175068_a(2, var1, var2);
      }

      this.mc.mcProfiler.endSection();
   }

   public void func_180436_i() {
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
      GlStateManager.func_179098_w();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.enableLightmap();
      }

   }

   public void setupOverlayRendering() {
      ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
   }

   private void addRainParticles() {
      float var1 = this.mc.theWorld.getRainStrength(1.0F);
      if (!Config.isRainFancy()) {
         var1 /= 2.0F;
      }

      if (var1 != 0.0F && Config.isRainSplash()) {
         this.random.setSeed((long)this.rendererUpdateCount * 312987231L);
         Entity var2 = this.mc.func_175606_aa();
         WorldClient var3 = this.mc.theWorld;
         BlockPos var4 = new BlockPos(var2);
         byte var5 = 10;
         double var6 = 0.0D;
         double var8 = 0.0D;
         double var10 = 0.0D;
         int var12 = 0;
         int var13 = (int)(100.0F * var1 * var1);
         if (this.mc.gameSettings.particleSetting == 1) {
            var13 >>= 1;
         } else if (this.mc.gameSettings.particleSetting == 2) {
            var13 = 0;
         }

         for(int var14 = 0; var14 < var13; ++var14) {
            BlockPos var15 = var3.func_175725_q(var4.add(this.random.nextInt(var5) - this.random.nextInt(var5), 0, this.random.nextInt(var5) - this.random.nextInt(var5)));
            BiomeGenBase var16 = var3.getBiomeGenForCoords(var15);
            BlockPos var17 = var15.offsetDown();
            Block var18 = var3.getBlockState(var17).getBlock();
            if (var15.getY() <= var4.getY() + var5 && var15.getY() >= var4.getY() - var5 && var16.canSpawnLightningBolt() && var16.func_180626_a(var15) >= 0.15F) {
               float var19 = this.random.nextFloat();
               float var20 = this.random.nextFloat();
               if (var18.getMaterial() == Material.lava) {
                  this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)((float)var15.getX() + var19), (double)((float)var15.getY() + 0.1F) - var18.getBlockBoundsMinY(), (double)((float)var15.getZ() + var20), 0.0D, 0.0D, 0.0D, new int[0]);
               } else if (var18.getMaterial() != Material.air) {
                  var18.setBlockBoundsBasedOnState(var3, var17);
                  ++var12;
                  if (this.random.nextInt(var12) == 0) {
                     var6 = (double)((float)var17.getX() + var19);
                     var8 = (double)((float)var17.getY() + 0.1F) + var18.getBlockBoundsMaxY() - 1.0D;
                     var10 = (double)((float)var17.getZ() + var20);
                  }

                  this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, (double)((float)var17.getX() + var19), (double)((float)var17.getY() + 0.1F) + var18.getBlockBoundsMaxY(), (double)((float)var17.getZ() + var20), 0.0D, 0.0D, 0.0D, new int[0]);
               }
            }
         }

         if (var12 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
            this.rainSoundCounter = 0;
            if (var8 > (double)(var4.getY() + 1) && var3.func_175725_q(var4).getY() > MathHelper.floor_float((float)var4.getY())) {
               this.mc.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.1F, 0.5F, false);
            } else {
               this.mc.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.2F, 1.0F, false);
            }
         }
      }

   }

   private boolean func_175070_n() {
      if (!this.field_175073_D) {
         return false;
      } else {
         Entity var1 = this.mc.func_175606_aa();
         boolean var2 = var1 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
         if (var2 && !((EntityPlayer)var1).capabilities.allowEdit) {
            ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
               BlockPos var4 = this.mc.objectMouseOver.func_178782_a();
               IBlockState var5 = this.mc.theWorld.getBlockState(var4);
               Block var6 = var5.getBlock();
               if (this.mc.playerController.func_178889_l() == WorldSettings.GameType.SPECTATOR) {
                  var2 = ReflectorForge.blockHasTileEntity(var5) && this.mc.theWorld.getTileEntity(var4) instanceof IInventory;
               } else {
                  var2 = var3 != null && (var3.canDestroy(var6) || var3.canPlaceOn(var6));
               }
            }
         }

         return var2;
      }
   }

   public void activateNextShader() {
      if (OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
         if (this.shaderIndex != shaderCount) {
            this.func_175069_a(shaderResourceLocations[this.shaderIndex]);
         } else {
            this.theShaderGroup = null;
         }
      }

   }

   public void func_175072_h() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.func_179090_x();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.disableLightmap();
      }

   }

   public void func_175066_a(Entity var1) {
      if (OpenGlHelper.shadersSupported) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.theShaderGroup = null;
         if (var1 instanceof EntityCreeper) {
            this.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
         } else if (var1 instanceof EntitySpider) {
            this.func_175069_a(new ResourceLocation("shaders/post/spider.json"));
         } else if (var1 instanceof EntityEnderman) {
            this.func_175069_a(new ResourceLocation("shaders/post/invert.json"));
         } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, var1, this);
         }
      }

   }

   static Minecraft access$0(EntityRenderer var0) {
      return var0.mc;
   }

   private void frameInit() {
      if (!this.initialized) {
         TextureUtils.registerResourceListener();
         if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
            Config.setNotify64BitJava(true);
         }

         this.initialized = true;
      }

      Config.checkDisplayMode();
      WorldClient var1 = this.mc.theWorld;
      if (var1 != null) {
         if (Config.getNewRelease() != null) {
            String var2 = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
            String var3 = String.valueOf((new StringBuilder(String.valueOf(var2))).append(" ").append(Config.getNewRelease()));
            ChatComponentText var4 = new ChatComponentText(I18n.format("of.message.newVersion", var3));
            this.mc.ingameGUI.getChatGUI().printChatMessage(var4);
            Config.setNewRelease((String)null);
         }

         if (Config.isNotify64BitJava()) {
            Config.setNotify64BitJava(false);
            ChatComponentText var5 = new ChatComponentText(I18n.format("of.message.java64Bit"));
            this.mc.ingameGUI.getChatGUI().printChatMessage(var5);
         }
      }

      if (this.mc.currentScreen instanceof GuiMainMenu) {
         this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
      }

      if (this.updatedWorld != var1) {
         RandomMobs.worldChanged(this.updatedWorld, var1);
         Config.updateThreadPriorities();
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
         this.updatedWorld = var1;
      }

      if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
         Shaders.configAntialiasingLevel = 0;
      }

   }

   private void updateTorchFlicker() {
      this.field_175075_L = (float)((double)this.field_175075_L + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.field_175075_L = (float)((double)this.field_175075_L * 0.9D);
      this.torchFlickerX += (this.field_175075_L - this.torchFlickerX) * 1.0F;
      this.lightmapUpdateNeeded = true;
   }

   public void updateRenderer() {
      if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
         ShaderLinkHelper.setNewStaticShaderLinkHelper();
      }

      this.updateFovModifierHand();
      this.updateTorchFlicker();
      this.fogColor2 = this.fogColor1;
      this.thirdPersonDistanceTemp = this.thirdPersonDistance;
      float var1;
      float var2;
      if (this.mc.gameSettings.smoothCamera) {
         var1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         var2 = var1 * var1 * var1 * 8.0F;
         this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * var2);
         this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * var2);
         this.smoothCamPartialTicks = 0.0F;
         this.smoothCamYaw = 0.0F;
         this.smoothCamPitch = 0.0F;
      } else {
         this.smoothCamFilterX = 0.0F;
         this.smoothCamFilterY = 0.0F;
         this.mouseFilterXAxis.func_180179_a();
         this.mouseFilterYAxis.func_180179_a();
      }

      if (this.mc.func_175606_aa() == null) {
         this.mc.func_175607_a(this.mc.thePlayer);
      }

      Entity var3 = this.mc.func_175606_aa();
      double var4 = var3.posX;
      double var6 = var3.posY + (double)var3.getEyeHeight();
      double var8 = var3.posZ;
      var1 = this.mc.theWorld.getLightBrightness(new BlockPos(var4, var6, var8));
      var2 = (float)this.mc.gameSettings.renderDistanceChunks / 16.0F;
      var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
      float var10 = var1 * (1.0F - var2) + var2;
      this.fogColor1 += (var10 - this.fogColor1) * 0.1F;
      ++this.rendererUpdateCount;
      this.itemRenderer.updateEquippedItem();
      this.addRainParticles();
      this.bossColorModifierPrev = this.bossColorModifier;
      if (BossStatus.hasColorModifier) {
         this.bossColorModifier += 0.05F;
         if (this.bossColorModifier > 1.0F) {
            this.bossColorModifier = 1.0F;
         }

         BossStatus.hasColorModifier = false;
      } else if (this.bossColorModifier > 0.0F) {
         this.bossColorModifier -= 0.0125F;
      }

   }

   static {
      shaderCount = shaderResourceLocations.length;
   }

   private void frameFinish() {
      if (this.mc.theWorld != null) {
         long var1 = System.currentTimeMillis();
         if (var1 > this.lastErrorCheckTimeMs + 10000L) {
            this.lastErrorCheckTimeMs = var1;
            int var3 = GL11.glGetError();
            if (var3 != 0) {
               String var4 = GLU.gluErrorString(var3);
               ChatComponentText var5 = new ChatComponentText(I18n.format("of.message.openglError", var3, var4));
               this.mc.ingameGUI.getChatGUI().printChatMessage(var5);
            }
         }
      }

   }

   public boolean setFxaaShader(int var1) {
      if (!OpenGlHelper.isFramebufferEnabled()) {
         return false;
      } else if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4]) {
         return true;
      } else if (var1 != 2 && var1 != 4) {
         if (this.theShaderGroup == null) {
            return true;
         } else {
            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
            return true;
         }
      } else if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[var1]) {
         return true;
      } else if (this.mc.theWorld == null) {
         return true;
      } else {
         this.func_175069_a(new ResourceLocation(String.valueOf((new StringBuilder("shaders/post/fxaa_of_")).append(var1).append("x.json"))));
         this.fxaaShaders[var1] = this.theShaderGroup;
         return this.field_175083_ad;
      }
   }

   public void setupCameraTransform(float var1, int var2) {
      this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
      if (Config.isFogFancy()) {
         this.farPlaneDistance *= 0.95F;
      }

      if (Config.isFogFast()) {
         this.farPlaneDistance *= 0.83F;
      }

      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      float var3 = 0.07F;
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((float)(-(var2 * 2 - 1)) * var3, 0.0F, 0.0F);
      }

      this.clipDistance = this.farPlaneDistance * 2.0F;
      if (this.clipDistance < 173.0F) {
         this.clipDistance = 173.0F;
      }

      if (this.mc.theWorld.provider.getDimensionId() == 1) {
         this.clipDistance = 256.0F;
      }

      if (this.cameraZoom != 1.0D) {
         GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
         GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
      }

      Project.gluPerspective(this.getFOVModifier(var1, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      this.hurtCameraEffect(var1);
      if (this.mc.gameSettings.viewBobbing) {
         this.setupViewBobbing(var1);
      }

      float var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * var1;
      if (var4 > 0.0F) {
         byte var5 = 20;
         if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            var5 = 7;
         }

         float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
         var6 *= var6;
         GlStateManager.rotate(((float)this.rendererUpdateCount + var1) * (float)var5, 0.0F, 1.0F, 1.0F);
         GlStateManager.scale(1.0F / var6, 1.0F, 1.0F);
         GlStateManager.rotate(-((float)this.rendererUpdateCount + var1) * (float)var5, 0.0F, 1.0F, 1.0F);
      }

      this.orientCamera(var1);
      if (this.field_175078_W) {
         switch(this.field_175079_V) {
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

   private void waitForServerThread() {
      this.serverWaitTimeCurrent = 0;
      if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
         if (this.mc.isIntegratedServerRunning()) {
            IntegratedServer var1 = this.mc.getIntegratedServer();
            if (var1 != null) {
               boolean var2 = this.mc.isGamePaused();
               if (!var2 && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                  if (this.serverWaitTime > 0) {
                     Lagometer.timerServer.start();
                     Config.sleep((long)this.serverWaitTime);
                     Lagometer.timerServer.end();
                     this.serverWaitTimeCurrent = this.serverWaitTime;
                  }

                  long var3 = System.nanoTime() / 1000000L;
                  if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                     long var5 = var3 - this.lastServerTime;
                     if (var5 < 0L) {
                        this.lastServerTime = var3;
                        var5 = 0L;
                     }

                     if (var5 >= 50L) {
                        this.lastServerTime = var3;
                        int var7 = var1.getTickCounter();
                        int var8 = var7 - this.lastServerTicks;
                        if (var8 < 0) {
                           this.lastServerTicks = var7;
                           var8 = 0;
                        }

                        if (var8 < 1 && this.serverWaitTime < 100) {
                           this.serverWaitTime += 2;
                        }

                        if (var8 > 1 && this.serverWaitTime > 0) {
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
                  if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                     Config.sleep(20L);
                  }

                  this.lastServerTime = 0L;
                  this.lastServerTicks = 0;
               }
            }
         }
      } else {
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
      }

   }

   private float func_180438_a(EntityLivingBase var1, float var2) {
      int var3 = var1.getActivePotionEffect(Potion.nightVision).getDuration();
      return var3 > 200 ? 1.0F : 0.7F + MathHelper.sin(((float)var3 - var2) * 3.1415927F * 0.2F) * 0.3F;
   }

   protected void renderRainSnow(float var1) {
      if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
         WorldProvider var2 = this.mc.theWorld.provider;
         Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getWeatherRenderer);
         if (var3 != null) {
            Reflector.callVoid(var3, Reflector.IRenderHandler_render, var1, this.mc.theWorld, this.mc);
            return;
         }
      }

      float var42 = this.mc.theWorld.getRainStrength(var1);
      if (var42 > 0.0F) {
         if (Config.isRainOff()) {
            return;
         }

         this.func_180436_i();
         Entity var43 = this.mc.func_175606_aa();
         WorldClient var4 = this.mc.theWorld;
         int var5 = MathHelper.floor_double(var43.posX);
         int var6 = MathHelper.floor_double(var43.posY);
         int var7 = MathHelper.floor_double(var43.posZ);
         Tessellator var8 = Tessellator.getInstance();
         WorldRenderer var9 = var8.getWorldRenderer();
         GlStateManager.disableCull();
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.alphaFunc(516, 0.1F);
         double var10 = var43.lastTickPosX + (var43.posX - var43.lastTickPosX) * (double)var1;
         double var12 = var43.lastTickPosY + (var43.posY - var43.lastTickPosY) * (double)var1;
         double var14 = var43.lastTickPosZ + (var43.posZ - var43.lastTickPosZ) * (double)var1;
         int var16 = MathHelper.floor_double(var12);
         byte var17 = 5;
         if (Config.isRainFancy()) {
            var17 = 10;
         }

         byte var18 = -1;
         float var19 = (float)this.rendererUpdateCount + var1;
         if (Config.isRainFancy()) {
            var17 = 10;
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

         for(int var20 = var7 - var17; var20 <= var7 + var17; ++var20) {
            for(int var21 = var5 - var17; var21 <= var5 + var17; ++var21) {
               int var22 = (var20 - var7 + 16) * 32 + var21 - var5 + 16;
               float var23 = this.field_175076_N[var22] * 0.5F;
               float var24 = this.field_175077_O[var22] * 0.5F;
               BlockPos var25 = new BlockPos(var21, 0, var20);
               BiomeGenBase var26 = var4.getBiomeGenForCoords(var25);
               if (var26.canSpawnLightningBolt() || var26.getEnableSnow()) {
                  int var27 = var4.func_175725_q(var25).getY();
                  int var28 = var6 - var17;
                  int var29 = var6 + var17;
                  if (var28 < var27) {
                     var28 = var27;
                  }

                  if (var29 < var27) {
                     var29 = var27;
                  }

                  float var30 = 1.0F;
                  int var31 = var27;
                  if (var27 < var16) {
                     var31 = var16;
                  }

                  if (var28 != var29) {
                     this.random.setSeed((long)(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761));
                     float var32 = var26.func_180626_a(new BlockPos(var21, var28, var20));
                     float var33;
                     double var34;
                     if (var4.getWorldChunkManager().getTemperatureAtHeight(var32, var27) >= 0.15F) {
                        if (var18 != 0) {
                           if (var18 >= 0) {
                              var8.draw();
                           }

                           var18 = 0;
                           this.mc.getTextureManager().bindTexture(locationRainPng);
                           var9.startDrawingQuads();
                        }

                        var33 = ((float)(this.rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + var1) / 32.0F * (3.0F + this.random.nextFloat());
                        double var36 = (double)((float)var21 + 0.5F) - var43.posX;
                        var34 = (double)((float)var20 + 0.5F) - var43.posZ;
                        float var38 = MathHelper.sqrt_double(var36 * var36 + var34 * var34) / (float)var17;
                        float var39 = 1.0F;
                        var9.func_178963_b(var4.getCombinedLight(new BlockPos(var21, var31, var20), 0));
                        var9.func_178960_a(var39, var39, var39, ((1.0F - var38 * var38) * 0.5F + 0.5F) * var42);
                        var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var30), (double)((float)var28 * var30 / 4.0F + var33 * var30));
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var30), (double)((float)var28 * var30 / 4.0F + var33 * var30));
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var29, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var30), (double)((float)var29 * var30 / 4.0F + var33 * var30));
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var29, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var30), (double)((float)var29 * var30 / 4.0F + var33 * var30));
                        var9.setTranslation(0.0D, 0.0D, 0.0D);
                     } else {
                        if (var18 != 1) {
                           if (var18 >= 0) {
                              var8.draw();
                           }

                           var18 = 1;
                           this.mc.getTextureManager().bindTexture(locationSnowPng);
                           var9.startDrawingQuads();
                        }

                        var33 = ((float)(this.rendererUpdateCount & 511) + var1) / 512.0F;
                        float var44 = this.random.nextFloat() + var19 * 0.01F * (float)this.random.nextGaussian();
                        float var37 = this.random.nextFloat() + var19 * (float)this.random.nextGaussian() * 0.001F;
                        var34 = (double)((float)var21 + 0.5F) - var43.posX;
                        double var45 = (double)((float)var20 + 0.5F) - var43.posZ;
                        float var40 = MathHelper.sqrt_double(var34 * var34 + var45 * var45) / (float)var17;
                        float var41 = 1.0F;
                        var9.func_178963_b((var4.getCombinedLight(new BlockPos(var21, var31, var20), 0) * 3 + 15728880) / 4);
                        var9.func_178960_a(var41, var41, var41, ((1.0F - var40 * var40) * 0.3F + 0.5F) * var42);
                        var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var30 + var44), (double)((float)var28 * var30 / 4.0F + var33 * var30 + var37));
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var30 + var44), (double)((float)var28 * var30 / 4.0F + var33 * var30 + var37));
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var29, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var30 + var44), (double)((float)var29 * var30 / 4.0F + var33 * var30 + var37));
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var29, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var30 + var44), (double)((float)var29 * var30 / 4.0F + var33 * var30 + var37));
                        var9.setTranslation(0.0D, 0.0D, 0.0D);
                     }
                  }
               }
            }
         }

         if (var18 >= 0) {
            var8.draw();
         }

         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.alphaFunc(516, 0.1F);
         this.func_175072_h();
      }

   }

   public void onResourceManagerReload(IResourceManager var1) {
      if (this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      if (this.shaderIndex != shaderCount) {
         this.func_175069_a(shaderResourceLocations[this.shaderIndex]);
      } else {
         this.func_175066_a(this.mc.func_175606_aa());
      }

   }

   private void orientCamera(float var1) {
      Entity var2 = this.mc.func_175606_aa();
      float var3 = var2.getEyeHeight();
      double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)var1;
      double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)var1 + (double)var3;
      double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)var1;
      float var10;
      float var11;
      if (var2 instanceof EntityLivingBase && ((EntityLivingBase)var2).isPlayerSleeping()) {
         var3 = (float)((double)var3 + 1.0D);
         GlStateManager.translate(0.0F, 0.3F, 0.0F);
         if (!this.mc.gameSettings.debugCamEnable) {
            BlockPos var27 = new BlockPos(var2);
            IBlockState var13 = this.mc.theWorld.getBlockState(var27);
            Block var32 = var13.getBlock();
            if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
               Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, this.mc.theWorld, var27, var13, var2);
            } else if (var32 == Blocks.bed) {
               int var15 = ((EnumFacing)var13.getValue(BlockBed.AGE)).getHorizontalIndex();
               GlStateManager.rotate((float)(var15 * 90), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1, -1.0F, 0.0F, 0.0F);
         }
      } else if (this.mc.gameSettings.thirdPersonView > 0) {
         double var12 = (double)(this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * var1);
         if (this.mc.gameSettings.debugCamEnable) {
            GlStateManager.translate(0.0F, 0.0F, (float)(-var12));
         } else {
            var10 = var2.rotationYaw;
            var11 = var2.rotationPitch;
            if (this.mc.gameSettings.thirdPersonView == 2) {
               var11 += 180.0F;
            }

            double var14 = (double)(-MathHelper.sin(var10 / 180.0F * 3.1415927F) * MathHelper.cos(var11 / 180.0F * 3.1415927F)) * var12;
            double var16 = (double)(MathHelper.cos(var10 / 180.0F * 3.1415927F) * MathHelper.cos(var11 / 180.0F * 3.1415927F)) * var12;
            double var18 = (double)(-MathHelper.sin(var11 / 180.0F * 3.1415927F)) * var12;

            for(int var20 = 0; var20 < 8; ++var20) {
               float var21 = (float)((var20 & 1) * 2 - 1);
               float var22 = (float)((var20 >> 1 & 1) * 2 - 1);
               float var23 = (float)((var20 >> 2 & 1) * 2 - 1);
               var21 *= 0.1F;
               var22 *= 0.1F;
               var23 *= 0.1F;
               MovingObjectPosition var24 = this.mc.theWorld.rayTraceBlocks(new Vec3(var4 + (double)var21, var6 + (double)var22, var8 + (double)var23), new Vec3(var4 - var14 + (double)var21 + (double)var23, var6 - var18 + (double)var22, var8 - var16 + (double)var23));
               if (var24 != null) {
                  double var25 = var24.hitVec.distanceTo(new Vec3(var4, var6, var8));
                  if (var25 < var12) {
                     var12 = var25;
                  }
               }
            }

            if (this.mc.gameSettings.thirdPersonView == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var2.rotationPitch - var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(var2.rotationYaw - var10, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, (float)(-var12));
            GlStateManager.rotate(var10 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(var11 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
         }
      } else {
         GlStateManager.translate(0.0F, 0.0F, -0.1F);
      }

      if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
         if (!this.mc.gameSettings.debugCamEnable) {
            var10 = var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F;
            var11 = var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1;
            float var28 = 0.0F;
            if (var2 instanceof EntityAnimal) {
               EntityAnimal var29 = (EntityAnimal)var2;
               var10 = var29.prevRotationYawHead + (var29.rotationYawHead - var29.prevRotationYawHead) * var1 + 180.0F;
            }

            Block var31 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var2, var1);
            Object var33 = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, this, var2, var31, var1, var10, var11, var28);
            Reflector.postForgeBusEvent(var33);
            var28 = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_roll, var28);
            var11 = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_pitch, var11);
            var10 = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_yaw, var10);
            GlStateManager.rotate(var28, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(var10, 0.0F, 1.0F, 0.0F);
         }
      } else if (!this.mc.gameSettings.debugCamEnable) {
         GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1, 1.0F, 0.0F, 0.0F);
         if (var2 instanceof EntityAnimal) {
            EntityAnimal var30 = (EntityAnimal)var2;
            GlStateManager.rotate(var30.prevRotationYawHead + (var30.rotationYawHead - var30.prevRotationYawHead) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
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

   private void func_180437_a(RenderGlobal var1, float var2, int var3) {
      if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
         this.mc.mcProfiler.endStartSection("clouds");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         this.setupFog(0, var2);
         var1.func_180447_b(var2, var3);
         GlStateManager.disableFog();
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      }

   }

   private void setupFog(int var1, float var2) {
      Entity var3 = this.mc.func_175606_aa();
      boolean var4 = false;
      this.fogStandard = false;
      if (var3 instanceof EntityPlayer) {
         var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
      }

      GL11.glFog(2918, this.setFogColorBuffer(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 1.0F));
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Block var5 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, var2);
      float var6 = -1.0F;
      if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
         var6 = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, this, var3, var5, var2, 0.1F);
      }

      if (var6 >= 0.0F) {
         GlStateManager.setFogDensity(var6);
      } else {
         float var7;
         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
            var7 = 5.0F;
            int var8 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
            if (var8 < 20) {
               var7 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - (float)var8 / 20.0F);
            }

            if (Config.isShaders()) {
               Shaders.setFog(9729);
            } else {
               GlStateManager.setFog(9729);
            }

            if (var1 == -1) {
               GlStateManager.setFogStart(0.0F);
               GlStateManager.setFogEnd(var7 * 0.8F);
            } else {
               GlStateManager.setFogStart(var7 * 0.25F);
               GlStateManager.setFogEnd(var7);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
               GL11.glFogi(34138, 34139);
            }
         } else if (this.cloudFog) {
            if (Config.isShaders()) {
               Shaders.setFog(2048);
            } else {
               GlStateManager.setFog(2048);
            }

            GlStateManager.setFogDensity(0.1F);
         } else if (var5.getMaterial() == Material.water) {
            if (Config.isShaders()) {
               Shaders.setFog(2048);
            } else {
               GlStateManager.setFog(2048);
            }

            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
               GlStateManager.setFogDensity(0.01F);
            } else {
               GlStateManager.setFogDensity(0.1F - (float)EnchantmentHelper.func_180319_a(var3) * 0.03F);
            }

            if (Config.isClearWater()) {
               GlStateManager.setFogDensity(0.02F);
            }
         } else if (var5.getMaterial() == Material.lava) {
            if (Config.isShaders()) {
               Shaders.setFog(2048);
            } else {
               GlStateManager.setFog(2048);
            }

            GlStateManager.setFogDensity(2.0F);
         } else {
            var7 = this.farPlaneDistance;
            this.fogStandard = true;
            if (Config.isShaders()) {
               Shaders.setFog(9729);
            } else {
               GlStateManager.setFog(9729);
            }

            if (var1 == -1) {
               GlStateManager.setFogStart(0.0F);
               GlStateManager.setFogEnd(var7);
            } else {
               GlStateManager.setFogStart(var7 * Config.getFogStart());
               GlStateManager.setFogEnd(var7);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance) {
               if (Config.isFogFancy()) {
                  GL11.glFogi(34138, 34139);
               }

               if (Config.isFogFast()) {
                  GL11.glFogi(34138, 34140);
               }
            }

            if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
               GlStateManager.setFogStart(var7 * 0.05F);
               GlStateManager.setFogEnd(var7);
            }

            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
               Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, this, var3, var5, var2, var1, var7);
            }
         }
      }

      GlStateManager.enableColorMaterial();
      GlStateManager.enableFog();
      GlStateManager.colorMaterial(1028, 4608);
   }

   private void setupViewBobbing(float var1) {
      if (this.mc.func_175606_aa() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.mc.func_175606_aa();
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

   private void func_175068_a(int var1, float var2, long var3) {
      boolean var5 = Config.isShaders();
      if (var5) {
         Shaders.beginRenderPass(var1, var2, var3);
      }

      RenderGlobal var6 = this.mc.renderGlobal;
      EffectRenderer var7 = this.mc.effectRenderer;
      boolean var8 = this.func_175070_n();
      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("clear");
      if (var5) {
         Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      } else {
         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      }

      this.updateFogColor(var2);
      GlStateManager.clear(16640);
      if (var5) {
         Shaders.clearRenderBuffer();
      }

      this.mc.mcProfiler.endStartSection("camera");
      this.setupCameraTransform(var2, var1);
      if (var5) {
         Shaders.setCamera(var2);
      }

      ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
      this.mc.mcProfiler.endStartSection("frustum");
      ClippingHelperImpl.getInstance();
      this.mc.mcProfiler.endStartSection("culling");
      Frustrum var9 = new Frustrum();
      Entity var10 = this.mc.func_175606_aa();
      double var11 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * (double)var2;
      double var13 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * (double)var2;
      double var15 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * (double)var2;
      if (var5) {
         ShadersRender.setFrustrumPosition(var9, var11, var13, var15);
      } else {
         var9.setPosition(var11, var13, var15);
      }

      if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
         this.setupFog(-1, var2);
         this.mc.mcProfiler.endStartSection("sky");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
         if (var5) {
            Shaders.beginSky();
         }

         var6.func_174976_a(var2, var1);
         if (var5) {
            Shaders.endSky();
         }

         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(var2, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      } else {
         GlStateManager.disableBlend();
      }

      this.setupFog(0, var2);
      GlStateManager.shadeModel(7425);
      if (var10.posY + (double)var10.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
         this.func_180437_a(var6, var2, var1);
      }

      this.mc.mcProfiler.endStartSection("prepareterrain");
      this.setupFog(0, var2);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      RenderHelper.disableStandardItemLighting();
      this.mc.mcProfiler.endStartSection("terrain_setup");
      if (var5) {
         ShadersRender.setupTerrain(var6, var10, (double)var2, var9, this.field_175084_ae++, this.mc.thePlayer.func_175149_v());
      } else {
         var6.func_174970_a(var10, (double)var2, var9, this.field_175084_ae++, this.mc.thePlayer.func_175149_v());
      }

      if (var1 == 0 || var1 == 2) {
         this.mc.mcProfiler.endStartSection("updatechunks");
         Lagometer.timerChunkUpload.start();
         if (var5) {
            ShadersRender.updateChunks(var6, var3);
         } else {
            this.mc.renderGlobal.func_174967_a(var3);
         }

         Lagometer.timerChunkUpload.end();
      }

      this.mc.mcProfiler.endStartSection("terrain");
      Lagometer.timerTerrain.start();
      if (this.mc.gameSettings.ofSmoothFps && var1 > 0) {
         this.mc.mcProfiler.endStartSection("finish");
         GL11.glFinish();
         this.mc.mcProfiler.endStartSection("terrain");
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      if (var5) {
         ShadersRender.beginTerrainSolid();
      }

      var6.func_174977_a(EnumWorldBlockLayer.SOLID, (double)var2, var1, var10);
      GlStateManager.enableAlpha();
      if (var5) {
         ShadersRender.beginTerrainCutoutMipped();
      }

      var6.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, (double)var2, var1, var10);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
      if (var5) {
         ShadersRender.beginTerrainCutout();
      }

      var6.func_174977_a(EnumWorldBlockLayer.CUTOUT, (double)var2, var1, var10);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
      if (var5) {
         ShadersRender.endTerrain();
      }

      Lagometer.timerTerrain.end();
      GlStateManager.shadeModel(7424);
      GlStateManager.alphaFunc(516, 0.1F);
      EntityPlayer var17;
      if (!this.field_175078_W) {
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         RenderHelper.enableStandardItemLighting();
         this.mc.mcProfiler.endStartSection("entities");
         if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
         }

         var6.func_180446_a(var10, var9, var2);
         if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
         }

         RenderHelper.disableStandardItemLighting();
         this.func_175072_h();
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         if (this.mc.objectMouseOver != null && var10.isInsideOfMaterial(Material.water) && var8) {
            var17 = (EntityPlayer)var10;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection("outline");
            if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var6, var17, this.mc.objectMouseOver, 0, var17.getHeldItem(), var2)) && !this.mc.gameSettings.hideGUI) {
               var6.drawSelectionBox(var17, this.mc.objectMouseOver, 0, var2);
            }

            GlStateManager.enableAlpha();
         }
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      if (var8 && this.mc.objectMouseOver != null && !var10.isInsideOfMaterial(Material.water)) {
         var17 = (EntityPlayer)var10;
         GlStateManager.disableAlpha();
         this.mc.mcProfiler.endStartSection("outline");
         if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var6, var17, this.mc.objectMouseOver, 0, var17.getHeldItem(), var2)) && !this.mc.gameSettings.hideGUI) {
            var6.drawSelectionBox(var17, this.mc.objectMouseOver, 0, var2);
         }

         GlStateManager.enableAlpha();
      }

      if (!var6.damagedBlocks.isEmpty()) {
         this.mc.mcProfiler.endStartSection("destroyProgress");
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         var6.func_174981_a(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var10, var2);
         GlStateManager.disableBlend();
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableBlend();
      if (!this.field_175078_W) {
         this.func_180436_i();
         this.mc.mcProfiler.endStartSection("litParticles");
         if (var5) {
            Shaders.beginLitParticles();
         }

         var7.renderLitParticles(var10, var2);
         RenderHelper.disableStandardItemLighting();
         this.setupFog(0, var2);
         this.mc.mcProfiler.endStartSection("particles");
         if (var5) {
            Shaders.beginParticles();
         }

         var7.renderParticles(var10, var2);
         if (var5) {
            Shaders.endParticles();
         }

         this.func_175072_h();
      }

      GlStateManager.depthMask(false);
      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("weather");
      if (var5) {
         Shaders.beginWeather();
      }

      this.renderRainSnow(var2);
      if (var5) {
         Shaders.endWeather();
      }

      GlStateManager.depthMask(true);
      var6.func_180449_a(var10, var2);
      if (var5) {
         ShadersRender.renderHand0(this, var2, var1);
         Shaders.preWater();
      }

      GlStateManager.disableBlend();
      GlStateManager.enableCull();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.1F);
      this.setupFog(0, var2);
      GlStateManager.enableBlend();
      GlStateManager.depthMask(false);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      GlStateManager.shadeModel(7425);
      if (Config.isTranslucentBlocksFancy()) {
         this.mc.mcProfiler.endStartSection("translucent");
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (var5) {
            Shaders.beginWater();
         }

         var6.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, (double)var2, var1, var10);
         if (var5) {
            Shaders.endWater();
         }

         GlStateManager.disableBlend();
      } else {
         this.mc.mcProfiler.endStartSection("translucent");
         if (var5) {
            Shaders.beginWater();
         }

         var6.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, (double)var2, var1, var10);
         if (var5) {
            Shaders.endWater();
         }
      }

      if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.field_175078_W) {
         RenderHelper.enableStandardItemLighting();
         this.mc.mcProfiler.endStartSection("entities");
         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 1);
         this.mc.renderGlobal.func_180446_a(var10, var9, var2);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
         RenderHelper.disableStandardItemLighting();
      }

      GlStateManager.shadeModel(7424);
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.disableFog();
      if (var10.posY + (double)var10.getEyeHeight() >= 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
         this.mc.mcProfiler.endStartSection("aboveClouds");
         this.func_180437_a(var6, var2, var1);
      }

      EventRender3D var18 = new EventRender3D(var2);
      Client.onEvent(var18);
      if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
         this.mc.mcProfiler.endStartSection("forge_render_last");
         Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, var6, var2);
      }

      this.mc.mcProfiler.endStartSection("hand");
      boolean var19 = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, this.mc.renderGlobal, var2, var1);
      if (!var19 && this.field_175074_C && !Shaders.isShadowPass) {
         if (var5) {
            ShadersRender.renderHand1(this, var2, var1);
            Shaders.renderCompositeFinal();
         }

         GlStateManager.clear(256);
         if (var5) {
            ShadersRender.renderFPOverlay(this, var2, var1);
         } else {
            this.renderHand(var2, var1);
         }

         this.func_175067_i(var2);
      }

      if (var5) {
         Shaders.endRender();
      }

   }

   public void func_152430_c(float var1) {
      this.setupOverlayRendering();
      this.mc.ingameGUI.func_180478_c(new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
   }

   private FloatBuffer setFogColorBuffer(float var1, float var2, float var3, float var4) {
      if (Config.isShaders()) {
         Shaders.setFogColor(var1, var2, var3);
      }

      this.fogColorBuffer.clear();
      this.fogColorBuffer.put(var1).put(var2).put(var3).put(var4);
      this.fogColorBuffer.flip();
      return this.fogColorBuffer;
   }

   private float getFOVModifier(float var1, boolean var2) {
      if (this.field_175078_W) {
         return 90.0F;
      } else {
         Entity var3 = this.mc.func_175606_aa();
         float var4 = 70.0F;
         if (var2) {
            var4 = this.mc.gameSettings.fovSetting;
            if (Config.isDynamicFov()) {
               var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * var1;
            }
         }

         boolean var5 = false;
         if (this.mc.currentScreen == null) {
            GameSettings var6 = this.mc.gameSettings;
            var5 = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
         }

         if (var5) {
            if (!Config.zoomMode) {
               Config.zoomMode = true;
               this.mc.gameSettings.smoothCamera = true;
            }

            if (Config.zoomMode) {
               var4 /= 4.0F;
            }
         } else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
            this.mc.renderGlobal.displayListEntitiesDirty = true;
         }

         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0F) {
            float var7 = (float)((EntityLivingBase)var3).deathTime + var1;
            var4 /= (1.0F - 500.0F / (var7 + 500.0F)) * 2.0F + 1.0F;
         }

         Block var8 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, var1);
         if (var8.getMaterial() == Material.water) {
            var4 = var4 * 60.0F / 70.0F;
         }

         return var4;
      }
   }

   public void updateShaderGroupSize(int var1, int var2) {
      if (OpenGlHelper.shadersSupported) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.createBindFramebuffers(var1, var2);
         }

         this.mc.renderGlobal.checkOcclusionQueryResult(var1, var2);
      }

   }

   public EntityRenderer(Minecraft var1, IResourceManager var2) {
      this.shaderIndex = shaderCount;
      this.field_175083_ad = false;
      this.field_175084_ae = 0;
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
            this.field_175076_N[var3 << 5 | var4] = -var6 / var7;
            this.field_175077_O[var3 << 5 | var4] = var5 / var7;
         }
      }

   }

   public boolean isShaderActive() {
      return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
   }

   private void updateLightmap(float var1) {
      if (this.lightmapUpdateNeeded) {
         this.mc.mcProfiler.startSection("lightTex");
         WorldClient var2 = this.mc.theWorld;
         if (var2 != null) {
            if (Config.isCustomColors() && CustomColors.updateLightmap(var2, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
               this.lightmapTexture.updateDynamicTexture();
               this.lightmapUpdateNeeded = false;
               this.mc.mcProfiler.endSection();
               return;
            }

            for(int var3 = 0; var3 < 256; ++var3) {
               float var4 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
               float var5 = var2.provider.getLightBrightnessTable()[var3 / 16] * var4;
               float var6 = var2.provider.getLightBrightnessTable()[var3 % 16] * (this.torchFlickerX * 0.1F + 1.5F);
               if (var2.func_175658_ac() > 0) {
                  var5 = var2.provider.getLightBrightnessTable()[var3 / 16];
               }

               float var7 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
               float var8 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
               float var9 = var6 * ((var6 * 0.6F + 0.4F) * 0.6F + 0.4F);
               float var10 = var6 * (var6 * var6 * 0.6F + 0.4F);
               float var11 = var7 + var6;
               float var12 = var8 + var9;
               float var13 = var5 + var10;
               var11 = var11 * 0.96F + 0.03F;
               var12 = var12 * 0.96F + 0.03F;
               var13 = var13 * 0.96F + 0.03F;
               float var14;
               if (this.bossColorModifier > 0.0F) {
                  var14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * var1;
                  var11 = var11 * (1.0F - var14) + var11 * 0.7F * var14;
                  var12 = var12 * (1.0F - var14) + var12 * 0.6F * var14;
                  var13 = var13 * (1.0F - var14) + var13 * 0.6F * var14;
               }

               if (var2.provider.getDimensionId() == 1) {
                  var11 = 0.22F + var6 * 0.75F;
                  var12 = 0.28F + var9 * 0.75F;
                  var13 = 0.25F + var10 * 0.75F;
               }

               float var15;
               if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                  var14 = this.func_180438_a(this.mc.thePlayer, var1);
                  var15 = 1.0F / var11;
                  if (var15 > 1.0F / var12) {
                     var15 = 1.0F / var12;
                  }

                  if (var15 > 1.0F / var13) {
                     var15 = 1.0F / var13;
                  }

                  var11 = var11 * (1.0F - var14) + var11 * var15 * var14;
                  var12 = var12 * (1.0F - var14) + var12 * var15 * var14;
                  var13 = var13 * (1.0F - var14) + var13 * var15 * var14;
               }

               if (var11 > 1.0F) {
                  var11 = 1.0F;
               }

               if (var12 > 1.0F) {
                  var12 = 1.0F;
               }

               if (var13 > 1.0F) {
                  var13 = 1.0F;
               }

               var14 = this.mc.gameSettings.gammaSetting;
               var15 = 1.0F - var11;
               float var16 = 1.0F - var12;
               float var17 = 1.0F - var13;
               var15 = 1.0F - var15 * var15 * var15 * var15;
               var16 = 1.0F - var16 * var16 * var16 * var16;
               var17 = 1.0F - var17 * var17 * var17 * var17;
               var11 = var11 * (1.0F - var14) + var15 * var14;
               var12 = var12 * (1.0F - var14) + var16 * var14;
               var13 = var13 * (1.0F - var14) + var17 * var14;
               var11 = var11 * 0.96F + 0.03F;
               var12 = var12 * 0.96F + 0.03F;
               var13 = var13 * 0.96F + 0.03F;
               if (var11 > 1.0F) {
                  var11 = 1.0F;
               }

               if (var12 > 1.0F) {
                  var12 = 1.0F;
               }

               if (var13 > 1.0F) {
                  var13 = 1.0F;
               }

               if (var11 < 0.0F) {
                  var11 = 0.0F;
               }

               if (var12 < 0.0F) {
                  var12 = 0.0F;
               }

               if (var13 < 0.0F) {
                  var13 = 0.0F;
               }

               short var18 = 255;
               int var19 = (int)(var11 * 255.0F);
               int var20 = (int)(var12 * 255.0F);
               int var21 = (int)(var13 * 255.0F);
               this.lightmapColors[var3] = var18 << 24 | var19 << 16 | var20 << 8 | var21;
            }

            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
            this.mc.mcProfiler.endSection();
         }
      }

   }

   public MapItemRenderer getMapItemRenderer() {
      return this.theMapItemRenderer;
   }

   private void updateFogColor(float var1) {
      WorldClient var2 = this.mc.theWorld;
      Entity var3 = this.mc.func_175606_aa();
      float var4 = 0.25F + 0.75F * (float)this.mc.gameSettings.renderDistanceChunks / 32.0F;
      var4 = 1.0F - (float)Math.pow((double)var4, 0.25D);
      Vec3 var5 = var2.getSkyColor(this.mc.func_175606_aa(), var1);
      var5 = CustomColors.getWorldSkyColor(var5, var2, this.mc.func_175606_aa(), var1);
      float var6 = (float)var5.xCoord;
      float var7 = (float)var5.yCoord;
      float var8 = (float)var5.zCoord;
      Vec3 var9 = var2.getFogColor(var1);
      var9 = CustomColors.getWorldFogColor(var9, var2, this.mc.func_175606_aa(), var1);
      this.field_175080_Q = (float)var9.xCoord;
      this.field_175082_R = (float)var9.yCoord;
      this.field_175081_S = (float)var9.zCoord;
      float var10;
      if (this.mc.gameSettings.renderDistanceChunks >= 4) {
         double var11 = -1.0D;
         Vec3 var13 = MathHelper.sin(var2.getCelestialAngleRadians(var1)) > 0.0F ? new Vec3(var11, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
         var10 = (float)var3.getLook(var1).dotProduct(var13);
         if (var10 < 0.0F) {
            var10 = 0.0F;
         }

         if (var10 > 0.0F) {
            float[] var14 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(var1), var1);
            if (var14 != null) {
               var10 *= var14[3];
               this.field_175080_Q = this.field_175080_Q * (1.0F - var10) + var14[0] * var10;
               this.field_175082_R = this.field_175082_R * (1.0F - var10) + var14[1] * var10;
               this.field_175081_S = this.field_175081_S * (1.0F - var10) + var14[2] * var10;
            }
         }
      }

      this.field_175080_Q += (var6 - this.field_175080_Q) * var4;
      this.field_175082_R += (var7 - this.field_175082_R) * var4;
      this.field_175081_S += (var8 - this.field_175081_S) * var4;
      float var23 = var2.getRainStrength(var1);
      float var12;
      float var24;
      if (var23 > 0.0F) {
         var12 = 1.0F - var23 * 0.5F;
         var24 = 1.0F - var23 * 0.4F;
         this.field_175080_Q *= var12;
         this.field_175082_R *= var12;
         this.field_175081_S *= var24;
      }

      var12 = var2.getWeightedThunderStrength(var1);
      if (var12 > 0.0F) {
         var24 = 1.0F - var12 * 0.5F;
         this.field_175080_Q *= var24;
         this.field_175082_R *= var24;
         this.field_175081_S *= var24;
      }

      Block var25 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, var1);
      Vec3 var15;
      if (this.cloudFog) {
         var15 = var2.getCloudColour(var1);
         this.field_175080_Q = (float)var15.xCoord;
         this.field_175082_R = (float)var15.yCoord;
         this.field_175081_S = (float)var15.zCoord;
      } else if (var25.getMaterial() == Material.water) {
         var10 = (float)EnchantmentHelper.func_180319_a(var3) * 0.2F;
         if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
            var10 = var10 * 0.3F + 0.6F;
         }

         this.field_175080_Q = 0.02F + var10;
         this.field_175082_R = 0.02F + var10;
         this.field_175081_S = 0.2F + var10;
         var15 = CustomColors.getUnderwaterColor(this.mc.theWorld, this.mc.func_175606_aa().posX, this.mc.func_175606_aa().posY + 1.0D, this.mc.func_175606_aa().posZ);
         if (var15 != null) {
            this.field_175080_Q = (float)var15.xCoord;
            this.field_175082_R = (float)var15.yCoord;
            this.field_175081_S = (float)var15.zCoord;
         }
      } else if (var25.getMaterial() == Material.lava) {
         this.field_175080_Q = 0.6F;
         this.field_175082_R = 0.1F;
         this.field_175081_S = 0.0F;
      }

      var10 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * var1;
      this.field_175080_Q *= var10;
      this.field_175082_R *= var10;
      this.field_175081_S *= var10;
      double var16 = var2.provider.getVoidFogYFactor();
      double var18 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var1) * var16;
      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
         int var20 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
         if (var20 < 20) {
            var18 *= (double)(1.0F - (float)var20 / 20.0F);
         } else {
            var18 = 0.0D;
         }
      }

      if (var18 < 1.0D) {
         if (var18 < 0.0D) {
            var18 = 0.0D;
         }

         var18 *= var18;
         this.field_175080_Q = (float)((double)this.field_175080_Q * var18);
         this.field_175082_R = (float)((double)this.field_175082_R * var18);
         this.field_175081_S = (float)((double)this.field_175081_S * var18);
      }

      float var26;
      if (this.bossColorModifier > 0.0F) {
         var26 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * var1;
         this.field_175080_Q = this.field_175080_Q * (1.0F - var26) + this.field_175080_Q * 0.7F * var26;
         this.field_175082_R = this.field_175082_R * (1.0F - var26) + this.field_175082_R * 0.6F * var26;
         this.field_175081_S = this.field_175081_S * (1.0F - var26) + this.field_175081_S * 0.6F * var26;
      }

      float var21;
      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.nightVision)) {
         var26 = this.func_180438_a((EntityLivingBase)var3, var1);
         var21 = 1.0F / this.field_175080_Q;
         if (var21 > 1.0F / this.field_175082_R) {
            var21 = 1.0F / this.field_175082_R;
         }

         if (var21 > 1.0F / this.field_175081_S) {
            var21 = 1.0F / this.field_175081_S;
         }

         this.field_175080_Q = this.field_175080_Q * (1.0F - var26) + this.field_175080_Q * var21 * var26;
         this.field_175082_R = this.field_175082_R * (1.0F - var26) + this.field_175082_R * var21 * var26;
         this.field_175081_S = this.field_175081_S * (1.0F - var26) + this.field_175081_S * var21 * var26;
      }

      if (this.mc.gameSettings.anaglyph) {
         var26 = (this.field_175080_Q * 30.0F + this.field_175082_R * 59.0F + this.field_175081_S * 11.0F) / 100.0F;
         var21 = (this.field_175080_Q * 30.0F + this.field_175082_R * 70.0F) / 100.0F;
         float var22 = (this.field_175080_Q * 30.0F + this.field_175081_S * 70.0F) / 100.0F;
         this.field_175080_Q = var26;
         this.field_175082_R = var21;
         this.field_175081_S = var22;
      }

      if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
         Object var27 = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, this, var3, var25, var1, this.field_175080_Q, this.field_175082_R, this.field_175081_S);
         Reflector.postForgeBusEvent(var27);
         this.field_175080_Q = Reflector.getFieldValueFloat(var27, Reflector.EntityViewRenderEvent_FogColors_red, this.field_175080_Q);
         this.field_175082_R = Reflector.getFieldValueFloat(var27, Reflector.EntityViewRenderEvent_FogColors_green, this.field_175082_R);
         this.field_175081_S = Reflector.getFieldValueFloat(var27, Reflector.EntityViewRenderEvent_FogColors_blue, this.field_175081_S);
      }

      Shaders.setClearColor(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 0.0F);
   }

   public void func_175071_c() {
      this.field_175083_ad = !this.field_175083_ad;
   }

   private void updateMainMenu(GuiMainMenu var1) {
      try {
         String var2 = null;
         Calendar var3 = Calendar.getInstance();
         var3.setTime(new Date());
         int var4 = var3.get(5);
         int var5 = var3.get(2) + 1;
         if (var4 == 8 && var5 == 4) {
            var2 = "Happy birthday, OptiFine!";
         }

         if (var4 == 14 && var5 == 8) {
            var2 = "Happy birthday, sp614x!";
         }

         if (var2 == null) {
            return;
         }

         Field[] var6 = GuiMainMenu.class.getDeclaredFields();

         for(int var7 = 0; var7 < var6.length; ++var7) {
            if (var6[var7].getType() == String.class) {
               var6[var7].setAccessible(true);
               var6[var7].set(var1, var2);
               break;
            }
         }
      } catch (Throwable var8) {
      }

   }

   public ShaderGroup getShaderGroup() {
      return this.theShaderGroup;
   }
}
