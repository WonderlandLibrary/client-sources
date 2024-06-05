package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class AAC extends Mode<Speed>
{
    public AAC(final Speed parent) {
        super(parent, "AAC");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate eventt) {
        if (this.mc.thePlayer.isMoving()) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionX *= 1.001;
                final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                thePlayer2.motionZ *= 1.001;
                this.mc.thePlayer.motionY = 0.41;
                final Timer timer = this.mc.timer;
                Timer.timerSpeed = 1.01f;
            }
            else {
                final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                thePlayer3.motionX *= 1.007;
                final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                thePlayer4.motionZ *= 1.007;
                final Timer timer2 = this.mc.timer;
                Timer.timerSpeed = 1.044f;
            }
        }
    }
}
