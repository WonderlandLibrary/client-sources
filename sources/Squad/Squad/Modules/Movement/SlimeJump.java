package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class SlimeJump extends Module{

	
	 private int yee = 1;
	
	public SlimeJump() {
		super("SlimeJump", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}
	@EventTarget
	public void onUpdate(EventUpdate e){
		        BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY -1.0D, mc.thePlayer.posZ);
		      if(mc.theWorld.getBlockState(bp).getBlock() == Blocks.slime_block){
		    	if(mc.gameSettings.keyBindJump.pressed){
		    		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook());
		    		mc.thePlayer.motionY += 1.0832D;
		    		mc.thePlayer.motionX += 0.1D;
		    	}else {
		    		mc.timer.timerSpeed = 1F;
		    	}
		        }
		       }
				}
	

			
		
	
