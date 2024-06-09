package lunadevs.luna.module.player;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventMiddleClick;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.faithsminiutils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClick extends Module{

	public MiddleClick() {
		super("MiddleClickFriend", 0, Category.PLAYER, false);
	}
	
	@EventTarget
	public void middleClick(EventMiddleClick event) {
		  {
			    if ((event.key == 2) && (Wrapper.mc.objectMouseOver != null) && (Wrapper.mc.objectMouseOver.entityHit != null) && ((Wrapper.mc.objectMouseOver.entityHit instanceof EntityPlayer)))
			    {
			      EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().objectMouseOver.entityHit;
			      String name = player.getName();
			      if (FriendManager.isFriend(name))
			      {
			        FriendManager.removeFriend(name);
			        Luna.logChatMessage("Removed: " + name);
			      }
			      else
			      {
			        FriendManager.addFriend(name, name);
			        Luna.logChatMessage("Added: " + name);
			      }
			    }
			  }
	}
	
	public String getValue(){
		return null;
	}
	
}
