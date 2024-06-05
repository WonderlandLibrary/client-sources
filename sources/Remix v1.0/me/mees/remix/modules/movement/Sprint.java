package me.mees.remix.modules.movement;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", 0, Category.MOVE);
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (Sprint.mc.thePlayer != null && Sprint.mc.theWorld != null) {
            Sprint.mc.thePlayer.setSprinting(Sprint.mc.gameSettings.keyBindForward.pressed && !Sprint.mc.thePlayer.isSneaking());
        }
    }
}
