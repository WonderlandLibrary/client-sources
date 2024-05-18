package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.io.*;

public class GameSettings
{
    private static final String[] RENDER_DISTANCES;
    private static final String[] DIFFICULTIES;
    private static final String[] GUISCALES;
    private static final String[] CHAT_VISIBILITIES;
    private static final String[] PARTICLES;
    private static final String[] LIMIT_FRAMERATES;
    private static final String[] AMBIENT_OCCLUSIONS;
    public float musicVolume;
    public float soundVolume;
    public float mouseSensitivity;
    public boolean invertMouse;
    public int renderDistance;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean advancedOpengl;
    public int limitFramerate;
    public boolean fancyGraphics;
    public int ambientOcclusion;
    public boolean clouds;
    public int ofRenderDistanceFine;
    public int ofLimitFramerateFine;
    public int ofFogType;
    public float ofFogStart;
    public int ofMipmapLevel;
    public int ofMipmapType;
    public boolean ofLoadFar;
    public int ofPreloadedChunks;
    public boolean ofOcclusionFancy;
    public boolean ofSmoothFps;
    public boolean ofSmoothWorld;
    public boolean ofLazyChunkLoading;
    public float ofAoLevel;
    public int ofAaLevel;
    public int ofAfLevel;
    public int ofClouds;
    public float ofCloudsHeight;
    public int ofTrees;
    public int ofGrass;
    public int ofRain;
    public int ofWater;
    public int ofDroppedItems;
    public int ofBetterGrass;
    public int ofAutoSaveTicks;
    public boolean ofLagometer;
    public boolean ofProfiler;
    public boolean ofWeather;
    public boolean ofSky;
    public boolean ofStars;
    public boolean ofSunMoon;
    public int ofChunkUpdates;
    public int ofChunkLoading;
    public boolean ofChunkUpdatesDynamic;
    public int ofTime;
    public boolean ofClearWater;
    public boolean ofDepthFog;
    public boolean ofBetterSnow;
    public String ofFullscreenMode;
    public boolean ofSwampColors;
    public boolean ofRandomMobs;
    public boolean ofSmoothBiomes;
    public boolean ofCustomFonts;
    public boolean ofCustomColors;
    public boolean ofCustomSky;
    public boolean ofShowCapes;
    public int ofConnectedTextures;
    public boolean ofNaturalTextures;
    public int ofAnimatedWater;
    public int ofAnimatedLava;
    public boolean ofAnimatedFire;
    public boolean ofAnimatedPortal;
    public boolean ofAnimatedRedstone;
    public boolean ofAnimatedExplosion;
    public boolean ofAnimatedFlame;
    public boolean ofAnimatedSmoke;
    public boolean ofVoidParticles;
    public boolean ofWaterParticles;
    public boolean ofRainSplash;
    public boolean ofPortalParticles;
    public boolean ofPotionParticles;
    public boolean ofDrippingWaterLava;
    public boolean ofAnimatedTerrain;
    public boolean ofAnimatedItems;
    public boolean ofAnimatedTextures;
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
    public String skin;
    public int chatVisibility;
    public boolean chatColours;
    public boolean chatLinks;
    public boolean chatLinksPrompt;
    public float chatOpacity;
    public boolean serverTextures;
    public boolean snooperEnabled;
    public boolean fullScreen;
    public boolean enableVsync;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus;
    public boolean showCape;
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips;
    public float chatScale;
    public float chatWidth;
    public float chatHeightUnfocused;
    public float chatHeightFocused;
    public KeyBinding keyBindForward;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindInventory;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindChat;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindAttack;
    public KeyBinding keyBindUseItem;
    public KeyBinding keyBindPlayerList;
    public KeyBinding keyBindPickBlock;
    public KeyBinding keyBindCommand;
    public KeyBinding[] keyBindings;
    protected Minecraft mc;
    private File optionsFile;
    public int difficulty;
    public boolean hideGUI;
    public int thirdPersonView;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public String lastServer;
    public boolean noclip;
    public boolean smoothCamera;
    public boolean debugCamEnable;
    public float noclipRate;
    public float debugCamRate;
    public float fovSetting;
    public float gammaSetting;
    public int guiScale;
    public int particleSetting;
    public String language;
    private File optionsFileOF;
    
    static {
        RENDER_DISTANCES = new String[] { "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny" };
        DIFFICULTIES = new String[] { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };
        GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
        CHAT_VISIBILITIES = new String[] { "options.chat.visibility.full", "options.chat.visibility.system", "options.chat.visibility.hidden" };
        PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
        LIMIT_FRAMERATES = new String[] { "performance.max", "performance.balanced", "performance.powersaver" };
        AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
    }
    
    public GameSettings(final Minecraft par1Minecraft, final File par2File) {
        this.musicVolume = 1.0f;
        this.soundVolume = 1.0f;
        this.mouseSensitivity = 0.5f;
        this.invertMouse = false;
        this.renderDistance = 0;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = 1;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.ofRenderDistanceFine = 128;
        this.ofLimitFramerateFine = 0;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapLevel = 0;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofPreloadedChunks = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
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
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.skin = "Default";
        this.chatVisibility = 0;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.serverTextures = true;
        this.snooperEnabled = true;
        this.fullScreen = false;
        this.enableVsync = true;
        this.hideServerAddress = false;
        this.advancedItemTooltips = false;
        this.pauseOnLostFocus = true;
        this.showCape = true;
        this.touchscreen = false;
        this.overrideWidth = 0;
        this.overrideHeight = 0;
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.keyBindForward = new KeyBinding("key.forward", 17);
        this.keyBindLeft = new KeyBinding("key.left", 30);
        this.keyBindBack = new KeyBinding("key.back", 31);
        this.keyBindRight = new KeyBinding("key.right", 32);
        this.keyBindJump = new KeyBinding("key.jump", 57);
        this.keyBindInventory = new KeyBinding("key.inventory", 18);
        this.keyBindDrop = new KeyBinding("key.drop", 16);
        this.keyBindChat = new KeyBinding("key.chat", 20);
        this.keyBindSneak = new KeyBinding("key.sneak", 42);
        this.keyBindAttack = new KeyBinding("key.attack", -100);
        this.keyBindUseItem = new KeyBinding("key.use", -99);
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15);
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98);
        this.keyBindCommand = new KeyBinding("key.command", 53);
        this.renderDistance = 1;
        this.limitFramerate = 0;
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
        this.keyBindings = new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.ofKeyBindZoom, this.keyBindCommand };
        this.difficulty = 2;
        this.hideGUI = false;
        this.thirdPersonView = 0;
        this.showDebugInfo = false;
        this.showDebugProfilerChart = false;
        this.lastServer = "";
        this.noclip = false;
        this.smoothCamera = false;
        this.debugCamEnable = false;
        this.noclipRate = 1.0f;
        this.debugCamRate = 1.0f;
        this.fovSetting = 0.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.language = "en_US";
        this.mc = par1Minecraft;
        this.optionsFile = new File(par2File, "options.txt");
        this.optionsFileOF = new File(par2File, "optionsof.txt");
        this.loadOptions();
        Config.setGameSettings(this);
    }
    
    public GameSettings() {
        this.musicVolume = 1.0f;
        this.soundVolume = 1.0f;
        this.mouseSensitivity = 0.5f;
        this.invertMouse = false;
        this.renderDistance = 0;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = 1;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.ofRenderDistanceFine = 128;
        this.ofLimitFramerateFine = 0;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapLevel = 0;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofPreloadedChunks = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
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
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.skin = "Default";
        this.chatVisibility = 0;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.serverTextures = true;
        this.snooperEnabled = true;
        this.fullScreen = false;
        this.enableVsync = true;
        this.hideServerAddress = false;
        this.advancedItemTooltips = false;
        this.pauseOnLostFocus = true;
        this.showCape = true;
        this.touchscreen = false;
        this.overrideWidth = 0;
        this.overrideHeight = 0;
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.keyBindForward = new KeyBinding("key.forward", 17);
        this.keyBindLeft = new KeyBinding("key.left", 30);
        this.keyBindBack = new KeyBinding("key.back", 31);
        this.keyBindRight = new KeyBinding("key.right", 32);
        this.keyBindJump = new KeyBinding("key.jump", 57);
        this.keyBindInventory = new KeyBinding("key.inventory", 18);
        this.keyBindDrop = new KeyBinding("key.drop", 16);
        this.keyBindChat = new KeyBinding("key.chat", 20);
        this.keyBindSneak = new KeyBinding("key.sneak", 42);
        this.keyBindAttack = new KeyBinding("key.attack", -100);
        this.keyBindUseItem = new KeyBinding("key.use", -99);
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15);
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98);
        this.keyBindCommand = new KeyBinding("key.command", 53);
        this.renderDistance = 1;
        this.limitFramerate = 0;
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
        this.keyBindings = new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.ofKeyBindZoom, this.keyBindCommand };
        this.difficulty = 2;
        this.hideGUI = false;
        this.thirdPersonView = 0;
        this.showDebugInfo = false;
        this.showDebugProfilerChart = false;
        this.lastServer = "";
        this.noclip = false;
        this.smoothCamera = false;
        this.debugCamEnable = false;
        this.noclipRate = 1.0f;
        this.debugCamRate = 1.0f;
        this.fovSetting = 0.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.language = "en_US";
    }
    
    public String getKeyBindingDescription(final int par1) {
        final StringTranslate var2 = StringTranslate.getInstance();
        return var2.translateKey(this.keyBindings[par1].keyDescription);
    }
    
    public String getOptionDisplayString(final int par1) {
        final int var2 = this.keyBindings[par1].keyCode;
        return getKeyDisplayString(var2);
    }
    
    public static String getKeyDisplayString(final int par0) {
        return (par0 < 0) ? StatCollector.translateToLocalFormatted("key.mouseButton", par0 + 101) : Keyboard.getKeyName(par0);
    }
    
    public static boolean isKeyDown(final KeyBinding par0KeyBinding) {
        return (par0KeyBinding.keyCode < 0) ? Mouse.isButtonDown(par0KeyBinding.keyCode + 100) : Keyboard.isKeyDown(par0KeyBinding.keyCode);
    }
    
    public void setKeyBinding(final int par1, final int par2) {
        this.keyBindings[par1].keyCode = par2;
        this.saveOptions();
    }
    
    public void setOptionFloatValue(final EnumOptions par1EnumOptions, final float par2) {
        if (par1EnumOptions == EnumOptions.MUSIC) {
            this.musicVolume = par2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (par1EnumOptions == EnumOptions.SOUND) {
            this.soundVolume = par2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (par1EnumOptions == EnumOptions.SENSITIVITY) {
            this.mouseSensitivity = par2;
        }
        if (par1EnumOptions == EnumOptions.FOV) {
            this.fovSetting = par2;
        }
        if (par1EnumOptions == EnumOptions.GAMMA) {
            this.gammaSetting = par2;
        }
        if (par1EnumOptions == EnumOptions.CLOUD_HEIGHT) {
            this.ofCloudsHeight = par2;
        }
        if (par1EnumOptions == EnumOptions.AO_LEVEL) {
            this.ofAoLevel = par2;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE) {
            final int var3 = this.ofRenderDistanceFine;
            this.ofRenderDistanceFine = 32 + (int)(par2 * 480.0f);
            this.ofRenderDistanceFine = this.ofRenderDistanceFine >> 4 << 4;
            this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
            this.renderDistance = fineToRenderDistance(this.ofRenderDistanceFine);
            if (this.ofRenderDistanceFine != var3) {
                this.mc.renderGlobal.loadRenderers();
            }
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE) {
            this.ofLimitFramerateFine = (int)(par2 * 200.0f);
            this.enableVsync = false;
            if (this.ofLimitFramerateFine < 5) {
                this.enableVsync = true;
                this.ofLimitFramerateFine = 0;
            }
            if (this.ofLimitFramerateFine > 199) {
                this.enableVsync = false;
                this.ofLimitFramerateFine = 0;
            }
            if (this.ofLimitFramerateFine > 30) {
                this.ofLimitFramerateFine = this.ofLimitFramerateFine / 5 * 5;
            }
            if (this.ofLimitFramerateFine > 100) {
                this.ofLimitFramerateFine = this.ofLimitFramerateFine / 10 * 10;
            }
            this.limitFramerate = fineToLimitFramerate(this.ofLimitFramerateFine);
            this.updateVSync();
        }
        if (par1EnumOptions == EnumOptions.CHAT_OPACITY) {
            this.chatOpacity = par2;
            this.mc.ingameGUI.getChatGUI().func_96132_b();
        }
        if (par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = par2;
            this.mc.ingameGUI.getChatGUI().func_96132_b();
        }
        if (par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = par2;
            this.mc.ingameGUI.getChatGUI().func_96132_b();
        }
        if (par1EnumOptions == EnumOptions.CHAT_WIDTH) {
            this.chatWidth = par2;
            this.mc.ingameGUI.getChatGUI().func_96132_b();
        }
        if (par1EnumOptions == EnumOptions.CHAT_SCALE) {
            this.chatScale = par2;
            this.mc.ingameGUI.getChatGUI().func_96132_b();
        }
    }
    
    private void updateWaterOpacity() {
        byte var1 = 3;
        if (this.ofClearWater) {
            var1 = 1;
        }
        Block.waterStill.setLightOpacity(var1);
        Block.waterMoving.setLightOpacity(var1);
        if (Minecraft.theWorld != null) {
            final IChunkProvider var2 = Minecraft.theWorld.chunkProvider;
            if (var2 != null) {
                for (int var3 = -512; var3 < 512; ++var3) {
                    for (int var4 = -512; var4 < 512; ++var4) {
                        if (var2.chunkExists(var3, var4)) {
                            final Chunk var5 = var2.provideChunk(var3, var4);
                            if (var5 != null && !(var5 instanceof EmptyChunk)) {
                                final ExtendedBlockStorage[] var6 = var5.getBlockStorageArray();
                                for (int var7 = 0; var7 < var6.length; ++var7) {
                                    final ExtendedBlockStorage var8 = var6[var7];
                                    if (var8 != null) {
                                        final NibbleArray var9 = var8.getSkylightArray();
                                        if (var9 != null) {
                                            final byte[] var10 = var9.data;
                                            for (int var11 = 0; var11 < var10.length; ++var11) {
                                                var10[var11] = 0;
                                            }
                                        }
                                    }
                                }
                                var5.generateSkylightMap();
                            }
                        }
                    }
                }
                this.mc.renderGlobal.loadRenderers();
            }
        }
    }
    
    public void updateChunkLoading() {
        switch (this.ofChunkLoading) {
            case 1: {
                WrUpdates.setWrUpdater(new WrUpdaterSmooth());
                break;
            }
            case 2: {
                WrUpdates.setWrUpdater(new WrUpdaterThreaded());
                break;
            }
            default: {
                WrUpdates.setWrUpdater(null);
                break;
            }
        }
        if (this.mc.renderGlobal != null) {
            this.mc.renderGlobal.loadRenderers();
        }
    }
    
    public void setAllAnimations(final boolean var1) {
        final int var2 = var1 ? 0 : 2;
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
        this.particleSetting = (var1 ? 0 : 2);
        this.ofDrippingWaterLava = var1;
        this.ofAnimatedTerrain = var1;
        this.ofAnimatedItems = var1;
        this.ofAnimatedTextures = var1;
    }
    
    public void setOptionValue(final EnumOptions par1EnumOptions, final int par2) {
        if (par1EnumOptions == EnumOptions.INVERT_MOUSE) {
            this.invertMouse = !this.invertMouse;
        }
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE) {
            this.renderDistance = (this.renderDistance + par2 & 0x3);
            this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
        }
        if (par1EnumOptions == EnumOptions.GUI_SCALE) {
            this.guiScale = (this.guiScale + par2 & 0x3);
        }
        if (par1EnumOptions == EnumOptions.PARTICLES) {
            this.particleSetting = (this.particleSetting + par2) % 3;
        }
        if (par1EnumOptions == EnumOptions.VIEW_BOBBING) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (par1EnumOptions == EnumOptions.RENDER_CLOUDS) {
            this.clouds = !this.clouds;
        }
        if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL) {
            if (!Config.isOcclusionAvailable()) {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            else if (!this.advancedOpengl) {
                this.advancedOpengl = true;
                this.ofOcclusionFancy = false;
            }
            else if (!this.ofOcclusionFancy) {
                this.ofOcclusionFancy = true;
            }
            else {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            this.mc.renderGlobal.setAllRenderersVisible();
        }
        if (par1EnumOptions == EnumOptions.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.renderEngine.refreshTextures();
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT) {
            this.limitFramerate = (this.limitFramerate + par2 + 3) % 3;
            this.ofLimitFramerateFine = limitFramerateToFine(this.limitFramerate);
        }
        if (par1EnumOptions == EnumOptions.DIFFICULTY) {
            this.difficulty = (this.difficulty + par2 & 0x3);
        }
        if (par1EnumOptions == EnumOptions.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + par2) % 3;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (!Config.isFancyFogAvailable()) {
                        this.ofFogType = 3;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.ofFogType = 3;
                    break;
                }
                case 3: {
                    this.ofFogType = 1;
                    break;
                }
                default: {
                    this.ofFogType = 1;
                    break;
                }
            }
        }
        if (par1EnumOptions == EnumOptions.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL) {
            ++this.ofMipmapLevel;
            if (this.ofMipmapLevel > 4) {
                this.ofMipmapLevel = 0;
            }
            this.mc.renderEngine.refreshBlockTextures();
        }
        if (par1EnumOptions == EnumOptions.MIPMAP_TYPE) {
            ++this.ofMipmapType;
            if (this.ofMipmapType > 3) {
                this.ofMipmapType = 0;
            }
            this.mc.renderEngine.refreshBlockTextures();
        }
        if (par1EnumOptions == EnumOptions.LOAD_FAR) {
            this.ofLoadFar = !this.ofLoadFar;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS) {
            this.ofPreloadedChunks += 2;
            if (this.ofPreloadedChunks > 8) {
                this.ofPreloadedChunks = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.SMOOTH_FPS) {
            this.ofSmoothFps = !this.ofSmoothFps;
        }
        if (par1EnumOptions == EnumOptions.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateThreadPriorities();
        }
        if (par1EnumOptions == EnumOptions.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.TREES) {
            ++this.ofTrees;
            if (this.ofTrees > 2) {
                this.ofTrees = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.GRASS) {
            ++this.ofGrass;
            if (this.ofGrass > 2) {
                this.ofGrass = 0;
            }
            RenderBlocks.fancyGrass = Config.isGrassFancy();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.WATER) {
            ++this.ofWater;
            if (this.ofWater > 2) {
                this.ofWater = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_FIRE) {
            this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL) {
            this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE) {
            this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION) {
            this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_FLAME) {
            this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE) {
            this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (par1EnumOptions == EnumOptions.VOID_PARTICLES) {
            this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (par1EnumOptions == EnumOptions.WATER_PARTICLES) {
            this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES) {
            this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (par1EnumOptions == EnumOptions.POTION_PARTICLES) {
            this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA) {
            this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN) {
            this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES) {
            this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS) {
            this.ofAnimatedItems = !this.ofAnimatedItems;
        }
        if (par1EnumOptions == EnumOptions.RAIN_SPLASH) {
            this.ofRainSplash = !this.ofRainSplash;
        }
        if (par1EnumOptions == EnumOptions.LAGOMETER) {
            this.ofLagometer = !this.ofLagometer;
        }
        if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }
        if (par1EnumOptions == EnumOptions.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.WEATHER) {
            this.ofWeather = !this.ofWeather;
        }
        if (par1EnumOptions == EnumOptions.SKY) {
            this.ofSky = !this.ofSky;
        }
        if (par1EnumOptions == EnumOptions.STARS) {
            this.ofStars = !this.ofStars;
        }
        if (par1EnumOptions == EnumOptions.SUN_MOON) {
            this.ofSunMoon = !this.ofSunMoon;
        }
        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES) {
            ++this.ofChunkUpdates;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }
        if (par1EnumOptions == EnumOptions.CHUNK_LOADING) {
            ++this.ofChunkLoading;
            if (this.ofChunkLoading > 2) {
                this.ofChunkLoading = 0;
            }
            this.updateChunkLoading();
        }
        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC) {
            this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }
        if (par1EnumOptions == EnumOptions.TIME) {
            ++this.ofTime;
            if (this.ofTime > 3) {
                this.ofTime = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.CLEAR_WATER) {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }
        if (par1EnumOptions == EnumOptions.DEPTH_FOG) {
            this.ofDepthFog = !this.ofDepthFog;
        }
        if (par1EnumOptions == EnumOptions.AA_LEVEL) {
            final int[] var3 = { 0, 2, 4, 6, 8, 12, 16 };
            boolean var4 = false;
            for (int var5 = 0; var5 < var3.length - 1; ++var5) {
                if (this.ofAaLevel == var3[var5]) {
                    this.ofAaLevel = var3[var5 + 1];
                    var4 = true;
                    break;
                }
            }
            if (!var4) {
                this.ofAaLevel = 0;
            }
        }
        if (par1EnumOptions == EnumOptions.AF_LEVEL) {
            this.ofAfLevel *= 2;
            if (this.ofAfLevel > 16) {
                this.ofAfLevel = 1;
            }
            this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            this.mc.renderEngine.refreshBlockTextures();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.PROFILER) {
            this.ofProfiler = !this.ofProfiler;
        }
        if (par1EnumOptions == EnumOptions.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.RANDOM_MOBS) {
            this.ofRandomMobs = !this.ofRandomMobs;
            RandomMobs.resetTextures();
        }
        if (par1EnumOptions == EnumOptions.SMOOTH_BIOMES) {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            this.mc.fontRenderer.readFontData();
            this.mc.standardGalacticFontRenderer.readFontData();
        }
        if (par1EnumOptions == EnumOptions.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            CustomColorizer.update(this.mc.renderEngine);
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
            CustomSky.update(this.mc.renderEngine);
        }
        if (par1EnumOptions == EnumOptions.SHOW_CAPES) {
            this.ofShowCapes = !this.ofShowCapes;
            this.mc.renderGlobal.updateCapes();
        }
        if (par1EnumOptions == EnumOptions.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            NaturalTextures.update(this.mc.renderEngine);
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == EnumOptions.FULLSCREEN_MODE) {
            final List var6 = Arrays.asList(Config.getFullscreenModes());
            if (this.ofFullscreenMode.equals("Default")) {
                this.ofFullscreenMode = var6.get(0);
            }
            else {
                int var7 = var6.indexOf(this.ofFullscreenMode);
                if (var7 < 0) {
                    this.ofFullscreenMode = "Default";
                }
                else if (++var7 >= var6.size()) {
                    this.ofFullscreenMode = "Default";
                }
                else {
                    this.ofFullscreenMode = var6.get(var7);
                }
            }
        }
        if (par1EnumOptions == EnumOptions.HELD_ITEM_TOOLTIPS) {
            this.heldItemTooltips = !this.heldItemTooltips;
        }
        if (par1EnumOptions == EnumOptions.CHAT_VISIBILITY) {
            this.chatVisibility = (this.chatVisibility + par2) % 3;
        }
        if (par1EnumOptions == EnumOptions.CHAT_COLOR) {
            this.chatColours = !this.chatColours;
        }
        if (par1EnumOptions == EnumOptions.CHAT_LINKS) {
            this.chatLinks = !this.chatLinks;
        }
        if (par1EnumOptions == EnumOptions.CHAT_LINKS_PROMPT) {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }
        if (par1EnumOptions == EnumOptions.USE_SERVER_TEXTURES) {
            this.serverTextures = !this.serverTextures;
        }
        if (par1EnumOptions == EnumOptions.SNOOPER_ENABLED) {
            this.snooperEnabled = !this.snooperEnabled;
        }
        if (par1EnumOptions == EnumOptions.SHOW_CAPE) {
            this.showCape = !this.showCape;
        }
        if (par1EnumOptions == EnumOptions.TOUCHSCREEN) {
            this.touchscreen = !this.touchscreen;
        }
        if (par1EnumOptions == EnumOptions.USE_FULLSCREEN) {
            this.fullScreen = !this.fullScreen;
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (par1EnumOptions == EnumOptions.ENABLE_VSYNC) {
            Display.setVSyncEnabled(this.enableVsync = !this.enableVsync);
        }
        this.saveOptions();
    }
    
    public float getOptionFloatValue(final EnumOptions par1EnumOptions) {
        return (par1EnumOptions == EnumOptions.CLOUD_HEIGHT) ? this.ofCloudsHeight : ((par1EnumOptions == EnumOptions.AO_LEVEL) ? this.ofAoLevel : ((par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE) ? ((this.ofRenderDistanceFine - 32) / 480.0f) : ((par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE) ? ((this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200) ? (this.ofLimitFramerateFine / 200.0f) : (this.enableVsync ? 0.0f : 1.0f)) : ((par1EnumOptions == EnumOptions.FOV) ? this.fovSetting : ((par1EnumOptions == EnumOptions.GAMMA) ? this.gammaSetting : ((par1EnumOptions == EnumOptions.MUSIC) ? this.musicVolume : ((par1EnumOptions == EnumOptions.SOUND) ? this.soundVolume : ((par1EnumOptions == EnumOptions.SENSITIVITY) ? this.mouseSensitivity : ((par1EnumOptions == EnumOptions.CHAT_OPACITY) ? this.chatOpacity : ((par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((par1EnumOptions == EnumOptions.CHAT_SCALE) ? this.chatScale : ((par1EnumOptions == EnumOptions.CHAT_WIDTH) ? this.chatWidth : 0.0f)))))))))))));
    }
    
    public boolean getOptionOrdinalValue(final EnumOptions par1EnumOptions) {
        switch (EnumOptionsHelper.enumOptionsMappingHelperArray[par1EnumOptions.ordinal()]) {
            case 1: {
                return this.invertMouse;
            }
            case 2: {
                return this.viewBobbing;
            }
            case 3: {
                return this.anaglyph;
            }
            case 4: {
                return this.advancedOpengl;
            }
            case 5: {
                return this.clouds;
            }
            case 6: {
                return this.chatColours;
            }
            case 7: {
                return this.chatLinks;
            }
            case 8: {
                return this.chatLinksPrompt;
            }
            case 9: {
                return this.serverTextures;
            }
            case 10: {
                return this.snooperEnabled;
            }
            case 11: {
                return this.fullScreen;
            }
            case 12: {
                return this.enableVsync;
            }
            case 13: {
                return this.showCape;
            }
            case 14: {
                return this.touchscreen;
            }
            default: {
                return false;
            }
        }
    }
    
    private static String getTranslation(final String[] par0ArrayOfStr, int par1) {
        if (par1 < 0 || par1 >= par0ArrayOfStr.length) {
            par1 = 0;
        }
        final StringTranslate var2 = StringTranslate.getInstance();
        return var2.translateKey(par0ArrayOfStr[par1]);
    }
    
    public String getKeyBinding(final EnumOptions par1EnumOptions) {
        final StringTranslate var2 = StringTranslate.getInstance();
        String var3 = var2.translateKey(par1EnumOptions.getEnumString());
        if (var3 == null) {
            var3 = par1EnumOptions.getEnumString();
        }
        final String var4 = String.valueOf(var3) + ": ";
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE) {
            String var5 = "Tiny";
            short var6 = 32;
            if (this.ofRenderDistanceFine >= 64) {
                var5 = "Short";
                var6 = 64;
            }
            if (this.ofRenderDistanceFine >= 128) {
                var5 = "Normal";
                var6 = 128;
            }
            if (this.ofRenderDistanceFine >= 256) {
                var5 = "Far";
                var6 = 256;
            }
            if (this.ofRenderDistanceFine >= 512) {
                var5 = "Extreme";
                var6 = 512;
            }
            final int var7 = this.ofRenderDistanceFine - var6;
            return (var7 == 0) ? (String.valueOf(var4) + var5) : (String.valueOf(var4) + var5 + " +" + var7);
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE) {
            return (this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200) ? (String.valueOf(var4) + " " + this.ofLimitFramerateFine + " FPS") : (this.enableVsync ? (String.valueOf(var4) + " VSync") : (String.valueOf(var4) + " MaxFPS"));
        }
        if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL) {
            return this.advancedOpengl ? (this.ofOcclusionFancy ? (String.valueOf(var4) + "Fancy") : (String.valueOf(var4) + "Fast")) : (String.valueOf(var4) + "OFF");
        }
        if (par1EnumOptions == EnumOptions.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return String.valueOf(var4) + "Fast";
                }
                case 2: {
                    return String.valueOf(var4) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var4) + "OFF";
                }
                default: {
                    return String.valueOf(var4) + "OFF";
                }
            }
        }
        else {
            if (par1EnumOptions == EnumOptions.FOG_START) {
                return String.valueOf(var4) + this.ofFogStart;
            }
            if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL) {
                return (this.ofMipmapLevel == 0) ? (String.valueOf(var4) + "OFF") : ((this.ofMipmapLevel == 4) ? (String.valueOf(var4) + "Max") : (String.valueOf(var4) + this.ofMipmapLevel));
            }
            if (par1EnumOptions == EnumOptions.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0: {
                        return String.valueOf(var4) + "Nearest";
                    }
                    case 1: {
                        return String.valueOf(var4) + "Linear";
                    }
                    case 2: {
                        return String.valueOf(var4) + "Bilinear";
                    }
                    case 3: {
                        return String.valueOf(var4) + "Trilinear";
                    }
                    default: {
                        return String.valueOf(var4) + "Nearest";
                    }
                }
            }
            else {
                if (par1EnumOptions == EnumOptions.LOAD_FAR) {
                    return this.ofLoadFar ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                }
                if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS) {
                    return (this.ofPreloadedChunks == 0) ? (String.valueOf(var4) + "OFF") : (String.valueOf(var4) + this.ofPreloadedChunks);
                }
                if (par1EnumOptions == EnumOptions.SMOOTH_FPS) {
                    return this.ofSmoothFps ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                }
                if (par1EnumOptions == EnumOptions.SMOOTH_WORLD) {
                    return this.ofSmoothWorld ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                }
                if (par1EnumOptions == EnumOptions.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        case 3: {
                            return String.valueOf(var4) + "OFF";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.TREES) {
                    switch (this.ofTrees) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.GRASS) {
                    switch (this.ofGrass) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.RAIN) {
                    switch (this.ofRain) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        case 3: {
                            return String.valueOf(var4) + "OFF";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.WATER) {
                    switch (this.ofWater) {
                        case 1: {
                            return String.valueOf(var4) + "Fast";
                        }
                        case 2: {
                            return String.valueOf(var4) + "Fancy";
                        }
                        case 3: {
                            return String.valueOf(var4) + "OFF";
                        }
                        default: {
                            return String.valueOf(var4) + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1: {
                            return String.valueOf(var4) + "Dynamic";
                        }
                        case 2: {
                            return String.valueOf(var4) + "OFF";
                        }
                        default: {
                            return String.valueOf(var4) + "ON";
                        }
                    }
                }
                else if (par1EnumOptions == EnumOptions.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1: {
                            return String.valueOf(var4) + "Dynamic";
                        }
                        case 2: {
                            return String.valueOf(var4) + "OFF";
                        }
                        default: {
                            return String.valueOf(var4) + "ON";
                        }
                    }
                }
                else {
                    if (par1EnumOptions == EnumOptions.ANIMATED_FIRE) {
                        return this.ofAnimatedFire ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL) {
                        return this.ofAnimatedPortal ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE) {
                        return this.ofAnimatedRedstone ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION) {
                        return this.ofAnimatedExplosion ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_FLAME) {
                        return this.ofAnimatedFlame ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE) {
                        return this.ofAnimatedSmoke ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.VOID_PARTICLES) {
                        return this.ofVoidParticles ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.WATER_PARTICLES) {
                        return this.ofWaterParticles ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES) {
                        return this.ofPortalParticles ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.POTION_PARTICLES) {
                        return this.ofPotionParticles ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA) {
                        return this.ofDrippingWaterLava ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN) {
                        return this.ofAnimatedTerrain ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES) {
                        return this.ofAnimatedTextures ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS) {
                        return this.ofAnimatedItems ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.RAIN_SPLASH) {
                        return this.ofRainSplash ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.LAGOMETER) {
                        return this.ofLagometer ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                    }
                    if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS) {
                        return (this.ofAutoSaveTicks <= 40) ? (String.valueOf(var4) + "Default (2s)") : ((this.ofAutoSaveTicks <= 400) ? (String.valueOf(var4) + "20s") : ((this.ofAutoSaveTicks <= 4000) ? (String.valueOf(var4) + "3min") : (String.valueOf(var4) + "30min")));
                    }
                    if (par1EnumOptions == EnumOptions.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1: {
                                return String.valueOf(var4) + "Fast";
                            }
                            case 2: {
                                return String.valueOf(var4) + "Fancy";
                            }
                            default: {
                                return String.valueOf(var4) + "OFF";
                            }
                        }
                    }
                    else if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1: {
                                return String.valueOf(var4) + "Fast";
                            }
                            case 2: {
                                return String.valueOf(var4) + "Fancy";
                            }
                            default: {
                                return String.valueOf(var4) + "OFF";
                            }
                        }
                    }
                    else {
                        if (par1EnumOptions == EnumOptions.WEATHER) {
                            return this.ofWeather ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.SKY) {
                            return this.ofSky ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.STARS) {
                            return this.ofStars ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.SUN_MOON) {
                            return this.ofSunMoon ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES) {
                            return String.valueOf(var4) + this.ofChunkUpdates;
                        }
                        if (par1EnumOptions == EnumOptions.CHUNK_LOADING) {
                            return (this.ofChunkLoading == 1) ? (String.valueOf(var4) + "Smooth") : ((this.ofChunkLoading == 2) ? (String.valueOf(var4) + "Multi-Core") : (String.valueOf(var4) + "Default"));
                        }
                        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC) {
                            return this.ofChunkUpdatesDynamic ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.TIME) {
                            return (this.ofTime == 1) ? (String.valueOf(var4) + "Day Only") : ((this.ofTime == 3) ? (String.valueOf(var4) + "Night Only") : (String.valueOf(var4) + "Default"));
                        }
                        if (par1EnumOptions == EnumOptions.CLEAR_WATER) {
                            return this.ofClearWater ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.DEPTH_FOG) {
                            return this.ofDepthFog ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.AA_LEVEL) {
                            return (this.ofAaLevel == 0) ? (String.valueOf(var4) + "OFF") : (String.valueOf(var4) + this.ofAaLevel);
                        }
                        if (par1EnumOptions == EnumOptions.AF_LEVEL) {
                            return (this.ofAfLevel == 1) ? (String.valueOf(var4) + "OFF") : (String.valueOf(var4) + this.ofAfLevel);
                        }
                        if (par1EnumOptions == EnumOptions.PROFILER) {
                            return this.ofProfiler ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.BETTER_SNOW) {
                            return this.ofBetterSnow ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.SWAMP_COLORS) {
                            return this.ofSwampColors ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.RANDOM_MOBS) {
                            return this.ofRandomMobs ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.SMOOTH_BIOMES) {
                            return this.ofSmoothBiomes ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.CUSTOM_FONTS) {
                            return this.ofCustomFonts ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.CUSTOM_COLORS) {
                            return this.ofCustomColors ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.CUSTOM_SKY) {
                            return this.ofCustomSky ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.SHOW_CAPES) {
                            return this.ofShowCapes ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.NATURAL_TEXTURES) {
                            return this.ofNaturalTextures ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.LAZY_CHUNK_LOADING) {
                            return this.ofLazyChunkLoading ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions == EnumOptions.FULLSCREEN_MODE) {
                            return String.valueOf(var4) + this.ofFullscreenMode;
                        }
                        if (par1EnumOptions == EnumOptions.HELD_ITEM_TOOLTIPS) {
                            return this.heldItemTooltips ? (String.valueOf(var4) + "ON") : (String.valueOf(var4) + "OFF");
                        }
                        if (par1EnumOptions.getEnumFloat()) {
                            final float var8 = this.getOptionFloatValue(par1EnumOptions);
                            return (par1EnumOptions == EnumOptions.SENSITIVITY) ? ((var8 == 0.0f) ? (String.valueOf(var4) + var2.translateKey("options.sensitivity.min")) : ((var8 == 1.0f) ? (String.valueOf(var4) + var2.translateKey("options.sensitivity.max")) : (String.valueOf(var4) + (int)(var8 * 200.0f) + "%"))) : ((par1EnumOptions == EnumOptions.FOV) ? ((var8 == 0.0f) ? (String.valueOf(var4) + var2.translateKey("options.fov.min")) : ((var8 == 1.0f) ? (String.valueOf(var4) + var2.translateKey("options.fov.max")) : (String.valueOf(var4) + (int)(70.0f + var8 * 40.0f)))) : ((par1EnumOptions == EnumOptions.GAMMA) ? ((var8 == 0.0f) ? (String.valueOf(var4) + var2.translateKey("options.gamma.min")) : ((var8 == 1.0f) ? (String.valueOf(var4) + var2.translateKey("options.gamma.max")) : (String.valueOf(var4) + "+" + (int)(var8 * 100.0f) + "%"))) : ((par1EnumOptions == EnumOptions.CHAT_OPACITY) ? (String.valueOf(var4) + (int)(var8 * 90.0f + 10.0f) + "%") : ((par1EnumOptions == EnumOptions.CHAT_HEIGHT_UNFOCUSED) ? (String.valueOf(var4) + GuiNewChat.func_96130_b(var8) + "px") : ((par1EnumOptions == EnumOptions.CHAT_HEIGHT_FOCUSED) ? (String.valueOf(var4) + GuiNewChat.func_96130_b(var8) + "px") : ((par1EnumOptions == EnumOptions.CHAT_WIDTH) ? (String.valueOf(var4) + GuiNewChat.func_96128_a(var8) + "px") : ((var8 == 0.0f) ? (String.valueOf(var4) + var2.translateKey("options.off")) : (String.valueOf(var4) + (int)(var8 * 100.0f) + "%"))))))));
                        }
                        if (par1EnumOptions.getEnumBoolean()) {
                            final boolean var9 = this.getOptionOrdinalValue(par1EnumOptions);
                            return var9 ? (String.valueOf(var4) + var2.translateKey("options.on")) : (String.valueOf(var4) + var2.translateKey("options.off"));
                        }
                        return (par1EnumOptions == EnumOptions.RENDER_DISTANCE) ? (String.valueOf(var4) + getTranslation(GameSettings.RENDER_DISTANCES, this.renderDistance)) : ((par1EnumOptions == EnumOptions.DIFFICULTY) ? (String.valueOf(var4) + getTranslation(GameSettings.DIFFICULTIES, this.difficulty)) : ((par1EnumOptions == EnumOptions.GUI_SCALE) ? (String.valueOf(var4) + getTranslation(GameSettings.GUISCALES, this.guiScale)) : ((par1EnumOptions == EnumOptions.CHAT_VISIBILITY) ? (String.valueOf(var4) + getTranslation(GameSettings.CHAT_VISIBILITIES, this.chatVisibility)) : ((par1EnumOptions == EnumOptions.PARTICLES) ? (String.valueOf(var4) + getTranslation(GameSettings.PARTICLES, this.particleSetting)) : ((par1EnumOptions == EnumOptions.FRAMERATE_LIMIT) ? (String.valueOf(var4) + getTranslation(GameSettings.LIMIT_FRAMERATES, this.limitFramerate)) : ((par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION) ? (String.valueOf(var4) + getTranslation(GameSettings.AMBIENT_OCCLUSIONS, this.ambientOcclusion)) : ((par1EnumOptions == EnumOptions.GRAPHICS) ? (this.fancyGraphics ? (String.valueOf(var4) + var2.translateKey("options.graphics.fancy")) : (String.valueOf(var4) + var2.translateKey("options.graphics.fast"))) : var4)))))));
                    }
                }
            }
        }
    }
    
    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            final BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
            String var2 = "";
            while ((var2 = var1.readLine()) != null) {
                try {
                    final String[] var3 = var2.split(":");
                    if (var3[0].equals("music")) {
                        this.musicVolume = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("sound")) {
                        this.soundVolume = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("mouseSensitivity")) {
                        this.mouseSensitivity = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("fov")) {
                        this.fovSetting = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("gamma")) {
                        this.gammaSetting = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("invertYMouse")) {
                        this.invertMouse = var3[1].equals("true");
                    }
                    if (var3[0].equals("viewDistance")) {
                        this.renderDistance = Integer.parseInt(var3[1]);
                        this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
                    }
                    if (var3[0].equals("guiScale")) {
                        this.guiScale = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("particles")) {
                        this.particleSetting = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("bobView")) {
                        this.viewBobbing = var3[1].equals("true");
                    }
                    if (var3[0].equals("anaglyph3d")) {
                        this.anaglyph = var3[1].equals("true");
                    }
                    if (var3[0].equals("advancedOpengl")) {
                        this.advancedOpengl = var3[1].equals("true");
                    }
                    if (var3[0].equals("fpsLimit")) {
                        this.limitFramerate = Integer.parseInt(var3[1]);
                        this.ofLimitFramerateFine = limitFramerateToFine(this.limitFramerate);
                    }
                    if (var3[0].equals("difficulty")) {
                        this.difficulty = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("fancyGraphics")) {
                        this.fancyGraphics = var3[1].equals("true");
                    }
                    if (var3[0].equals("ao")) {
                        if (var3[1].equals("true")) {
                            this.ambientOcclusion = 2;
                        }
                        else if (var3[1].equals("false")) {
                            this.ambientOcclusion = 0;
                        }
                        else {
                            this.ambientOcclusion = Integer.parseInt(var3[1]);
                        }
                    }
                    if (var3[0].equals("clouds")) {
                        this.clouds = var3[1].equals("true");
                    }
                    if (var3[0].equals("skin")) {
                        this.skin = var3[1];
                    }
                    if (var3[0].equals("lastServer") && var3.length >= 2) {
                        this.lastServer = var3[1];
                    }
                    if (var3[0].equals("lang") && var3.length >= 2) {
                        this.language = var3[1];
                    }
                    if (var3[0].equals("chatVisibility")) {
                        this.chatVisibility = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("chatColors")) {
                        this.chatColours = var3[1].equals("true");
                    }
                    if (var3[0].equals("chatLinks")) {
                        this.chatLinks = var3[1].equals("true");
                    }
                    if (var3[0].equals("chatLinksPrompt")) {
                        this.chatLinksPrompt = var3[1].equals("true");
                    }
                    if (var3[0].equals("chatOpacity")) {
                        this.chatOpacity = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("serverTextures")) {
                        this.serverTextures = var3[1].equals("true");
                    }
                    if (var3[0].equals("snooperEnabled")) {
                        this.snooperEnabled = var3[1].equals("true");
                    }
                    if (var3[0].equals("fullscreen")) {
                        this.fullScreen = var3[1].equals("true");
                    }
                    if (var3[0].equals("enableVsync")) {
                        this.enableVsync = var3[1].equals("true");
                        this.updateVSync();
                    }
                    if (var3[0].equals("hideServerAddress")) {
                        this.hideServerAddress = var3[1].equals("true");
                    }
                    if (var3[0].equals("advancedItemTooltips")) {
                        this.advancedItemTooltips = var3[1].equals("true");
                    }
                    if (var3[0].equals("pauseOnLostFocus")) {
                        this.pauseOnLostFocus = var3[1].equals("true");
                    }
                    if (var3[0].equals("showCape")) {
                        this.showCape = var3[1].equals("true");
                    }
                    if (var3[0].equals("touchscreen")) {
                        this.touchscreen = var3[1].equals("true");
                    }
                    if (var3[0].equals("overrideHeight")) {
                        this.overrideHeight = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("overrideWidth")) {
                        this.overrideWidth = Integer.parseInt(var3[1]);
                    }
                    if (var3[0].equals("heldItemTooltips")) {
                        this.heldItemTooltips = var3[1].equals("true");
                    }
                    if (var3[0].equals("chatHeightFocused")) {
                        this.chatHeightFocused = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("chatHeightUnfocused")) {
                        this.chatHeightUnfocused = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("chatScale")) {
                        this.chatScale = this.parseFloat(var3[1]);
                    }
                    if (var3[0].equals("chatWidth")) {
                        this.chatWidth = this.parseFloat(var3[1]);
                    }
                    for (int var4 = 0; var4 < this.keyBindings.length; ++var4) {
                        if (var3[0].equals("key_" + this.keyBindings[var4].keyDescription)) {
                            this.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
                        }
                    }
                }
                catch (Exception var5) {
                    this.mc.getLogAgent().logWarning("Skipping bad option: " + var2);
                    var5.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var1.close();
        }
        catch (Exception var6) {
            this.mc.getLogAgent().logWarning("Failed to load options");
            var6.printStackTrace();
        }
        try {
            File var7 = this.optionsFileOF;
            if (!var7.exists()) {
                var7 = this.optionsFile;
            }
            if (!var7.exists()) {
                return;
            }
            final BufferedReader var8 = new BufferedReader(new FileReader(var7));
            String var9 = "";
            while ((var9 = var8.readLine()) != null) {
                try {
                    final String[] var10 = var9.split(":");
                    if (var10[0].equals("ofRenderDistanceFine") && var10.length >= 2) {
                        this.ofRenderDistanceFine = Integer.valueOf(var10[1]);
                        this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
                        this.renderDistance = fineToRenderDistance(this.ofRenderDistanceFine);
                    }
                    if (var10[0].equals("ofLimitFramerateFine") && var10.length >= 2) {
                        this.ofLimitFramerateFine = Integer.valueOf(var10[1]);
                        this.ofLimitFramerateFine = Config.limit(this.ofLimitFramerateFine, 0, 199);
                        this.limitFramerate = fineToLimitFramerate(this.ofLimitFramerateFine);
                    }
                    if (var10[0].equals("ofFogType") && var10.length >= 2) {
                        this.ofFogType = Integer.valueOf(var10[1]);
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }
                    if (var10[0].equals("ofFogStart") && var10.length >= 2) {
                        this.ofFogStart = Float.valueOf(var10[1]);
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (var10[0].equals("ofMipmapLevel") && var10.length >= 2) {
                        this.ofMipmapLevel = Integer.valueOf(var10[1]);
                        if (this.ofMipmapLevel < 0) {
                            this.ofMipmapLevel = 0;
                        }
                        if (this.ofMipmapLevel > 4) {
                            this.ofMipmapLevel = 4;
                        }
                    }
                    if (var10[0].equals("ofMipmapType") && var10.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(var10[1]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
                    }
                    if (var10[0].equals("ofLoadFar") && var10.length >= 2) {
                        this.ofLoadFar = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofPreloadedChunks") && var10.length >= 2) {
                        this.ofPreloadedChunks = Integer.valueOf(var10[1]);
                        if (this.ofPreloadedChunks < 0) {
                            this.ofPreloadedChunks = 0;
                        }
                        if (this.ofPreloadedChunks > 8) {
                            this.ofPreloadedChunks = 8;
                        }
                    }
                    if (var10[0].equals("ofOcclusionFancy") && var10.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSmoothFps") && var10.length >= 2) {
                        this.ofSmoothFps = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSmoothWorld") && var10.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAoLevel") && var10.length >= 2) {
                        this.ofAoLevel = Float.valueOf(var10[1]);
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
                    }
                    if (var10[0].equals("ofClouds") && var10.length >= 2) {
                        this.ofClouds = Integer.valueOf(var10[1]);
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                    }
                    if (var10[0].equals("ofCloudsHeight") && var10.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(var10[1]);
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
                    }
                    if (var10[0].equals("ofTrees") && var10.length >= 2) {
                        this.ofTrees = Integer.valueOf(var10[1]);
                        this.ofTrees = Config.limit(this.ofTrees, 0, 2);
                    }
                    if (var10[0].equals("ofGrass") && var10.length >= 2) {
                        this.ofGrass = Integer.valueOf(var10[1]);
                        this.ofGrass = Config.limit(this.ofGrass, 0, 2);
                    }
                    if (var10[0].equals("ofDroppedItems") && var10.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(var10[1]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }
                    if (var10[0].equals("ofRain") && var10.length >= 2) {
                        this.ofRain = Integer.valueOf(var10[1]);
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }
                    if (var10[0].equals("ofWater") && var10.length >= 2) {
                        this.ofWater = Integer.valueOf(var10[1]);
                        this.ofWater = Config.limit(this.ofWater, 0, 3);
                    }
                    if (var10[0].equals("ofAnimatedWater") && var10.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(var10[1]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }
                    if (var10[0].equals("ofAnimatedLava") && var10.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(var10[1]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }
                    if (var10[0].equals("ofAnimatedFire") && var10.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedPortal") && var10.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedRedstone") && var10.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedExplosion") && var10.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedFlame") && var10.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedSmoke") && var10.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofVoidParticles") && var10.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofWaterParticles") && var10.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofPortalParticles") && var10.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofPotionParticles") && var10.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofDrippingWaterLava") && var10.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedTerrain") && var10.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedTextures") && var10.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAnimatedItems") && var10.length >= 2) {
                        this.ofAnimatedItems = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofRainSplash") && var10.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofLagometer") && var10.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAutoSaveTicks") && var10.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(var10[1]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }
                    if (var10[0].equals("ofBetterGrass") && var10.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(var10[1]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }
                    if (var10[0].equals("ofConnectedTextures") && var10.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(var10[1]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }
                    if (var10[0].equals("ofWeather") && var10.length >= 2) {
                        this.ofWeather = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSky") && var10.length >= 2) {
                        this.ofSky = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofStars") && var10.length >= 2) {
                        this.ofStars = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSunMoon") && var10.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofChunkUpdates") && var10.length >= 2) {
                        this.ofChunkUpdates = Integer.valueOf(var10[1]);
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
                    }
                    if (var10[0].equals("ofChunkLoading") && var10.length >= 2) {
                        this.ofChunkLoading = Integer.valueOf(var10[1]);
                        this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                        this.updateChunkLoading();
                    }
                    if (var10[0].equals("ofChunkUpdatesDynamic") && var10.length >= 2) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofTime") && var10.length >= 2) {
                        this.ofTime = Integer.valueOf(var10[1]);
                        this.ofTime = Config.limit(this.ofTime, 0, 3);
                    }
                    if (var10[0].equals("ofClearWater") && var10.length >= 2) {
                        this.ofClearWater = Boolean.valueOf(var10[1]);
                        this.updateWaterOpacity();
                    }
                    if (var10[0].equals("ofDepthFog") && var10.length >= 2) {
                        this.ofDepthFog = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofAaLevel") && var10.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(var10[1]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }
                    if (var10[0].equals("ofAfLevel") && var10.length >= 2) {
                        this.ofAfLevel = Integer.valueOf(var10[1]);
                        this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
                    }
                    if (var10[0].equals("ofProfiler") && var10.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofBetterSnow") && var10.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSwampColors") && var10.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofRandomMobs") && var10.length >= 2) {
                        this.ofRandomMobs = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofSmoothBiomes") && var10.length >= 2) {
                        this.ofSmoothBiomes = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofCustomFonts") && var10.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofCustomColors") && var10.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofCustomSky") && var10.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofShowCapes") && var10.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofNaturalTextures") && var10.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(var10[1]);
                    }
                    if (var10[0].equals("ofLazyChunkLoading") && var10.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(var10[1]);
                    }
                    if (!var10[0].equals("ofFullscreenMode") || var10.length < 2) {
                        continue;
                    }
                    this.ofFullscreenMode = var10[1];
                }
                catch (Exception var11) {
                    Config.dbg("Skipping bad option: " + var9);
                    var11.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var8.close();
        }
        catch (Exception var12) {
            Config.dbg("Failed to load options");
            var12.printStackTrace();
        }
    }
    
    private float parseFloat(final String par1Str) {
        return par1Str.equals("true") ? 1.0f : (par1Str.equals("false") ? 0.0f : Float.parseFloat(par1Str));
    }
    
    public void saveOptions() {
        if (Reflector.FMLClientHandler.exists()) {
            final Object var1 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            if (var1 != null && Reflector.callBoolean(var1, Reflector.FMLClientHandler_isLoading, new Object[0])) {
                return;
            }
        }
        try {
            final PrintWriter var2 = new PrintWriter(new FileWriter(this.optionsFile));
            var2.println("music:" + this.musicVolume);
            var2.println("sound:" + this.soundVolume);
            var2.println("invertYMouse:" + this.invertMouse);
            var2.println("mouseSensitivity:" + this.mouseSensitivity);
            var2.println("fov:" + this.fovSetting);
            var2.println("gamma:" + this.gammaSetting);
            var2.println("viewDistance:" + this.renderDistance);
            var2.println("guiScale:" + this.guiScale);
            var2.println("particles:" + this.particleSetting);
            var2.println("bobView:" + this.viewBobbing);
            var2.println("anaglyph3d:" + this.anaglyph);
            var2.println("advancedOpengl:" + this.advancedOpengl);
            var2.println("fpsLimit:" + this.limitFramerate);
            var2.println("difficulty:" + this.difficulty);
            var2.println("fancyGraphics:" + this.fancyGraphics);
            var2.println("ao:" + this.ambientOcclusion);
            var2.println("clouds:" + this.clouds);
            var2.println("skin:" + this.skin);
            var2.println("lastServer:" + this.lastServer);
            var2.println("lang:" + this.language);
            var2.println("chatVisibility:" + this.chatVisibility);
            var2.println("chatColors:" + this.chatColours);
            var2.println("chatLinks:" + this.chatLinks);
            var2.println("chatLinksPrompt:" + this.chatLinksPrompt);
            var2.println("chatOpacity:" + this.chatOpacity);
            var2.println("serverTextures:" + this.serverTextures);
            var2.println("snooperEnabled:" + this.snooperEnabled);
            var2.println("fullscreen:" + this.fullScreen);
            var2.println("enableVsync:" + this.enableVsync);
            var2.println("hideServerAddress:" + this.hideServerAddress);
            var2.println("advancedItemTooltips:" + this.advancedItemTooltips);
            var2.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            var2.println("showCape:" + this.showCape);
            var2.println("touchscreen:" + this.touchscreen);
            var2.println("overrideWidth:" + this.overrideWidth);
            var2.println("overrideHeight:" + this.overrideHeight);
            var2.println("heldItemTooltips:" + this.heldItemTooltips);
            var2.println("chatHeightFocused:" + this.chatHeightFocused);
            var2.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            var2.println("chatScale:" + this.chatScale);
            var2.println("chatWidth:" + this.chatWidth);
            for (int var3 = 0; var3 < this.keyBindings.length; ++var3) {
                var2.println("key_" + this.keyBindings[var3].keyDescription + ":" + this.keyBindings[var3].keyCode);
            }
            var2.close();
        }
        catch (Exception var4) {
            this.mc.getLogAgent().logWarning("Failed to save options");
            var4.printStackTrace();
        }
        try {
            final PrintWriter var2 = new PrintWriter(new FileWriter(this.optionsFileOF));
            var2.println("ofRenderDistanceFine:" + this.ofRenderDistanceFine);
            var2.println("ofLimitFramerateFine:" + this.ofLimitFramerateFine);
            var2.println("ofFogType:" + this.ofFogType);
            var2.println("ofFogStart:" + this.ofFogStart);
            var2.println("ofMipmapLevel:" + this.ofMipmapLevel);
            var2.println("ofMipmapType:" + this.ofMipmapType);
            var2.println("ofLoadFar:" + this.ofLoadFar);
            var2.println("ofPreloadedChunks:" + this.ofPreloadedChunks);
            var2.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            var2.println("ofSmoothFps:" + this.ofSmoothFps);
            var2.println("ofSmoothWorld:" + this.ofSmoothWorld);
            var2.println("ofAoLevel:" + this.ofAoLevel);
            var2.println("ofClouds:" + this.ofClouds);
            var2.println("ofCloudsHeight:" + this.ofCloudsHeight);
            var2.println("ofTrees:" + this.ofTrees);
            var2.println("ofGrass:" + this.ofGrass);
            var2.println("ofDroppedItems:" + this.ofDroppedItems);
            var2.println("ofRain:" + this.ofRain);
            var2.println("ofWater:" + this.ofWater);
            var2.println("ofAnimatedWater:" + this.ofAnimatedWater);
            var2.println("ofAnimatedLava:" + this.ofAnimatedLava);
            var2.println("ofAnimatedFire:" + this.ofAnimatedFire);
            var2.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            var2.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            var2.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            var2.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            var2.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            var2.println("ofVoidParticles:" + this.ofVoidParticles);
            var2.println("ofWaterParticles:" + this.ofWaterParticles);
            var2.println("ofPortalParticles:" + this.ofPortalParticles);
            var2.println("ofPotionParticles:" + this.ofPotionParticles);
            var2.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            var2.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            var2.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            var2.println("ofAnimatedItems:" + this.ofAnimatedItems);
            var2.println("ofRainSplash:" + this.ofRainSplash);
            var2.println("ofLagometer:" + this.ofLagometer);
            var2.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            var2.println("ofBetterGrass:" + this.ofBetterGrass);
            var2.println("ofConnectedTextures:" + this.ofConnectedTextures);
            var2.println("ofWeather:" + this.ofWeather);
            var2.println("ofSky:" + this.ofSky);
            var2.println("ofStars:" + this.ofStars);
            var2.println("ofSunMoon:" + this.ofSunMoon);
            var2.println("ofChunkUpdates:" + this.ofChunkUpdates);
            var2.println("ofChunkLoading:" + this.ofChunkLoading);
            var2.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
            var2.println("ofTime:" + this.ofTime);
            var2.println("ofClearWater:" + this.ofClearWater);
            var2.println("ofDepthFog:" + this.ofDepthFog);
            var2.println("ofAaLevel:" + this.ofAaLevel);
            var2.println("ofAfLevel:" + this.ofAfLevel);
            var2.println("ofProfiler:" + this.ofProfiler);
            var2.println("ofBetterSnow:" + this.ofBetterSnow);
            var2.println("ofSwampColors:" + this.ofSwampColors);
            var2.println("ofRandomMobs:" + this.ofRandomMobs);
            var2.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
            var2.println("ofCustomFonts:" + this.ofCustomFonts);
            var2.println("ofCustomColors:" + this.ofCustomColors);
            var2.println("ofCustomSky:" + this.ofCustomSky);
            var2.println("ofShowCapes:" + this.ofShowCapes);
            var2.println("ofNaturalTextures:" + this.ofNaturalTextures);
            var2.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            var2.println("ofFullscreenMode:" + this.ofFullscreenMode);
            var2.close();
        }
        catch (Exception var5) {
            Config.dbg("Failed to save options");
            var5.printStackTrace();
        }
        this.sendSettingsToServer();
    }
    
    public void sendSettingsToServer() {
        if (Minecraft.thePlayer != null) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet204ClientInfo(this.language, this.renderDistance, this.chatVisibility, this.chatColours, this.difficulty, this.showCape));
        }
    }
    
    public void resetSettings() {
        this.renderDistance = 1;
        this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = 0;
        this.enableVsync = false;
        this.updateVSync();
        this.ofLimitFramerateFine = 0;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.fovSetting = 0.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapLevel = 0;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofPreloadedChunks = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
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
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.mc.renderGlobal.updateCapes();
        this.updateWaterOpacity();
        this.mc.renderGlobal.setAllRenderersVisible();
        this.mc.renderEngine.refreshTextures();
        this.mc.renderGlobal.loadRenderers();
        this.saveOptions();
    }
    
    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }
    
    private static int fineToRenderDistance(final int var0) {
        byte var = 3;
        if (var0 > 32) {
            var = 2;
        }
        if (var0 > 64) {
            var = 1;
        }
        if (var0 > 128) {
            var = 0;
        }
        return var;
    }
    
    private static int renderDistanceToFine(final int var0) {
        return 32 << 3 - var0;
    }
    
    private static int fineToLimitFramerate(final int var0) {
        byte var = 2;
        if (var0 > 35) {
            var = 1;
        }
        if (var0 >= 200) {
            var = 0;
        }
        if (var0 <= 0) {
            var = 0;
        }
        return var;
    }
    
    private static int limitFramerateToFine(final int var0) {
        switch (var0) {
            case 0: {
                return 0;
            }
            case 1: {
                return 120;
            }
            case 2: {
                return 35;
            }
            default: {
                return 0;
            }
        }
    }
    
    public boolean shouldRenderClouds() {
        return this.ofRenderDistanceFine > 64 && this.clouds;
    }
}
