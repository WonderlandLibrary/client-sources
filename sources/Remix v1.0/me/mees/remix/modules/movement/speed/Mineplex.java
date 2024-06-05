package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;

public class Mineplex extends Mode<Speed>
{
    public Mineplex(final Speed parent) {
        super(parent, "Mineplex");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (!Base.INSTANCE.getModuleManager().getModByName("Flight").isEnabled()) {
            if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY = 0.4;
                    this.mc.thePlayer.setSpeed(MiscellaneousUtil.getBaseMoveSpeed() * 1.8);
                }
                else {
                    this.mc.thePlayer.setSpeed(MiscellaneousUtil.getBaseMoveSpeed() * 1.6);
                }
            }
            else {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
            }
        }
    }
}
