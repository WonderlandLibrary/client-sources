/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.client.settings.ToggleableKeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.client.CClientSettingsPacket;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.HandSide;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Difficulty;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.config.FloatOptions;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.util.FontUtils;
import net.optifine.util.KeyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    private static final TypeToken<List<String>> TYPE_LIST_STRING = new TypeToken<List<String>>(){};
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on(':').limit(2);
    public double mouseSensitivity = 0.5;
    public int renderDistanceChunks = -1;
    public float entityDistanceScaling = 1.0f;
    public int framerateLimit = 120;
    public CloudOption cloudOption = CloudOption.FANCY;
    public GraphicsFanciness graphicFanciness = GraphicsFanciness.FANCY;
    public AmbientOcclusionStatus ambientOcclusionStatus = AmbientOcclusionStatus.MAX;
    public List<String> resourcePacks = Lists.newArrayList();
    public List<String> incompatibleResourcePacks = Lists.newArrayList();
    public ChatVisibility chatVisibility = ChatVisibility.FULL;
    public double chatOpacity = 1.0;
    public double chatLineSpacing = 0.0;
    public double accessibilityTextBackgroundOpacity = 0.5;
    @Nullable
    public String fullscreenResolution;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus = true;
    private final Set<PlayerModelPart> setModelParts = Sets.newHashSet(PlayerModelPart.values());
    public HandSide mainHand = HandSide.RIGHT;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips = true;
    public double chatScale = 1.0;
    public double chatWidth = 1.0;
    public double chatHeightUnfocused = 0.44366195797920227;
    public double chatHeightFocused = 1.0;
    public double chatDelay = 0.0;
    public int mipmapLevels = 4;
    private final Map<SoundCategory, Float> soundLevels = Maps.newEnumMap(SoundCategory.class);
    public boolean useNativeTransport = true;
    public AttackIndicatorStatus attackIndicator = AttackIndicatorStatus.CROSSHAIR;
    public TutorialSteps tutorialStep = TutorialSteps.MOVEMENT;
    public boolean field_244601_E = false;
    public int biomeBlendRadius = 2;
    public double mouseWheelSensitivity = 1.0;
    public boolean rawMouseInput = true;
    public int glDebugVerbosity = 1;
    public boolean autoJump = true;
    public boolean autoSuggestCommands = true;
    public boolean chatColor = true;
    public boolean chatLinks = true;
    public boolean chatLinksPrompt = true;
    public boolean vsync = true;
    public boolean entityShadows = true;
    public boolean forceUnicodeFont;
    public boolean invertMouse;
    public boolean discreteMouseScroll;
    public boolean realmsNotifications = true;
    public boolean reducedDebugInfo;
    public boolean snooper = true;
    public boolean showSubtitles;
    public boolean accessibilityTextBackground = true;
    public boolean touchscreen;
    public boolean fullscreen;
    public boolean viewBobbing = true;
    public boolean toggleCrouch;
    public boolean toggleSprint;
    public boolean skipMultiplayerWarning;
    public boolean field_244794_ae = true;
    public final KeyBinding keyBindForward = new KeyBinding("key.forward", 87, "key.categories.movement");
    public final KeyBinding keyBindLeft = new KeyBinding("key.left", 65, "key.categories.movement");
    public final KeyBinding keyBindBack = new KeyBinding("key.back", 83, "key.categories.movement");
    public final KeyBinding keyBindRight = new KeyBinding("key.right", 68, "key.categories.movement");
    public final KeyBinding keyBindJump = new KeyBinding("key.jump", 32, "key.categories.movement");
    public final KeyBinding keyBindSneak = new ToggleableKeyBinding("key.sneak", 340, "key.categories.movement", this::lambda$new$0);
    public final KeyBinding keyBindSprint = new ToggleableKeyBinding("key.sprint", 341, "key.categories.movement", this::lambda$new$1);
    public final KeyBinding keyBindInventory = new KeyBinding("key.inventory", 69, "key.categories.inventory");
    public final KeyBinding keyBindSwapHands = new KeyBinding("key.swapOffhand", 70, "key.categories.inventory");
    public final KeyBinding keyBindDrop = new KeyBinding("key.drop", 81, "key.categories.inventory");
    public final KeyBinding keyBindUseItem = new KeyBinding("key.use", InputMappings.Type.MOUSE, 1, "key.categories.gameplay");
    public final KeyBinding keyBindAttack = new KeyBinding("key.attack", InputMappings.Type.MOUSE, 0, "key.categories.gameplay");
    public final KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", InputMappings.Type.MOUSE, 2, "key.categories.gameplay");
    public final KeyBinding keyBindChat = new KeyBinding("key.chat", 84, "key.categories.multiplayer");
    public final KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 258, "key.categories.multiplayer");
    public final KeyBinding keyBindCommand = new KeyBinding("key.command", 47, "key.categories.multiplayer");
    public final KeyBinding field_244602_au = new KeyBinding("key.socialInteractions", 80, "key.categories.multiplayer");
    public final KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 291, "key.categories.misc");
    public final KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 294, "key.categories.misc");
    public final KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", InputMappings.INPUT_INVALID.getKeyCode(), "key.categories.misc");
    public final KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 300, "key.categories.misc");
    public final KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", InputMappings.INPUT_INVALID.getKeyCode(), "key.categories.misc");
    public final KeyBinding keyBindAdvancements = new KeyBinding("key.advancements", 76, "key.categories.misc");
    public final KeyBinding[] keyBindsHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 49, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 50, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 51, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 52, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 53, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 54, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 55, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 56, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 57, "key.categories.inventory")};
    public final KeyBinding keyBindSaveToolbar = new KeyBinding("key.saveToolbarActivator", 67, "key.categories.creative");
    public final KeyBinding keyBindLoadToolbar = new KeyBinding("key.loadToolbarActivator", 88, "key.categories.creative");
    public KeyBinding[] keyBindings = ArrayUtils.addAll(new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.field_244602_au, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines, this.keyBindSwapHands, this.keyBindSaveToolbar, this.keyBindLoadToolbar, this.keyBindAdvancements}, this.keyBindsHotbar);
    protected Minecraft mc;
    private final File optionsFile;
    public Difficulty difficulty = Difficulty.NORMAL;
    public boolean hideGUI;
    private PointOfView pointOfView = PointOfView.FIRST_PERSON;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public boolean showLagometer;
    public String lastServer = "";
    public boolean smoothCamera;
    public double fov = 70.0;
    public float screenEffectScale = 1.0f;
    public float fovScaleEffect = 1.0f;
    public double gamma;
    public int guiScale;
    public ParticleStatus particles = ParticleStatus.ALL;
    public NarratorStatus narrator = NarratorStatus.OFF;
    public String language = "en_us";
    public boolean syncChunkWrites;
    public int ofFogType = 1;
    public float ofFogStart = 0.8f;
    public int ofMipmapType = 0;
    public boolean ofOcclusionFancy = false;
    public boolean ofSmoothFps = false;
    public boolean ofSmoothWorld = Config.isSingleProcessor();
    public boolean ofLazyChunkLoading = Config.isSingleProcessor();
    public boolean ofRenderRegions = false;
    public boolean ofSmartAnimations = false;
    public double ofAoLevel = 1.0;
    public int ofAaLevel = 0;
    public int ofAfLevel = 1;
    public int ofClouds = 0;
    public double ofCloudsHeight = 0.0;
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
    public boolean ofBetterSnow = false;
    public boolean ofSwampColors = true;
    public boolean ofRandomEntities = true;
    public boolean ofCustomFonts = true;
    public boolean ofCustomColors = true;
    public boolean ofCustomSky = true;
    public boolean ofShowCapes = true;
    public int ofConnectedTextures = 2;
    public boolean ofCustomItems = true;
    public boolean ofNaturalTextures = false;
    public boolean ofEmissiveTextures = true;
    public boolean ofFastMath = false;
    public boolean ofFastRender = false;
    public int ofTranslucentBlocks = 0;
    public boolean ofDynamicFov = true;
    public boolean ofAlternateBlocks = true;
    public int ofDynamicLights = 3;
    public boolean ofCustomEntityModels = true;
    public boolean ofCustomGuis = true;
    public boolean ofShowGlErrors = true;
    public int ofScreenshotSize = 1;
    public int ofChatBackground = 0;
    public boolean ofChatShadow = true;
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
    public static final int DEFAULT = 0;
    public static final int FAST = 1;
    public static final int FANCY = 2;
    public static final int OFF = 3;
    public static final int SMART = 4;
    public static final int COMPACT = 5;
    public static final int ANIM_ON = 0;
    public static final int ANIM_GENERATED = 1;
    public static final int ANIM_OFF = 2;
    public static final String DEFAULT_STR = "Default";
    public static final double CHAT_WIDTH_SCALE = 4.0571431;
    private static final int[] OF_TREES_VALUES = new int[]{0, 1, 4, 2};
    private static final int[] OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
    private static final String[] KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
    public KeyBinding ofKeyBindZoom;
    private File optionsFileOF;

    public GameSettings(Minecraft minecraft, File file) {
        this.setForgeKeybindProperties();
        this.mc = minecraft;
        this.optionsFile = new File(file, "options.txt");
        if (minecraft.isJava64bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
            AbstractOption.RENDER_DISTANCE.setMaxValue(32.0f);
            long l = 1000000L;
            if (Runtime.getRuntime().maxMemory() >= 1500L * l) {
                AbstractOption.RENDER_DISTANCE.setMaxValue(48.0f);
            }
            if (Runtime.getRuntime().maxMemory() >= 2500L * l) {
                AbstractOption.RENDER_DISTANCE.setMaxValue(64.0f);
            }
        } else {
            AbstractOption.RENDER_DISTANCE.setMaxValue(16.0f);
        }
        this.renderDistanceChunks = minecraft.isJava64bit() ? 12 : 8;
        this.syncChunkWrites = Util.getOSType() == Util.OS.WINDOWS;
        this.optionsFileOF = new File(file, "optionsof.txt");
        this.framerateLimit = (int)AbstractOption.FRAMERATE_LIMIT.getMaxValue();
        this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 67, "key.categories.misc");
        this.keyBindings = ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
        KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[]{this.ofKeyBindZoom});
        this.renderDistanceChunks = 8;
        this.loadOptions();
        Config.initGameSettings(this);
    }

    public float getTextBackgroundOpacity(float f) {
        return this.accessibilityTextBackground ? f : (float)this.accessibilityTextBackgroundOpacity;
    }

    public int getTextBackgroundColor(float f) {
        return (int)(this.getTextBackgroundOpacity(f) * 255.0f) << 24 & 0xFF000000;
    }

    public int getChatBackgroundColor(int n) {
        return this.accessibilityTextBackground ? n : (int)(this.accessibilityTextBackgroundOpacity * 255.0) << 24 & 0xFF000000;
    }

    public void setKeyBindingCode(KeyBinding keyBinding, InputMappings.Input input) {
        keyBinding.bind(input);
        this.saveOptions();
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            this.soundLevels.clear();
            CompoundNBT compoundNBT = new CompoundNBT();
            try (Object object = Files.newReader(this.optionsFile, Charsets.UTF_8);){
                ((BufferedReader)object).lines().forEach(arg_0 -> GameSettings.lambda$loadOptions$2(compoundNBT, arg_0));
            }
            object = this.dataFix(compoundNBT);
            if (!((CompoundNBT)object).contains("graphicsMode") && ((CompoundNBT)object).contains("fancyGraphics")) {
                this.graphicFanciness = "true".equals(((CompoundNBT)object).getString("fancyGraphics")) ? GraphicsFanciness.FANCY : GraphicsFanciness.FAST;
            }
            for (String string : ((CompoundNBT)object).keySet()) {
                String string2 = ((CompoundNBT)object).getString(string);
                try {
                    if ("autoJump".equals(string)) {
                        AbstractOption.AUTO_JUMP.set(this, string2);
                    }
                    if ("autoSuggestions".equals(string)) {
                        AbstractOption.AUTO_SUGGEST_COMMANDS.set(this, string2);
                    }
                    if ("chatColors".equals(string)) {
                        AbstractOption.CHAT_COLOR.set(this, string2);
                    }
                    if ("chatLinks".equals(string)) {
                        AbstractOption.CHAT_LINKS.set(this, string2);
                    }
                    if ("chatLinksPrompt".equals(string)) {
                        AbstractOption.CHAT_LINKS_PROMPT.set(this, string2);
                    }
                    if ("enableVsync".equals(string)) {
                        AbstractOption.VSYNC.set(this, string2);
                        if (this.vsync) {
                            this.framerateLimit = (int)AbstractOption.FRAMERATE_LIMIT.getMaxValue();
                        }
                        this.updateVSync();
                    }
                    if ("entityShadows".equals(string)) {
                        AbstractOption.ENTITY_SHADOWS.set(this, string2);
                    }
                    if ("forceUnicodeFont".equals(string)) {
                        AbstractOption.FORCE_UNICODE_FONT.set(this, string2);
                    }
                    if ("discrete_mouse_scroll".equals(string)) {
                        AbstractOption.DISCRETE_MOUSE_SCROLL.set(this, string2);
                    }
                    if ("invertYMouse".equals(string)) {
                        AbstractOption.INVERT_MOUSE.set(this, string2);
                    }
                    if ("realmsNotifications".equals(string)) {
                        AbstractOption.REALMS_NOTIFICATIONS.set(this, string2);
                    }
                    if ("reducedDebugInfo".equals(string)) {
                        AbstractOption.REDUCED_DEBUG_INFO.set(this, string2);
                    }
                    if ("showSubtitles".equals(string)) {
                        AbstractOption.SHOW_SUBTITLES.set(this, string2);
                    }
                    if ("snooperEnabled".equals(string)) {
                        AbstractOption.SNOOPER.set(this, string2);
                    }
                    if ("touchscreen".equals(string)) {
                        AbstractOption.TOUCHSCREEN.set(this, string2);
                    }
                    if ("fullscreen".equals(string)) {
                        AbstractOption.FULLSCREEN.set(this, string2);
                    }
                    if ("bobView".equals(string)) {
                        AbstractOption.VIEW_BOBBING.set(this, string2);
                    }
                    if ("toggleCrouch".equals(string)) {
                        this.toggleCrouch = "true".equals(string2);
                    }
                    if ("toggleSprint".equals(string)) {
                        this.toggleSprint = "true".equals(string2);
                    }
                    if ("mouseSensitivity".equals(string)) {
                        this.mouseSensitivity = GameSettings.parseFloat(string2);
                    }
                    if ("fov".equals(string)) {
                        this.fov = GameSettings.parseFloat(string2) * 40.0f + 70.0f;
                    }
                    if ("screenEffectScale".equals(string)) {
                        this.screenEffectScale = GameSettings.parseFloat(string2);
                    }
                    if ("fovEffectScale".equals(string)) {
                        this.fovScaleEffect = GameSettings.parseFloat(string2);
                    }
                    if ("gamma".equals(string)) {
                        this.gamma = GameSettings.parseFloat(string2);
                    }
                    if ("renderDistance".equals(string)) {
                        this.renderDistanceChunks = Integer.parseInt(string2);
                    }
                    if ("entityDistanceScaling".equals(string)) {
                        this.entityDistanceScaling = Float.parseFloat(string2);
                    }
                    if ("guiScale".equals(string)) {
                        this.guiScale = Integer.parseInt(string2);
                    }
                    if ("particles".equals(string)) {
                        this.particles = ParticleStatus.byId(Integer.parseInt(string2));
                    }
                    if ("maxFps".equals(string)) {
                        this.framerateLimit = Integer.parseInt(string2);
                        if (this.vsync) {
                            this.framerateLimit = (int)AbstractOption.FRAMERATE_LIMIT.getMaxValue();
                        }
                        if (this.framerateLimit <= 0) {
                            this.framerateLimit = (int)AbstractOption.FRAMERATE_LIMIT.getMaxValue();
                        }
                        if (this.mc.getMainWindow() != null) {
                            this.mc.getMainWindow().setFramerateLimit(this.framerateLimit);
                        }
                    }
                    if ("difficulty".equals(string)) {
                        this.difficulty = Difficulty.byId(Integer.parseInt(string2));
                    }
                    if ("graphicsMode".equals(string)) {
                        this.graphicFanciness = GraphicsFanciness.func_238163_a_(Integer.parseInt(string2));
                        this.updateRenderClouds();
                    }
                    if ("tutorialStep".equals(string)) {
                        this.tutorialStep = TutorialSteps.byName(string2);
                    }
                    if ("ao".equals(string)) {
                        this.ambientOcclusionStatus = "true".equals(string2) ? AmbientOcclusionStatus.MAX : ("false".equals(string2) ? AmbientOcclusionStatus.OFF : AmbientOcclusionStatus.getValue(Integer.parseInt(string2)));
                    }
                    if ("renderClouds".equals(string)) {
                        if ("true".equals(string2)) {
                            this.cloudOption = CloudOption.FANCY;
                        } else if ("false".equals(string2)) {
                            this.cloudOption = CloudOption.OFF;
                        } else if ("fast".equals(string2)) {
                            this.cloudOption = CloudOption.FAST;
                        }
                    }
                    if ("attackIndicator".equals(string)) {
                        this.attackIndicator = AttackIndicatorStatus.byId(Integer.parseInt(string2));
                    }
                    if ("resourcePacks".equals(string)) {
                        this.resourcePacks = JSONUtils.fromJSONUnlenient(GSON, string2, TYPE_LIST_STRING);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = Lists.newArrayList();
                        }
                    }
                    if ("incompatibleResourcePacks".equals(string)) {
                        this.incompatibleResourcePacks = JSONUtils.fromJSONUnlenient(GSON, string2, TYPE_LIST_STRING);
                        if (this.incompatibleResourcePacks == null) {
                            this.incompatibleResourcePacks = Lists.newArrayList();
                        }
                    }
                    if ("lastServer".equals(string)) {
                        this.lastServer = string2;
                    }
                    if ("lang".equals(string)) {
                        this.language = string2;
                    }
                    if ("chatVisibility".equals(string)) {
                        this.chatVisibility = ChatVisibility.getValue(Integer.parseInt(string2));
                    }
                    if ("chatOpacity".equals(string)) {
                        this.chatOpacity = GameSettings.parseFloat(string2);
                    }
                    if ("chatLineSpacing".equals(string)) {
                        this.chatLineSpacing = GameSettings.parseFloat(string2);
                    }
                    if ("textBackgroundOpacity".equals(string)) {
                        this.accessibilityTextBackgroundOpacity = GameSettings.parseFloat(string2);
                    }
                    if ("backgroundForChatOnly".equals(string)) {
                        this.accessibilityTextBackground = "true".equals(string2);
                    }
                    if ("fullscreenResolution".equals(string)) {
                        this.fullscreenResolution = string2;
                    }
                    if ("hideServerAddress".equals(string)) {
                        this.hideServerAddress = "true".equals(string2);
                    }
                    if ("advancedItemTooltips".equals(string)) {
                        this.advancedItemTooltips = "true".equals(string2);
                    }
                    if ("pauseOnLostFocus".equals(string)) {
                        this.pauseOnLostFocus = "true".equals(string2);
                    }
                    if ("overrideHeight".equals(string)) {
                        this.overrideHeight = Integer.parseInt(string2);
                    }
                    if ("overrideWidth".equals(string)) {
                        this.overrideWidth = Integer.parseInt(string2);
                    }
                    if ("heldItemTooltips".equals(string)) {
                        this.heldItemTooltips = "true".equals(string2);
                    }
                    if ("chatHeightFocused".equals(string)) {
                        this.chatHeightFocused = GameSettings.parseFloat(string2);
                    }
                    if ("chatDelay".equals(string)) {
                        this.chatDelay = GameSettings.parseFloat(string2);
                    }
                    if ("chatHeightUnfocused".equals(string)) {
                        this.chatHeightUnfocused = GameSettings.parseFloat(string2);
                    }
                    if ("chatScale".equals(string)) {
                        this.chatScale = GameSettings.parseFloat(string2);
                    }
                    if ("chatWidth".equals(string)) {
                        this.chatWidth = GameSettings.parseFloat(string2);
                    }
                    if ("mipmapLevels".equals(string)) {
                        this.mipmapLevels = Integer.parseInt(string2);
                    }
                    if ("useNativeTransport".equals(string)) {
                        this.useNativeTransport = "true".equals(string2);
                    }
                    if ("mainHand".equals(string)) {
                        HandSide handSide = this.mainHand = "left".equals(string2) ? HandSide.LEFT : HandSide.RIGHT;
                    }
                    if ("narrator".equals(string)) {
                        this.narrator = NarratorStatus.byId(Integer.parseInt(string2));
                    }
                    if ("biomeBlendRadius".equals(string)) {
                        this.biomeBlendRadius = Integer.parseInt(string2);
                    }
                    if ("mouseWheelSensitivity".equals(string)) {
                        this.mouseWheelSensitivity = GameSettings.parseFloat(string2);
                    }
                    if ("rawMouseInput".equals(string)) {
                        this.rawMouseInput = "true".equals(string2);
                    }
                    if ("glDebugVerbosity".equals(string)) {
                        this.glDebugVerbosity = Integer.parseInt(string2);
                    }
                    if ("skipMultiplayerWarning".equals(string)) {
                        this.skipMultiplayerWarning = "true".equals(string2);
                    }
                    if ("hideMatchedNames".equals(string)) {
                        this.field_244794_ae = "true".equals(string2);
                    }
                    if ("joinedFirstServer".equals(string)) {
                        this.field_244601_E = "true".equals(string2);
                    }
                    if ("syncChunkWrites".equals(string)) {
                        this.syncChunkWrites = "true".equals(string2);
                    }
                    for (KeyBinding keyBinding : this.keyBindings) {
                        if (!string.equals("key_" + keyBinding.getKeyDescription())) continue;
                        if (Reflector.KeyModifier_valueFromString.exists()) {
                            String[] stringArray;
                            if (string2.indexOf(58) != -1) {
                                stringArray = string2.split(":");
                                Object object = Reflector.call(Reflector.KeyModifier_valueFromString, stringArray[0]);
                                Reflector.call(keyBinding, Reflector.ForgeKeyBinding_setKeyModifierAndCode, object, InputMappings.getInputByName(stringArray[5]));
                                continue;
                            }
                            stringArray = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
                            Reflector.call(keyBinding, Reflector.ForgeKeyBinding_setKeyModifierAndCode, stringArray, InputMappings.getInputByName(string2));
                            continue;
                        }
                        keyBinding.bind(InputMappings.getInputByName(string2));
                    }
                    for (SoundCategory soundCategory : SoundCategory.values()) {
                        if (!string.equals("soundCategory_" + soundCategory.getName())) continue;
                        this.soundLevels.put(soundCategory, Float.valueOf(GameSettings.parseFloat(string2)));
                    }
                    for (PlayerModelPart playerModelPart : PlayerModelPart.values()) {
                        if (!string.equals("modelPart_" + playerModelPart.getPartName())) continue;
                        this.setModelPartEnabled(playerModelPart, "true".equals(string2));
                    }
                } catch (Exception exception) {
                    LOGGER.warn("Skipping bad option: {}:{}", (Object)string, (Object)string2);
                    exception.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
        } catch (Exception exception) {
            LOGGER.error("Failed to load options", (Throwable)exception);
        }
        this.loadOfOptions();
    }

    private CompoundNBT dataFix(CompoundNBT compoundNBT) {
        int n = 0;
        try {
            n = Integer.parseInt(compoundNBT.getString("version"));
        } catch (RuntimeException runtimeException) {
            // empty catch block
        }
        return NBTUtil.update(this.mc.getDataFixer(), DefaultTypeReferences.OPTIONS, compoundNBT, n);
    }

    private static float parseFloat(String string) {
        if ("true".equals(string)) {
            return 1.0f;
        }
        return "false".equals(string) ? 0.0f : Float.parseFloat(string);
    }

    public void saveOptions() {
        if (!Reflector.ClientModLoader_isLoading.exists() || !Reflector.callBoolean(Reflector.ClientModLoader_isLoading, new Object[0])) {
            try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));){
                printWriter.println("version:" + SharedConstants.getVersion().getWorldVersion());
                printWriter.println("autoJump:" + AbstractOption.AUTO_JUMP.get(this));
                printWriter.println("autoSuggestions:" + AbstractOption.AUTO_SUGGEST_COMMANDS.get(this));
                printWriter.println("chatColors:" + AbstractOption.CHAT_COLOR.get(this));
                printWriter.println("chatLinks:" + AbstractOption.CHAT_LINKS.get(this));
                printWriter.println("chatLinksPrompt:" + AbstractOption.CHAT_LINKS_PROMPT.get(this));
                printWriter.println("enableVsync:" + AbstractOption.VSYNC.get(this));
                printWriter.println("entityShadows:" + AbstractOption.ENTITY_SHADOWS.get(this));
                printWriter.println("forceUnicodeFont:" + AbstractOption.FORCE_UNICODE_FONT.get(this));
                printWriter.println("discrete_mouse_scroll:" + AbstractOption.DISCRETE_MOUSE_SCROLL.get(this));
                printWriter.println("invertYMouse:" + AbstractOption.INVERT_MOUSE.get(this));
                printWriter.println("realmsNotifications:" + AbstractOption.REALMS_NOTIFICATIONS.get(this));
                printWriter.println("reducedDebugInfo:" + AbstractOption.REDUCED_DEBUG_INFO.get(this));
                printWriter.println("snooperEnabled:" + AbstractOption.SNOOPER.get(this));
                printWriter.println("showSubtitles:" + AbstractOption.SHOW_SUBTITLES.get(this));
                printWriter.println("touchscreen:" + AbstractOption.TOUCHSCREEN.get(this));
                printWriter.println("fullscreen:" + AbstractOption.FULLSCREEN.get(this));
                printWriter.println("bobView:" + AbstractOption.VIEW_BOBBING.get(this));
                printWriter.println("toggleCrouch:" + this.toggleCrouch);
                printWriter.println("toggleSprint:" + this.toggleSprint);
                printWriter.println("mouseSensitivity:" + this.mouseSensitivity);
                printWriter.println("fov:" + (this.fov - 70.0) / 40.0);
                printWriter.println("screenEffectScale:" + this.screenEffectScale);
                printWriter.println("fovEffectScale:" + this.fovScaleEffect);
                printWriter.println("gamma:" + this.gamma);
                printWriter.println("renderDistance:" + this.renderDistanceChunks);
                printWriter.println("entityDistanceScaling:" + this.entityDistanceScaling);
                printWriter.println("guiScale:" + this.guiScale);
                printWriter.println("particles:" + this.particles.getId());
                printWriter.println("maxFps:" + this.framerateLimit);
                printWriter.println("difficulty:" + this.difficulty.getId());
                printWriter.println("graphicsMode:" + this.graphicFanciness.func_238162_a_());
                printWriter.println("ao:" + this.ambientOcclusionStatus.getId());
                printWriter.println("biomeBlendRadius:" + this.biomeBlendRadius);
                switch (2.$SwitchMap$net$minecraft$client$settings$CloudOption[this.cloudOption.ordinal()]) {
                    case 1: {
                        printWriter.println("renderClouds:true");
                        break;
                    }
                    case 2: {
                        printWriter.println("renderClouds:fast");
                        break;
                    }
                    case 3: {
                        printWriter.println("renderClouds:false");
                    }
                }
                printWriter.println("resourcePacks:" + GSON.toJson(this.resourcePacks));
                printWriter.println("incompatibleResourcePacks:" + GSON.toJson(this.incompatibleResourcePacks));
                printWriter.println("lastServer:" + this.lastServer);
                printWriter.println("lang:" + this.language);
                printWriter.println("chatVisibility:" + this.chatVisibility.getId());
                printWriter.println("chatOpacity:" + this.chatOpacity);
                printWriter.println("chatLineSpacing:" + this.chatLineSpacing);
                printWriter.println("textBackgroundOpacity:" + this.accessibilityTextBackgroundOpacity);
                printWriter.println("backgroundForChatOnly:" + this.accessibilityTextBackground);
                if (this.mc.getMainWindow().getVideoMode().isPresent()) {
                    printWriter.println("fullscreenResolution:" + this.mc.getMainWindow().getVideoMode().get().getSettingsString());
                }
                printWriter.println("hideServerAddress:" + this.hideServerAddress);
                printWriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
                printWriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
                printWriter.println("overrideWidth:" + this.overrideWidth);
                printWriter.println("overrideHeight:" + this.overrideHeight);
                printWriter.println("heldItemTooltips:" + this.heldItemTooltips);
                printWriter.println("chatHeightFocused:" + this.chatHeightFocused);
                printWriter.println("chatDelay: " + this.chatDelay);
                printWriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
                printWriter.println("chatScale:" + this.chatScale);
                printWriter.println("chatWidth:" + (float)this.chatWidth);
                printWriter.println("mipmapLevels:" + this.mipmapLevels);
                printWriter.println("useNativeTransport:" + this.useNativeTransport);
                printWriter.println("mainHand:" + (this.mainHand == HandSide.LEFT ? "left" : "right"));
                printWriter.println("attackIndicator:" + this.attackIndicator.getId());
                printWriter.println("narrator:" + this.narrator.getId());
                printWriter.println("tutorialStep:" + this.tutorialStep.getName());
                printWriter.println("mouseWheelSensitivity:" + this.mouseWheelSensitivity);
                printWriter.println("rawMouseInput:" + AbstractOption.RAW_MOUSE_INPUT.get(this));
                printWriter.println("glDebugVerbosity:" + this.glDebugVerbosity);
                printWriter.println("skipMultiplayerWarning:" + this.skipMultiplayerWarning);
                printWriter.println("hideMatchedNames:" + this.field_244794_ae);
                printWriter.println("joinedFirstServer:" + this.field_244601_E);
                printWriter.println("syncChunkWrites:" + this.syncChunkWrites);
                for (KeyBinding keyBinding : this.keyBindings) {
                    if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
                        Object object;
                        String string = "key_" + keyBinding.getKeyDescription() + ":" + keyBinding.getTranslationKey();
                        Object object2 = Reflector.call(keyBinding, Reflector.ForgeKeyBinding_getKeyModifier, new Object[0]);
                        printWriter.println(object2 != (object = Reflector.getFieldValue(Reflector.KeyModifier_NONE)) ? string + ":" + object2 : string);
                        continue;
                    }
                    printWriter.println("key_" + keyBinding.getKeyDescription() + ":" + keyBinding.getTranslationKey());
                }
                for (SoundCategory soundCategory : SoundCategory.values()) {
                    printWriter.println("soundCategory_" + soundCategory.getName() + ":" + this.getSoundLevel(soundCategory));
                }
                for (PlayerModelPart playerModelPart : PlayerModelPart.values()) {
                    printWriter.println("modelPart_" + playerModelPart.getPartName() + ":" + this.setModelParts.contains((Object)playerModelPart));
                }
            } catch (Exception exception) {
                LOGGER.error("Failed to save options", (Throwable)exception);
            }
            this.saveOfOptions();
            this.sendSettingsToServer();
        }
    }

    public float getSoundLevel(SoundCategory soundCategory) {
        return this.soundLevels.containsKey((Object)soundCategory) ? this.soundLevels.get((Object)soundCategory).floatValue() : 1.0f;
    }

    public void setSoundLevel(SoundCategory soundCategory, float f) {
        this.soundLevels.put(soundCategory, Float.valueOf(f));
        this.mc.getSoundHandler().setSoundLevel(soundCategory, f);
    }

    public void sendSettingsToServer() {
        if (this.mc.player != null) {
            int n = 0;
            for (PlayerModelPart playerModelPart : this.setModelParts) {
                n |= playerModelPart.getPartMask();
            }
            this.mc.player.connection.sendPacket(new CClientSettingsPacket(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColor, n, this.mainHand));
        }
    }

    public Set<PlayerModelPart> getModelParts() {
        return ImmutableSet.copyOf(this.setModelParts);
    }

    public void setModelPartEnabled(PlayerModelPart playerModelPart, boolean bl) {
        if (bl) {
            this.setModelParts.add(playerModelPart);
        } else {
            this.setModelParts.remove((Object)playerModelPart);
        }
        this.sendSettingsToServer();
    }

    public void switchModelPartEnabled(PlayerModelPart playerModelPart) {
        if (this.getModelParts().contains((Object)playerModelPart)) {
            this.setModelParts.remove((Object)playerModelPart);
        } else {
            this.setModelParts.add(playerModelPart);
        }
        this.sendSettingsToServer();
    }

    public CloudOption getCloudOption() {
        return this.renderDistanceChunks >= 4 ? this.cloudOption : CloudOption.OFF;
    }

    public boolean isUsingNativeTransport() {
        return this.useNativeTransport;
    }

    public void setOptionFloatValueOF(AbstractOption abstractOption, double d) {
        int n;
        if (abstractOption == AbstractOption.CLOUD_HEIGHT) {
            this.ofCloudsHeight = d;
        }
        if (abstractOption == AbstractOption.AO_LEVEL) {
            this.ofAoLevel = d;
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.AA_LEVEL) {
            n = (int)d;
            if (n > 0 && Config.isShaders()) {
                Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
                return;
            }
            if (n > 0 && Config.isGraphicsFabulous()) {
                Config.showGuiMessage(Lang.get("of.message.aa.gf1"), Lang.get("of.message.aa.gf2"));
                return;
            }
            this.ofAaLevel = n;
            this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
        }
        if (abstractOption == AbstractOption.AF_LEVEL) {
            this.ofAfLevel = n = (int)d;
            this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            this.mc.scheduleResourcesRefresh();
            Shaders.uninit();
        }
        if (abstractOption == AbstractOption.MIPMAP_TYPE) {
            n = (int)d;
            this.ofMipmapType = Config.limit(n, 0, 3);
            this.updateMipmaps();
        }
    }

    public double getOptionFloatValueOF(AbstractOption abstractOption) {
        if (abstractOption == AbstractOption.CLOUD_HEIGHT) {
            return this.ofCloudsHeight;
        }
        if (abstractOption == AbstractOption.AO_LEVEL) {
            return this.ofAoLevel;
        }
        if (abstractOption == AbstractOption.AA_LEVEL) {
            return this.ofAaLevel;
        }
        if (abstractOption == AbstractOption.AF_LEVEL) {
            return this.ofAfLevel;
        }
        if (abstractOption == AbstractOption.MIPMAP_TYPE) {
            return this.ofMipmapType;
        }
        if (abstractOption == AbstractOption.FRAMERATE_LIMIT) {
            return (double)this.framerateLimit == AbstractOption.FRAMERATE_LIMIT.getMaxValue() && this.vsync ? 0.0 : (double)this.framerateLimit;
        }
        return 3.4028234663852886E38;
    }

    public void setOptionValueOF(AbstractOption abstractOption, int n) {
        if (abstractOption == AbstractOption.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (Config.isFancyFogAvailable()) break;
                    this.ofFogType = 3;
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
                }
            }
        }
        if (abstractOption == AbstractOption.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (abstractOption == AbstractOption.SMOOTH_FPS) {
            boolean bl = this.ofSmoothFps = !this.ofSmoothFps;
        }
        if (abstractOption == AbstractOption.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateThreadPriorities();
        }
        if (abstractOption == AbstractOption.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
            this.updateRenderClouds();
        }
        if (abstractOption == AbstractOption.TREES) {
            this.ofTrees = GameSettings.nextValue(this.ofTrees, OF_TREES_VALUES);
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (abstractOption == AbstractOption.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (abstractOption == AbstractOption.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater == 1) {
                ++this.ofAnimatedWater;
            }
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (abstractOption == AbstractOption.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava == 1) {
                ++this.ofAnimatedLava;
            }
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (abstractOption == AbstractOption.ANIMATED_FIRE) {
            boolean bl = this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (abstractOption == AbstractOption.ANIMATED_PORTAL) {
            boolean bl = this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (abstractOption == AbstractOption.ANIMATED_REDSTONE) {
            boolean bl = this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (abstractOption == AbstractOption.ANIMATED_EXPLOSION) {
            boolean bl = this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (abstractOption == AbstractOption.ANIMATED_FLAME) {
            boolean bl = this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (abstractOption == AbstractOption.ANIMATED_SMOKE) {
            boolean bl = this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (abstractOption == AbstractOption.VOID_PARTICLES) {
            boolean bl = this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (abstractOption == AbstractOption.WATER_PARTICLES) {
            boolean bl = this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (abstractOption == AbstractOption.PORTAL_PARTICLES) {
            boolean bl = this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (abstractOption == AbstractOption.POTION_PARTICLES) {
            boolean bl = this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (abstractOption == AbstractOption.FIREWORK_PARTICLES) {
            boolean bl = this.ofFireworkParticles = !this.ofFireworkParticles;
        }
        if (abstractOption == AbstractOption.DRIPPING_WATER_LAVA) {
            boolean bl = this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (abstractOption == AbstractOption.ANIMATED_TERRAIN) {
            boolean bl = this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (abstractOption == AbstractOption.ANIMATED_TEXTURES) {
            boolean bl = this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (abstractOption == AbstractOption.RAIN_SPLASH) {
            boolean bl = this.ofRainSplash = !this.ofRainSplash;
        }
        if (abstractOption == AbstractOption.LAGOMETER) {
            boolean bl = this.ofLagometer = !this.ofLagometer;
        }
        if (abstractOption == AbstractOption.SHOW_FPS) {
            boolean bl = this.ofShowFps = !this.ofShowFps;
        }
        if (abstractOption == AbstractOption.AUTOSAVE_TICKS) {
            int n2 = 900;
            this.ofAutoSaveTicks = Math.max(this.ofAutoSaveTicks / n2 * n2, n2);
            this.ofAutoSaveTicks *= 2;
            if (this.ofAutoSaveTicks > 32 * n2) {
                this.ofAutoSaveTicks = n2;
            }
        }
        if (abstractOption == AbstractOption.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            if (this.ofConnectedTextures == 2) {
                this.mc.worldRenderer.loadRenderers();
            } else {
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (abstractOption == AbstractOption.WEATHER) {
            boolean bl = this.ofWeather = !this.ofWeather;
        }
        if (abstractOption == AbstractOption.SKY) {
            boolean bl = this.ofSky = !this.ofSky;
        }
        if (abstractOption == AbstractOption.STARS) {
            boolean bl = this.ofStars = !this.ofStars;
        }
        if (abstractOption == AbstractOption.SUN_MOON) {
            boolean bl = this.ofSunMoon = !this.ofSunMoon;
        }
        if (abstractOption == AbstractOption.VIGNETTE) {
            ++this.ofVignette;
            if (this.ofVignette > 2) {
                this.ofVignette = 0;
            }
        }
        if (abstractOption == AbstractOption.CHUNK_UPDATES) {
            ++this.ofChunkUpdates;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }
        if (abstractOption == AbstractOption.CHUNK_UPDATES_DYNAMIC) {
            boolean bl = this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }
        if (abstractOption == AbstractOption.TIME) {
            ++this.ofTime;
            if (this.ofTime > 2) {
                this.ofTime = 0;
            }
        }
        if (abstractOption == AbstractOption.PROFILER) {
            boolean bl = this.ofProfiler = !this.ofProfiler;
        }
        if (abstractOption == AbstractOption.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            CustomColors.updateUseDefaultGrassFoliageColors();
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.RANDOM_ENTITIES) {
            this.ofRandomEntities = !this.ofRandomEntities;
            RandomEntities.update();
        }
        if (abstractOption == AbstractOption.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            FontUtils.reloadFonts();
        }
        if (abstractOption == AbstractOption.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            CustomColors.update();
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.CUSTOM_ITEMS) {
            this.ofCustomItems = !this.ofCustomItems;
            this.mc.scheduleResourcesRefresh();
        }
        if (abstractOption == AbstractOption.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
            CustomSky.update();
        }
        if (abstractOption == AbstractOption.SHOW_CAPES) {
            boolean bl = this.ofShowCapes = !this.ofShowCapes;
        }
        if (abstractOption == AbstractOption.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            NaturalTextures.update();
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.EMISSIVE_TEXTURES) {
            this.ofEmissiveTextures = !this.ofEmissiveTextures;
            this.mc.scheduleResourcesRefresh();
        }
        if (abstractOption == AbstractOption.FAST_MATH) {
            MathHelper.fastMath = this.ofFastMath = !this.ofFastMath;
        }
        if (abstractOption == AbstractOption.FAST_RENDER) {
            boolean bl = this.ofFastRender = !this.ofFastRender;
        }
        if (abstractOption == AbstractOption.TRANSLUCENT_BLOCKS) {
            this.ofTranslucentBlocks = this.ofTranslucentBlocks == 0 ? 1 : (this.ofTranslucentBlocks == 1 ? 2 : (this.ofTranslucentBlocks == 2 ? 0 : 0));
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.LAZY_CHUNK_LOADING) {
            boolean bl = this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
        }
        if (abstractOption == AbstractOption.RENDER_REGIONS) {
            this.ofRenderRegions = !this.ofRenderRegions;
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.SMART_ANIMATIONS) {
            this.ofSmartAnimations = !this.ofSmartAnimations;
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.DYNAMIC_FOV) {
            boolean bl = this.ofDynamicFov = !this.ofDynamicFov;
        }
        if (abstractOption == AbstractOption.ALTERNATE_BLOCKS) {
            this.ofAlternateBlocks = !this.ofAlternateBlocks;
            this.mc.worldRenderer.loadRenderers();
        }
        if (abstractOption == AbstractOption.DYNAMIC_LIGHTS) {
            this.ofDynamicLights = GameSettings.nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
            DynamicLights.removeLights(this.mc.worldRenderer);
        }
        if (abstractOption == AbstractOption.SCREENSHOT_SIZE) {
            ++this.ofScreenshotSize;
            if (this.ofScreenshotSize > 4) {
                this.ofScreenshotSize = 1;
            }
        }
        if (abstractOption == AbstractOption.CUSTOM_ENTITY_MODELS) {
            this.ofCustomEntityModels = !this.ofCustomEntityModels;
            this.mc.scheduleResourcesRefresh();
        }
        if (abstractOption == AbstractOption.CUSTOM_GUIS) {
            this.ofCustomGuis = !this.ofCustomGuis;
            CustomGuis.update();
        }
        if (abstractOption == AbstractOption.SHOW_GL_ERRORS) {
            boolean bl = this.ofShowGlErrors = !this.ofShowGlErrors;
        }
        if (abstractOption == AbstractOption.HELD_ITEM_TOOLTIPS) {
            boolean bl = this.heldItemTooltips = !this.heldItemTooltips;
        }
        if (abstractOption == AbstractOption.ADVANCED_TOOLTIPS) {
            boolean bl = this.advancedItemTooltips = !this.advancedItemTooltips;
        }
        if (abstractOption == AbstractOption.CHAT_BACKGROUND) {
            this.ofChatBackground = this.ofChatBackground == 0 ? 5 : (this.ofChatBackground == 5 ? 3 : 0);
        }
        if (abstractOption == AbstractOption.CHAT_SHADOW) {
            this.ofChatShadow = !this.ofChatShadow;
        }
    }

    public ITextComponent getKeyComponentOF(AbstractOption abstractOption) {
        String string = this.getKeyBindingOF(abstractOption);
        StringTextComponent stringTextComponent = new StringTextComponent(string);
        return stringTextComponent;
    }

    public String getKeyBindingOF(AbstractOption abstractOption) {
        Object object = I18n.format(abstractOption.getResourceKey(), new Object[0]) + ": ";
        if (object == null) {
            object = abstractOption.getResourceKey();
        }
        if (abstractOption == AbstractOption.RENDER_DISTANCE) {
            int n = (int)AbstractOption.RENDER_DISTANCE.get(this);
            String string = I18n.format("of.options.renderDistance.tiny", new Object[0]);
            int n2 = 2;
            if (n >= 4) {
                string = I18n.format("of.options.renderDistance.short", new Object[0]);
                n2 = 4;
            }
            if (n >= 8) {
                string = I18n.format("of.options.renderDistance.normal", new Object[0]);
                n2 = 8;
            }
            if (n >= 16) {
                string = I18n.format("of.options.renderDistance.far", new Object[0]);
                n2 = 16;
            }
            if (n >= 32) {
                string = Lang.get("of.options.renderDistance.extreme");
                n2 = 32;
            }
            if (n >= 48) {
                string = Lang.get("of.options.renderDistance.insane");
                n2 = 48;
            }
            if (n >= 64) {
                string = Lang.get("of.options.renderDistance.ludicrous");
                n2 = 64;
            }
            int n3 = this.renderDistanceChunks - n2;
            Object object2 = string;
            if (n3 > 0) {
                object2 = string + "+";
            }
            return (String)object + n + " " + (String)object2;
        }
        if (abstractOption == AbstractOption.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
                case 3: {
                    return (String)object + Lang.getOff();
                }
            }
            return (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.FOG_START) {
            return (String)object + this.ofFogStart;
        }
        if (abstractOption == AbstractOption.MIPMAP_TYPE) {
            return FloatOptions.getText(abstractOption, this.ofMipmapType);
        }
        if (abstractOption == AbstractOption.SMOOTH_FPS) {
            return this.ofSmoothFps ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SMOOTH_WORLD) {
            return this.ofSmoothWorld ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CLOUDS) {
            switch (this.ofClouds) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
                case 3: {
                    return (String)object + Lang.getOff();
                }
            }
            return (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.TREES) {
            switch (this.ofTrees) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
                default: {
                    return (String)object + Lang.getDefault();
                }
                case 4: 
            }
            return (String)object + Lang.get("of.general.smart");
        }
        if (abstractOption == AbstractOption.DROPPED_ITEMS) {
            switch (this.ofDroppedItems) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
            }
            return (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.RAIN) {
            switch (this.ofRain) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
                case 3: {
                    return (String)object + Lang.getOff();
                }
            }
            return (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.ANIMATED_WATER) {
            switch (this.ofAnimatedWater) {
                case 1: {
                    return (String)object + Lang.get("of.options.animation.dynamic");
                }
                case 2: {
                    return (String)object + Lang.getOff();
                }
            }
            return (String)object + Lang.getOn();
        }
        if (abstractOption == AbstractOption.ANIMATED_LAVA) {
            switch (this.ofAnimatedLava) {
                case 1: {
                    return (String)object + Lang.get("of.options.animation.dynamic");
                }
                case 2: {
                    return (String)object + Lang.getOff();
                }
            }
            return (String)object + Lang.getOn();
        }
        if (abstractOption == AbstractOption.ANIMATED_FIRE) {
            return this.ofAnimatedFire ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_PORTAL) {
            return this.ofAnimatedPortal ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_REDSTONE) {
            return this.ofAnimatedRedstone ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_EXPLOSION) {
            return this.ofAnimatedExplosion ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_FLAME) {
            return this.ofAnimatedFlame ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_SMOKE) {
            return this.ofAnimatedSmoke ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.VOID_PARTICLES) {
            return this.ofVoidParticles ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.WATER_PARTICLES) {
            return this.ofWaterParticles ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.PORTAL_PARTICLES) {
            return this.ofPortalParticles ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.POTION_PARTICLES) {
            return this.ofPotionParticles ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.FIREWORK_PARTICLES) {
            return this.ofFireworkParticles ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.DRIPPING_WATER_LAVA) {
            return this.ofDrippingWaterLava ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_TERRAIN) {
            return this.ofAnimatedTerrain ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ANIMATED_TEXTURES) {
            return this.ofAnimatedTextures ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.RAIN_SPLASH) {
            return this.ofRainSplash ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.LAGOMETER) {
            return this.ofLagometer ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SHOW_FPS) {
            return this.ofShowFps ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.AUTOSAVE_TICKS) {
            int n = 900;
            if (this.ofAutoSaveTicks <= n) {
                return (String)object + Lang.get("of.options.save.45s");
            }
            if (this.ofAutoSaveTicks <= 2 * n) {
                return (String)object + Lang.get("of.options.save.90s");
            }
            if (this.ofAutoSaveTicks <= 4 * n) {
                return (String)object + Lang.get("of.options.save.3min");
            }
            if (this.ofAutoSaveTicks <= 8 * n) {
                return (String)object + Lang.get("of.options.save.6min");
            }
            return this.ofAutoSaveTicks <= 16 * n ? (String)object + Lang.get("of.options.save.12min") : (String)object + Lang.get("of.options.save.24min");
        }
        if (abstractOption == AbstractOption.BETTER_GRASS) {
            switch (this.ofBetterGrass) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
            }
            return (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CONNECTED_TEXTURES) {
            switch (this.ofConnectedTextures) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
            }
            return (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.WEATHER) {
            return this.ofWeather ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SKY) {
            return this.ofSky ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.STARS) {
            return this.ofStars ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SUN_MOON) {
            return this.ofSunMoon ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.VIGNETTE) {
            switch (this.ofVignette) {
                case 1: {
                    return (String)object + Lang.getFast();
                }
                case 2: {
                    return (String)object + Lang.getFancy();
                }
            }
            return (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.CHUNK_UPDATES) {
            return (String)object + this.ofChunkUpdates;
        }
        if (abstractOption == AbstractOption.CHUNK_UPDATES_DYNAMIC) {
            return this.ofChunkUpdatesDynamic ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.TIME) {
            if (this.ofTime == 1) {
                return (String)object + Lang.get("of.options.time.dayOnly");
            }
            return this.ofTime == 2 ? (String)object + Lang.get("of.options.time.nightOnly") : (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.AA_LEVEL) {
            return FloatOptions.getText(abstractOption, this.ofAaLevel);
        }
        if (abstractOption == AbstractOption.AF_LEVEL) {
            return FloatOptions.getText(abstractOption, this.ofAfLevel);
        }
        if (abstractOption == AbstractOption.PROFILER) {
            return this.ofProfiler ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.BETTER_SNOW) {
            return this.ofBetterSnow ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SWAMP_COLORS) {
            return this.ofSwampColors ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.RANDOM_ENTITIES) {
            return this.ofRandomEntities ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CUSTOM_FONTS) {
            return this.ofCustomFonts ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CUSTOM_COLORS) {
            return this.ofCustomColors ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CUSTOM_SKY) {
            return this.ofCustomSky ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SHOW_CAPES) {
            return this.ofShowCapes ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CUSTOM_ITEMS) {
            return this.ofCustomItems ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.NATURAL_TEXTURES) {
            return this.ofNaturalTextures ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.EMISSIVE_TEXTURES) {
            return this.ofEmissiveTextures ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.FAST_MATH) {
            return this.ofFastMath ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.FAST_RENDER) {
            return this.ofFastRender ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.TRANSLUCENT_BLOCKS) {
            if (this.ofTranslucentBlocks == 1) {
                return (String)object + Lang.getFast();
            }
            return this.ofTranslucentBlocks == 2 ? (String)object + Lang.getFancy() : (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.LAZY_CHUNK_LOADING) {
            return this.ofLazyChunkLoading ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.RENDER_REGIONS) {
            return this.ofRenderRegions ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SMART_ANIMATIONS) {
            return this.ofSmartAnimations ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.DYNAMIC_FOV) {
            return this.ofDynamicFov ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ALTERNATE_BLOCKS) {
            return this.ofAlternateBlocks ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.DYNAMIC_LIGHTS) {
            int n = GameSettings.indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
            return (String)object + GameSettings.getTranslation(KEYS_DYNAMIC_LIGHTS, n);
        }
        if (abstractOption == AbstractOption.SCREENSHOT_SIZE) {
            return this.ofScreenshotSize <= 1 ? (String)object + Lang.getDefault() : (String)object + this.ofScreenshotSize + "x";
        }
        if (abstractOption == AbstractOption.CUSTOM_ENTITY_MODELS) {
            return this.ofCustomEntityModels ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.CUSTOM_GUIS) {
            return this.ofCustomGuis ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.SHOW_GL_ERRORS) {
            return this.ofShowGlErrors ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.HELD_ITEM_TOOLTIPS) {
            return this.heldItemTooltips ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.ADVANCED_TOOLTIPS) {
            return this.advancedItemTooltips ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption == AbstractOption.FRAMERATE_LIMIT) {
            double d = AbstractOption.FRAMERATE_LIMIT.get(this);
            if (d == 0.0) {
                return (String)object + Lang.get("of.options.framerateLimit.vsync");
            }
            return d == AbstractOption.FRAMERATE_LIMIT.getMaxValue() ? (String)object + I18n.format("options.framerateLimit.max", new Object[0]) : (String)object + (int)d + " fps";
        }
        if (abstractOption == AbstractOption.CHAT_BACKGROUND) {
            if (this.ofChatBackground == 3) {
                return (String)object + Lang.getOff();
            }
            return this.ofChatBackground == 5 ? (String)object + Lang.get("of.general.compact") : (String)object + Lang.getDefault();
        }
        if (abstractOption == AbstractOption.CHAT_SHADOW) {
            return this.ofChatShadow ? (String)object + Lang.getOn() : (String)object + Lang.getOff();
        }
        if (abstractOption instanceof SliderPercentageOption) {
            SliderPercentageOption sliderPercentageOption = (SliderPercentageOption)abstractOption;
            double d = sliderPercentageOption.get(this);
            return d == 0.0 ? (String)object + I18n.format("options.off", new Object[0]) : (String)object + (int)(d * 100.0) + "%";
        }
        return null;
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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), StandardCharsets.UTF_8));
            String string = "";
            while ((string = bufferedReader.readLine()) != null) {
                try {
                    String[] stringArray = string.split(":");
                    if (stringArray[0].equals("ofRenderDistanceChunks") && stringArray.length >= 2) {
                        this.renderDistanceChunks = Integer.valueOf(stringArray[5]);
                        this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 1024);
                    }
                    if (stringArray[0].equals("ofFogType") && stringArray.length >= 2) {
                        this.ofFogType = Integer.valueOf(stringArray[5]);
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }
                    if (stringArray[0].equals("ofFogStart") && stringArray.length >= 2) {
                        this.ofFogStart = Float.valueOf(stringArray[5]).floatValue();
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (stringArray[0].equals("ofMipmapType") && stringArray.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(stringArray[5]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
                    }
                    if (stringArray[0].equals("ofOcclusionFancy") && stringArray.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSmoothFps") && stringArray.length >= 2) {
                        this.ofSmoothFps = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSmoothWorld") && stringArray.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAoLevel") && stringArray.length >= 2) {
                        this.ofAoLevel = Float.valueOf(stringArray[5]).floatValue();
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0, 1.0);
                    }
                    if (stringArray[0].equals("ofClouds") && stringArray.length >= 2) {
                        this.ofClouds = Integer.valueOf(stringArray[5]);
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                        this.updateRenderClouds();
                    }
                    if (stringArray[0].equals("ofCloudsHeight") && stringArray.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(stringArray[5]).floatValue();
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0, 1.0);
                    }
                    if (stringArray[0].equals("ofTrees") && stringArray.length >= 2) {
                        this.ofTrees = Integer.valueOf(stringArray[5]);
                        this.ofTrees = GameSettings.limit(this.ofTrees, OF_TREES_VALUES);
                    }
                    if (stringArray[0].equals("ofDroppedItems") && stringArray.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(stringArray[5]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }
                    if (stringArray[0].equals("ofRain") && stringArray.length >= 2) {
                        this.ofRain = Integer.valueOf(stringArray[5]);
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }
                    if (stringArray[0].equals("ofAnimatedWater") && stringArray.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(stringArray[5]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }
                    if (stringArray[0].equals("ofAnimatedLava") && stringArray.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(stringArray[5]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }
                    if (stringArray[0].equals("ofAnimatedFire") && stringArray.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedPortal") && stringArray.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedRedstone") && stringArray.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedExplosion") && stringArray.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedFlame") && stringArray.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedSmoke") && stringArray.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofVoidParticles") && stringArray.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofWaterParticles") && stringArray.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofPortalParticles") && stringArray.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofPotionParticles") && stringArray.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofFireworkParticles") && stringArray.length >= 2) {
                        this.ofFireworkParticles = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofDrippingWaterLava") && stringArray.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedTerrain") && stringArray.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAnimatedTextures") && stringArray.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofRainSplash") && stringArray.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofLagometer") && stringArray.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofShowFps") && stringArray.length >= 2) {
                        this.ofShowFps = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAutoSaveTicks") && stringArray.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(stringArray[5]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }
                    if (stringArray[0].equals("ofBetterGrass") && stringArray.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(stringArray[5]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }
                    if (stringArray[0].equals("ofConnectedTextures") && stringArray.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(stringArray[5]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }
                    if (stringArray[0].equals("ofWeather") && stringArray.length >= 2) {
                        this.ofWeather = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSky") && stringArray.length >= 2) {
                        this.ofSky = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofStars") && stringArray.length >= 2) {
                        this.ofStars = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSunMoon") && stringArray.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofVignette") && stringArray.length >= 2) {
                        this.ofVignette = Integer.valueOf(stringArray[5]);
                        this.ofVignette = Config.limit(this.ofVignette, 0, 2);
                    }
                    if (stringArray[0].equals("ofChunkUpdates") && stringArray.length >= 2) {
                        this.ofChunkUpdates = Integer.valueOf(stringArray[5]);
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
                    }
                    if (stringArray[0].equals("ofChunkUpdatesDynamic") && stringArray.length >= 2) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofTime") && stringArray.length >= 2) {
                        this.ofTime = Integer.valueOf(stringArray[5]);
                        this.ofTime = Config.limit(this.ofTime, 0, 2);
                    }
                    if (stringArray[0].equals("ofAaLevel") && stringArray.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(stringArray[5]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }
                    if (stringArray[0].equals("ofAfLevel") && stringArray.length >= 2) {
                        this.ofAfLevel = Integer.valueOf(stringArray[5]);
                        this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
                    }
                    if (stringArray[0].equals("ofProfiler") && stringArray.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofBetterSnow") && stringArray.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSwampColors") && stringArray.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofRandomEntities") && stringArray.length >= 2) {
                        this.ofRandomEntities = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofCustomFonts") && stringArray.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofCustomColors") && stringArray.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofCustomItems") && stringArray.length >= 2) {
                        this.ofCustomItems = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofCustomSky") && stringArray.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofShowCapes") && stringArray.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofNaturalTextures") && stringArray.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofEmissiveTextures") && stringArray.length >= 2) {
                        this.ofEmissiveTextures = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofLazyChunkLoading") && stringArray.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofRenderRegions") && stringArray.length >= 2) {
                        this.ofRenderRegions = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofSmartAnimations") && stringArray.length >= 2) {
                        this.ofSmartAnimations = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofDynamicFov") && stringArray.length >= 2) {
                        this.ofDynamicFov = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofAlternateBlocks") && stringArray.length >= 2) {
                        this.ofAlternateBlocks = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofDynamicLights") && stringArray.length >= 2) {
                        this.ofDynamicLights = Integer.valueOf(stringArray[5]);
                        this.ofDynamicLights = GameSettings.limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
                    }
                    if (stringArray[0].equals("ofScreenshotSize") && stringArray.length >= 2) {
                        this.ofScreenshotSize = Integer.valueOf(stringArray[5]);
                        this.ofScreenshotSize = Config.limit(this.ofScreenshotSize, 1, 4);
                    }
                    if (stringArray[0].equals("ofCustomEntityModels") && stringArray.length >= 2) {
                        this.ofCustomEntityModels = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofCustomGuis") && stringArray.length >= 2) {
                        this.ofCustomGuis = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofShowGlErrors") && stringArray.length >= 2) {
                        this.ofShowGlErrors = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofFastMath") && stringArray.length >= 2) {
                        MathHelper.fastMath = this.ofFastMath = Boolean.valueOf(stringArray[5]).booleanValue();
                    }
                    if (stringArray[0].equals("ofFastRender") && stringArray.length >= 2) {
                        this.ofFastRender = Boolean.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofTranslucentBlocks") && stringArray.length >= 2) {
                        this.ofTranslucentBlocks = Integer.valueOf(stringArray[5]);
                        this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
                    }
                    if (stringArray[0].equals("ofChatBackground") && stringArray.length >= 2) {
                        this.ofChatBackground = Integer.valueOf(stringArray[5]);
                    }
                    if (stringArray[0].equals("ofChatShadow") && stringArray.length >= 2) {
                        this.ofChatShadow = Boolean.valueOf(stringArray[5]);
                    }
                    if (!stringArray[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) continue;
                    this.ofKeyBindZoom.bind(InputMappings.getInputByName(stringArray[5]));
                } catch (Exception exception) {
                    Config.dbg("Skipping bad option: " + string);
                    exception.printStackTrace();
                }
            }
            KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[]{this.ofKeyBindZoom});
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedReader.close();
        } catch (Exception exception) {
            Config.warn("Failed to load options");
            exception.printStackTrace();
        }
    }

    public void saveOfOptions() {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
            printWriter.println("ofFogType:" + this.ofFogType);
            printWriter.println("ofFogStart:" + this.ofFogStart);
            printWriter.println("ofMipmapType:" + this.ofMipmapType);
            printWriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            printWriter.println("ofSmoothFps:" + this.ofSmoothFps);
            printWriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
            printWriter.println("ofAoLevel:" + this.ofAoLevel);
            printWriter.println("ofClouds:" + this.ofClouds);
            printWriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
            printWriter.println("ofTrees:" + this.ofTrees);
            printWriter.println("ofDroppedItems:" + this.ofDroppedItems);
            printWriter.println("ofRain:" + this.ofRain);
            printWriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
            printWriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
            printWriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
            printWriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            printWriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            printWriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            printWriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            printWriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            printWriter.println("ofVoidParticles:" + this.ofVoidParticles);
            printWriter.println("ofWaterParticles:" + this.ofWaterParticles);
            printWriter.println("ofPortalParticles:" + this.ofPortalParticles);
            printWriter.println("ofPotionParticles:" + this.ofPotionParticles);
            printWriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
            printWriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            printWriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            printWriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            printWriter.println("ofRainSplash:" + this.ofRainSplash);
            printWriter.println("ofLagometer:" + this.ofLagometer);
            printWriter.println("ofShowFps:" + this.ofShowFps);
            printWriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            printWriter.println("ofBetterGrass:" + this.ofBetterGrass);
            printWriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
            printWriter.println("ofWeather:" + this.ofWeather);
            printWriter.println("ofSky:" + this.ofSky);
            printWriter.println("ofStars:" + this.ofStars);
            printWriter.println("ofSunMoon:" + this.ofSunMoon);
            printWriter.println("ofVignette:" + this.ofVignette);
            printWriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
            printWriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
            printWriter.println("ofTime:" + this.ofTime);
            printWriter.println("ofAaLevel:" + this.ofAaLevel);
            printWriter.println("ofAfLevel:" + this.ofAfLevel);
            printWriter.println("ofProfiler:" + this.ofProfiler);
            printWriter.println("ofBetterSnow:" + this.ofBetterSnow);
            printWriter.println("ofSwampColors:" + this.ofSwampColors);
            printWriter.println("ofRandomEntities:" + this.ofRandomEntities);
            printWriter.println("ofCustomFonts:" + this.ofCustomFonts);
            printWriter.println("ofCustomColors:" + this.ofCustomColors);
            printWriter.println("ofCustomItems:" + this.ofCustomItems);
            printWriter.println("ofCustomSky:" + this.ofCustomSky);
            printWriter.println("ofShowCapes:" + this.ofShowCapes);
            printWriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
            printWriter.println("ofEmissiveTextures:" + this.ofEmissiveTextures);
            printWriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            printWriter.println("ofRenderRegions:" + this.ofRenderRegions);
            printWriter.println("ofSmartAnimations:" + this.ofSmartAnimations);
            printWriter.println("ofDynamicFov:" + this.ofDynamicFov);
            printWriter.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
            printWriter.println("ofDynamicLights:" + this.ofDynamicLights);
            printWriter.println("ofScreenshotSize:" + this.ofScreenshotSize);
            printWriter.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
            printWriter.println("ofCustomGuis:" + this.ofCustomGuis);
            printWriter.println("ofShowGlErrors:" + this.ofShowGlErrors);
            printWriter.println("ofFastMath:" + this.ofFastMath);
            printWriter.println("ofFastRender:" + this.ofFastRender);
            printWriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
            printWriter.println("ofChatBackground:" + this.ofChatBackground);
            printWriter.println("ofChatShadow:" + this.ofChatShadow);
            printWriter.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getTranslationKey());
            printWriter.close();
        } catch (Exception exception) {
            Config.warn("Failed to save options");
            exception.printStackTrace();
        }
    }

    public void updateRenderClouds() {
        Framebuffer framebuffer;
        WorldRenderer worldRenderer;
        switch (this.ofClouds) {
            case 1: {
                this.cloudOption = CloudOption.FAST;
                break;
            }
            case 2: {
                this.cloudOption = CloudOption.FANCY;
                break;
            }
            case 3: {
                this.cloudOption = CloudOption.OFF;
                break;
            }
            default: {
                this.cloudOption = this.graphicFanciness != GraphicsFanciness.FAST ? CloudOption.FANCY : CloudOption.FAST;
            }
        }
        if (this.graphicFanciness == GraphicsFanciness.FABULOUS && (worldRenderer = Minecraft.getInstance().worldRenderer) != null && (framebuffer = worldRenderer.func_239232_u_()) != null) {
            framebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        }
    }

    public void resetSettings() {
        this.renderDistanceChunks = 8;
        this.entityDistanceScaling = 1.0f;
        this.viewBobbing = true;
        this.framerateLimit = (int)AbstractOption.FRAMERATE_LIMIT.getMaxValue();
        this.vsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.graphicFanciness = GraphicsFanciness.FANCY;
        this.ambientOcclusionStatus = AmbientOcclusionStatus.MAX;
        this.cloudOption = CloudOption.FANCY;
        this.fov = 70.0;
        this.gamma = 0.0;
        this.guiScale = 0;
        this.particles = ParticleStatus.ALL;
        this.heldItemTooltips = true;
        this.forceUnicodeFont = false;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmartAnimations = false;
        this.ofSmoothFps = false;
        Config.updateAvailableProcessors();
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = false;
        this.ofRenderRegions = false;
        this.ofFastMath = false;
        this.ofFastRender = false;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofAlternateBlocks = true;
        this.ofDynamicLights = 3;
        this.ofScreenshotSize = 1;
        this.ofCustomEntityModels = true;
        this.ofCustomGuis = true;
        this.ofShowGlErrors = true;
        this.ofChatBackground = 0;
        this.ofChatShadow = true;
        this.ofAoLevel = 1.0;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0;
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
        this.ofBetterSnow = false;
        this.ofSwampColors = true;
        this.ofRandomEntities = true;
        this.biomeBlendRadius = 2;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomItems = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofEmissiveTextures = true;
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
        Shaders.setShaderPack("OFF");
        Shaders.configAntialiasingLevel = 0;
        Shaders.uninit();
        Shaders.storeConfig();
        this.mc.scheduleResourcesRefresh();
        this.saveOptions();
    }

    public void updateVSync() {
        if (this.mc.getMainWindow() != null) {
            this.mc.getMainWindow().setVsync(this.vsync);
        }
    }

    public void updateMipmaps() {
        this.mc.setMipmapLevels(this.mipmapLevels);
        this.mc.scheduleResourcesRefresh();
    }

    public void setAllAnimations(boolean bl) {
        int n;
        this.ofAnimatedWater = n = bl ? 0 : 2;
        this.ofAnimatedLava = n;
        this.ofAnimatedFire = bl;
        this.ofAnimatedPortal = bl;
        this.ofAnimatedRedstone = bl;
        this.ofAnimatedExplosion = bl;
        this.ofAnimatedFlame = bl;
        this.ofAnimatedSmoke = bl;
        this.ofVoidParticles = bl;
        this.ofWaterParticles = bl;
        this.ofRainSplash = bl;
        this.ofPortalParticles = bl;
        this.ofPotionParticles = bl;
        this.ofFireworkParticles = bl;
        this.particles = bl ? ParticleStatus.ALL : ParticleStatus.MINIMAL;
        this.ofDrippingWaterLava = bl;
        this.ofAnimatedTerrain = bl;
        this.ofAnimatedTextures = bl;
    }

    private static int nextValue(int n, int[] nArray) {
        int n2 = GameSettings.indexOf(n, nArray);
        if (n2 < 0) {
            return nArray[0];
        }
        if (++n2 >= nArray.length) {
            n2 = 0;
        }
        return nArray[n2];
    }

    private static int limit(int n, int[] nArray) {
        int n2 = GameSettings.indexOf(n, nArray);
        return n2 < 0 ? nArray[0] : n;
    }

    private static int indexOf(int n, int[] nArray) {
        for (int i = 0; i < nArray.length; ++i) {
            if (nArray[i] != n) continue;
            return i;
        }
        return 1;
    }

    private static String getTranslation(String[] stringArray, int n) {
        if (n < 0 || n >= stringArray.length) {
            n = 0;
        }
        return I18n.format(stringArray[n], new Object[0]);
    }

    private void setForgeKeybindProperties() {
        if (Reflector.KeyConflictContext_IN_GAME.exists() && Reflector.ForgeKeyBinding_setKeyConflictContext.exists()) {
            Object object = Reflector.getFieldValue(Reflector.KeyConflictContext_IN_GAME);
            Reflector.call(this.keyBindForward, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindLeft, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindBack, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindRight, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindJump, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindSneak, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindSprint, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindAttack, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindChat, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindPlayerList, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindCommand, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindTogglePerspective, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
            Reflector.call(this.keyBindSmoothCamera, Reflector.ForgeKeyBinding_setKeyConflictContext, object);
        }
    }

    public void fillResourcePackList(ResourcePackList resourcePackList) {
        LinkedHashSet<String> linkedHashSet = Sets.newLinkedHashSet();
        Iterator<String> iterator2 = this.resourcePacks.iterator();
        while (iterator2.hasNext()) {
            String string = iterator2.next();
            ResourcePackInfo resourcePackInfo = resourcePackList.getPackInfo(string);
            if (resourcePackInfo == null && !string.startsWith("file/")) {
                resourcePackInfo = resourcePackList.getPackInfo("file/" + string);
            }
            if (resourcePackInfo == null) {
                LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", (Object)string);
                iterator2.remove();
                continue;
            }
            if (!resourcePackInfo.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains(string)) {
                LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", (Object)string);
                iterator2.remove();
                continue;
            }
            if (resourcePackInfo.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains(string)) {
                LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", (Object)string);
                this.incompatibleResourcePacks.remove(string);
                continue;
            }
            linkedHashSet.add(resourcePackInfo.getName());
        }
        resourcePackList.setEnabledPacks(linkedHashSet);
    }

    public PointOfView getPointOfView() {
        return this.pointOfView;
    }

    public void setPointOfView(PointOfView pointOfView) {
        this.pointOfView = pointOfView;
    }

    private static void lambda$loadOptions$2(CompoundNBT compoundNBT, String string) {
        try {
            Iterator<String> iterator2 = KEY_VALUE_SPLITTER.split(string).iterator();
            compoundNBT.putString(iterator2.next(), iterator2.next());
        } catch (Exception exception) {
            LOGGER.warn("Skipping bad option: {}", (Object)string);
        }
    }

    private boolean lambda$new$1() {
        return this.toggleSprint;
    }

    private boolean lambda$new$0() {
        return this.toggleCrouch;
    }
}

