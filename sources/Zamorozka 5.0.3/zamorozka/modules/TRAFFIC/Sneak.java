package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Sneak extends Module {

	public Sneak() {
		super("Sneak", 0, Category.TRAFFIC);
	}
	
	@Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("Packet");
        Zamorozka.instance.settingsManager.rSetting(new Setting("Sneak Mode", this, "Vanilla", options));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Sneak Mode").getValString();
		if(mode.equalsIgnoreCase("Vanilla")) {
			mc.gameSettings.keyBindSneak.pressed = true;
		}
		if(mode.equalsIgnoreCase("Packet")) {
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
		}
	}
	
	@Override
	public void onDisable() {
		if(!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak))
            mc.gameSettings.keyBindSneak.pressed = false;
		mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
		super.onDisable();
	}

}
