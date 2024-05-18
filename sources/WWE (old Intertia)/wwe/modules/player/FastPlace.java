package wwe.modules.player;

import wwe.modules.*;
import wwe.*;

public class FastPlace extends qMIe
{
    public FastPlace() {
        super("Fast Place", "Allows you to place blocks faster", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (this.IuiN()) {
            yYGD.wtIT(0);
        }
    }
    
    @Override
    public void qBmy() {
        yYGD.wtIT(6);
        super.qBmy();
    }
}
