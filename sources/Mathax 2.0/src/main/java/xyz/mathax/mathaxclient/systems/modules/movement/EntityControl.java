package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class EntityControl extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Boolean> maxJumpSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Max jump")
            .description("Sets jump power to maximum.")
            .defaultValue(true)
            .build()
    );

    public EntityControl(Category category) {
        super(category, "Entity Control", "Lets you control rideable entities without a saddle.");
    }
}
