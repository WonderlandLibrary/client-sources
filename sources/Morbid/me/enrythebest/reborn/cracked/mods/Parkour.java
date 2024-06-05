package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;

public final class Parkour extends ModBase
{
    public Parkour() {
        super("Parkour", "V", true, ".t parkour");
        this.setDescription("Prevents you from falling down a parkour.");
    }
    
    @Override
    public void preMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().fallDistance >= 1.0f) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().isSneaking()) {
                this.getWrapper();
                MorbidWrapper.getPlayer().motionY = 0.0;
            }
        }
    }
}
