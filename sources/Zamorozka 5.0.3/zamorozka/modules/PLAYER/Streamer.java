package zamorozka.modules.PLAYER;

import de.Hero.settings.Setting;
import net.minecraft.network.play.server.SPacketChat;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ChatUtils;

public class Streamer extends Module {

	private final String SPOOFSKINS = "SPOOFSKINS";
    private final String SCRAMBLE = "SCRAMBLENAMES";
    public static String YOURNAME = "YOURNAME";
    private String NAMEPROTECT = "NAMEPROTECT";
    public static boolean spoofskin;
    public static boolean scrambleNames;
    public static boolean nameprotect;
    public static String name;
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("NameProtect", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SpoofSkins", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ScrambleNames", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("YourName", this, true));
	}
	public Streamer() {
		super("YoutuberMode", 0, Category.Exploits);
	}
	 @EventTarget
	    public void onPacket(EventPacket event) {
	        name = Zamorozka.settingsManager.getSettingByName("YourName").getValString();
	        spoofskin = Zamorozka.settingsManager.getSettingByName("SpoofSkins").getValBoolean();
	        scrambleNames = Zamorozka.settingsManager.getSettingByName("SpoofSkins").getValBoolean();
	        nameprotect = Zamorozka.settingsManager.getSettingByName("NameProtect").getValBoolean();

	        if (event.getPacket() != null && event.isIncoming() && event.getPacket() instanceof SPacketChat && Zamorozka.settingsManager.getSettingByName("NameProtect").getValBoolean()) {
	            SPacketChat packet = (SPacketChat) event.getPacket();
	            if (packet.getChatComponent().getUnformattedText().contains(mc.player.getName())) {
	                String temp = packet.getChatComponent().getFormattedText();
	                ChatUtils.printChat(temp.replaceAll(mc.player.getName(), "\247d" + name + "\247r"));
	                event.setCancelled(true);
	            } else {
	                String[] list = new String[]{"join", "left", "leave", "leaving", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game"};
	                for (String str : list) {
	                    if (packet.getChatComponent().getUnformattedText().toLowerCase().contains(str)) {
	                        event.setCancelled(true);
	                        break;
	                    }
	                }
	            }
	        }
	    }
	}
