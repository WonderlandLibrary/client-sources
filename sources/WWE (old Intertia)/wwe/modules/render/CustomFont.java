package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;
import java.util.*;

public class CustomFont extends qMIe
{
    public NumberValue height;
    public ModeValue font;
    
    public CustomFont() {
        super("Custom Font", "Enables custom font", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Comfortaa");
        list.add("Roboto");
        list.add("Cosmic Sans");
        list.add("Verdana");
        list.add("Custom");
        this.height = new NumberValue("Height", this, "Custom mode font height", 0.0, 0.0, 5.0);
        this.font = new ModeValue("Font", this, "Current font", "Comfortaa", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
}
