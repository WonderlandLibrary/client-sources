/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.settings.SliderMultiplierOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.config.IteratableOptionOF;
import net.optifine.config.SliderPercentageOptionOF;

public abstract class AbstractOption {
    public static final SliderPercentageOption BIOME_BLEND_RADIUS = new SliderPercentageOption("options.biomeBlendRadius", 0.0, 7.0, 1.0f, AbstractOption::lambda$static$0, AbstractOption::lambda$static$1, AbstractOption::lambda$static$2);
    public static final SliderPercentageOption CHAT_HEIGHT_FOCUSED = new SliderPercentageOption("options.chat.height.focused", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$3, AbstractOption::lambda$static$4, AbstractOption::lambda$static$5);
    public static final SliderPercentageOption CHAT_HEIGHT_UNFOCUSED = new SliderPercentageOption("options.chat.height.unfocused", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$6, AbstractOption::lambda$static$7, AbstractOption::lambda$static$8);
    public static final SliderPercentageOption CHAT_OPACITY = new SliderPercentageOption("options.chat.opacity", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$9, AbstractOption::lambda$static$10, AbstractOption::lambda$static$11);
    public static final SliderPercentageOption CHAT_SCALE = new SliderPercentageOption("options.chat.scale", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$12, AbstractOption::lambda$static$13, AbstractOption::lambda$static$14);
    public static final SliderPercentageOption CHAT_WIDTH = new SliderPercentageOption("options.chat.width", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$15, AbstractOption::lambda$static$16, AbstractOption::lambda$static$17);
    public static final SliderPercentageOption LINE_SPACING = new SliderPercentageOption("options.chat.line_spacing", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$18, AbstractOption::lambda$static$19, AbstractOption::lambda$static$20);
    public static final SliderPercentageOption DELAY_INSTANT = new SliderPercentageOption("options.chat.delay_instant", 0.0, 6.0, 0.1f, AbstractOption::lambda$static$21, AbstractOption::lambda$static$22, AbstractOption::lambda$static$23);
    public static final SliderPercentageOption FOV = new SliderPercentageOption("options.fov", 30.0, 110.0, 1.0f, AbstractOption::lambda$static$24, AbstractOption::lambda$static$25, AbstractOption::lambda$static$26);
    private static final ITextComponent FOV_EFFECT_SCALE_TOOLTIP = new TranslationTextComponent("options.fovEffectScale.tooltip");
    public static final SliderPercentageOption FOV_EFFECT_SCALE_SLIDER = new SliderPercentageOption("options.fovEffectScale", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$27, AbstractOption::lambda$static$28, AbstractOption::lambda$static$29);
    private static final ITextComponent SCREEN_EFFECT_SCALE_TOOLTIP = new TranslationTextComponent("options.screenEffectScale.tooltip");
    public static final SliderPercentageOption SCREEN_EFFECT_SCALE_SLIDER = new SliderPercentageOption("options.screenEffectScale", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$30, AbstractOption::lambda$static$31, AbstractOption::lambda$static$32);
    public static final SliderPercentageOption FRAMERATE_LIMIT = new SliderPercentageOption("options.framerateLimit", 0.0, 260.0, 5.0f, AbstractOption::lambda$static$33, AbstractOption::lambda$static$34, AbstractOption::lambda$static$35);
    public static final SliderPercentageOption GAMMA = new SliderPercentageOption("options.gamma", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$36, AbstractOption::lambda$static$37, AbstractOption::lambda$static$38);
    public static final SliderPercentageOption MIPMAP_LEVELS = new SliderPercentageOption("options.mipmapLevels", 0.0, 4.0, 1.0f, AbstractOption::lambda$static$39, AbstractOption::lambda$static$40, AbstractOption::lambda$static$41);
    public static final SliderPercentageOption MOUSE_WHEEL_SENSITIVITY = new SliderMultiplierOption("options.mouseWheelSensitivity", 0.01, 10.0, 0.01f, AbstractOption::lambda$static$42, AbstractOption::lambda$static$43, AbstractOption::lambda$static$44);
    public static final BooleanOption RAW_MOUSE_INPUT = new BooleanOption("options.rawMouseInput", AbstractOption::lambda$static$45, AbstractOption::lambda$static$46);
    public static final SliderPercentageOption RENDER_DISTANCE = new SliderPercentageOption("options.renderDistance", 2.0, 16.0, 1.0f, AbstractOption::lambda$static$47, AbstractOption::lambda$static$48, AbstractOption::lambda$static$49);
    public static final SliderPercentageOption ENTITY_DISTANCE_SCALING = new SliderPercentageOption("options.entityDistanceScaling", 0.5, 5.0, 0.25f, AbstractOption::lambda$static$50, AbstractOption::lambda$static$51, AbstractOption::lambda$static$52);
    public static final SliderPercentageOption SENSITIVITY = new SliderPercentageOption("options.sensitivity", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$53, AbstractOption::lambda$static$54, AbstractOption::lambda$static$55);
    public static final SliderPercentageOption ACCESSIBILITY_TEXT_BACKGROUND_OPACITY = new SliderPercentageOption("options.accessibility.text_background_opacity", 0.0, 1.0, 0.0f, AbstractOption::lambda$static$56, AbstractOption::lambda$static$57, AbstractOption::lambda$static$58);
    public static final IteratableOption AO = new IteratableOption("options.ao", AbstractOption::lambda$static$59, AbstractOption::lambda$static$60);
    public static final IteratableOption ATTACK_INDICATOR = new IteratableOption("options.attackIndicator", AbstractOption::lambda$static$61, AbstractOption::lambda$static$62);
    public static final IteratableOption CHAT_VISIBILITY = new IteratableOption("options.chat.visibility", AbstractOption::lambda$static$63, AbstractOption::lambda$static$64);
    private static final ITextComponent FAST_GRAPHICS = new TranslationTextComponent("options.graphics.fast.tooltip");
    private static final ITextComponent FABULOUS_GRAPHICS = new TranslationTextComponent("options.graphics.fabulous.tooltip", new TranslationTextComponent("options.graphics.fabulous").mergeStyle(TextFormatting.ITALIC));
    private static final ITextComponent FANCY_GRAPHICS = new TranslationTextComponent("options.graphics.fancy.tooltip");
    public static final IteratableOption GRAPHICS = new IteratableOption("options.graphics", AbstractOption::lambda$static$65, AbstractOption::lambda$static$66);
    public static final IteratableOption GUI_SCALE = new IteratableOption("options.guiScale", AbstractOption::lambda$static$67, AbstractOption::lambda$static$68);
    public static final IteratableOption MAIN_HAND = new IteratableOption("options.mainHand", AbstractOption::lambda$static$69, AbstractOption::lambda$static$70);
    public static final IteratableOption NARRATOR = new IteratableOption("options.narrator", AbstractOption::lambda$static$71, AbstractOption::lambda$static$72);
    public static final IteratableOption PARTICLES = new IteratableOption("options.particles", AbstractOption::lambda$static$73, AbstractOption::lambda$static$74);
    public static final IteratableOption RENDER_CLOUDS = new IteratableOption("options.renderClouds", AbstractOption::lambda$static$75, AbstractOption::lambda$static$76);
    public static final IteratableOption ACCESSIBILITY_TEXT_BACKGROUND = new IteratableOption("options.accessibility.text_background", AbstractOption::lambda$static$77, AbstractOption::lambda$static$78);
    private static final ITextComponent field_244787_ad = new TranslationTextComponent("options.hideMatchedNames.tooltip");
    public static final BooleanOption AUTO_JUMP = new BooleanOption("options.autoJump", AbstractOption::lambda$static$79, AbstractOption::lambda$static$80);
    public static final BooleanOption AUTO_SUGGEST_COMMANDS = new BooleanOption("options.autoSuggestCommands", AbstractOption::lambda$static$81, AbstractOption::lambda$static$82);
    public static final BooleanOption field_244786_G = new BooleanOption("options.hideMatchedNames", field_244787_ad, AbstractOption::lambda$static$83, AbstractOption::lambda$static$84);
    public static final BooleanOption CHAT_COLOR = new BooleanOption("options.chat.color", AbstractOption::lambda$static$85, AbstractOption::lambda$static$86);
    public static final BooleanOption CHAT_LINKS = new BooleanOption("options.chat.links", AbstractOption::lambda$static$87, AbstractOption::lambda$static$88);
    public static final BooleanOption CHAT_LINKS_PROMPT = new BooleanOption("options.chat.links.prompt", AbstractOption::lambda$static$89, AbstractOption::lambda$static$90);
    public static final BooleanOption DISCRETE_MOUSE_SCROLL = new BooleanOption("options.discrete_mouse_scroll", AbstractOption::lambda$static$91, AbstractOption::lambda$static$92);
    public static final BooleanOption VSYNC = new BooleanOption("options.vsync", AbstractOption::lambda$static$93, AbstractOption::lambda$static$94);
    public static final BooleanOption ENTITY_SHADOWS = new BooleanOption("options.entityShadows", AbstractOption::lambda$static$95, AbstractOption::lambda$static$96);
    public static final BooleanOption FORCE_UNICODE_FONT = new BooleanOption("options.forceUnicodeFont", AbstractOption::lambda$static$97, AbstractOption::lambda$static$98);
    public static final BooleanOption INVERT_MOUSE = new BooleanOption("options.invertMouse", AbstractOption::lambda$static$99, AbstractOption::lambda$static$100);
    public static final BooleanOption REALMS_NOTIFICATIONS = new BooleanOption("options.realmsNotifications", AbstractOption::lambda$static$101, AbstractOption::lambda$static$102);
    public static final BooleanOption REDUCED_DEBUG_INFO = new BooleanOption("options.reducedDebugInfo", AbstractOption::lambda$static$103, AbstractOption::lambda$static$104);
    public static final BooleanOption SHOW_SUBTITLES = new BooleanOption("options.showSubtitles", AbstractOption::lambda$static$105, AbstractOption::lambda$static$106);
    public static final BooleanOption SNOOPER = new BooleanOption("options.snooper", AbstractOption::lambda$static$107, AbstractOption::lambda$static$108);
    public static final IteratableOption SNEAK = new IteratableOption("key.sneak", AbstractOption::lambda$static$109, AbstractOption::lambda$static$110);
    public static final IteratableOption SPRINT = new IteratableOption("key.sprint", AbstractOption::lambda$static$111, AbstractOption::lambda$static$112);
    public static final BooleanOption TOUCHSCREEN = new BooleanOption("options.touchscreen", AbstractOption::lambda$static$113, AbstractOption::lambda$static$114);
    public static final BooleanOption FULLSCREEN = new BooleanOption("options.fullscreen", AbstractOption::lambda$static$115, AbstractOption::lambda$static$116);
    public static final BooleanOption VIEW_BOBBING = new BooleanOption("options.viewBobbing", AbstractOption::lambda$static$117, AbstractOption::lambda$static$118);
    private final ITextComponent translatedBaseMessage;
    private Optional<List<IReorderingProcessor>> optionValues = Optional.empty();
    private final String translationKey;
    public static final IteratableOption FOG_FANCY = new IteratableOptionOF("of.options.FOG_FANCY");
    public static final IteratableOption FOG_START = new IteratableOptionOF("of.options.FOG_START");
    public static final SliderPercentageOption MIPMAP_TYPE = new SliderPercentageOptionOF("of.options.MIPMAP_TYPE", 0.0, 3.0, 1.0f);
    public static final IteratableOption SMOOTH_FPS = new IteratableOptionOF("of.options.SMOOTH_FPS");
    public static final IteratableOption CLOUDS = new IteratableOptionOF("of.options.CLOUDS");
    public static final SliderPercentageOption CLOUD_HEIGHT = new SliderPercentageOptionOF("of.options.CLOUD_HEIGHT");
    public static final IteratableOption TREES = new IteratableOptionOF("of.options.TREES");
    public static final IteratableOption RAIN = new IteratableOptionOF("of.options.RAIN");
    public static final IteratableOption ANIMATED_WATER = new IteratableOptionOF("of.options.ANIMATED_WATER");
    public static final IteratableOption ANIMATED_LAVA = new IteratableOptionOF("of.options.ANIMATED_LAVA");
    public static final IteratableOption ANIMATED_FIRE = new IteratableOptionOF("of.options.ANIMATED_FIRE");
    public static final IteratableOption ANIMATED_PORTAL = new IteratableOptionOF("of.options.ANIMATED_PORTAL");
    public static final SliderPercentageOption AO_LEVEL = new SliderPercentageOptionOF("of.options.AO_LEVEL");
    public static final IteratableOption LAGOMETER = new IteratableOptionOF("of.options.LAGOMETER");
    public static final IteratableOption SHOW_FPS = new IteratableOptionOF("of.options.SHOW_FPS");
    public static final IteratableOption AUTOSAVE_TICKS = new IteratableOptionOF("of.options.AUTOSAVE_TICKS");
    public static final IteratableOption BETTER_GRASS = new IteratableOptionOF("of.options.BETTER_GRASS");
    public static final IteratableOption ANIMATED_REDSTONE = new IteratableOptionOF("of.options.ANIMATED_REDSTONE");
    public static final IteratableOption ANIMATED_EXPLOSION = new IteratableOptionOF("of.options.ANIMATED_EXPLOSION");
    public static final IteratableOption ANIMATED_FLAME = new IteratableOptionOF("of.options.ANIMATED_FLAME");
    public static final IteratableOption ANIMATED_SMOKE = new IteratableOptionOF("of.options.ANIMATED_SMOKE");
    public static final IteratableOption WEATHER = new IteratableOptionOF("of.options.WEATHER");
    public static final IteratableOption SKY = new IteratableOptionOF("of.options.SKY");
    public static final IteratableOption STARS = new IteratableOptionOF("of.options.STARS");
    public static final IteratableOption SUN_MOON = new IteratableOptionOF("of.options.SUN_MOON");
    public static final IteratableOption VIGNETTE = new IteratableOptionOF("of.options.VIGNETTE");
    public static final IteratableOption CHUNK_UPDATES = new IteratableOptionOF("of.options.CHUNK_UPDATES");
    public static final IteratableOption CHUNK_UPDATES_DYNAMIC = new IteratableOptionOF("of.options.CHUNK_UPDATES_DYNAMIC");
    public static final IteratableOption TIME = new IteratableOptionOF("of.options.TIME");
    public static final IteratableOption SMOOTH_WORLD = new IteratableOptionOF("of.options.SMOOTH_WORLD");
    public static final IteratableOption VOID_PARTICLES = new IteratableOptionOF("of.options.VOID_PARTICLES");
    public static final IteratableOption WATER_PARTICLES = new IteratableOptionOF("of.options.WATER_PARTICLES");
    public static final IteratableOption RAIN_SPLASH = new IteratableOptionOF("of.options.RAIN_SPLASH");
    public static final IteratableOption PORTAL_PARTICLES = new IteratableOptionOF("of.options.PORTAL_PARTICLES");
    public static final IteratableOption POTION_PARTICLES = new IteratableOptionOF("of.options.POTION_PARTICLES");
    public static final IteratableOption FIREWORK_PARTICLES = new IteratableOptionOF("of.options.FIREWORK_PARTICLES");
    public static final IteratableOption PROFILER = new IteratableOptionOF("of.options.PROFILER");
    public static final IteratableOption DRIPPING_WATER_LAVA = new IteratableOptionOF("of.options.DRIPPING_WATER_LAVA");
    public static final IteratableOption BETTER_SNOW = new IteratableOptionOF("of.options.BETTER_SNOW");
    public static final IteratableOption ANIMATED_TERRAIN = new IteratableOptionOF("of.options.ANIMATED_TERRAIN");
    public static final IteratableOption SWAMP_COLORS = new IteratableOptionOF("of.options.SWAMP_COLORS");
    public static final IteratableOption RANDOM_ENTITIES = new IteratableOptionOF("of.options.RANDOM_ENTITIES");
    public static final IteratableOption SMOOTH_BIOMES = new IteratableOptionOF("of.options.SMOOTH_BIOMES");
    public static final IteratableOption CUSTOM_FONTS = new IteratableOptionOF("of.options.CUSTOM_FONTS");
    public static final IteratableOption CUSTOM_COLORS = new IteratableOptionOF("of.options.CUSTOM_COLORS");
    public static final IteratableOption SHOW_CAPES = new IteratableOptionOF("of.options.SHOW_CAPES");
    public static final IteratableOption CONNECTED_TEXTURES = new IteratableOptionOF("of.options.CONNECTED_TEXTURES");
    public static final IteratableOption CUSTOM_ITEMS = new IteratableOptionOF("of.options.CUSTOM_ITEMS");
    public static final SliderPercentageOption AA_LEVEL = new SliderPercentageOptionOF("of.options.AA_LEVEL", 0.0, 16.0, new double[]{0.0, 2.0, 4.0, 6.0, 8.0, 12.0, 16.0});
    public static final SliderPercentageOption AF_LEVEL = new SliderPercentageOptionOF("of.options.AF_LEVEL", 1.0, 16.0, new double[]{1.0, 2.0, 4.0, 8.0, 16.0});
    public static final IteratableOption ANIMATED_TEXTURES = new IteratableOptionOF("of.options.ANIMATED_TEXTURES");
    public static final IteratableOption NATURAL_TEXTURES = new IteratableOptionOF("of.options.NATURAL_TEXTURES");
    public static final IteratableOption EMISSIVE_TEXTURES = new IteratableOptionOF("of.options.EMISSIVE_TEXTURES");
    public static final IteratableOption HELD_ITEM_TOOLTIPS = new IteratableOptionOF("of.options.HELD_ITEM_TOOLTIPS");
    public static final IteratableOption DROPPED_ITEMS = new IteratableOptionOF("of.options.DROPPED_ITEMS");
    public static final IteratableOption LAZY_CHUNK_LOADING = new IteratableOptionOF("of.options.LAZY_CHUNK_LOADING");
    public static final IteratableOption CUSTOM_SKY = new IteratableOptionOF("of.options.CUSTOM_SKY");
    public static final IteratableOption FAST_MATH = new IteratableOptionOF("of.options.FAST_MATH");
    public static final IteratableOption FAST_RENDER = new IteratableOptionOF("of.options.FAST_RENDER");
    public static final IteratableOption TRANSLUCENT_BLOCKS = new IteratableOptionOF("of.options.TRANSLUCENT_BLOCKS");
    public static final IteratableOption DYNAMIC_FOV = new IteratableOptionOF("of.options.DYNAMIC_FOV");
    public static final IteratableOption DYNAMIC_LIGHTS = new IteratableOptionOF("of.options.DYNAMIC_LIGHTS");
    public static final IteratableOption ALTERNATE_BLOCKS = new IteratableOptionOF("of.options.ALTERNATE_BLOCKS");
    public static final IteratableOption CUSTOM_ENTITY_MODELS = new IteratableOptionOF("of.options.CUSTOM_ENTITY_MODELS");
    public static final IteratableOption ADVANCED_TOOLTIPS = new IteratableOptionOF("of.options.ADVANCED_TOOLTIPS");
    public static final IteratableOption SCREENSHOT_SIZE = new IteratableOptionOF("of.options.SCREENSHOT_SIZE");
    public static final IteratableOption CUSTOM_GUIS = new IteratableOptionOF("of.options.CUSTOM_GUIS");
    public static final IteratableOption RENDER_REGIONS = new IteratableOptionOF("of.options.RENDER_REGIONS");
    public static final IteratableOption SHOW_GL_ERRORS = new IteratableOptionOF("of.options.SHOW_GL_ERRORS");
    public static final IteratableOption SMART_ANIMATIONS = new IteratableOptionOF("of.options.SMART_ANIMATIONS");
    public static final IteratableOption CHAT_BACKGROUND = new IteratableOptionOF("of.options.CHAT_BACKGROUND");
    public static final IteratableOption CHAT_SHADOW = new IteratableOptionOF("of.options.CHAT_SHADOW");

    public AbstractOption(String string) {
        this.translatedBaseMessage = new TranslationTextComponent(string);
        this.translationKey = string;
    }

    public abstract Widget createWidget(GameSettings var1, int var2, int var3, int var4);

    public ITextComponent getBaseMessageTranslation() {
        return this.translatedBaseMessage;
    }

    public void setOptionValues(List<IReorderingProcessor> list) {
        this.optionValues = Optional.of(list);
    }

    public Optional<List<IReorderingProcessor>> getOptionValues() {
        return this.optionValues;
    }

    protected ITextComponent getPixelValueComponent(int n) {
        return new TranslationTextComponent("options.pixel_value", this.getBaseMessageTranslation(), n);
    }

    protected ITextComponent getPercentValueComponent(double d) {
        return new TranslationTextComponent("options.percent_value", this.getBaseMessageTranslation(), (int)(d * 100.0));
    }

    protected ITextComponent getPercentageAddMessage(int n) {
        return new TranslationTextComponent("options.percent_add_value", this.getBaseMessageTranslation(), n);
    }

    public ITextComponent getGenericValueComponent(ITextComponent iTextComponent) {
        return new TranslationTextComponent("options.generic_value", this.getBaseMessageTranslation(), iTextComponent);
    }

    public ITextComponent getMessageWithValue(int n) {
        return this.getGenericValueComponent(new StringTextComponent(Integer.toString(n)));
    }

    public String getResourceKey() {
        return this.translationKey;
    }

    private static void lambda$static$118(GameSettings gameSettings, Boolean bl) {
        gameSettings.viewBobbing = bl;
    }

    private static boolean lambda$static$117(GameSettings gameSettings) {
        return gameSettings.viewBobbing;
    }

    private static void lambda$static$116(GameSettings gameSettings, Boolean bl) {
        gameSettings.fullscreen = bl;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getMainWindow() != null && minecraft.getMainWindow().isFullscreen() != gameSettings.fullscreen) {
            minecraft.getMainWindow().toggleFullscreen();
            gameSettings.fullscreen = minecraft.getMainWindow().isFullscreen();
        }
    }

    private static boolean lambda$static$115(GameSettings gameSettings) {
        return gameSettings.fullscreen;
    }

    private static void lambda$static$114(GameSettings gameSettings, Boolean bl) {
        gameSettings.touchscreen = bl;
    }

    private static boolean lambda$static$113(GameSettings gameSettings) {
        return gameSettings.touchscreen;
    }

    private static ITextComponent lambda$static$112(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.toggleSprint ? "options.key.toggle" : "options.key.hold"));
    }

    private static void lambda$static$111(GameSettings gameSettings, Integer n) {
        gameSettings.toggleSprint = !gameSettings.toggleSprint;
    }

    private static ITextComponent lambda$static$110(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.toggleCrouch ? "options.key.toggle" : "options.key.hold"));
    }

    private static void lambda$static$109(GameSettings gameSettings, Integer n) {
        gameSettings.toggleCrouch = !gameSettings.toggleCrouch;
    }

    private static void lambda$static$108(GameSettings gameSettings, Boolean bl) {
        gameSettings.snooper = bl;
    }

    private static boolean lambda$static$107(GameSettings gameSettings) {
        if (gameSettings.snooper) {
            // empty if block
        }
        return true;
    }

    private static void lambda$static$106(GameSettings gameSettings, Boolean bl) {
        gameSettings.showSubtitles = bl;
    }

    private static boolean lambda$static$105(GameSettings gameSettings) {
        return gameSettings.showSubtitles;
    }

    private static void lambda$static$104(GameSettings gameSettings, Boolean bl) {
        gameSettings.reducedDebugInfo = bl;
    }

    private static boolean lambda$static$103(GameSettings gameSettings) {
        return gameSettings.reducedDebugInfo;
    }

    private static void lambda$static$102(GameSettings gameSettings, Boolean bl) {
        gameSettings.realmsNotifications = bl;
    }

    private static boolean lambda$static$101(GameSettings gameSettings) {
        return gameSettings.realmsNotifications;
    }

    private static void lambda$static$100(GameSettings gameSettings, Boolean bl) {
        gameSettings.invertMouse = bl;
    }

    private static boolean lambda$static$99(GameSettings gameSettings) {
        return gameSettings.invertMouse;
    }

    private static void lambda$static$98(GameSettings gameSettings, Boolean bl) {
        gameSettings.forceUnicodeFont = bl;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getMainWindow() != null) {
            minecraft.forceUnicodeFont(bl);
        }
    }

    private static boolean lambda$static$97(GameSettings gameSettings) {
        return gameSettings.forceUnicodeFont;
    }

    private static void lambda$static$96(GameSettings gameSettings, Boolean bl) {
        gameSettings.entityShadows = bl;
    }

    private static boolean lambda$static$95(GameSettings gameSettings) {
        return gameSettings.entityShadows;
    }

    private static void lambda$static$94(GameSettings gameSettings, Boolean bl) {
        gameSettings.vsync = bl;
        if (Minecraft.getInstance().getMainWindow() != null) {
            Minecraft.getInstance().getMainWindow().setVsync(gameSettings.vsync);
        }
    }

    private static boolean lambda$static$93(GameSettings gameSettings) {
        return gameSettings.vsync;
    }

    private static void lambda$static$92(GameSettings gameSettings, Boolean bl) {
        gameSettings.discreteMouseScroll = bl;
    }

    private static boolean lambda$static$91(GameSettings gameSettings) {
        return gameSettings.discreteMouseScroll;
    }

    private static void lambda$static$90(GameSettings gameSettings, Boolean bl) {
        gameSettings.chatLinksPrompt = bl;
    }

    private static boolean lambda$static$89(GameSettings gameSettings) {
        return gameSettings.chatLinksPrompt;
    }

    private static void lambda$static$88(GameSettings gameSettings, Boolean bl) {
        gameSettings.chatLinks = bl;
    }

    private static boolean lambda$static$87(GameSettings gameSettings) {
        return gameSettings.chatLinks;
    }

    private static void lambda$static$86(GameSettings gameSettings, Boolean bl) {
        gameSettings.chatColor = bl;
    }

    private static boolean lambda$static$85(GameSettings gameSettings) {
        return gameSettings.chatColor;
    }

    private static void lambda$static$84(GameSettings gameSettings, Boolean bl) {
        gameSettings.field_244794_ae = bl;
    }

    private static boolean lambda$static$83(GameSettings gameSettings) {
        return gameSettings.field_244794_ae;
    }

    private static void lambda$static$82(GameSettings gameSettings, Boolean bl) {
        gameSettings.autoSuggestCommands = bl;
    }

    private static boolean lambda$static$81(GameSettings gameSettings) {
        return gameSettings.autoSuggestCommands;
    }

    private static void lambda$static$80(GameSettings gameSettings, Boolean bl) {
        gameSettings.autoJump = bl;
    }

    private static boolean lambda$static$79(GameSettings gameSettings) {
        return gameSettings.autoJump;
    }

    private static ITextComponent lambda$static$78(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.accessibilityTextBackground ? "options.accessibility.text_background.chat" : "options.accessibility.text_background.everywhere"));
    }

    private static void lambda$static$77(GameSettings gameSettings, Integer n) {
        gameSettings.accessibilityTextBackground = !gameSettings.accessibilityTextBackground;
    }

    private static ITextComponent lambda$static$76(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.cloudOption.getKey()));
    }

    private static void lambda$static$75(GameSettings gameSettings, Integer n) {
        Framebuffer framebuffer;
        gameSettings.cloudOption = CloudOption.byId(gameSettings.cloudOption.getId() + n);
        if (Minecraft.isFabulousGraphicsEnabled() && (framebuffer = Minecraft.getInstance().worldRenderer.func_239232_u_()) != null) {
            framebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        }
    }

    private static ITextComponent lambda$static$74(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.particles.getResourceKey()));
    }

    private static void lambda$static$73(GameSettings gameSettings, Integer n) {
        gameSettings.particles = ParticleStatus.byId(gameSettings.particles.getId() + n);
    }

    private static ITextComponent lambda$static$72(GameSettings gameSettings, IteratableOption iteratableOption) {
        return NarratorChatListener.INSTANCE.isActive() ? iteratableOption.getGenericValueComponent(gameSettings.narrator.func_238233_b_()) : iteratableOption.getGenericValueComponent(new TranslationTextComponent("options.narrator.notavailable"));
    }

    private static void lambda$static$71(GameSettings gameSettings, Integer n) {
        gameSettings.narrator = NarratorChatListener.INSTANCE.isActive() ? NarratorStatus.byId(gameSettings.narrator.getId() + n) : NarratorStatus.OFF;
        NarratorChatListener.INSTANCE.announceMode(gameSettings.narrator);
    }

    private static ITextComponent lambda$static$70(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(gameSettings.mainHand.getHandName());
    }

    private static void lambda$static$69(GameSettings gameSettings, Integer n) {
        gameSettings.mainHand = gameSettings.mainHand.opposite();
    }

    private static ITextComponent lambda$static$68(GameSettings gameSettings, IteratableOption iteratableOption) {
        return gameSettings.guiScale == 0 ? iteratableOption.getGenericValueComponent(new TranslationTextComponent("options.guiScale.auto")) : iteratableOption.getMessageWithValue(gameSettings.guiScale);
    }

    private static void lambda$static$67(GameSettings gameSettings, Integer n) {
        gameSettings.guiScale = MathHelper.normalizeAngle(gameSettings.guiScale + n, Minecraft.getInstance().getMainWindow().calcGuiScale(0, Minecraft.getInstance().getForceUnicodeFont()) + 1);
    }

    private static ITextComponent lambda$static$66(GameSettings gameSettings, IteratableOption iteratableOption) {
        switch (1.$SwitchMap$net$minecraft$client$settings$GraphicsFanciness[gameSettings.graphicFanciness.ordinal()]) {
            case 1: {
                iteratableOption.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(FAST_GRAPHICS, 200));
                break;
            }
            case 2: {
                iteratableOption.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(FANCY_GRAPHICS, 200));
                break;
            }
            case 3: {
                iteratableOption.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(FABULOUS_GRAPHICS, 200));
            }
        }
        TranslationTextComponent translationTextComponent = new TranslationTextComponent(gameSettings.graphicFanciness.func_238164_b_());
        return gameSettings.graphicFanciness == GraphicsFanciness.FABULOUS ? iteratableOption.getGenericValueComponent(translationTextComponent.mergeStyle(TextFormatting.ITALIC)) : iteratableOption.getGenericValueComponent(translationTextComponent);
    }

    private static void lambda$static$65(GameSettings gameSettings, Integer n) {
        Minecraft minecraft = Minecraft.getInstance();
        GPUWarning gPUWarning = minecraft.getGPUWarning();
        if (gameSettings.graphicFanciness == GraphicsFanciness.FANCY && gPUWarning.func_241695_b_()) {
            gPUWarning.func_241697_d_();
        } else {
            gameSettings.graphicFanciness = gameSettings.graphicFanciness.func_238166_c_();
            if (gameSettings.graphicFanciness == GraphicsFanciness.FABULOUS && (Config.isShaders() || !GLX.isUsingFBOs() || !GlStateManager.isFabulous() || gPUWarning.func_241701_h_())) {
                gameSettings.graphicFanciness = GraphicsFanciness.FAST;
            }
            gameSettings.updateRenderClouds();
            minecraft.worldRenderer.loadRenderers();
        }
    }

    private static ITextComponent lambda$static$64(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.chatVisibility.getResourceKey()));
    }

    private static void lambda$static$63(GameSettings gameSettings, Integer n) {
        gameSettings.chatVisibility = ChatVisibility.getValue((gameSettings.chatVisibility.getId() + n) % 3);
    }

    private static ITextComponent lambda$static$62(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.attackIndicator.getResourceKey()));
    }

    private static void lambda$static$61(GameSettings gameSettings, Integer n) {
        gameSettings.attackIndicator = AttackIndicatorStatus.byId(gameSettings.attackIndicator.getId() + n);
    }

    private static ITextComponent lambda$static$60(GameSettings gameSettings, IteratableOption iteratableOption) {
        return iteratableOption.getGenericValueComponent(new TranslationTextComponent(gameSettings.ambientOcclusionStatus.getResourceKey()));
    }

    private static void lambda$static$59(GameSettings gameSettings, Integer n) {
        gameSettings.ambientOcclusionStatus = AmbientOcclusionStatus.getValue(gameSettings.ambientOcclusionStatus.getId() + n);
        Minecraft.getInstance().worldRenderer.loadRenderers();
    }

    private static ITextComponent lambda$static$58(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        return sliderPercentageOption.getPercentValueComponent(sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings)));
    }

    private static void lambda$static$57(GameSettings gameSettings, Double d) {
        gameSettings.accessibilityTextBackgroundOpacity = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$56(GameSettings gameSettings) {
        return gameSettings.accessibilityTextBackgroundOpacity;
    }

    private static ITextComponent lambda$static$55(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        if (d == 0.0) {
            return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.sensitivity.min"));
        }
        return d == 1.0 ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.sensitivity.max")) : sliderPercentageOption.getPercentValueComponent(2.0 * d);
    }

    private static void lambda$static$54(GameSettings gameSettings, Double d) {
        gameSettings.mouseSensitivity = d;
    }

    private static Double lambda$static$53(GameSettings gameSettings) {
        return gameSettings.mouseSensitivity;
    }

    private static ITextComponent lambda$static$52(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        return sliderPercentageOption.getPercentValueComponent(d);
    }

    private static void lambda$static$51(GameSettings gameSettings, Double d) {
        gameSettings.entityDistanceScaling = (float)d.doubleValue();
    }

    private static Double lambda$static$50(GameSettings gameSettings) {
        return gameSettings.entityDistanceScaling;
    }

    private static ITextComponent lambda$static$49(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.chunks", (int)d));
    }

    private static void lambda$static$48(GameSettings gameSettings, Double d) {
        gameSettings.renderDistanceChunks = (int)d.doubleValue();
        Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty();
    }

    private static Double lambda$static$47(GameSettings gameSettings) {
        return gameSettings.renderDistanceChunks;
    }

    private static void lambda$static$46(GameSettings gameSettings, Boolean bl) {
        gameSettings.rawMouseInput = bl;
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        if (mainWindow != null) {
            mainWindow.setRawMouseInput(bl);
        }
    }

    private static boolean lambda$static$45(GameSettings gameSettings) {
        return gameSettings.rawMouseInput;
    }

    private static ITextComponent lambda$static$44(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return sliderPercentageOption.getGenericValueComponent(new StringTextComponent(String.format("%.2f", sliderPercentageOption.denormalizeValue(d))));
    }

    private static void lambda$static$43(GameSettings gameSettings, Double d) {
        gameSettings.mouseWheelSensitivity = d;
    }

    private static Double lambda$static$42(GameSettings gameSettings) {
        return gameSettings.mouseWheelSensitivity;
    }

    private static ITextComponent lambda$static$41(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        if (d >= 4.0) {
            return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("of.general.max"));
        }
        return d == 0.0 ? DialogTexts.getComposedOptionMessage(sliderPercentageOption.getBaseMessageTranslation(), false) : sliderPercentageOption.getMessageWithValue((int)d);
    }

    private static void lambda$static$40(GameSettings gameSettings, Double d) {
        gameSettings.mipmapLevels = (int)d.doubleValue();
        gameSettings.updateMipmaps();
    }

    private static Double lambda$static$39(GameSettings gameSettings) {
        return gameSettings.mipmapLevels;
    }

    private static ITextComponent lambda$static$38(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        if (d == 0.0) {
            return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.gamma.min"));
        }
        return d == 1.0 ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.gamma.max")) : sliderPercentageOption.getPercentageAddMessage((int)(d * 100.0));
    }

    private static void lambda$static$37(GameSettings gameSettings, Double d) {
        gameSettings.gamma = d == 1.0 ? 1000.0 : d;
    }

    private static Double lambda$static$36(GameSettings gameSettings) {
        return gameSettings.gamma;
    }

    private static ITextComponent lambda$static$35(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        if (gameSettings.vsync) {
            return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("of.options.framerateLimit.vsync"));
        }
        double d = sliderPercentageOption.get(gameSettings);
        return d == sliderPercentageOption.getMaxValue() ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.framerateLimit.max")) : sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.framerate", (int)d));
    }

    private static void lambda$static$34(GameSettings gameSettings, Double d) {
        gameSettings.framerateLimit = (int)d.doubleValue();
        gameSettings.vsync = false;
        if (gameSettings.framerateLimit <= 0) {
            gameSettings.framerateLimit = 260;
            gameSettings.vsync = true;
        }
        gameSettings.updateVSync();
        Minecraft.getInstance().getMainWindow().setFramerateLimit(gameSettings.framerateLimit);
    }

    private static Double lambda$static$33(GameSettings gameSettings) {
        return gameSettings.vsync ? 0.0 : (double)gameSettings.framerateLimit;
    }

    private static ITextComponent lambda$static$32(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        sliderPercentageOption.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(SCREEN_EFFECT_SCALE_TOOLTIP, 200));
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return d == 0.0 ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.screenEffectScale.off")) : sliderPercentageOption.getPercentValueComponent(d);
    }

    private static void lambda$static$31(GameSettings gameSettings, Double d) {
        gameSettings.screenEffectScale = d.floatValue();
    }

    private static Double lambda$static$30(GameSettings gameSettings) {
        return gameSettings.screenEffectScale;
    }

    private static ITextComponent lambda$static$29(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        sliderPercentageOption.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(FOV_EFFECT_SCALE_TOOLTIP, 200));
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return d == 0.0 ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.fovEffectScale.off")) : sliderPercentageOption.getPercentValueComponent(d);
    }

    private static void lambda$static$28(GameSettings gameSettings, Double d) {
        gameSettings.fovScaleEffect = MathHelper.sqrt(d);
    }

    private static Double lambda$static$27(GameSettings gameSettings) {
        return Math.pow(gameSettings.fovScaleEffect, 2.0);
    }

    private static ITextComponent lambda$static$26(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        if (d == 70.0) {
            return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.fov.min"));
        }
        return d == sliderPercentageOption.getMaxValue() ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.fov.max")) : sliderPercentageOption.getMessageWithValue((int)d);
    }

    private static void lambda$static$25(GameSettings gameSettings, Double d) {
        gameSettings.fov = d;
    }

    private static Double lambda$static$24(GameSettings gameSettings) {
        return gameSettings.fov;
    }

    private static ITextComponent lambda$static$23(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        return d <= 0.0 ? new TranslationTextComponent("options.chat.delay_none") : new TranslationTextComponent("options.chat.delay", String.format("%.1f", d));
    }

    private static void lambda$static$22(GameSettings gameSettings, Double d) {
        gameSettings.chatDelay = d;
    }

    private static Double lambda$static$21(GameSettings gameSettings) {
        return gameSettings.chatDelay;
    }

    private static ITextComponent lambda$static$20(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        return sliderPercentageOption.getPercentValueComponent(sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings)));
    }

    private static void lambda$static$19(GameSettings gameSettings, Double d) {
        gameSettings.chatLineSpacing = d;
    }

    private static Double lambda$static$18(GameSettings gameSettings) {
        return gameSettings.chatLineSpacing;
    }

    private static ITextComponent lambda$static$17(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return sliderPercentageOption.getPixelValueComponent(NewChatGui.calculateChatboxWidth(d * 4.0571431));
    }

    private static void lambda$static$16(GameSettings gameSettings, Double d) {
        d = d * 4.0571431;
        gameSettings.chatWidth = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$15(GameSettings gameSettings) {
        return gameSettings.chatWidth / 4.0571431;
    }

    private static ITextComponent lambda$static$14(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return d == 0.0 ? DialogTexts.getComposedOptionMessage(sliderPercentageOption.getBaseMessageTranslation(), false) : sliderPercentageOption.getPercentValueComponent(d);
    }

    private static void lambda$static$13(GameSettings gameSettings, Double d) {
        gameSettings.chatScale = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$12(GameSettings gameSettings) {
        return gameSettings.chatScale;
    }

    private static ITextComponent lambda$static$11(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return sliderPercentageOption.getPercentValueComponent(d * 0.9 + 0.1);
    }

    private static void lambda$static$10(GameSettings gameSettings, Double d) {
        gameSettings.chatOpacity = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$9(GameSettings gameSettings) {
        return gameSettings.chatOpacity;
    }

    private static ITextComponent lambda$static$8(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return sliderPercentageOption.getPixelValueComponent(NewChatGui.calculateChatboxHeight(d));
    }

    private static void lambda$static$7(GameSettings gameSettings, Double d) {
        gameSettings.chatHeightUnfocused = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$6(GameSettings gameSettings) {
        return gameSettings.chatHeightUnfocused;
    }

    private static ITextComponent lambda$static$5(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings));
        return sliderPercentageOption.getPixelValueComponent(NewChatGui.calculateChatboxHeight(d));
    }

    private static void lambda$static$4(GameSettings gameSettings, Double d) {
        gameSettings.chatHeightFocused = d;
        Minecraft.getInstance().ingameGUI.getChatGUI().refreshChat();
    }

    private static Double lambda$static$3(GameSettings gameSettings) {
        return gameSettings.chatHeightFocused;
    }

    private static ITextComponent lambda$static$2(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        double d = sliderPercentageOption.get(gameSettings);
        int n = (int)d * 2 + 1;
        return sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.biomeBlendRadius." + n));
    }

    private static void lambda$static$1(GameSettings gameSettings, Double d) {
        gameSettings.biomeBlendRadius = MathHelper.clamp((int)d.doubleValue(), 0, 7);
        Minecraft.getInstance().worldRenderer.loadRenderers();
    }

    private static Double lambda$static$0(GameSettings gameSettings) {
        return gameSettings.biomeBlendRadius;
    }
}

