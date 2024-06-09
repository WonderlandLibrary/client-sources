package lunadevs.luna.module.fun;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import lunadevs.luna.utils.TimerUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;
import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;

//coded by faith

public class Spammer extends Module{

	private String[] spamList = { "MiDNiGHT developed by MinemanFaith, Mega_Mixer, Timothy/ZiTROX", "I am using the best hacked client!!", "Did you know? That You can download Luna for free! but only for a certain time!", "Im not hacking, just energy drink.","No hax just Nuddles mixtape!","If the floor is stormanticheat just toggle fly and fly away cause stormanticheat is terrible!","ZOOM ZOOM ZOOOM","Nuddles is a tall midget!"  };
	private int last;
	TimerUtil timer = new TimerUtil();
	
	public Spammer() {
		super("Spammer", Keyboard.KEY_NONE, Category.FUN, false);
	}
	
	public void onUpdate() {
		if(!this.isEnabled) return;
		if(this.timer.hasDelay(0.3D)) {
			mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(randomPhrase()));
			this.timer.reset();
		}
	}
	
	public String getValue() {
		return null;
	}
	
	  private String randomPhrase()
	  {
	    Random rand = new Random();
	    int randInt = rand.nextInt(this.spamList.length);
	    while (this.last == randInt) {
	      randInt = rand.nextInt(this.spamList.length);
	    }
	    this.last = randInt;
	    return this.spamList[randInt];
	  }
	  
	  private int randomDelay()
	  {
	    Random randy = new Random();
	    int randyInt = randy.nextInt(4000) + 4000;
	    return randyInt;
	  }
	
}
