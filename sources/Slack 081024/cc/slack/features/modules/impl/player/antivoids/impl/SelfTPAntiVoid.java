package cc.slack.features.modules.impl.player.antivoids.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.player.antivoids.IAntiVoid;

public class SelfTPAntiVoid implements IAntiVoid {

    private double groundX = 0.0;
    private double groundY = 0.0;
    private double groundZ = 0.0;
    private boolean triedTP = false;


    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            groundX = mc.thePlayer.posX;
            groundY = mc.thePlayer.posY;
            groundZ = mc.thePlayer.posZ;
            triedTP = false;
        } else if (mc.thePlayer.fallDistance > 5f && !triedTP) {
            mc.thePlayer.setPosition(groundX, groundY, groundZ);
            mc.thePlayer.fallDistance = 0;
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionY = 0;
            mc.thePlayer.motionZ = 0;
            triedTP = true;
        }
    }

    @Override
    public String toString() {
        return "Self TP";
    }
}
