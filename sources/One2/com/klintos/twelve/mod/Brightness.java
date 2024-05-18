// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.settings.GameSettings;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Brightness extends Mod
{
    private float startGamma;
    
    public Brightness() {
        super("Brightness", 34, ModCategory.WORLD);
    }
    
    @Override
    public void onEnable() {
        this.startGamma = Brightness.mc.gameSettings.gammaSetting;
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (Brightness.mc.gameSettings.gammaSetting <= 10.0f) {
            final GameSettings gameSettings = Brightness.mc.gameSettings;
            gameSettings.gammaSetting += 0.5f;
        }
    }
    
    @Override
    public void onDisable() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (float f = Brightness.mc.gameSettings.gammaSetting; f >= Brightness.this.startGamma; f -= 0.1) {
                        Brightness.mc.gameSettings.gammaSetting = f;
                        Thread.sleep(10L);
                    }
                    Brightness.mc.gameSettings.gammaSetting = Brightness.this.startGamma;
                }
                catch (Exception ex) {}
            }
        }.start();
    }
}
