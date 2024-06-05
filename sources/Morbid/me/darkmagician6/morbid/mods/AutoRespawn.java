package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class AutoRespawn extends ModBase
{
    public AutoRespawn() {
        super("AutoRespawn", "0", false, ".t respawn");
        this.setDescription("Automatically respawns you if you die.");
        this.setEnabled(true);
    }
    
    @Override
    public void postMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().M) {
            this.getWrapper();
            MorbidWrapper.getPlayer().cf();
            Morbid.getManager().getMod("killaura").setEnabled(false);
        }
    }
}
