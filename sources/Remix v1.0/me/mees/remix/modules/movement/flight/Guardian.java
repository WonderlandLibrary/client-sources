package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;

public class Guardian extends Mode<Flight>
{
    public Guardian(final Flight parent) {
        super(parent, "Guardian");
    }
    
    @Override
    public void onDisable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
    }
    
    @Subscriber
    public void onUpdate(final EventMotion event) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        this.mc.thePlayer.motionY = 0.0;
        if (this.mc.thePlayer.isMoving() && !this.mc.gameSettings.keyBindJump.pressed) {
            final Timer timer = this.mc.timer;
            Timer.timerSpeed = 3.0f;
        }
        else {
            final Timer timer2 = this.mc.timer;
            Timer.timerSpeed = 1.0f;
        }
        if (this.mc.gameSettings.keyBindJump.pressed) {
            for (double i = 0.0; i < 1.0; i += 0.5) {
                this.mc.thePlayer.moveEntity(0.0, i, 0.0);
            }
        }
        if (this.mc.thePlayer.isSneaking()) {
            for (double i = 0.0; i < 1.0; i += 0.5) {
                this.mc.thePlayer.moveEntity(0.0, -i, 0.0);
            }
        }
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-4, this.mc.thePlayer.posZ);
    }
}
