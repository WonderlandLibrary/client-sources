package win.sightclient.module.combat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.player.EntityPlayer;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.chat.EventChatReceive;
import win.sightclient.event.events.player.EventAttack;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.button.ButtonSetting;
import win.sightclient.module.settings.button.Execute;
import win.sightclient.notification.Notification;
import win.sightclient.utils.FileUtils;

public class KillSults extends Module {

	public static ArrayList<String> insults = new ArrayList<String>();
	
	private ButtonSetting reload = new ButtonSetting("Reload", this, new Execute() {
		@Override
		public void onButtonClick() {
			KillSults.insults = FileUtils.getLines(Sight.instance.fileManager.getKillsultsFile().getFile());
			Sight.instance.nm.send(new Notification("Killsults", "Reloaded Killsults"));
		}
	});
	
	private EntityPlayer lastHit;
	private String lastMessage = "";
	
	public KillSults() {
		super("KillSults",  Category.COMBAT);
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAttack) {
			EventAttack ea = (EventAttack)e;
			if (ea.getHit() instanceof EntityPlayer) {
				lastHit = (EntityPlayer) ea.getHit();
			}
		} else if (e instanceof EventChatReceive) {
			EventChatReceive ec = (EventChatReceive)e;
			if (this.lastHit != null) {
				String message = ec.getMessage().getUnformattedText();
				if (message.startsWith(this.lastHit.getName()) && (message.endsWith(mc.thePlayer.getName()) || message.endsWith(mc.thePlayer.getName() + "."))) {
					this.insult();
				}
			}
		}
	}
	
	public void insult() {
		if (this.insults.isEmpty()) {
			return;
		}
		String insult = this.insults.get(ThreadLocalRandom.current().nextInt(this.insults.size()));
		if (insult.equals(lastMessage) && lastMessage != "" && this.insults.contains(lastMessage)) {
			while (insult.equals(lastMessage)) {
				insult = this.insults.get(ThreadLocalRandom.current().nextInt(this.insults.size()));
			}
		}
		lastMessage = insult;
		mc.thePlayer.sendChatMessage(insult.replaceAll("%name%", this.lastHit.getName()));
	}
}
