package xyz.mathax.mathaxclient.utils.render.color;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.init.PostInit;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoint;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoints;
import xyz.mathax.mathaxclient.utils.misc.UnorderedArrayList;

import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class RainbowColors {
    private static final List<Setting<SettingColor>> colorSettings = new UnorderedArrayList<>();
    private static final List<Setting<List<SettingColor>>> colorListSettings = new UnorderedArrayList<>();

    private static final List<SettingColor> colors = new UnorderedArrayList<>();
    private static final List<Runnable> listeners = new UnorderedArrayList<>();

    public static final RainbowColor GLOBAL = new RainbowColor();

    @PostInit
    public static void init() {
        MatHax.EVENT_BUS.subscribe(RainbowColors.class);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void addSettingList(Setting<List<SettingColor>> setting) {
        colorListSettings.add(setting);
    }

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void removeSettingList(Setting<List<SettingColor>> setting) {
        colorListSettings.remove(setting);
    }

    public static void add(SettingColor color) {
        colors.add(color);
    }

    public static void register(Runnable runnable) {
        listeners.add(runnable);
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        Theme theme = Systems.get(Themes.class).getTheme();
        GLOBAL.setSpeed(theme.rainbowSpeed() / 100);
        GLOBAL.setSaturation(theme.rainbowSaturation());
        GLOBAL.setBrightness(theme.rainbowBrightness());
        GLOBAL.getNext();

        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isEnabled()) {
                setting.get().update();
            }
        }

        for (Setting<List<SettingColor>> setting : colorListSettings) {
            if (setting.module == null || setting.module.isEnabled()) {
                for (SettingColor color : setting.get()) {
                    color.update();
                }
            }
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.colorSetting.get().update();
        }

        if (mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup group : Systems.get(Themes.class).getTheme().settings) {
                for (Setting<?> setting : group) {
                    if (setting instanceof ColorSetting) {
                        ((SettingColor) setting.get()).update();
                    }
                }
            }
        }

        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
