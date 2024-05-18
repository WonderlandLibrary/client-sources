package wwe.modules.gui;

import wwe.modules.*;
import java.util.*;
import wwe.utils.*;
import wwe.*;
import net.minecraft.client.gui.*;

public class Gui extends qMIe
{
    public NumberValue red;
    public NumberValue green;
    public NumberValue blue;
    public ModeValue scrolling;
    public BooleanValue search;
    public BooleanValue toolTips;
    
    public Gui() {
        super("Gui", "Click Gui", 54, Category.GUI);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("None");
        list.add("Normal");
        list.add("All");
        this.red = new NumberValue("Red", this, "How much red for the gui's color", 189.0, 0.0, 255.0);
        this.green = new NumberValue("Green", this, "How much green for the gui's color", 0.0, 0.0, 255.0);
        this.blue = new NumberValue("Blue", this, "How much blue for the gui's color", 0.0, 0.0, 255.0);
        this.scrolling = new ModeValue("Scrolling", this, "How to scroll in the click gui", "Normal", list);
        this.search = new BooleanValue("Search", this, "Adds a seach to the gui", false);
        this.toolTips = new BooleanValue("Tool Tips", this, "Renders the description for hovered items", true);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        Wrapper.mc.displayGuiScreen((GuiScreen)vlwv.pRNz);
        this.GDVp();
    }
}
