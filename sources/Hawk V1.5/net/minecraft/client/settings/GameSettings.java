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
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import optifine.ClearWater;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lang;
import optifine.NaturalTextures;
import optifine.RandomMobs;
import optifine.Reflector;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import shadersmod.client.Shaders;

public class GameSettings {
   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
   private static final String[] STREAM_CHAT_MODES = new String[]{"options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never"};
   public String language;
   public float chatScale;
   public int ofClouds = 0;
   public float chatWidth;
   public boolean ofAnimatedTerrain = true;
   public int ofMipmapType = 0;
   public boolean heldItemTooltips;
   public KeyBinding keyBindStreamToggleMic;
   public boolean fancyGraphics = true;
   public boolean debugCamEnable;
   public KeyBinding keyBindSneak;
   public boolean ofSmoothWorld = Config.isSingleProcessor();
   public boolean ofDrippingWaterLava = true;
   public static final String DEFAULT_STR = "Default";
   public boolean ofAnimatedTextures = true;
   public int ofTranslucentBlocks = 0;
   public int ofAutoSaveTicks = 4000;
   public KeyBinding keyBindDrop;
   public boolean ofOcclusionFancy = false;
   public boolean hideGUI;
   public static final int SMART = 4;
   public int ofDroppedItems = 0;
   public boolean ofCustomItems = true;
   private static final String[] STREAM_CHAT_FILTER_MODES = new String[]{"options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods"};
   public float streamGameVolume;
   public float mouseSensitivity = 0.5F;
   public float streamBytesPerPixel;
   private static final String[] AMBIENT_OCCLUSIONS = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   public boolean field_178879_v;
   public boolean ofAnimatedExplosion = true;
   public boolean ofVoidParticles = true;
   public int ofChunkUpdates = 1;
   public boolean smoothCamera;
   public int ofAnimatedLava = 0;
   public boolean ofAnimatedPortal = true;
   public int ofTrees = 0;
   public float streamFps;
   public int ofVignette = 0;
   public boolean fullScreen;
   public KeyBinding ofKeyBindZoom;
   public boolean ofFireworkParticles = true;
   public static final int ANIM_OFF = 2;
   public KeyBinding keyBindSprint;
   public static final int DEFAULT = 0;
   public boolean ofPotionParticles = true;
   public KeyBinding keyBindSmoothCamera;
   private static final int[] OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
   public KeyBinding keyBindForward;
   private static final String[] PARTICLES = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   public String lastServer;
   public boolean ofAnimatedRedstone = true;
   public int particleSetting;
   public KeyBinding field_178883_an;
   public KeyBinding keyBindFullscreen;
   public KeyBinding keyBindJump;
   public int ofRain = 0;
   public boolean ofShowFps = false;
   public List resourcePacks = Lists.newArrayList();
   public static final int ANIM_ON = 0;
   public float streamMicVolume;
   private File optionsFile;
   public boolean ofSky = true;
   public boolean forceUnicodeFont;
   public int ofTime = 0;
   public static final int FAST = 1;
   private static final String[] GUISCALES = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
   public String ofFullscreenMode = "Default";
   private static final String[] STREAM_MIC_MODES = new String[]{"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"};
   public boolean ofFastMath = false;
   public float fovSetting;
   public boolean ofWaterParticles = true;
   public float chatOpacity;
   public boolean ofBetterSnow = false;
   public int ofFogType = 1;
   public int guiScale;
   public boolean anaglyph;
   public boolean pauseOnLostFocus;
   public boolean field_178881_t;
   public EntityPlayer.EnumChatVisibility chatVisibility;
   public int mipmapLevels;
   public boolean invertMouse;
   public KeyBinding keyBindInventory;
   public float chatHeightUnfocused;
   public boolean ofFastRender = true;
   public int ambientOcclusion = 2;
   public float saturation;
   public boolean chatColours;
   public boolean showDebugProfilerChart;
   private static final String __OBFID = "CL_00000650";
   public boolean ofPortalParticles = true;
   public boolean chatLinks;
   public boolean ofSmoothFps = false;
   public int thirdPersonView;
   public KeyBinding keyBindUseItem;
   public KeyBinding keyBindTogglePerspective;
   public KeyBinding keyBindAttack;
   public float ofCloudsHeight = 0.0F;
   public int streamChatUserFilter;
   public boolean streamSendMetadata;
   public float streamKbps;
   public boolean ofRandomMobs = true;
   public boolean snooperEnabled;
   public KeyBinding keyBindPickBlock;
   public KeyBinding keyBindStreamCommercials;
   public int streamMicToggleBehavior;
   public boolean ofAnimatedFire = true;
   public boolean ofProfiler = false;
   public boolean ofWeather = true;
   public boolean touchscreen;
   public boolean ofCustomColors = true;
   public int ofAaLevel = 0;
   public int ofAnimatedWater = 0;
   public static final int ANIM_GENERATED = 1;
   public KeyBinding[] keyBindings;
   public static final int OFF = 3;
   public KeyBinding keyBindStreamStartStop;
   public boolean ofSunMoon = true;
   public boolean ofNaturalTextures = false;
   public float ofFogStart = 0.8F;
   private final Set field_178882_aU;
   public boolean ofSmoothBiomes = true;
   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   private static final int[] OF_TREES_VALUES = new int[]{0, 1, 4, 2};
   public float ofAoLevel = 1.0F;
   public boolean ofAnimatedSmoke = true;
   public String streamPreferredServer;
   public boolean advancedItemTooltips;
   public boolean ofShowCapes = true;
   public boolean enableVsync;
   public boolean fboEnable = true;
   public boolean ofStars = true;
   public int renderDistanceChunks = -1;
   private static final Logger logger = LogManager.getLogger();
   public KeyBinding keyBindCommand;
   public boolean ofDynamicFov = true;
   public KeyBinding keyBindPlayerList;
   public int overrideWidth;
   public KeyBinding keyBindLeft;
   public boolean ofAnimatedFlame = true;
   public KeyBinding keyBindStreamPauseUnpause;
   public boolean ofSwampColors = true;
   public int overrideHeight;
   public boolean clouds = true;
   public boolean ofCustomSky = true;
   public int streamCompression;
   public int ofConnectedTextures = 2;
   public boolean hideServerAddress;
   public EnumDifficulty difficulty;
   public boolean field_178880_u;
   public boolean ofClearWater = false;
   public static final int FANCY = 2;
   public boolean ofChunkUpdatesDynamic = false;
   public KeyBinding keyBindChat;
   private static final ParameterizedType typeListString = new ParameterizedType() {
      private static final String __OBFID = "CL_00000651";

      public Type getOwnerType() {
         return null;
      }

      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      public Type getRawType() {
         return List.class;
      }
   };
   public int ofDynamicLights = 3;
   public KeyBinding[] keyBindsHotbar;
   public boolean viewBobbing = true;
   public int streamChatEnabled;
   private static final Gson gson = new Gson();
   protected Minecraft mc;
   public boolean showInventoryAchievementHint;
   public KeyBinding keyBindScreenshot;
   public boolean chatLinksPrompt;
   public float gammaSetting;
   public int ofAfLevel = 1;
   public boolean ofLagometer = false;
   private Map mapSoundLevels;
   public KeyBinding keyBindRight;
   public boolean ofRainSplash = true;
   public KeyBinding keyBindBack;
   private static final String[] STREAM_COMPRESSIONS = new String[]{"options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high"};
   public int limitFramerate = 120;
   public int ofBetterGrass = 3;
   public boolean ofCustomFonts = true;
   public boolean showDebugInfo;
   private File optionsFileOF;
   public float chatHeightFocused;

   public void saveOfOptions() {
      try {
         PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFileOF));
         var1.println(String.valueOf((new StringBuilder("ofRenderDistanceChunks:")).append(this.renderDistanceChunks)));
         var1.println(String.valueOf((new StringBuilder("ofFogType:")).append(this.ofFogType)));
         var1.println(String.valueOf((new StringBuilder("ofFogStart:")).append(this.ofFogStart)));
         var1.println(String.valueOf((new StringBuilder("ofMipmapType:")).append(this.ofMipmapType)));
         var1.println(String.valueOf((new StringBuilder("ofOcclusionFancy:")).append(this.ofOcclusionFancy)));
         var1.println(String.valueOf((new StringBuilder("ofSmoothFps:")).append(this.ofSmoothFps)));
         var1.println(String.valueOf((new StringBuilder("ofSmoothWorld:")).append(this.ofSmoothWorld)));
         var1.println(String.valueOf((new StringBuilder("ofAoLevel:")).append(this.ofAoLevel)));
         var1.println(String.valueOf((new StringBuilder("ofClouds:")).append(this.ofClouds)));
         var1.println(String.valueOf((new StringBuilder("ofCloudsHeight:")).append(this.ofCloudsHeight)));
         var1.println(String.valueOf((new StringBuilder("ofTrees:")).append(this.ofTrees)));
         var1.println(String.valueOf((new StringBuilder("ofDroppedItems:")).append(this.ofDroppedItems)));
         var1.println(String.valueOf((new StringBuilder("ofRain:")).append(this.ofRain)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedWater:")).append(this.ofAnimatedWater)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedLava:")).append(this.ofAnimatedLava)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedFire:")).append(this.ofAnimatedFire)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedPortal:")).append(this.ofAnimatedPortal)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedRedstone:")).append(this.ofAnimatedRedstone)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedExplosion:")).append(this.ofAnimatedExplosion)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedFlame:")).append(this.ofAnimatedFlame)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedSmoke:")).append(this.ofAnimatedSmoke)));
         var1.println(String.valueOf((new StringBuilder("ofVoidParticles:")).append(this.ofVoidParticles)));
         var1.println(String.valueOf((new StringBuilder("ofWaterParticles:")).append(this.ofWaterParticles)));
         var1.println(String.valueOf((new StringBuilder("ofPortalParticles:")).append(this.ofPortalParticles)));
         var1.println(String.valueOf((new StringBuilder("ofPotionParticles:")).append(this.ofPotionParticles)));
         var1.println(String.valueOf((new StringBuilder("ofFireworkParticles:")).append(this.ofFireworkParticles)));
         var1.println(String.valueOf((new StringBuilder("ofDrippingWaterLava:")).append(this.ofDrippingWaterLava)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedTerrain:")).append(this.ofAnimatedTerrain)));
         var1.println(String.valueOf((new StringBuilder("ofAnimatedTextures:")).append(this.ofAnimatedTextures)));
         var1.println(String.valueOf((new StringBuilder("ofRainSplash:")).append(this.ofRainSplash)));
         var1.println(String.valueOf((new StringBuilder("ofLagometer:")).append(this.ofLagometer)));
         var1.println(String.valueOf((new StringBuilder("ofShowFps:")).append(this.ofShowFps)));
         var1.println(String.valueOf((new StringBuilder("ofAutoSaveTicks:")).append(this.ofAutoSaveTicks)));
         var1.println(String.valueOf((new StringBuilder("ofBetterGrass:")).append(this.ofBetterGrass)));
         var1.println(String.valueOf((new StringBuilder("ofConnectedTextures:")).append(this.ofConnectedTextures)));
         var1.println(String.valueOf((new StringBuilder("ofWeather:")).append(this.ofWeather)));
         var1.println(String.valueOf((new StringBuilder("ofSky:")).append(this.ofSky)));
         var1.println(String.valueOf((new StringBuilder("ofStars:")).append(this.ofStars)));
         var1.println(String.valueOf((new StringBuilder("ofSunMoon:")).append(this.ofSunMoon)));
         var1.println(String.valueOf((new StringBuilder("ofVignette:")).append(this.ofVignette)));
         var1.println(String.valueOf((new StringBuilder("ofChunkUpdates:")).append(this.ofChunkUpdates)));
         var1.println(String.valueOf((new StringBuilder("ofChunkUpdatesDynamic:")).append(this.ofChunkUpdatesDynamic)));
         var1.println(String.valueOf((new StringBuilder("ofTime:")).append(this.ofTime)));
         var1.println(String.valueOf((new StringBuilder("ofClearWater:")).append(this.ofClearWater)));
         var1.println(String.valueOf((new StringBuilder("ofAaLevel:")).append(this.ofAaLevel)));
         var1.println(String.valueOf((new StringBuilder("ofAfLevel:")).append(this.ofAfLevel)));
         var1.println(String.valueOf((new StringBuilder("ofProfiler:")).append(this.ofProfiler)));
         var1.println(String.valueOf((new StringBuilder("ofBetterSnow:")).append(this.ofBetterSnow)));
         var1.println(String.valueOf((new StringBuilder("ofSwampColors:")).append(this.ofSwampColors)));
         var1.println(String.valueOf((new StringBuilder("ofRandomMobs:")).append(this.ofRandomMobs)));
         var1.println(String.valueOf((new StringBuilder("ofSmoothBiomes:")).append(this.ofSmoothBiomes)));
         var1.println(String.valueOf((new StringBuilder("ofCustomFonts:")).append(this.ofCustomFonts)));
         var1.println(String.valueOf((new StringBuilder("ofCustomColors:")).append(this.ofCustomColors)));
         var1.println(String.valueOf((new StringBuilder("ofCustomItems:")).append(this.ofCustomItems)));
         var1.println(String.valueOf((new StringBuilder("ofCustomSky:")).append(this.ofCustomSky)));
         var1.println(String.valueOf((new StringBuilder("ofShowCapes:")).append(this.ofShowCapes)));
         var1.println(String.valueOf((new StringBuilder("ofNaturalTextures:")).append(this.ofNaturalTextures)));
         var1.println(String.valueOf((new StringBuilder("ofLazyChunkLoading:")).append(this.ofLazyChunkLoading)));
         var1.println(String.valueOf((new StringBuilder("ofDynamicFov:")).append(this.ofDynamicFov)));
         var1.println(String.valueOf((new StringBuilder("ofDynamicLights:")).append(this.ofDynamicLights)));
         var1.println(String.valueOf((new StringBuilder("ofFullscreenMode:")).append(this.ofFullscreenMode)));
         var1.println(String.valueOf((new StringBuilder("ofFastMath:")).append(this.ofFastMath)));
         var1.println(String.valueOf((new StringBuilder("ofFastRender:")).append(this.ofFastRender)));
         var1.println(String.valueOf((new StringBuilder("ofTranslucentBlocks:")).append(this.ofTranslucentBlocks)));
         var1.println(String.valueOf((new StringBuilder("key_")).append(this.ofKeyBindZoom.getKeyDescription()).append(":").append(this.ofKeyBindZoom.getKeyCode())));
         var1.close();
      } catch (Exception var2) {
         Config.warn("Failed to save options");
         var2.printStackTrace();
      }

   }

   private float parseFloat(String var1) {
      return var1.equals("true") ? 1.0F : (var1.equals("false") ? 0.0F : Float.parseFloat(var1));
   }

   public boolean shouldRenderClouds() {
      return this.renderDistanceChunks >= 4 && this.clouds;
   }

   public static boolean isKeyDown(KeyBinding var0) {
      int var1 = var0.getKeyCode();
      return var1 >= -100 && var1 <= 255 ? (var0.getKeyCode() == 0 ? false : (var0.getKeyCode() < 0 ? Mouse.isButtonDown(var0.getKeyCode() + 100) : Keyboard.isKeyDown(var0.getKeyCode()))) : false;
   }

   public void setOptionFloatValue(GameSettings.Options var1, float var2) {
      this.setOptionFloatValueOF(var1, var2);
      if (var1 == GameSettings.Options.SENSITIVITY) {
         this.mouseSensitivity = var2;
      }

      if (var1 == GameSettings.Options.FOV) {
         this.fovSetting = var2;
      }

      if (var1 == GameSettings.Options.GAMMA) {
         this.gammaSetting = var2;
      }

      if (var1 == GameSettings.Options.FRAMERATE_LIMIT) {
         this.limitFramerate = (int)var2;
         this.enableVsync = false;
         if (this.limitFramerate <= 0) {
            this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
            this.enableVsync = true;
         }

         this.updateVSync();
      }

      if (var1 == GameSettings.Options.CHAT_OPACITY) {
         this.chatOpacity = var2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (var1 == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         this.chatHeightFocused = var2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (var1 == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         this.chatHeightUnfocused = var2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (var1 == GameSettings.Options.CHAT_WIDTH) {
         this.chatWidth = var2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (var1 == GameSettings.Options.CHAT_SCALE) {
         this.chatScale = var2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (var1 == GameSettings.Options.MIPMAP_LEVELS) {
         int var3 = this.mipmapLevels;
         this.mipmapLevels = (int)var2;
         if ((float)var3 != var2) {
            this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.mc.getTextureMapBlocks().func_174937_a(false, this.mipmapLevels > 0);
            this.mc.func_175603_A();
         }
      }

      if (var1 == GameSettings.Options.BLOCK_ALTERNATIVES) {
         this.field_178880_u = !this.field_178880_u;
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.RENDER_DISTANCE) {
         this.renderDistanceChunks = (int)var2;
         this.mc.renderGlobal.func_174979_m();
      }

      if (var1 == GameSettings.Options.STREAM_BYTES_PER_PIXEL) {
         this.streamBytesPerPixel = var2;
      }

      if (var1 == GameSettings.Options.STREAM_VOLUME_MIC) {
         this.streamMicVolume = var2;
         this.mc.getTwitchStream().func_152915_s();
      }

      if (var1 == GameSettings.Options.STREAM_VOLUME_SYSTEM) {
         this.streamGameVolume = var2;
         this.mc.getTwitchStream().func_152915_s();
      }

      if (var1 == GameSettings.Options.STREAM_KBPS) {
         this.streamKbps = var2;
      }

      if (var1 == GameSettings.Options.STREAM_FPS) {
         this.streamFps = var2;
      }

   }

   private static String getTranslation(String[] var0, int var1) {
      if (var1 < 0 || var1 >= var0.length) {
         var1 = 0;
      }

      return I18n.format(var0[var1]);
   }

   public float getSoundLevel(SoundCategory var1) {
      return this.mapSoundLevels.containsKey(var1) ? (Float)this.mapSoundLevels.get(var1) : 1.0F;
   }

   private static int indexOf(int var0, int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2] == var0) {
            return var2;
         }
      }

      return -1;
   }

   private String getKeyBindingOF(GameSettings.Options var1) {
      String var2 = String.valueOf((new StringBuilder(String.valueOf(I18n.format(var1.getEnumString())))).append(": "));
      if (var2 == null) {
         var2 = var1.getEnumString();
      }

      int var3;
      String var8;
      if (var1 == GameSettings.Options.RENDER_DISTANCE) {
         var3 = (int)this.getOptionFloatValue(var1);
         var8 = I18n.format("options.renderDistance.tiny");
         byte var5 = 2;
         if (var3 >= 4) {
            var8 = I18n.format("options.renderDistance.short");
            var5 = 4;
         }

         if (var3 >= 8) {
            var8 = I18n.format("options.renderDistance.normal");
            var5 = 8;
         }

         if (var3 >= 16) {
            var8 = I18n.format("options.renderDistance.far");
            var5 = 16;
         }

         if (var3 >= 32) {
            var8 = Lang.get("of.options.renderDistance.extreme");
            var5 = 32;
         }

         int var6 = this.renderDistanceChunks - var5;
         String var7 = var8;
         if (var6 > 0) {
            var7 = String.valueOf((new StringBuilder(String.valueOf(var8))).append("+"));
         }

         return String.valueOf((new StringBuilder(String.valueOf(var2))).append(var3).append(" ").append(var7));
      } else if (var1 == GameSettings.Options.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         case 3:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         }
      } else if (var1 == GameSettings.Options.FOG_START) {
         return String.valueOf((new StringBuilder(String.valueOf(var2))).append(this.ofFogStart));
      } else if (var1 == GameSettings.Options.MIPMAP_TYPE) {
         switch(this.ofMipmapType) {
         case 0:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.mipmap.nearest")));
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.mipmap.linear")));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.mipmap.bilinear")));
         case 3:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.mipmap.trilinear")));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append("of.options.mipmap.nearest"));
         }
      } else if (var1 == GameSettings.Options.SMOOTH_FPS) {
         return this.ofSmoothFps ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SMOOTH_WORLD) {
         return this.ofSmoothWorld ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.CLOUDS) {
         switch(this.ofClouds) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         case 3:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault()));
         }
      } else if (var1 == GameSettings.Options.TREES) {
         switch(this.ofTrees) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         case 3:
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault()));
         case 4:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.general.smart")));
         }
      } else if (var1 == GameSettings.Options.DROPPED_ITEMS) {
         switch(this.ofDroppedItems) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault()));
         }
      } else if (var1 == GameSettings.Options.RAIN) {
         switch(this.ofRain) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         case 3:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault()));
         }
      } else if (var1 == GameSettings.Options.ANIMATED_WATER) {
         switch(this.ofAnimatedWater) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.animation.dynamic")));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn()));
         }
      } else if (var1 == GameSettings.Options.ANIMATED_LAVA) {
         switch(this.ofAnimatedLava) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.animation.dynamic")));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn()));
         }
      } else if (var1 == GameSettings.Options.ANIMATED_FIRE) {
         return this.ofAnimatedFire ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_PORTAL) {
         return this.ofAnimatedPortal ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_REDSTONE) {
         return this.ofAnimatedRedstone ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_EXPLOSION) {
         return this.ofAnimatedExplosion ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_FLAME) {
         return this.ofAnimatedFlame ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_SMOKE) {
         return this.ofAnimatedSmoke ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.VOID_PARTICLES) {
         return this.ofVoidParticles ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.WATER_PARTICLES) {
         return this.ofWaterParticles ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.PORTAL_PARTICLES) {
         return this.ofPortalParticles ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.POTION_PARTICLES) {
         return this.ofPotionParticles ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.FIREWORK_PARTICLES) {
         return this.ofFireworkParticles ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.DRIPPING_WATER_LAVA) {
         return this.ofDrippingWaterLava ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_TERRAIN) {
         return this.ofAnimatedTerrain ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.ANIMATED_TEXTURES) {
         return this.ofAnimatedTextures ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.RAIN_SPLASH) {
         return this.ofRainSplash ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.LAGOMETER) {
         return this.ofLagometer ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SHOW_FPS) {
         return this.ofShowFps ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.AUTOSAVE_TICKS) {
         return this.ofAutoSaveTicks <= 40 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.save.default"))) : (this.ofAutoSaveTicks <= 400 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.save.20s"))) : (this.ofAutoSaveTicks <= 4000 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.save.3min"))) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.save.30min")))));
      } else if (var1 == GameSettings.Options.BETTER_GRASS) {
         switch(this.ofBetterGrass) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         }
      } else if (var1 == GameSettings.Options.CONNECTED_TEXTURES) {
         switch(this.ofConnectedTextures) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
         }
      } else if (var1 == GameSettings.Options.WEATHER) {
         return this.ofWeather ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SKY) {
         return this.ofSky ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.STARS) {
         return this.ofStars ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SUN_MOON) {
         return this.ofSunMoon ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.VIGNETTE) {
         switch(this.ofVignette) {
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast()));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy()));
         default:
            return String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault()));
         }
      } else if (var1 == GameSettings.Options.CHUNK_UPDATES) {
         return String.valueOf((new StringBuilder(String.valueOf(var2))).append(this.ofChunkUpdates));
      } else if (var1 == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
         return this.ofChunkUpdatesDynamic ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.TIME) {
         return this.ofTime == 1 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.time.dayOnly"))) : (this.ofTime == 2 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.time.nightOnly"))) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault())));
      } else if (var1 == GameSettings.Options.CLEAR_WATER) {
         return this.ofClearWater ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.AA_LEVEL) {
         var8 = "";
         if (this.ofAaLevel != Config.getAntialiasingLevel()) {
            var8 = String.valueOf((new StringBuilder(" (")).append(Lang.get("of.general.restart")).append(")"));
         }

         return this.ofAaLevel == 0 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()).append(var8)) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(this.ofAaLevel).append(var8));
      } else if (var1 == GameSettings.Options.AF_LEVEL) {
         return this.ofAfLevel == 1 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(this.ofAfLevel));
      } else if (var1 == GameSettings.Options.PROFILER) {
         return this.ofProfiler ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.BETTER_SNOW) {
         return this.ofBetterSnow ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SWAMP_COLORS) {
         return this.ofSwampColors ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.RANDOM_MOBS) {
         return this.ofRandomMobs ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SMOOTH_BIOMES) {
         return this.ofSmoothBiomes ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.CUSTOM_FONTS) {
         return this.ofCustomFonts ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.CUSTOM_COLORS) {
         return this.ofCustomColors ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.CUSTOM_SKY) {
         return this.ofCustomSky ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.SHOW_CAPES) {
         return this.ofShowCapes ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.CUSTOM_ITEMS) {
         return this.ofCustomItems ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.NATURAL_TEXTURES) {
         return this.ofNaturalTextures ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.FAST_MATH) {
         return this.ofFastMath ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.FAST_RENDER) {
         return this.ofFastRender ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.TRANSLUCENT_BLOCKS) {
         return this.ofTranslucentBlocks == 1 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFast())) : (this.ofTranslucentBlocks == 2 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getFancy())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault())));
      } else if (var1 == GameSettings.Options.LAZY_CHUNK_LOADING) {
         return this.ofLazyChunkLoading ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.DYNAMIC_FOV) {
         return this.ofDynamicFov ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.DYNAMIC_LIGHTS) {
         var3 = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         return String.valueOf((new StringBuilder(String.valueOf(var2))).append(getTranslation(KEYS_DYNAMIC_LIGHTS, var3)));
      } else if (var1 == GameSettings.Options.FULLSCREEN_MODE) {
         return this.ofFullscreenMode.equals("Default") ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getDefault())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(this.ofFullscreenMode));
      } else if (var1 == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
         return this.heldItemTooltips ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOn())) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.getOff()));
      } else if (var1 == GameSettings.Options.FRAMERATE_LIMIT) {
         float var4 = this.getOptionFloatValue(var1);
         return var4 == 0.0F ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(Lang.get("of.options.framerateLimit.vsync"))) : (var4 == GameSettings.Options.access$2(var1) ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(I18n.format("options.framerateLimit.max"))) : String.valueOf((new StringBuilder(String.valueOf(var2))).append((int)var4).append(" fps")));
      } else {
         return null;
      }
   }

   public void loadOptions() {
      // $FF: Couldn't be decompiled
   }

   private void updateRenderClouds() {
      switch(this.ofClouds) {
      case 1:
      case 2:
      default:
         this.clouds = true;
         break;
      case 3:
         this.clouds = false;
      }

   }

   private void setOptionValueOF(GameSettings.Options var1, int var2) {
      if (var1 == GameSettings.Options.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            this.ofFogType = 2;
            if (!Config.isFancyFogAvailable()) {
               this.ofFogType = 3;
            }
            break;
         case 2:
            this.ofFogType = 3;
            break;
         case 3:
            this.ofFogType = 1;
            break;
         default:
            this.ofFogType = 1;
         }
      }

      if (var1 == GameSettings.Options.FOG_START) {
         this.ofFogStart += 0.2F;
         if (this.ofFogStart > 0.81F) {
            this.ofFogStart = 0.2F;
         }
      }

      if (var1 == GameSettings.Options.SMOOTH_FPS) {
         this.ofSmoothFps = !this.ofSmoothFps;
      }

      if (var1 == GameSettings.Options.SMOOTH_WORLD) {
         this.ofSmoothWorld = !this.ofSmoothWorld;
         Config.updateThreadPriorities();
      }

      if (var1 == GameSettings.Options.CLOUDS) {
         ++this.ofClouds;
         if (this.ofClouds > 3) {
            this.ofClouds = 0;
         }

         this.updateRenderClouds();
         this.mc.renderGlobal.resetClouds();
      }

      if (var1 == GameSettings.Options.TREES) {
         this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.DROPPED_ITEMS) {
         ++this.ofDroppedItems;
         if (this.ofDroppedItems > 2) {
            this.ofDroppedItems = 0;
         }
      }

      if (var1 == GameSettings.Options.RAIN) {
         ++this.ofRain;
         if (this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if (var1 == GameSettings.Options.ANIMATED_WATER) {
         ++this.ofAnimatedWater;
         if (this.ofAnimatedWater == 1) {
            ++this.ofAnimatedWater;
         }

         if (this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }
      }

      if (var1 == GameSettings.Options.ANIMATED_LAVA) {
         ++this.ofAnimatedLava;
         if (this.ofAnimatedLava == 1) {
            ++this.ofAnimatedLava;
         }

         if (this.ofAnimatedLava > 2) {
            this.ofAnimatedLava = 0;
         }
      }

      if (var1 == GameSettings.Options.ANIMATED_FIRE) {
         this.ofAnimatedFire = !this.ofAnimatedFire;
      }

      if (var1 == GameSettings.Options.ANIMATED_PORTAL) {
         this.ofAnimatedPortal = !this.ofAnimatedPortal;
      }

      if (var1 == GameSettings.Options.ANIMATED_REDSTONE) {
         this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
      }

      if (var1 == GameSettings.Options.ANIMATED_EXPLOSION) {
         this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
      }

      if (var1 == GameSettings.Options.ANIMATED_FLAME) {
         this.ofAnimatedFlame = !this.ofAnimatedFlame;
      }

      if (var1 == GameSettings.Options.ANIMATED_SMOKE) {
         this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
      }

      if (var1 == GameSettings.Options.VOID_PARTICLES) {
         this.ofVoidParticles = !this.ofVoidParticles;
      }

      if (var1 == GameSettings.Options.WATER_PARTICLES) {
         this.ofWaterParticles = !this.ofWaterParticles;
      }

      if (var1 == GameSettings.Options.PORTAL_PARTICLES) {
         this.ofPortalParticles = !this.ofPortalParticles;
      }

      if (var1 == GameSettings.Options.POTION_PARTICLES) {
         this.ofPotionParticles = !this.ofPotionParticles;
      }

      if (var1 == GameSettings.Options.FIREWORK_PARTICLES) {
         this.ofFireworkParticles = !this.ofFireworkParticles;
      }

      if (var1 == GameSettings.Options.DRIPPING_WATER_LAVA) {
         this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
      }

      if (var1 == GameSettings.Options.ANIMATED_TERRAIN) {
         this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
      }

      if (var1 == GameSettings.Options.ANIMATED_TEXTURES) {
         this.ofAnimatedTextures = !this.ofAnimatedTextures;
      }

      if (var1 == GameSettings.Options.RAIN_SPLASH) {
         this.ofRainSplash = !this.ofRainSplash;
      }

      if (var1 == GameSettings.Options.LAGOMETER) {
         this.ofLagometer = !this.ofLagometer;
      }

      if (var1 == GameSettings.Options.SHOW_FPS) {
         this.ofShowFps = !this.ofShowFps;
      }

      if (var1 == GameSettings.Options.AUTOSAVE_TICKS) {
         this.ofAutoSaveTicks *= 10;
         if (this.ofAutoSaveTicks > 40000) {
            this.ofAutoSaveTicks = 40;
         }
      }

      if (var1 == GameSettings.Options.BETTER_GRASS) {
         ++this.ofBetterGrass;
         if (this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.CONNECTED_TEXTURES) {
         ++this.ofConnectedTextures;
         if (this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         if (this.ofConnectedTextures != 2) {
            this.mc.refreshResources();
         }
      }

      if (var1 == GameSettings.Options.WEATHER) {
         this.ofWeather = !this.ofWeather;
      }

      if (var1 == GameSettings.Options.SKY) {
         this.ofSky = !this.ofSky;
      }

      if (var1 == GameSettings.Options.STARS) {
         this.ofStars = !this.ofStars;
      }

      if (var1 == GameSettings.Options.SUN_MOON) {
         this.ofSunMoon = !this.ofSunMoon;
      }

      if (var1 == GameSettings.Options.VIGNETTE) {
         ++this.ofVignette;
         if (this.ofVignette > 2) {
            this.ofVignette = 0;
         }
      }

      if (var1 == GameSettings.Options.CHUNK_UPDATES) {
         ++this.ofChunkUpdates;
         if (this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if (var1 == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if (var1 == GameSettings.Options.TIME) {
         ++this.ofTime;
         if (this.ofTime > 2) {
            this.ofTime = 0;
         }
      }

      if (var1 == GameSettings.Options.CLEAR_WATER) {
         this.ofClearWater = !this.ofClearWater;
         this.updateWaterOpacity();
      }

      if (var1 == GameSettings.Options.PROFILER) {
         this.ofProfiler = !this.ofProfiler;
      }

      if (var1 == GameSettings.Options.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.RANDOM_MOBS) {
         this.ofRandomMobs = !this.ofRandomMobs;
         RandomMobs.resetTextures();
      }

      if (var1 == GameSettings.Options.SMOOTH_BIOMES) {
         this.ofSmoothBiomes = !this.ofSmoothBiomes;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
         this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
      }

      if (var1 == GameSettings.Options.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         CustomColors.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.CUSTOM_ITEMS) {
         this.ofCustomItems = !this.ofCustomItems;
         this.mc.refreshResources();
      }

      if (var1 == GameSettings.Options.CUSTOM_SKY) {
         this.ofCustomSky = !this.ofCustomSky;
         CustomSky.update();
      }

      if (var1 == GameSettings.Options.SHOW_CAPES) {
         this.ofShowCapes = !this.ofShowCapes;
      }

      if (var1 == GameSettings.Options.NATURAL_TEXTURES) {
         this.ofNaturalTextures = !this.ofNaturalTextures;
         NaturalTextures.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.FAST_MATH) {
         this.ofFastMath = !this.ofFastMath;
         MathHelper.fastMath = this.ofFastMath;
      }

      if (var1 == GameSettings.Options.FAST_RENDER) {
         if (!this.ofFastRender && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
            return;
         }

         this.ofFastRender = !this.ofFastRender;
         if (this.ofFastRender) {
            this.mc.entityRenderer.stopUseShader();
         }

         Config.updateFramebufferSize();
      }

      if (var1 == GameSettings.Options.TRANSLUCENT_BLOCKS) {
         if (this.ofTranslucentBlocks == 0) {
            this.ofTranslucentBlocks = 1;
         } else if (this.ofTranslucentBlocks == 1) {
            this.ofTranslucentBlocks = 2;
         } else if (this.ofTranslucentBlocks == 2) {
            this.ofTranslucentBlocks = 0;
         } else {
            this.ofTranslucentBlocks = 0;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.LAZY_CHUNK_LOADING) {
         this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
         Config.updateAvailableProcessors();
         if (!Config.isSingleProcessor()) {
            this.ofLazyChunkLoading = false;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.FULLSCREEN_MODE) {
         List var3 = Arrays.asList(Config.getFullscreenModes());
         if (this.ofFullscreenMode.equals("Default")) {
            this.ofFullscreenMode = (String)var3.get(0);
         } else {
            int var4 = var3.indexOf(this.ofFullscreenMode);
            if (var4 < 0) {
               this.ofFullscreenMode = "Default";
            } else {
               ++var4;
               if (var4 >= var3.size()) {
                  this.ofFullscreenMode = "Default";
               } else {
                  this.ofFullscreenMode = (String)var3.get(var4);
               }
            }
         }
      }

      if (var1 == GameSettings.Options.DYNAMIC_FOV) {
         this.ofDynamicFov = !this.ofDynamicFov;
      }

      if (var1 == GameSettings.Options.DYNAMIC_LIGHTS) {
         this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         DynamicLights.removeLights(this.mc.renderGlobal);
      }

      if (var1 == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
         this.heldItemTooltips = !this.heldItemTooltips;
      }

   }

   public void setOptionKeyBinding(KeyBinding var1, int var2) {
      var1.setKeyCode(var2);
      this.saveOptions();
   }

   public String getKeyBinding(GameSettings.Options var1) {
      String var2 = this.getKeyBindingOF(var1);
      if (var2 != null) {
         return var2;
      } else {
         String var3 = String.valueOf((new StringBuilder(String.valueOf(I18n.format(var1.getEnumString())))).append(": "));
         if (var1.getEnumFloat()) {
            float var7 = this.getOptionFloatValue(var1);
            float var5 = var1.normalizeValue(var7);
            return var1 == GameSettings.Options.SENSITIVITY ? (var5 == 0.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.sensitivity.min"))) : (var5 == 1.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.sensitivity.max"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)(var5 * 200.0F)).append("%")))) : (var1 == GameSettings.Options.FOV ? (var7 == 70.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.fov.min"))) : (var7 == 110.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.fov.max"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)var7)))) : (var1 == GameSettings.Options.FRAMERATE_LIMIT ? (var7 == GameSettings.Options.access$2(var1) ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.framerateLimit.max"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)var7).append(" fps"))) : (var1 == GameSettings.Options.RENDER_CLOUDS ? (var7 == GameSettings.Options.access$3(var1) ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.cloudHeight.min"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)var7 + 128))) : (var1 == GameSettings.Options.GAMMA ? (var5 == 0.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.gamma.min"))) : (var5 == 1.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.gamma.max"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append("+").append((int)(var5 * 100.0F)).append("%")))) : (var1 == GameSettings.Options.SATURATION ? String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)(var5 * 400.0F)).append("%")) : (var1 == GameSettings.Options.CHAT_OPACITY ? String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)(var5 * 90.0F + 10.0F)).append("%")) : (var1 == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(GuiNewChat.calculateChatboxHeight(var5)).append("px")) : (var1 == GameSettings.Options.CHAT_HEIGHT_FOCUSED ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(GuiNewChat.calculateChatboxHeight(var5)).append("px")) : (var1 == GameSettings.Options.CHAT_WIDTH ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(GuiNewChat.calculateChatboxWidth(var5)).append("px")) : (var1 == GameSettings.Options.RENDER_DISTANCE ? String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)var7).append(" chunks")) : (var1 == GameSettings.Options.MIPMAP_LEVELS ? (var7 == 0.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.off"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)var7))) : (var1 == GameSettings.Options.STREAM_FPS ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(TwitchStream.func_152948_a(var5)).append(" fps")) : (var1 == GameSettings.Options.STREAM_KBPS ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(TwitchStream.func_152946_b(var5)).append(" Kbps")) : (var1 == GameSettings.Options.STREAM_BYTES_PER_PIXEL ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(String.format("%.3f bpp", TwitchStream.func_152947_c(var5)))) : (var5 == 0.0F ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.off"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append((int)(var5 * 100.0F)).append("%")))))))))))))))));
         } else if (var1.getEnumBoolean()) {
            boolean var6 = this.getOptionOrdinalValue(var1);
            return var6 ? String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.on"))) : String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.off")));
         } else if (var1 == GameSettings.Options.GUI_SCALE) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(GUISCALES, this.guiScale)));
         } else if (var1 == GameSettings.Options.CHAT_VISIBILITY) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format(this.chatVisibility.getResourceKey())));
         } else if (var1 == GameSettings.Options.PARTICLES) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(PARTICLES, this.particleSetting)));
         } else if (var1 == GameSettings.Options.AMBIENT_OCCLUSION) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion)));
         } else if (var1 == GameSettings.Options.STREAM_COMPRESSION) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(STREAM_COMPRESSIONS, this.streamCompression)));
         } else if (var1 == GameSettings.Options.STREAM_CHAT_ENABLED) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(STREAM_CHAT_MODES, this.streamChatEnabled)));
         } else if (var1 == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(STREAM_CHAT_FILTER_MODES, this.streamChatUserFilter)));
         } else if (var1 == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return String.valueOf((new StringBuilder(String.valueOf(var3))).append(getTranslation(STREAM_MIC_MODES, this.streamMicToggleBehavior)));
         } else if (var1 == GameSettings.Options.GRAPHICS) {
            if (this.fancyGraphics) {
               return String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.graphics.fancy")));
            } else {
               String var4 = "options.graphics.fast";
               return String.valueOf((new StringBuilder(String.valueOf(var3))).append(I18n.format("options.graphics.fast")));
            }
         } else {
            return var3;
         }
      }
   }

   public GameSettings(Minecraft var1, File var2) {
      this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
      this.chatColours = true;
      this.chatLinks = true;
      this.chatLinksPrompt = true;
      this.chatOpacity = 1.0F;
      this.snooperEnabled = true;
      this.enableVsync = true;
      this.field_178881_t = false;
      this.field_178880_u = true;
      this.field_178879_v = false;
      this.pauseOnLostFocus = true;
      this.field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
      this.heldItemTooltips = true;
      this.chatScale = 1.0F;
      this.chatWidth = 1.0F;
      this.chatHeightUnfocused = 0.44366196F;
      this.chatHeightFocused = 1.0F;
      this.showInventoryAchievementHint = true;
      this.mipmapLevels = 4;
      this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
      this.streamBytesPerPixel = 0.5F;
      this.streamMicVolume = 1.0F;
      this.streamGameVolume = 1.0F;
      this.streamKbps = 0.5412844F;
      this.streamFps = 0.31690142F;
      this.streamCompression = 1;
      this.streamSendMetadata = true;
      this.streamPreferredServer = "";
      this.streamChatEnabled = 0;
      this.streamChatUserFilter = 0;
      this.streamMicToggleBehavior = 0;
      this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
      this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
      this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
      this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
      this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
      this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
      this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
      this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
      this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
      this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
      this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
      this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
      this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
      this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
      this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
      this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
      this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
      this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
      this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
      this.field_178883_an = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
      this.keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
      this.keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
      this.keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
      this.keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
      this.keyBindsHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.field_178883_an}, this.keyBindsHotbar);
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
      this.mc = var1;
      this.optionsFile = new File(var2, "options.txt");
      this.optionsFileOF = new File(var2, "optionsof.txt");
      this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
      this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
      this.keyBindings = (KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
      GameSettings.Options.RENDER_DISTANCE.setValueMax(32.0F);
      this.renderDistanceChunks = 8;
      this.loadOptions();
      Config.initGameSettings(this);
   }

   public void resetSettings() {
      this.renderDistanceChunks = 8;
      this.viewBobbing = true;
      this.anaglyph = false;
      this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
      this.enableVsync = false;
      this.updateVSync();
      this.mipmapLevels = 4;
      this.fancyGraphics = true;
      this.ambientOcclusion = 2;
      this.clouds = true;
      this.fovSetting = 70.0F;
      this.gammaSetting = 0.0F;
      this.guiScale = 0;
      this.particleSetting = 0;
      this.heldItemTooltips = true;
      this.field_178881_t = false;
      this.field_178880_u = true;
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
      this.ofFastRender = false;
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

   public void saveOptions() {
      if (Reflector.FMLClientHandler.exists()) {
         Object var1 = Reflector.call(Reflector.FMLClientHandler_instance);
         if (var1 != null && Reflector.callBoolean(var1, Reflector.FMLClientHandler_isLoading)) {
            return;
         }
      }

      try {
         PrintWriter var9 = new PrintWriter(new FileWriter(this.optionsFile));
         var9.println(String.valueOf((new StringBuilder("invertYMouse:")).append(this.invertMouse)));
         var9.println(String.valueOf((new StringBuilder("mouseSensitivity:")).append(this.mouseSensitivity)));
         var9.println(String.valueOf((new StringBuilder("fov:")).append((this.fovSetting - 70.0F) / 40.0F)));
         var9.println(String.valueOf((new StringBuilder("gamma:")).append(this.gammaSetting)));
         var9.println(String.valueOf((new StringBuilder("saturation:")).append(this.saturation)));
         var9.println(String.valueOf((new StringBuilder("renderDistance:")).append(this.renderDistanceChunks)));
         var9.println(String.valueOf((new StringBuilder("guiScale:")).append(this.guiScale)));
         var9.println(String.valueOf((new StringBuilder("particles:")).append(this.particleSetting)));
         var9.println(String.valueOf((new StringBuilder("bobView:")).append(this.viewBobbing)));
         var9.println(String.valueOf((new StringBuilder("anaglyph3d:")).append(this.anaglyph)));
         var9.println(String.valueOf((new StringBuilder("maxFps:")).append(this.limitFramerate)));
         var9.println(String.valueOf((new StringBuilder("fboEnable:")).append(this.fboEnable)));
         var9.println(String.valueOf((new StringBuilder("difficulty:")).append(this.difficulty.getDifficultyId())));
         var9.println(String.valueOf((new StringBuilder("fancyGraphics:")).append(this.fancyGraphics)));
         var9.println(String.valueOf((new StringBuilder("ao:")).append(this.ambientOcclusion)));
         var9.println(String.valueOf((new StringBuilder("renderClouds:")).append(this.clouds)));
         var9.println(String.valueOf((new StringBuilder("resourcePacks:")).append(gson.toJson(this.resourcePacks))));
         var9.println(String.valueOf((new StringBuilder("lastServer:")).append(this.lastServer)));
         var9.println(String.valueOf((new StringBuilder("lang:")).append(this.language)));
         var9.println(String.valueOf((new StringBuilder("chatVisibility:")).append(this.chatVisibility.getChatVisibility())));
         var9.println(String.valueOf((new StringBuilder("chatColors:")).append(this.chatColours)));
         var9.println(String.valueOf((new StringBuilder("chatLinks:")).append(this.chatLinks)));
         var9.println(String.valueOf((new StringBuilder("chatLinksPrompt:")).append(this.chatLinksPrompt)));
         var9.println(String.valueOf((new StringBuilder("chatOpacity:")).append(this.chatOpacity)));
         var9.println(String.valueOf((new StringBuilder("snooperEnabled:")).append(this.snooperEnabled)));
         var9.println(String.valueOf((new StringBuilder("fullscreen:")).append(this.fullScreen)));
         var9.println(String.valueOf((new StringBuilder("enableVsync:")).append(this.enableVsync)));
         var9.println(String.valueOf((new StringBuilder("useVbo:")).append(this.field_178881_t)));
         var9.println(String.valueOf((new StringBuilder("hideServerAddress:")).append(this.hideServerAddress)));
         var9.println(String.valueOf((new StringBuilder("advancedItemTooltips:")).append(this.advancedItemTooltips)));
         var9.println(String.valueOf((new StringBuilder("pauseOnLostFocus:")).append(this.pauseOnLostFocus)));
         var9.println(String.valueOf((new StringBuilder("touchscreen:")).append(this.touchscreen)));
         var9.println(String.valueOf((new StringBuilder("overrideWidth:")).append(this.overrideWidth)));
         var9.println(String.valueOf((new StringBuilder("overrideHeight:")).append(this.overrideHeight)));
         var9.println(String.valueOf((new StringBuilder("heldItemTooltips:")).append(this.heldItemTooltips)));
         var9.println(String.valueOf((new StringBuilder("chatHeightFocused:")).append(this.chatHeightFocused)));
         var9.println(String.valueOf((new StringBuilder("chatHeightUnfocused:")).append(this.chatHeightUnfocused)));
         var9.println(String.valueOf((new StringBuilder("chatScale:")).append(this.chatScale)));
         var9.println(String.valueOf((new StringBuilder("chatWidth:")).append(this.chatWidth)));
         var9.println(String.valueOf((new StringBuilder("showInventoryAchievementHint:")).append(this.showInventoryAchievementHint)));
         var9.println(String.valueOf((new StringBuilder("mipmapLevels:")).append(this.mipmapLevels)));
         var9.println(String.valueOf((new StringBuilder("streamBytesPerPixel:")).append(this.streamBytesPerPixel)));
         var9.println(String.valueOf((new StringBuilder("streamMicVolume:")).append(this.streamMicVolume)));
         var9.println(String.valueOf((new StringBuilder("streamSystemVolume:")).append(this.streamGameVolume)));
         var9.println(String.valueOf((new StringBuilder("streamKbps:")).append(this.streamKbps)));
         var9.println(String.valueOf((new StringBuilder("streamFps:")).append(this.streamFps)));
         var9.println(String.valueOf((new StringBuilder("streamCompression:")).append(this.streamCompression)));
         var9.println(String.valueOf((new StringBuilder("streamSendMetadata:")).append(this.streamSendMetadata)));
         var9.println(String.valueOf((new StringBuilder("streamPreferredServer:")).append(this.streamPreferredServer)));
         var9.println(String.valueOf((new StringBuilder("streamChatEnabled:")).append(this.streamChatEnabled)));
         var9.println(String.valueOf((new StringBuilder("streamChatUserFilter:")).append(this.streamChatUserFilter)));
         var9.println(String.valueOf((new StringBuilder("streamMicToggleBehavior:")).append(this.streamMicToggleBehavior)));
         var9.println(String.valueOf((new StringBuilder("forceUnicodeFont:")).append(this.forceUnicodeFont)));
         var9.println(String.valueOf((new StringBuilder("allowBlockAlternatives:")).append(this.field_178880_u)));
         var9.println(String.valueOf((new StringBuilder("reducedDebugInfo:")).append(this.field_178879_v)));
         KeyBinding[] var2 = this.keyBindings;
         int var3 = var2.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            KeyBinding var5 = var2[var4];
            var9.println(String.valueOf((new StringBuilder("key_")).append(var5.getKeyDescription()).append(":").append(var5.getKeyCode())));
         }

         SoundCategory[] var10 = SoundCategory.values();
         var3 = var10.length;

         for(var4 = 0; var4 < var3; ++var4) {
            SoundCategory var6 = var10[var4];
            var9.println(String.valueOf((new StringBuilder("soundCategory_")).append(var6.getCategoryName()).append(":").append(this.getSoundLevel(var6))));
         }

         EnumPlayerModelParts[] var11 = EnumPlayerModelParts.values();
         var3 = var11.length;

         for(var4 = 0; var4 < var3; ++var4) {
            EnumPlayerModelParts var7 = var11[var4];
            var9.println(String.valueOf((new StringBuilder("modelPart_")).append(var7.func_179329_c()).append(":").append(this.field_178882_aU.contains(var7))));
         }

         var9.close();
      } catch (Exception var8) {
         logger.error("Failed to save options", var8);
      }

      this.saveOfOptions();
      this.sendSettingsToServer();
   }

   private static int nextValue(int var0, int[] var1) {
      int var2 = indexOf(var0, var1);
      if (var2 < 0) {
         return var1[0];
      } else {
         ++var2;
         if (var2 >= var1.length) {
            var2 = 0;
         }

         return var1[var2];
      }
   }

   public boolean getOptionOrdinalValue(GameSettings.Options var1) {
      switch(var1) {
      case INVERT_MOUSE:
         return this.invertMouse;
      case VIEW_BOBBING:
         return this.viewBobbing;
      case ANAGLYPH:
         return this.anaglyph;
      case FBO_ENABLE:
         return this.fboEnable;
      case RENDER_CLOUDS:
         return this.clouds;
      case CHAT_COLOR:
         return this.chatColours;
      case CHAT_LINKS:
         return this.chatLinks;
      case CHAT_LINKS_PROMPT:
         return this.chatLinksPrompt;
      case SNOOPER_ENABLED:
         return this.snooperEnabled;
      case USE_FULLSCREEN:
         return this.fullScreen;
      case ENABLE_VSYNC:
         return this.enableVsync;
      case USE_VBO:
         return this.field_178881_t;
      case TOUCHSCREEN:
         return this.touchscreen;
      case STREAM_SEND_METADATA:
         return this.streamSendMetadata;
      case FORCE_UNICODE_FONT:
         return this.forceUnicodeFont;
      case BLOCK_ALTERNATIVES:
         return this.field_178880_u;
      case REDUCED_DEBUG_INFO:
         return this.field_178879_v;
      default:
         return false;
      }
   }

   public void sendSettingsToServer() {
      if (this.mc.thePlayer != null) {
         int var1 = 0;

         EnumPlayerModelParts var2;
         for(Iterator var3 = this.field_178882_aU.iterator(); var3.hasNext(); var1 |= var2.func_179327_a()) {
            var2 = (EnumPlayerModelParts)var3.next();
         }

         this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, var1));
      }

   }

   private static int limit(int var0, int[] var1) {
      int var2 = indexOf(var0, var1);
      return var2 < 0 ? var1[0] : var0;
   }

   private void setOptionFloatValueOF(GameSettings.Options var1, float var2) {
      if (var1 == GameSettings.Options.CLOUD_HEIGHT) {
         this.ofCloudsHeight = var2;
         this.mc.renderGlobal.resetClouds();
      }

      if (var1 == GameSettings.Options.AO_LEVEL) {
         this.ofAoLevel = var2;
         this.mc.renderGlobal.loadRenderers();
      }

      int var3;
      if (var1 == GameSettings.Options.AA_LEVEL) {
         var3 = (int)var2;
         if (var3 > 0 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
            return;
         }

         int[] var4 = new int[]{0, 2, 4, 6, 8, 12, 16};
         this.ofAaLevel = 0;

         for(int var5 = 0; var5 < var4.length; ++var5) {
            if (var3 >= var4[var5]) {
               this.ofAaLevel = var4[var5];
            }
         }

         this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
      }

      if (var1 == GameSettings.Options.AF_LEVEL) {
         var3 = (int)var2;
         if (var3 > 1 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
            return;
         }

         for(this.ofAfLevel = 1; this.ofAfLevel * 2 <= var3; this.ofAfLevel *= 2) {
         }

         this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
         this.mc.refreshResources();
      }

      if (var1 == GameSettings.Options.MIPMAP_TYPE) {
         var3 = (int)var2;
         this.ofMipmapType = Config.limit(var3, 0, 3);
         this.mc.refreshResources();
      }

   }

   public void setOptionValue(GameSettings.Options var1, int var2) {
      this.setOptionValueOF(var1, var2);
      if (var1 == GameSettings.Options.INVERT_MOUSE) {
         this.invertMouse = !this.invertMouse;
      }

      if (var1 == GameSettings.Options.GUI_SCALE) {
         this.guiScale = this.guiScale + var2 & 3;
      }

      if (var1 == GameSettings.Options.PARTICLES) {
         this.particleSetting = (this.particleSetting + var2) % 3;
      }

      if (var1 == GameSettings.Options.VIEW_BOBBING) {
         this.viewBobbing = !this.viewBobbing;
      }

      if (var1 == GameSettings.Options.RENDER_CLOUDS) {
         this.clouds = !this.clouds;
      }

      if (var1 == GameSettings.Options.FORCE_UNICODE_FONT) {
         this.forceUnicodeFont = !this.forceUnicodeFont;
         this.mc.fontRendererObj.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
      }

      if (var1 == GameSettings.Options.FBO_ENABLE) {
         this.fboEnable = !this.fboEnable;
      }

      if (var1 == GameSettings.Options.ANAGLYPH) {
         this.anaglyph = !this.anaglyph;
         this.mc.refreshResources();
      }

      if (var1 == GameSettings.Options.GRAPHICS) {
         this.fancyGraphics = !this.fancyGraphics;
         this.updateRenderClouds();
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.AMBIENT_OCCLUSION) {
         this.ambientOcclusion = (this.ambientOcclusion + var2) % 3;
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.CHAT_VISIBILITY) {
         this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + var2) % 3);
      }

      if (var1 == GameSettings.Options.STREAM_COMPRESSION) {
         this.streamCompression = (this.streamCompression + var2) % 3;
      }

      if (var1 == GameSettings.Options.STREAM_SEND_METADATA) {
         this.streamSendMetadata = !this.streamSendMetadata;
      }

      if (var1 == GameSettings.Options.STREAM_CHAT_ENABLED) {
         this.streamChatEnabled = (this.streamChatEnabled + var2) % 3;
      }

      if (var1 == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
         this.streamChatUserFilter = (this.streamChatUserFilter + var2) % 3;
      }

      if (var1 == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
         this.streamMicToggleBehavior = (this.streamMicToggleBehavior + var2) % 2;
      }

      if (var1 == GameSettings.Options.CHAT_COLOR) {
         this.chatColours = !this.chatColours;
      }

      if (var1 == GameSettings.Options.CHAT_LINKS) {
         this.chatLinks = !this.chatLinks;
      }

      if (var1 == GameSettings.Options.CHAT_LINKS_PROMPT) {
         this.chatLinksPrompt = !this.chatLinksPrompt;
      }

      if (var1 == GameSettings.Options.SNOOPER_ENABLED) {
         this.snooperEnabled = !this.snooperEnabled;
      }

      if (var1 == GameSettings.Options.TOUCHSCREEN) {
         this.touchscreen = !this.touchscreen;
      }

      if (var1 == GameSettings.Options.USE_FULLSCREEN) {
         this.fullScreen = !this.fullScreen;
         if (this.mc.isFullScreen() != this.fullScreen) {
            this.mc.toggleFullscreen();
         }
      }

      if (var1 == GameSettings.Options.ENABLE_VSYNC) {
         this.enableVsync = !this.enableVsync;
         Display.setVSyncEnabled(this.enableVsync);
      }

      if (var1 == GameSettings.Options.USE_VBO) {
         this.field_178881_t = !this.field_178881_t;
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.BLOCK_ALTERNATIVES) {
         this.field_178880_u = !this.field_178880_u;
         this.mc.renderGlobal.loadRenderers();
      }

      if (var1 == GameSettings.Options.REDUCED_DEBUG_INFO) {
         this.field_178879_v = !this.field_178879_v;
      }

      this.saveOptions();
   }

   private void updateWaterOpacity() {
      if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
         Config.waterOpacityChanged = true;
      }

      ClearWater.updateWaterOpacity(this, this.mc.theWorld);
   }

   public Set func_178876_d() {
      return ImmutableSet.copyOf(this.field_178882_aU);
   }

   public GameSettings() {
      this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
      this.chatColours = true;
      this.chatLinks = true;
      this.chatLinksPrompt = true;
      this.chatOpacity = 1.0F;
      this.snooperEnabled = true;
      this.enableVsync = true;
      this.field_178881_t = false;
      this.field_178880_u = true;
      this.field_178879_v = false;
      this.pauseOnLostFocus = true;
      this.field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
      this.heldItemTooltips = true;
      this.chatScale = 1.0F;
      this.chatWidth = 1.0F;
      this.chatHeightUnfocused = 0.44366196F;
      this.chatHeightFocused = 1.0F;
      this.showInventoryAchievementHint = true;
      this.mipmapLevels = 4;
      this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
      this.streamBytesPerPixel = 0.5F;
      this.streamMicVolume = 1.0F;
      this.streamGameVolume = 1.0F;
      this.streamKbps = 0.5412844F;
      this.streamFps = 0.31690142F;
      this.streamCompression = 1;
      this.streamSendMetadata = true;
      this.streamPreferredServer = "";
      this.streamChatEnabled = 0;
      this.streamChatUserFilter = 0;
      this.streamMicToggleBehavior = 0;
      this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
      this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
      this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
      this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
      this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
      this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
      this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
      this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
      this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
      this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
      this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
      this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
      this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
      this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
      this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
      this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
      this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
      this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
      this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
      this.field_178883_an = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
      this.keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
      this.keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
      this.keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
      this.keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
      this.keyBindsHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause, this.keyBindStreamCommercials, this.keyBindStreamToggleMic, this.keyBindFullscreen, this.field_178883_an}, this.keyBindsHotbar);
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
   }

   public void func_178877_a(EnumPlayerModelParts var1) {
      if (!this.func_178876_d().contains(var1)) {
         this.field_178882_aU.add(var1);
      } else {
         this.field_178882_aU.remove(var1);
      }

      this.sendSettingsToServer();
   }

   public void func_178878_a(EnumPlayerModelParts var1, boolean var2) {
      if (var2) {
         this.field_178882_aU.add(var1);
      } else {
         this.field_178882_aU.remove(var1);
      }

      this.sendSettingsToServer();
   }

   public void updateVSync() {
      Display.setVSyncEnabled(this.enableVsync);
   }

   public void loadOfOptions() {
      try {
         File var1 = this.optionsFileOF;
         if (!var1.exists()) {
            var1 = this.optionsFile;
         }

         if (!var1.exists()) {
            return;
         }

         BufferedReader var2 = new BufferedReader(new FileReader(var1));
         String var3 = "";

         while((var3 = var2.readLine()) != null) {
            try {
               String[] var4 = var3.split(":");
               if (var4[0].equals("ofRenderDistanceChunks") && var4.length >= 2) {
                  this.renderDistanceChunks = Integer.valueOf(var4[1]);
                  this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
               }

               if (var4[0].equals("ofFogType") && var4.length >= 2) {
                  this.ofFogType = Integer.valueOf(var4[1]);
                  this.ofFogType = Config.limit(this.ofFogType, 1, 3);
               }

               if (var4[0].equals("ofFogStart") && var4.length >= 2) {
                  this.ofFogStart = Float.valueOf(var4[1]);
                  if (this.ofFogStart < 0.2F) {
                     this.ofFogStart = 0.2F;
                  }

                  if (this.ofFogStart > 0.81F) {
                     this.ofFogStart = 0.8F;
                  }
               }

               if (var4[0].equals("ofMipmapType") && var4.length >= 2) {
                  this.ofMipmapType = Integer.valueOf(var4[1]);
                  this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
               }

               if (var4[0].equals("ofOcclusionFancy") && var4.length >= 2) {
                  this.ofOcclusionFancy = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSmoothFps") && var4.length >= 2) {
                  this.ofSmoothFps = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSmoothWorld") && var4.length >= 2) {
                  this.ofSmoothWorld = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAoLevel") && var4.length >= 2) {
                  this.ofAoLevel = Float.valueOf(var4[1]);
                  this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
               }

               if (var4[0].equals("ofClouds") && var4.length >= 2) {
                  this.ofClouds = Integer.valueOf(var4[1]);
                  this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                  this.updateRenderClouds();
               }

               if (var4[0].equals("ofCloudsHeight") && var4.length >= 2) {
                  this.ofCloudsHeight = Float.valueOf(var4[1]);
                  this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
               }

               if (var4[0].equals("ofTrees") && var4.length >= 2) {
                  this.ofTrees = Integer.valueOf(var4[1]);
                  this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
               }

               if (var4[0].equals("ofDroppedItems") && var4.length >= 2) {
                  this.ofDroppedItems = Integer.valueOf(var4[1]);
                  this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
               }

               if (var4[0].equals("ofRain") && var4.length >= 2) {
                  this.ofRain = Integer.valueOf(var4[1]);
                  this.ofRain = Config.limit(this.ofRain, 0, 3);
               }

               if (var4[0].equals("ofAnimatedWater") && var4.length >= 2) {
                  this.ofAnimatedWater = Integer.valueOf(var4[1]);
                  this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
               }

               if (var4[0].equals("ofAnimatedLava") && var4.length >= 2) {
                  this.ofAnimatedLava = Integer.valueOf(var4[1]);
                  this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
               }

               if (var4[0].equals("ofAnimatedFire") && var4.length >= 2) {
                  this.ofAnimatedFire = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedPortal") && var4.length >= 2) {
                  this.ofAnimatedPortal = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedRedstone") && var4.length >= 2) {
                  this.ofAnimatedRedstone = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedExplosion") && var4.length >= 2) {
                  this.ofAnimatedExplosion = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedFlame") && var4.length >= 2) {
                  this.ofAnimatedFlame = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedSmoke") && var4.length >= 2) {
                  this.ofAnimatedSmoke = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofVoidParticles") && var4.length >= 2) {
                  this.ofVoidParticles = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofWaterParticles") && var4.length >= 2) {
                  this.ofWaterParticles = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofPortalParticles") && var4.length >= 2) {
                  this.ofPortalParticles = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofPotionParticles") && var4.length >= 2) {
                  this.ofPotionParticles = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofFireworkParticles") && var4.length >= 2) {
                  this.ofFireworkParticles = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofDrippingWaterLava") && var4.length >= 2) {
                  this.ofDrippingWaterLava = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedTerrain") && var4.length >= 2) {
                  this.ofAnimatedTerrain = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAnimatedTextures") && var4.length >= 2) {
                  this.ofAnimatedTextures = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofRainSplash") && var4.length >= 2) {
                  this.ofRainSplash = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofLagometer") && var4.length >= 2) {
                  this.ofLagometer = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofShowFps") && var4.length >= 2) {
                  this.ofShowFps = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofAutoSaveTicks") && var4.length >= 2) {
                  this.ofAutoSaveTicks = Integer.valueOf(var4[1]);
                  this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
               }

               if (var4[0].equals("ofBetterGrass") && var4.length >= 2) {
                  this.ofBetterGrass = Integer.valueOf(var4[1]);
                  this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
               }

               if (var4[0].equals("ofConnectedTextures") && var4.length >= 2) {
                  this.ofConnectedTextures = Integer.valueOf(var4[1]);
                  this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
               }

               if (var4[0].equals("ofWeather") && var4.length >= 2) {
                  this.ofWeather = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSky") && var4.length >= 2) {
                  this.ofSky = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofStars") && var4.length >= 2) {
                  this.ofStars = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSunMoon") && var4.length >= 2) {
                  this.ofSunMoon = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofVignette") && var4.length >= 2) {
                  this.ofVignette = Integer.valueOf(var4[1]);
                  this.ofVignette = Config.limit(this.ofVignette, 0, 2);
               }

               if (var4[0].equals("ofChunkUpdates") && var4.length >= 2) {
                  this.ofChunkUpdates = Integer.valueOf(var4[1]);
                  this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
               }

               if (var4[0].equals("ofChunkUpdatesDynamic") && var4.length >= 2) {
                  this.ofChunkUpdatesDynamic = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofTime") && var4.length >= 2) {
                  this.ofTime = Integer.valueOf(var4[1]);
                  this.ofTime = Config.limit(this.ofTime, 0, 2);
               }

               if (var4[0].equals("ofClearWater") && var4.length >= 2) {
                  this.ofClearWater = Boolean.valueOf(var4[1]);
                  this.updateWaterOpacity();
               }

               if (var4[0].equals("ofAaLevel") && var4.length >= 2) {
                  this.ofAaLevel = Integer.valueOf(var4[1]);
                  this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
               }

               if (var4[0].equals("ofAfLevel") && var4.length >= 2) {
                  this.ofAfLevel = Integer.valueOf(var4[1]);
                  this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
               }

               if (var4[0].equals("ofProfiler") && var4.length >= 2) {
                  this.ofProfiler = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofBetterSnow") && var4.length >= 2) {
                  this.ofBetterSnow = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSwampColors") && var4.length >= 2) {
                  this.ofSwampColors = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofRandomMobs") && var4.length >= 2) {
                  this.ofRandomMobs = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofSmoothBiomes") && var4.length >= 2) {
                  this.ofSmoothBiomes = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofCustomFonts") && var4.length >= 2) {
                  this.ofCustomFonts = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofCustomColors") && var4.length >= 2) {
                  this.ofCustomColors = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofCustomItems") && var4.length >= 2) {
                  this.ofCustomItems = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofCustomSky") && var4.length >= 2) {
                  this.ofCustomSky = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofShowCapes") && var4.length >= 2) {
                  this.ofShowCapes = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofNaturalTextures") && var4.length >= 2) {
                  this.ofNaturalTextures = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofLazyChunkLoading") && var4.length >= 2) {
                  this.ofLazyChunkLoading = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofDynamicFov") && var4.length >= 2) {
                  this.ofDynamicFov = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofDynamicLights") && var4.length >= 2) {
                  this.ofDynamicLights = Integer.valueOf(var4[1]);
                  this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
               }

               if (var4[0].equals("ofFullscreenMode") && var4.length >= 2) {
                  this.ofFullscreenMode = var4[1];
               }

               if (var4[0].equals("ofFastMath") && var4.length >= 2) {
                  this.ofFastMath = Boolean.valueOf(var4[1]);
                  MathHelper.fastMath = this.ofFastMath;
               }

               if (var4[0].equals("ofFastRender") && var4.length >= 2) {
                  this.ofFastRender = Boolean.valueOf(var4[1]);
               }

               if (var4[0].equals("ofTranslucentBlocks") && var4.length >= 2) {
                  this.ofTranslucentBlocks = Integer.valueOf(var4[1]);
                  this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
               }

               if (var4[0].equals(String.valueOf((new StringBuilder("key_")).append(this.ofKeyBindZoom.getKeyDescription())))) {
                  this.ofKeyBindZoom.setKeyCode(Integer.parseInt(var4[1]));
               }
            } catch (Exception var5) {
               Config.dbg(String.valueOf((new StringBuilder("Skipping bad option: ")).append(var3)));
               var5.printStackTrace();
            }
         }

         KeyBinding.resetKeyBindingArrayAndHash();
         var2.close();
      } catch (Exception var6) {
         Config.warn("Failed to load options");
         var6.printStackTrace();
      }

   }

   public void setSoundLevel(SoundCategory var1, float var2) {
      this.mc.getSoundHandler().setSoundLevel(var1, var2);
      this.mapSoundLevels.put(var1, var2);
   }

   public float getOptionFloatValue(GameSettings.Options var1) {
      return var1 == GameSettings.Options.CLOUD_HEIGHT ? this.ofCloudsHeight : (var1 == GameSettings.Options.AO_LEVEL ? this.ofAoLevel : (var1 == GameSettings.Options.AA_LEVEL ? (float)this.ofAaLevel : (var1 == GameSettings.Options.AF_LEVEL ? (float)this.ofAfLevel : (var1 == GameSettings.Options.MIPMAP_TYPE ? (float)this.ofMipmapType : (var1 == GameSettings.Options.FRAMERATE_LIMIT ? ((float)this.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync ? 0.0F : (float)this.limitFramerate) : (var1 == GameSettings.Options.FOV ? this.fovSetting : (var1 == GameSettings.Options.GAMMA ? this.gammaSetting : (var1 == GameSettings.Options.SATURATION ? this.saturation : (var1 == GameSettings.Options.SENSITIVITY ? this.mouseSensitivity : (var1 == GameSettings.Options.CHAT_OPACITY ? this.chatOpacity : (var1 == GameSettings.Options.CHAT_HEIGHT_FOCUSED ? this.chatHeightFocused : (var1 == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED ? this.chatHeightUnfocused : (var1 == GameSettings.Options.CHAT_SCALE ? this.chatScale : (var1 == GameSettings.Options.CHAT_WIDTH ? this.chatWidth : (var1 == GameSettings.Options.FRAMERATE_LIMIT ? (float)this.limitFramerate : (var1 == GameSettings.Options.MIPMAP_LEVELS ? (float)this.mipmapLevels : (var1 == GameSettings.Options.RENDER_DISTANCE ? (float)this.renderDistanceChunks : (var1 == GameSettings.Options.STREAM_BYTES_PER_PIXEL ? this.streamBytesPerPixel : (var1 == GameSettings.Options.STREAM_VOLUME_MIC ? this.streamMicVolume : (var1 == GameSettings.Options.STREAM_VOLUME_SYSTEM ? this.streamGameVolume : (var1 == GameSettings.Options.STREAM_KBPS ? this.streamKbps : (var1 == GameSettings.Options.STREAM_FPS ? this.streamFps : 0.0F))))))))))))))))))))));
   }

   public static String getKeyDisplayString(int var0) {
      return var0 < 0 ? I18n.format("key.mouseButton", var0 + 101) : (var0 < 256 ? Keyboard.getKeyName(var0) : String.format("%c", (char)(var0 - 256)).toUpperCase());
   }

   public void setAllAnimations(boolean var1) {
      int var2 = var1 ? 0 : 2;
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
      this.particleSetting = var1 ? 0 : 2;
      this.ofDrippingWaterLava = var1;
      this.ofAnimatedTerrain = var1;
      this.ofAnimatedTextures = var1;
   }

   public static enum Options {
      SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true),
      CHAT_WIDTH("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false),
      MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
      CUSTOM_COLORS("CUSTOM_COLORS", 89, "", 999, "of.options.CUSTOM_COLORS", false, false),
      DYNAMIC_LIGHTS("DYNAMIC_LIGHTS", 105, "", 999, "of.options.DYNAMIC_LIGHTS", false, false),
      SUN_MOON("SUN_MOON", 67, "", 999, "of.options.SUN_MOON", false, false),
      POTION_PARTICLES("POTION_PARTICLES", 78, "", 999, "of.options.POTION_PARTICLES", false, false),
      TREES("TREES", 49, "", 999, "of.options.TREES", false, false),
      VIGNETTE("VIGNETTE", 68, "", 999, "of.options.VIGNETTE", false, false),
      DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 81, "", 999, "of.options.DRIPPING_WATER_LAVA", false, false),
      STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false),
      ANIMATED_TERRAIN("ANIMATED_TERRAIN", 84, "", 999, "of.options.ANIMATED_TERRAIN", false, false);

      private static final GameSettings.Options[] ENUM$VALUES = new GameSettings.Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO, FOG_FANCY, FOG_START, MIPMAP_TYPE, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, RAIN, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, SHOW_FPS, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, VIGNETTE, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, FIREWORK_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, CUSTOM_ITEMS, AA_LEVEL, AF_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, FAST_RENDER, TRANSLUCENT_BLOCKS, DYNAMIC_FOV, DYNAMIC_LIGHTS};
      PROFILER("PROFILER", 80, "", 999, "of.options.PROFILER", false, false),
      BETTER_GRASS("BETTER_GRASS", 59, "", 999, "of.options.BETTER_GRASS", false, false),
      PORTAL_PARTICLES("PORTAL_PARTICLES", 77, "", 999, "of.options.PORTAL_PARTICLES", false, false),
      ANIMATED_REDSTONE("ANIMATED_REDSTONE", 60, "", 999, "of.options.ANIMATED_REDSTONE", false, false),
      CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 27, "CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false),
      CHAT_LINKS("CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true),
      FOG_FANCY("FOG_FANCY", 43, "", 999, "of.options.FOG_FANCY", false, false),
      FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0F, 110.0F, 1.0F);

      private final boolean enumBoolean;
      DYNAMIC_FOV("DYNAMIC_FOV", 104, "", 999, "of.options.DYNAMIC_FOV", false, false),
      CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 19, "CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true);

      private float valueMin;
      CHAT_OPACITY("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false);

      private final boolean enumFloat;
      FAST_RENDER("FAST_RENDER", 102, "", 999, "of.options.FAST_RENDER", false, false);

      private final String enumString;
      CLOUDS("CLOUDS", 47, "", 999, "of.options.CLOUDS", false, false),
      GRAPHICS("GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false),
      RAIN("RAIN", 50, "", 999, "of.options.RAIN", false, false),
      CUSTOM_FONTS("CUSTOM_FONTS", 88, "", 999, "of.options.CUSTOM_FONTS", false, false),
      STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 39, "STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false),
      STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 38, "STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false),
      CHAT_VISIBILITY("CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false),
      RENDER_CLOUDS("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, true),
      SHOW_CAPES("SHOW_CAPES", 90, "", 999, "of.options.SHOW_CAPES", false, false),
      MIPMAP_TYPE("MIPMAP_TYPE", 45, "", 999, "of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
      FULLSCREEN_MODE("FULLSCREEN_MODE", 83, "", 999, "of.options.FULLSCREEN_MODE", false, false),
      CHAT_SCALE("CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false),
      FOG_START("FOG_START", 44, "", 999, "of.options.FOG_START", false, false),
      ANIMATED_LAVA("ANIMATED_LAVA", 52, "", 999, "of.options.ANIMATED_LAVA", false, false),
      INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true),
      ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true),
      CLOUD_HEIGHT("CLOUD_HEIGHT", 48, "", 999, "of.options.CLOUD_HEIGHT", true, false),
      LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 99, "", 999, "of.options.LAZY_CHUNK_LOADING", false, false),
      ANIMATED_SMOKE("ANIMATED_SMOKE", 63, "", 999, "of.options.ANIMATED_SMOKE", false, false),
      CHUNK_UPDATES("CHUNK_UPDATES", 69, "", 999, "of.options.CHUNK_UPDATES", false, false),
      SMOOTH_FPS("SMOOTH_FPS", 46, "", 999, "of.options.SMOOTH_FPS", false, false),
      CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 28, "CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false),
      ANIMATED_FLAME("ANIMATED_FLAME", 62, "", 999, "of.options.ANIMATED_FLAME", false, false),
      SMOOTH_BIOMES("SMOOTH_BIOMES", 87, "", 999, "of.options.SMOOTH_BIOMES", false, false),
      AF_LEVEL("AF_LEVEL", 94, "", 999, "of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
      RANDOM_MOBS("RANDOM_MOBS", 86, "", 999, "of.options.RANDOM_MOBS", false, false),
      FIREWORK_PARTICLES("FIREWORK_PARTICLES", 79, "", 999, "of.options.FIREWORK_PARTICLES", false, false),
      NATURAL_TEXTURES("NATURAL_TEXTURES", 96, "", 999, "of.options.NATURAL_TEXTURES", false, false),
      CLEAR_WATER("CLEAR_WATER", 72, "", 999, "of.options.CLEAR_WATER", false, false),
      REDUCED_DEBUG_INFO("REDUCED_DEBUG_INFO", 42, "REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true),
      SWAMP_COLORS("SWAMP_COLORS", 85, "", 999, "of.options.SWAMP_COLORS", false, false),
      SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false),
      STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 31, "STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false),
      HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 97, "", 999, "of.options.HELD_ITEM_TOOLTIPS", false, false),
      ENABLE_VSYNC("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true),
      WEATHER("WEATHER", 64, "", 999, "of.options.WEATHER", false, false),
      FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
      SHOW_FPS("SHOW_FPS", 57, "", 999, "of.options.SHOW_FPS", false, false),
      GUI_SCALE("GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false),
      ANIMATED_PORTAL("ANIMATED_PORTAL", 54, "", 999, "of.options.ANIMATED_PORTAL", false, false);

      private static final GameSettings.Options[] $VALUES = new GameSettings.Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO};
      VOID_PARTICLES("VOID_PARTICLES", 74, "", 999, "of.options.VOID_PARTICLES", false, false);

      private final float valueStep;
      VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true),
      ANIMATED_WATER("ANIMATED_WATER", 51, "", 999, "of.options.ANIMATED_WATER", false, false),
      ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 61, "", 999, "of.options.ANIMATED_EXPLOSION", false, false),
      TOUCHSCREEN("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true),
      CONNECTED_TEXTURES("CONNECTED_TEXTURES", 91, "", 999, "of.options.CONNECTED_TEXTURES", false, false),
      USE_FULLSCREEN("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true),
      RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
      AO_LEVEL("AO_LEVEL", 55, "", 999, "of.options.AO_LEVEL", true, false);

      private static final String __OBFID = "CL_00000653";
      STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 33, "STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false),
      DROPPED_ITEMS("DROPPED_ITEMS", 98, "", 999, "of.options.DROPPED_ITEMS", false, false),
      CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 70, "", 999, "of.options.CHUNK_UPDATES_DYNAMIC", false, false),
      STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 32, "STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false),
      ANIMATED_FIRE("ANIMATED_FIRE", 53, "", 999, "of.options.ANIMATED_FIRE", false, false),
      LAGOMETER("LAGOMETER", 56, "", 999, "of.options.LAGOMETER", false, false),
      CUSTOM_ITEMS("CUSTOM_ITEMS", 92, "", 999, "of.options.CUSTOM_ITEMS", false, false),
      BETTER_SNOW("BETTER_SNOW", 82, "", 999, "of.options.BETTER_SNOW", false, false),
      USE_VBO("USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true),
      AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false),
      ANIMATED_TEXTURES("ANIMATED_TEXTURES", 95, "", 999, "of.options.ANIMATED_TEXTURES", false, false),
      STARS("STARS", 66, "", 999, "of.options.STARS", false, false),
      FAST_MATH("FAST_MATH", 101, "", 999, "of.options.FAST_MATH", false, false),
      STREAM_FPS("STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false),
      CUSTOM_SKY("CUSTOM_SKY", 100, "", 999, "of.options.CUSTOM_SKY", false, false),
      AUTOSAVE_TICKS("AUTOSAVE_TICKS", 58, "", 999, "of.options.AUTOSAVE_TICKS", false, false),
      STREAM_KBPS("STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false);

      private static final GameSettings.Options[] $VALUES$ = new GameSettings.Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO, FOG_FANCY, FOG_START, MIPMAP_TYPE, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, RAIN, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, SHOW_FPS, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, VIGNETTE, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, FIREWORK_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, CUSTOM_ITEMS, AA_LEVEL, AF_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, FAST_RENDER, TRANSLUCENT_BLOCKS, DYNAMIC_FOV, DYNAMIC_LIGHTS};
      STREAM_COMPRESSION("STREAM_COMPRESSION", 36, "STREAM_COMPRESSION", 36, "options.stream.compression", false, false),
      SKY("SKY", 65, "", 999, "of.options.SKY", false, false),
      RAIN_SPLASH("RAIN_SPLASH", 76, "", 999, "of.options.RAIN_SPLASH", false, false),
      BLOCK_ALTERNATIVES("BLOCK_ALTERNATIVES", 41, "BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true),
      CHAT_COLOR("CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true),
      TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 103, "", 999, "of.options.TRANSLUCENT_BLOCKS", false, false),
      FBO_ENABLE("FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true),
      AA_LEVEL("AA_LEVEL", 93, "", 999, "of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
      GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false);

      private float valueMax;
      SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false),
      FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 30, "FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true),
      PARTICLES("PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false),
      SMOOTH_WORLD("SMOOTH_WORLD", 73, "", 999, "of.options.SMOOTH_WORLD", false, false),
      STREAM_SEND_METADATA("STREAM_SEND_METADATA", 37, "STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true),
      TIME("TIME", 71, "", 999, "of.options.TIME", false, false),
      WATER_PARTICLES("WATER_PARTICLES", 75, "", 999, "of.options.WATER_PARTICLES", false, false);

      public boolean getEnumFloat() {
         return this.enumFloat;
      }

      public int returnEnumOrdinal() {
         return this.ordinal();
      }

      public boolean getEnumBoolean() {
         return this.enumBoolean;
      }

      public String getEnumString() {
         return this.enumString;
      }

      static float access$3(GameSettings.Options var0) {
         return var0.valueMin;
      }

      public static GameSettings.Options getEnumOptions(int var0) {
         GameSettings.Options[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            GameSettings.Options var4 = var1[var3];
            if (var4.returnEnumOrdinal() == var0) {
               return var4;
            }
         }

         return null;
      }

      public float normalizeValue(float var1) {
         return MathHelper.clamp_float((this.snapToStepClamp(var1) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
      }

      protected float snapToStep(float var1) {
         if (this.valueStep > 0.0F) {
            var1 = this.valueStep * (float)Math.round(var1 / this.valueStep);
         }

         return var1;
      }

      public float denormalizeValue(float var1) {
         return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(var1, 0.0F, 1.0F));
      }

      static float access$2(GameSettings.Options var0) {
         return var0.valueMax;
      }

      public void setValueMax(float var1) {
         this.valueMax = var1;
      }

      private Options(String var3, int var4, String var5, int var6, String var7, boolean var8, boolean var9) {
         this(var3, var4, var5, var6, var7, var8, var9, 0.0F, 1.0F, 0.0F);
      }

      public float snapToStepClamp(float var1) {
         var1 = this.snapToStep(var1);
         return MathHelper.clamp_float(var1, this.valueMin, this.valueMax);
      }

      public float getValueMax() {
         return this.valueMax;
      }

      private Options(String var3, int var4, String var5, int var6, String var7, boolean var8, boolean var9, float var10, float var11, float var12) {
         this.enumString = var7;
         this.enumFloat = var8;
         this.enumBoolean = var9;
         this.valueMin = var10;
         this.valueMax = var11;
         this.valueStep = var12;
      }
   }

   static final class SwitchOptions {
      static final int[] optionIds = new int[GameSettings.Options.values().length];
      private static final String __OBFID = "CL_00000652";

      static {
         try {
            optionIds[GameSettings.Options.INVERT_MOUSE.ordinal()] = 1;
         } catch (NoSuchFieldError var17) {
         }

         try {
            optionIds[GameSettings.Options.VIEW_BOBBING.ordinal()] = 2;
         } catch (NoSuchFieldError var16) {
         }

         try {
            optionIds[GameSettings.Options.ANAGLYPH.ordinal()] = 3;
         } catch (NoSuchFieldError var15) {
         }

         try {
            optionIds[GameSettings.Options.FBO_ENABLE.ordinal()] = 4;
         } catch (NoSuchFieldError var14) {
         }

         try {
            optionIds[GameSettings.Options.RENDER_CLOUDS.ordinal()] = 5;
         } catch (NoSuchFieldError var13) {
         }

         try {
            optionIds[GameSettings.Options.CHAT_COLOR.ordinal()] = 6;
         } catch (NoSuchFieldError var12) {
         }

         try {
            optionIds[GameSettings.Options.CHAT_LINKS.ordinal()] = 7;
         } catch (NoSuchFieldError var11) {
         }

         try {
            optionIds[GameSettings.Options.CHAT_LINKS_PROMPT.ordinal()] = 8;
         } catch (NoSuchFieldError var10) {
         }

         try {
            optionIds[GameSettings.Options.SNOOPER_ENABLED.ordinal()] = 9;
         } catch (NoSuchFieldError var9) {
         }

         try {
            optionIds[GameSettings.Options.USE_FULLSCREEN.ordinal()] = 10;
         } catch (NoSuchFieldError var8) {
         }

         try {
            optionIds[GameSettings.Options.ENABLE_VSYNC.ordinal()] = 11;
         } catch (NoSuchFieldError var7) {
         }

         try {
            optionIds[GameSettings.Options.USE_VBO.ordinal()] = 12;
         } catch (NoSuchFieldError var6) {
         }

         try {
            optionIds[GameSettings.Options.TOUCHSCREEN.ordinal()] = 13;
         } catch (NoSuchFieldError var5) {
         }

         try {
            optionIds[GameSettings.Options.STREAM_SEND_METADATA.ordinal()] = 14;
         } catch (NoSuchFieldError var4) {
         }

         try {
            optionIds[GameSettings.Options.FORCE_UNICODE_FONT.ordinal()] = 15;
         } catch (NoSuchFieldError var3) {
         }

         try {
            optionIds[GameSettings.Options.BLOCK_ALTERNATIVES.ordinal()] = 16;
         } catch (NoSuchFieldError var2) {
         }

         try {
            optionIds[GameSettings.Options.REDUCED_DEBUG_INFO.ordinal()] = 17;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
