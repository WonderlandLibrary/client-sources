package mathax.client.gui;

import mathax.client.gui.renderer.packer.GuiTexture;
import mathax.client.gui.screens.proxies.ProxiesScreen;
import mathax.client.gui.screens.accounts.AccountsScreen;
import mathax.client.gui.screens.clickgui.ModuleScreen;
import mathax.client.gui.screens.clickgui.ModulesScreen;
import mathax.client.gui.tabs.TabScreen;
import mathax.client.gui.utils.CharFilter;
import mathax.client.gui.utils.SettingsWidgetFactory;
import mathax.client.gui.utils.WindowConfig;
import mathax.client.gui.widgets.*;
import mathax.client.gui.widgets.containers.*;
import mathax.client.gui.widgets.input.*;
import mathax.client.gui.widgets.pressable.*;
import mathax.client.renderer.Texture;
import mathax.client.renderer.text.TextRenderer;
import mathax.client.settings.Settings;
import mathax.client.systems.accounts.Account;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.ISerializable;
import mathax.client.utils.misc.KeyBind;
import mathax.client.utils.misc.Names;
import mathax.client.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class GuiTheme implements ISerializable<GuiTheme> {
    public static final double TITLE_TEXT_SCALE = 1.25;

    public final String name;
    public final Settings settings = new Settings();

    public boolean disableHoverColor;

    protected SettingsWidgetFactory settingsFactory;

    protected final Map<String, WindowConfig> windowConfigs = new HashMap<>();

    public GuiTheme(String name) {
        this.name = name;
    }

    public void beforeRender() {
        disableHoverColor = false;
    }

    // Widgets

    public abstract WWindow window(WWidget icon, String title);

    public WWindow window(String title) {
        return window(null, title);
    }

    public abstract WLabel label(String text, boolean title, double maxWidth);
    public WLabel label(String text, boolean title) {
        return label(text, title, 0);
    }
    public WLabel label(String text, double maxWidth) {
        return label(text, false, maxWidth);
    }
    public WLabel label(String text) {
        return label(text, false);
    }

    public abstract WHorizontalSeparator horizontalSeparator(String text);
    public WHorizontalSeparator horizontalSeparator() {
        return horizontalSeparator(null);
    }
    public abstract WVerticalSeparator verticalSeparator();

    protected abstract WButton button(String text, GuiTexture texture);
    public WButton button(String text) {
        return button(text, null);
    }
    public WButton button(GuiTexture texture) {
        return button(null, texture);
    }

    public abstract WMinus minus();
    public abstract WPlus plus();

    public abstract WCheckbox checkbox(boolean checked);

    public abstract WSlider slider(double value, double min, double max);

    public abstract WTextBox textBox(String text, CharFilter filter);
    public abstract WTextBox textBox(String text, String placeholder, CharFilter filter, Class<? extends WTextBox.Renderer> renderer);
    public WTextBox textBox(String text, String placeholder) {
        return textBox(text, placeholder, (text1, c) -> true, null);
    }
    public WTextBox textBox(String text) {
        return textBox(text, (text1, c) -> true);
    }

    public abstract <T> WDropdown<T> dropdown(T[] values, T value);
    public <T extends Enum<?>> WDropdown<T> dropdown(T value) {
        Class<?> klass = value.getClass();
        T[] values = null;
        try {
            values = (T[]) klass.getDeclaredMethod("values").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return dropdown(values, value);
    }

    public abstract WTriangle triangle();

    public abstract WTooltip tooltip(String text);

    public abstract WView view();

    public WVerticalList verticalList() {
        return w(new WVerticalList());
    }
    public WHorizontalList horizontalList() {
        return w(new WHorizontalList());
    }
    public WTable table() {
        return w(new WTable());
    }

    public abstract WSection section(String title, boolean expanded, WWidget headerWidget);
    public WSection section(String title, boolean expanded) {
        return section(title, expanded, null);
    }
    public WSection section(String title) {
        return section(title, true);
    }

    public abstract WAccount account(WidgetScreen screen, Account<?> account);

    public abstract WWidget module(Module module);

    public abstract WQuad quad(Color color);

    public abstract WTopBar topBar();

    public abstract WFavorite favorite(boolean checked);

    public WItem item(ItemStack itemStack) {
        return w(new WItem(itemStack));
    }
    public WItemWithLabel itemWithLabel(ItemStack stack, String name) {
        return w(new WItemWithLabel(stack, name));
    }
    public WItemWithLabel itemWithLabel(ItemStack stack) {
        return itemWithLabel(stack, Names.get(stack.getItem()));
    }

    public WTexture texture(double width, double height, double rotation, Texture texture) {
        return w(new WTexture(width, height, rotation, texture));
    }

    public WIntEdit intEdit(int value, int min, int max, int sliderMin, int sliderMax, boolean noSlider) {
        return w(new WIntEdit(value, min, max, sliderMin, sliderMax, noSlider));
    }

    public WIntEdit intEdit(int value, int min, int max, int sliderMin, int sliderMax) {
        return w(new WIntEdit(value, min, max, sliderMin, sliderMax, false));
    }

    public WIntEdit intEdit(int value, int min, int max, boolean noSlider) {
        return w(new WIntEdit(value, min, max, 0, 0, noSlider));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max, double sliderMin, double sliderMax, int decimalPlaces, boolean noSlider) {
        return w(new WDoubleEdit(value, min, max, sliderMin, sliderMax, decimalPlaces, noSlider));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max, double sliderMin, double sliderMax) {
        return w(new WDoubleEdit(value, min, max, sliderMin, sliderMax, 3, false));
    }

    public WDoubleEdit doubleEdit(double value, double min, double max) {
        return w(new WDoubleEdit(value, min, max, 0, 10, 3, false));
    }

    public WBlockPosEdit blockPosEdit(BlockPos value) {
        return w(new WBlockPosEdit(value));
    }

    public WKeyBind keybind(KeyBind keybind) {
        return keybind(keybind, KeyBind.none());
    }

    public WKeyBind keybind(KeyBind keybind, KeyBind defaultValue) {
        return w(new WKeyBind(keybind, defaultValue));
    }

    public WWidget settings(Settings settings, String filter) {
        return settingsFactory.create(this, settings, filter);
    }
    public WWidget settings(Settings settings) {
        return settings(settings, "");
    }

    // Screens

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

    public WidgetScreen proxiesScreen() {
        return new ProxiesScreen(this);
    }

    // Colors

    public abstract Color textColor();

    public abstract Color textSecondaryColor();

    // Other

    public abstract TextRenderer textRenderer();

    public abstract double scale(double value);

    public abstract void resetScale();

    public abstract boolean categoryIcons();

    public abstract int roundAmount();

    public abstract boolean hideHUD();

    public double textWidth(String text, int length, boolean title) {
        return scale(textRenderer().getWidth(text, length, false) * (title ? TITLE_TEXT_SCALE : 1));
    }
    public double textWidth(String text) {
        return textWidth(text, text.length(), false);
    }

    public double textHeight(boolean title) {
        return scale(textRenderer().getHeight() * (title ? TITLE_TEXT_SCALE : 1));
    }
    public double textHeight() {
        return textHeight(false);
    }

    public double pad() {
        return scale(6);
    }

    public WindowConfig getWindowConfig(String id) {
        WindowConfig config = windowConfigs.get(id);
        if (config != null) return config;

        config = new WindowConfig();
        windowConfigs.put(id, config);
        return config;
    }

    public void clearWindowConfigs() {
        windowConfigs.clear();
    }

    protected <T extends WWidget> T w(T widget) {
        widget.theme = this;
        return widget;
    }

    // Saving / Loading

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("name", name);
        tag.put("settings", settings.toTag());

        NbtCompound configs = new NbtCompound();
        for (String id : windowConfigs.keySet()) {
            configs.put(id, windowConfigs.get(id).toTag());
        }
        tag.put("windowConfigs", configs);

        return tag;
    }

    @Override
    public GuiTheme fromTag(NbtCompound tag) {
        settings.fromTag(tag.getCompound("settings"));

        NbtCompound configs = tag.getCompound("windowConfigs");
        for (String id : configs.getKeys()) {
            windowConfigs.put(id, new WindowConfig().fromTag(configs.getCompound(id)));
        }

        return this;
    }
}
