/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.CallSite;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Config;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.GlErrors;
import net.optifine.Lang;
import net.optifine.config.ConnectedParser;
import net.optifine.expr.IExpressionBool;
import net.optifine.reflect.Reflector;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.render.RenderTypes;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.BlockAliases;
import net.optifine.shaders.ComputeProgram;
import net.optifine.shaders.CustomTexture;
import net.optifine.shaders.CustomTextureLocation;
import net.optifine.shaders.CustomTextureRaw;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.EntityAliases;
import net.optifine.shaders.FixedFramebuffer;
import net.optifine.shaders.GlState;
import net.optifine.shaders.HFNoiseTexture;
import net.optifine.shaders.ICustomTexture;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.ItemAliases;
import net.optifine.shaders.Program;
import net.optifine.shaders.ProgramStack;
import net.optifine.shaders.ProgramStage;
import net.optifine.shaders.ProgramUtils;
import net.optifine.shaders.Programs;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.SMath;
import net.optifine.shaders.ShaderPackDefault;
import net.optifine.shaders.ShaderPackFolder;
import net.optifine.shaders.ShaderPackNone;
import net.optifine.shaders.ShaderPackZip;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.ShadersFramebuffer;
import net.optifine.shaders.ShadersRender;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.SimpleShaderTexture;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
import net.optifine.shaders.config.PropertyDefaultTrueFalse;
import net.optifine.shaders.config.RenderScale;
import net.optifine.shaders.config.ScreenShaderOptions;
import net.optifine.shaders.config.ShaderLine;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionRest;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.shaders.config.ShaderParser;
import net.optifine.shaders.config.ShaderProfile;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniform4i;
import net.optifine.shaders.uniform.ShaderUniformM4;
import net.optifine.shaders.uniform.ShaderUniforms;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.PixelType;
import net.optifine.texture.TextureType;
import net.optifine.util.ArrayUtils;
import net.optifine.util.DynamicDimension;
import net.optifine.util.EntityUtils;
import net.optifine.util.LineBuffer;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TimedEvent;
import net.optifine.util.WorldUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTGeometryShader4;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.KHRDebug;

public class Shaders {
    static Minecraft mc;
    static GameRenderer entityRenderer;
    public static boolean isInitializedOnce;
    public static boolean isShaderPackInitialized;
    public static GLCapabilities capabilities;
    public static String glVersionString;
    public static String glVendorString;
    public static String glRendererString;
    public static boolean hasGlGenMipmap;
    public static int countResetDisplayLists;
    private static int renderDisplayWidth;
    private static int renderDisplayHeight;
    public static int renderWidth;
    public static int renderHeight;
    public static boolean isRenderingWorld;
    public static boolean isRenderingSky;
    public static boolean isCompositeRendered;
    public static boolean isRenderingDfb;
    public static boolean isShadowPass;
    public static boolean isEntitiesGlowing;
    public static boolean isSleeping;
    private static boolean isRenderingFirstPersonHand;
    private static boolean isHandRenderedMain;
    private static boolean isHandRenderedOff;
    private static boolean skipRenderHandMain;
    private static boolean skipRenderHandOff;
    public static boolean renderItemKeepDepthMask;
    public static boolean itemToRenderMainTranslucent;
    public static boolean itemToRenderOffTranslucent;
    static float[] sunPosition;
    static float[] moonPosition;
    static float[] shadowLightPosition;
    static float[] upPosition;
    static float[] shadowLightPositionVector;
    static float[] upPosModelView;
    static float[] sunPosModelView;
    static float[] moonPosModelView;
    private static float[] tempMat;
    static Vector4f clearColor;
    static float skyColorR;
    static float skyColorG;
    static float skyColorB;
    static long worldTime;
    static long lastWorldTime;
    static long diffWorldTime;
    static float celestialAngle;
    static float sunAngle;
    static float shadowAngle;
    static int moonPhase;
    static long systemTime;
    static long lastSystemTime;
    static long diffSystemTime;
    static int frameCounter;
    static float frameTime;
    static float frameTimeCounter;
    static int systemTimeInt32;
    public static PointOfView pointOfView;
    public static boolean pointOfViewChanged;
    static float rainStrength;
    static float wetness;
    public static float wetnessHalfLife;
    public static float drynessHalfLife;
    public static float eyeBrightnessHalflife;
    static boolean usewetness;
    static int isEyeInWater;
    static int eyeBrightness;
    static float eyeBrightnessFadeX;
    static float eyeBrightnessFadeY;
    static float eyePosY;
    static float centerDepth;
    static float centerDepthSmooth;
    static float centerDepthSmoothHalflife;
    static boolean centerDepthSmoothEnabled;
    static int superSamplingLevel;
    static float nightVision;
    static float blindness;
    static boolean lightmapEnabled;
    static boolean fogEnabled;
    static RenderStage renderStage;
    private static int baseAttribId;
    public static int entityAttrib;
    public static int midTexCoordAttrib;
    public static int tangentAttrib;
    public static int velocityAttrib;
    public static int midBlockAttrib;
    public static boolean useEntityAttrib;
    public static boolean useMidTexCoordAttrib;
    public static boolean useTangentAttrib;
    public static boolean useVelocityAttrib;
    public static boolean useMidBlockAttrib;
    public static boolean progUseEntityAttrib;
    public static boolean progUseMidTexCoordAttrib;
    public static boolean progUseTangentAttrib;
    public static boolean progUseVelocityAttrib;
    public static boolean progUseMidBlockAttrib;
    private static boolean progArbGeometryShader4;
    private static boolean progExtGeometryShader4;
    private static int progMaxVerticesOut;
    private static boolean hasGeometryShaders;
    public static int atlasSizeX;
    public static int atlasSizeY;
    private static ShaderUniforms shaderUniforms;
    public static ShaderUniform4f uniform_entityColor;
    public static ShaderUniform1i uniform_entityId;
    public static ShaderUniform1i uniform_blockEntityId;
    public static ShaderUniform1i uniform_texture;
    public static ShaderUniform1i uniform_lightmap;
    public static ShaderUniform1i uniform_normals;
    public static ShaderUniform1i uniform_specular;
    public static ShaderUniform1i uniform_shadow;
    public static ShaderUniform1i uniform_watershadow;
    public static ShaderUniform1i uniform_shadowtex0;
    public static ShaderUniform1i uniform_shadowtex1;
    public static ShaderUniform1i uniform_depthtex0;
    public static ShaderUniform1i uniform_depthtex1;
    public static ShaderUniform1i uniform_shadowcolor;
    public static ShaderUniform1i uniform_shadowcolor0;
    public static ShaderUniform1i uniform_shadowcolor1;
    public static ShaderUniform1i uniform_noisetex;
    public static ShaderUniform1i uniform_gcolor;
    public static ShaderUniform1i uniform_gdepth;
    public static ShaderUniform1i uniform_gnormal;
    public static ShaderUniform1i uniform_composite;
    public static ShaderUniform1i uniform_gaux1;
    public static ShaderUniform1i uniform_gaux2;
    public static ShaderUniform1i uniform_gaux3;
    public static ShaderUniform1i uniform_gaux4;
    public static ShaderUniform1i uniform_colortex0;
    public static ShaderUniform1i uniform_colortex1;
    public static ShaderUniform1i uniform_colortex2;
    public static ShaderUniform1i uniform_colortex3;
    public static ShaderUniform1i uniform_colortex4;
    public static ShaderUniform1i uniform_colortex5;
    public static ShaderUniform1i uniform_colortex6;
    public static ShaderUniform1i uniform_colortex7;
    public static ShaderUniform1i uniform_gdepthtex;
    public static ShaderUniform1i uniform_depthtex2;
    public static ShaderUniform1i uniform_colortex8;
    public static ShaderUniform1i uniform_colortex9;
    public static ShaderUniform1i uniform_colortex10;
    public static ShaderUniform1i uniform_colortex11;
    public static ShaderUniform1i uniform_colortex12;
    public static ShaderUniform1i uniform_colortex13;
    public static ShaderUniform1i uniform_colortex14;
    public static ShaderUniform1i uniform_colortex15;
    public static ShaderUniform1i uniform_colorimg0;
    public static ShaderUniform1i uniform_colorimg1;
    public static ShaderUniform1i uniform_colorimg2;
    public static ShaderUniform1i uniform_colorimg3;
    public static ShaderUniform1i uniform_colorimg4;
    public static ShaderUniform1i uniform_colorimg5;
    public static ShaderUniform1i uniform_shadowcolorimg0;
    public static ShaderUniform1i uniform_shadowcolorimg1;
    public static ShaderUniform1i uniform_tex;
    public static ShaderUniform1i uniform_heldItemId;
    public static ShaderUniform1i uniform_heldBlockLightValue;
    public static ShaderUniform1i uniform_heldItemId2;
    public static ShaderUniform1i uniform_heldBlockLightValue2;
    public static ShaderUniform1i uniform_fogMode;
    public static ShaderUniform1f uniform_fogDensity;
    public static ShaderUniform3f uniform_fogColor;
    public static ShaderUniform3f uniform_skyColor;
    public static ShaderUniform1i uniform_worldTime;
    public static ShaderUniform1i uniform_worldDay;
    public static ShaderUniform1i uniform_moonPhase;
    public static ShaderUniform1i uniform_frameCounter;
    public static ShaderUniform1f uniform_frameTime;
    public static ShaderUniform1f uniform_frameTimeCounter;
    public static ShaderUniform1f uniform_sunAngle;
    public static ShaderUniform1f uniform_shadowAngle;
    public static ShaderUniform1f uniform_rainStrength;
    public static ShaderUniform1f uniform_aspectRatio;
    public static ShaderUniform1f uniform_viewWidth;
    public static ShaderUniform1f uniform_viewHeight;
    public static ShaderUniform1f uniform_near;
    public static ShaderUniform1f uniform_far;
    public static ShaderUniform3f uniform_sunPosition;
    public static ShaderUniform3f uniform_moonPosition;
    public static ShaderUniform3f uniform_shadowLightPosition;
    public static ShaderUniform3f uniform_upPosition;
    public static ShaderUniform3f uniform_previousCameraPosition;
    public static ShaderUniform3f uniform_cameraPosition;
    public static ShaderUniformM4 uniform_gbufferModelView;
    public static ShaderUniformM4 uniform_gbufferModelViewInverse;
    public static ShaderUniformM4 uniform_gbufferPreviousProjection;
    public static ShaderUniformM4 uniform_gbufferProjection;
    public static ShaderUniformM4 uniform_gbufferProjectionInverse;
    public static ShaderUniformM4 uniform_gbufferPreviousModelView;
    public static ShaderUniformM4 uniform_shadowProjection;
    public static ShaderUniformM4 uniform_shadowProjectionInverse;
    public static ShaderUniformM4 uniform_shadowModelView;
    public static ShaderUniformM4 uniform_shadowModelViewInverse;
    public static ShaderUniform1f uniform_wetness;
    public static ShaderUniform1f uniform_eyeAltitude;
    public static ShaderUniform2i uniform_eyeBrightness;
    public static ShaderUniform2i uniform_eyeBrightnessSmooth;
    public static ShaderUniform2i uniform_terrainTextureSize;
    public static ShaderUniform1i uniform_terrainIconSize;
    public static ShaderUniform1i uniform_isEyeInWater;
    public static ShaderUniform1f uniform_nightVision;
    public static ShaderUniform1f uniform_blindness;
    public static ShaderUniform1f uniform_screenBrightness;
    public static ShaderUniform1i uniform_hideGUI;
    public static ShaderUniform1f uniform_centerDepthSmooth;
    public static ShaderUniform2i uniform_atlasSize;
    public static ShaderUniform4f uniform_spriteBounds;
    public static ShaderUniform4i uniform_blendFunc;
    public static ShaderUniform1i uniform_instanceId;
    public static ShaderUniform1f uniform_playerMood;
    public static ShaderUniform1i uniform_renderStage;
    static double previousCameraPositionX;
    static double previousCameraPositionY;
    static double previousCameraPositionZ;
    static double cameraPositionX;
    static double cameraPositionY;
    static double cameraPositionZ;
    static int cameraOffsetX;
    static int cameraOffsetZ;
    static boolean hasShadowMap;
    public static boolean needResizeShadow;
    static int shadowMapWidth;
    static int shadowMapHeight;
    static int spShadowMapWidth;
    static int spShadowMapHeight;
    static float shadowMapFOV;
    static float shadowMapHalfPlane;
    static boolean shadowMapIsOrtho;
    static float shadowDistanceRenderMul;
    public static boolean shouldSkipDefaultShadow;
    static boolean waterShadowEnabled;
    public static final int MaxDrawBuffers = 8;
    public static final int MaxColorBuffers = 16;
    public static final int MaxDepthBuffers = 3;
    public static final int MaxShadowColorBuffers = 2;
    public static final int MaxShadowDepthBuffers = 2;
    static int usedColorBuffers;
    static int usedDepthBuffers;
    static int usedShadowColorBuffers;
    static int usedShadowDepthBuffers;
    static int usedColorAttachs;
    static int usedDrawBuffers;
    static boolean bindImageTextures;
    static ShadersFramebuffer dfb;
    static ShadersFramebuffer sfb;
    private static int[] gbuffersFormat;
    public static boolean[] gbuffersClear;
    public static Vector4f[] gbuffersClearColor;
    private static final Vector4f CLEAR_COLOR_0;
    private static final Vector4f CLEAR_COLOR_1;
    private static int[] shadowBuffersFormat;
    public static boolean[] shadowBuffersClear;
    public static Vector4f[] shadowBuffersClearColor;
    private static Programs programs;
    public static final Program ProgramNone;
    public static final Program ProgramShadow;
    public static final Program ProgramShadowSolid;
    public static final Program ProgramShadowCutout;
    public static final Program[] ProgramsShadowcomp;
    public static final Program[] ProgramsPrepare;
    public static final Program ProgramBasic;
    public static final Program ProgramTextured;
    public static final Program ProgramTexturedLit;
    public static final Program ProgramSkyBasic;
    public static final Program ProgramSkyTextured;
    public static final Program ProgramClouds;
    public static final Program ProgramTerrain;
    public static final Program ProgramTerrainSolid;
    public static final Program ProgramTerrainCutoutMip;
    public static final Program ProgramTerrainCutout;
    public static final Program ProgramDamagedBlock;
    public static final Program ProgramBlock;
    public static final Program ProgramBeaconBeam;
    public static final Program ProgramItem;
    public static final Program ProgramEntities;
    public static final Program ProgramEntitiesGlowing;
    public static final Program ProgramArmorGlint;
    public static final Program ProgramSpiderEyes;
    public static final Program ProgramHand;
    public static final Program ProgramWeather;
    public static final Program ProgramDeferredPre;
    public static final Program[] ProgramsDeferred;
    public static final Program ProgramDeferred;
    public static final Program ProgramWater;
    public static final Program ProgramHandWater;
    public static final Program ProgramCompositePre;
    public static final Program[] ProgramsComposite;
    public static final Program ProgramComposite;
    public static final Program ProgramFinal;
    public static final int ProgramCount;
    public static final Program[] ProgramsAll;
    public static Program activeProgram;
    public static int activeProgramID;
    private static ProgramStack programStack;
    private static boolean hasDeferredPrograms;
    public static boolean hasShadowcompPrograms;
    public static boolean hasPreparePrograms;
    public static Properties loadedShaders;
    public static Properties shadersConfig;
    public static Texture defaultTexture;
    public static boolean[] shadowHardwareFilteringEnabled;
    public static boolean[] shadowMipmapEnabled;
    public static boolean[] shadowFilterNearest;
    public static boolean[] shadowColorMipmapEnabled;
    public static boolean[] shadowColorFilterNearest;
    public static boolean configTweakBlockDamage;
    public static boolean configCloudShadow;
    public static float configHandDepthMul;
    public static float configRenderResMul;
    public static float configShadowResMul;
    public static int configTexMinFilB;
    public static int configTexMinFilN;
    public static int configTexMinFilS;
    public static int configTexMagFilB;
    public static int configTexMagFilN;
    public static int configTexMagFilS;
    public static boolean configShadowClipFrustrum;
    public static boolean configNormalMap;
    public static boolean configSpecularMap;
    public static PropertyDefaultTrueFalse configOldLighting;
    public static PropertyDefaultTrueFalse configOldHandLight;
    public static int configAntialiasingLevel;
    public static final int texMinFilRange = 3;
    public static final int texMagFilRange = 2;
    public static final String[] texMinFilDesc;
    public static final String[] texMagFilDesc;
    public static final int[] texMinFilValue;
    public static final int[] texMagFilValue;
    private static IShaderPack shaderPack;
    public static boolean shaderPackLoaded;
    public static String currentShaderName;
    public static final String SHADER_PACK_NAME_NONE = "OFF";
    public static final String SHADER_PACK_NAME_DEFAULT = "(internal)";
    public static final String SHADER_PACKS_DIR_NAME = "shaderpacks";
    public static final String OPTIONS_FILE_NAME = "optionsshaders.txt";
    public static File shaderPacksDir;
    static File configFile;
    private static ShaderOption[] shaderPackOptions;
    private static Set<String> shaderPackOptionSliders;
    static ShaderProfile[] shaderPackProfiles;
    static Map<String, ScreenShaderOptions> shaderPackGuiScreens;
    static Map<String, IExpressionBool> shaderPackProgramConditions;
    public static final String PATH_SHADERS_PROPERTIES = "/shaders/shaders.properties";
    public static PropertyDefaultFastFancyOff shaderPackClouds;
    public static PropertyDefaultTrueFalse shaderPackOldLighting;
    public static PropertyDefaultTrueFalse shaderPackOldHandLight;
    public static PropertyDefaultTrueFalse shaderPackDynamicHandLight;
    public static PropertyDefaultTrueFalse shaderPackShadowTerrain;
    public static PropertyDefaultTrueFalse shaderPackShadowTranslucent;
    public static PropertyDefaultTrueFalse shaderPackShadowEntities;
    public static PropertyDefaultTrueFalse shaderPackShadowBlockEntities;
    public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay;
    public static PropertyDefaultTrueFalse shaderPackSun;
    public static PropertyDefaultTrueFalse shaderPackMoon;
    public static PropertyDefaultTrueFalse shaderPackVignette;
    public static PropertyDefaultTrueFalse shaderPackBackFaceSolid;
    public static PropertyDefaultTrueFalse shaderPackBackFaceCutout;
    public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped;
    public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent;
    public static PropertyDefaultTrueFalse shaderPackRainDepth;
    public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth;
    public static PropertyDefaultTrueFalse shaderPackSeparateAo;
    public static PropertyDefaultTrueFalse shaderPackFrustumCulling;
    private static Map<String, String> shaderPackResources;
    private static ClientWorld currentWorld;
    private static List<Integer> shaderPackDimensions;
    private static ICustomTexture[] customTexturesGbuffers;
    private static ICustomTexture[] customTexturesComposite;
    private static ICustomTexture[] customTexturesDeferred;
    private static ICustomTexture[] customTexturesShadowcomp;
    private static ICustomTexture[] customTexturesPrepare;
    private static String noiseTexturePath;
    private static DynamicDimension[] colorBufferSizes;
    private static CustomUniforms customUniforms;
    public static final boolean saveFinalShaders;
    public static float blockLightLevel05;
    public static float blockLightLevel06;
    public static float blockLightLevel08;
    public static float aoLevel;
    public static float sunPathRotation;
    public static float shadowAngleInterval;
    public static int fogMode;
    public static float fogDensity;
    public static float fogColorR;
    public static float fogColorG;
    public static float fogColorB;
    public static float shadowIntervalSize;
    public static int terrainIconSize;
    public static int[] terrainTextureSize;
    private static ICustomTexture noiseTexture;
    private static boolean noiseTextureEnabled;
    private static int noiseTextureResolution;
    static final int[] colorTextureImageUnit;
    static final int[] depthTextureImageUnit;
    static final int[] shadowColorTextureImageUnit;
    static final int[] shadowDepthTextureImageUnit;
    static final int[] colorImageUnit;
    static final int[] shadowColorImageUnit;
    private static final int bigBufferSize;
    private static final ByteBuffer bigBuffer;
    static final float[] faProjection;
    static final float[] faProjectionInverse;
    static final float[] faModelView;
    static final float[] faModelViewInverse;
    static final float[] faShadowProjection;
    static final float[] faShadowProjectionInverse;
    static final float[] faShadowModelView;
    static final float[] faShadowModelViewInverse;
    static final FloatBuffer projection;
    static final FloatBuffer projectionInverse;
    static final FloatBuffer modelView;
    static final FloatBuffer modelViewInverse;
    static final FloatBuffer shadowProjection;
    static final FloatBuffer shadowProjectionInverse;
    static final FloatBuffer shadowModelView;
    static final FloatBuffer shadowModelViewInverse;
    static final FloatBuffer previousProjection;
    static final FloatBuffer previousModelView;
    static final FloatBuffer tempMatrixDirectBuffer;
    static final FloatBuffer tempDirectFloatBuffer;
    static final DrawBuffers dfbDrawBuffers;
    static final DrawBuffers sfbDrawBuffers;
    static final DrawBuffers drawBuffersNone;
    static final DrawBuffers[] drawBuffersColorAtt;
    static boolean glDebugGroups;
    static boolean glDebugGroupProgram;
    static Map<Block, Integer> mapBlockToEntityData;
    private static final String[] formatNames;
    private static final int[] formatIds;
    private static final Pattern patternLoadEntityDataMap;
    public static int[] entityData;
    public static int entityDataIndex;

    private Shaders() {
    }

    private static ByteBuffer nextByteBuffer(int n) {
        ByteBuffer byteBuffer = bigBuffer;
        int n2 = byteBuffer.limit();
        byteBuffer.position(n2).limit(n2 + n);
        return byteBuffer.slice();
    }

    public static IntBuffer nextIntBuffer(int n) {
        ByteBuffer byteBuffer = bigBuffer;
        int n2 = byteBuffer.limit();
        byteBuffer.position(n2).limit(n2 + n * 4);
        return byteBuffer.asIntBuffer();
    }

    private static FloatBuffer nextFloatBuffer(int n) {
        ByteBuffer byteBuffer = bigBuffer;
        int n2 = byteBuffer.limit();
        byteBuffer.position(n2).limit(n2 + n * 4);
        return byteBuffer.asFloatBuffer();
    }

    private static IntBuffer[] nextIntBufferArray(int n, int n2) {
        IntBuffer[] intBufferArray = new IntBuffer[n];
        for (int i = 0; i < n; ++i) {
            intBufferArray[i] = Shaders.nextIntBuffer(n2);
        }
        return intBufferArray;
    }

    private static DrawBuffers[] makeDrawBuffersColorSingle(int n) {
        DrawBuffers[] drawBuffersArray = new DrawBuffers[n];
        for (int i = 0; i < drawBuffersArray.length; ++i) {
            DrawBuffers drawBuffers = new DrawBuffers("single" + i, 16, 8);
            drawBuffers.put(36064 + i);
            drawBuffers.position(0);
            drawBuffers.limit(1);
            drawBuffersArray[i] = drawBuffers;
        }
        return drawBuffersArray;
    }

    public static void loadConfig() {
        Object object;
        SMCLog.info("Load shaders configuration.");
        try {
            if (!shaderPacksDir.exists()) {
                shaderPacksDir.mkdir();
            }
        } catch (Exception exception) {
            SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
        }
        shadersConfig = new PropertiesOrdered();
        shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
        if (configFile.exists()) {
            try {
                object = new FileReader(configFile);
                shadersConfig.load((Reader)object);
                ((InputStreamReader)object).close();
            } catch (Exception exception) {
                // empty catch block
            }
        }
        if (!configFile.exists()) {
            try {
                Shaders.storeConfig();
            } catch (Exception exception) {
                // empty catch block
            }
        }
        object = EnumShaderOption.values();
        for (int i = 0; i < ((EnumShaderOption[])object).length; ++i) {
            Object object2 = object[i];
            String string = ((EnumShaderOption)((Object)object2)).getPropertyKey();
            String string2 = ((EnumShaderOption)((Object)object2)).getValueDefault();
            String string3 = shadersConfig.getProperty(string, string2);
            Shaders.setEnumShaderOption((EnumShaderOption)((Object)object2), string3);
        }
        Shaders.loadShaderPack();
    }

    private static void setEnumShaderOption(EnumShaderOption enumShaderOption, String string) {
        if (string == null) {
            string = enumShaderOption.getValueDefault();
        }
        switch (enumShaderOption) {
            case ANTIALIASING: {
                configAntialiasingLevel = Config.parseInt(string, 0);
                break;
            }
            case NORMAL_MAP: {
                configNormalMap = Config.parseBoolean(string, true);
                break;
            }
            case SPECULAR_MAP: {
                configSpecularMap = Config.parseBoolean(string, true);
                break;
            }
            case RENDER_RES_MUL: {
                configRenderResMul = Config.parseFloat(string, 1.0f);
                break;
            }
            case SHADOW_RES_MUL: {
                configShadowResMul = Config.parseFloat(string, 1.0f);
                break;
            }
            case HAND_DEPTH_MUL: {
                configHandDepthMul = Config.parseFloat(string, 0.125f);
                break;
            }
            case CLOUD_SHADOW: {
                configCloudShadow = Config.parseBoolean(string, true);
                break;
            }
            case OLD_HAND_LIGHT: {
                configOldHandLight.setPropertyValue(string);
                break;
            }
            case OLD_LIGHTING: {
                configOldLighting.setPropertyValue(string);
                break;
            }
            case SHADER_PACK: {
                currentShaderName = string;
                break;
            }
            case TWEAK_BLOCK_DAMAGE: {
                configTweakBlockDamage = Config.parseBoolean(string, true);
                break;
            }
            case SHADOW_CLIP_FRUSTRUM: {
                configShadowClipFrustrum = Config.parseBoolean(string, true);
                break;
            }
            case TEX_MIN_FIL_B: {
                configTexMinFilB = Config.parseInt(string, 0);
                break;
            }
            case TEX_MIN_FIL_N: {
                configTexMinFilN = Config.parseInt(string, 0);
                break;
            }
            case TEX_MIN_FIL_S: {
                configTexMinFilS = Config.parseInt(string, 0);
                break;
            }
            case TEX_MAG_FIL_B: {
                configTexMagFilB = Config.parseInt(string, 0);
                break;
            }
            case TEX_MAG_FIL_N: {
                configTexMagFilB = Config.parseInt(string, 0);
                break;
            }
            case TEX_MAG_FIL_S: {
                configTexMagFilB = Config.parseInt(string, 0);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
            }
        }
    }

    public static void storeConfig() {
        SMCLog.info("Save shaders configuration.");
        if (shadersConfig == null) {
            shadersConfig = new PropertiesOrdered();
        }
        EnumShaderOption[] enumShaderOptionArray = EnumShaderOption.values();
        for (int i = 0; i < enumShaderOptionArray.length; ++i) {
            EnumShaderOption enumShaderOption = enumShaderOptionArray[i];
            String string = enumShaderOption.getPropertyKey();
            String string2 = Shaders.getEnumShaderOption(enumShaderOption);
            shadersConfig.setProperty(string, string2);
        }
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            shadersConfig.store(fileWriter, (String)null);
            fileWriter.close();
        } catch (Exception exception) {
            SMCLog.severe("Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    public static String getEnumShaderOption(EnumShaderOption enumShaderOption) {
        switch (enumShaderOption) {
            case ANTIALIASING: {
                return Integer.toString(configAntialiasingLevel);
            }
            case NORMAL_MAP: {
                return Boolean.toString(configNormalMap);
            }
            case SPECULAR_MAP: {
                return Boolean.toString(configSpecularMap);
            }
            case RENDER_RES_MUL: {
                return Float.toString(configRenderResMul);
            }
            case SHADOW_RES_MUL: {
                return Float.toString(configShadowResMul);
            }
            case HAND_DEPTH_MUL: {
                return Float.toString(configHandDepthMul);
            }
            case CLOUD_SHADOW: {
                return Boolean.toString(configCloudShadow);
            }
            case OLD_HAND_LIGHT: {
                return configOldHandLight.getPropertyValue();
            }
            case OLD_LIGHTING: {
                return configOldLighting.getPropertyValue();
            }
            case SHADER_PACK: {
                return currentShaderName;
            }
            case TWEAK_BLOCK_DAMAGE: {
                return Boolean.toString(configTweakBlockDamage);
            }
            case SHADOW_CLIP_FRUSTRUM: {
                return Boolean.toString(configShadowClipFrustrum);
            }
            case TEX_MIN_FIL_B: {
                return Integer.toString(configTexMinFilB);
            }
            case TEX_MIN_FIL_N: {
                return Integer.toString(configTexMinFilN);
            }
            case TEX_MIN_FIL_S: {
                return Integer.toString(configTexMinFilS);
            }
            case TEX_MAG_FIL_B: {
                return Integer.toString(configTexMagFilB);
            }
            case TEX_MAG_FIL_N: {
                return Integer.toString(configTexMagFilB);
            }
            case TEX_MAG_FIL_S: {
                return Integer.toString(configTexMagFilB);
            }
        }
        throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
    }

    public static void setShaderPack(String string) {
        currentShaderName = string;
        shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), string);
        Shaders.loadShaderPack();
    }

    public static void loadShaderPack() {
        boolean bl;
        mc = Minecraft.getInstance();
        boolean bl2 = shaderPackLoaded;
        boolean bl3 = Shaders.isOldLighting();
        if (Shaders.mc.worldRenderer != null) {
            Shaders.mc.worldRenderer.pauseChunkUpdates();
        }
        shaderPackLoaded = false;
        if (shaderPack != null) {
            shaderPack.close();
            shaderPack = null;
            shaderPackResources.clear();
            shaderPackDimensions.clear();
            shaderPackOptions = null;
            shaderPackOptionSliders = null;
            shaderPackProfiles = null;
            shaderPackGuiScreens = null;
            shaderPackProgramConditions.clear();
            shaderPackClouds.resetValue();
            shaderPackOldHandLight.resetValue();
            shaderPackDynamicHandLight.resetValue();
            shaderPackOldLighting.resetValue();
            Shaders.resetCustomTextures();
            noiseTexturePath = null;
        }
        boolean bl4 = false;
        if (Config.isAntialiasing()) {
            SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
            bl4 = true;
        }
        if (Config.isGraphicsFabulous()) {
            SMCLog.info("Shaders can not be loaded, Fabulous Graphics is enabled.");
            bl4 = true;
        }
        String string = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), SHADER_PACK_NAME_DEFAULT);
        if (!bl4) {
            shaderPack = Shaders.getShaderPack(string);
            boolean bl5 = shaderPackLoaded = shaderPack != null;
        }
        if (shaderPackLoaded) {
            SMCLog.info("Loaded shaderpack: " + Shaders.getShaderPackName());
        } else {
            SMCLog.info("No shaderpack loaded.");
            shaderPack = new ShaderPackNone();
        }
        if (saveFinalShaders) {
            Shaders.clearDirectory(new File(shaderPacksDir, "debug"));
        }
        Shaders.loadShaderPackResources();
        Shaders.loadShaderPackDimensions();
        shaderPackOptions = Shaders.loadShaderPackOptions();
        Shaders.loadShaderPackFixedProperties();
        Shaders.loadShaderPackDynamicProperties();
        boolean bl6 = shaderPackLoaded != bl2;
        boolean bl7 = bl = Shaders.isOldLighting() != bl3;
        if (bl6 || bl) {
            DefaultVertexFormats.updateVertexFormats();
            if (Reflector.LightUtil.exists()) {
                Reflector.LightUtil_itemConsumer.setValue(null);
                Reflector.LightUtil_tessellator.setValue(null);
            }
            Shaders.updateBlockLightLevel();
        }
        if (mc.getResourceManager() != null) {
            CustomBlockLayers.update();
        }
        if (Shaders.mc.worldRenderer != null) {
            Shaders.mc.worldRenderer.resumeChunkUpdates();
        }
        if ((bl6 || bl) && mc.getResourceManager() != null) {
            mc.scheduleResourcesRefresh();
        }
    }

    public static IShaderPack getShaderPack(String string) {
        if (string == null) {
            return null;
        }
        if (!(string = string.trim()).isEmpty() && !string.equals(SHADER_PACK_NAME_NONE)) {
            if (string.equals(SHADER_PACK_NAME_DEFAULT)) {
                return new ShaderPackDefault();
            }
            try {
                File file = new File(shaderPacksDir, string);
                if (file.isDirectory()) {
                    return new ShaderPackFolder(string, file);
                }
                return file.isFile() && string.toLowerCase().endsWith(".zip") ? new ShaderPackZip(string, file) : null;
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static IShaderPack getShaderPack() {
        return shaderPack;
    }

    private static void loadShaderPackDimensions() {
        shaderPackDimensions.clear();
        for (int i = -128; i <= 128; ++i) {
            String string = "/shaders/world" + i;
            if (!shaderPack.hasDirectory(string)) continue;
            shaderPackDimensions.add(i);
        }
        if (shaderPackDimensions.size() > 0) {
            Integer[] integerArray = shaderPackDimensions.toArray(new Integer[shaderPackDimensions.size()]);
            Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])integerArray));
        }
    }

    private static void loadShaderPackFixedProperties() {
        shaderPackOldLighting.resetValue();
        shaderPackSeparateAo.resetValue();
        if (shaderPack != null) {
            String string = PATH_SHADERS_PROPERTIES;
            try {
                InputStream inputStream = shaderPack.getResourceAsStream(string);
                if (inputStream == null) {
                    return;
                }
                inputStream = MacroProcessor.process(inputStream, string, false);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                shaderPackOldLighting.loadFrom(propertiesOrdered);
                shaderPackSeparateAo.loadFrom(propertiesOrdered);
                shaderPackOptionSliders = ShaderPackParser.parseOptionSliders(propertiesOrdered, shaderPackOptions);
                shaderPackProfiles = ShaderPackParser.parseProfiles(propertiesOrdered, shaderPackOptions);
                shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(propertiesOrdered, shaderPackProfiles, shaderPackOptions);
            } catch (IOException iOException) {
                Config.warn("[Shaders] Error reading: " + string);
            }
        }
    }

    private static void loadShaderPackDynamicProperties() {
        Object object;
        shaderPackClouds.resetValue();
        shaderPackOldHandLight.resetValue();
        shaderPackDynamicHandLight.resetValue();
        shaderPackShadowTerrain.resetValue();
        shaderPackShadowTranslucent.resetValue();
        shaderPackShadowEntities.resetValue();
        shaderPackShadowBlockEntities.resetValue();
        shaderPackUnderwaterOverlay.resetValue();
        shaderPackSun.resetValue();
        shaderPackMoon.resetValue();
        shaderPackVignette.resetValue();
        shaderPackBackFaceSolid.resetValue();
        shaderPackBackFaceCutout.resetValue();
        shaderPackBackFaceCutoutMipped.resetValue();
        shaderPackBackFaceTranslucent.resetValue();
        shaderPackRainDepth.resetValue();
        shaderPackBeaconBeamDepth.resetValue();
        shaderPackFrustumCulling.resetValue();
        BlockAliases.reset();
        ItemAliases.reset();
        EntityAliases.reset();
        customUniforms = null;
        for (int i = 0; i < ProgramsAll.length; ++i) {
            object = ProgramsAll[i];
            ((Program)object).resetProperties();
        }
        Arrays.fill(colorBufferSizes, null);
        if (shaderPack != null) {
            BlockAliases.update(shaderPack);
            ItemAliases.update(shaderPack);
            EntityAliases.update(shaderPack);
            String string = PATH_SHADERS_PROPERTIES;
            try {
                object = shaderPack.getResourceAsStream(string);
                if (object == null) {
                    return;
                }
                object = MacroProcessor.process((InputStream)object, string, true);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load((InputStream)object);
                ((InputStream)object).close();
                shaderPackClouds.loadFrom(propertiesOrdered);
                shaderPackOldHandLight.loadFrom(propertiesOrdered);
                shaderPackDynamicHandLight.loadFrom(propertiesOrdered);
                shaderPackShadowTerrain.loadFrom(propertiesOrdered);
                shaderPackShadowTranslucent.loadFrom(propertiesOrdered);
                shaderPackShadowEntities.loadFrom(propertiesOrdered);
                shaderPackShadowBlockEntities.loadFrom(propertiesOrdered);
                shaderPackUnderwaterOverlay.loadFrom(propertiesOrdered);
                shaderPackSun.loadFrom(propertiesOrdered);
                shaderPackVignette.loadFrom(propertiesOrdered);
                shaderPackMoon.loadFrom(propertiesOrdered);
                shaderPackBackFaceSolid.loadFrom(propertiesOrdered);
                shaderPackBackFaceCutout.loadFrom(propertiesOrdered);
                shaderPackBackFaceCutoutMipped.loadFrom(propertiesOrdered);
                shaderPackBackFaceTranslucent.loadFrom(propertiesOrdered);
                shaderPackRainDepth.loadFrom(propertiesOrdered);
                shaderPackBeaconBeamDepth.loadFrom(propertiesOrdered);
                shaderPackFrustumCulling.loadFrom(propertiesOrdered);
                shaderPackProgramConditions = ShaderPackParser.parseProgramConditions(propertiesOrdered, shaderPackOptions);
                customTexturesGbuffers = Shaders.loadCustomTextures(propertiesOrdered, ProgramStage.GBUFFERS);
                customTexturesComposite = Shaders.loadCustomTextures(propertiesOrdered, ProgramStage.COMPOSITE);
                customTexturesDeferred = Shaders.loadCustomTextures(propertiesOrdered, ProgramStage.DEFERRED);
                customTexturesShadowcomp = Shaders.loadCustomTextures(propertiesOrdered, ProgramStage.SHADOWCOMP);
                customTexturesPrepare = Shaders.loadCustomTextures(propertiesOrdered, ProgramStage.PREPARE);
                noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
                if (noiseTexturePath != null) {
                    noiseTextureEnabled = true;
                }
                customUniforms = ShaderPackParser.parseCustomUniforms(propertiesOrdered);
                ShaderPackParser.parseAlphaStates(propertiesOrdered);
                ShaderPackParser.parseBlendStates(propertiesOrdered);
                ShaderPackParser.parseRenderScales(propertiesOrdered);
                ShaderPackParser.parseBuffersFlip(propertiesOrdered);
                colorBufferSizes = ShaderPackParser.parseBufferSizes(propertiesOrdered, 16);
            } catch (IOException iOException) {
                Config.warn("[Shaders] Error reading: " + string);
            }
        }
    }

    private static ICustomTexture[] loadCustomTextures(Properties properties, ProgramStage programStage) {
        String string = "texture." + programStage.getName() + ".";
        Set set = properties.keySet();
        ArrayList<ICustomTexture> arrayList = new ArrayList<ICustomTexture>();
        for (String string2 : set) {
            if (!string2.startsWith(string)) continue;
            String string3 = StrUtils.removePrefix(string2, string);
            string3 = StrUtils.removeSuffix(string3, new String[]{".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"});
            String string4 = properties.getProperty(string2).trim();
            int n = Shaders.getTextureIndex(programStage, string3);
            if (n < 0) {
                SMCLog.warning("Invalid texture name: " + string2);
                continue;
            }
            ICustomTexture iCustomTexture = Shaders.loadCustomTexture(n, string4);
            if (iCustomTexture == null) continue;
            SMCLog.info("Custom texture: " + string2 + " = " + string4);
            arrayList.add(iCustomTexture);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        ICustomTexture[] iCustomTextureArray = arrayList.toArray(new ICustomTexture[arrayList.size()]);
        return iCustomTextureArray;
    }

    private static ICustomTexture loadCustomTexture(int n, String string) {
        if (string == null) {
            return null;
        }
        if ((string = string.trim()).indexOf(58) >= 0) {
            return Shaders.loadCustomTextureLocation(n, string);
        }
        return string.indexOf(32) >= 0 ? Shaders.loadCustomTextureRaw(n, string) : Shaders.loadCustomTextureShaders(n, string);
    }

    private static ICustomTexture loadCustomTextureLocation(int n, String string) {
        String string2 = string.trim();
        int n2 = 0;
        if (string2.startsWith("minecraft:textures/")) {
            if ((string2 = StrUtils.addSuffixCheck(string2, ".png")).endsWith("_n.png")) {
                string2 = StrUtils.replaceSuffix(string2, "_n.png", ".png");
                n2 = 1;
            } else if (string2.endsWith("_s.png")) {
                string2 = StrUtils.replaceSuffix(string2, "_s.png", ".png");
                n2 = 2;
            }
        }
        if (string2.startsWith("minecraft:dynamic/lightmap_")) {
            string2 = string2.replace("lightmap", "light_map");
        }
        ResourceLocation resourceLocation = new ResourceLocation(string2);
        return new CustomTextureLocation(n, resourceLocation, n2);
    }

    private static void reloadCustomTexturesLocation(ICustomTexture[] iCustomTextureArray) {
        if (iCustomTextureArray != null) {
            for (int i = 0; i < iCustomTextureArray.length; ++i) {
                ICustomTexture iCustomTexture = iCustomTextureArray[i];
                if (!(iCustomTexture instanceof CustomTextureLocation)) continue;
                CustomTextureLocation customTextureLocation = (CustomTextureLocation)iCustomTexture;
                customTextureLocation.reloadTexture();
            }
        }
    }

    private static ICustomTexture loadCustomTextureRaw(int n, String string) {
        ConnectedParser connectedParser = new ConnectedParser("Shaders");
        String[] stringArray = Config.tokenize(string, " ");
        ArrayDeque<String> arrayDeque = new ArrayDeque<String>(Arrays.asList(stringArray));
        String string2 = (String)arrayDeque.poll();
        TextureType textureType = (TextureType)connectedParser.parseEnum((String)arrayDeque.poll(), TextureType.values(), "texture type");
        if (textureType == null) {
            SMCLog.warning("Invalid raw texture type: " + string);
            return null;
        }
        InternalFormat internalFormat = (InternalFormat)connectedParser.parseEnum((String)arrayDeque.poll(), InternalFormat.values(), "internal format");
        if (internalFormat == null) {
            SMCLog.warning("Invalid raw texture internal format: " + string);
            return null;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        switch (textureType) {
            case TEXTURE_1D: {
                n2 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                break;
            }
            case TEXTURE_2D: {
                n2 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                n3 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                break;
            }
            case TEXTURE_3D: {
                n2 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                n3 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                n4 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                break;
            }
            case TEXTURE_RECTANGLE: {
                n2 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                n3 = connectedParser.parseInt((String)arrayDeque.poll(), -1);
                break;
            }
            default: {
                SMCLog.warning("Invalid raw texture type: " + textureType);
                return null;
            }
        }
        if (n2 >= 0 && n3 >= 0 && n4 >= 0) {
            PixelFormat pixelFormat = (PixelFormat)connectedParser.parseEnum((String)arrayDeque.poll(), PixelFormat.values(), "pixel format");
            if (pixelFormat == null) {
                SMCLog.warning("Invalid raw texture pixel format: " + string);
                return null;
            }
            PixelType pixelType = (PixelType)connectedParser.parseEnum((String)arrayDeque.poll(), PixelType.values(), "pixel type");
            if (pixelType == null) {
                SMCLog.warning("Invalid raw texture pixel type: " + string);
                return null;
            }
            if (!arrayDeque.isEmpty()) {
                SMCLog.warning("Invalid raw texture, too many parameters: " + string);
                return null;
            }
            return Shaders.loadCustomTextureRaw(n, string, string2, textureType, internalFormat, n2, n3, n4, pixelFormat, pixelType);
        }
        SMCLog.warning("Invalid raw texture size: " + string);
        return null;
    }

    private static ICustomTexture loadCustomTextureRaw(int n, String string, String string2, TextureType textureType, InternalFormat internalFormat, int n2, int n3, int n4, PixelFormat pixelFormat, PixelType pixelType) {
        try {
            String string3 = "shaders/" + StrUtils.removePrefix(string2, "/");
            InputStream inputStream = shaderPack.getResourceAsStream(string3);
            if (inputStream == null) {
                SMCLog.warning("Raw texture not found: " + string2);
                return null;
            }
            byte[] byArray = Config.readAll(inputStream);
            IOUtils.closeQuietly(inputStream);
            ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(byArray.length);
            byteBuffer.put(byArray);
            byteBuffer.flip();
            TextureMetadataSection textureMetadataSection = SimpleShaderTexture.loadTextureMetadataSection(string3, new TextureMetadataSection(true, true));
            return new CustomTextureRaw(textureType, internalFormat, n2, n3, n4, pixelFormat, pixelType, byteBuffer, n, textureMetadataSection.getTextureBlur(), textureMetadataSection.getTextureClamp());
        } catch (IOException iOException) {
            SMCLog.warning("Error loading raw texture: " + string2);
            SMCLog.warning(iOException.getClass().getName() + ": " + iOException.getMessage());
            return null;
        }
    }

    private static ICustomTexture loadCustomTextureShaders(int n, String object) {
        if (((String)(object = ((String)object).trim())).indexOf(46) < 0) {
            object = (String)object + ".png";
        }
        try {
            String string = "shaders/" + StrUtils.removePrefix((String)object, "/");
            InputStream inputStream = shaderPack.getResourceAsStream(string);
            if (inputStream == null) {
                SMCLog.warning("Texture not found: " + (String)object);
                return null;
            }
            IOUtils.closeQuietly(inputStream);
            SimpleShaderTexture simpleShaderTexture = new SimpleShaderTexture(string);
            simpleShaderTexture.loadTexture(mc.getResourceManager());
            return new CustomTexture(n, string, simpleShaderTexture);
        } catch (IOException iOException) {
            SMCLog.warning("Error loading texture: " + (String)object);
            SMCLog.warning(iOException.getClass().getName() + ": " + iOException.getMessage());
            return null;
        }
    }

    private static int getTextureIndex(ProgramStage programStage, String string) {
        int n;
        if (programStage == ProgramStage.GBUFFERS) {
            n = ShaderParser.getIndex(string, "colortex", 4, 15);
            if (n >= 0) {
                return colorTextureImageUnit[n];
            }
            if (string.equals("texture")) {
                return 1;
            }
            if (string.equals("lightmap")) {
                return 0;
            }
            if (string.equals("normals")) {
                return 1;
            }
            if (string.equals("specular")) {
                return 0;
            }
            if (string.equals("shadowtex0") || string.equals("watershadow")) {
                return 1;
            }
            if (string.equals("shadow")) {
                return waterShadowEnabled ? 5 : 4;
            }
            if (string.equals("shadowtex1")) {
                return 0;
            }
            if (string.equals("depthtex0")) {
                return 1;
            }
            if (string.equals("gaux1")) {
                return 0;
            }
            if (string.equals("gaux2")) {
                return 1;
            }
            if (string.equals("gaux3")) {
                return 0;
            }
            if (string.equals("gaux4")) {
                return 1;
            }
            if (string.equals("depthtex1")) {
                return 1;
            }
            if (string.equals("shadowcolor0") || string.equals("shadowcolor")) {
                return 0;
            }
            if (string.equals("shadowcolor1")) {
                return 1;
            }
            if (string.equals("noisetex")) {
                return 0;
            }
        }
        if (programStage.isAnyComposite()) {
            n = ShaderParser.getIndex(string, "colortex", 0, 15);
            if (n >= 0) {
                return colorTextureImageUnit[n];
            }
            if (string.equals("colortex0")) {
                return 1;
            }
            if (string.equals("gdepth")) {
                return 0;
            }
            if (string.equals("gnormal")) {
                return 1;
            }
            if (string.equals("composite")) {
                return 0;
            }
            if (string.equals("shadowtex0") || string.equals("watershadow")) {
                return 1;
            }
            if (string.equals("shadow")) {
                return waterShadowEnabled ? 5 : 4;
            }
            if (string.equals("shadowtex1")) {
                return 0;
            }
            if (string.equals("depthtex0") || string.equals("gdepthtex")) {
                return 1;
            }
            if (string.equals("gaux1")) {
                return 0;
            }
            if (string.equals("gaux2")) {
                return 1;
            }
            if (string.equals("gaux3")) {
                return 0;
            }
            if (string.equals("gaux4")) {
                return 1;
            }
            if (string.equals("depthtex1")) {
                return 0;
            }
            if (string.equals("depthtex2")) {
                return 1;
            }
            if (string.equals("shadowcolor0") || string.equals("shadowcolor")) {
                return 0;
            }
            if (string.equals("shadowcolor1")) {
                return 1;
            }
            if (string.equals("noisetex")) {
                return 0;
            }
        }
        return 1;
    }

    private static void bindCustomTextures(ICustomTexture[] iCustomTextureArray) {
        if (iCustomTextureArray != null) {
            for (int i = 0; i < iCustomTextureArray.length; ++i) {
                ICustomTexture iCustomTexture = iCustomTextureArray[i];
                GlStateManager.activeTexture(33984 + iCustomTexture.getTextureUnit());
                int n = iCustomTexture.getTextureId();
                int n2 = iCustomTexture.getTarget();
                if (n2 == 3553) {
                    GlStateManager.bindTexture(n);
                    continue;
                }
                GL11.glBindTexture(n2, n);
            }
            GlStateManager.activeTexture(33984);
        }
    }

    private static void resetCustomTextures() {
        Shaders.deleteCustomTextures(customTexturesGbuffers);
        Shaders.deleteCustomTextures(customTexturesComposite);
        Shaders.deleteCustomTextures(customTexturesDeferred);
        Shaders.deleteCustomTextures(customTexturesShadowcomp);
        Shaders.deleteCustomTextures(customTexturesPrepare);
        customTexturesGbuffers = null;
        customTexturesComposite = null;
        customTexturesDeferred = null;
        customTexturesShadowcomp = null;
        customTexturesPrepare = null;
    }

    private static void deleteCustomTextures(ICustomTexture[] iCustomTextureArray) {
        if (iCustomTextureArray != null) {
            for (int i = 0; i < iCustomTextureArray.length; ++i) {
                ICustomTexture iCustomTexture = iCustomTextureArray[i];
                iCustomTexture.deleteTexture();
            }
        }
    }

    public static ShaderOption[] getShaderPackOptions(String string) {
        Object[] objectArray = (ShaderOption[])shaderPackOptions.clone();
        if (shaderPackGuiScreens == null) {
            if (shaderPackProfiles != null) {
                ShaderOptionProfile shaderOptionProfile = new ShaderOptionProfile(shaderPackProfiles, (ShaderOption[])objectArray);
                objectArray = (ShaderOption[])Config.addObjectToArray(objectArray, shaderOptionProfile, 0);
            }
            return Shaders.getVisibleOptions((ShaderOption[])objectArray);
        }
        Object object = string != null ? "screen." + string : "screen";
        ScreenShaderOptions screenShaderOptions = shaderPackGuiScreens.get(object);
        if (screenShaderOptions == null) {
            return new ShaderOption[0];
        }
        ShaderOption[] shaderOptionArray = screenShaderOptions.getShaderOptions();
        ArrayList<ShaderOption> arrayList = new ArrayList<ShaderOption>();
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (shaderOption == null) {
                arrayList.add(null);
                continue;
            }
            if (shaderOption instanceof ShaderOptionRest) {
                ShaderOption[] shaderOptionArray2 = Shaders.getShaderOptionsRest(shaderPackGuiScreens, (ShaderOption[])objectArray);
                arrayList.addAll(Arrays.asList(shaderOptionArray2));
                continue;
            }
            arrayList.add(shaderOption);
        }
        return arrayList.toArray(new ShaderOption[arrayList.size()]);
    }

    public static int getShaderPackColumns(String string, int n) {
        Object object;
        Object object2 = object = string != null ? "screen." + string : "screen";
        if (shaderPackGuiScreens == null) {
            return n;
        }
        ScreenShaderOptions screenShaderOptions = shaderPackGuiScreens.get(object);
        return screenShaderOptions == null ? n : screenShaderOptions.getColumns();
    }

    private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> map, ShaderOption[] shaderOptionArray) {
        Object object;
        Object object2;
        HashSet<String> hashSet = new HashSet<String>();
        for (String string : map.keySet()) {
            object2 = map.get(string);
            object = ((ScreenShaderOptions)object2).getShaderOptions();
            for (int i = 0; i < ((ShaderOption[])object).length; ++i) {
                ShaderOption shaderOption = object[i];
                if (shaderOption == null) continue;
                hashSet.add(shaderOption.getName());
            }
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            object2 = shaderOptionArray[i];
            if (!((ShaderOption)object2).isVisible() || hashSet.contains(object = ((ShaderOption)object2).getName())) continue;
            arrayList.add(object2);
        }
        return arrayList.toArray(new ShaderOption[arrayList.size()]);
    }

    public static ShaderOption getShaderOption(String string) {
        return ShaderUtils.getShaderOption(string, shaderPackOptions);
    }

    public static ShaderOption[] getShaderPackOptions() {
        return shaderPackOptions;
    }

    public static boolean isShaderPackOptionSlider(String string) {
        return shaderPackOptionSliders == null ? false : shaderPackOptionSliders.contains(string);
    }

    private static ShaderOption[] getVisibleOptions(ShaderOption[] shaderOptionArray) {
        ArrayList<ShaderOption> arrayList = new ArrayList<ShaderOption>();
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (!shaderOption.isVisible()) continue;
            arrayList.add(shaderOption);
        }
        return arrayList.toArray(new ShaderOption[arrayList.size()]);
    }

    public static void saveShaderPackOptions() {
        Shaders.saveShaderPackOptions(shaderPackOptions, shaderPack);
    }

    private static void saveShaderPackOptions(ShaderOption[] shaderOptionArray, IShaderPack iShaderPack) {
        PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
        if (shaderPackOptions != null) {
            for (int i = 0; i < shaderOptionArray.length; ++i) {
                ShaderOption shaderOption = shaderOptionArray[i];
                if (!shaderOption.isChanged() || !shaderOption.isEnabled()) continue;
                propertiesOrdered.setProperty(shaderOption.getName(), shaderOption.getValue());
            }
        }
        try {
            Shaders.saveOptionProperties(iShaderPack, propertiesOrdered);
        } catch (IOException iOException) {
            Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
            iOException.printStackTrace();
        }
    }

    private static void saveOptionProperties(IShaderPack iShaderPack, Properties properties) throws IOException {
        String string = "shaderpacks/" + iShaderPack.getName() + ".txt";
        File file = new File(Minecraft.getInstance().gameDir, string);
        if (properties.isEmpty()) {
            file.delete();
        } else {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, (String)null);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }

    private static ShaderOption[] loadShaderPackOptions() {
        try {
            String[] stringArray = programs.getProgramNames();
            Properties properties = Shaders.loadOptionProperties(shaderPack);
            ShaderOption[] shaderOptionArray = ShaderPackParser.parseShaderPackOptions(shaderPack, stringArray, shaderPackDimensions);
            for (int i = 0; i < shaderOptionArray.length; ++i) {
                ShaderOption shaderOption = shaderOptionArray[i];
                String string = properties.getProperty(shaderOption.getName());
                if (string == null) continue;
                shaderOption.resetValue();
                if (shaderOption.setValue(string)) continue;
                Config.warn("[Shaders] Invalid value, option: " + shaderOption.getName() + ", value: " + string);
            }
            return shaderOptionArray;
        } catch (IOException iOException) {
            Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
            iOException.printStackTrace();
            return null;
        }
    }

    private static Properties loadOptionProperties(IShaderPack iShaderPack) throws IOException {
        PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
        String string = "shaderpacks/" + iShaderPack.getName() + ".txt";
        File file = new File(Minecraft.getInstance().gameDir, string);
        if (file.exists() && file.isFile() && file.canRead()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            propertiesOrdered.load(fileInputStream);
            fileInputStream.close();
            return propertiesOrdered;
        }
        return propertiesOrdered;
    }

    public static ShaderOption[] getChangedOptions(ShaderOption[] shaderOptionArray) {
        ArrayList<ShaderOption> arrayList = new ArrayList<ShaderOption>();
        for (int i = 0; i < shaderOptionArray.length; ++i) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (!shaderOption.isEnabled() || !shaderOption.isChanged()) continue;
            arrayList.add(shaderOption);
        }
        return arrayList.toArray(new ShaderOption[arrayList.size()]);
    }

    private static String applyOptions(String string, ShaderOption[] shaderOptionArray) {
        if (shaderOptionArray != null && shaderOptionArray.length > 0) {
            for (int i = 0; i < shaderOptionArray.length; ++i) {
                ShaderOption shaderOption = shaderOptionArray[i];
                if (!shaderOption.matchesLine(string)) continue;
                string = shaderOption.getSourceLine();
                break;
            }
            return string;
        }
        return string;
    }

    public static ArrayList listOfShaders() {
        Object object;
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        try {
            if (!shaderPacksDir.exists()) {
                shaderPacksDir.mkdir();
            }
            object = shaderPacksDir.listFiles();
            for (int i = 0; i < ((File[])object).length; ++i) {
                File file = object[i];
                String string = file.getName();
                if (file.isDirectory()) {
                    File file2;
                    if (string.equals("debug") || !(file2 = new File(file, "shaders")).exists() || !file2.isDirectory()) continue;
                    arrayList.add(string);
                    continue;
                }
                if (!file.isFile() || !string.toLowerCase().endsWith(".zip")) continue;
                arrayList2.add(string);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        Collections.sort(arrayList, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(arrayList2, String.CASE_INSENSITIVE_ORDER);
        object = new ArrayList();
        ((ArrayList)object).add(SHADER_PACK_NAME_NONE);
        ((ArrayList)object).add(SHADER_PACK_NAME_DEFAULT);
        ((ArrayList)object).addAll(arrayList);
        ((ArrayList)object).addAll(arrayList2);
        return object;
    }

    public static int checkFramebufferStatus(String string) {
        int n = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (n != 36053) {
            SMCLog.severe("FramebufferStatus 0x%04X at '%s'", n, string);
        }
        return n;
    }

    public static int checkGLError(String string) {
        int n = GlStateManager.getError();
        if (n != 0 && GlErrors.isEnabled(n)) {
            String string2 = Config.getGlErrorString(n);
            String string3 = Shaders.getErrorInfo(n, string);
            String string4 = String.format("OpenGL error: %s (%s)%s, at: %s", n, string2, string3, string);
            SMCLog.severe(string4);
            if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
                String string5 = I18n.format("of.message.openglError", n, string2);
                Shaders.printChat(string5);
            }
        }
        return n;
    }

    private static String getErrorInfo(int n, String string) {
        String string2;
        Object object;
        Object object2;
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 1286) {
            int n2 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            object2 = Shaders.getFramebufferStatusText(n2);
            object = ", fbStatus: " + n2 + " (" + (String)object2 + ")";
            stringBuilder.append((String)object);
        }
        if ((string2 = activeProgram.getName()).isEmpty()) {
            string2 = "none";
        }
        stringBuilder.append(", program: " + string2);
        object2 = Shaders.getProgramById(activeProgramID);
        if (object2 != activeProgram) {
            object = ((Program)object2).getName();
            if (((String)object).isEmpty()) {
                object = "none";
            }
            stringBuilder.append(" (" + (String)object + ")");
        }
        if (string.equals("setDrawBuffers")) {
            stringBuilder.append(", drawBuffers: " + ArrayUtils.arrayToString(activeProgram.getDrawBufSettings()));
        }
        return stringBuilder.toString();
    }

    private static Program getProgramById(int n) {
        for (int i = 0; i < ProgramsAll.length; ++i) {
            Program program = ProgramsAll[i];
            if (program.getId() != n) continue;
            return program;
        }
        return ProgramNone;
    }

    private static String getFramebufferStatusText(int n) {
        switch (n) {
            case 33305: {
                return "Undefined";
            }
            case 36053: {
                return "Complete";
            }
            case 36054: {
                return "Incomplete attachment";
            }
            case 36055: {
                return "Incomplete missing attachment";
            }
            case 36059: {
                return "Incomplete draw buffer";
            }
            case 36060: {
                return "Incomplete read buffer";
            }
            case 36061: {
                return "Unsupported";
            }
            case 36182: {
                return "Incomplete multisample";
            }
            case 36264: {
                return "Incomplete layer targets";
            }
        }
        return "Unknown";
    }

    private static void printChat(String string) {
        Shaders.mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(string));
    }

    public static void printChatAndLogError(String string) {
        SMCLog.severe(string);
        Shaders.mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(string));
    }

    public static void printIntBuffer(String string, IntBuffer intBuffer) {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(string).append(" [pos ").append(intBuffer.position()).append(" lim ").append(intBuffer.limit()).append(" cap ").append(intBuffer.capacity()).append(" :");
        int n = intBuffer.limit();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(" ").append(intBuffer.get(i));
        }
        stringBuilder.append("]");
        SMCLog.info(stringBuilder.toString());
    }

    public static void startup(Minecraft minecraft) {
        Shaders.checkShadersModInstalled();
        mc = minecraft;
        minecraft = Minecraft.getInstance();
        capabilities = GL.getCapabilities();
        glVersionString = GL11.glGetString(7938);
        glVendorString = GL11.glGetString(7936);
        glRendererString = GL11.glGetString(7937);
        SMCLog.info("OpenGL Version: " + glVersionString);
        SMCLog.info("Vendor:  " + glVendorString);
        SMCLog.info("Renderer: " + glRendererString);
        SMCLog.info("Capabilities: " + (Shaders.capabilities.OpenGL20 ? " 2.0 " : " - ") + (Shaders.capabilities.OpenGL21 ? " 2.1 " : " - ") + (Shaders.capabilities.OpenGL30 ? " 3.0 " : " - ") + (Shaders.capabilities.OpenGL32 ? " 3.2 " : " - ") + (Shaders.capabilities.OpenGL40 ? " 4.0 " : " - "));
        SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL43.glGetInteger(34852));
        SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL43.glGetInteger(36063));
        SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL43.glGetInteger(34930));
        hasGlGenMipmap = Shaders.capabilities.OpenGL30;
        boolean bl = glDebugGroups = Boolean.getBoolean("gl.debug.groups") && Shaders.capabilities.GL_KHR_debug;
        if (glDebugGroups) {
            SMCLog.info("glDebugGroups: true");
        }
        Shaders.loadConfig();
    }

    public static void updateBlockLightLevel() {
        if (Shaders.isOldLighting()) {
            blockLightLevel05 = 0.5f;
            blockLightLevel06 = 0.6f;
            blockLightLevel08 = 0.8f;
        } else {
            blockLightLevel05 = 1.0f;
            blockLightLevel06 = 1.0f;
            blockLightLevel08 = 1.0f;
        }
    }

    public static boolean isOldHandLight() {
        if (!configOldHandLight.isDefault()) {
            return configOldHandLight.isTrue();
        }
        return !shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true;
    }

    public static boolean isDynamicHandLight() {
        return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
    }

    public static boolean isOldLighting() {
        if (!configOldLighting.isDefault()) {
            return configOldLighting.isTrue();
        }
        return !shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true;
    }

    public static boolean isRenderShadowTerrain() {
        return !shaderPackShadowTerrain.isFalse();
    }

    public static boolean isRenderShadowTranslucent() {
        return !shaderPackShadowTranslucent.isFalse();
    }

    public static boolean isRenderShadowEntities() {
        return !shaderPackShadowEntities.isFalse();
    }

    public static boolean isRenderShadowBlockEntities() {
        return !shaderPackShadowBlockEntities.isFalse();
    }

    public static boolean isUnderwaterOverlay() {
        return !shaderPackUnderwaterOverlay.isFalse();
    }

    public static boolean isSun() {
        return !shaderPackSun.isFalse();
    }

    public static boolean isMoon() {
        return !shaderPackMoon.isFalse();
    }

    public static boolean isVignette() {
        return !shaderPackVignette.isFalse();
    }

    public static boolean isRenderBackFace(RenderType renderType) {
        if (renderType == RenderTypes.SOLID) {
            return shaderPackBackFaceSolid.isTrue();
        }
        if (renderType == RenderTypes.CUTOUT) {
            return shaderPackBackFaceCutout.isTrue();
        }
        if (renderType == RenderTypes.CUTOUT_MIPPED) {
            return shaderPackBackFaceCutoutMipped.isTrue();
        }
        return renderType == RenderTypes.TRANSLUCENT ? shaderPackBackFaceTranslucent.isTrue() : false;
    }

    public static boolean isRainDepth() {
        return shaderPackRainDepth.isTrue();
    }

    public static boolean isBeaconBeamDepth() {
        return shaderPackBeaconBeamDepth.isTrue();
    }

    public static boolean isSeparateAo() {
        return shaderPackSeparateAo.isTrue();
    }

    public static boolean isFrustumCulling() {
        return !shaderPackFrustumCulling.isFalse();
    }

    public static void init() {
        boolean bl;
        if (!isInitializedOnce) {
            isInitializedOnce = true;
            bl = true;
        } else {
            bl = false;
        }
        if (!isShaderPackInitialized) {
            int n;
            Object object;
            Object object2;
            int n2;
            Shaders.checkGLError("Shaders.init pre");
            if (Shaders.getShaderPackName() != null) {
                // empty if block
            }
            if (!Shaders.capabilities.OpenGL20) {
                Shaders.printChatAndLogError("No OpenGL 2.0");
            }
            if (!Shaders.capabilities.GL_EXT_framebuffer_object) {
                Shaders.printChatAndLogError("No EXT_framebuffer_object");
            }
            dfbDrawBuffers.position(0).limit(8);
            sfbDrawBuffers.position(0).limit(8);
            usedColorBuffers = 4;
            usedDepthBuffers = 1;
            usedShadowColorBuffers = 0;
            usedShadowDepthBuffers = 0;
            usedColorAttachs = 1;
            usedDrawBuffers = 1;
            bindImageTextures = false;
            Arrays.fill(gbuffersFormat, 6408);
            Arrays.fill(gbuffersClear, true);
            Arrays.fill(gbuffersClearColor, null);
            Arrays.fill(shadowBuffersFormat, 6408);
            Arrays.fill(shadowBuffersClear, true);
            Arrays.fill(shadowBuffersClearColor, null);
            Arrays.fill(shadowHardwareFilteringEnabled, false);
            Arrays.fill(shadowMipmapEnabled, false);
            Arrays.fill(shadowFilterNearest, false);
            Arrays.fill(shadowColorMipmapEnabled, false);
            Arrays.fill(shadowColorFilterNearest, false);
            centerDepthSmoothEnabled = false;
            noiseTextureEnabled = false;
            sunPathRotation = 0.0f;
            shadowIntervalSize = 2.0f;
            shadowMapWidth = 1024;
            shadowMapHeight = 1024;
            spShadowMapWidth = 1024;
            spShadowMapHeight = 1024;
            shadowMapFOV = 90.0f;
            shadowMapHalfPlane = 160.0f;
            shadowMapIsOrtho = true;
            shadowDistanceRenderMul = -1.0f;
            aoLevel = -1.0f;
            useEntityAttrib = false;
            useMidTexCoordAttrib = false;
            useTangentAttrib = false;
            useVelocityAttrib = false;
            waterShadowEnabled = false;
            hasGeometryShaders = false;
            Shaders.updateBlockLightLevel();
            Smoother.resetValues();
            shaderUniforms.reset();
            if (customUniforms != null) {
                customUniforms.reset();
            }
            ShaderProfile shaderProfile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
            Object object3 = "";
            if (currentWorld != null && shaderPackDimensions.contains(n2 = WorldUtils.getDimensionId(currentWorld.getDimensionKey()))) {
                object3 = "world" + n2 + "/";
            }
            Shaders.loadShaderPackDynamicProperties();
            for (n2 = 0; n2 < ProgramsAll.length; ++n2) {
                Program program = ProgramsAll[n2];
                program.resetId();
                program.resetConfiguration();
                if (program.getProgramStage() == ProgramStage.NONE) continue;
                object2 = program.getName();
                object = (String)object3 + (String)object2;
                boolean bl2 = true;
                if (shaderPackProgramConditions.containsKey(object)) {
                    boolean bl3 = bl2 = bl2 && shaderPackProgramConditions.get(object).eval();
                }
                if (shaderProfile != null) {
                    boolean bl4 = bl2 = bl2 && !shaderProfile.isProgramDisabled((String)object);
                }
                if (!bl2) {
                    SMCLog.info("Program disabled: " + (String)object);
                    object2 = "<disabled>";
                    object = (String)object3 + (String)object2;
                }
                String string = "/shaders/" + (String)object;
                String string2 = string + ".vsh";
                String string3 = string + ".gsh";
                String string4 = string + ".fsh";
                ComputeProgram[] computeProgramArray = Shaders.setupComputePrograms(program, "/shaders/", (String)object, ".csh");
                program.setComputePrograms(computeProgramArray);
                Config.sleep(10L);
                Shaders.setupProgram(program, string2, string3, string4);
                int n3 = program.getId();
                if (n3 > 0) {
                    SMCLog.info("Program loaded: " + (String)object);
                }
                Shaders.initDrawBuffers(program);
                Shaders.initBlendStatesIndexed(program);
                Shaders.updateToggleBuffers(program);
                Shaders.updateProgramSize(program);
            }
            hasDeferredPrograms = ProgramUtils.hasActive(ProgramsDeferred);
            hasShadowcompPrograms = ProgramUtils.hasActive(ProgramsShadowcomp);
            hasPreparePrograms = ProgramUtils.hasActive(ProgramsPrepare);
            usedColorAttachs = usedColorBuffers;
            if (usedShadowDepthBuffers > 0 || usedShadowColorBuffers > 0) {
                hasShadowMap = true;
                usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, 1);
            }
            shouldSkipDefaultShadow = hasShadowMap;
            SMCLog.info("usedColorBuffers: " + usedColorBuffers);
            SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
            SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
            SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
            SMCLog.info("usedColorAttachs: " + usedColorAttachs);
            SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
            SMCLog.info("bindImageTextures: " + bindImageTextures);
            n2 = GL43.glGetInteger(34852);
            if (usedDrawBuffers > n2) {
                Shaders.printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + n2);
                usedDrawBuffers = n2;
            }
            dfbDrawBuffers.position(0).limit(usedDrawBuffers);
            for (n = 0; n < usedDrawBuffers; ++n) {
                dfbDrawBuffers.put(n, 36064 + n);
            }
            sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
            for (n = 0; n < usedShadowColorBuffers; ++n) {
                sfbDrawBuffers.put(n, 36064 + n);
            }
            for (n = 0; n < ProgramsAll.length; ++n) {
                for (object = object2 = ProgramsAll[n]; ((Program)object).getId() == 0 && ((Program)object).getProgramBackup() != object; object = ((Program)object).getProgramBackup()) {
                }
                if (object == object2 || object2 == ProgramShadow) continue;
                ((Program)object2).copyFrom((Program)object);
            }
            Shaders.resize();
            Shaders.resizeShadow();
            if (noiseTextureEnabled) {
                Shaders.setupNoiseTexture();
            }
            if (defaultTexture == null) {
                defaultTexture = ShadersTex.createDefaultTexture();
            }
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0f));
            Shaders.preCelestialRotate(matrixStack);
            Shaders.postCelestialRotate(matrixStack);
            isShaderPackInitialized = true;
            Shaders.loadEntityDataMap();
            Shaders.resetDisplayLists();
            if (!bl) {
                // empty if block
            }
            Shaders.checkGLError("Shaders.init");
        }
    }

    private static void initDrawBuffers(Program program) {
        int n = GL43.glGetInteger(34852);
        Arrays.fill(program.getToggleColorTextures(), false);
        if (program == ProgramFinal) {
            program.setDrawBuffers(null);
        } else if (program.getId() == 0) {
            if (program == ProgramShadow) {
                program.setDrawBuffers(drawBuffersNone);
            } else {
                program.setDrawBuffers(drawBuffersColorAtt[0]);
            }
        } else {
            String[] stringArray = program.getDrawBufSettings();
            if (stringArray == null) {
                if (program != ProgramShadow && program != ProgramShadowSolid && program != ProgramShadowCutout) {
                    program.setDrawBuffers(dfbDrawBuffers);
                    usedDrawBuffers = Math.min(usedColorBuffers, n);
                    Arrays.fill(program.getToggleColorTextures(), 0, usedColorBuffers, true);
                } else {
                    program.setDrawBuffers(sfbDrawBuffers);
                }
            } else {
                String string;
                DrawBuffers drawBuffers = program.getDrawBuffersCustom();
                int n2 = stringArray.length;
                usedDrawBuffers = Math.max(usedDrawBuffers, n2);
                n2 = Math.min(n2, n);
                program.setDrawBuffers(drawBuffers);
                drawBuffers.limit(n2);
                for (int i = 0; i < n2; ++i) {
                    int n3 = Shaders.getDrawBuffer(program, stringArray[i]);
                    drawBuffers.put(i, n3);
                }
                String string2 = drawBuffers.getInfo(true);
                if (!Config.equals(string2, string = drawBuffers.getInfo(false))) {
                    SMCLog.info("Draw buffers: " + string2 + " -> " + string);
                }
            }
        }
    }

    private static void initBlendStatesIndexed(Program program) {
        GlBlendState[] glBlendStateArray = program.getBlendStatesColorIndexed();
        if (glBlendStateArray != null) {
            for (int i = 0; i < glBlendStateArray.length; ++i) {
                GlBlendState glBlendState = glBlendStateArray[i];
                if (glBlendState == null) continue;
                String string = Integer.toHexString(i).toUpperCase();
                int n = 36064 + i;
                int n2 = program.getDrawBuffers().indexOf(n);
                if (n2 < 0) {
                    SMCLog.warning("Blend buffer not used in draw buffers: " + string);
                    continue;
                }
                program.setBlendStateIndexed(n2, glBlendState);
                SMCLog.info("Blend buffer: " + string);
            }
        }
    }

    private static int getDrawBuffer(Program program, String string) {
        int n = 0;
        int n2 = Config.parseInt(string, -1);
        if (program == ProgramShadow) {
            if (n2 >= 0 && n2 < 2) {
                n = 36064 + n2;
                usedShadowColorBuffers = Math.max(usedShadowColorBuffers, n2 + 1);
            }
            return n;
        }
        if (n2 >= 0 && n2 < 16) {
            program.getToggleColorTextures()[n2] = true;
            n = 36064 + n2;
            usedColorAttachs = Math.max(usedColorAttachs, n2 + 1);
            usedColorBuffers = Math.max(usedColorBuffers, n2 + 1);
        }
        return n;
    }

    private static void updateToggleBuffers(Program program) {
        boolean[] blArray = program.getToggleColorTextures();
        Boolean[] booleanArray = program.getBuffersFlip();
        for (int i = 0; i < booleanArray.length; ++i) {
            Boolean bl = booleanArray[i];
            if (bl == null) continue;
            blArray[i] = bl;
        }
    }

    private static void updateProgramSize(Program program) {
        if (program.getProgramStage().isMainComposite()) {
            DynamicDimension dynamicDimension = null;
            int n = 0;
            int n2 = 0;
            DrawBuffers drawBuffers = program.getDrawBuffers();
            if (drawBuffers != null) {
                for (int i = 0; i < drawBuffers.limit(); ++i) {
                    DynamicDimension dynamicDimension2;
                    int n3 = drawBuffers.get(i);
                    int n4 = n3 - 36064;
                    if (n4 < 0 || n4 >= colorBufferSizes.length || (dynamicDimension2 = colorBufferSizes[n4]) == null) continue;
                    ++n;
                    if (dynamicDimension == null) {
                        dynamicDimension = dynamicDimension2;
                    }
                    if (!dynamicDimension2.equals(dynamicDimension)) continue;
                    ++n2;
                }
                if (n != 0) {
                    if (n2 != drawBuffers.limit()) {
                        SMCLog.severe("Program " + program.getName() + " draws to buffers with different sizes");
                    } else {
                        program.setDrawSize(dynamicDimension);
                    }
                }
            }
        }
    }

    public static void resetDisplayLists() {
        SMCLog.info("Reset model renderers");
        ++countResetDisplayLists;
        SMCLog.info("Reset world renderers");
        Shaders.mc.worldRenderer.loadRenderers();
    }

    private static void setupProgram(Program program, String string, String string2, String string3) {
        Shaders.checkGLError("pre setupProgram");
        progUseEntityAttrib = false;
        progUseMidTexCoordAttrib = false;
        progUseTangentAttrib = false;
        progUseVelocityAttrib = false;
        progUseMidBlockAttrib = false;
        int n = Shaders.createVertShader(program, string);
        int n2 = Shaders.createGeomShader(program, string2);
        int n3 = Shaders.createFragShader(program, string3);
        Shaders.checkGLError("create");
        if (n != 0 || n2 != 0 || n3 != 0) {
            int n4 = ARBShaderObjects.glCreateProgramObjectARB();
            Shaders.checkGLError("create");
            if (n != 0) {
                ARBShaderObjects.glAttachObjectARB(n4, n);
                Shaders.checkGLError("attach");
            }
            if (n2 != 0) {
                ARBShaderObjects.glAttachObjectARB(n4, n2);
                Shaders.checkGLError("attach");
                if (progArbGeometryShader4) {
                    ARBGeometryShader4.glProgramParameteriARB(n4, 36315, 4);
                    ARBGeometryShader4.glProgramParameteriARB(n4, 36316, 5);
                    ARBGeometryShader4.glProgramParameteriARB(n4, 36314, progMaxVerticesOut);
                    Shaders.checkGLError("arbGeometryShader4");
                }
                if (progExtGeometryShader4) {
                    EXTGeometryShader4.glProgramParameteriEXT(n4, 36315, 4);
                    EXTGeometryShader4.glProgramParameteriEXT(n4, 36316, 5);
                    EXTGeometryShader4.glProgramParameteriEXT(n4, 36314, progMaxVerticesOut);
                    Shaders.checkGLError("extGeometryShader4");
                }
                hasGeometryShaders = true;
            }
            if (n3 != 0) {
                ARBShaderObjects.glAttachObjectARB(n4, n3);
                Shaders.checkGLError("attach");
            }
            if (progUseEntityAttrib) {
                ARBVertexShader.glBindAttribLocationARB(n4, entityAttrib, "mc_Entity");
                Shaders.checkGLError("mc_Entity");
            }
            if (progUseMidTexCoordAttrib) {
                ARBVertexShader.glBindAttribLocationARB(n4, midTexCoordAttrib, "mc_midTexCoord");
                Shaders.checkGLError("mc_midTexCoord");
            }
            if (progUseTangentAttrib) {
                ARBVertexShader.glBindAttribLocationARB(n4, tangentAttrib, "at_tangent");
                Shaders.checkGLError("at_tangent");
            }
            if (progUseVelocityAttrib) {
                ARBVertexShader.glBindAttribLocationARB(n4, velocityAttrib, "at_velocity");
                Shaders.checkGLError("at_velocity");
            }
            if (progUseMidBlockAttrib) {
                ARBVertexShader.glBindAttribLocationARB(n4, midBlockAttrib, "at_midBlock");
                Shaders.checkGLError("at_midBlock");
            }
            ARBShaderObjects.glLinkProgramARB(n4);
            if (GL43.glGetProgrami(n4, 35714) != 1) {
                SMCLog.severe("Error linking program: " + n4 + " (" + program.getName() + ")");
            }
            Shaders.printLogInfo(n4, program.getName());
            if (n != 0) {
                ARBShaderObjects.glDetachObjectARB(n4, n);
                ARBShaderObjects.glDeleteObjectARB(n);
            }
            if (n2 != 0) {
                ARBShaderObjects.glDetachObjectARB(n4, n2);
                ARBShaderObjects.glDeleteObjectARB(n2);
            }
            if (n3 != 0) {
                ARBShaderObjects.glDetachObjectARB(n4, n3);
                ARBShaderObjects.glDeleteObjectARB(n3);
            }
            program.setId(n4);
            program.setRef(n4);
            Shaders.useProgram(program);
            ARBShaderObjects.glValidateProgramARB(n4);
            Shaders.useProgram(ProgramNone);
            Shaders.printLogInfo(n4, program.getName());
            int n5 = GL43.glGetProgrami(n4, 35715);
            if (n5 != 1) {
                String string4 = "\"";
                Shaders.printChatAndLogError("[Shaders] Error: Invalid program " + string4 + program.getName() + string4);
                ARBShaderObjects.glDeleteObjectARB(n4);
                n4 = 0;
                program.resetId();
            }
        }
    }

    private static ComputeProgram[] setupComputePrograms(Program program, String string, String string2, String string3) {
        if (program.getProgramStage() == ProgramStage.GBUFFERS) {
            return new ComputeProgram[0];
        }
        ArrayList<ComputeProgram> arrayList = new ArrayList<ComputeProgram>();
        int n = 27;
        for (int i = 0; i < n; ++i) {
            String string4 = i > 0 ? "_" + (char)(97 + i - 1) : "";
            String string5 = string2 + string4;
            String string6 = string + string5 + string3;
            ComputeProgram computeProgram = new ComputeProgram(program.getName(), program.getProgramStage());
            Shaders.setupComputeProgram(computeProgram, string6);
            if (computeProgram.getId() <= 0) continue;
            arrayList.add(computeProgram);
            SMCLog.info("Compute program loaded: " + string5);
        }
        return arrayList.toArray(new ComputeProgram[arrayList.size()]);
    }

    private static void setupComputeProgram(ComputeProgram computeProgram, String string) {
        Shaders.checkGLError("pre setupProgram");
        int n = Shaders.createCompShader(computeProgram, string);
        Shaders.checkGLError("create");
        if (n != 0) {
            int n2 = ARBShaderObjects.glCreateProgramObjectARB();
            Shaders.checkGLError("create");
            if (n != 0) {
                ARBShaderObjects.glAttachObjectARB(n2, n);
                Shaders.checkGLError("attach");
            }
            ARBShaderObjects.glLinkProgramARB(n2);
            if (GL43.glGetProgrami(n2, 35714) != 1) {
                SMCLog.severe("Error linking program: " + n2 + " (" + computeProgram.getName() + ")");
            }
            Shaders.printLogInfo(n2, computeProgram.getName());
            if (n != 0) {
                ARBShaderObjects.glDetachObjectARB(n2, n);
                ARBShaderObjects.glDeleteObjectARB(n);
            }
            computeProgram.setId(n2);
            computeProgram.setRef(n2);
            ARBShaderObjects.glUseProgramObjectARB(n2);
            ARBShaderObjects.glValidateProgramARB(n2);
            ARBShaderObjects.glUseProgramObjectARB(0);
            Shaders.printLogInfo(n2, computeProgram.getName());
            int n3 = GL43.glGetProgrami(n2, 35715);
            if (n3 != 1) {
                String string2 = "\"";
                Shaders.printChatAndLogError("[Shaders] Error: Invalid program " + string2 + computeProgram.getName() + string2);
                ARBShaderObjects.glDeleteObjectARB(n2);
                n2 = 0;
                computeProgram.resetId();
            }
        }
    }

    private static int createCompShader(ComputeProgram computeProgram, String string) {
        Object object;
        InputStream inputStream = shaderPack.getResourceAsStream(string);
        if (inputStream == null) {
            return 1;
        }
        int n = ARBShaderObjects.glCreateShaderObjectARB(37305);
        if (n == 0) {
            return 1;
        }
        ShaderOption[] shaderOptionArray = Shaders.getChangedOptions(shaderPackOptions);
        ArrayList<String> arrayList = new ArrayList<String>();
        LineBuffer lineBuffer = new LineBuffer();
        if (lineBuffer != null) {
            try {
                object = LineBuffer.readAll(new InputStreamReader(inputStream));
                object = ShaderPackParser.resolveIncludes((LineBuffer)object, string, shaderPack, 0, arrayList, 0);
                MacroState macroState = new MacroState();
                Iterator<String> iterator2 = ((LineBuffer)object).iterator();
                while (iterator2.hasNext()) {
                    int n2;
                    Object object2;
                    ShaderLine shaderLine;
                    String string2 = iterator2.next();
                    string2 = Shaders.applyOptions(string2, shaderOptionArray);
                    lineBuffer.add(string2);
                    if (!macroState.processLine(string2) || (shaderLine = ShaderParser.parseLine(string2)) == null) continue;
                    if (shaderLine.isUniform()) {
                        object2 = shaderLine.getName();
                        n2 = ShaderParser.getShadowDepthIndex((String)object2);
                        if (n2 >= 0) {
                            usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getShadowColorIndex((String)object2);
                        if (n2 >= 0) {
                            usedShadowColorBuffers = Math.max(usedShadowColorBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getShadowColorImageIndex((String)object2);
                        if (n2 >= 0) {
                            usedShadowColorBuffers = Math.max(usedShadowColorBuffers, n2 + 1);
                            bindImageTextures = true;
                            continue;
                        }
                        n2 = ShaderParser.getDepthIndex((String)object2);
                        if (n2 >= 0) {
                            usedDepthBuffers = Math.max(usedDepthBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getColorIndex((String)object2);
                        if (n2 >= 0) {
                            usedColorBuffers = Math.max(usedColorBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getColorImageIndex((String)object2);
                        if (n2 < 0) continue;
                        usedColorBuffers = Math.max(usedColorBuffers, n2 + 1);
                        bindImageTextures = true;
                        continue;
                    }
                    if (shaderLine.isLayout("in")) {
                        object2 = ShaderParser.parseLocalSize(shaderLine.getValue());
                        if (object2 != null) {
                            computeProgram.setLocalSize((Vector3i)object2);
                            continue;
                        }
                        SMCLog.severe("Invalid local size: " + string2);
                        continue;
                    }
                    if (shaderLine.isConstIVec3("workGroups")) {
                        object2 = shaderLine.getValueIVec3();
                        if (object2 != null) {
                            computeProgram.setWorkGroups((Vector3i)object2);
                            continue;
                        }
                        SMCLog.severe("Invalid workGroups: " + string2);
                        continue;
                    }
                    if (shaderLine.isConstVec2("workGroupsRender")) {
                        object2 = shaderLine.getValueVec2();
                        if (object2 != null) {
                            computeProgram.setWorkGroupsRender((Vector2f)object2);
                            continue;
                        }
                        SMCLog.severe("Invalid workGroupsRender: " + string2);
                        continue;
                    }
                    if (!shaderLine.isConstBoolSuffix("MipmapEnabled", false) || (n2 = Shaders.getBufferIndex((String)(object2 = StrUtils.removeSuffix(shaderLine.getName(), "MipmapEnabled")))) < 0) continue;
                    int n3 = computeProgram.getCompositeMipmapSetting();
                    computeProgram.setCompositeMipmapSetting(n3 |= 1 << n2);
                    SMCLog.info("%s mipmap enabled", object2);
                }
            } catch (Exception exception) {
                SMCLog.severe("Couldn't read " + string + "!");
                exception.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(n);
                return 1;
            }
        }
        object = lineBuffer.toString();
        if (saveFinalShaders) {
            Shaders.saveShader(string, (String)object);
        }
        if (computeProgram.getLocalSize() == null) {
            SMCLog.severe("Missing local size: " + string);
            GL43.glDeleteShader(n);
            return 1;
        }
        ARBShaderObjects.glShaderSourceARB(n, (CharSequence)object);
        ARBShaderObjects.glCompileShaderARB(n);
        if (GL43.glGetShaderi(n, 35713) != 1) {
            SMCLog.severe("Error compiling compute shader: " + string);
        }
        Shaders.printShaderLogInfo(n, string, arrayList);
        return n;
    }

    private static int createVertShader(Program program, String string) {
        Object object;
        InputStream inputStream = shaderPack.getResourceAsStream(string);
        if (inputStream == null) {
            return 1;
        }
        int n = ARBShaderObjects.glCreateShaderObjectARB(35633);
        if (n == 0) {
            return 1;
        }
        ShaderOption[] shaderOptionArray = Shaders.getChangedOptions(shaderPackOptions);
        ArrayList<String> arrayList = new ArrayList<String>();
        LineBuffer lineBuffer = new LineBuffer();
        if (lineBuffer != null) {
            try {
                object = LineBuffer.readAll(new InputStreamReader(inputStream));
                object = ShaderPackParser.resolveIncludes((LineBuffer)object, string, shaderPack, 0, arrayList, 0);
                object = ShaderPackParser.remapTextureUnits((LineBuffer)object);
                MacroState macroState = new MacroState();
                Iterator<String> iterator2 = ((LineBuffer)object).iterator();
                while (iterator2.hasNext()) {
                    ShaderLine shaderLine;
                    String string2 = iterator2.next();
                    string2 = Shaders.applyOptions(string2, shaderOptionArray);
                    lineBuffer.add(string2);
                    if (!macroState.processLine(string2) || (shaderLine = ShaderParser.parseLine(string2)) == null) continue;
                    if (shaderLine.isAttribute("mc_Entity")) {
                        useEntityAttrib = true;
                        progUseEntityAttrib = true;
                    } else if (shaderLine.isAttribute("mc_midTexCoord")) {
                        useMidTexCoordAttrib = true;
                        progUseMidTexCoordAttrib = true;
                    } else if (shaderLine.isAttribute("at_tangent")) {
                        useTangentAttrib = true;
                        progUseTangentAttrib = true;
                    } else if (shaderLine.isAttribute("at_velocity")) {
                        useVelocityAttrib = true;
                        progUseVelocityAttrib = true;
                    } else if (shaderLine.isAttribute("at_midBlock")) {
                        useMidBlockAttrib = true;
                        progUseMidBlockAttrib = true;
                    }
                    if (!shaderLine.isConstInt("countInstances")) continue;
                    program.setCountInstances(shaderLine.getValueInt());
                    SMCLog.info("countInstances: " + program.getCountInstances());
                }
            } catch (Exception exception) {
                SMCLog.severe("Couldn't read " + string + "!");
                exception.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(n);
                return 1;
            }
        }
        object = lineBuffer.toString();
        if (saveFinalShaders) {
            Shaders.saveShader(string, (String)object);
        }
        ARBShaderObjects.glShaderSourceARB(n, (CharSequence)object);
        ARBShaderObjects.glCompileShaderARB(n);
        if (GL43.glGetShaderi(n, 35713) != 1) {
            SMCLog.severe("Error compiling vertex shader: " + string);
        }
        Shaders.printShaderLogInfo(n, string, arrayList);
        return n;
    }

    private static int createGeomShader(Program program, String string) {
        Object object;
        InputStream inputStream = shaderPack.getResourceAsStream(string);
        if (inputStream == null) {
            return 1;
        }
        int n = ARBShaderObjects.glCreateShaderObjectARB(36313);
        if (n == 0) {
            return 1;
        }
        ShaderOption[] shaderOptionArray = Shaders.getChangedOptions(shaderPackOptions);
        ArrayList<String> arrayList = new ArrayList<String>();
        progArbGeometryShader4 = false;
        progExtGeometryShader4 = false;
        progMaxVerticesOut = 3;
        LineBuffer lineBuffer = new LineBuffer();
        if (lineBuffer != null) {
            try {
                object = LineBuffer.readAll(new InputStreamReader(inputStream));
                object = ShaderPackParser.resolveIncludes((LineBuffer)object, string, shaderPack, 0, arrayList, 0);
                MacroState macroState = new MacroState();
                Iterator<String> iterator2 = ((LineBuffer)object).iterator();
                while (iterator2.hasNext()) {
                    String string2;
                    ShaderLine shaderLine;
                    String string3 = iterator2.next();
                    string3 = Shaders.applyOptions(string3, shaderOptionArray);
                    lineBuffer.add(string3);
                    if (!macroState.processLine(string3) || (shaderLine = ShaderParser.parseLine(string3)) == null) continue;
                    if (shaderLine.isExtension("GL_ARB_geometry_shader4") && ((string2 = Config.normalize(shaderLine.getValue())).equals("enable") || string2.equals("require") || string2.equals("warn"))) {
                        progArbGeometryShader4 = true;
                    }
                    if (shaderLine.isExtension("GL_EXT_geometry_shader4") && ((string2 = Config.normalize(shaderLine.getValue())).equals("enable") || string2.equals("require") || string2.equals("warn"))) {
                        progExtGeometryShader4 = true;
                    }
                    if (!shaderLine.isConstInt("maxVerticesOut")) continue;
                    progMaxVerticesOut = shaderLine.getValueInt();
                }
            } catch (Exception exception) {
                SMCLog.severe("Couldn't read " + string + "!");
                exception.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(n);
                return 1;
            }
        }
        object = lineBuffer.toString();
        if (saveFinalShaders) {
            Shaders.saveShader(string, (String)object);
        }
        ARBShaderObjects.glShaderSourceARB(n, (CharSequence)object);
        ARBShaderObjects.glCompileShaderARB(n);
        if (GL43.glGetShaderi(n, 35713) != 1) {
            SMCLog.severe("Error compiling geometry shader: " + string);
        }
        Shaders.printShaderLogInfo(n, string, arrayList);
        return n;
    }

    private static int createFragShader(Program program, String string) {
        Object object;
        InputStream inputStream = shaderPack.getResourceAsStream(string);
        if (inputStream == null) {
            return 1;
        }
        int n = ARBShaderObjects.glCreateShaderObjectARB(35632);
        if (n == 0) {
            return 1;
        }
        ShaderOption[] shaderOptionArray = Shaders.getChangedOptions(shaderPackOptions);
        ArrayList<String> arrayList = new ArrayList<String>();
        LineBuffer lineBuffer = new LineBuffer();
        if (lineBuffer != null) {
            try {
                object = LineBuffer.readAll(new InputStreamReader(inputStream));
                object = ShaderPackParser.resolveIncludes((LineBuffer)object, string, shaderPack, 0, arrayList, 0);
                MacroState macroState = new MacroState();
                Iterator<String> iterator2 = ((LineBuffer)object).iterator();
                while (iterator2.hasNext()) {
                    String string2;
                    ShaderLine shaderLine;
                    String string3 = iterator2.next();
                    string3 = Shaders.applyOptions(string3, shaderOptionArray);
                    lineBuffer.add(string3);
                    if (!macroState.processLine(string3) || (shaderLine = ShaderParser.parseLine(string3)) == null) continue;
                    if (shaderLine.isUniform()) {
                        string2 = shaderLine.getName();
                        int n2 = ShaderParser.getShadowDepthIndex(string2);
                        if (n2 >= 0) {
                            usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getShadowColorIndex(string2);
                        if (n2 >= 0) {
                            usedShadowColorBuffers = Math.max(usedShadowColorBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getShadowColorImageIndex(string2);
                        if (n2 >= 0) {
                            usedShadowColorBuffers = Math.max(usedShadowColorBuffers, n2 + 1);
                            bindImageTextures = true;
                            continue;
                        }
                        n2 = ShaderParser.getDepthIndex(string2);
                        if (n2 >= 0) {
                            usedDepthBuffers = Math.max(usedDepthBuffers, n2 + 1);
                            continue;
                        }
                        if (string2.equals("gdepth") && gbuffersFormat[1] == 6408) {
                            Shaders.gbuffersFormat[1] = 34836;
                            continue;
                        }
                        n2 = ShaderParser.getColorIndex(string2);
                        if (n2 >= 0) {
                            usedColorBuffers = Math.max(usedColorBuffers, n2 + 1);
                            continue;
                        }
                        n2 = ShaderParser.getColorImageIndex(string2);
                        if (n2 >= 0) {
                            usedColorBuffers = Math.max(usedColorBuffers, n2 + 1);
                            bindImageTextures = true;
                            continue;
                        }
                        if (!string2.equals("centerDepthSmooth")) continue;
                        centerDepthSmoothEnabled = true;
                        continue;
                    }
                    if (!shaderLine.isConstInt("shadowMapResolution") && !shaderLine.isProperty("SHADOWRES")) {
                        if (!shaderLine.isConstFloat("shadowMapFov") && !shaderLine.isProperty("SHADOWFOV")) {
                            if (!shaderLine.isConstFloat("shadowDistance") && !shaderLine.isProperty("SHADOWHPL")) {
                                if (shaderLine.isConstFloat("shadowDistanceRenderMul")) {
                                    shadowDistanceRenderMul = shaderLine.getValueFloat();
                                    SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul);
                                    continue;
                                }
                                if (shaderLine.isConstFloat("shadowIntervalSize")) {
                                    shadowIntervalSize = shaderLine.getValueFloat();
                                    SMCLog.info("Shadow map interval size: " + shadowIntervalSize);
                                    continue;
                                }
                                if (shaderLine.isConstBool("generateShadowMipmap", false)) {
                                    Arrays.fill(shadowMipmapEnabled, true);
                                    SMCLog.info("Generate shadow mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("generateShadowColorMipmap", false)) {
                                    Arrays.fill(shadowColorMipmapEnabled, true);
                                    SMCLog.info("Generate shadow color mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowHardwareFiltering", false)) {
                                    Arrays.fill(shadowHardwareFilteringEnabled, true);
                                    SMCLog.info("Hardware shadow filtering enabled.");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowHardwareFiltering0", false)) {
                                    Shaders.shadowHardwareFilteringEnabled[0] = true;
                                    SMCLog.info("shadowHardwareFiltering0");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowHardwareFiltering1", false)) {
                                    Shaders.shadowHardwareFilteringEnabled[1] = true;
                                    SMCLog.info("shadowHardwareFiltering1");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", false)) {
                                    Shaders.shadowMipmapEnabled[0] = true;
                                    SMCLog.info("shadowtex0Mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowtex1Mipmap", false)) {
                                    Shaders.shadowMipmapEnabled[1] = true;
                                    SMCLog.info("shadowtex1Mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", false)) {
                                    Shaders.shadowColorMipmapEnabled[0] = true;
                                    SMCLog.info("shadowcolor0Mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", false)) {
                                    Shaders.shadowColorMipmapEnabled[1] = true;
                                    SMCLog.info("shadowcolor1Mipmap");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", false)) {
                                    Shaders.shadowFilterNearest[0] = true;
                                    SMCLog.info("shadowtex0Nearest");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", false)) {
                                    Shaders.shadowFilterNearest[1] = true;
                                    SMCLog.info("shadowtex1Nearest");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", false)) {
                                    Shaders.shadowColorFilterNearest[0] = true;
                                    SMCLog.info("shadowcolor0Nearest");
                                    continue;
                                }
                                if (shaderLine.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", false)) {
                                    Shaders.shadowColorFilterNearest[1] = true;
                                    SMCLog.info("shadowcolor1Nearest");
                                    continue;
                                }
                                if (!shaderLine.isConstFloat("wetnessHalflife") && !shaderLine.isProperty("WETNESSHL")) {
                                    if (!shaderLine.isConstFloat("drynessHalflife") && !shaderLine.isProperty("DRYNESSHL")) {
                                        int n3;
                                        int n4;
                                        if (shaderLine.isConstFloat("eyeBrightnessHalflife")) {
                                            eyeBrightnessHalflife = shaderLine.getValueFloat();
                                            SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife);
                                            continue;
                                        }
                                        if (shaderLine.isConstFloat("centerDepthHalflife")) {
                                            centerDepthSmoothHalflife = shaderLine.getValueFloat();
                                            SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife);
                                            continue;
                                        }
                                        if (shaderLine.isConstFloat("sunPathRotation")) {
                                            sunPathRotation = shaderLine.getValueFloat();
                                            SMCLog.info("Sun path rotation: " + sunPathRotation);
                                            continue;
                                        }
                                        if (shaderLine.isConstFloat("ambientOcclusionLevel")) {
                                            aoLevel = Config.limit(shaderLine.getValueFloat(), 0.0f, 1.0f);
                                            SMCLog.info("AO Level: " + aoLevel);
                                            continue;
                                        }
                                        if (shaderLine.isConstInt("superSamplingLevel")) {
                                            int n5 = shaderLine.getValueInt();
                                            if (n5 > 1) {
                                                SMCLog.info("Super sampling level: " + n5 + "x");
                                                superSamplingLevel = n5;
                                                continue;
                                            }
                                            superSamplingLevel = 1;
                                            continue;
                                        }
                                        if (shaderLine.isConstInt("noiseTextureResolution")) {
                                            noiseTextureResolution = shaderLine.getValueInt();
                                            noiseTextureEnabled = true;
                                            SMCLog.info("Noise texture enabled");
                                            SMCLog.info("Noise texture resolution: " + noiseTextureResolution);
                                            continue;
                                        }
                                        if (shaderLine.isConstIntSuffix("Format")) {
                                            int n6;
                                            string2 = StrUtils.removeSuffix(shaderLine.getName(), "Format");
                                            String string4 = shaderLine.getValue();
                                            n4 = Shaders.getTextureFormatFromString(string4);
                                            if (n4 == 0) continue;
                                            n3 = Shaders.getBufferIndex(string2);
                                            if (n3 >= 0) {
                                                Shaders.gbuffersFormat[n3] = n4;
                                                SMCLog.info("%s format: %s", string2, string4);
                                            }
                                            if ((n6 = ShaderParser.getShadowColorIndex(string2)) < 0) continue;
                                            Shaders.shadowBuffersFormat[n6] = n4;
                                            SMCLog.info("%s format: %s", string2, string4);
                                            continue;
                                        }
                                        if (shaderLine.isConstBoolSuffix("Clear", true)) {
                                            if (!program.getProgramStage().isAnyComposite()) continue;
                                            string2 = StrUtils.removeSuffix(shaderLine.getName(), "Clear");
                                            int n7 = Shaders.getBufferIndex(string2);
                                            if (n7 >= 0) {
                                                Shaders.gbuffersClear[n7] = false;
                                                SMCLog.info("%s clear disabled", string2);
                                            }
                                            if ((n4 = ShaderParser.getShadowColorIndex(string2)) < 0) continue;
                                            Shaders.shadowBuffersClear[n4] = false;
                                            SMCLog.info("%s clear disabled", string2);
                                            continue;
                                        }
                                        if (shaderLine.isConstVec4Suffix("ClearColor")) {
                                            if (!program.getProgramStage().isAnyComposite()) continue;
                                            string2 = StrUtils.removeSuffix(shaderLine.getName(), "ClearColor");
                                            Vector4f vector4f = shaderLine.getValueVec4();
                                            if (vector4f != null) {
                                                n4 = Shaders.getBufferIndex(string2);
                                                if (n4 >= 0) {
                                                    Shaders.gbuffersClearColor[n4] = vector4f;
                                                    SMCLog.info("%s clear color: %s %s %s %s", string2, Float.valueOf(vector4f.getX()), Float.valueOf(vector4f.getY()), Float.valueOf(vector4f.getZ()), Float.valueOf(vector4f.getW()));
                                                }
                                                if ((n3 = ShaderParser.getShadowColorIndex(string2)) < 0) continue;
                                                Shaders.shadowBuffersClearColor[n3] = vector4f;
                                                SMCLog.info("%s clear color: %s %s %s %s", string2, Float.valueOf(vector4f.getX()), Float.valueOf(vector4f.getY()), Float.valueOf(vector4f.getZ()), Float.valueOf(vector4f.getW()));
                                                continue;
                                            }
                                            SMCLog.warning("Invalid color value: " + shaderLine.getValue());
                                            continue;
                                        }
                                        if (shaderLine.isProperty("GAUX4FORMAT", "RGBA32F")) {
                                            Shaders.gbuffersFormat[7] = 34836;
                                            SMCLog.info("gaux4 format : RGB32AF");
                                            continue;
                                        }
                                        if (shaderLine.isProperty("GAUX4FORMAT", "RGB32F")) {
                                            Shaders.gbuffersFormat[7] = 34837;
                                            SMCLog.info("gaux4 format : RGB32F");
                                            continue;
                                        }
                                        if (shaderLine.isProperty("GAUX4FORMAT", "RGB16")) {
                                            Shaders.gbuffersFormat[7] = 32852;
                                            SMCLog.info("gaux4 format : RGB16");
                                            continue;
                                        }
                                        if (shaderLine.isConstBoolSuffix("MipmapEnabled", false)) {
                                            int n8;
                                            if (!program.getProgramStage().isAnyComposite() || (n8 = Shaders.getBufferIndex(string2 = StrUtils.removeSuffix(shaderLine.getName(), "MipmapEnabled"))) < 0) continue;
                                            n4 = program.getCompositeMipmapSetting();
                                            program.setCompositeMipmapSetting(n4 |= 1 << n8);
                                            SMCLog.info("%s mipmap enabled", string2);
                                            continue;
                                        }
                                        if (shaderLine.isProperty("DRAWBUFFERS")) {
                                            string2 = shaderLine.getValue();
                                            String[] stringArray = ShaderParser.parseDrawBuffers(string2);
                                            if (stringArray != null) {
                                                program.setDrawBufSettings(stringArray);
                                                continue;
                                            }
                                            SMCLog.warning("Invalid draw buffers: " + string2);
                                            continue;
                                        }
                                        if (!shaderLine.isProperty("RENDERTARGETS")) continue;
                                        string2 = shaderLine.getValue();
                                        String[] stringArray = ShaderParser.parseRenderTargets(string2);
                                        if (stringArray != null) {
                                            program.setDrawBufSettings(stringArray);
                                            continue;
                                        }
                                        SMCLog.warning("Invalid render targets: " + string2);
                                        continue;
                                    }
                                    drynessHalfLife = shaderLine.getValueFloat();
                                    SMCLog.info("Dryness halflife: " + drynessHalfLife);
                                    continue;
                                }
                                wetnessHalfLife = shaderLine.getValueFloat();
                                SMCLog.info("Wetness halflife: " + wetnessHalfLife);
                                continue;
                            }
                            shadowMapHalfPlane = shaderLine.getValueFloat();
                            shadowMapIsOrtho = true;
                            SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
                            continue;
                        }
                        shadowMapFOV = shaderLine.getValueFloat();
                        shadowMapIsOrtho = false;
                        SMCLog.info("Shadow map field of view: " + shadowMapFOV);
                        continue;
                    }
                    spShadowMapWidth = spShadowMapHeight = shaderLine.getValueInt();
                    shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                    SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
                }
            } catch (Exception exception) {
                SMCLog.severe("Couldn't read " + string + "!");
                exception.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(n);
                return 1;
            }
        }
        object = lineBuffer.toString();
        if (saveFinalShaders) {
            Shaders.saveShader(string, (String)object);
        }
        ARBShaderObjects.glShaderSourceARB(n, (CharSequence)object);
        ARBShaderObjects.glCompileShaderARB(n);
        if (GL43.glGetShaderi(n, 35713) != 1) {
            SMCLog.severe("Error compiling fragment shader: " + string);
        }
        Shaders.printShaderLogInfo(n, string, arrayList);
        return n;
    }

    public static void saveShader(String string, String string2) {
        try {
            File file = new File(shaderPacksDir, "debug/" + string);
            file.getParentFile().mkdirs();
            Config.writeFile(file, string2);
        } catch (IOException iOException) {
            Config.warn("Error saving: " + string);
            iOException.printStackTrace();
        }
    }

    private static void clearDirectory(File file) {
        File[] fileArray;
        if (file.exists() && file.isDirectory() && (fileArray = file.listFiles()) != null) {
            for (int i = 0; i < fileArray.length; ++i) {
                File file2 = fileArray[i];
                if (file2.isDirectory()) {
                    Shaders.clearDirectory(file2);
                }
                file2.delete();
            }
        }
    }

    private static boolean printLogInfo(int n, String string) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterivARB(n, 35716, intBuffer);
        int n2 = intBuffer.get();
        if (n2 > 1) {
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(n2);
            intBuffer.flip();
            ARBShaderObjects.glGetInfoLogARB(n, intBuffer, byteBuffer);
            byte[] byArray = new byte[n2];
            byteBuffer.get(byArray);
            if (byArray[n2 - 1] == 0) {
                byArray[n2 - 1] = 10;
            }
            String string2 = new String(byArray, StandardCharsets.US_ASCII);
            string2 = StrUtils.trim(string2, " \n\r\t");
            SMCLog.info("Info log: " + string + "\n" + string2);
            return true;
        }
        return false;
    }

    private static boolean printShaderLogInfo(int n, String string, List<String> list) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        int n2 = GL43.glGetShaderi(n, 35716);
        if (n2 <= 1) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            String string2 = list.get(i);
            SMCLog.info("File: " + (i + 1) + " = " + string2);
        }
        String string3 = GL43.glGetShaderInfoLog(n, n2);
        string3 = StrUtils.trim(string3, " \n\r\t");
        SMCLog.info("Shader info log: " + string + "\n" + string3);
        return true;
    }

    public static void useProgram(Program program) {
        Shaders.checkGLError("pre-useProgram");
        if (isShadowPass) {
            program = ProgramShadow;
        } else if (isEntitiesGlowing) {
            program = ProgramEntitiesGlowing;
        }
        if (activeProgram != program) {
            int n;
            Shaders.flushRenderBuffers();
            Shaders.updateAlphaBlend(activeProgram, program);
            if (glDebugGroups && glDebugGroupProgram) {
                KHRDebug.glPopDebugGroup();
            }
            activeProgram = program;
            if (glDebugGroups) {
                KHRDebug.glPushDebugGroup(33354, 0, activeProgram.getRealProgramName());
                glDebugGroupProgram = true;
            }
            activeProgramID = n = program.getId();
            ARBShaderObjects.glUseProgramObjectARB(n);
            if (Shaders.checkGLError("useProgram") != 0) {
                program.setId(0);
                activeProgramID = n = program.getId();
                ARBShaderObjects.glUseProgramObjectARB(n);
            }
            shaderUniforms.setProgram(n);
            if (customUniforms != null) {
                customUniforms.setProgram(n);
            }
            if (n != 0) {
                DrawBuffers drawBuffers = program.getDrawBuffers();
                if (isRenderingDfb) {
                    GlState.setDrawBuffers(drawBuffers);
                }
                Shaders.setProgramUniforms(program.getProgramStage());
                Shaders.setImageUniforms();
                Shaders.checkGLError("end useProgram");
            }
        }
    }

    private static void setProgramUniforms(ProgramStage programStage) {
        int n;
        switch (programStage) {
            case GBUFFERS: {
                Shaders.setProgramUniform1i(uniform_texture, 0);
                Shaders.setProgramUniform1i(uniform_lightmap, 2);
                Shaders.setProgramUniform1i(uniform_normals, 1);
                Shaders.setProgramUniform1i(uniform_specular, 3);
                Shaders.setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                Shaders.setProgramUniform1i(uniform_watershadow, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex0, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex1, 5);
                Shaders.setProgramUniform1i(uniform_depthtex0, 6);
                if (customTexturesGbuffers != null || hasDeferredPrograms) {
                    Shaders.setProgramUniform1i(uniform_gaux1, 7);
                    Shaders.setProgramUniform1i(uniform_gaux2, 8);
                    Shaders.setProgramUniform1i(uniform_gaux3, 9);
                    Shaders.setProgramUniform1i(uniform_gaux4, 10);
                    Shaders.setProgramUniform1i(uniform_colortex4, 7);
                    Shaders.setProgramUniform1i(uniform_colortex5, 8);
                    Shaders.setProgramUniform1i(uniform_colortex6, 9);
                    Shaders.setProgramUniform1i(uniform_colortex7, 10);
                    if (usedColorBuffers > 8) {
                        Shaders.setProgramUniform1i(uniform_colortex8, 16);
                        Shaders.setProgramUniform1i(uniform_colortex9, 17);
                        Shaders.setProgramUniform1i(uniform_colortex10, 18);
                        Shaders.setProgramUniform1i(uniform_colortex11, 19);
                        Shaders.setProgramUniform1i(uniform_colortex12, 20);
                        Shaders.setProgramUniform1i(uniform_colortex13, 21);
                        Shaders.setProgramUniform1i(uniform_colortex14, 22);
                        Shaders.setProgramUniform1i(uniform_colortex15, 23);
                    }
                }
                Shaders.setProgramUniform1i(uniform_depthtex1, 11);
                Shaders.setProgramUniform1i(uniform_shadowcolor, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor0, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor1, 14);
                Shaders.setProgramUniform1i(uniform_noisetex, 15);
                break;
            }
            case SHADOWCOMP: 
            case PREPARE: 
            case DEFERRED: 
            case COMPOSITE: {
                Shaders.setProgramUniform1i(uniform_gcolor, 0);
                Shaders.setProgramUniform1i(uniform_gdepth, 1);
                Shaders.setProgramUniform1i(uniform_gnormal, 2);
                Shaders.setProgramUniform1i(uniform_composite, 3);
                Shaders.setProgramUniform1i(uniform_gaux1, 7);
                Shaders.setProgramUniform1i(uniform_gaux2, 8);
                Shaders.setProgramUniform1i(uniform_gaux3, 9);
                Shaders.setProgramUniform1i(uniform_gaux4, 10);
                Shaders.setProgramUniform1i(uniform_colortex0, 0);
                Shaders.setProgramUniform1i(uniform_colortex1, 1);
                Shaders.setProgramUniform1i(uniform_colortex2, 2);
                Shaders.setProgramUniform1i(uniform_colortex3, 3);
                Shaders.setProgramUniform1i(uniform_colortex4, 7);
                Shaders.setProgramUniform1i(uniform_colortex5, 8);
                Shaders.setProgramUniform1i(uniform_colortex6, 9);
                Shaders.setProgramUniform1i(uniform_colortex7, 10);
                if (usedColorBuffers > 8) {
                    Shaders.setProgramUniform1i(uniform_colortex8, 16);
                    Shaders.setProgramUniform1i(uniform_colortex9, 17);
                    Shaders.setProgramUniform1i(uniform_colortex10, 18);
                    Shaders.setProgramUniform1i(uniform_colortex11, 19);
                    Shaders.setProgramUniform1i(uniform_colortex12, 20);
                    Shaders.setProgramUniform1i(uniform_colortex13, 21);
                    Shaders.setProgramUniform1i(uniform_colortex14, 22);
                    Shaders.setProgramUniform1i(uniform_colortex15, 23);
                }
                Shaders.setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                Shaders.setProgramUniform1i(uniform_watershadow, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex0, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex1, 5);
                Shaders.setProgramUniform1i(uniform_gdepthtex, 6);
                Shaders.setProgramUniform1i(uniform_depthtex0, 6);
                Shaders.setProgramUniform1i(uniform_depthtex1, 11);
                Shaders.setProgramUniform1i(uniform_depthtex2, 12);
                Shaders.setProgramUniform1i(uniform_shadowcolor, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor0, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor1, 14);
                Shaders.setProgramUniform1i(uniform_noisetex, 15);
                break;
            }
            case SHADOW: {
                Shaders.setProgramUniform1i(uniform_tex, 0);
                Shaders.setProgramUniform1i(uniform_texture, 0);
                Shaders.setProgramUniform1i(uniform_lightmap, 2);
                Shaders.setProgramUniform1i(uniform_normals, 1);
                Shaders.setProgramUniform1i(uniform_specular, 3);
                Shaders.setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                Shaders.setProgramUniform1i(uniform_watershadow, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex0, 4);
                Shaders.setProgramUniform1i(uniform_shadowtex1, 5);
                if (customTexturesGbuffers != null) {
                    Shaders.setProgramUniform1i(uniform_gaux1, 7);
                    Shaders.setProgramUniform1i(uniform_gaux2, 8);
                    Shaders.setProgramUniform1i(uniform_gaux3, 9);
                    Shaders.setProgramUniform1i(uniform_gaux4, 10);
                    Shaders.setProgramUniform1i(uniform_colortex4, 7);
                    Shaders.setProgramUniform1i(uniform_colortex5, 8);
                    Shaders.setProgramUniform1i(uniform_colortex6, 9);
                    Shaders.setProgramUniform1i(uniform_colortex7, 10);
                    if (usedColorBuffers > 8) {
                        Shaders.setProgramUniform1i(uniform_colortex8, 16);
                        Shaders.setProgramUniform1i(uniform_colortex9, 17);
                        Shaders.setProgramUniform1i(uniform_colortex10, 18);
                        Shaders.setProgramUniform1i(uniform_colortex11, 19);
                        Shaders.setProgramUniform1i(uniform_colortex12, 20);
                        Shaders.setProgramUniform1i(uniform_colortex13, 21);
                        Shaders.setProgramUniform1i(uniform_colortex14, 22);
                        Shaders.setProgramUniform1i(uniform_colortex15, 23);
                    }
                }
                Shaders.setProgramUniform1i(uniform_shadowcolor, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor0, 13);
                Shaders.setProgramUniform1i(uniform_shadowcolor1, 14);
                Shaders.setProgramUniform1i(uniform_noisetex, 15);
            }
        }
        ItemStack itemStack = Shaders.mc.player != null ? Shaders.mc.player.getHeldItemMainhand() : null;
        Item item = itemStack != null ? itemStack.getItem() : null;
        int n2 = -1;
        Block block = null;
        if (item != null) {
            n2 = Registry.ITEM.getId(item);
            if (item instanceof BlockItem) {
                block = ((BlockItem)item).getBlock();
            }
            n2 = ItemAliases.getItemAliasId(n2);
        }
        int n3 = block != null ? block.getDefaultState().getLightValue() : 0;
        ItemStack itemStack2 = Shaders.mc.player != null ? Shaders.mc.player.getHeldItemOffhand() : null;
        Item item2 = itemStack2 != null ? itemStack2.getItem() : null;
        int n4 = -1;
        Block block2 = null;
        if (item2 != null) {
            n4 = Registry.ITEM.getId(item2);
            if (item2 instanceof BlockItem) {
                block2 = ((BlockItem)item2).getBlock();
            }
            n4 = ItemAliases.getItemAliasId(n4);
        }
        int n5 = n = block2 != null ? block2.getDefaultState().getLightValue() : 0;
        if (Shaders.isOldHandLight() && n > n3) {
            n2 = n4;
            n3 = n;
        }
        float f = Shaders.mc.player != null ? Shaders.mc.player.getDarknessAmbience() : 0.0f;
        Shaders.setProgramUniform1i(uniform_heldItemId, n2);
        Shaders.setProgramUniform1i(uniform_heldBlockLightValue, n3);
        Shaders.setProgramUniform1i(uniform_heldItemId2, n4);
        Shaders.setProgramUniform1i(uniform_heldBlockLightValue2, n);
        Shaders.setProgramUniform1i(uniform_fogMode, fogEnabled ? fogMode : 0);
        Shaders.setProgramUniform1f(uniform_fogDensity, fogEnabled ? fogDensity : 0.0f);
        Shaders.setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
        Shaders.setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
        Shaders.setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
        Shaders.setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
        Shaders.setProgramUniform1i(uniform_moonPhase, moonPhase);
        Shaders.setProgramUniform1i(uniform_frameCounter, frameCounter);
        Shaders.setProgramUniform1f(uniform_frameTime, frameTime);
        Shaders.setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
        Shaders.setProgramUniform1f(uniform_sunAngle, sunAngle);
        Shaders.setProgramUniform1f(uniform_shadowAngle, shadowAngle);
        Shaders.setProgramUniform1f(uniform_rainStrength, rainStrength);
        Shaders.setProgramUniform1f(uniform_aspectRatio, (float)renderWidth / (float)renderHeight);
        Shaders.setProgramUniform1f(uniform_viewWidth, renderWidth);
        Shaders.setProgramUniform1f(uniform_viewHeight, renderHeight);
        Shaders.setProgramUniform1f(uniform_near, 0.05f);
        Shaders.setProgramUniform1f(uniform_far, Shaders.mc.gameSettings.renderDistanceChunks * 16);
        Shaders.setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
        Shaders.setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
        Shaders.setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
        Shaders.setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
        Shaders.setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
        Shaders.setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
        if (hasShadowMap) {
            Shaders.setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
            Shaders.setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
            Shaders.setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
            Shaders.setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
        }
        Shaders.setProgramUniform1f(uniform_wetness, wetness);
        Shaders.setProgramUniform1f(uniform_eyeAltitude, eyePosY);
        Shaders.setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 0xFFFF, eyeBrightness >> 16);
        Shaders.setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
        Shaders.setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
        Shaders.setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
        Shaders.setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
        Shaders.setProgramUniform1f(uniform_nightVision, nightVision);
        Shaders.setProgramUniform1f(uniform_blindness, blindness);
        Shaders.setProgramUniform1f(uniform_screenBrightness, (float)Shaders.mc.gameSettings.gamma);
        Shaders.setProgramUniform1i(uniform_hideGUI, Shaders.mc.gameSettings.hideGUI ? 1 : 0);
        Shaders.setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
        Shaders.setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
        Shaders.setProgramUniform1f(uniform_playerMood, f);
        Shaders.setProgramUniform1i(uniform_renderStage, renderStage.ordinal());
        if (customUniforms != null) {
            customUniforms.update();
        }
    }

    private static void setImageUniforms() {
        if (bindImageTextures) {
            uniform_colorimg0.setValue(colorImageUnit[0]);
            uniform_colorimg1.setValue(colorImageUnit[1]);
            uniform_colorimg2.setValue(colorImageUnit[2]);
            uniform_colorimg3.setValue(colorImageUnit[3]);
            uniform_colorimg4.setValue(colorImageUnit[4]);
            uniform_colorimg5.setValue(colorImageUnit[5]);
            uniform_shadowcolorimg0.setValue(shadowColorImageUnit[0]);
            uniform_shadowcolorimg1.setValue(shadowColorImageUnit[1]);
        }
    }

    private static void updateAlphaBlend(Program program, Program program2) {
        GlBlendState glBlendState;
        GlAlphaState glAlphaState;
        if (program.getAlphaState() != null) {
            GlStateManager.unlockAlpha();
        }
        if (program.getBlendState() != null) {
            GlStateManager.unlockBlend();
        }
        if (program.getBlendStatesIndexed() != null) {
            GlStateManager.applyCurrentBlend();
        }
        if ((glAlphaState = program2.getAlphaState()) != null) {
            GlStateManager.lockAlpha(glAlphaState);
        }
        if ((glBlendState = program2.getBlendState()) != null) {
            GlStateManager.lockBlend(glBlendState);
        }
        if (program2.getBlendStatesIndexed() != null) {
            GlStateManager.setBlendsIndexed(program2.getBlendStatesIndexed());
        }
    }

    private static void setProgramUniform1i(ShaderUniform1i shaderUniform1i, int n) {
        shaderUniform1i.setValue(n);
    }

    private static void setProgramUniform2i(ShaderUniform2i shaderUniform2i, int n, int n2) {
        shaderUniform2i.setValue(n, n2);
    }

    private static void setProgramUniform1f(ShaderUniform1f shaderUniform1f, float f) {
        shaderUniform1f.setValue(f);
    }

    private static void setProgramUniform3f(ShaderUniform3f shaderUniform3f, float f, float f2, float f3) {
        shaderUniform3f.setValue(f, f2, f3);
    }

    private static void setProgramUniformMatrix4ARB(ShaderUniformM4 shaderUniformM4, boolean bl, FloatBuffer floatBuffer) {
        shaderUniformM4.setValue(bl, floatBuffer);
    }

    public static int getBufferIndex(String string) {
        int n = ShaderParser.getIndex(string, "colortex", 0, 15);
        if (n >= 0) {
            return n;
        }
        int n2 = ShaderParser.getIndex(string, "colorimg", 0, 15);
        if (n2 >= 0) {
            return n2;
        }
        if (string.equals("gcolor")) {
            return 1;
        }
        if (string.equals("gdepth")) {
            return 0;
        }
        if (string.equals("gnormal")) {
            return 1;
        }
        if (string.equals("composite")) {
            return 0;
        }
        if (string.equals("gaux1")) {
            return 1;
        }
        if (string.equals("gaux2")) {
            return 0;
        }
        if (string.equals("gaux3")) {
            return 1;
        }
        return string.equals("gaux4") ? 7 : -1;
    }

    private static int getTextureFormatFromString(String string) {
        string = string.trim();
        for (int i = 0; i < formatNames.length; ++i) {
            String string2 = formatNames[i];
            if (!string.equals(string2)) continue;
            return formatIds[i];
        }
        return 1;
    }

    public static int getImageFormat(int n) {
        switch (n) {
            case 6407: {
                return 0;
            }
            case 6408: {
                return 1;
            }
            case 8194: {
                return 0;
            }
            case 10768: {
                return 0;
            }
            case 32855: {
                return 1;
            }
            case 33319: {
                return 0;
            }
            case 35901: {
                return 1;
            }
        }
        return n;
    }

    private static void setupNoiseTexture() {
        if (noiseTexture == null && noiseTexturePath != null) {
            noiseTexture = Shaders.loadCustomTexture(15, noiseTexturePath);
        }
        if (noiseTexture == null) {
            noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
        }
    }

    private static void loadEntityDataMap() {
        Object object;
        mapBlockToEntityData = new IdentityHashMap<Block, Integer>(300);
        if (mapBlockToEntityData.isEmpty()) {
            for (ResourceLocation object2 : Registry.BLOCK.keySet()) {
                object = Registry.BLOCK.getOrDefault(object2);
                int n = Registry.BLOCK.getId((Block)object);
                mapBlockToEntityData.put((Block)object, n);
            }
        }
        Object object3 = null;
        try {
            object3 = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
        } catch (Exception exception) {
            // empty catch block
        }
        if (object3 != null) {
            try {
                String string;
                while ((string = ((BufferedReader)object3).readLine()) != null) {
                    object = patternLoadEntityDataMap.matcher(string);
                    if (((Matcher)object).matches()) {
                        String string2 = ((Matcher)object).group(1);
                        String string3 = ((Matcher)object).group(2);
                        int n = Integer.parseInt(string3);
                        ResourceLocation resourceLocation = new ResourceLocation(string2);
                        if (Registry.BLOCK.containsKey(resourceLocation)) {
                            Block block = Registry.BLOCK.getOrDefault(resourceLocation);
                            mapBlockToEntityData.put(block, n);
                            continue;
                        }
                        SMCLog.warning("Unknown block name %s", string2);
                        continue;
                    }
                    SMCLog.warning("unmatched %s\n", string);
                }
            } catch (Exception exception) {
                SMCLog.warning("Error parsing mc_Entity_x.txt");
            }
        }
        if (object3 != null) {
            try {
                ((BufferedReader)object3).close();
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private static IntBuffer fillIntBufferZero(IntBuffer intBuffer) {
        int n = intBuffer.limit();
        for (int i = intBuffer.position(); i < n; ++i) {
            intBuffer.put(i, 0);
        }
        return intBuffer;
    }

    private static DrawBuffers fillIntBufferZero(DrawBuffers drawBuffers) {
        int n = drawBuffers.limit();
        for (int i = drawBuffers.position(); i < n; ++i) {
            drawBuffers.put(i, 0);
        }
        return drawBuffers;
    }

    public static void uninit() {
        if (isShaderPackInitialized) {
            int n;
            Shaders.checkGLError("Shaders.uninit pre");
            for (n = 0; n < ProgramsAll.length; ++n) {
                Program program = ProgramsAll[n];
                if (program.getRef() != 0) {
                    ARBShaderObjects.glDeleteObjectARB(program.getRef());
                    Shaders.checkGLError("del programRef");
                }
                program.setRef(0);
                program.setId(0);
                program.setDrawBufSettings(null);
                program.setDrawBuffers(null);
                program.setCompositeMipmapSetting(0);
                ComputeProgram[] computeProgramArray = program.getComputePrograms();
                for (int i = 0; i < computeProgramArray.length; ++i) {
                    ComputeProgram computeProgram = computeProgramArray[i];
                    if (computeProgram.getRef() != 0) {
                        ARBShaderObjects.glDeleteObjectARB(computeProgram.getRef());
                        Shaders.checkGLError("del programRef");
                    }
                    computeProgram.setRef(0);
                    computeProgram.setId(0);
                }
                program.setComputePrograms(new ComputeProgram[0]);
            }
            hasDeferredPrograms = false;
            hasShadowcompPrograms = false;
            hasPreparePrograms = false;
            if (dfb != null) {
                dfb.delete();
                dfb = null;
                Shaders.checkGLError("del dfb");
            }
            if (sfb != null) {
                sfb.delete();
                sfb = null;
                Shaders.checkGLError("del sfb");
            }
            if (dfbDrawBuffers != null) {
                Shaders.fillIntBufferZero(dfbDrawBuffers);
            }
            if (sfbDrawBuffers != null) {
                Shaders.fillIntBufferZero(sfbDrawBuffers);
            }
            if (noiseTexture != null) {
                noiseTexture.deleteTexture();
                noiseTexture = null;
            }
            for (n = 0; n < colorImageUnit.length; ++n) {
                GlStateManager.bindImageTexture(colorImageUnit[n], 0, 0, false, 0, 35000, 32856);
            }
            SMCLog.info("Uninit");
            hasShadowMap = false;
            shouldSkipDefaultShadow = false;
            isShaderPackInitialized = false;
            Shaders.checkGLError("Shaders.uninit");
        }
    }

    public static void scheduleResize() {
        renderDisplayHeight = 0;
    }

    public static void scheduleResizeShadow() {
        needResizeShadow = true;
    }

    private static void resize() {
        renderDisplayWidth = mc.getMainWindow().getFramebufferWidth();
        renderDisplayHeight = mc.getMainWindow().getFramebufferHeight();
        renderWidth = Math.round((float)renderDisplayWidth * configRenderResMul);
        renderHeight = Math.round((float)renderDisplayHeight * configRenderResMul);
        Shaders.setupFrameBuffer();
    }

    private static void resizeShadow() {
        needResizeShadow = false;
        shadowMapWidth = Math.round((float)spShadowMapWidth * configShadowResMul);
        shadowMapHeight = Math.round((float)spShadowMapHeight * configShadowResMul);
        Shaders.setupShadowFrameBuffer();
    }

    private static void setupFrameBuffer() {
        if (dfb != null) {
            dfb.delete();
        }
        boolean[] blArray = ArrayUtils.newBoolean(usedDepthBuffers, true);
        boolean[] blArray2 = new boolean[usedDepthBuffers];
        boolean[] blArray3 = new boolean[usedColorBuffers];
        int[] nArray = (int[])(bindImageTextures ? colorImageUnit : null);
        dfb = new ShadersFramebuffer("dfb", renderWidth, renderHeight, usedColorBuffers, usedDepthBuffers, 8, blArray, blArray2, blArray3, colorBufferSizes, gbuffersFormat, colorTextureImageUnit, depthTextureImageUnit, nArray, dfbDrawBuffers);
        dfb.setup();
    }

    public static int getPixelFormat(int n) {
        switch (n) {
            case 33329: 
            case 33335: 
            case 36238: 
            case 36239: {
                return 0;
            }
            case 33330: 
            case 33336: 
            case 36220: 
            case 36221: {
                return 0;
            }
            case 33331: 
            case 33337: 
            case 36232: 
            case 36233: {
                return 0;
            }
            case 33332: 
            case 33338: 
            case 36214: 
            case 36215: {
                return 0;
            }
            case 33333: 
            case 33339: 
            case 36226: 
            case 36227: {
                return 0;
            }
            case 33334: 
            case 33340: 
            case 36208: 
            case 36209: {
                return 0;
            }
        }
        return 0;
    }

    private static void setupShadowFrameBuffer() {
        if (hasShadowMap) {
            isShadowPass = true;
            if (sfb != null) {
                sfb.delete();
            }
            DynamicDimension[] dynamicDimensionArray = new DynamicDimension[2];
            int[] nArray = (int[])(bindImageTextures ? shadowColorImageUnit : null);
            sfb = new ShadersFramebuffer("sfb", shadowMapWidth, shadowMapHeight, usedShadowColorBuffers, usedShadowDepthBuffers, 8, shadowFilterNearest, shadowHardwareFilteringEnabled, shadowColorFilterNearest, dynamicDimensionArray, shadowBuffersFormat, shadowColorTextureImageUnit, shadowDepthTextureImageUnit, nArray, sfbDrawBuffers);
            sfb.setup();
            isShadowPass = false;
        }
    }

    public static void beginRender(Minecraft minecraft, ActiveRenderInfo activeRenderInfo, float f, long l) {
        block14: {
            Shaders.checkGLError("pre beginRender");
            Shaders.checkWorldChanged(Shaders.mc.world);
            mc = minecraft;
            mc.getProfiler().startSection("init");
            entityRenderer = Shaders.mc.gameRenderer;
            if (!isShaderPackInitialized) {
                try {
                    Shaders.init();
                } catch (IllegalStateException illegalStateException) {
                    if (!Config.normalize(illegalStateException.getMessage()).equals("Function is not supported")) break block14;
                    Shaders.printChatAndLogError("[Shaders] Error: " + illegalStateException.getMessage());
                    illegalStateException.printStackTrace();
                    Shaders.setShaderPack(SHADER_PACK_NAME_NONE);
                    return;
                }
            }
        }
        if (mc.getMainWindow().getFramebufferWidth() != renderDisplayWidth || mc.getMainWindow().getFramebufferHeight() != renderDisplayHeight) {
            Shaders.resize();
        }
        if (needResizeShadow) {
            Shaders.resizeShadow();
        }
        if (++frameCounter >= 720720) {
            frameCounter = 0;
        }
        systemTime = System.currentTimeMillis();
        if (lastSystemTime == 0L) {
            lastSystemTime = systemTime;
        }
        diffSystemTime = systemTime - lastSystemTime;
        lastSystemTime = systemTime;
        frameTime = (float)diffSystemTime / 1000.0f;
        frameTimeCounter += frameTime;
        frameTimeCounter %= 3600.0f;
        pointOfViewChanged = pointOfView != Shaders.mc.gameSettings.getPointOfView();
        pointOfView = Shaders.mc.gameSettings.getPointOfView();
        GlStateManager.pushMatrix();
        ShadersRender.updateActiveRenderInfo(activeRenderInfo, minecraft, f);
        GlStateManager.popMatrix();
        ClientWorld clientWorld = Shaders.mc.world;
        if (clientWorld != null) {
            worldTime = clientWorld.getDayTime();
            diffWorldTime = (worldTime - lastWorldTime) % 24000L;
            if (diffWorldTime < 0L) {
                diffWorldTime += 24000L;
            }
            lastWorldTime = worldTime;
            moonPhase = clientWorld.getMoonPhase();
            rainStrength = clientWorld.getRainStrength(f);
            float f2 = (float)diffSystemTime * 0.01f;
            float f3 = (float)Math.exp(Math.log(0.5) * (double)f2 / (double)(wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
            wetness = wetness * f3 + rainStrength * (1.0f - f3);
            Entity entity2 = activeRenderInfo.getRenderViewEntity();
            if (entity2 != null) {
                Object object;
                isSleeping = entity2 instanceof LivingEntity && ((LivingEntity)entity2).isSleeping();
                eyePosY = (float)activeRenderInfo.getProjectedView().getY();
                eyeBrightness = mc.getRenderManager().getPackedLight(entity2, f);
                float f4 = (float)diffSystemTime * 0.01f;
                float f5 = (float)Math.exp(Math.log(0.5) * (double)f4 / (double)eyeBrightnessHalflife);
                eyeBrightnessFadeX = eyeBrightnessFadeX * f5 + (float)(eyeBrightness & 0xFFFF) * (1.0f - f5);
                eyeBrightnessFadeY = eyeBrightnessFadeY * f5 + (float)(eyeBrightness >> 16) * (1.0f - f5);
                FluidState fluidState = activeRenderInfo.getFluidState();
                isEyeInWater = fluidState.isTagged(FluidTags.WATER) ? 1 : (fluidState.isTagged(FluidTags.LAVA) ? 2 : 0);
                if (entity2 instanceof LivingEntity) {
                    object = (LivingEntity)entity2;
                    nightVision = 0.0f;
                    if (((LivingEntity)object).isPotionActive(Effects.NIGHT_VISION)) {
                        GameRenderer gameRenderer = entityRenderer;
                        nightVision = GameRenderer.getNightVisionBrightness((LivingEntity)object, f);
                    }
                    blindness = 0.0f;
                    if (((LivingEntity)object).isPotionActive(Effects.BLINDNESS)) {
                        int n = ((LivingEntity)object).getActivePotionEffect(Effects.BLINDNESS).getDuration();
                        blindness = Config.limit((float)n / 20.0f, 0.0f, 1.0f);
                    }
                }
                object = clientWorld.getSkyColor(entity2.getPosition(), f);
                object = CustomColors.getWorldSkyColor((Vector3d)object, clientWorld, entity2, f);
                skyColorR = (float)((Vector3d)object).x;
                skyColorG = (float)((Vector3d)object).y;
                skyColorB = (float)((Vector3d)object).z;
            }
        }
        isRenderingWorld = true;
        isCompositeRendered = false;
        isShadowPass = false;
        isHandRenderedMain = false;
        isHandRenderedOff = false;
        skipRenderHandMain = false;
        skipRenderHandOff = false;
        dfb.setColorBuffersFiltering(9729, 9729);
        Shaders.bindGbuffersTextures();
        dfb.bindColorImages(false);
        if (sfb != null) {
            sfb.bindColorImages(false);
        }
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
        Shaders.checkGLError("beginRender");
        ShadersRender.renderShadowMap(entityRenderer, activeRenderInfo, 0, f, l);
        mc.getProfiler().endSection();
        dfb.setColorTextures(false);
        Shaders.setRenderStage(RenderStage.NONE);
        Shaders.checkGLError("end beginRender");
    }

    private static void bindGbuffersTextures() {
        Shaders.bindTextures(4, customTexturesGbuffers);
    }

    private static void bindTextures(int n, ICustomTexture[] iCustomTextureArray) {
        if (sfb != null) {
            sfb.bindColorTextures(0);
            sfb.bindDepthTextures(shadowDepthTextureImageUnit);
        }
        dfb.bindColorTextures(n);
        dfb.bindDepthTextures(depthTextureImageUnit);
        if (noiseTextureEnabled) {
            GlStateManager.activeTexture(33984 + noiseTexture.getTextureUnit());
            GlStateManager.bindTexture(noiseTexture.getTextureId());
            GlStateManager.activeTexture(33984);
        }
        Shaders.bindCustomTextures(iCustomTextureArray);
    }

    public static void checkWorldChanged(ClientWorld clientWorld) {
        if (currentWorld != clientWorld) {
            ClientWorld clientWorld2 = currentWorld;
            currentWorld = clientWorld;
            if (currentWorld == null) {
                cameraPositionX = 0.0;
                cameraPositionY = 0.0;
                cameraPositionZ = 0.0;
                previousCameraPositionX = 0.0;
                previousCameraPositionY = 0.0;
                previousCameraPositionZ = 0.0;
            }
            Shaders.setCameraOffset(mc.getRenderViewEntity());
            int n = WorldUtils.getDimensionId(clientWorld2);
            int n2 = WorldUtils.getDimensionId(clientWorld);
            if (n2 != n) {
                boolean bl = shaderPackDimensions.contains(n);
                boolean bl2 = shaderPackDimensions.contains(n2);
                if (bl || bl2) {
                    Shaders.uninit();
                }
            }
            Smoother.resetValues();
        }
    }

    public static void beginRenderPass(float f, long l) {
        if (!isShadowPass) {
            dfb.bindFramebuffer();
            GL11.glViewport(0, 0, renderWidth, renderHeight);
            GlState.setDrawBuffers(null);
            ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
            Shaders.useProgram(ProgramTextured);
            Shaders.checkGLError("end beginRenderPass");
        }
    }

    public static void setViewport(int n, int n2, int n3, int n4) {
        GlStateManager.colorMask(true, true, true, true);
        if (isShadowPass) {
            GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
        } else {
            GL11.glViewport(0, 0, renderWidth, renderHeight);
            dfb.bindFramebuffer();
            isRenderingDfb = true;
            GlStateManager.enableCull();
            GlStateManager.enableDepthTest();
            GlState.setDrawBuffers(drawBuffersNone);
            Shaders.useProgram(ProgramTextured);
            Shaders.checkGLError("beginRenderPass");
        }
    }

    public static void setFogMode(int n) {
        fogMode = n;
        if (fogEnabled) {
            Shaders.setProgramUniform1i(uniform_fogMode, n);
        }
    }

    public static void setFogColor(float f, float f2, float f3) {
        fogColorR = f;
        fogColorG = f2;
        fogColorB = f3;
        Shaders.setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
    }

    public static void setClearColor(float f, float f2, float f3, float f4) {
        clearColor.set(f, f2, f3, 1.0f);
    }

    public static void clearRenderBuffer() {
        if (isShadowPass) {
            Shaders.checkGLError("shadow clear pre");
            sfb.clearDepthBuffer(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
            Shaders.checkGLError("shadow clear");
        } else {
            Shaders.checkGLError("clear pre");
            Vector4f[] vector4fArray = new Vector4f[usedColorBuffers];
            for (int i = 0; i < vector4fArray.length; ++i) {
                vector4fArray[i] = Shaders.getBufferClearColor(i);
            }
            dfb.clearColorBuffers(gbuffersClear, vector4fArray);
            dfb.setDrawBuffers();
            Shaders.checkFramebufferStatus("clear");
            Shaders.checkGLError("clear");
        }
    }

    public static void renderPrepare() {
        if (hasPreparePrograms) {
            Shaders.renderPrepareComposites();
            Shaders.bindGbuffersTextures();
            dfb.setDrawBuffers();
            dfb.setColorTextures(false);
        }
    }

    private static Vector4f getBufferClearColor(int n) {
        Vector4f vector4f = gbuffersClearColor[n];
        if (vector4f != null) {
            return vector4f;
        }
        if (n == 0) {
            return clearColor;
        }
        return n == 1 ? CLEAR_COLOR_1 : CLEAR_COLOR_0;
    }

    public static void setCamera(MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        Entity entity2 = activeRenderInfo.getRenderViewEntity();
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        double d = vector3d.x;
        double d2 = vector3d.y;
        double d3 = vector3d.z;
        Shaders.updateCameraOffset(entity2);
        cameraPositionX = d - (double)cameraOffsetX;
        cameraPositionY = d2;
        cameraPositionZ = d3 - (double)cameraOffsetZ;
        Shaders.updateProjectionMatrix();
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        Matrix4f matrix4f2 = new Matrix4f(matrix4f);
        matrix4f2.transpose();
        matrix4f2.write(tempMat);
        modelView.position(0);
        modelView.put(tempMat);
        SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
        modelView.position(0);
        modelViewInverse.position(0);
        Shaders.checkGLError("setCamera");
    }

    public static void updateProjectionMatrix() {
        GL43.glGetFloatv(2983, (FloatBuffer)projection.position(0));
        SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
        projection.position(0);
        projectionInverse.position(0);
    }

    private static void updateShadowProjectionMatrix() {
        GL43.glGetFloatv(2983, (FloatBuffer)shadowProjection.position(0));
        SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
        shadowProjection.position(0);
        shadowProjectionInverse.position(0);
    }

    private static void updateCameraOffset(Entity entity2) {
        double d = Math.abs(cameraPositionX - previousCameraPositionX);
        double d2 = Math.abs(cameraPositionZ - previousCameraPositionZ);
        double d3 = Math.abs(cameraPositionX);
        double d4 = Math.abs(cameraPositionZ);
        if (d > 1000.0 || d2 > 1000.0 || d3 > 1000000.0 || d4 > 1000000.0) {
            Shaders.setCameraOffset(entity2);
        }
    }

    private static void setCameraOffset(Entity entity2) {
        if (entity2 == null) {
            cameraOffsetX = 0;
            cameraOffsetZ = 0;
        } else {
            cameraOffsetX = (int)entity2.getPosX() / 1000 * 1000;
            cameraOffsetZ = (int)entity2.getPosZ() / 1000 * 1000;
        }
    }

    public static void setCameraShadow(MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        float f2;
        float f3;
        float f4;
        Entity entity2 = activeRenderInfo.getRenderViewEntity();
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        double d = vector3d.x;
        double d2 = vector3d.y;
        double d3 = vector3d.z;
        Shaders.updateCameraOffset(entity2);
        cameraPositionX = d - (double)cameraOffsetX;
        cameraPositionY = d2;
        cameraPositionZ = d3 - (double)cameraOffsetZ;
        GL43.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
        GL43.glMatrixMode(5889);
        GL43.glLoadIdentity();
        if (shadowMapIsOrtho) {
            GL43.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05f, 256.0);
        } else {
            GlStateManager.multMatrix(Matrix4f.perspective(shadowMapFOV, (float)shadowMapWidth / (float)shadowMapHeight, 0.05f, 256.0f));
        }
        matrixStack.translate(0.0, 0.0, -100.0);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
        celestialAngle = Shaders.mc.world.func_242415_f(f);
        sunAngle = celestialAngle < 0.75f ? celestialAngle + 0.25f : celestialAngle - 0.75f;
        float f5 = celestialAngle * -360.0f;
        float f6 = f4 = shadowAngleInterval > 0.0f ? f5 % shadowAngleInterval - shadowAngleInterval * 0.5f : 0.0f;
        if ((double)sunAngle <= 0.5) {
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f5 - f4));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(sunPathRotation));
            shadowAngle = sunAngle;
        } else {
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f5 + 180.0f - f4));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(sunPathRotation));
            shadowAngle = sunAngle - 0.5f;
        }
        if (shadowMapIsOrtho) {
            f3 = shadowIntervalSize;
            f2 = f3 / 2.0f;
            matrixStack.translate((float)d % f3 - f2, (float)d2 % f3 - f2, (float)d3 % f3 - f2);
        }
        f3 = sunAngle * ((float)Math.PI * 2);
        f2 = (float)Math.cos(f3);
        float f7 = (float)Math.sin(f3);
        float f8 = sunPathRotation * ((float)Math.PI * 2);
        float f9 = f2;
        float f10 = f7 * (float)Math.cos(f8);
        float f11 = f7 * (float)Math.sin(f8);
        if ((double)sunAngle > 0.5) {
            f9 = -f2;
            f10 = -f10;
            f11 = -f11;
        }
        Shaders.shadowLightPositionVector[0] = f9;
        Shaders.shadowLightPositionVector[1] = f10;
        Shaders.shadowLightPositionVector[2] = f11;
        Shaders.shadowLightPositionVector[3] = 0.0f;
        Shaders.updateShadowProjectionMatrix();
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        matrix4f.write((FloatBuffer)shadowModelView.position(0));
        SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
        shadowModelView.position(0);
        shadowModelViewInverse.position(0);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
        Shaders.setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
        Shaders.setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
        Shaders.setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
        Shaders.setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
        Shaders.setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
        Shaders.checkGLError("setCamera");
    }

    public static void preCelestialRotate(MatrixStack matrixStack) {
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(sunPathRotation * 1.0f));
        Shaders.checkGLError("preCelestialRotate");
    }

    public static void postCelestialRotate(MatrixStack matrixStack) {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        Matrix4f matrix4f2 = new Matrix4f(matrix4f);
        matrix4f2.transpose();
        matrix4f2.write(tempMat);
        SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
        SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
        System.arraycopy(shadowAngle == sunAngle ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
        Shaders.setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
        Shaders.setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
        Shaders.setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
        if (customUniforms != null) {
            customUniforms.update();
        }
        Shaders.checkGLError("postCelestialRotate");
    }

    public static void setUpPosition(MatrixStack matrixStack) {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        Matrix4f matrix4f2 = new Matrix4f(matrix4f);
        matrix4f2.transpose();
        matrix4f2.write(tempMat);
        SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
        Shaders.setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
        if (customUniforms != null) {
            customUniforms.update();
        }
    }

    public static void drawComposite() {
        GL43.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Shaders.drawCompositeQuad();
        int n = activeProgram.getCountInstances();
        if (n > 1) {
            for (int i = 1; i < n; ++i) {
                uniform_instanceId.setValue(i);
                Shaders.drawCompositeQuad();
            }
            uniform_instanceId.setValue(0);
        }
    }

    private static void drawCompositeQuad() {
        if (!Shaders.canRenderQuads()) {
            GL43.glBegin(5);
            GL43.glTexCoord2f(0.0f, 0.0f);
            GL43.glVertex3f(0.0f, 0.0f, 0.0f);
            GL43.glTexCoord2f(1.0f, 0.0f);
            GL43.glVertex3f(1.0f, 0.0f, 0.0f);
            GL43.glTexCoord2f(0.0f, 1.0f);
            GL43.glVertex3f(0.0f, 1.0f, 0.0f);
            GL43.glTexCoord2f(1.0f, 1.0f);
            GL43.glVertex3f(1.0f, 1.0f, 0.0f);
            GL43.glEnd();
        } else {
            GL43.glBegin(7);
            GL43.glTexCoord2f(0.0f, 0.0f);
            GL43.glVertex3f(0.0f, 0.0f, 0.0f);
            GL43.glTexCoord2f(1.0f, 0.0f);
            GL43.glVertex3f(1.0f, 0.0f, 0.0f);
            GL43.glTexCoord2f(1.0f, 1.0f);
            GL43.glVertex3f(1.0f, 1.0f, 0.0f);
            GL43.glTexCoord2f(0.0f, 1.0f);
            GL43.glVertex3f(0.0f, 1.0f, 0.0f);
            GL43.glEnd();
        }
    }

    public static void renderDeferred() {
        if (!isShadowPass) {
            boolean bl = Shaders.checkBufferFlip(dfb, ProgramDeferredPre);
            if (hasDeferredPrograms) {
                Shaders.checkGLError("pre-render Deferred");
                Shaders.renderDeferredComposites();
                bl = true;
            }
            if (bl) {
                Shaders.bindGbuffersTextures();
                dfb.setColorTextures(false);
                DrawBuffers drawBuffers = ProgramWater.getDrawBuffers() != null ? ProgramWater.getDrawBuffers() : dfb.getDrawBuffers();
                GlState.setDrawBuffers(drawBuffers);
                GlStateManager.activeTexture(33984);
                mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            }
        }
    }

    public static void renderCompositeFinal() {
        if (!isShadowPass) {
            Shaders.checkBufferFlip(dfb, ProgramCompositePre);
            Shaders.checkGLError("pre-render CompositeFinal");
            Shaders.renderComposites();
        }
    }

    private static boolean checkBufferFlip(ShadersFramebuffer shadersFramebuffer, Program program) {
        boolean bl = false;
        Boolean[] booleanArray = program.getBuffersFlip();
        for (int i = 0; i < usedColorBuffers; ++i) {
            if (!Config.isTrue(booleanArray[i])) continue;
            shadersFramebuffer.flipColorTexture(i);
            bl = true;
        }
        return bl;
    }

    private static void renderComposites() {
        if (!isShadowPass) {
            Shaders.renderComposites(ProgramsComposite, true, customTexturesComposite);
        }
    }

    private static void renderDeferredComposites() {
        if (!isShadowPass) {
            Shaders.renderComposites(ProgramsDeferred, false, customTexturesDeferred);
        }
    }

    public static void renderPrepareComposites() {
        Shaders.renderComposites(ProgramsPrepare, false, customTexturesPrepare);
    }

    private static void renderComposites(Program[] programArray, boolean bl, ICustomTexture[] iCustomTextureArray) {
        Shaders.renderComposites(dfb, programArray, bl, iCustomTextureArray);
    }

    public static void renderShadowComposites() {
        Shaders.renderComposites(sfb, ProgramsShadowcomp, false, customTexturesShadowcomp);
    }

    private static void renderComposites(ShadersFramebuffer shadersFramebuffer, Program[] programArray, boolean bl, ICustomTexture[] iCustomTextureArray) {
        GL43.glPushMatrix();
        GL43.glLoadIdentity();
        GL43.glMatrixMode(5889);
        GL43.glPushMatrix();
        GL43.glLoadIdentity();
        GL43.glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
        GL43.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        Shaders.bindTextures(0, iCustomTextureArray);
        shadersFramebuffer.bindColorImages(false);
        shadersFramebuffer.setColorTextures(true);
        shadersFramebuffer.setDepthTexture();
        shadersFramebuffer.setDrawBuffers();
        Shaders.checkGLError("pre-composite");
        for (int i = 0; i < programArray.length; ++i) {
            Program program = programArray[i];
            Shaders.dispatchComputes(shadersFramebuffer, program.getComputePrograms());
            if (program.getId() == 0) continue;
            Shaders.useProgram(program);
            Shaders.checkGLError(program.getName());
            if (program.hasCompositeMipmaps()) {
                shadersFramebuffer.genCompositeMipmap(program.getCompositeMipmapSetting());
            }
            Shaders.preDrawComposite(shadersFramebuffer, program);
            Shaders.drawComposite();
            Shaders.postDrawComposite(shadersFramebuffer, program);
            shadersFramebuffer.flipColorTextures(program.getToggleColorTextures());
        }
        Shaders.checkGLError("composite");
        if (bl) {
            Shaders.renderFinal();
            isCompositeRendered = true;
        }
        GlStateManager.enableTexture();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        GL43.glPopMatrix();
        GL43.glMatrixMode(5888);
        GL43.glPopMatrix();
        Shaders.useProgram(ProgramNone);
    }

    private static void preDrawComposite(ShadersFramebuffer shadersFramebuffer, Program program) {
        Object object;
        int n = shadersFramebuffer.getWidth();
        int n2 = shadersFramebuffer.getHeight();
        if (program.getDrawSize() != null) {
            object = program.getDrawSize().getDimension(n, n2);
            n = ((Dimension)object).width;
            n2 = ((Dimension)object).height;
            FixedFramebuffer fixedFramebuffer = shadersFramebuffer.getFixedFramebuffer(n, n2, program.getDrawBuffers(), true);
            fixedFramebuffer.bindFramebuffer();
            GL43.glViewport(0, 0, n, n2);
        }
        if ((object = program.getRenderScale()) != null) {
            int n3 = (int)((float)n * ((RenderScale)object).getOffsetX());
            int n4 = (int)((float)n2 * ((RenderScale)object).getOffsetY());
            int n5 = (int)((float)n * ((RenderScale)object).getScale());
            int n6 = (int)((float)n2 * ((RenderScale)object).getScale());
            GL43.glViewport(n3, n4, n5, n6);
        }
    }

    private static void postDrawComposite(ShadersFramebuffer shadersFramebuffer, Program program) {
        RenderScale renderScale;
        if (program.getDrawSize() != null) {
            shadersFramebuffer.bindFramebuffer();
            GL43.glViewport(0, 0, shadersFramebuffer.getWidth(), shadersFramebuffer.getHeight());
        }
        if ((renderScale = activeProgram.getRenderScale()) != null) {
            GL43.glViewport(0, 0, shadersFramebuffer.getWidth(), shadersFramebuffer.getHeight());
        }
    }

    public static void dispatchComputes(ShadersFramebuffer shadersFramebuffer, ComputeProgram[] computeProgramArray) {
        for (int i = 0; i < computeProgramArray.length; ++i) {
            ComputeProgram computeProgram = computeProgramArray[i];
            Shaders.dispatchCompute(computeProgram);
            if (!computeProgram.hasCompositeMipmaps()) continue;
            shadersFramebuffer.genCompositeMipmap(computeProgram.getCompositeMipmapSetting());
        }
    }

    public static void dispatchCompute(ComputeProgram computeProgram) {
        if (dfb != null) {
            ARBShaderObjects.glUseProgramObjectARB(computeProgram.getId());
            if (Shaders.checkGLError("useComputeProgram") != 0) {
                computeProgram.setId(0);
            } else {
                shaderUniforms.setProgram(computeProgram.getId());
                if (customUniforms != null) {
                    customUniforms.setProgram(computeProgram.getId());
                }
                Shaders.setProgramUniforms(computeProgram.getProgramStage());
                Shaders.setImageUniforms();
                dfb.bindColorImages(false);
                Vector3i vector3i = computeProgram.getWorkGroups();
                if (vector3i == null) {
                    Vector2f vector2f = computeProgram.getWorkGroupsRender();
                    if (vector2f == null) {
                        vector2f = new Vector2f(1.0f, 1.0f);
                    }
                    int n = (int)Math.ceil((float)renderWidth * vector2f.x);
                    int n2 = (int)Math.ceil((float)renderHeight * vector2f.y);
                    Vector3i vector3i2 = computeProgram.getLocalSize();
                    int n3 = (int)Math.ceil(1.0 * (double)n / (double)vector3i2.getX());
                    int n4 = (int)Math.ceil(1.0 * (double)n2 / (double)vector3i2.getY());
                    vector3i = new Vector3i(n3, n4, 1);
                }
                GL43.glMemoryBarrier(40);
                GL43.glDispatchCompute(vector3i.getX(), vector3i.getY(), vector3i.getZ());
                GL43.glMemoryBarrier(40);
                Shaders.checkGLError("compute");
            }
        }
    }

    private static void renderFinal() {
        Shaders.dispatchComputes(dfb, ProgramFinal.getComputePrograms());
        isRenderingDfb = false;
        mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_COLOR_ATTACHMENT0, 3553, mc.getFramebuffer().func_242996_f(), 0);
        GL43.glViewport(0, 0, mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
        GlStateManager.depthMask(true);
        GL43.glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), 1.0f);
        GL43.glClear(16640);
        GL43.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        Shaders.checkGLError("pre-final");
        Shaders.useProgram(ProgramFinal);
        Shaders.checkGLError("final");
        if (ProgramFinal.hasCompositeMipmaps()) {
            dfb.genCompositeMipmap(ProgramFinal.getCompositeMipmapSetting());
        }
        Shaders.drawComposite();
        Shaders.checkGLError("renderCompositeFinal");
    }

    public static void endRender() {
        if (isShadowPass) {
            Shaders.checkGLError("shadow endRender");
        } else {
            if (!isCompositeRendered) {
                Shaders.renderCompositeFinal();
            }
            isRenderingWorld = false;
            GlStateManager.colorMask(true, true, true, true);
            Shaders.useProgram(ProgramNone);
            Shaders.setRenderStage(RenderStage.NONE);
            RenderHelper.disableStandardItemLighting();
            Shaders.checkGLError("endRender end");
        }
    }

    public static void beginSky() {
        isRenderingSky = true;
        fogEnabled = true;
        Shaders.useProgram(ProgramSkyTextured);
        Shaders.pushEntity(-2, 0);
        Shaders.setRenderStage(RenderStage.SKY);
    }

    public static void setSkyColor(Vector3d vector3d) {
        skyColorR = (float)vector3d.x;
        skyColorG = (float)vector3d.y;
        skyColorB = (float)vector3d.z;
        Shaders.setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
    }

    public static void drawHorizon(MatrixStack matrixStack) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float f = Shaders.mc.gameSettings.renderDistanceChunks * 16;
        double d = (double)f * 0.9238;
        double d2 = (double)f * 0.3826;
        double d3 = -d2;
        double d4 = -d;
        double d5 = 16.0;
        double d6 = -cameraPositionY + currentWorld.getWorldInfo().getVoidFogHeight() + 12.0 - 16.0;
        if (cameraPositionY < currentWorld.getWorldInfo().getVoidFogHeight()) {
            d6 = -4.0;
        }
        GlStateManager.pushMatrix();
        GlStateManager.multMatrix(matrixStack.getLast().getMatrix());
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(d3, d6, d4).endVertex();
        bufferBuilder.pos(d3, d5, d4).endVertex();
        bufferBuilder.pos(d4, d5, d3).endVertex();
        bufferBuilder.pos(d4, d6, d3).endVertex();
        bufferBuilder.pos(d4, d6, d3).endVertex();
        bufferBuilder.pos(d4, d5, d3).endVertex();
        bufferBuilder.pos(d4, d5, d2).endVertex();
        bufferBuilder.pos(d4, d6, d2).endVertex();
        bufferBuilder.pos(d4, d6, d2).endVertex();
        bufferBuilder.pos(d4, d5, d2).endVertex();
        bufferBuilder.pos(d3, d5, d).endVertex();
        bufferBuilder.pos(d3, d6, d).endVertex();
        bufferBuilder.pos(d3, d6, d).endVertex();
        bufferBuilder.pos(d3, d5, d).endVertex();
        bufferBuilder.pos(d2, d5, d).endVertex();
        bufferBuilder.pos(d2, d6, d).endVertex();
        bufferBuilder.pos(d2, d6, d).endVertex();
        bufferBuilder.pos(d2, d5, d).endVertex();
        bufferBuilder.pos(d, d5, d2).endVertex();
        bufferBuilder.pos(d, d6, d2).endVertex();
        bufferBuilder.pos(d, d6, d2).endVertex();
        bufferBuilder.pos(d, d5, d2).endVertex();
        bufferBuilder.pos(d, d5, d3).endVertex();
        bufferBuilder.pos(d, d6, d3).endVertex();
        bufferBuilder.pos(d, d6, d3).endVertex();
        bufferBuilder.pos(d, d5, d3).endVertex();
        bufferBuilder.pos(d2, d5, d4).endVertex();
        bufferBuilder.pos(d2, d6, d4).endVertex();
        bufferBuilder.pos(d2, d6, d4).endVertex();
        bufferBuilder.pos(d2, d5, d4).endVertex();
        bufferBuilder.pos(d3, d5, d4).endVertex();
        bufferBuilder.pos(d3, d6, d4).endVertex();
        bufferBuilder.pos(d4, d6, d4).endVertex();
        bufferBuilder.pos(d4, d6, d).endVertex();
        bufferBuilder.pos(d, d6, d).endVertex();
        bufferBuilder.pos(d, d6, d4).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    public static void preSkyList(MatrixStack matrixStack) {
        Shaders.setUpPosition(matrixStack);
        GL11.glColor3f(fogColorR, fogColorG, fogColorB);
        Shaders.drawHorizon(matrixStack);
        GL11.glColor3f(skyColorR, skyColorG, skyColorB);
    }

    public static void endSky() {
        isRenderingSky = false;
        Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
        Shaders.popEntity();
        Shaders.setRenderStage(RenderStage.NONE);
    }

    public static void beginUpdateChunks() {
        Shaders.checkGLError("beginUpdateChunks1");
        Shaders.checkFramebufferStatus("beginUpdateChunks1");
        if (!isShadowPass) {
            Shaders.useProgram(ProgramTerrain);
        }
        Shaders.checkGLError("beginUpdateChunks2");
        Shaders.checkFramebufferStatus("beginUpdateChunks2");
    }

    public static void endUpdateChunks() {
        Shaders.checkGLError("endUpdateChunks1");
        Shaders.checkFramebufferStatus("endUpdateChunks1");
        if (!isShadowPass) {
            Shaders.useProgram(ProgramTerrain);
        }
        Shaders.checkGLError("endUpdateChunks2");
        Shaders.checkFramebufferStatus("endUpdateChunks2");
    }

    public static boolean shouldRenderClouds(GameSettings gameSettings) {
        if (!shaderPackLoaded) {
            return false;
        }
        Shaders.checkGLError("shouldRenderClouds");
        return isShadowPass ? configCloudShadow : gameSettings.cloudOption != CloudOption.OFF;
    }

    public static void beginClouds() {
        fogEnabled = true;
        Shaders.pushEntity(-3, 0);
        Shaders.useProgram(ProgramClouds);
        Shaders.setRenderStage(RenderStage.CLOUDS);
    }

    public static void endClouds() {
        Shaders.disableFog();
        Shaders.popEntity();
        Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
        Shaders.setRenderStage(RenderStage.NONE);
    }

    public static void beginEntities() {
        if (isRenderingWorld) {
            Shaders.useProgram(ProgramEntities);
            Shaders.setRenderStage(RenderStage.ENTITIES);
        }
    }

    public static void nextEntity(Entity entity2) {
        if (isRenderingWorld) {
            if (entity2.isGlowing()) {
                Shaders.useProgram(ProgramEntitiesGlowing);
            } else {
                Shaders.useProgram(ProgramEntities);
            }
            Shaders.setEntityId(entity2);
        }
    }

    public static void setEntityId(Entity entity2) {
        if (uniform_entityId.isDefined()) {
            int n = EntityUtils.getEntityIdByClass(entity2);
            int n2 = EntityAliases.getEntityAliasId(n);
            uniform_entityId.setValue(n2);
        }
    }

    public static void beginSpiderEyes() {
        if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
            Shaders.useProgram(ProgramSpiderEyes);
            GlStateManager.enableAlphaTest();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
        }
    }

    public static void endSpiderEyes() {
        if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
            Shaders.useProgram(ProgramEntities);
            GlStateManager.disableAlphaTest();
        }
    }

    public static void endEntities() {
        if (isRenderingWorld) {
            Shaders.setEntityId(null);
            Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
        }
    }

    public static void beginEntitiesGlowing() {
        if (isRenderingWorld) {
            isEntitiesGlowing = true;
        }
    }

    public static void endEntitiesGlowing() {
        if (isRenderingWorld) {
            isEntitiesGlowing = false;
        }
    }

    public static void setEntityColor(float f, float f2, float f3, float f4) {
        if (isRenderingWorld && !isShadowPass) {
            uniform_entityColor.setValue(f, f2, f3, f4);
        }
    }

    public static void beginLivingDamage() {
        if (isRenderingWorld) {
            ShadersTex.bindTexture(defaultTexture);
            if (!isShadowPass) {
                GlState.setDrawBuffers(drawBuffersColorAtt[0]);
            }
        }
    }

    public static void endLivingDamage() {
        if (isRenderingWorld && !isShadowPass) {
            GlState.setDrawBuffers(ProgramEntities.getDrawBuffers());
        }
    }

    public static void beginBlockEntities() {
        if (isRenderingWorld) {
            Shaders.checkGLError("beginBlockEntities");
            Shaders.useProgram(ProgramBlock);
            Shaders.setRenderStage(RenderStage.BLOCK_ENTITIES);
        }
    }

    public static void nextBlockEntity(TileEntity tileEntity) {
        if (isRenderingWorld) {
            Shaders.checkGLError("nextBlockEntity");
            Shaders.useProgram(ProgramBlock);
            Shaders.setBlockEntityId(tileEntity);
        }
    }

    public static void setBlockEntityId(TileEntity tileEntity) {
        if (uniform_blockEntityId.isDefined()) {
            int n = Shaders.getBlockEntityId(tileEntity);
            uniform_blockEntityId.setValue(n);
        }
    }

    private static int getBlockEntityId(TileEntity tileEntity) {
        if (tileEntity == null) {
            return 1;
        }
        BlockState blockState = tileEntity.getBlockState();
        return BlockAliases.getAliasBlockId(blockState);
    }

    public static void endBlockEntities() {
        if (isRenderingWorld) {
            Shaders.checkGLError("endBlockEntities");
            Shaders.setBlockEntityId(null);
            Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
            ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
        }
    }

    public static void beginLitParticles() {
        Shaders.useProgram(ProgramTexturedLit);
    }

    public static void beginParticles() {
        Shaders.useProgram(ProgramTextured);
        Shaders.setRenderStage(RenderStage.PARTICLES);
    }

    public static void endParticles() {
        Shaders.useProgram(ProgramTexturedLit);
        Shaders.setRenderStage(RenderStage.NONE);
    }

    public static void readCenterDepth() {
        if (!isShadowPass && centerDepthSmoothEnabled) {
            tempDirectFloatBuffer.clear();
            GL43.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
            centerDepth = tempDirectFloatBuffer.get(0);
            float f = (float)diffSystemTime * 0.01f;
            float f2 = (float)Math.exp(Math.log(0.5) * (double)f / (double)centerDepthSmoothHalflife);
            centerDepthSmooth = centerDepthSmooth * f2 + centerDepth * (1.0f - f2);
        }
    }

    public static void beginWeather() {
        if (!isShadowPass) {
            GlStateManager.enableDepthTest();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableAlphaTest();
            Shaders.useProgram(ProgramWeather);
            Shaders.setRenderStage(RenderStage.RAIN_SNOW);
        }
    }

    public static void endWeather() {
        GlStateManager.disableBlend();
        Shaders.useProgram(ProgramTexturedLit);
        Shaders.setRenderStage(RenderStage.NONE);
    }

    public static void preRenderHand() {
        if (!isShadowPass && usedDepthBuffers >= 3) {
            GlStateManager.activeTexture(33996);
            GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
            GlStateManager.activeTexture(33984);
        }
    }

    public static void preWater() {
        if (usedDepthBuffers >= 2) {
            GlStateManager.activeTexture(33995);
            Shaders.checkGLError("pre copy depth");
            GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
            Shaders.checkGLError("copy depth");
            GlStateManager.activeTexture(33984);
        }
        ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
    }

    public static void beginWater() {
        if (isRenderingWorld) {
            if (!isShadowPass) {
                Shaders.renderDeferred();
                Shaders.useProgram(ProgramWater);
                GlStateManager.enableBlend();
                GlStateManager.depthMask(true);
            } else {
                GlStateManager.depthMask(true);
            }
        }
    }

    public static void endWater() {
        if (isRenderingWorld) {
            if (isShadowPass) {
                // empty if block
            }
            Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
        }
    }

    public static void applyHandDepth(MatrixStack matrixStack) {
        if ((double)configHandDepthMul != 1.0) {
            matrixStack.scale(1.0f, 1.0f, configHandDepthMul);
        }
    }

    public static void beginHand(MatrixStack matrixStack, boolean bl) {
        GL43.glMatrixMode(5888);
        GL43.glPushMatrix();
        GL43.glMatrixMode(5889);
        GL43.glPushMatrix();
        GL43.glMatrixMode(5888);
        matrixStack.push();
        if (bl) {
            Shaders.useProgram(ProgramHandWater);
        } else {
            Shaders.useProgram(ProgramHand);
        }
        Shaders.checkGLError("beginHand");
        Shaders.checkFramebufferStatus("beginHand");
    }

    public static void endHand(MatrixStack matrixStack) {
        Shaders.checkGLError("pre endHand");
        Shaders.checkFramebufferStatus("pre endHand");
        matrixStack.pop();
        GL43.glMatrixMode(5889);
        GL43.glPopMatrix();
        GL43.glMatrixMode(5888);
        GL43.glPopMatrix();
        GlStateManager.blendFunc(770, 771);
        Shaders.checkGLError("endHand");
    }

    public static void beginFPOverlay() {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    public static void endFPOverlay() {
    }

    public static void glEnableWrapper(int n) {
        GL43.glEnable(n);
        if (n == 3553) {
            Shaders.enableTexture2D();
        } else if (n == 2912) {
            Shaders.enableFog();
        }
    }

    public static void glDisableWrapper(int n) {
        GL43.glDisable(n);
        if (n == 3553) {
            Shaders.disableTexture2D();
        } else if (n == 2912) {
            Shaders.disableFog();
        }
    }

    public static void sglEnableT2D(int n) {
        GL43.glEnable(n);
        Shaders.enableTexture2D();
    }

    public static void sglDisableT2D(int n) {
        GL43.glDisable(n);
        Shaders.disableTexture2D();
    }

    public static void sglEnableFog(int n) {
        GL43.glEnable(n);
        Shaders.enableFog();
    }

    public static void sglDisableFog(int n) {
        GL43.glDisable(n);
        Shaders.disableFog();
    }

    public static void enableTexture2D() {
        if (isRenderingSky) {
            Shaders.useProgram(ProgramSkyTextured);
        } else if (activeProgram == ProgramBasic) {
            Shaders.useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
        }
    }

    public static void disableTexture2D() {
        if (isRenderingSky) {
            Shaders.useProgram(ProgramSkyBasic);
        } else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
            Shaders.useProgram(ProgramBasic);
        }
    }

    public static void pushProgram() {
        programStack.push(activeProgram);
    }

    public static void popProgram() {
        Program program = programStack.pop();
        Shaders.useProgram(program);
    }

    public static void beginLeash() {
        Shaders.pushProgram();
        Shaders.useProgram(ProgramBasic);
    }

    public static void endLeash() {
        Shaders.popProgram();
    }

    public static void enableFog() {
        fogEnabled = true;
        Shaders.setProgramUniform1i(uniform_fogMode, fogMode);
        Shaders.setProgramUniform1f(uniform_fogDensity, fogDensity);
    }

    public static void disableFog() {
        fogEnabled = false;
        Shaders.setProgramUniform1i(uniform_fogMode, 0);
    }

    public static void setFogMode(GlStateManager.FogMode fogMode) {
        Shaders.setFogMode(fogMode.param);
    }

    public static void setFogDensity(float f) {
        fogDensity = f;
        if (fogEnabled) {
            Shaders.setProgramUniform1f(uniform_fogDensity, f);
        }
    }

    public static void sglFogi(int n, int n2) {
        GL11.glFogi(n, n2);
        if (n == 2917) {
            fogMode = n2;
            if (fogEnabled) {
                Shaders.setProgramUniform1i(uniform_fogMode, fogMode);
            }
        }
    }

    public static void enableLightmap() {
        lightmapEnabled = true;
        if (activeProgram == ProgramTextured) {
            Shaders.useProgram(ProgramTexturedLit);
        }
    }

    public static void disableLightmap() {
        lightmapEnabled = false;
        if (activeProgram == ProgramTexturedLit) {
            Shaders.useProgram(ProgramTextured);
        }
    }

    public static int getEntityData() {
        return entityData[entityDataIndex * 2];
    }

    public static int getEntityData2() {
        return entityData[entityDataIndex * 2 + 1];
    }

    public static int setEntityData1(int n) {
        Shaders.entityData[Shaders.entityDataIndex * 2] = entityData[entityDataIndex * 2] & 0xFFFF | n << 16;
        return n;
    }

    public static int setEntityData2(int n) {
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & 0xFFFF0000 | n & 0xFFFF;
        return n;
    }

    public static void pushEntity(int n, int n2) {
        Shaders.entityData[++Shaders.entityDataIndex * 2] = n & 0xFFFF | n2 << 16;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }

    public static void pushEntity(int n) {
        Shaders.entityData[++Shaders.entityDataIndex * 2] = n & 0xFFFF;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }

    public static void pushEntity(Block block) {
        int n = block.getRenderType(block.getDefaultState()).ordinal();
        Shaders.entityData[++Shaders.entityDataIndex * 2] = Registry.BLOCK.getId(block) & 0xFFFF | n << 16;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }

    public static void popEntity() {
        Shaders.entityData[Shaders.entityDataIndex * 2] = 0;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
        --entityDataIndex;
    }

    public static void mcProfilerEndSection() {
        mc.getProfiler().endSection();
    }

    public static String getShaderPackName() {
        if (shaderPack == null) {
            return null;
        }
        return shaderPack instanceof ShaderPackNone ? null : shaderPack.getName();
    }

    public static InputStream getShaderPackResourceStream(String string) {
        return shaderPack == null ? null : shaderPack.getResourceAsStream(string);
    }

    public static void nextAntialiasingLevel(boolean bl) {
        if (bl) {
            if ((configAntialiasingLevel += 2) > 4) {
                configAntialiasingLevel = 0;
            }
        } else if ((configAntialiasingLevel -= 2) < 0) {
            configAntialiasingLevel = 4;
        }
        configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
        configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
    }

    public static void checkShadersModInstalled() {
        try {
            Class<?> clazz = Class.forName("shadersmod.transform.SMCClassTransformer");
        } catch (Throwable throwable) {
            return;
        }
        throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
    }

    public static void resourcesReloaded() {
        Shaders.loadShaderPackResources();
        Shaders.reloadCustomTexturesLocation(customTexturesGbuffers);
        Shaders.reloadCustomTexturesLocation(customTexturesComposite);
        Shaders.reloadCustomTexturesLocation(customTexturesDeferred);
        Shaders.reloadCustomTexturesLocation(customTexturesShadowcomp);
        Shaders.reloadCustomTexturesLocation(customTexturesPrepare);
        if (shaderPackLoaded) {
            BlockAliases.resourcesReloaded();
            ItemAliases.resourcesReloaded();
            EntityAliases.resourcesReloaded();
        }
    }

    private static void loadShaderPackResources() {
        shaderPackResources = new HashMap<String, String>();
        if (shaderPackLoaded) {
            ArrayList<CallSite> arrayList = new ArrayList<CallSite>();
            String string = "/shaders/lang/";
            String string2 = "en_us";
            String string3 = ".lang";
            arrayList.add((CallSite)((Object)(string + string2 + string3)));
            arrayList.add((CallSite)((Object)(string + Shaders.getLocaleUppercase(string2) + string3)));
            if (!Config.getGameSettings().language.equals(string2)) {
                String string4 = Config.getGameSettings().language;
                arrayList.add((CallSite)((Object)(string + (String)string4 + string3)));
                arrayList.add((CallSite)((Object)(string + Shaders.getLocaleUppercase(string4) + string3)));
            }
            try {
                for (String string5 : arrayList) {
                    InputStream inputStream = shaderPack.getResourceAsStream(string5);
                    if (inputStream == null) continue;
                    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                    Lang.loadLocaleData(inputStream, propertiesOrdered);
                    inputStream.close();
                    for (String string6 : ((Hashtable)propertiesOrdered).keySet()) {
                        String string7 = propertiesOrdered.getProperty(string6);
                        shaderPackResources.put(string6, string7);
                    }
                }
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    private static String getLocaleUppercase(String string) {
        int n = string.indexOf(95);
        return n < 0 ? string : string.substring(0, n) + string.substring(n).toUpperCase(Locale.ROOT);
    }

    public static String translate(String string, String string2) {
        String string3 = shaderPackResources.get(string);
        return string3 == null ? string2 : string3;
    }

    public static boolean isProgramPath(String string) {
        Program program;
        if (string == null) {
            return true;
        }
        if (string.length() <= 0) {
            return true;
        }
        int n = string.lastIndexOf("/");
        if (n >= 0) {
            string = string.substring(n + 1);
        }
        return (program = Shaders.getProgram(string)) != null;
    }

    public static Program getProgram(String string) {
        return programs.getProgram(string);
    }

    public static void setItemToRenderMain(ItemStack itemStack) {
        itemToRenderMainTranslucent = Shaders.isTranslucentBlock(itemStack);
    }

    public static void setItemToRenderOff(ItemStack itemStack) {
        itemToRenderOffTranslucent = Shaders.isTranslucentBlock(itemStack);
    }

    public static boolean isItemToRenderMainTranslucent() {
        return itemToRenderMainTranslucent;
    }

    public static boolean isItemToRenderOffTranslucent() {
        return itemToRenderOffTranslucent;
    }

    public static boolean isBothHandsRendered() {
        return isHandRenderedMain && isHandRenderedOff;
    }

    private static boolean isTranslucentBlock(ItemStack itemStack) {
        if (itemStack == null) {
            return true;
        }
        Item item = itemStack.getItem();
        if (item == null) {
            return true;
        }
        if (!(item instanceof BlockItem)) {
            return true;
        }
        BlockItem blockItem = (BlockItem)item;
        Block block = blockItem.getBlock();
        if (block == null) {
            return true;
        }
        RenderType renderType = RenderTypeLookup.getChunkRenderType(block.getDefaultState());
        return renderType == RenderTypes.TRANSLUCENT;
    }

    public static boolean isSkipRenderHand(Hand hand) {
        if (hand == Hand.MAIN_HAND && skipRenderHandMain) {
            return false;
        }
        return hand == Hand.OFF_HAND && skipRenderHandOff;
    }

    public static boolean isRenderBothHands() {
        return !skipRenderHandMain && !skipRenderHandOff;
    }

    public static void setSkipRenderHands(boolean bl, boolean bl2) {
        skipRenderHandMain = bl;
        skipRenderHandOff = bl2;
    }

    public static void setHandsRendered(boolean bl, boolean bl2) {
        isHandRenderedMain = bl;
        isHandRenderedOff = bl2;
    }

    public static boolean isHandRenderedMain() {
        return isHandRenderedMain;
    }

    public static boolean isHandRenderedOff() {
        return isHandRenderedOff;
    }

    public static float getShadowRenderDistance() {
        return shadowDistanceRenderMul < 0.0f ? -1.0f : shadowMapHalfPlane * shadowDistanceRenderMul;
    }

    public static void beginRenderFirstPersonHand(boolean bl) {
        isRenderingFirstPersonHand = true;
        if (bl) {
            Shaders.setRenderStage(RenderStage.HAND_TRANSLUCENT);
        } else {
            Shaders.setRenderStage(RenderStage.HAND_SOLID);
        }
    }

    public static void endRenderFirstPersonHand() {
        isRenderingFirstPersonHand = false;
        Shaders.setRenderStage(RenderStage.NONE);
    }

    public static boolean isRenderingFirstPersonHand() {
        return isRenderingFirstPersonHand;
    }

    public static void beginBeacon() {
        if (isRenderingWorld) {
            Shaders.useProgram(ProgramBeaconBeam);
        }
    }

    public static void endBeacon() {
        if (isRenderingWorld) {
            Shaders.useProgram(ProgramBlock);
        }
    }

    public static ClientWorld getCurrentWorld() {
        return currentWorld;
    }

    public static BlockPos getWorldCameraPosition() {
        return new BlockPos(cameraPositionX + (double)cameraOffsetX, cameraPositionY, cameraPositionZ + (double)cameraOffsetZ);
    }

    public static boolean isCustomUniforms() {
        return customUniforms != null;
    }

    public static boolean canRenderQuads() {
        return hasGeometryShaders ? Shaders.capabilities.GL_NV_geometry_shader4 : true;
    }

    public static boolean isOverlayDisabled() {
        return shaderPackLoaded;
    }

    public static boolean isRemapLightmap() {
        return shaderPackLoaded;
    }

    public static boolean isEffectsModelView() {
        return shaderPackLoaded;
    }

    public static void flushRenderBuffers() {
        RenderUtils.flushRenderBuffers();
    }

    public static void setRenderStage(RenderStage renderStage) {
        if (shaderPackLoaded) {
            Shaders.renderStage = renderStage;
            uniform_renderStage.setValue(renderStage.ordinal());
        }
    }

    static {
        isInitializedOnce = false;
        isShaderPackInitialized = false;
        hasGlGenMipmap = false;
        countResetDisplayLists = 0;
        renderDisplayWidth = 0;
        renderDisplayHeight = 0;
        renderWidth = 0;
        renderHeight = 0;
        isRenderingWorld = false;
        isRenderingSky = false;
        isCompositeRendered = false;
        isRenderingDfb = false;
        isShadowPass = false;
        isEntitiesGlowing = false;
        renderItemKeepDepthMask = false;
        itemToRenderMainTranslucent = false;
        itemToRenderOffTranslucent = false;
        sunPosition = new float[4];
        moonPosition = new float[4];
        shadowLightPosition = new float[4];
        upPosition = new float[4];
        shadowLightPositionVector = new float[4];
        upPosModelView = new float[]{0.0f, 100.0f, 0.0f, 0.0f};
        sunPosModelView = new float[]{0.0f, 100.0f, 0.0f, 0.0f};
        moonPosModelView = new float[]{0.0f, -100.0f, 0.0f, 0.0f};
        tempMat = new float[16];
        clearColor = new Vector4f();
        worldTime = 0L;
        lastWorldTime = 0L;
        diffWorldTime = 0L;
        celestialAngle = 0.0f;
        sunAngle = 0.0f;
        shadowAngle = 0.0f;
        moonPhase = 0;
        systemTime = 0L;
        lastSystemTime = 0L;
        diffSystemTime = 0L;
        frameCounter = 0;
        frameTime = 0.0f;
        frameTimeCounter = 0.0f;
        systemTimeInt32 = 0;
        pointOfView = PointOfView.FIRST_PERSON;
        pointOfViewChanged = false;
        rainStrength = 0.0f;
        wetness = 0.0f;
        wetnessHalfLife = 600.0f;
        drynessHalfLife = 200.0f;
        eyeBrightnessHalflife = 10.0f;
        usewetness = false;
        isEyeInWater = 0;
        eyeBrightness = 0;
        eyeBrightnessFadeX = 0.0f;
        eyeBrightnessFadeY = 0.0f;
        eyePosY = 0.0f;
        centerDepth = 0.0f;
        centerDepthSmooth = 0.0f;
        centerDepthSmoothHalflife = 1.0f;
        centerDepthSmoothEnabled = false;
        superSamplingLevel = 1;
        nightVision = 0.0f;
        blindness = 0.0f;
        lightmapEnabled = false;
        fogEnabled = true;
        renderStage = RenderStage.NONE;
        baseAttribId = 11;
        entityAttrib = baseAttribId + 0;
        midTexCoordAttrib = baseAttribId + 1;
        tangentAttrib = baseAttribId + 2;
        velocityAttrib = baseAttribId + 3;
        midBlockAttrib = baseAttribId + 4;
        useEntityAttrib = false;
        useMidTexCoordAttrib = false;
        useTangentAttrib = false;
        useVelocityAttrib = false;
        useMidBlockAttrib = false;
        progUseEntityAttrib = false;
        progUseMidTexCoordAttrib = false;
        progUseTangentAttrib = false;
        progUseVelocityAttrib = false;
        progUseMidBlockAttrib = false;
        progArbGeometryShader4 = false;
        progExtGeometryShader4 = false;
        progMaxVerticesOut = 3;
        hasGeometryShaders = false;
        atlasSizeX = 0;
        atlasSizeY = 0;
        shaderUniforms = new ShaderUniforms();
        uniform_entityColor = shaderUniforms.make4f("entityColor");
        uniform_entityId = shaderUniforms.make1i("entityId");
        uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
        uniform_texture = shaderUniforms.make1i("texture");
        uniform_lightmap = shaderUniforms.make1i("lightmap");
        uniform_normals = shaderUniforms.make1i("normals");
        uniform_specular = shaderUniforms.make1i("specular");
        uniform_shadow = shaderUniforms.make1i("shadow");
        uniform_watershadow = shaderUniforms.make1i("watershadow");
        uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
        uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
        uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
        uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
        uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
        uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
        uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
        uniform_noisetex = shaderUniforms.make1i("noisetex");
        uniform_gcolor = shaderUniforms.make1i("gcolor");
        uniform_gdepth = shaderUniforms.make1i("gdepth");
        uniform_gnormal = shaderUniforms.make1i("gnormal");
        uniform_composite = shaderUniforms.make1i("composite");
        uniform_gaux1 = shaderUniforms.make1i("gaux1");
        uniform_gaux2 = shaderUniforms.make1i("gaux2");
        uniform_gaux3 = shaderUniforms.make1i("gaux3");
        uniform_gaux4 = shaderUniforms.make1i("gaux4");
        uniform_colortex0 = shaderUniforms.make1i("colortex0");
        uniform_colortex1 = shaderUniforms.make1i("colortex1");
        uniform_colortex2 = shaderUniforms.make1i("colortex2");
        uniform_colortex3 = shaderUniforms.make1i("colortex3");
        uniform_colortex4 = shaderUniforms.make1i("colortex4");
        uniform_colortex5 = shaderUniforms.make1i("colortex5");
        uniform_colortex6 = shaderUniforms.make1i("colortex6");
        uniform_colortex7 = shaderUniforms.make1i("colortex7");
        uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
        uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
        uniform_colortex8 = shaderUniforms.make1i("colortex8");
        uniform_colortex9 = shaderUniforms.make1i("colortex9");
        uniform_colortex10 = shaderUniforms.make1i("colortex10");
        uniform_colortex11 = shaderUniforms.make1i("colortex11");
        uniform_colortex12 = shaderUniforms.make1i("colortex12");
        uniform_colortex13 = shaderUniforms.make1i("colortex13");
        uniform_colortex14 = shaderUniforms.make1i("colortex14");
        uniform_colortex15 = shaderUniforms.make1i("colortex15");
        uniform_colorimg0 = shaderUniforms.make1i("colorimg0");
        uniform_colorimg1 = shaderUniforms.make1i("colorimg1");
        uniform_colorimg2 = shaderUniforms.make1i("colorimg2");
        uniform_colorimg3 = shaderUniforms.make1i("colorimg3");
        uniform_colorimg4 = shaderUniforms.make1i("colorimg4");
        uniform_colorimg5 = shaderUniforms.make1i("colorimg5");
        uniform_shadowcolorimg0 = shaderUniforms.make1i("shadowcolorimg0");
        uniform_shadowcolorimg1 = shaderUniforms.make1i("shadowcolorimg1");
        uniform_tex = shaderUniforms.make1i("tex");
        uniform_heldItemId = shaderUniforms.make1i("heldItemId");
        uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
        uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
        uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
        uniform_fogMode = shaderUniforms.make1i("fogMode");
        uniform_fogDensity = shaderUniforms.make1f("fogDensity");
        uniform_fogColor = shaderUniforms.make3f("fogColor");
        uniform_skyColor = shaderUniforms.make3f("skyColor");
        uniform_worldTime = shaderUniforms.make1i("worldTime");
        uniform_worldDay = shaderUniforms.make1i("worldDay");
        uniform_moonPhase = shaderUniforms.make1i("moonPhase");
        uniform_frameCounter = shaderUniforms.make1i("frameCounter");
        uniform_frameTime = shaderUniforms.make1f("frameTime");
        uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
        uniform_sunAngle = shaderUniforms.make1f("sunAngle");
        uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
        uniform_rainStrength = shaderUniforms.make1f("rainStrength");
        uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
        uniform_viewWidth = shaderUniforms.make1f("viewWidth");
        uniform_viewHeight = shaderUniforms.make1f("viewHeight");
        uniform_near = shaderUniforms.make1f("near");
        uniform_far = shaderUniforms.make1f("far");
        uniform_sunPosition = shaderUniforms.make3f("sunPosition");
        uniform_moonPosition = shaderUniforms.make3f("moonPosition");
        uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
        uniform_upPosition = shaderUniforms.make3f("upPosition");
        uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
        uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
        uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
        uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
        uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
        uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
        uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
        uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
        uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
        uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
        uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
        uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
        uniform_wetness = shaderUniforms.make1f("wetness");
        uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
        uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
        uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
        uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
        uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
        uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
        uniform_nightVision = shaderUniforms.make1f("nightVision");
        uniform_blindness = shaderUniforms.make1f("blindness");
        uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
        uniform_hideGUI = shaderUniforms.make1i("hideGUI");
        uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
        uniform_atlasSize = shaderUniforms.make2i("atlasSize");
        uniform_spriteBounds = shaderUniforms.make4f("spriteBounds");
        uniform_blendFunc = shaderUniforms.make4i("blendFunc");
        uniform_instanceId = shaderUniforms.make1i("instanceId");
        uniform_playerMood = shaderUniforms.make1f("playerMood");
        uniform_renderStage = shaderUniforms.make1i("renderStage");
        hasShadowMap = false;
        needResizeShadow = false;
        shadowMapWidth = 1024;
        shadowMapHeight = 1024;
        spShadowMapWidth = 1024;
        spShadowMapHeight = 1024;
        shadowMapFOV = 90.0f;
        shadowMapHalfPlane = 160.0f;
        shadowMapIsOrtho = true;
        shadowDistanceRenderMul = -1.0f;
        shouldSkipDefaultShadow = false;
        waterShadowEnabled = false;
        usedColorBuffers = 0;
        usedDepthBuffers = 0;
        usedShadowColorBuffers = 0;
        usedShadowDepthBuffers = 0;
        usedColorAttachs = 0;
        usedDrawBuffers = 0;
        bindImageTextures = false;
        gbuffersFormat = new int[16];
        gbuffersClear = new boolean[16];
        gbuffersClearColor = new Vector4f[16];
        CLEAR_COLOR_0 = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        CLEAR_COLOR_1 = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        shadowBuffersFormat = new int[2];
        shadowBuffersClear = new boolean[2];
        shadowBuffersClearColor = new Vector4f[2];
        programs = new Programs();
        ProgramNone = programs.getProgramNone();
        ProgramShadow = programs.makeShadow("shadow", ProgramNone);
        ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
        ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
        ProgramsShadowcomp = programs.makeShadowcomps("shadowcomp", 16);
        ProgramsPrepare = programs.makePrepares("prepare", 16);
        ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
        ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
        ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
        ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
        ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
        ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
        ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
        ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
        ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
        ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
        ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
        ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
        ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
        ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
        ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
        ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
        ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
        ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
        ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
        ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
        ProgramDeferredPre = programs.makeVirtual("deferred_pre");
        ProgramsDeferred = programs.makeDeferreds("deferred", 16);
        ProgramDeferred = ProgramsDeferred[0];
        ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
        ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
        ProgramCompositePre = programs.makeVirtual("composite_pre");
        ProgramsComposite = programs.makeComposites("composite", 16);
        ProgramComposite = ProgramsComposite[0];
        ProgramFinal = programs.makeComposite("final");
        ProgramCount = programs.getCount();
        ProgramsAll = programs.getPrograms();
        activeProgram = ProgramNone;
        activeProgramID = 0;
        programStack = new ProgramStack();
        hasDeferredPrograms = false;
        hasShadowcompPrograms = false;
        hasPreparePrograms = false;
        loadedShaders = null;
        shadersConfig = null;
        defaultTexture = null;
        shadowHardwareFilteringEnabled = new boolean[2];
        shadowMipmapEnabled = new boolean[2];
        shadowFilterNearest = new boolean[2];
        shadowColorMipmapEnabled = new boolean[2];
        shadowColorFilterNearest = new boolean[2];
        configTweakBlockDamage = false;
        configCloudShadow = false;
        configHandDepthMul = 0.125f;
        configRenderResMul = 1.0f;
        configShadowResMul = 1.0f;
        configTexMinFilB = 0;
        configTexMinFilN = 0;
        configTexMinFilS = 0;
        configTexMagFilB = 0;
        configTexMagFilN = 0;
        configTexMagFilS = 0;
        configShadowClipFrustrum = true;
        configNormalMap = true;
        configSpecularMap = true;
        configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
        configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
        configAntialiasingLevel = 0;
        texMinFilDesc = new String[]{"Nearest", "Nearest-Nearest", "Nearest-Linear"};
        texMagFilDesc = new String[]{"Nearest", "Linear"};
        texMinFilValue = new int[]{9728, 9984, 9986};
        texMagFilValue = new int[]{9728, 9729};
        shaderPack = null;
        shaderPackLoaded = false;
        shaderPacksDir = new File(Minecraft.getInstance().gameDir, SHADER_PACKS_DIR_NAME);
        configFile = new File(Minecraft.getInstance().gameDir, OPTIONS_FILE_NAME);
        shaderPackOptions = null;
        shaderPackOptionSliders = null;
        shaderPackProfiles = null;
        shaderPackGuiScreens = null;
        shaderPackProgramConditions = new HashMap<String, IExpressionBool>();
        shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
        shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
        shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
        shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
        shaderPackShadowTerrain = new PropertyDefaultTrueFalse("shadowTerrain", "Shadow Terrain", 0);
        shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
        shaderPackShadowEntities = new PropertyDefaultTrueFalse("shadowEntities", "Shadow Entities", 0);
        shaderPackShadowBlockEntities = new PropertyDefaultTrueFalse("shadowBlockEntities", "Shadow Block Entities", 0);
        shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
        shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
        shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
        shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
        shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
        shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
        shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
        shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
        shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
        shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
        shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
        shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
        shaderPackResources = new HashMap<String, String>();
        currentWorld = null;
        shaderPackDimensions = new ArrayList<Integer>();
        customTexturesGbuffers = null;
        customTexturesComposite = null;
        customTexturesDeferred = null;
        customTexturesShadowcomp = null;
        customTexturesPrepare = null;
        noiseTexturePath = null;
        colorBufferSizes = new DynamicDimension[16];
        customUniforms = null;
        saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
        blockLightLevel05 = 0.5f;
        blockLightLevel06 = 0.6f;
        blockLightLevel08 = 0.8f;
        aoLevel = -1.0f;
        sunPathRotation = 0.0f;
        shadowAngleInterval = 0.0f;
        fogMode = 0;
        fogDensity = 0.0f;
        shadowIntervalSize = 2.0f;
        terrainIconSize = 16;
        terrainTextureSize = new int[2];
        noiseTextureEnabled = false;
        noiseTextureResolution = 256;
        colorTextureImageUnit = new int[]{0, 1, 2, 3, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 23};
        depthTextureImageUnit = new int[]{6, 11, 12};
        shadowColorTextureImageUnit = new int[]{13, 14};
        shadowDepthTextureImageUnit = new int[]{4, 5};
        colorImageUnit = new int[]{0, 1, 2, 3, 4, 5};
        shadowColorImageUnit = new int[]{6, 7};
        bigBufferSize = (295 + 8 * ProgramCount) * 4;
        bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(bigBufferSize).limit(0);
        faProjection = new float[16];
        faProjectionInverse = new float[16];
        faModelView = new float[16];
        faModelViewInverse = new float[16];
        faShadowProjection = new float[16];
        faShadowProjectionInverse = new float[16];
        faShadowModelView = new float[16];
        faShadowModelViewInverse = new float[16];
        projection = Shaders.nextFloatBuffer(16);
        projectionInverse = Shaders.nextFloatBuffer(16);
        modelView = Shaders.nextFloatBuffer(16);
        modelViewInverse = Shaders.nextFloatBuffer(16);
        shadowProjection = Shaders.nextFloatBuffer(16);
        shadowProjectionInverse = Shaders.nextFloatBuffer(16);
        shadowModelView = Shaders.nextFloatBuffer(16);
        shadowModelViewInverse = Shaders.nextFloatBuffer(16);
        previousProjection = Shaders.nextFloatBuffer(16);
        previousModelView = Shaders.nextFloatBuffer(16);
        tempMatrixDirectBuffer = Shaders.nextFloatBuffer(16);
        tempDirectFloatBuffer = Shaders.nextFloatBuffer(16);
        dfbDrawBuffers = new DrawBuffers("dfbDrawBuffers", 16, 8);
        sfbDrawBuffers = new DrawBuffers("sfbDrawBuffers", 16, 8);
        drawBuffersNone = new DrawBuffers("drawBuffersNone", 16, 8).limit(0);
        drawBuffersColorAtt = Shaders.makeDrawBuffersColorSingle(16);
        formatNames = new String[]{"R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R8I", "RG8I", "RGB8I", "RGBA8I", "R8UI", "RG8UI", "RGB8UI", "RGBA8UI", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R16I", "RG16I", "RGB16I", "RGBA16I", "R16UI", "RG16UI", "RGB16UI", "RGBA16UI", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5"};
        formatIds = new int[]{33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33329, 33335, 36239, 36238, 33330, 33336, 36221, 36220, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33331, 33337, 36233, 36232, 33332, 33338, 36215, 36214, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901};
        patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
        entityData = new int[32];
        entityDataIndex = 0;
    }
}

