package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/5/2017.
 */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT);
    }

    private boolean canSprint() {
        return !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking()
                && mc.thePlayer.getFoodStats().getFoodLevel() > 5
                && (mc.thePlayer.movementInput.moveForward != 0.0f || mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e){
            mc.thePlayer.setSprinting(canSprint());
    }

}
