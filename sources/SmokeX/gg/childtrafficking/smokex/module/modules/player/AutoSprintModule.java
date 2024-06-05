// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoSprint", renderName = "Auto Sprint", description = "Automatically makes the player sprint.", aliases = { "sprint" }, category = ModuleCategory.PLAYER)
public final class AutoSprintModule extends Module
{
    private boolean ok;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AutoSprintModule() {
        this.updateEventListener = (event -> this.mc.thePlayer.setSprinting((ModuleManager.getInstance(ScaffoldModule.class).isToggled() && ModuleManager.getInstance(ScaffoldModule.class).sprintProperty.getValue()) ? this.isOk() : MovementUtils.canSprint()));
    }
    
    @Override
    public void init() {
        this.toggle();
    }
    
    public boolean isOk() {
        return this.ok;
    }
    
    public void setOk(final boolean ok) {
        this.ok = ok;
    }
}
