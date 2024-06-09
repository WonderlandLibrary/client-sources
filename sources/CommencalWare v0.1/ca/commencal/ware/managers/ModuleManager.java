package ca.commencal.ware.managers;

import ca.commencal.ware.gui.click.ClickGuiScreen;
import ca.commencal.ware.gui.click.theme.dark.DarkTheme;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.modules.combat.AutoArmor;
import ca.commencal.ware.module.modules.player.Sprint;
import ca.commencal.ware.module.modules.player.Strafe;
import ca.commencal.ware.module.modules.render.ClickGui;
import ca.commencal.ware.module.modules.combat.KillAura;
import ca.commencal.ware.module.modules.render.FullBright;
import ca.commencal.ware.module.modules.render.NoRain;
import ca.commencal.ware.utils.system.Wrapper;
import ca.commencal.ware.value.Mode;
import ca.commencal.ware.value.ModeValue;
import ca.commencal.ware.value.Value;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {

	private static Module toggleModule = null;
	private static ArrayList<Module> modules;
	private GuiManager guiManager;
	private ClickGuiScreen guiScreen;

	public ModuleManager() {
		modules = new ArrayList<Module>();

		addModule(new KillAura());
		addModule(new AutoArmor());
		addModule(new NoRain());
		addModule(new ClickGui());
		addModule(new Sprint());
		addModule(new Strafe());
		addModule(new FullBright());
	}

	public void setGuiManager(GuiManager guiManager) {
		this.guiManager = guiManager;
	}

	public ClickGuiScreen getGui() {
		if (this.guiManager == null) {
			this.guiManager = new GuiManager();
			this.guiScreen = new ClickGuiScreen();
			ClickGuiScreen.clickGui = this.guiManager;
			this.guiManager.Initialization();
			this.guiManager.setTheme(new DarkTheme());
		}
		return this.guiManager;
	}

	public static Module getModule(String name) {
		Module module = null;
		for(Module h : getModules()) {
			if(h.getName().equals(name)) {
				module = h;
			}
		}
		return module;
	}

	public static List<Module> getSortedModules() {
		final List<Module> list = new ArrayList<Module>();
		for (final Module module : getModules()) {
			if (module.isToggled()) {
				if (!module.isShow()) {
					continue;
				}
				list.add(module);
			}
		}
		list.sort(new Comparator<Module>() {
			@Override
			public int compare(final Module h1, final Module h2) {
				String s1 = h1.getName();
				String s2 = h2.getName();
				for(Value value : h1.getValues()) {
					if(value instanceof ModeValue) {
						ModeValue modeValue = (ModeValue)value;
						if(!modeValue.getModeName().equals("Priority")) {
							for(Mode mode : modeValue.getModes()) {
								if(mode.isToggled()) {
									s1 = s1 + " " + mode.getName();
								}
							}
						}
					}
				}
				for(Value value : h2.getValues()) {
					if(value instanceof ModeValue) {
						ModeValue modeValue = (ModeValue)value;
						if(!modeValue.getModeName().equals("Priority")) {
							for(Mode mode : modeValue.getModes()) {
								if(mode.isToggled()) {
									s2 = s2 + " " + mode.getName();
								}
							}
						}
					}
				}
				final int cmp = Wrapper.INSTANCE.fontRenderer().getStringWidth(s2) - Wrapper.INSTANCE.fontRenderer().getStringWidth(s1);
				return (cmp != 0) ? cmp : s2.compareTo(s1);
			}
		});
		return list;
	}

	public static void addModule(Module module) {
		modules.add(module);
	}

	public static ArrayList<Module> getModules() {
		return modules;
	}

	public static ArrayList<Module> getToggleModule() {
		ArrayList<Module> ToggledModules = new ArrayList<Module>();

		for(Module module : modules){
			if(module.isToggled()){
				ToggledModules.add(module);
			}
		}

		return ToggledModules;
	}

	public static void onKeyPressed(int key) {
		if (Wrapper.INSTANCE.mc().currentScreen != null) {
			return;
		}
		for(Module module : getModules()) {
			if(module.getKey() == key) {
				module.toggle();
				toggleModule = module;
			}
		}
	}

	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onCameraSetup(event);
			}
		}
	}

	public static void onAttackEntity(AttackEntityEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onAttackEntity(event);
			}
		}
	}

	public static void onProjectileImpact(ProjectileImpactEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onProjectileImpact(event);
			}
		}
	}

	public static void onItemPickup(EntityItemPickupEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onItemPickup(event);
			}
		}
	}

	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onPlayerTick(event);
			}
		}
	}

	public static void onClientTick(TickEvent.ClientTickEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onClientTick(event);
			}
		}
	}
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onLivingUpdate(event);
			}
		}
	}
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onRenderWorldLast(event);
			}
		}
	}
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onRenderGameOverlay(event);
			}
		}
	}
}
