package me.gishreload.yukon.events;

import net.minecraft.client.Minecraft;

public class MoveEvent extends Event implements Cancellable {

	private boolean cancelled;

	private Minecraft minecraft = Minecraft.getMinecraft();

	public double x = minecraft.getPlayer().motionX, y = minecraft.getPlayer().motionY,
			z = minecraft.getPlayer().motionZ;

	public MoveEvent(Minecraft mc) {
		this.minecraft = mc;
		double x = minecraft.getPlayer().motionX;
		double y = minecraft.getPlayer().motionY;
		double z = minecraft.getPlayer().motionZ;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Minecraft getMinecraft() {
		return minecraft;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
