package xyz.mathax.mathaxclient.systems.themes;

import xyz.mathax.mathaxclient.gui.DefaultSettingsWidgetFactory;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.gui.renderer.packer.GuiTexture;
import xyz.mathax.mathaxclient.gui.widgets.*;
import xyz.mathax.mathaxclient.gui.widgets.containers.*;
import xyz.mathax.mathaxclient.gui.widgets.input.*;
import xyz.mathax.mathaxclient.gui.widgets.pressable.*;
import xyz.mathax.mathaxclient.renderer.text.Fonts;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.gui.screens.accounts.AccountsScreen;
import xyz.mathax.mathaxclient.gui.screens.modules.notebot.NotebotSongsScreen;
import xyz.mathax.mathaxclient.gui.screens.modules.ModuleScreen;
import xyz.mathax.mathaxclient.gui.screens.modules.ModulesScreen;
import xyz.mathax.mathaxclient.gui.screens.modules.proxies.ProxiesScreen;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.renderer.Texture;
import xyz.mathax.mathaxclient.renderer.text.FontFace;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.systems.accounts.Account;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.widgets.input.WDropdownTheme;
import xyz.mathax.mathaxclient.systems.themes.widgets.input.WSliderTheme;
import xyz.mathax.mathaxclient.systems.themes.widgets.input.WTextBoxTheme;
import xyz.mathax.mathaxclient.utils.gui.CharFilter;
import xyz.mathax.mathaxclient.utils.gui.SettingsWidgetFactory;
import xyz.mathax.mathaxclient.utils.gui.WindowConfig;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.misc.Names;
import xyz.mathax.mathaxclient.utils.render.Alignment;
import xyz.mathax.mathaxclient.utils.render.CategoryIcons;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.systems.themes.widgets.*;
import xyz.mathax.mathaxclient.systems.themes.widgets.pressable.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Theme implements ISerializable<Theme> {

    protected SettingsWidgetFactory settingsFactory = new DefaultSettingsWidgetFactory(this);

    protected final Map<String, WindowConfig> windowConfigs = new HashMap<>();

    public static final double TITLE_TEXT_SCALE = 1.25;

    public boolean disableHoverColor;

    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup fontSettings = settings.createGroup("Font");
    private final SettingGroup rainbowSettings = settings.createGroup("Rainbow");
    private final SettingGroup colorSettings = settings.createGroup("Colors");
    private final SettingGroup textColorsSettings = settings.createGroup("Text");
    private final SettingGroup backgroundColorsSettings = settings.createGroup("Background");
    private final SettingGroup outlineSettings = settings.createGroup("Outline");
    private final SettingGroup separatorSettings = settings.createGroup("Separator");
    private final SettingGroup scrollbarSettings = settings.createGroup("Scrollbar");
    private final SettingGroup sliderSettings = settings.createGroup("Slider");

    // General

    public final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("Scale of the GUI.")
            .defaultValue(1)
            .min(0.75)
            .sliderRange(0.75, 4)
            .onSliderRelease()
            .onChanged(value -> {
                if (mc.currentScreen instanceof WidgetScreen) {
                    ((WidgetScreen) mc.currentScreen).invalidate();
                }
            })
            .build()
    );

    /*public final Setting<Double> roundingSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Rounding")
            .description("How much to round the GUI.")
            .defaultValue(12.5)
            .range(0, 25)
            .sliderRange(0, 25)
            .build()
    );*/

    public final Setting<CategoryIcons> categoryIconsSetting = generalSettings.add(new EnumSetting.Builder<CategoryIcons>()
            .name("Category icons")
            .description("Icons in module categories titles.")
            .defaultValue(CategoryIcons.Custom)
            .build()
    );

    public final Setting<Alignment.X> moduleAlignmentSetting = generalSettings.add(new EnumSetting.Builder<Alignment.X>()
            .name("Module alignment")
            .description("How module titles are aligned.")
            .defaultValue(Alignment.X.Center)
            .build()
    );

    public final Setting<Boolean> hideHudSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Hide HUD")
            .description("Hide HUD when in GUI.")
            .defaultValue(false)
            .onChanged(value -> {
                if (mc.currentScreen instanceof WidgetScreen) {
                    mc.options.hudHidden = value;
                }
            })
            .build()
    );

    // Font

    public final Setting<Boolean> customFontSetting = fontSettings.add(new BoolSetting.Builder()
            .name("Custom font")
            .description("Use custom font.")
            .defaultValue(true)
            .onChanged(value -> {
                if (mc.currentScreen instanceof WidgetScreen) {
                    ((WidgetScreen) mc.currentScreen).invalidate();
                }
            })
            .build()
    );

    public final Setting<FontFace> fontSetting = fontSettings.add(new FontFaceSetting.Builder()
            .name("Font")
            .description("Custom font to use.")
            .visible(customFontSetting::get)
            .onChanged(Fonts::load)
            .build()
    );

    public final Setting<Boolean> fontShadowSetting = fontSettings.add(new BoolSetting.Builder()
            .name("Shadow")
            .description("Show shadow behind the font.")
            .defaultValue(false)
            .onChanged(value -> {
                if (mc.currentScreen instanceof WidgetScreen) {
                    ((WidgetScreen) mc.currentScreen).invalidate();
                }
            })
            .build()
    );

    // Rainbow

    public final Setting<Double> rainbowSpeedSetting = rainbowSettings.add(new DoubleSetting.Builder()
            .name("Rainbow speed")
            .description("Global rainbow speed.")
            .defaultValue(1)
            .min(0.001)
            .sliderRange(0.001, 2)
            .build()
    );

    private final Setting<Double> rainbowSaturationSetting = rainbowSettings.add(new DoubleSetting.Builder()
            .name("Rainbow saturation")
            .description("Global rainbow saturation.")
            .defaultValue(1)
            .range(0.001, 1)
            .sliderRange(0.001, 1)
            .build()
    );

    private final Setting<Double> rainbowBrightnessSetting = rainbowSettings.add(new DoubleSetting.Builder()
            .name("Rainbow brightness")
            .description("Global rainbow brightness.")
            .defaultValue(1)
            .range(0.001, 1)
            .sliderRange(0.001, 1)
            .build()
    );

    // Colors

    public final Setting<SettingColor> accentColorSetting = colorSetting("Accent", "Accent color of the GUI.", new SettingColor(Color.MATHAX));

    public final Setting<SettingColor> checkboxColorSetting = colorSetting("Checkbox", "Color of checkbox.", new SettingColor(Color.MATHAX));

    public final Setting<SettingColor> plusColorSetting = colorSetting("Plus", "Color of plus button.", new SettingColor(0, 255, 0));

    public final Setting<SettingColor> minusColorSetting = colorSetting("Minus", "Color of minus button.", new SettingColor(255, 0, 0));

    public final Setting<SettingColor> favoriteColorSetting = colorSetting("Favorite", "Color of checked favorite button.", new SettingColor(255, 255, 0));

    // Text

    public final Setting<SettingColor> textColorSetting = colorSetting(textColorsSettings, "Text", "Color of text.", new SettingColor(255, 255, 255));

    public final Setting<SettingColor> textSecondaryColorSetting = colorSetting(textColorsSettings, "Secondary text", "Color of secondary text.", new SettingColor(150, 150, 150));

    public final Setting<SettingColor> textHighlightColorSetting = colorSetting(textColorsSettings, "Text highlight", "Color of text highlighting.", new SettingColor(Color.MATHAX));

    public final Setting<SettingColor> titleTextColorSetting = colorSetting(textColorsSettings, "Title text", "Color of title text.", new SettingColor(255, 255, 255));

    public final Setting<SettingColor> loggedInColorSetting = colorSetting(textColorsSettings, "Logged in text", "Color of logged in account name in Account Manager.", new SettingColor(45, 225, 45));

    // Background

    public final ThreeStateColorSetting backgroundColorSetting = new ThreeStateColorSetting(backgroundColorsSettings, "Background", new SettingColor(Color.MATHAX_BACKGROUND.r, Color.MATHAX_BACKGROUND.g, Color.MATHAX_BACKGROUND.b, 150), new SettingColor(Color.MATHAX, 135), new SettingColor(Color.MATHAX, 175));

    public final Setting<SettingColor> moduleBackgroundSetting = colorSetting(backgroundColorsSettings, "Module background", "Color of module background when enabled.", new SettingColor(Color.MATHAX, 150));

    // Outline

    public final ThreeStateColorSetting outlineColorSetting = new ThreeStateColorSetting(outlineSettings, "Outline", new SettingColor(125, 125, 125), new SettingColor(150, 150, 150), new SettingColor(175, 175, 175));

    // Separator

    public final Setting<SettingColor> separatorTextSetting = colorSetting(separatorSettings, "Separator text", "Color of separator text", new SettingColor(255, 255, 255));

    public final Setting<SettingColor> separatorCenterSetting = colorSetting(separatorSettings, "Separator center", "Center color of separators.", new SettingColor(255, 255, 255));

    public final Setting<SettingColor> separatorEdgesSetting = colorSetting(separatorSettings, "Separator edges", "Color of separator edges.", new SettingColor(225, 225, 225, 150));

    // Scrollbar

    public final ThreeStateColorSetting scrollbarColorSetting = new ThreeStateColorSetting(scrollbarSettings, "Scrollbar", new SettingColor(255, 100, 125), new SettingColor(Color.MATHAX), new SettingColor(255, 75, 100));

    // Slider

    public final ThreeStateColorSetting sliderHandleSetting = new ThreeStateColorSetting(sliderSettings, "Slider handle", new SettingColor(255, 100, 125), new SettingColor(Color.MATHAX), new SettingColor(255, 75, 100));

    public final Setting<SettingColor> sliderLeftSetting = colorSetting(sliderSettings, "Slider left", "Color of slider left part.", new SettingColor(Color.MATHAX));

    public final Setting<SettingColor> sliderRightSetting = colorSetting(sliderSettings, "Slider right", "Color of slider right part.", new SettingColor(Color.MATHAX_BACKGROUND));

    public void beforeRender() {
        disableHoverColor = false;
    }

    protected <T extends WWidget> T widget(T widget) {
        widget.theme = this;
        return widget;
    }

    public WWindow window(WWidget icon, String title) {
        return widget(new WWindowTheme(icon, title));
    }

    public WWindow window(String title) {
        return window(null, title);
    }

    public WLabel label(String text, boolean title, double maxWidth) {
        if (maxWidth == 0) {
            return widget(new WLabelTheme(text, title));
        }

        return widget(new WMultiLabelTheme(text, title, maxWidth));
    }

    public WLabel label(String text, boolean title) {
        return label(text, title, 0);
    }
    public WLabel label(String text, double maxWidth) {
        return label(text, false, maxWidth);
    }
    public WLabel label(String text) {
        return label(text, false);
    }

    public WHorizontalSeparatorTheme horizontalSeparator(String text) {
        return widget(new WHorizontalSeparatorTheme(text));
    }

    public WHorizontalSeparatorTheme horizontalSeparator() {
        return horizontalSeparator(null);
    }

    public WVerticalSeparatorTheme verticalSeparator() {
        return widget(new WVerticalSeparatorTheme());
    }

    protected WButton button(String text, GuiTexture texture) {
        return widget(new WButtonTheme(text, texture));
    }

    public WButton button(String text) {
        return button(text, null);
    }

    public WButton button(GuiTexture texture) {
        return button(null, texture);
    }

    public WMinus minus() {
        return widget(new WMinusTheme());
    }

    public WPlus plus() {
        return widget(new WPlusTheme());
    }

    public WCheckbox checkbox(boolean checked) {
        return widget(new WCheckboxTheme(checked));
    }

    public WSlider slider(double value, double min, double max) {
        return widget(new WSliderTheme(value, min, max));
    }

    public WTextBox textBox(String text, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
        return widget(new WTextBoxTheme(text, filter, renderer));
    }

    public WTextBox textBox(String text, CharFilter filter) {
        return textBox(text, filter, null);
    }

    public WTextBox textBox(String text) {
        return textBox(text, (text1, c) -> true, null);
    }

    public <T> WDropdown<T> dropdown(T[] values, T value) {
        return widget(new WDropdownTheme<>(values, value));
    }

    public <T extends Enum<?>> WDropdown<T> dropdown(T value) {
        Class<?> klass = value.getClass();
        T[] values = null;
        try {
            values = (T[]) klass.getDeclaredMethod("values").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            exception.printStackTrace();
        }

        return dropdown(values, value);
    }

    public WTriangle triangle() {
        return widget(new WTriangleTheme());
    }

    public WTooltip tooltip(String text) {
        return widget(new WTooltipTheme(text));
    }

    public WView view() {
        return widget(new WViewTheme());
    }

    public WVerticalList verticalList() {
        return widget(new WVerticalList());
    }

    public WHorizontalList horizontalList() {
        return widget(new WHorizontalList());
    }

    public WTable table() {
        return widget(new WTable());
    }

    public WSection section(String title, boolean expanded, WWidget headerWidget) {
        return widget(new WSectionTheme(title, expanded, headerWidget));
    }

    public WSection section(String title, boolean expanded) {
        return section(title, expanded, null);
    }

    public WSection section(String title) {
        return section(title, true);
    }

    public WAccount account(WidgetScreen screen, Account<?> account) {
        return widget(new WAccountTheme(screen, account));
    }

    public WWidget module(Module module) {
        return widget(new WModuleTheme(module));
    }

    public WQuad quad(Color color) {
        return widget(new WQuadTheme(color));
    }

    public WTopBar topBar() {
        return widget(new WTopBarTheme());
    }

    public WFavorite favorite(boolean checked) {
        return widget(new WFavoriteTheme(checked));
    }


    public WItem item(ItemStack itemStack) {
        return widget(new WItem(itemStack));
    }

    public WItemWithLabel itemWithLabel(ItemStack stack, String name) {
        return widget(new WItemWithLabel(stack, name));
    }

    public WItemWithLabel itemWithLabel(ItemStack stack) {
        return itemWithLabel(stack, Names.get(stack.getItem()));
    }

    public WTexture texture(double width, double height, double rotation, Texture texture) {
        return widget(new WTexture(width, height, rotation, texture));
    }

    public WGuiTexture guiTexture(double width, double height, double rotation, GuiTexture guiTexture) {
        return widget(new WGuiTexture(width, height, rotation, guiTexture));
    }

    public WIntEdit intEdit(int value, int min, int max, int sliderMin, int sliderMax, boolean noSlider) {
        return widget(new WIntEdit(value, min, max, sliderMin, sliderMax, noSlider));
    }

    public WIntEdit intEdit(int value, int min, int max, int sliderMin, int sliderMax) {
        return widget(new WIntEdit(value, min, max, sliderMin, sliderMax, false));
    }

    public WIntEdit intEdit(int value, int min, int max, boolean noSlider) {
        return widget(new WIntEdit(value, min, max, 0, 0, noSlider));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max, double sliderMin, double sliderMax, int decimalPlaces, boolean noSlider) {
        return widget(new WDoubleEdit(value, min, max, sliderMin, sliderMax, decimalPlaces, noSlider));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max, double sliderMin, double sliderMax) {
        return widget(new WDoubleEdit(value, min, max, sliderMin, sliderMax, 3, false));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max) {
        return widget(new WDoubleEdit(value, min, max, 0, 10, 3, false));
    }

    public WBlockPosEdit blockPosEdit(BlockPos value) {
        return widget(new WBlockPosEdit(value));
    }

    public WKeybind keybind(KeyBind keybind) {
        return keybind(keybind, KeyBind.none());
    }

    public WKeybind keybind(KeyBind keybind, KeyBind defaultValue) {
        return widget(new WKeybind(keybind, defaultValue));
    }

    public WWidget settings(Settings settings, String filter) {
        return settingsFactory.create(this, settings, filter);
    }

    public WWidget settings(Settings settings) {
        return settings(settings, "");
    }

    public TabScreen modulesScreen() {
        return new ModulesScreen(this);
    }

    public boolean isModulesScreen(Screen screen) {
        return screen instanceof ModulesScreen;
    }

    public WidgetScreen moduleScreen(Module module) {
        return new ModuleScreen(this, module);
    }

    public WidgetScreen accountsScreen() {
        return new AccountsScreen(this);
    }

    public NotebotSongsScreen notebotSongs() {
        return new NotebotSongsScreen(this);
    }

    public WidgetScreen proxiesScreen() {
        return new ProxiesScreen(this);
    }

    public Color textColor() {
        return textColorSetting.get();
    }

    public Color textSecondaryColor() {
        return textSecondaryColorSetting.get();
    }

    public TextRenderer textRenderer() {
        return TextRenderer.get();
    }

    public double scale(double value) {
        return value * scaleSetting.get();
    }

    public double scale() {
        return scale(1);
    }

    public void resetScale() {
        scaleSetting.reset();
    }

    public double rounding() {
        return /*roundSetting.get() ? roundingSetting.get() :*/ 0;
    }

    public CategoryIcons categoryIcons() {
        return categoryIconsSetting.get();
    }

    public boolean hideHud() {
        return hideHudSetting.get();
    }

    public boolean customFont() {
        return customFontSetting.get();
    }

    public FontFace font() {
        return fontSetting.get();
    }

    public boolean fontShadow() {
        return fontShadowSetting.get();
    }

    public double rainbowSpeed() {
        return rainbowSpeedSetting.get();
    }

    public double rainbowSaturation() {
        return rainbowSaturationSetting.get();
    }

    public double rainbowBrightness() {
        return rainbowBrightnessSetting.get();
    }

    public double textWidth(String text, int length, boolean title, boolean shadow) {
        return scale(textRenderer().getWidth(text, length, shadow) * (title ? TITLE_TEXT_SCALE : 1));
    }

    public double textWidth(String text, boolean title, boolean shadow) {
        return textWidth(text, text.length(), title, shadow);
    }

    public double textWidth(String text) {
        return textWidth(text, text.length(), false, false);
    }

    public double textHeight(boolean title, boolean shadow) {
        return scale(textRenderer().getHeight(shadow) * (title ? TITLE_TEXT_SCALE : 1));
    }

    public double textHeight(boolean shadow) {
        return textHeight(false, shadow);
    }

    public double textHeight() {
        return textHeight(false);
    }

    public double pad() {
        return scale(6);
    }

    public WindowConfig getWindowConfig(String id) {
        WindowConfig config = windowConfigs.get(id);
        if (config != null) {
            return config;
        }

        config = new WindowConfig();
        windowConfigs.put(id, config);
        return config;
    }

    public void clearWindowConfigs() {
        windowConfigs.clear();
    }

    public void save(File file) {
        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        JSONUtils.saveJSON(json, file);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        json.put("window-configs", new JSONArray());

        windowConfigs.forEach((id, config) -> {
            JSONObject windowConfigJson = new JSONObject();
            windowConfigJson.put("id", id);
            windowConfigJson.put("config", config.toJson());
            json.append("window-configs", windowConfigJson);
        });

        return json;
    }

    public void load(File file) {
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);
    }

    @Override
    public Theme fromJson(JSONObject json) {
        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("window-configs") && JSONUtils.isValidJSONArray(json, "window-configs")) {
            for (Object object : json.getJSONArray("window-configs")) {
                if (object instanceof JSONObject windowConfigJson) {
                    if (windowConfigJson.has("id") && windowConfigJson.has("config")) {
                        windowConfigs.put(windowConfigJson.getString("id"), new WindowConfig(windowConfigJson.getJSONObject("config")));
                    }
                }
            }
        }

        return this;
    }

    private Setting<SettingColor> colorSetting(SettingGroup group, String name, String description, SettingColor color) {
        return group.add(new ColorSetting.Builder().name(name + " color").description(description).defaultValue(color).build());
    }

    private Setting<SettingColor> colorSetting(String name, String description, SettingColor color) {
        return colorSetting(colorSettings, name, description, color);
    }

    public class ThreeStateColorSetting {
        private final Setting<SettingColor> normal, hovered, pressed;

        public ThreeStateColorSetting(SettingGroup group, String name, SettingColor color1, SettingColor color2, SettingColor color3) {
            normal = colorSetting(group, name, "Color of " + name + ".", color1);
            hovered = colorSetting(group, "Hovered " + name, "Color of " + name + " when hovered.", color2);
            pressed = colorSetting(group, "Pressed " + name, "Color of " + name + " when pressed.", color3);
        }

        public SettingColor get() {
            return normal.get();
        }

        public SettingColor get(boolean pressed, boolean hovered, boolean bypassDisableHoverColor) {
            if (pressed) {
                return this.pressed.get();
            }

            return (hovered && (bypassDisableHoverColor || !disableHoverColor)) ? this.hovered.get() : this.normal.get();
        }

        public SettingColor get(boolean pressed, boolean hovered) {
            return get(pressed, hovered, false);
        }
    }
}
