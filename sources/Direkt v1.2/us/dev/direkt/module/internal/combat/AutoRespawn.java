package us.dev.direkt.module.internal.combat;

import net.minecraft.client.gui.GuiGameOver;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.event.internal.events.game.gui.screen.EventDisplayGui;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Auto Respawn", category = ModCategory.COMBAT)
public class AutoRespawn extends ToggleableModule {

    @Exposed(description = "Spawn at your set home instead of world spawn")
    private Property<Boolean> homeProperty = new Property<>("Home", false);

	private boolean hasRespawned = false;
    @Listener
    protected Link<EventDisplayGui> onDisplayGui = new Link<>(event -> {
    	if (event.getGuiScreen() instanceof GuiGameOver) {
    		event.setCancelled(true);
    		if(!hasRespawned && homeProperty.getValue())
        		Wrapper.sendChatMessage("/esethome");
    		Wrapper.getPlayer().respawnPlayer();
    		if(!hasRespawned && homeProperty.getValue())
    		Wrapper.sendChatMessage("/ehome");
    		this.hasRespawned = true;
    	}
    });
    
    @Listener
    protected Link<EventGameTick> onPreMotion = new Link<>(event -> {
    	if(homeProperty.getValue() && Wrapper.getPlayer().getHealth() > 0 && this.hasRespawned)
    		this.hasRespawned = false;
    });
    
	
}
