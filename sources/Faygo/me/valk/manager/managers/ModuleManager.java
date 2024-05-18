package me.valk.manager.managers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

import com.google.common.reflect.ClassPath;

import me.valk.agway.modules.combat.AutoArmorMod;
import me.valk.agway.modules.combat.AutoPotion;
import me.valk.agway.modules.combat.Autoclicker;
import me.valk.agway.modules.combat.Criticals;
import me.valk.agway.modules.combat.GCheatAura;
import me.valk.agway.modules.combat.KillAuraMod;
import me.valk.agway.modules.combat.VelocityMod;
import me.valk.agway.modules.movement.Fly;
import me.valk.agway.modules.movement.Jesus;
import me.valk.agway.modules.movement.LongJump;
import me.valk.agway.modules.movement.NoFallMod;
import me.valk.agway.modules.movement.NoSlowMod;
import me.valk.agway.modules.movement.Scaffold;
import me.valk.agway.modules.movement.SpeedMod;
import me.valk.agway.modules.movement.SprintMod;
import me.valk.agway.modules.other.ChestStealer;
import me.valk.agway.modules.other.MiddleClickMod;
import me.valk.agway.modules.other.PingSpoofMod;
import me.valk.agway.modules.player.FreecamMod;
import me.valk.agway.modules.player.InvMoveMod;
import me.valk.agway.modules.player.NoRotateMod;
import me.valk.agway.modules.player.ZootMod;
import me.valk.agway.modules.render.Chams;
import me.valk.agway.modules.render.FullbrightMod;
import me.valk.agway.modules.render.MotionBlur;
import me.valk.agway.modules.render.NametagsMod;
import me.valk.agway.modules.render.Tracers;
import me.valk.agway.modules.render.Waypoints;
import me.valk.agway.modules.render.XrayMod;
import me.valk.agway.modules.world.Phase;
import me.valk.agway.modules.world.SneakMod;
import me.valk.manager.Manager;
import me.valk.module.ModType;
import me.valk.module.Module;

public class ModuleManager extends Manager<Module> {

	public ModType c;

	public ModuleManager() {

		// COMBAT
		addContent(new AutoArmorMod());
		addContent(new KillAuraMod());
		addContent(new VelocityMod());
		addContent(new Criticals());
		addContent(new Autoclicker());
		addContent(new AutoPotion());
		addContent(new GCheatAura());


		// MOVEMENT
		addContent(new NoSlowMod());
		addContent(new LongJump());
		addContent(new SpeedMod());
		addContent(new NoFallMod());
		addContent(new SprintMod());
		addContent(new Fly());
		addContent(new Jesus());

		// OTHER
		addContent(new MiddleClickMod());
		addContent(new PingSpoofMod());
		addContent(new NoRotateMod());
		addContent(new ChestStealer());

		// PLAYER
		addContent(new Scaffold());
		addContent(new FreecamMod());
		addContent(new InvMoveMod());
		addContent(new SneakMod());
		addContent(new ZootMod());

		// RENDER
		addContent(new Waypoints());
		addContent(new FullbrightMod());
		addContent(new NametagsMod());
		addContent(new Tracers());
		addContent(new Chams());
		addContent(new XrayMod());
		addContent(new MotionBlur());

		
		//WORLD
		addContent(new Phase());

		
	}

	public Module getModuleFromName(String name) {
		for (Module m : getContents()) {
			if (m.getName().equalsIgnoreCase(name))
				return m;
		}

		return null;
	}

	public Module getcat(ModType c) {
		for (Module m : getContents()) {
			if (m.getModCategory2(c))
				return m;
		}

		return null;
	}

	public Module getModuleFromClass(Class clas) {
		for (Module m : getContents()) {
			if (m.getClass() == clas) {
				return m;
			}
		}
		return null;
	}

	private ArrayList<Class<?>> getClasses(final String packageName) {
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			final ClassLoader loader = Thread.currentThread().getContextClassLoader();
			for (final ClassPath.ClassInfo info : ClassPath.from(loader).getAllClasses()) {
				if (info.getName().startsWith(packageName)) {
					final Class<?> clazz = info.load();
					classes.add(clazz);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public boolean hasMod(Module m) {

		for (Module module : getContents()) {
			if (module == m)
				return true;
		}

		return false;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PACKAGE)
	public @interface RegisterModulePackage {

	}

	public boolean getModCategory2(ModType cat) {
		return c == cat;
	}

}
