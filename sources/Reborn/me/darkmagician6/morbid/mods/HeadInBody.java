package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class HeadInBody extends ModBase
{
    public HeadInBody() {
        super("Retard", "0", true, ".t retard");
        this.setDescription("Puts your head in your body.");
    }
    
    @Override
    public void preMotionUpdate() {
        Morbid.getRotationManager().setPitch(180.0f);
        this.getWrapper();
        if (MorbidWrapper.getPlayer().B == 90.0f) {
            this.getWrapper();
            final bdv player = MorbidWrapper.getPlayer();
            this.getWrapper();
            player.B = MorbidWrapper.getPlayer().cr;
        }
        if (KillAura.curTarget != null) {
            final float yaw = Morbid.getRotationManager().getYaw();
            if (yaw > 180.0f) {
                Morbid.getRotationManager().setYaw(yaw - 180.0f);
            }
            else {
                Morbid.getRotationManager().setYaw(yaw + 180.0f);
            }
        }
    }
}
