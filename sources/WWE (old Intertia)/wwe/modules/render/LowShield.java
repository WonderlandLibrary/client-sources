package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class LowShield extends qMIe
{
    public NumberValue height;
    
    public LowShield() {
        super("Low Shield", "Makes the item in your off hand smaller", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.height = new NumberValue("Height", this, "Height of the shield", 0.75, 0.0, 1.0);
    }
}
