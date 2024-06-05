package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.*;

public final class Aimbot extends ModBase
{
    public double Valore;
    
    public Aimbot() {
        super("Aimbot", "0", true, ".t aim");
        this.Valore = KillAura.aRange;
        this.setDescription("Segue i player con la testa.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && this.isEnabled()) {
            final sq e = Utils.getClosestPlayer();
            final boolean checks = !(e instanceof bfj) && e instanceof sq && MorbidWrapper.mcObj().g.d(e) <= this.Valore && MorbidWrapper.mcObj().g.n(e) && !e.M;
            if (checks && !Morbid.getFriends().isFriend(e)) {
                this.faceEntity(e);
            }
        }
    }
    
    public void faceEntity(final mp entity) {
        final double x = entity.u - MorbidWrapper.mcObj().g.u;
        final double z = entity.w - MorbidWrapper.mcObj().g.w;
        final double y = entity.v + entity.e() / 1.2 - MorbidWrapper.mcObj().g.v + MorbidWrapper.mcObj().g.e() / 1.4;
        final double helper = kx.a(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        MorbidWrapper.mcObj().g.A = newYaw;
        MorbidWrapper.mcObj().g.B = newPitch;
        MorbidWrapper.mcObj().g.aA = newPitch;
    }
}
