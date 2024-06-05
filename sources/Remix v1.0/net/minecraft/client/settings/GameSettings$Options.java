package net.minecraft.client.settings;

import net.minecraft.util.*;

public enum Options
{
    INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
    SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
    FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0f, 110.0f, 1.0f), 
    GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
    SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false), 
    RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f), 
    VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
    ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true), 
    FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "FRAMERATE_LIMIT", 8, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f), 
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
    MIPMAP_LEVELS("MIPMAP_LEVELS", 29, "MIPMAP_LEVELS", 29, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f), 
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
    FOG_FANCY("FOG_FANCY", 43, "", 999, "of.options.FOG_FANCY", false, false), 
    FOG_START("FOG_START", 44, "", 999, "of.options.FOG_START", false, false), 
    MIPMAP_TYPE("MIPMAP_TYPE", 45, "", 999, "of.options.MIPMAP_TYPE", true, false, 0.0f, 3.0f, 1.0f), 
    SMOOTH_FPS("SMOOTH_FPS", 46, "", 999, "of.options.SMOOTH_FPS", false, false), 
    CLOUDS("CLOUDS", 47, "", 999, "of.options.CLOUDS", false, false), 
    CLOUD_HEIGHT("CLOUD_HEIGHT", 48, "", 999, "of.options.CLOUD_HEIGHT", true, false), 
    TREES("TREES", 49, "", 999, "of.options.TREES", false, false), 
    RAIN("RAIN", 50, "", 999, "of.options.RAIN", false, false), 
    ANIMATED_WATER("ANIMATED_WATER", 51, "", 999, "of.options.ANIMATED_WATER", false, false), 
    ANIMATED_LAVA("ANIMATED_LAVA", 52, "", 999, "of.options.ANIMATED_LAVA", false, false), 
    ANIMATED_FIRE("ANIMATED_FIRE", 53, "", 999, "of.options.ANIMATED_FIRE", false, false), 
    ANIMATED_PORTAL("ANIMATED_PORTAL", 54, "", 999, "of.options.ANIMATED_PORTAL", false, false), 
    AO_LEVEL("AO_LEVEL", 55, "", 999, "of.options.AO_LEVEL", true, false), 
    LAGOMETER("LAGOMETER", 56, "", 999, "of.options.LAGOMETER", false, false), 
    SHOW_FPS("SHOW_FPS", 57, "", 999, "of.options.SHOW_FPS", false, false), 
    AUTOSAVE_TICKS("AUTOSAVE_TICKS", 58, "", 999, "of.options.AUTOSAVE_TICKS", false, false), 
    BETTER_GRASS("BETTER_GRASS", 59, "", 999, "of.options.BETTER_GRASS", false, false), 
    ANIMATED_REDSTONE("ANIMATED_REDSTONE", 60, "", 999, "of.options.ANIMATED_REDSTONE", false, false), 
    ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 61, "", 999, "of.options.ANIMATED_EXPLOSION", false, false), 
    ANIMATED_FLAME("ANIMATED_FLAME", 62, "", 999, "of.options.ANIMATED_FLAME", false, false), 
    ANIMATED_SMOKE("ANIMATED_SMOKE", 63, "", 999, "of.options.ANIMATED_SMOKE", false, false), 
    WEATHER("WEATHER", 64, "", 999, "of.options.WEATHER", false, false), 
    SKY("SKY", 65, "", 999, "of.options.SKY", false, false), 
    STARS("STARS", 66, "", 999, "of.options.STARS", false, false), 
    SUN_MOON("SUN_MOON", 67, "", 999, "of.options.SUN_MOON", false, false), 
    VIGNETTE("VIGNETTE", 68, "", 999, "of.options.VIGNETTE", false, false), 
    CHUNK_UPDATES("CHUNK_UPDATES", 69, "", 999, "of.options.CHUNK_UPDATES", false, false), 
    CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 70, "", 999, "of.options.CHUNK_UPDATES_DYNAMIC", false, false), 
    TIME("TIME", 71, "", 999, "of.options.TIME", false, false), 
    CLEAR_WATER("CLEAR_WATER", 72, "", 999, "of.options.CLEAR_WATER", false, false), 
    SMOOTH_WORLD("SMOOTH_WORLD", 73, "", 999, "of.options.SMOOTH_WORLD", false, false), 
    VOID_PARTICLES("VOID_PARTICLES", 74, "", 999, "of.options.VOID_PARTICLES", false, false), 
    WATER_PARTICLES("WATER_PARTICLES", 75, "", 999, "of.options.WATER_PARTICLES", false, false), 
    RAIN_SPLASH("RAIN_SPLASH", 76, "", 999, "of.options.RAIN_SPLASH", false, false), 
    PORTAL_PARTICLES("PORTAL_PARTICLES", 77, "", 999, "of.options.PORTAL_PARTICLES", false, false), 
    POTION_PARTICLES("POTION_PARTICLES", 78, "", 999, "of.options.POTION_PARTICLES", false, false), 
    FIREWORK_PARTICLES("FIREWORK_PARTICLES", 79, "", 999, "of.options.FIREWORK_PARTICLES", false, false), 
    PROFILER("PROFILER", 80, "", 999, "of.options.PROFILER", false, false), 
    DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 81, "", 999, "of.options.DRIPPING_WATER_LAVA", false, false), 
    BETTER_SNOW("BETTER_SNOW", 82, "", 999, "of.options.BETTER_SNOW", false, false), 
    FULLSCREEN_MODE("FULLSCREEN_MODE", 83, "", 999, "of.options.FULLSCREEN_MODE", false, false), 
    ANIMATED_TERRAIN("ANIMATED_TERRAIN", 84, "", 999, "of.options.ANIMATED_TERRAIN", false, false), 
    SWAMP_COLORS("SWAMP_COLORS", 85, "", 999, "of.options.SWAMP_COLORS", false, false), 
    RANDOM_MOBS("RANDOM_MOBS", 86, "", 999, "of.options.RANDOM_MOBS", false, false), 
    SMOOTH_BIOMES("SMOOTH_BIOMES", 87, "", 999, "of.options.SMOOTH_BIOMES", false, false), 
    CUSTOM_FONTS("CUSTOM_FONTS", 88, "", 999, "of.options.CUSTOM_FONTS", false, false), 
    CUSTOM_COLORS("CUSTOM_COLORS", 89, "", 999, "of.options.CUSTOM_COLORS", false, false), 
    SHOW_CAPES("SHOW_CAPES", 90, "", 999, "of.options.SHOW_CAPES", false, false), 
    CONNECTED_TEXTURES("CONNECTED_TEXTURES", 91, "", 999, "of.options.CONNECTED_TEXTURES", false, false), 
    CUSTOM_ITEMS("CUSTOM_ITEMS", 92, "", 999, "of.options.CUSTOM_ITEMS", false, false), 
    AA_LEVEL("AA_LEVEL", 93, "", 999, "of.options.AA_LEVEL", true, false, 0.0f, 16.0f, 1.0f), 
    AF_LEVEL("AF_LEVEL", 94, "", 999, "of.options.AF_LEVEL", true, false, 1.0f, 16.0f, 1.0f), 
    ANIMATED_TEXTURES("ANIMATED_TEXTURES", 95, "", 999, "of.options.ANIMATED_TEXTURES", false, false), 
    NATURAL_TEXTURES("NATURAL_TEXTURES", 96, "", 999, "of.options.NATURAL_TEXTURES", false, false), 
    HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 97, "", 999, "of.options.HELD_ITEM_TOOLTIPS", false, false), 
    DROPPED_ITEMS("DROPPED_ITEMS", 98, "", 999, "of.options.DROPPED_ITEMS", false, false), 
    LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 99, "", 999, "of.options.LAZY_CHUNK_LOADING", false, false), 
    CUSTOM_SKY("CUSTOM_SKY", 100, "", 999, "of.options.CUSTOM_SKY", false, false), 
    FAST_MATH("FAST_MATH", 101, "", 999, "of.options.FAST_MATH", false, false), 
    FAST_RENDER("FAST_RENDER", 102, "", 999, "of.options.FAST_RENDER", false, false), 
    TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 103, "", 999, "of.options.TRANSLUCENT_BLOCKS", false, false), 
    DYNAMIC_FOV("DYNAMIC_FOV", 104, "", 999, "of.options.DYNAMIC_FOV", false, false), 
    DYNAMIC_LIGHTS("DYNAMIC_LIGHTS", 105, "", 999, "of.options.DYNAMIC_LIGHTS", false, false);
    
    private static final Options[] $VALUES;
    private static final Options[] $VALUES$;
    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final String enumString;
    private final float valueStep;
    private float valueMin;
    private float valueMax;
    
    private Options(final String p_i46375_1_, final int p_i46375_2_, final String p_i1015_1_, final int p_i1015_2_, final String p_i1015_3_, final boolean p_i1015_4_, final boolean p_i1015_5_) {
        this(p_i46375_1_, p_i46375_2_, p_i1015_1_, p_i1015_2_, p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0f, 1.0f, 0.0f);
    }
    
    private Options(final String p_i46376_1_, final int p_i46376_2_, final String p_i45004_1_, final int p_i45004_2_, final String p_i45004_3_, final boolean p_i45004_4_, final boolean p_i45004_5_, final float p_i45004_6_, final float p_i45004_7_, final float p_i45004_8_) {
        this.enumString = p_i45004_3_;
        this.enumFloat = p_i45004_4_;
        this.enumBoolean = p_i45004_5_;
        this.valueMin = p_i45004_6_;
        this.valueMax = p_i45004_7_;
        this.valueStep = p_i45004_8_;
    }
    
    public static Options getEnumOptions(final int p_74379_0_) {
        for (final Options var4 : values()) {
            if (var4.returnEnumOrdinal() == p_74379_0_) {
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
    
    public void setValueMax(final float p_148263_1_) {
        this.valueMax = p_148263_1_;
    }
    
    public float normalizeValue(final float p_148266_1_) {
        return MathHelper.clamp_float((this.snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
    }
    
    public float denormalizeValue(final float p_148262_1_) {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0f, 1.0f));
    }
    
    public float snapToStepClamp(float p_148268_1_) {
        p_148268_1_ = this.snapToStep(p_148268_1_);
        return MathHelper.clamp_float(p_148268_1_, this.valueMin, this.valueMax);
    }
    
    protected float snapToStep(float p_148264_1_) {
        if (this.valueStep > 0.0f) {
            p_148264_1_ = this.valueStep * Math.round(p_148264_1_ / this.valueStep);
        }
        return p_148264_1_;
    }
    
    static {
        $VALUES = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO };
        $VALUES$ = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ANAGLYPH, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.RENDER_CLOUDS, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.USE_VBO, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.BLOCK_ALTERNATIVES, Options.REDUCED_DEBUG_INFO, Options.FOG_FANCY, Options.FOG_START, Options.MIPMAP_TYPE, Options.SMOOTH_FPS, Options.CLOUDS, Options.CLOUD_HEIGHT, Options.TREES, Options.RAIN, Options.ANIMATED_WATER, Options.ANIMATED_LAVA, Options.ANIMATED_FIRE, Options.ANIMATED_PORTAL, Options.AO_LEVEL, Options.LAGOMETER, Options.SHOW_FPS, Options.AUTOSAVE_TICKS, Options.BETTER_GRASS, Options.ANIMATED_REDSTONE, Options.ANIMATED_EXPLOSION, Options.ANIMATED_FLAME, Options.ANIMATED_SMOKE, Options.WEATHER, Options.SKY, Options.STARS, Options.SUN_MOON, Options.VIGNETTE, Options.CHUNK_UPDATES, Options.CHUNK_UPDATES_DYNAMIC, Options.TIME, Options.CLEAR_WATER, Options.SMOOTH_WORLD, Options.VOID_PARTICLES, Options.WATER_PARTICLES, Options.RAIN_SPLASH, Options.PORTAL_PARTICLES, Options.POTION_PARTICLES, Options.FIREWORK_PARTICLES, Options.PROFILER, Options.DRIPPING_WATER_LAVA, Options.BETTER_SNOW, Options.FULLSCREEN_MODE, Options.ANIMATED_TERRAIN, Options.SWAMP_COLORS, Options.RANDOM_MOBS, Options.SMOOTH_BIOMES, Options.CUSTOM_FONTS, Options.CUSTOM_COLORS, Options.SHOW_CAPES, Options.CONNECTED_TEXTURES, Options.CUSTOM_ITEMS, Options.AA_LEVEL, Options.AF_LEVEL, Options.ANIMATED_TEXTURES, Options.NATURAL_TEXTURES, Options.HELD_ITEM_TOOLTIPS, Options.DROPPED_ITEMS, Options.LAZY_CHUNK_LOADING, Options.CUSTOM_SKY, Options.FAST_MATH, Options.FAST_RENDER, Options.TRANSLUCENT_BLOCKS, Options.DYNAMIC_FOV, Options.DYNAMIC_LIGHTS };
    }
}
