package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.BlockBreakingCooldownEvent;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class BreakDelay extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Integer> cooldownSetting = generalSettings.add(new IntSetting.Builder()
            .name("Cooldown")
            .description("Block break cooldown in ticks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    public BreakDelay(Category category) {
        super(category, "Break Delay", "Changes the delay between breaking blocks.");
    }

    @EventHandler
    private void onBlockBreakingCooldown(BlockBreakingCooldownEvent event) {
        event.cooldown = cooldownSetting.get();
    }
}