package eze.modules.render;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;

public class ModulesListOptions extends Module
{
    public static int ColorOptionInt;
    public ModeSetting Color;
    Timer timer;
    boolean PlayerEat;
    
    static {
        ModulesListOptions.ColorOptionInt = 0;
    }
    
    public ModulesListOptions() {
        super("HUD Options", 0, Category.RENDER);
        this.Color = new ModeSetting("Color", "Colorful", new String[] { "Colorful", "Red", "Blue", "Orange", "Green", "White" });
        this.timer = new Timer();
        this.PlayerEat = false;
        this.addSettings(this.Color);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.Color.is("Colorful")) {
                ModulesListOptions.ColorOptionInt = 0;
            }
            if (this.Color.is("Red")) {
                ModulesListOptions.ColorOptionInt = 1;
            }
            if (this.Color.is("Blue")) {
                ModulesListOptions.ColorOptionInt = 2;
            }
            if (this.Color.is("Orange")) {
                ModulesListOptions.ColorOptionInt = 3;
            }
            if (this.Color.is("Green")) {
                ModulesListOptions.ColorOptionInt = 4;
            }
            if (this.Color.is("White")) {
                ModulesListOptions.ColorOptionInt = 5;
            }
        }
    }
}
