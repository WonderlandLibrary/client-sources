package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastUse extends Module{

	public FastUse() {
		super("FastUse", Keyboard.KEY_NONE, 0x88888, Category.Player);
		
	}
	
	@EventTarget
	 public void onUpdate(EventUpdate e){ 
		 if (this.getState()) {
		    if (mc.thePlayer.isEating()) {
		      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01D, mc.thePlayer.posZ, false));
		    }
	 	}
	 }
	
	public void onEnable(){
		EventManager.register(this);
	}
	
	public void onDisable(){
		EventManager.unregister(this);
	}

}
