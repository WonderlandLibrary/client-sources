package me.gishreload.yukon.events;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerConnectEvent extends Event implements Cancellable {

	private boolean cancelled;

	private EntityPlayer player;

	public PlayerConnectEvent(EntityPlayer connected) {
		this.player = connected;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
