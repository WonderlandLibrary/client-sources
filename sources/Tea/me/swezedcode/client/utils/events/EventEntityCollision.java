package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import me.swezedcode.client.utils.location.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class EventEntityCollision extends EventCancellable {

	private Entity entity;
	private Block block;
	private Location location;
	private AxisAlignedBB boundingBox;

	public EventEntityCollision(Entity entity, Location location, AxisAlignedBB boundingBox, Block block) {
		this.entity = entity;
		this.location = location;
		this.boundingBox = boundingBox;
		this.block = block;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Entity getEntity() {
		return entity;
	}

	public Location getLocation() {
		return location;
	}

	public Block getBlock() {
		return block;
	}

}