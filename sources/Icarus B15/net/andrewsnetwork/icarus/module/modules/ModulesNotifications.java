// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class ModulesNotifications extends Module
{
    public ModulesNotifications() {
        super("ModulesNotifications", Category.MISC);
        this.setEnabled(false);
    }
    
    @Override
    public void onEvent(final Event e) {
    }
}
