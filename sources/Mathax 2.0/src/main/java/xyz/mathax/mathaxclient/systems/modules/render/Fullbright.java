package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class Fullbright extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("The mode to use for Fullbright.")
            .defaultValue(Mode.Gamma)
            .build()
    );

    private final Setting<Integer> minimumLightLevelSetting = generalSettings.add(new IntSetting.Builder()
            .name("Minimum light level")
            .description("Minimum light level when using Luminance mode.")
            .visible(() -> modeSetting.get() == Mode.Luminance)
            .defaultValue(8)
            .range(0, 15)
            .sliderRange(0, 15)
            .onChanged(integer -> {
                if (mc.worldRenderer != null) {
                    mc.worldRenderer.reload();
                }
            })
            .build()
    );

    public Fullbright(Category category) {
        super(category, "Fullbright", "Lights up your world.");
    }

    @Override
    public void onEnable() {
        if (modeSetting.get() == Mode.Luminance) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    public void onDisable() {
        if (modeSetting.get() == Mode.Luminance) {
            mc.worldRenderer.reload();
        }
    }

    public int getLuminance() {
        if (!isEnabled() || modeSetting.get() != Mode.Luminance) {
            return 0;
        }

        return minimumLightLevelSetting.get();
    }

    public boolean getGamma() {
        return isEnabled() && modeSetting.get() == Mode.Gamma;
    }

    public enum Mode {
        Gamma("Gamma"),
        Luminance("Luminance");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
