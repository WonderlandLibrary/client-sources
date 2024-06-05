package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreecamEntity extends EntityOtherPlayerMP
{
	 public static Minecraft mc = Minecraft.getMinecraft();
	public FreecamEntity()
	{
		super(mc.world, mc.player.getGameProfile());
		copyLocationAndAnglesFrom(mc.player);
		
		rotationYawHead = mc.player.rotationYawHead;
		renderYawOffset = mc.player.renderYawOffset;
		
		chasingPosX = posX;
		chasingPosY = posY;
		chasingPosZ = posZ;
		
		mc.world.addEntityToWorld(getEntityId(), this);
	}
	
	public void resetPlayerPosition()
	{
		mc.player.setPositionAndRotation(posX, posY, posZ,
			rotationYaw, rotationPitch);
	}
	
	public void despawn()
	{
		mc.world.removeEntityFromWorld(getEntityId());
	}
}