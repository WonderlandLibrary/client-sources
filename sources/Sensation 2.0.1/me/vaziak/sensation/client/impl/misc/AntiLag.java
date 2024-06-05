package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.RenderNametagEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import net.minecraft.entity.item.EntityItem;

/**
 * Made by Jonathan H. (Niada)
 *
 * Sensation development - Since 8/25/19
 **/
public class AntiLag extends Module {
	
	private BooleanProperty cancelTags  = new BooleanProperty("Remove Nametags", "Don't render nametags", false);
	private BooleanProperty removeItems = new BooleanProperty("Remove Items", "Remove items from the world", false);
	
	public AntiLag() {
		super("AntiLag", Category.MISC);
		registerValue(cancelTags, removeItems);
	}
	
    @Collect
    public void onRenderNametag(RenderNametagEvent renderNametagEvent) {
        if (cancelTags.getValue())
            renderNametagEvent.setCancelled(true);
    }
	
	@Collect
	public void runTick(RunTickEvent event) {
		 if (mc.thePlayer != null && mc.theWorld != null) {
			 mc.theWorld.loadedEntityList.forEach(entity -> {
				 if (entity != null && removeItems.getValue()) {
					 if (entity instanceof EntityItem) {
						 if (mc.thePlayer.getDistanceToEntity(entity) > 5) {
							 mc.theWorld.removeEntity(entity);
						 }
					 }
				 }
			 });
		 }
	}
}
