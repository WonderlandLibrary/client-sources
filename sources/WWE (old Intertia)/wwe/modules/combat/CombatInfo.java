package wwe.modules.combat;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class CombatInfo extends qMIe
{
    public NumberValue range;
    public BooleanValue heads;
    public BooleanValue names;
    public BooleanValue coords;
    
    public CombatInfo() {
        super("Combat Info", "Renders players health on YOUR screen", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        this.range = new NumberValue("Range", this, "Range for rendering", 200.0, 1.0, 300.0);
        this.heads = new BooleanValue("Heads", this, "Show heads", true);
        this.names = new BooleanValue("Names", this, "Show names", false);
        this.coords = new BooleanValue("Coords", this, "Show there coords", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
}
