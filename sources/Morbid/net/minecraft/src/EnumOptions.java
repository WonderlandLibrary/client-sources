package net.minecraft.src;

public enum EnumOptions
{
    MUSIC("MUSIC", 0, "options.music", true, false), 
    SOUND("SOUND", 1, "options.sound", true, false), 
    INVERT_MOUSE("INVERT_MOUSE", 2, "options.invertMouse", false, true), 
    SENSITIVITY("SENSITIVITY", 3, "options.sensitivity", true, false), 
    FOV("FOV", 4, "options.fov", true, false), 
    GAMMA("GAMMA", 5, "options.gamma", true, false), 
    RENDER_DISTANCE("RENDER_DISTANCE", 6, "options.renderDistance", false, false), 
    VIEW_BOBBING("VIEW_BOBBING", 7, "options.viewBobbing", false, true), 
    ANAGLYPH("ANAGLYPH", 8, "options.anaglyph", false, true), 
    ADVANCED_OPENGL("ADVANCED_OPENGL", 9, "options.advancedOpengl", false, true), 
    FRAMERATE_LIMIT("FRAMERATE_LIMIT", 10, "options.framerateLimit", false, false), 
    DIFFICULTY("DIFFICULTY", 11, "options.difficulty", false, false), 
    GRAPHICS("GRAPHICS", 12, "options.graphics", false, false), 
    AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 13, "options.ao", false, false), 
    GUI_SCALE("GUI_SCALE", 14, "options.guiScale", false, false), 
    RENDER_CLOUDS("RENDER_CLOUDS", 15, "options.renderClouds", false, true), 
    PARTICLES("PARTICLES", 16, "options.particles", false, false), 
    CHAT_VISIBILITY("CHAT_VISIBILITY", 17, "options.chat.visibility", false, false), 
    CHAT_COLOR("CHAT_COLOR", 18, "options.chat.color", false, true), 
    CHAT_LINKS("CHAT_LINKS", 19, "options.chat.links", false, true), 
    CHAT_OPACITY("CHAT_OPACITY", 20, "options.chat.opacity", true, false), 
    CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 21, "options.chat.links.prompt", false, true), 
    USE_SERVER_TEXTURES("USE_SERVER_TEXTURES", 22, "options.serverTextures", false, true), 
    SNOOPER_ENABLED("SNOOPER_ENABLED", 23, "options.snooper", false, true), 
    USE_FULLSCREEN("USE_FULLSCREEN", 24, "options.fullscreen", false, true), 
    ENABLE_VSYNC("ENABLE_VSYNC", 25, "options.vsync", false, true), 
    SHOW_CAPE("SHOW_CAPE", 26, "options.showCape", false, true), 
    TOUCHSCREEN("TOUCHSCREEN", 27, "options.touchscreen", false, true), 
    CHAT_SCALE("CHAT_SCALE", 28, "options.chat.scale", true, false), 
    CHAT_WIDTH("CHAT_WIDTH", 29, "options.chat.width", true, false), 
    CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 30, "options.chat.height.focused", true, false), 
    CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 31, "options.chat.height.unfocused", true, false), 
    FOG_FANCY("FOG_FANCY", 32, "Fog", false, false), 
    FOG_START("FOG_START", 33, "Fog Start", false, false), 
    MIPMAP_LEVEL("MIPMAP_LEVEL", 34, "Mipmap Level", false, false), 
    MIPMAP_TYPE("MIPMAP_TYPE", 35, "Mipmap Type", false, false), 
    LOAD_FAR("LOAD_FAR", 36, "Load Far", false, false), 
    PRELOADED_CHUNKS("PRELOADED_CHUNKS", 37, "Preloaded Chunks", false, false), 
    SMOOTH_FPS("SMOOTH_FPS", 38, "Smooth FPS", false, false), 
    CLOUDS("CLOUDS", 39, "Clouds", false, false), 
    CLOUD_HEIGHT("CLOUD_HEIGHT", 40, "Cloud Height", true, false), 
    TREES("TREES", 41, "Trees", false, false), 
    GRASS("GRASS", 42, "Grass", false, false), 
    RAIN("RAIN", 43, "Rain & Snow", false, false), 
    WATER("WATER", 44, "Water", false, false), 
    ANIMATED_WATER("ANIMATED_WATER", 45, "Water Animated", false, false), 
    ANIMATED_LAVA("ANIMATED_LAVA", 46, "Lava Animated", false, false), 
    ANIMATED_FIRE("ANIMATED_FIRE", 47, "Fire Animated", false, false), 
    ANIMATED_PORTAL("ANIMATED_PORTAL", 48, "Portal Animated", false, false), 
    AO_LEVEL("AO_LEVEL", 49, "Smooth Lighting Level", true, false), 
    LAGOMETER("LAGOMETER", 50, "Lagometer", false, false), 
    AUTOSAVE_TICKS("AUTOSAVE_TICKS", 51, "Autosave", false, false), 
    BETTER_GRASS("BETTER_GRASS", 52, "Better Grass", false, false), 
    ANIMATED_REDSTONE("ANIMATED_REDSTONE", 53, "Redstone Animated", false, false), 
    ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 54, "Explosion Animated", false, false), 
    ANIMATED_FLAME("ANIMATED_FLAME", 55, "Flame Animated", false, false), 
    ANIMATED_SMOKE("ANIMATED_SMOKE", 56, "Smoke Animated", false, false), 
    WEATHER("WEATHER", 57, "Weather", false, false), 
    SKY("SKY", 58, "Sky", false, false), 
    STARS("STARS", 59, "Stars", false, false), 
    SUN_MOON("SUN_MOON", 60, "Sun & Moon", false, false), 
    CHUNK_UPDATES("CHUNK_UPDATES", 61, "Chunk Updates per Frame", false, false), 
    CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 62, "Dynamic Updates", false, false), 
    TIME("TIME", 63, "Time", false, false), 
    CLEAR_WATER("CLEAR_WATER", 64, "Clear Water", false, false), 
    SMOOTH_WORLD("SMOOTH_WORLD", 65, "Smooth World", false, false), 
    DEPTH_FOG("DEPTH_FOG", 66, "Depth Fog", false, false), 
    VOID_PARTICLES("VOID_PARTICLES", 67, "Void Particles", false, false), 
    WATER_PARTICLES("WATER_PARTICLES", 68, "Water Particles", false, false), 
    RAIN_SPLASH("RAIN_SPLASH", 69, "Rain Splash", false, false), 
    PORTAL_PARTICLES("PORTAL_PARTICLES", 70, "Portal Particles", false, false), 
    POTION_PARTICLES("POTION_PARTICLES", 71, "Potion Particles", false, false), 
    PROFILER("PROFILER", 72, "Debug Profiler", false, false), 
    DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 73, "Dripping Water/Lava", false, false), 
    BETTER_SNOW("BETTER_SNOW", 74, "Better Snow", false, false), 
    FULLSCREEN_MODE("FULLSCREEN_MODE", 75, "Fullscreen Mode", false, false), 
    ANIMATED_TERRAIN("ANIMATED_TERRAIN", 76, "Terrain Animated", false, false), 
    ANIMATED_ITEMS("ANIMATED_ITEMS", 77, "Items Animated", false, false), 
    SWAMP_COLORS("SWAMP_COLORS", 78, "Swamp Colors", false, false), 
    RANDOM_MOBS("RANDOM_MOBS", 79, "Random Mobs", false, false), 
    SMOOTH_BIOMES("SMOOTH_BIOMES", 80, "Smooth Biomes", false, false), 
    CUSTOM_FONTS("CUSTOM_FONTS", 81, "Custom Fonts", false, false), 
    CUSTOM_COLORS("CUSTOM_COLORS", 82, "Custom Colors", false, false), 
    SHOW_CAPES("SHOW_CAPES", 83, "Show Capes", false, false), 
    CONNECTED_TEXTURES("CONNECTED_TEXTURES", 84, "Connected Textures", false, false), 
    AA_LEVEL("AA_LEVEL", 85, "Antialiasing", false, false), 
    AF_LEVEL("AF_LEVEL", 86, "Anisotropic Filtering", false, false), 
    RENDER_DISTANCE_FINE("RENDER_DISTANCE_FINE", 87, "Render Distance", true, false), 
    ANIMATED_TEXTURES("ANIMATED_TEXTURES", 88, "Textures Animated", false, false), 
    NATURAL_TEXTURES("NATURAL_TEXTURES", 89, "Natural Textures", false, false), 
    CHUNK_LOADING("CHUNK_LOADING", 90, "Chunk Loading", false, false), 
    FRAMERATE_LIMIT_FINE("FRAMERATE_LIMIT_FINE", 91, "Performance", true, false), 
    HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 92, "Held Item Tooltips", false, false), 
    DROPPED_ITEMS("DROPPED_ITEMS", 93, "Dropped Items", false, false), 
    LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 94, "Lazy Chunk Loading", false, false), 
    CUSTOM_SKY("CUSTOM_SKY", 95, "Custom Sky", false, false);
    
    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final String enumString;
    
    public static EnumOptions getEnumOptions(final int par0) {
        for (final EnumOptions var4 : values()) {
            if (var4.returnEnumOrdinal() == par0) {
                return var4;
            }
        }
        return null;
    }
    
    private EnumOptions(final String s, final int n, final String par3Str, final boolean par4, final boolean par5) {
        this.enumString = par3Str;
        this.enumFloat = par4;
        this.enumBoolean = par5;
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
}
