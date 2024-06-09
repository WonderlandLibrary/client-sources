package lunadevs.luna.module.config;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.exploits.AntiBot;
import lunadevs.luna.module.combat.AntiFire;
import lunadevs.luna.module.combat.AntiKnockback;
import lunadevs.luna.module.combat.FastBow;
import lunadevs.luna.module.combat.InfiniteAura;
import lunadevs.luna.module.combat.Killaura;
import lunadevs.luna.module.exploits.NCPGlide;
import lunadevs.luna.module.exploits.PacketFly;
import lunadevs.luna.module.exploits.Regen;
import lunadevs.luna.module.exploits.Zoot;
import lunadevs.luna.module.movement.AirHop;
import lunadevs.luna.module.movement.Boost;
import lunadevs.luna.module.movement.Nuker;
import lunadevs.luna.module.movement.TimerBoost;

public class GommeHD extends Module {

	public boolean active;

	public GommeHD() {
		super("GommeHD", Keyboard.KEY_NONE, Category.CONFIG, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (ModuleManager.findMod(AirHop.class).isEnabled()) {
			ModuleManager.findMod(AirHop.class).toggle();
		} else if (ModuleManager.findMod(Boost.class).isEnabled()) {
			ModuleManager.findMod(Boost.class).toggle();
		} else if (ModuleManager.findMod(Nuker.class).isEnabled()) {
			ModuleManager.findMod(Nuker.class).toggle();
		} else if (ModuleManager.findMod(TimerBoost.class).isEnabled()) {
			ModuleManager.findMod(TimerBoost.class).toggle();
		} else if (ModuleManager.findMod(Regen.class).isEnabled()) {
			ModuleManager.findMod(Regen.class).toggle();
		} else if (ModuleManager.findMod(FastBow.class).isEnabled()) {
			ModuleManager.findMod(FastBow.class).toggle();
		} else if (ModuleManager.findMod(InfiniteAura.class).isEnabled()) {
			ModuleManager.findMod(InfiniteAura.class).toggle();
		} else if (ModuleManager.findMod(PacketFly.class).isEnabled()) {
			ModuleManager.findMod(PacketFly.class).toggle();
		} else if (ModuleManager.findMod(NCPGlide.class).isEnabled()) {
			ModuleManager.findMod(NCPGlide.class).toggle();
		} else if (ModuleManager.findMod(AntiKnockback.class).isEnabled()) {
			ModuleManager.findMod(AntiKnockback.class).toggle();
		} else if (!ModuleManager.findMod(AntiBot.class).isEnabled()) {
			ModuleManager.findMod(AntiBot.class).toggle();
		} else if (ModuleManager.findMod(AntiFire.class).isEnabled() || (ModuleManager.findMod(Zoot.class).isEnabled() )) {
			ModuleManager.findMod(AntiFire.class).toggle();
			ModuleManager.findMod(Zoot.class).toggle();
		}
		Killaura.range = 4.5f;
		Killaura.block = true;
		Killaura.lock = false;
		AntiBot.Advanced = true;
		AntiBot.Watchdog = false;
		AntiBot.GWEN = false;
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
