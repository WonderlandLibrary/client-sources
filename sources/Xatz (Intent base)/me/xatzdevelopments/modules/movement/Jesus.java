package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.PlayerUtils;

public class Jesus extends Module
{
    public ModeSetting Mode;
    
    public Jesus() {
        super("Jesus", 0, Category.MOVEMENT, null);
        this.Mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla" });
        this.addSettings(this.Mode);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            final String mode;
            switch (mode = this.Mode.getMode()) {
                case "Vanilla": {
                    if (PlayerUtils.isOnLiquid()) {
                        this.mc.thePlayer.motionY = 1.0E-6;
                        this.mc.thePlayer.onGround = true;
                        break;
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }
}
