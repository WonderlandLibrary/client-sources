// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "FastPlace", description = "Allows you to place more faster", cat = Category.PLAYER)
public class FastPlace extends Module
{
    private final DoubleProperty speed;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public FastPlace() {
        this.speed = new DoubleProperty("Speed", 0.0, 0.0, 4.0, 1.0);
        this.updateEventListener = (e -> this.mc.rightClickDelayTimer = this.speed.getValue().intValue());
    }
}
