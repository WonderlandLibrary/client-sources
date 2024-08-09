package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class Reach extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Double> reachSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Reach")
            .description("Your reach modifier.")
            .defaultValue(5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    public Reach(Category category) {
        super(category, "Reach", "Gives you super long arms.");
    }

    public float getReach() {
        if (!isEnabled()) {
            return mc.interactionManager.getCurrentGameMode().isCreative() ? 5.0F : 4.5F;
        }

        return reachSetting.get().floatValue();
    }
}
