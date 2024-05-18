package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;
import java.util.*;

public class ColorList extends qMIe
{
    public ModeValue mode;
    
    public ColorList() {
        super("Color List", "Rainbow", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Up");
        list.add("Down");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Down", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
}
