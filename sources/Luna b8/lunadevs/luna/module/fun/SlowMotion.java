package lunadevs.luna.module.fun;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.util.Timer;

//Coded by faith.

public class SlowMotion extends Module{

	public SlowMotion() {
		super("SlowMotion", Keyboard.KEY_NONE, Category.FUN, false);
	}

	public void onUpdate() {
		if(!this.isEnabled) return;
		
	    if (mc.thePlayer.onGround)
	    {
	      if (mc.thePlayer.moveForward == 0.0F) {}
	      net.minecraft.util.Timer.timerSpeed = 0.5F;
	    }
	  }
	  
	  public void onDisable()
	  {
	    Timer.timerSpeed = 1.0F;
	    mc.thePlayer.landMovementFactor = 0.03F;
	    mc.thePlayer.jumpMovementFactor = 0.03F;
	  }

	
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
