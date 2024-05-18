package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.events.Event;

import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;

public class SafeWalk extends Module {

	public SafeWalk() {
		super("SafeWalk", Keyboard.KEY_NONE, 0x666699, Category.Player);
		setDisplayname("");

	}

	@Override
	public void onEvent(Event e) {
		  Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
		  mc.rightClickDelayTimer = 1;
		  
		  if((item instanceof ItemBlock)) {
		   BlockPos bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
		   mc.gameSettings.keyBindSneak.pressed = false;
		   if(mc.theWorld.getBlockState(bp).getBlock() == Blocks.air) {
		    mc.gameSettings.keyBindSneak.pressed = true;
		   }
		  }
		  
		 }

	
		  
		 

		
		
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	@Override
	public void onEnable() {
		super.onEnable();
	}
}