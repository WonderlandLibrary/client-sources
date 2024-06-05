package zamorozka.modules.ZAMOROZKA;

import de.Hero.settings.Setting;
import net.minecraft.network.play.server.SPacketChat;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventNameTag;
import zamorozka.event.events.EventPacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ChatUtils;

public class YoutuberMode extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("NameProtect", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SpoofSkins", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("GuiTabSpoof", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SpoofNames", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("InfoSpoofer", this, true));
	}

	public YoutuberMode() {
		super("YoutuberMode", 0, Category.Zamorozka);
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		if (mc.player == null)
			return;
		if (event.getPacket() != null && event.isIncoming() && event.getPacket() instanceof SPacketChat && Zamorozka.settingsManager.getSettingByName("NameProtect").getValBoolean()) {
			SPacketChat packet = (SPacketChat) event.getPacket();
			if (packet.getChatComponent().getUnformattedText().contains(mc.player.getName())) {
				String temp = packet.getChatComponent().getFormattedText();
				ChatUtils.printChat(temp.replaceAll(mc.player.getName(), "\247d" + name + "\247r"));
				event.setCancelled(true);
			} else {
				String[] list = new String[] { "join", "left", "leave", "leaving", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game" };
				for (String str : list) {
					if (packet.getChatComponent().getUnformattedText().toLowerCase().contains(str)) {
						event.setCancelled(true);
						break;
					}
				}
			}
		}
	}

	@EventTarget
	public void onNameTag(EventNameTag event) {
		if (Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
			event.setRenderedName("Protected");
		}
	}

}