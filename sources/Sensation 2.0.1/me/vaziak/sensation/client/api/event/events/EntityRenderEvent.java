package me.vaziak.sensation.client.api.event.events;

import net.minecraft.entity.Entity;

/**
 * @author antja03
 */
public class EntityRenderEvent {
    private float partialTicks;
	private float entityYaw;
	private Entity entity;
	private double z;
	private double y;
	private double x;

    public EntityRenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
 
    }
    
    public float getEntityYaw() {
		return entityYaw;
    }
    
    public float getPartialTicks() {
        return partialTicks;
    }
    
    public Entity getEntity() {
    	return entity;
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
}
