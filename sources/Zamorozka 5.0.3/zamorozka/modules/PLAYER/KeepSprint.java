package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;

public class KeepSprint extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("CustomKeepValue", this, 0.75, 0, 2, true));
	}
	
	public KeepSprint() {
		super("KeepSprint", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		double rc = Zamorozka.settingsManager.getSettingByName("CustomKeepValue").getValDouble();
	      this.setDisplayName("KeepSprint §f§ " + ClientUtils.round((float)Zamorozka.settingsManager.getSettingByName("CustomKeepValue").getValDouble(), 2));
	}
}