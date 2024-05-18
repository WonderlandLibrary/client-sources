package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class Scaffoldhelper extends Module{

	public Scaffoldhelper() {
		super("ScaffoldHelper", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}

	@EventTarget
	public void onUpdate(EventUpdate e){

		if(mc.thePlayer.distanceWalkedModified > 2){
		if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,
			     mc.thePlayer.posY - 0.5, mc.thePlayer.posZ)).getBlock() instanceof
			     BlockAir){
			mc.gameSettings.keyBindSneak.pressed = true;
			
			
		}else{
			mc.gameSettings.keyBindSneak.pressed = false;
		}
	}

}

}
