package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;

public class Glide extends qMIe
{
    public Glide() {
        super("Glide", "Makes you glide like a BIRDY", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (!Wrapper.mc.player.onGround) {
            Wrapper.mc.player.motionY = -0.4000000059604645;
        }
    }
}
