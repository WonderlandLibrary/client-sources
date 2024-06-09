// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Fullbright", description = "Sets minecraft gamma to max", cat = Category.RENDER)
public class Fullbright extends Module
{
    public float actualGamma;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Fullbright() {
        this.updateEventListener = (e -> this.mc.gameSettings.gammaSetting = 10000.0f);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.actualGamma = this.mc.gameSettings.gammaSetting;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.gammaSetting = this.actualGamma;
    }
}
