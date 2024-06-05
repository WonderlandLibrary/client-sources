package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

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
            MorbidWrapper.getPlayer().F = false;
        }
    }
    
    @Override
    public void postMotionUpdate() {
        if (this.shouldCrit()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().F = true;
        }
    }
    
    private boolean shouldCrit() {
        this.getWrapper();
        if (!MorbidWrapper.getPlayer().G()) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().H) {
                return true;
            }
        }
        return false;
    }
}
