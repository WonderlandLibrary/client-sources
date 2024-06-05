package me.rich.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import me.rich.module.combat.*;
import me.rich.module.hud.*;
import me.rich.module.misc.*;
import me.rich.module.movement.*;
import me.rich.module.player.*;
import me.rich.module.render.*;
import net.minecraft.client.Minecraft;

public class FeatureDirector {
	public static ArrayList<Feature> modules = new ArrayList<Feature>();

	public FeatureDirector() {
		// Combat.
		modules.add(new AutoGApple());
		modules.add(new AutoArmor());
		modules.add(new AutoPotion());
		modules.add(new AutoTotem());
		modules.add(new TriggerBot());
		modules.add(new Velocity());
		modules.add(new KillAura());
		modules.add(new AntiBot());
		modules.add(new HitBox());
		
		// Movement.
		modules.add(new TargetStrafe());
		modules.add(new WaterLeave());
		modules.add(new AutoSprint());
		modules.add(new NoDelay());
		modules.add(new AirJump());
		modules.add(new GuiWalk());
		modules.add(new Speed());
		modules.add(new Timer());
		modules.add(new Eagle());
		modules.add(new Jesus());
		modules.add(new Flight());

		// Player.
		modules.add(new MiddleClickPearl());
		modules.add(new NoSlowDown());
		modules.add(new AutoVClip());
        modules.add(new FreeCam());
        modules.add(new Spider());
        modules.add(new NoInteract());
		modules.add(new KeepSprint());
		modules.add(new AntiCollision());
		modules.add(new NoClip());
		
		// Render.
		modules.add(new BlockHitAnimation());
		modules.add(new ParticleTrails());
		modules.add(new Breadcrumbs());
		modules.add(new BlockOverlay());
		modules.add(new ChatFeatures());
		modules.add(new NameProtect());
		modules.add(new ScoreBoard());
		modules.add(new TriangleESP());
		modules.add(new TargetHUD());
		modules.add(new Animations());
		modules.add(new ArmorHUD());
		modules.add(new WorldTime());
		modules.add(new ViewModel());
		modules.add(new NameTags());
		modules.add(new NoRender());
		modules.add(new FullBright());
		modules.add(new Crosshair());
		modules.add(new ChestESP());
		modules.add(new ChinaHat());
		modules.add(new FogColor());
		modules.add(new ClickGUI());
		modules.add(new Tracers());
		modules.add(new Chams());
		modules.add(new ESP());
	
		
		// Misc.
		modules.add(new ModuleSoundAlert());
		modules.add(new MiddleClickFriend());
		modules.add(new AutoRespawn());
        modules.add(new NoSRotations());
        modules.add(new AutoAccept());
        modules.add(new AutoAuth());
        modules.add(new Disabler());
    
		// Hud.
		modules.add(new Watermark());
		modules.add(new SessionInfo());
		modules.add(new FeatureList());
		modules.add(new Indicators());
		modules.add(new KeyBinds());
		
		modules.sort(Comparator.comparingInt(m -> Minecraft.getMinecraft().fontRendererObj.getStringWidth(((Feature) m).getName())).reversed());
	}


	public static ArrayList<Feature> getModules() {
		return modules;
	}
    
	public Feature getModuleByName(String name) {
		return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
    public List<Feature> getCheats(Category cheatType) {
        return getModules().stream()
                .filter(cheat -> cheat.getCategory() == cheatType)
                .collect(Collectors.toList());
    }

	public Feature getModule(Class moduleClass) {
		for (Feature module : modules) {
			if (module.getClass() != moduleClass) {
				continue;
			}
			return module;
		}
		return null;
		}
	
    public Feature[] getModulesInCategory(Category category) {
        return this.modules.stream().filter(module -> module.getCategory() == category).toArray(Feature[]::new);
    }
}
