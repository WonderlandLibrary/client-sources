/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventChatSendMessage;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.utils.Uwuifier;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;

/**
 * @author DistastefulBannock
 *
 */
public class ModUwuifier extends Module {
	
	public ModUwuifier() {
		super("UwUifier", Category.PLAYER);
		setSettings(wholeGame, incomingChat, outgoingChat);
	}
	
	private BooleanSetting wholeGame = new BooleanSetting("Uwuify Whole Game", true);
	private BooleanSetting incomingChat = new BooleanSetting("Incoming Chat Messages", true).setDependency(wholeGame::isDisabled);
	private BooleanSetting outgoingChat = new BooleanSetting("Outgoing Chat Messages", true).setDependency(wholeGame::isDisabled);
	
	@EventHandler
	private Handler<EventChatSendMessage> onChatSendMessage = evt -> {
		if (evt.isPost() || evt.isCanceled() || evt.getMessage().startsWith(".") || evt.getMessage().startsWith("/") || (outgoingChat.isDisabled() && wholeGame.isDisabled()))
			return;
		evt.setMessage(Uwuifier.uwuify(evt.getMessage()));
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = evt -> {
		if (!(evt.getPacket() instanceof S02PacketChat) || evt.isPost() || incomingChat.isDisabled())
			return;
		S02PacketChat s02PacketChat = (S02PacketChat)evt.getPacket();
		if (!(s02PacketChat.getChatComponent() instanceof ChatComponentText))
			return;
		((ChatComponentText)s02PacketChat.getChatComponent()).setText(Uwuifier.uwuify(((ChatComponentText)s02PacketChat.getChatComponent()).getChatComponentText_TextValue()));
	};
	
	/**
	 * @return the wholeGame
	 */
	public BooleanSetting getWholeGame() {
		return wholeGame;
	}
	
}
