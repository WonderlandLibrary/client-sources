package none.module.modules.combat;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AuraTeams extends Module{

	public AuraTeams() {
		super("AuraTeams", "AuraTeams", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Float> range = new NumberValue<>("Range", 5F, 3F, 10F);
	private NumberValue<Integer> maxplayer = new NumberValue<>("MaxPlayer", 2, 1, 6);
	private BooleanValue autoremove = new BooleanValue("AutoRemove", true);
	
	public static List<EntityPlayer> player = new ArrayList<EntityPlayer>();
	TimeHelper timer = new TimeHelper();
	@Override
	protected void onEnable() {
		super.onEnable();
		if (!player.isEmpty()) {
			player.clear();
		}
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		if (!player.isEmpty()) {
			player.clear();
		}
	}
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (autoremove.getObject() && !player.isEmpty()) {
			if (mc.thePlayer.ticksExisted <= 1) {
				timer.setLastMS();
				player.clear();
				Client.instance.notification.show(new Notification(NotificationType.SP, getName(), " Remove Player by Respawn", 3));
			}
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				for (EntityPlayer ent : mc.theWorld.playerEntities) {
					if (ent != mc.thePlayer && mc.thePlayer.ticksExisted <= 160) {
						if (mc.thePlayer.getDistanceToEntity(ent) <= range.getFloat() && !player.contains(ent)) {
							if (player.size() < maxplayer.getInteger()) {
								player.add(ent);
							}
						}
					}
				}
			}
		}
	}

}
