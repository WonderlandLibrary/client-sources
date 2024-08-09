package xyz.mathax.mathaxclient.utils.entity.fakeplayer;

import xyz.mathax.mathaxclient.settings.*;

public class FakePlayerSettings {
    public static final Settings settings = new Settings();

    private static final SettingGroup onSpawnSettings = settings.createGroup("On Spawn");

    // General

    public static final Setting<String> nameSetting = onSpawnSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the fake player.")
            .defaultValue("Matejko06")
            .build()
    );

    public static final Setting<Boolean> copyInventorySetting = onSpawnSettings.add(new BoolSetting.Builder()
            .name("Copy inventory")
            .description("Copy your inventory to the fake player.")
            .defaultValue(true)
            .build()
    );

    public static final Setting<Integer> healthSetting = onSpawnSettings.add(new IntSetting.Builder()
            .name("Health")
            .description("The fake player's default health.")
            .defaultValue(20)
            .min(1)
            .sliderRange(1, 36)
            .build()
    );
}
