package me.mees.remix.modules.player;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import pw.stamina.causam.scan.method.model.*;

public class Nofall extends Module
{
    public Nofall() {
        super("Nofall", 0, Category.PLAYER);
    }
    
    @Subscriber
    public void EventMotion(final EventMotion event) {
        if (Nofall.mc.thePlayer.fallDistance > 3.0f && !Base.INSTANCE.getModuleManager().getModByName("Flight").isEnabled()) {
            event.onGround = true;
            Nofall.mc.thePlayer.fallDistance = 0.0f;
        }
    }
}
