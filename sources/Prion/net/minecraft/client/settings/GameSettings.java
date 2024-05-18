package net.minecraft.client.settings;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.stream.IStream;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.CustomSky;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.Reflector;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class GameSettings
{
  private static final Logger logger = ;
  private static final Gson gson = new Gson();
  private static final ParameterizedType typeListString = new ParameterizedType()
  {
    private static final String __OBFID = "CL_00000651";
    
    public Type[] getActualTypeArguments() {
      return new Type[] { String.class };
    }
    
    public Type getRawType() {
      return List.class;
    }
    
    public Type getOwnerType() {
      return null;
    }
  };
  

  private static final String[] GUISCALES = { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
  private static final String[] PARTICLES = { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
  private static final String[] AMBIENT_OCCLUSIONS = { "options.ao.off", "options.ao.min", "options.ao.max" };
  private static final String[] STREAM_COMPRESSIONS = { "options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high" };
  private static final String[] STREAM_CHAT_MODES = { "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never" };
  private static final String[] STREAM_CHAT_FILTER_MODES = { "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods" };
  private static final String[] STREAM_MIC_MODES = { "options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk" };
  public float mouseSensitivity = 0.5F;
  public boolean invertMouse;
  public int renderDistanceChunks = -1;
  public boolean viewBobbing = true;
  public boolean anaglyph;
  public boolean fboEnable = true;
  public int limitFramerate = 120;
  public int ofFogType = 1;
  public float ofFogStart = 0.8F;
  public int ofMipmapType = 0;
  public boolean ofLoadFar = false;
  public int ofPreloadedChunks = 0;
  public boolean ofOcclusionFancy = false;
  public boolean ofSmoothWorld = Config.isSingleProcessor();
  public boolean ofLazyChunkLoading = Config.isSingleProcessor();
  public float ofAoLevel = 1.0F;
  public int ofAaLevel = 0;
  public int ofClouds = 0;
  public float ofCloudsHeight = 0.0F;
  public int ofTrees = 0;
  public int ofRain = 0;
  public int ofDroppedItems = 0;
  public int ofBetterGrass = 3;
  public int ofAutoSaveTicks = 4000;
  public boolean ofLagometer = false;
  public boolean ofProfiler = false;
  public boolean ofWeather = true;
  public boolean ofSky = true;
  public boolean ofStars = true;
  public boolean ofSunMoon = true;
  public int ofChunkUpdates = 1;
  public int ofChunkLoading = 0;
  public boolean ofChunkUpdatesDynamic = false;
  public int ofTime = 0;
  public boolean ofClearWater = false;
  public boolean ofBetterSnow = false;
  public String ofFullscreenMode = "Default";
  public boolean ofSwampColors = true;
  public boolean ofRandomMobs = true;
  public boolean ofSmoothBiomes = true;
  public boolean ofCustomFonts = true;
  public boolean ofCustomColors = true;
  public boolean ofCustomSky = true;
  public boolean ofShowCapes = true;
  public int ofConnectedTextures = 2;
  public boolean ofNaturalTextures = false;
  public boolean ofFastMath = false;
  public boolean ofFastRender = true;
  public int ofTranslucentBlocks = 0;
  public int ofAnimatedWater = 0;
  public int ofAnimatedLava = 0;
  public boolean ofAnimatedFire = true;
  public boolean ofAnimatedPortal = true;
  public boolean ofAnimatedRedstone = true;
  public boolean ofAnimatedExplosion = true;
  public boolean ofAnimatedFlame = true;
  public boolean ofAnimatedSmoke = true;
  public boolean ofVoidParticles = true;
  public boolean ofWaterParticles = true;
  public boolean ofRainSplash = true;
  public boolean ofPortalParticles = true;
  public boolean ofPotionParticles = true;
  public boolean ofDrippingWaterLava = true;
  public boolean ofAnimatedTerrain = true;
  public boolean ofAnimatedTextures = true;
  
  public static final int DEFAULT = 0;
  
  public static final int FAST = 1;
  public static final int FANCY = 2;
  public static final int OFF = 3;
  public static final int ANIM_ON = 0;
  public static final int ANIM_GENERATED = 1;
  public static final int ANIM_OFF = 2;
  public static final int CL_DEFAULT = 0;
  public static final int CL_SMOOTH = 1;
  public static final int CL_THREADED = 2;
  public static final String DEFAULT_STR = "Default";
  public KeyBinding ofKeyBindZoom;
  private File optionsFileOF;
  public boolean clouds = true;
  public boolean fancyGraphics = true;
  

  public int ambientOcclusion = 2;
  public List resourcePacks = Lists.newArrayList();
  
  public EntityPlayer.EnumChatVisibility chatVisibility;
  
  public boolean chatColours;
  
  public boolean chatLinks;
  
  public boolean chatLinksPrompt;
  
  public float chatOpacity;
  
  public boolean snooperEnabled;
  
  public boolean fullScreen;
  
  public boolean enableVsync;
  
  public boolean field_178881_t;
  
  public boolean field_178880_u;
  
  public boolean field_178879_v;
  
  public boolean hideServerAddress;
  
  public boolean advancedItemTooltips;
  
  public boolean pauseOnLostFocus;
  
  public final Set field_178882_aU;
  
  public boolean touchscreen;
  
  public int overrideWidth;
  
  public int overrideHeight;
  
  public boolean heldItemTooltips;
  public float chatScale;
  public float chatWidth;
  public float chatHeightUnfocused;
  public float chatHeightFocused;
  public boolean showInventoryAchievementHint;
  public int mipmapLevels;
  private Map mapSoundLevels;
  public float streamBytesPerPixel;
  public float streamMicVolume;
  public float streamGameVolume;
  public float streamKbps;
  public float streamFps;
  public int streamCompression;
  public boolean streamSendMetadata;
  public String streamPreferredServer;
  public int streamChatEnabled;
  public int streamChatUserFilter;
  public int streamMicToggleBehavior;
  public KeyBinding keyBindForward;
  public KeyBinding keyBindLeft;
  public KeyBinding keyBindBack;
  public KeyBinding keyBindRight;
  public KeyBinding keyBindJump;
  public KeyBinding keyBindSneak;
  public KeyBinding keyBindInventory;
  public KeyBinding keyBindUseItem;
  public KeyBinding keyBindDrop;
  public KeyBinding keyBindAttack;
  public KeyBinding keyBindPickBlock;
  public KeyBinding keyBindSprint;
  public KeyBinding keyBindChat;
  public KeyBinding keyBindPlayerList;
  public KeyBinding keyBindCommand;
  public KeyBinding keyBindScreenshot;
  public KeyBinding keyBindTogglePerspective;
  public KeyBinding keyBindSmoothCamera;
  public KeyBinding keyBindFullscreen;
  public KeyBinding field_178883_an;
  public KeyBinding keyBindStreamStartStop;
  public KeyBinding keyBindStreamPauseUnpause;
  public KeyBinding keyBindStreamCommercials;
  public KeyBinding keyBindStreamToggleMic;
  public KeyBinding[] keyBindsHotbar;
  public KeyBinding[] keyBindings;
  protected Minecraft mc;
  private File optionsFile;
  public EnumDifficulty difficulty;
  public boolean hideGUI;
  public int thirdPersonView;
  public boolean showDebugInfo;
  public boolean showDebugProfilerChart;
  public String lastServer;
  public boolean smoothCamera;
  public boolean debugCamEnable;
  public float fovSetting;
  public float gammaSetting;
  public float saturation;
  public int guiScale;
  public int particleSetting;
  public String language;
  public boolean forceUnicodeFont;
  private static final String __OBFID = "CL_00000650";
  
  public GameSettings(Minecraft mcIn, File p_i46326_2_)
  {
    chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
    chatColours = true;
    chatLinks = true;
    chatLinksPrompt = true;
    chatOpacity = 1.0F;
    snooperEnabled = true;
    enableVsync = true;
    field_178881_t = false;
    field_178880_u = true;
    field_178879_v = false;
    pauseOnLostFocus = true;
    field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
    heldItemTooltips = true;
    chatScale = 1.0F;
    chatWidth = 1.0F;
    chatHeightUnfocused = 0.44366196F;
    chatHeightFocused = 1.0F;
    showInventoryAchievementHint = true;
    mipmapLevels = 4;
    mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
    streamBytesPerPixel = 0.5F;
    streamMicVolume = 1.0F;
    streamGameVolume = 1.0F;
    streamKbps = 0.5412844F;
    streamFps = 0.31690142F;
    streamCompression = 1;
    streamSendMetadata = true;
    streamPreferredServer = "";
    streamChatEnabled = 0;
    streamChatUserFilter = 0;
    streamMicToggleBehavior = 0;
    keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
    keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
    keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
    keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
    keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
    keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
    keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
    keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
    keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
    keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
    keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
    keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
    keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
    keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
    keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
    keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
    keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
    keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
    keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
    field_178883_an = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
    keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
    keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
    keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
    keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
    keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
    keyBindings = ((KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective, keyBindSmoothCamera, keyBindSprint, keyBindStreamStartStop, keyBindStreamPauseUnpause, keyBindStreamCommercials, keyBindStreamToggleMic, keyBindFullscreen, field_178883_an }, keyBindsHotbar));
    difficulty = EnumDifficulty.NORMAL;
    lastServer = "";
    fovSetting = 70.0F;
    language = "en_US";
    forceUnicodeFont = false;
    mc = mcIn;
    optionsFile = new File(p_i46326_2_, "options.txt");
    optionsFileOF = new File(p_i46326_2_, "optionsof.txt");
    limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
    ofKeyBindZoom = new KeyBinding("Zoom", 46, "key.categories.misc");
    keyBindings = ((KeyBinding[])ArrayUtils.add(keyBindings, ofKeyBindZoom));
    Options.RENDER_DISTANCE.setValueMax(32.0F);
    renderDistanceChunks = 8;
    loadOptions();
    Config.initGameSettings(this);
  }
  
  public GameSettings()
  {
    chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
    chatColours = true;
    chatLinks = true;
    chatLinksPrompt = true;
    chatOpacity = 1.0F;
    snooperEnabled = true;
    enableVsync = true;
    field_178881_t = false;
    field_178880_u = true;
    field_178879_v = false;
    pauseOnLostFocus = true;
    field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
    heldItemTooltips = true;
    chatScale = 1.0F;
    chatWidth = 1.0F;
    chatHeightUnfocused = 0.44366196F;
    chatHeightFocused = 1.0F;
    showInventoryAchievementHint = true;
    mipmapLevels = 4;
    mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
    streamBytesPerPixel = 0.5F;
    streamMicVolume = 1.0F;
    streamGameVolume = 1.0F;
    streamKbps = 0.5412844F;
    streamFps = 0.31690142F;
    streamCompression = 1;
    streamSendMetadata = true;
    streamPreferredServer = "";
    streamChatEnabled = 0;
    streamChatUserFilter = 0;
    streamMicToggleBehavior = 0;
    keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
    keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
    keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
    keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
    keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
    keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
    keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
    keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
    keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
    keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
    keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
    keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
    keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
    keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
    keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
    keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
    keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
    keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
    keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
    field_178883_an = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
    keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
    keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
    keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
    keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
    keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
    keyBindings = ((KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective, keyBindSmoothCamera, keyBindSprint, keyBindStreamStartStop, keyBindStreamPauseUnpause, keyBindStreamCommercials, keyBindStreamToggleMic, keyBindFullscreen, field_178883_an }, keyBindsHotbar));
    difficulty = EnumDifficulty.NORMAL;
    lastServer = "";
    fovSetting = 70.0F;
    language = "en_US";
    forceUnicodeFont = false;
  }
  



  public static String getKeyDisplayString(int p_74298_0_)
  {
    return p_74298_0_ < 256 ? org.lwjgl.input.Keyboard.getKeyName(p_74298_0_) : p_74298_0_ < 0 ? I18n.format("key.mouseButton", new Object[] { Integer.valueOf(p_74298_0_ + 101) }) : String.format("%c", new Object[] { Character.valueOf((char)(p_74298_0_ - 256)) }).toUpperCase();
  }
  



  public static boolean isKeyDown(KeyBinding p_100015_0_)
  {
    int keyCode = p_100015_0_.getKeyCode();
    return p_100015_0_.getKeyCode() != 0;
  }
  



  public void setOptionKeyBinding(KeyBinding p_151440_1_, int p_151440_2_)
  {
    p_151440_1_.setKeyCode(p_151440_2_);
    saveOptions();
  }
  



  public void setOptionFloatValue(Options p_74304_1_, float p_74304_2_)
  {
    setOptionFloatValueOF(p_74304_1_, p_74304_2_);
    
    if (p_74304_1_ == Options.SENSITIVITY)
    {
      mouseSensitivity = p_74304_2_;
    }
    
    if (p_74304_1_ == Options.FOV)
    {
      fovSetting = p_74304_2_;
    }
    
    if (p_74304_1_ == Options.GAMMA)
    {
      gammaSetting = p_74304_2_;
    }
    
    if (p_74304_1_ == Options.FRAMERATE_LIMIT)
    {
      limitFramerate = ((int)p_74304_2_);
      enableVsync = false;
      
      if (limitFramerate <= 0)
      {
        limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
        enableVsync = true;
      }
      
      updateVSync();
    }
    
    if (p_74304_1_ == Options.CHAT_OPACITY)
    {
      chatOpacity = p_74304_2_;
      mc.ingameGUI.getChatGUI().refreshChat();
    }
    
    if (p_74304_1_ == Options.CHAT_HEIGHT_FOCUSED)
    {
      chatHeightFocused = p_74304_2_;
      mc.ingameGUI.getChatGUI().refreshChat();
    }
    
    if (p_74304_1_ == Options.CHAT_HEIGHT_UNFOCUSED)
    {
      chatHeightUnfocused = p_74304_2_;
      mc.ingameGUI.getChatGUI().refreshChat();
    }
    
    if (p_74304_1_ == Options.CHAT_WIDTH)
    {
      chatWidth = p_74304_2_;
      mc.ingameGUI.getChatGUI().refreshChat();
    }
    
    if (p_74304_1_ == Options.CHAT_SCALE)
    {
      chatScale = p_74304_2_;
      mc.ingameGUI.getChatGUI().refreshChat();
    }
    
    if (p_74304_1_ == Options.MIPMAP_LEVELS)
    {
      int var3 = mipmapLevels;
      mipmapLevels = ((int)p_74304_2_);
      
      if (var3 != p_74304_2_)
      {
        mc.getTextureMapBlocks().setMipmapLevels(mipmapLevels);
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        mc.getTextureMapBlocks().func_174937_a(false, mipmapLevels > 0);
        mc.func_175603_A();
      }
    }
    
    if (p_74304_1_ == Options.BLOCK_ALTERNATIVES)
    {
      field_178880_u = (!field_178880_u);
      mc.renderGlobal.loadRenderers();
    }
    
    if (p_74304_1_ == Options.RENDER_DISTANCE)
    {
      renderDistanceChunks = ((int)p_74304_2_);
      mc.renderGlobal.func_174979_m();
    }
    
    if (p_74304_1_ == Options.STREAM_BYTES_PER_PIXEL)
    {
      streamBytesPerPixel = p_74304_2_;
    }
    
    if (p_74304_1_ == Options.STREAM_VOLUME_MIC)
    {
      streamMicVolume = p_74304_2_;
      mc.getTwitchStream().func_152915_s();
    }
    
    if (p_74304_1_ == Options.STREAM_VOLUME_SYSTEM)
    {
      streamGameVolume = p_74304_2_;
      mc.getTwitchStream().func_152915_s();
    }
    
    if (p_74304_1_ == Options.STREAM_KBPS)
    {
      streamKbps = p_74304_2_;
    }
    
    if (p_74304_1_ == Options.STREAM_FPS)
    {
      streamFps = p_74304_2_;
    }
  }
  



  public void setOptionValue(Options p_74306_1_, int p_74306_2_)
  {
    setOptionValueOF(p_74306_1_, p_74306_2_);
    
    if (p_74306_1_ == Options.INVERT_MOUSE)
    {
      invertMouse = (!invertMouse);
    }
    
    if (p_74306_1_ == Options.GUI_SCALE)
    {
      guiScale = (guiScale + p_74306_2_ & 0x3);
    }
    
    if (p_74306_1_ == Options.PARTICLES)
    {
      particleSetting = ((particleSetting + p_74306_2_) % 3);
    }
    
    if (p_74306_1_ == Options.VIEW_BOBBING)
    {
      viewBobbing = (!viewBobbing);
    }
    
    if (p_74306_1_ == Options.RENDER_CLOUDS)
    {
      clouds = (!clouds);
    }
    
    if (p_74306_1_ == Options.FORCE_UNICODE_FONT)
    {
      forceUnicodeFont = (!forceUnicodeFont);
      mc.fontRendererObj.setUnicodeFlag((mc.getLanguageManager().isCurrentLocaleUnicode()) || (forceUnicodeFont));
    }
    
    if (p_74306_1_ == Options.FBO_ENABLE)
    {
      fboEnable = (!fboEnable);
    }
    
    if (p_74306_1_ == Options.ANAGLYPH)
    {
      anaglyph = (!anaglyph);
      mc.refreshResources();
    }
    
    if (p_74306_1_ == Options.GRAPHICS)
    {
      fancyGraphics = (!fancyGraphics);
      mc.renderGlobal.loadRenderers();
    }
    
    if (p_74306_1_ == Options.AMBIENT_OCCLUSION)
    {
      ambientOcclusion = ((ambientOcclusion + p_74306_2_) % 3);
      mc.renderGlobal.loadRenderers();
    }
    
    if (p_74306_1_ == Options.CHAT_VISIBILITY)
    {
      chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((chatVisibility.getChatVisibility() + p_74306_2_) % 3);
    }
    
    if (p_74306_1_ == Options.STREAM_COMPRESSION)
    {
      streamCompression = ((streamCompression + p_74306_2_) % 3);
    }
    
    if (p_74306_1_ == Options.STREAM_SEND_METADATA)
    {
      streamSendMetadata = (!streamSendMetadata);
    }
    
    if (p_74306_1_ == Options.STREAM_CHAT_ENABLED)
    {
      streamChatEnabled = ((streamChatEnabled + p_74306_2_) % 3);
    }
    
    if (p_74306_1_ == Options.STREAM_CHAT_USER_FILTER)
    {
      streamChatUserFilter = ((streamChatUserFilter + p_74306_2_) % 3);
    }
    
    if (p_74306_1_ == Options.STREAM_MIC_TOGGLE_BEHAVIOR)
    {
      streamMicToggleBehavior = ((streamMicToggleBehavior + p_74306_2_) % 2);
    }
    
    if (p_74306_1_ == Options.CHAT_COLOR)
    {
      chatColours = (!chatColours);
    }
    
    if (p_74306_1_ == Options.CHAT_LINKS)
    {
      chatLinks = (!chatLinks);
    }
    
    if (p_74306_1_ == Options.CHAT_LINKS_PROMPT)
    {
      chatLinksPrompt = (!chatLinksPrompt);
    }
    
    if (p_74306_1_ == Options.SNOOPER_ENABLED)
    {
      snooperEnabled = (!snooperEnabled);
    }
    
    if (p_74306_1_ == Options.TOUCHSCREEN)
    {
      touchscreen = (!touchscreen);
    }
    
    if (p_74306_1_ == Options.USE_FULLSCREEN)
    {
      fullScreen = (!fullScreen);
      
      if (mc.isFullScreen() != fullScreen)
      {
        mc.toggleFullscreen();
      }
    }
    
    if (p_74306_1_ == Options.ENABLE_VSYNC)
    {
      enableVsync = (!enableVsync);
      Display.setVSyncEnabled(enableVsync);
    }
    
    if (p_74306_1_ == Options.USE_VBO)
    {
      field_178881_t = (!field_178881_t);
      mc.renderGlobal.loadRenderers();
    }
    
    if (p_74306_1_ == Options.BLOCK_ALTERNATIVES)
    {
      field_178880_u = (!field_178880_u);
      mc.renderGlobal.loadRenderers();
    }
    
    if (p_74306_1_ == Options.REDUCED_DEBUG_INFO)
    {
      field_178879_v = (!field_178879_v);
    }
    
    saveOptions();
  }
  
  public float getOptionFloatValue(Options p_74296_1_)
  {
    return p_74296_1_ == Options.STREAM_FPS ? streamFps : p_74296_1_ == Options.STREAM_KBPS ? streamKbps : p_74296_1_ == Options.STREAM_VOLUME_SYSTEM ? streamGameVolume : p_74296_1_ == Options.STREAM_VOLUME_MIC ? streamMicVolume : p_74296_1_ == Options.STREAM_BYTES_PER_PIXEL ? streamBytesPerPixel : p_74296_1_ == Options.RENDER_DISTANCE ? renderDistanceChunks : p_74296_1_ == Options.MIPMAP_LEVELS ? mipmapLevels : p_74296_1_ == Options.FRAMERATE_LIMIT ? limitFramerate : p_74296_1_ == Options.CHAT_WIDTH ? chatWidth : p_74296_1_ == Options.CHAT_SCALE ? chatScale : p_74296_1_ == Options.CHAT_HEIGHT_UNFOCUSED ? chatHeightUnfocused : p_74296_1_ == Options.CHAT_HEIGHT_FOCUSED ? chatHeightFocused : p_74296_1_ == Options.CHAT_OPACITY ? chatOpacity : p_74296_1_ == Options.SENSITIVITY ? mouseSensitivity : p_74296_1_ == Options.SATURATION ? saturation : p_74296_1_ == Options.GAMMA ? gammaSetting : p_74296_1_ == Options.FOV ? fovSetting : p_74296_1_ == Options.FRAMERATE_LIMIT ? limitFramerate : (limitFramerate == Options.FRAMERATE_LIMIT.getValueMax()) && (enableVsync) ? 0.0F : p_74296_1_ == Options.AO_LEVEL ? ofAoLevel : p_74296_1_ == Options.CLOUD_HEIGHT ? ofCloudsHeight : 0.0F;
  }
  
  public boolean getOptionOrdinalValue(Options p_74308_1_)
  {
    switch (SwitchOptions.optionIds[p_74308_1_.ordinal()])
    {
    case 1: 
      return invertMouse;
    
    case 2: 
      return viewBobbing;
    
    case 3: 
      return anaglyph;
    
    case 4: 
      return fboEnable;
    
    case 5: 
      return clouds;
    
    case 6: 
      return chatColours;
    
    case 7: 
      return chatLinks;
    
    case 8: 
      return chatLinksPrompt;
    
    case 9: 
      return snooperEnabled;
    
    case 10: 
      return fullScreen;
    
    case 11: 
      return enableVsync;
    
    case 12: 
      return field_178881_t;
    
    case 13: 
      return touchscreen;
    
    case 14: 
      return streamSendMetadata;
    
    case 15: 
      return forceUnicodeFont;
    
    case 16: 
      return field_178880_u;
    
    case 17: 
      return field_178879_v;
    }
    
    return false;
  }
  





  private static String getTranslation(String[] p_74299_0_, int p_74299_1_)
  {
    if ((p_74299_1_ < 0) || (p_74299_1_ >= p_74299_0_.length))
    {
      p_74299_1_ = 0;
    }
    
    return I18n.format(p_74299_0_[p_74299_1_], new Object[0]);
  }
  



  public String getKeyBinding(Options p_74297_1_)
  {
    String strOF = getKeyBindingOF(p_74297_1_);
    
    if (strOF != null)
    {
      return strOF;
    }
    

    String var2 = I18n.format(p_74297_1_.getEnumString(), new Object[0]) + ": ";
    
    if (p_74297_1_.getEnumFloat())
    {
      float var32 = getOptionFloatValue(p_74297_1_);
      float var4 = p_74297_1_.normalizeValue(var32);
      return var2 + (int)(var4 * 100.0F) + "%";
    }
    if (p_74297_1_.getEnumBoolean())
    {
      boolean var31 = getOptionOrdinalValue(p_74297_1_);
      return var2 + I18n.format("options.off", new Object[0]);
    }
    if (p_74297_1_ == Options.GUI_SCALE)
    {
      return var2 + getTranslation(GUISCALES, guiScale);
    }
    if (p_74297_1_ == Options.CHAT_VISIBILITY)
    {
      return var2 + I18n.format(chatVisibility.getResourceKey(), new Object[0]);
    }
    if (p_74297_1_ == Options.PARTICLES)
    {
      return var2 + getTranslation(PARTICLES, particleSetting);
    }
    if (p_74297_1_ == Options.AMBIENT_OCCLUSION)
    {
      return var2 + getTranslation(AMBIENT_OCCLUSIONS, ambientOcclusion);
    }
    if (p_74297_1_ == Options.STREAM_COMPRESSION)
    {
      return var2 + getTranslation(STREAM_COMPRESSIONS, streamCompression);
    }
    if (p_74297_1_ == Options.STREAM_CHAT_ENABLED)
    {
      return var2 + getTranslation(STREAM_CHAT_MODES, streamChatEnabled);
    }
    if (p_74297_1_ == Options.STREAM_CHAT_USER_FILTER)
    {
      return var2 + getTranslation(STREAM_CHAT_FILTER_MODES, streamChatUserFilter);
    }
    if (p_74297_1_ == Options.STREAM_MIC_TOGGLE_BEHAVIOR)
    {
      return var2 + getTranslation(STREAM_MIC_MODES, streamMicToggleBehavior);
    }
    if (p_74297_1_ == Options.GRAPHICS)
    {
      if (fancyGraphics)
      {
        return var2 + I18n.format("options.graphics.fancy", new Object[0]);
      }
      

      String var3 = "options.graphics.fast";
      return var2 + I18n.format("options.graphics.fast", new Object[0]);
    }
    


    return var2;
  }
  





  public void loadOptions()
  {
    try
    {
      if (!optionsFile.exists())
      {
        return;
      }
      
      BufferedReader var9 = new BufferedReader(new FileReader(optionsFile));
      String var2 = "";
      mapSoundLevels.clear();
      
      while ((var2 = var9.readLine()) != null)
      {
        try
        {
          String[] var8 = var2.split(":");
          
          if (var8[0].equals("mouseSensitivity"))
          {
            mouseSensitivity = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("fov"))
          {
            fovSetting = (parseFloat(var8[1]) * 40.0F + 70.0F);
          }
          
          if (var8[0].equals("gamma"))
          {
            gammaSetting = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("saturation"))
          {
            saturation = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("invertYMouse"))
          {
            invertMouse = var8[1].equals("true");
          }
          
          if (var8[0].equals("renderDistance"))
          {
            renderDistanceChunks = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("guiScale"))
          {
            guiScale = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("particles"))
          {
            particleSetting = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("bobView"))
          {
            viewBobbing = var8[1].equals("true");
          }
          
          if (var8[0].equals("anaglyph3d"))
          {
            anaglyph = var8[1].equals("true");
          }
          
          if (var8[0].equals("maxFps"))
          {
            limitFramerate = Integer.parseInt(var8[1]);
            enableVsync = false;
            
            if (limitFramerate <= 0)
            {
              limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
              enableVsync = true;
            }
            
            updateVSync();
          }
          
          if (var8[0].equals("fboEnable"))
          {
            fboEnable = var8[1].equals("true");
          }
          
          if (var8[0].equals("difficulty"))
          {
            difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var8[1]));
          }
          
          if (var8[0].equals("fancyGraphics"))
          {
            fancyGraphics = var8[1].equals("true");
          }
          
          if (var8[0].equals("ao"))
          {
            if (var8[1].equals("true"))
            {
              ambientOcclusion = 2;
            }
            else if (var8[1].equals("false"))
            {
              ambientOcclusion = 0;
            }
            else
            {
              ambientOcclusion = Integer.parseInt(var8[1]);
            }
          }
          
          if (var8[0].equals("renderClouds"))
          {
            clouds = var8[1].equals("true");
          }
          
          if (var8[0].equals("resourcePacks"))
          {
            resourcePacks = ((List)gson.fromJson(var2.substring(var2.indexOf(':') + 1), typeListString));
            
            if (resourcePacks == null)
            {
              resourcePacks = Lists.newArrayList();
            }
          }
          
          if ((var8[0].equals("lastServer")) && (var8.length >= 2))
          {
            lastServer = var2.substring(var2.indexOf(':') + 1);
          }
          
          if ((var8[0].equals("lang")) && (var8.length >= 2))
          {
            language = var8[1];
          }
          
          if (var8[0].equals("chatVisibility"))
          {
            chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var8[1]));
          }
          
          if (var8[0].equals("chatColors"))
          {
            chatColours = var8[1].equals("true");
          }
          
          if (var8[0].equals("chatLinks"))
          {
            chatLinks = var8[1].equals("true");
          }
          
          if (var8[0].equals("chatLinksPrompt"))
          {
            chatLinksPrompt = var8[1].equals("true");
          }
          
          if (var8[0].equals("chatOpacity"))
          {
            chatOpacity = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("snooperEnabled"))
          {
            snooperEnabled = var8[1].equals("true");
          }
          
          if (var8[0].equals("fullscreen"))
          {
            fullScreen = var8[1].equals("true");
          }
          
          if (var8[0].equals("enableVsync"))
          {
            enableVsync = var8[1].equals("true");
            updateVSync();
          }
          
          if (var8[0].equals("useVbo"))
          {
            field_178881_t = var8[1].equals("true");
          }
          
          if (var8[0].equals("hideServerAddress"))
          {
            hideServerAddress = var8[1].equals("true");
          }
          
          if (var8[0].equals("advancedItemTooltips"))
          {
            advancedItemTooltips = var8[1].equals("true");
          }
          
          if (var8[0].equals("pauseOnLostFocus"))
          {
            pauseOnLostFocus = var8[1].equals("true");
          }
          
          if (var8[0].equals("touchscreen"))
          {
            touchscreen = var8[1].equals("true");
          }
          
          if (var8[0].equals("overrideHeight"))
          {
            overrideHeight = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("overrideWidth"))
          {
            overrideWidth = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("heldItemTooltips"))
          {
            heldItemTooltips = var8[1].equals("true");
          }
          
          if (var8[0].equals("chatHeightFocused"))
          {
            chatHeightFocused = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("chatHeightUnfocused"))
          {
            chatHeightUnfocused = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("chatScale"))
          {
            chatScale = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("chatWidth"))
          {
            chatWidth = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("showInventoryAchievementHint"))
          {
            showInventoryAchievementHint = var8[1].equals("true");
          }
          
          if (var8[0].equals("mipmapLevels"))
          {
            mipmapLevels = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("streamBytesPerPixel"))
          {
            streamBytesPerPixel = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("streamMicVolume"))
          {
            streamMicVolume = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("streamSystemVolume"))
          {
            streamGameVolume = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("streamKbps"))
          {
            streamKbps = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("streamFps"))
          {
            streamFps = parseFloat(var8[1]);
          }
          
          if (var8[0].equals("streamCompression"))
          {
            streamCompression = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("streamSendMetadata"))
          {
            streamSendMetadata = var8[1].equals("true");
          }
          
          if ((var8[0].equals("streamPreferredServer")) && (var8.length >= 2))
          {
            streamPreferredServer = var2.substring(var2.indexOf(':') + 1);
          }
          
          if (var8[0].equals("streamChatEnabled"))
          {
            streamChatEnabled = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("streamChatUserFilter"))
          {
            streamChatUserFilter = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("streamMicToggleBehavior"))
          {
            streamMicToggleBehavior = Integer.parseInt(var8[1]);
          }
          
          if (var8[0].equals("forceUnicodeFont"))
          {
            forceUnicodeFont = var8[1].equals("true");
          }
          
          if (var8[0].equals("allowBlockAlternatives"))
          {
            field_178880_u = var8[1].equals("true");
          }
          
          if (var8[0].equals("reducedDebugInfo"))
          {
            field_178879_v = var8[1].equals("true");
          }
          
          KeyBinding[] var4 = keyBindings;
          int var5 = var4.length;
          

          for (int var6 = 0; var6 < var5; var6++)
          {
            KeyBinding var10 = var4[var6];
            
            if (var8[0].equals("key_" + var10.getKeyDescription()))
            {
              var10.setKeyCode(Integer.parseInt(var8[1]));
            }
          }
          
          SoundCategory[] var12 = SoundCategory.values();
          var5 = var12.length;
          
          for (var6 = 0; var6 < var5; var6++)
          {
            SoundCategory var11 = var12[var6];
            
            if (var8[0].equals("soundCategory_" + var11.getCategoryName()))
            {
              mapSoundLevels.put(var11, Float.valueOf(parseFloat(var8[1])));
            }
          }
          
          EnumPlayerModelParts[] var131 = EnumPlayerModelParts.values();
          var5 = var131.length;
          
          for (var6 = 0; var6 < var5; var6++)
          {
            EnumPlayerModelParts var13 = var131[var6];
            
            if (var8[0].equals("modelPart_" + var13.func_179329_c()))
            {
              func_178878_a(var13, var8[1].equals("true"));
            }
          }
        }
        catch (Exception var101)
        {
          logger.warn("Skipping bad option: " + var2);
          var101.printStackTrace();
        }
      }
      
      KeyBinding.resetKeyBindingArrayAndHash();
      var9.close();
    }
    catch (Exception var111)
    {
      logger.error("Failed to load options", var111);
    }
    
    loadOfOptions();
  }
  



  private float parseFloat(String p_74305_1_)
  {
    return p_74305_1_.equals("false") ? 0.0F : p_74305_1_.equals("true") ? 1.0F : Float.parseFloat(p_74305_1_);
  }
  



  public void saveOptions()
  {
    if (Reflector.FMLClientHandler.exists())
    {
      Object var6 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
      
      if ((var6 != null) && (Reflector.callBoolean(var6, Reflector.FMLClientHandler_isLoading, new Object[0])))
      {
        return;
      }
    }
    
    try
    {
      PrintWriter var9 = new PrintWriter(new FileWriter(optionsFile));
      var9.println("invertYMouse:" + invertMouse);
      var9.println("mouseSensitivity:" + mouseSensitivity);
      var9.println("fov:" + (fovSetting - 70.0F) / 40.0F);
      var9.println("gamma:" + gammaSetting);
      var9.println("saturation:" + saturation);
      var9.println("renderDistance:" + renderDistanceChunks);
      var9.println("guiScale:" + guiScale);
      var9.println("particles:" + particleSetting);
      var9.println("bobView:" + viewBobbing);
      var9.println("anaglyph3d:" + anaglyph);
      var9.println("maxFps:" + limitFramerate);
      var9.println("fboEnable:" + fboEnable);
      var9.println("difficulty:" + difficulty.getDifficultyId());
      var9.println("fancyGraphics:" + fancyGraphics);
      var9.println("ao:" + ambientOcclusion);
      var9.println("renderClouds:" + clouds);
      var9.println("resourcePacks:" + gson.toJson(resourcePacks));
      var9.println("lastServer:" + lastServer);
      var9.println("lang:" + language);
      var9.println("chatVisibility:" + chatVisibility.getChatVisibility());
      var9.println("chatColors:" + chatColours);
      var9.println("chatLinks:" + chatLinks);
      var9.println("chatLinksPrompt:" + chatLinksPrompt);
      var9.println("chatOpacity:" + chatOpacity);
      var9.println("snooperEnabled:" + snooperEnabled);
      var9.println("fullscreen:" + fullScreen);
      var9.println("enableVsync:" + enableVsync);
      var9.println("useVbo:" + field_178881_t);
      var9.println("hideServerAddress:" + hideServerAddress);
      var9.println("advancedItemTooltips:" + advancedItemTooltips);
      var9.println("pauseOnLostFocus:" + pauseOnLostFocus);
      var9.println("touchscreen:" + touchscreen);
      var9.println("overrideWidth:" + overrideWidth);
      var9.println("overrideHeight:" + overrideHeight);
      var9.println("heldItemTooltips:" + heldItemTooltips);
      var9.println("chatHeightFocused:" + chatHeightFocused);
      var9.println("chatHeightUnfocused:" + chatHeightUnfocused);
      var9.println("chatScale:" + chatScale);
      var9.println("chatWidth:" + chatWidth);
      var9.println("showInventoryAchievementHint:" + showInventoryAchievementHint);
      var9.println("mipmapLevels:" + mipmapLevels);
      var9.println("streamBytesPerPixel:" + streamBytesPerPixel);
      var9.println("streamMicVolume:" + streamMicVolume);
      var9.println("streamSystemVolume:" + streamGameVolume);
      var9.println("streamKbps:" + streamKbps);
      var9.println("streamFps:" + streamFps);
      var9.println("streamCompression:" + streamCompression);
      var9.println("streamSendMetadata:" + streamSendMetadata);
      var9.println("streamPreferredServer:" + streamPreferredServer);
      var9.println("streamChatEnabled:" + streamChatEnabled);
      var9.println("streamChatUserFilter:" + streamChatUserFilter);
      var9.println("streamMicToggleBehavior:" + streamMicToggleBehavior);
      var9.println("forceUnicodeFont:" + forceUnicodeFont);
      var9.println("allowBlockAlternatives:" + field_178880_u);
      var9.println("reducedDebugInfo:" + field_178879_v);
      KeyBinding[] var2 = keyBindings;
      int var3 = var2.length;
      

      for (int var4 = 0; var4 < var3; var4++)
      {
        KeyBinding var7 = var2[var4];
        var9.println("key_" + var7.getKeyDescription() + ":" + var7.getKeyCode());
      }
      
      SoundCategory[] var101 = SoundCategory.values();
      var3 = var101.length;
      
      for (var4 = 0; var4 < var3; var4++)
      {
        SoundCategory var8 = var101[var4];
        var9.println("soundCategory_" + var8.getCategoryName() + ":" + getSoundLevel(var8));
      }
      
      EnumPlayerModelParts[] var11 = EnumPlayerModelParts.values();
      var3 = var11.length;
      
      for (var4 = 0; var4 < var3; var4++)
      {
        EnumPlayerModelParts var10 = var11[var4];
        var9.println("modelPart_" + var10.func_179329_c() + ":" + field_178882_aU.contains(var10));
      }
      
      var9.close();
    }
    catch (Exception var81)
    {
      logger.error("Failed to save options", var81);
    }
    
    saveOfOptions();
    sendSettingsToServer();
  }
  
  public float getSoundLevel(SoundCategory p_151438_1_)
  {
    return mapSoundLevels.containsKey(p_151438_1_) ? ((Float)mapSoundLevels.get(p_151438_1_)).floatValue() : 1.0F;
  }
  
  public void setSoundLevel(SoundCategory p_151439_1_, float p_151439_2_)
  {
    mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
    mapSoundLevels.put(p_151439_1_, Float.valueOf(p_151439_2_));
  }
  



  public void sendSettingsToServer()
  {
    if (mc.thePlayer != null)
    {
      int var1 = 0;
      
      EnumPlayerModelParts var3;
      for (Iterator var2 = field_178882_aU.iterator(); var2.hasNext(); var1 |= var3.func_179327_a())
      {
        var3 = (EnumPlayerModelParts)var2.next();
      }
      
      mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C15PacketClientSettings(language, renderDistanceChunks, chatVisibility, chatColours, var1));
    }
  }
  
  public Set func_178876_d()
  {
    return ImmutableSet.copyOf(field_178882_aU);
  }
  
  public void func_178878_a(EnumPlayerModelParts p_178878_1_, boolean p_178878_2_)
  {
    if (p_178878_2_)
    {
      field_178882_aU.add(p_178878_1_);
    }
    else
    {
      field_178882_aU.remove(p_178878_1_);
    }
    
    sendSettingsToServer();
  }
  
  public void func_178877_a(EnumPlayerModelParts p_178877_1_)
  {
    if (!func_178876_d().contains(p_178877_1_))
    {
      field_178882_aU.add(p_178877_1_);
    }
    else
    {
      field_178882_aU.remove(p_178877_1_);
    }
    
    sendSettingsToServer();
  }
  



  public boolean shouldRenderClouds()
  {
    return (renderDistanceChunks >= 4) && (clouds);
  }
  
  private void setOptionFloatValueOF(Options option, float val)
  {
    if (option == Options.CLOUD_HEIGHT)
    {
      ofCloudsHeight = val;
      mc.renderGlobal.resetClouds();
    }
    
    if (option == Options.AO_LEVEL)
    {
      ofAoLevel = val;
      mc.renderGlobal.loadRenderers();
    }
  }
  
  private void setOptionValueOF(Options par1EnumOptions, int par2)
  {
    if (par1EnumOptions == Options.FOG_FANCY)
    {
      switch (ofFogType)
      {
      case 1: 
        ofFogType = 2;
        
        if (!Config.isFancyFogAvailable())
        {
          ofFogType = 3;
        }
        
        break;
      
      case 2: 
        ofFogType = 3;
        break;
      
      case 3: 
        ofFogType = 1;
        break;
      
      default: 
        ofFogType = 1;
      }
      
    }
    if (par1EnumOptions == Options.FOG_START)
    {
      ofFogStart += 0.2F;
      
      if (ofFogStart > 0.81F)
      {
        ofFogStart = 0.2F;
      }
    }
    
    if (par1EnumOptions == Options.MIPMAP_TYPE)
    {
      ofMipmapType += 1;
      
      if (ofMipmapType > 3)
      {
        ofMipmapType = 0;
      }
      
      net.minecraft.src.TextureUtils.refreshBlockTextures();
    }
    
    if (par1EnumOptions == Options.LOAD_FAR)
    {
      ofLoadFar = false;
    }
    
    if (par1EnumOptions == Options.PRELOADED_CHUNKS)
    {
      ofPreloadedChunks = 0;
    }
    
    if (par1EnumOptions == Options.SMOOTH_WORLD)
    {
      ofSmoothWorld = (!ofSmoothWorld);
      Config.updateThreadPriorities();
    }
    
    if (par1EnumOptions == Options.CLOUDS)
    {
      ofClouds += 1;
      
      if (ofClouds > 3)
      {
        ofClouds = 0;
      }
      
      mc.renderGlobal.resetClouds();
    }
    
    if (par1EnumOptions == Options.TREES)
    {
      ofTrees += 1;
      
      if (ofTrees > 2)
      {
        ofTrees = 0;
      }
      
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.DROPPED_ITEMS)
    {
      ofDroppedItems += 1;
      
      if (ofDroppedItems > 2)
      {
        ofDroppedItems = 0;
      }
    }
    
    if (par1EnumOptions == Options.RAIN)
    {
      ofRain += 1;
      
      if (ofRain > 3)
      {
        ofRain = 0;
      }
    }
    
    if (par1EnumOptions == Options.ANIMATED_WATER)
    {
      ofAnimatedWater += 1;
      
      if (ofAnimatedWater > 2)
      {
        ofAnimatedWater = 0;
      }
    }
    
    if (par1EnumOptions == Options.ANIMATED_LAVA)
    {
      ofAnimatedLava += 1;
      
      if (ofAnimatedLava > 2)
      {
        ofAnimatedLava = 0;
      }
    }
    
    if (par1EnumOptions == Options.ANIMATED_FIRE)
    {
      ofAnimatedFire = (!ofAnimatedFire);
    }
    
    if (par1EnumOptions == Options.ANIMATED_PORTAL)
    {
      ofAnimatedPortal = (!ofAnimatedPortal);
    }
    
    if (par1EnumOptions == Options.ANIMATED_REDSTONE)
    {
      ofAnimatedRedstone = (!ofAnimatedRedstone);
    }
    
    if (par1EnumOptions == Options.ANIMATED_EXPLOSION)
    {
      ofAnimatedExplosion = (!ofAnimatedExplosion);
    }
    
    if (par1EnumOptions == Options.ANIMATED_FLAME)
    {
      ofAnimatedFlame = (!ofAnimatedFlame);
    }
    
    if (par1EnumOptions == Options.ANIMATED_SMOKE)
    {
      ofAnimatedSmoke = (!ofAnimatedSmoke);
    }
    
    if (par1EnumOptions == Options.VOID_PARTICLES)
    {
      ofVoidParticles = (!ofVoidParticles);
    }
    
    if (par1EnumOptions == Options.WATER_PARTICLES)
    {
      ofWaterParticles = (!ofWaterParticles);
    }
    
    if (par1EnumOptions == Options.PORTAL_PARTICLES)
    {
      ofPortalParticles = (!ofPortalParticles);
    }
    
    if (par1EnumOptions == Options.POTION_PARTICLES)
    {
      ofPotionParticles = (!ofPotionParticles);
    }
    
    if (par1EnumOptions == Options.DRIPPING_WATER_LAVA)
    {
      ofDrippingWaterLava = (!ofDrippingWaterLava);
    }
    
    if (par1EnumOptions == Options.ANIMATED_TERRAIN)
    {
      ofAnimatedTerrain = (!ofAnimatedTerrain);
    }
    
    if (par1EnumOptions == Options.ANIMATED_TEXTURES)
    {
      ofAnimatedTextures = (!ofAnimatedTextures);
    }
    
    if (par1EnumOptions == Options.RAIN_SPLASH)
    {
      ofRainSplash = (!ofRainSplash);
    }
    
    if (par1EnumOptions == Options.LAGOMETER)
    {
      ofLagometer = (!ofLagometer);
    }
    
    if (par1EnumOptions == Options.AUTOSAVE_TICKS)
    {
      ofAutoSaveTicks *= 10;
      
      if (ofAutoSaveTicks > 40000)
      {
        ofAutoSaveTicks = 40;
      }
    }
    
    if (par1EnumOptions == Options.BETTER_GRASS)
    {
      ofBetterGrass += 1;
      
      if (ofBetterGrass > 3)
      {
        ofBetterGrass = 1;
      }
      
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.CONNECTED_TEXTURES)
    {
      ofConnectedTextures += 1;
      
      if (ofConnectedTextures > 3)
      {
        ofConnectedTextures = 1;
      }
      
      if (ofConnectedTextures != 2)
      {
        mc.func_175603_A();
      }
    }
    
    if (par1EnumOptions == Options.WEATHER)
    {
      ofWeather = (!ofWeather);
    }
    
    if (par1EnumOptions == Options.SKY)
    {
      ofSky = (!ofSky);
    }
    
    if (par1EnumOptions == Options.STARS)
    {
      ofStars = (!ofStars);
    }
    
    if (par1EnumOptions == Options.SUN_MOON)
    {
      ofSunMoon = (!ofSunMoon);
    }
    
    if (par1EnumOptions == Options.CHUNK_UPDATES)
    {
      ofChunkUpdates += 1;
      
      if (ofChunkUpdates > 5)
      {
        ofChunkUpdates = 1;
      }
    }
    
    if (par1EnumOptions == Options.CHUNK_LOADING)
    {
      ofChunkLoading += 1;
      
      if (ofChunkLoading > 2)
      {
        ofChunkLoading = 0;
      }
      
      updateChunkLoading();
    }
    
    if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC)
    {
      ofChunkUpdatesDynamic = (!ofChunkUpdatesDynamic);
    }
    
    if (par1EnumOptions == Options.TIME)
    {
      ofTime += 1;
      
      if (ofTime > 3)
      {
        ofTime = 0;
      }
    }
    
    if (par1EnumOptions == Options.CLEAR_WATER)
    {
      ofClearWater = (!ofClearWater);
      updateWaterOpacity();
    }
    
    if (par1EnumOptions == Options.AA_LEVEL)
    {
      ofAaLevel = 0;
    }
    
    if (par1EnumOptions == Options.PROFILER)
    {
      ofProfiler = (!ofProfiler);
    }
    
    if (par1EnumOptions == Options.BETTER_SNOW)
    {
      ofBetterSnow = (!ofBetterSnow);
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.SWAMP_COLORS)
    {
      ofSwampColors = (!ofSwampColors);
      CustomColorizer.updateUseDefaultColorMultiplier();
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.RANDOM_MOBS)
    {
      ofRandomMobs = (!ofRandomMobs);
      net.minecraft.src.RandomMobs.resetTextures();
    }
    
    if (par1EnumOptions == Options.SMOOTH_BIOMES)
    {
      ofSmoothBiomes = (!ofSmoothBiomes);
      CustomColorizer.updateUseDefaultColorMultiplier();
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.CUSTOM_FONTS)
    {
      ofCustomFonts = (!ofCustomFonts);
      mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
      mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
    }
    
    if (par1EnumOptions == Options.CUSTOM_COLORS)
    {
      ofCustomColors = (!ofCustomColors);
      CustomColorizer.update();
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.CUSTOM_SKY)
    {
      ofCustomSky = (!ofCustomSky);
      CustomSky.update();
    }
    
    if (par1EnumOptions == Options.SHOW_CAPES)
    {
      ofShowCapes = (!ofShowCapes);
    }
    
    if (par1EnumOptions == Options.NATURAL_TEXTURES)
    {
      ofNaturalTextures = (!ofNaturalTextures);
      NaturalTextures.update();
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.FAST_MATH)
    {
      ofFastMath = (!ofFastMath);
      MathHelper.fastMath = ofFastMath;
    }
    
    if (par1EnumOptions == Options.FAST_RENDER)
    {
      ofFastRender = (!ofFastRender);
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS)
    {
      if (ofTranslucentBlocks == 0)
      {
        ofTranslucentBlocks = 1;
      }
      else if (ofTranslucentBlocks == 1)
      {
        ofTranslucentBlocks = 2;
      }
      else if (ofTranslucentBlocks == 2)
      {
        ofTranslucentBlocks = 0;
      }
      else
      {
        ofTranslucentBlocks = 0;
      }
      
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.LAZY_CHUNK_LOADING)
    {
      ofLazyChunkLoading = (!ofLazyChunkLoading);
      Config.updateAvailableProcessors();
      
      if (!Config.isSingleProcessor())
      {
        ofLazyChunkLoading = false;
      }
      
      mc.renderGlobal.loadRenderers();
    }
    
    if (par1EnumOptions == Options.FULLSCREEN_MODE)
    {
      List modeList = Arrays.asList(Config.getFullscreenModes());
      
      if (ofFullscreenMode.equals("Default"))
      {
        ofFullscreenMode = ((String)modeList.get(0));
      }
      else
      {
        int index = modeList.indexOf(ofFullscreenMode);
        
        if (index < 0)
        {
          ofFullscreenMode = "Default";
        }
        else
        {
          index++;
          
          if (index >= modeList.size())
          {
            ofFullscreenMode = "Default";
          }
          else
          {
            ofFullscreenMode = ((String)modeList.get(index));
          }
        }
      }
    }
    
    if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS)
    {
      heldItemTooltips = (!heldItemTooltips);
    }
  }
  
  private String getKeyBindingOF(Options par1EnumOptions)
  {
    String var2 = I18n.format(par1EnumOptions.getEnumString(), new Object[0]) + ": ";
    
    if (var2 == null)
    {
      var2 = par1EnumOptions.getEnumString();
    }
    
    if (par1EnumOptions == Options.RENDER_DISTANCE)
    {
      int var61 = (int)getOptionFloatValue(par1EnumOptions);
      String str = "Tiny";
      byte baseDist = 2;
      
      if (var61 >= 4)
      {
        str = "Short";
        baseDist = 4;
      }
      
      if (var61 >= 8)
      {
        str = "Normal";
        baseDist = 8;
      }
      
      if (var61 >= 16)
      {
        str = "Far";
        baseDist = 16;
      }
      
      if (var61 >= 32)
      {
        str = "Extreme";
        baseDist = 32;
      }
      
      int diff = renderDistanceChunks - baseDist;
      String descr = str;
      
      if (diff > 0)
      {
        descr = str + "+";
      }
      
      return var2 + var61 + " " + descr;
    }
    if (par1EnumOptions == Options.FOG_FANCY)
    {
      switch (ofFogType)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      
      case 3: 
        return var2 + "OFF";
      }
      
      return var2 + "OFF";
    }
    
    if (par1EnumOptions == Options.FOG_START)
    {
      return var2 + ofFogStart;
    }
    if (par1EnumOptions == Options.MIPMAP_TYPE)
    {
      switch (ofMipmapType)
      {
      case 0: 
        return var2 + "Nearest";
      
      case 1: 
        return var2 + "Linear";
      
      case 2: 
        return var2 + "Bilinear";
      
      case 3: 
        return var2 + "Trilinear";
      }
      
      return var2 + "Nearest";
    }
    
    if (par1EnumOptions == Options.LOAD_FAR)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.PRELOADED_CHUNKS)
    {
      return var2 + ofPreloadedChunks;
    }
    if (par1EnumOptions == Options.SMOOTH_WORLD)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.CLOUDS)
    {
      switch (ofClouds)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      
      case 3: 
        return var2 + "OFF";
      }
      
      return var2 + "Default";
    }
    
    if (par1EnumOptions == Options.TREES)
    {
      switch (ofTrees)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      }
      
      return var2 + "Default";
    }
    
    if (par1EnumOptions == Options.DROPPED_ITEMS)
    {
      switch (ofDroppedItems)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      }
      
      return var2 + "Default";
    }
    
    if (par1EnumOptions == Options.RAIN)
    {
      switch (ofRain)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      
      case 3: 
        return var2 + "OFF";
      }
      
      return var2 + "Default";
    }
    
    if (par1EnumOptions == Options.ANIMATED_WATER)
    {
      switch (ofAnimatedWater)
      {
      case 1: 
        return var2 + "Dynamic";
      
      case 2: 
        return var2 + "OFF";
      }
      
      return var2 + "ON";
    }
    
    if (par1EnumOptions == Options.ANIMATED_LAVA)
    {
      switch (ofAnimatedLava)
      {
      case 1: 
        return var2 + "Dynamic";
      
      case 2: 
        return var2 + "OFF";
      }
      
      return var2 + "ON";
    }
    
    if (par1EnumOptions == Options.ANIMATED_FIRE)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_PORTAL)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_REDSTONE)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_EXPLOSION)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_FLAME)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_SMOKE)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.VOID_PARTICLES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.WATER_PARTICLES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.PORTAL_PARTICLES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.POTION_PARTICLES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.DRIPPING_WATER_LAVA)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_TERRAIN)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.ANIMATED_TEXTURES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.RAIN_SPLASH)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.LAGOMETER)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.AUTOSAVE_TICKS)
    {
      return var2 + "30min";
    }
    if (par1EnumOptions == Options.BETTER_GRASS)
    {
      switch (ofBetterGrass)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      }
      
      return var2 + "OFF";
    }
    
    if (par1EnumOptions == Options.CONNECTED_TEXTURES)
    {
      switch (ofConnectedTextures)
      {
      case 1: 
        return var2 + "Fast";
      
      case 2: 
        return var2 + "Fancy";
      }
      
      return var2 + "OFF";
    }
    
    if (par1EnumOptions == Options.WEATHER)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.SKY)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.STARS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.SUN_MOON)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.CHUNK_UPDATES)
    {
      return var2 + ofChunkUpdates;
    }
    if (par1EnumOptions == Options.CHUNK_LOADING)
    {
      return var2 + "Default";
    }
    if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.TIME)
    {
      return var2 + "Default";
    }
    if (par1EnumOptions == Options.CLEAR_WATER)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.AA_LEVEL)
    {
      return var2 + ofAaLevel;
    }
    if (par1EnumOptions == Options.PROFILER)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.BETTER_SNOW)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.SWAMP_COLORS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.RANDOM_MOBS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.SMOOTH_BIOMES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.CUSTOM_FONTS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.CUSTOM_COLORS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.CUSTOM_SKY)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.SHOW_CAPES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.NATURAL_TEXTURES)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.FAST_MATH)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.FAST_RENDER)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS)
    {
      return var2 + "Default";
    }
    if (par1EnumOptions == Options.LAZY_CHUNK_LOADING)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.FULLSCREEN_MODE)
    {
      return var2 + ofFullscreenMode;
    }
    if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS)
    {
      return var2 + "OFF";
    }
    if (par1EnumOptions == Options.FRAMERATE_LIMIT)
    {
      float var6 = getOptionFloatValue(par1EnumOptions);
      return var2 + (int)var6 + " fps";
    }
    

    return null;
  }
  

  public void loadOfOptions()
  {
    try
    {
      File exception = optionsFileOF;
      
      if (!exception.exists())
      {
        exception = optionsFile;
      }
      
      if (!exception.exists())
      {
        return;
      }
      
      BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
      String s = "";
      
      while ((s = bufferedreader.readLine()) != null)
      {
        try
        {
          String[] exception1 = s.split(":");
          
          if ((exception1[0].equals("ofRenderDistanceChunks")) && (exception1.length >= 2))
          {
            renderDistanceChunks = Integer.valueOf(exception1[1]).intValue();
            renderDistanceChunks = Config.limit(renderDistanceChunks, 2, 32);
          }
          
          if ((exception1[0].equals("ofFogType")) && (exception1.length >= 2))
          {
            ofFogType = Integer.valueOf(exception1[1]).intValue();
            ofFogType = Config.limit(ofFogType, 1, 3);
          }
          
          if ((exception1[0].equals("ofFogStart")) && (exception1.length >= 2))
          {
            ofFogStart = Float.valueOf(exception1[1]).floatValue();
            
            if (ofFogStart < 0.2F)
            {
              ofFogStart = 0.2F;
            }
            
            if (ofFogStart > 0.81F)
            {
              ofFogStart = 0.8F;
            }
          }
          
          if ((exception1[0].equals("ofMipmapType")) && (exception1.length >= 2))
          {
            ofMipmapType = Integer.valueOf(exception1[1]).intValue();
            ofMipmapType = Config.limit(ofMipmapType, 0, 3);
          }
          
          if ((exception1[0].equals("ofLoadFar")) && (exception1.length >= 2))
          {
            ofLoadFar = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofPreloadedChunks")) && (exception1.length >= 2))
          {
            ofPreloadedChunks = Integer.valueOf(exception1[1]).intValue();
            
            if (ofPreloadedChunks < 0)
            {
              ofPreloadedChunks = 0;
            }
            
            if (ofPreloadedChunks > 8)
            {
              ofPreloadedChunks = 8;
            }
          }
          
          if ((exception1[0].equals("ofOcclusionFancy")) && (exception1.length >= 2))
          {
            ofOcclusionFancy = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofSmoothWorld")) && (exception1.length >= 2))
          {
            ofSmoothWorld = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAoLevel")) && (exception1.length >= 2))
          {
            ofAoLevel = Float.valueOf(exception1[1]).floatValue();
            ofAoLevel = Config.limit(ofAoLevel, 0.0F, 1.0F);
          }
          
          if ((exception1[0].equals("ofClouds")) && (exception1.length >= 2))
          {
            ofClouds = Integer.valueOf(exception1[1]).intValue();
            ofClouds = Config.limit(ofClouds, 0, 3);
          }
          
          if ((exception1[0].equals("ofCloudsHeight")) && (exception1.length >= 2))
          {
            ofCloudsHeight = Float.valueOf(exception1[1]).floatValue();
            ofCloudsHeight = Config.limit(ofCloudsHeight, 0.0F, 1.0F);
          }
          
          if ((exception1[0].equals("ofTrees")) && (exception1.length >= 2))
          {
            ofTrees = Integer.valueOf(exception1[1]).intValue();
            ofTrees = Config.limit(ofTrees, 0, 2);
          }
          
          if ((exception1[0].equals("ofDroppedItems")) && (exception1.length >= 2))
          {
            ofDroppedItems = Integer.valueOf(exception1[1]).intValue();
            ofDroppedItems = Config.limit(ofDroppedItems, 0, 2);
          }
          
          if ((exception1[0].equals("ofRain")) && (exception1.length >= 2))
          {
            ofRain = Integer.valueOf(exception1[1]).intValue();
            ofRain = Config.limit(ofRain, 0, 3);
          }
          
          if ((exception1[0].equals("ofAnimatedWater")) && (exception1.length >= 2))
          {
            ofAnimatedWater = Integer.valueOf(exception1[1]).intValue();
            ofAnimatedWater = Config.limit(ofAnimatedWater, 0, 2);
          }
          
          if ((exception1[0].equals("ofAnimatedLava")) && (exception1.length >= 2))
          {
            ofAnimatedLava = Integer.valueOf(exception1[1]).intValue();
            ofAnimatedLava = Config.limit(ofAnimatedLava, 0, 2);
          }
          
          if ((exception1[0].equals("ofAnimatedFire")) && (exception1.length >= 2))
          {
            ofAnimatedFire = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedPortal")) && (exception1.length >= 2))
          {
            ofAnimatedPortal = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedRedstone")) && (exception1.length >= 2))
          {
            ofAnimatedRedstone = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedExplosion")) && (exception1.length >= 2))
          {
            ofAnimatedExplosion = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedFlame")) && (exception1.length >= 2))
          {
            ofAnimatedFlame = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedSmoke")) && (exception1.length >= 2))
          {
            ofAnimatedSmoke = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofVoidParticles")) && (exception1.length >= 2))
          {
            ofVoidParticles = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofWaterParticles")) && (exception1.length >= 2))
          {
            ofWaterParticles = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofPortalParticles")) && (exception1.length >= 2))
          {
            ofPortalParticles = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofPotionParticles")) && (exception1.length >= 2))
          {
            ofPotionParticles = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofDrippingWaterLava")) && (exception1.length >= 2))
          {
            ofDrippingWaterLava = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedTerrain")) && (exception1.length >= 2))
          {
            ofAnimatedTerrain = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAnimatedTextures")) && (exception1.length >= 2))
          {
            ofAnimatedTextures = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofRainSplash")) && (exception1.length >= 2))
          {
            ofRainSplash = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofLagometer")) && (exception1.length >= 2))
          {
            ofLagometer = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofAutoSaveTicks")) && (exception1.length >= 2))
          {
            ofAutoSaveTicks = Integer.valueOf(exception1[1]).intValue();
            ofAutoSaveTicks = Config.limit(ofAutoSaveTicks, 40, 40000);
          }
          
          if ((exception1[0].equals("ofBetterGrass")) && (exception1.length >= 2))
          {
            ofBetterGrass = Integer.valueOf(exception1[1]).intValue();
            ofBetterGrass = Config.limit(ofBetterGrass, 1, 3);
          }
          
          if ((exception1[0].equals("ofConnectedTextures")) && (exception1.length >= 2))
          {
            ofConnectedTextures = Integer.valueOf(exception1[1]).intValue();
            ofConnectedTextures = Config.limit(ofConnectedTextures, 1, 3);
          }
          
          if ((exception1[0].equals("ofWeather")) && (exception1.length >= 2))
          {
            ofWeather = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofSky")) && (exception1.length >= 2))
          {
            ofSky = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofStars")) && (exception1.length >= 2))
          {
            ofStars = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofSunMoon")) && (exception1.length >= 2))
          {
            ofSunMoon = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofChunkUpdates")) && (exception1.length >= 2))
          {
            ofChunkUpdates = Integer.valueOf(exception1[1]).intValue();
            ofChunkUpdates = Config.limit(ofChunkUpdates, 1, 5);
          }
          
          if ((exception1[0].equals("ofChunkLoading")) && (exception1.length >= 2))
          {
            ofChunkLoading = Integer.valueOf(exception1[1]).intValue();
            ofChunkLoading = Config.limit(ofChunkLoading, 0, 2);
            updateChunkLoading();
          }
          
          if ((exception1[0].equals("ofChunkUpdatesDynamic")) && (exception1.length >= 2))
          {
            ofChunkUpdatesDynamic = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofTime")) && (exception1.length >= 2))
          {
            ofTime = Integer.valueOf(exception1[1]).intValue();
            ofTime = Config.limit(ofTime, 0, 3);
          }
          
          if ((exception1[0].equals("ofClearWater")) && (exception1.length >= 2))
          {
            ofClearWater = Boolean.valueOf(exception1[1]).booleanValue();
            updateWaterOpacity();
          }
          
          if ((exception1[0].equals("ofAaLevel")) && (exception1.length >= 2))
          {
            ofAaLevel = Integer.valueOf(exception1[1]).intValue();
            ofAaLevel = Config.limit(ofAaLevel, 0, 16);
          }
          
          if ((exception1[0].equals("ofProfiler")) && (exception1.length >= 2))
          {
            ofProfiler = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofBetterSnow")) && (exception1.length >= 2))
          {
            ofBetterSnow = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofSwampColors")) && (exception1.length >= 2))
          {
            ofSwampColors = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofRandomMobs")) && (exception1.length >= 2))
          {
            ofRandomMobs = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofSmoothBiomes")) && (exception1.length >= 2))
          {
            ofSmoothBiomes = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofCustomFonts")) && (exception1.length >= 2))
          {
            ofCustomFonts = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofCustomColors")) && (exception1.length >= 2))
          {
            ofCustomColors = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofCustomSky")) && (exception1.length >= 2))
          {
            ofCustomSky = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofShowCapes")) && (exception1.length >= 2))
          {
            ofShowCapes = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofNaturalTextures")) && (exception1.length >= 2))
          {
            ofNaturalTextures = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofLazyChunkLoading")) && (exception1.length >= 2))
          {
            ofLazyChunkLoading = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofFullscreenMode")) && (exception1.length >= 2))
          {
            ofFullscreenMode = exception1[1];
          }
          
          if ((exception1[0].equals("ofFastMath")) && (exception1.length >= 2))
          {
            ofFastMath = Boolean.valueOf(exception1[1]).booleanValue();
            MathHelper.fastMath = ofFastMath;
          }
          
          if ((exception1[0].equals("ofFastRender")) && (exception1.length >= 2))
          {
            ofFastRender = Boolean.valueOf(exception1[1]).booleanValue();
          }
          
          if ((exception1[0].equals("ofTranslucentBlocks")) && (exception1.length >= 2))
          {
            ofTranslucentBlocks = Integer.valueOf(exception1[1]).intValue();
            ofTranslucentBlocks = Config.limit(ofTranslucentBlocks, 0, 2);
          }
          
          if (exception1[0].equals("key_" + ofKeyBindZoom.getKeyDescription()))
          {
            ofKeyBindZoom.setKeyCode(Integer.parseInt(exception1[1]));
          }
        }
        catch (Exception var5)
        {
          Config.dbg("Skipping bad option: " + s);
          var5.printStackTrace();
        }
      }
      
      KeyBinding.resetKeyBindingArrayAndHash();
      bufferedreader.close();
    }
    catch (Exception var6)
    {
      Config.warn("Failed to load options");
      var6.printStackTrace();
    }
  }
  
  public void saveOfOptions()
  {
    try
    {
      PrintWriter exception = new PrintWriter(new FileWriter(optionsFileOF));
      exception.println("ofRenderDistanceChunks:" + renderDistanceChunks);
      exception.println("ofFogType:" + ofFogType);
      exception.println("ofFogStart:" + ofFogStart);
      exception.println("ofMipmapType:" + ofMipmapType);
      exception.println("ofLoadFar:" + ofLoadFar);
      exception.println("ofPreloadedChunks:" + ofPreloadedChunks);
      exception.println("ofOcclusionFancy:" + ofOcclusionFancy);
      exception.println("ofSmoothWorld:" + ofSmoothWorld);
      exception.println("ofAoLevel:" + ofAoLevel);
      exception.println("ofClouds:" + ofClouds);
      exception.println("ofCloudsHeight:" + ofCloudsHeight);
      exception.println("ofTrees:" + ofTrees);
      exception.println("ofDroppedItems:" + ofDroppedItems);
      exception.println("ofRain:" + ofRain);
      exception.println("ofAnimatedWater:" + ofAnimatedWater);
      exception.println("ofAnimatedLava:" + ofAnimatedLava);
      exception.println("ofAnimatedFire:" + ofAnimatedFire);
      exception.println("ofAnimatedPortal:" + ofAnimatedPortal);
      exception.println("ofAnimatedRedstone:" + ofAnimatedRedstone);
      exception.println("ofAnimatedExplosion:" + ofAnimatedExplosion);
      exception.println("ofAnimatedFlame:" + ofAnimatedFlame);
      exception.println("ofAnimatedSmoke:" + ofAnimatedSmoke);
      exception.println("ofVoidParticles:" + ofVoidParticles);
      exception.println("ofWaterParticles:" + ofWaterParticles);
      exception.println("ofPortalParticles:" + ofPortalParticles);
      exception.println("ofPotionParticles:" + ofPotionParticles);
      exception.println("ofDrippingWaterLava:" + ofDrippingWaterLava);
      exception.println("ofAnimatedTerrain:" + ofAnimatedTerrain);
      exception.println("ofAnimatedTextures:" + ofAnimatedTextures);
      exception.println("ofRainSplash:" + ofRainSplash);
      exception.println("ofLagometer:" + ofLagometer);
      exception.println("ofAutoSaveTicks:" + ofAutoSaveTicks);
      exception.println("ofBetterGrass:" + ofBetterGrass);
      exception.println("ofConnectedTextures:" + ofConnectedTextures);
      exception.println("ofWeather:" + ofWeather);
      exception.println("ofSky:" + ofSky);
      exception.println("ofStars:" + ofStars);
      exception.println("ofSunMoon:" + ofSunMoon);
      exception.println("ofChunkUpdates:" + ofChunkUpdates);
      exception.println("ofChunkLoading:" + ofChunkLoading);
      exception.println("ofChunkUpdatesDynamic:" + ofChunkUpdatesDynamic);
      exception.println("ofTime:" + ofTime);
      exception.println("ofClearWater:" + ofClearWater);
      exception.println("ofAaLevel:" + ofAaLevel);
      exception.println("ofProfiler:" + ofProfiler);
      exception.println("ofBetterSnow:" + ofBetterSnow);
      exception.println("ofSwampColors:" + ofSwampColors);
      exception.println("ofRandomMobs:" + ofRandomMobs);
      exception.println("ofSmoothBiomes:" + ofSmoothBiomes);
      exception.println("ofCustomFonts:" + ofCustomFonts);
      exception.println("ofCustomColors:" + ofCustomColors);
      exception.println("ofCustomSky:" + ofCustomSky);
      exception.println("ofShowCapes:" + ofShowCapes);
      exception.println("ofNaturalTextures:" + ofNaturalTextures);
      exception.println("ofLazyChunkLoading:" + ofLazyChunkLoading);
      exception.println("ofFullscreenMode:" + ofFullscreenMode);
      exception.println("ofFastMath:" + ofFastMath);
      exception.println("ofFastRender:" + ofFastRender);
      exception.println("ofTranslucentBlocks:" + ofTranslucentBlocks);
      exception.println("key_" + ofKeyBindZoom.getKeyDescription() + ":" + ofKeyBindZoom.getKeyCode());
      exception.close();
    }
    catch (Exception var2)
    {
      Config.warn("Failed to save options");
      var2.printStackTrace();
    }
  }
  
  public void resetSettings()
  {
    renderDistanceChunks = 8;
    viewBobbing = true;
    anaglyph = false;
    limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
    enableVsync = false;
    updateVSync();
    mipmapLevels = 4;
    fancyGraphics = true;
    ambientOcclusion = 2;
    clouds = true;
    fovSetting = 70.0F;
    gammaSetting = 0.0F;
    guiScale = 0;
    particleSetting = 0;
    heldItemTooltips = true;
    field_178881_t = false;
    field_178880_u = true;
    forceUnicodeFont = false;
    ofFogType = 1;
    ofFogStart = 0.8F;
    ofMipmapType = 0;
    ofLoadFar = false;
    ofPreloadedChunks = 0;
    ofOcclusionFancy = false;
    Config.updateAvailableProcessors();
    ofSmoothWorld = Config.isSingleProcessor();
    ofLazyChunkLoading = Config.isSingleProcessor();
    ofFastMath = false;
    ofFastRender = true;
    ofTranslucentBlocks = 0;
    ofAoLevel = 1.0F;
    ofAaLevel = 0;
    ofClouds = 0;
    ofCloudsHeight = 0.0F;
    ofTrees = 0;
    ofRain = 0;
    ofBetterGrass = 3;
    ofAutoSaveTicks = 4000;
    ofLagometer = false;
    ofProfiler = false;
    ofWeather = true;
    ofSky = true;
    ofStars = true;
    ofSunMoon = true;
    ofChunkUpdates = 1;
    ofChunkLoading = 0;
    ofChunkUpdatesDynamic = false;
    ofTime = 0;
    ofClearWater = false;
    ofBetterSnow = false;
    ofFullscreenMode = "Default";
    ofSwampColors = true;
    ofRandomMobs = true;
    ofSmoothBiomes = true;
    ofCustomFonts = true;
    ofCustomColors = true;
    ofCustomSky = true;
    ofShowCapes = true;
    ofConnectedTextures = 2;
    ofNaturalTextures = false;
    ofAnimatedWater = 0;
    ofAnimatedLava = 0;
    ofAnimatedFire = true;
    ofAnimatedPortal = true;
    ofAnimatedRedstone = true;
    ofAnimatedExplosion = true;
    ofAnimatedFlame = true;
    ofAnimatedSmoke = true;
    ofVoidParticles = true;
    ofWaterParticles = true;
    ofRainSplash = true;
    ofPortalParticles = true;
    ofPotionParticles = true;
    ofDrippingWaterLava = true;
    ofAnimatedTerrain = true;
    ofAnimatedTextures = true;
    updateWaterOpacity();
    mc.refreshResources();
    saveOptions();
  }
  
  public void updateVSync()
  {
    Display.setVSyncEnabled(enableVsync);
  }
  
  private void updateWaterOpacity()
  {
    if ((mc.isIntegratedServerRunning()) && (mc.getIntegratedServer() != null))
    {
      Config.waterOpacityChanged = true;
    }
    
    net.minecraft.src.ClearWater.updateWaterOpacity(this, mc.theWorld);
  }
  
  public void updateChunkLoading()
  {
    if (mc.renderGlobal != null)
    {
      mc.renderGlobal.loadRenderers();
    }
  }
  
  public void setAllAnimations(boolean flag)
  {
    int animVal = flag ? 0 : 2;
    ofAnimatedWater = animVal;
    ofAnimatedLava = animVal;
    ofAnimatedFire = flag;
    ofAnimatedPortal = flag;
    ofAnimatedRedstone = flag;
    ofAnimatedExplosion = flag;
    ofAnimatedFlame = flag;
    ofAnimatedSmoke = flag;
    ofVoidParticles = flag;
    ofWaterParticles = flag;
    ofRainSplash = flag;
    ofPortalParticles = flag;
    ofPotionParticles = flag;
    particleSetting = (flag ? 0 : 2);
    ofDrippingWaterLava = flag;
    ofAnimatedTerrain = flag;
    ofAnimatedTextures = flag;
  }
  
  public static enum Options
  {
    INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
    SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
    FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0F, 110.0F, 1.0F), 
    GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
    SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false), 
    RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0F, 16.0F, 1.0F), 
    VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
    ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true), 
    FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F), 
    FBO_ENABLE("FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true), 
    RENDER_CLOUDS("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, true), 
    GRAPHICS("GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false), 
    AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false), 
    GUI_SCALE("GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false), 
    PARTICLES("PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false), 
    CHAT_VISIBILITY("CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false), 
    CHAT_COLOR("CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true), 
    CHAT_LINKS("CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true), 
    CHAT_OPACITY("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false), 
    CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true), 
    SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true), 
    USE_FULLSCREEN("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true), 
    ENABLE_VSYNC("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true), 
    USE_VBO("USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true), 
    TOUCHSCREEN("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true), 
    CHAT_SCALE("CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false), 
    CHAT_WIDTH("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false), 
    CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false), 
    CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false), 
    MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F), 
    FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true), 
    STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false), 
    STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false), 
    STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false), 
    STREAM_KBPS("STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false), 
    STREAM_FPS("STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false), 
    STREAM_COMPRESSION("STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "options.stream.compression", false, false), 
    STREAM_SEND_METADATA("STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true), 
    STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false), 
    STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false), 
    STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false), 
    BLOCK_ALTERNATIVES("BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true), 
    REDUCED_DEBUG_INFO("REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true), 
    FOG_FANCY("FOG_FANCY", 43, "FOG", 999, "Fog", false, false), 
    FOG_START("FOG_START", 44, "", 999, "Fog Start", false, false), 
    MIPMAP_TYPE("MIPMAP_TYPE", 45, "", 999, "Mipmap Type", false, false), 
    LOAD_FAR("LOAD_FAR", 46, "", 999, "Load Far", false, false), 
    PRELOADED_CHUNKS("PRELOADED_CHUNKS", 47, "", 999, "Preloaded Chunks", false, false), 
    CLOUDS("CLOUDS", 48, "", 999, "Clouds", false, false), 
    CLOUD_HEIGHT("CLOUD_HEIGHT", 49, "", 999, "Cloud Height", true, false), 
    TREES("TREES", 50, "", 999, "Trees", false, false), 
    RAIN("RAIN", 51, "", 999, "Rain & Snow", false, false), 
    ANIMATED_WATER("ANIMATED_WATER", 52, "", 999, "Water Animated", false, false), 
    ANIMATED_LAVA("ANIMATED_LAVA", 53, "", 999, "Lava Animated", false, false), 
    ANIMATED_FIRE("ANIMATED_FIRE", 54, "", 999, "Fire Animated", false, false), 
    ANIMATED_PORTAL("ANIMATED_PORTAL", 55, "", 999, "Portal Animated", false, false), 
    AO_LEVEL("AO_LEVEL", 56, "", 999, "Smooth Lighting Level", true, false), 
    LAGOMETER("LAGOMETER", 57, "", 999, "Lagometer", false, false), 
    AUTOSAVE_TICKS("AUTOSAVE_TICKS", 58, "", 999, "Autosave", false, false), 
    BETTER_GRASS("BETTER_GRASS", 59, "", 999, "Better Grass", false, false), 
    ANIMATED_REDSTONE("ANIMATED_REDSTONE", 60, "", 999, "Redstone Animated", false, false), 
    ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 61, "", 999, "Explosion Animated", false, false), 
    ANIMATED_FLAME("ANIMATED_FLAME", 62, "", 999, "Flame Animated", false, false), 
    ANIMATED_SMOKE("ANIMATED_SMOKE", 63, "", 999, "Smoke Animated", false, false), 
    WEATHER("WEATHER", 64, "", 999, "Weather", false, false), 
    SKY("SKY", 65, "", 999, "Sky", false, false), 
    STARS("STARS", 66, "", 999, "Stars", false, false), 
    SUN_MOON("SUN_MOON", 67, "", 999, "Sun & Moon", false, false), 
    CHUNK_UPDATES("CHUNK_UPDATES", 68, "", 999, "Chunk Updates", false, false), 
    CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 69, "", 999, "Dynamic Updates", false, false), 
    TIME("TIME", 70, "", 999, "Time", false, false), 
    CLEAR_WATER("CLEAR_WATER", 71, "", 999, "Clear Water", false, false), 
    SMOOTH_WORLD("SMOOTH_WORLD", 72, "", 999, "Smooth World", false, false), 
    VOID_PARTICLES("VOID_PARTICLES", 73, "", 999, "Void Particles", false, false), 
    WATER_PARTICLES("WATER_PARTICLES", 74, "", 999, "Water Particles", false, false), 
    RAIN_SPLASH("RAIN_SPLASH", 75, "", 999, "Rain Splash", false, false), 
    PORTAL_PARTICLES("PORTAL_PARTICLES", 76, "", 999, "Portal Particles", false, false), 
    POTION_PARTICLES("POTION_PARTICLES", 77, "", 999, "Potion Particles", false, false), 
    PROFILER("PROFILER", 78, "", 999, "Debug Profiler", false, false), 
    DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 79, "", 999, "Dripping Water/Lava", false, false), 
    BETTER_SNOW("BETTER_SNOW", 80, "", 999, "Better Snow", false, false), 
    FULLSCREEN_MODE("FULLSCREEN_MODE", 81, "", 999, "Fullscreen Mode", false, false), 
    ANIMATED_TERRAIN("ANIMATED_TERRAIN", 82, "", 999, "Terrain Animated", false, false), 
    SWAMP_COLORS("SWAMP_COLORS", 83, "", 999, "Swamp Colors", false, false), 
    RANDOM_MOBS("RANDOM_MOBS", 84, "", 999, "Random Mobs", false, false), 
    SMOOTH_BIOMES("SMOOTH_BIOMES", 85, "", 999, "Smooth Biomes", false, false), 
    CUSTOM_FONTS("CUSTOM_FONTS", 86, "", 999, "Custom Fonts", false, false), 
    CUSTOM_COLORS("CUSTOM_COLORS", 87, "", 999, "Custom Colors", false, false), 
    SHOW_CAPES("SHOW_CAPES", 88, "", 999, "Show Capes", false, false), 
    CONNECTED_TEXTURES("CONNECTED_TEXTURES", 89, "", 999, "Connected Textures", false, false), 
    AA_LEVEL("AA_LEVEL", 90, "", 999, "Antialiasing", false, false), 
    AF_LEVEL("AF_LEVEL", 91, "", 999, "Anisotropic Filtering", false, false), 
    ANIMATED_TEXTURES("ANIMATED_TEXTURES", 92, "", 999, "Textures Animated", false, false), 
    NATURAL_TEXTURES("NATURAL_TEXTURES", 93, "", 999, "Natural Textures", false, false), 
    CHUNK_LOADING("CHUNK_LOADING", 94, "", 999, "Chunk Loading", false, false), 
    HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 95, "", 999, "Held Item Tooltips", false, false), 
    DROPPED_ITEMS("DROPPED_ITEMS", 96, "", 999, "Dropped Items", false, false), 
    LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 97, "", 999, "Lazy Chunk Loading", false, false), 
    CUSTOM_SKY("CUSTOM_SKY", 98, "", 999, "Custom Sky", false, false), 
    FAST_MATH("FAST_MATH", 99, "", 999, "Fast Math", false, false), 
    FAST_RENDER("FAST_RENDER", 100, "", 999, "Fast Render", false, false), 
    TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 101, "", 999, "Translucent Blocks", false, false);
    
    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final String enumString;
    private final float valueStep;
    private float valueMin;
    private float valueMax; private static final Options[] $VALUES = { INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO };
    
    private static final String __OBFID = "CL_00000653";
    private static final Options[] $VALUES$ = { INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO, FOG_FANCY, FOG_START, MIPMAP_TYPE, LOAD_FAR, PRELOADED_CHUNKS, CLOUDS, CLOUD_HEIGHT, TREES, RAIN, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, AA_LEVEL, AF_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, CHUNK_LOADING, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, FAST_RENDER, TRANSLUCENT_BLOCKS };
    
    public static Options getEnumOptions(int p_74379_0_)
    {
      Options[] var1 = values();
      int var2 = var1.length;
      
      for (int var3 = 0; var3 < var2; var3++)
      {
        Options var4 = var1[var3];
        
        if (var4.returnEnumOrdinal() == p_74379_0_)
        {
          return var4;
        }
      }
      
      return null;
    }
    
    private Options(String p_i46381_1_, int p_i46381_2_, String p_i1015_1_, int p_i1015_2_, String p_i1015_3_, boolean p_i1015_4_, boolean p_i1015_5_)
    {
      this(p_i46381_1_, p_i46381_2_, p_i1015_1_, p_i1015_2_, p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0F, 1.0F, 0.0F);
    }
    
    private Options(String p_i46382_1_, int p_i46382_2_, String p_i45004_1_, int p_i45004_2_, String p_i45004_3_, boolean p_i45004_4_, boolean p_i45004_5_, float p_i45004_6_, float p_i45004_7_, float p_i45004_8_)
    {
      enumString = p_i45004_3_;
      enumFloat = p_i45004_4_;
      enumBoolean = p_i45004_5_;
      valueMin = p_i45004_6_;
      valueMax = p_i45004_7_;
      valueStep = p_i45004_8_;
    }
    
    public boolean getEnumFloat()
    {
      return enumFloat;
    }
    
    public boolean getEnumBoolean()
    {
      return enumBoolean;
    }
    
    public int returnEnumOrdinal()
    {
      return ordinal();
    }
    
    public String getEnumString()
    {
      return enumString;
    }
    
    public float getValueMax()
    {
      return valueMax;
    }
    
    public void setValueMax(float p_148263_1_)
    {
      valueMax = p_148263_1_;
    }
    
    public float normalizeValue(float p_148266_1_)
    {
      return MathHelper.clamp_float((snapToStepClamp(p_148266_1_) - valueMin) / (valueMax - valueMin), 0.0F, 1.0F);
    }
    
    public float denormalizeValue(float p_148262_1_)
    {
      return snapToStepClamp(valueMin + (valueMax - valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
    }
    
    public float snapToStepClamp(float p_148268_1_)
    {
      p_148268_1_ = snapToStep(p_148268_1_);
      return MathHelper.clamp_float(p_148268_1_, valueMin, valueMax);
    }
    
    protected float snapToStep(float p_148264_1_)
    {
      if (valueStep > 0.0F)
      {
        p_148264_1_ = valueStep * Math.round(p_148264_1_ / valueStep);
      }
      
      return p_148264_1_;
    }
  }
  
  static final class SwitchOptions
  {
    static final int[] optionIds = new int[GameSettings.Options.values().length];
    private static final String __OBFID = "CL_00000652";
    
    static
    {
      try
      {
        optionIds[GameSettings.Options.INVERT_MOUSE.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        optionIds[GameSettings.Options.VIEW_BOBBING.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        optionIds[GameSettings.Options.ANAGLYPH.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        optionIds[GameSettings.Options.FBO_ENABLE.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
      



      try
      {
        optionIds[GameSettings.Options.RENDER_CLOUDS.ordinal()] = 5;
      }
      catch (NoSuchFieldError localNoSuchFieldError5) {}
      



      try
      {
        optionIds[GameSettings.Options.CHAT_COLOR.ordinal()] = 6;
      }
      catch (NoSuchFieldError localNoSuchFieldError6) {}
      



      try
      {
        optionIds[GameSettings.Options.CHAT_LINKS.ordinal()] = 7;
      }
      catch (NoSuchFieldError localNoSuchFieldError7) {}
      



      try
      {
        optionIds[GameSettings.Options.CHAT_LINKS_PROMPT.ordinal()] = 8;
      }
      catch (NoSuchFieldError localNoSuchFieldError8) {}
      



      try
      {
        optionIds[GameSettings.Options.SNOOPER_ENABLED.ordinal()] = 9;
      }
      catch (NoSuchFieldError localNoSuchFieldError9) {}
      



      try
      {
        optionIds[GameSettings.Options.USE_FULLSCREEN.ordinal()] = 10;
      }
      catch (NoSuchFieldError localNoSuchFieldError10) {}
      



      try
      {
        optionIds[GameSettings.Options.ENABLE_VSYNC.ordinal()] = 11;
      }
      catch (NoSuchFieldError localNoSuchFieldError11) {}
      



      try
      {
        optionIds[GameSettings.Options.USE_VBO.ordinal()] = 12;
      }
      catch (NoSuchFieldError localNoSuchFieldError12) {}
      



      try
      {
        optionIds[GameSettings.Options.TOUCHSCREEN.ordinal()] = 13;
      }
      catch (NoSuchFieldError localNoSuchFieldError13) {}
      



      try
      {
        optionIds[GameSettings.Options.STREAM_SEND_METADATA.ordinal()] = 14;
      }
      catch (NoSuchFieldError localNoSuchFieldError14) {}
      



      try
      {
        optionIds[GameSettings.Options.FORCE_UNICODE_FONT.ordinal()] = 15;
      }
      catch (NoSuchFieldError localNoSuchFieldError15) {}
      



      try
      {
        optionIds[GameSettings.Options.BLOCK_ALTERNATIVES.ordinal()] = 16;
      }
      catch (NoSuchFieldError localNoSuchFieldError16) {}
      



      try
      {
        optionIds[GameSettings.Options.REDUCED_DEBUG_INFO.ordinal()] = 17;
      }
      catch (NoSuchFieldError localNoSuchFieldError17) {}
    }
    
    SwitchOptions() {}
  }
}
