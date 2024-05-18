package wwe.modules.movement;

import wwe.modules.*;
import wwe.utils.*;
import wwe.*;

public class AntiVoid extends qMIe
{
    public AntiVoid() {
        super("Anti Void", "Makes you not fall in void", 0, Category.MOVEMENT);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (!Wrapper.mc.player.onGround && Wrapper.mc.player.fallDistance >= 4.0f) {
            Wrapper.mc.player.motionY = -0.01;
        }
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
