package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.TimerUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;
import events.ItemUseEvent;

public class NoSlowDown extends Module {
	private TimerUtil timer = new TimerUtil();
	int timer1;

	public NoSlowDown() {
		super("No Slow", "noslow", 0, Category.MOVEMENT, new String[]{"noslow", "noslowdown"});
	}

	@EventTarget
	public void onPreUpdate(EventPreMotionUpdates event){
		if ((Wrapper.getPlayer().isBlocking()) && ((Wrapper.getPlayer().motionX != 0.0D) || (Wrapper.getPlayer().motionZ != 0.0D))) {
			Wrapper.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
		}
	}
	  @EventTarget
	  public void onPostUpdate(EventPostMotionUpdates event){
		  if ((Wrapper.getPlayer().isBlocking()) && ((Wrapper.getPlayer().motionX != 0.0D) || (Wrapper.getPlayer().motionZ != 0.0D))) {
		  Wrapper.getPlayer().sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Wrapper.getPlayer().inventory.getCurrentItem()));
	  }
	  }
	@EventTarget
	private void onItemUse(ItemUseEvent event) {
		event.setCancelled(true);
	}

	public void onDisable() {
		EventManager.unregister(this);// Registers the Object of this class to
										// the EventManager.
	}

	public void onEnable() {
		EventManager.register(this);// Registers the Object of this class to the
									// EventManager.
	}
}
