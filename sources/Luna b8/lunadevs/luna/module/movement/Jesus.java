package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventCollision;
import lunadevs.luna.events.EventLiquidCollide;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.events.EventType;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.BlockUtils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.util.AxisAlignedBB;

public class Jesus extends Module{

	public Jesus() {
		super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	
	  @EventTarget
	  public void onBoundingBox(EventCollision e)
	  {
		  if(!this.isEnabled) return;
	    if (((e.getBlock() instanceof BlockLiquid)) && (e.getEntity() == mc.thePlayer) && (!mc.thePlayer.isInLiquid()) && 
	      (mc.thePlayer.fallDistance < 3.0F) && (!mc.thePlayer.isSneaking())) {
	      e.setBoundingBox(AxisAlignedBB.fromBounds(e.getLocation().getX(), e
	        .getLocation().getY(), e.getLocation().getZ(), e
	        .getLocation().getX() + 1.0D, e.getLocation().getY() + 1.0D, e
	        .getLocation().getZ() + 1.0D));
	    }
	  }
	  
	  @EventTarget
	  public void onPacketSend(EventPacket event)
	  {
		  if(!this.isEnabled) return;
	    if ((event.getPacket() instanceof S49PacketUpdateEntityNBT)) {
	      S49PacketUpdateEntityNBT localS49PacketUpdateEntityNBT = (S49PacketUpdateEntityNBT)event.getPacket();
	    }
	    if (((event.getPacket() instanceof C03PacketPlayer)) && (event.getType() == EventPacket.EventPacketType.SEND))
	    {
	      C03PacketPlayer player = (C03PacketPlayer)event.getPacket();
	      if (mc.thePlayer.isOnLiquid()) {
	        player.setY(mc.thePlayer.posY + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.01D : -0.01D));
	      }
	    }
	  }
	  
	  @EventTarget
	  public void onUpdate(EventPlayerUpdate event)
	  {
		  if(!this.isEnabled) return;
	    if ((event.getType() == EventType.PRE) && (mc.thePlayer.isInLiquid()) && (!mc.thePlayer.isSneaking()) && (!mc.gameSettings.keyBindJump.pressed)) {
	      mc.thePlayer.motionY = 0.1D;
	    }
	  }
	
	
	@Override
	public String getValue() {
		return null;
	}
	
}
