package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;

public class CombatSprintFix extends Module
{
    public CombatSprintFix() {
        super("CombatSprintFix", "Stay sprinting while attacking.", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PacketReceiveEvent) {}
    }
}
