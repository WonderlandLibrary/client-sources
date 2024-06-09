package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.BoundingBoxEvent;
import lunadevs.luna.events.Event;
import lunadevs.luna.events.InsideBlockRenderEvent;
import lunadevs.luna.events.PushOutOfBlocksEvent;
import lunadevs.luna.events.UpdateEvent;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.BlockHelper;
import lunadevs.luna.utils.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Phase extends Module{

	public Phase() {
		super("Phase", Keyboard.KEY_NONE, Category.PLAYER, false);
	}
	 @Override
	  public void onUpdate()
	  {
		 if(!this.isEnabled)
			 return;
		 if(mc.thePlayer.isSneaking() || z.p().isCollidedHorizontally) {
	            float dir = mc.thePlayer.rotationYaw;
	            if (mc.thePlayer.moveForward < 0.0f) {
	                dir += 180.0f;
	            }
	            if (mc.thePlayer.moveStrafing > 0.0f) {
	                dir -= 90.0f * ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
	            }
	            if (mc.thePlayer.moveStrafing < 0.0f) {
	                dir += 90.0f * ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
	            }
	            final double hOff = 0.1;
	            final double vOff = 0.2;
	            final float xD = (float)((float)Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0) * 0.1);
	            final float yD = 0.2f;
	            final float zD = (float)((float)Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0) * 0.1);
	            final double[] phase = { -0.02500000037252903, -0.028571428997176036, -0.033333333830038704, -0.04000000059604645, -0.05000000074505806, -0.06666666766007741, -0.10000000149011612, -0.20000000298023224, -0.04000000059604645, -0.033333333830038704, -0.028571428997176036, -0.02500000037252903 };
	            for (int i = 0; i < phase.length; ++i) {
	                z.packet(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + phase[i], mc.thePlayer.posZ, true));
	                z.packet(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + xD * i, mc.thePlayer.posY, mc.thePlayer.posZ + zD * i, true));
	            }
	        }
	    }
	 @EventTarget
	    public void onBB(BoundingBoxEvent event) {
	        if ((mc.thePlayer.isCollidedHorizontally || !mc.thePlayer.onGround || BlockHelper.isInsideBlock() || mc.thePlayer.posY > mc.thePlayer.getEntityBoundingBox().maxY) && event.boundingBox.maxY > mc.thePlayer.getEntityBoundingBox().minY) {
	            event.setBoundingBox(null);
	        }
	}

	  
	  @EventTarget
	  public void onPush(PushOutOfBlocksEvent event)
	  {
	    event.setCancelled(true);
	  }
	  
	  @EventTarget
	  private void onInsideBlockRender(InsideBlockRenderEvent event)
	  {
	    event.setCancelled(true);
	  }
	
	@Override
	public void onEnable() {
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return null;
	}

}
