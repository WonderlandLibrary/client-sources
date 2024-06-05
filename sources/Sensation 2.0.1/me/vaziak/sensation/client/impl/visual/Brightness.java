package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;

public class Brightness extends Module {
 
    private float userGammaSetting; 

    public Brightness() {
        super("Brightness", Category.VISUAL); 
    }

    public void onEnable() {
        userGammaSetting = mc.gameSettings.gammaSetting;
    }
    public void onDisable() {
    	mc.gameSettings.gammaSetting = userGammaSetting;
    }

    @Collect
    public void onRunTick(RunTickEvent runTickEvent) { 
    	mc.gameSettings.gammaSetting = 10;
    }
}
