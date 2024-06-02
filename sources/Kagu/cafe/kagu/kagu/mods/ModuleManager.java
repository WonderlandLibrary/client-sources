/**
 * 
 */
package cafe.kagu.kagu.mods;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.mods.impl.yiff.*;
import cafe.kagu.kagu.mods.impl.visual.*;
import cafe.kagu.kagu.mods.impl.player.*;
import cafe.kagu.kagu.mods.impl.move.*;
import cafe.kagu.kagu.mods.impl.ghost.*;
import cafe.kagu.kagu.mods.impl.exploit.*;
import cafe.kagu.kagu.mods.impl.combat.*;

/**
 * @author DistastefulBannock
 *
 */
public class ModuleManager {

	private Logger logger = LogManager.getLogger();
	
	// An array of all the modules in the client
	private final Map<Class<? extends Module>, Module> MODULES = new LinkedHashMap<>(); // Why hashmap no preserve order :(

	/**
	 * Called at the start of the client
	 */
	public void start() {
		logger.info("Registering modules...");
		
		registerModule(new ModClickGui());
		registerModule(new ModAntiAim());
		registerModule(new ModHud());
		registerModule(new ModAnimations());
		registerModule(new ModChestEsp());
		registerModule(new ModEsp());
		registerModule(new ModNormalZoomCam());
		registerModule(new ModMoonJump());
		registerModule(new ModSprint());
		registerModule(new ModDisabler());
		registerModule(new ModCreative64Stack());
		registerModule(new ModDebugBoundingBoxes());
		registerModule(new ModTest());
		registerModule(new ModAntiCrash());
		registerModule(new ModViewModels());
		registerModule(new ModKillAura());
		registerModule(new ModBacktrack());
		registerModule(new ModTimer());
		registerModule(new ModCrasher());
		registerModule(new ModReach());
		registerModule(new ModHitboxes());
		registerModule(new ModNoHCollisionSlowdown());
		registerModule(new ModSpeed());
		registerModule(new ModDistastefulEars());
		registerModule(new ModAmbience());
		registerModule(new ModFly());
		registerModule(new ModScaffold());
		registerModule(new ModChestStealer());
		registerModule(new ModGroundClip());
		registerModule(new ModSpecialSlime());
		registerModule(new ModVelocity());
		registerModule(new ModInventoryManager());
		registerModule(new ModAntiBot());
		registerModule(new ModNoSlow());
		registerModule(new ModKeepSprintAfterCombat());
		registerModule(new ModCivBreak());
		registerModule(new ModStep());
		registerModule(new ModTargetHud());
		registerModule(new ModNoFall());
		registerModule(new ModAntiVoid());
		registerModule(new ModHideHud());
		registerModule(new ModCamera());
		registerModule(new ModObsProofUi());
		registerModule(new ModObsProofEsp());
		registerModule(new ModSafeWalk());
		registerModule(new ModFunnyLimbs());
		registerModule(new ModSpider());
		registerModule(new ModAntiGroundClipStuck());
		registerModule(new ModMagnetAura());
		registerModule(new ModAutoRod());
		registerModule(new ModNoHitDelay());
		registerModule(new ModChangeRightClickDelay());
		registerModule(new ModAimAssist());
		registerModule(new ModSlashSpawn());
		registerModule(new ModEagle());
		registerModule(new ModInventoryHelper());
		registerModule(new ModKeepSprint());
		registerModule(new ModObsProofHud());
		registerModule(new ModWideMen());
		registerModule(new ModHideName());
		registerModule(new ModUwuifier());
		registerModule(new ModBacktrack());
		registerModule(new ModGCDFix());
		registerModule(new ModNewScaffold());
		
		registerModule(new ModBlink()); // Blink last because we want it to get events last (event bus no priority options 😭)
		logger.info("Registered the modules...");
		
		logger.info("Loading modules...");
		for (Module module : MODULES.values()) {
			Kagu.getEventBus().subscribe(module); // Subscribe any listeners to the event bus
		}
		logger.info("Loaded the modules");
	}
	
	/**
	 * Registers a module
	 * @param module The module to register
	 */
	private void registerModule(Module module) {
		MODULES.put(module.getClass(), module);
	}
	
	/**
	 * Gets a module
	 * @param module the class of the module
	 * @return The module object, or null if it doesn't exit
	 */
	@SuppressWarnings("unchecked")
	public <M extends Module> M getModule(Class<M> module) {
		return (M) MODULES.get(module);
	}
	
	/**
	 * @return the modules
	 */
	public Collection<Module> getModules() {
		return MODULES.values();
	}

}
