package eze.modules.movement;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;

public class Sprint extends Module
{
    public boolean isSprintToggled;
    
    public Sprint() {
        super("Sprint", 45, Category.MOVEMENT);
        this.isSprintToggled = false;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.setSprinting(this.mc.gameSettings.keyBindSprint.getIsKeyPressed());
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.moveForward > 0.0f && !this.mc.thePlayer.isSneaking() && !this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isUsingItem()) {
            this.mc.thePlayer.setSprinting(true);
            this.isSprintToggled = true;
        }
    }
}
