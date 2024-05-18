package Squad.Modules.Player;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class Spammer extends Module {
	Random r = new Random();
	private int delay = 0;
	public static float myDelay = 100;
	public static String msg = "Sqoad best client ";
	
	public Spammer() {
		super("Spammer", "", Keyboard.KEY_NONE, 0xffffff, Category.Player);
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(this.getState()){
			if(delay >= myDelay){
				mc.thePlayer.sendChatMessage(msg + " - " + r.nextInt(1000));
				delay = 0;
			} else {
				delay++;
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
