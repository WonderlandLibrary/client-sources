package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.impl.player.NoSlow;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;
import dev.client.tenacity.utils.player.MovementUtils;

public class Sprint extends Module {

    private final BooleanSetting omniSprint = new BooleanSetting("Omni Sprint", false);

    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Sprints automatically");
        this.addSettings(omniSprint);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        if (Tenacity.INSTANCE.getModuleCollection().get(Scaffold.class).isToggled() && !Scaffold.sprint.isEnabled()) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
            return;
        }
        if (omniSprint.isEnabled()) {
            mc.thePlayer.setSprinting(MovementUtils.isMoving());
        } else {
            if(mc.thePlayer.isUsingItem()) {
                if (mc.thePlayer.moveForward > 0 && (Tenacity.INSTANCE.getModuleCollection().get(NoSlow.class).isToggled() || !mc.thePlayer.isUsingItem()) && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
                    mc.thePlayer.setSprinting(true);
                }
            }else{
                mc.gameSettings.keyBindSprint.pressed = true;
            }
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }

}
