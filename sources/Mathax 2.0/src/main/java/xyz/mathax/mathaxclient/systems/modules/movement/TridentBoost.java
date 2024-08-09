package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class TridentBoost extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Double> multiplierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Boost")
            .description("How much your velocity is multiplied by when using riptide.")
            .defaultValue(2)
            .min(0.1)
            .sliderRange(1, 3)
            .build()
    );

    private final Setting<Boolean> allowOutOfWaterSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Out of water")
            .description("Whether riptide should work out of water")
            .defaultValue(true)
            .build()
    );

    public TridentBoost(Category category) {
        super(category, "Trident Boost", "Boosts you when using riptide with a trident.");
    }

    public double getMultiplier() {
        return isEnabled() ? multiplierSetting.get() : 1;
    }

    public boolean allowOutOfWater() {
        return isEnabled() ? allowOutOfWaterSetting.get() : false;
    }
}