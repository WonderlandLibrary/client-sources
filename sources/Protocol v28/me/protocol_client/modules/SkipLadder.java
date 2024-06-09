package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class SkipLadder extends Module{

	public SkipLadder() {
		super("Fast Ladder", "fastladder", 0, Category.MOVEMENT, new String[]{"ladder", "fastladder"});
	}
	
	public final Value<Boolean> vanilla = new Value<>("fastladder_vanilla", false);
	
	@EventTarget
	 public void onEvent(EventPreMotionUpdates pre)
    {
		setDisplayName("Fast Ladder");
		if(vanilla.getValue()){
			setDisplayName(getDisplayName() + " [Vanilla]");
		}else{
			setDisplayName(getDisplayName());
		}
	  if(Minecraft.getMinecraft().thePlayer.isOnLadder() && Minecraft.getMinecraft().thePlayer.isCollidedHorizontally){
		 Minecraft.getMinecraft().thePlayer.motionY = !vanilla.getValue() ? 0.2873F : 0.4f;
	    }
    }
	public void onEnable()
	  {
	    EventManager.register(this);
	  }
	  
	  public void onDisable()
	  {
	    EventManager.unregister(this);
	  }
}
