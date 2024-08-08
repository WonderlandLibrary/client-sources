package me.xatzdevelopments.events.listeners;

import net.minecraft.client.gui.ScaledResolution;
import me.xatzdevelopments.events.Event;

public class EventRenderGUI extends Event<EventRenderGUI> {
	 private static ScaledResolution scaledResolution;
	public static ScaledResolution getScaledResolution() {
	      return EventRenderGUI.scaledResolution;
	   }
	
	 
}
