package me.mees.remix.modules.combat.antiknockback;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;

public class AAC extends Mode<AntiKnockback>
{
    private double motionX;
    private double motionZ;
    
    public AAC(final AntiKnockback parent) {
        super(parent, "AAC");
    }
    
    @Subscriber
    public void eventMotion(final EventMotion event) {
        if (this.mc.thePlayer.onGround) {
            return;
        }
        if (this.mc.thePlayer.hurtTime == 6) {
            final double nX = -Math.sin(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 0.12;
            final double nZ = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 0.12;
            this.mc.thePlayer.motionX = nX;
            this.mc.thePlayer.motionZ = nZ;
            this.mc.thePlayer.jumpMovementFactor = 0.08f;
        }
        else {
            this.mc.thePlayer.jumpMovementFactor = 0.025999999f;
        }
    }
}
