package net.minecraft.client.settings;

import com.google.gson.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import org.apache.commons.lang3.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.stream.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import optfine.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;

public class GameSettings
{
    public boolean fboEnable;
    public boolean ofNaturalTextures;
    public float ofCloudsHeight;
    public static final String DEFAULT_STR;
    public KeyBinding keyBindDrop;
    public boolean smoothCamera;
    public boolean advancedItemTooltips;
    public KeyBinding keyBindPlayerList;
    public KeyBinding keyBindStreamPauseUnpause;
    public int ofDroppedItems;
    public boolean enableVsync;
    public boolean ofFastMath;
    public boolean hideGUI;
    public int ofAnimatedWater;
    public KeyBinding keyBindForward;
    public boolean ofAnimatedFire;
    public List field_183018_l;
    public boolean field_181657_aC;
    private static final String[] STREAM_MIC_MODES;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindScreenshot;
    private static final Logger logger;
    public boolean chatColours;
    private File optionsFile;
    public boolean ofAnimatedRedstone;
    private static final Gson gson;
    public float chatHeightFocused;
    public static final int FANCY;
    public boolean ofSky;
    public int ofFogType;
    public float saturation;
    public int ofTranslucentBlocks;
    public boolean heldItemTooltips;
    public boolean ofBetterSnow;
    public int thirdPersonView;
    public boolean invertMouse;
    public int clouds;
    public boolean anaglyph;
    public KeyBinding[] keyBindings;
    public boolean ofSwampColors;
    public float gammaSetting;
    public static final int CL_THREADED;
    public boolean ofAnimatedTerrain;
    public int particleSetting;
    public int ofTrees;
    public int limitFramerate;
    private static final String __OBFID;
    public boolean ofAnimatedTextures;
    public boolean ofFireworkParticles;
    public boolean snooperEnabled;
    public boolean ofStars;
    public KeyBinding ofKeyBindZoom;
    public boolean showDebugInfo;
    public float streamKbps;
    public int ofVignette;
    public String language;
    public boolean chatLinksPrompt;
    public boolean allowBlockAlternatives;
    public boolean ofWaterParticles;
    public int ofRain;
    public boolean ofPortalParticles;
    public boolean pauseOnLostFocus;
    public boolean ofAnimatedExplosion;
    public List resourcePacks;
    public boolean forceUnicodeFont;
    public boolean ofAnimatedSmoke;
    public float fovSetting;
    public KeyBinding keyBindSprint;
    private static final ParameterizedType typeListString;
    public static final int ANIM_OFF;
    public boolean fancyGraphics;
    public int streamMicToggleBehavior;
    public float streamBytesPerPixel;
    public int renderDistanceChunks;
    public EnumDifficulty difficulty;
    public boolean field_181151_V;
    public int ofChunkLoading;
    public float ofFogStart;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindPickBlock;
    public KeyBinding[] keyBindsHotbar;
    public int streamChatEnabled;
    public boolean useVbo;
    public static final int CL_SMOOTH;
    public KeyBinding keyBindChat;
    public boolean ofAnimatedFlame;
    public int mipmapLevels;
    public boolean ofProfiler;
    public boolean ofCustomColors;
    public EntityPlayer.EnumChatVisibility chatVisibility;
    public boolean viewBobbing;
    public boolean ofRandomMobs;
    public int overrideHeight;
    public int ofBetterGrass;
    public KeyBinding keyBindInventory;
    public float chatHeightUnfocused;
    public int ofMipmapType;
    public int ofClouds;
    private static final String[] STREAM_COMPRESSIONS;
    private static final String[] GUISCALES;
    public boolean hideServerAddress;
    public int ofConnectedTextures;
    public float ofAoLevel;
    public boolean ofLazyChunkLoading;
    public float streamMicVolume;
    private static final String[] PARTICLES;
    private static final String[] field_181149_aW;
    public String lastServer;
    public int overrideWidth;
    public KeyBinding keyBindStreamCommercials;
    public KeyBinding keyBindUseItem;
    public boolean ofWeather;
    public boolean fullScreen;
    public float mouseSensitivity;
    private File optionsFileOF;
    public static final int DEFAULT;
    public boolean ofAnimatedPortal;
    private static final String[] STREAM_CHAT_MODES;
    public String streamPreferredServer;
    public boolean ofSmoothBiomes;
    public boolean ofShowCapes;
    public int ambientOcclusion;
    public int guiScale;
    public boolean ofCustomFonts;
    public static final int ANIM_ON;
    public String ofFullscreenMode;
    public boolean debugCamEnable;
    public static final int OFF;
    public boolean ofChunkUpdatesDynamic;
    public float streamFps;
    public KeyBinding keyBindSmoothCamera;
    public boolean ofOcclusionFancy;
    public boolean showDebugProfilerChart;
    public boolean showInventoryAchievementHint;
    public int ofTime;
    public KeyBinding keyBindSpectatorOutlines;
    public static final int FAST;
    public boolean touchscreen;
    public boolean reducedDebugInfo;
    public boolean ofSunMoon;
    public KeyBinding keyBindStreamToggleMic;
    private Map mapSoundLevels;
    public int streamCompression;
    public boolean ofVoidParticles;
    public float streamGameVolume;
    private static final String[] STREAM_CHAT_FILTER_MODES;
    public boolean ofCustomSky;
    public boolean ofSmoothFps;
    public boolean ofSmoothWorld;
    private static final String[] I;
    public KeyBinding keyBindAttack;
    public int ofChunkUpdates;
    public static final int ANIM_GENERATED;
    public KeyBinding keyBindFullscreen;
    public float chatOpacity;
    public int ofAaLevel;
    public boolean streamSendMetadata;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindSneak;
    public int streamChatUserFilter;
    public boolean ofClearWater;
    public KeyBinding keyBindCommand;
    public boolean ofLagometer;
    protected Minecraft mc;
    public KeyBinding keyBindTogglePerspective;
    public int ofAutoSaveTicks;
    public KeyBinding keyBindStreamStartStop;
    public boolean chatLinks;
    public KeyBinding keyBindLeft;
    private final Set setModelParts;
    public float chatScale;
    public boolean ofFastRender;
    public boolean ofDrippingWaterLava;
    public boolean ofShowFps;
    public static final int CL_DEFAULT;
    public boolean ofPotionParticles;
    private static final String[] AMBIENT_OCCLUSIONS;
    public int ofAnimatedLava;
    public boolean ofRainSplash;
    public float chatWidth;
    public boolean field_181150_U;
    public int ofAfLevel;
    
    public void setOptionValue(final Options options, final int n) {
        this.setOptionValueOF(options, n);
        if (options == Options.INVERT_MOUSE) {
            int invertMouse;
            if (this.invertMouse) {
                invertMouse = "".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                invertMouse = " ".length();
            }
            this.invertMouse = (invertMouse != 0);
        }
        if (options == Options.GUI_SCALE) {
            this.guiScale = (this.guiScale + n & "   ".length());
        }
        if (options == Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + n) % "   ".length();
        }
        if (options == Options.VIEW_BOBBING) {
            int viewBobbing;
            if (this.viewBobbing) {
                viewBobbing = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                viewBobbing = " ".length();
            }
            this.viewBobbing = (viewBobbing != 0);
        }
        if (options == Options.RENDER_CLOUDS) {
            this.clouds = (this.clouds + n) % "   ".length();
        }
        if (options == Options.FORCE_UNICODE_FONT) {
            int forceUnicodeFont;
            if (this.forceUnicodeFont) {
                forceUnicodeFont = "".length();
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            else {
                forceUnicodeFont = " ".length();
            }
            this.forceUnicodeFont = (forceUnicodeFont != 0);
            final FontRenderer fontRendererObj = this.mc.fontRendererObj;
            int unicodeFlag;
            if (!this.mc.getLanguageManager().isCurrentLocaleUnicode() && !this.forceUnicodeFont) {
                unicodeFlag = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                unicodeFlag = " ".length();
            }
            fontRendererObj.setUnicodeFlag(unicodeFlag != 0);
        }
        if (options == Options.FBO_ENABLE) {
            int fboEnable;
            if (this.fboEnable) {
                fboEnable = "".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                fboEnable = " ".length();
            }
            this.fboEnable = (fboEnable != 0);
        }
        if (options == Options.ANAGLYPH) {
            int anaglyph;
            if (this.anaglyph) {
                anaglyph = "".length();
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                anaglyph = " ".length();
            }
            this.anaglyph = (anaglyph != 0);
            this.mc.refreshResources();
        }
        if (options == Options.GRAPHICS) {
            int fancyGraphics;
            if (this.fancyGraphics) {
                fancyGraphics = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                fancyGraphics = " ".length();
            }
            this.fancyGraphics = (fancyGraphics != 0);
            this.updateRenderClouds();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + n) % "   ".length();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CHAT_VISIBILITY) {
            this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + n) % "   ".length());
        }
        if (options == Options.STREAM_COMPRESSION) {
            this.streamCompression = (this.streamCompression + n) % "   ".length();
        }
        if (options == Options.STREAM_SEND_METADATA) {
            int streamSendMetadata;
            if (this.streamSendMetadata) {
                streamSendMetadata = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                streamSendMetadata = " ".length();
            }
            this.streamSendMetadata = (streamSendMetadata != 0);
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            this.streamChatEnabled = (this.streamChatEnabled + n) % "   ".length();
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            this.streamChatUserFilter = (this.streamChatUserFilter + n) % "   ".length();
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            this.streamMicToggleBehavior = (this.streamMicToggleBehavior + n) % "  ".length();
        }
        if (options == Options.CHAT_COLOR) {
            int chatColours;
            if (this.chatColours) {
                chatColours = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                chatColours = " ".length();
            }
            this.chatColours = (chatColours != 0);
        }
        if (options == Options.CHAT_LINKS) {
            int chatLinks;
            if (this.chatLinks) {
                chatLinks = "".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                chatLinks = " ".length();
            }
            this.chatLinks = (chatLinks != 0);
        }
        if (options == Options.CHAT_LINKS_PROMPT) {
            int chatLinksPrompt;
            if (this.chatLinksPrompt) {
                chatLinksPrompt = "".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                chatLinksPrompt = " ".length();
            }
            this.chatLinksPrompt = (chatLinksPrompt != 0);
        }
        if (options == Options.SNOOPER_ENABLED) {
            int snooperEnabled;
            if (this.snooperEnabled) {
                snooperEnabled = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                snooperEnabled = " ".length();
            }
            this.snooperEnabled = (snooperEnabled != 0);
        }
        if (options == Options.TOUCHSCREEN) {
            int touchscreen;
            if (this.touchscreen) {
                touchscreen = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                touchscreen = " ".length();
            }
            this.touchscreen = (touchscreen != 0);
        }
        if (options == Options.USE_FULLSCREEN) {
            int fullScreen;
            if (this.fullScreen) {
                fullScreen = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                fullScreen = " ".length();
            }
            this.fullScreen = (fullScreen != 0);
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (options == Options.ENABLE_VSYNC) {
            int enableVsync;
            if (this.enableVsync) {
                enableVsync = "".length();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else {
                enableVsync = " ".length();
            }
            Display.setVSyncEnabled(this.enableVsync = (enableVsync != 0));
        }
        if (options == Options.USE_VBO) {
            int useVbo;
            if (this.useVbo) {
                useVbo = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                useVbo = " ".length();
            }
            this.useVbo = (useVbo != 0);
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            int allowBlockAlternatives;
            if (this.allowBlockAlternatives) {
                allowBlockAlternatives = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                allowBlockAlternatives = " ".length();
            }
            this.allowBlockAlternatives = (allowBlockAlternatives != 0);
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.REDUCED_DEBUG_INFO) {
            int reducedDebugInfo;
            if (this.reducedDebugInfo) {
                reducedDebugInfo = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                reducedDebugInfo = " ".length();
            }
            this.reducedDebugInfo = (reducedDebugInfo != 0);
        }
        if (options == Options.ENTITY_SHADOWS) {
            int field_181151_V;
            if (this.field_181151_V) {
                field_181151_V = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                field_181151_V = " ".length();
            }
            this.field_181151_V = (field_181151_V != 0);
        }
        this.saveOptions();
    }
    
    public GameSettings() {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -" ".length();
        this.viewBobbing = (" ".length() != 0);
        this.fboEnable = (" ".length() != 0);
        this.limitFramerate = (0xE9 ^ 0x91);
        this.clouds = "  ".length();
        this.fancyGraphics = (" ".length() != 0);
        this.ambientOcclusion = "  ".length();
        this.resourcePacks = Lists.newArrayList();
        this.field_183018_l = Lists.newArrayList();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = (" ".length() != 0);
        this.chatLinks = (" ".length() != 0);
        this.chatLinksPrompt = (" ".length() != 0);
        this.chatOpacity = 1.0f;
        this.snooperEnabled = (" ".length() != 0);
        this.enableVsync = (" ".length() != 0);
        this.useVbo = ("".length() != 0);
        this.allowBlockAlternatives = (" ".length() != 0);
        this.reducedDebugInfo = ("".length() != 0);
        this.pauseOnLostFocus = (" ".length() != 0);
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = (" ".length() != 0);
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = (" ".length() != 0);
        this.mipmapLevels = (0x89 ^ 0x8D);
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = " ".length();
        this.streamSendMetadata = (" ".length() != 0);
        this.streamPreferredServer = GameSettings.I[0x79 ^ 0x1D];
        this.streamChatEnabled = "".length();
        this.streamChatUserFilter = "".length();
        this.streamMicToggleBehavior = "".length();
        this.field_181150_U = (" ".length() != 0);
        this.field_181151_V = (" ".length() != 0);
        this.keyBindForward = new KeyBinding(GameSettings.I[0xEE ^ 0x8B], 0x1 ^ 0x10, GameSettings.I[0x10 ^ 0x76]);
        this.keyBindLeft = new KeyBinding(GameSettings.I[0xDB ^ 0xBC], 0xDC ^ 0xC2, GameSettings.I[0x72 ^ 0x1A]);
        this.keyBindBack = new KeyBinding(GameSettings.I[0x45 ^ 0x2C], 0x85 ^ 0x9A, GameSettings.I[0x1E ^ 0x74]);
        this.keyBindRight = new KeyBinding(GameSettings.I[0x1 ^ 0x6A], 0x91 ^ 0xB1, GameSettings.I[0x58 ^ 0x34]);
        this.keyBindJump = new KeyBinding(GameSettings.I[0xFB ^ 0x96], 0x83 ^ 0xBA, GameSettings.I[0x36 ^ 0x58]);
        this.keyBindSneak = new KeyBinding(GameSettings.I[0x5E ^ 0x31], 0x37 ^ 0x1D, GameSettings.I[0xF9 ^ 0x89]);
        this.keyBindSprint = new KeyBinding(GameSettings.I[0x4F ^ 0x3E], 0xB0 ^ 0xAD, GameSettings.I[0x44 ^ 0x36]);
        this.keyBindInventory = new KeyBinding(GameSettings.I[0x38 ^ 0x4B], 0x55 ^ 0x47, GameSettings.I[0xB7 ^ 0xC3]);
        this.keyBindUseItem = new KeyBinding(GameSettings.I[0x2B ^ 0x5E], -(0x68 ^ 0xB), GameSettings.I[0x53 ^ 0x25]);
        this.keyBindDrop = new KeyBinding(GameSettings.I[0xE1 ^ 0x96], 0x30 ^ 0x20, GameSettings.I[0xCF ^ 0xB7]);
        this.keyBindAttack = new KeyBinding(GameSettings.I[0xBA ^ 0xC3], -(0xF0 ^ 0x94), GameSettings.I[0x6F ^ 0x15]);
        this.keyBindPickBlock = new KeyBinding(GameSettings.I[0x6B ^ 0x10], -(0xEA ^ 0x88), GameSettings.I[0xC ^ 0x70]);
        this.keyBindChat = new KeyBinding(GameSettings.I[0xE8 ^ 0x95], 0x69 ^ 0x7D, GameSettings.I[0x7E ^ 0x0]);
        this.keyBindPlayerList = new KeyBinding(GameSettings.I[56 + 50 - 78 + 99], 0x13 ^ 0x1C, GameSettings.I[53 + 37 - 3 + 41]);
        this.keyBindCommand = new KeyBinding(GameSettings.I[42 + 80 - 86 + 93], 0x95 ^ 0xA0, GameSettings.I[79 + 55 - 14 + 10]);
        this.keyBindScreenshot = new KeyBinding(GameSettings.I[24 + 98 - 88 + 97], 0xBD ^ 0x81, GameSettings.I[25 + 72 - 42 + 77]);
        this.keyBindTogglePerspective = new KeyBinding(GameSettings.I[11 + 77 - 87 + 132], 0x13 ^ 0x2C, GameSettings.I[66 + 46 + 4 + 18]);
        this.keyBindSmoothCamera = new KeyBinding(GameSettings.I[14 + 31 - 2 + 92], "".length(), GameSettings.I[113 + 8 - 59 + 74]);
        this.keyBindFullscreen = new KeyBinding(GameSettings.I[47 + 127 - 129 + 92], 0x1F ^ 0x48, GameSettings.I[37 + 62 + 26 + 13]);
        this.keyBindSpectatorOutlines = new KeyBinding(GameSettings.I[67 + 71 - 54 + 55], "".length(), GameSettings.I[24 + 18 + 5 + 93]);
        this.keyBindStreamStartStop = new KeyBinding(GameSettings.I[20 + 6 + 41 + 74], 0x7F ^ 0x3F, GameSettings.I[106 + 111 - 199 + 124]);
        this.keyBindStreamPauseUnpause = new KeyBinding(GameSettings.I[34 + 51 + 16 + 42], 0xF7 ^ 0xB6, GameSettings.I[122 + 78 - 165 + 109]);
        this.keyBindStreamCommercials = new KeyBinding(GameSettings.I[29 + 27 - 36 + 125], "".length(), GameSettings.I[119 + 60 - 88 + 55]);
        this.keyBindStreamToggleMic = new KeyBinding(GameSettings.I[121 + 18 - 4 + 12], "".length(), GameSettings.I[55 + 118 - 77 + 52]);
        final KeyBinding[] keyBindsHotbar = new KeyBinding[0x83 ^ 0x8A];
        keyBindsHotbar["".length()] = new KeyBinding(GameSettings.I[65 + 3 + 54 + 27], "  ".length(), GameSettings.I[106 + 29 - 96 + 111]);
        keyBindsHotbar[" ".length()] = new KeyBinding(GameSettings.I[44 + 71 + 29 + 7], "   ".length(), GameSettings.I[76 + 115 - 137 + 98]);
        keyBindsHotbar["  ".length()] = new KeyBinding(GameSettings.I[8 + 102 - 36 + 79], 0x72 ^ 0x76, GameSettings.I[1 + 22 + 115 + 16]);
        keyBindsHotbar["   ".length()] = new KeyBinding(GameSettings.I[0 + 49 + 53 + 53], 0x5A ^ 0x5F, GameSettings.I[37 + 29 - 19 + 109]);
        keyBindsHotbar[0x19 ^ 0x1D] = new KeyBinding(GameSettings.I[144 + 133 - 222 + 102], 0x70 ^ 0x76, GameSettings.I[112 + 59 - 48 + 35]);
        keyBindsHotbar[0x94 ^ 0x91] = new KeyBinding(GameSettings.I[57 + 129 - 76 + 49], 0x71 ^ 0x76, GameSettings.I[119 + 117 - 192 + 116]);
        keyBindsHotbar[0x5 ^ 0x3] = new KeyBinding(GameSettings.I[1 + 125 - 112 + 147], 0x2A ^ 0x22, GameSettings.I[107 + 103 - 144 + 96]);
        keyBindsHotbar[0x1D ^ 0x1A] = new KeyBinding(GameSettings.I[33 + 124 - 116 + 122], 0x99 ^ 0x90, GameSettings.I[157 + 132 - 186 + 61]);
        keyBindsHotbar[0x99 ^ 0x91] = new KeyBinding(GameSettings.I[93 + 148 - 176 + 100], 0x55 ^ 0x5F, GameSettings.I[58 + 93 - 92 + 107]);
        this.keyBindsHotbar = keyBindsHotbar;
        this.ofFogType = " ".length();
        this.ofFogStart = 0.8f;
        this.ofMipmapType = "".length();
        this.ofOcclusionFancy = ("".length() != 0);
        this.ofSmoothFps = ("".length() != 0);
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = "".length();
        this.ofAfLevel = " ".length();
        this.ofClouds = "".length();
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = "".length();
        this.ofRain = "".length();
        this.ofDroppedItems = "".length();
        this.ofBetterGrass = "   ".length();
        this.ofAutoSaveTicks = 2031 + 1015 - 1749 + 2703;
        this.ofLagometer = ("".length() != 0);
        this.ofProfiler = ("".length() != 0);
        this.ofShowFps = ("".length() != 0);
        this.ofWeather = (" ".length() != 0);
        this.ofSky = (" ".length() != 0);
        this.ofStars = (" ".length() != 0);
        this.ofSunMoon = (" ".length() != 0);
        this.ofVignette = "".length();
        this.ofChunkUpdates = " ".length();
        this.ofChunkLoading = "".length();
        this.ofChunkUpdatesDynamic = ("".length() != 0);
        this.ofTime = "".length();
        this.ofClearWater = ("".length() != 0);
        this.ofBetterSnow = ("".length() != 0);
        this.ofFullscreenMode = GameSettings.I[104 + 109 - 170 + 124];
        this.ofSwampColors = (" ".length() != 0);
        this.ofRandomMobs = (" ".length() != 0);
        this.ofSmoothBiomes = (" ".length() != 0);
        this.ofCustomFonts = (" ".length() != 0);
        this.ofCustomColors = (" ".length() != 0);
        this.ofCustomSky = (" ".length() != 0);
        this.ofShowCapes = (" ".length() != 0);
        this.ofConnectedTextures = "  ".length();
        this.ofNaturalTextures = ("".length() != 0);
        this.ofFastMath = ("".length() != 0);
        this.ofFastRender = (" ".length() != 0);
        this.ofTranslucentBlocks = "".length();
        this.ofAnimatedWater = "".length();
        this.ofAnimatedLava = "".length();
        this.ofAnimatedFire = (" ".length() != 0);
        this.ofAnimatedPortal = (" ".length() != 0);
        this.ofAnimatedRedstone = (" ".length() != 0);
        this.ofAnimatedExplosion = (" ".length() != 0);
        this.ofAnimatedFlame = (" ".length() != 0);
        this.ofAnimatedSmoke = (" ".length() != 0);
        this.ofVoidParticles = (" ".length() != 0);
        this.ofWaterParticles = (" ".length() != 0);
        this.ofRainSplash = (" ".length() != 0);
        this.ofPortalParticles = (" ".length() != 0);
        this.ofPotionParticles = (" ".length() != 0);
        this.ofFireworkParticles = (" ".length() != 0);
        this.ofDrippingWaterLava = (" ".length() != 0);
        this.ofAnimatedTerrain = (" ".length() != 0);
        this.ofAnimatedTextures = (" ".length() != 0);
        final KeyBinding[] array = new KeyBinding[0x30 ^ 0x28];
        array["".length()] = this.keyBindAttack;
        array[" ".length()] = this.keyBindUseItem;
        array["  ".length()] = this.keyBindForward;
        array["   ".length()] = this.keyBindLeft;
        array[0x90 ^ 0x94] = this.keyBindBack;
        array[0x45 ^ 0x40] = this.keyBindRight;
        array[0xA7 ^ 0xA1] = this.keyBindJump;
        array[0xB7 ^ 0xB0] = this.keyBindSneak;
        array[0x7D ^ 0x75] = this.keyBindSprint;
        array[0x67 ^ 0x6E] = this.keyBindDrop;
        array[0x2D ^ 0x27] = this.keyBindInventory;
        array[0x72 ^ 0x79] = this.keyBindChat;
        array[0x25 ^ 0x29] = this.keyBindPlayerList;
        array[0x2 ^ 0xF] = this.keyBindPickBlock;
        array[0xB ^ 0x5] = this.keyBindCommand;
        array[0x2E ^ 0x21] = this.keyBindScreenshot;
        array[0x58 ^ 0x48] = this.keyBindTogglePerspective;
        array[0x67 ^ 0x76] = this.keyBindSmoothCamera;
        array[0x9 ^ 0x1B] = this.keyBindStreamStartStop;
        array[0xBA ^ 0xA9] = this.keyBindStreamPauseUnpause;
        array[0x77 ^ 0x63] = this.keyBindStreamCommercials;
        array[0x6B ^ 0x7E] = this.keyBindStreamToggleMic;
        array[0x30 ^ 0x26] = this.keyBindFullscreen;
        array[0x1E ^ 0x9] = this.keyBindSpectatorOutlines;
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])array, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = GameSettings.I[162 + 12 - 44 + 38];
        this.fovSetting = 70.0f;
        this.language = GameSettings.I[135 + 122 - 218 + 130];
        this.forceUnicodeFont = ("".length() != 0);
    }
    
    private float parseFloat(final String s) {
        float float1;
        if (s.equals(GameSettings.I[83 + 127 - 144 + 231])) {
            float1 = 1.0f;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (s.equals(GameSettings.I[96 + 154 - 27 + 75])) {
            float1 = 0.0f;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            float1 = Float.parseFloat(s);
        }
        return float1;
    }
    
    static {
        I();
        OFF = "   ".length();
        DEFAULT = "".length();
        FANCY = "  ".length();
        CL_THREADED = "  ".length();
        ANIM_GENERATED = " ".length();
        __OBFID = GameSettings.I["".length()];
        FAST = " ".length();
        ANIM_ON = "".length();
        ANIM_OFF = "  ".length();
        CL_DEFAULT = "".length();
        CL_SMOOTH = " ".length();
        DEFAULT_STR = GameSettings.I[" ".length()];
        logger = LogManager.getLogger();
        gson = new Gson();
        typeListString = new ParameterizedType() {
            private static final String __OBFID;
            private static final String[] I;
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0001\u001f\u0016@\\rcyFYs", "BSIpl");
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                final Type[] array = new Type[" ".length()];
                array["".length()] = String.class;
                return array;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            static {
                I();
                __OBFID = GameSettings$1.I["".length()];
            }
        };
        final String[] guiscales = new String[0x2E ^ 0x2A];
        guiscales["".length()] = GameSettings.I["  ".length()];
        guiscales[" ".length()] = GameSettings.I["   ".length()];
        guiscales["  ".length()] = GameSettings.I[0x2E ^ 0x2A];
        guiscales["   ".length()] = GameSettings.I[0x43 ^ 0x46];
        GUISCALES = guiscales;
        final String[] particles = new String["   ".length()];
        particles["".length()] = GameSettings.I[0xB1 ^ 0xB7];
        particles[" ".length()] = GameSettings.I[0x19 ^ 0x1E];
        particles["  ".length()] = GameSettings.I[0xB2 ^ 0xBA];
        PARTICLES = particles;
        final String[] ambient_OCCLUSIONS = new String["   ".length()];
        ambient_OCCLUSIONS["".length()] = GameSettings.I[0x26 ^ 0x2F];
        ambient_OCCLUSIONS[" ".length()] = GameSettings.I[0xA6 ^ 0xAC];
        ambient_OCCLUSIONS["  ".length()] = GameSettings.I[0x68 ^ 0x63];
        AMBIENT_OCCLUSIONS = ambient_OCCLUSIONS;
        final String[] stream_COMPRESSIONS = new String["   ".length()];
        stream_COMPRESSIONS["".length()] = GameSettings.I[0x37 ^ 0x3B];
        stream_COMPRESSIONS[" ".length()] = GameSettings.I[0x5C ^ 0x51];
        stream_COMPRESSIONS["  ".length()] = GameSettings.I[0x74 ^ 0x7A];
        STREAM_COMPRESSIONS = stream_COMPRESSIONS;
        final String[] stream_CHAT_MODES = new String["   ".length()];
        stream_CHAT_MODES["".length()] = GameSettings.I[0x44 ^ 0x4B];
        stream_CHAT_MODES[" ".length()] = GameSettings.I[0xA6 ^ 0xB6];
        stream_CHAT_MODES["  ".length()] = GameSettings.I[0xBD ^ 0xAC];
        STREAM_CHAT_MODES = stream_CHAT_MODES;
        final String[] stream_CHAT_FILTER_MODES = new String["   ".length()];
        stream_CHAT_FILTER_MODES["".length()] = GameSettings.I[0x7C ^ 0x6E];
        stream_CHAT_FILTER_MODES[" ".length()] = GameSettings.I[0x2E ^ 0x3D];
        stream_CHAT_FILTER_MODES["  ".length()] = GameSettings.I[0x2E ^ 0x3A];
        STREAM_CHAT_FILTER_MODES = stream_CHAT_FILTER_MODES;
        final String[] stream_MIC_MODES = new String["  ".length()];
        stream_MIC_MODES["".length()] = GameSettings.I[0x7B ^ 0x6E];
        stream_MIC_MODES[" ".length()] = GameSettings.I[0x97 ^ 0x81];
        STREAM_MIC_MODES = stream_MIC_MODES;
        final String[] field_181149_aW2 = new String["   ".length()];
        field_181149_aW2["".length()] = GameSettings.I[0x8B ^ 0x9C];
        field_181149_aW2[" ".length()] = GameSettings.I[0xAE ^ 0xB6];
        field_181149_aW2["  ".length()] = GameSettings.I[0x13 ^ 0xA];
        field_181149_aW = field_181149_aW2;
    }
    
    public static String getKeyDisplayString(final int n) {
        String s2;
        if (n < 0) {
            final String s = GameSettings.I[108 + 81 - 171 + 152];
            final Object[] array = new Object[" ".length()];
            array["".length()] = n + (0xF5 ^ 0x90);
            s2 = I18n.format(s, array);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (n < 27 + 203 - 207 + 233) {
            s2 = Keyboard.getKeyName(n);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            final String s3 = GameSettings.I[138 + 118 - 155 + 70];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = (char)(n - (227 + 233 - 412 + 208));
            s2 = String.format(s3, array2).toUpperCase();
        }
        return s2;
    }
    
    private static void I() {
        (I = new String[270 + 204 - 471 + 643])["".length()] = I("7\u00030tSD\u007f_rVD", "tOoDc");
        GameSettings.I[" ".length()] = I(" \t)+=\b\u0018", "dlOJH");
        GameSettings.I["  ".length()] = I("!>3;\u0005 =i5\u001f'\u001d$3\u0006+`&'\u001e!", "NNGRj");
        GameSettings.I["   ".length()] = I("\u0019\u0001.*\f\u0018\u0002t$\u0016\u001f\"9\"\u000f\u0013_).\u0002\u001a\u001d", "vqZCc");
        GameSettings.I[0x6 ^ 0x2] = I("*\u00193\u001f#+\u001ai\u00119,:$\u0017  G)\u0019>(\b+", "EiGvL");
        GameSettings.I[0x1A ^ 0x1F] = I("\u0007\u001f\f\u000e\u001b\u0006\u001cV\u0000\u0001\u0001<\u001b\u0006\u0018\rA\u0014\u0006\u0006\u000f\n", "hoxgt");
        GameSettings.I[0x13 ^ 0x15] = I("\u0006\u0015\u0013$\n\u0007\u0016I=\u0004\u001b\u0011\u000e.\t\f\u0016I,\t\u0005", "iegMe");
        GameSettings.I[0x6C ^ 0x6B] = I("\u0006\u0018\f?\u001f\u0007\u001bV&\u0011\u001b\u001c\u00115\u001c\f\u001bV2\u0015\n\u001a\u001d7\u0003\f\f", "ihxVp");
        GameSettings.I[0xAC ^ 0xA4] = I(">3\u001f \u0018?0E9\u0016#7\u0002*\u001b40E$\u001e?*\u0006(\u001b", "QCkIw");
        GameSettings.I[0x3 ^ 0xA] = I("&\u0016\u0003\u0010\u000e'\u0015Y\u0018\u000eg\t\u0011\u001f", "Ifwya");
        GameSettings.I[0x88 ^ 0x82] = I("\u0005&\f0\u000e\u0004%V8\u000eD;\u00117", "jVxYa");
        GameSettings.I[0x43 ^ 0x48] = I("\b\u0000\u001f\n\u0002\t\u0003E\u0002\u0002I\u001d\n\u001b", "gpkcm");
        GameSettings.I[0xAF ^ 0xA3] = I("5#\u0000\u0005$4 Z\u001f?(6\u0015\u0001e9<\u0019\u001c9? \u0007\u0005$4}\u0018\u0003<", "ZStlK");
        GameSettings.I[0x92 ^ 0x9F] = I(")\u0011?%)(\u0012e?24\u0004*!h%\u000e&<4#\u00128%)(O&)\"/\u0014&", "FaKLF");
        GameSettings.I[0x6F ^ 0x61] = I(" \u0018\u001e*\n!\u001bD0\u0011=\r\u000b.K,\u0007\u00073\u0017*\u001b\u0019*\n!F\u0002*\u0002'", "OhjCe");
        GameSettings.I[0x1D ^ 0x12] = I("\u0000\t\u001e\r\t\u0001\nD\u0017\u0012\u001d\u001c\u000b\tH\f\u0011\u000b\u0010H\n\u0017\u000b\u0006\n\n\u001dD\u0017\u0012\u001d\u001c\u000b\t\u000f\u0001\u001e", "oyjdf");
        GameSettings.I[0x52 ^ 0x42] = I(";28\u00018:1b\u001b#&'-\u0005y7*-\u001cy1,-\n;1&b\t;##5\u001b", "TBLhW");
        GameSettings.I[0x65 ^ 0x74] = I("\u00059\u0017:\u001a\u0004:M \u0001\u0018,\u0002>[\t!\u0002'[\u000f'\u00021\u0019\u000f-M=\u0010\u001c,\u0011", "jIcSu");
        GameSettings.I[0xA8 ^ 0xBA] = I("-\n%\u001d<,\t\u007f\u0007'0\u001f0\u0019}!\u00120\u0000}7\t4\u0006\u0015+\u0016%\u0011!l\u001b=\u0018", "BzQtS");
        GameSettings.I[0x9F ^ 0x8C] = I("9\u001b\u0005=(8\u0018_'3$\u000e\u00109i5\u0003\u0010 i#\u0018\u0014&\u0001?\u0007\u000515x\u0018\u000464", "VkqTG");
        GameSettings.I[0xAD ^ 0xB9] = I("\b\u00043\u0018'\t\u0007i\u0002<\u0015\u0011&\u001cf\u0004\u001c&\u0005f\u0012\u0007\"\u0003\u000e\u000e\u00183\u0014:I\u0019(\u0015;", "gtGqH");
        GameSettings.I[0x13 ^ 0x6] = I(";(\u0011\u001c\t:+K\u0006\u0012&=\u0004\u0018H91\u0006*\u0012;?\u0002\u0019\u0003z5\u0010\u0001\u0003", "TXeuf");
        GameSettings.I[0xA0 ^ 0xB6] = I("\b\u0013\u0015\u0011 \t\u0010O\u000b;\u0015\u0006\u0000\u0015a\n\n\u0002';\b\u0004\u0006\u0014*I\u0017\u0000\u0014$", "gcaxO");
        GameSettings.I[0x82 ^ 0x95] = I("?<\u001b0+>?A6\"6", "PLoYD");
        GameSettings.I[0xDE ^ 0xC6] = I(" \u0001$-\t!\u0002~#\u0014.\u00018-\u0005<_6%\u0015;", "OqPDf");
        GameSettings.I[0x66 ^ 0x7F] = I("#\n5\r$\"\to\u00039-\n)\r(?T'\u0005%/\u0003", "LzAdK");
        GameSettings.I[0xBA ^ 0xA0] = I("", "Knakn");
        GameSettings.I[0xA ^ 0x11] = I("*=\u001f~\u001f.*\u00111\u000b%", "AXfPy");
        GameSettings.I[0x52 ^ 0x4E] = I("%  y\u0001/1<0\r<,<$L#*/2\u000f++-", "NEYWb");
        GameSettings.I[0x9D ^ 0x80] = I("\u001e#,\u007f\u0005\u0010 !", "uFUQi");
        GameSettings.I[0x5F ^ 0x41] = I("(\u001d\u001ec!\"\f\u0002*-1\u0011\u0002>l.\u0017\u0011(/&\u0016\u0013", "CxgMB");
        GameSettings.I[0x3E ^ 0x21] = I("\u00195\u000f~\u0010\u00133\u001d", "rPvPr");
        GameSettings.I[0xA1 ^ 0x81] = I("$\u001c5B\u0015.\r)\u000b\u0019=\u0010)\u001fX\"\u0016:\t\u001b*\u00178", "OyLlv");
        GameSettings.I[0x77 ^ 0x56] = I("\u0019\u0012\u001fL;\u001b\u0010\u000e\u0016", "rwfbI");
        GameSettings.I[0x22 ^ 0x0] = I("=\u001f)y)7\u000e50%$\u00135$d;\u0015&2'3\u0014$", "VzPWJ");
        GameSettings.I[0x12 ^ 0x31] = I("#\u0006\u0011{.=\u000e\u0018", "HchUD");
        GameSettings.I[0x5F ^ 0x7B] = I("1\u000b\u001bC\f;\u001a\u0007\n\u0000(\u0007\u0007\u001eA7\u0001\u0014\b\u0002?\u0000\u0016", "Znbmo");
        GameSettings.I[0x31 ^ 0x14] = I("\u00040\fE\u0015\u00010\u0014\u0000", "oUukf");
        GameSettings.I[0x8B ^ 0xAD] = I("\u000e!2{9\u00040.25\u0017-.&t\b+=07\u0000*?", "eDKUZ");
        GameSettings.I[0xB0 ^ 0x97] = I(";?\u0010x\u0015 (\u00008\u0012", "PZiVf");
        GameSettings.I[0xE ^ 0x26] = I("\u0018\u001d\by:\u0012\f\u001406\u0001\u0011\u0014$w\u001e\u0017\u000724\u0016\u0016\u0005", "sxqWY");
        GameSettings.I[0x9C ^ 0xB5] = I("2=\u0018{ 7.\u0004;=6*\u0018", "YXaUI");
        GameSettings.I[0xAC ^ 0x86] = I("\t\u000e#F(\u0003\u001f?\u000f$\u0010\u0002?\u001be\u000b\u0005,\r%\u0016\u0004(\u0011", "bkZhK");
        GameSettings.I[0x98 ^ 0xB3] = I("\u0002\u00105A\r\u001a\u0010", "iuLox");
        GameSettings.I[0x1 ^ 0x2D] = I("\u0007\u0015\u0015[\u000b\r\u0004\t\u0012\u0007\u001e\u0019\t\u0006F\u000b\u0011\u0001\u0010\u0018\u0000\u0011\u0015", "lpluh");
        GameSettings.I[0x60 ^ 0x4D] = I("\u000e+?l\u0013\u0017!6", "eNFBw");
        GameSettings.I[0x95 ^ 0xBB] = I("8<\u0001W\u001b2-\u001d\u001e\u0017!0\u001d\nV48\u0015\u001c\b?8\u0001", "SYxyx");
        GameSettings.I[0x60 ^ 0x4F] = I("\u0002\u000f!O-\u001d\u001e9\u0002'", "ijXaL");
        GameSettings.I[0x6C ^ 0x5C] = I("&\"\u0015Z(,3\t\u0013$?.\t\u0007e*&\u0001\u0011;!&\u0015", "MGltK");
        GameSettings.I[0x72 ^ 0x43] = I("1.3j%3(!\r!?&", "ZKJDU");
        GameSettings.I[0xB6 ^ 0x84] = I("\u0018\u0012(O(\u0012\u00034\u0006$\u0001\u001e4\u0012e\u0014\u0016<\u0004;\u001f\u0016(", "swQaK");
        GameSettings.I[0x6F ^ 0x5C] = I("\u001e0\u0017[\u0004\u001d4\u001a", "uUnug");
        GameSettings.I[0x47 ^ 0x73] = I("/-4W7%<(\u001e;6!(\nz)=!\r=4$,\u000016", "DHMyT");
        GameSettings.I[0x80 ^ 0xB5] = I("\u0018)\u000eO\u0006\u001f-\u000e\u0004\u0004\u001f%\u0004\u0015", "sLwav");
        GameSettings.I[0xB5 ^ 0x83] = I("3\u0014*M\u00059\u00056\u0004\t*\u00186\u0010H5\u0004?\u0017\u000f(\u001d2\u001a\u0003*", "XqScf");
        GameSettings.I[0xB1 ^ 0x86] = I("\u00116\u000fJ\t\u0015>\u001b\u0005\u0004\u001e", "zSvdj");
        GameSettings.I[0x29 ^ 0x11] = I(";!\u0011O*10\r\u0006&\"-\r\u0012g=1\u0004\u0015  (\t\u0018,\"", "PDhaI");
        GameSettings.I[0x59 ^ 0x60] = I("\u0012-\u0015K:\u001a:\t\u0000'\n \u0003\u0011", "yHleI");
        GameSettings.I[0x16 ^ 0x2C] = I(",\u000b\u001bB\u0013&\u001a\u0007\u000b\u001f5\u0007\u0007\u001f^*\u0007\u0011\u000f", "Gnblp");
        GameSettings.I[0x9D ^ 0xA6] = I("%?2{\u0012!=,9\u0003\u001e?9&\u0016+9?<\u0010+", "NZKUf");
        GameSettings.I[0x71 ^ 0x4D] = I("\u0000\n\rD\u001b\n\u001b\u0011\r\u0017\u0019\u0006\u0011\u0019V\u0006\u0006\u0007\t", "kotjx");
        GameSettings.I[0x7C ^ 0x41] = I("\u001c\u0006\u001db\n\u001a\f\u000b8\u00114\u0002\t)\u000b\u0016", "wcdLy");
        GameSettings.I[0x97 ^ 0xA9] = I("\u000e\u0002;d$\u0004\u0013'-(\u0017\u000e'9i\b\u000e1)", "egBJG");
        GameSettings.I[0x15 ^ 0x2A] = I("'0\u0011z\u000299\u0004'\u0007>0\r:", "LUhTd");
        GameSettings.I[0xE0 ^ 0xA0] = I("?\u0002\u0010j-5\u0013\f#!&\u000e\f7`9\u000e\u001a'", "TgiDN");
        GameSettings.I[0x14 ^ 0x55] = I("?=+]\u0010$=1\u0007\u0002 7 <\u0016 4;\u001d\u0006'", "TXRsc");
        GameSettings.I[0x13 ^ 0x51] = I("; (Z\"114\u0013.\",4\u0007o=,\"\u0017", "PEQtA");
        GameSettings.I[0x21 ^ 0x62] = I("\n\u0000 y\u0010\u0015\u0017<6\u000e2\u00118%\u00172\u00116'", "aeYWc");
        GameSettings.I[0x2 ^ 0x46] = I("3\u0006\u001cV!9\u0017\u0000\u001f-*\n\u0000\u000bl+\u0017\u0017\u001d#5", "XcexB");
        GameSettings.I[0xE7 ^ 0xA2] = I("\u000f'\nK<\u00100\u0016\u0004\"4#\u0006\u0016*1,\u0003\u0004:\u0017'", "dBseO");
        GameSettings.I[0x7E ^ 0x38] = I("< \u0016j\u001761\n#\u001b%,\n7Z$1\u001d!\u0015:", "WEoDt");
        GameSettings.I[0xF5 ^ 0xB2] = I("\u0004\u001d\rJ\t\u001b\n\u0011\u0005\u0017,\u0017\u0019\t\u001f\u001d\u001b\u001d\u0005\u0016", "oxtdz");
        GameSettings.I[0x24 ^ 0x6C] = I("\u0005\u001f\u0011e3\u000f\u000e\r,?\u001c\u0013\r8~\u001d\u000e\u001a.1\u0003", "nzhKP");
        GameSettings.I[0xC1 ^ 0x88] = I(".\n5A'1\u001d)\u000e9\u0011\u0000+\b8 \"%\f", "EoLoT");
        GameSettings.I[0x48 ^ 0x2] = I("\u0005\u0013)v\r\u000f\u00025?\u0001\u001c\u001f5+@\u001d\u0002\"=\u000f\u0003", "nvPXn");
        GameSettings.I[0x2C ^ 0x67] = I("\u001c\u000b\u001bE&\u0018\u001a\u0000\n<Y_", "wnbkN");
        GameSettings.I[0x8F ^ 0xC3] = I("\u0018*8I5\u0012;$\u00009\u0001&$\u0014x\u001a!7\u00028\u0007 3\u001e", "sOAgV");
        GameSettings.I[0xCD ^ 0x80] = I("\u0007\u00157W\u0001\u0003\u0004,\u0018\u001bBB", "lpNyi");
        GameSettings.I[0x77 ^ 0x39] = I("\r\r>h\u0002\u0007\u001c\"!\u000e\u0014\u0001\"5O\u000f\u00061#\u000f\u0012\u00075?", "fhGFa");
        GameSettings.I[0x5F ^ 0x10] = I(" \u001c\u001b|'$\r\u00003=eJ", "KybRO");
        GameSettings.I[0x12 ^ 0x42] = I(":&1M\u000007-\u0004\f#*-\u0010M8->\u0006\r%,:\u001a", "QCHcc");
        GameSettings.I[0xE ^ 0x5F] = I("\u00185\u001c\u007f \u001c$\u00070:]d", "sPeQH");
        GameSettings.I[0x4D ^ 0x1F] = I("\u0001'!l1\u000b6=%=\u0018+=1|\u0003,.'<\u001e-*;", "jBXBR");
        GameSettings.I[0x48 ^ 0x1B] = I("3<\u001bj,7-\u0000%6vl", "XYbDD");
        GameSettings.I[0x96 ^ 0xC2] = I("$\u0017?{\u001a.\u0006#2\u0016=\u001b#&W&\u001c00\u0017;\u001d4,", "OrFUy");
        GameSettings.I[0xC3 ^ 0x96] = I("2\u0011\u0017X 6\u0000\f\u0017:wB", "YtnvH");
        GameSettings.I[0xF3 ^ 0xA5] = I("?-#z\r5<?3\u0001&!?'@=&,1\u0000 '(-", "THZTn");
        GameSettings.I[0xD7 ^ 0x80] = I("\u001d(.`\n\u001995/\u0010Xz", "vMWNb");
        GameSettings.I[0xDA ^ 0x82] = I("&&\u001fZ/,7\u0003\u0013#?*\u0003\u0007b$-\u0010\u0011\"9,\u0014\r", "MCftL");
        GameSettings.I[0x1B ^ 0x42] = I(";\t)H\u0002?\u00182\u0007\u0018~T", "PlPfj");
        GameSettings.I[0x9B ^ 0xC1] = I("(')d\"\"65-.1+59o*,&//7-\"3", "CBPJA");
        GameSettings.I[0x5D ^ 0x6] = I("\u000028X;\u0004##\u0017!En", "kWAvS");
        GameSettings.I[0x1F ^ 0x43] = I("\r\u000f\u0003~\t\u0007\u001e\u001f7\u0005\u0014\u0003\u001f#D\u000f\u0004\f5\u0004\u0012\u0005\b)", "fjzPj");
        GameSettings.I[0x46 ^ 0x1B] = I("<=$\u000e\u000f\u0014,", "xXBoz");
        GameSettings.I[0x78 ^ 0x26] = I("", "lzqDE");
        GameSettings.I[0xEF ^ 0xB0] = I("\u0012\r(\u0013)", "wcwFz");
        GameSettings.I[0x6 ^ 0x66] = I("\u0016%\u000e\u000e;\u0017&T\u0013,\r", "yUzgT");
        GameSettings.I[0x1A ^ 0x7B] = I("5#\u001d>\u00054 \u00061D.+\u001d", "ZSiWj");
        GameSettings.I[0x24 ^ 0x46] = I("\u000b\t\u001a7", "QfuZv");
        GameSettings.I[0x4F ^ 0x2C] = I("\f\u0017\u001ay%\u0006\u0006\u00060)\u0015\u001b\u0006$h\n\u001b\u00104", "grcWF");
        GameSettings.I[0x46 ^ 0x22] = I("", "HhXDW");
        GameSettings.I[0xF9 ^ 0x9C] = I(",\b\u001bO\u0014(\u001f\u0015\u0000\u0000#", "Gmbar");
        GameSettings.I[0x64 ^ 0x2] = I(":.<D00? \r<#\" \u0019}<$3\u000f>4%1", "QKEjS");
        GameSettings.I[0xF ^ 0x68] = I("3\u0016\u0018x\u000f=\u0015\u0015", "XsaVc");
        GameSettings.I[0x49 ^ 0x21] = I("\u001d\u0007\u000fh*\u0017\u0016\u0013!&\u0004\u000b\u00135g\u001b\r\u0000#$\u0013\f\u0002", "vbvFI");
        GameSettings.I[0x6F ^ 0x6] = I(";/-{\u001a1)?", "PJTUx");
        GameSettings.I[0x0 ^ 0x6A] = I("8\u000e\u001fd\r2\u001f\u0003-\u0001!\u0002\u00039@>\u0004\u0010/\u00036\u0005\u0012", "SkfJn");
        GameSettings.I[0xA ^ 0x61] = I("\u0007'<c\n\u0005%-9", "lBEMx");
        GameSettings.I[0xF6 ^ 0x9A] = I("\u001b\u0011.W9\u0011\u00002\u001e5\u0002\u001d2\nt\u001d\u001b!\u001c7\u0015\u001a#", "ptWyZ");
        GameSettings.I[0x58 ^ 0x35] = I(" =\u0017~\u0007>5\u001e", "KXnPm");
        GameSettings.I[0x29 ^ 0x47] = I("\b)\tX\u000f\u00028\u0015\u0011\u0003\u0011%\u0015\u0005B\u000e#\u0006\u0013\u0001\u0006\"\u0004", "cLpvl");
        GameSettings.I[0x4E ^ 0x21] = I("&\u00116A\u0010#\u0011.\u0004", "MtOoc");
        GameSettings.I[0x51 ^ 0x21] = I("\u00195\ni;\u0013$\u0016 7\u00009\u00164v\u001f?\u0005\"5\u0017>\u0007", "rPsGX");
        GameSettings.I[0x5A ^ 0x2B] = I("?#\u0013m+$4\u0003-,", "TFjCX");
        GameSettings.I[0x44 ^ 0x36] = I("\u0019\u0017/K3\u0013\u00063\u0002?\u0000\u001b3\u0016~\u001f\u001d \u0000=\u0017\u001c\"", "rrVeP");
        GameSettings.I[0x77 ^ 0x4] = I("(6\u0012J\u0003-%\u000e\n\u001e,!\u0012", "CSkdj");
        GameSettings.I[0xC6 ^ 0xB2] = I(".\u0003\u001ck($\u0012\u0000\"$7\u000f\u00006e,\b\u0013 %1\t\u0017<", "EfeEK");
        GameSettings.I[0xD2 ^ 0xA7] = I(">\u0010\r]2&\u0010", "UutsG");
        GameSettings.I[0xE ^ 0x78] = I("*\u0003\u0013_3 \u0012\u000f\u0016?3\u000f\u000f\u0002~&\u0007\u0007\u0014 -\u0007\u0013", "AfjqP");
        GameSettings.I[0xD8 ^ 0xAF] = I("\u001a\f\u0014i<\u0003\u0006\u001d", "qimGX");
        GameSettings.I[0x26 ^ 0x5E] = I(")26A\u0006##*\b\n0>*\u001cK%6\"\n\u0015.66", "BWOoe");
        GameSettings.I[0xE8 ^ 0x91] = I("$-\u001ci#;<\u0004$)", "OHeGB");
        GameSettings.I[0xF6 ^ 0x8C] = I("\u0007\u00165z4\r\u0007)38\u001e\u001a)'y\u000b\u0012!1'\u0000\u00125", "lsLTW");
        GameSettings.I[0xF5 ^ 0x8E] = I("%\u001d7l\u0017'\u001b%\u000b\u0013+\u0015", "NxNBg");
        GameSettings.I[0x38 ^ 0x44] = I(";\u0010\u0015d\f1\u0001\t-\u0000\"\u001c\t9A7\u0014\u0001/\u001f<\u0014\u0015", "PulJo");
        GameSettings.I[0x65 ^ 0x18] = I(",+\u0001K\u0014//\f", "GNxew");
        GameSettings.I[0x40 ^ 0x3E] = I("\b\b\u0017e\u0002\u0002\u0019\u000b,\u000e\u0011\u0004\u000b8O\u000e\u0018\u0002?\b\u0013\u0001\u000f2\u0004\u0011", "cmnKa");
        GameSettings.I[16 + 59 - 15 + 67] = I("\u0007!\u000e[\u0018\u0000%\u000e\u0010\u001a\u0000-\u0004\u0001", "lDwuh");
        GameSettings.I[47 + 30 - 17 + 68] = I("\u0002\u001c:l6\b\r&%:\u001b\u0010&1{\u0004\f/6<\u0019\u0015\";0\u001b", "iyCBU");
        GameSettings.I[96 + 10 - 43 + 66] = I("<\u0001=~(8\t)1%3", "WdDPK");
        GameSettings.I[33 + 38 + 21 + 38] = I("\u0019\u00123w\f\u0013\u0003/>\u0000\u0000\u001e/*A\u001f\u0002&-\u0006\u0002\u001b+ \n\u0000", "rwJYo");
        GameSettings.I[2 + 10 + 26 + 93] = I("\u0018\u000f,T$\u0010\u00180\u001f9\u0000\u0002:\u000e", "sjUzW");
        GameSettings.I[45 + 66 - 37 + 58] = I("\u001f)\u0015|\u0011\u00158\t5\u001d\u0006%\t!\\\u0019%\u001f1", "tLlRr");
        GameSettings.I[61 + 3 + 6 + 63] = I("=\u0003\rW\f9\u0001\u0013\u0015\u001d\u0006\u0003\u0006\n\b3\u0005\u0000\u0010\u000e3", "Vftyx");
        GameSettings.I[121 + 10 - 20 + 23] = I(")\u001d/G!#\f3\u000e-0\u00113\u001al/\u0011%\n", "BxViB");
        GameSettings.I[93 + 14 + 1 + 27] = I("\r!\u0010{\u001d\u000b+\u0006!\u0006%%\u00040\u001c\u0007", "fDiUn");
        GameSettings.I[39 + 130 - 140 + 107] = I("(5\u000eG1\"$\u0012\u000e=19\u0012\u001a|.9\u0004\n", "CPwiR");
        GameSettings.I[80 + 125 - 194 + 126] = I("8(\u0015f\u0004&!\u0000;\u0001!(\t&", "SMlHb");
        GameSettings.I[48 + 31 - 40 + 99] = I(".\u0011?A\u0004$\u0000#\b\b7\u001d#\u001cI(\u001d5\f", "EtFog");
        GameSettings.I[97 + 30 - 21 + 33] = I("\u000e&;k\u0003\u0015&!1\u0011\u0011,0\n\u0005\u0011/++\u0015\u0016", "eCBEp");
        GameSettings.I[46 + 6 - 33 + 121] = I("-\u0011=J\u0015'\u0000!\u0003\u00194\u001d!\u0017X+\u001d7\u0007", "FtDdv");
        GameSettings.I[124 + 4 - 115 + 128] = I("\u0003\f#\u007f\u000b\u001c\u001b?0\u0015;\u001d;#\f;\u001d5!", "hiZQx");
        GameSettings.I[101 + 139 - 165 + 67] = I("\u0000\u000b\u0014|'\n\u001a\b5+\u0019\u0007\b!j\u0018\u001a\u001f7%\u0006", "knmRD");
        GameSettings.I[61 + 96 - 143 + 129] = I("%,\tc\u0017:;\u0015,\t\u001e(\u0005>\u0001\u001b'\u0000,\u0011=,", "NIpMd");
        GameSettings.I[48 + 119 - 143 + 120] = I("\u0007)\u0011c*\r8\r*&\u001e%\r>g\u001f8\u001a((\u0001", "lLhMI");
        GameSettings.I[115 + 23 - 90 + 97] = I("\u0011.\u001ff\u000b\u000e9\u0003)\u00159$\u000b%\u001d\b(\u000f)\u0014", "zKfHx");
        GameSettings.I[70 + 78 - 34 + 32] = I("\u0006\t#E\u0011\f\u0018?\f\u001d\u001f\u0005?\u0018\\\u001e\u0018(\u000e\u0013\u0000", "mlZkr");
        GameSettings.I[52 + 28 + 39 + 28] = I("97\u001b]:& \u0007\u0012$\u0006=\u0005\u0014%7\u001f\u000b\u0010", "RRbsI");
        GameSettings.I[114 + 29 - 139 + 144] = I(";\u0012\nZ11\u0003\u0016\u0013=\"\u001e\u0016\u0007|#\u0003\u0001\u00113=", "PwstR");
        GameSettings.I[138 + 6 - 94 + 99] = I("\n77f%\u000e&,)?Oc", "aRNHM");
        GameSettings.I[61 + 22 - 73 + 140] = I("\u0012\f\u001aZ0\u0018\u001d\u0006\u0013<\u000b\u0000\u0006\u0007}\u0010\u0007\u0015\u0011=\r\u0006\u0011\r", "yictS");
        GameSettings.I[104 + 62 - 83 + 68] = I(">\u0003!x?:\u0012:7%{T", "UfXVW");
        GameSettings.I[99 + 32 - 109 + 130] = I("\r\u0010\u0001w\u0001\u0007\u0001\u001d>\r\u0014\u001c\u001d*L\u000f\u001b\u000e<\f\u0012\u001a\n ", "fuxYb");
        GameSettings.I[91 + 31 - 111 + 142] = I("\u001a\u000b3O\u0018\u001e\u001a(\u0000\u0002_]", "qnJap");
        GameSettings.I[14 + 152 - 52 + 40] = I("&4\u000bb\b,%\u0017+\u0004?8\u0017?E$?\u0004)\u00059>\u00005", "MQrLk");
        GameSettings.I[20 + 67 - 54 + 122] = I("\f\f7i%\b\u001d,&?I]", "giNGM");
        GameSettings.I[88 + 44 - 88 + 112] = I("\f?+K\u0000\u0006.7\u0002\f\u001537\u0016M\u000e4$\u0000\r\u00135 \u001c", "gZRec");
        GameSettings.I[26 + 87 - 69 + 113] = I("\f.\u001aC*\b?\u0001\f0I~", "gKcmB");
        GameSettings.I[9 + 10 + 135 + 4] = I("\u000e\t\ry\u000f\u0004\u0018\u00110\u0003\u0017\u0005\u0011$B\f\u0002\u00022\u0002\u0011\u0003\u0006.", "eltWl");
        GameSettings.I[104 + 91 - 43 + 7] = I(",\u001d>f&(\f%)<iN", "GxGHN");
        GameSettings.I[93 + 16 - 103 + 154] = I("?\u00042E\u000e5\u0015.\f\u0002&\b.\u0018C=\u000f=\u000e\u0003 \u000e9\u0012", "TaKkm");
        GameSettings.I[157 + 69 - 163 + 98] = I(",\u0006\u001bC (\u0017\u0000\f:iT", "GcbmH");
        GameSettings.I[25 + 139 - 80 + 78] = I("\u0003\u00038}\u0016\t\u0012$4\u001a\u001a\u000f$ [\u0001\b76\u001b\u001c\t3*", "hfASu");
        GameSettings.I[17 + 14 - 27 + 159] = I("\u0005*([\u0000\u0001;3\u0014\u001a@w", "nOQuh");
        GameSettings.I[65 + 147 - 177 + 129] = I("!\b*b!+\u00196+-8\u00046?l#\u0003%),>\u0002!5", "JmSLB");
        GameSettings.I[137 + 76 - 124 + 76] = I("\"\u001d\u0016{2&\f\r4(gA", "IxoUZ");
        GameSettings.I[72 + 117 - 149 + 126] = I("=/\u001f_-7>\u0003\u0016!$#\u0003\u0002`?$\u0010\u0014 \"%\u0014\b", "VJfqN");
        GameSettings.I[72 + 98 - 26 + 23] = I("\t\b+$\u0006!\u0019", "MmMEs");
        GameSettings.I[138 + 75 - 180 + 135] = I("", "DanVP");
        GameSettings.I[103 + 91 - 174 + 149] = I("\u0016\u00045\u0013\u001d", "sjjFN");
        GameSettings.I[34 + 168 - 180 + 148] = I("\u001e6\u0012b\u000e\u001a&\u0018)!\u0000'\u001f#\r", "uSkLc");
        GameSettings.I[41 + 61 - 50 + 119] = I("u\"", "PAEBC");
        GameSettings.I[108 + 130 - 81 + 15] = I("Ql", "kLmdf");
        GameSettings.I[96 + 117 - 65 + 25] = I("\u001e\t6,\t\u001f\nl6\u0003\u001f\n+1\u000f\u0007\u00106<H\u001c\u0010,", "qyBEf");
        GameSettings.I[18 + 111 + 33 + 12] = I("\u00006\u0010'\t\u00015J=\u0003\u00015\r:\u000f\u0019/\u00107H\u0002'\u001c", "oFdNf");
        GameSettings.I[136 + 163 - 254 + 130] = I("J", "ohXNU");
        GameSettings.I[172 + 142 - 282 + 144] = I(",!&,,-\"|#,5\u007f?,-", "CQREC");
        GameSettings.I[134 + 98 - 168 + 113] = I("#5,;\u0019\"6v4\u0019:k53\u000e", "LEXRv");
        GameSettings.I[33 + 57 + 23 + 65] = I("\t(>\u0006\u0001\b+d\t\u001c\u00075/\u001d\u000f\u0012=\u0006\u0006\u0003\u000f,d\u0002\u000f\u001e", "fXJon");
        GameSettings.I[164 + 45 - 97 + 67] = I("r\u000e!\u000b", "RhQxf");
        GameSettings.I[129 + 25 - 150 + 176] = I("\u00002\u0000\u001a8\u00011Z\u0010;\u00007\u0010;2\u0006%\u001c\u0007y\u0002+\u001a", "oBtsW");
        GameSettings.I[135 + 9 - 121 + 158] = I("6;!\b778{\u000694&4O50%", "YKUaX");
        GameSettings.I[140 + 30 - 96 + 108] = I("\u001b59\u000e.\u001a6c\u0000 \u0019(,I,\u0015=", "tEMgA");
        GameSettings.I[180 + 158 - 268 + 113] = I("J", "axAuq");
        GameSettings.I[34 + 2 + 38 + 110] = I("B", "gEFHa");
        GameSettings.I[103 + 128 - 216 + 170] = I("C", "fPpcv");
        GameSettings.I[84 + 3 - 59 + 158] = I("o", "JFzDB");
        GameSettings.I[164 + 149 - 271 + 145] = I("\u00036", "sNwIu");
        GameSettings.I[43 + 71 + 74 + 0] = I("\u0014 ", "dXWZL");
        GameSettings.I[89 + 141 - 165 + 124] = I("\u0018\u0012", "hjWko");
        GameSettings.I[56 + 115 - 168 + 187] = I("E/+%?\u000e?", "eLCPQ");
        GameSettings.I[34 + 89 + 62 + 6] = I("::;.%;9a(,3", "UJOGJ");
        GameSettings.I[162 + 184 - 278 + 124] = I("v\r$2", "VkTAL");
        GameSettings.I[35 + 49 + 23 + 86] = I("O\u00004\u0016'", "oKVfT");
        GameSettings.I[19 + 71 + 47 + 57] = I("wZ@\u0005u0\u0004\u0003", "RtscU");
        GameSettings.I[155 + 121 - 203 + 122] = I("=9\u0003;\b<:Y=\u00014", "RIwRg");
        GameSettings.I[55 + 113 - 58 + 86] = I("\\", "yldwb");
        GameSettings.I[178 + 14 - 33 + 38] = I("\u000b\u001a\u0016$?\n\u0019L\">", "djbMP");
        GameSettings.I[131 + 173 - 237 + 131] = I("\u0018&?\u0005\u0015\u0019%e\u0003\u001c\u0011", "wVKlz");
        GameSettings.I[139 + 145 - 192 + 107] = I("\t\u0003\f\u0007 \b\u0000V\t=\u0007\u0003\u0010\u0007,\u0015]\u001e\u000f!\u0005\n", "fsxnO");
        GameSettings.I[185 + 29 - 165 + 151] = I("\u001a\u0017\u001f.\u000b\u001b\u0014E \u0016\u0014\u0017\u0003.\u0007\u0006I\r&\u0017\u0001", "ugkGd");
        GameSettings.I[68 + 192 - 207 + 148] = I("\u0006<<>\u0019\u0007?f0\u0004\b< >\u0015\u001ab.6\u0005\u001d", "iLHWv");
        GameSettings.I[57 + 115 - 54 + 84] = I("", "bJYjF");
        GameSettings.I[182 + 62 - 50 + 9] = I("k", "QdUDy");
        GameSettings.I[171 + 131 - 207 + 109] = I("\u0019;7<1'1,<=\u0000=4& \r", "tTBOT");
        GameSettings.I[143 + 149 - 140 + 53] = I("\u0016\u0004\u001c", "pkjMb");
        GameSettings.I[204 + 198 - 333 + 137] = I("63\u00008\"", "QRmUC");
        GameSettings.I[140 + 25 - 5 + 47] = I("1&:9\b#3'#\u0014", "BGNLz");
        GameSettings.I[118 + 198 - 298 + 190] = I("\u0004$\u001a\u0001\u0018\u0019\u0013!\u000b\u001f\u001e/", "mJldj");
        GameSettings.I[150 + 116 - 159 + 102] = I("\u0013#\u0003\u001c", "gQvyC");
        GameSettings.I[103 + 5 + 61 + 41] = I("\u0015?-\f'\u0015\u001e*\u001b6\u00064 \r", "gZChB");
        GameSettings.I[99 + 1 - 56 + 167] = I("\u0002%\u00021/\u0004<\u000e", "ePkbL");
        GameSettings.I[117 + 160 - 253 + 188] = I("\u00154\u000b&\u0004\u00069\u001c!", "eUyRm");
        GameSettings.I[91 + 160 - 118 + 80] = I("$\u000e\u0012.\u0002#\u0016", "Fapxk");
        GameSettings.I[94 + 29 - 21 + 112] = I("63\u0017\u000f", "BAbje");
        GameSettings.I[134 + 84 - 134 + 131] = I("\u0003\u0006&1.\u001b\u0018/e&", "bhGVB");
        GameSettings.I[75 + 195 - 241 + 187] = I("\u001d&\u0005\u0012", "iTpwV");
        GameSettings.I[123 + 63 + 9 + 22] = I("\u000f'+?7\u0011", "bFSyG");
        GameSettings.I[57 + 25 - 51 + 187] = I("'3\u001e\u0004& 3\u001d$", "AQqAH");
        GameSettings.I[53 + 58 - 26 + 134] = I("\u001d1\u0014\b", "iCamm");
        GameSettings.I[114 + 101 - 162 + 167] = I("\u0006\u001a3+\b\u0001\u000699\u0018", "bsUMa");
        GameSettings.I[192 + 54 - 230 + 205] = I("\u0010%\u001c\u0011:16\u0013\u0002+\u001f'\u0001", "vDrrC");
        GameSettings.I[63 + 63 - 116 + 212] = I("\u0003=87", "wOMRf");
        GameSettings.I[109 + 141 - 146 + 119] = I("3.", "RAfMW");
        GameSettings.I[28 + 15 + 60 + 121] = I("\u0004+\u0014\t", "pYaln");
        GameSettings.I[163 + 78 - 101 + 85] = I("$#-\u00051", "BBAvT");
        GameSettings.I[169 + 192 - 236 + 101] = I("&\u0015++)&3) 90\u0003", "TpEOL");
        GameSettings.I[117 + 51 + 25 + 34] = I("\u00159=\n", "aKHom");
        GameSettings.I[67 + 29 + 44 + 88] = I("\u001e8&\u0002\n", "xYJqo");
        GameSettings.I[69 + 213 - 225 + 172] = I("-;\u0019-", "KZjYm");
        GameSettings.I[120 + 129 - 221 + 202] = I("41\u0011-\u001447\u0007\u0012\u0000%?\u0011", "FTbBa");
        GameSettings.I[126 + 219 - 243 + 129] = I("\u000f>5?\u001f\u00161\"9\u0010\n5\u00045\u0001\t%$3\u0017615;\u0001", "fPVPr");
        GameSettings.I[111 + 132 - 192 + 181] = I("5,\u0018\r\n<?\u001d\u001c+", "YMkyY");
        GameSettings.I[125 + 107 - 72 + 73] = I("\u000e\u0006\u001b\u001d", "bguzW");
        GameSettings.I[149 + 60 - 142 + 167] = I("\")-\u00139(2%\u0005\u0006-(8\u001e", "AALgo");
        GameSettings.I[135 + 133 - 209 + 176] = I("\u0013\n\u0019\u001c$\u001f\u000e\u0017\u001a\u0014", "pbxhg");
        GameSettings.I[148 + 161 - 220 + 147] = I("\u0010\u0010\"\u000f", "dbWjQ");
        GameSettings.I[96 + 91 - 85 + 135] = I("\u0002\u000b\r54\b\r\u00072", "aclAx");
        GameSettings.I[210 + 214 - 387 + 201] = I("\u0018\u0000\u0011\"", "lrdGv");
        GameSettings.I[47 + 135 - 157 + 214] = I("&\u0012\"\u0011;,\u0014(\u0016'7\u0015.\u0015\u0003", "EzCew");
        GameSettings.I[26 + 154 + 46 + 14] = I("\u0011\u001e\u001e,", "elkIB");
        GameSettings.I[131 + 168 - 205 + 147] = I("-\r\u000b-->\u0004\t0\u00167", "NejYb");
        GameSettings.I[186 + 45 - 16 + 27] = I("%\t\u0005:43\u0015/;%4\u000b\u000f1", "VgjUD");
        GameSettings.I[41 + 213 - 135 + 124] = I("\u001e\n'5", "jxRPg");
        GameSettings.I[152 + 243 - 155 + 4] = I("\u0004\u0011<\u00077\u0001\u00165\u000e*", "bdPkD");
        GameSettings.I[25 + 66 - 38 + 192] = I("!(<#", "UZIFx");
        GameSettings.I[49 + 155 - 63 + 105] = I("\f>\"\u001b\u0006\f\u00060\u0000\u0004\n", "iPCyj");
        GameSettings.I[14 + 229 - 70 + 74] = I("\u0016\u001b\u0012\b", "bigmE");
        GameSettings.I[145 + 143 - 61 + 21] = I("4'#\u0013\u0017.", "ATFEu");
        GameSettings.I[4 + 205 + 14 + 26] = I("\u000e1\u001a\u0004", "zCoaw");
        GameSettings.I[157 + 167 - 269 + 195] = I("\u000b\n=+;\u0006\u0011/+\u001a\"\u0007=<\r\u0010\u0010", "ccYNh");
        GameSettings.I[32 + 23 + 194 + 2] = I("\u001d4\u001b\u001d", "iFnxF");
        GameSettings.I[31 + 101 - 68 + 188] = I("\b#9\u0005:\n\"+- \f*\u001b\u000b;\u00053&\u0014'", "iGOdT");
        GameSettings.I[18 + 55 - 10 + 190] = I("-\u001b\u001a\u0012", "YiowF");
        GameSettings.I[202 + 95 - 156 + 113] = I("\u001602\u001b\u0003)?\u000b\u0007\u0015\u0012\u0017(\u000b\u0013\u0015", "fQGhf");
        GameSettings.I[177 + 37 - 3 + 44] = I("84/4", "LFZQg");
        GameSettings.I[180 + 171 - 293 + 198] = I("\f\u000e\u00146$\u000b\u0002\u00130)\u0016", "xaaUL");
        GameSettings.I[52 + 152 - 16 + 69] = I("\u001f\u0018\u0001$", "kjtAN");
        GameSettings.I[174 + 233 - 266 + 117] = I("\u0004\u0015.\u0010?\u0002\u0007.*(\u0002\u0004#\u0016", "kcKbM");
        GameSettings.I[128 + 192 - 233 + 172] = I("9!\u001f\n\u0005?3\u001f/\u001e2#\u0012", "VWzxw");
        GameSettings.I[189 + 162 - 247 + 156] = I("\f\u00068\u000e\u001e\u0010\u00069>8\u000b\u000f \u0003'\u0017", "dcTjW");
        GameSettings.I[161 + 252 - 296 + 144] = I("\u0004'2\b", "pUGmO");
        GameSettings.I[129 + 108 + 25 + 0] = I("\n\f\u0006\u001f\u001b\f\r\u0000\u0003'/\u000b\u0004\u001e \f\u0000", "idgkS");
        GameSettings.I[18 + 221 - 49 + 73] = I("\u0000-9\u001d0\u0006,?\u0001\f6+>\u0006\u001b\u00166=\r", "cEXix");
        GameSettings.I[80 + 60 - 130 + 254] = I("'!*-$'('<", "DIKYw");
        GameSettings.I[88 + 94 + 38 + 45] = I("\u0000;\u001b\u001e0\n7\u000e\u0002", "cSzjg");
        GameSettings.I[12 + 220 - 224 + 258] = I("\u0010#;!!\r=18\u001c\f9-\u0017\u000b\u000b\"1 \r\u000e.:\" \n% ", "cKTVh");
        GameSettings.I[228 + 101 - 94 + 32] = I("\"<\u000f\u0007", "VNzbx");
        GameSettings.I[241 + 194 - 417 + 250] = I("5\b\u001e\u0001\u0017(-\u000b\u001a\u00134\u0012", "Xanlv");
        GameSettings.I[220 + 111 - 309 + 247] = I("\u0017\u0007\u00061\u0018\t1\r \u001c\u0017#\u0011&)\r\u000b\u00118", "dstTy");
        GameSettings.I[71 + 75 + 116 + 8] = I("\u0003\u001d:1\b\u001d$!7?\u001f\u0005=9\f", "piHTi");
        GameSettings.I[145 + 41 + 20 + 65] = I("<\u0011\u0000.)\"6\u000b8<*\b$$$:\b\u0017", "OerKH");
        GameSettings.I[54 + 181 - 134 + 171] = I("&\u001d\u0013\u0017,8\"\u0003\u0002>", "UiarM");
        GameSettings.I[122 + 101 + 27 + 23] = I("\u0011;\u0013.$\u000f\t\u00118", "bOaKE");
        GameSettings.I[25 + 114 + 13 + 122] = I("\u001a\u0012=4%\u0004% <4\u001b\u0003<\"-\u0006\b", "ifOQD");
        GameSettings.I[144 + 131 - 226 + 226] = I(" 20\u0012)>\u0015'\u0019,\u001e#6\u0016,22#", "SFBwH");
        GameSettings.I[44 + 9 + 161 + 62] = I("-8-\n", "YJXoL");
        GameSettings.I[250 + 156 - 155 + 26] = I("\u0001\u0010\u0016\u0010\f\u001f4\u0016\u0010\u000b\u0017\u0016\u0016\u0010\t!\u0001\u0016\u0003\b\u0000", "rddum");
        GameSettings.I[197 + 141 - 123 + 63] = I(";>\b',%\t\u0012#9\r$\u001b !-.", "HJzBM");
        GameSettings.I[147 + 120 + 7 + 5] = I("\u0002&:\u0012\u0014\u001c\u0011 \u0016\u0001$!-\u00053\u0018><\u0012\u0007", "qRHwu");
        GameSettings.I[17 + 42 + 29 + 192] = I("8\u00169#3&/\"%\u0006$\u0005,*7\t\u0007#'$\"\r9", "KbKFR");
        GameSettings.I[109 + 230 - 74 + 16] = I("$8\u0013\u0012\u0002\u00179\b\u0012\b&2'\u001e\t6", "BWaqg");
        GameSettings.I[2 + 56 + 217 + 7] = I("\"89\u001f", "VJLzy");
        GameSettings.I[26 + 64 + 172 + 21] = I("$\u001e\u0016\u0019\u0002\u0007\u001e\u0015\u0015\u001e\u0004\u001e\u000e\u0013\u0007+\u0013\u000e\u001f\u0003 \u0001", "Erzvu");
        GameSettings.I[96 + 0 - 79 + 267] = I("\u0018\u001f7#", "lmBFw");
        GameSettings.I[4 + 238 - 101 + 144] = I("\u000b\u0001\u000b\u00105\u001c\u0000+\u00004\f\u0003&\u000b0\u0016", "ydoeV");
        GameSettings.I[4 + 255 - 67 + 94] = I("\"\u001a\u0016/", "VhcJP");
        GameSettings.I[274 + 73 - 272 + 212] = I("\"='=\u000b#'4\u0016>%/,\u0000\u001a8<6", "WNBsj");
        GameSettings.I[157 + 85 - 9 + 55] = I("\u0016\u001c40", "bnAUj");
        GameSettings.I[45 + 196 - 123 + 171] = I("\u0000\u000b\u0003,0\u001c6\u001f$ \n\u0012\u0004", "eewED");
        GameSettings.I[12 + 285 - 208 + 201] = I("0\u0015<-", "DgIHV");
        GameSettings.I[286 + 271 - 399 + 133] = I(".\b!)", "EmXvw");
        GameSettings.I[14 + 68 + 122 + 88] = I("\u0014),\r5$'-\u00066\b4 <", "gFYcQ");
        GameSettings.I[232 + 163 - 273 + 171] = I("/\u001c#\u000f)\u0012\u00125\u001e\u001a", "BsGjE");
        GameSettings.I[236 + 50 - 107 + 115] = I("#\u0019\u0007<", "WkrYY");
        GameSettings.I[22 + 93 - 88 + 268] = I(")\n \u0004\b\u0013\u000f.T\u001a\u001b\u0005i\u001b\b\u000e\b&\u001aBZ", "zaItx");
        GameSettings.I[57 + 193 - 15 + 61] = I("65*%3\u0014t7&v\u001c;\"-v\u001f$7 9\u001e'", "pTCIV");
        GameSettings.I[203 + 246 - 337 + 185] = I("6\u001e0\r", "BlEhS");
        GameSettings.I[191 + 44 + 63 + 0] = I("\u0010\u000f%<(", "vnIOM");
        GameSettings.I[275 + 282 - 327 + 69] = I("3\b\u0000\u0004:.?;\u000e=)\u0003L", "ZfvaH");
        GameSettings.I[128 + 40 + 71 + 61] = I("\u0015\n\u0014>\u000b+\u0000\u000f>\u0007\f\f\u0017$\u001a\u0001_", "xeaMn");
        GameSettings.I[111 + 198 - 135 + 127] = I("\u001f 9m", "yOOWq");
        GameSettings.I[17 + 138 + 37 + 110] = I("\u000f\u0003\u0004<8R", "hbiQY");
        GameSettings.I[22 + 257 - 163 + 187] = I("\u0012# \u0007\u0013\u00006=\u001d\u000f[", "aBTra");
        GameSettings.I[35 + 4 + 166 + 99] = I("\u0013\u000f7&/\u0013.01>\u0000\u0004:'p", "ajYBJ");
        GameSettings.I[46 + 27 + 127 + 105] = I("\u00028/;\u0011\u0004!#R", "eMFhr");
        GameSettings.I[101 + 302 - 252 + 155] = I("\u001f\b?'\u0010\f\u0005( C", "oiMSy");
        GameSettings.I[194 + 273 - 378 + 218] = I("$\u0006\u0017\u0015\u000b#\u001eO", "FiuCb");
        GameSettings.I[196 + 171 - 231 + 172] = I("\u0012\u001c0\t)\n\u00029]!I", "srQnE");
        GameSettings.I[90 + 259 - 266 + 226] = I("+ \"\u000335{", "FAZEC");
        GameSettings.I[171 + 194 - 231 + 176] = I("\u001f1\u0018\"#\u00181\u001b\u0002w", "ySwgM");
        GameSettings.I[228 + 240 - 205 + 48] = I(".\"*\u00123)> \u0000#p", "JKLtZ");
        GameSettings.I[94 + 192 - 14 + 40] = I("\u0011\r6\u0016!0\u001e9\u00050\u001e\u000f+O", "wlXuX");
        GameSettings.I[177 + 3 + 95 + 38] = I("3\u001fi", "RpSQu");
        GameSettings.I[239 + 60 - 188 + 203] = I("#\u0007\u001a2(#!\u0018985\u0011N0,=\u0011\u0011", "QbtVM");
        GameSettings.I[66 + 65 - 16 + 200] = I("6\u000b\u0017036-\u0015;# \u001dC277\u001a", "DnyTV");
        GameSettings.I[43 + 196 - 159 + 236] = I("\u0006\f\u0003\u0013\u0016\u0006*\u0001\u0018\u0006\u0010\u001aW\u0003\u0001\u0001\f", "timws");
        GameSettings.I[186 + 295 - 461 + 297] = I("*\u0002?\r/*\u0004)2;;\f?X", "XgLbZ");
        GameSettings.I[55 + 47 + 123 + 93] = I(" 6,<599;::%=\u001d6+&-=0=\u00199,8+s", "IXOSX");
        GameSettings.I[15 + 178 - 83 + 209] = I("-0\t 2$#\f1\u0013{", "AQzTa");
        GameSettings.I[317 + 252 - 393 + 144] = I("#'\f=@", "OFbZz");
        GameSettings.I[181 + 130 - 140 + 150] = I("\u0002\u0007;:\u001a\b\u001c3,%\r\u0006.7v", "aoZNL");
        GameSettings.I[21 + 221 - 96 + 176] = I("5<\b \u000f98\u0006&?l", "VTiTL");
        GameSettings.I[237 + 162 - 123 + 47] = I("!\u001c\u00130\u0001+\u001a\u00197w", "BtrDM");
        GameSettings.I[72 + 180 - 186 + 258] = I("\u0011\u0001\f\u001b6\u001b\u0007\u0006\u001c*\u0000\u0006\u0000\u001f\u000eH", "rimoz");
        GameSettings.I[311 + 259 - 484 + 239] = I("9\u000b%'8*\u0002':\u0003#Y", "ZcDSw");
        GameSettings.I[134 + 239 - 218 + 171] = I("2\b\u001d\t\u001a$\u00147\b\u000b#\n\u0017\u0002P", "Afrfj");
        GameSettings.I[33 + 137 + 7 + 150] = I("\f\u0013$\u000b\u0019\t\u0014-\u0002\u0004P", "jfHgj");
        GameSettings.I[270 + 246 - 500 + 312] = I("+ 3\u0000'+\u0018!\u001b%-t", "NNRbK");
        GameSettings.I[249 + 250 - 344 + 174] = I(";\u00175<\u0006!^", "NdPjd");
        GameSettings.I[13 + 23 + 289 + 5] = I("\u00010\u000f$\u0005\f+\u001d$$(=\u000f33\u001a*Q", "iYkAV");
        GameSettings.I[300 + 211 - 427 + 247] = I("\u00057\u00147\r\u00076\u0006\u001f\u0017\u0001>69\f\b'\u000b&\u0010^", "dSbVc");
        GameSettings.I[291 + 91 - 78 + 28] = I("15/*<\u000e:\u00166*5\u00125:,2n", "ATZYY");
        GameSettings.I[297 + 299 - 422 + 159] = I(".<7\u001b\u000f)00\u001d\u00024i", "ZSBxg");
        GameSettings.I[154 + 14 - 67 + 233] = I("69<\";0+<\u0007 =;1j", "YOYPI");
        GameSettings.I[235 + 258 - 319 + 161] = I(".'\u000f\n\u0004(5\u000f0\u0013(6\u0002\fL", "AQjxv");
        GameSettings.I[0 + 100 + 12 + 224] = I("$1\u001c\u0005\u001c81\u001d5:#8\u0004\b%?n", "LTpaU");
        GameSettings.I[205 + 93 - 221 + 260] = I("9 \u0012\u0012%?!\u0014\u000e\u0019\u001c'\u0010\u0013\u001e?,I", "ZHsfm");
        GameSettings.I[102 + 284 - 364 + 316] = I("\u0011/,.\u0019\u0017.*2%')+52\u00074(>k", "rGMZQ");
        GameSettings.I[239 + 192 - 121 + 29] = I("\u000f\u001f&#\n\u000f\u0016+2c", "lwGWY");
        GameSettings.I[143 + 212 - 40 + 25] = I("\u001793\u0006 \u001d5&\u001aM", "tQRrw");
        GameSettings.I[170 + 304 - 309 + 176] = I("\u000b\u0019'\u001e\n\u0016\u0007-\u00077\u0017\u00031( \u0010\u0018-\u001f&\u0015\u0014&\u001d\u000b\u0011\u001f<S", "xqHiC");
        GameSettings.I[103 + 266 - 243 + 216] = I("\t:)\u001f\u0013\u0014\u001f<\u0004\u0017\b c", "dSYrr");
        GameSettings.I[340 + 184 - 453 + 272] = I("7\f\u0010\u000b9):\u001b\u001a=7(\u0007\u001c\b-\u0000\u0007\u0002b", "DxbnX");
        GameSettings.I[261 + 220 - 313 + 176] = I("7\u0013<\u000b\u0006)*'\r1+\u000b;\u0003\u0002~", "DgNng");
        GameSettings.I[165 + 19 + 151 + 10] = I("8 %\u0013\u0000&\u0007.\u0005\u0015.9\u0001\u0019\r>92L", "KTWva");
        GameSettings.I[199 + 27 - 190 + 310] = I("*,9$\u00114\u0013)1\u0003c", "YXKAp");
        GameSettings.I[89 + 130 - 36 + 164] = I("\u0000\u00107\u0014)\u001e\"5\u0002r", "sdEqH");
        GameSettings.I[242 + 76 - 230 + 260] = I("\u0012\r\b2\u001b\f:\u0015:\n\u0013\u001c\t$\u0013\u000e\u0017@", "ayzWz");
        GameSettings.I[60 + 263 - 215 + 241] = I("!\u0018\u0007'\u0000??\u0010,\u0005\u001f\t\u0001#\u00053\u0018\u0014x", "RluBa");
        GameSettings.I[349 + 194 - 433 + 240] = I("\u001f\u0007< $\u0001#< #\t\u0001< !?\u0016<3 \u001eI", "lsNEE");
        GameSettings.I[349 + 297 - 351 + 56] = I("\t\u0005%\t-\u00172?\r8?\u001f6\u000e \u001f\u0015m", "zqWlL");
        GameSettings.I[129 + 98 - 151 + 276] = I("0&*\u0007\f.\u00110\u0003\u0019\u0016!=\u0010+*>,\u0007\u001fy", "CRXbm");
        GameSettings.I[152 + 145 - 53 + 109] = I("\u0017\u0001\u00103\u000b\t8\u000b5>\u000b\u0012\u0005:\u000f&\u0010\n7\u001c\r\u001a\u0010l", "dubVj");
        GameSettings.I[135 + 325 - 362 + 256] = I("\u0012\u0004\n3\u0014!\u0005\u00113\u001e\u0010\u000e>?\u001f\u0000Q", "tkxPq");
        GameSettings.I[145 + 112 - 108 + 206] = I("(\u001a$#.\u000b\u001a'/2\b\u001a<)+'\u0017<%/,\u0005r", "IvHLY");
        GameSettings.I[224 + 21 + 33 + 78] = I("0\u0016\u0011\u0001\u001b'\u00171\u0011\u001a7\u0014<\u001a\u001e-I", "Bsutx");
        GameSettings.I[203 + 346 - 280 + 88] = I("\u0003\u001c-\u0003\r\u0002\u0006>(8\u0004\u000e&>\u001c\u0019\u001d<w", "voHMl");
        GameSettings.I[323 + 70 - 355 + 320] = I("\u00107-\u001e\u0007\f\n1\u0016\u0017\u001a.*M", "uYYws");
        GameSettings.I[358 + 214 - 308 + 95] = I("\b$\u0012%", "cAkzD");
        GameSettings.I[346 + 337 - 412 + 89] = I("b", "XCrss");
        GameSettings.I[305 + 79 - 46 + 23] = I("\"\u0002'\u0019<\u0012\f&\u0012?>\u001f+(", "QmRwX");
        GameSettings.I[280 + 233 - 202 + 51] = I("c", "YsBTk");
        GameSettings.I[83 + 211 - 122 + 191] = I(",&5\u0006\u0006\u0011(#\u00175", "AIQcj");
        GameSettings.I[136 + 14 - 105 + 319] = I("b", "XWuJe");
        GameSettings.I[174 + 75 - 48 + 164] = I("?\u0003#\u0016\u000e\u001dB>\u0015K\n\u0003<\u001fK\u0016\u0012>\u0013\u0004\u0017\u0011", "ybJzk");
        GameSettings.I[181 + 5 - 159 + 339] = I("'\u0003\u00017%\u000f\u0012", "cfgVP");
        GameSettings.I[95 + 145 - 48 + 175] = I("\u0015\r\"--=\u001c", "QhDLX");
        GameSettings.I[70 + 15 + 29 + 254] = I("\u0000\u0006\u0017\u0003/(\u0017", "DcqbZ");
        GameSettings.I[258 + 72 - 327 + 366] = I("cX", "YxTlf");
        GameSettings.I[130 + 353 - 384 + 271] = I("<\u001c\u00002", "hunKp");
        GameSettings.I[73 + 114 - 47 + 231] = I("\u0018\u0012\t\b\r", "Kzfzy");
        GameSettings.I[151 + 268 - 340 + 293] = I("\u0003\u0000\u001e/*!", "MolBK");
        GameSettings.I[58 + 310 - 339 + 344] = I("*\u0010\u0018", "lqjBu");
        GameSettings.I[73 + 235 - 86 + 152] = I("\u000e)\u0012\u00142&4", "KQffW");
        GameSettings.I[347 + 207 - 247 + 68] = I("D", "opLAR");
        GameSettings.I[273 + 156 - 162 + 109] = I("v", "VUDkU");
        GameSettings.I[136 + 301 - 196 + 136] = I(">)\u001d\u000e", "xHnzs");
        GameSettings.I[67 + 37 + 34 + 240] = I(">\u0016/\t\u001f", "xwAjf");
        GameSettings.I[226 + 88 - 283 + 348] = I("=.\u0004", "rhBTT");
        GameSettings.I[311 + 333 - 578 + 314] = I(")\u00042", "fBtKh");
        GameSettings.I[342 + 276 - 364 + 127] = I("\u0019\u0013 # $\u0002", "WvAQE");
        GameSettings.I[77 + 156 - 66 + 215] = I("\u00163\u0004\u0002\u0011(", "ZZjgp");
        GameSettings.I[106 + 59 - 12 + 230] = I("\u0015\u001c\u0006\r72\u0014\u0018", "WujdY");
        GameSettings.I[53 + 98 + 25 + 208] = I(">&'\u001a3\u00041/\u0004", "jTNvZ");
        GameSettings.I[131 + 120 - 106 + 240] = I("/\u0016\u0017\u0000\u001d\u0012\u0007", "asvrx");
        GameSettings.I[132 + 248 - 316 + 322] = I("8\u000b", "wEVSK");
        GameSettings.I[214 + 128 - 309 + 354] = I("9-4", "vkrfu");
        GameSettings.I[206 + 276 - 472 + 378] = I("<\u001d", "sSUBQ");
        GameSettings.I[377 + 323 - 688 + 377] = I("\u001c\u000f\u000f", "SIIoz");
        GameSettings.I[201 + 99 - 46 + 136] = I("#7<\u001a", "eVOnC");
        GameSettings.I[27 + 357 - 13 + 20] = I("\r \u001a$(", "KAtGQ");
        GameSettings.I[361 + 5 - 259 + 285] = I("=\u0011\u0005", "rWCLK");
        GameSettings.I[69 + 113 + 5 + 206] = I("6()\u0011>\u001e9", "rMOpK");
        GameSettings.I[95 + 236 - 330 + 393] = I("/5\u0011\u0011", "iTbet");
        GameSettings.I[311 + 350 - 643 + 377] = I("\u001399\u0014?", "UXWwF");
        GameSettings.I[238 + 381 - 442 + 219] = I("7-%\f/\u001f<", "sHCmZ");
        GameSettings.I[135 + 293 - 308 + 277] = I("\f\u00110&", "JpCRH");
        GameSettings.I[284 + 77 - 17 + 54] = I("\u0007\u0018!+\u0016", "AyOHo");
        GameSettings.I[250 + 327 - 222 + 44] = I("\u0010\b \u001328\u0019", "TmFrG");
        GameSettings.I[260 + 66 - 1 + 75] = I("\u001e\u0005!\r", "XdRyn");
        GameSettings.I[115 + 62 + 125 + 99] = I("\u0017\u0012-*(", "QsCIQ");
        GameSettings.I[30 + 129 + 132 + 111] = I("8'2", "watRd");
        GameSettings.I[54 + 197 - 172 + 324] = I("\u001c\u001f2\u0014\"4\u000e", "XzTuW");
        GameSettings.I[363 + 164 - 508 + 385] = I("+\u0014\u001b\u0012\u0018\u0006\u000e", "omusu");
        GameSettings.I[246 + 7 + 116 + 36] = I("\u001a\u000f2", "UItcA");
        GameSettings.I[182 + 115 + 75 + 34] = I("!\"", "nluuU");
        GameSettings.I[35 + 344 - 162 + 190] = I("/\f#\u0004>\u0002\u0016", "kuMeS");
        GameSettings.I[47 + 306 - 340 + 395] = I("<\u0000\u0004", "sFBme");
        GameSettings.I[88 + 201 - 209 + 329] = I("\u0019 ", "VngFk");
        GameSettings.I[228 + 67 - 221 + 336] = I("\u0016(", "YfxIc");
        GameSettings.I[68 + 355 - 273 + 261] = I("\u0002,\"", "MjdPV");
        GameSettings.I[60 + 9 + 36 + 307] = I("\u001d)", "RgcKy");
        GameSettings.I[191 + 389 - 307 + 140] = I("\u000e\u00162", "APtKd");
        GameSettings.I[398 + 117 - 335 + 234] = I("\u001c;", "SuqkT");
        GameSettings.I[56 + 142 - 185 + 402] = I("*)*", "eolFS");
        GameSettings.I[199 + 407 - 430 + 240] = I("\u0004\u000b", "KEwAX");
        GameSettings.I[204 + 62 - 252 + 403] = I(">+\b", "qmNUD");
        GameSettings.I[104 + 201 - 119 + 232] = I("\n'", "EiWFL");
        GameSettings.I[314 + 367 - 279 + 17] = I("\u0006>(", "IxnxI");
        GameSettings.I[210 + 412 - 310 + 108] = I("7*", "xdcFn");
        GameSettings.I[361 + 378 - 452 + 134] = I("\u00006\u0000", "OpFML");
        GameSettings.I[370 + 157 - 353 + 248] = I("\u0005(", "JfqzW");
        GameSettings.I[162 + 391 - 145 + 15] = I("\b\u000f(", "GIndD");
        GameSettings.I[120 + 397 - 317 + 224] = I("'\u0018", "hVJQN");
        GameSettings.I[34 + 141 + 189 + 61] = I("\u0000\t*", "OOlMU");
        GameSettings.I[245 + 198 - 408 + 391] = I("-7", "bycCY");
        GameSettings.I[246 + 139 - 44 + 86] = I("\u001b>6", "Txpgo");
        GameSettings.I[264 + 425 - 440 + 179] = I("(\u0014", "gZxYy");
        GameSettings.I[0 + 127 + 74 + 228] = I("\u0000\u0014)", "ORofJ");
        GameSettings.I[259 + 178 - 131 + 124] = I("%\u0001", "jOzvf");
        GameSettings.I[332 + 23 - 289 + 365] = I(",\n\u001f", "cLYrY");
        GameSettings.I[196 + 185 + 34 + 17] = I(";\t", "tGZqT");
        GameSettings.I[160 + 351 - 211 + 133] = I("'?\u000f", "hyIbT");
        GameSettings.I[273 + 97 + 8 + 56] = I("\u0002?", "MqhpQ");
        GameSettings.I[351 + 404 - 746 + 426] = I("?\f\u0017", "pJQaD");
        GameSettings.I[294 + 219 - 313 + 236] = I("\u000b;", "DuCix");
        GameSettings.I[270 + 38 + 33 + 96] = I("\u001c\f?", "SJygT");
        GameSettings.I[305 + 388 - 612 + 357] = I("+\u0000", "dNTVO");
        GameSettings.I[330 + 389 - 426 + 146] = I("\n\u0005\b", "ECNaU");
        GameSettings.I[386 + 220 - 284 + 118] = I("%\u0006", "jHQUD");
        GameSettings.I[54 + 346 + 36 + 5] = I("\u000330", "LuvHW");
        GameSettings.I[150 + 83 - 6 + 215] = I("\u000e\t", "AGNHF");
        GameSettings.I[184 + 29 + 174 + 56] = I("%$!", "jbgNJ");
        GameSettings.I[192 + 276 - 345 + 321] = I("\u0005\u001c#\n\u0007-\reC@2P", "AyEkr");
        GameSettings.I[383 + 66 - 245 + 241] = I("uB5", "GrFMo");
        GameSettings.I[154 + 153 - 256 + 395] = I("G9+)", "tTBGV");
        GameSettings.I[249 + 106 - 48 + 140] = I("ps\u0005\u0007#", "CChnM");
        GameSettings.I[224 + 95 - 223 + 352] = I("\u0010.1\r", "VOByW");
        GameSettings.I[34 + 407 - 432 + 440] = I("! (\u0002-", "gAFaT");
        GameSettings.I[410 + 262 - 443 + 221] = I("\u00027<", "Mqzzk");
        GameSettings.I[372 + 211 - 356 + 224] = I("\u0010$\t\u0017", "VEzch");
        GameSettings.I[429 + 225 - 349 + 147] = I("4\u00168\u000f?", "rwVlF");
        GameSettings.I[101 + 22 - 54 + 384] = I(">\u0016?", "qPyWR");
        GameSettings.I[302 + 50 + 16 + 86] = I(")#", "fmOeu");
        GameSettings.I[20 + 325 + 24 + 86] = I("%->", "jkxzA");
        GameSettings.I[435 + 254 - 438 + 205] = I("\u0019\n", "VDFkB");
        GameSettings.I[126 + 198 - 13 + 146] = I("\u0002+-", "MmkjT");
        GameSettings.I[132 + 178 - 17 + 165] = I("8(", "wfOiA");
        GameSettings.I[17 + 411 - 113 + 144] = I("\u0018\u00032", "WEtVD");
        GameSettings.I[136 + 405 - 100 + 19] = I("\u0000=", "Osvnn");
        GameSettings.I[402 + 204 - 405 + 260] = I("\u0001/)", "Niosp");
        GameSettings.I[287 + 230 - 235 + 180] = I("\u000e\u000e9\u001d", "HoJit");
        GameSettings.I[106 + 380 - 236 + 213] = I("!'\u0017&\r", "gFyEt");
        GameSettings.I[450 + 144 - 591 + 461] = I("\f\u00037, $\u0012", "HfQMU");
        GameSettings.I[378 + 249 - 368 + 206] = I("\"\t(\u00020\u0019", "qdGmD");
        GameSettings.I[52 + 408 - 430 + 436] = I("\u0014!!3?t\u0017\"53", "YTMGV");
        GameSettings.I[9 + 372 - 233 + 319] = I("/\u000b+\u0007\u0005\u0007\u001a", "knMfp");
        GameSettings.I[406 + 406 - 382 + 38] = I("\u0000\"", "OlCBd");
        GameSettings.I[132 + 257 - 89 + 169] = I("&\u000b\b", "iMNlt");
        GameSettings.I[368 + 237 - 136 + 1] = I("7\u000b\nD9\u001d\u0006\n", "sjsdv");
        GameSettings.I[36 + 251 + 111 + 73] = I("$\u0003\r)\u0004J%\u0004-\t", "jjjAp");
        GameSettings.I[271 + 172 - 52 + 81] = I("%\b'2\u0007\r\u0019", "amASr");
        GameSettings.I[430 + 339 - 594 + 298] = I("!4", "nzGPy");
        GameSettings.I[67 + 401 - 120 + 126] = I("\u001c,\u0011", "SjWJa");
        GameSettings.I[241 + 461 - 548 + 321] = I("", "HeDRM");
        GameSettings.I[154 + 65 + 27 + 230] = I("NC\u0006+\u0005\u001a\n\u0006:_", "nktNv");
        GameSettings.I[226 + 314 - 509 + 446] = I("\u0007'\f", "HaJVT");
        GameSettings.I[11 + 165 + 186 + 116] = I("\u001c\u0002\u000f", "SDIUc");
        GameSettings.I[257 + 244 - 499 + 477] = I(":/", "uaGkI");
        GameSettings.I[430 + 404 - 525 + 171] = I("\u001c\u0002\u0002", "SDDSz");
        GameSettings.I[97 + 146 + 60 + 178] = I("'\t", "hGWrD");
        GameSettings.I[407 + 356 - 325 + 44] = I("?!\"", "pgddD");
        GameSettings.I[385 + 424 - 570 + 244] = I("\u00068", "IvkVx");
        GameSettings.I[253 + 164 - 268 + 335] = I("!\u000b\"", "nMdtk");
        GameSettings.I[277 + 482 - 736 + 462] = I("\u001e\u0004", "QJQiU");
        GameSettings.I[467 + 296 - 372 + 95] = I("\u001e\b\u0013", "QNUjp");
        GameSettings.I[163 + 283 - 306 + 347] = I("\u0019\u001a", "VTQSv");
        GameSettings.I[253 + 453 - 396 + 178] = I("\u0002\u0003!", "MEgyF");
        GameSettings.I[222 + 416 - 395 + 246] = I(">?", "qqpsl");
        GameSettings.I[378 + 486 - 820 + 446] = I("\n!\u0004", "EgBXn");
        GameSettings.I[18 + 470 - 144 + 147] = I("?\u001b", "pUzeG");
        GameSettings.I[93 + 90 + 169 + 140] = I("8.<", "whzXe");
        GameSettings.I[27 + 143 + 32 + 291] = I("-\u0014", "bZkBo");
        GameSettings.I[165 + 422 - 440 + 347] = I(":\u000f?", "uIyFi");
        GameSettings.I[267 + 345 - 584 + 467] = I("\t\u000f", "FALrI");
        GameSettings.I[251 + 35 - 169 + 379] = I("\u000b\u001f7", "DYqqz");
        GameSettings.I[337 + 248 - 194 + 106] = I(",\u0002", "cLkRz");
        GameSettings.I[9 + 466 - 147 + 170] = I(",\u0007\r", "cAKZi");
        GameSettings.I[126 + 125 + 196 + 52] = I(",8", "cvdaF");
        GameSettings.I[94 + 304 - 254 + 356] = I("\u0002$\u0015", "MbSaU");
        GameSettings.I[18 + 426 - 337 + 394] = I("\u0018\u0005", "WKCWe");
        GameSettings.I[349 + 62 + 89 + 2] = I("$.)", "khogE");
        GameSettings.I[197 + 425 - 323 + 204] = I("\u00017\u001e\u001b", "GVmom");
        GameSettings.I[346 + 183 - 82 + 57] = I("*\u0010\u000013", "lqnRJ");
        GameSettings.I[107 + 2 + 99 + 297] = I("+\u0002)($\u0003\u0013", "ogOIQ");
        GameSettings.I[1 + 112 - 29 + 422] = I(";6", "txyiT");
        GameSettings.I[21 + 349 + 6 + 131] = I("+\u0015\u001f", "dSYoR");
        GameSettings.I[416 + 404 - 813 + 501] = I("!/", "naDYp");
        GameSettings.I[264 + 34 + 5 + 206] = I("\u00196%", "VpcLa");
        GameSettings.I[27 + 85 + 112 + 286] = I("\u0010$\u00037\u0019", "FwzYz");
        GameSettings.I[325 + 420 - 371 + 137] = I("8>\u0010\u0006>9=J\t#6#\u0001\u001d0#+(\u0006<>:J\u00020/", "WNdoQ");
        GameSettings.I[358 + 18 - 45 + 181] = I("S0\u0019#", "sViPl");
        GameSettings.I[30 + 359 - 8 + 132] = I("", "wAGhK");
        GameSettings.I[340 + 330 - 320 + 164] = I("_", "eNzGL");
        GameSettings.I[104 + 510 - 241 + 142] = I("9\n\u001f\u0010 2\t?1'%\u0018,\u001b-3/%\u0000 =\u001f", "VlMuN");
        GameSettings.I[151 + 408 - 308 + 265] = I("\u000b\t\u00126\f0\u0016$<", "doTYk");
        GameSettings.I[56 + 255 - 39 + 245] = I("\t\u0014',!5\u0006\u000012", "fraCF");
        GameSettings.I[278 + 493 - 569 + 316] = I("6\n;:\u001f4\r\u0006\u0007\u0016)\t", "YlvSo");
        GameSettings.I[253 + 463 - 337 + 140] = I("\")\u0017&+!:+,'#\t9++4", "MOXEH");
        GameSettings.I[339 + 284 - 136 + 33] = I("-\t\"& -\u001b\u0019\r?1", "BoqKO");
        GameSettings.I[398 + 467 - 742 + 398] = I("7'\u0019?:75\"\u0005:*-.", "XAJRU");
        GameSettings.I[480 + 170 - 598 + 470] = I("?\u000e3>\u00145\u001e\u0017=", "PhrQX");
        GameSettings.I[421 + 260 - 403 + 245] = I("'27\u0003\u0006=0\u0007", "HTtoi");
        GameSettings.I[398 + 223 - 410 + 313] = I("\t\r1\u001e(\u0013\u000f\u0001:\"\u000f\f\u001a\u0006", "fkrrG");
        GameSettings.I[295 + 114 - 266 + 382] = I("\u001a4\u0004(/\u0010!", "uRPZJ");
        GameSettings.I[329 + 130 - 48 + 115] = I("5('\u0003&*>\u0006\u0015\u0000.+\u000e\u0002", "ZNcqI");
        GameSettings.I[288 + 156 - 132 + 215] = I("!'\u001c\f\u0000 ", "NANmi");
        GameSettings.I[234 + 446 - 655 + 503] = I("? 3&\u001e='\u0006-\u0013\u0007'\u0006-\u0005", "PFrHw");
        GameSettings.I[30 + 32 + 314 + 153] = I("8)\u0006\u001d/:.3\u0016\"\u001b.1\u0012", "WOGsF");
        GameSettings.I[528 + 396 - 818 + 424] = I("!0$-/#7\u0011&\"\b?\u0017&", "NVeCF");
        GameSettings.I[414 + 24 - 69 + 162] = I("\u0017#\u0013!+\u0015$&*&(* ;#\u0014", "xEROB");
        GameSettings.I[298 + 121 - 299 + 412] = I("$.\u0019$\u0001&),/\f\u0019-<9\u001c$&=", "KHXJh");
        GameSettings.I[37 + 324 - 179 + 351] = I("\u001b00+!\u00197\u0005 ,1.\u0001)'\u0007?\u001e+", "tVqEH");
        GameSettings.I[329 + 40 + 85 + 80] = I("\u000b\u00027:0\t\u0005\u00021=\"\b\u00179<", "ddvTY");
        GameSettings.I[45 + 465 - 38 + 63] = I("'\u0013-9%%\u0014\u00182(\u001b\u0018\u0003<)", "HulWL");
        GameSettings.I[470 + 57 - 369 + 378] = I(".>\u0012\b.%\b%\u00153(;(\u00024", "AXDgG");
        GameSettings.I[375 + 405 - 583 + 340] = I("\u000e\"\u0013.=\u00046\u0014.;\u0015-'#,\u0012", "aDDOI");
        GameSettings.I[537 + 359 - 676 + 318] = I("$\u0016\u000297?\u0011>\u0006$9\u0004;5).\u0003", "KpRVE");
        GameSettings.I[332 + 334 - 315 + 188] = I("\u0003,\u00055!\u0005%;\n4\u001e><99\t9", "lJUZU");
        GameSettings.I[469 + 510 - 518 + 79] = I(".\u000b\"\b*$\u001a\u000b\u00133\u0011\f\u0016\u00151\"\u0001\u0001\u0012", "AmdaX");
        GameSettings.I[214 + 292 - 106 + 141] = I("\u001d\"\u0003\u001c&\u00024.\u0000(%%3\u000b=>%1\u000f", "rDGnO");
        GameSettings.I[225 + 403 - 541 + 455] = I("\u001b\r \r&\u0019\n\u0015\u0006+ \u000e\u0013\u0011.\u001d\u0005", "tkacO");
        GameSettings.I[459 + 144 - 313 + 253] = I(",\u000f-*'.\b\u0018!*\u0017\f\u00140;1\f\u001f", "CilDN");
        GameSettings.I[88 + 473 - 439 + 422] = I("'\u0003\u0006\r\u0018&6$\u0000\u0010;\r", "HeTlq");
        GameSettings.I[178 + 50 - 49 + 366] = I("$\u000e&\u0018\u0010$\u0005\u000f\r\u00129", "Khjyw");
        GameSettings.I[441 + 201 - 324 + 228] = I("\b\u0005\u0010 '\u0010%3;", "gcCHH");
        GameSettings.I[425 + 149 - 257 + 230] = I("8\u0003\u0000\u0013\u001f86 \u0010\u000e\u0003\f\"\r\u0018", "WeAfk");
        GameSettings.I[332 + 10 - 246 + 452] = I("\b 7\u000b\u001e\u0013#\u0007)\u0018\u00065\u0006", "gFunj");
        GameSettings.I[101 + 279 - 25 + 194] = I(";\u00170#\u0018:\u0014\u00108\u00130%\u00164\u0002!\u0003\u0016?", "TqsLv");
        GameSettings.I[127 + 361 - 233 + 295] = I("\t\u001c%-2\u0012\u0012\u0017:", "fzrHS");
        GameSettings.I[336 + 287 - 187 + 115] = I("\u0017\u00160\r\u0015", "xpcfl");
        GameSettings.I[280 + 8 + 161 + 103] = I("\u0000*+:0\u001d?", "oLxNQ");
        GameSettings.I[127 + 300 - 271 + 397] = I("\u000559\u001d\u0017'<\u0005\u0006", "jSjhy");
        GameSettings.I[373 + 99 - 22 + 104] = I("\u0006\r\u0011>1\u0007\u000e3#3", "ikGWV");
        GameSettings.I[158 + 199 + 82 + 116] = I("\u001e% #6\u001f(6;'\u00107\u00068", "qCcKC");
        GameSettings.I[393 + 140 - 315 + 338] = I("\u0002\r\f!\u0003\u0003\u0000\u0003&\u0017\t\u0002!.", "mkOIv");
        GameSettings.I[171 + 2 + 78 + 306] = I("'\u00176>\u0019&\u001a &\b)\u0005\u0010%(1\u001f\u0014;\u0005+", "HquVl");
        GameSettings.I[325 + 110 - 226 + 349] = I("%\n\u0002=+/", "JlVTF");
        GameSettings.I[361 + 502 - 551 + 247] = I("\u0000'\u0015+)\u000e3\u0001&8\n3", "oAVGL");
        GameSettings.I[492 + 284 - 517 + 301] = I("(5*-6\"%\u000e ", "GSkLz");
        GameSettings.I[390 + 75 - 270 + 366] = I("\b\b3\r#\u0002\u0018\u0017\u0007", "gnrko");
        GameSettings.I[190 + 529 - 630 + 473] = I("\u001e\u0004$?6\u0017\u000b\u0018(+", "qbtMY");
        GameSettings.I[38 + 346 + 116 + 63] = I(",\u00174<$7\u0014\u0004\n>,\u0006", "CqvYP");
        GameSettings.I[142 + 430 - 105 + 97] = I(".1\u0017\u001f\u0002,'\u0007\u0007\u000f.%7", "AWDhc");
        GameSettings.I[342 + 174 - 225 + 274] = I("\u001d/\u0003)\r\u0016&<\u0005\f\u0010:", "rIQHc");
        GameSettings.I[48 + 249 + 241 + 28] = I("\r+\u0015\u0007\r\r9.(\u000b\r #\u0019", "bMFjb");
        GameSettings.I[20 + 530 - 331 + 348] = I("\u0018\u0015'\u0000>\u0003\u001c\t3\"\u0019\u0007\u0017", "wsduM");
        GameSettings.I[495 + 430 - 373 + 16] = I("*\u000f\u0016\u000f)1\u0006895)\u0006'\t", "EiUzZ");
        GameSettings.I[10 + 380 + 71 + 108] = I("\u0016 \u001a3\u001e\r)4\u0015\u0006\u0000", "yFYFm");
        GameSettings.I[124 + 253 - 164 + 357] = I(":4 \u0019\u0003\"\u0011\u0012\u0001\t&", "URsql");
        GameSettings.I[387 + 504 - 479 + 159] = I("'\u0017-\r\r=\u0003\u0002\u0000--\t\u0017\u0019\u000b-\u0002", "Hqcly");
        GameSettings.I[181 + 334 - 274 + 331] = I("=\u001c<\u00064+9\u0018\u0012 96\u001f\u0006*;\u0014\u0017", "RzpgN");
        GameSettings.I[390 + 442 - 480 + 221] = I("\u001f$\u0003\u001d\u0006\u001c1&\u001a\u000f\u0015,\b\u0007\u000e\u0015", "pBEhj");
        GameSettings.I[366 + 352 - 362 + 218] = I("\u0018++\u0010\u001e\u0003\u0000\f\u0005\u0005", "wMmqm");
        GameSettings.I[530 + 98 - 387 + 334] = I("'5\u0016\u0011 <\u00015\u001e7-!", "HSPpS");
        GameSettings.I[204 + 416 - 537 + 493] = I("\u0001\u0015\u00063\u0003\u0000\u0000>4\u0001\u000b\u001d&\u0003\u000e\u0001\u001092", "nsRAb");
        GameSettings.I[130 + 64 + 184 + 199] = I("\r\u0013\u0010\u0011", "fviNY");
        GameSettings.I[442 + 563 - 628 + 201] = I("\t\u000f\"()3\n,x;;\u0000k7).\r$6cz", "ZdKXY");
        GameSettings.I[239 + 491 - 409 + 258] = I("\u0000*\u0006\u00026\"k\u001b\u0001s*$\u000e\ns);\u001b\u0007<(8", "FKonS");
        GameSettings.I[270 + 37 - 39 + 312] = I("\u001b\u00109*\u0014\u0010\u0013\u0019\u000b\u0013\u0007\u0002\n!\u0019\u00115\u0003:\u0014\u001f\u0005Q", "tvkOz");
        GameSettings.I[460 + 345 - 289 + 65] = I("\u0018\n0&?#\u0015\u0006,b", "wlvIX");
        GameSettings.I[346 + 547 - 886 + 575] = I(")\u0001\u0007\u001e2\u0015\u0013 \u0003!|", "FgAqU");
        GameSettings.I[384 + 87 - 389 + 501] = I("?2;#\u001c=5\u0006\u001e\u0015 1L", "PTvJl");
        GameSettings.I[129 + 229 + 18 + 208] = I("=\u000e\u0017\n,>\u001d+\u0000 <.9\u0007,+R", "RhXiO");
        GameSettings.I[333 + 463 - 494 + 283] = I("\b(+(\u0007\b:\u0010\u0003\u0018\u0014t", "gNxEh");
        GameSettings.I[453 + 152 - 575 + 556] = I("&\f&\u0006,&\u001e\u001d<,;\u0006\u0011Q", "IjukC");
        GameSettings.I[460 + 445 - 519 + 201] = I("*)\u0013\f\u000f 97\u000fy", "EORcC");
        GameSettings.I[169 + 486 - 354 + 287] = I("\u0001\u001f5\r\u0017\u001b\u001d\u0005[", "nyvax");
        GameSettings.I[223 + 478 - 250 + 138] = I(" \u0002).\t:\u0000\u0019\n\u0003&\u0003\u00026\\", "OdjBf");
        GameSettings.I[412 + 269 - 377 + 286] = I("61;:+<$U", "YWoHN");
        GameSettings.I[325 + 278 - 143 + 131] = I("*$(\b\u000652\t\u001e 1'\u0001\tS", "EBlzi");
        GameSettings.I[349 + 203 - 32 + 72] = I("*\u0014>\u0004-+H", "ErleD");
        GameSettings.I[76 + 192 - 85 + 410] = I("\u0004\u0003\u000b>\u001a\u0006\u0004>5\u0017<\u0004>5\u0001Q", "keJPs");
        GameSettings.I[444 + 463 - 512 + 199] = I("6(\u0011\u0000'4/$\u000b*\u0015/&\u000ft", "YNPnN");
        GameSettings.I[103 + 374 + 96 + 22] = I("=\u000e/&9?\t\u001a-4\u0014\u0001\u001c-j", "RhnHP");
        GameSettings.I[474 + 183 - 505 + 444] = I("\u001b\"8\r\u0001\u0019%\r\u0006\f$+\u000b\u0017\t\u0018~", "tDych");
        GameSettings.I[507 + 234 - 259 + 115] = I("\b(*&\u000e\n/\u001f-\u00035+\u000f;\u0013\b \u000er", "gNkHg");
        GameSettings.I[130 + 95 - 91 + 464] = I("\u0003\u001f()*\u0001\u0018\u001d\"')\u0001\u0019+,\u001f\u0010\u0006)y", "lyiGC");
        GameSettings.I[540 + 155 - 598 + 502] = I("\u0000\u0004\u000b+$\u0002\u0003> ))\u000e+((U", "obJEM");
        GameSettings.I[282 + 2 - 235 + 551] = I("\u00050\u0013\u0014=\u00077&\u001f09;=\u00111P", "jVRzT");
        GameSettings.I[514 + 90 - 140 + 137] = I("#\u0013\u000e5\u0000(%9(\u001d%\u00164?\u001av", "LuXZi");
        GameSettings.I[39 + 469 - 132 + 226] = I("\u00197\u00032\f\u0013#\u00042\n\u000287?\u001d\u0005k", "vQTSx");
        GameSettings.I[474 + 454 - 580 + 255] = I("\t %\u0015 \u0012'\u0019*3\u00142\u001c\u0019>\u00035O", "fFuzR");
        GameSettings.I[107 + 520 - 316 + 293] = I("\r.$6\u001c\u000b'\u001a\t\t\u0010<\u001d:\u0004\u0007;N", "bHtYh");
        GameSettings.I[344 + 132 - 278 + 407] = I("?!6\n\u000150\u001f\u0011\u0018\u0000&\u0002\u0017\u001a3+\u0015\u0010I", "PGpcs");
        GameSettings.I[308 + 100 - 115 + 313] = I("\u00006\u0007\u0011\u0004\u001f *\r\n817\u0006\u001f#15\u0002W", "oPCcm");
        GameSettings.I[234 + 175 - 299 + 497] = I(".\u000e$9\r,\t\u00112\u0000\u0015\r\u0017%\u0005(\u0006_", "AheWd");
        GameSettings.I[4 + 197 - 40 + 447] = I("\u0018\u001e)\u0006\u0003\u001a\u0019\u001c\r\u000e#\u001d\u0010\u001c\u001f\u0005\u001d\u001bR", "wxhhj");
        GameSettings.I[381 + 101 - 481 + 608] = I("<+\u00016 =\u001e#;( %i", "SMSWI");
        GameSettings.I[373 + 476 - 459 + 220] = I("\"\u0005\b9\u0011\"\u000e!,\u0013?Y", "McDXv");
        GameSettings.I[588 + 214 - 600 + 409] = I("\r0\u0002\u0019\u0004\u0015\u0010!\u0002Q", "bVQqk");
        GameSettings.I[306 + 518 - 297 + 85] = I("><\t\u001d\u0015>\t)\u001e\u0004\u00053+\u0003\u0012k", "QZHha");
        GameSettings.I[442 + 271 - 171 + 71] = I(",+\u0015-\u00177(%\u000f\u0011\">$r", "CMWHc");
        GameSettings.I[224 + 612 - 692 + 470] = I("\u001f>2\u0005+\u001e=\u0012\u001e \u0014\f\u0014\u00121\u0005*\u0014\u0019\u007f", "pXqjE");
        GameSettings.I[349 + 528 - 759 + 497] = I("$0/\u0013-?>\u001d\u0004v", "KVxvL");
        GameSettings.I[433 + 191 - 599 + 591] = I("6\u000b8\u000f\rc", "Ymkdt");
        GameSettings.I[322 + 125 + 150 + 20] = I("\t(\u0019\u0002\u0016\u0014=p", "fNJvw");
        GameSettings.I[153 + 80 - 111 + 496] = I("!4\u00061/\u0003=:*{", "NRUDA");
        GameSettings.I[517 + 176 - 369 + 295] = I("8(\u0015\u001b\u000e9+7\u0006\fm", "WNCri");
        GameSettings.I[366 + 480 - 735 + 509] = I("\r<42\u0006\f1\"*\u0017\u0003.\u0012)I", "bZwZs");
        GameSettings.I[311 + 108 + 36 + 166] = I(".\u001e\u0005\u0004 /\u0013\n\u00034%\u0011(\u000bo", "AxFlU");
        GameSettings.I[404 + 309 - 549 + 458] = I("\u0019\u0012\"\u0010%\u0018\u001f4\b4\u0017\u0000\u0004\u000b\u0014\u000f\u001a\u0000\u00159\u0015N", "vtaxP");
        GameSettings.I[112 + 525 - 204 + 190] = I("\u00055\u001a,\u0005\u000fi", "jSNEh");
        GameSettings.I[440 + 501 - 386 + 69] = I("\u0016\u0015\u0005\u0004\r\u0018\u0001\u0011\t\u001c\u001c\u0001|", "ysFhh");
        GameSettings.I[20 + 138 + 415 + 52] = I("\t\f$\u000b\r\u0003\u001c\u0000\u0006{", "fjejA");
        GameSettings.I[296 + 363 - 315 + 282] = I("\u001e6\u00193\n\u0014&=9|", "qPXUF");
        GameSettings.I[273 + 68 + 151 + 135] = I("*\u001f\u0019*;#\u0010%=&\u007f", "EyIXT");
        GameSettings.I[386 + 462 - 790 + 570] = I(",.\u0011$57-!\u0012/,?i", "CHSAA");
        GameSettings.I[594 + 431 - 608 + 212] = I("\"\u0005>\r) \u0013.\u0015$\"\u0011\u001e@", "McmzH");
        GameSettings.I[294 + 541 - 438 + 233] = I("\u0017\u0013\u001d\b\u0005\u001c\u001a\"$\u0004\u001a\u0006u", "xuOik");
        GameSettings.I[549 + 321 - 401 + 162] = I("\u0004\u0012*<!\u0004\u0000\u0011\u0013'\u0004\u0019\u001c\"t", "ktyQN");
        GameSettings.I[67 + 1 + 250 + 314] = I("'\n\u0010&\u0018<\u0003>\u0015\u0004&\u0018 i", "HlSSk");
        GameSettings.I[361 + 24 + 78 + 170] = I(",%.\u001c67,\u0000**/,\u001f\u001a\u007f", "CCmiE");
        GameSettings.I[220 + 435 - 29 + 8] = I("*\u0000\b\u0005\u00161\t&#\u000e<\\", "EfKpe");
        GameSettings.I[106 + 437 - 166 + 258] = I("\"\u00141\u001f\u0001:1\u0003\u0007\u000b>H", "Mrbwn");
        GameSettings.I[305 + 521 - 647 + 457] = I("\u000e!\u0016\u00109\u001459\u001d\u0019\u0004?,\u0004?\u00044b", "aGXqM");
        GameSettings.I[44 + 104 + 291 + 198] = I(")1$38?\u0014\u0000',-\u001b\u00073&/9\u000fh", "FWhRB");
        GameSettings.I[243 + 409 - 455 + 441] = I("?-\f,<<8)+55%\u0007645q", "PKJYP");
        GameSettings.I[625 + 332 - 750 + 432] = I(",\u0017$*\u00047<\u0003?\u001fy", "CqbKw");
        GameSettings.I[461 + 292 - 283 + 170] = I("(2/\u000773\u0006\f\b \"&S", "GTifD");
        GameSettings.I[389 + 410 - 649 + 491] = I("\u0001\u000f0\u0017\u0013\u0000\u001a\b\u0010\u0011\u000b\u0007\u0010'\u001e\u0001\n\u000f\u0016H", "nider");
        GameSettings.I[37 + 631 - 219 + 193] = I("\u001e*\n\b", "uOsWC");
        GameSettings.I[135 + 372 - 57 + 193] = I("c", "YQlUZ");
        GameSettings.I[365 + 208 + 7 + 64] = I("\f-,\u00162.l1\u0015w9-3\u001fw%<1\u00138$?", "JLEzW");
        GameSettings.I[56 + 607 - 174 + 156] = I("\n\n\n\u0005=\"\u001b", "NoldH");
    }
    
    public boolean getOptionOrdinalValue(final Options options) {
        switch (GameSettings$2.field_151477_a[options.ordinal()]) {
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
                return this.fboEnable;
            }
            case 5: {
                return this.chatColours;
            }
            case 6: {
                return this.chatLinks;
            }
            case 7: {
                return this.chatLinksPrompt;
            }
            case 8: {
                return this.snooperEnabled;
            }
            case 9: {
                return this.fullScreen;
            }
            case 10: {
                return this.enableVsync;
            }
            case 11: {
                return this.useVbo;
            }
            case 12: {
                return this.touchscreen;
            }
            case 13: {
                return this.streamSendMetadata;
            }
            case 14: {
                return this.forceUnicodeFont;
            }
            case 15: {
                return this.allowBlockAlternatives;
            }
            case 16: {
                return this.reducedDebugInfo;
            }
            case 17: {
                return this.field_181151_V;
            }
            default: {
                return "".length() != 0;
            }
        }
    }
    
    public void saveOptions() {
        if (Reflector.FMLClientHandler.exists()) {
            final Object call = Reflector.call(Reflector.FMLClientHandler_instance, new Object["".length()]);
            if (call != null && Reflector.callBoolean(call, Reflector.FMLClientHandler_isLoading, new Object["".length()])) {
                return;
            }
        }
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFile));
            printWriter.println(GameSettings.I[298 + 66 - 198 + 133] + this.invertMouse);
            printWriter.println(GameSettings.I[133 + 293 - 202 + 76] + this.mouseSensitivity);
            printWriter.println(GameSettings.I[226 + 255 - 447 + 267] + (this.fovSetting - 70.0f) / 40.0f);
            printWriter.println(GameSettings.I[11 + 41 + 114 + 136] + this.gammaSetting);
            printWriter.println(GameSettings.I[2 + 159 + 29 + 113] + this.saturation);
            printWriter.println(GameSettings.I[151 + 192 - 204 + 165] + this.renderDistanceChunks);
            printWriter.println(GameSettings.I[215 + 212 - 145 + 23] + this.guiScale);
            printWriter.println(GameSettings.I[270 + 186 - 294 + 144] + this.particleSetting);
            printWriter.println(GameSettings.I[41 + 148 + 118 + 0] + this.viewBobbing);
            printWriter.println(GameSettings.I[291 + 303 - 505 + 219] + this.anaglyph);
            printWriter.println(GameSettings.I[80 + 13 + 131 + 85] + this.limitFramerate);
            printWriter.println(GameSettings.I[36 + 109 - 98 + 263] + this.fboEnable);
            printWriter.println(GameSettings.I[303 + 23 - 196 + 181] + this.difficulty.getDifficultyId());
            printWriter.println(GameSettings.I[105 + 190 - 52 + 69] + this.fancyGraphics);
            printWriter.println(GameSettings.I[22 + 283 - 249 + 257] + this.ambientOcclusion);
            switch (this.clouds) {
                case 0: {
                    printWriter.println(GameSettings.I[7 + 74 + 184 + 49]);
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    printWriter.println(GameSettings.I[133 + 204 - 85 + 63]);
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    printWriter.println(GameSettings.I[183 + 148 - 329 + 314]);
                    break;
                }
            }
            printWriter.println(GameSettings.I[162 + 148 - 142 + 149] + GameSettings.gson.toJson((Object)this.resourcePacks));
            printWriter.println(GameSettings.I[10 + 259 - 69 + 118] + GameSettings.gson.toJson((Object)this.field_183018_l));
            printWriter.println(GameSettings.I[246 + 113 - 357 + 317] + this.lastServer);
            printWriter.println(GameSettings.I[291 + 111 - 389 + 307] + this.language);
            printWriter.println(GameSettings.I[164 + 170 - 184 + 171] + this.chatVisibility.getChatVisibility());
            printWriter.println(GameSettings.I[25 + 114 - 11 + 194] + this.chatColours);
            printWriter.println(GameSettings.I[138 + 25 - 97 + 257] + this.chatLinks);
            printWriter.println(GameSettings.I[139 + 320 - 390 + 255] + this.chatLinksPrompt);
            printWriter.println(GameSettings.I[283 + 289 - 480 + 233] + this.chatOpacity);
            printWriter.println(GameSettings.I[125 + 93 - 134 + 242] + this.snooperEnabled);
            printWriter.println(GameSettings.I[179 + 73 - 24 + 99] + this.fullScreen);
            printWriter.println(GameSettings.I[243 + 137 - 230 + 178] + this.enableVsync);
            printWriter.println(GameSettings.I[139 + 257 - 217 + 150] + this.useVbo);
            printWriter.println(GameSettings.I[37 + 131 + 61 + 101] + this.hideServerAddress);
            printWriter.println(GameSettings.I[324 + 275 - 302 + 34] + this.advancedItemTooltips);
            printWriter.println(GameSettings.I[38 + 222 - 211 + 283] + this.pauseOnLostFocus);
            printWriter.println(GameSettings.I[272 + 290 - 422 + 193] + this.touchscreen);
            printWriter.println(GameSettings.I[262 + 44 - 9 + 37] + this.overrideWidth);
            printWriter.println(GameSettings.I[263 + 96 - 309 + 285] + this.overrideHeight);
            printWriter.println(GameSettings.I[17 + 306 - 21 + 34] + this.heldItemTooltips);
            printWriter.println(GameSettings.I[5 + 97 - 74 + 309] + this.chatHeightFocused);
            printWriter.println(GameSettings.I[262 + 81 - 295 + 290] + this.chatHeightUnfocused);
            printWriter.println(GameSettings.I[83 + 220 - 246 + 282] + this.chatScale);
            printWriter.println(GameSettings.I[265 + 278 - 522 + 319] + this.chatWidth);
            printWriter.println(GameSettings.I[71 + 5 + 4 + 261] + this.showInventoryAchievementHint);
            printWriter.println(GameSettings.I[38 + 257 - 170 + 217] + this.mipmapLevels);
            printWriter.println(GameSettings.I[313 + 40 - 107 + 97] + this.streamBytesPerPixel);
            printWriter.println(GameSettings.I[64 + 61 + 186 + 33] + this.streamMicVolume);
            printWriter.println(GameSettings.I[344 + 53 - 78 + 26] + this.streamGameVolume);
            printWriter.println(GameSettings.I[226 + 55 - 243 + 308] + this.streamKbps);
            printWriter.println(GameSettings.I[130 + 83 + 87 + 47] + this.streamFps);
            printWriter.println(GameSettings.I[325 + 272 - 302 + 53] + this.streamCompression);
            printWriter.println(GameSettings.I[94 + 198 - 60 + 117] + this.streamSendMetadata);
            printWriter.println(GameSettings.I[320 + 99 - 131 + 62] + this.streamPreferredServer);
            printWriter.println(GameSettings.I[56 + 338 - 226 + 183] + this.streamChatEnabled);
            printWriter.println(GameSettings.I[253 + 238 - 197 + 58] + this.streamChatUserFilter);
            printWriter.println(GameSettings.I[328 + 319 - 498 + 204] + this.streamMicToggleBehavior);
            printWriter.println(GameSettings.I[67 + 244 - 215 + 258] + this.forceUnicodeFont);
            printWriter.println(GameSettings.I[307 + 92 - 221 + 177] + this.allowBlockAlternatives);
            printWriter.println(GameSettings.I[329 + 323 - 399 + 103] + this.reducedDebugInfo);
            printWriter.println(GameSettings.I[132 + 152 - 241 + 314] + this.field_181150_U);
            printWriter.println(GameSettings.I[164 + 7 - 159 + 346] + this.field_181151_V);
            final KeyBinding[] keyBindings;
            final int length = (keyBindings = this.keyBindings).length;
            int i = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (i < length) {
                final KeyBinding keyBinding = keyBindings[i];
                printWriter.println(GameSettings.I[173 + 313 - 431 + 304] + keyBinding.getKeyDescription() + GameSettings.I[198 + 121 - 304 + 345] + keyBinding.getKeyCode());
                ++i;
            }
            final SoundCategory[] values;
            final int length2 = (values = SoundCategory.values()).length;
            int j = "".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
            while (j < length2) {
                final SoundCategory soundCategory = values[j];
                printWriter.println(GameSettings.I[242 + 350 - 557 + 326] + soundCategory.getCategoryName() + GameSettings.I[8 + 39 + 281 + 34] + this.getSoundLevel(soundCategory));
                ++j;
            }
            final EnumPlayerModelParts[] values2;
            final int length3 = (values2 = EnumPlayerModelParts.values()).length;
            int k = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (k < length3) {
                final EnumPlayerModelParts enumPlayerModelParts = values2[k];
                printWriter.println(GameSettings.I[326 + 298 - 449 + 188] + enumPlayerModelParts.getPartName() + GameSettings.I[355 + 84 - 382 + 307] + this.setModelParts.contains(enumPlayerModelParts));
                ++k;
            }
            printWriter.close();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        catch (Exception ex) {
            GameSettings.logger.error(GameSettings.I[172 + 161 - 51 + 83], (Throwable)ex);
        }
        this.saveOfOptions();
        this.sendSettingsToServer();
    }
    
    public void switchModelPartEnabled(final EnumPlayerModelParts enumPlayerModelParts) {
        if (!this.getModelParts().contains(enumPlayerModelParts)) {
            this.setModelParts.add(enumPlayerModelParts);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.setModelParts.remove(enumPlayerModelParts);
        }
        this.sendSettingsToServer();
    }
    
    public GameSettings(final Minecraft mc, final File file) {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -" ".length();
        this.viewBobbing = (" ".length() != 0);
        this.fboEnable = (" ".length() != 0);
        this.limitFramerate = (0x6A ^ 0x12);
        this.clouds = "  ".length();
        this.fancyGraphics = (" ".length() != 0);
        this.ambientOcclusion = "  ".length();
        this.resourcePacks = Lists.newArrayList();
        this.field_183018_l = Lists.newArrayList();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = (" ".length() != 0);
        this.chatLinks = (" ".length() != 0);
        this.chatLinksPrompt = (" ".length() != 0);
        this.chatOpacity = 1.0f;
        this.snooperEnabled = (" ".length() != 0);
        this.enableVsync = (" ".length() != 0);
        this.useVbo = ("".length() != 0);
        this.allowBlockAlternatives = (" ".length() != 0);
        this.reducedDebugInfo = ("".length() != 0);
        this.pauseOnLostFocus = (" ".length() != 0);
        this.setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
        this.heldItemTooltips = (" ".length() != 0);
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = (" ".length() != 0);
        this.mipmapLevels = (0x7E ^ 0x7A);
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.streamBytesPerPixel = 0.5f;
        this.streamMicVolume = 1.0f;
        this.streamGameVolume = 1.0f;
        this.streamKbps = 0.5412844f;
        this.streamFps = 0.31690142f;
        this.streamCompression = " ".length();
        this.streamSendMetadata = (" ".length() != 0);
        this.streamPreferredServer = GameSettings.I[0x9E ^ 0x84];
        this.streamChatEnabled = "".length();
        this.streamChatUserFilter = "".length();
        this.streamMicToggleBehavior = "".length();
        this.field_181150_U = (" ".length() != 0);
        this.field_181151_V = (" ".length() != 0);
        this.keyBindForward = new KeyBinding(GameSettings.I[0x27 ^ 0x3C], 0x7C ^ 0x6D, GameSettings.I[0x26 ^ 0x3A]);
        this.keyBindLeft = new KeyBinding(GameSettings.I[0x4 ^ 0x19], 0x99 ^ 0x87, GameSettings.I[0xF ^ 0x11]);
        this.keyBindBack = new KeyBinding(GameSettings.I[0x78 ^ 0x67], 0xA6 ^ 0xB9, GameSettings.I[0x4B ^ 0x6B]);
        this.keyBindRight = new KeyBinding(GameSettings.I[0x9 ^ 0x28], 0x2C ^ 0xC, GameSettings.I[0x6A ^ 0x48]);
        this.keyBindJump = new KeyBinding(GameSettings.I[0x25 ^ 0x6], 0x4E ^ 0x77, GameSettings.I[0x1C ^ 0x38]);
        this.keyBindSneak = new KeyBinding(GameSettings.I[0x8E ^ 0xAB], 0x34 ^ 0x1E, GameSettings.I[0x9B ^ 0xBD]);
        this.keyBindSprint = new KeyBinding(GameSettings.I[0xA ^ 0x2D], 0xA0 ^ 0xBD, GameSettings.I[0x8 ^ 0x20]);
        this.keyBindInventory = new KeyBinding(GameSettings.I[0x8 ^ 0x21], 0xD4 ^ 0xC6, GameSettings.I[0x94 ^ 0xBE]);
        this.keyBindUseItem = new KeyBinding(GameSettings.I[0x1B ^ 0x30], -(0x1F ^ 0x7C), GameSettings.I[0xF ^ 0x23]);
        this.keyBindDrop = new KeyBinding(GameSettings.I[0x80 ^ 0xAD], 0xBD ^ 0xAD, GameSettings.I[0x33 ^ 0x1D]);
        this.keyBindAttack = new KeyBinding(GameSettings.I[0x15 ^ 0x3A], -(0x79 ^ 0x1D), GameSettings.I[0x30 ^ 0x0]);
        this.keyBindPickBlock = new KeyBinding(GameSettings.I[0x9A ^ 0xAB], -(0xA6 ^ 0xC4), GameSettings.I[0x4C ^ 0x7E]);
        this.keyBindChat = new KeyBinding(GameSettings.I[0x2 ^ 0x31], 0x1 ^ 0x15, GameSettings.I[0x65 ^ 0x51]);
        this.keyBindPlayerList = new KeyBinding(GameSettings.I[0x86 ^ 0xB3], 0xA1 ^ 0xAE, GameSettings.I[0xF3 ^ 0xC5]);
        this.keyBindCommand = new KeyBinding(GameSettings.I[0x47 ^ 0x70], 0xF4 ^ 0xC1, GameSettings.I[0x11 ^ 0x29]);
        this.keyBindScreenshot = new KeyBinding(GameSettings.I[0x5D ^ 0x64], 0x1B ^ 0x27, GameSettings.I[0xA5 ^ 0x9F]);
        this.keyBindTogglePerspective = new KeyBinding(GameSettings.I[0xAE ^ 0x95], 0x18 ^ 0x27, GameSettings.I[0x88 ^ 0xB4]);
        this.keyBindSmoothCamera = new KeyBinding(GameSettings.I[0xA5 ^ 0x98], "".length(), GameSettings.I[0x1B ^ 0x25]);
        this.keyBindFullscreen = new KeyBinding(GameSettings.I[0x89 ^ 0xB6], 0x51 ^ 0x6, GameSettings.I[0x6D ^ 0x2D]);
        this.keyBindSpectatorOutlines = new KeyBinding(GameSettings.I[0x70 ^ 0x31], "".length(), GameSettings.I[0x3F ^ 0x7D]);
        this.keyBindStreamStartStop = new KeyBinding(GameSettings.I[0x0 ^ 0x43], 0x8 ^ 0x48, GameSettings.I[0x2 ^ 0x46]);
        this.keyBindStreamPauseUnpause = new KeyBinding(GameSettings.I[0x1 ^ 0x44], 0x47 ^ 0x6, GameSettings.I[0x2A ^ 0x6C]);
        this.keyBindStreamCommercials = new KeyBinding(GameSettings.I[0x4F ^ 0x8], "".length(), GameSettings.I[0x51 ^ 0x19]);
        this.keyBindStreamToggleMic = new KeyBinding(GameSettings.I[0x3D ^ 0x74], "".length(), GameSettings.I[0xA ^ 0x40]);
        final KeyBinding[] keyBindsHotbar = new KeyBinding[0x6F ^ 0x66];
        keyBindsHotbar["".length()] = new KeyBinding(GameSettings.I[0x6E ^ 0x25], "  ".length(), GameSettings.I[0x88 ^ 0xC4]);
        keyBindsHotbar[" ".length()] = new KeyBinding(GameSettings.I[0xC7 ^ 0x8A], "   ".length(), GameSettings.I[0x8D ^ 0xC3]);
        keyBindsHotbar["  ".length()] = new KeyBinding(GameSettings.I[0x8B ^ 0xC4], 0x2D ^ 0x29, GameSettings.I[0x28 ^ 0x78]);
        keyBindsHotbar["   ".length()] = new KeyBinding(GameSettings.I[0xED ^ 0xBC], 0x6B ^ 0x6E, GameSettings.I[0x17 ^ 0x45]);
        keyBindsHotbar[0x85 ^ 0x81] = new KeyBinding(GameSettings.I[0x30 ^ 0x63], 0x4D ^ 0x4B, GameSettings.I[0x5F ^ 0xB]);
        keyBindsHotbar[0x2E ^ 0x2B] = new KeyBinding(GameSettings.I[0x5A ^ 0xF], 0x9C ^ 0x9B, GameSettings.I[0x77 ^ 0x21]);
        keyBindsHotbar[0x8B ^ 0x8D] = new KeyBinding(GameSettings.I[0xF9 ^ 0xAE], 0x12 ^ 0x1A, GameSettings.I[0x8 ^ 0x50]);
        keyBindsHotbar[0x8B ^ 0x8C] = new KeyBinding(GameSettings.I[0x71 ^ 0x28], 0x3A ^ 0x33, GameSettings.I[0xC8 ^ 0x92]);
        keyBindsHotbar[0x5C ^ 0x54] = new KeyBinding(GameSettings.I[0x32 ^ 0x69], 0xBE ^ 0xB4, GameSettings.I[0x2B ^ 0x77]);
        this.keyBindsHotbar = keyBindsHotbar;
        this.ofFogType = " ".length();
        this.ofFogStart = 0.8f;
        this.ofMipmapType = "".length();
        this.ofOcclusionFancy = ("".length() != 0);
        this.ofSmoothFps = ("".length() != 0);
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = "".length();
        this.ofAfLevel = " ".length();
        this.ofClouds = "".length();
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = "".length();
        this.ofRain = "".length();
        this.ofDroppedItems = "".length();
        this.ofBetterGrass = "   ".length();
        this.ofAutoSaveTicks = 436 + 42 - 109 + 3631;
        this.ofLagometer = ("".length() != 0);
        this.ofProfiler = ("".length() != 0);
        this.ofShowFps = ("".length() != 0);
        this.ofWeather = (" ".length() != 0);
        this.ofSky = (" ".length() != 0);
        this.ofStars = (" ".length() != 0);
        this.ofSunMoon = (" ".length() != 0);
        this.ofVignette = "".length();
        this.ofChunkUpdates = " ".length();
        this.ofChunkLoading = "".length();
        this.ofChunkUpdatesDynamic = ("".length() != 0);
        this.ofTime = "".length();
        this.ofClearWater = ("".length() != 0);
        this.ofBetterSnow = ("".length() != 0);
        this.ofFullscreenMode = GameSettings.I[0x9 ^ 0x54];
        this.ofSwampColors = (" ".length() != 0);
        this.ofRandomMobs = (" ".length() != 0);
        this.ofSmoothBiomes = (" ".length() != 0);
        this.ofCustomFonts = (" ".length() != 0);
        this.ofCustomColors = (" ".length() != 0);
        this.ofCustomSky = (" ".length() != 0);
        this.ofShowCapes = (" ".length() != 0);
        this.ofConnectedTextures = "  ".length();
        this.ofNaturalTextures = ("".length() != 0);
        this.ofFastMath = ("".length() != 0);
        this.ofFastRender = (" ".length() != 0);
        this.ofTranslucentBlocks = "".length();
        this.ofAnimatedWater = "".length();
        this.ofAnimatedLava = "".length();
        this.ofAnimatedFire = (" ".length() != 0);
        this.ofAnimatedPortal = (" ".length() != 0);
        this.ofAnimatedRedstone = (" ".length() != 0);
        this.ofAnimatedExplosion = (" ".length() != 0);
        this.ofAnimatedFlame = (" ".length() != 0);
        this.ofAnimatedSmoke = (" ".length() != 0);
        this.ofVoidParticles = (" ".length() != 0);
        this.ofWaterParticles = (" ".length() != 0);
        this.ofRainSplash = (" ".length() != 0);
        this.ofPortalParticles = (" ".length() != 0);
        this.ofPotionParticles = (" ".length() != 0);
        this.ofFireworkParticles = (" ".length() != 0);
        this.ofDrippingWaterLava = (" ".length() != 0);
        this.ofAnimatedTerrain = (" ".length() != 0);
        this.ofAnimatedTextures = (" ".length() != 0);
        final KeyBinding[] array = new KeyBinding[0x9C ^ 0x84];
        array["".length()] = this.keyBindAttack;
        array[" ".length()] = this.keyBindUseItem;
        array["  ".length()] = this.keyBindForward;
        array["   ".length()] = this.keyBindLeft;
        array[0x78 ^ 0x7C] = this.keyBindBack;
        array[0x3D ^ 0x38] = this.keyBindRight;
        array[0x7F ^ 0x79] = this.keyBindJump;
        array[0x25 ^ 0x22] = this.keyBindSneak;
        array[0xAB ^ 0xA3] = this.keyBindSprint;
        array[0x7B ^ 0x72] = this.keyBindDrop;
        array[0x26 ^ 0x2C] = this.keyBindInventory;
        array[0x54 ^ 0x5F] = this.keyBindChat;
        array[0x3F ^ 0x33] = this.keyBindPlayerList;
        array[0xB0 ^ 0xBD] = this.keyBindPickBlock;
        array[0x29 ^ 0x27] = this.keyBindCommand;
        array[0xBE ^ 0xB1] = this.keyBindScreenshot;
        array[0x6A ^ 0x7A] = this.keyBindTogglePerspective;
        array[0x3 ^ 0x12] = this.keyBindSmoothCamera;
        array[0x51 ^ 0x43] = this.keyBindStreamStartStop;
        array[0x8A ^ 0x99] = this.keyBindStreamPauseUnpause;
        array[0x16 ^ 0x2] = this.keyBindStreamCommercials;
        array[0x2 ^ 0x17] = this.keyBindStreamToggleMic;
        array[0x6E ^ 0x78] = this.keyBindFullscreen;
        array[0x42 ^ 0x55] = this.keyBindSpectatorOutlines;
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])array, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = GameSettings.I[0xC7 ^ 0x99];
        this.fovSetting = 70.0f;
        this.language = GameSettings.I[0xF2 ^ 0xAD];
        this.forceUnicodeFont = ("".length() != 0);
        this.mc = mc;
        this.optionsFile = new File(file, GameSettings.I[0xCA ^ 0xAA]);
        this.optionsFileOF = new File(file, GameSettings.I[0x1 ^ 0x60]);
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding(GameSettings.I[0x3C ^ 0x5E], 0x88 ^ 0xA6, GameSettings.I[0xA2 ^ 0xC1]);
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
        Options.RENDER_DISTANCE.setValueMax(32.0f);
        this.renderDistanceChunks = (0x2D ^ 0x25);
        this.loadOptions();
        Config.initGameSettings(this);
    }
    
    public float getSoundLevel(final SoundCategory soundCategory) {
        float floatValue;
        if (this.mapSoundLevels.containsKey(soundCategory)) {
            floatValue = this.mapSoundLevels.get(soundCategory);
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            floatValue = 1.0f;
        }
        return floatValue;
    }
    
    public String getKeyBinding(final Options options) {
        final String keyBindingOF = this.getKeyBindingOF(options);
        if (keyBindingOF != null) {
            return keyBindingOF;
        }
        final String string = String.valueOf(I18n.format(options.getEnumString(), new Object["".length()])) + GameSettings.I[79 + 87 + 1 + 5];
        if (options.getEnumFloat()) {
            final float optionFloatValue = this.getOptionFloatValue(options);
            final float normalizeValue = options.normalizeValue(optionFloatValue);
            String s;
            if (options == Options.SENSITIVITY) {
                if (normalizeValue == 0.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[151 + 49 - 86 + 59], new Object["".length()]);
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                else if (normalizeValue == 1.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[32 + 51 - 14 + 105], new Object["".length()]);
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + (int)(normalizeValue * 200.0f) + GameSettings.I[167 + 170 - 203 + 41];
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
            }
            else if (options == Options.FOV) {
                if (optionFloatValue == 70.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[95 + 134 - 193 + 140], new Object["".length()]);
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else if (optionFloatValue == 110.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[100 + 94 - 186 + 169], new Object["".length()]);
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + (int)optionFloatValue;
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                }
            }
            else if (options == Options.FRAMERATE_LIMIT) {
                if (optionFloatValue == Options.access$2(options)) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[131 + 82 - 147 + 112], new Object["".length()]);
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + (int)optionFloatValue + GameSettings.I[11 + 64 - 63 + 167];
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
            }
            else if (options == Options.RENDER_CLOUDS) {
                if (optionFloatValue == Options.access$3(options)) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[41 + 54 - 32 + 117], new Object["".length()]);
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + ((int)optionFloatValue + (78 + 53 - 26 + 23));
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                }
            }
            else if (options == Options.GAMMA) {
                if (normalizeValue == 0.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[2 + 166 - 33 + 46], new Object["".length()]);
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else if (normalizeValue == 1.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[44 + 108 - 26 + 56], new Object["".length()]);
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + GameSettings.I[49 + 38 + 69 + 27] + (int)(normalizeValue * 100.0f) + GameSettings.I[146 + 131 - 275 + 182];
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
            }
            else if (options == Options.SATURATION) {
                s = String.valueOf(string) + (int)(normalizeValue * 400.0f) + GameSettings.I[176 + 81 - 92 + 20];
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else if (options == Options.CHAT_OPACITY) {
                s = String.valueOf(string) + (int)(normalizeValue * 90.0f + 10.0f) + GameSettings.I[165 + 70 - 147 + 98];
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (options == Options.CHAT_HEIGHT_UNFOCUSED) {
                s = String.valueOf(string) + GuiNewChat.calculateChatboxHeight(normalizeValue) + GameSettings.I[5 + 142 - 48 + 88];
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (options == Options.CHAT_HEIGHT_FOCUSED) {
                s = String.valueOf(string) + GuiNewChat.calculateChatboxHeight(normalizeValue) + GameSettings.I[5 + 151 - 130 + 162];
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else if (options == Options.CHAT_WIDTH) {
                s = String.valueOf(string) + GuiNewChat.calculateChatboxWidth(normalizeValue) + GameSettings.I[77 + 150 - 99 + 61];
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else if (options == Options.RENDER_DISTANCE) {
                s = String.valueOf(string) + (int)optionFloatValue + GameSettings.I[112 + 104 - 58 + 32];
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (options == Options.MIPMAP_LEVELS) {
                if (optionFloatValue == 0.0f) {
                    s = String.valueOf(string) + I18n.format(GameSettings.I[6 + 88 + 29 + 68], new Object["".length()]);
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(string) + (int)optionFloatValue;
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
            }
            else if (options == Options.STREAM_FPS) {
                s = String.valueOf(string) + TwitchStream.formatStreamFps(normalizeValue) + GameSettings.I[7 + 109 - 35 + 111];
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else if (options == Options.STREAM_KBPS) {
                s = String.valueOf(string) + TwitchStream.formatStreamKbps(normalizeValue) + GameSettings.I[73 + 139 - 52 + 33];
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else if (options == Options.STREAM_BYTES_PER_PIXEL) {
                final StringBuilder sb = new StringBuilder(String.valueOf(string));
                final String s2 = GameSettings.I[142 + 73 - 55 + 34];
                final Object[] array = new Object[" ".length()];
                array["".length()] = TwitchStream.formatStreamBps(normalizeValue);
                s = sb.append(String.format(s2, array)).toString();
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else if (normalizeValue == 0.0f) {
                s = String.valueOf(string) + I18n.format(GameSettings.I[182 + 88 - 86 + 11], new Object["".length()]);
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                s = String.valueOf(string) + (int)(normalizeValue * 100.0f) + GameSettings.I[117 + 22 - 127 + 184];
            }
            return s;
        }
        if (options.getEnumBoolean()) {
            String s3;
            if (this.getOptionOrdinalValue(options)) {
                s3 = String.valueOf(string) + I18n.format(GameSettings.I[87 + 17 + 92 + 1], new Object["".length()]);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                s3 = String.valueOf(string) + I18n.format(GameSettings.I[135 + 97 - 226 + 192], new Object["".length()]);
            }
            return s3;
        }
        if (options == Options.GUI_SCALE) {
            return String.valueOf(string) + getTranslation(GameSettings.GUISCALES, this.guiScale);
        }
        if (options == Options.CHAT_VISIBILITY) {
            return String.valueOf(string) + I18n.format(this.chatVisibility.getResourceKey(), new Object["".length()]);
        }
        if (options == Options.PARTICLES) {
            return String.valueOf(string) + getTranslation(GameSettings.PARTICLES, this.particleSetting);
        }
        if (options == Options.AMBIENT_OCCLUSION) {
            return String.valueOf(string) + getTranslation(GameSettings.AMBIENT_OCCLUSIONS, this.ambientOcclusion);
        }
        if (options == Options.STREAM_COMPRESSION) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_COMPRESSIONS, this.streamCompression);
        }
        if (options == Options.STREAM_CHAT_ENABLED) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_CHAT_MODES, this.streamChatEnabled);
        }
        if (options == Options.STREAM_CHAT_USER_FILTER) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_CHAT_FILTER_MODES, this.streamChatUserFilter);
        }
        if (options == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return String.valueOf(string) + getTranslation(GameSettings.STREAM_MIC_MODES, this.streamMicToggleBehavior);
        }
        if (options == Options.RENDER_CLOUDS) {
            return String.valueOf(string) + getTranslation(GameSettings.field_181149_aW, this.clouds);
        }
        if (options != Options.GRAPHICS) {
            return string;
        }
        if (this.fancyGraphics) {
            return String.valueOf(string) + I18n.format(GameSettings.I[30 + 159 - 60 + 70], new Object["".length()]);
        }
        final String s4 = GameSettings.I[143 + 184 - 194 + 67];
        return String.valueOf(string) + I18n.format(GameSettings.I[138 + 84 - 65 + 44], new Object["".length()]);
    }
    
    public void setAllAnimations(final boolean ofAnimatedTextures) {
        int n;
        if (ofAnimatedTextures) {
            n = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = "  ".length();
        }
        final int n2 = n;
        this.ofAnimatedWater = n2;
        this.ofAnimatedLava = n2;
        this.ofAnimatedFire = ofAnimatedTextures;
        this.ofAnimatedPortal = ofAnimatedTextures;
        this.ofAnimatedRedstone = ofAnimatedTextures;
        this.ofAnimatedExplosion = ofAnimatedTextures;
        this.ofAnimatedFlame = ofAnimatedTextures;
        this.ofAnimatedSmoke = ofAnimatedTextures;
        this.ofVoidParticles = ofAnimatedTextures;
        this.ofWaterParticles = ofAnimatedTextures;
        this.ofRainSplash = ofAnimatedTextures;
        this.ofPortalParticles = ofAnimatedTextures;
        this.ofPotionParticles = ofAnimatedTextures;
        this.ofFireworkParticles = ofAnimatedTextures;
        int particleSetting;
        if (ofAnimatedTextures) {
            particleSetting = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            particleSetting = "  ".length();
        }
        this.particleSetting = particleSetting;
        this.ofDrippingWaterLava = ofAnimatedTextures;
        this.ofAnimatedTerrain = ofAnimatedTextures;
        this.ofAnimatedTextures = ofAnimatedTextures;
    }
    
    public void setModelPartEnabled(final EnumPlayerModelParts enumPlayerModelParts, final boolean b) {
        if (b) {
            this.setModelParts.add(enumPlayerModelParts);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            this.setModelParts.remove(enumPlayerModelParts);
        }
        this.sendSettingsToServer();
    }
    
    private static String getTranslation(final String[] array, int length) {
        if (length < 0 || length >= array.length) {
            length = "".length();
        }
        return I18n.format(array[length], new Object["".length()]);
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        this.mc.getSoundHandler().setSoundLevel(soundCategory, n);
        this.mapSoundLevels.put(soundCategory, n);
    }
    
    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile));
            final String s = GameSettings.I[170 + 58 - 101 + 75];
            this.mapSoundLevels.clear();
            "".length();
            if (4 == -1) {
                throw null;
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    final String[] split = line.split(GameSettings.I[49 + 102 - 90 + 142]);
                    if (split["".length()].equals(GameSettings.I[82 + 110 - 175 + 187])) {
                        this.mouseSensitivity = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[97 + 14 - 77 + 171])) {
                        this.fovSetting = this.parseFloat(split[" ".length()]) * 40.0f + 70.0f;
                    }
                    if (split["".length()].equals(GameSettings.I[59 + 20 + 116 + 11])) {
                        this.gammaSetting = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[40 + 7 - 22 + 182])) {
                        this.saturation = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[194 + 66 - 179 + 127])) {
                        this.invertMouse = split[" ".length()].equals(GameSettings.I[145 + 0 + 27 + 37]);
                    }
                    if (split["".length()].equals(GameSettings.I[117 + 173 - 152 + 72])) {
                        this.renderDistanceChunks = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[167 + 161 - 273 + 156])) {
                        this.guiScale = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[76 + 198 - 120 + 58])) {
                        this.particleSetting = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[188 + 99 - 199 + 125])) {
                        this.viewBobbing = split[" ".length()].equals(GameSettings.I[120 + 131 - 184 + 147]);
                    }
                    if (split["".length()].equals(GameSettings.I[168 + 168 - 305 + 184])) {
                        this.anaglyph = split[" ".length()].equals(GameSettings.I[13 + 32 + 94 + 77]);
                    }
                    if (split["".length()].equals(GameSettings.I[208 + 200 - 200 + 9])) {
                        this.limitFramerate = Integer.parseInt(split[" ".length()]);
                        this.enableVsync = ("".length() != 0);
                        if (this.limitFramerate <= 0) {
                            this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                            this.enableVsync = (" ".length() != 0);
                        }
                        this.updateVSync();
                    }
                    if (split["".length()].equals(GameSettings.I[39 + 5 + 174 + 0])) {
                        this.fboEnable = split[" ".length()].equals(GameSettings.I[37 + 54 + 96 + 32]);
                    }
                    if (split["".length()].equals(GameSettings.I[34 + 5 + 166 + 15])) {
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(split[" ".length()]));
                    }
                    if (split["".length()].equals(GameSettings.I[176 + 112 - 105 + 38])) {
                        this.fancyGraphics = split[" ".length()].equals(GameSettings.I[84 + 60 - 30 + 108]);
                        this.updateRenderClouds();
                    }
                    if (split["".length()].equals(GameSettings.I[62 + 99 - 88 + 150])) {
                        if (split[" ".length()].equals(GameSettings.I[117 + 189 - 146 + 64])) {
                            this.ambientOcclusion = "  ".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else if (split[" ".length()].equals(GameSettings.I[34 + 153 - 160 + 198])) {
                            this.ambientOcclusion = "".length();
                            "".length();
                            if (1 <= 0) {
                                throw null;
                            }
                        }
                        else {
                            this.ambientOcclusion = Integer.parseInt(split[" ".length()]);
                        }
                    }
                    if (split["".length()].equals(GameSettings.I[156 + 118 - 86 + 38])) {
                        if (split[" ".length()].equals(GameSettings.I[51 + 9 + 111 + 56])) {
                            this.clouds = "  ".length();
                            "".length();
                            if (1 < 0) {
                                throw null;
                            }
                        }
                        else if (split[" ".length()].equals(GameSettings.I[96 + 13 + 61 + 58])) {
                            this.clouds = "".length();
                            "".length();
                            if (2 <= 0) {
                                throw null;
                            }
                        }
                        else if (split[" ".length()].equals(GameSettings.I[138 + 186 - 112 + 17])) {
                            this.clouds = " ".length();
                        }
                    }
                    if (split["".length()].equals(GameSettings.I[59 + 84 + 25 + 62])) {
                        this.resourcePacks = (List)GameSettings.gson.fromJson(line.substring(line.indexOf(0x5 ^ 0x3F) + " ".length()), (Type)GameSettings.typeListString);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = Lists.newArrayList();
                        }
                    }
                    if (split["".length()].equals(GameSettings.I[40 + 121 + 43 + 27])) {
                        this.field_183018_l = (List)GameSettings.gson.fromJson(line.substring(line.indexOf(0x80 ^ 0xBA) + " ".length()), (Type)GameSettings.typeListString);
                        if (this.field_183018_l == null) {
                            this.field_183018_l = Lists.newArrayList();
                        }
                    }
                    if (split["".length()].equals(GameSettings.I[21 + 118 - 113 + 206]) && split.length >= "  ".length()) {
                        this.lastServer = line.substring(line.indexOf(0xFF ^ 0xC5) + " ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[96 + 224 - 300 + 213]) && split.length >= "  ".length()) {
                        this.language = split[" ".length()];
                    }
                    if (split["".length()].equals(GameSettings.I[194 + 96 - 220 + 164])) {
                        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(split[" ".length()]));
                    }
                    if (split["".length()].equals(GameSettings.I[30 + 18 + 62 + 125])) {
                        this.chatColours = split[" ".length()].equals(GameSettings.I[80 + 24 + 66 + 66]);
                    }
                    if (split["".length()].equals(GameSettings.I[35 + 89 - 88 + 201])) {
                        this.chatLinks = split[" ".length()].equals(GameSettings.I[77 + 79 - 106 + 188]);
                    }
                    if (split["".length()].equals(GameSettings.I[234 + 79 - 182 + 108])) {
                        this.chatLinksPrompt = split[" ".length()].equals(GameSettings.I[138 + 43 + 41 + 18]);
                    }
                    if (split["".length()].equals(GameSettings.I[26 + 34 + 13 + 168])) {
                        this.chatOpacity = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[207 + 134 - 239 + 140])) {
                        this.snooperEnabled = split[" ".length()].equals(GameSettings.I[53 + 161 - 37 + 66]);
                    }
                    if (split["".length()].equals(GameSettings.I[188 + 204 - 377 + 229])) {
                        this.fullScreen = split[" ".length()].equals(GameSettings.I[46 + 167 - 76 + 108]);
                    }
                    if (split["".length()].equals(GameSettings.I[193 + 141 - 179 + 91])) {
                        this.enableVsync = split[" ".length()].equals(GameSettings.I[18 + 107 - 8 + 130]);
                        this.updateVSync();
                    }
                    if (split["".length()].equals(GameSettings.I[137 + 47 + 48 + 16])) {
                        this.useVbo = split[" ".length()].equals(GameSettings.I[57 + 75 + 69 + 48]);
                    }
                    if (split["".length()].equals(GameSettings.I[106 + 136 - 194 + 202])) {
                        this.hideServerAddress = split[" ".length()].equals(GameSettings.I[85 + 150 - 114 + 130]);
                    }
                    if (split["".length()].equals(GameSettings.I[120 + 179 - 176 + 129])) {
                        this.advancedItemTooltips = split[" ".length()].equals(GameSettings.I[128 + 233 - 155 + 47]);
                    }
                    if (split["".length()].equals(GameSettings.I[37 + 244 - 185 + 158])) {
                        this.pauseOnLostFocus = split[" ".length()].equals(GameSettings.I[253 + 110 - 283 + 175]);
                    }
                    if (split["".length()].equals(GameSettings.I[188 + 111 - 78 + 35])) {
                        this.touchscreen = split[" ".length()].equals(GameSettings.I[248 + 163 - 182 + 28]);
                    }
                    if (split["".length()].equals(GameSettings.I[224 + 127 - 195 + 102])) {
                        this.overrideHeight = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[174 + 131 - 75 + 29])) {
                        this.overrideWidth = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[146 + 162 - 59 + 11])) {
                        this.heldItemTooltips = split[" ".length()].equals(GameSettings.I[66 + 26 + 40 + 129]);
                    }
                    if (split["".length()].equals(GameSettings.I[260 + 145 - 297 + 154])) {
                        this.chatHeightFocused = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[141 + 216 - 322 + 228])) {
                        this.chatHeightUnfocused = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[160 + 155 - 117 + 66])) {
                        this.chatScale = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[106 + 256 - 107 + 10])) {
                        this.chatWidth = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[114 + 40 - 4 + 116])) {
                        this.showInventoryAchievementHint = split[" ".length()].equals(GameSettings.I[88 + 77 - 9 + 111]);
                    }
                    if (split["".length()].equals(GameSettings.I[243 + 64 - 204 + 165])) {
                        this.mipmapLevels = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[103 + 176 - 188 + 178])) {
                        this.streamBytesPerPixel = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[221 + 44 - 30 + 35])) {
                        this.streamMicVolume = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[259 + 38 - 93 + 67])) {
                        this.streamGameVolume = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[125 + 201 - 153 + 99])) {
                        this.streamKbps = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[8 + 200 - 89 + 154])) {
                        this.streamFps = this.parseFloat(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[163 + 62 - 111 + 160])) {
                        this.streamCompression = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[105 + 172 - 40 + 38])) {
                        this.streamSendMetadata = split[" ".length()].equals(GameSettings.I[51 + 144 - 191 + 272]);
                    }
                    if (split["".length()].equals(GameSettings.I[75 + 22 - 34 + 214]) && split.length >= "  ".length()) {
                        this.streamPreferredServer = line.substring(line.indexOf(0x66 ^ 0x5C) + " ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[41 + 157 - 194 + 274])) {
                        this.streamChatEnabled = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[96 + 229 - 54 + 8])) {
                        this.streamChatUserFilter = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[5 + 56 + 164 + 55])) {
                        this.streamMicToggleBehavior = Integer.parseInt(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[84 + 238 - 160 + 119])) {
                        this.forceUnicodeFont = split[" ".length()].equals(GameSettings.I[123 + 26 + 12 + 121]);
                    }
                    if (split["".length()].equals(GameSettings.I[250 + 71 - 114 + 76])) {
                        this.allowBlockAlternatives = split[" ".length()].equals(GameSettings.I[192 + 147 - 143 + 88]);
                    }
                    if (split["".length()].equals(GameSettings.I[211 + 94 - 64 + 44])) {
                        this.reducedDebugInfo = split[" ".length()].equals(GameSettings.I[12 + 92 - 67 + 249]);
                    }
                    if (split["".length()].equals(GameSettings.I[69 + 32 + 163 + 23])) {
                        this.field_181150_U = split[" ".length()].equals(GameSettings.I[82 + 61 + 128 + 17]);
                    }
                    if (split["".length()].equals(GameSettings.I[224 + 49 - 60 + 76])) {
                        this.field_181151_V = split[" ".length()].equals(GameSettings.I[287 + 188 - 285 + 100]);
                    }
                    final KeyBinding[] keyBindings;
                    final int length = (keyBindings = this.keyBindings).length;
                    int i = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    while (i < length) {
                        final KeyBinding keyBinding = keyBindings[i];
                        if (split["".length()].equals(GameSettings.I[258 + 234 - 281 + 80] + keyBinding.getKeyDescription())) {
                            keyBinding.setKeyCode(Integer.parseInt(split[" ".length()]));
                        }
                        ++i;
                    }
                    final SoundCategory[] values;
                    final int length2 = (values = SoundCategory.values()).length;
                    int j = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    while (j < length2) {
                        final SoundCategory soundCategory = values[j];
                        if (split["".length()].equals(GameSettings.I[162 + 254 - 400 + 276] + soundCategory.getCategoryName())) {
                            this.mapSoundLevels.put(soundCategory, this.parseFloat(split[" ".length()]));
                        }
                        ++j;
                    }
                    final EnumPlayerModelParts[] values2;
                    final int length3 = (values2 = EnumPlayerModelParts.values()).length;
                    int k = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    while (k < length3) {
                        final EnumPlayerModelParts enumPlayerModelParts = values2[k];
                        if (split["".length()].equals(GameSettings.I[284 + 146 - 322 + 185] + enumPlayerModelParts.getPartName())) {
                            this.setModelPartEnabled(enumPlayerModelParts, split[" ".length()].equals(GameSettings.I[90 + 75 + 57 + 72]));
                        }
                        ++k;
                    }
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    continue;
                }
                catch (Exception ex) {
                    GameSettings.logger.warn(GameSettings.I[190 + 100 - 77 + 82] + line);
                    ex.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedReader.close();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (Exception ex2) {
            GameSettings.logger.error(GameSettings.I[224 + 224 - 225 + 73], (Throwable)ex2);
        }
        this.loadOfOptions();
    }
    
    private void updateWaterOpacity() {
        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            Config.waterOpacityChanged = (" ".length() != 0);
        }
        ClearWater.updateWaterOpacity(this, this.mc.theWorld);
    }
    
    public void updateChunkLoading() {
        if (this.mc.renderGlobal != null) {
            this.mc.renderGlobal.loadRenderers();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void resetSettings() {
        this.renderDistanceChunks = (0x14 ^ 0x1C);
        this.viewBobbing = (" ".length() != 0);
        this.anaglyph = ("".length() != 0);
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = ("".length() != 0);
        this.updateVSync();
        this.mipmapLevels = (0x26 ^ 0x22);
        this.fancyGraphics = (" ".length() != 0);
        this.ambientOcclusion = "  ".length();
        this.clouds = "  ".length();
        this.fovSetting = 70.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = "".length();
        this.particleSetting = "".length();
        this.heldItemTooltips = (" ".length() != 0);
        this.useVbo = ("".length() != 0);
        this.allowBlockAlternatives = (" ".length() != 0);
        this.forceUnicodeFont = ("".length() != 0);
        this.ofFogType = " ".length();
        this.ofFogStart = 0.8f;
        this.ofMipmapType = "".length();
        this.ofOcclusionFancy = ("".length() != 0);
        this.ofSmoothFps = ("".length() != 0);
        Config.updateAvailableProcessors();
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofFastMath = ("".length() != 0);
        this.ofFastRender = (" ".length() != 0);
        this.ofTranslucentBlocks = "".length();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = "".length();
        this.ofAfLevel = " ".length();
        this.ofClouds = "".length();
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = "".length();
        this.ofRain = "".length();
        this.ofBetterGrass = "   ".length();
        this.ofAutoSaveTicks = 3420 + 1833 - 5215 + 3962;
        this.ofLagometer = ("".length() != 0);
        this.ofShowFps = ("".length() != 0);
        this.ofProfiler = ("".length() != 0);
        this.ofWeather = (" ".length() != 0);
        this.ofSky = (" ".length() != 0);
        this.ofStars = (" ".length() != 0);
        this.ofSunMoon = (" ".length() != 0);
        this.ofVignette = "".length();
        this.ofChunkUpdates = " ".length();
        this.ofChunkLoading = "".length();
        this.ofChunkUpdatesDynamic = ("".length() != 0);
        this.ofTime = "".length();
        this.ofClearWater = ("".length() != 0);
        this.ofBetterSnow = ("".length() != 0);
        this.ofFullscreenMode = GameSettings.I[173 + 428 - 392 + 436];
        this.ofSwampColors = (" ".length() != 0);
        this.ofRandomMobs = (" ".length() != 0);
        this.ofSmoothBiomes = (" ".length() != 0);
        this.ofCustomFonts = (" ".length() != 0);
        this.ofCustomColors = (" ".length() != 0);
        this.ofCustomSky = (" ".length() != 0);
        this.ofShowCapes = (" ".length() != 0);
        this.ofConnectedTextures = "  ".length();
        this.ofNaturalTextures = ("".length() != 0);
        this.ofAnimatedWater = "".length();
        this.ofAnimatedLava = "".length();
        this.ofAnimatedFire = (" ".length() != 0);
        this.ofAnimatedPortal = (" ".length() != 0);
        this.ofAnimatedRedstone = (" ".length() != 0);
        this.ofAnimatedExplosion = (" ".length() != 0);
        this.ofAnimatedFlame = (" ".length() != 0);
        this.ofAnimatedSmoke = (" ".length() != 0);
        this.ofVoidParticles = (" ".length() != 0);
        this.ofWaterParticles = (" ".length() != 0);
        this.ofRainSplash = (" ".length() != 0);
        this.ofPortalParticles = (" ".length() != 0);
        this.ofPotionParticles = (" ".length() != 0);
        this.ofFireworkParticles = (" ".length() != 0);
        this.ofDrippingWaterLava = (" ".length() != 0);
        this.ofAnimatedTerrain = (" ".length() != 0);
        this.ofAnimatedTextures = (" ".length() != 0);
        this.updateWaterOpacity();
        this.mc.refreshResources();
        this.saveOptions();
    }
    
    private String getKeyBindingOF(final Options options) {
        String s = String.valueOf(I18n.format(options.getEnumString(), new Object["".length()])) + GameSettings.I[295 + 308 - 574 + 340];
        if (s == null) {
            s = options.getEnumString();
        }
        if (options == Options.RENDER_DISTANCE) {
            final int n = (int)this.getOptionFloatValue(options);
            String s2 = GameSettings.I[232 + 54 - 196 + 280];
            int length = "  ".length();
            if (n >= (0x38 ^ 0x3C)) {
                s2 = GameSettings.I[306 + 345 - 307 + 27];
                length = (0x57 ^ 0x53);
            }
            if (n >= (0xAF ^ 0xA7)) {
                s2 = GameSettings.I[154 + 176 - 225 + 267];
                length = (0x18 ^ 0x10);
            }
            if (n >= (0x8F ^ 0x9F)) {
                s2 = GameSettings.I[185 + 342 - 280 + 126];
                length = (0x6C ^ 0x7C);
            }
            if (n >= (0xBE ^ 0x9E)) {
                s2 = GameSettings.I[123 + 69 - 35 + 217];
                length = (0x1E ^ 0x3E);
            }
            final int n2 = this.renderDistanceChunks - length;
            String string = s2;
            if (n2 > 0) {
                string = String.valueOf(s2) + GameSettings.I[130 + 234 + 2 + 9];
            }
            return String.valueOf(s) + n + GameSettings.I[323 + 299 - 397 + 151] + string;
        }
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return String.valueOf(s) + GameSettings.I[351 + 22 - 148 + 152];
                }
                case 2: {
                    return String.valueOf(s) + GameSettings.I[150 + 270 - 58 + 16];
                }
                case 3: {
                    return String.valueOf(s) + GameSettings.I[135 + 285 - 224 + 183];
                }
                default: {
                    return String.valueOf(s) + GameSettings.I[8 + 317 - 204 + 259];
                }
            }
        }
        else {
            if (options == Options.FOG_START) {
                return String.valueOf(s) + this.ofFogStart;
            }
            if (options == Options.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0: {
                        return String.valueOf(s) + GameSettings.I[20 + 42 + 167 + 152];
                    }
                    case 1: {
                        return String.valueOf(s) + GameSettings.I[95 + 355 - 290 + 222];
                    }
                    case 2: {
                        return String.valueOf(s) + GameSettings.I[81 + 243 - 209 + 268];
                    }
                    case 3: {
                        return String.valueOf(s) + GameSettings.I[272 + 230 - 313 + 195];
                    }
                    default: {
                        return String.valueOf(s) + GameSettings.I[133 + 309 - 177 + 120];
                    }
                }
            }
            else {
                if (options == Options.SMOOTH_FPS) {
                    String s3;
                    if (this.ofSmoothFps) {
                        s3 = String.valueOf(s) + GameSettings.I[143 + 115 + 1 + 127];
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        s3 = String.valueOf(s) + GameSettings.I[157 + 149 - 153 + 234];
                    }
                    return s3;
                }
                if (options == Options.SMOOTH_WORLD) {
                    String s4;
                    if (this.ofSmoothWorld) {
                        s4 = String.valueOf(s) + GameSettings.I[207 + 191 - 199 + 189];
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        s4 = String.valueOf(s) + GameSettings.I[16 + 371 - 230 + 232];
                    }
                    return s4;
                }
                if (options == Options.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[173 + 187 - 335 + 365];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[198 + 283 - 389 + 299];
                        }
                        case 3: {
                            return String.valueOf(s) + GameSettings.I[100 + 205 - 113 + 200];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[40 + 29 - 27 + 351];
                        }
                    }
                }
                else if (options == Options.TREES) {
                    switch (this.ofTrees) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[117 + 4 - 61 + 334];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[238 + 174 - 190 + 173];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[260 + 65 - 245 + 316];
                        }
                    }
                }
                else if (options == Options.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[115 + 22 + 102 + 158];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[312 + 179 - 219 + 126];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[66 + 102 + 145 + 86];
                        }
                    }
                }
                else if (options == Options.RAIN) {
                    switch (this.ofRain) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[127 + 128 - 213 + 358];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[254 + 382 - 526 + 291];
                        }
                        case 3: {
                            return String.valueOf(s) + GameSettings.I[33 + 4 - 6 + 371];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[17 + 138 + 9 + 239];
                        }
                    }
                }
                else if (options == Options.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[369 + 369 - 553 + 219];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[152 + 393 - 196 + 56];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[77 + 98 + 50 + 181];
                        }
                    }
                }
                else if (options == Options.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1: {
                            return String.valueOf(s) + GameSettings.I[262 + 30 - 177 + 292];
                        }
                        case 2: {
                            return String.valueOf(s) + GameSettings.I[3 + 179 - 95 + 321];
                        }
                        default: {
                            return String.valueOf(s) + GameSettings.I[173 + 132 - 188 + 292];
                        }
                    }
                }
                else {
                    if (options == Options.ANIMATED_FIRE) {
                        String s5;
                        if (this.ofAnimatedFire) {
                            s5 = String.valueOf(s) + GameSettings.I[194 + 334 - 415 + 297];
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        else {
                            s5 = String.valueOf(s) + GameSettings.I[123 + 273 - 201 + 216];
                        }
                        return s5;
                    }
                    if (options == Options.ANIMATED_PORTAL) {
                        String s6;
                        if (this.ofAnimatedPortal) {
                            s6 = String.valueOf(s) + GameSettings.I[131 + 272 - 238 + 247];
                            "".length();
                            if (!true) {
                                throw null;
                            }
                        }
                        else {
                            s6 = String.valueOf(s) + GameSettings.I[237 + 258 - 387 + 305];
                        }
                        return s6;
                    }
                    if (options == Options.ANIMATED_REDSTONE) {
                        String s7;
                        if (this.ofAnimatedRedstone) {
                            s7 = String.valueOf(s) + GameSettings.I[175 + 261 - 70 + 48];
                            "".length();
                            if (4 < 1) {
                                throw null;
                            }
                        }
                        else {
                            s7 = String.valueOf(s) + GameSettings.I[219 + 341 - 160 + 15];
                        }
                        return s7;
                    }
                    if (options == Options.ANIMATED_EXPLOSION) {
                        String s8;
                        if (this.ofAnimatedExplosion) {
                            s8 = String.valueOf(s) + GameSettings.I[220 + 219 - 110 + 87];
                            "".length();
                            if (0 == 2) {
                                throw null;
                            }
                        }
                        else {
                            s8 = String.valueOf(s) + GameSettings.I[177 + 360 - 148 + 28];
                        }
                        return s8;
                    }
                    if (options == Options.ANIMATED_FLAME) {
                        String s9;
                        if (this.ofAnimatedFlame) {
                            s9 = String.valueOf(s) + GameSettings.I[398 + 255 - 499 + 264];
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                        }
                        else {
                            s9 = String.valueOf(s) + GameSettings.I[331 + 209 - 302 + 181];
                        }
                        return s9;
                    }
                    if (options == Options.ANIMATED_SMOKE) {
                        String s10;
                        if (this.ofAnimatedSmoke) {
                            s10 = String.valueOf(s) + GameSettings.I[232 + 18 - 171 + 341];
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            s10 = String.valueOf(s) + GameSettings.I[88 + 323 + 7 + 3];
                        }
                        return s10;
                    }
                    if (options == Options.VOID_PARTICLES) {
                        String s11;
                        if (this.ofVoidParticles) {
                            s11 = String.valueOf(s) + GameSettings.I[325 + 206 - 327 + 218];
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                        else {
                            s11 = String.valueOf(s) + GameSettings.I[320 + 312 - 617 + 408];
                        }
                        return s11;
                    }
                    if (options == Options.WATER_PARTICLES) {
                        String s12;
                        if (this.ofWaterParticles) {
                            s12 = String.valueOf(s) + GameSettings.I[184 + 183 - 30 + 87];
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                        else {
                            s12 = String.valueOf(s) + GameSettings.I[1 + 225 + 171 + 28];
                        }
                        return s12;
                    }
                    if (options == Options.PORTAL_PARTICLES) {
                        String s13;
                        if (this.ofPortalParticles) {
                            s13 = String.valueOf(s) + GameSettings.I[132 + 189 + 68 + 37];
                            "".length();
                            if (4 == 0) {
                                throw null;
                            }
                        }
                        else {
                            s13 = String.valueOf(s) + GameSettings.I[129 + 63 + 125 + 110];
                        }
                        return s13;
                    }
                    if (options == Options.POTION_PARTICLES) {
                        String s14;
                        if (this.ofPotionParticles) {
                            s14 = String.valueOf(s) + GameSettings.I[350 + 45 - 95 + 128];
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            s14 = String.valueOf(s) + GameSettings.I[348 + 291 - 297 + 87];
                        }
                        return s14;
                    }
                    if (options == Options.FIREWORK_PARTICLES) {
                        String s15;
                        if (this.ofFireworkParticles) {
                            s15 = String.valueOf(s) + GameSettings.I[2 + 416 - 32 + 44];
                            "".length();
                            if (4 < 2) {
                                throw null;
                            }
                        }
                        else {
                            s15 = String.valueOf(s) + GameSettings.I[158 + 177 - 72 + 168];
                        }
                        return s15;
                    }
                    if (options == Options.DRIPPING_WATER_LAVA) {
                        String s16;
                        if (this.ofDrippingWaterLava) {
                            s16 = String.valueOf(s) + GameSettings.I[224 + 201 - 243 + 250];
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            s16 = String.valueOf(s) + GameSettings.I[264 + 116 + 31 + 22];
                        }
                        return s16;
                    }
                    if (options == Options.ANIMATED_TERRAIN) {
                        String s17;
                        if (this.ofAnimatedTerrain) {
                            s17 = String.valueOf(s) + GameSettings.I[81 + 77 + 90 + 186];
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                        else {
                            s17 = String.valueOf(s) + GameSettings.I[166 + 41 - 106 + 334];
                        }
                        return s17;
                    }
                    if (options == Options.ANIMATED_TEXTURES) {
                        String s18;
                        if (this.ofAnimatedTextures) {
                            s18 = String.valueOf(s) + GameSettings.I[196 + 159 - 175 + 256];
                            "".length();
                            if (3 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            s18 = String.valueOf(s) + GameSettings.I[252 + 403 - 298 + 80];
                        }
                        return s18;
                    }
                    if (options == Options.RAIN_SPLASH) {
                        String s19;
                        if (this.ofRainSplash) {
                            s19 = String.valueOf(s) + GameSettings.I[393 + 34 - 322 + 333];
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        else {
                            s19 = String.valueOf(s) + GameSettings.I[281 + 212 - 410 + 356];
                        }
                        return s19;
                    }
                    if (options == Options.LAGOMETER) {
                        String s20;
                        if (this.ofLagometer) {
                            s20 = String.valueOf(s) + GameSettings.I[116 + 435 - 475 + 364];
                            "".length();
                            if (0 == 4) {
                                throw null;
                            }
                        }
                        else {
                            s20 = String.valueOf(s) + GameSettings.I[194 + 273 - 143 + 117];
                        }
                        return s20;
                    }
                    if (options == Options.SHOW_FPS) {
                        String s21;
                        if (this.ofShowFps) {
                            s21 = String.valueOf(s) + GameSettings.I[202 + 398 - 315 + 157];
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                        }
                        else {
                            s21 = String.valueOf(s) + GameSettings.I[406 + 338 - 530 + 229];
                        }
                        return s21;
                    }
                    if (options == Options.AUTOSAVE_TICKS) {
                        String s22;
                        if (this.ofAutoSaveTicks <= (0x73 ^ 0x5B)) {
                            s22 = String.valueOf(s) + GameSettings.I[152 + 398 - 228 + 122];
                            "".length();
                            if (1 < 0) {
                                throw null;
                            }
                        }
                        else if (this.ofAutoSaveTicks <= 362 + 84 - 206 + 160) {
                            s22 = String.valueOf(s) + GameSettings.I[25 + 120 + 142 + 158];
                            "".length();
                            if (-1 >= 2) {
                                throw null;
                            }
                        }
                        else if (this.ofAutoSaveTicks <= 1827 + 1568 - 1828 + 2433) {
                            s22 = String.valueOf(s) + GameSettings.I[279 + 436 - 708 + 439];
                            "".length();
                            if (3 <= 2) {
                                throw null;
                            }
                        }
                        else {
                            s22 = String.valueOf(s) + GameSettings.I[277 + 413 - 336 + 93];
                        }
                        return s22;
                    }
                    if (options == Options.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1: {
                                return String.valueOf(s) + GameSettings.I[202 + 403 - 441 + 284];
                            }
                            case 2: {
                                return String.valueOf(s) + GameSettings.I[295 + 289 - 144 + 9];
                            }
                            default: {
                                return String.valueOf(s) + GameSettings.I[179 + 191 + 25 + 55];
                            }
                        }
                    }
                    else if (options == Options.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1: {
                                return String.valueOf(s) + GameSettings.I[36 + 141 - 56 + 330];
                            }
                            case 2: {
                                return String.valueOf(s) + GameSettings.I[85 + 72 + 167 + 128];
                            }
                            default: {
                                return String.valueOf(s) + GameSettings.I[217 + 173 - 95 + 158];
                            }
                        }
                    }
                    else {
                        if (options == Options.WEATHER) {
                            String s23;
                            if (this.ofWeather) {
                                s23 = String.valueOf(s) + GameSettings.I[119 + 110 - 183 + 408];
                                "".length();
                                if (2 < 0) {
                                    throw null;
                                }
                            }
                            else {
                                s23 = String.valueOf(s) + GameSettings.I[259 + 241 - 87 + 42];
                            }
                            return s23;
                        }
                        if (options == Options.SKY) {
                            String s24;
                            if (this.ofSky) {
                                s24 = String.valueOf(s) + GameSettings.I[138 + 285 - 295 + 328];
                                "".length();
                                if (3 < 0) {
                                    throw null;
                                }
                            }
                            else {
                                s24 = String.valueOf(s) + GameSettings.I[426 + 74 - 390 + 347];
                            }
                            return s24;
                        }
                        if (options == Options.STARS) {
                            String s25;
                            if (this.ofStars) {
                                s25 = String.valueOf(s) + GameSettings.I[80 + 403 - 233 + 208];
                                "".length();
                                if (3 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                s25 = String.valueOf(s) + GameSettings.I[264 + 428 - 366 + 133];
                            }
                            return s25;
                        }
                        if (options == Options.SUN_MOON) {
                            String s26;
                            if (this.ofSunMoon) {
                                s26 = String.valueOf(s) + GameSettings.I[99 + 16 + 191 + 154];
                                "".length();
                                if (3 != 3) {
                                    throw null;
                                }
                            }
                            else {
                                s26 = String.valueOf(s) + GameSettings.I[1 + 345 + 48 + 67];
                            }
                            return s26;
                        }
                        if (options == Options.VIGNETTE) {
                            switch (this.ofVignette) {
                                case 1: {
                                    return String.valueOf(s) + GameSettings.I[336 + 207 - 527 + 446];
                                }
                                case 2: {
                                    return String.valueOf(s) + GameSettings.I[10 + 295 + 101 + 57];
                                }
                                default: {
                                    return String.valueOf(s) + GameSettings.I[315 + 318 - 433 + 264];
                                }
                            }
                        }
                        else {
                            if (options == Options.CHUNK_UPDATES) {
                                return String.valueOf(s) + this.ofChunkUpdates;
                            }
                            if (options == Options.CHUNK_LOADING) {
                                String s27;
                                if (this.ofChunkLoading == " ".length()) {
                                    s27 = String.valueOf(s) + GameSettings.I[435 + 163 - 343 + 210];
                                    "".length();
                                    if (2 == 4) {
                                        throw null;
                                    }
                                }
                                else if (this.ofChunkLoading == "  ".length()) {
                                    s27 = String.valueOf(s) + GameSettings.I[411 + 93 - 480 + 442];
                                    "".length();
                                    if (0 >= 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s27 = String.valueOf(s) + GameSettings.I[280 + 274 - 440 + 353];
                                }
                                return s27;
                            }
                            if (options == Options.CHUNK_UPDATES_DYNAMIC) {
                                String s28;
                                if (this.ofChunkUpdatesDynamic) {
                                    s28 = String.valueOf(s) + GameSettings.I[205 + 331 - 334 + 266];
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s28 = String.valueOf(s) + GameSettings.I[211 + 339 - 203 + 122];
                                }
                                return s28;
                            }
                            if (options == Options.TIME) {
                                String s29;
                                if (this.ofTime == " ".length()) {
                                    s29 = String.valueOf(s) + GameSettings.I[88 + 171 - 8 + 219];
                                    "".length();
                                    if (2 <= 1) {
                                        throw null;
                                    }
                                }
                                else if (this.ofTime == "   ".length()) {
                                    s29 = String.valueOf(s) + GameSettings.I[106 + 297 - 163 + 231];
                                    "".length();
                                    if (0 >= 2) {
                                        throw null;
                                    }
                                }
                                else {
                                    s29 = String.valueOf(s) + GameSettings.I[271 + 432 - 685 + 454];
                                }
                                return s29;
                            }
                            if (options == Options.CLEAR_WATER) {
                                String s30;
                                if (this.ofClearWater) {
                                    s30 = String.valueOf(s) + GameSettings.I[303 + 195 - 485 + 460];
                                    "".length();
                                    if (3 < -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s30 = String.valueOf(s) + GameSettings.I[133 + 443 - 162 + 60];
                                }
                                return s30;
                            }
                            if (options == Options.AA_LEVEL) {
                                String s31 = GameSettings.I[374 + 400 - 510 + 211];
                                if (this.ofAaLevel != Config.getAntialiasingLevel()) {
                                    s31 = GameSettings.I[353 + 145 - 291 + 269];
                                }
                                String s32;
                                if (this.ofAaLevel == 0) {
                                    s32 = String.valueOf(s) + GameSettings.I[435 + 147 - 416 + 311] + s31;
                                    "".length();
                                    if (0 >= 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    s32 = String.valueOf(s) + this.ofAaLevel + s31;
                                }
                                return s32;
                            }
                            if (options == Options.AF_LEVEL) {
                                String s33;
                                if (this.ofAfLevel == " ".length()) {
                                    s33 = String.valueOf(s) + GameSettings.I[431 + 289 - 436 + 194];
                                    "".length();
                                    if (3 != 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    s33 = String.valueOf(s) + this.ofAfLevel;
                                }
                                return s33;
                            }
                            if (options == Options.PROFILER) {
                                String s34;
                                if (this.ofProfiler) {
                                    s34 = String.valueOf(s) + GameSettings.I[67 + 388 - 11 + 35];
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s34 = String.valueOf(s) + GameSettings.I[10 + 212 + 243 + 15];
                                }
                                return s34;
                            }
                            if (options == Options.BETTER_SNOW) {
                                String s35;
                                if (this.ofBetterSnow) {
                                    s35 = String.valueOf(s) + GameSettings.I[469 + 233 - 614 + 393];
                                    "".length();
                                    if (4 != 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    s35 = String.valueOf(s) + GameSettings.I[187 + 333 - 456 + 418];
                                }
                                return s35;
                            }
                            if (options == Options.SWAMP_COLORS) {
                                String s36;
                                if (this.ofSwampColors) {
                                    s36 = String.valueOf(s) + GameSettings.I[428 + 186 - 538 + 407];
                                    "".length();
                                    if (3 < 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    s36 = String.valueOf(s) + GameSettings.I[388 + 242 - 479 + 333];
                                }
                                return s36;
                            }
                            if (options == Options.RANDOM_MOBS) {
                                String s37;
                                if (this.ofRandomMobs) {
                                    s37 = String.valueOf(s) + GameSettings.I[379 + 423 - 466 + 149];
                                    "".length();
                                    if (2 == 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s37 = String.valueOf(s) + GameSettings.I[329 + 476 - 488 + 169];
                                }
                                return s37;
                            }
                            if (options == Options.SMOOTH_BIOMES) {
                                String s38;
                                if (this.ofSmoothBiomes) {
                                    s38 = String.valueOf(s) + GameSettings.I[123 + 397 - 105 + 72];
                                    "".length();
                                    if (3 < 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s38 = String.valueOf(s) + GameSettings.I[425 + 398 - 636 + 301];
                                }
                                return s38;
                            }
                            if (options == Options.CUSTOM_FONTS) {
                                String s39;
                                if (this.ofCustomFonts) {
                                    s39 = String.valueOf(s) + GameSettings.I[250 + 380 - 182 + 41];
                                    "".length();
                                    if (false) {
                                        throw null;
                                    }
                                }
                                else {
                                    s39 = String.valueOf(s) + GameSettings.I[219 + 458 - 478 + 291];
                                }
                                return s39;
                            }
                            if (options == Options.CUSTOM_COLORS) {
                                String s40;
                                if (this.ofCustomColors) {
                                    s40 = String.valueOf(s) + GameSettings.I[423 + 452 - 762 + 378];
                                    "".length();
                                    if (3 < 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s40 = String.valueOf(s) + GameSettings.I[14 + 391 - 335 + 422];
                                }
                                return s40;
                            }
                            if (options == Options.CUSTOM_SKY) {
                                String s41;
                                if (this.ofCustomSky) {
                                    s41 = String.valueOf(s) + GameSettings.I[276 + 405 - 466 + 278];
                                    "".length();
                                    if (4 < 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s41 = String.valueOf(s) + GameSettings.I[102 + 76 + 164 + 152];
                                }
                                return s41;
                            }
                            if (options == Options.SHOW_CAPES) {
                                String s42;
                                if (this.ofShowCapes) {
                                    s42 = String.valueOf(s) + GameSettings.I[159 + 185 - 81 + 232];
                                    "".length();
                                    if (true != true) {
                                        throw null;
                                    }
                                }
                                else {
                                    s42 = String.valueOf(s) + GameSettings.I[299 + 59 + 40 + 98];
                                }
                                return s42;
                            }
                            if (options == Options.NATURAL_TEXTURES) {
                                String s43;
                                if (this.ofNaturalTextures) {
                                    s43 = String.valueOf(s) + GameSettings.I[424 + 291 - 352 + 134];
                                    "".length();
                                    if (4 == 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s43 = String.valueOf(s) + GameSettings.I[227 + 463 - 670 + 478];
                                }
                                return s43;
                            }
                            if (options == Options.FAST_MATH) {
                                String s44;
                                if (this.ofFastMath) {
                                    s44 = String.valueOf(s) + GameSettings.I[457 + 382 - 689 + 349];
                                    "".length();
                                    if (3 == 1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s44 = String.valueOf(s) + GameSettings.I[149 + 394 - 541 + 498];
                                }
                                return s44;
                            }
                            if (options == Options.FAST_RENDER) {
                                String s45;
                                if (this.ofFastRender) {
                                    s45 = String.valueOf(s) + GameSettings.I[73 + 253 - 94 + 269];
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    s45 = String.valueOf(s) + GameSettings.I[246 + 340 - 116 + 32];
                                }
                                return s45;
                            }
                            if (options == Options.TRANSLUCENT_BLOCKS) {
                                String s46;
                                if (this.ofTranslucentBlocks == " ".length()) {
                                    s46 = String.valueOf(s) + GameSettings.I[211 + 154 + 71 + 67];
                                    "".length();
                                    if (-1 >= 2) {
                                        throw null;
                                    }
                                }
                                else if (this.ofTranslucentBlocks == "  ".length()) {
                                    s46 = String.valueOf(s) + GameSettings.I[500 + 76 - 126 + 54];
                                    "".length();
                                    if (2 != 2) {
                                        throw null;
                                    }
                                }
                                else {
                                    s46 = String.valueOf(s) + GameSettings.I[114 + 262 + 108 + 21];
                                }
                                return s46;
                            }
                            if (options == Options.LAZY_CHUNK_LOADING) {
                                String s47;
                                if (this.ofLazyChunkLoading) {
                                    s47 = String.valueOf(s) + GameSettings.I[417 + 199 - 507 + 397];
                                    "".length();
                                    if (4 < 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    s47 = String.valueOf(s) + GameSettings.I[434 + 414 - 668 + 327];
                                }
                                return s47;
                            }
                            if (options == Options.FULLSCREEN_MODE) {
                                return String.valueOf(s) + this.ofFullscreenMode;
                            }
                            if (options == Options.HELD_ITEM_TOOLTIPS) {
                                String s48;
                                if (this.heldItemTooltips) {
                                    s48 = String.valueOf(s) + GameSettings.I[210 + 345 - 209 + 162];
                                    "".length();
                                    if (4 != 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    s48 = String.valueOf(s) + GameSettings.I[372 + 387 - 744 + 494];
                                }
                                return s48;
                            }
                            if (options == Options.FRAMERATE_LIMIT) {
                                final float optionFloatValue = this.getOptionFloatValue(options);
                                String s49;
                                if (optionFloatValue == 0.0f) {
                                    s49 = String.valueOf(s) + GameSettings.I[458 + 256 - 564 + 360];
                                    "".length();
                                    if (4 <= 0) {
                                        throw null;
                                    }
                                }
                                else if (optionFloatValue == Options.access$2(options)) {
                                    s49 = String.valueOf(s) + I18n.format(GameSettings.I[174 + 257 - 142 + 222], new Object["".length()]);
                                    "".length();
                                    if (3 == 2) {
                                        throw null;
                                    }
                                }
                                else {
                                    s49 = String.valueOf(s) + (int)optionFloatValue + GameSettings.I[204 + 417 - 276 + 167];
                                }
                                return s49;
                            }
                            return null;
                        }
                    }
                }
            }
        }
    }
    
    private void setOptionFloatValueOF(final Options options, final float n) {
        if (options == Options.CLOUD_HEIGHT) {
            this.ofCloudsHeight = n;
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.AO_LEVEL) {
            this.ofAoLevel = n;
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.AA_LEVEL) {
            final int[] array = new int[0x4E ^ 0x49];
            array[" ".length()] = "  ".length();
            array["  ".length()] = (0x37 ^ 0x33);
            array["   ".length()] = (0x4A ^ 0x4C);
            array[0xBF ^ 0xBB] = (0x57 ^ 0x5F);
            array[0x46 ^ 0x43] = (0x8B ^ 0x87);
            array[0xBD ^ 0xBB] = (0xC ^ 0x1C);
            final int[] array2 = array;
            this.ofAaLevel = "".length();
            final int n2 = (int)n;
            int i = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (i < array2.length) {
                if (n2 >= array2[i]) {
                    this.ofAaLevel = array2[i];
                }
                ++i;
            }
            this.ofAaLevel = Config.limit(this.ofAaLevel, "".length(), 0x6B ^ 0x7B);
        }
        if (options == Options.AF_LEVEL) {
            final int n3 = (int)n;
            this.ofAfLevel = " ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (this.ofAfLevel * "  ".length() <= n3) {
                this.ofAfLevel *= "  ".length();
            }
            this.ofAfLevel = Config.limit(this.ofAfLevel, " ".length(), 0x80 ^ 0x90);
            this.mc.refreshResources();
        }
        if (options == Options.MIPMAP_TYPE) {
            this.ofMipmapType = Config.limit((int)n, "".length(), "   ".length());
            this.mc.refreshResources();
        }
    }
    
    public void loadOfOptions() {
        try {
            File file = this.optionsFileOF;
            if (!file.exists()) {
                file = this.optionsFile;
            }
            if (!file.exists()) {
                return;
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final String s = GameSettings.I[346 + 335 - 567 + 399];
            "".length();
            if (1 >= 4) {
                throw null;
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    final String[] split = line.split(GameSettings.I[227 + 166 - 114 + 235]);
                    if (split["".length()].equals(GameSettings.I[323 + 134 - 134 + 192]) && split.length >= "  ".length()) {
                        this.renderDistanceChunks = Integer.valueOf(split[" ".length()]);
                        this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, "  ".length(), 0x85 ^ 0xA5);
                    }
                    if (split["".length()].equals(GameSettings.I[294 + 258 - 405 + 369]) && split.length >= "  ".length()) {
                        this.ofFogType = Integer.valueOf(split[" ".length()]);
                        this.ofFogType = Config.limit(this.ofFogType, " ".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[53 + 484 - 72 + 52]) && split.length >= "  ".length()) {
                        this.ofFogStart = Float.valueOf(split[" ".length()]);
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (split["".length()].equals(GameSettings.I[54 + 444 - 210 + 230]) && split.length >= "  ".length()) {
                        this.ofMipmapType = Integer.valueOf(split[" ".length()]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, "".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[334 + 246 - 241 + 180]) && split.length >= "  ".length()) {
                        this.ofOcclusionFancy = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[307 + 330 - 362 + 245]) && split.length >= "  ".length()) {
                        this.ofSmoothFps = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[414 + 360 - 681 + 428]) && split.length >= "  ".length()) {
                        this.ofSmoothWorld = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[431 + 148 - 440 + 383]) && split.length >= "  ".length()) {
                        this.ofAoLevel = Float.valueOf(split[" ".length()]);
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
                    }
                    if (split["".length()].equals(GameSettings.I[204 + 476 - 632 + 475]) && split.length >= "  ".length()) {
                        this.ofClouds = Integer.valueOf(split[" ".length()]);
                        this.ofClouds = Config.limit(this.ofClouds, "".length(), "   ".length());
                        this.updateRenderClouds();
                    }
                    if (split["".length()].equals(GameSettings.I[310 + 498 - 439 + 155]) && split.length >= "  ".length()) {
                        this.ofCloudsHeight = Float.valueOf(split[" ".length()]);
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
                    }
                    if (split["".length()].equals(GameSettings.I[504 + 92 - 167 + 96]) && split.length >= "  ".length()) {
                        this.ofTrees = Integer.valueOf(split[" ".length()]);
                        this.ofTrees = Config.limit(this.ofTrees, "".length(), "  ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[128 + 489 - 474 + 383]) && split.length >= "  ".length()) {
                        this.ofDroppedItems = Integer.valueOf(split[" ".length()]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, "".length(), "  ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[91 + 468 - 393 + 361]) && split.length >= "  ".length()) {
                        this.ofRain = Integer.valueOf(split[" ".length()]);
                        this.ofRain = Config.limit(this.ofRain, "".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[267 + 454 - 232 + 39]) && split.length >= "  ".length()) {
                        this.ofAnimatedWater = Integer.valueOf(split[" ".length()]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, "".length(), "  ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[152 + 361 - 444 + 460]) && split.length >= "  ".length()) {
                        this.ofAnimatedLava = Integer.valueOf(split[" ".length()]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, "".length(), "  ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[372 + 150 - 317 + 325]) && split.length >= "  ".length()) {
                        this.ofAnimatedFire = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[278 + 300 - 296 + 249]) && split.length >= "  ".length()) {
                        this.ofAnimatedPortal = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[321 + 316 - 430 + 325]) && split.length >= "  ".length()) {
                        this.ofAnimatedRedstone = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[255 + 8 - 171 + 441]) && split.length >= "  ".length()) {
                        this.ofAnimatedExplosion = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[213 + 312 + 7 + 2]) && split.length >= "  ".length()) {
                        this.ofAnimatedFlame = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[247 + 453 - 287 + 122]) && split.length >= "  ".length()) {
                        this.ofAnimatedSmoke = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[355 + 35 + 30 + 116]) && split.length >= "  ".length()) {
                        this.ofVoidParticles = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[1 + 346 + 161 + 29]) && split.length >= "  ".length()) {
                        this.ofWaterParticles = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[132 + 297 - 414 + 523]) && split.length >= "  ".length()) {
                        this.ofPortalParticles = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[520 + 371 - 599 + 247]) && split.length >= "  ".length()) {
                        this.ofPotionParticles = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[171 + 296 - 24 + 97]) && split.length >= "  ".length()) {
                        this.ofFireworkParticles = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[0 + 213 + 123 + 205]) && split.length >= "  ".length()) {
                        this.ofDrippingWaterLava = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[1 + 44 + 400 + 97]) && split.length >= "  ".length()) {
                        this.ofAnimatedTerrain = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[461 + 478 - 632 + 236]) && split.length >= "  ".length()) {
                        this.ofAnimatedTextures = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[228 + 250 - 158 + 224]) && split.length >= "  ".length()) {
                        this.ofRainSplash = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[165 + 113 - 153 + 420]) && split.length >= "  ".length()) {
                        this.ofLagometer = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[460 + 193 - 374 + 267]) && split.length >= "  ".length()) {
                        this.ofShowFps = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[533 + 405 - 745 + 354]) && split.length >= "  ".length()) {
                        this.ofAutoSaveTicks = Integer.valueOf(split[" ".length()]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 0x7F ^ 0x57, 13785 + 35773 - 37218 + 27660);
                    }
                    if (split["".length()].equals(GameSettings.I[154 + 518 - 637 + 513]) && split.length >= "  ".length()) {
                        this.ofBetterGrass = Integer.valueOf(split[" ".length()]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, " ".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[310 + 432 - 384 + 191]) && split.length >= "  ".length()) {
                        this.ofConnectedTextures = Integer.valueOf(split[" ".length()]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, " ".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[71 + 74 + 109 + 296]) && split.length >= "  ".length()) {
                        this.ofWeather = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[268 + 245 - 495 + 533]) && split.length >= "  ".length()) {
                        this.ofSky = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[160 + 498 - 434 + 328]) && split.length >= "  ".length()) {
                        this.ofStars = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[64 + 83 + 56 + 350]) && split.length >= "  ".length()) {
                        this.ofSunMoon = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[226 + 168 - 302 + 462]) && split.length >= "  ".length()) {
                        this.ofVignette = Integer.valueOf(split[" ".length()]);
                        this.ofVignette = Config.limit(this.ofVignette, "".length(), "  ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[333 + 513 - 656 + 365]) && split.length >= "  ".length()) {
                        this.ofChunkUpdates = Integer.valueOf(split[" ".length()]);
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, " ".length(), 0x1C ^ 0x19);
                    }
                    if (split["".length()].equals(GameSettings.I[555 + 130 - 382 + 253]) && split.length >= "  ".length()) {
                        this.ofChunkLoading = Integer.valueOf(split[" ".length()]);
                        this.ofChunkLoading = Config.limit(this.ofChunkLoading, "".length(), "  ".length());
                        this.updateChunkLoading();
                    }
                    if (split["".length()].equals(GameSettings.I[433 + 278 - 360 + 206]) && split.length >= "  ".length()) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[157 + 104 + 24 + 273]) && split.length >= "  ".length()) {
                        this.ofTime = Integer.valueOf(split[" ".length()]);
                        this.ofTime = Config.limit(this.ofTime, "".length(), "   ".length());
                    }
                    if (split["".length()].equals(GameSettings.I[301 + 291 - 510 + 477]) && split.length >= "  ".length()) {
                        this.ofClearWater = Boolean.valueOf(split[" ".length()]);
                        this.updateWaterOpacity();
                    }
                    if (split["".length()].equals(GameSettings.I[132 + 53 - 4 + 379]) && split.length >= "  ".length()) {
                        this.ofAaLevel = Integer.valueOf(split[" ".length()]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, "".length(), 0xBE ^ 0xAE);
                    }
                    if (split["".length()].equals(GameSettings.I[158 + 443 - 509 + 469]) && split.length >= "  ".length()) {
                        this.ofAfLevel = Integer.valueOf(split[" ".length()]);
                        this.ofAfLevel = Config.limit(this.ofAfLevel, " ".length(), 0xD7 ^ 0xC7);
                    }
                    if (split["".length()].equals(GameSettings.I[529 + 405 - 632 + 260]) && split.length >= "  ".length()) {
                        this.ofProfiler = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[132 + 524 - 374 + 281]) && split.length >= "  ".length()) {
                        this.ofBetterSnow = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[473 + 397 - 818 + 512]) && split.length >= "  ".length()) {
                        this.ofSwampColors = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[318 + 363 - 473 + 357]) && split.length >= "  ".length()) {
                        this.ofRandomMobs = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[408 + 452 - 780 + 486]) && split.length >= "  ".length()) {
                        this.ofSmoothBiomes = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[536 + 45 - 329 + 315]) && split.length >= "  ".length()) {
                        this.ofCustomFonts = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[299 + 124 - 267 + 412]) && split.length >= "  ".length()) {
                        this.ofCustomColors = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[118 + 471 - 411 + 391]) && split.length >= "  ".length()) {
                        this.ofCustomSky = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[353 + 259 - 435 + 393]) && split.length >= "  ".length()) {
                        this.ofShowCapes = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[283 + 487 - 364 + 165]) && split.length >= "  ".length()) {
                        this.ofNaturalTextures = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[106 + 116 + 195 + 155]) && split.length >= "  ".length()) {
                        this.ofLazyChunkLoading = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[413 + 121 - 330 + 369]) && split.length >= "  ".length()) {
                        this.ofFullscreenMode = split[" ".length()];
                    }
                    if (split["".length()].equals(GameSettings.I[471 + 245 - 504 + 362]) && split.length >= "  ".length()) {
                        this.ofFastMath = Boolean.valueOf(split[" ".length()]);
                        MathHelper.fastMath = this.ofFastMath;
                    }
                    if (split["".length()].equals(GameSettings.I[366 + 287 - 331 + 253]) && split.length >= "  ".length()) {
                        this.ofFastRender = Boolean.valueOf(split[" ".length()]);
                    }
                    if (split["".length()].equals(GameSettings.I[572 + 542 - 720 + 182]) && split.length >= "  ".length()) {
                        this.ofTranslucentBlocks = Integer.valueOf(split[" ".length()]);
                        this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, "".length(), "  ".length());
                    }
                    if (!split["".length()].equals(GameSettings.I[285 + 101 - 362 + 553] + this.ofKeyBindZoom.getKeyDescription())) {
                        continue;
                    }
                    this.ofKeyBindZoom.setKeyCode(Integer.parseInt(split[" ".length()]));
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                    continue;
                }
                catch (Exception ex) {
                    Config.dbg(GameSettings.I[532 + 460 - 987 + 573] + line);
                    ex.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedReader.close();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (Exception ex2) {
            Config.warn(GameSettings.I[22 + 144 - 119 + 532]);
            ex2.printStackTrace();
        }
    }
    
    private void setOptionValueOF(final Options options, final int n) {
        if (options == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = "  ".length();
                    if (Config.isFancyFogAvailable()) {
                        break;
                    }
                    this.ofFogType = "   ".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.ofFogType = "   ".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    this.ofFogType = " ".length();
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.ofFogType = " ".length();
                    break;
                }
            }
        }
        if (options == Options.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (options == Options.SMOOTH_FPS) {
            int ofSmoothFps;
            if (this.ofSmoothFps) {
                ofSmoothFps = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                ofSmoothFps = " ".length();
            }
            this.ofSmoothFps = (ofSmoothFps != 0);
        }
        if (options == Options.SMOOTH_WORLD) {
            int ofSmoothWorld;
            if (this.ofSmoothWorld) {
                ofSmoothWorld = "".length();
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                ofSmoothWorld = " ".length();
            }
            this.ofSmoothWorld = (ofSmoothWorld != 0);
            Config.updateThreadPriorities();
        }
        if (options == Options.CLOUDS) {
            this.ofClouds += " ".length();
            if (this.ofClouds > "   ".length()) {
                this.ofClouds = "".length();
            }
            this.updateRenderClouds();
            this.mc.renderGlobal.resetClouds();
        }
        if (options == Options.TREES) {
            this.ofTrees += " ".length();
            if (this.ofTrees > "  ".length()) {
                this.ofTrees = "".length();
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.DROPPED_ITEMS) {
            this.ofDroppedItems += " ".length();
            if (this.ofDroppedItems > "  ".length()) {
                this.ofDroppedItems = "".length();
            }
        }
        if (options == Options.RAIN) {
            this.ofRain += " ".length();
            if (this.ofRain > "   ".length()) {
                this.ofRain = "".length();
            }
        }
        if (options == Options.ANIMATED_WATER) {
            this.ofAnimatedWater += " ".length();
            if (this.ofAnimatedWater > "  ".length()) {
                this.ofAnimatedWater = "".length();
            }
        }
        if (options == Options.ANIMATED_LAVA) {
            this.ofAnimatedLava += " ".length();
            if (this.ofAnimatedLava > "  ".length()) {
                this.ofAnimatedLava = "".length();
            }
        }
        if (options == Options.ANIMATED_FIRE) {
            int ofAnimatedFire;
            if (this.ofAnimatedFire) {
                ofAnimatedFire = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                ofAnimatedFire = " ".length();
            }
            this.ofAnimatedFire = (ofAnimatedFire != 0);
        }
        if (options == Options.ANIMATED_PORTAL) {
            int ofAnimatedPortal;
            if (this.ofAnimatedPortal) {
                ofAnimatedPortal = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                ofAnimatedPortal = " ".length();
            }
            this.ofAnimatedPortal = (ofAnimatedPortal != 0);
        }
        if (options == Options.ANIMATED_REDSTONE) {
            int ofAnimatedRedstone;
            if (this.ofAnimatedRedstone) {
                ofAnimatedRedstone = "".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                ofAnimatedRedstone = " ".length();
            }
            this.ofAnimatedRedstone = (ofAnimatedRedstone != 0);
        }
        if (options == Options.ANIMATED_EXPLOSION) {
            int ofAnimatedExplosion;
            if (this.ofAnimatedExplosion) {
                ofAnimatedExplosion = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                ofAnimatedExplosion = " ".length();
            }
            this.ofAnimatedExplosion = (ofAnimatedExplosion != 0);
        }
        if (options == Options.ANIMATED_FLAME) {
            int ofAnimatedFlame;
            if (this.ofAnimatedFlame) {
                ofAnimatedFlame = "".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                ofAnimatedFlame = " ".length();
            }
            this.ofAnimatedFlame = (ofAnimatedFlame != 0);
        }
        if (options == Options.ANIMATED_SMOKE) {
            int ofAnimatedSmoke;
            if (this.ofAnimatedSmoke) {
                ofAnimatedSmoke = "".length();
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                ofAnimatedSmoke = " ".length();
            }
            this.ofAnimatedSmoke = (ofAnimatedSmoke != 0);
        }
        if (options == Options.VOID_PARTICLES) {
            int ofVoidParticles;
            if (this.ofVoidParticles) {
                ofVoidParticles = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                ofVoidParticles = " ".length();
            }
            this.ofVoidParticles = (ofVoidParticles != 0);
        }
        if (options == Options.WATER_PARTICLES) {
            int ofWaterParticles;
            if (this.ofWaterParticles) {
                ofWaterParticles = "".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                ofWaterParticles = " ".length();
            }
            this.ofWaterParticles = (ofWaterParticles != 0);
        }
        if (options == Options.PORTAL_PARTICLES) {
            int ofPortalParticles;
            if (this.ofPortalParticles) {
                ofPortalParticles = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                ofPortalParticles = " ".length();
            }
            this.ofPortalParticles = (ofPortalParticles != 0);
        }
        if (options == Options.POTION_PARTICLES) {
            int ofPotionParticles;
            if (this.ofPotionParticles) {
                ofPotionParticles = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                ofPotionParticles = " ".length();
            }
            this.ofPotionParticles = (ofPotionParticles != 0);
        }
        if (options == Options.FIREWORK_PARTICLES) {
            int ofFireworkParticles;
            if (this.ofFireworkParticles) {
                ofFireworkParticles = "".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                ofFireworkParticles = " ".length();
            }
            this.ofFireworkParticles = (ofFireworkParticles != 0);
        }
        if (options == Options.DRIPPING_WATER_LAVA) {
            int ofDrippingWaterLava;
            if (this.ofDrippingWaterLava) {
                ofDrippingWaterLava = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                ofDrippingWaterLava = " ".length();
            }
            this.ofDrippingWaterLava = (ofDrippingWaterLava != 0);
        }
        if (options == Options.ANIMATED_TERRAIN) {
            int ofAnimatedTerrain;
            if (this.ofAnimatedTerrain) {
                ofAnimatedTerrain = "".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                ofAnimatedTerrain = " ".length();
            }
            this.ofAnimatedTerrain = (ofAnimatedTerrain != 0);
        }
        if (options == Options.ANIMATED_TEXTURES) {
            int ofAnimatedTextures;
            if (this.ofAnimatedTextures) {
                ofAnimatedTextures = "".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                ofAnimatedTextures = " ".length();
            }
            this.ofAnimatedTextures = (ofAnimatedTextures != 0);
        }
        if (options == Options.RAIN_SPLASH) {
            int ofRainSplash;
            if (this.ofRainSplash) {
                ofRainSplash = "".length();
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                ofRainSplash = " ".length();
            }
            this.ofRainSplash = (ofRainSplash != 0);
        }
        if (options == Options.LAGOMETER) {
            int ofLagometer;
            if (this.ofLagometer) {
                ofLagometer = "".length();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                ofLagometer = " ".length();
            }
            this.ofLagometer = (ofLagometer != 0);
        }
        if (options == Options.SHOW_FPS) {
            int ofShowFps;
            if (this.ofShowFps) {
                ofShowFps = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                ofShowFps = " ".length();
            }
            this.ofShowFps = (ofShowFps != 0);
        }
        if (options == Options.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= (0x25 ^ 0x2F);
            if (this.ofAutoSaveTicks > 29972 + 20274 - 23490 + 13244) {
                this.ofAutoSaveTicks = (0x67 ^ 0x4F);
            }
        }
        if (options == Options.BETTER_GRASS) {
            this.ofBetterGrass += " ".length();
            if (this.ofBetterGrass > "   ".length()) {
                this.ofBetterGrass = " ".length();
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CONNECTED_TEXTURES) {
            this.ofConnectedTextures += " ".length();
            if (this.ofConnectedTextures > "   ".length()) {
                this.ofConnectedTextures = " ".length();
            }
            if (this.ofConnectedTextures != "  ".length()) {
                this.mc.refreshResources();
            }
        }
        if (options == Options.WEATHER) {
            int ofWeather;
            if (this.ofWeather) {
                ofWeather = "".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                ofWeather = " ".length();
            }
            this.ofWeather = (ofWeather != 0);
        }
        if (options == Options.SKY) {
            int ofSky;
            if (this.ofSky) {
                ofSky = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                ofSky = " ".length();
            }
            this.ofSky = (ofSky != 0);
        }
        if (options == Options.STARS) {
            int ofStars;
            if (this.ofStars) {
                ofStars = "".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                ofStars = " ".length();
            }
            this.ofStars = (ofStars != 0);
        }
        if (options == Options.SUN_MOON) {
            int ofSunMoon;
            if (this.ofSunMoon) {
                ofSunMoon = "".length();
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                ofSunMoon = " ".length();
            }
            this.ofSunMoon = (ofSunMoon != 0);
        }
        if (options == Options.VIGNETTE) {
            this.ofVignette += " ".length();
            if (this.ofVignette > "  ".length()) {
                this.ofVignette = "".length();
            }
        }
        if (options == Options.CHUNK_UPDATES) {
            this.ofChunkUpdates += " ".length();
            if (this.ofChunkUpdates > (0x32 ^ 0x37)) {
                this.ofChunkUpdates = " ".length();
            }
        }
        if (options == Options.CHUNK_LOADING) {
            this.ofChunkLoading += " ".length();
            if (this.ofChunkLoading > "  ".length()) {
                this.ofChunkLoading = "".length();
            }
            this.updateChunkLoading();
        }
        if (options == Options.CHUNK_UPDATES_DYNAMIC) {
            int ofChunkUpdatesDynamic;
            if (this.ofChunkUpdatesDynamic) {
                ofChunkUpdatesDynamic = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                ofChunkUpdatesDynamic = " ".length();
            }
            this.ofChunkUpdatesDynamic = (ofChunkUpdatesDynamic != 0);
        }
        if (options == Options.TIME) {
            this.ofTime += " ".length();
            if (this.ofTime > "   ".length()) {
                this.ofTime = "".length();
            }
        }
        if (options == Options.CLEAR_WATER) {
            int ofClearWater;
            if (this.ofClearWater) {
                ofClearWater = "".length();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else {
                ofClearWater = " ".length();
            }
            this.ofClearWater = (ofClearWater != 0);
            this.updateWaterOpacity();
        }
        if (options == Options.PROFILER) {
            int ofProfiler;
            if (this.ofProfiler) {
                ofProfiler = "".length();
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                ofProfiler = " ".length();
            }
            this.ofProfiler = (ofProfiler != 0);
        }
        if (options == Options.BETTER_SNOW) {
            int ofBetterSnow;
            if (this.ofBetterSnow) {
                ofBetterSnow = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                ofBetterSnow = " ".length();
            }
            this.ofBetterSnow = (ofBetterSnow != 0);
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.SWAMP_COLORS) {
            int ofSwampColors;
            if (this.ofSwampColors) {
                ofSwampColors = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                ofSwampColors = " ".length();
            }
            this.ofSwampColors = (ofSwampColors != 0);
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RANDOM_MOBS) {
            int ofRandomMobs;
            if (this.ofRandomMobs) {
                ofRandomMobs = "".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                ofRandomMobs = " ".length();
            }
            this.ofRandomMobs = (ofRandomMobs != 0);
            RandomMobs.resetTextures();
        }
        if (options == Options.SMOOTH_BIOMES) {
            int ofSmoothBiomes;
            if (this.ofSmoothBiomes) {
                ofSmoothBiomes = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                ofSmoothBiomes = " ".length();
            }
            this.ofSmoothBiomes = (ofSmoothBiomes != 0);
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_FONTS) {
            int ofCustomFonts;
            if (this.ofCustomFonts) {
                ofCustomFonts = "".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                ofCustomFonts = " ".length();
            }
            this.ofCustomFonts = (ofCustomFonts != 0);
            this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
        if (options == Options.CUSTOM_COLORS) {
            int ofCustomColors;
            if (this.ofCustomColors) {
                ofCustomColors = "".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                ofCustomColors = " ".length();
            }
            this.ofCustomColors = (ofCustomColors != 0);
            CustomColorizer.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.CUSTOM_SKY) {
            int ofCustomSky;
            if (this.ofCustomSky) {
                ofCustomSky = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                ofCustomSky = " ".length();
            }
            this.ofCustomSky = (ofCustomSky != 0);
            CustomSky.update();
        }
        if (options == Options.SHOW_CAPES) {
            int ofShowCapes;
            if (this.ofShowCapes) {
                ofShowCapes = "".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                ofShowCapes = " ".length();
            }
            this.ofShowCapes = (ofShowCapes != 0);
        }
        if (options == Options.NATURAL_TEXTURES) {
            int ofNaturalTextures;
            if (this.ofNaturalTextures) {
                ofNaturalTextures = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                ofNaturalTextures = " ".length();
            }
            this.ofNaturalTextures = (ofNaturalTextures != 0);
            NaturalTextures.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FAST_MATH) {
            int ofFastMath;
            if (this.ofFastMath) {
                ofFastMath = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                ofFastMath = " ".length();
            }
            this.ofFastMath = (ofFastMath != 0);
            MathHelper.fastMath = this.ofFastMath;
        }
        if (options == Options.FAST_RENDER) {
            int ofFastRender;
            if (this.ofFastRender) {
                ofFastRender = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                ofFastRender = " ".length();
            }
            this.ofFastRender = (ofFastRender != 0);
            Config.updateFramebufferSize();
        }
        if (options == Options.TRANSLUCENT_BLOCKS) {
            if (this.ofTranslucentBlocks == 0) {
                this.ofTranslucentBlocks = " ".length();
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else if (this.ofTranslucentBlocks == " ".length()) {
                this.ofTranslucentBlocks = "  ".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else if (this.ofTranslucentBlocks == "  ".length()) {
                this.ofTranslucentBlocks = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                this.ofTranslucentBlocks = "".length();
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.LAZY_CHUNK_LOADING) {
            int ofLazyChunkLoading;
            if (this.ofLazyChunkLoading) {
                ofLazyChunkLoading = "".length();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            else {
                ofLazyChunkLoading = " ".length();
            }
            this.ofLazyChunkLoading = (ofLazyChunkLoading != 0);
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofLazyChunkLoading = ("".length() != 0);
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.FULLSCREEN_MODE) {
            final List<String> list = Arrays.asList(Config.getFullscreenModes());
            if (this.ofFullscreenMode.equals(GameSettings.I[335 + 237 - 554 + 348])) {
                this.ofFullscreenMode = list.get("".length());
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                int index = list.indexOf(this.ofFullscreenMode);
                if (index < 0) {
                    this.ofFullscreenMode = GameSettings.I[108 + 249 - 178 + 188];
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
                else if (++index >= list.size()) {
                    this.ofFullscreenMode = GameSettings.I[141 + 322 - 385 + 290];
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    this.ofFullscreenMode = list.get(index);
                }
            }
        }
        if (options == Options.HELD_ITEM_TOOLTIPS) {
            int heldItemTooltips;
            if (this.heldItemTooltips) {
                heldItemTooltips = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                heldItemTooltips = " ".length();
            }
            this.heldItemTooltips = (heldItemTooltips != 0);
        }
    }
    
    public int func_181147_e() {
        int n;
        if (this.renderDistanceChunks >= (0x50 ^ 0x54)) {
            n = this.clouds;
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public static boolean isKeyDown(final KeyBinding keyBinding) {
        final int keyCode = keyBinding.getKeyCode();
        int n;
        if (keyCode >= -(0xA ^ 0x6E) && keyCode <= 12 + 50 - 45 + 238) {
            if (keyBinding.getKeyCode() == 0) {
                n = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else if (keyBinding.getKeyCode() < 0) {
                n = (Mouse.isButtonDown(keyBinding.getKeyCode() + (0x1B ^ 0x7F)) ? 1 : 0);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                n = (Keyboard.isKeyDown(keyBinding.getKeyCode()) ? 1 : 0);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void setOptionFloatValue(final Options options, final float streamFps) {
        this.setOptionFloatValueOF(options, streamFps);
        if (options == Options.SENSITIVITY) {
            this.mouseSensitivity = streamFps;
        }
        if (options == Options.FOV) {
            this.fovSetting = streamFps;
        }
        if (options == Options.GAMMA) {
            this.gammaSetting = streamFps;
        }
        if (options == Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)streamFps;
            this.enableVsync = ("".length() != 0);
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = (" ".length() != 0);
            }
            this.updateVSync();
        }
        if (options == Options.CHAT_OPACITY) {
            this.chatOpacity = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_WIDTH) {
            this.chatWidth = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.CHAT_SCALE) {
            this.chatScale = streamFps;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }
        if (options == Options.MIPMAP_LEVELS) {
            final int mipmapLevels = this.mipmapLevels;
            this.mipmapLevels = (int)streamFps;
            if (mipmapLevels != streamFps) {
                this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                final TextureMap textureMapBlocks = this.mc.getTextureMapBlocks();
                final int length = "".length();
                int n;
                if (this.mipmapLevels > 0) {
                    n = " ".length();
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                textureMapBlocks.setBlurMipmapDirect(length != 0, n != 0);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (options == Options.BLOCK_ALTERNATIVES) {
            int allowBlockAlternatives;
            if (this.allowBlockAlternatives) {
                allowBlockAlternatives = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                allowBlockAlternatives = " ".length();
            }
            this.allowBlockAlternatives = (allowBlockAlternatives != 0);
            this.mc.renderGlobal.loadRenderers();
        }
        if (options == Options.RENDER_DISTANCE) {
            this.renderDistanceChunks = (int)streamFps;
            this.mc.renderGlobal.setDisplayListEntitiesDirty();
        }
        if (options == Options.STREAM_BYTES_PER_PIXEL) {
            this.streamBytesPerPixel = streamFps;
        }
        if (options == Options.STREAM_VOLUME_MIC) {
            this.streamMicVolume = streamFps;
            this.mc.getTwitchStream().updateStreamVolume();
        }
        if (options == Options.STREAM_VOLUME_SYSTEM) {
            this.streamGameVolume = streamFps;
            this.mc.getTwitchStream().updateStreamVolume();
        }
        if (options == Options.STREAM_KBPS) {
            this.streamKbps = streamFps;
        }
        if (options == Options.STREAM_FPS) {
            this.streamFps = streamFps;
        }
    }
    
    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }
    
    public void setOptionKeyBinding(final KeyBinding keyBinding, final int keyCode) {
        keyBinding.setKeyCode(keyCode);
        this.saveOptions();
    }
    
    public float getOptionFloatValue(final Options options) {
        float n;
        if (options == Options.CLOUD_HEIGHT) {
            n = this.ofCloudsHeight;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else if (options == Options.AO_LEVEL) {
            n = this.ofAoLevel;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (options == Options.AA_LEVEL) {
            n = this.ofAaLevel;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (options == Options.AF_LEVEL) {
            n = this.ofAfLevel;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (options == Options.MIPMAP_TYPE) {
            n = this.ofMipmapType;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (options == Options.FRAMERATE_LIMIT) {
            if (this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) {
                n = 0.0f;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n = this.limitFramerate;
                "".length();
                if (false == true) {
                    throw null;
                }
            }
        }
        else if (options == Options.FOV) {
            n = this.fovSetting;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (options == Options.GAMMA) {
            n = this.gammaSetting;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (options == Options.SATURATION) {
            n = this.saturation;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else if (options == Options.SENSITIVITY) {
            n = this.mouseSensitivity;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (options == Options.CHAT_OPACITY) {
            n = this.chatOpacity;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (options == Options.CHAT_HEIGHT_FOCUSED) {
            n = this.chatHeightFocused;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (options == Options.CHAT_HEIGHT_UNFOCUSED) {
            n = this.chatHeightUnfocused;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (options == Options.CHAT_SCALE) {
            n = this.chatScale;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (options == Options.CHAT_WIDTH) {
            n = this.chatWidth;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (options == Options.FRAMERATE_LIMIT) {
            n = this.limitFramerate;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (options == Options.MIPMAP_LEVELS) {
            n = this.mipmapLevels;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (options == Options.RENDER_DISTANCE) {
            n = this.renderDistanceChunks;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (options == Options.STREAM_BYTES_PER_PIXEL) {
            n = this.streamBytesPerPixel;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (options == Options.STREAM_VOLUME_MIC) {
            n = this.streamMicVolume;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (options == Options.STREAM_VOLUME_SYSTEM) {
            n = this.streamGameVolume;
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (options == Options.STREAM_KBPS) {
            n = this.streamKbps;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (options == Options.STREAM_FPS) {
            n = this.streamFps;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n = 0.0f;
        }
        return n;
    }
    
    private void updateRenderClouds() {
        switch (this.ofClouds) {
            case 1: {
                this.clouds = " ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.clouds = "  ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.clouds = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                break;
            }
            default: {
                if (!this.fancyGraphics) {
                    this.clouds = " ".length();
                    break;
                }
                this.clouds = "  ".length();
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
        }
    }
    
    public void saveOfOptions() {
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.optionsFileOF));
            printWriter.println(GameSettings.I[357 + 79 - 360 + 504] + this.renderDistanceChunks);
            printWriter.println(GameSettings.I[359 + 71 - 170 + 321] + this.ofFogType);
            printWriter.println(GameSettings.I[122 + 507 - 495 + 448] + this.ofFogStart);
            printWriter.println(GameSettings.I[104 + 142 - 38 + 375] + this.ofMipmapType);
            printWriter.println(GameSettings.I[507 + 430 - 740 + 387] + this.ofOcclusionFancy);
            printWriter.println(GameSettings.I[388 + 330 - 343 + 210] + this.ofSmoothFps);
            printWriter.println(GameSettings.I[337 + 97 - 170 + 322] + this.ofSmoothWorld);
            printWriter.println(GameSettings.I[341 + 535 - 611 + 322] + this.ofAoLevel);
            printWriter.println(GameSettings.I[486 + 537 - 647 + 212] + this.ofClouds);
            printWriter.println(GameSettings.I[440 + 178 - 441 + 412] + this.ofCloudsHeight);
            printWriter.println(GameSettings.I[8 + 470 - 167 + 279] + this.ofTrees);
            printWriter.println(GameSettings.I[569 + 30 - 388 + 380] + this.ofDroppedItems);
            printWriter.println(GameSettings.I[36 + 569 - 543 + 530] + this.ofRain);
            printWriter.println(GameSettings.I[549 + 265 - 551 + 330] + this.ofAnimatedWater);
            printWriter.println(GameSettings.I[260 + 336 - 203 + 201] + this.ofAnimatedLava);
            printWriter.println(GameSettings.I[574 + 577 - 999 + 443] + this.ofAnimatedFire);
            printWriter.println(GameSettings.I[155 + 495 - 442 + 388] + this.ofAnimatedPortal);
            printWriter.println(GameSettings.I[224 + 19 - 188 + 542] + this.ofAnimatedRedstone);
            printWriter.println(GameSettings.I[86 + 382 + 29 + 101] + this.ofAnimatedExplosion);
            printWriter.println(GameSettings.I[26 + 435 - 327 + 465] + this.ofAnimatedFlame);
            printWriter.println(GameSettings.I[280 + 516 - 317 + 121] + this.ofAnimatedSmoke);
            printWriter.println(GameSettings.I[111 + 139 + 245 + 106] + this.ofVoidParticles);
            printWriter.println(GameSettings.I[248 + 493 - 733 + 594] + this.ofWaterParticles);
            printWriter.println(GameSettings.I[476 + 136 - 131 + 122] + this.ofPortalParticles);
            printWriter.println(GameSettings.I[204 + 32 - 101 + 469] + this.ofPotionParticles);
            printWriter.println(GameSettings.I[3 + 86 + 204 + 312] + this.ofFireworkParticles);
            printWriter.println(GameSettings.I[309 + 345 - 543 + 495] + this.ofDrippingWaterLava);
            printWriter.println(GameSettings.I[448 + 291 - 458 + 326] + this.ofAnimatedTerrain);
            printWriter.println(GameSettings.I[487 + 479 - 439 + 81] + this.ofAnimatedTextures);
            printWriter.println(GameSettings.I[27 + 604 - 416 + 394] + this.ofRainSplash);
            printWriter.println(GameSettings.I[315 + 586 - 791 + 500] + this.ofLagometer);
            printWriter.println(GameSettings.I[110 + 252 + 76 + 173] + this.ofShowFps);
            printWriter.println(GameSettings.I[542 + 397 - 920 + 593] + this.ofAutoSaveTicks);
            printWriter.println(GameSettings.I[106 + 474 - 127 + 160] + this.ofBetterGrass);
            printWriter.println(GameSettings.I[381 + 266 - 320 + 287] + this.ofConnectedTextures);
            printWriter.println(GameSettings.I[362 + 155 + 43 + 55] + this.ofWeather);
            printWriter.println(GameSettings.I[215 + 165 - 11 + 247] + this.ofSky);
            printWriter.println(GameSettings.I[423 + 54 - 206 + 346] + this.ofStars);
            printWriter.println(GameSettings.I[343 + 564 - 392 + 103] + this.ofSunMoon);
            printWriter.println(GameSettings.I[266 + 234 + 20 + 99] + this.ofVignette);
            printWriter.println(GameSettings.I[600 + 153 - 313 + 180] + this.ofChunkUpdates);
            printWriter.println(GameSettings.I[72 + 75 + 405 + 69] + this.ofChunkLoading);
            printWriter.println(GameSettings.I[100 + 233 - 31 + 320] + this.ofChunkUpdatesDynamic);
            printWriter.println(GameSettings.I[605 + 410 - 549 + 157] + this.ofTime);
            printWriter.println(GameSettings.I[396 + 344 - 186 + 70] + this.ofClearWater);
            printWriter.println(GameSettings.I[610 + 135 - 462 + 342] + this.ofAaLevel);
            printWriter.println(GameSettings.I[117 + 169 + 322 + 18] + this.ofAfLevel);
            printWriter.println(GameSettings.I[498 + 433 - 456 + 152] + this.ofProfiler);
            printWriter.println(GameSettings.I[49 + 232 + 318 + 29] + this.ofBetterSnow);
            printWriter.println(GameSettings.I[113 + 551 - 93 + 58] + this.ofSwampColors);
            printWriter.println(GameSettings.I[553 + 451 - 977 + 603] + this.ofRandomMobs);
            printWriter.println(GameSettings.I[322 + 179 + 62 + 68] + this.ofSmoothBiomes);
            printWriter.println(GameSettings.I[15 + 224 + 82 + 311] + this.ofCustomFonts);
            printWriter.println(GameSettings.I[222 + 212 - 142 + 341] + this.ofCustomColors);
            printWriter.println(GameSettings.I[31 + 96 + 461 + 46] + this.ofCustomSky);
            printWriter.println(GameSettings.I[141 + 226 + 12 + 256] + this.ofShowCapes);
            printWriter.println(GameSettings.I[439 + 363 - 685 + 519] + this.ofNaturalTextures);
            printWriter.println(GameSettings.I[622 + 621 - 727 + 121] + this.ofLazyChunkLoading);
            printWriter.println(GameSettings.I[532 + 453 - 799 + 452] + this.ofFullscreenMode);
            printWriter.println(GameSettings.I[190 + 10 + 16 + 423] + this.ofFastMath);
            printWriter.println(GameSettings.I[56 + 524 - 294 + 354] + this.ofFastRender);
            printWriter.println(GameSettings.I[30 + 404 - 76 + 283] + this.ofTranslucentBlocks);
            printWriter.println(GameSettings.I[498 + 323 - 525 + 346] + this.ofKeyBindZoom.getKeyDescription() + GameSettings.I[117 + 61 - 129 + 594] + this.ofKeyBindZoom.getKeyCode());
            printWriter.close();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (Exception ex) {
            Config.warn(GameSettings.I[262 + 577 - 748 + 553]);
            ex.printStackTrace();
        }
    }
    
    public Set getModelParts() {
        return (Set)ImmutableSet.copyOf((Collection)this.setModelParts);
    }
    
    public void sendSettingsToServer() {
        if (this.mc.thePlayer != null) {
            int length = "".length();
            final Iterator<EnumPlayerModelParts> iterator = this.setModelParts.iterator();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                length |= iterator.next().getPartMask();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, length));
        }
    }
    
    public boolean func_181148_f() {
        return this.field_181150_U;
    }
    
    static final class GameSettings$2
    {
        private static final String[] I;
        private static final String __OBFID;
        static final int[] field_151477_a;
        
        static {
            I();
            __OBFID = GameSettings$2.I["".length()];
            field_151477_a = new int[Options.values().length];
            try {
                GameSettings$2.field_151477_a[Options.INVERT_MOUSE.ordinal()] = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GameSettings$2.field_151477_a[Options.VIEW_BOBBING.ordinal()] = "  ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GameSettings$2.field_151477_a[Options.ANAGLYPH.ordinal()] = "   ".length();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GameSettings$2.field_151477_a[Options.FBO_ENABLE.ordinal()] = (0x72 ^ 0x76);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                GameSettings$2.field_151477_a[Options.CHAT_COLOR.ordinal()] = (0x1F ^ 0x1A);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                GameSettings$2.field_151477_a[Options.CHAT_LINKS.ordinal()] = (0x7D ^ 0x7B);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                GameSettings$2.field_151477_a[Options.CHAT_LINKS_PROMPT.ordinal()] = (0xBA ^ 0xBD);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                GameSettings$2.field_151477_a[Options.SNOOPER_ENABLED.ordinal()] = (0x4E ^ 0x46);
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                GameSettings$2.field_151477_a[Options.USE_FULLSCREEN.ordinal()] = (0x53 ^ 0x5A);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                GameSettings$2.field_151477_a[Options.ENABLE_VSYNC.ordinal()] = (0x88 ^ 0x82);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                GameSettings$2.field_151477_a[Options.USE_VBO.ordinal()] = (0x1C ^ 0x17);
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                GameSettings$2.field_151477_a[Options.TOUCHSCREEN.ordinal()] = (0xB0 ^ 0xBC);
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                GameSettings$2.field_151477_a[Options.STREAM_SEND_METADATA.ordinal()] = (0xD ^ 0x0);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                GameSettings$2.field_151477_a[Options.FORCE_UNICODE_FONT.ordinal()] = (0x94 ^ 0x9A);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                GameSettings$2.field_151477_a[Options.BLOCK_ALTERNATIVES.ordinal()] = (0x1C ^ 0x13);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                GameSettings$2.field_151477_a[Options.REDUCED_DEBUG_INFO.ordinal()] = (0xB5 ^ 0xA5);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
            try {
                GameSettings$2.field_151477_a[Options.ENTITY_SHADOWS.ordinal()] = (0x97 ^ 0x86);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError17) {}
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0012\u0007.caa{Aedc", "QKqSQ");
        }
    }
    
    public enum Options
    {
        CHAT_HEIGHT_UNFOCUSED(Options.I[0x12 ^ 0x47], 0x2 ^ 0x1E, Options.I[0x4A ^ 0x1C], 0x1F ^ 0x3, Options.I[0xCC ^ 0x9B], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        FRAMERATE_LIMIT(Options.I[0xAC ^ 0xB5], 0x1A ^ 0x12, Options.I[0x84 ^ 0x9E], 0xCA ^ 0xC2, Options.I[0x6D ^ 0x76], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 0.0f, 260.0f, 5.0f), 
        TRANSLUCENT_BLOCKS(Options.I[49 + 243 - 42 + 63], 0x74 ^ 0x1C, Options.I[296 + 222 - 305 + 101], 210 + 148 - 314 + 955, Options.I[169 + 109 + 30 + 7], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_PORTAL(Options.I[107 + 133 - 232 + 158], 0x28 ^ 0x1F, Options.I[90 + 26 - 48 + 99], 255 + 761 - 483 + 466, Options.I[19 + 35 - 9 + 123], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SWAMP_COLORS(Options.I[145 + 250 - 296 + 160], 0x18 ^ 0x4E, Options.I[208 + 123 - 101 + 30], 986 + 590 - 737 + 160, Options.I[32 + 250 - 269 + 248], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        BETTER_GRASS(Options.I[10 + 11 + 38 + 122], 0x64 ^ 0x58, Options.I[123 + 51 - 24 + 32], 725 + 768 - 775 + 281, Options.I[0 + 118 + 8 + 57], (boolean)("".length() != 0), (boolean)("".length() != 0));
        
        private final float valueStep;
        
        USE_FULLSCREEN(Options.I[0x37 ^ 0x77], 0x55 ^ 0x40, Options.I[0xCA ^ 0x8B], 0x93 ^ 0x86, Options.I[0xE5 ^ 0xA7], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        SMOOTH_BIOMES(Options.I[177 + 176 - 172 + 84], 0x29 ^ 0x71, Options.I[263 + 213 - 462 + 252], 7 + 190 + 672 + 130, Options.I[220 + 35 - 246 + 258], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FAST_RENDER(Options.I[31 + 162 - 171 + 288], 0x7F ^ 0x18, Options.I[164 + 249 - 411 + 309], 722 + 808 - 1293 + 762, Options.I[74 + 124 - 180 + 294], (boolean)("".length() != 0), (boolean)("".length() != 0));
        
        private final boolean enumFloat;
        
        STREAM_BYTES_PER_PIXEL(Options.I[0x28 ^ 0x76], 0x7E ^ 0x61, Options.I[0x9B ^ 0xC4], 0x3F ^ 0x20, Options.I[0x36 ^ 0x56], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        TOUCHSCREEN(Options.I[0x38 ^ 0x71], 0x1C ^ 0x4, Options.I[0x48 ^ 0x2], 0x0 ^ 0x18, Options.I[0xDD ^ 0x96], (boolean)("".length() != 0), (boolean)(" ".length() != 0));
        
        private static final String __OBFID;
        
        VOID_PARTICLES(Options.I[14 + 190 - 130 + 152], 0x3F ^ 0x74, Options.I[162 + 223 - 272 + 114], 551 + 321 + 70 + 57, Options.I[130 + 66 - 130 + 162], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        AF_LEVEL(Options.I[44 + 258 - 216 + 197], 0x27 ^ 0x79, Options.I[180 + 179 - 349 + 274], 876 + 225 - 215 + 113, Options.I[277 + 177 - 374 + 205], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 1.0f, 16.0f, 1.0f), 
        GAMMA(Options.I[0x7A ^ 0x70], "   ".length(), Options.I[0x33 ^ 0x38], "   ".length(), Options.I[0xAD ^ 0xA1], (boolean)(" ".length() != 0), (boolean)("".length() != 0));
        
        private static final Options[] $VALUES;
        
        CHUNK_UPDATES(Options.I[175 + 160 - 334 + 210], 0xF5 ^ 0xB3, Options.I[68 + 62 - 17 + 99], 715 + 556 - 824 + 552, Options.I[90 + 155 - 84 + 52], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FOG_FANCY(Options.I[16 + 30 + 82 + 5], 0x60 ^ 0x4C, Options.I[118 + 48 - 49 + 17], 652 + 445 - 970 + 872, Options.I[30 + 110 - 112 + 107], (boolean)("".length() != 0), (boolean)("".length() != 0));
        
        private float valueMax;
        
        CHUNK_LOADING(Options.I[11 + 237 - 1 + 45], 0x22 ^ 0x43, Options.I[171 + 177 - 150 + 95], 830 + 48 - 829 + 950, Options.I[75 + 74 - 124 + 269], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        REDUCED_DEBUG_INFO(Options.I[83 + 66 - 70 + 48], 0x5D ^ 0x77, Options.I[81 + 107 - 119 + 59], 0x71 ^ 0x5B, Options.I[78 + 9 - 55 + 97], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        STREAM_CHAT_ENABLED(Options.I[0xD5 ^ 0xA6], 0xA6 ^ 0x80, Options.I[0xC9 ^ 0xBD], 0x93 ^ 0xB5, Options.I[0x72 ^ 0x7], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        MIPMAP_TYPE(Options.I[106 + 117 - 124 + 40], 0xB4 ^ 0x9A, Options.I[66 + 86 - 115 + 103], 698 + 507 - 233 + 27, Options.I[81 + 27 - 44 + 77], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 0.0f, 3.0f, 1.0f), 
        GUI_SCALE(Options.I[0x43 ^ 0x6B], 0x65 ^ 0x68, Options.I[0xA7 ^ 0x8E], 0x7E ^ 0x73, Options.I[0x27 ^ 0xD], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        PORTAL_PARTICLES(Options.I[163 + 103 - 210 + 179], 0x1 ^ 0x4F, Options.I[224 + 46 - 52 + 18], 993 + 941 - 1459 + 524, Options.I[87 + 65 + 18 + 67], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_LAVA(Options.I[150 + 87 - 144 + 67], 0xA3 ^ 0x96, Options.I[64 + 61 - 74 + 110], 266 + 619 + 101 + 13, Options.I[114 + 101 - 142 + 89], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CUSTOM_FONTS(Options.I[94 + 257 - 137 + 54], 0x4F ^ 0x16, Options.I[245 + 89 - 237 + 172], 558 + 912 - 697 + 226, Options.I[8 + 52 - 14 + 224], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SUN_MOON(Options.I[37 + 80 - 63 + 151], 0xCD ^ 0x89, Options.I[90 + 203 - 193 + 106], 111 + 422 - 509 + 975, Options.I[80 + 146 - 207 + 188], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        RAIN_SPLASH(Options.I[4 + 16 + 211 + 1], 0xC4 ^ 0x89, Options.I[163 + 133 - 99 + 36], 433 + 390 - 572 + 748, Options.I[157 + 83 - 173 + 167], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FORCE_UNICODE_FONT(Options.I[0x2A ^ 0x71], 0x8C ^ 0x92, Options.I[0xCF ^ 0x93], 0xA1 ^ 0xBF, Options.I[0x55 ^ 0x8], (boolean)("".length() != 0), (boolean)(" ".length() != 0));
        
        private final boolean enumBoolean;
        
        FULLSCREEN_MODE(Options.I[37 + 219 - 39 + 36], 0x2C ^ 0x78, Options.I[104 + 64 + 70 + 16], 502 + 706 - 1052 + 843, Options.I[224 + 235 - 389 + 185], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_SMOKE(Options.I[85 + 55 + 40 + 13], 0x4A ^ 0xA, Options.I[21 + 6 + 141 + 26], 588 + 814 - 424 + 21, Options.I[179 + 76 - 204 + 144], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        POTION_PARTICLES(Options.I[226 + 138 - 249 + 123], 0x66 ^ 0x29, Options.I[126 + 185 - 120 + 48], 208 + 961 - 1160 + 990, Options.I[200 + 130 - 150 + 60], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_LINKS_PROMPT(Options.I[0x2 ^ 0x38], 0xE ^ 0x1D, Options.I[0xA6 ^ 0x9D], 0xD ^ 0x1E, Options.I[0x23 ^ 0x1F], (boolean)("".length() != 0), (boolean)(" ".length() != 0));
        
        private float valueMin;
        
        DRIPPING_WATER_LAVA(Options.I[188 + 111 - 91 + 39], 0x1F ^ 0x4D, Options.I[246 + 113 - 151 + 40], 326 + 361 - 261 + 573, Options.I[121 + 216 - 213 + 125], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SKY(Options.I[35 + 196 - 191 + 159], 0x76 ^ 0x34, Options.I[36 + 152 - 63 + 75], 150 + 309 + 141 + 399, Options.I[142 + 36 - 114 + 137], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_TERRAIN(Options.I[45 + 36 + 66 + 109], 0x14 ^ 0x41, Options.I[168 + 94 - 200 + 195], 460 + 942 - 653 + 250, Options.I[75 + 106 + 3 + 74], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_EXPLOSION(Options.I[111 + 155 - 79 + 0], 0x5B ^ 0x65, Options.I[8 + 102 - 49 + 127], 608 + 464 - 864 + 791, Options.I[130 + 32 - 144 + 171], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FIREWORK_PARTICLES(Options.I[186 + 185 - 237 + 107], 0xCD ^ 0x9D, Options.I[2 + 166 - 80 + 154], 671 + 666 - 632 + 294, Options.I[34 + 10 + 77 + 122], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CLEAR_WATER(Options.I[67 + 209 - 128 + 72], 0x67 ^ 0x2E, Options.I[190 + 37 - 205 + 199], 930 + 308 - 1003 + 764, Options.I[147 + 32 - 6 + 49], (boolean)("".length() != 0), (boolean)("".length() != 0));
        
        private final String enumString;
        
        CHAT_SCALE(Options.I[0x4D ^ 0x1], 0xDA ^ 0xC3, Options.I[0x28 ^ 0x65], 0x32 ^ 0x2B, Options.I[0x44 ^ 0xA], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        RAIN(Options.I[27 + 102 - 24 + 49], 0x3F ^ 0xC, Options.I[105 + 17 - 107 + 140], 439 + 4 + 305 + 251, Options.I[118 + 110 - 82 + 10], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ENTITY_SHADOWS(Options.I[65 + 103 - 70 + 32], 0x8B ^ 0xA0, Options.I[62 + 32 + 30 + 7], 0x45 ^ 0x6E, Options.I[33 + 122 - 75 + 52], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        CHAT_LINKS(Options.I[0x40 ^ 0x74], 0x89 ^ 0x98, Options.I[0x91 ^ 0xA4], 0x67 ^ 0x76, Options.I[0x3C ^ 0xA], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        FOV(Options.I[0x58 ^ 0x5F], "  ".length(), Options.I[0x66 ^ 0x6E], "  ".length(), Options.I[0x33 ^ 0x3A], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 30.0f, 110.0f, 1.0f), 
        BLOCK_ALTERNATIVES(Options.I[0xFD ^ 0x81], 0x91 ^ 0xB8, Options.I[0xCE ^ 0xB3], 0xB ^ 0x22, Options.I[0x58 ^ 0x26], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        VIEW_BOBBING(Options.I[0x58 ^ 0x4B], 0x1D ^ 0x1B, Options.I[0x56 ^ 0x42], 0x69 ^ 0x6F, Options.I[0x9D ^ 0x88], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        STREAM_CHAT_USER_FILTER(Options.I[0xCC ^ 0xBA], 0xA3 ^ 0x84, Options.I[0x73 ^ 0x4], 0xA7 ^ 0x80, Options.I[0x1B ^ 0x63], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_OPACITY(Options.I[0x46 ^ 0x71], 0xA2 ^ 0xB0, Options.I[0xFE ^ 0xC6], 0x80 ^ 0x92, Options.I[0x1A ^ 0x23], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        STREAM_FPS(Options.I[0xE8 ^ 0x82], 0x2C ^ 0xF, Options.I[0x44 ^ 0x2F], 0xE6 ^ 0xC5, Options.I[0x70 ^ 0x1C], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        CLOUD_HEIGHT(Options.I[106 + 104 - 81 + 19], 0x88 ^ 0xB9, Options.I[120 + 135 - 183 + 77], 176 + 951 - 1072 + 944, Options.I[137 + 38 - 66 + 41], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        CONNECTED_TEXTURES(Options.I[36 + 46 + 135 + 60], 0x98 ^ 0xC4, Options.I[261 + 65 - 193 + 145], 540 + 54 + 176 + 229, Options.I[52 + 252 - 32 + 7], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FBO_ENABLE(Options.I[0xB0 ^ 0xAC], 0x12 ^ 0x1B, Options.I[0xD9 ^ 0xC4], 0x94 ^ 0x9D, Options.I[0x46 ^ 0x58], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        LAZY_CHUNK_LOADING(Options.I[228 + 4 - 24 + 93], 0xE ^ 0x6A, Options.I[104 + 100 - 36 + 134], 402 + 463 - 66 + 200, Options.I[260 + 214 - 287 + 116], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        AA_LEVEL(Options.I[148 + 252 - 129 + 9], 0xE7 ^ 0xBA, Options.I[40 + 250 - 160 + 151], 392 + 328 - 441 + 720, Options.I[170 + 105 - 234 + 241], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 0.0f, 16.0f, 1.0f), 
        ANIMATED_TEXTURES(Options.I[84 + 181 - 256 + 277], 0x60 ^ 0x3F, Options.I[80 + 134 - 95 + 168], 166 + 924 - 999 + 908, Options.I[183 + 73 + 1 + 31], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CUSTOM_COLORS(Options.I[45 + 225 - 152 + 153], 0xC6 ^ 0x9C, Options.I[241 + 183 - 358 + 206], 168 + 791 - 408 + 448, Options.I[240 + 66 - 136 + 103], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        STREAM_KBPS(Options.I[0xC3 ^ 0xA4], 0x1 ^ 0x23, Options.I[0xF5 ^ 0x9D], 0x40 ^ 0x62, Options.I[0x4D ^ 0x24], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        SNOOPER_ENABLED(Options.I[0xF ^ 0x32], 0xA5 ^ 0xB1, Options.I[0x45 ^ 0x7B], 0x2A ^ 0x3E, Options.I[0x92 ^ 0xAD], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        CUSTOM_SKY(Options.I[285 + 147 - 387 + 259], 0xB ^ 0x6E, Options.I[224 + 63 - 12 + 30], 386 + 691 - 248 + 170, Options.I[220 + 130 - 134 + 90], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ENABLE_VSYNC(Options.I[0x71 ^ 0x32], 0x14 ^ 0x2, Options.I[0x66 ^ 0x22], 0x2C ^ 0x3A, Options.I[0x26 ^ 0x63], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        PARTICLES(Options.I[0x55 ^ 0x7E], 0x49 ^ 0x47, Options.I[0x31 ^ 0x1D], 0x71 ^ 0x7F, Options.I[0x25 ^ 0x8], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        DROPPED_ITEMS(Options.I[131 + 1 - 122 + 288], 0x3D ^ 0x5E, Options.I[49 + 58 + 27 + 165], 577 + 606 - 1111 + 927, Options.I[130 + 152 - 233 + 251], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        TREES(Options.I[10 + 30 + 75 + 36], 0x56 ^ 0x64, Options.I[76 + 8 - 33 + 101], 75 + 724 - 547 + 747, Options.I[119 + 115 - 140 + 59], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        BETTER_SNOW(Options.I[75 + 119 - 124 + 180], 0x6C ^ 0x3F, Options.I[85 + 30 + 75 + 61], 313 + 957 - 683 + 412, Options.I[88 + 38 + 108 + 18], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        ANAGLYPH(Options.I[0x9F ^ 0x89], 0x7 ^ 0x0, Options.I[0x25 ^ 0x32], 0x71 ^ 0x76, Options.I[0x6A ^ 0x72], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        AUTOSAVE_TICKS(Options.I[50 + 124 - 58 + 62], 0x13 ^ 0x28, Options.I[60 + 155 - 72 + 36], 181 + 232 + 164 + 422, Options.I[162 + 110 - 105 + 13], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        WATER_PARTICLES(Options.I[213 + 121 - 303 + 198], 0x18 ^ 0x54, Options.I[31 + 2 + 172 + 25], 850 + 774 - 743 + 118, Options.I[120 + 8 + 60 + 43], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FOG_START(Options.I[124 + 36 - 158 + 134], 0xE8 ^ 0xC5, Options.I[74 + 37 - 0 + 26], 645 + 414 - 627 + 567, Options.I[27 + 79 + 28 + 4], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_COLOR(Options.I[0x7F ^ 0x4E], 0x85 ^ 0x95, Options.I[0x2B ^ 0x19], 0xA1 ^ 0xB1, Options.I[0x9B ^ 0xA8], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        SENSITIVITY(Options.I[0x5 ^ 0x1], " ".length(), Options.I[0x8B ^ 0x8E], " ".length(), Options.I[0x46 ^ 0x40], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        MIPMAP_LEVELS(Options.I[0x34 ^ 0x6C], 0xDD ^ 0xC0, Options.I[0x74 ^ 0x2D], 0x64 ^ 0x79, Options.I[0x79 ^ 0x23], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 0.0f, 4.0f, 1.0f), 
        HELD_ITEM_TOOLTIPS(Options.I[251 + 269 - 364 + 139], 0x70 ^ 0x12, Options.I[224 + 278 - 470 + 264], 40 + 337 + 596 + 26, Options.I[201 + 216 - 128 + 8], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        AMBIENT_OCCLUSION(Options.I[0xA ^ 0x2F], 0x67 ^ 0x6B, Options.I[0xA3 ^ 0x85], 0x57 ^ 0x5B, Options.I[0xBB ^ 0x9C], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        FAST_MATH(Options.I[246 + 56 - 85 + 90], 0xF7 ^ 0x91, Options.I[139 + 111 - 237 + 295], 714 + 80 + 180 + 25, Options.I[158 + 179 - 236 + 208], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SMOOTH_WORLD(Options.I[64 + 88 - 111 + 182], 0x5B ^ 0x11, Options.I[86 + 71 + 33 + 34], 458 + 652 - 235 + 124, Options.I[154 + 43 - 36 + 64], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        USE_VBO(Options.I[0x1D ^ 0x5B], 0x40 ^ 0x57, Options.I[0x63 ^ 0x24], 0x84 ^ 0x93, Options.I[0x5F ^ 0x17], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        ANIMATED_REDSTONE(Options.I[49 + 87 + 41 + 7], 0x25 ^ 0x18, Options.I[170 + 76 - 182 + 121], 878 + 696 - 999 + 424, Options.I[73 + 28 - 17 + 102], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_WIDTH(Options.I[0x2 ^ 0x4D], 0x1D ^ 0x7, Options.I[0xD2 ^ 0x82], 0x58 ^ 0x42, Options.I[0x2A ^ 0x7B], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        ANIMATED_FIRE(Options.I[37 + 118 - 107 + 115], 0x7E ^ 0x48, Options.I[37 + 79 - 32 + 80], 878 + 515 - 1012 + 618, Options.I[152 + 64 - 108 + 57], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        AO_LEVEL(Options.I[46 + 7 + 64 + 52], 0x6F ^ 0x57, Options.I[40 + 156 - 116 + 90], 928 + 211 - 443 + 303, Options.I[90 + 74 - 50 + 57], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        STREAM_MIC_TOGGLE_BEHAVIOR(Options.I[0xBF ^ 0xC6], 0xAE ^ 0x86, Options.I[0x12 ^ 0x68], 0xEF ^ 0xC7, Options.I[0x4E ^ 0x35], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        LAGOMETER(Options.I[48 + 77 - 102 + 149], 0x25 ^ 0x1C, Options.I[84 + 151 - 226 + 164], 795 + 334 - 353 + 223, Options.I[29 + 128 - 20 + 37], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_HEIGHT_FOCUSED(Options.I[0x50 ^ 0x2], 0x58 ^ 0x43, Options.I[0x3C ^ 0x6F], 0xB8 ^ 0xA3, Options.I[0x77 ^ 0x23], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        SHOW_CAPES(Options.I[131 + 106 - 83 + 120], 0x19 ^ 0x42, Options.I[160 + 256 - 236 + 95], 315 + 842 - 1082 + 924, Options.I[85 + 45 + 88 + 58], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        STREAM_VOLUME_MIC(Options.I[0x2B ^ 0x4A], 0xBE ^ 0x9E, Options.I[0xFB ^ 0x99], 0x39 ^ 0x19, Options.I[0x45 ^ 0x26], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        WEATHER(Options.I[45 + 102 + 25 + 24], 0x2C ^ 0x6D, Options.I[104 + 172 - 118 + 39], 39 + 138 + 79 + 743, Options.I[161 + 22 - 15 + 30], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        TIME(Options.I[181 + 8 - 11 + 39], 0x56 ^ 0x1E, Options.I[145 + 53 - 60 + 80], 824 + 972 - 1465 + 668, Options.I[95 + 3 + 48 + 73], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        RANDOM_MOBS(Options.I[141 + 223 - 139 + 37], 0x7D ^ 0x2A, Options.I[235 + 65 - 85 + 48], 296 + 785 - 465 + 383, Options.I[257 + 156 - 200 + 51], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        PROFILER(Options.I[7 + 77 + 11 + 149], 0x64 ^ 0x35, Options.I[104 + 168 - 129 + 102], 306 + 630 - 83 + 146, Options.I[172 + 42 - 81 + 113], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        GRAPHICS(Options.I[0x8F ^ 0xAD], 0xAC ^ 0xA7, Options.I[0xAD ^ 0x8E], 0xAB ^ 0xA0, Options.I[0x4E ^ 0x6A], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        STREAM_SEND_METADATA(Options.I[0x20 ^ 0x50], 0x7B ^ 0x5E, Options.I[0xCF ^ 0xBE], 0x79 ^ 0x5C, Options.I[0x35 ^ 0x47], (boolean)("".length() != 0), (boolean)(" ".length() != 0));
        
        private static final Options[] ENUM$VALUES;
        
        ANIMATED_FLAME(Options.I[17 + 5 + 69 + 99], 0x71 ^ 0x4E, Options.I[21 + 19 + 111 + 40], 615 + 632 - 873 + 625, Options.I[168 + 139 - 236 + 121], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CLOUDS(Options.I[121 + 108 - 221 + 137], 0x20 ^ 0x10, Options.I[130 + 118 - 244 + 142], 944 + 25 - 129 + 159, Options.I[123 + 90 - 198 + 132], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHAT_VISIBILITY(Options.I[0xB0 ^ 0x9E], 0x50 ^ 0x5F, Options.I[0xB4 ^ 0x9B], 0x2C ^ 0x23, Options.I[0x94 ^ 0xA4], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        NATURAL_TEXTURES(Options.I[249 + 138 - 108 + 10], 0x6F ^ 0xF, Options.I[168 + 23 + 98 + 1], 552 + 762 - 628 + 313, Options.I[247 + 52 - 28 + 20], (boolean)("".length() != 0), (boolean)("".length() != 0));
        
        private static final String[] I;
        
        ANIMATED_WATER(Options.I[135 + 40 - 56 + 38], 0x3 ^ 0x37, Options.I[47 + 71 - 13 + 53], 327 + 186 - 23 + 509, Options.I[139 + 61 - 125 + 84], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        CHUNK_UPDATES_DYNAMIC(Options.I[194 + 159 - 261 + 122], 0x51 ^ 0x16, Options.I[4 + 96 - 21 + 136], 359 + 337 - 634 + 937, Options.I[121 + 148 - 100 + 47], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        STREAM_COMPRESSION(Options.I[0xD7 ^ 0xBA], 0x71 ^ 0x55, Options.I[0x59 ^ 0x37], 0xBC ^ 0x98, Options.I[0xF ^ 0x60], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        INVERT_MOUSE(Options.I[" ".length()], "".length(), Options.I["  ".length()], "".length(), Options.I["   ".length()], (boolean)("".length() != 0), (boolean)(" ".length() != 0)), 
        STREAM_VOLUME_SYSTEM(Options.I[0xC1 ^ 0xA5], 0xB8 ^ 0x99, Options.I[0x1D ^ 0x78], 0x4B ^ 0x6A, Options.I[0x65 ^ 0x3], (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
        STARS(Options.I[87 + 198 - 181 + 98], 0x45 ^ 0x6, Options.I[146 + 163 - 263 + 157], 998 + 546 - 1445 + 900, Options.I[10 + 170 - 79 + 103], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        RENDER_CLOUDS(Options.I[0xB8 ^ 0xA7], 0x9E ^ 0x94, Options.I[0x38 ^ 0x18], 0x45 ^ 0x4F, Options.I[0x17 ^ 0x36], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        RENDER_DISTANCE(Options.I[0x8C ^ 0x9C], 0x29 ^ 0x2C, Options.I[0x5B ^ 0x4A], 0x96 ^ 0x93, Options.I[0x5A ^ 0x48], (boolean)(" ".length() != 0), (boolean)("".length() != 0), 2.0f, 16.0f, 1.0f), 
        SMOOTH_FPS(Options.I[94 + 75 - 114 + 87], 0x7 ^ 0x28, Options.I[35 + 51 - 55 + 112], 724 + 211 - 267 + 331, Options.I[117 + 12 - 113 + 128], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SHOW_FPS(Options.I[124 + 142 - 169 + 78], 0x5E ^ 0x64, Options.I[164 + 93 - 81 + 0], 655 + 11 - 118 + 451, Options.I[100 + 116 - 147 + 108], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        VIGNETTE(Options.I[75 + 19 + 47 + 67], 0x37 ^ 0x72, Options.I[83 + 57 + 16 + 53], 344 + 844 - 1089 + 900, Options.I[126 + 35 + 36 + 13], (boolean)("".length() != 0), (boolean)("".length() != 0)), 
        SATURATION(Options.I[0x6 ^ 0xB], 0x95 ^ 0x91, Options.I[0x6 ^ 0x8], 0x18 ^ 0x1C, Options.I[0x18 ^ 0x17], (boolean)(" ".length() != 0), (boolean)("".length() != 0));
        
        public boolean getEnumFloat() {
            return this.enumFloat;
        }
        
        protected float snapToStep(float n) {
            if (this.valueStep > 0.0f) {
                n = this.valueStep * Math.round(n / this.valueStep);
            }
            return n;
        }
        
        public String getEnumString() {
            return this.enumString;
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String s3, final boolean b, final boolean b2) {
            this(s, n, s2, n2, s3, b, b2, 0.0f, 1.0f, 0.0f);
        }
        
        public float snapToStepClamp(float snapToStep) {
            snapToStep = this.snapToStep(snapToStep);
            return MathHelper.clamp_float(snapToStep, this.valueMin, this.valueMax);
        }
        
        public int returnEnumOrdinal() {
            return this.ordinal();
        }
        
        static float access$3(final Options options) {
            return options.valueMin;
        }
        
        public float denormalizeValue(final float n) {
            return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(n, 0.0f, 1.0f));
        }
        
        public void setValueMax(final float valueMax) {
            this.valueMax = valueMax;
        }
        
        static float access$2(final Options options) {
            return options.valueMax;
        }
        
        public float getValueMax() {
            return this.valueMax;
        }
        
        static {
            I();
            __OBFID = Options.I["".length()];
            final Options[] enum$VALUES = new Options[0x73 ^ 0x1A];
            enum$VALUES["".length()] = Options.INVERT_MOUSE;
            enum$VALUES[" ".length()] = Options.SENSITIVITY;
            enum$VALUES["  ".length()] = Options.FOV;
            enum$VALUES["   ".length()] = Options.GAMMA;
            enum$VALUES[0x39 ^ 0x3D] = Options.SATURATION;
            enum$VALUES[0xA0 ^ 0xA5] = Options.RENDER_DISTANCE;
            enum$VALUES[0x63 ^ 0x65] = Options.VIEW_BOBBING;
            enum$VALUES[0xA7 ^ 0xA0] = Options.ANAGLYPH;
            enum$VALUES[0xB3 ^ 0xBB] = Options.FRAMERATE_LIMIT;
            enum$VALUES[0x7C ^ 0x75] = Options.FBO_ENABLE;
            enum$VALUES[0xCC ^ 0xC6] = Options.RENDER_CLOUDS;
            enum$VALUES[0x51 ^ 0x5A] = Options.GRAPHICS;
            enum$VALUES[0x2E ^ 0x22] = Options.AMBIENT_OCCLUSION;
            enum$VALUES[0x12 ^ 0x1F] = Options.GUI_SCALE;
            enum$VALUES[0x51 ^ 0x5F] = Options.PARTICLES;
            enum$VALUES[0x2E ^ 0x21] = Options.CHAT_VISIBILITY;
            enum$VALUES[0xBD ^ 0xAD] = Options.CHAT_COLOR;
            enum$VALUES[0x68 ^ 0x79] = Options.CHAT_LINKS;
            enum$VALUES[0xD1 ^ 0xC3] = Options.CHAT_OPACITY;
            enum$VALUES[0x94 ^ 0x87] = Options.CHAT_LINKS_PROMPT;
            enum$VALUES[0x58 ^ 0x4C] = Options.SNOOPER_ENABLED;
            enum$VALUES[0x27 ^ 0x32] = Options.USE_FULLSCREEN;
            enum$VALUES[0xA8 ^ 0xBE] = Options.ENABLE_VSYNC;
            enum$VALUES[0x78 ^ 0x6F] = Options.USE_VBO;
            enum$VALUES[0x3 ^ 0x1B] = Options.TOUCHSCREEN;
            enum$VALUES[0x8F ^ 0x96] = Options.CHAT_SCALE;
            enum$VALUES[0x64 ^ 0x7E] = Options.CHAT_WIDTH;
            enum$VALUES[0xF ^ 0x14] = Options.CHAT_HEIGHT_FOCUSED;
            enum$VALUES[0x2C ^ 0x30] = Options.CHAT_HEIGHT_UNFOCUSED;
            enum$VALUES[0x1A ^ 0x7] = Options.MIPMAP_LEVELS;
            enum$VALUES[0x76 ^ 0x68] = Options.FORCE_UNICODE_FONT;
            enum$VALUES[0x4B ^ 0x54] = Options.STREAM_BYTES_PER_PIXEL;
            enum$VALUES[0x82 ^ 0xA2] = Options.STREAM_VOLUME_MIC;
            enum$VALUES[0xAD ^ 0x8C] = Options.STREAM_VOLUME_SYSTEM;
            enum$VALUES[0x55 ^ 0x77] = Options.STREAM_KBPS;
            enum$VALUES[0xE3 ^ 0xC0] = Options.STREAM_FPS;
            enum$VALUES[0x30 ^ 0x14] = Options.STREAM_COMPRESSION;
            enum$VALUES[0x95 ^ 0xB0] = Options.STREAM_SEND_METADATA;
            enum$VALUES[0x16 ^ 0x30] = Options.STREAM_CHAT_ENABLED;
            enum$VALUES[0xBC ^ 0x9B] = Options.STREAM_CHAT_USER_FILTER;
            enum$VALUES[0x49 ^ 0x61] = Options.STREAM_MIC_TOGGLE_BEHAVIOR;
            enum$VALUES[0x3B ^ 0x12] = Options.BLOCK_ALTERNATIVES;
            enum$VALUES[0xB ^ 0x21] = Options.REDUCED_DEBUG_INFO;
            enum$VALUES[0xEF ^ 0xC4] = Options.ENTITY_SHADOWS;
            enum$VALUES[0x86 ^ 0xAA] = Options.FOG_FANCY;
            enum$VALUES[0xA0 ^ 0x8D] = Options.FOG_START;
            enum$VALUES[0x39 ^ 0x17] = Options.MIPMAP_TYPE;
            enum$VALUES[0xA5 ^ 0x8A] = Options.SMOOTH_FPS;
            enum$VALUES[0x3 ^ 0x33] = Options.CLOUDS;
            enum$VALUES[0x8A ^ 0xBB] = Options.CLOUD_HEIGHT;
            enum$VALUES[0x25 ^ 0x17] = Options.TREES;
            enum$VALUES[0x1F ^ 0x2C] = Options.RAIN;
            enum$VALUES[0xB7 ^ 0x83] = Options.ANIMATED_WATER;
            enum$VALUES[0x76 ^ 0x43] = Options.ANIMATED_LAVA;
            enum$VALUES[0xA2 ^ 0x94] = Options.ANIMATED_FIRE;
            enum$VALUES[0xAD ^ 0x9A] = Options.ANIMATED_PORTAL;
            enum$VALUES[0xFA ^ 0xC2] = Options.AO_LEVEL;
            enum$VALUES[0x6A ^ 0x53] = Options.LAGOMETER;
            enum$VALUES[0xBD ^ 0x87] = Options.SHOW_FPS;
            enum$VALUES[0x38 ^ 0x3] = Options.AUTOSAVE_TICKS;
            enum$VALUES[0x21 ^ 0x1D] = Options.BETTER_GRASS;
            enum$VALUES[0x69 ^ 0x54] = Options.ANIMATED_REDSTONE;
            enum$VALUES[0x13 ^ 0x2D] = Options.ANIMATED_EXPLOSION;
            enum$VALUES[0xAC ^ 0x93] = Options.ANIMATED_FLAME;
            enum$VALUES[0x84 ^ 0xC4] = Options.ANIMATED_SMOKE;
            enum$VALUES[0xD0 ^ 0x91] = Options.WEATHER;
            enum$VALUES[0x78 ^ 0x3A] = Options.SKY;
            enum$VALUES[0xE2 ^ 0xA1] = Options.STARS;
            enum$VALUES[0xE1 ^ 0xA5] = Options.SUN_MOON;
            enum$VALUES[0x6E ^ 0x2B] = Options.VIGNETTE;
            enum$VALUES[0x45 ^ 0x3] = Options.CHUNK_UPDATES;
            enum$VALUES[0x4C ^ 0xB] = Options.CHUNK_UPDATES_DYNAMIC;
            enum$VALUES[0xCA ^ 0x82] = Options.TIME;
            enum$VALUES[0x44 ^ 0xD] = Options.CLEAR_WATER;
            enum$VALUES[0x6B ^ 0x21] = Options.SMOOTH_WORLD;
            enum$VALUES[0x1D ^ 0x56] = Options.VOID_PARTICLES;
            enum$VALUES[0xD1 ^ 0x9D] = Options.WATER_PARTICLES;
            enum$VALUES[0x4B ^ 0x6] = Options.RAIN_SPLASH;
            enum$VALUES[0xE7 ^ 0xA9] = Options.PORTAL_PARTICLES;
            enum$VALUES[0x9 ^ 0x46] = Options.POTION_PARTICLES;
            enum$VALUES[0xFB ^ 0xAB] = Options.FIREWORK_PARTICLES;
            enum$VALUES[0x79 ^ 0x28] = Options.PROFILER;
            enum$VALUES[0x7B ^ 0x29] = Options.DRIPPING_WATER_LAVA;
            enum$VALUES[0xE ^ 0x5D] = Options.BETTER_SNOW;
            enum$VALUES[0xDA ^ 0x8E] = Options.FULLSCREEN_MODE;
            enum$VALUES[0x39 ^ 0x6C] = Options.ANIMATED_TERRAIN;
            enum$VALUES[0xB ^ 0x5D] = Options.SWAMP_COLORS;
            enum$VALUES[0xE3 ^ 0xB4] = Options.RANDOM_MOBS;
            enum$VALUES[0x35 ^ 0x6D] = Options.SMOOTH_BIOMES;
            enum$VALUES[0xE1 ^ 0xB8] = Options.CUSTOM_FONTS;
            enum$VALUES[0xFD ^ 0xA7] = Options.CUSTOM_COLORS;
            enum$VALUES[0xDF ^ 0x84] = Options.SHOW_CAPES;
            enum$VALUES[0x63 ^ 0x3F] = Options.CONNECTED_TEXTURES;
            enum$VALUES[0x42 ^ 0x1F] = Options.AA_LEVEL;
            enum$VALUES[0xC3 ^ 0x9D] = Options.AF_LEVEL;
            enum$VALUES[0xD0 ^ 0x8F] = Options.ANIMATED_TEXTURES;
            enum$VALUES[0x4C ^ 0x2C] = Options.NATURAL_TEXTURES;
            enum$VALUES[0xA2 ^ 0xC3] = Options.CHUNK_LOADING;
            enum$VALUES[0xD7 ^ 0xB5] = Options.HELD_ITEM_TOOLTIPS;
            enum$VALUES[0xD3 ^ 0xB0] = Options.DROPPED_ITEMS;
            enum$VALUES[0xD0 ^ 0xB4] = Options.LAZY_CHUNK_LOADING;
            enum$VALUES[0x5C ^ 0x39] = Options.CUSTOM_SKY;
            enum$VALUES[0x45 ^ 0x23] = Options.FAST_MATH;
            enum$VALUES[0xFB ^ 0x9C] = Options.FAST_RENDER;
            enum$VALUES[0xC0 ^ 0xA8] = Options.TRANSLUCENT_BLOCKS;
            ENUM$VALUES = enum$VALUES;
            final Options[] $values = new Options[0x4C ^ 0x60];
            $values["".length()] = Options.INVERT_MOUSE;
            $values[" ".length()] = Options.SENSITIVITY;
            $values["  ".length()] = Options.FOV;
            $values["   ".length()] = Options.GAMMA;
            $values[0x9F ^ 0x9B] = Options.SATURATION;
            $values[0xAE ^ 0xAB] = Options.RENDER_DISTANCE;
            $values[0x60 ^ 0x66] = Options.VIEW_BOBBING;
            $values[0x8F ^ 0x88] = Options.ANAGLYPH;
            $values[0x61 ^ 0x69] = Options.FRAMERATE_LIMIT;
            $values[0x8E ^ 0x87] = Options.FBO_ENABLE;
            $values[0x69 ^ 0x63] = Options.RENDER_CLOUDS;
            $values[0x38 ^ 0x33] = Options.GRAPHICS;
            $values[0x99 ^ 0x95] = Options.AMBIENT_OCCLUSION;
            $values[0x15 ^ 0x18] = Options.GUI_SCALE;
            $values[0x17 ^ 0x19] = Options.PARTICLES;
            $values[0xA4 ^ 0xAB] = Options.CHAT_VISIBILITY;
            $values[0x5 ^ 0x15] = Options.CHAT_COLOR;
            $values[0x88 ^ 0x99] = Options.CHAT_LINKS;
            $values[0xD2 ^ 0xC0] = Options.CHAT_OPACITY;
            $values[0x2B ^ 0x38] = Options.CHAT_LINKS_PROMPT;
            $values[0x8F ^ 0x9B] = Options.SNOOPER_ENABLED;
            $values[0xD6 ^ 0xC3] = Options.USE_FULLSCREEN;
            $values[0x7 ^ 0x11] = Options.ENABLE_VSYNC;
            $values[0x9B ^ 0x8C] = Options.USE_VBO;
            $values[0x3E ^ 0x26] = Options.TOUCHSCREEN;
            $values[0x7A ^ 0x63] = Options.CHAT_SCALE;
            $values[0xAF ^ 0xB5] = Options.CHAT_WIDTH;
            $values[0xAE ^ 0xB5] = Options.CHAT_HEIGHT_FOCUSED;
            $values[0x68 ^ 0x74] = Options.CHAT_HEIGHT_UNFOCUSED;
            $values[0xDF ^ 0xC2] = Options.MIPMAP_LEVELS;
            $values[0x62 ^ 0x7C] = Options.FORCE_UNICODE_FONT;
            $values[0x4A ^ 0x55] = Options.STREAM_BYTES_PER_PIXEL;
            $values[0xA4 ^ 0x84] = Options.STREAM_VOLUME_MIC;
            $values[0x8B ^ 0xAA] = Options.STREAM_VOLUME_SYSTEM;
            $values[0x23 ^ 0x1] = Options.STREAM_KBPS;
            $values[0x1 ^ 0x22] = Options.STREAM_FPS;
            $values[0x5D ^ 0x79] = Options.STREAM_COMPRESSION;
            $values[0x5D ^ 0x78] = Options.STREAM_SEND_METADATA;
            $values[0x9C ^ 0xBA] = Options.STREAM_CHAT_ENABLED;
            $values[0x3A ^ 0x1D] = Options.STREAM_CHAT_USER_FILTER;
            $values[0xB3 ^ 0x9B] = Options.STREAM_MIC_TOGGLE_BEHAVIOR;
            $values[0xBA ^ 0x93] = Options.BLOCK_ALTERNATIVES;
            $values[0x4C ^ 0x66] = Options.REDUCED_DEBUG_INFO;
            $values[0x1A ^ 0x31] = Options.ENTITY_SHADOWS;
            $VALUES = $values;
        }
        
        private static void I() {
            (I = new String[292 + 171 - 258 + 111])["".length()] = I("\f>\u001dT^\u007fBrR[|", "OrBdn");
            Options.I[" ".length()] = I("\r\",2!\u0010378&\u0017)", "Dlzws");
            Options.I["  ".length()] = I("\r\u001e\u0017\u0004\u0015\u0010\u000f\f\u000e\u0012\u0017\u0015", "DPAAG");
            Options.I["   ".length()] = I("\n\u001b&/\u0016\u000b\u0018|/\u0017\u0013\u000e 24\n\u001e!#", "ekRFy");
            Options.I[0xBE ^ 0xBA] = I("6?-\u0002-135\u00180<", "ezcQd");
            Options.I[0x7D ^ 0x78] = I("42\u0006 33>\u001e:.>", "gwHsz");
            Options.I[0x1B ^ 0x1D] = I(":\u001a1(\u0003;\u0019k2\t;\u0019,5\u0005#\u000318", "UjEAl");
            Options.I[0x0 ^ 0x7] = I("\t\u0001\u001c", "ONJAc");
            Options.I[0x80 ^ 0x88] = I("-'0", "khfVM");
            Options.I[0x7F ^ 0x76] = I("\u0017\u0002\u001e\u0013\u0000\u0016\u0001D\u001c\u0000\u000e", "xrjzo");
            Options.I[0xB9 ^ 0xB3] = I("&\n\u000b\u001b\u0003", "aKFVB");
            Options.I[0x4 ^ 0xF] = I("\u0002\u000f\"\b\u0007", "ENoEF");
            Options.I[0x8A ^ 0x86] = I("7\u0019\f )6\u001aV.'5\u0004\u0019", "XixIF");
            Options.I[0xAF ^ 0xA2] = I("2+\u0007$\u001f >\u001a>\u0003", "ajSqM");
            Options.I[0x75 ^ 0x7B] = I("\u0019\u0004,\u00178\u000b\u00111\r$", "JExBj");
            Options.I[0xCC ^ 0xC3] = I("\u0015\u001f\u0015'=\u0014\u001cO=3\u000e\u001a\u0013/&\u0013\u0000\u000f", "zoaNR");
            Options.I[0x83 ^ 0x93] = I("=,\u001e\u0000\u000f=6\u0014\r\u0019;(\u001e\u0007\u000f", "oiPDJ");
            Options.I[0xAA ^ 0xBB] = I("\n=\u0019&\u0017\n'\u0013+\u0001\f9\u0019!\u0017", "XxWbR");
            Options.I[0xD1 ^ 0xC3] = I("-\u001f!\u0000\u000e,\u001c{\u001b\u0004,\u000b0\u001b%+\u001c!\b\u000f!\n", "BoUia");
            Options.I[0x42 ^ 0x51] = I(" \u0001\u00161\u00104\u0007\u0011$\u00068\u000f", "vHSfO");
            Options.I[0xA1 ^ 0xB5] = I("& 1\u001692&6\u0003/>.", "pitAf");
            Options.I[0x57 ^ 0x42] = I("*\u000119<+\u0002k&: \u0006\u0007?1'\u0018+7", "EqEPS");
            Options.I[0x5F ^ 0x49] = I("\u001b\u001e\u0004\u0006?\u0003\u0000\r", "ZPEAs");
            Options.I[0xA7 ^ 0xB0] = I(" \u00175\u0016 8\t<", "aYtQl");
            Options.I[0xBC ^ 0xA4] = I("=8\u0016?\u0002<;L7\u00033/\u000e/\u001d:", "RHbVm");
            Options.I[0x93 ^ 0x8A] = I("\u001e+\u00039\u0001\n8\u00161\u001b\u00140\u000f=\u0010", "XyBtD");
            Options.I[0x83 ^ 0x99] = I("\u0011= \u0006\r\u0005.5\u000e\u0017\u001b&,\u0002\u001c", "WoaKH");
            Options.I[0x76 ^ 0x6D] = I("\r\u0014?\u0019,\f\u0017e\u00161\u0003\t.\u0002\"\u0016\u0001\u0007\u0019.\u000b\u0010", "bdKpC");
            Options.I[0x96 ^ 0x8A] = I("..)\b!&-$\u001b!", "hlfWd");
            Options.I[0x96 ^ 0x8B] = I("+.?\u0016\"#-2\u0005\"", "mlpIg");
            Options.I[0xBA ^ 0xA4] = I("!\u0011\r\u0004\u0006 \u0012W\u000b\u000b!$\u0017\f\u000b\"\u0004", "Naymi");
            Options.I[0x2F ^ 0x30] = I("\u000b\u001f\b\u000f\u0016\u000b\u0005\u0005\u0007\u001c\f\u001e\u0015", "YZFKS");
            Options.I[0xB5 ^ 0x95] = I("\u0001+(\u0002,\u00011%\n&\u0006*5", "SnfFi");
            Options.I[0x3C ^ 0x1D] = I(":>';,;=} &;*6 \u00009!&60", "UNSRC");
            Options.I[0xA4 ^ 0x86] = I("\f\u001f '\u001f\u0002\u000e2", "KMawW");
            Options.I[0x16 ^ 0x35] = I("7\u001f6\u0003\u00039\u000e$", "pMwSK");
            Options.I[0x63 ^ 0x47] = I("\u0007(:\u000e\u0007\u0006+`\u0000\u001a\t(&\u000e\u000b\u001b", "hXNgh");
            Options.I[0x37 ^ 0x12] = I("\u000f\u00063(\u000f\u0000\u001f..\t\r\u0007$2\u0003\u0001\u0005", "NKqaJ");
            Options.I[0xB3 ^ 0x95] = I("0\u000f .1?\u0016=(72\u000e74=>\f", "qBbgt");
            Options.I[0x88 ^ 0xAF] = I("\u0016\u0007>\u0006.\u0017\u0004d\u000e.", "ywJoA");
            Options.I[0x5B ^ 0x73] = I("\u0001\u001a$.\u0017\u0005\u000e!4", "FOmqD");
            Options.I[0xB5 ^ 0x9C] = I("\u0014\u001d\u001f4\u0000\u0010\t\u001a.", "SHVkS");
            Options.I[0x43 ^ 0x69] = I("\u00198\u0011\"\u0018\u0018;K,\u0002\u001f\u001b\u0006*\u001b\u0013", "vHeKw");
            Options.I[0x2C ^ 0x7] = I("\u00142>\r\u0013\u0007?)\n", "DslYZ");
            Options.I[0xAF ^ 0x83] = I("\u0001,\u00142\u000b\u0012!\u00035", "QmFfB");
            Options.I[0x93 ^ 0xBE] = I("\u0019':\r\u001c\u0018$`\u0014\u0012\u0004#'\u0007\u001f\u0013$", "vWNds");
            Options.I[0x2 ^ 0x2C] = I("5%\t\r> $\u001b\u0010#?!\u0001\r8", "vmHYa");
            Options.I[0xED ^ 0xC2] = I(",#3\u001b\u00189\"!\u0006\u0005&';\u001b\u001e", "okrOG");
            Options.I[0xB2 ^ 0x82] = I("\r4\f.\r\f7V$\n\u00030V1\u000b\u0011-\u001a.\u000e\u000b0\u0001", "bDxGb");
            Options.I[0x5E ^ 0x6F] = I("\" \u001b\u0012\b\"'\u0016\t\u0005", "ahZFW");
            Options.I[0x9D ^ 0xAF] = I("\u000f1\u0018\u0015\u0012\u000f6\u0015\u000e\u001f", "LyYAM");
            Options.I[0x1B ^ 0x28] = I("#\t\u001a>6\"\n@41-\r@46 \u0016\u001c", "LynWY");
            Options.I[0x5F ^ 0x6B] = I("\u0006\u001e+\u00047\t\u001f$\u001b;", "EVjPh");
            Options.I[0xB5 ^ 0x80] = I("\u0016\u0006,\u0005\u0017\u0019\u0007#\u001a\u001b", "UNmQH");
            Options.I[0x20 ^ 0x16] = I(",\n!<\u0005-\t{6\u0002\"\u000e{9\u0003-\u0011&", "CzUUj");
            Options.I[0x86 ^ 0xB1] = I("\r,\u0002\u000e\u001b\u00014\u0002\u0019\r\u001a=", "NdCZD");
            Options.I[0x84 ^ 0xBC] = I("\u0015/\u0015\u001b;\u00197\u0015\f-\u0002>", "VgTOd");
            Options.I[0x36 ^ 0xF] = I("><7\u001b\n??m\u0011\r08m\u001d\u00150/*\u0006\u001c", "QLCre");
            Options.I[0x73 ^ 0x49] = I("5\u000b\u0002\u00028:\n\r\u001d4)\u0013\u0011\u0019*&\u0017", "vCCVg");
            Options.I[0x79 ^ 0x42] = I("(\r\u0014:\u0016'\f\u001b%\u001a4\u0015\u0007!\u0004;\u0011", "kEUnI");
            Options.I[0xB7 ^ 0x8B] = I("'<6\u0002 &?l\b')8l\u0007&&'1E?:#/\u001b;", "HLBkO");
            Options.I[0x71 ^ 0x4C] = I("\u0019\b>=)\u000f\u0014.77\u000b\u0004=7=", "JFqry");
            Options.I[0xAE ^ 0x90] = I("\u0007\u001b+\u0018\u0001\u0011\u0007;\u0012\u001f\u0015\u0017(\u0012\u0015", "TUdWQ");
            Options.I[0x22 ^ 0x1D] = I("+\u0003:\u0019+*\u0000`\u0003*+\u001c>\u00156", "DsNpD");
            Options.I[0x54 ^ 0x14] = I(":\u0004\u0002.\u0014:\u001b\u000b\"\u0011=\u0012\u0002?", "oWGqR");
            Options.I[0x30 ^ 0x71] = I("\u0019\u0015\u0017\n/\u0019\n\u001e\u0006*\u001e\u0003\u0017\u001b", "LFRUi");
            Options.I[0x71 ^ 0x33] = I("\u0018\u0019\u0015\u00046\u0019\u001aO\u000b,\u001b\u0005\u0012\u000e+\u0012\f\u000f", "wiamY");
            Options.I[0xD5 ^ 0x96] = I(" \u0016\n4\u0004 \u0007\u001d%\u0011+\u001b", "eXKvH");
            Options.I[0x10 ^ 0x54] = I("\u0002\u0016%\f&\u0002\u00072\u001d3\t\u001b", "GXdNj");
            Options.I[0x43 ^ 0x6] = I("9#\u001e\u001b\t8 D\u0004\u0015/=\t", "VSjrf");
            Options.I[0x45 ^ 0x3] = I("#9\u001f\u001a%4%", "vjZEs");
            Options.I[0xD ^ 0x4A] = I("\u001e4/4.\t(", "Kgjkx");
            Options.I[0x75 ^ 0x3D] = I(")\u0007\u0017,\f(\u0004M3\u0001)", "FwcEc");
            Options.I[0x43 ^ 0xA] = I("#>\u001b\u0019!$2\u001c\u001f,9", "wqNZi");
            Options.I[0xDA ^ 0x90] = I("$\f\u001f\u0013\u0002#\u0000\u0018\u0015\u000f>", "pCJPJ");
            Options.I[0x68 ^ 0x23] = I("\u0007=\u001c;\b\u0006>F&\b\u001d.\u0000!\u0004\u001a(\r<", "hMhRg");
            Options.I[0xA ^ 0x46] = I("+-\u000b\u000e6;&\u000b\u0016,", "heJZi");
            Options.I[0x33 ^ 0x7E] = I("\u001a\t\u00143\t\n\u0002\u0014+\u0013", "YAUgV");
            Options.I[0xF6 ^ 0xB8] = I("\u0002\u0014\u00129.\u0003\u0017H3)\f\u0010H#\"\f\b\u0003", "mdfPA");
            Options.I[0x71 ^ 0x3E] = I("2 \u0002\u0012\u0012&!\u0007\u0012\u0005", "qhCFM");
            Options.I[0xDD ^ 0x8D] = I("\u00191\r%4\r0\b%#", "ZyLqk");
            Options.I[0xCA ^ 0x9B] = I("*\n\"\u000f\u001a+\tx\u0005\u001d$\u000ex\u0011\u001c!\u000e>", "EzVfu");
            Options.I[0xFB ^ 0xA9] = I("\u0004\u0001+:\b\u000f\f#)\u001f\u0013\u0016,!\u0014\u0012\u001a/*", "GIjnW");
            Options.I[0x5E ^ 0xD] = I("\u00042\u0006=\b\u000f?\u000e.\u001f\u0013%\u0001&\u0014\u0012)\u0002-", "GzGiW");
            Options.I[0x40 ^ 0x14] = I(":'#08;$y:?4#y12<0?-y384,$03", "UWWYW");
            Options.I[0xD1 ^ 0x84] = I("\u0014!\u00185\u001c\u001f,\u0010&\u000b\u00036\f/\u0005\u0018*\f2\u0006\u0013", "WiYaC");
            Options.I[0x4D ^ 0x1B] = I("'\u0010\u00181\u0010,\u001d\u0010\"\u00070\u0007\f+\t+\u001b\f6\n ", "dXYeO");
            Options.I[0x68 ^ 0x3F] = I("<\u00176\u000e&=\u0014l\u0004!2\u0013l\u000f,:\u0000*\u0013g&\t$\b*&\u0014'\u0003", "SgBgI");
            Options.I[0x6 ^ 0x5E] = I("\u0002.\u0001\u001f3\u001f8\u001d\u0017$\n+\u0002", "OgQRr");
            Options.I[0x27 ^ 0x7E] = I("\u001f\u0019\u0016\t%\u0002\u000f\n\u00012\u0017\u001c\u0015", "RPFDd");
            Options.I[0x2F ^ 0x75] = I("8(>\u001a,9+d\u001e*'5+\u0003\u000f2./\u001f0", "WXJsC");
            Options.I[0x76 ^ 0x2D] = I("3\"9( *8%\"&:).4#:#?", "umkke");
            Options.I[0x20 ^ 0x7C] = I("+\u000b%\u0016\u00102\u00119\u001c\u0016\"\u00002\n\u0013\"\n#", "mDwUU");
            Options.I[0x1E ^ 0x43] = I("\u0006\u001b9+\r\u0007\u0018c$\r\u001b\b(\u0017\f\u0000\b\"&\u0007/\u0004#6", "ikMBb");
            Options.I[0x44 ^ 0x1A] = I(" \u00011\u00023>\n!\u001e&6\u0006<\u00177!\n3\u000e*6\u0019", "sUcGr");
            Options.I[0x5E ^ 0x1] = I("#\u0012\u001b*\u0015=\u0019\u000b6\u00005\u0015\u0016?\u0011\"\u0019\u0019&\f5\n", "pFIoT");
            Options.I[0x39 ^ 0x59] = I(".\u00051%\u000b/\u0006k?\u00103\u0010$!J#\f1)\u0017\u0011\u00107\u001c\r9\u0010)", "AuELd");
            Options.I[0x3F ^ 0x5E] = I("\u0000=!\u001d\f\u001e6%\u0017\u0001\u0006$6\u0007\u0000\u001a*", "SisXM");
            Options.I[0xF2 ^ 0x90] = I("7\u0011\u0019\u000e\u0018)\u001a\u001d\u0004\u00151\b\u000e\u0014\u0014-\u0006", "dEKKY");
            Options.I[0x54 ^ 0x37] = I(".'\r\u000f?/$W\u0015$32\u0018\u000b~,>\u001a0?-\"\u0014\b5", "AWyfP");
            Options.I[0xEA ^ 0x8E] = I("+\u001e\n1\u00075\u0015\u000e;\n-\u0007\u001d+\u0015!\u0019\f1\u000b", "xJXtF");
            Options.I[0x54 ^ 0x31] = I("+\u00004&\u001b5\u000b0,\u0016-\u0019#<\t!\u00072&\u0017", "xTfcZ");
            Options.I[0x4B ^ 0x2D] = I("6\u0017!\u0011-7\u0014{\u000b6+\u00024\u0015l*\u001e&\f'41:\u001474\u0002", "YgUxB");
            Options.I[0xA ^ 0x6D] = I("\t\"\u001c\u0010 \u0017)\u0005\u00171\t", "ZvNUa");
            Options.I[0x72 ^ 0x1A] = I("4.*0\u0013*%37\u00024", "gzxuR");
            Options.I[0x7A ^ 0x13] = I("\u001f&\u0000\u0000-\u001e%Z\u001a6\u00023\u0015\u0004l\u001b4\u0004\u001a", "pVtiB");
            Options.I[0x3C ^ 0x56] = I("\u001423\t&\n9'\u001c4", "GfaLg");
            Options.I[0xCC ^ 0xA7] = I("\u0012#\u0018\b0\f(\f\u001d\"", "AwJMq");
            Options.I[0x46 ^ 0x2A] = I("\u0004'\u0013\u00005\u0005$I\u001a.\u00192\u0006\u0004t\r'\u0014", "kWgiZ");
            Options.I[0x8 ^ 0x65] = I("\u0018\u0010\u001a*\u0005\u0006\u001b\u000b \t\u001b\u0016\r<\u0017\u0002\u000b\u0006", "KDHoD");
            Options.I[0x6B ^ 0x5] = I("\"\f\u0016\u0007\f<\u0007\u0007\r\u0000!\n\u0001\u0011\u001e8\u0017\n", "qXDBM");
            Options.I[0x24 ^ 0x4B] = I("*\u0002,;\u0015+\u0001v!\u000e7\u00179?T&\u001d5\"\b \u0001+;\u0015+", "ErXRz");
            Options.I[0x1 ^ 0x71] = I("\u0003\u0011\"7*\u001d\u001a#7%\u0014\u001a=7?\u0011\u00011&*", "PEprk");
            Options.I[0x1A ^ 0x6B] = I("\u0011\u00016\u0016\u0016\u000f\n7\u0016\u0019\u0006\n)\u0016\u0003\u0003\u0011%\u0007\u0016", "BUdSW");
            Options.I[0xF4 ^ 0x86] = I("\u001e)\u0015\u0004.\u001f*O\u001e5\u0003<\u0000\u0000o\u0002<\u000f\t\f\u0014-\u0000\t \u00058", "qYamA");
            Options.I[0xF8 ^ 0x8B] = I("4$\b\u0000\f*/\u0019\r\f3/\u001f\u000b\f%<\u001f\u0001", "gpZEM");
            Options.I[0x38 ^ 0x4C] = I("5>\u001f\u00040+5\u000e\t025\b\u000f0$&\b\u0005", "fjMAq");
            Options.I[0x5D ^ 0x28] = I(")\u001a\u0005\u0019\u0004(\u0019_\u0003\u001f4\u000f\u0010\u001dE%\u0002\u0010\u0004E#\u0004\u0010\u0012\u0007#\u000e", "Fjqpk");
            Options.I[0xD8 ^ 0xAE] = I("?\u000e\u001e\r\u0003!\u0005\u000f\u0000\u00038\u0005\u0019\u001b\u0007>\u0005\n\u0001\u000e8\u001f\u001e", "lZLHB");
            Options.I[0x3F ^ 0x48] = I("\u0015\u0003\u0017(;\u000b\b\u0006%;\u0012\b\u0010>?\u0014\b\u0003$6\u0012\u0012\u0017", "FWEmz");
            Options.I[0x2D ^ 0x55] = I(".\u001a2\u0006\u000b/\u0019h\u001c\u00103\u000f'\u0002J\"\u0002'\u001bJ4\u0019#\u001d\"(\u00062\n\u0016", "AjFod");
            Options.I[0xF8 ^ 0x81] = I("*\u0010\u001f\u000f\u00134\u001b\u0000\u0003\u0011&\u0010\u0002\r\u00155\u0001\u0012\b\u00171\u0005\u001b\u0003\u001d+", "yDMJR");
            Options.I[0xE3 ^ 0x99] = I("=\u0001>\u000b\u0000#\n!\u0007\u00021\u0001#\t\u0006\"\u00103\f\u0004&\u0014:\u0007\u000e<", "nUlNA");
            Options.I[0xC8 ^ 0xB3] = I("&?\u0000(5'<Z2.;*\u0015,t$&\u0017\u00155.(\u0018$\u0018,'\u001573&=", "IOtAZ");
            Options.I[0x6D ^ 0x11] = I("4\r\u001a\u0012\u0005)\u0000\u0019\u0005\u000b$\u000f\u0014\u0005\u0007 \u0004\u0006", "vAUQN");
            Options.I[0xD9 ^ 0xA4] = I("\u0015+\u001a$!\b&\u00193/\u0005)\u00143#\u0001\"\u0006", "WgUgj");
            Options.I[0x68 ^ 0x16] = I("##\u001b\"\u0002\" A)\u0001#0\u0004\n\u000186\u001d%\f8:\u0019.\u001e", "LSoKm");
            Options.I[39 + 26 - 12 + 74] = I(";)7$\u0017,(,5\u0011+94.\u001d'*<", "ilsqT");
            Options.I[120 + 98 - 162 + 72] = I("\u0018\u0015#\u00113\u000f\u00148\u00005\b\u0005 \u001b9\u0004\u0016(", "JPgDp");
            Options.I[27 + 10 + 74 + 18] = I("\u0002\u0007\u0007!7\u0003\u0004]:=\t\u0002\u0010-<)\u0012\u0011=?$\u0019\u0015'", "mwsHX");
            Options.I[122 + 25 - 120 + 103] = I("\u0013\u0006\f.\u0019\u000f\u0017\u000b/\f\u0012\u0007\u000f4", "VHXgM");
            Options.I[69 + 114 - 114 + 62] = I("\u0014\u001b\u0001.3\b\n\u0006/&\u0015\u001a\u00024", "QUUgg");
            Options.I[90 + 36 - 83 + 89] = I("77\u0016\u0013\u001564L\u001f\u0014,.\u0016\u0003)0&\u0006\u0015\r+", "XGbzz");
            Options.I[13 + 99 - 35 + 56] = I("\u0004(\t1\u0011\u0003)\r7", "BgNnW");
            Options.I[123 + 125 - 136 + 22] = I("\u0000(#", "Fgdzv");
            Options.I[134 + 78 - 96 + 19] = I("\u001c\u001d-", "ZrJIE");
            Options.I[0 + 64 - 46 + 118] = I("\u0005\u001e\u00057!\u0017\u0010\u0010<", "CQBhr");
            Options.I[9 + 42 + 72 + 14] = I("", "PWHGP");
            Options.I[58 + 54 - 80 + 106] = I("\u0013%\u0016c\u0010!+\u00037", "UJqCC");
            Options.I[92 + 130 - 201 + 118] = I("8\u0013$\u0019#%\u0005 \r20", "uZtTb");
            Options.I[102 + 20 + 6 + 12] = I("", "Wfzrl");
            Options.I[38 + 127 - 44 + 20] = I("\u001f0\u001d\u0002\u0010\"y9\u0016\u00017", "RYmoq");
            Options.I[9 + 49 + 47 + 37] = I("*\u000e\u0006\u001a\r1\u001c\u000f\u0005\n", "yCIUY");
            Options.I[121 + 59 - 58 + 21] = I("", "cTWiE");
            Options.I[63 + 107 - 147 + 121] = I("7\u0005&\r6\fH\u000f2\u0011", "dhIbB");
            Options.I[67 + 114 - 153 + 117] = I("\"*\u00180\u000e2", "afWeJ");
            Options.I[135 + 1 - 123 + 133] = I("", "zXPYN");
            Options.I[131 + 116 - 159 + 59] = I("\t\u0014\u00004\u00159", "JxoAq");
            Options.I[57 + 99 - 110 + 102] = I("-\u001e=\u000771\u001a7\u001b4&\u0006", "nRrRs");
            Options.I[102 + 2 - 7 + 52] = I("", "OuiOH");
            Options.I[106 + 61 - 118 + 101] = I("\u000b\u0018(1%h<\"-& \u0000", "HtGDA");
            Options.I[36 + 88 - 80 + 107] = I("\u000364(\n", "WdqmY");
            Options.I[103 + 111 - 105 + 43] = I("", "qrsBJ");
            Options.I[17 + 132 - 100 + 104] = I("\u0018\u0018\",\u0017", "LjGId");
            Options.I[112 + 85 - 167 + 124] = I("\u00195\u0013$", "KtZjq");
            Options.I[1 + 149 - 46 + 51] = I("", "fFHcI");
            Options.I[113 + 119 - 126 + 50] = I("\u00174\u0010\u000bvcu*\u000b92", "EUyeV");
            Options.I[27 + 126 - 118 + 122] = I("\u0018\u000b\n=8\r\u0000\u0007/.\u0018\u0011\u0006\"", "YECpy");
            Options.I[114 + 45 - 123 + 122] = I("", "ZDtJb");
            Options.I[129 + 141 - 229 + 118] = I("\u0015\u0019\u001a\n\u0006b9\u0000\u0006\u0019#\f\u000b\u000b", "Bxnot");
            Options.I[2 + 158 - 104 + 104] = I("\u00149\u0003'%\u00012\u000e5(\u0014!\u000b", "UwJjd");
            Options.I[132 + 69 - 60 + 20] = I("", "jpErL");
            Options.I[97 + 83 - 149 + 131] = I("\u00151\u001b4M\u0018>\u00048\f-5\t", "YPmUm");
            Options.I[31 + 30 + 31 + 71] = I("\u0000 \u001e*\u0011\u0015+\u00138\u0016\b<\u0012", "AnWgP");
            Options.I[137 + 121 - 210 + 116] = I("", "WjGga");
            Options.I[105 + 78 - 49 + 31] = I(")\u001c\u0000\"G.\u001b\u001b*\u0006\u001b\u0010\u0016", "ourGg");
            Options.I[132 + 148 - 141 + 27] = I(")\u0016\f%/<\u001d\u00017>'\n\u0011)\"", "hXEhn");
            Options.I[85 + 12 - 65 + 135] = I("", "IrhRV");
            Options.I[20 + 69 + 35 + 44] = I("%8?.\u0007\u0019w\f4\u000f\u001869?\u0002", "uWMZf");
            Options.I[34 + 123 - 47 + 59] = I("\b\t\u000b\u0003\r\u001f\u0003\u0018", "IFTOH");
            Options.I[38 + 6 + 34 + 92] = I("", "FhLgq");
            Options.I[127 + 29 - 13 + 28] = I(":\u000b\u001c?\u000e\u0001F?9\u001d\u0001\u0012\u001a>\u001dI*\u0016&\u001f\u0005", "ifsPz");
            Options.I[1 + 138 - 69 + 102] = I("\r\r\u001d\u001b7\u0004\u0018\u001f\u0006", "ALZTz");
            Options.I[90 + 158 - 111 + 36] = I("", "MzQIS");
            Options.I[121 + 9 + 34 + 10] = I("9\b5:*\u0010\u001d7'", "uiRUG");
            Options.I[52 + 112 + 7 + 4] = I("0*\u001b\r\u001c%2\u0007", "cbTZC");
            Options.I[122 + 68 - 31 + 17] = I("", "WUywN");
            Options.I[173 + 100 - 190 + 94] = I("\u0001=\u001a\u001aq\u0014\u0005&", "RUumQ");
            Options.I[76 + 172 - 225 + 155] = I("1-'!\t1.61\u000e9;8=", "pxsnZ");
            Options.I[127 + 85 - 38 + 5] = I("", "GqoCb");
            Options.I[56 + 144 - 117 + 97] = I("'\u001d\u001a()\u0007\u001e\u000b", "fhnGZ");
            Options.I[11 + 123 + 43 + 4] = I("\u000f\u0000\u00176=\u001f\u001a\u000409\u001e\u0016", "MECbx");
            Options.I[42 + 113 - 110 + 137] = I("", "WVZFP");
            Options.I[156 + 54 - 200 + 173] = I("6-\u00196\u001f\u0006h*0\u001b\u0007;", "tHmBz");
            Options.I[52 + 132 - 173 + 173] = I("\u000b\u0018&\u0003)\u001e\u0013+\u0011:\u000f\u0012<\u001a'\u0004\u0013", "JVoNh");
            Options.I[129 + 108 - 182 + 130] = I("", "klHyG");
            Options.I[44 + 84 - 122 + 180] = I("\u00017*<\u0004<<+o1=;#.\u000466", "SRNOp");
            Options.I[5 + 19 + 163 + 0] = I("\u000b\u0006\"\u001e5\u001e\r/\f1\u0012\u0018'\u001c'\u0003\u0007%", "JHkSt");
            Options.I[91 + 180 - 259 + 176] = I("", "HeZFa");
            Options.I[64 + 82 + 43 + 0] = I("\u001f!\t6\u0004)0\u00164K\u001b7\u00107\n.<\u001d", "ZYyZk");
            Options.I[41 + 93 - 39 + 95] = I("\u000f'\u0002/.\u001a,\u000f=)\u0002(\u0006'", "NiKbo");
            Options.I[90 + 54 - 85 + 132] = I("", "rbrtT");
            Options.I[77 + 144 - 180 + 151] = I("\f#\u0011=\u0001j\u000e\u001e9\t+;\u00154", "JOpPd");
            Options.I[85 + 149 - 138 + 97] = I("-\u0016=\u0003\n8\u001d0\u0011\u0018!\u0017?\u000b", "lXtNK");
            Options.I[28 + 94 - 41 + 113] = I("", "GkOfW");
            Options.I[6 + 6 + 168 + 15] = I("6<5\u000f$E\u00104\r,\u0004%?\u0000", "eQZdA");
            Options.I[174 + 78 - 70 + 14] = I("0,\t5\u0007\";", "giHaO");
            Options.I[173 + 31 - 84 + 77] = I("", "qBPnW");
            Options.I[17 + 164 - 23 + 40] = I("4*(\u001f\u0011\u0006=", "cOIky");
            Options.I[103 + 1 - 1 + 96] = I("\u001f%\u0015", "LnLDD");
            Options.I[48 + 45 + 81 + 26] = I("", "gKNew");
            Options.I[117 + 153 - 80 + 11] = I("\u0004\b\u000f", "WcvZN");
            Options.I[199 + 197 - 361 + 167] = I("6:;\u0018\u0016", "enzJE");
            Options.I[180 + 142 - 308 + 189] = I("", "sNywd");
            Options.I[20 + 149 - 52 + 87] = I("\t\u000e9\u0014\u0011", "ZzXfb");
            Options.I[101 + 99 - 75 + 80] = I("\u00179\r(+\u000b#\r", "DlCwf");
            Options.I[149 + 177 - 233 + 113] = I("", "iBIqc");
            Options.I[53 + 77 - 0 + 77] = I("\u0001?\u000bj|r\u0007\n%4", "RJeJZ");
            Options.I[132 + 19 - 53 + 110] = I("\u00029\n9\u0006\u0000$\b", "TpMwC");
            Options.I[158 + 149 - 220 + 122] = I("", "vwcnS");
            Options.I[147 + 27 - 97 + 133] = I("\u0017*\u0014\u000b\t57\u0016", "ACsel");
            Options.I[111 + 149 - 58 + 9] = I("\b)\"?8\u00144'52\u001f$$", "Kawqs");
            Options.I[50 + 44 + 80 + 38] = I("", "TZjrx");
            Options.I[106 + 44 + 38 + 25] = I("4\u00110\u001f:W,5\u00150\u0003\u001c6", "wyEqQ");
            Options.I[150 + 127 - 167 + 104] = I(".2\u0010>\u00052/\u00154\u000f9?\u0016/\n44\u0004=\u0007.", "mzEpN");
            Options.I[15 + 180 - 41 + 61] = I("", "xiqel");
            Options.I[200 + 98 - 207 + 125] = I("/\u001e\u0005\n<\u0002\u0004K>!\u000f\u0006\u001f\u000e\"", "kgkkQ");
            Options.I[194 + 148 - 178 + 53] = I("\u0012;>\"", "FrsgH");
            Options.I[102 + 40 - 8 + 84] = I("", "laVeA");
            Options.I[29 + 38 + 54 + 98] = I("79+\u001c", "cPFym");
            Options.I[119 + 111 - 84 + 74] = I("55(\n\u0019).,\u001f\u000e$", "vymKK");
            Options.I[204 + 5 - 138 + 150] = I("", "MTHXV");
            Options.I[80 + 38 + 69 + 35] = I("9(\u0016\b=Z\u0013\u0012\u001d*\b", "zDsiO");
            Options.I[39 + 29 - 39 + 194] = I("4\u000f\u0004;\u000e/\u001d\u001c;\b+\u0006", "gBKtZ");
            Options.I[166 + 190 - 238 + 106] = I("", "rYeaZ");
            Options.I[46 + 213 - 142 + 108] = I("#\u001c=\n\u0004\u0018Q\u0005\n\u0002\u001c\u0015", "pqRep");
            Options.I[29 + 31 + 31 + 135] = I("8\u0001\u0002\u001c\u000b>\u000f\u0019\f\u001d-\u0002\u000e\u000b", "nNKXT");
            Options.I[93 + 137 - 20 + 17] = I("", "BGcJj");
            Options.I[60 + 104 + 8 + 56] = I(" )\u000b\u0012R&'\u0010\u0002\u001b\u0015*\u0007\u0005", "vFbvr");
            Options.I[188 + 200 - 218 + 59] = I("09\u0007\u001c\u001f8(\u0012\u000b\u0019.;\u001f\u001c\u001e", "gxSYM");
            Options.I[171 + 131 - 238 + 166] = I("", "WAmHq");
            Options.I[119 + 142 - 166 + 136] = I("\u00114\u00054<f\u0005\u0010#:/6\u001d4=", "FUqQN");
            Options.I[202 + 124 - 224 + 130] = I("?\u0014\n=\u0018>\u0005\u000f2\u0014%", "mUCsG");
            Options.I[142 + 80 - 57 + 68] = I("", "bmdgL");
            Options.I[38 + 86 + 96 + 14] = I("\u0006\u0003;\u0005n\u0007\u0012>\n=<", "TbRkN");
            Options.I[4 + 55 + 4 + 172] = I("(\u0002%5\u00054\u0012' \u0016,\u00044-\u0001+", "xMwaD");
            Options.I[96 + 107 + 11 + 22] = I("", "BojNs");
            Options.I[17 + 108 + 66 + 46] = I("6;+\u0010\r\nt\t\u0005\u001e\u0012=:\b\t\u0015", "fTYdl");
            Options.I[226 + 83 - 259 + 188] = I("?\u001d\u0013= !\r\u00175=;\u001b\u00048*<", "oRGto");
            Options.I[58 + 43 - 56 + 194] = I("", "PQLyF");
            Options.I[202 + 147 - 315 + 206] = I("\u0011)\u001c',/f8/15/\u000b\"&2", "AFhNC");
            Options.I[70 + 177 - 169 + 163] = I("\u0013(\u001f\u00130\u001a3\u0006\t7\u00143\u0019\u001f$\u0019$\u001e", "UaMVg");
            Options.I[134 + 193 - 284 + 199] = I("", "vhnoU");
            Options.I[242 + 67 - 281 + 215] = I("6\u0010\u001a)\u001f\u001f\u000b\u0003l8\u0011\u000b\u001c%\u000b\u001c\u001c\u001b", "pyhLh");
            Options.I[83 + 136 - 215 + 240] = I("\u00110\u000b\".\r'\u0016", "AbDdg");
            Options.I[56 + 115 - 57 + 131] = I("", "Oiqqy");
            Options.I[145 + 60 - 59 + 100] = I("\r\u00145$.i!%>/ \u001d2#", "IqWQI");
            Options.I[71 + 46 - 88 + 218] = I("*\u0007=\u001b\u0001'\u001b3\u0014\u0006/\u00011\u0019\u000e\"\u0014\"\n", "nUtKQ");
            Options.I[214 + 60 - 47 + 21] = I("", "kpipC");
            Options.I[67 + 189 - 74 + 67] = I("\u000b8\u0002\"6&$\fr\u0011.>\u000e i\u0003+\u001d3", "OJkRF");
            Options.I[17 + 180 + 47 + 6] = I(".\u0006\u001a&\u0014>\u001c\u001d<\u001e;", "lCNrQ");
            Options.I[237 + 147 - 178 + 45] = I("", "ISdeY");
            Options.I[54 + 60 + 5 + 133] = I("50!=2\u0005u\u0006'8\u0000", "wUUIW");
            Options.I[16 + 218 - 178 + 197] = I("\u0001$\u00079#\u0004#\u000e0>\u0018<\u000415", "GqKup");
            Options.I[94 + 112 - 145 + 193] = I("", "QHXfu");
            Options.I[42 + 56 + 83 + 74] = I("!\u0004\r\u0014\u0003\u0004\u0003\u0004\u001d\u001eG<\u000e\u001c\u0015", "gqaxp");
            Options.I[253 + 186 - 301 + 118] = I("+\n\u000e!\u0012>\u0001\u00033\u0007/\u0016\u0015-\u001a$", "jDGlS");
            Options.I[158 + 151 - 134 + 82] = I("", "BdSVG");
            Options.I[233 + 242 - 240 + 23] = I("5)\u001d<\u0015\b\"O\u000f\u001a\b!\u000e:\u0011\u0005", "aLoNt");
            Options.I[253 + 129 - 135 + 12] = I("\"16\u001b\u0018.%8\u001a\u0007#5", "qfwVH");
            Options.I[223 + 160 - 233 + 110] = I("", "BOlmQ");
            Options.I[111 + 168 - 61 + 43] = I("*\u000e0$\u0019Y:>%\u0006\u000b\n", "yyQIi");
            Options.I[94 + 140 - 170 + 198] = I("\u001f)\u0018\u0010\u0002\u00007\u001b\u001b\u000f\u001e", "MhVTM");
            Options.I[123 + 14 + 115 + 11] = I("", "YMVrM");
            Options.I[164 + 133 - 179 + 146] = I("\u0014\u001b\u001a\u0001\u0007+Z9\n\n5", "Fzteh");
            Options.I[26 + 188 - 102 + 153] = I("\u0014\f\u001a\u001f5\u000f\u001e\u0017\u0019.\n\u0004\u0006", "GAUPa");
            Options.I[247 + 4 - 221 + 236] = I("", "wJkTd");
            Options.I[192 + 238 - 264 + 101] = I(" \u001e\u001b\u0017,\u001bS6\u00117\u001e\u0016\u0007", "sstxX");
            Options.I[192 + 15 - 7 + 68] = I("\u0006\u001c\u0012&%\b\u0016\u0007=$\u0011\u001a", "EIArj");
            Options.I[124 + 38 - 80 + 187] = I("", "AACNA");
            Options.I[184 + 231 - 203 + 58] = I("\u00112#\u0015'?g\u0016\u000e&&4", "RGPaH");
            Options.I[144 + 153 - 146 + 120] = I("\t\"\u00140\u000b\u0007(\u0004+\b\u0005%\u0014", "JwGdD");
            Options.I[152 + 241 - 388 + 267] = I("", "IAnTw");
            Options.I[213 + 60 - 254 + 254] = I("+\u0013\n,\u0016\u0005F:7\u0015\u0007\u0014\n", "hfyXy");
            Options.I[123 + 101 - 189 + 239] = I("\u00162\t-1\u0006;\u0016?=", "EzFzn");
            Options.I[235 + 168 - 186 + 58] = I("", "stDnO");
            Options.I[256 + 267 - 475 + 228] = I("$\u0004\"3X4\r=!\u000b", "wlMDx");
            Options.I[61 + 137 - 97 + 176] = I(" ;-4\u0013  &>\t71;.\u0003110", "ctczV");
            Options.I[37 + 217 - 249 + 273] = I("", "PPsFW");
            Options.I[53 + 245 - 97 + 78] = I("):=7)\t!6=l>0+-9\u00180 ", "jUSYL");
            Options.I[107 + 103 - 189 + 259] = I("%;\u001e5?2?\r", "dzAyz");
            Options.I[21 + 36 + 167 + 57] = I("", "glbTE");
            Options.I[183 + 44 - 97 + 152] = I("6-\u0011\r\n\u001b*\u0004\u0017\u0002\u0019$", "wCedk");
            Options.I[4 + 110 + 138 + 31] = I("\u0005%\u0015(3\u0012&\u0006", "DcJdv");
            Options.I[55 + 152 - 4 + 81] = I("", "uQzzJ");
            Options.I[205 + 263 - 370 + 187] = I("+-\n\u001b\"\u001e1\f\u0018$\tc%\u0001!\u001e&\u0011\u0001#\r", "jCchM");
            Options.I[257 + 99 - 116 + 46] = I("\u0019\u000f !,\f\u0004-39\u001d\u0019=9?\u001d\u0012", "XAilm");
            Options.I[10 + 96 + 70 + 111] = I("", "mDZkd");
            Options.I[49 + 221 - 58 + 76] = I("\u0005*46\"#*?b\u0016?&!##4+", "QOLBW");
            Options.I[105 + 22 + 56 + 106] = I("\r\u0004\u00187\u0010\u0002\t\u00136\u0007\u001b\u0011\u00190\u0007\u0010", "CELbB");
            Options.I[133 + 102 - 34 + 89] = I("", "JGMNd");
            Options.I[129 + 219 - 309 + 252] = I(",\u0006\u0003\u0011<\u0003\u000bW0+\u001a\u0013\u0002\u0016+\u0011", "bgwdN");
            Options.I[144 + 141 - 236 + 243] = I("7\u0000\u00079\f+\u0004\u001d6\u0003=\u0006\u0015", "tHRwG");
            Options.I[0 + 55 + 52 + 186] = I("", "Hcpgk");
            Options.I[200 + 171 - 83 + 6] = I("6/66>U\u000b,91\u001c)$", "uGCXU");
            Options.I[76 + 209 - 23 + 33] = I("\u001f\u001f-\u001e-\u001e\u000e$\u0017-\u0003\u0015.\u0016&\u001e\n2", "WZaZr");
            Options.I[91 + 269 - 340 + 276] = I("", "kUDdN");
            Options.I[93 + 93 + 19 + 92] = I("*4\u000b2m+%\u0002;m6>\b:9\u000b!\u0014", "bQgVM");
            Options.I[204 + 118 - 175 + 151] = I("(\u001f\u0017\u00165)\t\u0007\u000f1)\u0000\u000b", "lMXFe");
            Options.I[115 + 206 - 209 + 187] = I("", "tRnAN");
            Options.I[5 + 2 + 233 + 60] = I("=\n'\u001f=\u001c\u001ch&9\u001c\u0015;", "yxHoM");
            Options.I[163 + 268 - 329 + 199] = I("\u000e\t,\u0012\u000e\u0001\u0000#\u0005\u001a\u001d\u00049\n\u0015\u000b\u00061", "BHvKQ");
            Options.I[207 + 201 - 173 + 67] = I("", "HWytl");
            Options.I[220 + 36 - 252 + 299] = I("$\f2\tI+\u0005=\u001e\u0002H!'\u0011\r\u0001\u0003/", "hmHpi");
            Options.I[218 + 24 - 69 + 131] = I(",9\u001d\u0011$\"3\u001d\u000e2", "olNEk");
            Options.I[261 + 223 - 212 + 33] = I("", "AWjCz");
            Options.I[183 + 90 - 92 + 125] = I("'\u0012\u0002\u0019!\tG\"\u00067", "dgqmN");
            Options.I[91 + 155 + 22 + 39] = I("/5\u0005\u0002\u000b$5\u0002\u001e", "itVVT");
            Options.I[280 + 109 - 291 + 210] = I("", "Jplho");
            Options.I[2 + 31 + 212 + 64] = I("\"&\u0010\u0015F)&\u0017\t", "dGcaf");
            Options.I[192 + 284 - 243 + 77] = I("\u00036\u0005\u001a\u0019\u00172\u0018\n\u0003\u0017", "EwVNF");
            Options.I[294 + 296 - 422 + 143] = I("", "NOrov");
            Options.I[68 + 32 - 86 + 298] = I("0\u0006+\u001ch$\u00026\f-\u0004", "vgXhH");
            Options.I[246 + 245 - 462 + 284] = I("<\u0015\u0006\u0005\u0003$\u0012\u0004\u000e\u001e<\u0018\u0005\u0007\u001f+\f\u0014", "hGGKP");
            Options.I[115 + 179 - 184 + 204] = I("", "sOjMc");
            Options.I[82 + 219 - 89 + 103] = I(">\u00018&;\u0006\u0006:-&\u001eS\u001b$'\t\u0018*", "jsYHH");
        }
        
        public static Options getEnumOptions(final int n) {
            final Options[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i < length) {
                final Options options = values[i];
                if (options.returnEnumOrdinal() == n) {
                    return options;
                }
                ++i;
            }
            return null;
        }
        
        private Options(final String s, final int n, final String s2, final int n2, final String enumString, final boolean enumFloat, final boolean enumBoolean, final float valueMin, final float valueMax, final float valueStep) {
            this.enumString = enumString;
            this.enumFloat = enumFloat;
            this.enumBoolean = enumBoolean;
            this.valueMin = valueMin;
            this.valueMax = valueMax;
            this.valueStep = valueStep;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public float normalizeValue(final float n) {
            return MathHelper.clamp_float((this.snapToStepClamp(n) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
        }
        
        public boolean getEnumBoolean() {
            return this.enumBoolean;
        }
    }
}
