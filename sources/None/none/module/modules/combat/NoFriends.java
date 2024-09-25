package none.module.modules.combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;

public class NoFriends extends Module{

	public NoFriends() {
		super("NoFriends", "NoFriends", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	ArrayList<EntityPlayer> player = new ArrayList<>();
	
	@Override
	protected void onEnable() {
		super.onEnable();
		player.clear();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (mc.thePlayer.ticksExisted <= 1) {
			player.clear();
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				for (Entity entity : mc.theWorld.loadedEntityList) {
					if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
						EntityPlayer player = (EntityPlayer) entity;
						if (!this.player.contains(player) && FriendManager.isFriend(player.getName())) {
							this.player.add(player);
						}
					}
				}
			}
		}
		setDisplayName(getName() + ChatFormatting.GRAY + ":" + player.size());
	}

}
