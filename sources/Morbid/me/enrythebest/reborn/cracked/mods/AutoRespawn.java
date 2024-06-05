package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;

public final class AutoRespawn extends ModBase
{
    public AutoRespawn() {
        super("AutoRespawn", "0", true, ".t respawn");
        this.setDescription("Automatically respawns you if you die.");
        this.setEnabled(true);
    }
    
    @Override
    public void postMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().isDead) {
            this.getWrapper();
            MorbidWrapper.getPlayer().respawnPlayer();
        }
    }
}
