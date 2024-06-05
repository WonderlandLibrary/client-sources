package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;

public class Mineman extends Mode<Speed>
{
    public Mineman(final Speed parent) {
        super(parent, "Mineman");
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.setSpeed(0.0);
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (!Base.INSTANCE.getModuleManager().getModByName("Flight").isEnabled()) {
            if ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.setSpeed(1.4);
                final Timer timer = this.mc.timer;
                Timer.timerSpeed = 0.3f;
            }
            else {
                this.mc.thePlayer.setSpeed(0.0);
                final Timer timer2 = this.mc.timer;
                Timer.timerSpeed = 1.0f;
            }
        }
    }
}
