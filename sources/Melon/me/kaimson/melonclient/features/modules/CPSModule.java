package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.*;

public class CPSModule extends DefaultModuleRenderer
{
    private final Setting cpsMode;
    
    public CPSModule() {
        super("CPS Display");
        new Setting(this, "General Options");
        this.cpsMode = new Setting(this, "Mode").setDefault(2).setRange("Left", "Right", "Both", "Higher");
    }
    
    @Override
    public Object getValue() {
        switch (this.cpsMode.getInt()) {
            case 0: {
                return Client.left.getCPS();
            }
            case 1: {
                return Client.right.getCPS();
            }
            case 2: {
                return Client.left.getCPS() + " | " + Client.right.getCPS();
            }
            case 3: {
                return Math.max(Client.left.getCPS(), Client.right.getCPS());
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public String getFormat() {
        return "[%value% CPS]";
    }
}
