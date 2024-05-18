package wwe.modules.player;

import net.minecraft.util.math.*;
import wwe.modules.*;
import wwe.utils.*;
import wwe.*;

public class AutoMine extends qMIe
{
    BlockPos VKjR;
    
    public AutoMine() {
        super("Auto Mine", "Automatically mines for you", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        yYGD.rvRL(Wrapper.mc.gameSettings.keyBindAttack, true);
    }
    
    @Override
    public void qBmy() {
        yYGD.rvRL(Wrapper.mc.gameSettings.keyBindAttack, false);
    }
}
