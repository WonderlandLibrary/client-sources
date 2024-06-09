package dev.vertic.module.impl.movement;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.other.StrafeEvent;
import dev.vertic.event.impl.other.TickEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.util.player.MoveUtil;
import dev.vertic.util.player.PlayerUtil;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    private final BooleanSetting multiDir = new BooleanSetting("All Directions", false);
    private final BooleanSetting onlyInAir = new BooleanSetting("Only In Air", multiDir::isEnabled, false);
    private int onGroundTicks;

    public Sprint() {
        super("Sprint", "Makes the player sprint.", Category.MOVEMENT);
        this.addSettings(multiDir, onlyInAir);
        this.enable();
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
        mc.thePlayer.omniSprint = false;
    }

    @EventLink
    public void onStrafe(StrafeEvent event) {
        if (mc.thePlayer.onGround) {
            onGroundTicks += 1;
        } else {
            onGroundTicks = 0;
        }

        mc.gameSettings.keyBindSprint.setPressed(true);
        mc.thePlayer.omniSprint = multiDir.isEnabled()
                && !mc.thePlayer.isSneaking()
                && MoveUtil.isMoving()
                && !mc.thePlayer.isCollidedHorizontally
                && (!onlyInAir.isEnabled() || onGroundTicks < 2);
    }

}
