package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.Lagometer;
import net.minecraft.src.Lagometer.TimerNano;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorConstructor;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.RenderPlayerOF;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class EntityRenderer implements IResourceManagerReloadListener
{
  private static final Logger logger = ;
  private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
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
  private MouseFilter mouseFilterXAxis = new MouseFilter();
  private MouseFilter mouseFilterYAxis = new MouseFilter();
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
  
  private boolean field_175074_C = true;
  private boolean field_175073_D = true;
  

  private long prevFrameTime = Minecraft.getSystemTime();
  

  private long renderEndNanoTime;
  

  private final DynamicTexture lightmapTexture;
  

  private final int[] lightmapColors;
  

  private final ResourceLocation locationLightMap;
  

  private boolean lightmapUpdateNeeded;
  

  private float torchFlickerX;
  

  private float field_175075_L;
  

  private int rainSoundCounter;
  

  private float[] field_175076_N = new float['Ѐ'];
  private float[] field_175077_O = new float['Ѐ'];
  

  private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
  
  public float field_175080_Q;
  
  public float field_175082_R;
  
  public float field_175081_S;
  
  private float fogColor2;
  private float fogColor1;
  private int field_175079_V = 0;
  private boolean field_175078_W = false;
  private double cameraZoom = 1.0D;
  private double cameraYaw;
  private double cameraPitch;
  public ShaderGroup theShaderGroup;
  public static final ResourceLocation[] shaderResourceLocations = { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
  public static final int shaderCount = shaderResourceLocations.length;
  public int shaderIndex;
  private boolean field_175083_ad;
  private int field_175084_ae;
  private static final String __OBFID = "CL_00000947";
  private boolean initialized = false;
  private net.minecraft.world.World updatedWorld = null;
  private boolean showDebugInfo = false;
  public boolean fogStandard = false;
  private float clipDistance = 128.0F;
  private long lastServerTime = 0L;
  private int lastServerTicks = 0;
  private int serverWaitTime = 0;
  private int serverWaitTimeCurrent = 0;
  private float avgServerTimeDiff = 0.0F;
  private float avgServerTickDiff = 0.0F;
  
  public EntityRenderer(Minecraft mcIn, IResourceManager p_i45076_2_)
  {
    shaderIndex = shaderCount;
    field_175083_ad = false;
    field_175084_ae = 0;
    mc = mcIn;
    resourceManager = p_i45076_2_;
    itemRenderer = mcIn.getItemRenderer();
    theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
    lightmapTexture = new DynamicTexture(16, 16);
    locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", lightmapTexture);
    lightmapColors = lightmapTexture.getTextureData();
    theShaderGroup = null;
    
    for (int var3 = 0; var3 < 32; var3++)
    {
      for (int var4 = 0; var4 < 32; var4++)
      {
        float var5 = var4 - 16;
        float var6 = var3 - 16;
        float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
        field_175076_N[(var3 << 5 | var4)] = (-var6 / var7);
        field_175077_O[(var3 << 5 | var4)] = (var5 / var7);
      }
    }
  }
  
  public boolean isShaderActive()
  {
    return (OpenGlHelper.shadersSupported) && (theShaderGroup != null);
  }
  
  public void func_175071_c()
  {
    field_175083_ad = (!field_175083_ad);
  }
  
  public void func_175066_a(Entity p_175066_1_)
  {
    if (OpenGlHelper.shadersSupported)
    {
      if (theShaderGroup != null)
      {
        theShaderGroup.deleteShaderGroup();
      }
      
      theShaderGroup = null;
      
      if ((p_175066_1_ instanceof EntityCreeper))
      {
        func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
      }
      else if ((p_175066_1_ instanceof EntitySpider))
      {
        func_175069_a(new ResourceLocation("shaders/post/spider.json"));
      }
      else if ((p_175066_1_ instanceof net.minecraft.entity.monster.EntityEnderman))
      {
        func_175069_a(new ResourceLocation("shaders/post/invert.json"));
      }
    }
  }
  
  public void activateNextShader()
  {
    if ((OpenGlHelper.shadersSupported) && ((mc.func_175606_aa() instanceof EntityPlayer)))
    {
      if (theShaderGroup != null)
      {
        theShaderGroup.deleteShaderGroup();
      }
      
      shaderIndex = ((shaderIndex + 1) % (shaderResourceLocations.length + 1));
      
      if (shaderIndex != shaderCount)
      {
        func_175069_a(shaderResourceLocations[shaderIndex]);
      }
      else
      {
        theShaderGroup = null;
      }
    }
  }
  
  public void func_175069_a(ResourceLocation p_175069_1_)
  {
    try
    {
      theShaderGroup = new ShaderGroup(mc.getTextureManager(), resourceManager, mc.getFramebuffer(), p_175069_1_);
      theShaderGroup.createBindFramebuffers(Minecraft.displayWidth, Minecraft.displayHeight);
      field_175083_ad = true;
    }
    catch (IOException var3)
    {
      logger.warn("Failed to load shader: " + p_175069_1_, var3);
      shaderIndex = shaderCount;
      field_175083_ad = false;
    }
    catch (JsonSyntaxException var4)
    {
      logger.warn("Failed to load shader: " + p_175069_1_, var4);
      shaderIndex = shaderCount;
      field_175083_ad = false;
    }
  }
  
  public void onResourceManagerReload(IResourceManager resourceManager)
  {
    if (theShaderGroup != null)
    {
      theShaderGroup.deleteShaderGroup();
    }
    
    theShaderGroup = null;
    
    if (shaderIndex != shaderCount)
    {
      func_175069_a(shaderResourceLocations[shaderIndex]);
    }
    else
    {
      func_175066_a(mc.func_175606_aa());
    }
  }
  



  public void updateRenderer()
  {
    if ((OpenGlHelper.shadersSupported) && (ShaderLinkHelper.getStaticShaderLinkHelper() == null))
    {
      ShaderLinkHelper.setNewStaticShaderLinkHelper();
    }
    
    updateFovModifierHand();
    updateTorchFlicker();
    fogColor2 = fogColor1;
    thirdPersonDistanceTemp = thirdPersonDistance;
    


    if (gameSettingssmoothCamera)
    {
      float var1 = gameSettingsmouseSensitivity * 0.6F + 0.2F;
      float var2 = var1 * var1 * var1 * 8.0F;
      smoothCamFilterX = mouseFilterXAxis.smooth(smoothCamYaw, 0.05F * var2);
      smoothCamFilterY = mouseFilterYAxis.smooth(smoothCamPitch, 0.05F * var2);
      smoothCamPartialTicks = 0.0F;
      smoothCamYaw = 0.0F;
      smoothCamPitch = 0.0F;
    }
    else
    {
      smoothCamFilterX = 0.0F;
      smoothCamFilterY = 0.0F;
      mouseFilterXAxis.func_180179_a();
      mouseFilterYAxis.func_180179_a();
    }
    
    if (mc.func_175606_aa() == null)
    {
      mc.func_175607_a(Minecraft.thePlayer);
    }
    
    float var1 = Minecraft.theWorld.getLightBrightness(new BlockPos(mc.func_175606_aa()));
    float var2 = gameSettingsrenderDistanceChunks / 32.0F;
    float var3 = var1 * (1.0F - var2) + var2;
    fogColor1 += (var3 - fogColor1) * 0.1F;
    rendererUpdateCount += 1;
    itemRenderer.updateEquippedItem();
    addRainParticles();
    bossColorModifierPrev = bossColorModifier;
    
    if (net.minecraft.entity.boss.BossStatus.hasColorModifier)
    {
      bossColorModifier += 0.05F;
      
      if (bossColorModifier > 1.0F)
      {
        bossColorModifier = 1.0F;
      }
      
      net.minecraft.entity.boss.BossStatus.hasColorModifier = false;
    }
    else if (bossColorModifier > 0.0F)
    {
      bossColorModifier -= 0.0125F;
    }
  }
  
  public ShaderGroup getShaderGroup()
  {
    return theShaderGroup;
  }
  
  public void updateShaderGroupSize(int p_147704_1_, int p_147704_2_)
  {
    if (OpenGlHelper.shadersSupported)
    {
      if (theShaderGroup != null)
      {
        theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
      }
      
      mc.renderGlobal.checkOcclusionQueryResult(p_147704_1_, p_147704_2_);
    }
  }
  



  public void getMouseOver(float p_78473_1_)
  {
    Entity var2 = mc.func_175606_aa();
    
    if ((var2 != null) && (Minecraft.theWorld != null))
    {
      mc.mcProfiler.startSection("pick");
      mc.pointedEntity = null;
      double var3 = Minecraft.playerController.getBlockReachDistance();
      mc.objectMouseOver = var2.func_174822_a(var3, p_78473_1_);
      double var5 = var3;
      Vec3 var7 = var2.func_174824_e(p_78473_1_);
      
      if (Minecraft.playerController.extendedReach())
      {
        var3 = 6.0D;
        var5 = 6.0D;
      }
      else
      {
        if (var3 > 3.0D)
        {
          var5 = 3.0D;
        }
        
        var3 = var5;
      }
      
      if (mc.objectMouseOver != null)
      {
        var5 = mc.objectMouseOver.hitVec.distanceTo(var7);
      }
      
      Vec3 var8 = var2.getLook(p_78473_1_);
      Vec3 var9 = var7.addVector(xCoord * var3, yCoord * var3, zCoord * var3);
      pointedEntity = null;
      Vec3 var10 = null;
      float var11 = 1.0F;
      List var12 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(xCoord * var3, yCoord * var3, zCoord * var3).expand(var11, var11, var11));
      double var13 = var5;
      
      for (int var15 = 0; var15 < var12.size(); var15++)
      {
        Entity var16 = (Entity)var12.get(var15);
        
        if (var16.canBeCollidedWith())
        {
          float var17 = var16.getCollisionBorderSize();
          AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
          MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
          
          if (var18.isVecInside(var7))
          {
            if ((0.0D < var13) || (var13 == 0.0D))
            {
              pointedEntity = var16;
              var10 = var19 == null ? var7 : hitVec;
              var13 = 0.0D;
            }
          }
          else if (var19 != null)
          {
            double var20 = var7.distanceTo(hitVec);
            
            if ((var20 < var13) || (var13 == 0.0D))
            {
              boolean canRiderInteract = false;
              
              if (Reflector.ForgeEntity_canRiderInteract.exists())
              {
                canRiderInteract = Reflector.callBoolean(Integer.valueOf(var15), Reflector.ForgeEntity_canRiderInteract, new Object[0]);
              }
              
              if ((var16 == ridingEntity) && (!canRiderInteract))
              {
                if (var13 == 0.0D)
                {
                  pointedEntity = var16;
                  var10 = hitVec;
                }
              }
              else
              {
                pointedEntity = var16;
                var10 = hitVec;
                var13 = var20;
              }
            }
          }
        }
      }
      
      if ((pointedEntity != null) && ((var13 < var5) || (mc.objectMouseOver == null)))
      {
        mc.objectMouseOver = new MovingObjectPosition(pointedEntity, var10);
        
        if (((pointedEntity instanceof EntityLivingBase)) || ((pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame)))
        {
          mc.pointedEntity = pointedEntity;
        }
      }
      
      mc.mcProfiler.endSection();
    }
  }
  



  private void updateFovModifierHand()
  {
    float var1 = 1.0F;
    
    if ((mc.func_175606_aa() instanceof AbstractClientPlayer))
    {
      AbstractClientPlayer var2 = (AbstractClientPlayer)mc.func_175606_aa();
      var1 = var2.func_175156_o();
    }
    else
    {
      var1 = Minecraft.thePlayer.func_175156_o();
    }
    
    fovModifierHandPrev = fovModifierHand;
    fovModifierHand += (var1 - fovModifierHand) * 0.5F;
    
    if (fovModifierHand > 1.5F)
    {
      fovModifierHand = 1.5F;
    }
    
    if (fovModifierHand < 0.1F)
    {
      fovModifierHand = 0.1F;
    }
  }
  



  private float getFOVModifier(float partialTicks, boolean p_78481_2_)
  {
    if (field_175078_W)
    {
      return 90.0F;
    }
    

    Entity var3 = mc.func_175606_aa();
    float var4 = 70.0F;
    
    if (p_78481_2_)
    {
      var4 = gameSettingsfovSetting;
      var4 *= (fovModifierHandPrev + (fovModifierHand - fovModifierHandPrev) * partialTicks);
    }
    
    boolean zoomActive = false;
    
    if (Minecraft.currentScreen == null)
    {
      GameSettings var10000 = Minecraft.gameSettings;
      zoomActive = GameSettings.isKeyDown(gameSettingsofKeyBindZoom);
    }
    
    if (zoomActive)
    {
      if (!Config.zoomMode)
      {
        Config.zoomMode = true;
        gameSettingssmoothCamera = true;
      }
      
      if (Config.zoomMode)
      {
        var4 /= 4.0F;
      }
    }
    else if (Config.zoomMode)
    {
      Config.zoomMode = false;
      gameSettingssmoothCamera = false;
      mouseFilterXAxis = new MouseFilter();
      mouseFilterYAxis = new MouseFilter();
      mc.renderGlobal.displayListEntitiesDirty = true;
    }
    
    if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).getHealth() <= 0.0F))
    {
      float var6 = deathTime + partialTicks;
      var4 /= ((1.0F - 500.0F / (var6 + 500.0F)) * 2.0F + 1.0F);
    }
    
    Block var61 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);
    
    if (var61.getMaterial() == Material.water)
    {
      var4 = var4 * 60.0F / 70.0F;
    }
    
    return var4;
  }
  

  private void hurtCameraEffect(float p_78482_1_)
  {
    if ((mc.func_175606_aa() instanceof EntityLivingBase))
    {
      EntityLivingBase var2 = (EntityLivingBase)mc.func_175606_aa();
      float var3 = hurtTime - p_78482_1_;
      

      if (var2.getHealth() <= 0.0F)
      {
        float var4 = deathTime + p_78482_1_;
        GlStateManager.rotate(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
      }
      
      if (var3 < 0.0F)
      {
        return;
      }
      
      var3 /= maxHurtTime;
      var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927F);
      float var4 = attackedAtYaw;
      GlStateManager.rotate(-var4, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var4, 0.0F, 1.0F, 0.0F);
    }
  }
  




  private void setupViewBobbing(float p_78475_1_)
  {
    if (ModuleManager.getModule(net.SliceClient.modules.BetterBobbing.class).isEnabled())
    {
      EntityPlayer var2 = (EntityPlayer)mc.func_175606_aa();
      float var3 = distanceWalkedModified - prevDistanceWalkedModified;
      float var4 = -(distanceWalkedModified + var3 * p_78475_1_);
      float var5 = prevCameraYaw + (cameraYaw - prevCameraYaw) * p_78475_1_;
      float var6 = prevCameraPitch + (cameraPitch - prevCameraPitch) * p_78475_1_;
      GlStateManager.translate(MathHelper.sin(var4 * 3.1415927F) * var5 * 2.5F, -Math.abs(MathHelper.cos(var4 * 3.1415927F) * var5), 0.0F);
      GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 10.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 10.0F, 20.0F, 20.0F, 20.0F);
      GlStateManager.rotate(var6, 3.0F, 3.0F, 3.0F);
    }
    else
    {
      EntityPlayer var2 = (EntityPlayer)mc.func_175606_aa();
      float var3 = distanceWalkedModified - prevDistanceWalkedModified;
      float var4 = -(distanceWalkedModified + var3 * p_78475_1_);
      float var5 = prevCameraYaw + (cameraYaw - prevCameraYaw) * p_78475_1_;
      float var6 = prevCameraPitch + (cameraPitch - prevCameraPitch) * p_78475_1_;
      GlStateManager.translate(MathHelper.sin(var4 * 3.1415927F) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * 3.1415927F) * var5), 0.0F);
      GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6, 1.0F, 0.0F, 0.0F);
    }
    
    if ((mc.func_175606_aa() instanceof EntityPlayer))
    {
      EntityPlayer var2 = (EntityPlayer)mc.func_175606_aa();
      float var3 = distanceWalkedModified - prevDistanceWalkedModified;
      float var4 = -(distanceWalkedModified + var3 * p_78475_1_);
      float var5 = prevCameraYaw + (cameraYaw - prevCameraYaw) * p_78475_1_;
      float var6 = prevCameraPitch + (cameraPitch - prevCameraPitch) * p_78475_1_;
      GlStateManager.translate(MathHelper.sin(var4 * 3.1415927F) * var5 * 0.5F, 
        -Math.abs(MathHelper.cos(var4 * 3.1415927F) * var5), 0.0F);
      GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 
        0.0F);
      if (!ModuleManager.getModule(net.SliceClient.modules.Drugs.class).isEnabled()) {
        GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
      } else {
        GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927F) * var5 * 180.0F, MathHelper.sin(var4 * 3.1415927F), 1.5F, var5);
      }
      GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927F - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6, 1.0F, 0.0F, 0.0F);
    }
  }
  




  private void orientCamera(float p_78467_1_)
  {
    Entity var2 = mc.func_175606_aa();
    float var3 = var2.getEyeHeight();
    double var4 = prevPosX + (posX - prevPosX) * p_78467_1_;
    double var6 = prevPosY + (posY - prevPosY) * p_78467_1_ + var3;
    double var8 = prevPosZ + (posZ - prevPosZ) * p_78467_1_;
    
    if (((var2 instanceof EntityLivingBase)) && (((EntityLivingBase)var2).isPlayerSleeping()))
    {
      var3 = (float)(var3 + 1.0D);
      GlStateManager.translate(0.0F, 0.3F, 0.0F);
      
      if (!gameSettingsdebugCamEnable)
      {
        BlockPos var27 = new BlockPos(var2);
        IBlockState var11 = Minecraft.theWorld.getBlockState(var27);
        Block var29 = var11.getBlock();
        
        if (Reflector.ForgeHooksClient_orientBedCamera.exists())
        {
          Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { mc, var2 });
        }
        else if (var29 == net.minecraft.init.Blocks.bed)
        {
          int var30 = ((EnumFacing)var11.getValue(BlockBed.AGE)).getHorizontalIndex();
          GlStateManager.rotate(var30 * 90, 0.0F, 1.0F, 0.0F);
        }
        
        GlStateManager.rotate(prevRotationYaw + (rotationYaw - prevRotationYaw) * p_78467_1_ + 180.0F, 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(prevRotationPitch + (rotationPitch - prevRotationPitch) * p_78467_1_, -1.0F, 0.0F, 0.0F);
      }
    }
    else if (gameSettingsthirdPersonView > 0)
    {
      double var28 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * p_78467_1_;
      
      if (gameSettingsdebugCamEnable)
      {
        GlStateManager.translate(0.0F, 0.0F, (float)-var28);
      }
      else
      {
        float var12 = rotationYaw;
        float var13 = rotationPitch;
        
        if (gameSettingsthirdPersonView == 2)
        {
          var13 += 180.0F;
        }
        
        double var14 = -MathHelper.sin(var12 / 180.0F * 3.1415927F) * MathHelper.cos(var13 / 180.0F * 3.1415927F) * var28;
        double var16 = MathHelper.cos(var12 / 180.0F * 3.1415927F) * MathHelper.cos(var13 / 180.0F * 3.1415927F) * var28;
        double var18 = -MathHelper.sin(var13 / 180.0F * 3.1415927F) * var28;
        
        for (int var20 = 0; var20 < 8; var20++)
        {
          float var21 = (var20 & 0x1) * 2 - 1;
          float var22 = (var20 >> 1 & 0x1) * 2 - 1;
          float var23 = (var20 >> 2 & 0x1) * 2 - 1;
          var21 *= 0.1F;
          var22 *= 0.1F;
          var23 *= 0.1F;
          MovingObjectPosition var24 = Minecraft.theWorld.rayTraceBlocks(new Vec3(var4 + var21, var6 + var22, var8 + var23), new Vec3(var4 - var14 + var21 + var23, var6 - var18 + var22, var8 - var16 + var23));
          
          if (var24 != null)
          {
            double var25 = hitVec.distanceTo(new Vec3(var4, var6, var8));
            
            if (var25 < var28)
            {
              var28 = var25;
            }
          }
        }
        
        if (gameSettingsthirdPersonView == 2)
        {
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }
        
        GlStateManager.rotate(rotationPitch - var13, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(rotationYaw - var12, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, (float)-var28);
        GlStateManager.rotate(var12 - rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var13 - rotationPitch, 1.0F, 0.0F, 0.0F);
      }
    }
    else
    {
      GlStateManager.translate(0.0F, 0.0F, -0.1F);
    }
    
    if (!gameSettingsdebugCamEnable)
    {
      GlStateManager.rotate(prevRotationPitch + (rotationPitch - prevRotationPitch) * p_78467_1_, 1.0F, 0.0F, 0.0F);
      
      if ((var2 instanceof EntityAnimal))
      {
        EntityAnimal var281 = (EntityAnimal)var2;
        GlStateManager.rotate(prevRotationYawHead + (rotationYawHead - prevRotationYawHead) * p_78467_1_ + 180.0F, 0.0F, 1.0F, 0.0F);
      }
      else
      {
        GlStateManager.rotate(prevRotationYaw + (rotationYaw - prevRotationYaw) * p_78467_1_ + 180.0F, 0.0F, 1.0F, 0.0F);
      }
    }
    
    GlStateManager.translate(0.0F, -var3, 0.0F);
    var4 = prevPosX + (posX - prevPosX) * p_78467_1_;
    var6 = prevPosY + (posY - prevPosY) * p_78467_1_ + var3;
    var8 = prevPosZ + (posZ - prevPosZ) * p_78467_1_;
    cloudFog = mc.renderGlobal.hasCloudFog(var4, var6, var8, p_78467_1_);
  }
  



  public void setupCameraTransform(float partialTicks, int pass)
  {
    farPlaneDistance = (gameSettingsrenderDistanceChunks * 16);
    
    if (Config.isFogFancy())
    {
      farPlaneDistance *= 0.95F;
    }
    
    if (Config.isFogFast())
    {
      farPlaneDistance *= 0.83F;
    }
    
    GlStateManager.matrixMode(5889);
    GlStateManager.loadIdentity();
    float var3 = 0.07F;
    
    if (gameSettingsanaglyph)
    {
      GlStateManager.translate(-(pass * 2 - 1) * var3, 0.0F, 0.0F);
    }
    
    clipDistance = (farPlaneDistance * MathHelper.field_180189_a);
    
    if (clipDistance < 173.0F)
    {
      clipDistance = 173.0F;
    }
    
    if (theWorldprovider.getDimensionId() == 1)
    {
      clipDistance = 256.0F;
    }
    
    if (cameraZoom != 1.0D)
    {
      GlStateManager.translate((float)cameraYaw, (float)-cameraPitch, 0.0F);
      GlStateManager.scale(cameraZoom, cameraZoom, 1.0D);
    }
    
    Project.gluPerspective(getFOVModifier(partialTicks, true), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, clipDistance);
    GlStateManager.matrixMode(5888);
    GlStateManager.loadIdentity();
    
    if (gameSettingsanaglyph)
    {
      GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
    }
    
    hurtCameraEffect(partialTicks);
    
    if (gameSettingsviewBobbing)
    {
      setupViewBobbing(partialTicks);
    }
    
    float var4 = thePlayerprevTimeInPortal + (thePlayertimeInPortal - thePlayerprevTimeInPortal) * partialTicks;
    
    if (var4 > 0.0F)
    {
      byte var5 = 20;
      
      if (Minecraft.thePlayer.isPotionActive(Potion.confusion))
      {
        var5 = 7;
      }
      
      float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
      var6 *= var6;
      GlStateManager.rotate((rendererUpdateCount + partialTicks) * var5, 0.0F, 1.0F, 1.0F);
      GlStateManager.scale(1.0F / var6, 1.0F, 1.0F);
      GlStateManager.rotate(-(rendererUpdateCount + partialTicks) * var5, 0.0F, 1.0F, 1.0F);
    }
    
    orientCamera(partialTicks);
    
    if (field_175078_W)
    {
      switch (field_175079_V)
      {
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
  


  private void renderHand(float p_78476_1_, int p_78476_2_)
  {
    if (!field_175078_W)
    {
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      float var3 = 0.07F;
      
      if (gameSettingsanaglyph)
      {
        GlStateManager.translate(-(p_78476_2_ * 2 - 1) * var3, 0.0F, 0.0F);
      }
      
      Project.gluPerspective(getFOVModifier(p_78476_1_, false), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, farPlaneDistance * 2.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      
      if (gameSettingsanaglyph)
      {
        GlStateManager.translate((p_78476_2_ * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }
      
      GlStateManager.pushMatrix();
      hurtCameraEffect(p_78476_1_);
      
      if (gameSettingsviewBobbing)
      {
        setupViewBobbing(p_78476_1_);
      }
      
      boolean var4 = ((mc.func_175606_aa() instanceof EntityLivingBase)) && (((EntityLivingBase)mc.func_175606_aa()).isPlayerSleeping());
      
      if ((gameSettingsthirdPersonView == 0) && (!var4) && (!gameSettingshideGUI) && (!Minecraft.playerController.enableEverythingIsScrewedUpMode()))
      {
        func_180436_i();
        itemRenderer.renderItemInFirstPerson(p_78476_1_);
        func_175072_h();
      }
      
      GlStateManager.popMatrix();
      
      if ((gameSettingsthirdPersonView == 0) && (!var4))
      {
        itemRenderer.renderOverlays(p_78476_1_);
        hurtCameraEffect(p_78476_1_);
      }
      
      if (gameSettingsviewBobbing)
      {
        setupViewBobbing(p_78476_1_);
      }
    }
  }
  
  public void func_175072_h()
  {
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.func_179090_x();
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
  
  public void func_180436_i()
  {
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.matrixMode(5890);
    GlStateManager.loadIdentity();
    float var1 = 0.00390625F;
    GlStateManager.scale(var1, var1, var1);
    GlStateManager.translate(8.0F, 8.0F, 8.0F);
    GlStateManager.matrixMode(5888);
    mc.getTextureManager().bindTexture(locationLightMap);
    GL11.glTexParameteri(3553, 10241, 9729);
    GL11.glTexParameteri(3553, 10240, 9729);
    GL11.glTexParameteri(3553, 10242, 10496);
    GL11.glTexParameteri(3553, 10243, 10496);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
  



  private void updateTorchFlicker()
  {
    field_175075_L = ((float)(field_175075_L + (Math.random() - Math.random()) * Math.random() * Math.random()));
    field_175075_L = ((float)(field_175075_L * 0.9D));
    torchFlickerX += (field_175075_L - torchFlickerX) * 1.0F;
    lightmapUpdateNeeded = true;
  }
  
  private void updateLightmap(float partialTicks)
  {
    if (lightmapUpdateNeeded)
    {
      mc.mcProfiler.startSection("lightTex");
      WorldClient var2 = Minecraft.theWorld;
      
      if (var2 != null)
      {
        if (CustomColorizer.updateLightmap(var2, torchFlickerX, lightmapColors, Minecraft.thePlayer.isPotionActive(Potion.nightVision)))
        {
          lightmapTexture.updateDynamicTexture();
          lightmapUpdateNeeded = false;
          mc.mcProfiler.endSection();
          return;
        }
        
        for (int var3 = 0; var3 < 256; var3++)
        {
          float var4 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
          float var5 = provider.getLightBrightnessTable()[(var3 / 16)] * var4;
          float var6 = provider.getLightBrightnessTable()[(var3 % 16)] * (torchFlickerX * 0.1F + 1.5F);
          
          if (var2.func_175658_ac() > 0)
          {
            var5 = provider.getLightBrightnessTable()[(var3 / 16)];
          }
          
          float var7 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
          float var8 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
          float var11 = var6 * ((var6 * 0.6F + 0.4F) * 0.6F + 0.4F);
          float var12 = var6 * (var6 * var6 * 0.6F + 0.4F);
          float var13 = var7 + var6;
          float var14 = var8 + var11;
          float var15 = var5 + var12;
          var13 = var13 * 0.96F + 0.03F;
          var14 = var14 * 0.96F + 0.03F;
          var15 = var15 * 0.96F + 0.03F;
          

          if (bossColorModifier > 0.0F)
          {
            float var16 = bossColorModifierPrev + (bossColorModifier - bossColorModifierPrev) * partialTicks;
            var13 = var13 * (1.0F - var16) + var13 * 0.7F * var16;
            var14 = var14 * (1.0F - var16) + var14 * 0.6F * var16;
            var15 = var15 * (1.0F - var16) + var15 * 0.6F * var16;
          }
          
          if (provider.getDimensionId() == 1)
          {
            var13 = 0.22F + var6 * 0.75F;
            var14 = 0.28F + var11 * 0.75F;
            var15 = 0.25F + var12 * 0.75F;
          }
          


          if (Minecraft.thePlayer.isPotionActive(Potion.nightVision))
          {
            float var16 = func_180438_a(Minecraft.thePlayer, partialTicks);
            float var17 = 1.0F / var13;
            
            if (var17 > 1.0F / var14)
            {
              var17 = 1.0F / var14;
            }
            
            if (var17 > 1.0F / var15)
            {
              var17 = 1.0F / var15;
            }
            
            var13 = var13 * (1.0F - var16) + var13 * var17 * var16;
            var14 = var14 * (1.0F - var16) + var14 * var17 * var16;
            var15 = var15 * (1.0F - var16) + var15 * var17 * var16;
          }
          
          if (var13 > 1.0F)
          {
            var13 = 1.0F;
          }
          
          if (var14 > 1.0F)
          {
            var14 = 1.0F;
          }
          
          if (var15 > 1.0F)
          {
            var15 = 1.0F;
          }
          
          float var16 = gameSettingsgammaSetting;
          float var17 = 1.0F - var13;
          float var18 = 1.0F - var14;
          float var19 = 1.0F - var15;
          var17 = 1.0F - var17 * var17 * var17 * var17;
          var18 = 1.0F - var18 * var18 * var18 * var18;
          var19 = 1.0F - var19 * var19 * var19 * var19;
          var13 = var13 * (1.0F - var16) + var17 * var16;
          var14 = var14 * (1.0F - var16) + var18 * var16;
          var15 = var15 * (1.0F - var16) + var19 * var16;
          var13 = var13 * 0.96F + 0.03F;
          var14 = var14 * 0.96F + 0.03F;
          var15 = var15 * 0.96F + 0.03F;
          
          if (var13 > 1.0F)
          {
            var13 = 1.0F;
          }
          
          if (var14 > 1.0F)
          {
            var14 = 1.0F;
          }
          
          if (var15 > 1.0F)
          {
            var15 = 1.0F;
          }
          
          if (var13 < 0.0F)
          {
            var13 = 0.0F;
          }
          
          if (var14 < 0.0F)
          {
            var14 = 0.0F;
          }
          
          if (var15 < 0.0F)
          {
            var15 = 0.0F;
          }
          
          short var20 = 255;
          int var21 = (int)(var13 * 255.0F);
          int var22 = (int)(var14 * 255.0F);
          int var23 = (int)(var15 * 255.0F);
          lightmapColors[var3] = (var20 << 24 | var21 << 16 | var22 << 8 | var23);
        }
        
        lightmapTexture.updateDynamicTexture();
        lightmapUpdateNeeded = false;
        mc.mcProfiler.endSection();
      }
    }
  }
  
  private float func_180438_a(EntityLivingBase p_180438_1_, float partialTicks)
  {
    int var3 = p_180438_1_.getActivePotionEffect(Potion.nightVision).getDuration();
    return var3 > 200 ? 1.0F : 0.7F + MathHelper.sin((var3 - partialTicks) * 3.1415927F * 0.2F) * 0.3F;
  }
  



  public void updateCameraAndRender(float partialTicks)
  {
    frameInit();
    boolean var2 = Display.isActive();
    
    if ((!var2) && (gameSettingspauseOnLostFocus) && ((!gameSettingstouchscreen) || (!Mouse.isButtonDown(1))))
    {
      if (Minecraft.getSystemTime() - prevFrameTime > 500L)
      {
        mc.displayInGameMenu();
      }
      
    }
    else {
      prevFrameTime = Minecraft.getSystemTime();
    }
    
    mc.mcProfiler.startSection("mouse");
    
    if ((var2) && (Minecraft.isRunningOnMac) && (mc.inGameHasFocus) && (!Mouse.isInsideWindow()))
    {
      Mouse.setGrabbed(false);
      Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
      Mouse.setGrabbed(true);
    }
    
    if ((mc.inGameHasFocus) && (var2))
    {
      mc.mouseHelper.mouseXYChange();
      float var13 = gameSettingsmouseSensitivity * 0.6F + 0.2F;
      float var14 = var13 * var13 * var13 * 8.0F;
      float var15 = mc.mouseHelper.deltaX * var14;
      float var16 = mc.mouseHelper.deltaY * var14;
      byte var17 = 1;
      
      if (gameSettingsinvertMouse)
      {
        var17 = -1;
      }
      
      if (gameSettingssmoothCamera)
      {
        smoothCamYaw += var15;
        smoothCamPitch += var16;
        float var18 = partialTicks - smoothCamPartialTicks;
        smoothCamPartialTicks = partialTicks;
        var15 = smoothCamFilterX * var18;
        var16 = smoothCamFilterY * var18;
        Minecraft.thePlayer.setAngles(var15, var16 * var17);
      }
      else
      {
        smoothCamYaw = 0.0F;
        smoothCamPitch = 0.0F;
        Minecraft.thePlayer.setAngles(var15, var16 * var17);
      }
    }
    
    mc.mcProfiler.endSection();
    
    if (!mc.skipRenderWorld)
    {
      anaglyphEnable = gameSettingsanaglyph;
      final ScaledResolution var131 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
      int var141 = var131.getScaledWidth();
      int var151 = var131.getScaledHeight();
      final int var161 = Mouse.getX() * var141 / Minecraft.displayWidth;
      final int var171 = var151 - Mouse.getY() * var151 / Minecraft.displayHeight - 1;
      int var181 = gameSettingslimitFramerate;
      
      if (Minecraft.theWorld != null)
      {
        mc.mcProfiler.startSection("level");
        int var12 = Math.max(Minecraft.func_175610_ah(), 30);
        renderWorld(partialTicks, renderEndNanoTime + 1000000000 / var12);
        
        if (OpenGlHelper.shadersSupported)
        {
          mc.renderGlobal.func_174975_c();
          
          if ((theShaderGroup != null) && (field_175083_ad))
          {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            theShaderGroup.loadShaderGroup(partialTicks);
            GlStateManager.popMatrix();
          }
          
          mc.getFramebuffer().bindFramebuffer(true);
        }
        
        renderEndNanoTime = System.nanoTime();
        mc.mcProfiler.endStartSection("gui");
        
        if ((!gameSettingshideGUI) || (Minecraft.currentScreen != null))
        {
          GlStateManager.alphaFunc(516, 0.1F);
          mc.ingameGUI.func_175180_a(partialTicks);
        }
        
        mc.mcProfiler.endSection();
      }
      else
      {
        GlStateManager.viewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        setupOverlayRendering();
        renderEndNanoTime = System.nanoTime();
      }
      
      if (Minecraft.currentScreen != null)
      {
        GlStateManager.clear(256);
        
        try
        {
          boolean var122 = false;
          
          if (Reflector.EventBus_post.exists())
          {
            var122 = Reflector.postForgeBusEvent(Reflector.DrawScreenEvent_Pre_Constructor, new Object[] { Minecraft.currentScreen, Integer.valueOf(var161), Integer.valueOf(var171), Float.valueOf(partialTicks) });
          }
          
          if (!var122)
          {
            Minecraft.currentScreen.drawScreen(var161, var171, partialTicks);
          }
          
          Reflector.postForgeBusEvent(Reflector.DrawScreenEvent_Post_Constructor, new Object[] { Minecraft.currentScreen, Integer.valueOf(var161), Integer.valueOf(var171), Float.valueOf(partialTicks) });
        }
        catch (Throwable var121)
        {
          CrashReport var10 = CrashReport.makeCrashReport(var121, "Rendering screen");
          CrashReportCategory var11 = var10.makeCategory("Screen render details");
          var11.addCrashSectionCallable("Screen name", new Callable()
          {
            private static final String __OBFID = "CL_00000948";
            
            public String call() {
              return Minecraft.currentScreen.getClass().getCanonicalName();
            }
          });
          var11.addCrashSectionCallable("Mouse location", new Callable()
          {
            private static final String __OBFID = "CL_00000950";
            
            public String call() {
              return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(var161), Integer.valueOf(var171), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
            }
          });
          var11.addCrashSectionCallable("Screen size", new Callable()
          {
            private static final String __OBFID = "CL_00000951";
            
            public String call() {
              return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(var131.getScaledWidth()), Integer.valueOf(var131.getScaledHeight()), Integer.valueOf(Minecraft.displayWidth), Integer.valueOf(Minecraft.displayHeight), Integer.valueOf(var131.getScaleFactor()) });
            }
          });
          throw new net.minecraft.util.ReportedException(var10);
        }
      }
    }
    
    waitForServerThread();
    Lagometer.updateLagometer();
    
    if (gameSettingsofProfiler)
    {
      gameSettingsshowDebugProfilerChart = true;
    }
  }
  
  public void func_152430_c(float p_152430_1_)
  {
    setupOverlayRendering();
    mc.ingameGUI.func_180478_c(new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight));
  }
  
  private boolean func_175070_n()
  {
    if (!field_175073_D)
    {
      return false;
    }
    

    Entity var1 = mc.func_175606_aa();
    boolean var2 = ((var1 instanceof EntityPlayer)) && (!gameSettingshideGUI);
    
    if ((var2) && (!capabilities.allowEdit))
    {
      ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
      
      if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK))
      {
        BlockPos var4 = mc.objectMouseOver.func_178782_a();
        Block var5 = Minecraft.theWorld.getBlockState(var4).getBlock();
        
        if (Minecraft.playerController.func_178889_l() == net.minecraft.world.WorldSettings.GameType.SPECTATOR)
        {
          var2 = (var5.hasTileEntity()) && ((Minecraft.theWorld.getTileEntity(var4) instanceof IInventory));
        }
        else
        {
          var2 = (var3 != null) && ((var3.canDestroy(var5)) || (var3.canPlaceOn(var5)));
        }
      }
    }
    
    return var2;
  }
  

  private void func_175067_i(float p_175067_1_)
  {
    if ((gameSettingsshowDebugInfo) && (!gameSettingshideGUI) && (!Minecraft.thePlayer.func_175140_cp()) && (!gameSettingsfield_178879_v))
    {
      Entity var2 = mc.func_175606_aa();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179090_x();
      GlStateManager.depthMask(false);
      GlStateManager.pushMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      orientCamera(p_175067_1_);
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
  
  public void renderWorld(float partialTicks, long finishTimeNano)
  {
    updateLightmap(partialTicks);
    
    if (mc.func_175606_aa() == null)
    {
      mc.func_175607_a(Minecraft.thePlayer);
    }
    
    getMouseOver(partialTicks);
    GlStateManager.enableDepth();
    GlStateManager.enableAlpha();
    GlStateManager.alphaFunc(516, 0.1F);
    mc.mcProfiler.startSection("center");
    
    if (gameSettingsanaglyph)
    {
      anaglyphField = 0;
      GlStateManager.colorMask(false, true, true, false);
      func_175068_a(0, partialTicks, finishTimeNano);
      anaglyphField = 1;
      GlStateManager.colorMask(true, false, false, false);
      func_175068_a(1, partialTicks, finishTimeNano);
      GlStateManager.colorMask(true, true, true, false);
    }
    else
    {
      func_175068_a(2, partialTicks, finishTimeNano);
    }
    
    mc.mcProfiler.endSection();
  }
  
  private void func_175068_a(int pass, float partialTicks, long finishTimeNano)
  {
    RenderGlobal var5 = mc.renderGlobal;
    EffectRenderer var6 = mc.effectRenderer;
    boolean var7 = func_175070_n();
    GlStateManager.enableCull();
    mc.mcProfiler.endStartSection("clear");
    GlStateManager.viewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
    updateFogColor(partialTicks);
    GlStateManager.clear(16640);
    mc.mcProfiler.endStartSection("camera");
    setupCameraTransform(partialTicks, pass);
    ActiveRenderInfo.updateRenderInfo(Minecraft.thePlayer, gameSettingsthirdPersonView == 2);
    mc.mcProfiler.endStartSection("frustum");
    ClippingHelperImpl.getInstance();
    mc.mcProfiler.endStartSection("culling");
    Frustrum var8 = new Frustrum();
    Entity var9 = mc.func_175606_aa();
    double var10 = lastTickPosX + (posX - lastTickPosX) * partialTicks;
    double var12 = lastTickPosY + (posY - lastTickPosY) * partialTicks;
    double var14 = lastTickPosZ + (posZ - lastTickPosZ) * partialTicks;
    var8.setPosition(var10, var12, var14);
    
    if ((!Config.isSkyEnabled()) && (!Config.isSunMoonEnabled()) && (!Config.isStarsEnabled()))
    {
      GlStateManager.disableBlend();
    }
    else
    {
      setupFog(-1, partialTicks);
      mc.mcProfiler.endStartSection("sky");
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      Project.gluPerspective(getFOVModifier(partialTicks, true), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, clipDistance);
      GlStateManager.matrixMode(5888);
      var5.func_174976_a(partialTicks, pass);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      Project.gluPerspective(getFOVModifier(partialTicks, true), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, farPlaneDistance * MathHelper.field_180189_a);
      GlStateManager.matrixMode(5888);
    }
    
    setupFog(0, partialTicks);
    GlStateManager.shadeModel(7425);
    
    if (posY + var9.getEyeHeight() < 128.0D + gameSettingsofCloudsHeight * 128.0F)
    {
      func_180437_a(var5, partialTicks, pass);
    }
    
    mc.mcProfiler.endStartSection("prepareterrain");
    setupFog(0, partialTicks);
    mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    RenderHelper.disableStandardItemLighting();
    mc.mcProfiler.endStartSection("terrain_setup");
    var5.func_174970_a(var9, partialTicks, var8, field_175084_ae++, Minecraft.thePlayer.func_175149_v());
    
    if ((pass == 0) || (pass == 2))
    {
      mc.mcProfiler.endStartSection("updatechunks");
      Lagometer.timerChunkUpload.start();
      mc.renderGlobal.func_174967_a(finishTimeNano);
      Lagometer.timerChunkUpload.end();
    }
    
    mc.mcProfiler.endStartSection("terrain");
    Lagometer.timerTerrain.start();
    GlStateManager.matrixMode(5888);
    GlStateManager.pushMatrix();
    GlStateManager.disableAlpha();
    var5.func_174977_a(EnumWorldBlockLayer.SOLID, partialTicks, pass, var9);
    GlStateManager.enableAlpha();
    var5.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, var9);
    mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
    var5.func_174977_a(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, var9);
    mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
    Lagometer.timerTerrain.end();
    GlStateManager.shadeModel(7424);
    GlStateManager.alphaFunc(516, 0.1F);
    

    if (!field_175078_W)
    {
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      RenderHelper.enableStandardItemLighting();
      mc.mcProfiler.endStartSection("entities");
      
      if (Reflector.ForgeHooksClient_setRenderPass.exists())
      {
        Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
      }
      
      var5.func_180446_a(var9, var8, partialTicks);
      
      if (Reflector.ForgeHooksClient_setRenderPass.exists())
      {
        Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
      }
      
      RenderHelper.disableStandardItemLighting();
      func_175072_h();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      
      if ((mc.objectMouseOver != null) && (var9.isInsideOfMaterial(Material.water)) && (var7))
      {
        EntityPlayer var16 = (EntityPlayer)var9;
        GlStateManager.disableAlpha();
        mc.mcProfiler.endStartSection("outline");
        var5.drawSelectionBox(var16, mc.objectMouseOver, 0, partialTicks);
        GlStateManager.enableAlpha();
      }
    }
    
    GlStateManager.matrixMode(5888);
    GlStateManager.popMatrix();
    
    if ((var7) && (mc.objectMouseOver != null) && (!var9.isInsideOfMaterial(Material.water)))
    {
      EntityPlayer var16 = (EntityPlayer)var9;
      GlStateManager.disableAlpha();
      mc.mcProfiler.endStartSection("outline");
      var5.drawSelectionBox(var16, mc.objectMouseOver, 0, partialTicks);
      GlStateManager.enableAlpha();
    }
    
    mc.mcProfiler.endStartSection("destroyProgress");
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
    var5.func_174981_a(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var9, partialTicks);
    GlStateManager.disableBlend();
    
    if (!field_175078_W)
    {
      func_180436_i();
      mc.mcProfiler.endStartSection("litParticles");
      var6.renderLitParticles(var9, partialTicks);
      RenderHelper.disableStandardItemLighting();
      setupFog(0, partialTicks);
      mc.mcProfiler.endStartSection("particles");
      var6.renderParticles(var9, partialTicks);
      func_175072_h();
    }
    
    GlStateManager.depthMask(false);
    GlStateManager.enableCull();
    mc.mcProfiler.endStartSection("weather");
    renderRainSnow(partialTicks);
    GlStateManager.depthMask(true);
    var5.func_180449_a(var9, partialTicks);
    GlStateManager.disableBlend();
    GlStateManager.enableCull();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.alphaFunc(516, 0.1F);
    setupFog(0, partialTicks);
    GlStateManager.enableBlend();
    GlStateManager.depthMask(false);
    mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    GlStateManager.shadeModel(7425);
    
    if (Config.isTranslucentBlocksFancy())
    {
      mc.mcProfiler.endStartSection("translucent");
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);
      GlStateManager.disableBlend();
    }
    else
    {
      mc.mcProfiler.endStartSection("translucent");
      var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);
    }
    
    GlStateManager.shadeModel(7424);
    GlStateManager.depthMask(true);
    GlStateManager.enableCull();
    GlStateManager.disableBlend();
    GlStateManager.disableFog();
    if (posY + var9.getEyeHeight() >= 128.0D + gameSettingsofCloudsHeight * 128.0F)
    {
      mc.mcProfiler.endStartSection("aboveClouds");
      func_180437_a(var5, partialTicks, pass);
    }
    if (Reflector.ForgeHooksClient_dispatchRenderLast.exists())
    {
      mc.mcProfiler.endStartSection("FRenderLast");
      Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { var5, Float.valueOf(partialTicks) });
    }
    mc.mcProfiler.endStartSection("hand");
    for (Module eventModule : ModuleManager.activeModules) {
      if (eventModule.getState()) {
        eventModule.onRender();
      }
    }
    boolean renderFirstPersonHand = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { mc.renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
    if (!renderFirstPersonHand)
    {
      GlStateManager.clear(256);
      renderHand(partialTicks, pass);
      func_175067_i(partialTicks);
    }
  }
  
  private void func_180437_a(RenderGlobal p_180437_1_, float partialTicks, int pass) {
    if (Minecraft.gameSettings.shouldRenderClouds())
    {
      mc.mcProfiler.endStartSection("clouds");
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      Project.gluPerspective(getFOVModifier(partialTicks, true), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, clipDistance * 4.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      setupFog(0, partialTicks);
      p_180437_1_.func_180447_b(partialTicks, pass);
      GlStateManager.disableFog();
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      Project.gluPerspective(getFOVModifier(partialTicks, true), Minecraft.displayWidth / Minecraft.displayHeight, 0.05F, farPlaneDistance * MathHelper.field_180189_a);
      GlStateManager.matrixMode(5888);
    }
  }
  
  private void addRainParticles()
  {
    float var1 = Minecraft.theWorld.getRainStrength(1.0F);
    
    if (!Config.isRainFancy())
    {
      var1 /= 2.0F;
    }
    
    if ((var1 != 0.0F) && (Config.isRainSplash()))
    {
      random.setSeed(rendererUpdateCount * 312987231L);
      Entity var2 = mc.func_175606_aa();
      WorldClient var3 = Minecraft.theWorld;
      BlockPos var4 = new BlockPos(var2);
      byte var5 = 10;
      double var6 = 0.0D;
      double var8 = 0.0D;
      double var10 = 0.0D;
      int var12 = 0;
      int var13 = (int)(100.0F * var1 * var1);
      
      if (gameSettingsparticleSetting == 1)
      {
        var13 >>= 1;
      }
      else if (gameSettingsparticleSetting == 2)
      {
        var13 = 0;
      }
      
      for (int var14 = 0; var14 < var13; var14++)
      {
        BlockPos var15 = var3.func_175725_q(var4.add(random.nextInt(var5) - random.nextInt(var5), 0, random.nextInt(var5) - random.nextInt(var5)));
        BiomeGenBase var16 = var3.getBiomeGenForCoords(var15);
        BlockPos var17 = var15.offsetDown();
        Block var18 = var3.getBlockState(var17).getBlock();
        
        if ((var15.getY() <= var4.getY() + var5) && (var15.getY() >= var4.getY() - var5) && (var16.canSpawnLightningBolt()) && (var16.func_180626_a(var15) >= 0.15F))
        {
          float var19 = random.nextFloat();
          float var20 = random.nextFloat();
          
          if (var18.getMaterial() == Material.lava)
          {
            Minecraft.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var15.getX() + var19, var15.getY() + 0.1F - var18.getBlockBoundsMinY(), var15.getZ() + var20, 0.0D, 0.0D, 0.0D, new int[0]);
          }
          else if (var18.getMaterial() != Material.air)
          {
            var18.setBlockBoundsBasedOnState(var3, var17);
            var12++;
            
            if (random.nextInt(var12) == 0)
            {
              var6 = var17.getX() + var19;
              var8 = var17.getY() + 0.1F + var18.getBlockBoundsMaxY() - 1.0D;
              var10 = var17.getZ() + var20;
            }
            
            Minecraft.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, var17.getX() + var19, var17.getY() + 0.1F + var18.getBlockBoundsMaxY(), var17.getZ() + var20, 0.0D, 0.0D, 0.0D, new int[0]);
          }
        }
      }
      
      if ((var12 > 0) && (random.nextInt(3) < rainSoundCounter++))
      {
        rainSoundCounter = 0;
        
        if ((var8 > var4.getY() + 1) && (var3.func_175725_q(var4).getY() > MathHelper.floor_float(var4.getY())))
        {
          Minecraft.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.1F, 0.5F, false);
        }
        else
        {
          Minecraft.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.2F, 1.0F, false);
        }
      }
    }
  }
  



  protected void renderRainSnow(float partialTicks)
  {
    if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists())
    {
      WorldProvider var2 = theWorldprovider;
      Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
      
      if (var3 != null)
      {
        Reflector.callVoid(var3, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), Minecraft.theWorld, mc });
        return;
      }
    }
    
    float var421 = Minecraft.theWorld.getRainStrength(partialTicks);
    
    if (var421 > 0.0F)
    {
      if (Config.isRainOff())
      {
        return;
      }
      
      func_180436_i();
      Entity var431 = mc.func_175606_aa();
      WorldClient var4 = Minecraft.theWorld;
      int var5 = MathHelper.floor_double(posX);
      int var6 = MathHelper.floor_double(posY);
      int var7 = MathHelper.floor_double(posZ);
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      GlStateManager.disableCull();
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.1F);
      double var10 = lastTickPosX + (posX - lastTickPosX) * partialTicks;
      double var12 = lastTickPosY + (posY - lastTickPosY) * partialTicks;
      double var14 = lastTickPosZ + (posZ - lastTickPosZ) * partialTicks;
      int var16 = MathHelper.floor_double(var12);
      byte var17 = 5;
      
      if (Config.isRainFancy())
      {
        var17 = 10;
      }
      
      byte var18 = -1;
      float var19 = rendererUpdateCount + partialTicks;
      
      if (Config.isRainFancy())
      {
        var17 = 10;
      }
      
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      
      for (int var20 = var7 - var17; var20 <= var7 + var17; var20++)
      {
        for (int var21 = var5 - var17; var21 <= var5 + var17; var21++)
        {
          int var22 = (var20 - var7 + 16) * 32 + var21 - var5 + 16;
          float var23 = field_175076_N[var22] * 0.5F;
          float var24 = field_175077_O[var22] * 0.5F;
          BlockPos var25 = new BlockPos(var21, 0, var20);
          BiomeGenBase var26 = var4.getBiomeGenForCoords(var25);
          
          if ((var26.canSpawnLightningBolt()) || (var26.getEnableSnow()))
          {
            int var27 = var4.func_175725_q(var25).getY();
            int var28 = var6 - var17;
            int var29 = var6 + var17;
            
            if (var28 < var27)
            {
              var28 = var27;
            }
            
            if (var29 < var27)
            {
              var29 = var27;
            }
            
            float var30 = 1.0F;
            int var31 = var27;
            
            if (var27 < var16)
            {
              var31 = var16;
            }
            
            if (var28 != var29)
            {
              random.setSeed(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
              float var32 = var26.func_180626_a(new BlockPos(var21, var28, var20));
              


              if (var4.getWorldChunkManager().getTemperatureAtHeight(var32, var27) >= 0.15F)
              {
                if (var18 != 0)
                {
                  if (var18 >= 0)
                  {
                    var8.draw();
                  }
                  
                  var18 = 0;
                  mc.getTextureManager().bindTexture(locationRainPng);
                  var9.startDrawingQuads();
                }
                
                float var33 = ((rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 0x1F) + partialTicks) / 32.0F * (3.0F + random.nextFloat());
                double var42 = var21 + 0.5F - posX;
                double var36 = var20 + 0.5F - posZ;
                float var43 = MathHelper.sqrt_double(var42 * var42 + var36 * var36) / var17;
                float var39 = 1.0F;
                var9.func_178963_b(var4.getCombinedLight(new BlockPos(var21, var31, var20), 0));
                var9.func_178960_a(var39, var39, var39, ((1.0F - var43 * var43) * 0.5F + 0.5F) * var421);
                var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
                var9.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var30, var28 * var30 / 4.0F + var33 * var30);
                var9.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var30, var28 * var30 / 4.0F + var33 * var30);
                var9.addVertexWithUV(var21 + var23 + 0.5D, var29, var20 + var24 + 0.5D, 1.0F * var30, var29 * var30 / 4.0F + var33 * var30);
                var9.addVertexWithUV(var21 - var23 + 0.5D, var29, var20 - var24 + 0.5D, 0.0F * var30, var29 * var30 / 4.0F + var33 * var30);
                var9.setTranslation(0.0D, 0.0D, 0.0D);
              }
              else
              {
                if (var18 != 1)
                {
                  if (var18 >= 0)
                  {
                    var8.draw();
                  }
                  
                  var18 = 1;
                  mc.getTextureManager().bindTexture(locationSnowPng);
                  var9.startDrawingQuads();
                }
                
                float var33 = ((rendererUpdateCount & 0x1FF) + partialTicks) / 512.0F;
                float var44 = random.nextFloat() + var19 * 0.01F * (float)random.nextGaussian();
                float var35 = random.nextFloat() + var19 * (float)random.nextGaussian() * 0.001F;
                double var36 = var21 + 0.5F - posX;
                double var45 = var20 + 0.5F - posZ;
                float var40 = MathHelper.sqrt_double(var36 * var36 + var45 * var45) / var17;
                float var41 = 1.0F;
                var9.func_178963_b((var4.getCombinedLight(new BlockPos(var21, var31, var20), 0) * 3 + 15728880) / 4);
                var9.func_178960_a(var41, var41, var41, ((1.0F - var40 * var40) * 0.3F + 0.5F) * var421);
                var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
                var9.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var30 + var44, var28 * var30 / 4.0F + var33 * var30 + var35);
                var9.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var30 + var44, var28 * var30 / 4.0F + var33 * var30 + var35);
                var9.addVertexWithUV(var21 + var23 + 0.5D, var29, var20 + var24 + 0.5D, 1.0F * var30 + var44, var29 * var30 / 4.0F + var33 * var30 + var35);
                var9.addVertexWithUV(var21 - var23 + 0.5D, var29, var20 - var24 + 0.5D, 0.0F * var30 + var44, var29 * var30 / 4.0F + var33 * var30 + var35);
                var9.setTranslation(0.0D, 0.0D, 0.0D);
              }
            }
          }
        }
      }
      
      if (var18 >= 0)
      {
        var8.draw();
      }
      
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.alphaFunc(516, 0.1F);
      func_175072_h();
    }
  }
  



  public void setupOverlayRendering()
  {
    ScaledResolution var1 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
    GlStateManager.clear(256);
    GlStateManager.matrixMode(5889);
    GlStateManager.loadIdentity();
    GlStateManager.ortho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
    GlStateManager.matrixMode(5888);
    GlStateManager.loadIdentity();
    GlStateManager.translate(0.0F, 0.0F, -2000.0F);
  }
  



  private void updateFogColor(float partialTicks)
  {
    WorldClient var2 = Minecraft.theWorld;
    Entity var3 = mc.func_175606_aa();
    float var4 = 0.25F + 0.75F * gameSettingsrenderDistanceChunks / 32.0F;
    var4 = 1.0F - (float)Math.pow(var4, 0.25D);
    Vec3 var5 = var2.getSkyColor(mc.func_175606_aa(), partialTicks);
    var5 = CustomColorizer.getWorldSkyColor(var5, var2, mc.func_175606_aa(), partialTicks);
    float var6 = (float)xCoord;
    float var7 = (float)yCoord;
    float var8 = (float)zCoord;
    Vec3 var9 = var2.getFogColor(partialTicks);
    var9 = CustomColorizer.getWorldFogColor(var9, var2, mc.func_175606_aa(), partialTicks);
    field_175080_Q = ((float)xCoord);
    field_175082_R = ((float)yCoord);
    field_175081_S = ((float)zCoord);
    

    if (gameSettingsrenderDistanceChunks >= 4)
    {
      double var19 = -1.0D;
      Vec3 var20 = MathHelper.sin(var2.getCelestialAngleRadians(partialTicks)) > 0.0F ? new Vec3(var19, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
      float var13 = (float)var3.getLook(partialTicks).dotProduct(var20);
      
      if (var13 < 0.0F)
      {
        var13 = 0.0F;
      }
      
      if (var13 > 0.0F)
      {
        float[] var21 = provider.calcSunriseSunsetColors(var2.getCelestialAngle(partialTicks), partialTicks);
        
        if (var21 != null)
        {
          var13 *= var21[3];
          field_175080_Q = (field_175080_Q * (1.0F - var13) + var21[0] * var13);
          field_175082_R = (field_175082_R * (1.0F - var13) + var21[1] * var13);
          field_175081_S = (field_175081_S * (1.0F - var13) + var21[2] * var13);
        }
      }
    }
    
    field_175080_Q += (var6 - field_175080_Q) * var4;
    field_175082_R += (var7 - field_175082_R) * var4;
    field_175081_S += (var8 - field_175081_S) * var4;
    float var191 = var2.getRainStrength(partialTicks);
    


    if (var191 > 0.0F)
    {
      float var11 = 1.0F - var191 * 0.5F;
      float var201 = 1.0F - var191 * 0.4F;
      field_175080_Q *= var11;
      field_175082_R *= var11;
      field_175081_S *= var201;
    }
    
    float var11 = var2.getWeightedThunderStrength(partialTicks);
    
    if (var11 > 0.0F)
    {
      float var201 = 1.0F - var11 * 0.5F;
      field_175080_Q *= var201;
      field_175082_R *= var201;
      field_175081_S *= var201;
    }
    
    Block var211 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);
    

    if (cloudFog)
    {
      Vec3 fogYFactor = var2.getCloudColour(partialTicks);
      field_175080_Q = ((float)xCoord);
      field_175082_R = ((float)yCoord);
      field_175081_S = ((float)zCoord);
    }
    else if (var211.getMaterial() == Material.water)
    {
      float var13 = EnchantmentHelper.func_180319_a(var3) * 0.2F;
      
      if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)))
      {
        var13 = var13 * 0.3F + 0.6F;
      }
      
      field_175080_Q = (0.02F + var13);
      field_175082_R = (0.02F + var13);
      field_175081_S = (0.2F + var13);
      Vec3 fogYFactor = CustomColorizer.getUnderwaterColor(Minecraft.theWorld, mc.func_175606_aa().posX, mc.func_175606_aa().posY + 1.0D, mc.func_175606_aa().posZ);
      
      if (fogYFactor != null)
      {
        field_175080_Q = ((float)xCoord);
        field_175082_R = ((float)yCoord);
        field_175081_S = ((float)zCoord);
      }
    }
    else if (var211.getMaterial() == Material.lava)
    {
      field_175080_Q = 0.6F;
      field_175082_R = 0.1F;
      field_175081_S = 0.0F;
    }
    
    float var13 = fogColor2 + (fogColor1 - fogColor2) * partialTicks;
    field_175080_Q *= var13;
    field_175082_R *= var13;
    field_175081_S *= var13;
    double fogYFactor1 = provider.getVoidFogYFactor();
    double var23 = (lastTickPosY + (posY - lastTickPosY) * partialTicks) * fogYFactor1;
    
    if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).isPotionActive(Potion.blindness)))
    {
      int var24 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
      
      if (var24 < 20)
      {
        var23 *= (1.0F - var24 / 20.0F);
      }
      else
      {
        var23 = 0.0D;
      }
    }
    
    if (var23 < 1.0D)
    {
      if (var23 < 0.0D)
      {
        var23 = 0.0D;
      }
      
      var23 *= var23;
      field_175080_Q = ((float)(field_175080_Q * var23));
      field_175082_R = ((float)(field_175082_R * var23));
      field_175081_S = ((float)(field_175081_S * var23));
    }
    


    if (bossColorModifier > 0.0F)
    {
      float var241 = bossColorModifierPrev + (bossColorModifier - bossColorModifierPrev) * partialTicks;
      field_175080_Q = (field_175080_Q * (1.0F - var241) + field_175080_Q * 0.7F * var241);
      field_175082_R = (field_175082_R * (1.0F - var241) + field_175082_R * 0.6F * var241);
      field_175081_S = (field_175081_S * (1.0F - var241) + field_175081_S * 0.6F * var241);
    }
    


    if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).isPotionActive(Potion.nightVision)))
    {
      float var241 = func_180438_a((EntityLivingBase)var3, partialTicks);
      float var17 = 1.0F / field_175080_Q;
      
      if (var17 > 1.0F / field_175082_R)
      {
        var17 = 1.0F / field_175082_R;
      }
      
      if (var17 > 1.0F / field_175081_S)
      {
        var17 = 1.0F / field_175081_S;
      }
      
      field_175080_Q = (field_175080_Q * (1.0F - var241) + field_175080_Q * var17 * var241);
      field_175082_R = (field_175082_R * (1.0F - var241) + field_175082_R * var17 * var241);
      field_175081_S = (field_175081_S * (1.0F - var241) + field_175081_S * var17 * var241);
    }
    
    if (gameSettingsanaglyph)
    {
      float var241 = (field_175080_Q * 30.0F + field_175082_R * 59.0F + field_175081_S * 11.0F) / 100.0F;
      float var17 = (field_175080_Q * 30.0F + field_175082_R * 70.0F) / 100.0F;
      float event = (field_175080_Q * 30.0F + field_175081_S * 70.0F) / 100.0F;
      field_175080_Q = var241;
      field_175082_R = var17;
      field_175081_S = event;
    }
    
    if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists())
    {
      Object event1 = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, var3, var211, Float.valueOf(partialTicks), Float.valueOf(field_175080_Q), Float.valueOf(field_175082_R), Float.valueOf(field_175081_S) });
      Reflector.postForgeBusEvent(event1);
      field_175080_Q = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_red, field_175080_Q);
      field_175082_R = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_green, field_175082_R);
      field_175081_S = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_blue, field_175081_S);
    }
    
    GlStateManager.clearColor(field_175080_Q, field_175082_R, field_175081_S, 0.0F);
  }
  




  private void setupFog(int p_78468_1_, float partialTicks)
  {
    Entity var3 = mc.func_175606_aa();
    boolean var4 = false;
    fogStandard = false;
    
    if ((var3 instanceof EntityPlayer))
    {
      var4 = capabilities.isCreativeMode;
    }
    
    GL11.glFog(2918, setFogColorBuffer(field_175080_Q, field_175082_R, field_175081_S, 1.0F));
    GL11.glNormal3f(0.0F, -1.0F, 0.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    Block var5 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);
    Object event = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogDensity_Constructor, new Object[] { this, var3, var5, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
    
    if (Reflector.postForgeBusEvent(event))
    {
      float var7 = Reflector.getFieldValueFloat(event, Reflector.EntityViewRenderEvent_FogDensity_density, 0.0F);
      GL11.glFogf(2914, var7);




    }
    else if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).isPotionActive(Potion.blindness)))
    {
      float var6 = 5.0F;
      int var71 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
      
      if (var71 < 20)
      {
        var6 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - var71 / 20.0F);
      }
      
      GlStateManager.setFog(9729);
      
      if (p_78468_1_ == -1)
      {
        GlStateManager.setFogStart(0.0F);
        GlStateManager.setFogEnd(var6 * 0.8F);
      }
      else
      {
        GlStateManager.setFogStart(var6 * 0.25F);
        GlStateManager.setFogEnd(var6);
      }
      
      if ((getCapabilitiesGL_NV_fog_distance) && (Config.isFogFancy()))
      {
        GL11.glFogi(34138, 34139);
      }
    }
    else if (cloudFog)
    {
      GlStateManager.setFog(2048);
      GlStateManager.setFogDensity(0.1F);
    }
    else if (var5.getMaterial() == Material.water)
    {
      GlStateManager.setFog(2048);
      
      if (((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)))
      {
        GlStateManager.setFogDensity(0.01F);
      }
      else
      {
        GlStateManager.setFogDensity(0.1F - EnchantmentHelper.func_180319_a(var3) * 0.03F);
      }
      
      if (Config.isClearWater())
      {
        GL11.glFogf(2914, 0.02F);
      }
    }
    else if (var5.getMaterial() == Material.lava)
    {
      GlStateManager.setFog(2048);
      GlStateManager.setFogDensity(2.0F);
    }
    else
    {
      float var6 = farPlaneDistance;
      fogStandard = true;
      GlStateManager.setFog(9729);
      
      if (p_78468_1_ == -1)
      {
        GlStateManager.setFogStart(0.0F);
        GlStateManager.setFogEnd(var6);
      }
      else
      {
        GlStateManager.setFogStart(var6 * Config.getFogStart());
        GlStateManager.setFogEnd(var6);
      }
      
      if (getCapabilitiesGL_NV_fog_distance)
      {
        if (Config.isFogFancy())
        {
          GL11.glFogi(34138, 34139);
        }
        
        if (Config.isFogFast())
        {
          GL11.glFogi(34138, 34140);
        }
      }
      
      if (theWorldprovider.doesXZShowFog((int)posX, (int)posZ))
      {
        GlStateManager.setFogStart(var6 * 0.05F);
        GlStateManager.setFogEnd(var6);
      }
      
      Reflector.postForgeBusEvent(Reflector.newInstance(Reflector.EntityViewRenderEvent_RenderFogEvent_Constructor, new Object[] { this, var3, var5, Float.valueOf(partialTicks), Integer.valueOf(p_78468_1_), Float.valueOf(var6) }));
    }
    

    GlStateManager.enableColorMaterial();
    GlStateManager.enableFog();
    GlStateManager.colorMaterial(1028, 4608);
  }
  



  private FloatBuffer setFogColorBuffer(float p_78469_1_, float p_78469_2_, float p_78469_3_, float p_78469_4_)
  {
    fogColorBuffer.clear();
    fogColorBuffer.put(p_78469_1_).put(p_78469_2_).put(p_78469_3_).put(p_78469_4_);
    fogColorBuffer.flip();
    return fogColorBuffer;
  }
  
  public MapItemRenderer getMapItemRenderer()
  {
    return theMapItemRenderer;
  }
  
  private void waitForServerThread()
  {
    serverWaitTimeCurrent = 0;
    
    if ((Config.isSmoothWorld()) && (Config.isSingleProcessor()))
    {
      if (mc.isIntegratedServerRunning())
      {
        IntegratedServer srv = mc.getIntegratedServer();
        
        if (srv != null)
        {
          boolean paused = mc.isGamePaused();
          
          if ((!paused) && (!(Minecraft.currentScreen instanceof GuiDownloadTerrain)))
          {
            if (serverWaitTime > 0)
            {
              Lagometer.timerServer.start();
              Config.sleep(serverWaitTime);
              Lagometer.timerServer.end();
              serverWaitTimeCurrent = serverWaitTime;
            }
            
            long timeNow = System.nanoTime() / 1000000L;
            
            if ((lastServerTime != 0L) && (lastServerTicks != 0))
            {
              long timeDiff = timeNow - lastServerTime;
              
              if (timeDiff < 0L)
              {
                lastServerTime = timeNow;
                timeDiff = 0L;
              }
              
              if (timeDiff >= 50L)
              {
                lastServerTime = timeNow;
                int ticks = srv.getTickCounter();
                int tickDiff = ticks - lastServerTicks;
                
                if (tickDiff < 0)
                {
                  lastServerTicks = ticks;
                  tickDiff = 0;
                }
                
                if ((tickDiff < 1) && (serverWaitTime < 100))
                {
                  serverWaitTime += 2;
                }
                
                if ((tickDiff > 1) && (serverWaitTime > 0))
                {
                  serverWaitTime -= 1;
                }
                
                lastServerTicks = ticks;
              }
            }
            else
            {
              lastServerTime = timeNow;
              lastServerTicks = srv.getTickCounter();
              avgServerTickDiff = 1.0F;
              avgServerTimeDiff = 50.0F;
            }
          }
          else
          {
            if ((Minecraft.currentScreen instanceof GuiDownloadTerrain))
            {
              Config.sleep(20L);
            }
            
            lastServerTime = 0L;
            lastServerTicks = 0;
          }
        }
      }
    }
    else
    {
      lastServerTime = 0L;
      lastServerTicks = 0;
    }
  }
  
  private void frameInit()
  {
    if (!initialized)
    {
      net.minecraft.src.TextureUtils.registerResourceListener();
      RenderPlayerOF.register();
      initialized = true;
    }
    
    Config.isActing();
    Config.checkDisplayMode();
    WorldClient world = Minecraft.theWorld;
    
    if ((world != null) && (Config.getNewRelease() != null))
    {
      String userEdition = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
      String fullNewVer = userEdition + " " + Config.getNewRelease();
      ChatComponentText msg = new ChatComponentText("A new §eOptiFine§f version is available: §e" + fullNewVer + "§f");
      mc.ingameGUI.getChatGUI().printChatMessage(msg);
      Config.setNewRelease(null);
    }
    
    if ((Minecraft.currentScreen instanceof GuiMainMenu))
    {
      updateMainMenu((GuiMainMenu)Minecraft.currentScreen);
    }
    
    if (updatedWorld != world)
    {
      net.minecraft.src.RandomMobs.worldChanged(updatedWorld, world);
      Config.updateThreadPriorities();
      lastServerTime = 0L;
      lastServerTicks = 0;
      updatedWorld = world;
    }
  }
  
  private void updateMainMenu(GuiMainMenu mainGui)
  {
    try
    {
      String e = null;
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      int day = calendar.get(5);
      int month = calendar.get(2) + 1;
      
      if ((day == 8) && (month == 4))
      {
        e = "Happy birthday, OptiFine!";
      }
      
      if ((day == 14) && (month == 8))
      {
        e = "Happy birthday, sp614x!";
      }
      
      if (e == null)
      {
        return;
      }
      
      Field[] fs = GuiMainMenu.class.getDeclaredFields();
      
      for (int i = 0; i < fs.length; i++)
      {
        if (fs[i].getType() == String.class)
        {
          fs[i].setAccessible(true);
          fs[i].set(mainGui, e);
          break;
        }
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setupCameraTransformExt(float renderPartialTicks, int i) {}
}
