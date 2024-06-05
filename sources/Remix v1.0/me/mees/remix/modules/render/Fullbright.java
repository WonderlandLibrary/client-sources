package me.mees.remix.modules.render;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;

public class Fullbright extends Module
{
    private float gammabefore;
    
    public Fullbright() {
        super("Fullbright", 0, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        this.gammabefore = Fullbright.mc.gameSettings.gammaSetting;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Fullbright.mc.gameSettings.gammaSetting = this.gammabefore;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        Fullbright.mc.gameSettings.gammaSetting = 100.0f;
    }
}
