package lunadevs.luna.module.config;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.exploits.AntiBot;
import lunadevs.luna.module.combat.InfiniteAura;
import lunadevs.luna.module.combat.Killaura;
import lunadevs.luna.module.exploits.AACClip;
import lunadevs.luna.module.exploits.NCPGlide;
import lunadevs.luna.module.movement.Boost;
import lunadevs.luna.module.movement.Nuker;
import lunadevs.luna.module.movement.TimerBoost;
import lunadevs.luna.module.player.AACNoFall;

public class Mineplex extends Module {

	public boolean active;

	public Mineplex() {
		super("Mineplex", Keyboard.KEY_NONE, Category.CONFIG, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (ModuleManager.findMod(AACNoFall.class).isEnabled()) {
			ModuleManager.findMod(AACNoFall.class).toggle();
		} else if (ModuleManager.findMod(Boost.class).isEnabled()) {
			ModuleManager.findMod(Boost.class).toggle();
		} else if (ModuleManager.findMod(Nuker.class).isEnabled()) {
			ModuleManager.findMod(Nuker.class).toggle();
		} else if (ModuleManager.findMod(TimerBoost.class).isEnabled()) {
			ModuleManager.findMod(TimerBoost.class).toggle();
		} else if (ModuleManager.findMod(InfiniteAura.class).isEnabled()) {
			ModuleManager.findMod(InfiniteAura.class).toggle();
		} else if (ModuleManager.findMod(NCPGlide.class).isEnabled()) {
			ModuleManager.findMod(NCPGlide.class).toggle();
		} else if (!ModuleManager.findMod(AntiBot.class).isEnabled()) {
			ModuleManager.findMod(AntiBot.class).toggle();
		} else if (ModuleManager.findMod(AACClip.class).isEnabled()) {
			ModuleManager.findMod(AACClip.class).toggle();
		}
		Killaura.range = 7.0f;
		Killaura.block = true;
		Killaura.lock = false;
		AntiBot.GWEN = true;
		AntiBot.Watchdog = false;
	}

	@Override
	public void onEnable() {
		zCore.addChatMessageP("§cWARNING: §7This is in beta, Please remember that.");
		active = true;
	}

	@Override
	public void onDisable() {
		active = false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
