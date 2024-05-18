package us.dev.direkt.module.internal.movement;


import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "No Push", category = ModCategory.MOVEMENT)
public class NoPush extends ToggleableModule {

	private float savedReduction;
	
	@Override
	public void onEnable(){
        this.savedReduction = Wrapper.getPlayer() != null ? Wrapper.getPlayer().entityCollisionReduction : 0.0f;
	}
 
	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		Wrapper.getPlayer().entityCollisionReduction = 1.0F;
	});
	
	@Override
	public void onDisable(){
		Wrapper.getPlayer().entityCollisionReduction = this.savedReduction;
	}
	
}
