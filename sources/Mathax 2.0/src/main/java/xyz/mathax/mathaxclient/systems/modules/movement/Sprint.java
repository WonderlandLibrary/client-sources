package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class Sprint extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> whenStationarySetting = generalSettings.add(new BoolSetting.Builder()
            .name("When stationary")
            .description("Continues sprinting even if you do not move.")
            .defaultValue(true)
            .build()
    );

    public Sprint(Category category) {
        super(category, "Sprint", "Automatically sprints.");
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player.forwardSpeed > 0 && !whenStationarySetting.get()) {
            mc.player.setSprinting(true);
        } else if (whenStationarySetting.get()) {
            mc.player.setSprinting(true);
        }
    }
}