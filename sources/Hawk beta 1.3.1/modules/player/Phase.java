package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import eze.events.*;
import eze.events.listeners.*;

public class Phase extends Module
{
    double yaw;
    double oldX;
    double oldZ;
    double d4;
    double d5;
    Timer timer;
    
    public Phase() {
        super("Phase", 0, Category.PLAYER);
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.isCollidedHorizontally && this.timer.hasTimeElapsed(2L, true)) {
            this.yaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
            this.oldX = this.mc.thePlayer.posX;
            this.oldZ = this.mc.thePlayer.posZ;
            this.d4 = -Math.sin(this.yaw);
            this.d5 = Math.cos(this.yaw);
            this.mc.thePlayer.setSprinting(false);
            this.mc.thePlayer.setPosition(this.oldX + this.d4, this.mc.thePlayer.posY, this.oldZ + this.d5);
        }
    }
}
