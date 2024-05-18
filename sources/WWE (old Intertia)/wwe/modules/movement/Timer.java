package wwe.modules.movement;

import java.text.*;
import wwe.utils.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.*;

public class Timer extends qMIe
{
    public DecimalFormat PxbF;
    public NumberValue speed;
    
    public Timer() {
        super("Timer", "Speeds up the game", 0, Category.MOVEMENT);
        this.PxbF = new DecimalFormat("##.#");
    }
    
    @Override
    public void HrDr() {
        this.speed = new NumberValue("Speed", this, "Speed of the timer", 2.0, 0.0, 20.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.owgW("Timer " + TextFormatting.DARK_GRAY + this.PxbF.format(((IcRI<Object>)this.speed).OnGi()));
        vlwv.Axho = this.speed.OnGi().floatValue();
    }
    
    @Override
    public void qBmy() {
        vlwv.Axho = 1.0f;
    }
}
