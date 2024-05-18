package com.darkcart.xdolf.util;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreecamEntity extends EntityOtherPlayerMP
{
	public FreecamEntity()
	{
		super(Wrapper.getWorld(), Wrapper.getPlayer().getGameProfile());
		copyLocationAndAnglesFrom(Wrapper.getPlayer());
		
		clonePlayer(Wrapper.getPlayer(), true);
		
		rotationYawHead = Wrapper.getPlayer().rotationYawHead;
		renderYawOffset = Wrapper.getPlayer().renderYawOffset;
		
		chasingPosX = posX;
		chasingPosY = posY;
		chasingPosZ = posZ;
		
		Wrapper.getWorld().addEntityToWorld(getEntityId(), this);
	}
	
	public void resetPlayerPosition()
	{
		Wrapper.getPlayer().setPositionAndRotation(posX, posY, posZ,
			rotationYaw, rotationPitch);
	}
	
	public void despawn()
	{
		Wrapper.getWorld().removeEntityFromWorld(getEntityId());
	}
}