package me.valk.event.events.screen;

import me.valk.event.Event;
import net.minecraft.tileentity.TileEntity;

public class EventRenderTileEntity extends Event {

	private TileEntity tileEntity;

	public EventRenderTileEntity(TileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	public TileEntity getTileEntity() {
		return tileEntity;
	}
		
}
