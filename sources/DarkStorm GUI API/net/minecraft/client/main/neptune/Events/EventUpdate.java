package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.callables.MemeMeable;

public class EventUpdate extends MemeMeable {

	public double y;
	public float yaw, pitch;
	public boolean onGround;
	public EventState state;
    public boolean alwaysSend;
    private double stepHeight;
    private boolean active;
    

	public EventUpdate(double y, float yaw, float pitch, boolean onGround) {
		this.y = y;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.state = EventState.PRE;
	}

	public EventUpdate() {
		this.state = EventState.POST;
	}
	
    public double getStepHeight() {
        return this.stepHeight;
    }
    
    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public void setStepHeight(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
    
    public void setActive(final boolean bypass) {
        this.active = bypass;
    }
    
    public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
    
}
