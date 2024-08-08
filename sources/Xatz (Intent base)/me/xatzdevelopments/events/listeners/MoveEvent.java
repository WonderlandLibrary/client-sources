package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;

public final class MoveEvent extends Event<MoveEvent> {
	   public double x;
	   public double y;
	   public double z;

	   public MoveEvent(double x, double y, double z) {
	      this.x = x;
	      this.y = y;
	      this.z = z;
	   }
	}
