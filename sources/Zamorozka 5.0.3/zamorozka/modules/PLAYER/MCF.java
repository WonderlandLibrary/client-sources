package zamorozka.modules.PLAYER;

import java.awt.event.MouseEvent;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MouseHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMouse;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.FriendManager;

public class MCF extends Module {

	public MCF() {
		super("MiddleClickFriends", 0, Category.Zamorozka);
	}

	@EventTarget
	public void onMouseEvent(EventMouse event) {
		if (event.key == 2 && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
			if (indexer.friendManager.isFriend(entityPlayer.getName())) {
				indexer.friendManager.removeFriend(entityPlayer.getName());
				ChatUtils.printChatprefix(ChatFormatting.RED + entityPlayer.getName() + ChatFormatting.WHITE + " Был удален из списка ваших друзей!");
			} else {
				indexer.getFriends().addFriend(entityPlayer.getName(), "MCF");
				ChatUtils.printChatprefix(ChatFormatting.GREEN + entityPlayer.getName() + ChatFormatting.WHITE + " Добавлен в список ваших друзей!");
			}
		}
	}
}