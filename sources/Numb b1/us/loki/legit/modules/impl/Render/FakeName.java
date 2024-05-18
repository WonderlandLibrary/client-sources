package us.loki.legit.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import io.netty.util.internal.ThreadLocalRandom;
import us.loki.legit.Client;
import us.loki.legit.events.EventUpdate;
import us.loki.legit.modules.Category;
import us.loki.legit.modules.Module;
import us.loki.legit.utils.TimeHelper;

public class FakeName extends Module {
	public static boolean isEnabled = false;
	public static String fakename = "$ketchy";
	public static String fakename2 = "$pooky";
	TimeHelper time = new TimeHelper();

	public FakeName() {
		super("FakeName", "FakeName", Keyboard.KEY_K, Category.RENDER);
		
		Client.instance.setmgr.rSetting(new Setting("Change Name", this, false));
	}

	public void onEnable() {
		isEnabled = true;
		EventManager.register(this);
		super.onEnable();
	}

	public void onDisable() {
		isEnabled = false;
		EventManager.unregister(this);
		super.onDisable();
	}

	private int getRandomIntInRange(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		if (Client.instance.setmgr.getSettingByName("Change Name").getValBoolean()) {
		if (this.time.hasTimePassedM((long) Client.instance.setmgr.getSettingByName("Gamma (Test)").getValDouble()
				+ (long) this.getRandomIntInRange(0, 75))) {
			FakeName.mc.playerController.attackEntity(FakeName.mc.thePlayer, FakeName.mc.objectMouseOver.entityHit);
			FakeName.mc.thePlayer.swingItem();
		}
		this.time.updateLastMS();
	}
	}
}
