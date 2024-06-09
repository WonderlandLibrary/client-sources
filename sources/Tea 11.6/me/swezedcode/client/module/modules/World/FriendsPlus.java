package me.swezedcode.client.module.modules.World;

import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FriendManager;
import me.swezedcode.client.manager.managers.more.Friend;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventReadPacket;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StringUtils;

public class FriendsPlus extends Module {

	public FriendsPlus() {
		super("Friends+", Keyboard.KEY_NONE, 0xFF3CF096, ModCategory.World);
	}

	private BooleanValue middleClick = new BooleanValue(this, "Middle Click", "", Boolean.valueOf(true));
	private BooleanValue autoAccept = new BooleanValue(this, "Auto Accept", "", Boolean.valueOf(true));
	private BooleanValue factionsAccept = new BooleanValue(this, "Auto Join Faction Request", "",
			Boolean.valueOf(true));

	@EventListener
	public void onPacketRecive(EventReadPacket e) {
		if ((e.getPacket() instanceof S02PacketChat) && (autoAccept.getValue())) {
			S02PacketChat chat = (S02PacketChat) e.getPacket();
			String message = StringUtils.stripControlCodes(chat.func_148915_c().getFormattedText());
			for (Friend names : Manager.getManager().getFriendManager().getFriends()) {
				if ((message.contains(names.getUser())) && ((message.contains("has requested to teleport to you."))
						|| (message.contains("has requested you teleport to them.")))) {
					mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("/tpaccept"));
				}
			}
		}

		if ((e.getPacket() instanceof S02PacketChat) && (factionsAccept.getValue())) {
			S02PacketChat chat = (S02PacketChat) e.getPacket();
			String message = StringUtils.stripControlCodes(chat.func_148915_c().getFormattedText());
			for (Friend names : Manager.getManager().getFriendManager().getFriends()) {
				if ((message.contains(names.getUser())) && ((message.contains("has invited you to"))
						|| (message.contains("has invited you to join")))) {
					mc.thePlayer.sendQueue
							.addToSendQueue(new C01PacketChatMessage("/f join " + names.getUser().toString()));
				}
			}
		}
	}

	@Override
	public void onMidClick() {
		if (!this.isToggled())
			return;
		if (middleClick.getValue()) {
			if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectType.ENTITY
					&& Minecraft.getMinecraft().objectMouseOver.typeOfHit != null
					&& Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityPlayer) {
				String s = Minecraft.getMinecraft().objectMouseOver.entityHit.getName();
				if (!Manager.getManager().getFriendManager().isFriend(s)) {
					Manager.getManager().getFriendManager().addFriend(s, s);
				} else {
					Manager.getManager().getFriendManager().removeFriend(s);
				}
				msg(FriendManager.isFriend(s) ? "Added \"" + s + "\"." : "Deleted \"" + s + "\".");
			}
		}

	}

}
