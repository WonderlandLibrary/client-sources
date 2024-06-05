package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FastFall extends Module {

	public FastFall() {
		super("FastFall", Keyboard.KEY_NONE, Category.TRAFFIC);
	}
	
	public ArrayList<Entity> enad = new ArrayList<Entity>();
    @Override
    public void setup() {
    	Zamorozka.settingsManager.rSetting(new Setting("FallDistance", this, 5, 1.0, 10.0, true));
    	Zamorozka.settingsManager.rSetting(new Setting("Speed", this, 5, 0.1, 10.0, false));
    }
	@EventTarget
	public void onUpdate(EventUpdate event) {
		float fallDistance = (float) Zamorozka.settingsManager.getSettingByName("FallDistance").getValDouble();
		float speed = (float) Zamorozka.settingsManager.getSettingByName("Speed").getValDouble();
		if (mc.player.fallDistance >= (float)fallDistance) {
			 mc.player.motionX = 0.01f;
			  mc.player.motionY= -speed;
			  mc.player.connection.sendPacket(new CPacketPlayer(true));
		}
	}
}