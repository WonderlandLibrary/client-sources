package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import pw.stamina.causam.scan.method.model.*;

public class DEV extends Mode<Speed>
{
    int counter;
    float counter2;
    
    public DEV(final Speed parent) {
        super(parent, "DEV");
        this.counter = 0;
        this.counter2 = 0.0f;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (!Base.INSTANCE.getModuleManager().getModByName("Flight").isEnabled()) {
            if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
                ++this.counter;
                this.counter2 += 0.0015f;
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY = 0.177;
                    this.counter = 0;
                }
                else if (this.counter >= 3) {
                    this.mc.thePlayer.motionY = -0.18;
                }
            }
            else {
                this.mc.thePlayer.setSpeed(0.0);
                this.counter2 = 0.0f;
            }
        }
    }
}
