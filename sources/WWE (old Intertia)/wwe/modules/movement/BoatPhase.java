package wwe.modules.movement;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import wwe.*;

public class BoatPhase extends qMIe
{
    public BoatPhase() {
        super("Boat Phase", "Boat + sand on it + you in it = boat phase", 0, Category.MOVEMENT);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        Wrapper.mc.player.noClip = true;
        Wrapper.mc.player.motionY = 0.0;
        Wrapper.mc.player.onGround = false;
        Wrapper.mc.player.capabilities.isFlying = false;
    }
    
    @oqrr
    public void ffoK(final sFUx sfUx) {
        if (yYGD.AXSL((Entity)Wrapper.mc.player) != null && yYGD.AXSL((Entity)Wrapper.mc.player) instanceof EntityBoat) {
            yYGD.AXSL((Entity)Wrapper.mc.player).setEntityBoundingBox(yYGD.AXSL((Entity)Wrapper.mc.player).getEntityBoundingBox().offset(sfUx.VsNw * 10.0, sfUx.XNxu, sfUx.lcNE * 10.0));
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        Wrapper.mc.player.noClip = false;
        hSNE.kduK(this);
        super.qBmy();
    }
}
