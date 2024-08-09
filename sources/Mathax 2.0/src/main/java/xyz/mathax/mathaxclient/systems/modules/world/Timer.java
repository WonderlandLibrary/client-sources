package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class Timer extends Module {
    public static final double OFF = 1;
    private double override = 1;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Double> multiplierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Multiplier")
            .description("The timer multiplier amount.")
            .defaultValue(1)
            .min(0.1)
            .sliderRange(0.1, 2.5)
            .build()
    );

    public Timer(Category category) {
        super(category, "Timer", "Changes the speed of everything in your game.");
    }

    public double getMultiplier() {
        return override != OFF ? override : (isEnabled() ? multiplierSetting.get() : OFF);
    }

    public void setOverride(double override) {
        this.override = override;
    }
}