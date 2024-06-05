package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

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
        if (MorbidWrapper.getPlayer().rotationPitch == 90.0f) {
            this.getWrapper();
            final EntityClientPlayerMP var10000 = MorbidWrapper.getPlayer();
            this.getWrapper();
            var10000.rotationPitch = MorbidWrapper.getPlayer().oldRotationPitch;
        }
        if (KillAura.curTarget != null) {
            final float var10001 = Morbid.getRotationManager().getYaw();
            if (var10001 > 180.0f) {
                Morbid.getRotationManager().setYaw(var10001 - 180.0f);
            }
            else {
                Morbid.getRotationManager().setYaw(var10001 + 180.0f);
            }
        }
    }
}
