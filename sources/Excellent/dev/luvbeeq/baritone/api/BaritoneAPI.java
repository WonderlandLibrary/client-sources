package dev.luvbeeq.baritone.api;

import dev.luvbeeq.baritone.BaritoneProvider;
import dev.luvbeeq.baritone.api.utils.SettingsUtil;

/**
 * Exposes the {@link IBaritoneProvider} instance and the {@link Settings} instance for API usage.
 *
 * @author Brady
 * @since 9/23/2018
 */
public final class BaritoneAPI {

    private static IBaritoneProvider provider;
    private static Settings settings;

    public static void init() {
        settings = new Settings();
        SettingsUtil.readAndApply(settings, SettingsUtil.SETTINGS_DEFAULT_NAME);
        provider = new BaritoneProvider();
        //System.out.println("Baritone -> initializing.");
    }

    public static IBaritoneProvider getProvider() {
        return BaritoneAPI.provider;
    }

    public static Settings getSettings() {
        return BaritoneAPI.settings;
    }
}
