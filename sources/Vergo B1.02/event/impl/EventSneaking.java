package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EventSneaking extends Event {
	
	public double x, y, z, d3, d5, offset = -1.0D, hitboxExpand = 0;
	public World worldObj;
	public AxisAlignedBB boundingBox;
	public Entity entity;
	public boolean sneaking;
	public boolean revertFlagAfter = false;
	public boolean postEdgeOfBlock = false;
	
	public AxisAlignedBB getEntityBoundingBox() {
		return boundingBox;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getHitboxExpand() {
		return hitboxExpand;
	}

	public void setHitboxExpand(double hitboxExpand) {
		this.hitboxExpand = hitboxExpand;
	}
	
}
