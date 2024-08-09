package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class UnfocusedCPU extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Integer> fpsSetting = generalSettings.add(new IntSetting.Builder()
            .name("Target FPS")
            .description("Target FPS to set as the limit when the window is not focused.")
            .min(1)
            .defaultValue(1)
            .sliderRange(1, 30)
            .build()
    );

    public UnfocusedCPU(Category category) {
        super(category, "Unfocused CPU", "Saves performance by limiting FPS when your Minecraft window is not focused.");
    }
}
