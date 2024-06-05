package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import net.minecraft.client.*;
import pw.stamina.causam.scan.method.model.*;

public class GuardianHop extends Mode<Speed>
{
    public GuardianHop(final Speed parent) {
        super(parent, "GuardianHop");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (this.mc.gameSettings.keyBindForward.pressed || this.mc.gameSettings.keyBindBack.pressed || this.mc.gameSettings.keyBindLeft.pressed || this.mc.gameSettings.keyBindRight.pressed) {
            final Minecraft mc = this.mc;
            if (!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                if (this.mc.thePlayer.moveStrafing == 0.0f && this.mc.thePlayer.moveForward == 0.0f) {
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                }
                if (this.mc.thePlayer.onGround && this.mc.thePlayer.isMoving()) {
                    this.mc.thePlayer.setSpeed(1.2);
                    this.mc.thePlayer.motionY = 0.424;
                }
                this.mc.thePlayer.setSpeed((float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
            }
        }
    }
}
