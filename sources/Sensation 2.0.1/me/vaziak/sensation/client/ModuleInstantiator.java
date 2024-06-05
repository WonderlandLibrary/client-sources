package me.vaziak.sensation.client;

import java.util.ArrayList;
import java.util.HashMap;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.KeyPressEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.impl.combat.*;
import me.vaziak.sensation.client.impl.misc.*;
import me.vaziak.sensation.client.impl.misc.cheststealer.ChestStealer;
import me.vaziak.sensation.client.impl.movement.*;
import me.vaziak.sensation.client.impl.player.*;
import me.vaziak.sensation.client.impl.visual.*;

/**
 * @author antja03
 */
public class ModuleInstantiator {

	private final HashMap<String, Module> cheatRegistry;
	public ModuleInstantiator() {
		cheatRegistry = new HashMap<>();
		Sensation.eventBus.register(this);
	}

	public void registerCheats() {
		/*
		 * STATS: 
		 * Spec: 30 Cheats made and 28 fully complete
		 * Criiptic: 7 Cheats made, 7 working
		 * AnthonyJ: 9 Cheats made - 7 Confirmed completely working
		 * 
		 * Total cheats - 60 
		 */
		/* Combat */
		registerCheat(new KillAura());//Criiptic
		registerCheat(new AntiBot());// Spec/Criiptic
		registerCheat(new AutoPot());// Spec/Verble
		registerCheat(new Criticals());//Criiptic
		registerCheat(new AutoGapple());//oHare/oHareware - Took from Xen's github
		registerCheat(new MoreAttacks());// Spec
		registerCheat(new KillSults());//Spec
		registerCheat(new InfiniteAura());//Spec & Vaz

		/* Misc */
		registerCheat(new ChestStealer());// Anthony
		registerCheat(new AutoInventory());// Anthony
		registerCheat(new ChatBypass());//oHare/oHareware - Took from Xen's github
		registerCheat(new Debugger());//Criiptic
		registerCheat(new AutoSword());//oHare/oHareware - Took from Xen's github
		registerCheat(new Spammer());//Spec
		registerCheat(new Timer());// Spec
		registerCheat(new IRC()); // Vaziak
		registerCheat(new AutoPlay()); // VaZiAk
		registerCheat(new Nuker()); // VaZiAk
		registerCheat(new AntiLag());//Criiptic
		registerCheat(new ChestAura()); // VaZiAk
		registerCheat(new HackerDetect());//Criiptic/FlyCode
		registerCheat(new MCF());
		/* Movement */
		registerCheat(new Flight());// Criiptic - original by spec - recoded
		registerCheat(new Speed());// Spec
		registerCheat(new LongHop());// Spec
		registerCheat(new NoSlowdown());// Spec
		registerCheat(new Jesus());// Spec
		registerCheat(new GuiMove());// Spec
		registerCheat(new Step());// Spec
		registerCheat(new Scaffold());// Spec
		registerCheat(new NoFall());//Spec
		registerCheat(new Jesus());//Spec
		registerCheat(new Sprint());//Spec
		registerCheat(new FastLadder());//Spec
		registerCheat(new TargetStrafe());

		/* Player */
		registerCheat(new Derp());// Spec
		registerCheat(new AntiKnockback());// Spec/Criiptic
		registerCheat(new Disabler());// Spec
		registerCheat(new FastUse());// Spec
		registerCheat(new Regen());// Spec
		registerCheat(new Phase());// Spec & oHare/oHareware - took code from Xen's github
		registerCheat(new AntiVoid());// Spec
		registerCheat(new AntiServer());//Spec

		/* Visual */
		registerCheat(new BlockAnimation());// Spec
		registerCheat(new Brightness());// Anthony
		registerCheat(new ChestESP());// Anthony
		registerCheat(new ItemPhysics());
		registerCheat(new ESP());// Anthony
		registerCheat(new Overlay());// Spec
		registerCheat(new Interface());// Anthony
		registerCheat(new NameProtect());// Spec
		registerCheat(new ViewClip());// Spec
		registerCheat(new XRay());// Spec
		registerCheat(new TimeChanger());// Anthony
		registerCheat(new HudEditor());// Anthony
		registerCheat(new NoFov());// Spec
		registerCheat(new Chams());//Zane
		registerCheat(new NoHurtCam());
		registerCheat(new Notifications());
		registerCheat(new Crosshair());
	}

	public void registerCheat(Object object) {
		if (object instanceof Module) {
			Module cheat = (Module) object;
			if (!cheatRegistry.containsValue(cheat)) {
				/*
				 * Making sure the registry doesn't contain the cheat, will allow for future
				 * ghost mode
				 *
				 * TODO: Ghost mode
				 */
				cheatRegistry.put(cheat.getId(), cheat);
			}
		}
	}

	@Collect
	public void onKeyPress(KeyPressEvent event) {
		for (Module cheat : cheatRegistry.values()) {
			if (event.getKeyCode() == cheat.getBind()) {
				cheat.setState(!cheat.getState(), true);
			}
		}
	}

	public ArrayList<Module> searchRegistry(Category category) {
		ArrayList<Module> cheats = new ArrayList<>();
		cheatRegistry.values().forEach(cheat -> {
			if (cheat.getCategory().equals(category)) {
				cheats.add(cheat);
			}
		});
		return cheats;
	}

	public ArrayList<Module> searchRegistry(String term) {
		ArrayList<Module> cheats = new ArrayList<>();
		cheatRegistry.values().forEach(cheat -> {
			if (cheat.getId().toLowerCase().equals(term.toLowerCase())) {
				cheats.add(cheat);
			}
		});
		return cheats;
	}

	public ArrayList<Module> searchRegistryReplaceSpaces(String term) {
		ArrayList<Module> cheats = new ArrayList<>();
		cheatRegistry.values().forEach(cheat -> {
			if (cheat.getId().replaceAll("\\s+", "").equalsIgnoreCase(term)) {
				cheats.add(cheat);
			}
		});
		return cheats;
	}

	public HashMap<String, Module> getCheatRegistry() {
		return cheatRegistry;
	}

	public boolean isModuleEnabled(String id) {
		return getCheatRegistry().get(id).getState();
	}

}
