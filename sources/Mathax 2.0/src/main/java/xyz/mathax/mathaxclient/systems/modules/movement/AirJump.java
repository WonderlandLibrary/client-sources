package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Freecam;
import xyz.mathax.mathaxclient.utils.input.KeyAction;

public class AirJump extends Module {
    private int level;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> maintainLevelSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Maintain Y level")
            .description("Maintain your current Y level when holding the jump key.")
            .defaultValue(false)
            .build()
    );

    public AirJump(Category category) {
        super(category, "Air Jump", "Lets you jump in the air.");
    }

    @Override
    public void onEnable() {
        level = mc.player.getBlockPos().getY();
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (Modules.get().isEnabled(Freecam.class) || mc.currentScreen != null || mc.player.isOnGround()) {
            return;
        }

        if (event.action != KeyAction.Press) {
            return;
        }

        if (mc.options.jumpKey.matchesKey(event.key, 0)) {
            level = mc.player.getBlockPos().getY();
            mc.player.jump();
        } else if (mc.options.sneakKey.matchesKey(event.key, 0)) {
            level--;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (Modules.get().isEnabled(Freecam.class) || mc.player.isOnGround()) {
            return;
        }

        if (maintainLevelSetting.get() && mc.player.getBlockPos().getY() == level && mc.options.jumpKey.isPressed()) {
            mc.player.jump();
        }
    }
}