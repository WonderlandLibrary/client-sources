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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings$1;
import net.minecraft.client.settings.GameSettings$GameSettings$2;
import net.minecraft.client.settings.GameSettings$Options;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.optifine.ClearWater;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomMobs;
import net.optifine.Reflector;
import net.shadersmod.client.Shaders;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameSettings {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int b5 = 0;
   public static final int k = 1;
   public static final int b_ = 2;
   public static final int OFF = 3;
   public static final int SMART = 4;
   public static final int bX = 0;
   public static final int aE = 1;
   public static final int a7 = 2;
   public static final String bZ = "Default";
   private static final Gson gson = new Gson();
   private static final ParameterizedType typeListString = new GameSettings$1();
   private static final String[] cd = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
   private static final String[] bp = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] bl = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   private static final String[] bk = new String[]{"options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high"};
   private static final String[] bD = new String[]{"options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never"};
   private static final String[] a6 = new String[]{"options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods"};
   private static final String[] aL = new String[]{"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"};
   private static final String[] a_ = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   private static final String b9 = "CL_00000650";
   private static final int[] OF_TREES_VALUES = new int[]{0, 1, 4, 2};
   private static final int[] OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   private final Set setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
   private final Map mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
   public float mouseSensitivity = 0.5F;
   public boolean invertMouse;
   public int renderDistanceChunks = -1;
   public boolean viewBobbing = true;
   public boolean anaglyph;
   public boolean fboEnable = true;
   public int limitFramerate = 120;
   public int clouds = 2;
   public boolean fancyGraphics = true;
   public int ambientOcclusion = 2;
   public List resourcePacks = Lists.newArrayList();
   public List field_183018_l = Lists.newArrayList();
   public EntityPlayer$EnumChatVisibility chatVisibility = EntityPlayer$EnumChatVisibility.FULL;
   public boolean chatColours = true;
   public boolean chatLinks = true;
   public boolean chatLinksPrompt = true;
   public float chatOpacity = 1.0F;
   public boolean snooperEnabled = true;
   public boolean fullScreen;
   public boolean enableVsync = true;
   public boolean useVbo = false;
   public boolean allowBlockAlternatives = true;
   public boolean reducedDebugInfo = false;
   public boolean hideServerAddress;
   public boolean advancedItemTooltips;
   public boolean pauseOnLostFocus = true;
   public boolean touchscreen;
   public int overrideWidth;
   public int overrideHeight;
   public boolean heldItemTooltips = true;
   public float chatScale = 1.0F;
   public float chatWidth = 1.0F;
   public float chatHeightUnfocused = 0.44366196F;
   public float chatHeightFocused = 1.0F;
   public boolean showInventoryAchievementHint = true;
   public int mipmapLevels = 4;
   public float streamBytesPerPixel = 0.5F;
   public float streamMicVolume = 1.0F;
   public float streamGameVolume = 1.0F;
   public float streamKbps = 0.5412844F;
   public float streamFps = 0.31690142F;
   public int streamCompression = 1;
   public boolean streamSendMetadata = true;
   public String streamPreferredServer = "";
   public int streamChatEnabled = 0;
   public int streamChatUserFilter = 0;
   public int streamMicToggleBehavior = 0;
   public boolean field_181150_U = true;
   public boolean field_181151_V = true;
   public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
   public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
   public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
   public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
   public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
   public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
   public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
   public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
   public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
   public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
   public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
   public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
   public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
   public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
   public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
   public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
   public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
   public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
   public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
   public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
   public KeyBinding keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
   public KeyBinding keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
   public KeyBinding keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
   public KeyBinding keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
   public KeyBinding[] keyBindsHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
   public KeyBinding[] keyBindings;
   public EnumDifficulty difficulty;
   public boolean hideGUI;
   public int thirdPersonView;
   public boolean showDebugInfo;
   public boolean showDebugProfilerChart;
   public boolean field_181657_aC;
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
   public int ofFogType = 1;
   public float ofFogStart = 0.8F;
   public int ofMipmapType = 0;
   public boolean ofOcclusionFancy = false;
   public boolean ofSmoothFps = false;
   public boolean ofSmoothWorld = Config.isSingleProcessor();
   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
   public float ofAoLevel = 1.0F;
   public int ofAaLevel = 0;
   public int ofAfLevel = 1;
   public int ofClouds = 0;
   public float ofCloudsHeight = 0.0F;
   public int ofTrees = 0;
   public int ofRain = 0;
   public int ofDroppedItems = 0;
   public int ofBetterGrass = 3;
   public int ofAutoSaveTicks = 4000;
   public boolean ofLagometer = false;
   public boolean ofProfiler = false;
   public boolean ofShowFps = false;
   public boolean ofWeather = true;
   public boolean ofSky = true;
   public boolean ofStars = true;
   public boolean ofSunMoon = true;
   public int ofVignette = 0;
   public int ofChunkUpdates = 1;
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
   public boolean ofCustomItems = true;
   public boolean ofNaturalTextures = false;
   public boolean ofFastMath = false;
   public int ofTranslucentBlocks = 0;
   public boolean ofDynamicFov = true;
   public int ofDynamicLights = 3;
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
   public boolean ofFireworkParticles = true;
   public boolean ofDrippingWaterLava = true;
   public boolean ofAnimatedTerrain = true;
   public boolean ofAnimatedTextures = true;
   public KeyBinding ofKeyBindZoom;
   protected Minecraft mc;
   private File optionsFile;
   private File optionsFileOF;

   public GameSettings(Minecraft var1, File var2) {
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.keyBindSpectatorOutlines}, this.keyBindsHotbar);
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
      this.mc = var1;
      this.optionsFile = new File(var2, "options.txt");
      this.optionsFileOF = new File(var2, "optionsof.txt");
      this.limitFramerate = (int)GameSettings$Options.FRAMERATE_LIMIT.getValueMax();
      this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
      this.keyBindings = (KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
      GameSettings$Options.RENDER_DISTANCE.setValueMax(32.0F);
      this.renderDistanceChunks = 8;
      this.loadOptions();
      Config.initGameSettings(this);
   }

   public GameSettings() {
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.keyBindSpectatorOutlines}, this.keyBindsHotbar);
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
   }

   public static String getKeyDisplayString(int var0) {
      return I18n.format("key.mouseButton", new Object[]{Integer.valueOf(var0 + 101)});
   }

   public static boolean isKeyDown(KeyBinding var0) {
      boolean var10000;
      label0: {
         int var1 = var0.getKeyCode();
         if(var1 >= -100 && var1 <= 255 && var0.getKeyCode() != 0) {
            if(var0.getKeyCode() < 0) {
               if(Mouse.isButtonDown(var0.getKeyCode() + 100)) {
                  break label0;
               }
            } else if(Keyboard.isKeyDown(var0.getKeyCode())) {
               break label0;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   private static String getTranslation(String[] var0, int var1) {
      if(var1 >= var0.length) {
         var1 = 0;
      }

      return I18n.format(var0[var1], new Object[0]);
   }

   private static int nextValue(int var0, int[] var1) {
      int var2 = indexOf(var0, var1);
      return var1[0];
   }

   private static int limit(int var0, int[] var1) {
      int var2 = indexOf(var0, var1);
      return var1[0];
   }

   private static int indexOf(int var0, int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if(var1[var2] == var0) {
            return var2;
         }
      }

      return -1;
   }

   public void setOptionKeyBinding(KeyBinding var1, int var2) {
      var1.setKeyCode(var2);
      this.saveOptions();
   }

   public void setOptionFloatValue(GameSettings$Options var1, float var2) {
      this.setOptionFloatValueOF(var1, var2);
      if(var1 == GameSettings$Options.SENSITIVITY) {
         this.mouseSensitivity = var2;
      }

      if(var1 == GameSettings$Options.FOV) {
         this.fovSetting = var2;
      }

      if(var1 == GameSettings$Options.GAMMA) {
         this.gammaSetting = var2;
      }

      if(var1 == GameSettings$Options.FRAMERATE_LIMIT) {
         this.limitFramerate = (int)var2;
         this.enableVsync = false;
         if(this.limitFramerate <= 0) {
            this.limitFramerate = (int)GameSettings$Options.FRAMERATE_LIMIT.getValueMax();
            this.enableVsync = true;
         }

         this.updateVSync();
      }

      if(var1 == GameSettings$Options.CHAT_OPACITY) {
         this.chatOpacity = var2;
         this.mc.ingameGUI.n().f();
      }

      if(var1 == GameSettings$Options.CHAT_HEIGHT_FOCUSED) {
         this.chatHeightFocused = var2;
         this.mc.ingameGUI.n().f();
      }

      if(var1 == GameSettings$Options.CHAT_HEIGHT_UNFOCUSED) {
         this.chatHeightUnfocused = var2;
         this.mc.ingameGUI.n().f();
      }

      if(var1 == GameSettings$Options.CHAT_WIDTH) {
         this.chatWidth = var2;
         this.mc.ingameGUI.n().f();
      }

      if(var1 == GameSettings$Options.CHAT_SCALE) {
         this.chatScale = var2;
         this.mc.ingameGUI.n().f();
      }

      if(var1 == GameSettings$Options.MIPMAP_LEVELS) {
         int var3 = this.mipmapLevels;
         this.mipmapLevels = (int)var2;
         if((float)var3 != var2) {
            this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, this.mipmapLevels > 0);
            this.mc.scheduleResourcesRefresh();
         }
      }

      if(var1 == GameSettings$Options.BLOCK_ALTERNATIVES) {
         this.allowBlockAlternatives = !this.allowBlockAlternatives;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.RENDER_DISTANCE) {
         this.renderDistanceChunks = (int)var2;
         this.mc.renderGlobal.setDisplayListEntitiesDirty();
      }

      if(var1 == GameSettings$Options.STREAM_BYTES_PER_PIXEL) {
         this.streamBytesPerPixel = var2;
      }

      if(var1 == GameSettings$Options.STREAM_VOLUME_MIC) {
         this.streamMicVolume = var2;
         this.mc.getTwitchStream().updateStreamVolume();
      }

      if(var1 == GameSettings$Options.STREAM_VOLUME_SYSTEM) {
         this.streamGameVolume = var2;
         this.mc.getTwitchStream().updateStreamVolume();
      }

      if(var1 == GameSettings$Options.STREAM_KBPS) {
         this.streamKbps = var2;
      }

      if(var1 == GameSettings$Options.STREAM_FPS) {
         this.streamFps = var2;
      }

   }

   public void setOptionValue(GameSettings$Options var1, int var2) {
      this.setOptionValueOF(var1, var2);
      if(var1 == GameSettings$Options.INVERT_MOUSE) {
         this.invertMouse = !this.invertMouse;
      }

      if(var1 == GameSettings$Options.GUI_SCALE) {
         this.guiScale = this.guiScale + var2 & 3;
      }

      if(var1 == GameSettings$Options.PARTICLES) {
         this.particleSetting = (this.particleSetting + var2) % 3;
      }

      if(var1 == GameSettings$Options.VIEW_BOBBING) {
         this.viewBobbing = !this.viewBobbing;
      }

      if(var1 == GameSettings$Options.RENDER_CLOUDS) {
         this.clouds = (this.clouds + var2) % 3;
      }

      if(var1 == GameSettings$Options.FORCE_UNICODE_FONT) {
         this.forceUnicodeFont = !this.forceUnicodeFont;
         this.mc.fontRendererObj.setUnicodeFlag(this.mc.j().b() || this.forceUnicodeFont);
      }

      if(var1 == GameSettings$Options.FBO_ENABLE) {
         this.fboEnable = !this.fboEnable;
      }

      if(var1 == GameSettings$Options.ANAGLYPH) {
         if(!this.anaglyph && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.an.shaders1"), Lang.get("of.message.an.shaders2"));
            return;
         }

         this.anaglyph = !this.anaglyph;
         this.mc.refreshResources();
      }

      if(var1 == GameSettings$Options.GRAPHICS) {
         this.fancyGraphics = !this.fancyGraphics;
         this.updateRenderClouds();
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.AMBIENT_OCCLUSION) {
         this.ambientOcclusion = (this.ambientOcclusion + var2) % 3;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.CHAT_VISIBILITY) {
         this.chatVisibility = EntityPlayer$EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + var2) % 3);
      }

      if(var1 == GameSettings$Options.STREAM_COMPRESSION) {
         this.streamCompression = (this.streamCompression + var2) % 3;
      }

      if(var1 == GameSettings$Options.STREAM_SEND_METADATA) {
         this.streamSendMetadata = !this.streamSendMetadata;
      }

      if(var1 == GameSettings$Options.STREAM_CHAT_ENABLED) {
         this.streamChatEnabled = (this.streamChatEnabled + var2) % 3;
      }

      if(var1 == GameSettings$Options.STREAM_CHAT_USER_FILTER) {
         this.streamChatUserFilter = (this.streamChatUserFilter + var2) % 3;
      }

      if(var1 == GameSettings$Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
         this.streamMicToggleBehavior = (this.streamMicToggleBehavior + var2) % 2;
      }

      if(var1 == GameSettings$Options.CHAT_COLOR) {
         this.chatColours = !this.chatColours;
      }

      if(var1 == GameSettings$Options.CHAT_LINKS) {
         this.chatLinks = !this.chatLinks;
      }

      if(var1 == GameSettings$Options.CHAT_LINKS_PROMPT) {
         this.chatLinksPrompt = !this.chatLinksPrompt;
      }

      if(var1 == GameSettings$Options.SNOOPER_ENABLED) {
         this.snooperEnabled = !this.snooperEnabled;
      }

      if(var1 == GameSettings$Options.TOUCHSCREEN) {
         this.touchscreen = !this.touchscreen;
      }

      if(var1 == GameSettings$Options.USE_FULLSCREEN) {
         this.fullScreen = !this.fullScreen;
         if(this.mc.isFullScreen() != this.fullScreen) {
            this.mc.toggleFullscreen();
         }
      }

      if(var1 == GameSettings$Options.ENABLE_VSYNC) {
         this.enableVsync = !this.enableVsync;
         Display.setVSyncEnabled(this.enableVsync);
      }

      if(var1 == GameSettings$Options.USE_VBO) {
         this.useVbo = !this.useVbo;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.BLOCK_ALTERNATIVES) {
         this.allowBlockAlternatives = !this.allowBlockAlternatives;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.REDUCED_DEBUG_INFO) {
         this.reducedDebugInfo = !this.reducedDebugInfo;
      }

      if(var1 == GameSettings$Options.ENTITY_SHADOWS) {
         this.field_181151_V = !this.field_181151_V;
      }

      this.saveOptions();
   }

   public float getOptionFloatValue(GameSettings$Options var1) {
      return var1 == GameSettings$Options.CLOUD_HEIGHT?this.ofCloudsHeight:(var1 == GameSettings$Options.AO_LEVEL?this.ofAoLevel:(var1 == GameSettings$Options.AA_LEVEL?(float)this.ofAaLevel:(var1 == GameSettings$Options.AF_LEVEL?(float)this.ofAfLevel:(var1 == GameSettings$Options.MIPMAP_TYPE?(float)this.ofMipmapType:(var1 == GameSettings$Options.FRAMERATE_LIMIT?((float)this.limitFramerate == GameSettings$Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync?0.0F:(float)this.limitFramerate):(var1 == GameSettings$Options.FOV?this.fovSetting:(var1 == GameSettings$Options.GAMMA?this.gammaSetting:(var1 == GameSettings$Options.SATURATION?this.saturation:(var1 == GameSettings$Options.SENSITIVITY?this.mouseSensitivity:(var1 == GameSettings$Options.CHAT_OPACITY?this.chatOpacity:(var1 == GameSettings$Options.CHAT_HEIGHT_FOCUSED?this.chatHeightFocused:(var1 == GameSettings$Options.CHAT_HEIGHT_UNFOCUSED?this.chatHeightUnfocused:(var1 == GameSettings$Options.CHAT_SCALE?this.chatScale:(var1 == GameSettings$Options.CHAT_WIDTH?this.chatWidth:(var1 == GameSettings$Options.FRAMERATE_LIMIT?(float)this.limitFramerate:(var1 == GameSettings$Options.MIPMAP_LEVELS?(float)this.mipmapLevels:(var1 == GameSettings$Options.RENDER_DISTANCE?(float)this.renderDistanceChunks:(var1 == GameSettings$Options.STREAM_BYTES_PER_PIXEL?this.streamBytesPerPixel:(var1 == GameSettings$Options.STREAM_VOLUME_MIC?this.streamMicVolume:(var1 == GameSettings$Options.STREAM_VOLUME_SYSTEM?this.streamGameVolume:(var1 == GameSettings$Options.STREAM_KBPS?this.streamKbps:(var1 == GameSettings$Options.STREAM_FPS?this.streamFps:0.0F))))))))))))))))))))));
   }

   public boolean getOptionOrdinalValue(GameSettings$Options var1) {
      switch(GameSettings$GameSettings$2.field_151477_a[var1.ordinal()]) {
      case 1:
         return this.invertMouse;
      case 2:
         return this.viewBobbing;
      case 3:
         return this.anaglyph;
      case 4:
         return this.fboEnable;
      case 5:
         return this.chatColours;
      case 6:
         return this.chatLinks;
      case 7:
         return this.chatLinksPrompt;
      case 8:
         return this.snooperEnabled;
      case 9:
         return this.fullScreen;
      case 10:
         return this.enableVsync;
      case 11:
         return this.useVbo;
      case 12:
         return this.touchscreen;
      case 13:
         return this.streamSendMetadata;
      case 14:
         return this.forceUnicodeFont;
      case 15:
         return this.allowBlockAlternatives;
      case 16:
         return this.reducedDebugInfo;
      case 17:
         return this.field_181151_V;
      default:
         return false;
      }
   }

   public String b(GameSettings$Options var1) {
      String var2 = this.getKeyBindingOF(var1);
      return var2;
   }

   public void loadOptions() {
      try {
         if(!this.optionsFile.exists()) {
            return;
         }

         BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
         String var2 = "";
         this.mapSoundLevels.clear();

         while((var2 = var1.readLine()) != null) {
            try {
               String[] var3 = var2.split(":");
               if(var3[0].equals("mouseSensitivity")) {
                  this.mouseSensitivity = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("fov")) {
                  this.fovSetting = this.parseFloat(var3[1]) * 40.0F + 70.0F;
               }

               if(var3[0].equals("gamma")) {
                  this.gammaSetting = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("saturation")) {
                  this.saturation = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("invertYMouse")) {
                  this.invertMouse = var3[1].equals("true");
               }

               if(var3[0].equals("renderDistance")) {
                  this.renderDistanceChunks = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("guiScale")) {
                  this.guiScale = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("particles")) {
                  this.particleSetting = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("bobView")) {
                  this.viewBobbing = var3[1].equals("true");
               }

               if(var3[0].equals("anaglyph3d")) {
                  this.anaglyph = var3[1].equals("true");
               }

               if(var3[0].equals("maxFps")) {
                  this.limitFramerate = Integer.parseInt(var3[1]);
                  this.enableVsync = false;
                  if(this.limitFramerate <= 0) {
                     this.limitFramerate = (int)GameSettings$Options.FRAMERATE_LIMIT.getValueMax();
                     this.enableVsync = true;
                  }

                  this.updateVSync();
               }

               if(var3[0].equals("fboEnable")) {
                  this.fboEnable = var3[1].equals("true");
               }

               if(var3[0].equals("difficulty")) {
                  this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var3[1]));
               }

               if(var3[0].equals("fancyGraphics")) {
                  this.fancyGraphics = var3[1].equals("true");
                  this.updateRenderClouds();
               }

               if(var3[0].equals("ao")) {
                  if(var3[1].equals("true")) {
                     this.ambientOcclusion = 2;
                  } else if(var3[1].equals("false")) {
                     this.ambientOcclusion = 0;
                  } else {
                     this.ambientOcclusion = Integer.parseInt(var3[1]);
                  }
               }

               if(var3[0].equals("renderClouds")) {
                  String var4 = var3[1];
                  byte var5 = -1;
                  switch(var4.hashCode()) {
                  case 3135580:
                     if(var4.equals("fast")) {
                        var5 = 2;
                     }
                     break;
                  case 3569038:
                     if(var4.equals("true")) {
                        var5 = 0;
                     }
                     break;
                  case 97196323:
                     if(var4.equals("false")) {
                        var5 = 1;
                     }
                  }

                  switch(var5) {
                  case 0:
                     this.clouds = 2;
                     break;
                  case 1:
                     this.clouds = 0;
                     break;
                  case 2:
                     this.clouds = 1;
                  }
               }

               if(var3[0].equals("resourcePacks")) {
                  this.resourcePacks = (List)gson.fromJson(var2.substring(var2.indexOf(58) + 1), typeListString);
                  if(this.resourcePacks == null) {
                     this.resourcePacks = Lists.newArrayList();
                  }
               }

               if(var3[0].equals("incompatibleResourcePacks")) {
                  this.field_183018_l = (List)gson.fromJson(var2.substring(var2.indexOf(58) + 1), typeListString);
                  if(this.field_183018_l == null) {
                     this.field_183018_l = Lists.newArrayList();
                  }
               }

               if(var3[0].equals("lastServer") && var3.length >= 2) {
                  this.lastServer = var2.substring(var2.indexOf(58) + 1);
               }

               if(var3[0].equals("lang") && var3.length >= 2) {
                  this.language = var3[1];
               }

               if(var3[0].equals("chatVisibility")) {
                  this.chatVisibility = EntityPlayer$EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var3[1]));
               }

               if(var3[0].equals("chatColors")) {
                  this.chatColours = var3[1].equals("true");
               }

               if(var3[0].equals("chatLinks")) {
                  this.chatLinks = var3[1].equals("true");
               }

               if(var3[0].equals("chatLinksPrompt")) {
                  this.chatLinksPrompt = var3[1].equals("true");
               }

               if(var3[0].equals("chatOpacity")) {
                  this.chatOpacity = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("snooperEnabled")) {
                  this.snooperEnabled = var3[1].equals("true");
               }

               if(var3[0].equals("fullscreen")) {
                  this.fullScreen = var3[1].equals("true");
               }

               if(var3[0].equals("enableVsync")) {
                  this.enableVsync = var3[1].equals("true");
                  this.updateVSync();
               }

               if(var3[0].equals("useVbo")) {
                  this.useVbo = var3[1].equals("true");
               }

               if(var3[0].equals("hideServerAddress")) {
                  this.hideServerAddress = var3[1].equals("true");
               }

               if(var3[0].equals("advancedItemTooltips")) {
                  this.advancedItemTooltips = var3[1].equals("true");
               }

               if(var3[0].equals("pauseOnLostFocus")) {
                  this.pauseOnLostFocus = var3[1].equals("true");
               }

               if(var3[0].equals("touchscreen")) {
                  this.touchscreen = var3[1].equals("true");
               }

               if(var3[0].equals("overrideHeight")) {
                  this.overrideHeight = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("overrideWidth")) {
                  this.overrideWidth = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("heldItemTooltips")) {
                  this.heldItemTooltips = var3[1].equals("true");
               }

               if(var3[0].equals("chatHeightFocused")) {
                  this.chatHeightFocused = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("chatHeightUnfocused")) {
                  this.chatHeightUnfocused = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("chatScale")) {
                  this.chatScale = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("chatWidth")) {
                  this.chatWidth = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("showInventoryAchievementHint")) {
                  this.showInventoryAchievementHint = var3[1].equals("true");
               }

               if(var3[0].equals("mipmapLevels")) {
                  this.mipmapLevels = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("streamBytesPerPixel")) {
                  this.streamBytesPerPixel = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("streamMicVolume")) {
                  this.streamMicVolume = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("streamSystemVolume")) {
                  this.streamGameVolume = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("streamKbps")) {
                  this.streamKbps = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("streamFps")) {
                  this.streamFps = this.parseFloat(var3[1]);
               }

               if(var3[0].equals("streamCompression")) {
                  this.streamCompression = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("streamSendMetadata")) {
                  this.streamSendMetadata = var3[1].equals("true");
               }

               if(var3[0].equals("streamPreferredServer") && var3.length >= 2) {
                  this.streamPreferredServer = var2.substring(var2.indexOf(58) + 1);
               }

               if(var3[0].equals("streamChatEnabled")) {
                  this.streamChatEnabled = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("streamChatUserFilter")) {
                  this.streamChatUserFilter = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("streamMicToggleBehavior")) {
                  this.streamMicToggleBehavior = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("forceUnicodeFont")) {
                  this.forceUnicodeFont = var3[1].equals("true");
               }

               if(var3[0].equals("allowBlockAlternatives")) {
                  this.allowBlockAlternatives = var3[1].equals("true");
               }

               if(var3[0].equals("reducedDebugInfo")) {
                  this.reducedDebugInfo = var3[1].equals("true");
               }

               if(var3[0].equals("useNativeTransport")) {
                  this.field_181150_U = var3[1].equals("true");
               }

               if(var3[0].equals("entityShadows")) {
                  this.field_181151_V = var3[1].equals("true");
               }

               for(KeyBinding var7 : this.keyBindings) {
                  if(var3[0].equals("key_" + var7.getKeyDescription())) {
                     var7.setKeyCode(Integer.parseInt(var3[1]));
                  }
               }

               for(SoundCategory var19 : SoundCategory.values()) {
                  if(var3[0].equals("soundCategory_" + var19.getCategoryName())) {
                     this.mapSoundLevels.put(var19, Float.valueOf(this.parseFloat(var3[1])));
                  }
               }

               for(EnumPlayerModelParts var20 : EnumPlayerModelParts.values()) {
                  if(var3[0].equals("modelPart_" + var20.getPartName())) {
                     this.setModelPartEnabled(var20, var3[1].equals("true"));
                  }
               }
            } catch (Exception var8) {
               LOGGER.warn("Skipping bad option: " + var2);
               var8.printStackTrace();
            }
         }

         KeyBinding.resetKeyBindingArrayAndHash();
         var1.close();
      } catch (Exception var9) {
         LOGGER.error("Failed to load options", var9);
      }

      this.loadOfOptions();
   }

   private float parseFloat(String var1) {
      return var1.equals("true")?1.0F:(var1.equals("false")?0.0F:Float.parseFloat(var1));
   }

   public void saveOptions() {
      if(Reflector.FMLClientHandler.exists()) {
         Object var1 = Reflector.f(Reflector.bQ, new Object[0]);
         if(Reflector.d(var1, Reflector.J, new Object[0])) {
            return;
         }
      }

      try {
         PrintWriter var7 = new PrintWriter(new FileWriter(this.optionsFile));
         var7.println("invertYMouse:" + this.invertMouse);
         var7.println("mouseSensitivity:" + this.mouseSensitivity);
         var7.println("fov:" + (this.fovSetting - 70.0F) / 40.0F);
         var7.println("gamma:" + this.gammaSetting);
         var7.println("saturation:" + this.saturation);
         var7.println("renderDistance:" + this.renderDistanceChunks);
         var7.println("guiScale:" + this.guiScale);
         var7.println("particles:" + this.particleSetting);
         var7.println("bobView:" + this.viewBobbing);
         var7.println("anaglyph3d:" + this.anaglyph);
         var7.println("maxFps:" + this.limitFramerate);
         var7.println("fboEnable:" + this.fboEnable);
         var7.println("difficulty:" + this.difficulty.getDifficultyId());
         var7.println("fancyGraphics:" + this.fancyGraphics);
         var7.println("ao:" + this.ambientOcclusion);
         switch(this.clouds) {
         case 0:
            var7.println("renderClouds:false");
            break;
         case 1:
            var7.println("renderClouds:fast");
            break;
         case 2:
            var7.println("renderClouds:true");
         }

         var7.println("resourcePacks:" + gson.toJson(this.resourcePacks));
         var7.println("incompatibleResourcePacks:" + gson.toJson(this.field_183018_l));
         var7.println("lastServer:" + this.lastServer);
         var7.println("lang:" + this.language);
         var7.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
         var7.println("chatColors:" + this.chatColours);
         var7.println("chatLinks:" + this.chatLinks);
         var7.println("chatLinksPrompt:" + this.chatLinksPrompt);
         var7.println("chatOpacity:" + this.chatOpacity);
         var7.println("snooperEnabled:" + this.snooperEnabled);
         var7.println("fullscreen:" + this.fullScreen);
         var7.println("enableVsync:" + this.enableVsync);
         var7.println("useVbo:" + this.useVbo);
         var7.println("hideServerAddress:" + this.hideServerAddress);
         var7.println("advancedItemTooltips:" + this.advancedItemTooltips);
         var7.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
         var7.println("touchscreen:" + this.touchscreen);
         var7.println("overrideWidth:" + this.overrideWidth);
         var7.println("overrideHeight:" + this.overrideHeight);
         var7.println("heldItemTooltips:" + this.heldItemTooltips);
         var7.println("chatHeightFocused:" + this.chatHeightFocused);
         var7.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
         var7.println("chatScale:" + this.chatScale);
         var7.println("chatWidth:" + this.chatWidth);
         var7.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
         var7.println("mipmapLevels:" + this.mipmapLevels);
         var7.println("streamBytesPerPixel:" + this.streamBytesPerPixel);
         var7.println("streamMicVolume:" + this.streamMicVolume);
         var7.println("streamSystemVolume:" + this.streamGameVolume);
         var7.println("streamKbps:" + this.streamKbps);
         var7.println("streamFps:" + this.streamFps);
         var7.println("streamCompression:" + this.streamCompression);
         var7.println("streamSendMetadata:" + this.streamSendMetadata);
         var7.println("streamPreferredServer:" + this.streamPreferredServer);
         var7.println("streamChatEnabled:" + this.streamChatEnabled);
         var7.println("streamChatUserFilter:" + this.streamChatUserFilter);
         var7.println("streamMicToggleBehavior:" + this.streamMicToggleBehavior);
         var7.println("forceUnicodeFont:" + this.forceUnicodeFont);
         var7.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
         var7.println("reducedDebugInfo:" + this.reducedDebugInfo);
         var7.println("useNativeTransport:" + this.field_181150_U);
         var7.println("entityShadows:" + this.field_181151_V);

         for(KeyBinding var5 : this.keyBindings) {
            var7.println("key_" + var5.getKeyDescription() + ":" + var5.getKeyCode());
         }

         for(SoundCategory var14 : SoundCategory.values()) {
            var7.println("soundCategory_" + var14.getCategoryName() + ":" + this.getSoundLevel(var14));
         }

         for(EnumPlayerModelParts var15 : EnumPlayerModelParts.values()) {
            var7.println("modelPart_" + var15.getPartName() + ":" + this.setModelParts.contains(var15));
         }

         var7.close();
      } catch (Exception var6) {
         LOGGER.error("Failed to save options", var6);
      }

      this.saveOfOptions();
      this.sendSettingsToServer();
   }

   public float getSoundLevel(SoundCategory var1) {
      return this.mapSoundLevels.containsKey(var1)?((Float)this.mapSoundLevels.get(var1)).floatValue():1.0F;
   }

   public void setSoundLevel(SoundCategory var1, float var2) {
      this.mc.getSoundHandler().setSoundLevel(var1, var2);
      this.mapSoundLevels.put(var1, Float.valueOf(var2));
   }

   public void sendSettingsToServer() {
      if(this.mc.player != null) {
         int var1 = 0;

         for(Object var3 : this.setModelParts) {
            var1 |= ((EnumPlayerModelParts)var3).getPartMask();
         }

         this.mc.player.connection.b(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, var1));
      }

   }

   public Set getModelParts() {
      return ImmutableSet.copyOf(this.setModelParts);
   }

   public void setModelPartEnabled(EnumPlayerModelParts var1, boolean var2) {
      this.setModelParts.add(var1);
      this.sendSettingsToServer();
   }

   public void switchModelPartEnabled(EnumPlayerModelParts var1) {
      if(!this.getModelParts().contains(var1)) {
         this.setModelParts.add(var1);
      } else {
         this.setModelParts.remove(var1);
      }

      this.sendSettingsToServer();
   }

   public int func_181147_e() {
      return this.renderDistanceChunks >= 4?this.clouds:0;
   }

   public boolean func_181148_f() {
      return this.field_181150_U;
   }

   private void setOptionFloatValueOF(GameSettings$Options var1, float var2) {
      if(var1 == GameSettings$Options.CLOUD_HEIGHT) {
         this.ofCloudsHeight = var2;
         this.mc.renderGlobal.resetClouds();
      }

      if(var1 == GameSettings$Options.AO_LEVEL) {
         this.ofAoLevel = var2;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.AA_LEVEL) {
         int var3 = (int)var2;
         if(Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
            return;
         }

         int[] var4 = new int[]{0, 2, 4, 6, 8, 12, 16};
         this.ofAaLevel = 0;

         for(int var8 : var4) {
            if(var3 >= var8) {
               this.ofAaLevel = var8;
            }
         }

         this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
      }

      if(var1 == GameSettings$Options.AF_LEVEL) {
         int var9 = (int)var2;
         if(var9 > 1 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
            return;
         }

         for(this.ofAfLevel = 1; this.ofAfLevel * 2 <= var9; this.ofAfLevel *= 2) {
            ;
         }

         this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
         this.mc.refreshResources();
      }

      if(var1 == GameSettings$Options.MIPMAP_TYPE) {
         int var10 = (int)var2;
         this.ofMipmapType = Config.limit(var10, 0, 3);
         this.mc.refreshResources();
      }

   }

   private void setOptionValueOF(GameSettings$Options var1, int var2) {
      if(var1 == GameSettings$Options.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            this.ofFogType = 2;
            if(!Config.isFancyFogAvailable()) {
               this.ofFogType = 3;
            }
            break;
         case 2:
            this.ofFogType = 3;
            break;
         default:
            this.ofFogType = 1;
         }
      }

      if(var1 == GameSettings$Options.FOG_START) {
         this.ofFogStart += 0.2F;
         if(this.ofFogStart > 0.81F) {
            this.ofFogStart = 0.2F;
         }
      }

      if(var1 == GameSettings$Options.SMOOTH_FPS) {
         this.ofSmoothFps = !this.ofSmoothFps;
      }

      if(var1 == GameSettings$Options.SMOOTH_WORLD) {
         this.ofSmoothWorld = !this.ofSmoothWorld;
         Config.updateThreadPriorities();
      }

      if(var1 == GameSettings$Options.CLOUDS) {
         ++this.ofClouds;
         if(this.ofClouds > 3) {
            this.ofClouds = 0;
         }

         this.updateRenderClouds();
         this.mc.renderGlobal.resetClouds();
      }

      if(var1 == GameSettings$Options.TREES) {
         this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.DROPPED_ITEMS) {
         ++this.ofDroppedItems;
         if(this.ofDroppedItems > 2) {
            this.ofDroppedItems = 0;
         }
      }

      if(var1 == GameSettings$Options.RAIN) {
         ++this.ofRain;
         if(this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if(var1 == GameSettings$Options.ANIMATED_WATER) {
         ++this.ofAnimatedWater;
         if(this.ofAnimatedWater == 1) {
            ++this.ofAnimatedWater;
         }

         if(this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }
      }

      if(var1 == GameSettings$Options.ANIMATED_LAVA) {
         ++this.ofAnimatedLava;
         if(this.ofAnimatedLava == 1) {
            ++this.ofAnimatedLava;
         }

         if(this.ofAnimatedLava > 2) {
            this.ofAnimatedLava = 0;
         }
      }

      if(var1 == GameSettings$Options.ANIMATED_FIRE) {
         this.ofAnimatedFire = !this.ofAnimatedFire;
      }

      if(var1 == GameSettings$Options.ANIMATED_PORTAL) {
         this.ofAnimatedPortal = !this.ofAnimatedPortal;
      }

      if(var1 == GameSettings$Options.ANIMATED_REDSTONE) {
         this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
      }

      if(var1 == GameSettings$Options.ANIMATED_EXPLOSION) {
         this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
      }

      if(var1 == GameSettings$Options.ANIMATED_FLAME) {
         this.ofAnimatedFlame = !this.ofAnimatedFlame;
      }

      if(var1 == GameSettings$Options.ANIMATED_SMOKE) {
         this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
      }

      if(var1 == GameSettings$Options.VOID_PARTICLES) {
         this.ofVoidParticles = !this.ofVoidParticles;
      }

      if(var1 == GameSettings$Options.WATER_PARTICLES) {
         this.ofWaterParticles = !this.ofWaterParticles;
      }

      if(var1 == GameSettings$Options.PORTAL_PARTICLES) {
         this.ofPortalParticles = !this.ofPortalParticles;
      }

      if(var1 == GameSettings$Options.POTION_PARTICLES) {
         this.ofPotionParticles = !this.ofPotionParticles;
      }

      if(var1 == GameSettings$Options.FIREWORK_PARTICLES) {
         this.ofFireworkParticles = !this.ofFireworkParticles;
      }

      if(var1 == GameSettings$Options.DRIPPING_WATER_LAVA) {
         this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
      }

      if(var1 == GameSettings$Options.ANIMATED_TERRAIN) {
         this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
      }

      if(var1 == GameSettings$Options.ANIMATED_TEXTURES) {
         this.ofAnimatedTextures = !this.ofAnimatedTextures;
      }

      if(var1 == GameSettings$Options.RAIN_SPLASH) {
         this.ofRainSplash = !this.ofRainSplash;
      }

      if(var1 == GameSettings$Options.LAGOMETER) {
         this.ofLagometer = !this.ofLagometer;
      }

      if(var1 == GameSettings$Options.SHOW_FPS) {
         this.ofShowFps = !this.ofShowFps;
      }

      if(var1 == GameSettings$Options.AUTOSAVE_TICKS) {
         this.ofAutoSaveTicks *= 10;
         if(this.ofAutoSaveTicks > '鱀') {
            this.ofAutoSaveTicks = 40;
         }
      }

      if(var1 == GameSettings$Options.BETTER_GRASS) {
         ++this.ofBetterGrass;
         if(this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.CONNECTED_TEXTURES) {
         ++this.ofConnectedTextures;
         if(this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         if(this.ofConnectedTextures != 2) {
            this.mc.refreshResources();
         }
      }

      if(var1 == GameSettings$Options.WEATHER) {
         this.ofWeather = !this.ofWeather;
      }

      if(var1 == GameSettings$Options.SKY) {
         this.ofSky = !this.ofSky;
      }

      if(var1 == GameSettings$Options.STARS) {
         this.ofStars = !this.ofStars;
      }

      if(var1 == GameSettings$Options.SUN_MOON) {
         this.ofSunMoon = !this.ofSunMoon;
      }

      if(var1 == GameSettings$Options.VIGNETTE) {
         ++this.ofVignette;
         if(this.ofVignette > 2) {
            this.ofVignette = 0;
         }
      }

      if(var1 == GameSettings$Options.CHUNK_UPDATES) {
         ++this.ofChunkUpdates;
         if(this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if(var1 == GameSettings$Options.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if(var1 == GameSettings$Options.TIME) {
         ++this.ofTime;
         if(this.ofTime > 2) {
            this.ofTime = 0;
         }
      }

      if(var1 == GameSettings$Options.CLEAR_WATER) {
         this.ofClearWater = !this.ofClearWater;
         this.updateWaterOpacity();
      }

      if(var1 == GameSettings$Options.PROFILER) {
         this.ofProfiler = !this.ofProfiler;
      }

      if(var1 == GameSettings$Options.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.RANDOM_MOBS) {
         this.ofRandomMobs = !this.ofRandomMobs;
         RandomMobs.resetTextures();
      }

      if(var1 == GameSettings$Options.SMOOTH_BIOMES) {
         this.ofSmoothBiomes = !this.ofSmoothBiomes;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
         this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
      }

      if(var1 == GameSettings$Options.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         CustomColors.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.CUSTOM_ITEMS) {
         this.ofCustomItems = !this.ofCustomItems;
         this.mc.refreshResources();
      }

      if(var1 == GameSettings$Options.CUSTOM_SKY) {
         this.ofCustomSky = !this.ofCustomSky;
         CustomSky.update();
      }

      if(var1 == GameSettings$Options.SHOW_CAPES) {
         this.ofShowCapes = !this.ofShowCapes;
      }

      if(var1 == GameSettings$Options.NATURAL_TEXTURES) {
         this.ofNaturalTextures = !this.ofNaturalTextures;
         NaturalTextures.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.FAST_MATH) {
         this.ofFastMath = !this.ofFastMath;
         MathHelper.fastMath = this.ofFastMath;
      }

      if(var1 == GameSettings$Options.TRANSLUCENT_BLOCKS) {
         if(this.ofTranslucentBlocks == 0) {
            this.ofTranslucentBlocks = 1;
         } else if(this.ofTranslucentBlocks == 1) {
            this.ofTranslucentBlocks = 2;
         } else if(this.ofTranslucentBlocks == 2) {
            this.ofTranslucentBlocks = 0;
         } else {
            this.ofTranslucentBlocks = 0;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.LAZY_CHUNK_LOADING) {
         this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
         Config.updateAvailableProcessors();
         if(!Config.isSingleProcessor()) {
            this.ofLazyChunkLoading = false;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if(var1 == GameSettings$Options.FULLSCREEN_MODE) {
         List var3 = Arrays.asList(Config.getDisplayModeNames());
         if(this.ofFullscreenMode.equals("Default")) {
            this.ofFullscreenMode = (String)var3.get(0);
         } else {
            int var4 = var3.indexOf(this.ofFullscreenMode);
            this.ofFullscreenMode = "Default";
         }
      }

      if(var1 == GameSettings$Options.DYNAMIC_FOV) {
         this.ofDynamicFov = !this.ofDynamicFov;
      }

      if(var1 == GameSettings$Options.DYNAMIC_LIGHTS) {
         this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         DynamicLights.removeLights(this.mc.renderGlobal);
      }

      if(var1 == GameSettings$Options.HELD_ITEM_TOOLTIPS) {
         this.heldItemTooltips = !this.heldItemTooltips;
      }

   }

   private String getKeyBindingOF(GameSettings$Options var1) {
      String var2 = I18n.format(var1.getEnumString(), new Object[0]) + ": ";
      var2 = var1.getEnumString();
      if(var1 == GameSettings$Options.RENDER_DISTANCE) {
         int var11 = (int)this.getOptionFloatValue(var1);
         String var4 = I18n.format("options.renderDistance.tiny", new Object[0]);
         byte var5 = 2;
         if(var11 >= 4) {
            var4 = I18n.format("options.renderDistance.short", new Object[0]);
            var5 = 4;
         }

         if(var11 >= 8) {
            var4 = I18n.format("options.renderDistance.normal", new Object[0]);
            var5 = 8;
         }

         if(var11 >= 16) {
            var4 = I18n.format("options.renderDistance.far", new Object[0]);
            var5 = 16;
         }

         if(var11 >= 32) {
            var4 = Lang.get("of.options.renderDistance.extreme");
            var5 = 32;
         }

         int var6 = this.renderDistanceChunks - var5;
         String var7 = var4 + "+";
         return var2 + var11 + " " + var7 + "";
      } else if(var1 == GameSettings$Options.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         default:
            return var2 + Lang.getOff();
         }
      } else if(var1 == GameSettings$Options.FOG_START) {
         return var2 + this.ofFogStart;
      } else if(var1 == GameSettings$Options.MIPMAP_TYPE) {
         switch(this.ofMipmapType) {
         case 0:
            return var2 + Lang.get("of.options.mipmap.nearest");
         case 1:
            return var2 + Lang.get("of.options.mipmap.linear");
         case 2:
            return var2 + Lang.get("of.options.mipmap.bilinear");
         case 3:
            return var2 + Lang.get("of.options.mipmap.trilinear");
         default:
            return var2 + "of.options.mipmap.nearest";
         }
      } else if(var1 == GameSettings$Options.SMOOTH_FPS) {
         return this.ofSmoothFps?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SMOOTH_WORLD) {
         return this.ofSmoothWorld?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.CLOUDS) {
         switch(this.ofClouds) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         case 3:
            return var2 + Lang.getOff();
         default:
            return var2 + Lang.getDefault();
         }
      } else if(var1 == GameSettings$Options.TREES) {
         switch(this.ofTrees) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         case 3:
         default:
            return var2 + Lang.getDefault();
         case 4:
            return var2 + Lang.get("of.general.smart");
         }
      } else if(var1 == GameSettings$Options.DROPPED_ITEMS) {
         switch(this.ofDroppedItems) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         default:
            return var2 + Lang.getDefault();
         }
      } else if(var1 == GameSettings$Options.RAIN) {
         switch(this.ofRain) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         case 3:
            return var2 + Lang.getOff();
         default:
            return var2 + Lang.getDefault();
         }
      } else if(var1 == GameSettings$Options.ANIMATED_WATER) {
         switch(this.ofAnimatedWater) {
         case 1:
            return var2 + Lang.get("of.options.animation.dynamic");
         case 2:
            return var2 + Lang.getOff();
         default:
            return var2 + Lang.getOn();
         }
      } else if(var1 == GameSettings$Options.ANIMATED_LAVA) {
         switch(this.ofAnimatedLava) {
         case 1:
            return var2 + Lang.get("of.options.animation.dynamic");
         case 2:
            return var2 + Lang.getOff();
         default:
            return var2 + Lang.getOn();
         }
      } else if(var1 == GameSettings$Options.ANIMATED_FIRE) {
         return this.ofAnimatedFire?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_PORTAL) {
         return this.ofAnimatedPortal?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_REDSTONE) {
         return this.ofAnimatedRedstone?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_EXPLOSION) {
         return this.ofAnimatedExplosion?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_FLAME) {
         return this.ofAnimatedFlame?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_SMOKE) {
         return this.ofAnimatedSmoke?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.VOID_PARTICLES) {
         return this.ofVoidParticles?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.WATER_PARTICLES) {
         return this.ofWaterParticles?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.PORTAL_PARTICLES) {
         return this.ofPortalParticles?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.POTION_PARTICLES) {
         return this.ofPotionParticles?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.FIREWORK_PARTICLES) {
         return this.ofFireworkParticles?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.DRIPPING_WATER_LAVA) {
         return this.ofDrippingWaterLava?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_TERRAIN) {
         return this.ofAnimatedTerrain?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.ANIMATED_TEXTURES) {
         return this.ofAnimatedTextures?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.RAIN_SPLASH) {
         return this.ofRainSplash?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.LAGOMETER) {
         return this.ofLagometer?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SHOW_FPS) {
         return this.ofShowFps?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.AUTOSAVE_TICKS) {
         return this.ofAutoSaveTicks <= 40?var2 + Lang.get("of.options.save.default"):(this.ofAutoSaveTicks <= 400?var2 + Lang.get("of.options.save.20s"):(this.ofAutoSaveTicks <= 4000?var2 + Lang.get("of.options.save.3min"):var2 + Lang.get("of.options.save.30min")));
      } else if(var1 == GameSettings$Options.BETTER_GRASS) {
         switch(this.ofBetterGrass) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         default:
            return var2 + Lang.getOff();
         }
      } else if(var1 == GameSettings$Options.CONNECTED_TEXTURES) {
         switch(this.ofConnectedTextures) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         default:
            return var2 + Lang.getOff();
         }
      } else if(var1 == GameSettings$Options.WEATHER) {
         return this.ofWeather?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SKY) {
         return this.ofSky?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.STARS) {
         return this.ofStars?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SUN_MOON) {
         return this.ofSunMoon?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.VIGNETTE) {
         switch(this.ofVignette) {
         case 1:
            return var2 + Lang.getFast();
         case 2:
            return var2 + Lang.getFancy();
         default:
            return var2 + Lang.getDefault();
         }
      } else if(var1 == GameSettings$Options.CHUNK_UPDATES) {
         return var2 + this.ofChunkUpdates;
      } else if(var1 == GameSettings$Options.CHUNK_UPDATES_DYNAMIC) {
         return this.ofChunkUpdatesDynamic?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.TIME) {
         return this.ofTime == 1?var2 + Lang.get("of.options.time.dayOnly"):(this.ofTime == 2?var2 + Lang.get("of.options.time.nightOnly"):var2 + Lang.getDefault());
      } else if(var1 == GameSettings$Options.CLEAR_WATER) {
         return this.ofClearWater?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.AA_LEVEL) {
         String var10 = "";
         if(this.ofAaLevel != Config.getAntialiasingLevel()) {
            var10 = " (" + Lang.get("of.general.restart") + ")";
         }

         return this.ofAaLevel == 0?var2 + Lang.getOff() + var10:var2 + this.ofAaLevel + var10;
      } else if(var1 == GameSettings$Options.AF_LEVEL) {
         return this.ofAfLevel == 1?var2 + Lang.getOff():var2 + this.ofAfLevel;
      } else if(var1 == GameSettings$Options.PROFILER) {
         return this.ofProfiler?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.BETTER_SNOW) {
         return this.ofBetterSnow?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SWAMP_COLORS) {
         return this.ofSwampColors?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.RANDOM_MOBS) {
         return this.ofRandomMobs?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SMOOTH_BIOMES) {
         return this.ofSmoothBiomes?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.CUSTOM_FONTS) {
         return this.ofCustomFonts?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.CUSTOM_COLORS) {
         return this.ofCustomColors?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.CUSTOM_SKY) {
         return this.ofCustomSky?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.SHOW_CAPES) {
         return this.ofShowCapes?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.CUSTOM_ITEMS) {
         return this.ofCustomItems?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.NATURAL_TEXTURES) {
         return this.ofNaturalTextures?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.FAST_MATH) {
         return this.ofFastMath?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.TRANSLUCENT_BLOCKS) {
         return this.ofTranslucentBlocks == 1?var2 + Lang.getFast():(this.ofTranslucentBlocks == 2?var2 + Lang.getFancy():var2 + Lang.getDefault());
      } else if(var1 == GameSettings$Options.LAZY_CHUNK_LOADING) {
         return this.ofLazyChunkLoading?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.DYNAMIC_FOV) {
         return this.ofDynamicFov?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.DYNAMIC_LIGHTS) {
         int var9 = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         return var2 + getTranslation(KEYS_DYNAMIC_LIGHTS, var9);
      } else if(var1 == GameSettings$Options.FULLSCREEN_MODE) {
         return this.ofFullscreenMode.equals("Default")?var2 + Lang.getDefault():var2 + this.ofFullscreenMode;
      } else if(var1 == GameSettings$Options.HELD_ITEM_TOOLTIPS) {
         return this.heldItemTooltips?var2 + Lang.getOn():var2 + Lang.getOff();
      } else if(var1 == GameSettings$Options.FRAMERATE_LIMIT) {
         float var3 = this.getOptionFloatValue(var1);
         return var3 == 0.0F?var2 + Lang.get("of.options.framerateLimit.vsync"):(var3 == GameSettings$Options.access$000(var1)?var2 + I18n.format("options.framerateLimit.max", new Object[0]):var2 + (int)var3 + " fps");
      } else {
         return null;
      }
   }

   public void loadOfOptions() {
      try {
         File var1 = this.optionsFileOF;
         if(!var1.exists()) {
            var1 = this.optionsFile;
         }

         if(!var1.exists()) {
            return;
         }

         BufferedReader var2 = new BufferedReader(new FileReader(var1));
         String var3 = "";

         while((var3 = var2.readLine()) != null) {
            try {
               String[] var4 = var3.split(":");
               if(var4[0].equals("ofRenderDistanceChunks") && var4.length >= 2) {
                  this.renderDistanceChunks = Integer.parseInt(var4[1]);
                  this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
               }

               if(var4[0].equals("ofFogType") && var4.length >= 2) {
                  this.ofFogType = Integer.parseInt(var4[1]);
                  this.ofFogType = Config.limit(this.ofFogType, 1, 3);
               }

               if(var4[0].equals("ofFogStart") && var4.length >= 2) {
                  this.ofFogStart = Float.parseFloat(var4[1]);
                  if(this.ofFogStart < 0.2F) {
                     this.ofFogStart = 0.2F;
                  }

                  if(this.ofFogStart > 0.81F) {
                     this.ofFogStart = 0.8F;
                  }
               }

               if(var4[0].equals("ofMipmapType") && var4.length >= 2) {
                  this.ofMipmapType = Integer.parseInt(var4[1]);
                  this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
               }

               if(var4[0].equals("ofOcclusionFancy") && var4.length >= 2) {
                  this.ofOcclusionFancy = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSmoothFps") && var4.length >= 2) {
                  this.ofSmoothFps = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSmoothWorld") && var4.length >= 2) {
                  this.ofSmoothWorld = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAoLevel") && var4.length >= 2) {
                  this.ofAoLevel = Float.parseFloat(var4[1]);
                  this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
               }

               if(var4[0].equals("ofClouds") && var4.length >= 2) {
                  this.ofClouds = Integer.parseInt(var4[1]);
                  this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                  this.updateRenderClouds();
               }

               if(var4[0].equals("ofCloudsHeight") && var4.length >= 2) {
                  this.ofCloudsHeight = Float.parseFloat(var4[1]);
                  this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
               }

               if(var4[0].equals("ofTrees") && var4.length >= 2) {
                  this.ofTrees = Integer.parseInt(var4[1]);
                  this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
               }

               if(var4[0].equals("ofDroppedItems") && var4.length >= 2) {
                  this.ofDroppedItems = Integer.parseInt(var4[1]);
                  this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
               }

               if(var4[0].equals("ofRain") && var4.length >= 2) {
                  this.ofRain = Integer.parseInt(var4[1]);
                  this.ofRain = Config.limit(this.ofRain, 0, 3);
               }

               if(var4[0].equals("ofAnimatedWater") && var4.length >= 2) {
                  this.ofAnimatedWater = Integer.parseInt(var4[1]);
                  this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
               }

               if(var4[0].equals("ofAnimatedLava") && var4.length >= 2) {
                  this.ofAnimatedLava = Integer.parseInt(var4[1]);
                  this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
               }

               if(var4[0].equals("ofAnimatedFire") && var4.length >= 2) {
                  this.ofAnimatedFire = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedPortal") && var4.length >= 2) {
                  this.ofAnimatedPortal = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedRedstone") && var4.length >= 2) {
                  this.ofAnimatedRedstone = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedExplosion") && var4.length >= 2) {
                  this.ofAnimatedExplosion = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedFlame") && var4.length >= 2) {
                  this.ofAnimatedFlame = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedSmoke") && var4.length >= 2) {
                  this.ofAnimatedSmoke = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofVoidParticles") && var4.length >= 2) {
                  this.ofVoidParticles = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofWaterParticles") && var4.length >= 2) {
                  this.ofWaterParticles = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofPortalParticles") && var4.length >= 2) {
                  this.ofPortalParticles = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofPotionParticles") && var4.length >= 2) {
                  this.ofPotionParticles = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofFireworkParticles") && var4.length >= 2) {
                  this.ofFireworkParticles = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofDrippingWaterLava") && var4.length >= 2) {
                  this.ofDrippingWaterLava = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedTerrain") && var4.length >= 2) {
                  this.ofAnimatedTerrain = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAnimatedTextures") && var4.length >= 2) {
                  this.ofAnimatedTextures = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofRainSplash") && var4.length >= 2) {
                  this.ofRainSplash = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofLagometer") && var4.length >= 2) {
                  this.ofLagometer = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofShowFps") && var4.length >= 2) {
                  this.ofShowFps = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofAutoSaveTicks") && var4.length >= 2) {
                  this.ofAutoSaveTicks = Integer.parseInt(var4[1]);
                  this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, '鱀');
               }

               if(var4[0].equals("ofBetterGrass") && var4.length >= 2) {
                  this.ofBetterGrass = Integer.parseInt(var4[1]);
                  this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
               }

               if(var4[0].equals("ofConnectedTextures") && var4.length >= 2) {
                  this.ofConnectedTextures = Integer.parseInt(var4[1]);
                  this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
               }

               if(var4[0].equals("ofWeather") && var4.length >= 2) {
                  this.ofWeather = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSky") && var4.length >= 2) {
                  this.ofSky = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofStars") && var4.length >= 2) {
                  this.ofStars = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSunMoon") && var4.length >= 2) {
                  this.ofSunMoon = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofVignette") && var4.length >= 2) {
                  this.ofVignette = Integer.parseInt(var4[1]);
                  this.ofVignette = Config.limit(this.ofVignette, 0, 2);
               }

               if(var4[0].equals("ofChunkUpdates") && var4.length >= 2) {
                  this.ofChunkUpdates = Integer.parseInt(var4[1]);
                  this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
               }

               if(var4[0].equals("ofChunkUpdatesDynamic") && var4.length >= 2) {
                  this.ofChunkUpdatesDynamic = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofTime") && var4.length >= 2) {
                  this.ofTime = Integer.parseInt(var4[1]);
                  this.ofTime = Config.limit(this.ofTime, 0, 2);
               }

               if(var4[0].equals("ofClearWater") && var4.length >= 2) {
                  this.ofClearWater = Boolean.parseBoolean(var4[1]);
                  this.updateWaterOpacity();
               }

               if(var4[0].equals("ofAaLevel") && var4.length >= 2) {
                  this.ofAaLevel = Integer.parseInt(var4[1]);
                  this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
               }

               if(var4[0].equals("ofAfLevel") && var4.length >= 2) {
                  this.ofAfLevel = Integer.parseInt(var4[1]);
                  this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
               }

               if(var4[0].equals("ofProfiler") && var4.length >= 2) {
                  this.ofProfiler = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofBetterSnow") && var4.length >= 2) {
                  this.ofBetterSnow = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSwampColors") && var4.length >= 2) {
                  this.ofSwampColors = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofRandomMobs") && var4.length >= 2) {
                  this.ofRandomMobs = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofSmoothBiomes") && var4.length >= 2) {
                  this.ofSmoothBiomes = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofCustomFonts") && var4.length >= 2) {
                  this.ofCustomFonts = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofCustomColors") && var4.length >= 2) {
                  this.ofCustomColors = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofCustomItems") && var4.length >= 2) {
                  this.ofCustomItems = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofCustomSky") && var4.length >= 2) {
                  this.ofCustomSky = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofShowCapes") && var4.length >= 2) {
                  this.ofShowCapes = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofNaturalTextures") && var4.length >= 2) {
                  this.ofNaturalTextures = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofLazyChunkLoading") && var4.length >= 2) {
                  this.ofLazyChunkLoading = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofDynamicFov") && var4.length >= 2) {
                  this.ofDynamicFov = Boolean.parseBoolean(var4[1]);
               }

               if(var4[0].equals("ofDynamicLights") && var4.length >= 2) {
                  this.ofDynamicLights = Integer.parseInt(var4[1]);
                  this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
               }

               if(var4[0].equals("ofFullscreenMode") && var4.length >= 2) {
                  this.ofFullscreenMode = var4[1];
               }

               if(var4[0].equals("ofFastMath") && var4.length >= 2) {
                  this.ofFastMath = Boolean.parseBoolean(var4[1]);
                  MathHelper.fastMath = this.ofFastMath;
               }

               if(var4[0].equals("ofTranslucentBlocks") && var4.length >= 2) {
                  this.ofTranslucentBlocks = Integer.parseInt(var4[1]);
                  this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
               }

               if(var4[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) {
                  this.ofKeyBindZoom.setKeyCode(Integer.parseInt(var4[1]));
               }
            } catch (Exception var5) {
               Config.dbg("Skipping bad option: " + var3);
               this.mc.getLogger().error(var5);
            }
         }

         KeyBinding.resetKeyBindingArrayAndHash();
         var2.close();
      } catch (Exception var6) {
         Config.warn("Failed to load options");
         var6.printStackTrace();
      }

   }

   public void saveOfOptions() {
      try {
         PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFileOF));
         var1.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
         var1.println("ofFogType:" + this.ofFogType);
         var1.println("ofFogStart:" + this.ofFogStart);
         var1.println("ofMipmapType:" + this.ofMipmapType);
         var1.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
         var1.println("ofSmoothFps:" + this.ofSmoothFps);
         var1.println("ofSmoothWorld:" + this.ofSmoothWorld);
         var1.println("ofAoLevel:" + this.ofAoLevel);
         var1.println("ofClouds:" + this.ofClouds);
         var1.println("ofCloudsHeight:" + this.ofCloudsHeight);
         var1.println("ofTrees:" + this.ofTrees);
         var1.println("ofDroppedItems:" + this.ofDroppedItems);
         var1.println("ofRain:" + this.ofRain);
         var1.println("ofAnimatedWater:" + this.ofAnimatedWater);
         var1.println("ofAnimatedLava:" + this.ofAnimatedLava);
         var1.println("ofAnimatedFire:" + this.ofAnimatedFire);
         var1.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
         var1.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
         var1.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
         var1.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
         var1.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
         var1.println("ofVoidParticles:" + this.ofVoidParticles);
         var1.println("ofWaterParticles:" + this.ofWaterParticles);
         var1.println("ofPortalParticles:" + this.ofPortalParticles);
         var1.println("ofPotionParticles:" + this.ofPotionParticles);
         var1.println("ofFireworkParticles:" + this.ofFireworkParticles);
         var1.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
         var1.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
         var1.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
         var1.println("ofRainSplash:" + this.ofRainSplash);
         var1.println("ofLagometer:" + this.ofLagometer);
         var1.println("ofShowFps:" + this.ofShowFps);
         var1.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
         var1.println("ofBetterGrass:" + this.ofBetterGrass);
         var1.println("ofConnectedTextures:" + this.ofConnectedTextures);
         var1.println("ofWeather:" + this.ofWeather);
         var1.println("ofSky:" + this.ofSky);
         var1.println("ofStars:" + this.ofStars);
         var1.println("ofSunMoon:" + this.ofSunMoon);
         var1.println("ofVignette:" + this.ofVignette);
         var1.println("ofChunkUpdates:" + this.ofChunkUpdates);
         var1.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
         var1.println("ofTime:" + this.ofTime);
         var1.println("ofClearWater:" + this.ofClearWater);
         var1.println("ofAaLevel:" + this.ofAaLevel);
         var1.println("ofAfLevel:" + this.ofAfLevel);
         var1.println("ofProfiler:" + this.ofProfiler);
         var1.println("ofBetterSnow:" + this.ofBetterSnow);
         var1.println("ofSwampColors:" + this.ofSwampColors);
         var1.println("ofRandomMobs:" + this.ofRandomMobs);
         var1.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
         var1.println("ofCustomFonts:" + this.ofCustomFonts);
         var1.println("ofCustomColors:" + this.ofCustomColors);
         var1.println("ofCustomItems:" + this.ofCustomItems);
         var1.println("ofCustomSky:" + this.ofCustomSky);
         var1.println("ofShowCapes:" + this.ofShowCapes);
         var1.println("ofNaturalTextures:" + this.ofNaturalTextures);
         var1.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
         var1.println("ofDynamicFov:" + this.ofDynamicFov);
         var1.println("ofDynamicLights:" + this.ofDynamicLights);
         var1.println("ofFullscreenMode:" + this.ofFullscreenMode);
         var1.println("ofFastMath:" + this.ofFastMath);
         var1.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
         var1.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
         var1.close();
      } catch (Throwable var2) {
         Config.warn("Failed to save options");
         this.mc.getLogger().error(var2);
      }

   }

   private void updateRenderClouds() {
      switch(this.ofClouds) {
      case 1:
         this.clouds = 1;
         break;
      case 2:
         this.clouds = 2;
         break;
      case 3:
         this.clouds = 0;
         break;
      default:
         if(this.fancyGraphics) {
            this.clouds = 2;
         } else {
            this.clouds = 1;
         }
      }

   }

   public void resetSettings() {
      this.renderDistanceChunks = 8;
      this.viewBobbing = true;
      this.anaglyph = false;
      this.limitFramerate = (int)GameSettings$Options.FRAMERATE_LIMIT.getValueMax();
      this.enableVsync = false;
      this.updateVSync();
      this.mipmapLevels = 4;
      this.fancyGraphics = true;
      this.ambientOcclusion = 2;
      this.clouds = 2;
      this.fovSetting = 70.0F;
      this.gammaSetting = 0.0F;
      this.guiScale = 0;
      this.particleSetting = 0;
      this.heldItemTooltips = true;
      this.useVbo = false;
      this.allowBlockAlternatives = true;
      this.forceUnicodeFont = false;
      this.ofFogType = 1;
      this.ofFogStart = 0.8F;
      this.ofMipmapType = 0;
      this.ofOcclusionFancy = false;
      this.ofSmoothFps = false;
      Config.updateAvailableProcessors();
      this.ofSmoothWorld = Config.isSingleProcessor();
      this.ofLazyChunkLoading = Config.isSingleProcessor();
      this.ofFastMath = false;
      this.ofTranslucentBlocks = 0;
      this.ofDynamicFov = true;
      this.ofDynamicLights = 3;
      this.ofAoLevel = 1.0F;
      this.ofAaLevel = 0;
      this.ofAfLevel = 1;
      this.ofClouds = 0;
      this.ofCloudsHeight = 0.0F;
      this.ofTrees = 0;
      this.ofRain = 0;
      this.ofBetterGrass = 3;
      this.ofAutoSaveTicks = 4000;
      this.ofLagometer = false;
      this.ofShowFps = false;
      this.ofProfiler = false;
      this.ofWeather = true;
      this.ofSky = true;
      this.ofStars = true;
      this.ofSunMoon = true;
      this.ofVignette = 0;
      this.ofChunkUpdates = 1;
      this.ofChunkUpdatesDynamic = false;
      this.ofTime = 0;
      this.ofClearWater = false;
      this.ofBetterSnow = false;
      this.ofFullscreenMode = "Default";
      this.ofSwampColors = true;
      this.ofRandomMobs = true;
      this.ofSmoothBiomes = true;
      this.ofCustomFonts = true;
      this.ofCustomColors = true;
      this.ofCustomItems = true;
      this.ofCustomSky = true;
      this.ofShowCapes = true;
      this.ofConnectedTextures = 2;
      this.ofNaturalTextures = false;
      this.ofAnimatedWater = 0;
      this.ofAnimatedLava = 0;
      this.ofAnimatedFire = true;
      this.ofAnimatedPortal = true;
      this.ofAnimatedRedstone = true;
      this.ofAnimatedExplosion = true;
      this.ofAnimatedFlame = true;
      this.ofAnimatedSmoke = true;
      this.ofVoidParticles = true;
      this.ofWaterParticles = true;
      this.ofRainSplash = true;
      this.ofPortalParticles = true;
      this.ofPotionParticles = true;
      this.ofFireworkParticles = true;
      this.ofDrippingWaterLava = true;
      this.ofAnimatedTerrain = true;
      this.ofAnimatedTextures = true;
      Shaders.setShaderPack(Shaders.packNameNone);
      Shaders.configAntialiasingLevel = 0;
      Shaders.uninit();
      Shaders.storeConfig();
      this.updateWaterOpacity();
      this.mc.refreshResources();
      this.saveOptions();
   }

   public void updateVSync() {
      Display.setVSyncEnabled(this.enableVsync);
   }

   private void updateWaterOpacity() {
      if(this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
         Config.waterOpacityChanged = true;
      }

      ClearWater.updateWaterOpacity(this, this.mc.world);
   }

   public void setAllAnimations(boolean var1) {
      byte var2 = 0;
      this.ofAnimatedWater = var2;
      this.ofAnimatedLava = var2;
      this.ofAnimatedFire = var1;
      this.ofAnimatedPortal = var1;
      this.ofAnimatedRedstone = var1;
      this.ofAnimatedExplosion = var1;
      this.ofAnimatedFlame = var1;
      this.ofAnimatedSmoke = var1;
      this.ofVoidParticles = var1;
      this.ofWaterParticles = var1;
      this.ofRainSplash = var1;
      this.ofPortalParticles = var1;
      this.ofPotionParticles = var1;
      this.ofFireworkParticles = var1;
      this.particleSetting = 0;
      this.ofDrippingWaterLava = var1;
      this.ofAnimatedTerrain = var1;
      this.ofAnimatedTextures = var1;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
