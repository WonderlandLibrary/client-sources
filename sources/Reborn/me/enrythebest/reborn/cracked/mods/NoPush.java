package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.src.*;

public final class NoPush extends ModBase
{
    public NoPush() {
        super("NoPush", "O", true, ".t nopush");
        this.setDescription("Take no knockback effect.");
        this.setEnabled(true);
    }
    
    @Override
    public boolean onVelocityPacket(final Packet28EntityVelocity var1) {
        return this.isEnabled();
    }
}
