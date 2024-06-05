package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", "Toggle sprint but for hacked clients", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof WorldTickEvent && (this.mc.thePlayer.moveForward != 0.0f || (this.mc.thePlayer.moveStrafing != 0.0f && !this.mc.thePlayer.isSneaking()))) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
}
