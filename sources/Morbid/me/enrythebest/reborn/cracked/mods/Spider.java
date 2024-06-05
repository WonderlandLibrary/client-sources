package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;

public final class Spider extends ModBase
{
    public Spider() {
        super("Spider", "J", true, ".t spider");
        this.setDescription("Let's you climb up walls when you are hurt.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (this.shouldClimb()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().motionY = 0.4;
        }
    }
    
    private boolean shouldClimb() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().hurtTime > 0) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().isCollidedHorizontally) {
                this.getWrapper();
                if (!MorbidWrapper.getPlayer().isInWater()) {
                    this.getWrapper();
                    if (MorbidWrapper.getPlayer().movementInput.moveForward <= 0.0f) {
                        this.getWrapper();
                        if (MorbidWrapper.getPlayer().movementInput.moveStrafe <= 0.0f) {
                            return false;
                        }
                    }
                    final boolean var10000 = true;
                    return var10000;
                }
            }
        }
        final boolean var10000 = false;
        return var10000;
    }
}
