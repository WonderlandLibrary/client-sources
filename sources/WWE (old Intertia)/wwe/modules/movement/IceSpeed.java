package wwe.modules.movement;

import wwe.*;
import wwe.modules.*;
import net.minecraft.init.*;

public class IceSpeed extends qMIe
{
    public IceSpeed() {
        super("Ice Speed", "Makes you go faster on ice", 0, Category.MOVEMENT);
    }
    
    @Override
    public void LPxx() {
        Blocks.ICE.slipperiness = 0.4f;
        Blocks.PACKED_ICE.slipperiness = 0.4f;
        Blocks.FROSTED_ICE.slipperiness = 0.4f;
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
        super.qBmy();
    }
}
