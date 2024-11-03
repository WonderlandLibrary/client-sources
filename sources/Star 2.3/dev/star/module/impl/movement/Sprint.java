package dev.star.module.impl.movement;

import dev.star.Client;
import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.impl.player.NoSlow;
import dev.star.module.settings.impl.BooleanSetting;

public class Sprint extends Module {

    private final BooleanSetting omniSprint = new BooleanSetting("Omni Sprint", false);

    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Sprints automatically");
        this.addSettings(omniSprint);
    }

    @Override
    public void onMotionEvent(MotionEvent event) {
        if (Client.INSTANCE.getModuleCollection().get(Scaffold.class).isEnabled()) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
            return;
        }
        if (omniSprint.isEnabled()) {
            mc.thePlayer.setSprinting(true);
        } else {
            if (mc.thePlayer.isUsingItem()) {
                if (mc.thePlayer.moveForward > 0 && (Client.INSTANCE.isEnabled(NoSlow.class) || !mc.thePlayer.isUsingItem()) && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
                    mc.thePlayer.setSprinting(true);
                }
            } else {
                mc.gameSettings.keyBindSprint.pressed = true;
            }
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        mc.gameSettings.keyBindSprint.pressed = false;
        super.onDisable();
    }

}
