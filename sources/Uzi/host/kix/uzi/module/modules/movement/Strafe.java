package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/27/2017.
 */
public class Strafe extends Module {

    public Strafe() {
        super("Strafe", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onMotion(UpdateEvent event) {
        if ((this.mc.thePlayer.hurtTime == 0) && (!this.mc.thePlayer.onGround) && ((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F))) {
            this.mc.thePlayer.setSpeed(this.mc.thePlayer.getSpeed());
        }
    }

}
