package wwe.modules.combat;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class HitBoxes extends qMIe
{
    public NumberValue hitBoxSize;
    public BooleanValue entityBox;
    
    public HitBoxes() {
        super("Hit Boxes", "Extends entity's hit boxes", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        this.hitBoxSize = new NumberValue("Hit Box Size", this, "How much to extend hit box by", 0.2, 0.0, 1.0);
        this.entityBox = new BooleanValue("Entity Box", this, "Show expanded entity box in f3 and b", true);
    }
}
