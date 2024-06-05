package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;

public class Fall extends Mode<Flight>
{
    private double startPosY;
    
    public Fall(final Flight parent) {
        super(parent, "Fall");
    }
    
    @Subscriber
    public void onUpdate(final EventMove event) {
        MiscellaneousUtil.setSpeed(MiscellaneousUtil.getBaseMoveSpeed());
        if (this.mc.thePlayer.posY < this.startPosY - 2.0) {
            event.y = 2.0;
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEnable() {
        this.startPosY = this.mc.thePlayer.posY;
    }
}
