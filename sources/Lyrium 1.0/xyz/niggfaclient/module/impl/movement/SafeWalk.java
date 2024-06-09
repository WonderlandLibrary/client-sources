// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.events.CancellableEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.SafeEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "SafeWalk", description = "Allows you to safe walk without falling", cat = Category.MOVEMENT)
public class SafeWalk extends Module
{
    @EventLink
    private final Listener<SafeEvent> safeEventListener;
    
    public SafeWalk() {
        this.safeEventListener = CancellableEvent::setCancelled;
    }
}
