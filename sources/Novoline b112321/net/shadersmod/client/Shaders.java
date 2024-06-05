package net.shadersmod.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.Bg;
import net.Zs;
import net.af_;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.Lang;
import net.optifine.PropertiesOrdered;
import net.optifine.Reflector;
import net.optifine.StrUtils;
import net.shadersmod.client.CustomTexture;
import net.shadersmod.client.EnumShaderOption;
import net.shadersmod.client.HFNoiseTexture;
import net.shadersmod.client.IShaderPack;
import net.shadersmod.client.PropertyDefaultFastFancyOff;
import net.shadersmod.client.PropertyDefaultTrueFalse;
import net.shadersmod.client.SMath;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderOptionProfile;
import net.shadersmod.client.ShaderOptionRest;
import net.shadersmod.client.ShaderPackDefault;
import net.shadersmod.client.ShaderPackFolder;
import net.shadersmod.client.ShaderPackNone;
import net.shadersmod.client.ShaderPackZip;
import net.shadersmod.client.ShaderProfile;
import net.shadersmod.client.ShaderUniformFloat4;
import net.shadersmod.client.ShaderUniformInt;
import net.shadersmod.client.ShaderUtils;
import net.shadersmod.client.Shaders$1;
import net.shadersmod.client.ShadersRender;
import net.shadersmod.client.ShadersTex;
import net.shadersmod.client.SimpleShaderTexture;
import net.shadersmod.common.SMCLog;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class Shaders {
   static Minecraft mc;
   static EntityRenderer entityRenderer;
   public static boolean isInitializedOnce = false;
   public static boolean isShaderPackInitialized = false;
   public static ContextCapabilities capabilities;
   public static String glVersionString;
   public static String glVendorString;
   public static String glRendererString;
   public static boolean hasGlGenMipmap = false;
   public static boolean hasForge = false;
   public static int numberResetDisplayList = 0;
   static boolean needResetModels = false;
   private static int renderDisplayWidth = 0;
   private static int renderDisplayHeight = 0;
   public static int renderWidth = 0;
   public static int renderHeight = 0;
   public static boolean isRenderingWorld = false;
   public static boolean isRenderingSky = false;
   public static boolean isCompositeRendered = false;
   public static boolean isRenderingDfb = false;
   public static boolean isShadowPass = false;
   public static boolean isSleeping;
   private static boolean isHandRenderedMain;
   public static boolean renderItemKeepDepthMask = false;
   public static boolean itemToRenderMainTranslucent = false;
   static float[] sunPosition = new float[4];
   static float[] moonPosition = new float[4];
   static float[] shadowLightPosition = new float[4];
   static float[] upPosition = new float[4];
   static float[] shadowLightPositionVector = new float[4];
   static float[] upPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] sunPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] moonPosModelView = new float[]{0.0F, -100.0F, 0.0F, 0.0F};
   private static float[] tempMat = new float[16];
   static float clearColorR;
   static float clearColorG;
   static float clearColorB;
   static float skyColorR;
   static float skyColorG;
   static float skyColorB;
   static long worldTime = 0L;
   static long lastWorldTime = 0L;
   static long diffWorldTime = 0L;
   static float celestialAngle = 0.0F;
   static float sunAngle = 0.0F;
   static float shadowAngle = 0.0F;
   static int moonPhase = 0;
   static long systemTime = 0L;
   static long lastSystemTime = 0L;
   static long diffSystemTime = 0L;
   static int frameCounter = 0;
   static float frameTime = 0.0F;
   static float frameTimeCounter = 0.0F;
   static int systemTimeInt32 = 0;
   static float rainStrength = 0.0F;
   static float wetness = 0.0F;
   public static float wetnessHalfLife = 600.0F;
   public static float drynessHalfLife = 200.0F;
   public static float eyeBrightnessHalflife = 10.0F;
   static boolean usewetness = false;
   static int isEyeInWater = 0;
   static int eyeBrightness = 0;
   static float eyeBrightnessFadeX = 0.0F;
   static float eyeBrightnessFadeY = 0.0F;
   static float eyePosY = 0.0F;
   static float centerDepth = 0.0F;
   static float centerDepthSmooth = 0.0F;
   static float centerDepthSmoothHalflife = 1.0F;
   static boolean centerDepthSmoothEnabled = false;
   static int superSamplingLevel = 1;
   static float nightVision = 0.0F;
   static float blindness = 0.0F;
   static boolean updateChunksErrorRecorded = false;
   static boolean lightmapEnabled = false;
   static boolean fogEnabled = true;
   public static int entityAttrib = 10;
   public static int midTexCoordAttrib = 11;
   public static int tangentAttrib = 12;
   public static boolean useEntityAttrib = false;
   public static boolean useMidTexCoordAttrib = false;
   public static boolean useMultiTexCoord3Attrib = false;
   public static boolean useTangentAttrib = false;
   public static boolean progUseEntityAttrib = false;
   public static boolean progUseMidTexCoordAttrib = false;
   public static boolean progUseTangentAttrib = false;
   public static int atlasSizeX = 0;
   public static int atlasSizeY = 0;
   public static ShaderUniformFloat4 uniformEntityColor = new ShaderUniformFloat4("entityColor");
   public static ShaderUniformInt uniformEntityId = new ShaderUniformInt("entityId");
   public static ShaderUniformInt uniformBlockEntityId = new ShaderUniformInt("blockEntityId");
   static double previousCameraPositionX;
   static double previousCameraPositionY;
   static double previousCameraPositionZ;
   static double cameraPositionX;
   static double cameraPositionY;
   static double cameraPositionZ;
   static int shadowPassInterval = 0;
   public static boolean needResizeShadow = false;
   static int shadowMapWidth = 1024;
   static int shadowMapHeight = 1024;
   static int spShadowMapWidth = 1024;
   static int spShadowMapHeight = 1024;
   static float shadowMapFOV = 90.0F;
   static float shadowMapHalfPlane = 160.0F;
   static boolean shadowMapIsOrtho = true;
   static float shadowDistanceRenderMul = -1.0F;
   static int shadowPassCounter = 0;
   static int preShadowPassThirdPersonView;
   public static boolean shouldSkipDefaultShadow = false;
   static boolean waterShadowEnabled = false;
   static final int dc = 8;
   static final int cU = 8;
   static final int z = 3;
   static final int a8 = 8;
   static final int aD = 2;
   static int usedColorBuffers = 0;
   static int usedDepthBuffers = 0;
   static int usedShadowColorBuffers = 0;
   static int usedShadowDepthBuffers = 0;
   static int usedColorAttachs = 0;
   static int usedDrawBuffers = 0;
   static int dfb = 0;
   static int sfb = 0;
   private static int[] gbuffersFormat = new int[8];
   private static boolean[] gbuffersClear = new boolean[8];
   public static int activeProgram = 0;
   public static final int j = 0;
   public static final int bE = 1;
   public static final int d6 = 2;
   public static final int bs = 3;
   public static final int ProgramSkyBasic = 4;
   public static final int ProgramSkyTextured = 5;
   public static final int ProgramClouds = 6;
   public static final int ProgramTerrain = 7;
   public static final int bB = 8;
   public static final int ProgramTerrainCutoutMip = 9;
   public static final int ProgramTerrainCutout = 10;
   public static final int ProgramDamagedBlock = 11;
   public static final int ProgramWater = 12;
   public static final int ProgramBlock = 13;
   public static final int ProgramBeaconBeam = 14;
   public static final int ProgramItem = 15;
   public static final int ProgramEntities = 16;
   public static final int ProgramArmorGlint = 17;
   public static final int ProgramSpiderEyes = 18;
   public static final int ProgramHand = 19;
   public static final int ProgramWeather = 20;
   public static final int ProgramComposite = 21;
   public static final int ProgramComposite1 = 22;
   public static final int ProgramComposite2 = 23;
   public static final int ProgramComposite3 = 24;
   public static final int ProgramComposite4 = 25;
   public static final int ProgramComposite5 = 26;
   public static final int ProgramComposite6 = 27;
   public static final int ProgramComposite7 = 28;
   public static final int ProgramFinal = 29;
   public static final int ProgramShadow = 30;
   public static final int ProgramShadowSolid = 31;
   public static final int ProgramShadowCutout = 32;
   public static final int ProgramCount = 33;
   public static final int d8 = 8;
   private static final String[] programNames = new String[]{"", "gbuffers_basic", "gbuffers_textured", "gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_clouds", "gbuffers_terrain", "gbuffers_terrain_solid", "gbuffers_terrain_cutout_mip", "gbuffers_terrain_cutout", "gbuffers_damagedblock", "gbuffers_water", "gbuffers_block", "gbuffers_beaconbeam", "gbuffers_item", "gbuffers_entities", "gbuffers_armor_glint", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite", "composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final", "shadow", "shadow_solid", "shadow_cutout"};
   private static final int[] programBackups = new int[]{0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30};
   static int[] programsID = new int[33];
   private static int[] programsRef = new int[33];
   private static int programIDCopyDepth = 0;
   private static String[] programsDrawBufSettings = new String[33];
   private static String newDrawBufSetting = null;
   static IntBuffer[] programsDrawBuffers = new IntBuffer[33];
   static IntBuffer activeDrawBuffers = null;
   private static String[] programsColorAtmSettings = new String[33];
   private static String newColorAtmSetting = null;
   private static String activeColorAtmSettings = null;
   private static int[] programsCompositeMipmapSetting = new int[33];
   private static int newCompositeMipmapSetting = 0;
   private static int activeCompositeMipmapSetting = 0;
   public static Properties loadedShaders = null;
   public static Properties shadersConfig = null;
   public static ITextureObject defaultTexture = null;
   public static boolean normalMapEnabled = false;
   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
   public static boolean[] shadowMipmapEnabled = new boolean[2];
   public static boolean[] shadowFilterNearest = new boolean[2];
   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
   public static boolean[] shadowColorFilterNearest = new boolean[8];
   public static boolean configTweakBlockDamage = true;
   public static boolean configCloudShadow = true;
   public static float configHandDepthMul = 0.125F;
   public static float configRenderResMul = 1.0F;
   public static float configShadowResMul = 1.0F;
   public static int configTexMinFilB = 0;
   public static int configTexMinFilN = 0;
   public static int configTexMinFilS = 0;
   public static int configTexMagFilB = 0;
   public static int configTexMagFilN = 0;
   public static int configTexMagFilS = 0;
   public static boolean configShadowClipFrustrum = true;
   public static boolean configNormalMap = false;
   public static boolean configSpecularMap = false;
   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static int configAntialiasingLevel = 0;
   public static final int bZ = 3;
   public static final int Q = 2;
   public static final String[] texMinFilDesc = new String[]{"Nearest", "Nearest-Nearest", "Nearest-Linear"};
   public static final String[] texMagFilDesc = new String[]{"Nearest", "Linear"};
   public static final int[] texMinFilValue = new int[]{9728, 9984, 9986};
   public static final int[] texMagFilValue = new int[]{9728, 9729};
   static IShaderPack shaderPack = null;
   public static boolean shaderPackLoaded = false;
   static File currentshader;
   static String currentshadername;
   public static String packNameNone = "OFF";
   static String packNameDefault = "(internal)";
   static String shaderpacksdirname = "shaderpacks";
   static String optionsfilename = "optionsshaders.txt";
   static File shadersdir;
   static File shaderpacksdir;
   static File configFile;
   static ShaderOption[] shaderPackOptions = null;
   static ShaderProfile[] shaderPackProfiles = null;
   static Map shaderPackGuiScreens = null;
   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
   private static Map shaderPackResources = new HashMap();
   private static World currentWorld = null;
   private static List shaderPackDimensions = new ArrayList();
   private static CustomTexture[] customTexturesGbuffers = null;
   private static CustomTexture[] customTexturesComposite = null;
   private static final int bw = 0;
   private static final int cz = 1;
   private static final String[] STAGE_NAMES = new String[]{"gbuffers", "composite"};
   public static final boolean enableShadersOption = true;
   private static final boolean enableShadersDebug = true;
   private static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
   public static float blockLightLevel05 = 0.5F;
   public static float blockLightLevel06 = 0.6F;
   public static float blockLightLevel08 = 0.8F;
   public static float aoLevel = -1.0F;
   public static float sunPathRotation = 0.0F;
   public static float shadowAngleInterval = 0.0F;
   public static int fogMode = 0;
   public static float fogColorR;
   public static float fogColorG;
   public static float fogColorB;
   public static float shadowIntervalSize = 2.0F;
   public static int terrainIconSize = 16;
   public static int[] terrainTextureSize = new int[2];
   private static HFNoiseTexture noiseTexture;
   private static boolean noiseTextureEnabled = false;
   private static int noiseTextureResolution = 256;
   static final int[] dfbColorTexturesA = new int[16];
   static final int[] colorTexturesToggle = new int[8];
   static final int[] colorTextureTextureImageUnit = new int[]{0, 1, 2, 3, 7, 8, 9, 10};
   static final boolean[][] programsToggleColorTextures = new boolean[33][8];
   private static final int bigBufferSize = 2196;
   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(2196).limit(0);
   static final float[] faProjection = new float[16];
   static final float[] faProjectionInverse = new float[16];
   static final float[] faModelView = new float[16];
   static final float[] faModelViewInverse = new float[16];
   static final float[] faShadowProjection = new float[16];
   static final float[] faShadowProjectionInverse = new float[16];
   static final float[] faShadowModelView = new float[16];
   static final float[] faShadowModelViewInverse = new float[16];
   static final FloatBuffer projection = nextFloatBuffer(16);
   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
   static final FloatBuffer modelView = nextFloatBuffer(16);
   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
   static final FloatBuffer previousProjection = nextFloatBuffer(16);
   static final FloatBuffer previousModelView = nextFloatBuffer(16);
   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
   static final IntBuffer drawBuffersNone = nextIntBuffer(8);
   static final IntBuffer drawBuffersAll = nextIntBuffer(8);
   static final IntBuffer drawBuffersClear0 = nextIntBuffer(8);
   static final IntBuffer drawBuffersClear1 = nextIntBuffer(8);
   static final IntBuffer drawBuffersClearColor = nextIntBuffer(8);
   static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8);
   static final IntBuffer[] drawBuffersBuffer = nextIntBufferArray(33, 8);
   static Map mapBlockToEntityData;
   private static final Pattern gbufferFormatPattern = Pattern.compile("[ \t]*const[ \t]*int[ \t]*(\\w+)Format[ \t]*=[ \t]*([RGBA0123456789FUI_SNORM]*)[ \t]*;.*");
   private static final Pattern gbufferClearPattern = Pattern.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)Clear[ \t]*=[ \t]*false[ \t]*;.*");
   private static final Pattern gbufferMipmapEnabledPattern = Pattern.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*");
   private static final String[] formatNames = new String[]{"R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F"};
   private static final int[] formatIds = new int[]{'舩', '舫', '聑', '聘', '辔', '辕', '辖', '辗', '航', '般', '联', '聛', '辘', '辙', '辚', '辛', '舮', '舰', '蠕', '蠔', '舵', '舻', '趃', '趂', '舶', '舼', '赱', '走', 10768, '聗', '聙', '谺'};
   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
   public static int[] entityData = new int[32];
   public static int entityDataIndex = 0;
   private static String[] Y;

   private static ByteBuffer nextByteBuffer(int var0) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + var0);
      return var1.slice();
   }

   private static IntBuffer nextIntBuffer(int var0) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + var0 * 4);
      return var1.asIntBuffer();
   }

   private static FloatBuffer nextFloatBuffer(int var0) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + var0 * 4);
      return var1.asFloatBuffer();
   }

   private static IntBuffer[] nextIntBufferArray(int var0, int var1) {
      IntBuffer[] var2 = new IntBuffer[var0];

      for(int var3 = 0; var3 < var0; ++var3) {
         var2[var3] = nextIntBuffer(var1);
      }

      return var2;
   }

   public static void loadConfig() {
      ShaderOption.p();
      SMCLog.info("Load ShadersMod configuration.");

      try {
         if(!shaderpacksdir.exists()) {
            shaderpacksdir.mkdir();
         }
      } catch (Exception var11) {
         SMCLog.severe("Failed to open the shaderpacks directory: " + shaderpacksdir);
      }

      shadersConfig = new PropertiesOrdered();
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
      if(configFile.exists()) {
         try {
            FileReader var1 = new FileReader(configFile);
            shadersConfig.load(var1);
            var1.close();
         } catch (Exception var10) {
            ;
         }
      }

      if(!configFile.exists()) {
         try {
            storeConfig();
         } catch (Exception var9) {
            ;
         }
      }

      EnumShaderOption[] var12 = EnumShaderOption.values();
      int var3 = var12.length;
      int var4 = 0;
      if(var4 < var3) {
         EnumShaderOption var5 = var12[var4];
         String var6 = var5.getPropertyKey();
         String var7 = var5.getValueDefault();
         String var8 = shadersConfig.getProperty(var6, var7);
         setEnumShaderOption(var5, var8);
         ++var4;
      }

      loadShaderPack();
   }

   private static void setEnumShaderOption(EnumShaderOption var0, String var1) {
      String[] var2 = ShaderOption.p();
      if(var1 == null) {
         var1 = var0.getValueDefault();
      }

      switch(Shaders$1.$SwitchMap$net$shadersmod$client$EnumShaderOption[var0.ordinal()]) {
      case 1:
         configAntialiasingLevel = Config.parseInt(var1, 0);
      case 2:
         configNormalMap = Config.parseBoolean(var1, true);
      case 3:
         configSpecularMap = Config.parseBoolean(var1, true);
      case 4:
         configRenderResMul = Config.parseFloat(var1, 1.0F);
      case 5:
         configShadowResMul = Config.parseFloat(var1, 1.0F);
      case 6:
         configHandDepthMul = Config.parseFloat(var1, 0.125F);
      case 7:
         configCloudShadow = Config.parseBoolean(var1, true);
      case 8:
         configOldHandLight.setPropertyValue(var1);
      case 9:
         configOldLighting.setPropertyValue(var1);
      case 10:
         currentshadername = var1;
      case 11:
         configTweakBlockDamage = Config.parseBoolean(var1, true);
      case 12:
         configShadowClipFrustrum = Config.parseBoolean(var1, true);
      case 13:
         configTexMinFilB = Config.parseInt(var1, 0);
      case 14:
         configTexMinFilN = Config.parseInt(var1, 0);
      case 15:
         configTexMinFilS = Config.parseInt(var1, 0);
      case 16:
         configTexMagFilB = Config.parseInt(var1, 0);
      case 17:
         configTexMagFilB = Config.parseInt(var1, 0);
      case 18:
         configTexMagFilB = Config.parseInt(var1, 0);
      default:
         throw new IllegalArgumentException("Unknown option: " + var0);
      }
   }

   public static void storeConfig() {
      ShaderOption.p();
      SMCLog.info("Save ShadersMod configuration.");
      if(shadersConfig == null) {
         shadersConfig = new PropertiesOrdered();
      }

      EnumShaderOption[] var1 = EnumShaderOption.values();
      int var3 = var1.length;
      int var4 = 0;
      if(var4 < var3) {
         EnumShaderOption var5 = var1[var4];
         String var6 = var5.getPropertyKey();
         String var7 = getEnumShaderOption(var5);
         shadersConfig.setProperty(var6, var7);
         ++var4;
      }

      try {
         FileWriter var2 = new FileWriter(configFile);
         shadersConfig.store(var2, (String)null);
         var2.close();
      } catch (Exception var8) {
         SMCLog.severe("Error saving configuration: " + var8.getClass().getName() + ": " + var8.getMessage());
      }

   }

   public static String getEnumShaderOption(EnumShaderOption var0) {
      String[] var1 = ShaderOption.p();
      switch(Shaders$1.$SwitchMap$net$shadersmod$client$EnumShaderOption[var0.ordinal()]) {
      case 1:
         return Integer.toString(configAntialiasingLevel);
      case 2:
         return Boolean.toString(configNormalMap);
      case 3:
         return Boolean.toString(configSpecularMap);
      case 4:
         return Float.toString(configRenderResMul);
      case 5:
         return Float.toString(configShadowResMul);
      case 6:
         return Float.toString(configHandDepthMul);
      case 7:
         return Boolean.toString(configCloudShadow);
      case 8:
         return configOldHandLight.getPropertyValue();
      case 9:
         return configOldLighting.getPropertyValue();
      case 10:
         return currentshadername;
      case 11:
         return Boolean.toString(configTweakBlockDamage);
      case 12:
         return Boolean.toString(configShadowClipFrustrum);
      case 13:
         return Integer.toString(configTexMinFilB);
      case 14:
         return Integer.toString(configTexMinFilN);
      case 15:
         return Integer.toString(configTexMinFilS);
      case 16:
         return Integer.toString(configTexMagFilB);
      case 17:
         return Integer.toString(configTexMagFilB);
      case 18:
         return Integer.toString(configTexMagFilB);
      default:
         throw new IllegalArgumentException("Unknown option: " + var0);
      }
   }

   public static void setShaderPack(String var0) {
      currentshadername = var0;
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), var0);
      loadShaderPack();
   }

   public static void loadShaderPack() {
      ShaderOption.p();
      boolean var1 = shaderPackLoaded;
      boolean var2 = isOldLighting();
      shaderPackLoaded = false;
      if(shaderPack != null) {
         shaderPack.close();
         shaderPack = null;
         shaderPackResources.clear();
         shaderPackDimensions.clear();
         shaderPackOptions = null;
         shaderPackProfiles = null;
         shaderPackGuiScreens = null;
         shaderPackClouds.resetValue();
         shaderPackOldHandLight.resetValue();
         shaderPackDynamicHandLight.resetValue();
         shaderPackOldLighting.resetValue();
         resetCustomTextures();
      }

      boolean var3 = false;
      if(Config.isAntialiasing()) {
         SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
         var3 = true;
      }

      if(Config.isAnisotropicFiltering()) {
         SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
         var3 = true;
      }

      String var4 = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), packNameDefault);
      if(!var4.isEmpty() && !var4.equals(packNameNone) && !var3) {
         if(var4.equals(packNameDefault)) {
            shaderPack = new ShaderPackDefault();
            shaderPackLoaded = true;
         }

         try {
            File var5 = new File(shaderpacksdir, var4);
            if(var5.isDirectory()) {
               shaderPack = new ShaderPackFolder(var4, var5);
               shaderPackLoaded = true;
            }

            if(var5.isFile() && var4.toLowerCase().endsWith(".zip")) {
               shaderPack = new ShaderPackZip(var4, var5);
               shaderPackLoaded = true;
            }
         } catch (Exception var7) {
            ;
         }
      }

      if(shaderPack != null) {
         SMCLog.info("Loaded shaderpack: " + getShaderPackName());
      }

      SMCLog.info("No shaderpack loaded.");
      shaderPack = new ShaderPackNone();
      loadShaderPackResources();
      loadShaderPackDimensions();
      shaderPackOptions = loadShaderPackOptions();
      ax();
      boolean var8 = shaderPackLoaded != var1;
      boolean var6 = isOldLighting() != var2;
      if(var8 || var6) {
         DefaultVertexFormats.updateVertexFormats();
         if(Reflector.LightUtil.exists()) {
            Reflector.LightUtil_itemConsumer.setValue((Object)null);
            Reflector.LightUtil_tessellator.setValue((Object)null);
         }

         updateBlockLightLevel();
         mc.scheduleResourcesRefresh();
      }

   }

   private static void loadShaderPackDimensions() {
      ShaderOption.p();
      shaderPackDimensions.clear();
      int var1 = -128;
      if(var1 <= 128) {
         String var2 = "/shaders/world" + var1;
         if(shaderPack.hasDirectory(var2)) {
            shaderPackDimensions.add(Integer.valueOf(var1));
         }

         ++var1;
      }

      if(!shaderPackDimensions.isEmpty()) {
         Integer[] var4 = (Integer[])((Integer[])((Integer[])shaderPackDimensions.toArray(new Integer[shaderPackDimensions.size()])));
         Config.dbg("[Shaders] Worlds: " + Config.a((Object[])var4));
      }

   }

   private static void ax() {
      shaderPackClouds.resetValue();
      shaderPackOldHandLight.resetValue();
      shaderPackDynamicHandLight.resetValue();
      shaderPackOldLighting.resetValue();
      ShaderOption.p();
      shaderPackShadowTranslucent.resetValue();
      shaderPackUnderwaterOverlay.resetValue();
      shaderPackSun.resetValue();
      shaderPackMoon.resetValue();
      shaderPackVignette.resetValue();
      shaderPackBackFaceSolid.resetValue();
      shaderPackBackFaceCutout.resetValue();
      shaderPackBackFaceCutoutMipped.resetValue();
      shaderPackBackFaceTranslucent.resetValue();
      Bg.a();
      if(shaderPack != null) {
         Bg.a(shaderPack);
         String var1 = "/shaders/shaders.properties";

         try {
            InputStream var2 = shaderPack.getResourceAsStream(var1);
            return;
         } catch (IOException var3) {
            Config.warn("[Shaders] Error reading: " + var1);
         }
      }

   }

   private static CustomTexture[] loadCustomTextures(Properties var0, int var1) {
      ShaderOption.p();
      String var3 = "texture." + STAGE_NAMES[var1] + ".";
      Set var4 = var0.keySet();
      ArrayList var5 = new ArrayList();
      Iterator var6 = var4.iterator();
      if(var6.hasNext()) {
         Object var7 = var6.next();
         String var8 = (String)var7;
         if(var8.startsWith(var3)) {
            String var9 = var8.substring(var3.length());
            String var10 = var0.getProperty(var8).trim();
            int var11 = getTextureIndex(var1, var9);
            SMCLog.warning("Invalid texture name: " + var8);

            try {
               String var12 = "shaders/" + StrUtils.removePrefix(var10, "/");
               InputStream var13 = shaderPack.getResourceAsStream(var12);
               if(var13 == null) {
                  SMCLog.warning("Texture not found: " + var10);
               }

               IOUtils.closeQuietly(var13);
               SimpleShaderTexture var14 = new SimpleShaderTexture(var12);
               var14.loadTexture(mc.getResourceManager());
               CustomTexture var15 = new CustomTexture(var11, var12, var14);
               var5.add(var15);
            } catch (IOException var16) {
               SMCLog.warning("Error loading texture: " + var10);
               SMCLog.warning("" + var16.getClass().getName() + ": " + var16.getMessage());
            }
         }
      }

      if(var5.size() <= 0) {
         return null;
      } else {
         CustomTexture[] var17 = (CustomTexture[])((CustomTexture[])((CustomTexture[])var5.toArray(new CustomTexture[var5.size()])));
         return var17;
      }
   }

   private static int getTextureIndex(int var0, String var1) {
      String[] var2 = ShaderOption.p();
      if(var0 == 0) {
         if(var1.equals("texture")) {
            return 0;
         }

         if(var1.equals("lightmap")) {
            return 1;
         }

         if(var1.equals("normals")) {
            return 2;
         }

         if(var1.equals("specular")) {
            return 3;
         }

         if(var1.equals("shadowtex0") || var1.equals("watershadow")) {
            return 4;
         }

         if(var1.equals("shadow")) {
            return waterShadowEnabled?5:4;
         }

         if(var1.equals("shadowtex1")) {
            return 5;
         }

         if(var1.equals("depthtex0")) {
            return 6;
         }

         if(var1.equals("gaux1")) {
            return 7;
         }

         if(var1.equals("gaux2")) {
            return 8;
         }

         if(var1.equals("gaux3")) {
            return 9;
         }

         if(var1.equals("gaux4")) {
            return 10;
         }

         if(var1.equals("depthtex1")) {
            return 12;
         }

         if(var1.equals("shadowcolor0") || var1.equals("shadowcolor")) {
            return 13;
         }

         if(var1.equals("shadowcolor1")) {
            return 14;
         }

         if(var1.equals("noisetex")) {
            return 15;
         }
      }

      if(var0 == 1) {
         if(var1.equals("colortex0") || var1.equals("colortex0")) {
            return 0;
         }

         if(var1.equals("colortex1") || var1.equals("gdepth")) {
            return 1;
         }

         if(var1.equals("colortex2") || var1.equals("gnormal")) {
            return 2;
         }

         if(var1.equals("colortex3") || var1.equals("composite")) {
            return 3;
         }

         if(var1.equals("shadowtex0") || var1.equals("watershadow")) {
            return 4;
         }

         if(var1.equals("shadow")) {
            return waterShadowEnabled?5:4;
         }

         if(var1.equals("shadowtex1")) {
            return 5;
         }

         if(var1.equals("depthtex0") || var1.equals("gdepthtex")) {
            return 6;
         }

         if(var1.equals("colortex4") || var1.equals("gaux1")) {
            return 7;
         }

         if(var1.equals("colortex5") || var1.equals("gaux2")) {
            return 8;
         }

         if(var1.equals("colortex6") || var1.equals("gaux3")) {
            return 9;
         }

         if(var1.equals("colortex7") || var1.equals("gaux4")) {
            return 10;
         }

         if(var1.equals("depthtex1")) {
            return 11;
         }

         if(var1.equals("depthtex2")) {
            return 12;
         }

         if(var1.equals("shadowcolor0") || var1.equals("shadowcolor")) {
            return 13;
         }

         if(var1.equals("shadowcolor1")) {
            return 14;
         }

         if(var1.equals("noisetex")) {
            return 15;
         }
      }

      return -1;
   }

   private static void bindCustomTextures(CustomTexture[] var0) {
      String[] var1 = ShaderOption.p();
      if(var0 != null) {
         int var3 = var0.length;
         int var4 = 0;
         if(var4 < var3) {
            CustomTexture var5 = var0[var4];
            GlStateManager.setActiveTexture('蓀' + var5.getTextureUnit());
            ITextureObject var6 = var5.getTexture();
            GlStateManager.bindTexture(var6.getGlTextureId());
            ++var4;
         }
      }

   }

   private static void resetCustomTextures() {
      deleteCustomTextures(customTexturesGbuffers);
      deleteCustomTextures(customTexturesComposite);
      customTexturesGbuffers = null;
      customTexturesComposite = null;
   }

   private static void deleteCustomTextures(CustomTexture[] var0) {
      String[] var1 = ShaderOption.p();
      if(var0 != null) {
         int var3 = var0.length;
         int var4 = 0;
         if(var4 < var3) {
            CustomTexture var5 = var0[var4];
            ITextureObject var6 = var5.getTexture();
            TextureUtil.deleteTexture(var6.getGlTextureId());
            ++var4;
         }
      }

   }

   public static ShaderOption[] getShaderPackOptions(String var0) {
      ShaderOption.p();
      ShaderOption[] var2 = (ShaderOption[])((ShaderOption[])shaderPackOptions.clone());
      if(shaderPackGuiScreens == null) {
         if(shaderPackProfiles != null) {
            ShaderOptionProfile var12 = new ShaderOptionProfile(shaderPackProfiles, var2);
            var2 = (ShaderOption[])((ShaderOption[])((ShaderOption[])Config.addObjectToArray(var2, var12, 0)));
         }

         var2 = getVisibleOptions(var2);
         return var2;
      } else {
         String var3 = var0 != null?"screen." + var0:"screen";
         ShaderOption[] var4 = (ShaderOption[])((ShaderOption[])shaderPackGuiScreens.get(var3));
         if(var4 == null) {
            return new ShaderOption[0];
         } else {
            ArrayList var5 = new ArrayList();
            int var7 = var4.length;
            int var8 = 0;
            if(var8 < var7) {
               ShaderOption var9 = var4[var8];
               if(var9 == null) {
                  var5.add((ShaderOption)null);
               }

               if(var9 instanceof ShaderOptionRest) {
                  ShaderOption[] var10 = getShaderOptionsRest(shaderPackGuiScreens, var2);
                  var5.addAll(Arrays.asList(var10));
               }

               var5.add(var9);
               ++var8;
            }

            ShaderOption[] var6 = (ShaderOption[])((ShaderOption[])((ShaderOption[])var5.toArray(new ShaderOption[var5.size()])));
            return var6;
         }
      }
   }

   private static ShaderOption[] getShaderOptionsRest(Map var0, ShaderOption[] var1) {
      HashSet var3 = new HashSet();
      ShaderOption.p();
      Iterator var4 = var0.keySet().iterator();
      if(var4.hasNext()) {
         String var5 = (String)var4.next();
         ShaderOption[] var6 = (ShaderOption[])((ShaderOption[])var0.get(var5));
         int var8 = var6.length;
         int var9 = 0;
         if(var9 < var8) {
            ShaderOption var10 = var6[var9];
            if(var10 != null) {
               var3.add(var10.getName());
            }

            ++var9;
         }
      }

      ArrayList var11 = new ArrayList();
      int var13 = var1.length;
      int var7 = 0;
      if(var7 < var13) {
         ShaderOption var15 = var1[var7];
         if(var15.isVisible()) {
            String var17 = var15.getName();
            if(!var3.contains(var17)) {
               var11.add(var15);
            }
         }

         ++var7;
      }

      ShaderOption[] var12 = (ShaderOption[])((ShaderOption[])((ShaderOption[])var11.toArray(new ShaderOption[var11.size()])));
      return var12;
   }

   public static ShaderOption getShaderOption(String var0) {
      return ShaderUtils.getShaderOption(var0, shaderPackOptions);
   }

   public static ShaderOption[] getShaderPackOptions() {
      return shaderPackOptions;
   }

   private static ShaderOption[] getVisibleOptions(ShaderOption[] var0) {
      ShaderOption.p();
      ArrayList var2 = new ArrayList();
      int var4 = var0.length;
      int var5 = 0;
      if(var5 < var4) {
         ShaderOption var6 = var0[var5];
         if(var6.isVisible()) {
            var2.add(var6);
         }

         ++var5;
      }

      ShaderOption[] var3 = (ShaderOption[])((ShaderOption[])((ShaderOption[])var2.toArray(new ShaderOption[var2.size()])));
      return var3;
   }

   public static void saveShaderPackOptions() {
      saveShaderPackOptions(shaderPackOptions, shaderPack);
   }

   private static void saveShaderPackOptions(ShaderOption[] var0, IShaderPack var1) {
      ShaderOption.p();
      Properties var3 = new Properties();
      if(shaderPackOptions != null) {
         int var5 = var0.length;
         int var6 = 0;
         if(var6 < var5) {
            ShaderOption var7 = var0[var6];
            if(var7.isChanged() && var7.isEnabled()) {
               var3.setProperty(var7.getName(), var7.getValue());
            }

            ++var6;
         }
      }

      IShaderPack var10000 = var1;
      Properties var10001 = var3;

      try {
         saveOptionProperties(var10000, var10001);
      } catch (IOException var8) {
         Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
         var8.printStackTrace();
      }

   }

   private static void saveOptionProperties(IShaderPack var0, Properties var1) throws IOException {
      ShaderOption.p();
      String var3 = shaderpacksdirname + "/" + var0.getName() + ".txt";
      File var4 = new File(Minecraft.getInstance().mcDataDir, var3);
      if(var1.isEmpty()) {
         var4.delete();
      }

      FileOutputStream var5 = new FileOutputStream(var4);
      var1.store(var5, (String)null);
      var5.flush();
      var5.close();
   }

   private static ShaderOption[] loadShaderPackOptions() {
      String[] var0 = ShaderOption.p();

      try {
         ShaderOption[] var1 = Zs.a(shaderPack, programNames, shaderPackDimensions);
         Properties var2 = loadOptionProperties(shaderPack);
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            ShaderOption var6 = var1[var5];
            String var7 = var2.getProperty(var6.getName());
            var6.resetValue();
            if(!var6.setValue(var7)) {
               Config.warn("[Shaders] Invalid value, option: " + var6.getName() + ", value: " + var7);
            }

            ++var5;
         }

         return var1;
      } catch (IOException var8) {
         Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
         var8.printStackTrace();
         return null;
      }
   }

   private static Properties loadOptionProperties(IShaderPack var0) throws IOException {
      Properties var2 = new Properties();
      String var3 = shaderpacksdirname + "/" + var0.getName() + ".txt";
      ShaderOption.p();
      File var4 = new File(Minecraft.getInstance().mcDataDir, var3);
      if(var4.exists() && var4.isFile() && var4.canRead()) {
         FileInputStream var5 = new FileInputStream(var4);
         var2.load(var5);
         var5.close();
         return var2;
      } else {
         return var2;
      }
   }

   public static ShaderOption[] getChangedOptions(ShaderOption[] var0) {
      ShaderOption.p();
      ArrayList var2 = new ArrayList();
      int var4 = var0.length;
      int var5 = 0;
      if(var5 < var4) {
         ShaderOption var6 = var0[var5];
         if(var6.isEnabled() && var6.isChanged()) {
            var2.add(var6);
         }

         ++var5;
      }

      ShaderOption[] var3 = (ShaderOption[])((ShaderOption[])((ShaderOption[])var2.toArray(new ShaderOption[var2.size()])));
      return var3;
   }

   private static String applyOptions(String var0, ShaderOption[] var1) {
      String[] var2 = ShaderOption.p();
      if(var1 != null && var1.length > 0) {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            ShaderOption var6 = var1[var5];
            var6.getName();
            if(var6.matchesLine(var0)) {
               var0 = var6.getSourceLine();
            }

            ++var5;
         }
      }

      return var0;
   }

   static ArrayList listOfShaders() {
      ArrayList var1 = new ArrayList();
      var1.add(packNameNone);
      ShaderOption.p();
      var1.add(packNameDefault);

      try {
         if(!shaderpacksdir.exists()) {
            shaderpacksdir.mkdir();
         }

         File[] var2 = shaderpacksdir.listFiles();
         int var4 = var2.length;
         int var5 = 0;
         if(var5 < var4) {
            File var6 = var2[var5];
            String var7 = var6.getName();
            if(var6.isDirectory()) {
               File var8 = new File(var6, "shaders");
               if(var8.exists() && var8.isDirectory()) {
                  var1.add(var7);
               }
            }

            if(var6.isFile() && var7.toLowerCase().endsWith(".zip")) {
               var1.add(var7);
            }

            ++var5;
         }
      } catch (Exception var9) {
         ;
      }

      return var1;
   }

   static String versiontostring(int var0) {
      String var1 = Integer.toString(var0);
      return Integer.parseInt(var1.substring(1, 3)) + "." + Integer.parseInt(var1.substring(3, 5)) + "." + Integer.parseInt(var1.substring(5));
   }

   static void checkOptifine() {
   }

   public static int checkFramebufferStatus(String var0) {
      ShaderOption.p();
      int var2 = EXTFramebufferObject.glCheckFramebufferStatusEXT('赀');
      if(var2 != '賕') {
         System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[]{Integer.valueOf(var2), var0});
      }

      return var2;
   }

   public static int checkGLError(String var0) {
      ShaderOption.p();
      int var2 = GL11.glGetError();
      if(var2 != 0) {
         boolean var3 = false;
         if(!var3) {
            if(var2 == 1286) {
               int var4 = EXTFramebufferObject.glCheckFramebufferStatusEXT('赀');
               System.err.format("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", new Object[]{Integer.valueOf(var2), GLU.gluErrorString(var2), Integer.valueOf(var4), var0});
            }

            System.err.format("GL error 0x%04X: %s at %s\n", new Object[]{Integer.valueOf(var2), GLU.gluErrorString(var2), var0});
         }
      }

      return var2;
   }

   public static int checkGLError(String var0, String var1) {
      ShaderOption.p();
      int var3 = GL11.glGetError();
      if(var3 != 0) {
         System.err.format("GL error 0x%04x: %s at %s %s\n", new Object[]{Integer.valueOf(var3), GLU.gluErrorString(var3), var0, var1});
      }

      return var3;
   }

   public static int checkGLError(String var0, String var1, String var2) {
      ShaderOption.p();
      int var4 = GL11.glGetError();
      if(var4 != 0) {
         System.err.format("GL error 0x%04x: %s at %s %s %s\n", new Object[]{Integer.valueOf(var4), GLU.gluErrorString(var4), var0, var1, var2});
      }

      return var4;
   }

   private static void printChat(String var0) {
      mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var0)));
   }

   private static void printChatAndLogError(String var0) {
      SMCLog.severe(var0);
      mc.ingameGUI.n().a((IChatComponent)(new ChatComponentText(var0)));
   }

   public static void printIntBuffer(String var0, IntBuffer var1) {
      StringBuilder var3 = new StringBuilder(128);
      ShaderOption.p();
      var3.append(var0).append(" [pos ").append(var1.position()).append(" lim ").append(var1.limit()).append(" cap ").append(var1.capacity()).append(" :");
      int var4 = var1.limit();
      int var5 = 0;
      if(var5 < var4) {
         var3.append(" ").append(var1.get(var5));
         ++var5;
      }

      var3.append("]");
      SMCLog.info(var3.toString());
   }

   public static void startup(Minecraft var0) {
      checkShadersModInstalled();
      ShaderOption.p();
      mc = var0;
      var0 = Minecraft.getInstance();
      capabilities = GLContext.getCapabilities();
      glVersionString = GL11.glGetString(7938);
      glVendorString = GL11.glGetString(7936);
      glRendererString = GL11.glGetString(7937);
      SMCLog.info("ShadersMod version: 2.4.12");
      SMCLog.info("OpenGL Version: " + glVersionString);
      SMCLog.info("Vendor:  " + glVendorString);
      SMCLog.info("Renderer: " + glRendererString);
      SMCLog.info("Capabilities: " + (capabilities.OpenGL20?" 2.0 ":" - ") + (capabilities.OpenGL21?" 2.1 ":" - ") + (capabilities.OpenGL30?" 3.0 ":" - ") + (capabilities.OpenGL32?" 3.2 ":" - ") + (capabilities.OpenGL40?" 4.0 ":" - "));
      SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger('蠤'));
      SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger('賟'));
      SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger('衲'));
      hasGlGenMipmap = capabilities.OpenGL30;
      loadConfig();
   }

   private static String toStringYN(boolean var0) {
      return "Y";
   }

   public static void updateBlockLightLevel() {
      String[] var0 = ShaderOption.p();
      if(isOldLighting()) {
         blockLightLevel05 = 0.5F;
         blockLightLevel06 = 0.6F;
         blockLightLevel08 = 0.8F;
      }

      blockLightLevel05 = 1.0F;
      blockLightLevel06 = 1.0F;
      blockLightLevel08 = 1.0F;
   }

   public static boolean isOldHandLight() {
      String[] var0 = ShaderOption.p();
      return !configOldHandLight.isDefault()?configOldHandLight.isTrue():shaderPackOldHandLight.isDefault() || shaderPackOldHandLight.isTrue();
   }

   public static boolean isDynamicHandLight() {
      String[] var0 = ShaderOption.p();
      return shaderPackDynamicHandLight.isDefault() || shaderPackDynamicHandLight.isTrue();
   }

   public static boolean isOldLighting() {
      String[] var0 = ShaderOption.p();
      return !configOldLighting.isDefault()?configOldLighting.isTrue():shaderPackOldLighting.isDefault() || shaderPackOldLighting.isTrue();
   }

   public static boolean isRenderShadowTranslucent() {
      String[] var0 = ShaderOption.p();
      return !shaderPackShadowTranslucent.isFalse();
   }

   public static boolean isUnderwaterOverlay() {
      String[] var0 = ShaderOption.p();
      return !shaderPackUnderwaterOverlay.isFalse();
   }

   public static boolean isSun() {
      String[] var0 = ShaderOption.p();
      return !shaderPackSun.isFalse();
   }

   public static boolean isMoon() {
      String[] var0 = ShaderOption.p();
      return !shaderPackMoon.isFalse();
   }

   public static boolean isVignette() {
      String[] var0 = ShaderOption.p();
      return !shaderPackVignette.isFalse();
   }

   public static boolean isRenderBackFace(EnumWorldBlockLayer var0) {
      String[] var1 = ShaderOption.p();
      switch(Shaders$1.$SwitchMap$net$minecraft$util$EnumWorldBlockLayer[var0.ordinal()]) {
      case 1:
         return shaderPackBackFaceSolid.isTrue();
      case 2:
         return shaderPackBackFaceCutout.isTrue();
      case 3:
         return shaderPackBackFaceCutoutMipped.isTrue();
      case 4:
         return shaderPackBackFaceTranslucent.isTrue();
      default:
         return false;
      }
   }

   public static void init() {
      String[] var0 = ShaderOption.p();
      if(!isInitializedOnce) {
         isInitializedOnce = true;
         boolean var1 = true;
      }

      boolean var12 = false;
      if(!isShaderPackInitialized) {
         checkGLError("Shaders.init pre");
         if(getShaderPackName() != null) {
            ;
         }

         if(!capabilities.OpenGL20) {
            printChatAndLogError("No OpenGL 2.0");
         }

         if(!capabilities.GL_EXT_framebuffer_object) {
            printChatAndLogError("No EXT_framebuffer_object");
         }

         dfbDrawBuffers.position(0).limit(8);
         dfbColorTextures.position(0).limit(16);
         dfbDepthTextures.position(0).limit(3);
         sfbDrawBuffers.position(0).limit(8);
         sfbDepthTextures.position(0).limit(2);
         sfbColorTextures.position(0).limit(8);
         usedColorBuffers = 4;
         usedDepthBuffers = 1;
         usedShadowColorBuffers = 0;
         usedShadowDepthBuffers = 0;
         usedColorAttachs = 1;
         usedDrawBuffers = 1;
         Arrays.fill((int[])gbuffersFormat, 6408);
         Arrays.fill(gbuffersClear, true);
         Arrays.fill(shadowHardwareFilteringEnabled, false);
         Arrays.fill(shadowMipmapEnabled, false);
         Arrays.fill(shadowFilterNearest, false);
         Arrays.fill(shadowColorMipmapEnabled, false);
         Arrays.fill(shadowColorFilterNearest, false);
         centerDepthSmoothEnabled = false;
         noiseTextureEnabled = false;
         sunPathRotation = 0.0F;
         shadowIntervalSize = 2.0F;
         shadowDistanceRenderMul = -1.0F;
         aoLevel = -1.0F;
         useEntityAttrib = false;
         useMidTexCoordAttrib = false;
         useMultiTexCoord3Attrib = false;
         useTangentAttrib = false;
         waterShadowEnabled = false;
         updateChunksErrorRecorded = false;
         updateBlockLightLevel();
         ShaderProfile var2 = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
         String var3 = "";
         if(currentWorld != null) {
            int var4 = currentWorld.provider.getDimensionId();
            if(shaderPackDimensions.contains(Integer.valueOf(var4))) {
               var3 = "world" + var4 + "/";
            }
         }

         if(saveFinalShaders) {
            clearDirectory(new File(shaderpacksdir, "debug"));
         }

         int var13 = 0;
         if(var13 < 33) {
            String var5 = programNames[var13];
            if(var5.equals("")) {
               programsID[var13] = programsRef[var13] = 0;
               programsDrawBufSettings[var13] = null;
               programsColorAtmSettings[var13] = null;
               programsCompositeMipmapSetting[var13] = 0;
            }

            newDrawBufSetting = null;
            newColorAtmSetting = null;
            newCompositeMipmapSetting = 0;
            String var6 = var3 + var5;
            if(var2 != null && var2.isProgramDisabled(var6)) {
               SMCLog.info("Program disabled: " + var6);
               var5 = "<disabled>";
               var6 = var3 + var5;
            }

            String var7 = "/shaders/" + var6;
            int var8 = setupProgram(var13, var7 + ".vsh", var7 + ".fsh");
            SMCLog.info("Program loaded: " + var6);
            programsID[var13] = programsRef[var13] = var8;
            programsDrawBufSettings[var13] = newDrawBufSetting;
            programsColorAtmSettings[var13] = newColorAtmSetting;
            programsCompositeMipmapSetting[var13] = var8 != 0?newCompositeMipmapSetting:0;
            ++var13;
         }

         var13 = GL11.glGetInteger('蠤');
         new HashMap();
         int var17 = 0;
         if(var17 < 33) {
            Arrays.fill(programsToggleColorTextures[var17], false);
            if(var17 == 29) {
               programsDrawBuffers[var17] = null;
            }

            if(programsID[var17] == 0) {
               if(var17 == 30) {
                  programsDrawBuffers[var17] = drawBuffersNone;
               }

               programsDrawBuffers[var17] = drawBuffersColorAtt0;
            }

            String var25 = programsDrawBufSettings[var17];
            IntBuffer var27 = drawBuffersBuffer[var17];
            int var28 = var25.length();
            if(var28 > usedDrawBuffers) {
               usedDrawBuffers = var28;
            }

            if(var28 > var13) {
               var28 = var13;
            }

            programsDrawBuffers[var17] = var27;
            var27.limit(var28);
            int var9 = 0;
            if(var9 < var28) {
               int var10 = 0;
               if(var25.length() > var9) {
                  label955: {
                     int var11 = var25.charAt(var9) - 48;
                     if(var17 != 30) {
                        if(var11 < 0 || var11 > 7) {
                           break label955;
                        }

                        programsToggleColorTextures[var17][var11] = true;
                        var10 = var11 + '賠';
                        if(var11 > usedColorAttachs) {
                           usedColorAttachs = var11;
                        }

                        if(var11 <= usedColorBuffers) {
                           break label955;
                        }

                        usedColorBuffers = var11;
                     }

                     if(var11 >= 0 && var11 <= 1) {
                        var10 = var11 + '賠';
                        if(var11 > usedShadowColorBuffers) {
                           usedShadowColorBuffers = var11;
                        }
                     }
                  }
               }

               var27.put(var9, var10);
               ++var9;
            }

            if(var17 != 30 && var17 != 31 && var17 != 32) {
               programsDrawBuffers[var17] = dfbDrawBuffers;
               usedDrawBuffers = usedColorBuffers;
               Arrays.fill(programsToggleColorTextures[var17], 0, usedColorBuffers, true);
            }

            programsDrawBuffers[var17] = sfbDrawBuffers;
            ++var17;
         }

         usedColorAttachs = usedColorBuffers;
         shadowPassInterval = usedShadowDepthBuffers > 0?1:0;
         shouldSkipDefaultShadow = usedShadowDepthBuffers > 0;
         SMCLog.info("usedColorBuffers: " + usedColorBuffers);
         SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
         SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
         SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
         SMCLog.info("usedColorAttachs: " + usedColorAttachs);
         SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
         dfbDrawBuffers.position(0).limit(usedDrawBuffers);
         dfbColorTextures.position(0).limit(usedColorBuffers * 2);
         var17 = 0;
         if(var17 < usedDrawBuffers) {
            dfbDrawBuffers.put(var17, '賠' + var17);
            ++var17;
         }

         if(usedDrawBuffers > var13) {
            printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + var13);
         }

         sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
         var17 = 0;
         if(var17 < usedShadowColorBuffers) {
            sfbDrawBuffers.put(var17, '賠' + var17);
            ++var17;
         }

         var17 = 0;
         if(var17 < 33) {
            int var26 = var17;
            if(programsID[var17] == 0 && programBackups[var17] != var17) {
               var26 = programBackups[var17];
            }

            if(var26 != var17 && var17 != 30) {
               programsID[var17] = programsID[var26];
               programsDrawBufSettings[var17] = programsDrawBufSettings[var26];
               programsDrawBuffers[var17] = programsDrawBuffers[var26];
            }

            ++var17;
         }

         resize();
         resizeShadow();
         if(noiseTextureEnabled) {
            setupNoiseTexture();
         }

         if(defaultTexture == null) {
            defaultTexture = ShadersTex.createDefaultTexture();
         }

         GlStateManager.pushMatrix();
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         preCelestialRotate();
         postCelestialRotate();
         GlStateManager.popMatrix();
         isShaderPackInitialized = true;
         loadEntityDataMap();
         resetDisplayList();
         checkGLError("Shaders.init");
      }

   }

   public static void resetDisplayList() {
      ++numberResetDisplayList;
      needResetModels = true;
      SMCLog.info("Reset world renderers");
      mc.renderGlobal.loadRenderers();
   }

   public static void resetDisplayListModels() {
      String[] var0 = ShaderOption.p();
      if(needResetModels) {
         needResetModels = false;
         SMCLog.info("Reset model renderers");
         Iterator var1 = mc.getRenderManager().getEntityRenderMap().values().iterator();
         if(var1.hasNext()) {
            Object var2 = var1.next();
            if(var2 instanceof RendererLivingEntity) {
               RendererLivingEntity var3 = (RendererLivingEntity)var2;
               resetDisplayListModel(var3.getMainModel());
            }
         }
      }

   }

   public static void resetDisplayListModel(ModelBase var0) {
      String[] var1 = ShaderOption.p();
      if(var0 != null) {
         Iterator var2 = var0.boxList.iterator();
         if(var2.hasNext()) {
            Object var3 = var2.next();
            if(var3 instanceof ModelRenderer) {
               resetDisplayListModelRenderer((ModelRenderer)var3);
            }
         }
      }

   }

   public static void resetDisplayListModelRenderer(ModelRenderer var0) {
      ShaderOption.p();
      var0.resetDisplayList();
      if(var0.childModels != null) {
         int var2 = 0;
         int var3 = var0.childModels.size();
         if(var2 < var3) {
            resetDisplayListModelRenderer((ModelRenderer)var0.childModels.get(var2));
            ++var2;
         }
      }

   }

   private static int setupProgram(int var0, String var1, String var2) {
      ShaderOption.p();
      checkGLError("pre setupProgram");
      int var4 = ARBShaderObjects.glCreateProgramObjectARB();
      checkGLError("create");
      if(var4 != 0) {
         progUseEntityAttrib = false;
         progUseMidTexCoordAttrib = false;
         progUseTangentAttrib = false;
         int var5 = createVertShader(var1);
         int var6 = createFragShader(var2);
         checkGLError("create");
         if(var5 == 0 && var6 == 0) {
            ARBShaderObjects.glDeleteObjectARB(var4);
            var4 = 0;
         }

         if(var5 != 0) {
            ARBShaderObjects.glAttachObjectARB(var4, var5);
            checkGLError("attach");
         }

         if(var6 != 0) {
            ARBShaderObjects.glAttachObjectARB(var4, var6);
            checkGLError("attach");
         }

         if(progUseEntityAttrib) {
            ARBVertexShader.glBindAttribLocationARB(var4, entityAttrib, "mc_Entity");
            checkGLError("mc_Entity");
         }

         if(progUseMidTexCoordAttrib) {
            ARBVertexShader.glBindAttribLocationARB(var4, midTexCoordAttrib, "mc_midTexCoord");
            checkGLError("mc_midTexCoord");
         }

         if(progUseTangentAttrib) {
            ARBVertexShader.glBindAttribLocationARB(var4, tangentAttrib, "at_tangent");
            checkGLError("at_tangent");
         }

         ARBShaderObjects.glLinkProgramARB(var4);
         if(GL20.glGetProgrami(var4, '讂') != 1) {
            SMCLog.severe("Error linking program: " + var4);
         }

         printLogInfo(var4, var1 + ", " + var2);
         if(var5 != 0) {
            ARBShaderObjects.glDetachObjectARB(var4, var5);
            ARBShaderObjects.glDeleteObjectARB(var5);
         }

         if(var6 != 0) {
            ARBShaderObjects.glDetachObjectARB(var4, var6);
            ARBShaderObjects.glDeleteObjectARB(var6);
         }

         programsID[var0] = var4;
         useProgram(var0);
         ARBShaderObjects.glValidateProgramARB(var4);
         useProgram(0);
         printLogInfo(var4, var1 + ", " + var2);
         int var7 = GL20.glGetProgrami(var4, '讃');
         if(var7 != 1) {
            String var8 = "\"";
            printChatAndLogError("[Shaders] Error: Invalid program " + var8 + programNames[var0] + var8);
            ARBShaderObjects.glDeleteObjectARB(var4);
            var4 = 0;
         }
      }

      return var4;
   }

   private static int createVertShader(String var0) {
      ShaderOption.p();
      int var2 = ARBShaderObjects.glCreateShaderObjectARB('謱');
      if(var2 == 0) {
         return 0;
      } else {
         StringBuilder var3 = new StringBuilder(131072);
         BufferedReader var4 = null;

         try {
            var4 = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(var0)));
         } catch (Exception var10) {
            try {
               var4 = new BufferedReader(new FileReader(new File(var0)));
            } catch (Exception var9) {
               ARBShaderObjects.glDeleteObjectARB(var2);
               return 0;
            }
         }

         ShaderOption[] var5 = getChangedOptions(shaderPackOptions);
         ArrayList var6 = new ArrayList();
         if(var4 != null) {
            try {
               var4 = Zs.a(var4, var0, shaderPack, 0, var6, 0);
               String var7 = var4.readLine();
               var4.close();
               var7 = applyOptions(var7, var5);
               var3.append(var7).append('\n');
               if(var7.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
                  useEntityAttrib = true;
                  progUseEntityAttrib = true;
               }

               if(var7.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
                  useMidTexCoordAttrib = true;
                  progUseMidTexCoordAttrib = true;
               }

               if(var7.matches(".*gl_MultiTexCoord3.*")) {
                  useMultiTexCoord3Attrib = true;
               }

               if(var7.matches("attribute [_a-zA-Z0-9]+ at_tangent.*")) {
                  useTangentAttrib = true;
                  progUseTangentAttrib = true;
               }
            } catch (Exception var8) {
               SMCLog.severe("Couldn\'t read " + var0 + "!");
               var8.printStackTrace();
               ARBShaderObjects.glDeleteObjectARB(var2);
               return 0;
            }
         }

         if(saveFinalShaders) {
            saveShader(var0, var3.toString());
         }

         ARBShaderObjects.glShaderSourceARB(var2, var3);
         ARBShaderObjects.glCompileShaderARB(var2);
         if(GL20.glGetShaderi(var2, '讁') != 1) {
            SMCLog.severe("Error compiling vertex shader: " + var0);
         }

         printShaderLogInfo(var2, var0, var6);
         return var2;
      }
   }

   private static int createFragShader(String var0) {
      ShaderOption.p();
      int var2 = ARBShaderObjects.glCreateShaderObjectARB('謰');
      if(var2 == 0) {
         return 0;
      } else {
         StringBuilder var3 = new StringBuilder(131072);
         BufferedReader var4 = null;

         try {
            var4 = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(var0)));
         } catch (Exception var14) {
            try {
               var4 = new BufferedReader(new FileReader(new File(var0)));
            } catch (Exception var13) {
               ARBShaderObjects.glDeleteObjectARB(var2);
               return 0;
            }
         }

         ShaderOption[] var5 = getChangedOptions(shaderPackOptions);
         ArrayList var6 = new ArrayList();
         if(var4 != null) {
            try {
               var4 = Zs.a(var4, var0, shaderPack, 0, var6, 0);
               String var7 = var4.readLine();
               var4.close();
               var7 = applyOptions(var7, var5);
               var3.append(var7).append('\n');
               if(!var7.matches("#version .*")) {
                  label201: {
                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadow;.*")) {
                        if(usedShadowDepthBuffers >= 1) {
                           break label201;
                        }

                        usedShadowDepthBuffers = 1;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ watershadow;.*")) {
                        waterShadowEnabled = true;
                        if(usedShadowDepthBuffers >= 2) {
                           break label201;
                        }

                        usedShadowDepthBuffers = 2;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadowtex0;.*")) {
                        if(usedShadowDepthBuffers >= 1) {
                           break label201;
                        }

                        usedShadowDepthBuffers = 1;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadowtex1;.*")) {
                        if(usedShadowDepthBuffers >= 2) {
                           break label201;
                        }

                        usedShadowDepthBuffers = 2;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadowcolor;.*")) {
                        if(usedShadowColorBuffers >= 1) {
                           break label201;
                        }

                        usedShadowColorBuffers = 1;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadowcolor0;.*")) {
                        if(usedShadowColorBuffers >= 1) {
                           break label201;
                        }

                        usedShadowColorBuffers = 1;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ shadowcolor1;.*")) {
                        if(usedShadowColorBuffers >= 2) {
                           break label201;
                        }

                        usedShadowColorBuffers = 2;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ depthtex0;.*")) {
                        if(usedDepthBuffers >= 1) {
                           break label201;
                        }

                        usedDepthBuffers = 1;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ depthtex1;.*")) {
                        if(usedDepthBuffers >= 2) {
                           break label201;
                        }

                        usedDepthBuffers = 2;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ depthtex2;.*")) {
                        if(usedDepthBuffers >= 3) {
                           break label201;
                        }

                        usedDepthBuffers = 3;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ gdepth;.*")) {
                        if(gbuffersFormat[1] != 6408) {
                           break label201;
                        }

                        gbuffersFormat[1] = '蠔';
                     }

                     if(usedColorBuffers < 5 && var7.matches("uniform [ _a-zA-Z0-9]+ gaux1;.*")) {
                        usedColorBuffers = 5;
                     }

                     if(usedColorBuffers < 6 && var7.matches("uniform [ _a-zA-Z0-9]+ gaux2;.*")) {
                        usedColorBuffers = 6;
                     }

                     if(usedColorBuffers < 7 && var7.matches("uniform [ _a-zA-Z0-9]+ gaux3;.*")) {
                        usedColorBuffers = 7;
                     }

                     if(usedColorBuffers < 8 && var7.matches("uniform [ _a-zA-Z0-9]+ gaux4;.*")) {
                        usedColorBuffers = 8;
                     }

                     if(usedColorBuffers < 5 && var7.matches("uniform [ _a-zA-Z0-9]+ colortex4;.*")) {
                        usedColorBuffers = 5;
                     }

                     if(usedColorBuffers < 6 && var7.matches("uniform [ _a-zA-Z0-9]+ colortex5;.*")) {
                        usedColorBuffers = 6;
                     }

                     if(usedColorBuffers < 7 && var7.matches("uniform [ _a-zA-Z0-9]+ colortex6;.*")) {
                        usedColorBuffers = 7;
                     }

                     if(usedColorBuffers < 8 && var7.matches("uniform [ _a-zA-Z0-9]+ colortex7;.*")) {
                        usedColorBuffers = 8;
                     }

                     if(var7.matches("uniform [ _a-zA-Z0-9]+ centerDepthSmooth;.*")) {
                        centerDepthSmoothEnabled = true;
                     }

                     if(var7.matches("/\\* SHADOWRES:[0-9]+ \\*/.*")) {
                        String[] var8 = var7.split("(:| )", 4);
                        SMCLog.info("Shadow map resolution: " + var8[2]);
                        spShadowMapWidth = spShadowMapHeight = Integer.parseInt(var8[2]);
                        shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                     }

                     if(var7.matches("[ \t]*const[ \t]*int[ \t]*shadowMapResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var19 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Shadow map resolution: " + var19[1]);
                        spShadowMapWidth = spShadowMapHeight = Integer.parseInt(var19[1]);
                        shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                     }

                     if(var7.matches("/\\* SHADOWFOV:[0-9\\.]+ \\*/.*")) {
                        String[] var20 = var7.split("(:| )", 4);
                        SMCLog.info("Shadow map field of view: " + var20[2]);
                        shadowMapFOV = Float.parseFloat(var20[2]);
                        shadowMapIsOrtho = false;
                     }

                     if(var7.matches("/\\* SHADOWHPL:[0-9\\.]+ \\*/.*")) {
                        String[] var21 = var7.split("(:| )", 4);
                        SMCLog.info("Shadow map half-plane: " + var21[2]);
                        shadowMapHalfPlane = Float.parseFloat(var21[2]);
                        shadowMapIsOrtho = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*shadowDistance[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var22 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Shadow map distance: " + var22[1]);
                        shadowMapHalfPlane = Float.parseFloat(var22[1]);
                        shadowMapIsOrtho = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*shadowDistanceRenderMul[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var23 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Shadow distance render mul: " + var23[1]);
                        shadowDistanceRenderMul = Float.parseFloat(var23[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*shadowIntervalSize[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var24 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Shadow map interval size: " + var24[1]);
                        shadowIntervalSize = Float.parseFloat(var24[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("Generate shadow mipmap");
                        Arrays.fill(shadowMipmapEnabled, true);
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowColorMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("Generate shadow color mipmap");
                        Arrays.fill(shadowColorMipmapEnabled, true);
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("Hardware shadow filtering enabled.");
                        Arrays.fill(shadowHardwareFilteringEnabled, true);
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering0[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowHardwareFiltering0");
                        shadowHardwareFilteringEnabled[0] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering1[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowHardwareFiltering1");
                        shadowHardwareFilteringEnabled[1] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Mipmap|shadowtexMipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowtex0Mipmap");
                        shadowMipmapEnabled[0] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowtex1Mipmap");
                        shadowMipmapEnabled[1] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Mipmap|shadowColor0Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowcolor0Mipmap");
                        shadowColorMipmapEnabled[0] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Mipmap|shadowColor1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowcolor1Mipmap");
                        shadowColorMipmapEnabled[1] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Nearest|shadowtexNearest|shadow0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowtex0Nearest");
                        shadowFilterNearest[0] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Nearest|shadow1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowtex1Nearest");
                        shadowFilterNearest[1] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Nearest|shadowColor0Nearest|shadowColor0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowcolor0Nearest");
                        shadowColorFilterNearest[0] = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Nearest|shadowColor1Nearest|shadowColor1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                        SMCLog.info("shadowcolor1Nearest");
                        shadowColorFilterNearest[1] = true;
                     }

                     if(var7.matches("/\\* WETNESSHL:[0-9\\.]+ \\*/.*")) {
                        String[] var25 = var7.split("(:| )", 4);
                        SMCLog.info("Wetness halflife: " + var25[2]);
                        wetnessHalfLife = Float.parseFloat(var25[2]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*wetnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var26 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Wetness halflife: " + var26[1]);
                        wetnessHalfLife = Float.parseFloat(var26[1]);
                     }

                     if(var7.matches("/\\* DRYNESSHL:[0-9\\.]+ \\*/.*")) {
                        String[] var27 = var7.split("(:| )", 4);
                        SMCLog.info("Dryness halflife: " + var27[2]);
                        drynessHalfLife = Float.parseFloat(var27[2]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*drynessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var28 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Dryness halflife: " + var28[1]);
                        drynessHalfLife = Float.parseFloat(var28[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*eyeBrightnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var29 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Eye brightness halflife: " + var29[1]);
                        eyeBrightnessHalflife = Float.parseFloat(var29[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*centerDepthHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var30 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Center depth halflife: " + var30[1]);
                        centerDepthSmoothHalflife = Float.parseFloat(var30[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*sunPathRotation[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var31 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Sun path rotation: " + var31[1]);
                        sunPathRotation = Float.parseFloat(var31[1]);
                     }

                     if(var7.matches("[ \t]*const[ \t]*float[ \t]*ambientOcclusionLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var32 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("AO Level: " + var32[1]);
                        aoLevel = Config.limit(Float.parseFloat(var32[1]), 0.0F, 1.0F);
                     }

                     if(var7.matches("[ \t]*const[ \t]*int[ \t]*superSamplingLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var33 = var7.split("(=[ \t]*|;)");
                        int var9 = Integer.parseInt(var33[1]);
                        if(var9 > 1) {
                           SMCLog.info("Super sampling level: " + var9 + "x");
                           superSamplingLevel = var9;
                        }

                        superSamplingLevel = 1;
                     }

                     if(var7.matches("[ \t]*const[ \t]*int[ \t]*noiseTextureResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                        String[] var34 = var7.split("(=[ \t]*|;)");
                        SMCLog.info("Noise texture enabled");
                        SMCLog.info("Noise texture resolution: " + var34[1]);
                        noiseTextureResolution = Integer.parseInt(var34[1]);
                        noiseTextureEnabled = true;
                     }

                     if(var7.matches("[ \t]*const[ \t]*int[ \t]*\\w+Format[ \t]*=[ \t]*[RGBA0123456789FUI_SNORM]*[ \t]*;.*")) {
                        Matcher var35 = gbufferFormatPattern.matcher(var7);
                        var35.matches();
                        String var39 = var35.group(1);
                        String var10 = var35.group(2);
                        int var11 = getBufferIndexFromString(var39);
                        int var12 = getTextureFormatFromString(var10);
                        if(var11 >= 0 && var12 != 0) {
                           gbuffersFormat[var11] = var12;
                           SMCLog.info("%s format: %s", new Object[]{var39, var10});
                        }
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*\\w+Clear[ \t]*=[ \t]*false[ \t]*;.*")) {
                        if(!var0.matches(".*composite[0-9]?.fsh")) {
                           break label201;
                        }

                        Matcher var36 = gbufferClearPattern.matcher(var7);
                        var36.matches();
                        String var40 = var36.group(1);
                        int var42 = getBufferIndexFromString(var40);
                        gbuffersClear[var42] = false;
                        SMCLog.info("%s clear disabled", new Object[]{var40});
                     }

                     if(var7.matches("/\\* GAUX4FORMAT:RGBA32F \\*/.*")) {
                        SMCLog.info("gaux4 format : RGB32AF");
                        gbuffersFormat[7] = '蠔';
                     }

                     if(var7.matches("/\\* GAUX4FORMAT:RGB32F \\*/.*")) {
                        SMCLog.info("gaux4 format : RGB32F");
                        gbuffersFormat[7] = '蠕';
                     }

                     if(var7.matches("/\\* GAUX4FORMAT:RGB16 \\*/.*")) {
                        SMCLog.info("gaux4 format : RGB16");
                        gbuffersFormat[7] = '联';
                     }

                     if(var7.matches("[ \t]*const[ \t]*bool[ \t]*\\w+MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*")) {
                        if(!var0.matches(".*composite[0-9]?.fsh") && !var0.matches(".*final.fsh")) {
                           break label201;
                        }

                        Matcher var37 = gbufferMipmapEnabledPattern.matcher(var7);
                        var37.matches();
                        String var41 = var37.group(1);
                        int var43 = getBufferIndexFromString(var41);
                        if(var43 >= 0) {
                           newCompositeMipmapSetting |= 1 << var43;
                           SMCLog.info("%s mipmap enabled", new Object[]{var41});
                        }
                     }

                     if(var7.matches("/\\* DRAWBUFFERS:[0-7N]* \\*/.*")) {
                        String[] var38 = var7.split("(:| )", 4);
                        newDrawBufSetting = var38[2];
                     }
                  }
               }
            } catch (Exception var15) {
               SMCLog.severe("Couldn\'t read " + var0 + "!");
               var15.printStackTrace();
               ARBShaderObjects.glDeleteObjectARB(var2);
               return 0;
            }
         }

         if(saveFinalShaders) {
            saveShader(var0, var3.toString());
         }

         ARBShaderObjects.glShaderSourceARB(var2, var3);
         ARBShaderObjects.glCompileShaderARB(var2);
         if(GL20.glGetShaderi(var2, '讁') != 1) {
            SMCLog.severe("Error compiling fragment shader: " + var0);
         }

         printShaderLogInfo(var2, var0, var6);
         return var2;
      }
   }

   private static void saveShader(String var0, String var1) {
      try {
         File var2 = new File(shaderpacksdir, "debug/" + var0);
         var2.getParentFile().mkdirs();
         Config.writeFile(var2, var1);
      } catch (IOException var3) {
         Config.warn("Error saving: " + var0);
         var3.printStackTrace();
      }

   }

   private static void clearDirectory(File var0) {
      String[] var1 = ShaderOption.p();
      if(var0.exists() && var0.isDirectory()) {
         File[] var2 = var0.listFiles();
         if(var2 != null) {
            int var4 = var2.length;
            int var5 = 0;
            if(var5 < var4) {
               File var6 = var2[var5];
               if(var6.isDirectory()) {
                  clearDirectory(var6);
               }

               var6.delete();
               ++var5;
            }
         }
      }

   }

   private static boolean printLogInfo(int var0, String var1) {
      IntBuffer var3 = BufferUtils.createIntBuffer(1);
      ShaderOption.p();
      ARBShaderObjects.glGetObjectParameterARB(var0, '讄', var3);
      int var4 = var3.get();
      if(var4 > 1) {
         ByteBuffer var5 = BufferUtils.createByteBuffer(var4);
         var3.flip();
         ARBShaderObjects.glGetInfoLogARB(var0, var3, var5);
         byte[] var6 = new byte[var4];
         var5.get(var6);
         if(var6[var4 - 1] == 0) {
            var6[var4 - 1] = 10;
         }

         String var7 = new String(var6);
         SMCLog.info("Info log: " + var1 + "\n" + var7);
         return false;
      } else {
         return true;
      }
   }

   private static boolean printShaderLogInfo(int var0, String var1, List var2) {
      ShaderOption.p();
      IntBuffer var4 = BufferUtils.createIntBuffer(1);
      int var5 = GL20.glGetShaderi(var0, '讄');
      if(var5 <= 1) {
         return true;
      } else {
         int var6 = 0;
         if(var6 < var2.size()) {
            String var7 = (String)var2.get(var6);
            SMCLog.info("File: " + (var6 + 1) + " = " + var7);
            ++var6;
         }

         String var9 = GL20.glGetShaderInfoLog(var0, var5);
         SMCLog.info("Shader info log: " + var1 + "\n" + var9);
         return false;
      }
   }

   public static void setDrawBuffers(IntBuffer var0) {
      String[] var1 = ShaderOption.p();
      if(var0 == null) {
         var0 = drawBuffersNone;
      }

      if(activeDrawBuffers != var0) {
         activeDrawBuffers = var0;
         GL20.glDrawBuffers(var0);
      }

   }

   public static void useProgram(int var0) {
      ShaderOption.p();
      checkGLError("pre-useProgram");
      if(isShadowPass) {
         var0 = 30;
         if(programsID[30] == 0) {
            normalMapEnabled = false;
            return;
         }
      }

      if(activeProgram != var0) {
         activeProgram = var0;
         ARBShaderObjects.glUseProgramObjectARB(programsID[var0]);
         if(programsID[var0] == 0) {
            normalMapEnabled = false;
         }

         if(checkGLError("useProgram ", programNames[var0]) != 0) {
            programsID[var0] = 0;
         }

         IntBuffer var2 = programsDrawBuffers[var0];
         if(isRenderingDfb) {
            setDrawBuffers(var2);
            checkGLError(programNames[var0], " draw buffers = ", programsDrawBufSettings[var0]);
         }

         activeCompositeMipmapSetting = programsCompositeMipmapSetting[var0];
         uniformEntityColor.setProgram(programsID[activeProgram]);
         uniformEntityId.setProgram(programsID[activeProgram]);
         uniformBlockEntityId.setProgram(programsID[activeProgram]);
         switch(var0) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 16:
         case 18:
         case 19:
         case 20:
            normalMapEnabled = true;
            setProgramUniform1i("texture", 0);
            setProgramUniform1i("lightmap", 1);
            setProgramUniform1i("normals", 2);
            setProgramUniform1i("specular", 3);
            setProgramUniform1i("shadow", waterShadowEnabled?5:4);
            setProgramUniform1i("watershadow", 4);
            setProgramUniform1i("shadowtex0", 4);
            setProgramUniform1i("shadowtex1", 5);
            setProgramUniform1i("depthtex0", 6);
            if(customTexturesGbuffers != null) {
               setProgramUniform1i("gaux1", 7);
               setProgramUniform1i("gaux2", 8);
               setProgramUniform1i("gaux3", 9);
               setProgramUniform1i("gaux4", 10);
            }

            setProgramUniform1i("depthtex1", 12);
            setProgramUniform1i("shadowcolor", 13);
            setProgramUniform1i("shadowcolor0", 13);
            setProgramUniform1i("shadowcolor1", 14);
            setProgramUniform1i("noisetex", 15);
         case 14:
         case 15:
         case 17:
         default:
            normalMapEnabled = false;
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
            normalMapEnabled = false;
            setProgramUniform1i("gcolor", 0);
            setProgramUniform1i("gdepth", 1);
            setProgramUniform1i("gnormal", 2);
            setProgramUniform1i("composite", 3);
            setProgramUniform1i("gaux1", 7);
            setProgramUniform1i("gaux2", 8);
            setProgramUniform1i("gaux3", 9);
            setProgramUniform1i("gaux4", 10);
            setProgramUniform1i("colortex0", 0);
            setProgramUniform1i("colortex1", 1);
            setProgramUniform1i("colortex2", 2);
            setProgramUniform1i("colortex3", 3);
            setProgramUniform1i("colortex4", 7);
            setProgramUniform1i("colortex5", 8);
            setProgramUniform1i("colortex6", 9);
            setProgramUniform1i("colortex7", 10);
            setProgramUniform1i("shadow", waterShadowEnabled?5:4);
            setProgramUniform1i("watershadow", 4);
            setProgramUniform1i("shadowtex0", 4);
            setProgramUniform1i("shadowtex1", 5);
            setProgramUniform1i("gdepthtex", 6);
            setProgramUniform1i("depthtex0", 6);
            setProgramUniform1i("depthtex1", 11);
            setProgramUniform1i("depthtex2", 12);
            setProgramUniform1i("shadowcolor", 13);
            setProgramUniform1i("shadowcolor0", 13);
            setProgramUniform1i("shadowcolor1", 14);
            setProgramUniform1i("noisetex", 15);
         case 30:
         case 31:
         case 32:
            setProgramUniform1i("tex", 0);
            setProgramUniform1i("texture", 0);
            setProgramUniform1i("lightmap", 1);
            setProgramUniform1i("normals", 2);
            setProgramUniform1i("specular", 3);
            setProgramUniform1i("shadow", waterShadowEnabled?5:4);
            setProgramUniform1i("watershadow", 4);
            setProgramUniform1i("shadowtex0", 4);
            setProgramUniform1i("shadowtex1", 5);
            if(customTexturesGbuffers != null) {
               setProgramUniform1i("gaux1", 7);
               setProgramUniform1i("gaux2", 8);
               setProgramUniform1i("gaux3", 9);
               setProgramUniform1i("gaux4", 10);
            }

            setProgramUniform1i("shadowcolor", 13);
            setProgramUniform1i("shadowcolor0", 13);
            setProgramUniform1i("shadowcolor1", 14);
            setProgramUniform1i("noisetex", 15);
            ItemStack var3 = mc.player != null?mc.player.getHeldItem():null;
            Item var4 = var3 != null?var3.getItem():null;
            int var5 = -1;
            Block var6 = null;
            if(var4 != null) {
               var5 = Item.itemRegistry.getIDForObject(var4);
               var6 = (Block)Block.blockRegistry.getObjectById(var5);
            }

            int var7 = var6 != null?var6.getLightValue():0;
            setProgramUniform1i("heldItemId", var5);
            setProgramUniform1i("heldBlockLightValue", var7);
            setProgramUniform1i("fogMode", fogEnabled?fogMode:0);
            setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
            setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
            setProgramUniform1i("worldTime", (int)(worldTime % 24000L));
            setProgramUniform1i("worldDay", (int)(worldTime / 24000L));
            setProgramUniform1i("moonPhase", moonPhase);
            setProgramUniform1i("frameCounter", frameCounter);
            setProgramUniform1f("frameTime", frameTime);
            setProgramUniform1f("frameTimeCounter", frameTimeCounter);
            setProgramUniform1f("sunAngle", sunAngle);
            setProgramUniform1f("shadowAngle", shadowAngle);
            setProgramUniform1f("rainStrength", rainStrength);
            setProgramUniform1f("aspectRatio", (float)renderWidth / (float)renderHeight);
            setProgramUniform1f("viewWidth", (float)renderWidth);
            setProgramUniform1f("viewHeight", (float)renderHeight);
            setProgramUniform1f("near", 0.05F);
            setProgramUniform1f("far", (float)(mc.gameSettings.renderDistanceChunks * 16));
            setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
            setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
            setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
            setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
            setProgramUniform3f("previousCameraPosition", (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
            setProgramUniform3f("cameraPosition", (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
            setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
            setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
            setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
            setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
            setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
            setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
            if(usedShadowDepthBuffers > 0) {
               setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
               setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
               setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
               setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
            }

            setProgramUniform1f("wetness", wetness);
            setProgramUniform1f("eyeAltitude", eyePosY);
            setProgramUniform2i("eyeBrightness", eyeBrightness & '\uffff', eyeBrightness >> 16);
            setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
            setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
            setProgramUniform1i("terrainIconSize", terrainIconSize);
            setProgramUniform1i("isEyeInWater", isEyeInWater);
            setProgramUniform1f("nightVision", nightVision);
            setProgramUniform1f("blindness", blindness);
            setProgramUniform1f("screenBrightness", mc.gameSettings.gammaSetting);
            setProgramUniform1i("hideGUI", mc.gameSettings.hideGUI?1:0);
            setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
            setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
            checkGLError("useProgram ", programNames[var0]);
         }
      }

   }

   public static void setProgramUniform1i(String var0, int var1) {
      ShaderOption.p();
      int var3 = programsID[activeProgram];
      if(var3 != 0) {
         int var4 = ARBShaderObjects.glGetUniformLocationARB(var3, var0);
         ARBShaderObjects.glUniform1iARB(var4, var1);
         checkGLError(programNames[activeProgram], var0);
      }

   }

   public static void setProgramUniform2i(String var0, int var1, int var2) {
      ShaderOption.p();
      int var4 = programsID[activeProgram];
      if(var4 != 0) {
         int var5 = ARBShaderObjects.glGetUniformLocationARB(var4, var0);
         ARBShaderObjects.glUniform2iARB(var5, var1, var2);
         checkGLError(programNames[activeProgram], var0);
      }

   }

   public static void setProgramUniform1f(String var0, float var1) {
      ShaderOption.p();
      int var3 = programsID[activeProgram];
      if(var3 != 0) {
         int var4 = ARBShaderObjects.glGetUniformLocationARB(var3, var0);
         ARBShaderObjects.glUniform1fARB(var4, var1);
         checkGLError(programNames[activeProgram], var0);
      }

   }

   public static void setProgramUniform3f(String var0, float var1, float var2, float var3) {
      ShaderOption.p();
      int var5 = programsID[activeProgram];
      if(var5 != 0) {
         int var6 = ARBShaderObjects.glGetUniformLocationARB(var5, var0);
         ARBShaderObjects.glUniform3fARB(var6, var1, var2, var3);
         checkGLError(programNames[activeProgram], var0);
      }

   }

   public static void setProgramUniformMatrix4ARB(String var0, boolean var1, FloatBuffer var2) {
      int var3 = programsID[activeProgram];
      int var4 = ARBShaderObjects.glGetUniformLocationARB(var3, var0);
      ARBShaderObjects.glUniformMatrix4ARB(var4, var1, var2);
      checkGLError(programNames[activeProgram], var0);
   }

   private static int getBufferIndexFromString(String var0) {
      String[] var1 = ShaderOption.p();
      return !var0.equals("colortex0") && !var0.equals("gcolor")?(!var0.equals("colortex1") && !var0.equals("gdepth")?(!var0.equals("colortex2") && !var0.equals("gnormal")?(!var0.equals("colortex3") && !var0.equals("composite")?(!var0.equals("colortex4") && !var0.equals("gaux1")?(!var0.equals("colortex5") && !var0.equals("gaux2")?(!var0.equals("colortex6") && !var0.equals("gaux3")?(!var0.equals("colortex7") && !var0.equals("gaux4")?-1:7):6):5):4):3):2):1):0;
   }

   private static int getTextureFormatFromString(String var0) {
      ShaderOption.p();
      var0 = var0.trim();
      int var2 = 0;
      if(var2 < formatNames.length) {
         String var3 = formatNames[var2];
         if(var0.equals(var3)) {
            return formatIds[var2];
         }

         ++var2;
      }

      return 0;
   }

   private static void setupNoiseTexture() {
      String[] var0 = ShaderOption.p();
      if(noiseTexture == null) {
         noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
      }

   }

   private static void loadEntityDataMap() {
      ShaderOption.p();
      mapBlockToEntityData = new IdentityHashMap(300);
      if(mapBlockToEntityData.isEmpty()) {
         Iterator var1 = Block.blockRegistry.getKeys().iterator();
         if(var1.hasNext()) {
            ResourceLocation var2 = (ResourceLocation)var1.next();
            Block var3 = (Block)Block.blockRegistry.getObject(var2);
            int var4 = Block.blockRegistry.getIDForObject(var3);
            mapBlockToEntityData.put(var3, Integer.valueOf(var4));
         }
      }

      BufferedReader var11 = null;

      try {
         var11 = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
      } catch (Exception var10) {
         ;
      }

      if(var11 != null) {
         try {
            String var12;
            if((var12 = var11.readLine()) != null) {
               Matcher var13 = patternLoadEntityDataMap.matcher(var12);
               if(var13.matches()) {
                  String var14 = var13.group(1);
                  String var5 = var13.group(2);
                  int var6 = Integer.parseInt(var5);
                  Block var7 = Block.getBlockFromName(var14);
                  if(var7 != null) {
                     mapBlockToEntityData.put(var7, Integer.valueOf(var6));
                  }

                  SMCLog.warning("Unknown block name %s", new Object[]{var14});
               }

               SMCLog.warning("unmatched %s\n", new Object[]{var12});
            }
         } catch (Exception var9) {
            SMCLog.warning("Error parsing mc_Entity_x.txt");
         }
      }

      if(var11 != null) {
         BufferedReader var10000 = var11;

         try {
            var10000.close();
         } catch (Exception var8) {
            ;
         }
      }

   }

   private static IntBuffer fillIntBufferZero(IntBuffer var0) {
      ShaderOption.p();
      int var2 = var0.limit();
      int var3 = var0.position();
      if(var3 < var2) {
         var0.put(var3, 0);
         ++var3;
      }

      return var0;
   }

   public static void uninit() {
      String[] var0 = ShaderOption.p();
      if(isShaderPackInitialized) {
         checkGLError("Shaders.uninit pre");
         int var1 = 0;
         if(var1 < 33) {
            if(programsRef[var1] != 0) {
               ARBShaderObjects.glDeleteObjectARB(programsRef[var1]);
               checkGLError("del programRef");
            }

            programsRef[var1] = 0;
            programsID[var1] = 0;
            programsDrawBufSettings[var1] = null;
            programsDrawBuffers[var1] = null;
            programsCompositeMipmapSetting[var1] = 0;
            ++var1;
         }

         if(dfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
            dfb = 0;
            checkGLError("del dfb");
         }

         if(sfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
            sfb = 0;
            checkGLError("del sfb");
         }

         if(dfbDepthTextures != null) {
            GlStateManager.deleteTextures(dfbDepthTextures);
            fillIntBufferZero(dfbDepthTextures);
            checkGLError("del dfbDepthTextures");
         }

         if(dfbColorTextures != null) {
            GlStateManager.deleteTextures(dfbColorTextures);
            fillIntBufferZero(dfbColorTextures);
            checkGLError("del dfbTextures");
         }

         if(sfbDepthTextures != null) {
            GlStateManager.deleteTextures(sfbDepthTextures);
            fillIntBufferZero(sfbDepthTextures);
            checkGLError("del shadow depth");
         }

         if(sfbColorTextures != null) {
            GlStateManager.deleteTextures(sfbColorTextures);
            fillIntBufferZero(sfbColorTextures);
            checkGLError("del shadow color");
         }

         if(dfbDrawBuffers != null) {
            fillIntBufferZero(dfbDrawBuffers);
         }

         if(noiseTexture != null) {
            noiseTexture.destroy();
            noiseTexture = null;
         }

         SMCLog.info("Uninit");
         shadowPassInterval = 0;
         shouldSkipDefaultShadow = false;
         isShaderPackInitialized = false;
         checkGLError("Shaders.uninit");
      }

   }

   public static void scheduleResize() {
      renderDisplayHeight = 0;
   }

   public static void scheduleResizeShadow() {
      needResizeShadow = true;
   }

   private static void resize() {
      renderDisplayWidth = mc.displayWidth;
      renderDisplayHeight = mc.displayHeight;
      renderWidth = Math.round((float)renderDisplayWidth * configRenderResMul);
      renderHeight = Math.round((float)renderDisplayHeight * configRenderResMul);
      setupFrameBuffer();
   }

   private static void resizeShadow() {
      needResizeShadow = false;
      shadowMapWidth = Math.round((float)spShadowMapWidth * configShadowResMul);
      shadowMapHeight = Math.round((float)spShadowMapHeight * configShadowResMul);
      setupShadowFrameBuffer();
   }

   private static void setupFrameBuffer() {
      String[] var0 = ShaderOption.p();
      if(dfb != 0) {
         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
         GlStateManager.deleteTextures(dfbDepthTextures);
         GlStateManager.deleteTextures(dfbColorTextures);
      }

      dfb = EXTFramebufferObject.glGenFramebuffersEXT();
      GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
      GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
      dfbDepthTextures.position(0);
      dfbColorTextures.position(0);
      dfbColorTextures.get(dfbColorTexturesA).position(0);
      EXTFramebufferObject.glBindFramebufferEXT('赀', dfb);
      GL20.glDrawBuffers(0);
      GL11.glReadBuffer(0);
      int var1 = 0;
      if(var1 < usedDepthBuffers) {
         GlStateManager.bindTexture(dfbDepthTextures.get(var1));
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
         GL11.glTexParameteri(3553, '衋', 6409);
         GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (FloatBuffer)null);
         ++var1;
      }

      EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '贀', 3553, dfbDepthTextures.get(0), 0);
      GL20.glDrawBuffers(dfbDrawBuffers);
      GL11.glReadBuffer(0);
      checkGLError("FT d");
      var1 = 0;
      if(var1 < usedColorBuffers) {
         GlStateManager.bindTexture(dfbColorTexturesA[var1]);
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexImage2D(3553, 0, gbuffersFormat[var1], renderWidth, renderHeight, 0, '胡', '荧', (ByteBuffer)null);
         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var1, 3553, dfbColorTexturesA[var1], 0);
         checkGLError("FT c");
         ++var1;
      }

      var1 = 0;
      if(var1 < usedColorBuffers) {
         GlStateManager.bindTexture(dfbColorTexturesA[8 + var1]);
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexImage2D(3553, 0, gbuffersFormat[var1], renderWidth, renderHeight, 0, '胡', '荧', (ByteBuffer)null);
         checkGLError("FT ca");
         ++var1;
      }

      var1 = EXTFramebufferObject.glCheckFramebufferStatusEXT('赀');
      if(var1 == '賚') {
         printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
         int var2 = 0;
         if(var2 < usedColorBuffers) {
            GlStateManager.bindTexture(dfbColorTextures.get(var2));
            GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, '胡', '荧', (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var2, 3553, dfbColorTextures.get(var2), 0);
            checkGLError("FT c");
            ++var2;
         }

         var1 = EXTFramebufferObject.glCheckFramebufferStatusEXT('赀');
         if(var1 == '賕') {
            SMCLog.info("complete");
         }
      }

      GlStateManager.bindTexture(0);
      if(var1 != '賕') {
         printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + var1 + ")");
      }

      SMCLog.info("Framebuffer created.");
   }

   private static void setupShadowFrameBuffer() {
      String[] var0 = ShaderOption.p();
      if(usedShadowDepthBuffers != 0) {
         if(sfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
            GlStateManager.deleteTextures(sfbDepthTextures);
            GlStateManager.deleteTextures(sfbColorTextures);
         }

         sfb = EXTFramebufferObject.glGenFramebuffersEXT();
         EXTFramebufferObject.glBindFramebufferEXT('赀', sfb);
         GL11.glDrawBuffer(0);
         GL11.glReadBuffer(0);
         GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
         GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
         sfbDepthTextures.position(0);
         sfbColorTextures.position(0);
         int var1 = 0;
         if(var1 < usedShadowDepthBuffers) {
            GlStateManager.bindTexture(sfbDepthTextures.get(var1));
            GL11.glTexParameterf(3553, 10242, 10496.0F);
            GL11.glTexParameterf(3553, 10243, 10496.0F);
            int var2 = shadowFilterNearest[var1]?9728:9729;
            GL11.glTexParameteri(3553, 10241, var2);
            GL11.glTexParameteri(3553, 10240, var2);
            if(shadowHardwareFilteringEnabled[var1]) {
               GL11.glTexParameteri(3553, '行', '衎');
            }

            GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (FloatBuffer)null);
            ++var1;
         }

         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '贀', 3553, sfbDepthTextures.get(0), 0);
         checkGLError("FT sd");
         var1 = 0;
         if(var1 < usedShadowColorBuffers) {
            GlStateManager.bindTexture(sfbColorTextures.get(var1));
            GL11.glTexParameterf(3553, 10242, 10496.0F);
            GL11.glTexParameterf(3553, 10243, 10496.0F);
            int var7 = shadowColorFilterNearest[var1]?9728:9729;
            GL11.glTexParameteri(3553, 10241, var7);
            GL11.glTexParameteri(3553, 10240, var7);
            GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, '胡', '荧', (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var1, 3553, sfbColorTextures.get(var1), 0);
            checkGLError("FT sc");
            ++var1;
         }

         GlStateManager.bindTexture(0);
         if(usedShadowColorBuffers > 0) {
            GL20.glDrawBuffers(sfbDrawBuffers);
         }

         var1 = EXTFramebufferObject.glCheckFramebufferStatusEXT('赀');
         if(var1 != '賕') {
            printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + var1 + ")");
         }

         SMCLog.info("Shadow framebuffer created.");
      }

   }

   public static void beginRender(Minecraft var0, float var1, long var2) {
      checkGLError("pre beginRender");
      checkWorldChanged(mc.world);
      mc = var0;
      ShaderOption.p();
      mc.mcProfiler.startSection("init");
      entityRenderer = mc.entityRenderer;
      if(!isShaderPackInitialized) {
         try {
            init();
         } catch (IllegalStateException var10) {
            if(Config.c(var10.getMessage()).equals("Function is not supported")) {
               printChatAndLogError("[Shaders] Error: " + var10.getMessage());
               var10.printStackTrace();
               setShaderPack(packNameNone);
               return;
            }
         }
      }

      if(mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight) {
         resize();
      }

      if(needResizeShadow) {
         resizeShadow();
      }

      worldTime = mc.world.getWorldTime();
      diffWorldTime = (worldTime - lastWorldTime) % 24000L;
      if(diffWorldTime < 0L) {
         diffWorldTime += 24000L;
      }

      lastWorldTime = worldTime;
      moonPhase = mc.world.getMoonPhase();
      ++frameCounter;
      if(frameCounter >= 720720) {
         frameCounter = 0;
      }

      systemTime = System.currentTimeMillis();
      if(lastSystemTime == 0L) {
         lastSystemTime = systemTime;
      }

      diffSystemTime = systemTime - lastSystemTime;
      lastSystemTime = systemTime;
      frameTime = (float)diffSystemTime / 1000.0F;
      frameTimeCounter += frameTime;
      frameTimeCounter %= 3600.0F;
      rainStrength = var0.world.getRainStrength(var1);
      float var5 = (float)diffSystemTime * 0.01F;
      float var6 = (float)Math.exp(Math.log(0.5D) * (double)var5 / (double)(wetness < rainStrength?drynessHalfLife:wetnessHalfLife));
      wetness = wetness * var6 + rainStrength * (1.0F - var6);
      Entity var7 = mc.getRenderViewEntity();
      isSleeping = var7 instanceof EntityLivingBase && ((EntityLivingBase)var7).isPlayerSleeping();
      eyePosY = (float)var7.posY * var1 + (float)var7.lastTickPosY * (1.0F - var1);
      eyeBrightness = var7.getBrightnessForRender(var1);
      var6 = (float)diffSystemTime * 0.01F;
      float var8 = (float)Math.exp(Math.log(0.5D) * (double)var6 / (double)eyeBrightnessHalflife);
      eyeBrightnessFadeX = eyeBrightnessFadeX * var8 + (float)(eyeBrightness & '\uffff') * (1.0F - var8);
      eyeBrightnessFadeY = eyeBrightnessFadeY * var8 + (float)(eyeBrightness >> 16) * (1.0F - var8);
      isEyeInWater = mc.gameSettings.thirdPersonView == 0 && !isSleeping && mc.player.isInsideOfMaterial(Material.water)?1:0;
      if(mc.player != null) {
         nightVision = 0.0F;
         if(mc.player.isPotionActive(Potion.nightVision)) {
            nightVision = Config.getMinecraft().entityRenderer.getNightVisionBrightness(mc.player, var1);
         }

         blindness = 0.0F;
         if(mc.player.isPotionActive(Potion.blindness)) {
            int var9 = mc.player.getActivePotionEffect(Potion.blindness).getDuration();
            blindness = Config.limit((float)var9 / 20.0F, 0.0F, 1.0F);
         }
      }

      Vec3 var20 = mc.world.getSkyColor(var7, var1);
      var20 = CustomColors.getWorldSkyColor(var20, currentWorld, var7, var1);
      skyColorR = (float)var20.xCoord;
      skyColorG = (float)var20.yCoord;
      skyColorB = (float)var20.zCoord;
      isRenderingWorld = true;
      isCompositeRendered = false;
      isHandRenderedMain = false;
      if(usedShadowDepthBuffers >= 1) {
         GlStateManager.setActiveTexture('蓄');
         GlStateManager.bindTexture(sfbDepthTextures.get(0));
         if(usedShadowDepthBuffers >= 2) {
            GlStateManager.setActiveTexture('蓅');
            GlStateManager.bindTexture(sfbDepthTextures.get(1));
         }
      }

      GlStateManager.setActiveTexture('蓀');
      int var12 = 0;
      if(var12 < usedColorBuffers) {
         GlStateManager.bindTexture(dfbColorTexturesA[var12]);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10241, 9729);
         GlStateManager.bindTexture(dfbColorTexturesA[8 + var12]);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10241, 9729);
         ++var12;
      }

      GlStateManager.bindTexture(0);
      var12 = 0;
      if(var12 < 4 && 4 + var12 < usedColorBuffers) {
         GlStateManager.setActiveTexture('蓇' + var12);
         GlStateManager.bindTexture(dfbColorTextures.get(4 + var12));
         ++var12;
      }

      GlStateManager.setActiveTexture('蓆');
      GlStateManager.bindTexture(dfbDepthTextures.get(0));
      if(usedDepthBuffers >= 2) {
         GlStateManager.setActiveTexture('蓋');
         GlStateManager.bindTexture(dfbDepthTextures.get(1));
         if(usedDepthBuffers >= 3) {
            GlStateManager.setActiveTexture('蓌');
            GlStateManager.bindTexture(dfbDepthTextures.get(2));
         }
      }

      var12 = 0;
      if(var12 < usedShadowColorBuffers) {
         GlStateManager.setActiveTexture('蓍' + var12);
         GlStateManager.bindTexture(sfbColorTextures.get(var12));
         ++var12;
      }

      if(noiseTextureEnabled) {
         GlStateManager.setActiveTexture('蓀' + noiseTexture.textureUnit);
         GlStateManager.bindTexture(noiseTexture.getID());
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10241, 9729);
      }

      bindCustomTextures(customTexturesGbuffers);
      GlStateManager.setActiveTexture('蓀');
      previousCameraPositionX = cameraPositionX;
      previousCameraPositionY = cameraPositionY;
      previousCameraPositionZ = cameraPositionZ;
      previousProjection.position(0);
      projection.position(0);
      previousProjection.put(projection);
      previousProjection.position(0);
      projection.position(0);
      previousModelView.position(0);
      modelView.position(0);
      previousModelView.put(modelView);
      previousModelView.position(0);
      modelView.position(0);
      checkGLError("beginRender");
      ShadersRender.renderShadowMap(entityRenderer, 0, var1, var2);
      mc.mcProfiler.endSection();
      EXTFramebufferObject.glBindFramebufferEXT('赀', dfb);
      var12 = 0;
      if(var12 < usedColorBuffers) {
         colorTexturesToggle[var12] = 0;
         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var12, 3553, dfbColorTexturesA[var12], 0);
         ++var12;
      }

      checkGLError("end beginRender");
   }

   private static void checkWorldChanged(World var0) {
      String[] var1 = ShaderOption.p();
      if(currentWorld != var0) {
         World var2 = currentWorld;
         currentWorld = var0;
         if(var2 != null && var0 != null) {
            int var3 = var2.provider.getDimensionId();
            int var4 = var0.provider.getDimensionId();
            boolean var5 = shaderPackDimensions.contains(Integer.valueOf(var3));
            boolean var6 = shaderPackDimensions.contains(Integer.valueOf(var4));
            if(var5 || var6) {
               uninit();
            }
         }
      }

   }

   public static void beginRenderPass(int var0, float var1, long var2) {
      String[] var4 = ShaderOption.p();
      if(!isShadowPass) {
         EXTFramebufferObject.glBindFramebufferEXT('赀', dfb);
         GL11.glViewport(0, 0, renderWidth, renderHeight);
         activeDrawBuffers = null;
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
         useProgram(2);
         checkGLError("end beginRenderPass");
      }

   }

   public static void setViewport(int var0, int var1, int var2, int var3) {
      ShaderOption.p();
      GlStateManager.colorMask(true, true, true, true);
      if(isShadowPass) {
         GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
      }

      GL11.glViewport(0, 0, renderWidth, renderHeight);
      EXTFramebufferObject.glBindFramebufferEXT('赀', dfb);
      isRenderingDfb = true;
      GlStateManager.enableCull();
      GlStateManager.enableDepth();
      setDrawBuffers(drawBuffersNone);
      useProgram(2);
      checkGLError("beginRenderPass");
   }

   public static int setFogMode(int var0) {
      fogMode = var0;
      return var0;
   }

   public static void setFogColor(float var0, float var1, float var2) {
      fogColorR = var0;
      fogColorG = var1;
      fogColorB = var2;
   }

   public static void setClearColor(float var0, float var1, float var2, float var3) {
      GlStateManager.clearColor(var0, var1, var2, var3);
      clearColorR = var0;
      clearColorG = var1;
      clearColorB = var2;
   }

   public static void clearRenderBuffer() {
      String[] var0 = ShaderOption.p();
      if(isShadowPass) {
         checkGLError("shadow clear pre");
         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '贀', 3553, sfbDepthTextures.get(0), 0);
         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GL20.glDrawBuffers(programsDrawBuffers[30]);
         checkFramebufferStatus("shadow clear");
         GL11.glClear(16640);
         checkGLError("shadow clear");
      }

      checkGLError("clear pre");
      if(gbuffersClear[0]) {
         GL20.glDrawBuffers('賠');
         GL11.glClear(16384);
      }

      if(gbuffersClear[1]) {
         GL20.glDrawBuffers('賡');
         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glClear(16384);
      }

      int var1 = 2;
      if(var1 < usedColorBuffers) {
         if(gbuffersClear[var1]) {
            GL20.glDrawBuffers('賠' + var1);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
         }

         ++var1;
      }

      setDrawBuffers(dfbDrawBuffers);
      checkFramebufferStatus("clear");
      checkGLError("clear");
   }

   public static void setCamera(float var0) {
      Entity var1 = mc.getRenderViewEntity();
      double var2 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var0;
      double var4 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var0;
      double var6 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var0;
      cameraPositionX = var2;
      cameraPositionY = var4;
      cameraPositionZ = var6;
      GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
      SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
      projection.position(0);
      projectionInverse.position(0);
      GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
      SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
      modelView.position(0);
      modelViewInverse.position(0);
      checkGLError("setCamera");
   }

   public static void setCameraShadow(float var0) {
      Entity var2 = mc.getRenderViewEntity();
      double var3 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var0;
      double var5 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var0;
      double var7 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var0;
      cameraPositionX = var3;
      cameraPositionY = var5;
      cameraPositionZ = var7;
      GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
      SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
      projection.position(0);
      projectionInverse.position(0);
      ShaderOption.p();
      GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
      SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
      modelView.position(0);
      modelViewInverse.position(0);
      GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      if(shadowMapIsOrtho) {
         GL11.glOrtho((double)(-shadowMapHalfPlane), (double)shadowMapHalfPlane, (double)(-shadowMapHalfPlane), (double)shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
      }

      GLU.gluPerspective(shadowMapFOV, (float)shadowMapWidth / (float)shadowMapHeight, 0.05F, 256.0F);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -100.0F);
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      celestialAngle = mc.world.getCelestialAngle(var0);
      sunAngle = celestialAngle < 0.75F?celestialAngle + 0.25F:celestialAngle - 0.75F;
      float var9 = celestialAngle * -360.0F;
      float var10 = shadowAngleInterval > 0.0F?var9 % shadowAngleInterval - shadowAngleInterval * 0.5F:0.0F;
      if((double)sunAngle <= 0.5D) {
         GL11.glRotatef(var9 - var10, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
         shadowAngle = sunAngle;
      }

      GL11.glRotatef(var9 + 180.0F - var10, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
      shadowAngle = sunAngle - 0.5F;
      if(shadowMapIsOrtho) {
         float var11 = shadowIntervalSize;
         float var12 = var11 / 2.0F;
         GL11.glTranslatef((float)var3 % var11 - var12, (float)var5 % var11 - var12, (float)var7 % var11 - var12);
      }

      float var18 = sunAngle * 6.2831855F;
      float var19 = MathHelper.cos((double)var18);
      float var13 = MathHelper.sin((double)var18);
      float var14 = sunPathRotation * 6.2831855F;
      float var15 = var19;
      float var16 = var13 * MathHelper.cos((double)var14);
      float var17 = var13 * MathHelper.sin((double)var14);
      if((double)sunAngle > 0.5D) {
         var15 = -var19;
         var16 = -var16;
         var17 = -var17;
      }

      shadowLightPositionVector[0] = var15;
      shadowLightPositionVector[1] = var16;
      shadowLightPositionVector[2] = var17;
      shadowLightPositionVector[3] = 0.0F;
      GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
      SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
      shadowProjection.position(0);
      shadowProjectionInverse.position(0);
      GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
      SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
      shadowModelView.position(0);
      shadowModelViewInverse.position(0);
      setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
      setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
      setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
      setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
      setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
      setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
      setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
      setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
      setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
      setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
      mc.gameSettings.thirdPersonView = 1;
      checkGLError("setCamera");
   }

   public static void preCelestialRotate() {
      GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
      checkGLError("preCelestialRotate");
   }

   public static void postCelestialRotate() {
      FloatBuffer var1 = tempMatrixDirectBuffer;
      ShaderOption.p();
      var1.clear();
      GL11.glGetFloat(2982, var1);
      var1.get(tempMat, 0, 16);
      SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
      SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
      System.arraycopy(shadowAngle == sunAngle?sunPosition:moonPosition, 0, shadowLightPosition, 0, 3);
      setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
      setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
      setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
      checkGLError("postCelestialRotate");
   }

   public static void setUpPosition() {
      FloatBuffer var0 = tempMatrixDirectBuffer;
      var0.clear();
      GL11.glGetFloat(2982, var0);
      var0.get(tempMat, 0, 16);
      SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
      setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
   }

   public static void genCompositeMipmap() {
      String[] var0 = ShaderOption.p();
      if(hasGlGenMipmap) {
         int var1 = 0;
         if(var1 < usedColorBuffers) {
            if((activeCompositeMipmapSetting & 1 << var1) != 0) {
               GlStateManager.setActiveTexture('蓀' + colorTextureTextureImageUnit[var1]);
               GL11.glTexParameteri(3553, 10241, 9987);
               GL30.glGenerateMipmap(3553);
            }

            ++var1;
         }

         GlStateManager.setActiveTexture('蓀');
      }

   }

   public static void drawComposite() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex3f(0.0F, 0.0F, 0.0F);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex3f(1.0F, 0.0F, 0.0F);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex3f(1.0F, 1.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex3f(0.0F, 1.0F, 0.0F);
      GL11.glEnd();
   }

   public static void renderCompositeFinal() {
      String[] var0 = ShaderOption.p();
      if(!isShadowPass) {
         checkGLError("pre-renderCompositeFinal");
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.disableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(519);
         GlStateManager.depthMask(false);
         GlStateManager.disableLighting();
         if(usedShadowDepthBuffers >= 1) {
            GlStateManager.setActiveTexture('蓄');
            GlStateManager.bindTexture(sfbDepthTextures.get(0));
            if(usedShadowDepthBuffers >= 2) {
               GlStateManager.setActiveTexture('蓅');
               GlStateManager.bindTexture(sfbDepthTextures.get(1));
            }
         }

         int var1 = 0;
         if(var1 < usedColorBuffers) {
            GlStateManager.setActiveTexture('蓀' + colorTextureTextureImageUnit[var1]);
            GlStateManager.bindTexture(dfbColorTexturesA[var1]);
            ++var1;
         }

         GlStateManager.setActiveTexture('蓆');
         GlStateManager.bindTexture(dfbDepthTextures.get(0));
         if(usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture('蓋');
            GlStateManager.bindTexture(dfbDepthTextures.get(1));
            if(usedDepthBuffers >= 3) {
               GlStateManager.setActiveTexture('蓌');
               GlStateManager.bindTexture(dfbDepthTextures.get(2));
            }
         }

         var1 = 0;
         if(var1 < usedShadowColorBuffers) {
            GlStateManager.setActiveTexture('蓍' + var1);
            GlStateManager.bindTexture(sfbColorTextures.get(var1));
            ++var1;
         }

         if(noiseTextureEnabled) {
            GlStateManager.setActiveTexture('蓀' + noiseTexture.textureUnit);
            GlStateManager.bindTexture(noiseTexture.getID());
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
         }

         bindCustomTextures(customTexturesComposite);
         GlStateManager.setActiveTexture('蓀');
         var1 = 1;
         int var2 = 0;
         if(var2 < usedColorBuffers) {
            EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var2, 3553, dfbColorTexturesA[8 + var2], 0);
            ++var2;
         }

         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '贀', 3553, dfbDepthTextures.get(0), 0);
         GL20.glDrawBuffers(dfbDrawBuffers);
         checkGLError("pre-composite");
         var2 = 0;
         if(var2 < 8) {
            if(programsID[21 + var2] != 0) {
               useProgram(21 + var2);
               checkGLError(programNames[21 + var2]);
               if(activeCompositeMipmapSetting != 0) {
                  genCompositeMipmap();
               }

               drawComposite();
               int var3 = 0;
               if(var3 < usedColorBuffers) {
                  if(programsToggleColorTextures[21 + var2][var3]) {
                     int var4 = colorTexturesToggle[var3];
                     int var5 = colorTexturesToggle[var3] = 8 - var4;
                     GlStateManager.setActiveTexture('蓀' + colorTextureTextureImageUnit[var3]);
                     GlStateManager.bindTexture(dfbColorTexturesA[var5 + var3]);
                     EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠' + var3, 3553, dfbColorTexturesA[var4 + var3], 0);
                  }

                  ++var3;
               }

               GlStateManager.setActiveTexture('蓀');
            }

            ++var2;
         }

         checkGLError("composite");
         isRenderingDfb = false;
         mc.getFramebuffer().bindFramebuffer(true);
         OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, mc.getFramebuffer().framebufferTexture, 0);
         GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
         if(EntityRenderer.anaglyphEnable) {
            var2 = EntityRenderer.anaglyphField != 0;
            GlStateManager.colorMask((boolean)var2, !var2, !var2, true);
         }

         GlStateManager.depthMask(true);
         GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
         GL11.glClear(16640);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.disableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(519);
         GlStateManager.depthMask(false);
         checkGLError("pre-final");
         useProgram(29);
         checkGLError("final");
         if(activeCompositeMipmapSetting != 0) {
            genCompositeMipmap();
         }

         drawComposite();
         checkGLError("renderCompositeFinal");
         isCompositeRendered = true;
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
         useProgram(0);
      }

   }

   public static void endRender() {
      String[] var0 = ShaderOption.p();
      if(isShadowPass) {
         checkGLError("shadow endRender");
      }

      if(!isCompositeRendered) {
         renderCompositeFinal();
      }

      isRenderingWorld = false;
      GlStateManager.colorMask(true, true, true, true);
      useProgram(0);
      RenderHelper.disableStandardItemLighting();
      checkGLError("endRender end");
   }

   public static void beginSky() {
      isRenderingSky = true;
      fogEnabled = true;
      setDrawBuffers(dfbDrawBuffers);
      useProgram(5);
      pushEntity(-2, 0);
   }

   public static void setSkyColor(Vec3 var0) {
      skyColorR = (float)var0.xCoord;
      skyColorG = (float)var0.yCoord;
      skyColorB = (float)var0.zCoord;
      setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
   }

   public static void drawHorizon() {
      WorldRenderer var0 = Tessellator.getInstance().getWorldRenderer();
      float var1 = (float)(mc.gameSettings.renderDistanceChunks * 16);
      double var2 = (double)var1 * 0.9238D;
      double var4 = (double)var1 * 0.3826D;
      double var6 = -var4;
      double var8 = -var2;
      double var10 = 16.0D;
      double var12 = -cameraPositionY;
      var0.begin(7, DefaultVertexFormats.POSITION);
      var0.pos(var6, var12, var8).endVertex();
      var0.pos(var6, var10, var8).endVertex();
      var0.pos(var8, var10, var6).endVertex();
      var0.pos(var8, var12, var6).endVertex();
      var0.pos(var8, var12, var6).endVertex();
      var0.pos(var8, var10, var6).endVertex();
      var0.pos(var8, var10, var4).endVertex();
      var0.pos(var8, var12, var4).endVertex();
      var0.pos(var8, var12, var4).endVertex();
      var0.pos(var8, var10, var4).endVertex();
      var0.pos(var6, var10, var4).endVertex();
      var0.pos(var6, var12, var4).endVertex();
      var0.pos(var6, var12, var4).endVertex();
      var0.pos(var6, var10, var4).endVertex();
      var0.pos(var4, var10, var2).endVertex();
      var0.pos(var4, var12, var2).endVertex();
      var0.pos(var4, var12, var2).endVertex();
      var0.pos(var4, var10, var2).endVertex();
      var0.pos(var2, var10, var4).endVertex();
      var0.pos(var2, var12, var4).endVertex();
      var0.pos(var2, var12, var4).endVertex();
      var0.pos(var2, var10, var4).endVertex();
      var0.pos(var2, var10, var6).endVertex();
      var0.pos(var2, var12, var6).endVertex();
      var0.pos(var2, var12, var6).endVertex();
      var0.pos(var2, var10, var6).endVertex();
      var0.pos(var4, var10, var8).endVertex();
      var0.pos(var4, var12, var8).endVertex();
      var0.pos(var4, var12, var8).endVertex();
      var0.pos(var4, var10, var8).endVertex();
      var0.pos(var6, var10, var8).endVertex();
      var0.pos(var6, var12, var8).endVertex();
      Tessellator.getInstance().draw();
   }

   public static void preSkyList() {
      setUpPosition();
      GL11.glColor3f(fogColorR, fogColorG, fogColorB);
      drawHorizon();
      GL11.glColor3f(skyColorR, skyColorG, skyColorB);
   }

   public static void endSky() {
      ShaderOption.p();
      isRenderingSky = false;
      setDrawBuffers(dfbDrawBuffers);
      useProgram(lightmapEnabled?3:2);
      popEntity();
   }

   public static void beginUpdateChunks() {
      checkGLError("beginUpdateChunks1");
      ShaderOption.p();
      checkFramebufferStatus("beginUpdateChunks1");
      if(!isShadowPass) {
         useProgram(7);
      }

      checkGLError("beginUpdateChunks2");
      checkFramebufferStatus("beginUpdateChunks2");
   }

   public static void endUpdateChunks() {
      ShaderOption.p();
      checkGLError("endUpdateChunks1");
      checkFramebufferStatus("endUpdateChunks1");
      if(!isShadowPass) {
         useProgram(7);
      }

      checkGLError("endUpdateChunks2");
      checkFramebufferStatus("endUpdateChunks2");
   }

   public static boolean shouldRenderClouds(GameSettings var0) {
      String[] var1 = ShaderOption.p();
      if(!shaderPackLoaded) {
         return true;
      } else {
         checkGLError("shouldRenderClouds");
         return isShadowPass?configCloudShadow:var0.clouds > 0;
      }
   }

   public static void beginClouds() {
      fogEnabled = true;
      pushEntity(-3, 0);
      useProgram(6);
   }

   public static void endClouds() {
      ShaderOption.p();
      disableFog();
      popEntity();
      useProgram(lightmapEnabled?3:2);
   }

   public static void beginEntities() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         useProgram(16);
         resetDisplayListModels();
      }

   }

   public static void nextEntity(Entity var0) {
      String[] var1 = ShaderOption.p();
      if(isRenderingWorld) {
         useProgram(16);
         setEntityId(var0);
      }

   }

   public static void setEntityId(Entity var0) {
      String[] var1 = ShaderOption.p();
      if(isRenderingWorld && !isShadowPass && uniformEntityId.d()) {
         int var2 = EntityList.getEntityID(var0);
         uniformEntityId.setValue(var2);
      }

   }

   public static void beginSpiderEyes() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld && programsID[18] != programsID[0]) {
         useProgram(18);
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, 0.0F);
         GlStateManager.blendFunc(770, 771);
      }

   }

   public static void endEntities() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         useProgram(lightmapEnabled?3:2);
      }

   }

   public static void setEntityColor(float var0, float var1, float var2, float var3) {
      String[] var4 = ShaderOption.p();
      if(isRenderingWorld && !isShadowPass) {
         uniformEntityColor.setValue(var0, var1, var2, var3);
      }

   }

   public static void beginLivingDamage() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         ShadersTex.bindTexture(defaultTexture);
         if(!isShadowPass) {
            setDrawBuffers(drawBuffersColorAtt0);
         }
      }

   }

   public static void endLivingDamage() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld && !isShadowPass) {
         setDrawBuffers(programsDrawBuffers[16]);
      }

   }

   public static void beginBlockEntities() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         checkGLError("beginBlockEntities");
         useProgram(13);
      }

   }

   public static void nextBlockEntity(TileEntity var0) {
      String[] var1 = ShaderOption.p();
      if(isRenderingWorld) {
         checkGLError("nextBlockEntity");
         useProgram(13);
         setBlockEntityId(var0);
      }

   }

   public static void setBlockEntityId(TileEntity var0) {
      String[] var1 = ShaderOption.p();
      if(isRenderingWorld && !isShadowPass && uniformBlockEntityId.d()) {
         Block var2 = var0.getBlockType();
         int var3 = Block.getIdFromBlock(var2);
         uniformBlockEntityId.setValue(var3);
      }

   }

   public static void endBlockEntities() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         checkGLError("endBlockEntities");
         useProgram(lightmapEnabled?3:2);
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
      }

   }

   public static void g() {
      useProgram(3);
   }

   public static void ao() {
      useProgram(2);
   }

   public static void E() {
      useProgram(3);
   }

   public static void readCenterDepth() {
      String[] var0 = ShaderOption.p();
      if(!isShadowPass && centerDepthSmoothEnabled) {
         tempDirectFloatBuffer.clear();
         GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
         centerDepth = tempDirectFloatBuffer.get(0);
         float var1 = (float)diffSystemTime * 0.01F;
         float var2 = (float)Math.exp(Math.log(0.5D) * (double)var1 / (double)centerDepthSmoothHalflife);
         centerDepthSmooth = centerDepthSmooth * var2 + centerDepth * (1.0F - var2);
      }

   }

   public static void beginWeather() {
      String[] var0 = ShaderOption.p();
      if(!isShadowPass) {
         if(usedDepthBuffers >= 3) {
            GlStateManager.setActiveTexture('蓌');
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
            GlStateManager.setActiveTexture('蓀');
         }

         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.enableAlpha();
         useProgram(20);
      }

   }

   public static void aD() {
      GlStateManager.disableBlend();
      useProgram(3);
   }

   public static void preWater() {
      String[] var0 = ShaderOption.p();
      if(usedDepthBuffers >= 2) {
         GlStateManager.setActiveTexture('蓋');
         checkGLError("pre copy depth");
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
         checkGLError("copy depth");
         GlStateManager.setActiveTexture('蓀');
      }

      ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
   }

   public static void beginWater() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         if(!isShadowPass) {
            useProgram(12);
            GlStateManager.enableBlend();
         }

         GlStateManager.depthMask(true);
      }

   }

   public static void am() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         if(isShadowPass) {
            ;
         }

         useProgram(lightmapEnabled?3:2);
      }

   }

   public static void e() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         useProgram(1);
      }

   }

   public static void aG() {
      String[] var0 = ShaderOption.p();
      if(isRenderingWorld) {
         useProgram(3);
      }

   }

   public static void applyHandDepth() {
      String[] var0 = ShaderOption.p();
      if((double)configHandDepthMul != 1.0D) {
         GL11.glScaled(1.0D, 1.0D, (double)configHandDepthMul);
      }

   }

   public static void beginHand() {
      GL11.glMatrixMode(5888);
      GL11.glPushMatrix();
      GL11.glMatrixMode(5889);
      GL11.glPushMatrix();
      GL11.glMatrixMode(5888);
      useProgram(19);
      checkGLError("beginHand");
      checkFramebufferStatus("beginHand");
   }

   public static void endHand() {
      checkGLError("pre endHand");
      checkFramebufferStatus("pre endHand");
      GL11.glMatrixMode(5889);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glPopMatrix();
      GlStateManager.blendFunc(770, 771);
      checkGLError("endHand");
   }

   public static void beginFPOverlay() {
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
   }

   public static void endFPOverlay() {
   }

   public static void glEnableWrapper(int var0) {
      ShaderOption.p();
      GL11.glEnable(var0);
      if(var0 == 3553) {
         enableTexture2D();
      }

      if(var0 == 2912) {
         enableFog();
      }

   }

   public static void glDisableWrapper(int var0) {
      ShaderOption.p();
      GL11.glDisable(var0);
      if(var0 == 3553) {
         disableTexture2D();
      }

      if(var0 == 2912) {
         disableFog();
      }

   }

   public static void sglEnableT2D(int var0) {
      GL11.glEnable(var0);
      enableTexture2D();
   }

   public static void sglDisableT2D(int var0) {
      GL11.glDisable(var0);
      disableTexture2D();
   }

   public static void sglEnableFog(int var0) {
      GL11.glEnable(var0);
      enableFog();
   }

   public static void sglDisableFog(int var0) {
      GL11.glDisable(var0);
      disableFog();
   }

   public static void enableTexture2D() {
      String[] var0 = ShaderOption.p();
      if(isRenderingSky) {
         useProgram(5);
      }

      if(activeProgram == 1) {
         useProgram(lightmapEnabled?3:2);
      }

   }

   public static void disableTexture2D() {
      String[] var0 = ShaderOption.p();
      if(isRenderingSky) {
         useProgram(4);
      }

      if(activeProgram == 2 || activeProgram == 3) {
         useProgram(1);
      }

   }

   public static void beginLeash() {
      useProgram(1);
   }

   public static void endLeash() {
      useProgram(16);
   }

   public static void enableFog() {
      fogEnabled = true;
      setProgramUniform1i("fogMode", fogMode);
   }

   public static void disableFog() {
      fogEnabled = false;
      setProgramUniform1i("fogMode", 0);
   }

   public static void setFog(int var0) {
      GlStateManager.setFog(var0);
      if(fogEnabled) {
         setProgramUniform1i("fogMode", var0);
      }

   }

   public static void sglFogi(int var0, int var1) {
      ShaderOption.p();
      GL11.glFogi(var0, var1);
      if(var0 == 2917) {
         fogMode = var1;
         if(fogEnabled) {
            setProgramUniform1i("fogMode", fogMode);
         }
      }

   }

   public static void enableLightmap() {
      ShaderOption.p();
      lightmapEnabled = true;
      if(activeProgram == 2) {
         useProgram(3);
      }

   }

   public static void disableLightmap() {
      ShaderOption.p();
      lightmapEnabled = false;
      if(activeProgram == 3) {
         useProgram(2);
      }

   }

   public static int getEntityData() {
      return entityData[entityDataIndex * 2];
   }

   public static int getEntityData2() {
      return entityData[entityDataIndex * 2 + 1];
   }

   public static int setEntityData1(int var0) {
      entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & '\uffff' | var0 << 16;
      return var0;
   }

   public static int setEntityData2(int var0) {
      entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & -65536 | var0 & '\uffff';
      return var0;
   }

   public static void pushEntity(int var0, int var1) {
      ++entityDataIndex;
      entityData[entityDataIndex * 2] = var0 & '\uffff' | var1 << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(int var0) {
      ++entityDataIndex;
      entityData[entityDataIndex * 2] = var0 & '\uffff';
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(Block var0) {
      ++entityDataIndex;
      entityData[entityDataIndex * 2] = Block.blockRegistry.getIDForObject(var0) & '\uffff' | var0.getRenderType() << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void popEntity() {
      entityData[entityDataIndex * 2] = 0;
      entityData[entityDataIndex * 2 + 1] = 0;
      --entityDataIndex;
   }

   public static void mcProfilerEndSection() {
      mc.mcProfiler.endSection();
   }

   public static String getShaderPackName() {
      String[] var0 = ShaderOption.p();
      return shaderPack == null?null:(shaderPack instanceof ShaderPackNone?null:shaderPack.getName());
   }

   public static InputStream getShaderPackResourceStream(String var0) {
      String[] var1 = ShaderOption.p();
      return shaderPack == null?null:shaderPack.getResourceAsStream(var0);
   }

   public static void nextAntialiasingLevel() {
      ShaderOption.p();
      configAntialiasingLevel += 2;
      configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
      if(configAntialiasingLevel > 4) {
         configAntialiasingLevel = 0;
      }

      configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
   }

   public static void checkShadersModInstalled() {
      String var10000 = "net.shadersmod.transform.SMCClassTransformer";

      try {
         Class var0 = af_.a(var10000);
      } catch (Throwable var1) {
         return;
      }

      throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
   }

   public static void resourcesReloaded() {
      loadShaderPackResources();
   }

   private static void loadShaderPackResources() {
      ShaderOption.p();
      shaderPackResources = new HashMap();
      if(shaderPackLoaded) {
         ArrayList var1 = new ArrayList();
         String var2 = "/shaders/lang/";
         String var3 = "en_US";
         String var4 = ".lang";
         var1.add(var2 + var3 + var4);
         if(!Config.getGameSettings().language.equals(var3)) {
            var1.add(var2 + Config.getGameSettings().language + var4);
         }

         try {
            Iterator var5 = var1.iterator();
            if(var5.hasNext()) {
               String var6 = (String)var5.next();
               InputStream var7 = shaderPack.getResourceAsStream(var6);
               Properties var8 = new Properties();
               Lang.loadLocaleData(var7, var8);
               var7.close();
               Iterator var9 = var8.keySet().iterator();
               if(var9.hasNext()) {
                  Object var10 = var9.next();
                  String var11 = var8.getProperty((String)var10);
                  shaderPackResources.put((String)var10, var11);
               }
            }
         } catch (IOException var12) {
            var12.printStackTrace();
         }
      }

   }

   public static String translate(String var0, String var1) {
      ShaderOption.p();
      String var3 = (String)shaderPackResources.get(var0);
      return var3 == null?var1:var3;
   }

   public static boolean isProgramPath(String var0) {
      String[] var1 = ShaderOption.p();
      if(var0 == null) {
         return false;
      } else if(var0.length() <= 0) {
         return false;
      } else {
         int var2 = var0.lastIndexOf("/");
         if(var2 >= 0) {
            var0 = var0.substring(var2 + 1);
         }

         return Arrays.asList(programNames).contains(var0);
      }
   }

   public static void setItemToRenderMain(ItemStack var0) {
      itemToRenderMainTranslucent = isTranslucentBlock(var0);
   }

   public static boolean isItemToRenderMainTranslucent() {
      return itemToRenderMainTranslucent;
   }

   private static boolean isTranslucentBlock(ItemStack var0) {
      String[] var1 = ShaderOption.p();
      if(var0 == null) {
         return false;
      } else {
         Item var2 = var0.getItem();
         if(var2 == null) {
            return false;
         } else if(!(var2 instanceof ItemBlock)) {
            return false;
         } else {
            ItemBlock var3 = (ItemBlock)var2;
            Block var4 = var3.getBlock();
            if(var4 == null) {
               return false;
            } else {
               EnumWorldBlockLayer var5 = var4.getBlockLayer();
               return var5 == EnumWorldBlockLayer.TRANSLUCENT;
            }
         }
      }
   }

   public static boolean isRenderBothHands() {
      return false;
   }

   public static boolean isHandRenderedMain() {
      return isHandRenderedMain;
   }

   public static void setHandRenderedMain(boolean var0) {
      isHandRenderedMain = var0;
   }

   public static float ak() {
      String[] var0 = ShaderOption.p();
      return shadowDistanceRenderMul < 0.0F?-1.0F:shadowMapHalfPlane * shadowDistanceRenderMul;
   }

   static {
      b((String[])null);
      shadersdir = new File(Minecraft.getInstance().mcDataDir, "shaders");
      shaderpacksdir = new File(Minecraft.getInstance().mcDataDir, shaderpacksdirname);
      configFile = new File(Minecraft.getInstance().mcDataDir, optionsfilename);
      drawBuffersNone.limit(0);
      drawBuffersColorAtt0.put('賠').position(0).limit(1);
   }

   public static void b(String[] var0) {
      Y = var0;
   }

   public static String[] v() {
      return Y;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
