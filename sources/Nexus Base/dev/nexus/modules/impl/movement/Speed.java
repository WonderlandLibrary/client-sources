package dev.nexus.modules.impl.movement;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventLiving;
import dev.nexus.events.impl.EventStrafe;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.ModeSetting;
import dev.nexus.utils.game.MoveUtil;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    public final ModeSetting speedMode = new ModeSetting("Mode", "Watchdog Ground", "Watchdog Ground", "Watchdog Strafe");

    public Speed() {
        super("Speed", Keyboard.KEY_Z, ModuleCategory.MOVEMENT);
        this.addSettings(speedMode);
    }

    @EventLink
    public final Listener<EventLiving> eventLivingListener = event -> {
        this.setSuffix(speedMode.getMode());
    };

    @EventLink
    public final Listener<EventStrafe> eventStrafeListener = event -> {
        if (isNull()) {
            return;
        }
        if (!MoveUtil.isMoving()) {
            return;
        }
        switch (speedMode.getMode()) {
            case "Watchdog Ground":
                if (mc.thePlayer.onGround) {
                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.jump();
                    }
                    MoveUtil.strafe(0.415);
                }
                break;
        }
    };

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
