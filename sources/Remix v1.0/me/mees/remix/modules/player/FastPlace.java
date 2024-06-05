package me.mees.remix.modules.player;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;

public class FastPlace extends Module
{
    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER);
    }
    
    @Override
    public void onEnable() {
        FastPlace.mc.rightClickDelayTimer = 1;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        FastPlace.mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        FastPlace.mc.rightClickDelayTimer = 1;
    }
}
