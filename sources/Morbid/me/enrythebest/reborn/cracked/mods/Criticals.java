package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;

public final class Criticals extends ModBase
{
    public Criticals() {
        super("Criticals", "I", true, ".t crits");
        this.setDescription("Forces criticals.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (this.shouldCrit()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().onGround = false;
        }
    }
    
    @Override
    public void postMotionUpdate() {
        if (this.shouldCrit()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().onGround = true;
        }
    }
    
    private boolean shouldCrit() {
        this.getWrapper();
        if (!MorbidWrapper.getPlayer().isInWater()) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().isCollidedVertically) {
                final boolean var10000 = true;
                return var10000;
            }
        }
        final boolean var10000 = false;
        return var10000;
    }
}
