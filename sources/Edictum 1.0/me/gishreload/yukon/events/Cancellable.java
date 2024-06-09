package me.gishreload.yukon.events;

public interface Cancellable {
	boolean isCancelled();

	void setCancelled(boolean cancelled);
}
