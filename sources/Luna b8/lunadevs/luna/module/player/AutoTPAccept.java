package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.PacketRecieveEvent;
import lunadevs.luna.friend.Friend;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoTPAccept extends Module{

	public AutoTPAccept() {
		super("TPAccept", Keyboard.KEY_NONE, Category.PLAYER, false);
	}
	
	@EventTarget
	public void recievePacket(PacketRecieveEvent event) {
		if(!this.isEnabled) return;
		S02PacketChat msg = (S02PacketChat)event.getPacket();
		if(msg.func_148915_c().getFormattedText().contains("has requested to teleport to you")) {
			for(Friend friend : FriendManager.friendsList) {
		          if (msg.func_148915_c().getFormattedText().contains(friend.name)) {
		        	  mc.thePlayer.sendChatMessage("/tpaccept");
		        	  break;
		          }
			}
		}
	}
	
	public String getValue() {
		return null;
	}
	
}
