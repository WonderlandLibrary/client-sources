package Squad.Modules.Other;

import javax.management.Notification;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;

import Squad.base.Module;
import net.minecraft.entity.player.EntityPlayer;

public class Streammode extends Module{

	  public static String hideTag = "Squad";
	
	public Streammode() {
		super("Streammode", Keyboard.KEY_NONE, 0x88, Category.Other);
		// TODO Auto-generated constructor stub
	}
	
	 public void onEnable()
	  {
	    EventManager.register(this);
	    mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
	    super.onEnable();
	  }
	  
	  public void onDisable()
	  {
	    EventManager.unregister(this);
	    mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
	    super.onDisable();
	  }
	}



