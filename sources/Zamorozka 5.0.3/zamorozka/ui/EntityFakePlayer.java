package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;

public class EntityFakePlayer
extends EntityOtherPlayerMP
{
public static Minecraft mc = Minecraft.getMinecraft();
public EntityFakePlayer() {
  super(mc.world, mc.player.getGameProfile());
  copyLocationAndAnglesFrom(mc.player);

  
  this.inventory.copyInventory((mc.player).inventory);
  cPlayer.copyPlayerModel(mc.player, this);

  
  this.rotationYawHead = (mc.player).rotationYawHead;
  this.renderYawOffset = (mc.player).renderYawOffset;

  
  this.chasingPosX = this.posX;
  this.chasingPosY = this.posY;
  this.chasingPosZ = this.posZ;

  
  mc.world.addEntityToWorld(getEntityId(), this);
}


public void resetPlayerPosition() {
  mc.player.setPositionAndRotation(this.posX, this.posY, this.posZ, 
      this.rotationYaw, this.rotationPitch);
}



public void despawn() { mc.world.removeEntityFromWorld(getEntityId()); }


public void clonePlayer(EntityPlayerSP player, boolean b) {
	// TODO Auto-generated method stub
	
}
}

