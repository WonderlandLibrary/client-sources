package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventAttack;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.MouseAttackEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.TimerHelper;

public class AutoShiftTap extends Module {

	public AutoShiftTap() {
		super("AutoShiftTap", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("OnMouse");
		options.add("OnJump");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Tap Mode", this, "OnMouse", options));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Tap Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("AutoShiftTap §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("OnMouse")) {
			if (mc.gameSettings.keyBindAttack.pressed) {
				mc.gameSettings.keyBindSneak.pressed = true;
			} else {
				mc.gameSettings.keyBindSneak.pressed = false;
			}
		}
		if (mode.equalsIgnoreCase("OnJump")) {
			if (mc.player.fallDistance >= 0.08) {
				mc.gameSettings.keyBindSneak.pressed = true;
			} else {
				if (mc.player.onGround) {
					mc.gameSettings.keyBindSneak.pressed = false;
				}
			}
		}
	}

}