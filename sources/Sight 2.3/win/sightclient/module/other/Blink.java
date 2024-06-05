package win.sightclient.module.other;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class Blink extends Module {

	private ArrayList<Packet> packets = new ArrayList<Packet>();
	
	private TTFFontRenderer font;
	
	public Blink() {
		super("Blink", Category.OTHER);
	}

	@Override
	public void onEvent(Event e) {
		if (mc.theWorld == null || mc.thePlayer == null) {
			this.setToggled(false);
			return;
		}
		if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (!ep.isCancelled() && ep.isSending() && mc.thePlayer != null) {
				this.packets.add(ep.getPacket());
				ep.setCancelled();
			}
		} else if (e instanceof EventRender2D) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			
			if (font == null) {
				font = Sight.instance.fm.getFont("SFUI 16");
			}
			font.drawCenteredString("Blink is enabled.", sr.getScaledWidth() / 2, 18, -1);
		} else if (e instanceof EventUpdate) {
			if (!mc.thePlayer.isEntityAlive()) {
				this.setToggled(false);
			}
		}
	}
	
	@Override
	public void onDisable() {
		if (mc.thePlayer != null) {
			for (Packet p : this.packets) {
				mc.thePlayer.sendQueue.addToSendQueue(p);
			}
		}
		this.packets.clear();
	}
}
