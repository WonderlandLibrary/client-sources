package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;

public final class NoPush extends ModBase
{
    public NoPush() {
        super("NoPush", "O", true, ".t nopush");
        this.setDescription("Take no knockback effect.");
        this.setEnabled(true);
    }
    
    @Override
    public boolean onVelocityPacket(final ey packet) {
        return this.isEnabled();
    }
}
