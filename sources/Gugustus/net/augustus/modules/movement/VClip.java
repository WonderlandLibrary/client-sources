package net.augustus.modules.movement;

import java.awt.Color;

import net.augustus.events.EventBlockBoundingBox;
import net.augustus.events.EventTick;
import net.augustus.events.EventUpdate;
import net.augustus.events.PushOutOfBlockEvent;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.skid.rise5.PlayerUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.texture.Stitcher.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class VClip extends Module {
   public VClip() {
      super("VClip", new Color(63, 255, 0, 255), Categorys.MOVEMENT);
   }

   public void onDisable() {
	   mc.thePlayer.noClip = false;
   }
   
   @EventTarget
   public void onEventBlockBB(EventBlockBoundingBox event) {
	   if(PlayerUtil.isInsideBlock()) {
		   event.setAxisAlignedBB(null);
		   // Sets The Bounding Box To The Players Y Position.
           if (!(event.getBlock() instanceof BlockAir) && !mc.gameSettings.keyBindSneak.isKeyDown()) {
               final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(),
                       z = event.getBlockPos().getZ();

               if (y < mc.thePlayer.posY) {
                   event.setAxisAlignedBB(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
               }
           }
	   }
   }
   
   @EventTarget
   public void onEventPushOutOfBlock(PushOutOfBlockEvent e) {
	   e.setCancelled(true);
   }
   
   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.gameSettings.keyBindSneak.isPressed() && !mm.fly.isToggled()) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3.0, mc.thePlayer.posZ);
         if ((mc.theWorld == null || mc.thePlayer == null) && mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater()) {
            mc.thePlayer.motionY -= 6.0;
         }
      }
   }
   
   @EventTarget
   public void onEventUpdate(EventUpdate e) {
	   mc.thePlayer.noClip = true;
	   boolean foundBlocks = false;
	   int slot = -1;
	   for(int i = 0; i < mc.thePlayer.inventory.getHotbarSize(); i++) {
		   if(!foundBlocks)
			   mc.thePlayer.inventory.currentItem = i;
		   if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
			   slot = i;
			   foundBlocks = true;
		   }
	   }
	   if(slot == -1 || PlayerUtil.isInsideBlock())
		   return;
	   //can be removed since it already does it above
	   mc.thePlayer.inventory.currentItem = slot;
	   mc.thePlayer.rotationPitch = 90;
	   
	   if(mc.thePlayer.rotationPitch >= 89 && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.thePlayer.posY == mc.objectMouseOver.getBlockPos().up().getY()) {
		   mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);
		   mc.thePlayer.swingItem();
	   }
   }
}
