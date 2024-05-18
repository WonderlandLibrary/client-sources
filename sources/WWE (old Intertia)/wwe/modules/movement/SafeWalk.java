package wwe.modules.movement;

import net.minecraft.client.renderer.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import wwe.*;

public class SafeWalk extends qMIe
{
    private Vector3d eAPT;
    
    public SafeWalk() {
        super("Safe Walk", "Makes you not fall off blocks", 0, Category.MOVEMENT);
        this.eAPT = new Vector3d();
    }
    
    @oqrr
    public void QIFV(final sFUx sfUx) {
        double vsNw = sfUx.VsNw;
        final double xNxu = sfUx.XNxu;
        double lcNE = sfUx.lcNE;
        if (Wrapper.mc.player.onGround) {
            final double n = 0.05;
            while (vsNw != 0.0 && this.TypB(vsNw, -1.0, 0.0)) {
                if (vsNw < n && vsNw >= -n) {
                    vsNw = 0.0;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else if (vsNw > 0.0) {
                    vsNw -= n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else {
                    vsNw += n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
            }
            while (lcNE != 0.0 && this.TypB(0.0, -1.0, lcNE)) {
                if (lcNE < n && lcNE >= -n) {
                    lcNE = 0.0;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else if (lcNE > 0.0) {
                    lcNE -= n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else {
                    lcNE += n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
            }
            while (vsNw != 0.0 && lcNE != 0.0 && this.TypB(vsNw, -1.0, lcNE)) {
                if (vsNw < n && vsNw >= -n) {
                    vsNw = 0.0;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                }
                else if (vsNw > 0.0) {
                    vsNw -= n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                }
                else {
                    vsNw += n;
                }
                if (lcNE < n && lcNE >= -n) {
                    lcNE = 0.0;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else if (lcNE > 0.0) {
                    lcNE -= n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
                else {
                    lcNE += n;
                    if (SafeWalk.\u200f\u2001\u2006\u2006) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        sfUx.VsNw = vsNw;
        sfUx.XNxu = xNxu;
        sfUx.lcNE = lcNE;
    }
    
    public boolean TypB(final double x, final double y, final double z) {
        this.eAPT.x = x;
        this.eAPT.y = y;
        this.eAPT.z = z;
        return Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().offset(this.eAPT.x, this.eAPT.y, this.eAPT.z)).isEmpty();
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
}
