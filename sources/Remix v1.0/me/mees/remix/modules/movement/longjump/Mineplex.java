package me.mees.remix.modules.movement.longjump;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import net.minecraft.client.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class Mineplex extends Mode<Longjump>
{
    public Mineplex(final Longjump parent) {
        super(parent, "Mineplex");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
            this.mc.thePlayer.setSpeed(MiscellaneousUtil.getBaseMoveSpeed() * 1.6);
            if (this.mc.thePlayer.motionY <= 0.0) {
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionY += 0.075;
            }
            if (this.mc.thePlayer.isCollidedVertically) {
                this.mc.thePlayer.motionY = 0.1;
            }
        }
    }
}
