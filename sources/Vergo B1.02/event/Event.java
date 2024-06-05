package xyz.vergoclient.event;

import xyz.vergoclient.modules.ModuleManager;

public class Event {
	
	public boolean canceled;
	public EventType type;
	
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	public boolean isPre() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.PRE;
		
	}
	
	public boolean isPost() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.POST;
		
	}
	
	public void fire() {
		ModuleManager.fireEvent(this);
	}
	
	public static enum EventType{
		PRE,
		POST
	}
	
}
