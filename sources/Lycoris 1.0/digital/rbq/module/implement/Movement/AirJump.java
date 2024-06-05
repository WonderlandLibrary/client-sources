package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

public class AirJump extends Module{

    public AirJump() {
        super("AirJump", Category.Movement, false);
    }

    @EventTarget
    public void onUpdate(PreUpdateEvent event) {
        this.mc.thePlayer.onGround = true;
        this.mc.thePlayer.isAirBorne = false;
        this.mc.thePlayer.fallDistance = 0;
    }
}
