package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class Parkour extends ModBase
{
    public Parkour() {
        super("Parkour", "V", true, ".t parkour");
        this.setDescription("Prevents you from falling down a parkour.");
    }
    
    @Override
    public void preMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().T >= 1.0f) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().ag()) {
                this.getWrapper();
                MorbidWrapper.getPlayer().y = 0.0;
            }
        }
    }
}
