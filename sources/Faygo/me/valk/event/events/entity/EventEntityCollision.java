package me.valk.event.events.entity;

import me.valk.event.Event;
import me.valk.help.world.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventEntityCollision extends Event {

	private Entity entity;
	private Block block;
	private Location location;
	private AxisAlignedBB boundingBox;

	public EventEntityCollision(Entity entity, Location location, AxisAlignedBB boundingBox,
			Block block) {
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
