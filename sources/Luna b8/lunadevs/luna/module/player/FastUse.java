package lunadevs.luna.module.player;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.events.EventType;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module{

	public FastUse() {
		super("FastUse", 0, Category.PLAYER, false);
	}

	  @EventTarget
	  private void onPostUpdate(EventPlayerUpdate event)
	  {
		  if(!this.isEnabled) return;
	    event.getType();
	    if (event.getType() == EventType.PRE)
	    {
	        mc.rightClickDelayTimer = 1;
	        Minecraft.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, Integer.MAX_VALUE, 0));
	        mc.playerController.blockHitDelay = Integer.MIN_VALUE;
	      if ((Minecraft.thePlayer.getItemInUseDuration() == 16) && 
	        (!(Minecraft.thePlayer.getItemInUse().getItem() instanceof ItemBow)))
	      {
	        for (int i = 0; i < 17; i++) {
	          Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	        }
	        mc.getNetHandler().getNetworkManager().dispatchPacket(new C07PacketPlayerDigging(
	          C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN), null);
	      }
	    }
	  }
	  @Override
	public void onDisable() {
		    if (mc.thePlayer != null) {
		        Minecraft.thePlayer.removePotionEffect(Potion.digSpeed.id);
		      }
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return null;
	}

}
