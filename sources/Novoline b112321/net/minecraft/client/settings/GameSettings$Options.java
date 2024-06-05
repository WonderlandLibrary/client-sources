package net.minecraft.client.settings;

import net.minecraft.util.MathHelper;

public enum GameSettings$Options {
   INVERT_MOUSE("INVERT_MOUSE", 0, "options.invertMouse", false, true),
   SENSITIVITY("SENSITIVITY", 1, "options.sensitivity", true, false),
   FOV("FOV", 2, "options.fov", true, false, 30.0F, 110.0F, 1.0F),
   GAMMA("GAMMA", 3, "options.gamma", true, false),
   SATURATION("SATURATION", 4, "options.saturation", true, false),
   RENDER_DISTANCE("RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
   VIEW_BOBBING("VIEW_BOBBING", 6, "options.viewBobbing", false, true),
   ANAGLYPH("ANAGLYPH", 7, "options.anaglyph", false, true),
   FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
   FBO_ENABLE("FBO_ENABLE", 9, "options.fboEnable", false, true),
   RENDER_CLOUDS("RENDER_CLOUDS", 10, "options.renderClouds", false, false),
   GRAPHICS("GRAPHICS", 11, "options.graphics", false, false),
   AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 12, "options.ao", false, false),
   GUI_SCALE("GUI_SCALE", 13, "options.guiScale", false, false),
   PARTICLES("PARTICLES", 14, "options.particles", false, false),
   CHAT_VISIBILITY("CHAT_VISIBILITY", 15, "options.chat.visibility", false, false),
   CHAT_COLOR("CHAT_COLOR", 16, "options.chat.color", false, true),
   CHAT_LINKS("CHAT_LINKS", 17, "options.chat.links", false, true),
   CHAT_OPACITY("CHAT_OPACITY", 18, "options.chat.opacity", true, false),
   CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 19, "options.chat.links.prompt", false, true),
   SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "options.snooper", false, true),
   USE_FULLSCREEN("USE_FULLSCREEN", 21, "options.fullscreen", false, true),
   ENABLE_VSYNC("ENABLE_VSYNC", 22, "options.vsync", false, true),
   USE_VBO("USE_VBO", 23, "options.vbo", false, true),
   TOUCHSCREEN("TOUCHSCREEN", 24, "options.touchscreen", false, true),
   CHAT_SCALE("CHAT_SCALE", 25, "options.chat.scale", true, false),
   CHAT_WIDTH("CHAT_WIDTH", 26, "options.chat.width", true, false),
   CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 27, "options.chat.height.focused", true, false),
   CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 28, "options.chat.height.unfocused", true, false),
   MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
   FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 30, "options.forceUnicodeFont", false, true),
   STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 31, "options.stream.bytesPerPixel", true, false),
   STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 32, "options.stream.micVolumne", true, false),
   STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 33, "options.stream.systemVolume", true, false),
   STREAM_KBPS("STREAM_KBPS", 34, "options.stream.kbps", true, false),
   STREAM_FPS("STREAM_FPS", 35, "options.stream.fps", true, false),
   STREAM_COMPRESSION("STREAM_COMPRESSION", 36, "options.stream.compression", false, false),
   STREAM_SEND_METADATA("STREAM_SEND_METADATA", 37, "options.stream.sendMetadata", false, true),
   STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 38, "options.stream.chat.enabled", false, false),
   STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 39, "options.stream.chat.userFilter", false, false),
   STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 40, "options.stream.micToggleBehavior", false, false),
   BLOCK_ALTERNATIVES("BLOCK_ALTERNATIVES", 41, "options.blockAlternatives", false, true),
   REDUCED_DEBUG_INFO("REDUCED_DEBUG_INFO", 42, "options.reducedDebugInfo", false, true),
   ENTITY_SHADOWS("ENTITY_SHADOWS", 43, "options.entityShadows", false, true),
   FOG_FANCY("", 999, "of.options.FOG_FANCY", false, false),
   FOG_START("", 999, "of.options.FOG_START", false, false),
   MIPMAP_TYPE("", 999, "of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
   SMOOTH_FPS("", 999, "of.options.SMOOTH_FPS", false, false),
   CLOUDS("", 999, "of.options.CLOUDS", false, false),
   CLOUD_HEIGHT("", 999, "of.options.CLOUD_HEIGHT", true, false),
   TREES("", 999, "of.options.TREES", false, false),
   RAIN("", 999, "of.options.RAIN", false, false),
   ANIMATED_WATER("", 999, "of.options.ANIMATED_WATER", false, false),
   ANIMATED_LAVA("", 999, "of.options.ANIMATED_LAVA", false, false),
   ANIMATED_FIRE("", 999, "of.options.ANIMATED_FIRE", false, false),
   ANIMATED_PORTAL("", 999, "of.options.ANIMATED_PORTAL", false, false),
   AO_LEVEL("", 999, "of.options.AO_LEVEL", true, false),
   LAGOMETER("", 999, "of.options.LAGOMETER", false, false),
   SHOW_FPS("", 999, "of.options.SHOW_FPS", false, false),
   AUTOSAVE_TICKS("", 999, "of.options.AUTOSAVE_TICKS", false, false),
   BETTER_GRASS("", 999, "of.options.BETTER_GRASS", false, false),
   ANIMATED_REDSTONE("", 999, "of.options.ANIMATED_REDSTONE", false, false),
   ANIMATED_EXPLOSION("", 999, "of.options.ANIMATED_EXPLOSION", false, false),
   ANIMATED_FLAME("", 999, "of.options.ANIMATED_FLAME", false, false),
   ANIMATED_SMOKE("", 999, "of.options.ANIMATED_SMOKE", false, false),
   WEATHER("", 999, "of.options.WEATHER", false, false),
   SKY("", 999, "of.options.SKY", false, false),
   STARS("", 999, "of.options.STARS", false, false),
   SUN_MOON("", 999, "of.options.SUN_MOON", false, false),
   VIGNETTE("", 999, "of.options.VIGNETTE", false, false),
   CHUNK_UPDATES("", 999, "of.options.CHUNK_UPDATES", false, false),
   CHUNK_UPDATES_DYNAMIC("", 999, "of.options.CHUNK_UPDATES_DYNAMIC", false, false),
   TIME("", 999, "of.options.TIME", false, false),
   CLEAR_WATER("", 999, "of.options.CLEAR_WATER", false, false),
   SMOOTH_WORLD("", 999, "of.options.SMOOTH_WORLD", false, false),
   VOID_PARTICLES("", 999, "of.options.VOID_PARTICLES", false, false),
   WATER_PARTICLES("", 999, "of.options.WATER_PARTICLES", false, false),
   RAIN_SPLASH("", 999, "of.options.RAIN_SPLASH", false, false),
   PORTAL_PARTICLES("", 999, "of.options.PORTAL_PARTICLES", false, false),
   POTION_PARTICLES("", 999, "of.options.POTION_PARTICLES", false, false),
   FIREWORK_PARTICLES("", 999, "of.options.FIREWORK_PARTICLES", false, false),
   PROFILER("", 999, "of.options.PROFILER", false, false),
   DRIPPING_WATER_LAVA("", 999, "of.options.DRIPPING_WATER_LAVA", false, false),
   BETTER_SNOW("", 999, "of.options.BETTER_SNOW", false, false),
   FULLSCREEN_MODE("", 999, "of.options.FULLSCREEN_MODE", false, false),
   ANIMATED_TERRAIN("", 999, "of.options.ANIMATED_TERRAIN", false, false),
   SWAMP_COLORS("", 999, "of.options.SWAMP_COLORS", false, false),
   RANDOM_MOBS("", 999, "of.options.RANDOM_MOBS", false, false),
   SMOOTH_BIOMES("", 999, "of.options.SMOOTH_BIOMES", false, false),
   CUSTOM_FONTS("", 999, "of.options.CUSTOM_FONTS", false, false),
   CUSTOM_COLORS("", 999, "of.options.CUSTOM_COLORS", false, false),
   SHOW_CAPES("", 999, "of.options.SHOW_CAPES", false, false),
   CONNECTED_TEXTURES("", 999, "of.options.CONNECTED_TEXTURES", false, false),
   CUSTOM_ITEMS("", 999, "of.options.CUSTOM_ITEMS", false, false),
   AA_LEVEL("", 999, "of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
   AF_LEVEL("", 999, "of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
   ANIMATED_TEXTURES("", 999, "of.options.ANIMATED_TEXTURES", false, false),
   NATURAL_TEXTURES("", 999, "of.options.NATURAL_TEXTURES", false, false),
   HELD_ITEM_TOOLTIPS("", 999, "of.options.HELD_ITEM_TOOLTIPS", false, false),
   DROPPED_ITEMS("", 999, "of.options.DROPPED_ITEMS", false, false),
   LAZY_CHUNK_LOADING("", 999, "of.options.LAZY_CHUNK_LOADING", false, false),
   CUSTOM_SKY("", 999, "of.options.CUSTOM_SKY", false, false),
   FAST_MATH("", 999, "of.options.FAST_MATH", false, false),
   TRANSLUCENT_BLOCKS("", 999, "of.options.TRANSLUCENT_BLOCKS", false, false),
   DYNAMIC_FOV("", 999, "of.options.DYNAMIC_FOV", false, false),
   DYNAMIC_LIGHTS("", 999, "of.options.DYNAMIC_LIGHTS", false, false);

   private static final GameSettings$Options[] $VALUES = new GameSettings$Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO, ENTITY_SHADOWS};
   private static final String f = "CL_00000653";
   private final boolean enumFloat;
   private final boolean enumBoolean;
   private final String enumString;
   private final float valueStep;
   private final float valueMin;
   private float valueMax;
   private static final GameSettings$Options[] $VALUES$ = new GameSettings$Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, FRAMERATE_LIMIT, FBO_ENABLE, RENDER_CLOUDS, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, USE_VBO, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, FORCE_UNICODE_FONT, STREAM_BYTES_PER_PIXEL, STREAM_VOLUME_MIC, STREAM_VOLUME_SYSTEM, STREAM_KBPS, STREAM_FPS, STREAM_COMPRESSION, STREAM_SEND_METADATA, STREAM_CHAT_ENABLED, STREAM_CHAT_USER_FILTER, STREAM_MIC_TOGGLE_BEHAVIOR, BLOCK_ALTERNATIVES, REDUCED_DEBUG_INFO, ENTITY_SHADOWS, FOG_FANCY, FOG_START, MIPMAP_TYPE, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, RAIN, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, SHOW_FPS, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, VIGNETTE, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, FIREWORK_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, CUSTOM_ITEMS, AA_LEVEL, AF_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, TRANSLUCENT_BLOCKS, DYNAMIC_FOV, DYNAMIC_LIGHTS};

   private GameSettings$Options(String var3, int var4, String var5, boolean var6, boolean var7) {
      this(var3, var4, var5, var6, var7, 0.0F, 1.0F, 0.0F);
   }

   private GameSettings$Options(String var3, int var4, String var5, boolean var6, boolean var7, float var8, float var9, float var10) {
      this.enumString = var5;
      this.enumFloat = var6;
      this.enumBoolean = var7;
      this.valueMin = var8;
      this.valueMax = var9;
      this.valueStep = var10;
   }

   public static GameSettings$Options getEnumOptions(int var0) {
      for(GameSettings$Options var4 : values()) {
         if(var4.returnEnumOrdinal() == var0) {
            return var4;
         }
      }

      return null;
   }

   public boolean getEnumFloat() {
      return this.enumFloat;
   }

   public boolean getEnumBoolean() {
      return this.enumBoolean;
   }

   public int returnEnumOrdinal() {
      return this.ordinal();
   }

   public String getEnumString() {
      return this.enumString;
   }

   public float getValueMax() {
      return this.valueMax;
   }

   public void setValueMax(float var1) {
      this.valueMax = var1;
   }

   public float normalizeValue(float var1) {
      return MathHelper.clamp_float((this.snapToStepClamp(var1) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
   }

   public float denormalizeValue(float var1) {
      return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(var1, 0.0F, 1.0F));
   }

   public float snapToStepClamp(float var1) {
      var1 = this.snapToStep(var1);
      return MathHelper.clamp_float(var1, this.valueMin, this.valueMax);
   }

   protected float snapToStep(float var1) {
      if(this.valueStep > 0.0F) {
         var1 = this.valueStep * (float)Math.round(var1 / this.valueStep);
      }

      return var1;
   }

   static float access$000(GameSettings$Options var0) {
      return var0.valueMax;
   }

   static float access$100(GameSettings$Options var0) {
      return var0.valueMin;
   }
}
