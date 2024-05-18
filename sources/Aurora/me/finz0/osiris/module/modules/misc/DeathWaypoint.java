package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import me.finz0.osiris.waypoint.Waypoint;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

import java.awt.*;

public class DeathWaypoint extends Module {
    public DeathWaypoint() {
        super("DeathWaypoint", Category.MISC, "Makes a waypoint where you die");
    }

    @EventHandler
    private Listener<GuiScreenDisplayedEvent> listener = new Listener<>(event -> {
        if (event.getScreen() instanceof GuiGameOver) {
            AuroraMod.getInstance().waypointManager.delWaypoint( AuroraMod.getInstance().waypointManager.getWaypointByName("Last Death"));
            AuroraMod.getInstance().waypointManager.addWaypoint(new Waypoint("Last Death", mc.player.posX, mc.player.posY, mc.player.posZ, Color.RED.getRGB()));
        }
    });

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

}
